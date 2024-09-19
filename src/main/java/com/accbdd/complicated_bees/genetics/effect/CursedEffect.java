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

public class CursedEffect extends BeeEffect {
    @Override
    public void runEffect(BlockEntity apiary, ItemStack queen, int cycleProgress) {
        if (apiary.getLevel() == null) return;

        if (cycleProgress == 0) {
            Level level = apiary.getLevel();
            if (level == null) return;
            BlockPosBoxIterator iterator = getBlockIterator(apiary, queen);
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
                } else if (state.is(Blocks.GRASS_BLOCK)) {
                    level.setBlockAndUpdate(switchPos, Blocks.DIRT.defaultBlockState());
                } else if (state.is(Blocks.DIRT)) {
                    level.setBlockAndUpdate(switchPos, Blocks.COARSE_DIRT.defaultBlockState());
                } else {
                    level.setBlockAndUpdate(switchPos, Blocks.SOUL_SOIL.defaultBlockState());
                }
            }
        }
    }
}
