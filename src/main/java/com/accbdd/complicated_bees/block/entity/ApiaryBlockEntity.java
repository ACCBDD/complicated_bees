package com.accbdd.complicated_bees.block.entity;

import com.accbdd.complicated_bees.config.Config;
import com.accbdd.complicated_bees.genetics.*;
import com.accbdd.complicated_bees.genetics.effect.IBeeEffect;
import com.accbdd.complicated_bees.genetics.gene.*;
import com.accbdd.complicated_bees.genetics.gene.enums.EnumHumidity;
import com.accbdd.complicated_bees.genetics.gene.enums.EnumLifespan;
import com.accbdd.complicated_bees.genetics.gene.enums.EnumProductivity;
import com.accbdd.complicated_bees.genetics.gene.enums.EnumTemperature;
import com.accbdd.complicated_bees.item.*;
import com.accbdd.complicated_bees.registry.BlockEntitiesRegistration;
import com.accbdd.complicated_bees.registry.FlowerRegistration;
import com.accbdd.complicated_bees.registry.ItemsRegistration;
import com.accbdd.complicated_bees.util.BlockPosBoxIterator;
import com.accbdd.complicated_bees.util.enums.EnumErrorCodes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.CombinedInvWrapper;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

@ParametersAreNonnullByDefault
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

    public static final int CYCLE_LENGTH = Config.CONFIG.productionCycleLength.get();
    public static final String CYCLE_TAG = "cycle";
    public static final int SATISFY_CYCLE_LENGTH = Config.CONFIG.enviroCycleLength.get();

    private final ContainerData data;
    private int cycleProgress = 0;
    private int satisfyCycleProgress = 0;
    private int breedingProgress = 0;
    private int maxBreedingProgress = 20;
    private int errorState = 0;
    private boolean queenSatisfied = false;

    private EnumTemperature temperatureCache = null;
    private EnumHumidity humidityCache = null;
    private final List<BlockPos> flowerCache = new ArrayList<>();

    private final ItemStackHandler beeItems = createBeeHandler();
    private final ItemStackHandler outputItems = createOutputHandler();
    private final ItemStackHandler frameItems = createFrameHandler();

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
                    case 2 -> ApiaryBlockEntity.this.errorState = value;
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

    public int getCycleProgress() {
        return CYCLE_LENGTH - this.cycleProgress;
    }

    private ItemStackHandler createOutputHandler() {
        return new ItemStackHandler(ApiaryBlockEntity.OUTPUT_SLOT_COUNT) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }
        };
    }

    private ItemStackHandler createFrameHandler() {
        return new ItemStackHandler(ApiaryBlockEntity.FRAME_SLOT_COUNT) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
                ApiaryBlockEntity.this.humidityCache = null;
                ApiaryBlockEntity.this.temperatureCache = null;
            }
        };
    }

    private ItemStackHandler createBeeHandler() {
        return new ItemStackHandler(ApiaryBlockEntity.BEE_SLOT_COUNT) {
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

            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                if (slot == 0) {
                    ApiaryBlockEntity.this.clearFlowerCache();
                    ApiaryBlockEntity.this.checkQueenSatisfied();
                }
                setChanged();
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
        queenSatisfied = checkQueenSatisfied();
    }

    public void tickServer() {
        ItemStack top_stack = beeItems.getStackInSlot(0);
        ItemStack bottom_stack = beeItems.getStackInSlot(1);

        //empty buffer
        if (!outputBuffer.empty()) {
            tryEmptyBuffer();
        }

        //breed
        if (top_stack.getItem() instanceof PrincessItem && bottom_stack.getItem() instanceof DroneItem) {
            increaseBreedingProgress();
            if (hasFinished()) {
                resetBreedingProgress();
                beeItems.extractItem(1, 1, false);
                beeItems.setStackInSlot(0, createQueenFromPrincessAndDrone(top_stack, bottom_stack));
                rebuildFlowerCache(beeItems.getStackInSlot(0));
                checkQueenSatisfied();
            }
        } else {
            resetBreedingProgress();
        }

        //check if queen is satisfied
        if (satisfyCycleProgress >= SATISFY_CYCLE_LENGTH) {
            if (top_stack.getItem() instanceof QueenItem) {
                rebuildFlowerCache(top_stack);
                queenSatisfied = checkQueenSatisfied();
                satisfyCycleProgress = 0;
            }
        } else {
            satisfyCycleProgress++;
        }

        //do queen cycle
        if (top_stack.getItem() instanceof QueenItem) {
            if (queenSatisfied) {
                doBeeEffect(top_stack);
                if (cycleProgress < CYCLE_LENGTH) {
                    cycleProgress++;
                } else {
                    cycleProgress = 0;
                    ageQueen(top_stack);
                    generateProduce(top_stack);
                }
            }
        } else {
            cycleProgress = 0;
        }
    }

    private void doBeeEffect(ItemStack topStack) {
        if (topStack.getItem() instanceof QueenItem) {
            IBeeEffect effect = (IBeeEffect) GeneticHelper.getGeneValue(topStack, GeneEffect.ID, true);
            if (effect != null)
                effect.runEffect(this, topStack, cycleProgress);
        }
    }

    private void tryEmptyBuffer() {
        while (!outputBuffer.empty()) {
            ItemStack next = outputBuffer.pop();
            next = ItemHandlerHelper.insertItem(outputItems, next, false);
            if (next == ItemStack.EMPTY) {
                setChanged();
                removeError(EnumErrorCodes.OUTPUT_FULL);
            } else {
                outputBuffer.push(next);
                addError(EnumErrorCodes.OUTPUT_FULL);
                break;
            }
        }
    }

    private ItemStack createQueenFromPrincessAndDrone(ItemStack princess, ItemStack drone) {
        ItemStack queen = new ItemStack(ItemsRegistration.QUEEN.get());
        GeneticHelper.setGenome(queen, GeneticHelper.getGenome(princess));
        GeneticHelper.setMate(queen, GeneticHelper.getGenome(drone));
        QueenItem.setGeneration(queen, PrincessItem.getGeneration(princess));

        return queen;
    }

    //hook for effects to add to output
    public void addToOutput(ItemStack stack) {
        outputBuffer.add(stack);
    }

    public void generateProduce(ItemStack bee) {
        Species species = (Species) GeneticHelper.getGeneValue(bee, GeneSpecies.ID, true);
        float frameModifiers = getFrameModifiers().stream().map(BeeHousingModifier::getProductivityMod).reduce(1f, (cur, next) -> cur * next);
        for (Product product : species.getProducts()) {
            outputBuffer.add(product.getStackResult(((EnumProductivity) GeneticHelper.getGeneValue(bee, GeneProductivity.ID, true)).value, frameModifiers));
        }
        if (errorState == EnumErrorCodes.ECSTATIC.value) {
            for (Product special : species.getSpecialtyProducts()) {
                outputBuffer.add(special.getStackResult(((EnumProductivity) GeneticHelper.getGeneValue(bee, GeneProductivity.ID, true)).value, frameModifiers));
            }
        }
        setChanged();
    }

    public boolean checkQueenSatisfied() {
        if (!(beeItems.getStackInSlot(0).getItem() instanceof QueenItem))
            return false;

        if (getLevel() == null) return false;

        Chromosome chromosome = GeneticHelper.getChromosome(beeItems.getStackInSlot(0), true);
        queenSatisfied = true;

        if (!((GeneTemperature) chromosome.getGene(GeneTemperature.ID)).withinTolerance(getTemperature())) {
            addError(EnumErrorCodes.WRONG_TEMP);
            queenSatisfied = false;
        } else {
            removeError(EnumErrorCodes.WRONG_TEMP);
        }
        if (!((GeneHumidity) chromosome.getGene(GeneHumidity.ID)).withinTolerance(getHumidity())) {
            addError(EnumErrorCodes.WRONG_HUMIDITY);
            queenSatisfied = false;
        } else {
            removeError(EnumErrorCodes.WRONG_HUMIDITY);
        }
        if (this.flowerCache.isEmpty()) {
            addError(EnumErrorCodes.NO_FLOWER);
            queenSatisfied = false;
        } else {
            removeError(EnumErrorCodes.NO_FLOWER);
        }
        if (level.isRaining() && !(boolean) chromosome.getGene(new ResourceLocation(MODID, "weatherproof")).get()) {
            addError(EnumErrorCodes.WEATHER);
            queenSatisfied = false;
        } else {
            removeError(EnumErrorCodes.WEATHER);
        }
        if (level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, getBlockPos()).getY() > getBlockPos().getY() + 1
                && !(boolean) chromosome.getGene(new ResourceLocation(MODID, "cave_dwelling")).get()) {
            addError(EnumErrorCodes.UNDERGROUND);
            queenSatisfied = false;
        } else {
            removeError(EnumErrorCodes.UNDERGROUND);
        }
        if (level.isNight() && !(boolean) chromosome.getGene(new ResourceLocation(MODID, "nocturnal")).get()) {
            addError(EnumErrorCodes.WRONG_TIME);
            queenSatisfied = false;
        } else {
            removeError(EnumErrorCodes.WRONG_TIME);
        }

        queenEcstatic();

        return queenSatisfied;
    }

    public void queenEcstatic() {
        Chromosome chromosome = GeneticHelper.getChromosome(beeItems.getStackInSlot(0), true);
        if (((GeneTemperature) chromosome.getGene(GeneTemperature.ID)).get().equals(getTemperature())
                && ((GeneHumidity) chromosome.getGene(GeneHumidity.ID)).get().equals(getHumidity())
                && queenSatisfied) {
            addError(EnumErrorCodes.ECSTATIC);
        } else {
            removeError(EnumErrorCodes.ECSTATIC);
        }
    }

    public void ageQueen(ItemStack queen) {
        float ageFactor = 1;
        for (BeeHousingModifier mod : getFrameModifiers()) {
            ageFactor /= mod.getLifespanMod();
        }
        BeeItem.setAge(queen, BeeItem.getAge(queen) + ageFactor);
        damageFrames();
        if (BeeItem.getAge(queen) >= ((EnumLifespan) GeneticHelper.getGeneValue(queen, GeneLifespan.ID, true)).value) {
            errorState = 0;
            outputBuffer.add(GeneticHelper.getOffspring(queen, ItemsRegistration.PRINCESS.get(), getLevel(), getBlockPos()));
            for (int i = 0; i < (int) GeneticHelper.getGeneValue(queen, GeneFertility.ID, true); i++) {
                outputBuffer.add(GeneticHelper.getOffspring(queen, ItemsRegistration.DRONE.get(), getLevel(), getBlockPos()));
            }
            beeItems.extractItem(BEE_SLOT, 1, false);
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
        Flower flower = ServerLifecycleHooks.getCurrentServer().registryAccess().registry(FlowerRegistration.FLOWER_REGISTRY_KEY).get()
                .get(((GeneFlower) GeneticHelper.getGene(bee, GeneFlower.ID, true)).get());

        if (flower == null) {
            //no valid flower gene
            flowerCache.add(getBlockPos());
            return;
        }

        BlockPosBoxIterator it = new BlockPosBoxIterator(this.getBlockPos(), 3, 3);
        while (it.hasNext() && this.beeItems.getStackInSlot(0).is(ItemsRegistration.QUEEN)) {
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
            errorState = (errorState & (err.value ^ Integer.MAX_VALUE));
        }
    }
}
