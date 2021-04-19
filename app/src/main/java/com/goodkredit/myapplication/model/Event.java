package com.goodkredit.myapplication.model;

import com.goodkredit.myapplication.bean.GKService;

public class Event {

    private GKService item;
    private String category;

    public Event(GKService item, String  category) {
        this.item = item;
        this.category = category;
    }

    public GKService getService() {
        return item;
    }

    public String getCategory() {
        return category;
    }

}