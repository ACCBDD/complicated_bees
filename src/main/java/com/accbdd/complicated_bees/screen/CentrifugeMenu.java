package com.accbdd.complicated_bees.screen;

import com.accbdd.complicated_bees.block.entity.CentrifugeBlockEntity;
import com.accbdd.complicated_bees.registry.BlocksRegistration;
import com.accbdd.complicated_bees.registry.MenuRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.SlotItemHandler;

import static com.accbdd.complicated_bees.block.entity.CentrifugeBlockEntity.*;

public class CentrifugeMenu extends AbstractContainerMenu {
    private final BlockPos pos;
    private final ContainerData data;

    private int power;

    public CentrifugeMenu(int windowId, Player player, BlockPos pos) {
        this(windowId, player, pos, new SimpleContainerData(2));
    }

    public CentrifugeMenu(int windowId, Player player, BlockPos pos, ContainerData data) {
        super(MenuRegistration.CENTRIFUGE_MENU.get(), windowId);
        this.data = data;
        this.pos = pos;
        if (player.level().getBlockEntity(pos) instanceof CentrifugeBlockEntity centrifuge) {
            addSlot(new SlotItemHandler(centrifuge.getInputItems(), INPUT_SLOT, 34, 35));
            for (int i = 0; i < 9; i++) {
                addSlot(new SlotItemHandler(centrifuge.getOutputItems(),
                        OUTPUT_SLOT+i,
                        91+(18 * (i % 3)),
                        17+(18 * (i / 3))) {
                    @Override
                    public boolean mayPlace(ItemStack stack) {
                        return false;
                    }
                });
            }
            addDataSlot(new DataSlot() {
                @Override
                public int get() {
                    return centrifuge.getEnergyHandler().getEnergyStored() & 0xffff;
                }

                @Override
                public void set(int pValue) {
                    CentrifugeMenu.this.power = (CentrifugeMenu.this.power & 0xffff0000) | (pValue & 0xffff);
                }
            });
            addDataSlot(new DataSlot() {
                @Override
                public int get() {
                    return (centrifuge.getEnergyHandler().getEnergyStored() >> 16) & 0xffff;
                }

                @Override
                public void set(int pValue) {
                    CentrifugeMenu.this.power = (CentrifugeMenu.this.power & 0xffff) | ((pValue & 0xffff) << 16);
                }
            });
        }
        layoutPlayerInventorySlots(player.getInventory(), 8, 84);

        addDataSlots(data);
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }

    public int getPower() {
        return power;
    }

    public int getScaledProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);
        int progressArrowSize = 20;

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    private int addSlotRange(Container playerInventory, int index, int x, int y, int amount, int dx) {
        for (int i = 0 ; i < amount ; i++) {
            addSlot(new Slot(playerInventory, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(Container playerInventory, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0 ; j < verAmount ; j++) {
            index = addSlotRange(playerInventory, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
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
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack stack = slot.getItem();
            itemStack = stack.copy();
            if (index < SLOT_COUNT) { //if index of stack is in the centrifuge, try to move it to the inventory
                if (!this.moveItemStackTo(stack, SLOT_COUNT, Inventory.INVENTORY_SIZE + SLOT_COUNT, true)) {
                    return ItemStack.EMPTY;
                }
            }
            if (!this.moveItemStackTo(stack, INPUT_SLOT, INPUT_SLOT + 1, false)) { //if you can't move the stack to the input
                if (index < 27 + SLOT_COUNT) { // if the stack is in main inventory?, move it to hotbar
                    if (!this.moveItemStackTo(stack, 27 + SLOT_COUNT, 36 + SLOT_COUNT, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < Inventory.INVENTORY_SIZE + SLOT_COUNT && !this.moveItemStackTo(stack, SLOT_COUNT, 27 + SLOT_COUNT, false)) { //if the stack is in hotbar?, move it to main inventory?
                    return ItemStack.EMPTY;
                }
            }
            if (stack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (stack.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, stack);
        }

        return itemStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(player.level(), pos), player, BlocksRegistration.CENTRIFUGE.get());
    }
}
