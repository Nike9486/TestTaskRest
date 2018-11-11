package com.rest.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Hall implements Serializable {

    private int ROW_ID;
    String HallName;
    int PlaceCount;
    Places PlaceList;

    public int getROW_ID() {
        return ROW_ID;
    }

    public void setROW_ID(int ROW_ID) {
        this.ROW_ID = ROW_ID;
    }

    public void setPlaceList(Places placeList) {
        PlaceList = placeList;
    }



    public String getHallName() {
        return HallName;
    }

    public void setHallName(String hallName) {
        HallName = hallName;
    }

    public int getPlaceCount() {
        return PlaceCount;
    }

    public void setPlaceCount(int placeCount) {
        PlaceCount = placeCount;
    }

    public Places getPlaceList() {
        return PlaceList;
    }

//    public void setPlaceList(List<Place> placeList) {
//        if(PlaceList==null){
//            PlaceList = new Places();
//            PlaceList.setPlaces(new ArrayList<>());
//        }
//        PlaceList.setPlaces( placeList);
//    }


    @Override
    public String toString() {
        return "Hall{" +
                "ROW_ID=" + ROW_ID +
                ", HallName='" + HallName + '\'' +
                ", PlaceCount=" + PlaceCount +
                ", PlaceList=" + PlaceList +
                '}';
    }
}
