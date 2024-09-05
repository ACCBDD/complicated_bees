package com.accbdd.complicated_bees.datagen;

import com.accbdd.complicated_bees.client.BeeModelBuilder;
import com.accbdd.complicated_bees.registry.BlocksRegistration;
import com.accbdd.complicated_bees.registry.ItemsRegistration;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.neoforged.neoforge.client.model.ItemLayerModel;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.loaders.ItemLayerModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class ItemModelGenerator extends ItemModelProvider {
    public ItemModelGenerator(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        withExistingParent(BlocksRegistration.BEE_NEST.getId().getPath(), modLoc("block/bee_nest"));
        withExistingParent(BlocksRegistration.APIARY.getId().getPath(), modLoc("block/apiary"));
        withExistingParent(BlocksRegistration.CENTRIFUGE.getId().getPath(), modLoc("block/centrifuge"));
        withExistingParent(BlocksRegistration.GENERATOR.getId().getPath(), modLoc("block/generator"));
        basicItem(ItemsRegistration.SCOOP.get());
        basicItem(ItemsRegistration.HONEY_DROPLET.get());
        basicItem(ItemsRegistration.BEESWAX.get());
        basicItem(ItemsRegistration.PROPOLIS.get());
        basicItem(ItemsRegistration.ROYAL_JELLY.get());
        basicItem(ItemsRegistration.POLLEN.get());
        basicItem(ItemsRegistration.METER.get());
        basicItem(ItemsRegistration.ANALYZER.get());
        basicItem(ItemsRegistration.FRAME.get());
        basicItem(ItemsRegistration.THICK_FRAME.get());
        basicItem(ItemsRegistration.HOT_FRAME.get());
        basicItem(ItemsRegistration.COLD_FRAME.get());
        basicItem(ItemsRegistration.DRY_FRAME.get());
        basicItem(ItemsRegistration.MOIST_FRAME.get());
        basicItem(ItemsRegistration.DEADLY_FRAME.get());
        basicItem(ItemsRegistration.RESTRICTIVE_FRAME.get());
        createBeeModel(ItemsRegistration.DRONE.getId());
        createBeeModel(ItemsRegistration.PRINCESS.getId()).texture("layer2", modLoc("item/princess_crown"));
        createBeeModel(ItemsRegistration.QUEEN.getId()).texture("layer2", modLoc("item/queen_crown"));
        createCombModel();
    }

    private ItemModelBuilder createBeeModel(ResourceLocation bee_type) {
        //todo: create custom model to allow string based overrides!
        ResourceLocation bee_base = modLoc("item/bee_base");
        ResourceLocation bee_outline = modLoc("item/bee_outline");

        return getBuilder(bee_type.toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", bee_base)
                .texture("layer1", bee_outline)
//                .guiLight(BlockModel.GuiLight.FRONT)
//                .transforms()
//                    .transform(ItemDisplayContext.GROUND).rotation(0,0,0).translation(0,2, 0).scale(0.5f).end()
//                    .transform(ItemDisplayContext.HEAD).rotation(0, 180, 0).translation(0, 13, 7).scale(1).end()
//                    .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).rotation(0, 0, 0).translation(0, 3, 1).scale(0.55f).end()
//                    .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).rotation(0, -90, 25).translation(1.13f, 3.2f, 1.13f).scale(0.68f).end()
//                    .transform(ItemDisplayContext.FIXED).rotation(0, 180, 0).scale(1).end()
//                .end()
                .customLoader(BeeModelBuilder::begin).end();
    }

    private void createCombModel() {
        ResourceLocation comb_outer = modLoc("item/comb_outer");
        ResourceLocation comb_inner = modLoc("item/comb_inner");
        ResourceLocation path = modLoc("comb");
        getBuilder(path.toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", comb_outer)
                .texture("layer1", comb_inner);
    }
}
