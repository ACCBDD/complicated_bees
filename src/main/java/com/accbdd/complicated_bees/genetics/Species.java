package com.accbdd.complicated_bees.genetics;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

import java.util.Optional;

public class Species {
    private final String id;
    private final int color;
    private final Item primaryProduce;
    private final Item secondaryProduce;
    private final Item specialtyProduce;

    public Species(String id, int color, ResourceLocation primaryProduce, Optional<ResourceLocation> secondaryProduce, Optional<ResourceLocation> specialtyProduce) {
        this.id = id;
        this.color = color;
        this.primaryProduce = BuiltInRegistries.ITEM.get(primaryProduce);
        this.secondaryProduce = secondaryProduce.map(BuiltInRegistries.ITEM::get).orElse(Items.AIR);
        this.specialtyProduce = specialtyProduce.map(BuiltInRegistries.ITEM::get).orElse(Items.AIR);
    }

    public int getColor() {
        return this.color;
    }

    public String getId() {
        return id;
    }

    public Item getPrimaryProduce() {
        return primaryProduce;
    }

    public Item getSecondaryProduce() {
        return secondaryProduce;
    }

    public Item getSpecialtyProduce() {
        return specialtyProduce;
    }
}
