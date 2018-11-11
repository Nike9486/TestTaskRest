package com.rest.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cinema implements Serializable {

    private int ROW_ID;

    public int getROW_ID() {
        return ROW_ID;
    }

    public void setROW_ID(int ROW_ID) {
        this.ROW_ID = ROW_ID;
    }



    private String CinemaName;
    private int HallCount;
   Halls HallsList;

    public Halls getHallsList() {
        return HallsList;
    }

    public void setHallsList(Halls hallsList) {
        if(HallsList==null){
            HallsList = new Halls();
            HallsList.setHalls(new ArrayList<>());
        }
        HallsList = hallsList;
    }

    public String getCinemaName() {
        return CinemaName;
    }

    public void setCinemaName(String cinemaName) {
        CinemaName = cinemaName;
    }

    public int getHallCount() {
        return HallCount;
    }

    public void setHallCount(int hallCount) {
        HallCount = hallCount;
    }

    @Override
    public String toString() {
        return "Cinema{" +
                "ROW_ID=" + ROW_ID +
                ", CinemaName='" + CinemaName + '\'' +
                ", HallCount=" + HallCount +
                ", HallsList=" + HallsList +
                '}';
    }
}
