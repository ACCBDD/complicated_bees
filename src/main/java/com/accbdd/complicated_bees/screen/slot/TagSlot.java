package com.accbdd.complicated_bees.screen.slot;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class TagSlot extends SlotItemHandler {
    private final TagKey<Item> tag;

    public TagSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, TagKey<Item> tag) {
        super(itemHandler, index, xPosition, yPosition);
        this.tag = tag;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.is(tag);
    }
}
