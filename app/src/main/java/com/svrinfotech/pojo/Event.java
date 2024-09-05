package com.svrinfotech.pojo;

import java.io.Serializable;

/**
 * Created by Durgesh on 23-02-2018.
 */

public class Event implements Serializable {

    private String title,description,image;
    private int likes;
    public Event() {

    }

    public Event(String title, String description, String image, int likes) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.likes=likes;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public float getLikes() { return likes; }
}