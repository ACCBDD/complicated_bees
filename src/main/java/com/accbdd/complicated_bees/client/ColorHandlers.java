package com.accbdd.complicated_bees.client;

import com.accbdd.complicated_bees.block.BeeNestBlock;
import com.accbdd.complicated_bees.block.entity.BeeNestBlockEntity;
import com.accbdd.complicated_bees.item.BeeItem;
import com.accbdd.complicated_bees.item.CombItem;
import com.accbdd.complicated_bees.registry.BlocksRegistration;
import com.accbdd.complicated_bees.registry.ItemsRegistration;
import net.minecraft.world.level.BlockAndTintGetter;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

public class ColorHandlers {
    @SubscribeEvent
    public static void registerItemColorHandlers(RegisterColorHandlersEvent.Item event) {
        event.register(BeeItem::getItemColor, ItemsRegistration.DRONE, ItemsRegistration.PRINCESS, ItemsRegistration.QUEEN);
        event.register(CombItem::getItemColor, ItemsRegistration.COMB);
        event.register(BeeNestBlock::getItemColor, ItemsRegistration.BEE_NEST);
    }

    @SubscribeEvent
    public static void registerBlockColorHandlers(RegisterColorHandlersEvent.Block event) {
        event.register(BeeNestBlockEntity::getNestColor, BlocksRegistration.BEE_NEST.get());
    }
}
