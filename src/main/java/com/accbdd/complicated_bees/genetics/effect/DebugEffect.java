package com.accbdd.complicated_bees.genetics.effect;

import com.accbdd.complicated_bees.util.BlockPosBoxIterator;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;

public class DebugEffect extends BeeEffect {
    @Override
    public void runEffect(BlockEntity apiary, ItemStack queen, int cycleProgress) {
        if (apiary.getLevel() == null) return;

        if (cycleProgress == 0) {
            BlockPosBoxIterator iterator = getBlockIterator(apiary, queen);
            while (iterator.hasNext()) {
                BlockPos checkPos = iterator.next();
                if (apiary.getLevel().getBlockState(checkPos).is(Blocks.SAND)) {
                    apiary.getLevel().setBlockAndUpdate(checkPos, Blocks.GLASS.defaultBlockState());
                    break;
                }
            }
        }
    }
}
