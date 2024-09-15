package com.accbdd.complicated_bees.block.entity;

import com.accbdd.complicated_bees.config.Config;
import com.accbdd.complicated_bees.registry.BlockEntitiesRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class GeneratorBlockEntity extends BlockEntity {
    public static final String ITEMS_TAG = "items";
    public static final String ENERGY_TAG = "energy";
    public static final String BURN_TIME_TAG = "burn_time";

    public static final int GENERATE = Config.CONFIG.generatorEnergy.get();
    public static final int MAXTRANSFER = 1000;
    public static final int CAPACITY = 100000;

    public static final int SLOT_COUNT = 1;
    public static final int SLOT = 0;

    private final ItemStackHandler items = createItemHandler();
    private final Lazy<IItemHandler> itemHandler = Lazy.of(() -> items);

    private final EnergyStorage energy = createEnergyStorage();
    private final Lazy<IEnergyStorage> energyHandler = Lazy.of(() -> new AdaptedEnergyStorage(energy) {
        @Override
        public int receiveEnergy(int maxReceive, boolean simulate) {
            return 0;
        }

        @Override
        public int extractEnergy(int maxExtract, boolean simulate) {
            return 0;
        }

        @Override
        public boolean canExtract() {
            return false;
        }

        @Override
        public boolean canReceive() {
            return false;
        }
    });

    private int burnTime;
    private int maxBurnTime;

    public GeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntitiesRegistration.GENERATOR_BLOCK_ENTITY.get(), pos, state);
    }

    public void tickServer() {
        generateEnergy();
        distributeEnergy();
    }

    private void generateEnergy() {
        if (energy.getEnergyStored() < energy.getMaxEnergyStored()) {
            if (burnTime <= 0) {
                ItemStack fuel = items.getStackInSlot(SLOT);
                if (fuel.isEmpty()) {
                    return;
                }
                int burnTime = fuel.getBurnTime(RecipeType.SMELTING);
                maxBurnTime = burnTime;
                setBurnTime(burnTime);
                if (burnTime <= 0) {
                    return;
                }
                items.extractItem(SLOT, 1, false);
            } else {
                setBurnTime(burnTime - 1);
                energy.receiveEnergy(GENERATE, false);
            }
            setChanged();
        }
    }

    private void setBurnTime(int bt) {
        if (bt == burnTime) {
            return;
        }
        burnTime = bt;
        if (getBlockState().getValue(BlockStateProperties.POWERED) != burnTime > 0) {
            level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(BlockStateProperties.POWERED, burnTime > 0));
        }
        setChanged();
    }

    private void distributeEnergy() {
        // Check all sides of the block and send energy if that block supports the energy capability
        for (Direction direction : Direction.values()) {
            if (energy.getEnergyStored() <= 0) {
                return;
            }
            IEnergyStorage energy = level.getCapability(Capabilities.EnergyStorage.BLOCK, getBlockPos().relative(direction), null);
            if (energy != null) {
                if (energy.canReceive()) {
                    int received = energy.receiveEnergy(Math.min(this.energy.getEnergyStored(), MAXTRANSFER), false);
                    this.energy.extractEnergy(received, false);
                    setChanged();
                }
            }
        }
    }

    public int getBurnTime() {
        return this.burnTime;
    }

    public ItemStackHandler getItems() {
        return items;
    }

    public int getStoredPower() {
        return energy.getEnergyStored();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put(ITEMS_TAG, items.serializeNBT());
        tag.put(ENERGY_TAG, energy.serializeNBT());
        tag.put(BURN_TIME_TAG, IntTag.valueOf(burnTime));
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains(ITEMS_TAG)) {
            items.deserializeNBT(tag.getCompound(ITEMS_TAG));
        }
        if (tag.contains(ENERGY_TAG)) {
            energy.deserializeNBT(tag.get(ENERGY_TAG));
        }
        if (tag.contains(BURN_TIME_TAG)) {
            burnTime = tag.getInt(BURN_TIME_TAG);
            maxBurnTime = burnTime;
        }
    }

    @Nonnull
    private ItemStackHandler createItemHandler() {
        return new ItemStackHandler(SLOT_COUNT) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }
        };
    }

    @Nonnull
    private EnergyStorage createEnergyStorage() {
        return new EnergyStorage(CAPACITY, MAXTRANSFER, MAXTRANSFER);
    }

    public IItemHandler getItemHandler() {
        return itemHandler.get();
    }

    public IEnergyStorage getEnergyHandler() {
        return energyHandler.get();
    }

    public int getMaxBurnTime() {
        return maxBurnTime;
    }
}
