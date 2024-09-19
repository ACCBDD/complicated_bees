package com.accbdd.complicated_bees.genetics.effect;

import com.accbdd.complicated_bees.util.BlockPosBoxIterator;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.CocoaBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

import java.util.ArrayList;
import java.util.List;

public class PollenicEffect extends BeeEffect {
    @Override
    public void runEffect(BlockEntity apiary, ItemStack queen, int cycleProgress) {
        if (apiary.getLevel() == null) return;

        if (cycleProgress == 0) {
            BlockPosBoxIterator iterator = getBlockIterator(apiary, queen);
            List<BlockPos> positions = new ArrayList<>();
            ServerLevel level = ServerLifecycleHooks.getCurrentServer().getLevel(apiary.getLevel().dimension());
            if (level != null) {
                while (iterator.hasNext()) {
                    BlockPos checkPos = iterator.next();
                    BlockState state = level.getBlockState(checkPos);
                    if ((state.getBlock() instanceof CropBlock || state.getBlock() instanceof CocoaBlock || state.getBlock() instanceof BushBlock) && state.getBlock() instanceof BonemealableBlock mealable && mealable.isValidBonemealTarget(level, checkPos, state)) {
                        positions.add(checkPos);
                    }
                }
                if (!positions.isEmpty()) {
                    BlockPos growPos = positions.get(level.random.nextInt(positions.size()));
                    BlockState state = level.getBlockState(growPos);
                    if (state.getBlock() instanceof BonemealableBlock mealable) {
                        if (mealable.isValidBonemealTarget(level, growPos, state) && level.random.nextFloat() < 0.25) {
                            mealable.performBonemeal(level, level.random, growPos, state);
                            level.levelEvent(1505, growPos, 0);
                        }
                    }
                }
            }
        }
    }
}
