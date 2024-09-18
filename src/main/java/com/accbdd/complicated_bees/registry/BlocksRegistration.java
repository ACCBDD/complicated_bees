package com.accbdd.complicated_bees.registry;

import com.accbdd.complicated_bees.block.ApiaryBlock;
import com.accbdd.complicated_bees.block.BeeNestBlock;
import com.accbdd.complicated_bees.block.CentrifugeBlock;
import com.accbdd.complicated_bees.block.GeneratorBlock;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class BlocksRegistration {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    public static final BlockBehaviour.Properties WAX_PROPERTIES = BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_YELLOW)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .requiresCorrectToolForDrops()
            .strength(1.5F, 6.0F);
    public static final BlockBehaviour.Properties HONEYPLANK_PROPERTIES = BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_YELLOW)
            .instrument(NoteBlockInstrument.BASS)
            .sound(SoundType.WOOD)
            .strength(2, 3);

    public static final DeferredBlock<BeeNestBlock> BEE_NEST = BLOCKS.register("bee_nest", BeeNestBlock::new);
    public static final DeferredBlock<ApiaryBlock> APIARY = BLOCKS.register("apiary", ApiaryBlock::new);
    public static final DeferredBlock<CentrifugeBlock> CENTRIFUGE = BLOCKS.register("centrifuge", CentrifugeBlock::new);
    public static final DeferredBlock<GeneratorBlock> GENERATOR = BLOCKS.register("generator", GeneratorBlock::new);

    public static final DeferredBlock<Block> WAX_BLOCK = BLOCKS.register("wax_block", () -> new Block(WAX_PROPERTIES));
    public static final DeferredBlock<StairBlock> WAX_BLOCK_STAIRS = BLOCKS.register("wax_block_stairs", () -> stair(WAX_BLOCK.get()));
    public static final DeferredBlock<SlabBlock> WAX_BLOCK_SLAB = BLOCKS.register("wax_block_slab", () -> slab(WAX_BLOCK.get()));
    public static final DeferredBlock<WallBlock> WAX_BLOCK_WALL = BLOCKS.register("wax_block_wall", () -> wall(WAX_BLOCK.get()));
    public static final DeferredBlock<Block> SMOOTH_WAX = BLOCKS.register("smooth_wax", () -> new Block(WAX_PROPERTIES));
    public static final DeferredBlock<StairBlock> SMOOTH_WAX_STAIRS = BLOCKS.register("smooth_wax_stairs", () -> stair(SMOOTH_WAX.get()));
    public static final DeferredBlock<SlabBlock> SMOOTH_WAX_SLAB = BLOCKS.register("smooth_wax_slab", () -> slab(SMOOTH_WAX.get()));
    public static final DeferredBlock<WallBlock> SMOOTH_WAX_WALL = BLOCKS.register("smooth_wax_wall", () -> wall(SMOOTH_WAX.get()));
    public static final DeferredBlock<Block> WAX_BRICKS = BLOCKS.register("wax_bricks", () -> new Block(WAX_PROPERTIES));
    public static final DeferredBlock<StairBlock> WAX_BRICK_STAIRS = BLOCKS.register("wax_brick_stairs", () -> stair(WAX_BRICKS.get()));
    public static final DeferredBlock<SlabBlock> WAX_BRICK_SLAB = BLOCKS.register("wax_brick_slab", () -> slab(WAX_BRICKS.get()));
    public static final DeferredBlock<WallBlock> WAX_BRICK_WALL = BLOCKS.register("wax_brick_wall", () -> wall(WAX_BRICKS.get()));
    public static final DeferredBlock<Block> CHISELED_WAX = BLOCKS.register("chiseled_wax", () -> new Block(WAX_PROPERTIES));

    public static final DeferredBlock<Block> HONEYED_PLANKS = BLOCKS.register("honeyed_planks", () -> new Block(HONEYPLANK_PROPERTIES));
    public static final DeferredBlock<StairBlock> HONEYED_STAIRS = BLOCKS.register("honeyed_stairs", () -> stair(HONEYED_PLANKS.get()));
    public static final DeferredBlock<SlabBlock> HONEYED_SLAB = BLOCKS.register("honeyed_slab", () -> slab(HONEYED_PLANKS.get()));
    public static final DeferredBlock<FenceBlock> HONEYED_FENCE = BLOCKS.register("honeyed_fence", () -> fence(HONEYED_PLANKS.get()));
    public static final DeferredBlock<FenceGateBlock> HONEYED_FENCE_GATE = BLOCKS.register("honeyed_fence_gate", () -> gate(HONEYED_PLANKS.get()));
    public static final DeferredBlock<ButtonBlock> HONEYED_BUTTON = BLOCKS.register("honeyed_button", () -> button(BlockSetType.OAK, 30, HONEYED_PLANKS.get()));
    public static final DeferredBlock<PressurePlateBlock> HONEYED_PRESSURE_PLATE = BLOCKS.register("honeyed_pressure_plate", () -> plate(BlockSetType.OAK, HONEYED_PLANKS.get()));
    public static final DeferredBlock<DoorBlock> HONEYED_DOOR = BLOCKS.register("honeyed_door", () -> door(BlockSetType.OAK, HONEYED_PLANKS.get()));
    public static final DeferredBlock<TrapDoorBlock> HONEYED_TRAPDOOR = BLOCKS.register("honeyed_trapdoor", () -> trapdoor(BlockSetType.OAK, HONEYED_PLANKS.get()));

    private static StairBlock stair(Block base) {
        return new StairBlock(base::defaultBlockState, base.properties());
    }

    private static SlabBlock slab(Block base) {
        return new SlabBlock(base.properties());
    }

    private static WallBlock wall(Block base) {
        return new WallBlock(base.properties());
    }

    private static FenceBlock fence(Block base) {
        return new FenceBlock(base.properties());
    }

    private static FenceGateBlock gate(Block base) {
        return new FenceGateBlock(WoodType.OAK, base.properties());
    }

    private static ButtonBlock button(BlockSetType type, int ticksToPress, Block base) {
        return new ButtonBlock(type, ticksToPress, BlockBehaviour.Properties.ofFullCopy(base).noCollission());
    }

    private static PressurePlateBlock plate(BlockSetType type, Block base) {
        return new PressurePlateBlock(type, base.properties());
    }

    private static DoorBlock door(BlockSetType type, Block base) {
        return new DoorBlock(type, BlockBehaviour.Properties.ofFullCopy(base).noOcclusion());
    }

    private static TrapDoorBlock trapdoor(BlockSetType type, Block base) {
        return new TrapDoorBlock(type, BlockBehaviour.Properties.ofFullCopy(base).noOcclusion());
    }
}
