package com.accbdd.complicated_bees.genetics;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.Optional;

public class Comb {
    private final String id;
    private final int outerColor;
    private final int innerColor;
    private final Item primaryProduce;
    private final Item secondaryProduce;

    public Comb(String id, int outerColor, int innerColor, ResourceLocation primaryProduce, Optional<ResourceLocation> secondaryProduce) {
        this.id = id;
        this.outerColor = outerColor;
        this.innerColor = innerColor;
        this.primaryProduce = BuiltInRegistries.ITEM.get(primaryProduce);
        this.secondaryProduce = secondaryProduce.map(BuiltInRegistries.ITEM::get).orElse(Items.AIR);
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

    public Item getPrimaryProduce() {
        return primaryProduce;
    }

    public Item getSecondaryProduce() {
        return secondaryProduce;
    }
}
