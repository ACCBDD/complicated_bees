package com.accbdd.complicated_bees.genetics.effect;

import com.accbdd.complicated_bees.genetics.GeneticHelper;
import com.accbdd.complicated_bees.genetics.gene.GeneTerritory;
import com.accbdd.complicated_bees.registry.ItemsRegistration;
import com.accbdd.complicated_bees.util.BlockPosBoxIterator;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class BeeEffect implements IBeeEffect {
    @Override
    public abstract void runEffect(BlockEntity apiary, ItemStack queen, int cycleProgress);

    /**
     * @param apiary the BlockEntity generating this effect
     * @param queen  the queen generating this effect
     * @return a list of all entities in this queen's territory, minus any players wearing a full set of apiarist armor
     */
    protected List<Entity> getTerritoryEntities(BlockEntity apiary, ItemStack queen) {
        List<Entity> entities = new ArrayList<>();
        Vec3 center = apiary.getBlockPos().getCenter();
        int[] radii = (int[]) GeneticHelper.getGeneValue(queen, GeneTerritory.ID, true);
        Vec3 offset = new Vec3(radii[0], radii[1], radii[0]);
        for (Entity entity : Objects.requireNonNull(apiary.getLevel()).getEntities(null, new AABB(center.add(offset), center.subtract(offset)))) {
            if (entity instanceof Player player && hasApiaristArmorEquipped(player))
                continue;
            entities.add(entity);
        }
        return entities;
    }

    /**
     * @param apiary the BlockEntity generating this effect
     * @param queen  the queen generating this effect
     * @return a BlockPosBoxIterator sized to the queen's territory
     */
    protected BlockPosBoxIterator getBlockIterator(BlockEntity apiary, ItemStack queen) {
        int[] radii = (int[]) GeneticHelper.getGeneValue(queen, GeneTerritory.ID, true);
        return new BlockPosBoxIterator(apiary.getBlockPos(), radii[0], radii[1]);
    }

    private boolean hasApiaristArmorEquipped(Player player) {
        var inv = player.getInventory();
        return inv.getArmor(0).is(ItemsRegistration.APIARIST_BOOTS) &&
                inv.getArmor(1).is(ItemsRegistration.APIARIST_LEGGINGS) &&
                inv.getArmor(2).is(ItemsRegistration.APIARIST_CHESTPLATE) &&
                inv.getArmor(3).is(ItemsRegistration.APIARIST_HELMET);
    }
}
