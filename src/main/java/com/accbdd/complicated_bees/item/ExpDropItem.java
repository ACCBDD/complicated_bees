package com.accbdd.complicated_bees.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

public class ExpDropItem extends Item {
    public ExpDropItem(Properties prop) {
        super(prop.stacksTo(64).rarity(Rarity.UNCOMMON));
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);
        if (pPlayer.isCrouching()) {
            pPlayer.giveExperiencePoints(10 * stack.getCount());
            stack.setCount(0);
        } else {
            pPlayer.giveExperiencePoints(10);
            stack.shrink(1);
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
