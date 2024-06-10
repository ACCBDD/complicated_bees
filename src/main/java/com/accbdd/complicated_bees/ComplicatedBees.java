package com.accbdd.complicated_bees;

import com.accbdd.complicated_bees.block.ComplicatedBeesBlocks;
import com.accbdd.complicated_bees.client.ColorHandlers;
import com.accbdd.complicated_bees.item.ComplicatedBeesItems;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

@Mod(ComplicatedBees.MODID)
public class ComplicatedBees
{
    public static final String MODID = "complicated_bees";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);


    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BEES_TAB = CREATIVE_MODE_TABS.register("complicated_bees", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.complicated_bees"))
            .icon(() -> ComplicatedBeesItems.DRONE.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ComplicatedBeesItems.DRONE.get());
                output.accept(ComplicatedBeesItems.PRINCESS.get());
                output.accept(ComplicatedBeesItems.QUEEN.get());
                output.accept(ComplicatedBeesBlocks.BEE_NEST.get());
                output.accept(ComplicatedBeesItems.SCOOP.get());
            }).build());

    public ComplicatedBees(IEventBus modEventBus)
    {
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(ColorHandlers::registerItemColorHandlers);

        ComplicatedBeesItems.ITEMS.register(modEventBus);
        ComplicatedBeesBlocks.BLOCKS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        //setup code
    }
}
