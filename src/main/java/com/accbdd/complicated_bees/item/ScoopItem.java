package com.accbdd.complicated_bees.item;

import com.accbdd.complicated_bees.datagen.BlockTagGenerator;
import com.accbdd.complicated_bees.registry.BlocksRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.SimpleTier;

public class ScoopItem extends DiggerItem {

    public ScoopItem(Properties pProperties) {
        super(0,
                0,
                new SimpleTier(1, 50, 4, 0, 15, null, () -> Ingredient.of(ItemTags.WOOL)),
                BlockTagGenerator.SCOOPABLE,
                pProperties.durability(50));
    }

    @Override
    public boolean mineBlock(ItemStack pStack, Level pLevel, BlockState pState, BlockPos pPos, LivingEntity pMiningEntity) {
        if (!pLevel.isClientSide && !pState.is(BlockTags.FIRE)) {
            pStack.hurtAndBreak(1, pMiningEntity, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }
        return pState.is(BlocksRegistration.BEE_NEST.get());
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState pBlock) {
        if (pBlock.is(BlocksRegistration.BEE_NEST.get())) {
            return true;
        }
        return super.isCorrectToolForDrops(stack, pBlock);
    }

    @Override
    public int getEnchantmentValue(ItemStack stack) {
        return 14;
    }

    @Override
    public boolean isValidRepairItem(ItemStack pToRepair, ItemStack pRepair) {
        return pRepair.is(ItemTags.WOOL);
    }
}
