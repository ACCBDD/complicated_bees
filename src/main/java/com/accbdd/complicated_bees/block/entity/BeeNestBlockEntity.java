package com.accbdd.complicated_bees.block.entity;

import com.accbdd.complicated_bees.genetics.Product;
import com.accbdd.complicated_bees.genetics.Species;
import com.accbdd.complicated_bees.registry.BlockEntitiesRegistration;
import com.accbdd.complicated_bees.registry.ItemsRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BeeNestBlockEntity extends BlockEntity implements Container {
    private final Species species;
    private final NonNullList<ItemStack> items = NonNullList.create();

    public BeeNestBlockEntity(Species species, BlockPos pPos, BlockState pBlockState) {
        super(BlockEntitiesRegistration.BEE_NEST_ENTITY.get(), pPos, pBlockState);
        this.species = species;
        fillHive();
    }

    public Species getSpecies() {
        return species;
    }

    public NonNullList<ItemStack> getItems() {
        return items;
    }

    private void fillHive() {
        items.clear();
        items.add(getSpecies().toStack(ItemsRegistration.PRINCESS.get()));
        items.add(getSpecies().toStack(ItemsRegistration.DRONE.get()));
        items.add(getSpecies().toStack(ItemsRegistration.DRONE.get()));
        items.addAll(getSpecies().getProducts().stream().map(Product::getStackResult).toList());
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, items);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        ContainerHelper.loadAllItems(tag, items);
    }

    @Override
    public int getContainerSize() {
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public ItemStack getItem(int pSlot) {
        return items.get(pSlot);
    }

    @Override
    public ItemStack removeItem(int pSlot, int pAmount) {
        ItemStack itemstack = ContainerHelper.removeItem(this.getItems(), pSlot, pAmount);
        if (!itemstack.isEmpty()) {
            this.setChanged();
        }

        return itemstack;
    }

    @Override
    public ItemStack removeItemNoUpdate(int pSlot) {
        return ContainerHelper.takeItem(this.getItems(), pSlot);
    }

    @Override
    public void setItem(int pSlot, ItemStack pStack) {
        this.getItems().set(pSlot, pStack);
        if (pStack.getCount() > this.getMaxStackSize()) {
            pStack.setCount(this.getMaxStackSize());
        }

        this.setChanged();
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return Container.stillValidBlockEntity(this, pPlayer);
    }

    @Override
    public void clearContent() {
        this.getItems().clear();
    }
}
