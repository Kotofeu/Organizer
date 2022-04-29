package com.example.Organizer.adapter;

import java.io.Serializable;

public class link implements Serializable {
    private String title;
    private String desc;
    private String date;
    private String image;
    private int notifi;
    private int id = 0;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public boolean getNotifi() {
        if (notifi == 1){
            return true;
        }
        else {
            return false;
        }
    }

    public void setNotifi(int notifi) {
        this.notifi = notifi;
    }
}
