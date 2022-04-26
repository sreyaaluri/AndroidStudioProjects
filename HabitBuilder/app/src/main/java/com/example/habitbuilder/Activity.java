package com.example.habitbuilder;

import java.util.Date;

public class Activity {

    // instance variables
    public Date date;
    String dateText;
    String name;
    int rating; // 0 bad, 1 neutral, 2 good

    // default constructor
    public Activity() {}

    // parameterized constructor
    public Activity(Date date, String name, int rating) {
        this.date = date;
        this.name = name;
        this.rating = rating;
    }
}
