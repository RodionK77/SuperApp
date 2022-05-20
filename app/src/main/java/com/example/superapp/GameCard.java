package com.example.superapp;

import java.io.Serializable;

public class GameCard implements Serializable {
    protected String description1;
    protected String description2;
    protected String date;
    protected String img;

    public GameCard(String description1, String description2, String date, String img){
        this.description1 = description1;
        this.description2 = description2;
        this.date = date;
        this.img = img;
    }

    public String getDescription1() {
        return description1;
    }

    public void setDescription1(String description1) {
        this.description1 = description1;
    }

    public String getDescription2() {
        return description2;
    }

    public void setDescription2(String description2) {
        this.description2 = description2;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String date) {
        this.img = img;
    }
}
