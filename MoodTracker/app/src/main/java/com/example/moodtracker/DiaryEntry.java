package com.example.moodtracker;

public class DiaryEntry {

    // instance variables
    public String date ="";
    public String notes ="";
    public int exercise = 0;
    public int outside =0;
    public int socialize = 0;
    public int meditate = 0;
    public int read = 0;

    // default constructor
    public DiaryEntry() {}

    // parameterized constructor
    public DiaryEntry(String date, String notes, int exer, int out, int soc, int medit, int read) {
        this.date = date;
        this.notes = notes;
        this.exercise = exer;
        this.outside = out;
        this.socialize = soc;
        this.meditate = medit;
        this.read = read;
    }

    // getters and setters for instance variables

    public String getDate() { return date; }
    public String getNotes() { return notes; }
    public int getExercise() { return exercise; }
    public int getOutside() { return outside; }
    public int getSocialize() { return socialize; }
    public int getMeditate() { return meditate; }
    public int getRead() { return read; }

    public void setDate(String date) { this.date = date; }
    public void setNotes(String notes) { this.notes = notes; }
    public void setExercise(int exercise) { this.exercise = exercise; }
    public void setOutside(int outside) { this.outside = outside; }
    public void setSocialize(int socialize) { this.socialize = socialize; }
    public void setMeditate(int meditate) { this.meditate = meditate; }
    public void setRead(int read) { this.read = read; }
}
