package com.accbdd.complicated_bees.datagen;

import com.accbdd.complicated_bees.registry.BlocksRegistration;
import com.accbdd.complicated_bees.registry.ItemsRegistration;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

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
        withExistingParent(BlocksRegistration.FURNACE_GENERATOR.getId().getPath(), modLoc("block/furnace_generator"));
        withExistingParent(BlocksRegistration.HONEY_GENERATOR.getId().getPath(), modLoc("block/honey_generator"));
        withExistingParent(BlocksRegistration.APID_LIBRARY.getId().getPath(), modLoc("block/apid_library"));
        withExistingParent(BlocksRegistration.BEE_SORTER.getId().getPath(), modLoc("block/bee_sorter"));
        withExistingParent(BlocksRegistration.MELLARIUM_BASE.getId().getPath(), modLoc("block/mellarium_base"));
        withExistingParent(BlocksRegistration.MELLARIUM_TEMP_UNIT.getId().getPath(), modLoc("block/mellarium_temp_unit"));
        withExistingParent(BlocksRegistration.MELLARIUM_FRAME_HOUSING_1.getId().getPath(), modLoc("block/mellarium_frame_housing_1"));
        withExistingParent(BlocksRegistration.MELLARIUM_FRAME_HOUSING_2.getId().getPath(), modLoc("block/mellarium_frame_housing_2"));
        withExistingParent(BlocksRegistration.MELLARIUM_FRAME_HOUSING_3.getId().getPath(), modLoc("block/mellarium_frame_housing_3"));
        withExistingParent(BlocksRegistration.MELLARIUM_RAIN_SHIELD.getId().getPath(), modLoc("block/mellarium_rain_shield"));
        withExistingParent(BlocksRegistration.MELLARIUM_MUTATOR.getId().getPath(), modLoc("block/mellarium_mutator"));
        withExistingParent(BlocksRegistration.MELLARIUM_HYDROREGULATOR.getId().getPath(), modLoc("block/mellarium_hydroregulator"));
        withExistingParent(BlocksRegistration.MELLARIUM_ENERGY_CELL.getId().getPath(), modLoc("block/mellarium_energy_cell"));
        withExistingParent(BlocksRegistration.MELLARIUM_SKYBOX.getId().getPath(), modLoc("block/mellarium_skybox"));
        withExistingParent(BlocksRegistration.MELLARIUM_TEMPORAL_SIMULATOR.getId().getPath(), modLoc("block/mellarium_temporal_simulator"));
        withExistingParent(BlocksRegistration.GYROFUGE_BASE.getId().getPath(), modLoc("block/gyrofuge_base"));
        withExistingParent(BlocksRegistration.GYROFUGE_ENERGY_CELL.getId().getPath(), modLoc("block/gyrofuge_energy_cell"));
        withExistingParent(BlocksRegistration.GYROFUGE_BASIC_PROCESSING_UNIT.getId().getPath(), modLoc("block/gyrofuge_basic_processing_unit"));
        withExistingParent(BlocksRegistration.GYROFUGE_PROCESSING_UNIT.getId().getPath(), modLoc("block/gyrofuge_processing_unit"));
        withExistingParent(BlocksRegistration.GYROFUGE_ADVANCED_PROCESSING_UNIT.getId().getPath(), modLoc("block/gyrofuge_advanced_processing_unit"));
        withExistingParent(BlocksRegistration.GYROFUGE_SPEED_UNIT.getId().getPath(), modLoc("block/gyrofuge_speed_unit"));
        withExistingParent(BlocksRegistration.GYROFUGE_EFFICIENCY_UNIT.getId().getPath(), modLoc("block/gyrofuge_efficiency_unit"));
        withExistingParent(BlocksRegistration.GYROFUGE_EXTRACTION_UNIT.getId().getPath(), modLoc("block/gyrofuge_extraction_unit"));
        withExistingParent(BlocksRegistration.GYROFUGE_OUTPUT_HATCH.getId().getPath(), modLoc("block/gyrofuge_output_hatch"));
        withExistingParent(BlocksRegistration.GYROFUGE_INPUT_HATCH.getId().getPath(), modLoc("block/gyrofuge_input_hatch"));

        microscopeModel();
        withExistingParent(BlocksRegistration.WAX_BLOCK.getId().getPath(), modLoc("block/wax_block"));
        withExistingParent(BlocksRegistration.WAX_BLOCK_STAIRS.getId().getPath(), modLoc("block/wax_block_stairs"));
        withExistingParent(BlocksRegistration.WAX_BLOCK_SLAB.getId().getPath(), modLoc("block/wax_block_slab"));
        wallItem(BlocksRegistration.WAX_BLOCK_WALL, BlocksRegistration.WAX_BLOCK);
        withExistingParent(BlocksRegistration.SMOOTH_WAX.getId().getPath(), modLoc("block/smooth_wax"));
        withExistingParent(BlocksRegistration.SMOOTH_WAX_STAIRS.getId().getPath(), modLoc("block/smooth_wax_stairs"));
        withExistingParent(BlocksRegistration.SMOOTH_WAX_SLAB.getId().getPath(), modLoc("block/smooth_wax_slab"));
        wallItem(BlocksRegistration.SMOOTH_WAX_WALL, BlocksRegistration.SMOOTH_WAX);
        withExistingParent(BlocksRegistration.WAX_BRICKS.getId().getPath(), modLoc("block/wax_bricks"));
        withExistingParent(BlocksRegistration.WAX_BRICK_STAIRS.getId().getPath(), modLoc("block/wax_brick_stairs"));
        withExistingParent(BlocksRegistration.WAX_BRICK_SLAB.getId().getPath(), modLoc("block/wax_brick_slab"));
        wallItem(BlocksRegistration.WAX_BRICK_WALL, BlocksRegistration.WAX_BRICKS);
        withExistingParent(BlocksRegistration.CHISELED_WAX.getId().getPath(), modLoc("block/chiseled_wax"));

        withExistingParent(BlocksRegistration.HONEYED_PLANKS.getId().getPath(), modLoc("block/honeyed_planks"));
        withExistingParent(BlocksRegistration.HONEYED_STAIRS.getId().getPath(), modLoc("block/honeyed_stairs"));
        withExistingParent(BlocksRegistration.HONEYED_SLAB.getId().getPath(), modLoc("block/honeyed_slab"));
        fenceItem(BlocksRegistration.HONEYED_FENCE, BlocksRegistration.HONEYED_PLANKS);
        withExistingParent(BlocksRegistration.HONEYED_FENCE_GATE.getId().getPath(), modLoc("block/honeyed_fence_gate"));
        buttonItem(BlocksRegistration.HONEYED_BUTTON, BlocksRegistration.HONEYED_PLANKS);
        withExistingParent(BlocksRegistration.HONEYED_PRESSURE_PLATE.getId().getPath(), modLoc("block/honeyed_pressure_plate"));
        basicItem(BlocksRegistration.HONEYED_DOOR.getId());
        withExistingParent(BlocksRegistration.HONEYED_TRAPDOOR.getId().getPath(), modLoc("block/honeyed_trapdoor_bottom"));
        basicItem(ItemsRegistration.HONEYED_SIGN.getId());
        basicItem(ItemsRegistration.HONEYED_HANGING_SIGN.getId());

        basicItem(ItemsRegistration.SCOOP.get());
        basicItem(ItemsRegistration.HONEY_DROPLET.get());
        basicItem(ItemsRegistration.BEESWAX.get());
        basicItem(ItemsRegistration.PROPOLIS.get());
        basicItem(ItemsRegistration.ROYAL_JELLY.get());
        basicItem(ItemsRegistration.POLLEN.get());
        basicItem(ItemsRegistration.METER.get());
        basicItem(ItemsRegistration.ANALYZER.get());
        basicItem(ItemsRegistration.FRAME.get());
        basicItem(ItemsRegistration.WAXED_FRAME.get());
        basicItem(ItemsRegistration.HONEYED_FRAME.get());
        basicItem(ItemsRegistration.TWISTING_FRAME.get());
        basicItem(ItemsRegistration.SOOTHING_FRAME.get());
        basicItem(ItemsRegistration.HOT_FRAME.get());
        basicItem(ItemsRegistration.COLD_FRAME.get());
        basicItem(ItemsRegistration.DRY_FRAME.get());
        basicItem(ItemsRegistration.WET_FRAME.get());
        basicItem(ItemsRegistration.DEADLY_FRAME.get());
        basicItem(ItemsRegistration.RESTRICTIVE_FRAME.get());
        basicItem(ItemsRegistration.PEARL_SHARD.get());
        basicItem(ItemsRegistration.HONEYED_STICK.get());
        basicItem(ItemsRegistration.WAXED_STICK.get());
        basicItem(ItemsRegistration.EXP_DROP.get());
        basicItem(ItemsRegistration.SILK_WISP.get());
        basicItem(ItemsRegistration.WOVEN_MESH.get());
        basicItem(ItemsRegistration.APIARIST_HELMET.get());
        basicItem(ItemsRegistration.APIARIST_CHESTPLATE.get());
        basicItem(ItemsRegistration.APIARIST_LEGGINGS.get());
        basicItem(ItemsRegistration.APIARIST_BOOTS.get());
        basicItem(ItemsRegistration.HONEY_BREAD.get());
        basicItem(ItemsRegistration.HONEY_PORKCHOP.get());
        basicItem(ItemsRegistration.AMBROSIA.get());
        basicItem(ItemsRegistration.BEE_STAFF.get());
        basicItem(ItemsRegistration.MELLARIUM_PANEL.get());
        basicItem(ItemsRegistration.BASIC_UPGRADE.get());
        basicItem(ItemsRegistration.WAXED_UPGRADE.get());
        basicItem(ItemsRegistration.HONEYED_UPGRADE.get());
        basicItem(ItemsRegistration.TWISTED_UPGRADE.get());
        basicItem(ItemsRegistration.SILKY_UPGRADE.get());
        basicItem(ItemsRegistration.ROYAL_UPGRADE.get());
        basicItem(ItemsRegistration.ENDENIC_UPGRADE.get());
        basicItem(ItemsRegistration.WITHERED_UPGRADE.get());
        basicItem(ItemsRegistration.RESEARCH_NOTE.get());

        createCombModel();
        beeModel("base", "bee_base");
        beeModel("ender", "ender_bee_base");
        beeModel("gray", "gray_bee_base");
        beeModel("jazzy", "jazzy_bee_base", "jazzy_bee_outline");
        beeModel("red", "red_bee_base");
        beeModel("tricky", "tricky_bee_base");
        beeModel("primordial", "primordial_bee_base", "primordial_bee_outline");
        beeModel("plains_terraform", "plains_terraform_bee_base");
        beeModel("forest_terraform", "forest_terraform_bee_base");
        beeModel("taiga_terraform", "taiga_terraform_bee_base");
        beeModel("desert_terraform", "desert_terraform_bee_base");
        beeModel("mushroom_terraform", "mushroom_terraform_bee_base");
        beeModel("snowy_terraform", "snowy_terraform_bee_base");
        beeModel("jungle_terraform", "jungle_terraform_bee_base");
        beeModel("swamp_terraform", "swamp_terraform_bee_base");

        //patchouli book model
        getBuilder("complicated_bees:apiarist_guide")
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", modLoc("item/patchouli/apiarist_guide"));
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

    private void wallItem(RegistryObject<?> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/wall_inventory"))
                .texture("wall", ResourceLocation.tryBuild(MODID, "block/" + baseBlock.getId().getPath()));
    }

    private void fenceItem(RegistryObject<?> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/fence_inventory"))
                .texture("texture", ResourceLocation.tryBuild(MODID, "block/" + baseBlock.getId().getPath()));
    }

    private void buttonItem(RegistryObject<?> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/button_inventory"))
                .texture("texture", ResourceLocation.tryBuild(MODID, "block/" + baseBlock.getId().getPath()));
    }

    private void microscopeModel() {
        ResourceLocation path = modLoc("microscope");
        getBuilder(path.toString())
                .parent(getExistingFile(modLoc("block/microscope")))
                .transforms()
                    .transform(ItemDisplayContext.GROUND)
                        .scale(0.5f)
                        .translation(0, 3, 0)
                    .end()
                    .transform(ItemDisplayContext.GUI)
                        .scale(0.625f)
                        .rotation(30, 225, 0)
                    .end()
                .end().guiLight(BlockModel.GuiLight.SIDE);

    }

    private void beeModel(String name, String beeBase, String beeOutline) {
        getBuilder(modLoc("bee/"+name+"_drone").toString()).parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", modLoc("item/"+beeBase))
                .texture("layer1", modLoc("item/"+beeOutline));
        getBuilder(modLoc("bee/"+name+"_princess").toString()).parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", modLoc("item/"+beeBase))
                .texture("layer1", modLoc("item/"+beeOutline))
                .texture("layer2", modLoc("item/princess_crown"));
        getBuilder(modLoc("bee/"+name+"_queen").toString()).parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", modLoc("item/"+beeBase))
                .texture("layer1", modLoc("item/"+beeOutline))
                .texture("layer2", modLoc("item/queen_crown"));
    }

    private void beeModel(String name, String beeBase) {
        beeModel(name, beeBase, "bee_outline");
    }
}
