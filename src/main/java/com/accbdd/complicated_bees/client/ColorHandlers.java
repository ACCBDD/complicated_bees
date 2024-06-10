package com.accbdd.complicated_bees.client;

import com.accbdd.complicated_bees.item.Bee;
import com.accbdd.complicated_bees.item.ComplicatedBeesItems;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

public class ColorHandlers {
    @SubscribeEvent
    public static void registerItemColorHandlers(RegisterColorHandlersEvent.Item event) {
        event.register(Bee::getItemColor, ComplicatedBeesItems.DRONE, ComplicatedBeesItems.PRINCESS, ComplicatedBeesItems.QUEEN);
    }
}
