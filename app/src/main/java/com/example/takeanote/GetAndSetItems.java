package com.example.takeanote;

public class GetAndSetItems {

    public int id;
    public String title;
    public String text;


    public GetAndSetItems(int id, String title, String text) {
        this.id = id;
        this.title = title;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle1() {
        return title;
    }

    public void setTitle1(String title) {
        this.title = title;
    }

    public String getText1() {
        return text;
    }

    public void setText1(String text) {
        this.text = text;
    }
}
