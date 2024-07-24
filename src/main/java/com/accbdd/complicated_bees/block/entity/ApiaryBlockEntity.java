package com.accbdd.complicated_bees.block.entity;

import com.accbdd.complicated_bees.genetics.*;
import com.accbdd.complicated_bees.genetics.gene.*;
import com.accbdd.complicated_bees.genetics.gene.enums.EnumHumidity;
import com.accbdd.complicated_bees.genetics.gene.enums.EnumLifespan;
import com.accbdd.complicated_bees.genetics.gene.enums.EnumProductivity;
import com.accbdd.complicated_bees.genetics.gene.enums.EnumTemperature;
import com.accbdd.complicated_bees.item.*;
import com.accbdd.complicated_bees.registry.BlockEntitiesRegistration;
import com.accbdd.complicated_bees.registry.FlowerRegistry;
import com.accbdd.complicated_bees.registry.ItemsRegistration;
import com.accbdd.complicated_bees.utils.BlockPosBoxIterator;
import com.accbdd.complicated_bees.utils.enums.EnumErrorCodes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.CombinedInvWrapper;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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

    public final Stack<ItemStack> outputBuffer = new Stack<>();
    public static final String OUTPUT_BUFFER_TAG = "output_buffer";

    public static final int CYCLE_LENGTH = 20;
    public static final String CYCLE_TAG = "cycle";

    private final ContainerData data;
    private int cycleProgress = 0;
    private int breedingProgress = 0;
    private int maxBreedingProgress = 20;
    private byte errorState = 0;

    private EnumTemperature temperatureCache = null;
    private EnumHumidity humidityCache = null;
    private final List<BlockPos> flowerCache = new ArrayList<>();

    private final ItemStackHandler beeItems = createBeeHandler(BEE_SLOT_COUNT);
    private final ItemStackHandler outputItems = createItemHandler(OUTPUT_SLOT_COUNT);
    private final ItemStackHandler frameItems = createFrameHandler(FRAME_SLOT_COUNT);

    private final Lazy<IItemHandler> itemHandler = Lazy.of(() -> new CombinedInvWrapper(beeItems, outputItems, frameItems));
    private final Lazy<IItemHandler> beeItemHandler = Lazy.of(() -> new AdaptedItemHandler(beeItems) {
        @Override
        public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
            return ItemStack.EMPTY;
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
            return stack.getItem() instanceof FrameItem;
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
                    case 2 -> ApiaryBlockEntity.this.errorState;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> ApiaryBlockEntity.this.breedingProgress = value;
                    case 1 -> ApiaryBlockEntity.this.maxBreedingProgress = value;
                    case 2 -> ApiaryBlockEntity.this.errorState = (byte) value;
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

    private ItemStackHandler createFrameHandler(int slots) {
        return new ItemStackHandler(slots) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
                ApiaryBlockEntity.this.humidityCache = null;
                ApiaryBlockEntity.this.temperatureCache = null;
            }
        };
    }

    private ItemStackHandler createBeeHandler(int slots) {
        return new ItemStackHandler(slots) {
            @Override
            public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
                boolean itemValid = isItemValid(slot, stack);
                return itemValid ? super.insertItem(slot, stack, simulate) : stack;
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
        };
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put(CYCLE_TAG, IntTag.valueOf(cycleProgress));
        tag.put(ITEMS_BEES_TAG, beeItems.serializeNBT());
        tag.put(ITEMS_OUTPUT_TAG, outputItems.serializeNBT());
        tag.put(FRAME_SLOT_TAG, frameItems.serializeNBT());
        ListTag bufferTag = new ListTag();
        for (ItemStack stack : outputBuffer) {
            bufferTag.add(stack.save(new CompoundTag()));
        }
        tag.put(OUTPUT_BUFFER_TAG, bufferTag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        cycleProgress = tag.getInt(CYCLE_TAG);
        if (tag.contains(ITEMS_BEES_TAG)) {
            beeItems.deserializeNBT(tag.getCompound(ITEMS_BEES_TAG));
        }
        if (tag.contains(ITEMS_OUTPUT_TAG)) {
            outputItems.deserializeNBT(tag.getCompound(ITEMS_OUTPUT_TAG));
        }
        if (tag.contains(FRAME_SLOT_TAG)) {
            frameItems.deserializeNBT(tag.getCompound(FRAME_SLOT_TAG));
        }
        if (tag.contains(OUTPUT_BUFFER_TAG)) {
            for (Tag itemCompound : tag.getList(OUTPUT_BUFFER_TAG, Tag.TAG_COMPOUND)) {
                outputBuffer.add(ItemStack.of((CompoundTag) itemCompound));
            }
        }
    }

    public void tickServer() {
        ItemStack top_stack = beeItems.getStackInSlot(0);
        ItemStack bottom_stack = beeItems.getStackInSlot(1);

        if (!outputBuffer.empty()) {
            tryEmptyBuffer();
        }

        if (top_stack.getItem() instanceof PrincessItem && bottom_stack.getItem() instanceof DroneItem) {
            increaseBreedingProgress();
            if (hasFinished()) {
                resetBreedingProgress();
                beeItems.extractItem(1, 1, false);
                beeItems.setStackInSlot(0, createQueenFromPrincessAndDrone(top_stack, bottom_stack));
                rebuildFlowerCache(beeItems.getStackInSlot(0));
                queenSatisfied(top_stack);
            }
        } else {
            resetBreedingProgress();
        }

        if (top_stack.getItem() instanceof QueenItem && queenSatisfied(top_stack)) {
            if (cycleProgress < CYCLE_LENGTH) {
                cycleProgress++;
            } else {
                cycleProgress = 0;
                ageQueen(top_stack);
                generateProduce(top_stack);
            }
        }

        if (level.getGameTime() % 200 == 0 && top_stack.getItem() instanceof QueenItem) {
            rebuildFlowerCache(top_stack);
        }
    }

    private void tryEmptyBuffer() {
        while (!outputBuffer.empty()) {
            ItemStack next = outputBuffer.peek();
            next = ItemHandlerHelper.insertItem(outputItems, next, false);
            if (next == ItemStack.EMPTY) {
                outputBuffer.pop();
                setChanged();
                removeError(EnumErrorCodes.OUTPUT_FULL);
            } else {
                addError(EnumErrorCodes.OUTPUT_FULL);
                break;
            }
        }
    }

    private ItemStack createQueenFromPrincessAndDrone(ItemStack princess, ItemStack drone) {
        ItemStack queen = new ItemStack(ItemsRegistration.QUEEN.get());
        GeneticHelper.setGenome(queen, GeneticHelper.getGenome(princess));
        GeneticHelper.setMate(queen, GeneticHelper.getGenome(drone));

        return queen;
    }

    public void generateProduce(ItemStack bee) {
        //todo: modify with frames
        List<Product> products = ((GeneSpecies) GeneticHelper.getGene(bee, GeneSpecies.ID, true)).get().getProducts();
        for (Product product : products) {
            outputBuffer.add(product.getStackResult(((EnumProductivity) GeneticHelper.getGeneValue(bee, GeneProductivity.ID, true)).value));
        }
        //todo: generate specialty produce
        setChanged();
    }

    public boolean queenSatisfied(ItemStack queen) {
        Chromosome chromosome = GeneticHelper.getChromosome(queen, true);
        boolean satisfied = true;

        if (!((GeneTemperature) chromosome.getGene(GeneTemperature.ID)).withinTolerance(getTemperature())) {
            addError(EnumErrorCodes.WRONG_TEMP);
            satisfied = false;
        } else {
            removeError(EnumErrorCodes.WRONG_TEMP);
        }
        if (!((GeneHumidity) chromosome.getGene(GeneHumidity.ID)).withinTolerance(getHumidity())) {
            addError(EnumErrorCodes.WRONG_HUMIDITY);
            satisfied = false;
        } else {
            removeError(EnumErrorCodes.WRONG_HUMIDITY);
        }
        if (this.flowerCache.isEmpty()) {
            addError(EnumErrorCodes.NO_FLOWER);
            satisfied = false;
        } else {
            removeError(EnumErrorCodes.NO_FLOWER);
        }

        return satisfied;
    }

    public void ageQueen(ItemStack queen) {
        float ageFactor = 1;
        for (BeeHousingModifier mod : getFrameModifiers()) {
            ageFactor /= mod.getLifespanMod();
        }
        BeeItem.setAge(queen, BeeItem.getAge(queen) + ageFactor);
        damageFrames();
        if (BeeItem.getAge(queen) >= ((EnumLifespan) GeneticHelper.getGeneValue(queen, GeneLifespan.ID, true)).value) {
            beeItems.extractItem(BEE_SLOT, 1, false);
            outputBuffer.add(GeneticHelper.getFromMate(queen, ItemsRegistration.PRINCESS.get()));
            for (int i = 0; i < (int) GeneticHelper.getGeneValue(queen, GeneFertility.ID, true); i++) {
                outputBuffer.add(GeneticHelper.getFromMate(queen, ItemsRegistration.DRONE.get()));
            }
            setChanged();
        }
    }

    public List<BeeHousingModifier> getFrameModifiers() {
        List<BeeHousingModifier> modifiers = new ArrayList<>();
        for (int i = 0; i < frameItems.getSlots(); i++) {
            ItemStack item = frameItems.getStackInSlot(i);
            if (item.getItem() instanceof FrameItem frame)
                modifiers.add(frame.getModifier());
        }
        return modifiers;
    }

    public void damageFrames() {
        for (int i = 0; i < frameItems.getSlots(); i++) {
            if (frameItems.getStackInSlot(i).hurt(1, level.random, null))
                frameItems.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    public EnumHumidity getHumidity() {
        if (this.humidityCache == null) {
            if (getLevel() == null) {
                return null;
            }
            this.humidityCache = EnumHumidity.getFromPosition(getLevel(), getBlockPos());

            for (BeeHousingModifier mod : getFrameModifiers()) {
                int ordinal = humidityCache.ordinal() + mod.getHumidityMod().up - mod.getHumidityMod().down;
                humidityCache = EnumHumidity.values()[Math.max(0, Math.min(EnumHumidity.values().length - 1, ordinal))];
            }
        }

        return this.humidityCache;
    }

    public EnumTemperature getTemperature() {
        if (this.temperatureCache == null) {
            if (getLevel() == null) {
                return null;
            }
            this.temperatureCache = EnumTemperature.getFromPosition(getLevel(), getBlockPos());

            for (BeeHousingModifier mod : getFrameModifiers()) {
                int ordinal = temperatureCache.ordinal() + mod.getTemperatureMod().up - mod.getTemperatureMod().down;
                temperatureCache = EnumTemperature.values()[Math.max(0, Math.min(EnumTemperature.values().length - 1, ordinal))];
            }
        }

        return this.temperatureCache;
    }

    private void rebuildFlowerCache(ItemStack bee) {
        clearFlowerCache();
        Flower flower = ServerLifecycleHooks.getCurrentServer().registryAccess().registry(FlowerRegistry.FLOWER_REGISTRY_KEY).get()
                .get(((GeneFlower)GeneticHelper.getGene(bee, GeneFlower.ID, true)).get());

        if (flower == null) {
            //no valid flower gene
            flowerCache.add(getBlockPos());
            return;
        }

        for (BlockPosBoxIterator it = new BlockPosBoxIterator(this.getBlockPos(), 3, 3); it.hasNext(); ) {
            BlockPos pos = it.next();
            if (flower.isAcceptable(level.getBlockState(pos))) {
                flowerCache.add(pos);
            }
        }
    }

    private void clearFlowerCache() {
        flowerCache.clear();
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

    private void addError(EnumErrorCodes... error) {
        for (EnumErrorCodes err : error) {
            errorState |= err.value;
        }
    }

    private void removeError(EnumErrorCodes... error) {
        for (EnumErrorCodes err : error) {
            errorState = (byte) (errorState & (err.value ^ Byte.MAX_VALUE));
        }
    }
}
