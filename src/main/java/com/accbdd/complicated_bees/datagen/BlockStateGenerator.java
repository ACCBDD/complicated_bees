package com.accbdd.complicated_bees.datagen;

import com.accbdd.complicated_bees.registry.BlocksRegistration;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

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
}
