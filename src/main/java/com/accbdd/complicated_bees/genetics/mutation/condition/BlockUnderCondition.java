package com.accbdd.complicated_bees.genetics.mutation.condition;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BlockUnderCondition extends MutationCondition {
    public static String ID = "block_under";
    Block block;

    public BlockUnderCondition(Block block) {
        this.block = block;
    }

    @Override
    public boolean check(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos.below());
        return state.is(block);
    }

    @Override
    public Component getDescription() {
        return Component.translatable("gui.complicated_bees.mutations.block_under", Component.translatable(block.getDescriptionId()));
    }

    @Override
    public CompoundTag serialize() {
        CompoundTag tag = new CompoundTag();
        tag.put("block", StringTag.valueOf(BuiltInRegistries.BLOCK.getKey(this.block).toString()));
        return tag;
    }

    @Override
    public BlockUnderCondition deserialize(CompoundTag tag) {
        return new BlockUnderCondition(BuiltInRegistries.BLOCK.get(ResourceLocation.tryParse(tag.getString("block"))));
    }
}
