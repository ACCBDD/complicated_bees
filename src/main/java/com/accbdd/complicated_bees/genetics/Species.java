package com.accbdd.complicated_bees.genetics;

import net.minecraft.world.item.Items;

public class Species {
    private final String id;
    private final int color;
    private final BeeProducts products;

    public static final Species NULL = new Species(
            "null",
            0,
            new BeeProducts(Items.AIR.getDefaultInstance(), 0, Items.AIR.getDefaultInstance(), 0, Items.AIR.getDefaultInstance(), 0));

    public Species(String id, int color, BeeProducts products) {
        this.id = id;
        this.color = color;
        this.products = products;
    }

    public int getColor() {
        return this.color;
    }

    public String getId() {
        return id;
    }

    public BeeProducts getProducts() {
        return products;
    }
}
