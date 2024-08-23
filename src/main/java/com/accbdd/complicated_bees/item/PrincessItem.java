package com.accbdd.complicated_bees.item;

import net.minecraft.world.item.ItemStack;

public class PrincessItem extends BeeItem {
    public static final String GENERATION_TAG = "generation";

    public PrincessItem(Properties prop) {
        super(prop.stacksTo(1));
    }

    public static int getGeneration(ItemStack stack) {
        return stack.getOrCreateTag().getInt(GENERATION_TAG);
    }

    public static void setGeneration(ItemStack stack, int gen) {
        stack.getOrCreateTag().putInt(GENERATION_TAG, gen);
    }
}
