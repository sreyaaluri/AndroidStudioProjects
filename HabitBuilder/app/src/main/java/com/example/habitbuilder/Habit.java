package com.example.habitbuilder;

public class Habit {

    // instance variables
    public int alarm_id = -1;
    public String identity ="";
    public String hname ="";
    public String why ="";
    public String freq = "";
    public boolean reminder = false;
    public int hr = -1;
    public int min = -1;
    public String cue ="";
    public String craving ="";
    public String response ="";
    public String reward ="";

    // default constructor
    public Habit() {}

    // parameterized constructor
    public Habit(String identity, String hname, String why, String freq, boolean reminder,
                 int hr, int min, String cue, String craving, String response, String reward) {
        this.identity = identity;
        this.hname = hname;
        this.why = why;
        this.freq = freq;
        this.reminder = reminder;
        this.hr = hr;
        this.min = min;
        this.cue = cue;
        this.craving = craving;
        this.response = response;
        this.reward = reward;
    }
}
