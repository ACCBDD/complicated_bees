package com.accbdd.complicated_bees.block.entity;

import com.accbdd.complicated_bees.genetics.BeeProducts;
import com.accbdd.complicated_bees.genetics.Chromosome;
import com.accbdd.complicated_bees.genetics.GeneticHelper;
import com.accbdd.complicated_bees.genetics.gene.*;
import com.accbdd.complicated_bees.genetics.gene.enums.EnumHumidity;
import com.accbdd.complicated_bees.genetics.gene.enums.EnumTemperature;
import com.accbdd.complicated_bees.item.BeeItem;
import com.accbdd.complicated_bees.item.DroneItem;
import com.accbdd.complicated_bees.item.PrincessItem;
import com.accbdd.complicated_bees.item.QueenItem;
import com.accbdd.complicated_bees.registry.BlockEntitiesRegistration;
import com.accbdd.complicated_bees.registry.ItemsRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.CombinedInvWrapper;
import org.jetbrains.annotations.NotNull;

public class ApiaryBlockEntity extends BlockEntity {
    public static final int BEE_SLOT = 0;
    public static final int BEE_SLOT_COUNT = 2;
    public static final String ITEMS_BEES_TAG = "bee_items";

    public static final int OUTPUT_SLOT = 0;
    public static final int OUTPUT_SLOT_COUNT = 7;
    public static final String ITEMS_OUTPUT_TAG = "output_items";

    public static final int FRAME_SLOT = 0;
    public static final int FRAME_SLOT_COUNT = 3;
    public static final String FRAME_SLOT_TAG = "frame_slots";

    public static final int SLOT_COUNT = BEE_SLOT_COUNT + OUTPUT_SLOT_COUNT + FRAME_SLOT_COUNT;

    private final ContainerData data;
    private int breedingProgress = 0;
    private int maxBreedingProgress = 20;
    private int barState = 1;

    private EnumTemperature temperature = null;
    private EnumHumidity humidity = null;

    private final ItemStackHandler beeItems = createItemHandler(BEE_SLOT_COUNT);
    private final ItemStackHandler outputItems = createItemHandler(OUTPUT_SLOT_COUNT);
    private final ItemStackHandler frameItems = createItemHandler(FRAME_SLOT_COUNT);

