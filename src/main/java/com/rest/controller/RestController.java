package com.rest.controller;

import com.google.gson.Gson;
import com.rest.dao.DBConnectorImpl;
import com.rest.pojo.*;

import javax.ws.rs.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Path("/orderplace")
public class RestController {

    @GET
    @Produces("aplication/json")
    public String getCinemas() throws SQLException {

        Gson gson = new Gson();
        Cinemas cinemas = new Cinemas();
        cinemas.setCinemas(DBConnectorImpl.getCinemas());
        return gson.toJson(cinemas);
    }


    @GET
    @Path("cinema_id/{cinema_id}")
    @Produces("aplication/json")
    public String getHallsFromCinema(@PathParam("cinema_id") int cinema_id) throws SQLException {

        Gson gson = new Gson();


        Halls hall = new Halls();
        hall.setHalls(DBConnectorImpl.getHallsFromCinema(cinema_id));


        return gson.toJson(hall);
    }

    @GET
    @Path("cinema_id/{cinema_id}/Hall_id/{hall_id}")
    @Produces("aplication/json")
    public String getHallFromCinema(@PathParam("cinema_id") int cinema_id, @PathParam("hall_id") int hall_id) throws SQLException {

        Gson gson = new Gson();


        Hall hall = (DBConnectorImpl.getHallFromCinema(hall_id, cinema_id));


        return gson.toJson(hall);
    }

//    @GET
//    @Produces("text/html")
//    public String getGreeting() {
//        Gson gson = new Gson();
//        Cinema cinema = new Cinema();
//        cinema.setCinemaName("First");
//        cinema.setHallCount(1);
//        Hall hall = new Hall();
//        hall.setHallName("1");
//        hall.setPlaceCount(5);
//        List<Place> places = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            Place place = new Place();
//            place.setPlace(i);
//            place.setRow(1);
//            if (i % 3 == 0) {
//                place.setFree(false);
//            }
//            places.add(place);
//        }
//        hall.setPlaceList(places);
//        cinema.setHalls(Collections.singletonList(hall));
//
//        return gson.toJson(cinema);
//    }


    @POST
    @Consumes("aplication/json")
    @Produces("aplication/json")
    public String addNewCinema(String content) {
        Gson g = new Gson();

        Cinema c = g.fromJson(content, Cinema.class);
        try {
            DBConnectorImpl.addCinema(c);
        } catch (Exception e) {

        }
        return "We add new cinema: " + c.getCinemaName();
    }

    @POST
    @Consumes("aplication/json")
    @Path("cinema_id/{cinema_id}")
    public String addNewHall(@PathParam("cinema_id") int cinema_id, String content) {
        Gson g = new Gson();

        Hall h = g.fromJson(content, Hall.class);
        System.out.println(g.toJson(h));
        try {
            System.out.println(cinema_id);
            DBConnectorImpl.addHall(cinema_id, h);
        } catch (Exception e) {

        }
        return "We add new hall in cinema:" + cinema_id;
    }

    @DELETE
    @Path("cinema_id/{cinema_id}")
    @Consumes("aplication/json")
    public String deleteCinema(@PathParam("cinema_id") int cinema_id) throws SQLException {
        DBConnectorImpl.deleteCinema(DBConnectorImpl.getCinema(cinema_id));
        return "We delete cinema:" + cinema_id;
    }

    @DELETE
    @Path("cinema_id/{cinema_id}/Hall_id/{hall_id}")
    @Consumes("aplication/json")
    public String deleteHall(@PathParam("cinema_id") int cinema_id, @PathParam("hall_id") int hall_id) throws SQLException {
        DBConnectorImpl.deleteHall(DBConnectorImpl.getCinema(cinema_id), DBConnectorImpl.getHallFromCinema(hall_id, cinema_id));
        return "We delete hall:" + hall_id + "from cinema:" + cinema_id;
    }


    @POST
    @Path("cinema_id/{cinema_id}/Hall_id/{hall_id}/order")
    public String changePlaceStatus(@PathParam("cinema_id") int cinema_id, @PathParam("hall_id") int hall_id, String content) {
        Gson g = new Gson();
        System.out.println(content);
        Places h = g.fromJson(content, Places.class);
        try {
            DBConnectorImpl.updateHallStatus(hall_id, h);
        } catch (Exception e) {

        }

        return "We change place status: " + hall_id;
    }
}
