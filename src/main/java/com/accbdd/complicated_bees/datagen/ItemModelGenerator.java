package com.accbdd.complicated_bees.datagen;

import com.accbdd.complicated_bees.client.BeeModelBuilder;
import com.accbdd.complicated_bees.registry.BlocksRegistration;
import com.accbdd.complicated_bees.registry.ItemsRegistration;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

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
        basicItem(ItemsRegistration.PEARL_SHARD.get());
        basicItem(ItemsRegistration.EXP_DROP.get());
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
                .texture("layer1", bee_outline)
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

    private void wallItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/wall_inventory"))
                .texture("wall", new ResourceLocation(MODID, "block/" + baseBlock.getId().getPath()));
    }
}
