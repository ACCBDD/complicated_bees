package com.accbdd.complicated_bees.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

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

    @Override
    public @NotNull Component getName(ItemStack stack) {
        return Component.translatable("species.complicated_bees." +
                stack.getOrCreateTag().getInt("color"))
                .append(" ")
                .append(Component.translatable(getDescriptionId()));
    }

    public static int getItemColor(ItemStack stack, int tintIndex) {
        if (tintIndex == 1) {
            return getColor(stack);
        }
        return 0xFFFFFF;
    }
}
