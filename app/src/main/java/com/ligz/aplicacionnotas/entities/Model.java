package com.ligz.aplicacionnotas.entities;

public class Model {

    String title;
    String description;
    String id;
    String date;

    public Model(String id, String title, String description, String date) {
        this.title = title;
        this.description = description;
        this.id = id;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
