package com.accbdd.complicated_bees.client;

import com.accbdd.complicated_bees.block.BeeNestBlock;
import com.accbdd.complicated_bees.block.entity.BeeNestBlockEntity;
import com.accbdd.complicated_bees.item.BeeItem;
import com.accbdd.complicated_bees.item.CombItem;
import com.accbdd.complicated_bees.registry.BlocksRegistration;
import com.accbdd.complicated_bees.registry.ItemsRegistration;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class ColorHandlers {
    @SubscribeEvent
    public static void registerItemColorHandlers(RegisterColorHandlersEvent.Item event) {
        event.register(BeeItem::getItemColor, ItemsRegistration.DRONE.get(), ItemsRegistration.PRINCESS.get(), ItemsRegistration.QUEEN.get());
        event.register(CombItem::getItemColor, ItemsRegistration.COMB.get());
        event.register(BeeNestBlock::getItemColor, ItemsRegistration.BEE_NEST.get());
    }

    @SubscribeEvent
    public static void registerBlockColorHandlers(RegisterColorHandlersEvent.Block event) {
        event.register(BeeNestBlockEntity::getNestColor, BlocksRegistration.BEE_NEST.get());
    }
}
