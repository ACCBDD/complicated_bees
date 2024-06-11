package com.accbdd.complicated_bees.genetics;

public class Species {
    private int color;
    private String id;

    public Species(String id, int color) {
        this.id = id;
        this.color = color;
    }

    public Species(String id) {
        new Species(id,0xFFFFFF);
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
