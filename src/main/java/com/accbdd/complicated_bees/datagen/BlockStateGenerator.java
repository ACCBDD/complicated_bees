package com.accbdd.complicated_bees.datagen;

import com.accbdd.complicated_bees.registry.BlocksRegistration;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.VariantBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.function.BiConsumer;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class BlockStateGenerator extends BlockStateProvider {

    public BlockStateGenerator(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(BlocksRegistration.BEE_NEST.get(), createBeeNestModel());
        simpleBlock(BlocksRegistration.APIARY.get(), createApiaryModel());
        simpleBlock(BlocksRegistration.WAX_BLOCK.get());
        stairsBlock(BlocksRegistration.WAX_BLOCK_STAIRS.get(), modLoc("block/wax_block"));
        slabBlock(BlocksRegistration.WAX_BLOCK_SLAB.get(), modLoc("block/wax_block"), modLoc("block/wax_block"));
        simpleBlock(BlocksRegistration.WAX_BRICKS.get());
        stairsBlock(BlocksRegistration.WAX_BRICK_STAIRS.get(), modLoc("block/wax_bricks"));
        slabBlock(BlocksRegistration.WAX_BRICK_SLAB.get(), modLoc("block/wax_bricks"), modLoc("block/wax_bricks"));
        simpleBlock(BlocksRegistration.SMOOTH_WAX.get());
        stairsBlock(BlocksRegistration.SMOOTH_WAX_STAIRS.get(), modLoc("block/smooth_wax"));
        slabBlock(BlocksRegistration.SMOOTH_WAX_SLAB.get(), modLoc("block/smooth_wax_slab_side"), modLoc("block/smooth_wax"));
        simpleBlock(BlocksRegistration.CHISELED_WAX.get());
        registerCentrifuge();
        registerGenerator();
    }

    public void slabBlock(SlabBlock block, ResourceLocation side, ResourceLocation end) {
        slabBlock(
                block,
                models().slab(name(block), side, end, end),
                models().slab(name(block) + "_top", side, end, end),
                models().cubeColumn(name(block) + "_double", side, end)
        );
    }

    public BlockModelBuilder createBeeNestModel() {
        String path = "bee_nest";
        ResourceLocation top_texture = modLoc("block/bee_nest_top");
        ResourceLocation side_texture = modLoc("block/bee_nest_side");
        ResourceLocation bottom_texture = modLoc("block/bee_nest_bottom");
        return models().cubeBottomTop(path,
                        side_texture,
                        bottom_texture,
                        top_texture)
                .element().allFaces((dir, face) -> {
                    face.tintindex(1);
                    switch (dir) {
                        case UP -> face.texture("#top");
                        case DOWN -> face.texture("#bottom");
                        default -> face.texture("#side");
                    }
                }).end();
    }

    public BlockModelBuilder createApiaryModel() {
        String path = "apiary";
        ResourceLocation side = modLoc("block/apiary_side");
        ResourceLocation bottom = modLoc("block/apiary_bottom");
        ResourceLocation top = modLoc("block/apiary_top");

        return models().cube(path, bottom, top, side, side, side, side).texture("particle", side);
    }

    public void registerCentrifuge() {
        String path = "centrifuge";
        ResourceLocation side = modLoc("block/centrifuge_side");
        ResourceLocation end = modLoc("block/centrifuge_end");
        ResourceLocation front = modLoc("block/centrifuge_front");
        ResourceLocation front_on = modLoc("block/centrifuge_front_on");

        BlockModelBuilder modelOff = models().cube(path, end, end, front, side, side, side).texture("particle", side);
        BlockModelBuilder modelOn = models().cube(path + "_on", end, end, front_on, side, side, side).texture("particle", side);

        directionBlock(BlocksRegistration.CENTRIFUGE.get(), (state, builder) -> builder.modelFile(state.getValue(BlockStateProperties.CRAFTING) ? modelOn : modelOff));
    }

    public void registerGenerator() {
        ResourceLocation BOTTOM = new ResourceLocation(MODID, "block/generator_bottom");
        ResourceLocation SIDE = new ResourceLocation(MODID, "block/generator_side");
        ResourceLocation TOP = new ResourceLocation(MODID, "block/generator_top");
        ResourceLocation BACK = new ResourceLocation(MODID, "block/generator_back");
        ResourceLocation FRONT = new ResourceLocation(MODID, "block/generator_front");
        ResourceLocation FRONT_ON = new ResourceLocation(MODID, "block/generator_front_on");
        BlockModelBuilder modelOn = models().cube(BlocksRegistration.GENERATOR.getId().getPath() + "_on", BOTTOM, TOP, FRONT_ON, BACK, SIDE, SIDE).texture("particle", SIDE);
        BlockModelBuilder modelOff = models().cube(BlocksRegistration.GENERATOR.getId().getPath(), BOTTOM, TOP, FRONT, BACK, SIDE, SIDE).texture("particle", SIDE);
        directionBlock(BlocksRegistration.GENERATOR.get(), (state, builder) -> builder.modelFile(state.getValue(BlockStateProperties.POWERED) ? modelOn : modelOff));
    }

    private void directionBlock(Block block, BiConsumer<BlockState, ConfiguredModel.Builder<?>> model) {
        VariantBlockStateBuilder builder = getVariantBuilder(block);
        builder.forAllStates(state -> {
            ConfiguredModel.Builder<?> bld = ConfiguredModel.builder();
            model.accept(state, bld);
            applyRotationBld(bld, state.getValue(BlockStateProperties.FACING));
            return bld.build();
        });
    }

    private void applyRotationBld(ConfiguredModel.Builder<?> builder, Direction direction) {
        switch (direction) {
            case DOWN -> builder.rotationX(90);
            case UP -> builder.rotationX(-90);
            case NORTH -> {
            }
            case SOUTH -> builder.rotationY(180);
            case WEST -> builder.rotationY(270);
            case EAST -> builder.rotationY(90);
        }
    }

    private ResourceLocation key(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    private String name(Block block) {
        return key(block).getPath();
    }
}
