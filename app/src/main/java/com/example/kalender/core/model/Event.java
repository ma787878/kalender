package com.example.kalender.core.model;

import java.util.Date;

import org.bson.types.ObjectId;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

// java class für fragment_einfuegen_termin.xml

public class Event extends RealmObject {
    @PrimaryKey
    private ObjectId id = ObjectId.get();
    private String title;
    private Date date;

    public Event() {

    }

    public ObjectId getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Date getDate() {
        return date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}

