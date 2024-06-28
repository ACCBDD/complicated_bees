package com.accbdd.complicated_bees.screen;

import com.accbdd.complicated_bees.block.entity.ApiaryBlockEntity;
import com.accbdd.complicated_bees.registry.BlocksRegistration;
import com.accbdd.complicated_bees.registry.ItemsRegistration;
import com.accbdd.complicated_bees.registry.MenuRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

import static com.accbdd.complicated_bees.block.entity.ApiaryBlockEntity.*;

public class ApiaryMenu extends AbstractContainerMenu {
    private final BlockPos pos;
    private final ContainerData data;

    public ApiaryMenu(int windowId, Player player, BlockPos pos) {
        this(windowId, player, pos, new SimpleContainerData(3));
    }

    public ApiaryMenu(int windowId, Player player, BlockPos pos, ContainerData data) {
        super(MenuRegistration.APIARY_MENU.get(), windowId);
        this.data = data;
        this.pos = pos;
        if (player.level().getBlockEntity(pos) instanceof ApiaryBlockEntity apiary) {
            addSlot(new SlotItemHandler(apiary.getBeeItems(), BEE_SLOT, 29, 38));
            addSlot(new SlotItemHandler(apiary.getBeeItems(), BEE_SLOT+1, 29, 63));

            addSlot(createOutputSlot(apiary.getOutputItems(), OUTPUT_SLOT, 115, 51));
            addSlot(createOutputSlot(apiary.getOutputItems(), OUTPUT_SLOT+1, 115, 26));
            addSlot(createOutputSlot(apiary.getOutputItems(), OUTPUT_SLOT+2, 137, 39));
            addSlot(createOutputSlot(apiary.getOutputItems(), OUTPUT_SLOT+3, 137, 64));
            addSlot(createOutputSlot(apiary.getOutputItems(), OUTPUT_SLOT+4, 115, 76));
            addSlot(createOutputSlot(apiary.getOutputItems(), OUTPUT_SLOT+5, 93, 64));
            addSlot(createOutputSlot(apiary.getOutputItems(), OUTPUT_SLOT+6, 93, 39));

            addSlot(new SlotItemHandler(apiary.getFrameItems(), FRAME_SLOT, 65, 23));
            addSlot(new SlotItemHandler(apiary.getFrameItems(), FRAME_SLOT+1, 65, 51));
            addSlot(new SlotItemHandler(apiary.getFrameItems(), FRAME_SLOT+2, 65, 79));
        }
        layoutPlayerInventorySlots(player.getInventory(), 8, 105);

        addDataSlots(data);
    }

    private Slot createOutputSlot(ItemStackHandler handler, int index, int xPos, int yPos) {
        return new SlotItemHandler(handler, index, xPos, yPos) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }
        };
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
            if (index < SLOT_COUNT) { //if index of stack is in the apiary, try to move it to the inventory
                if (!this.moveItemStackTo(stack, SLOT_COUNT, Inventory.INVENTORY_SIZE + SLOT_COUNT, true)) {
                    return ItemStack.EMPTY;
                }
            }
            if (!this.moveItemStackTo(stack, BEE_SLOT, BEE_SLOT + 2, false)) { //if you can't move the stack to the input
                if (index < 27 + SLOT_COUNT) { // if the stack is in main inventory, move it to hotbar
                    if (!this.moveItemStackTo(stack, 27 + SLOT_COUNT, 36 + SLOT_COUNT, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < Inventory.INVENTORY_SIZE + SLOT_COUNT && !this.moveItemStackTo(stack, SLOT_COUNT, 27 + SLOT_COUNT, false)) { //if the stack is in hotbar, move it to main inventory
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
        return stillValid(ContainerLevelAccess.create(player.level(), pos), player, BlocksRegistration.APIARY.get());
    }

    public boolean hasQueen() {
        ItemStack item = getSlot(0).getItem();
        return item.getItem() == ItemsRegistration.QUEEN.get();
    }

    public int getScaledProgress(int progress, int maxProgress) {
        int barHeight = 45;
        int i = maxProgress != 0 && progress != 0 ? progress * barHeight / maxProgress : 0;
        return i;
    }

    public ItemStack getQueen() {
        return getSlot(0).getItem();
    }

    public boolean isBreeding() {
        return data.get(0) > 0;
    }

    public ContainerData getData() {
        return this.data;
    }
}
