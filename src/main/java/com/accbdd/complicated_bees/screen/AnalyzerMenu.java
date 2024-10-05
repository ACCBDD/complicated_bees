package com.accbdd.complicated_bees.screen;

import com.accbdd.complicated_bees.datagen.ItemTagGenerator;
import com.accbdd.complicated_bees.item.BeeItem;
import com.accbdd.complicated_bees.registry.MenuRegistration;
import com.accbdd.complicated_bees.screen.slot.TagSlot;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class AnalyzerMenu extends AbstractContainerMenu {
    public static final int SLOT_COUNT = 2;
    private static final String INVENTORY_TAG = "contents";

    private final int bagSlot;
    private final ItemStackHandler handler;

    public AnalyzerMenu(int windowId, Player player, int bagSlot) {
        super(MenuRegistration.ANALYZER_MENU.get(), windowId);
        this.bagSlot = bagSlot;
        this.handler = new ItemStackHandler(2) {
            @Override
            protected void onContentsChanged(int slot) {
                if (getSlot(0).hasItem()) {
                    ItemStack bee = getSlot(1).getItem();
                    if (!isBeeAnalyzed() && !bee.isEmpty()) {
                        bee.getOrCreateTag().putBoolean(BeeItem.ANALYZED_TAG, true);
                        getSlot(0).remove(1);
                    }
                }
                player.getInventory().getItem(bagSlot).getOrCreateTag().put(INVENTORY_TAG, this.serializeNBT());
            }
        };

        handler.deserializeNBT(player.getInventory().getItem(bagSlot).getOrCreateTag().getCompound(INVENTORY_TAG));
        addSlot(new TagSlot(handler, 0, 225, 8, ItemTagGenerator.ANALYZER_FUEL));
        addSlot(new TagSlot(handler, 1, 225, 26, ItemTagGenerator.BEE));
        layoutPlayerInventorySlots(player.getInventory(), 36, 134);
    }

    @Override
    public void removed(Player pPlayer) {
        ItemStack analyzer = pPlayer.getInventory().getItem(bagSlot);
        analyzer.getOrCreateTag().put(INVENTORY_TAG, handler.serializeNBT());
        super.removed(pPlayer);
    }

    public static AnalyzerMenu fromNetwork(int windowId, Inventory playerInv) {
        return new AnalyzerMenu(windowId, playerInv.player, playerInv.selected);
    }

    public boolean isBeeAnalyzed() {
        ItemStack bee = getSlot(1).getItem();
        return bee.is(ItemTagGenerator.BEE) && bee.getOrCreateTag().getBoolean(BeeItem.ANALYZED_TAG);
    }

    private int addSlotRange(Container playerInventory, int index, int x, int y, int amount, int dx) {
        for (int i = 0; i < amount; i++) {
            addSlot(new Slot(playerInventory, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private void addSlotBox(Container playerInventory, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0; j < verAmount; j++) {
            index = addSlotRange(playerInventory, index, x, y, horAmount, dx);
            y += dy;
        }
    }

    private void layoutPlayerInventorySlots(Container playerInventory, int leftCol, int topRow) {
        // Player inventory
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar
        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack stack = slot.getItem();
            itemstack = stack.copy();
            if (index < SLOT_COUNT) {
                if (!this.moveItemStackTo(stack, SLOT_COUNT, Inventory.INVENTORY_SIZE + SLOT_COUNT, true)) {
                    return ItemStack.EMPTY;
                }
            }
            if (!this.moveItemStackTo(stack, 0, 2, false)) {
                if (index < 27 + SLOT_COUNT) {
                    if (!this.moveItemStackTo(stack, 27 + SLOT_COUNT, 36 + SLOT_COUNT, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < Inventory.INVENTORY_SIZE + SLOT_COUNT && !this.moveItemStackTo(stack, SLOT_COUNT, 27 + SLOT_COUNT, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (stack.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, stack);
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void clicked(int pSlotId, int pButton, ClickType pClickType, Player pPlayer) {
        if (this.bagSlot == pSlotId - SLOT_COUNT - 27)
            return;
        super.clicked(pSlotId, pButton, pClickType, pPlayer);
    }
}
