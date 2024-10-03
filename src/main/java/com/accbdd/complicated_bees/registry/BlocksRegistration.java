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
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class BlocksRegistration {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
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

    public static final RegistryObject<BeeNestBlock> BEE_NEST = BLOCKS.register("bee_nest", BeeNestBlock::new);
    public static final RegistryObject<ApiaryBlock> APIARY = BLOCKS.register("apiary", ApiaryBlock::new);
    public static final RegistryObject<CentrifugeBlock> CENTRIFUGE = BLOCKS.register("centrifuge", CentrifugeBlock::new);
    public static final RegistryObject<GeneratorBlock> GENERATOR = BLOCKS.register("generator", GeneratorBlock::new);

    public static final RegistryObject<Block> WAX_BLOCK = BLOCKS.register("wax_block", () -> new Block(WAX_PROPERTIES));
    public static final RegistryObject<StairBlock> WAX_BLOCK_STAIRS = BLOCKS.register("wax_block_stairs", () -> stair(WAX_BLOCK.get()));
    public static final RegistryObject<SlabBlock> WAX_BLOCK_SLAB = BLOCKS.register("wax_block_slab", () -> slab(WAX_BLOCK.get()));
    public static final RegistryObject<WallBlock> WAX_BLOCK_WALL = BLOCKS.register("wax_block_wall", () -> wall(WAX_BLOCK.get()));
    public static final RegistryObject<Block> SMOOTH_WAX = BLOCKS.register("smooth_wax", () -> new Block(WAX_PROPERTIES));
    public static final RegistryObject<StairBlock> SMOOTH_WAX_STAIRS = BLOCKS.register("smooth_wax_stairs", () -> stair(SMOOTH_WAX.get()));
    public static final RegistryObject<SlabBlock> SMOOTH_WAX_SLAB = BLOCKS.register("smooth_wax_slab", () -> slab(SMOOTH_WAX.get()));
    public static final RegistryObject<WallBlock> SMOOTH_WAX_WALL = BLOCKS.register("smooth_wax_wall", () -> wall(SMOOTH_WAX.get()));
    public static final RegistryObject<Block> WAX_BRICKS = BLOCKS.register("wax_bricks", () -> new Block(WAX_PROPERTIES));
    public static final RegistryObject<StairBlock> WAX_BRICK_STAIRS = BLOCKS.register("wax_brick_stairs", () -> stair(WAX_BRICKS.get()));
    public static final RegistryObject<SlabBlock> WAX_BRICK_SLAB = BLOCKS.register("wax_brick_slab", () -> slab(WAX_BRICKS.get()));
    public static final RegistryObject<WallBlock> WAX_BRICK_WALL = BLOCKS.register("wax_brick_wall", () -> wall(WAX_BRICKS.get()));
    public static final RegistryObject<Block> CHISELED_WAX = BLOCKS.register("chiseled_wax", () -> new Block(WAX_PROPERTIES));

    public static final RegistryObject<Block> HONEYED_PLANKS = BLOCKS.register("honeyed_planks", () -> new Block(HONEYPLANK_PROPERTIES));
    public static final RegistryObject<StairBlock> HONEYED_STAIRS = BLOCKS.register("honeyed_stairs", () -> stair(HONEYED_PLANKS.get()));
    public static final RegistryObject<SlabBlock> HONEYED_SLAB = BLOCKS.register("honeyed_slab", () -> slab(HONEYED_PLANKS.get()));
    public static final RegistryObject<FenceBlock> HONEYED_FENCE = BLOCKS.register("honeyed_fence", () -> fence(HONEYED_PLANKS.get()));
    public static final RegistryObject<FenceGateBlock> HONEYED_FENCE_GATE = BLOCKS.register("honeyed_fence_gate", () -> gate(HONEYED_PLANKS.get()));
    public static final RegistryObject<ButtonBlock> HONEYED_BUTTON = BLOCKS.register("honeyed_button", () -> button(BlockSetType.OAK, 30, HONEYED_PLANKS.get()));
    public static final RegistryObject<PressurePlateBlock> HONEYED_PRESSURE_PLATE = BLOCKS.register("honeyed_pressure_plate", () -> plate(BlockSetType.OAK, HONEYED_PLANKS.get()));
    public static final RegistryObject<DoorBlock> HONEYED_DOOR = BLOCKS.register("honeyed_door", () -> door(BlockSetType.OAK, HONEYED_PLANKS.get()));
    public static final RegistryObject<TrapDoorBlock> HONEYED_TRAPDOOR = BLOCKS.register("honeyed_trapdoor", () -> trapdoor(BlockSetType.OAK, HONEYED_PLANKS.get()));

    private static StairBlock stair(Block base) {
        return new StairBlock(base::defaultBlockState, BlockBehaviour.Properties.copy(base));
    }

    private static SlabBlock slab(Block base) {
        return new SlabBlock(BlockBehaviour.Properties.copy(base));
    }

    private static WallBlock wall(Block base) {
        return new WallBlock(BlockBehaviour.Properties.copy(base));
    }

    private static FenceBlock fence(Block base) {
        return new FenceBlock(BlockBehaviour.Properties.copy(base));
    }

    private static FenceGateBlock gate(Block base) {
        return new FenceGateBlock(BlockBehaviour.Properties.copy(base), WoodType.OAK);
    }

    private static ButtonBlock button(BlockSetType type, int ticksToPress, Block base) {
        return new ButtonBlock(BlockBehaviour.Properties.copy(base).noCollission(), type, ticksToPress, true);
    }

    private static PressurePlateBlock plate(BlockSetType type, Block base) {
        return new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(base), type);
    }

    private static DoorBlock door(BlockSetType type, Block base) {
        return new DoorBlock(BlockBehaviour.Properties.copy(base).noOcclusion(), type);
    }

    private static TrapDoorBlock trapdoor(BlockSetType type, Block base) {
        return new TrapDoorBlock(BlockBehaviour.Properties.copy(base).noOcclusion(), type);
    }
}
