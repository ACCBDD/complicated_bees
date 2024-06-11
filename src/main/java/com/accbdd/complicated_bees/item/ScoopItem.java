package com.accbdd.complicated_bees.item;

import com.accbdd.complicated_bees.block.BeeNestBlock;
import com.accbdd.complicated_bees.block.ComplicatedBeesBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class ScoopItem extends Item {

    public ScoopItem(Properties prop) {
        super(prop.durability(50));
    }

    @Override
    public boolean mineBlock(ItemStack pStack, Level pLevel, BlockState pState, BlockPos pPos, LivingEntity pMiningEntity) {
        if (!pLevel.isClientSide && !pState.is(BlockTags.FIRE)) {
            pStack.hurtAndBreak(1, pMiningEntity, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }
        return !pState.is(ComplicatedBeesBlocks.BEE_NEST)
                ? super.mineBlock(pStack, pLevel, pState, pPos, pMiningEntity)
                : true;
    }

    @Override
    public boolean isCorrectToolForDrops(BlockState pBlock) {
        if (pBlock.is(ComplicatedBeesBlocks.BEE_NEST)) {
            return true;
        }
        return super.isCorrectToolForDrops(pBlock);
    }
}
