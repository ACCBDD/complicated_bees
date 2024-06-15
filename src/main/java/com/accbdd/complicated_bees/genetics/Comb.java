package com.accbdd.complicated_bees.genetics;

import net.minecraft.world.item.Items;

public class Comb {
    private final String id;
    private final int outerColor;
    private final int innerColor;
    private final CombProducts products;

    public static final Comb NULL_COMB = new Comb("null", 0, 0, new CombProducts(Items.AIR.getDefaultInstance(), 0, Items.AIR.getDefaultInstance(), 0));

    public Comb(String id, int outerColor, int innerColor, CombProducts products) {
        this.id = id;
        this.outerColor = outerColor;
        this.innerColor = innerColor;
        this.products = products;
    }

    public String getId() {
        return id;
    }

    public int getOuterColor() {
        return this.outerColor;
    }

    public int getInnerColor() {
        return this.innerColor;
    }

    public CombProducts getProducts() {
        return products;
    }
}
