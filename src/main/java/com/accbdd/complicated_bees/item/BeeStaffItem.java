package com.accbdd.complicated_bees.item;

import com.accbdd.complicated_bees.entity.BeeStaffProjectile;
import com.accbdd.complicated_bees.registry.ItemsRegistration;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeConfigSpec;

public class BeeStaffItem extends DisableableItem {
    public BeeStaffItem(Properties pProperties, ForgeConfigSpec.ConfigValue<Boolean> configValue) {
        super(pProperties.rarity(Rarity.UNCOMMON).defaultDurability(100), configValue);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        Vec3 vector = pPlayer.getLookAngle().scale(0.1f);
        Vec3 position = pPlayer.position();
        BeeStaffProjectile projectile = new BeeStaffProjectile(pLevel, pPlayer, vector.x, vector.y, vector.z);
        projectile.moveTo(position.x, position.y + 1.5f, position.z);
        pLevel.addFreshEntity(projectile);
        pPlayer.getItemInHand(pUsedHand).hurtAndBreak(1, pPlayer, (player) -> player.broadcastBreakEvent(pUsedHand));
        pPlayer.playSound(SoundEvents.BEE_HURT);
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public boolean isValidRepairItem(ItemStack pStack, ItemStack pRepairCandidate) {
        return pRepairCandidate.is(ItemsRegistration.ROYAL_JELLY.get());
    }
}
