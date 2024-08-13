package com.accbdd.complicated_bees.datagen;

import com.accbdd.complicated_bees.registry.BlocksRegistration;
import com.accbdd.complicated_bees.registry.ItemsRegistration;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
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
        ResourceLocation bee_base = modLoc("item/bee_base");
        ResourceLocation bee_outline = modLoc("item/bee_outline");
        return getBuilder(bee_type.toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", bee_base)
                .texture("layer1", bee_outline);
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
