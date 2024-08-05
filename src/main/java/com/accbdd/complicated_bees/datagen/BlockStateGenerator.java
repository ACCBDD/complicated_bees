package com.accbdd.complicated_bees.datagen;

import com.accbdd.complicated_bees.registry.BlocksRegistration;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
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
        simpleBlock(BlocksRegistration.CENTRIFUGE.get(), createCentrifugeModel());
        registerGenerator();
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
        ResourceLocation texture = modLoc("block/bee_nest_bottom");

        return models().cubeAll(path, texture);
    }

    public BlockModelBuilder createCentrifugeModel() {
        String path = "centrifuge";
        ResourceLocation texture = modLoc("block/bee_nest_top");

        return models().cubeAll(path, texture);
    }

    public void registerGenerator() {
        ResourceLocation BOTTOM = new ResourceLocation(MODID, "block/generator_bottom");
        ResourceLocation SIDE = new ResourceLocation(MODID, "block/generator_side");
        ResourceLocation TOP = new ResourceLocation(MODID, "block/generator_top");
        ResourceLocation FRONT = new ResourceLocation(MODID, "block/generator_front");
        ResourceLocation FRONT_ON = new ResourceLocation(MODID, "block/generator_front_on");
        BlockModelBuilder modelOn = models().cube(BlocksRegistration.GENERATOR.getId().getPath()+"_on", BOTTOM, TOP, FRONT_ON, SIDE, SIDE, SIDE).texture("particle", SIDE);
        BlockModelBuilder modelOff = models().cube(BlocksRegistration.GENERATOR.getId().getPath(), BOTTOM, TOP, FRONT, SIDE, SIDE, SIDE).texture("particle", SIDE);
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
            case NORTH -> { }
            case SOUTH -> builder.rotationY(180);
            case WEST -> builder.rotationY(270);
            case EAST -> builder.rotationY(90);
        }
    }
}
