package com.accbdd.complicated_bees.item;

import com.accbdd.complicated_bees.registry.BlocksRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;

public class BeeNestBlockItem extends BlockItem {
    public BeeNestBlockItem(Properties prop) {
        super(BlocksRegistration.BEE_NEST.get(), prop);
    }

    @Override
    public Component getName(ItemStack pStack) {
        return Component.translatable("species.complicated_bees." + pStack.getOrCreateTag().getCompound("BlockEntityTag").getString("species"))
                .append(" ")
                .append(super.getName(pStack));
    }
}
