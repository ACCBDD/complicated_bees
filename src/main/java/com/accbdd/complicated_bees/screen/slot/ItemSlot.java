package com.accbdd.complicated_bees.screen.slot;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ItemSlot extends SlotItemHandler {
    private final Item item;

    public ItemSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, Item item) {
        super(itemHandler, index, xPosition, yPosition);
        this.item = item;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.is(item);
    }
}
