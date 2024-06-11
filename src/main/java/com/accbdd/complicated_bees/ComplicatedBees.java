package com.accbdd.complicated_bees;

import com.accbdd.complicated_bees.block.ComplicatedBeesBlocks;
import com.accbdd.complicated_bees.client.ColorHandlers;
import com.accbdd.complicated_bees.genetics.Species;
import com.accbdd.complicated_bees.registry.ComplicatedBeesCodecs;
import com.accbdd.complicated_bees.registry.SpeciesRegistry;
import com.accbdd.complicated_bees.item.Bee;
import com.accbdd.complicated_bees.item.ComplicatedBeesItems;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
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
                for (ResourceLocation id : Minecraft.getInstance().getConnection().registryAccess().registry(SpeciesRegistry.SPECIES_REGISTRY_KEY).get().keySet()) {
                    output.accept(Bee.setSpecies(ComplicatedBeesItems.DRONE.get().getDefaultInstance(), id));
                    output.accept(Bee.setSpecies(ComplicatedBeesItems.PRINCESS.get().getDefaultInstance(), id));
                    output.accept(Bee.setSpecies(ComplicatedBeesItems.QUEEN.get().getDefaultInstance(), id));
                }
                output.accept(ComplicatedBeesItems.BEE_NEST.get());
                output.accept(ComplicatedBeesItems.SCOOP.get());
            }).build());

    public ComplicatedBees(IEventBus modEventBus)
    {
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(ColorHandlers::registerItemColorHandlers);
        modEventBus.addListener(this::registerRegistries);

        ComplicatedBeesItems.ITEMS.register(modEventBus);
        ComplicatedBeesBlocks.BLOCKS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
    }

    @SubscribeEvent
    public void registerRegistries(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(
                SpeciesRegistry.SPECIES_REGISTRY_KEY,
                ComplicatedBeesCodecs.SPECIES_CODEC,
                ComplicatedBeesCodecs.SPECIES_CODEC
        );
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        //setup code
    }
}
