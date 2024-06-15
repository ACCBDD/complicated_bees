package com.accbdd.complicated_bees.genetics;

import net.minecraft.world.item.ItemStack;

import java.util.Random;

public class CombProducts {
    private final ItemStack primary, secondary;
    private final float primary_chance, secondary_chance;
    private static final Random rand = new Random();

    public CombProducts(ItemStack primary, float primary_chance, ItemStack secondary, float secondary_chance) {
        this.primary = primary;
        this.secondary = secondary;
        this.primary_chance = primary_chance;
        this.secondary_chance = secondary_chance;
    }

    public ItemStack getPrimary() {
        return primary;
    }

    public ItemStack getSecondary() {
        return secondary;
    }

    public float getPrimaryChance() {
        return primary_chance;
    }

    public float getSecondaryChance() {
        return secondary_chance;
    }

    public ItemStack getPrimaryResult() {
        return rand.nextFloat() < this.primary_chance ? this.primary.copy() : ItemStack.EMPTY;
    }

    public ItemStack getSecondaryResult() {
        return rand.nextFloat() < this.secondary_chance ? this.secondary.copy() : ItemStack.EMPTY;
    }
}
