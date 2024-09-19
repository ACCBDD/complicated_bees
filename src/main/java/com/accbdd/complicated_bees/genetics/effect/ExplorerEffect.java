package com.accbdd.complicated_bees.genetics.effect;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ExplorerEffect extends BeeEffect {
    @Override
    public void runEffect(BlockEntity apiary, ItemStack queen, int cycleProgress) {
        if (apiary.getLevel() == null) return;

        if (cycleProgress == 0) {
            for (Entity entity : getTerritoryEntities(apiary, queen)) {
                if (entity instanceof Player player) {
                    player.giveExperiencePoints(apiary.getLevel().random.nextInt(1, 5));
                }
            }
        }
    }
}
