package com.rest.pojo;

public class Place {

    int Row,place;
    boolean free = true;

    public int getRow() {
        return Row;
    }

    public void setRow(int row) {
        Row = row;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    @Override
    public String toString() {
        return "Place{" +
                "Row=" + Row +
                ", place=" + place +
                ", free=" + free +
                '}';
    }
}