    private final Lazy<IItemHandler> itemHandler = Lazy.of(() -> new CombinedInvWrapper(beeItems, outputItems, frameItems));
    private final Lazy<IItemHandler> beeItemHandler = Lazy.of(() -> new AdaptedItemHandler(beeItems) {
        @Override
        public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
            return ItemStack.EMPTY;
        }

        @Override
        public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            return isItemValid(slot, stack) ? super.insertItem(slot, stack, simulate) : stack;
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            if (stack.getItem() instanceof BeeItem) {
                switch (slot) {
                    case 0:
                        return (stack.getItem() instanceof QueenItem || stack.getItem() instanceof PrincessItem);
                    case 1:
                        return (stack.getItem() instanceof DroneItem);
                }
            }
            return false;
        }
    });
    private final Lazy<IItemHandler> outputItemHandler = Lazy.of(() -> new AdaptedItemHandler(outputItems) {
        @Override
        public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            return stack;
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return false;
        }
    });
    private final Lazy<IItemHandler> frameItemHandler = Lazy.of(() -> new AdaptedItemHandler(frameItems) {
        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return super.isItemValid(slot, stack);
            //TODO: implement only frames allowed
        }
    });

    public ApiaryBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntitiesRegistration.APIARY_ENTITY.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> ApiaryBlockEntity.this.breedingProgress;
                    case 1 -> ApiaryBlockEntity.this.maxBreedingProgress;
                    case 2 -> ApiaryBlockEntity.this.barState;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> ApiaryBlockEntity.this.breedingProgress = value;
                    case 1 -> ApiaryBlockEntity.this.maxBreedingProgress = value;
                    case 2 -> ApiaryBlockEntity.this.barState = value;
                }
            }

            @Override
            public int getCount() {
                return 3;
            }
        };
    }

    public ItemStackHandler getBeeItems() {
        return beeItems;
    }

    public ItemStackHandler getOutputItems() {
        return outputItems;
    }

    public ItemStackHandler getFrameItems() {
        return frameItems;
    }

    public Lazy<IItemHandler> getItemHandler() {
        return itemHandler;
    }

    public Lazy<IItemHandler> getBeeItemHandler() {
        return beeItemHandler;
    }

    public Lazy<IItemHandler> getOutputItemHandler() {
        return outputItemHandler;
    }

    public Lazy<IItemHandler> getFrameItemHandler() {
        return frameItemHandler;
    }

    public ContainerData getData() {
        return this.data;
    }

    private ItemStackHandler createItemHandler(int slots) {
        return new ItemStackHandler(slots) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }
        };
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put(ITEMS_BEES_TAG, beeItems.serializeNBT());
        tag.put(ITEMS_OUTPUT_TAG, outputItems.serializeNBT());
        tag.put(FRAME_SLOT_TAG, frameItems.serializeNBT());
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains(ITEMS_BEES_TAG)) {
            beeItems.deserializeNBT(tag.getCompound(ITEMS_BEES_TAG));
        }
        if (tag.contains(ITEMS_OUTPUT_TAG)) {
            outputItems.deserializeNBT(tag.getCompound(ITEMS_OUTPUT_TAG));
        }
        if (tag.contains(FRAME_SLOT_TAG)) {
            frameItems.deserializeNBT(tag.getCompound(FRAME_SLOT_TAG));
        }
    }

    public void tickServer() {
        ItemStack top_stack = beeItems.getStackInSlot(0);
        ItemStack bottom_stack = beeItems.getStackInSlot(1);
        if (level.getGameTime() % 20 == 0) {
            if (top_stack.getItem() instanceof QueenItem) {
                if (queenSatisfied(top_stack)) {
                    setBarState(1);
                    ageQueen(top_stack);
                    generateProduce(top_stack);
                } else {
                    setBarState(2);
                }
            }
        }
        if (top_stack.getItem() instanceof PrincessItem && bottom_stack.getItem() instanceof DroneItem) {
            setBarState(1);
            increaseBreedingProgress();
            if (hasFinished()) {
                resetBreedingProgress();
                beeItems.extractItem(1, 1, false);
                beeItems.setStackInSlot(0, createQueenFromPrincessAndDrone(top_stack, bottom_stack));
            }
        } else {
            resetBreedingProgress();
        }
    }

    private ItemStack createQueenFromPrincessAndDrone(ItemStack princess, ItemStack drone) {
        ItemStack queen = new ItemStack(ItemsRegistration.QUEEN.get());
        GeneticHelper.setGenome(queen, GeneticHelper.getGenome(princess));
        GeneticHelper.setMate(queen, GeneticHelper.getGenome(drone));

        return queen;
    }

    public void generateProduce(ItemStack stack) {
        BeeProducts products = ((GeneSpecies) GeneticHelper.getGene(stack, GeneSpecies.ID, true)).get().getProducts();
        ItemStack primary = products.getPrimaryResult();
        ItemStack secondary = products.getSecondaryResult();
        ItemStack specialty = products.getSpecialtyResult();

        ItemHandlerHelper.insertItem(outputItems, primary, false);
        ItemHandlerHelper.insertItem(outputItems, secondary, false);
        ItemHandlerHelper.insertItem(outputItems, specialty, false);
        setChanged();
    }

    public boolean queenSatisfied(ItemStack queen) {

        Chromosome chromosome = GeneticHelper.getChromosome(queen, true);
        return (((GeneTemperature) chromosome.getGene(GeneTemperature.ID)).withinTolerance(getTemperature())
                && ((GeneHumidity) chromosome.getGene(GeneHumidity.ID)).withinTolerance(getHumidity()));
    }

    public void ageQueen(ItemStack queen) {
        BeeItem.setAge(queen, BeeItem.getAge(queen) + 1);
        if (BeeItem.getAge(queen) >= (int) GeneticHelper.getGeneValue(queen, GeneLifespan.ID, true)) {
            beeItems.extractItem(BEE_SLOT, 1, false);
            ItemHandlerHelper.insertItem(outputItems, GeneticHelper.getFromMate(queen, ItemsRegistration.PRINCESS.get()), false);
            ItemHandlerHelper.insertItem(outputItems, GeneticHelper.getFromMate(queen, ItemsRegistration.DRONE.get()), false);
            ItemHandlerHelper.insertItem(outputItems, GeneticHelper.getFromMate(queen, ItemsRegistration.DRONE.get()), false);
            setChanged();
        }
    }

    public EnumHumidity getHumidity() {
        if (this.humidity == null) {
            if (getLevel() == null) {
                return null;
            }
            this.humidity = EnumHumidity.getFromPosition(getLevel(), getBlockPos());
        }
        return this.humidity;
    }

    public EnumTemperature getTemperature() {
        if (this.temperature == null) {
            if (getLevel() == null) {
                return null;
            }
            this.temperature = EnumTemperature.getFromPosition(getLevel(), getBlockPos());
        }
        return this.temperature;
    }

    private void increaseBreedingProgress() {
        breedingProgress++;
        setChanged();
    }

    private boolean hasFinished() {
        return breedingProgress >= maxBreedingProgress;
    }

    private void resetBreedingProgress() {
        breedingProgress = 0;
    }

    private void setBarState(int value) {
        barState = value;
    }
}
