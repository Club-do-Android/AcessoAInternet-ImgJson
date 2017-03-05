package com.example.pedro.jsonimg.model;

/**
 * Created by pedro on 19/02/17.
 */

public class Post {
    private int userid;
    private int id;
    private String title;
    private String body;

    public Post(){}

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return this.id + " - " + this.title;
    }
}
