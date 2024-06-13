package com.accbdd.complicated_bees.client;

import com.accbdd.complicated_bees.item.BeeItem;
import com.accbdd.complicated_bees.item.CombItem;
import com.accbdd.complicated_bees.registry.ComplicatedBeesItems;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

public class ColorHandlers {
    @SubscribeEvent
    public static void registerItemColorHandlers(RegisterColorHandlersEvent.Item event) {
        event.register(BeeItem::getItemColor, ComplicatedBeesItems.DRONE, ComplicatedBeesItems.PRINCESS, ComplicatedBeesItems.QUEEN);
        event.register(CombItem::getItemColor, ComplicatedBeesItems.COMB);
    }
}
