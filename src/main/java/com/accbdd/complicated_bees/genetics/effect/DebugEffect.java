package com.accbdd.complicated_bees.genetics.effect;

import com.accbdd.complicated_bees.util.BlockPosBoxIterator;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;

public class DebugEffect implements IBeeEffect {
    @Override
    public void runEffect(BlockEntity entity, ItemStack queen, int cycleProgress) {
        if (cycleProgress == 0) {
            BlockPosBoxIterator iterator = new BlockPosBoxIterator(entity.getBlockPos(), 3, 3);
            while (iterator.hasNext()) {
                BlockPos checkPos = iterator.next();
                if (entity.getLevel().getBlockState(checkPos).is(Blocks.SAND)) {
                    entity.getLevel().setBlockAndUpdate(checkPos, Blocks.GLASS.defaultBlockState());
                    break;
                }
            }
        }
    }
}
