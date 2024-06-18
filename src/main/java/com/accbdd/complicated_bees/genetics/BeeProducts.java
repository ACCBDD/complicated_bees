package com.accbdd.complicated_bees.genetics;

import net.minecraft.world.item.ItemStack;

import java.util.Random;

public class BeeProducts {
    private final ItemStack primary, secondary, specialty;
    private final float primary_chance, secondary_chance, specialty_chance;
    private static final Random rand = new Random();

    public BeeProducts(ItemStack primary, float primary_chance, ItemStack secondary, float secondary_chance, ItemStack specialty, float specialty_chance) {
        this.primary = primary;
        this.secondary = secondary;
        this.specialty = specialty;
        this.primary_chance = primary_chance;
        this.secondary_chance = secondary_chance;
        this.specialty_chance = specialty_chance;
    }

    public ItemStack getPrimary() {
        return primary;
    }

    public ItemStack getSecondary() {
        return secondary;
    }

    public ItemStack getSpecialty() {
        return specialty;
    }

    public float getSpecialtyChance() {
        return specialty_chance;
    }

    public float getSecondaryChance() {
        return secondary_chance;
    }

    public float getPrimaryChance() {
        return primary_chance;
    }

    public ItemStack getPrimaryResult() {
        return rand.nextFloat() < this.primary_chance ? this.primary.copy() : ItemStack.EMPTY;
    }

    public ItemStack getSecondaryResult() {
        return rand.nextFloat() < this.secondary_chance ? this.secondary.copy() : ItemStack.EMPTY;
    }

    public ItemStack getSpecialtyResult() {
        return rand.nextFloat() < this.specialty_chance ? this.specialty.copy() : ItemStack.EMPTY;
    }
}
