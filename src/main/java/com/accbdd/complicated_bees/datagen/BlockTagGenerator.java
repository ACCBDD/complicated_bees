package com.accbdd.complicated_bees.datagen;

import com.accbdd.complicated_bees.registry.BlocksRegistration;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class BlockTagGenerator extends BlockTagsProvider {
    public static final TagKey<Block> SCOOPABLE = BlockTags.create(new ResourceLocation("complicated_bees:mineable/scoop_tool"));

    public BlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(BlockTags.MINEABLE_WITH_AXE).add(BlocksRegistration.APIARY.get());
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                BlocksRegistration.CENTRIFUGE.get(),
                BlocksRegistration.CHISELED_WAX.get(),
                BlocksRegistration.WAX_BLOCK.get(),
                BlocksRegistration.WAX_BLOCK_STAIRS.get(),
                BlocksRegistration.WAX_BLOCK_SLAB.get(),
                BlocksRegistration.WAX_BLOCK_WALL.get(),
                BlocksRegistration.SMOOTH_WAX.get(),
                BlocksRegistration.SMOOTH_WAX_STAIRS.get(),
                BlocksRegistration.SMOOTH_WAX_SLAB.get(),
                BlocksRegistration.SMOOTH_WAX_WALL.get(),
                BlocksRegistration.WAX_BRICKS.get(),
                BlocksRegistration.WAX_BRICK_STAIRS.get(),
                BlocksRegistration.WAX_BRICK_SLAB.get(),
                BlocksRegistration.WAX_BRICK_WALL.get(),
                BlocksRegistration.CHISELED_WAX.get()
        );
        tag(SCOOPABLE).add(BlocksRegistration.BEE_NEST.get());
        tag(BlockTags.WALLS).add(
                BlocksRegistration.WAX_BLOCK_WALL.get(),
                BlocksRegistration.SMOOTH_WAX_WALL.get(),
                BlocksRegistration.WAX_BRICK_WALL.get()
        );
    }
}
