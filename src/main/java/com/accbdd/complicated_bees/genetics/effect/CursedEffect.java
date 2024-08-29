package com.accbdd.complicated_bees.genetics.effect;

import com.accbdd.complicated_bees.util.BlockPosBoxIterator;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class CursedEffect implements IBeeEffect {
    @Override
    public void runEffect(BlockEntity entity, ItemStack queen, int cycleProgress) {
        if (cycleProgress == 0) {
            Level level = entity.getLevel();
            if (level == null) return;
            BlockPosBoxIterator iterator = new BlockPosBoxIterator(entity.getBlockPos(), 3, 3);
            List<BlockPos> convertables = new ArrayList<>();
            while (iterator.hasNext()) {
                BlockPos checkPos = iterator.next();
                BlockState state = level.getBlockState(checkPos);
                if (state.is(Blocks.SAND) || state.is(Blocks.RED_SAND) || state.is(Blocks.GRASS_BLOCK) || state.is(Blocks.DIRT) || state.is(Blocks.COARSE_DIRT)) {
                    convertables.add(checkPos);
                }
            }

            if (level.random.nextFloat() < 0.1 || !convertables.isEmpty()) {
                BlockPos switchPos = convertables.get(level.random.nextInt(convertables.size()));
                BlockState state = level.getBlockState(switchPos);
                if (state.is(Blocks.SAND) || state.is(Blocks.RED_SAND)) {
                    level.setBlockAndUpdate(switchPos, Blocks.SOUL_SAND.defaultBlockState());
                } else {
                    level.setBlockAndUpdate(switchPos, Blocks.SOUL_SOIL.defaultBlockState());
                }
            }
        }
    }
}
