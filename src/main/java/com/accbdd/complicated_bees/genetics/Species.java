package com.accbdd.complicated_bees.genetics;

public class Species {
    private int color;
    private String id;

    public Species(int color) {
        this.color = color;
    }

    public Species() {
        new Species(0xFFFFFF);
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return this.color;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
