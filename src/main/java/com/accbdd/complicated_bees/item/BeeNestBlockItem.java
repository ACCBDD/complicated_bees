package com.accbdd.complicated_bees.item;

import com.accbdd.complicated_bees.registry.BlocksRegistration;
import net.minecraft.world.item.BlockItem;

public class BeeNestBlockItem extends BlockItem {
    public BeeNestBlockItem(Properties prop) {
        super(BlocksRegistration.BEE_NEST.get(), prop);
    }
}
