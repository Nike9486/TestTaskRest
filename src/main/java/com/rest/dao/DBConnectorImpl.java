package com.rest.dao;

import com.google.gson.Gson;
import com.rest.pojo.Cinema;
import com.rest.pojo.Hall;
import com.rest.pojo.Place;
import com.rest.pojo.Places;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class DBConnectorImpl {

    private static Connection condb = null;

    private static String DriverMenager = "";
    private static String DataBase = "";
    private static String Login = "";
    private static String Password = "";

    public static void readCredential() {
        Properties prop = new Properties();

        try {
            InputStream inp = new FileInputStream("db.properties");
            prop.load(inp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        DriverMenager = prop.getProperty("DriverMenager");
        DataBase = prop.getProperty("DataBase");
        Login = prop.getProperty("Login");
        Password = prop.getProperty("Password");


    }


    public static void getConnection() throws ClassNotFoundException, SQLException, NamingException {

        readCredential();
        if (DriverMenager == null || DriverMenager.equals("")) {
            Context ctx = new InitialContext();
            DataSource dataSource = (DataSource) ctx.lookup("java:comp/env/myDB");
            condb = dataSource.getConnection();
        } else {


            Class.forName(DriverMenager);
            condb = DriverManager.getConnection(DataBase, Login, Password);
        }

    }


    public static Cinema getCinema(int c) throws SQLException {
        ResultSet resultSet = executeSelect("SELECT * FROM public.\"Cinemas\" WHERE \"Row_Num\" = " + c);
        Cinema cinema = new Cinema();
        resultSet.next();

        cinema.setROW_ID(resultSet.getInt(1));
        cinema.setCinemaName(resultSet.getString(2));
        cinema.setHallCount(resultSet.getInt(3));


        return cinema;
    }


    public static List<Cinema> getCinemas() throws SQLException {
        List<Cinema> cinemas = new ArrayList<>();
        ResultSet resultSet = executeSelect("SELECT * FROM public.\"Cinemas\" ");
        while (resultSet.next()) {

            Cinema cinema = new Cinema();
            cinema.setROW_ID(resultSet.getInt(1));
            cinema.setCinemaName(resultSet.getString(2));
            cinema.setHallCount(resultSet.getInt(3));
            cinemas.add(cinema);
        }
        condb.close();
        return cinemas;


    }


    public static Hall getHallFromCinema(int h, int c) throws SQLException {

        ResultSet resultSet = executeSelect("SELECT * FROM public.\"Halls\" where \"Cinema_RowID\"=" + c + " and \"Row_Num\"=" + h);
        Gson gson = new Gson();

        resultSet.next();
        Hall hall = new Hall();
        hall.setROW_ID(resultSet.getInt(1));
        hall.setHallName(resultSet.getString(2));
        hall.setPlaceList(gson.fromJson(new String(resultSet.getBytes(4)), Places.class));
        hall.setPlaceCount(resultSet.getInt(5));


        condb.close();
        return hall;
    }


    public static List<Hall> getHallsFromCinema(int c) throws SQLException {
        List<Hall> halls = new ArrayList<>();
        ResultSet resultSet = executeSelect("SELECT * FROM public.\"Halls\" where \"Cinema_RowID\"=" + c);
        Gson gson = new Gson();

        while (resultSet.next()) {

            Hall hall = new Hall();
            hall.setROW_ID(resultSet.getInt(1));
            hall.setHallName(resultSet.getString(2));
            System.out.println(new String(resultSet.getBytes(4)));
            hall.setPlaceList(gson.fromJson(new String(resultSet.getBytes(4)), Places.class));
            hall.setPlaceCount(resultSet.getInt(5));

            halls.add(hall);
        }
        condb.close();
        return halls;
    }


    public static int updateHallStatus(int h_id, Places p) throws SQLException {
        Gson gson = new Gson();
        boolean result = execute("UPDATE public.\"Halls\"\n" +
                "\tSET \"Place_Status\"='" + gson.toJson(p) + " WHERE \"Row_Num\" = " + h_id);
        if (result)
            return 0;
        return 1;
    }


    public static int addHall(int c, Hall h) throws SQLException {
        Gson gson = new Gson();
        if (h.getPlaceList() == null) {
            Places places = new Places();
            List<Place> placeList = new ArrayList<>();
            if (h.getPlaceCount() % 10 > 0) {
                for (int i = 1; i <= h.getPlaceCount() / 10; i++) {
                    Place place = new Place();
                    place.setPlace(i);
                    place.setRow(1);
                    place.setFree(true);

                    placeList.add(place);
                }

                for (int i = 1; i <= h.getPlaceCount() / 10; i++)
                    for (int j = 1; j <= 10; j++) {
                        Place place = new Place();
                        place.setPlace(j);
                        place.setRow(i + 1);
                        place.setFree(true);

                        placeList.add(place);
                    }
                places.setPlaces(placeList);
            } else {
                for (int i = 1; i <= h.getPlaceCount() / 10; i++)
                    for (int j = 1; j <= 10; j++) {
                        Place place = new Place();
                        place.setPlace(j);
                        place.setRow(i);
                        place.setFree(true);

                        placeList.add(place);
                    }
                places.setPlaces(placeList);
            }
            h.setPlaceList(places);
        }
        boolean result = execute("INSERT INTO public.\"Halls\"" +
                "(\"Row_Num\", \"Hall_Name\", \"Cinema_RowID\", \"Place_Status\", \"Place_Count\")" +
                "VALUES (" + h.getROW_ID() + ", '" + h.getHallName() + "', " + c + ", '" + gson.toJson(h.getPlaceList()) + "'," + h.getPlaceCount() + ");");
        if (result)
            return 0;
        return 1;
    }


    public static int addCinema(Cinema c) throws SQLException {
        boolean result = execute("INSERT INTO public.\"Cinemas\"(\"Row_Num\", \"Cinema_Name\", \"Halls_Count\") " +
                "VALUES (" + c.getROW_ID() + ", '" + c.getCinemaName() + "', " + c.getHallCount() + ");");
        if (result)
            return 0;
        return 1;
    }


    public static int deleteHall(Cinema c, Hall h) throws SQLException {
        boolean result = false;

        result = execute("DELETE FROM public.\"Halls\" WHERE \"Row_Num\"=" + h.getROW_ID());
        if (result) {
            result = execute("UPDATE public.\"Cinemas\" SET \"Halls_Count\"=" + (c.getHallCount() - 1) + " WHERE \"Row_Num\" = " + c.getROW_ID());

            c.setHallCount(c.getHallCount() - 1);
            if (result)
                return 0;
        }
        return 1;
    }


    public static int deleteCinema(Cinema c) throws SQLException {
        boolean result = execute("DELETE FROM public.\"Cinemas\" WHERE \"Row_Num\"=" + c.getROW_ID());
        if (result) {
            result = execute("DELETE FROM public.\"Halls\" WHERE \"Cinema_RowID\"=" + c.getROW_ID());


            if (result)
                return 0;
        }
        return 1;
    }


    private static ResultSet executeSelect(String sql) throws SQLException {
        if (condb == null || condb.isClosed()) {
            try {
                getConnection();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
        Statement statement = null;
        try {


            statement = condb.createStatement();
            return statement.executeQuery(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static boolean execute(String sql) throws SQLException {
        if (condb == null || condb.isClosed()) {
            try {
                getConnection();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
        Statement statement = null;
        try {


            statement = condb.createStatement();
            return statement.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
