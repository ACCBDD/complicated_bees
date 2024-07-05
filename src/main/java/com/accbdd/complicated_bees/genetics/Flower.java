package com.accbdd.complicated_bees.genetics;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Flower {
    private final Set<Block> flowerBlocks;
    private final Set<TagKey<Block>> flowerTags;

    public static final Flower INVALID = new Flower(new ArrayList<>(), new ArrayList<>());

    public Flower(List<ResourceLocation> blocks, List<TagKey<Block>> tags) {
        flowerBlocks = new HashSet<>();
        flowerTags = new HashSet<>();
        for (ResourceLocation block : blocks) {
            flowerBlocks.add(BuiltInRegistries.BLOCK.get(block));
        }
        flowerTags.addAll(tags);
    }

    public boolean isAcceptable(BlockState block) {
        return flowerBlocks.contains(block.getBlock()) || this.withinTags(block);
    }

    private boolean withinTags(BlockState block) {
        boolean within = false;
        for (TagKey<Block> blockTagKey : flowerTags) {
            within = block.is(blockTagKey) || within;
        }
        return within;
    }

    public List<ResourceLocation> getBlocksAsResourceLocs() {
        List<ResourceLocation> result = new ArrayList<>();
        for (Block block : flowerBlocks) {
            result.add(BuiltInRegistries.BLOCK.getKey(block));
        }

        return result;
    }

    public List<TagKey<Block>> getTags() {
        return flowerTags.stream().toList();
    }
}
