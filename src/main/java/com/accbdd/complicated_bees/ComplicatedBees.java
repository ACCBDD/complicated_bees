package com.accbdd.complicated_bees;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

@Mod(ComplicatedBees.MODID)
public class ComplicatedBees
{
    public static final String MODID = "complicated_bees";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ComplicatedBees.MODID);

    public ComplicatedBees(IEventBus modEventBus)
    {
        modEventBus.addListener(this::commonSetup);

        ITEMS.register(modEventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        //setup code
    }
}
