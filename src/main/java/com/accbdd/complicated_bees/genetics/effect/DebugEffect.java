package com.accbdd.complicated_bees.genetics.effect;

import com.accbdd.complicated_bees.util.BlockPosBoxIterator;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class DebugEffect implements IBeeEffect {
    @Override
    public void runEffect(Level level, BlockPos pos, ItemStack queen, int cycleProgress) {
        if (cycleProgress == 0) {
            BlockPosBoxIterator iterator = new BlockPosBoxIterator(pos, 3, 3);
            while (iterator.hasNext()) {
                BlockPos checkPos = iterator.next();
                if (level.getBlockState(checkPos).is(Blocks.SAND)) {
                    level.setBlockAndUpdate(checkPos, Blocks.GLASS.defaultBlockState());
                    break;
                }
            }
        }
    }
}
