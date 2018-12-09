package com.TicketIT.Model;

import org.eclipse.jetty.util.URIUtil;

import java.util.UUID;

public class Event {

    private String id;

    private String title;

    private String description;

    private String date;

    private String time;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }

    public void setTime(String time) { this.time = time; }

    public String getNameAsImagePath(){
        return "images/" + this.title.toLowerCase().replaceAll("\\s+","") + ".jpg";
    }
}
