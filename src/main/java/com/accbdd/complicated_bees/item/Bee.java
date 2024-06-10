package com.accbdd.complicated_bees.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class Bee extends Item {
    public Bee(Properties prop) {
        super(prop);
    }

    public static int getColor(ItemStack stack) {
        return stack.getOrCreateTag().getInt("color");
    }

    public static void setColor(ItemStack stack, int color) {
        stack.getOrCreateTag().putInt("color", color);
    }

    public static int getItemColor(ItemStack stack, int tintIndex) {
        if (tintIndex == 1) {
            return getColor(stack);
        }
        return 0xFFFFFF;
    }
}
