package com.accbdd.complicated_bees;

import com.accbdd.complicated_bees.client.ColorHandlers;
import com.accbdd.complicated_bees.datagen.DataGenerators;
import com.accbdd.complicated_bees.genetics.GeneticHelper;
import com.accbdd.complicated_bees.genetics.Species;
import com.accbdd.complicated_bees.item.CombItem;
import com.accbdd.complicated_bees.registry.*;
import com.accbdd.complicated_bees.screen.ApiaryScreen;
import com.accbdd.complicated_bees.screen.CentrifugeScreen;
import com.accbdd.complicated_bees.utils.ComplicatedBeesCodecs;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.slf4j.Logger;

import java.util.Map;

@Mod(ComplicatedBees.MODID)
public class ComplicatedBees
{
    public static final String MODID = "complicated_bees";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);


    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BEES_TAB = CREATIVE_MODE_TABS.register("complicated_bees", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.complicated_bees"))
            .icon(() -> ItemsRegistration.DRONE.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                for (Map.Entry<ResourceKey<Species>, Species> entry: Minecraft.getInstance().getConnection().registryAccess().registry(SpeciesRegistry.SPECIES_REGISTRY_KEY).get().entrySet()) {
                    output.accept(GeneticHelper.setBothGenome(ItemsRegistration.DRONE.get().getDefaultInstance(), entry.getValue().getDefaultChromosome()));
                    output.accept(GeneticHelper.setBothGenome(ItemsRegistration.PRINCESS.get().getDefaultInstance(), entry.getValue().getDefaultChromosome()));
                    output.accept(GeneticHelper.setBothGenome(ItemsRegistration.QUEEN.get().getDefaultInstance(), entry.getValue().getDefaultChromosome()));
                }
                for (ResourceLocation id : Minecraft.getInstance().getConnection().registryAccess().registry(CombRegistry.COMB_REGISTRY_KEY).get().keySet()) {
                    output.accept(CombItem.setComb(ItemsRegistration.COMB.get().getDefaultInstance(), id));
                }
                output.accept(ItemsRegistration.BEE_NEST.get());
                output.accept(ItemsRegistration.APIARY.get());
                output.accept(ItemsRegistration.CENTRIFUGE.get());
                output.accept(ItemsRegistration.SCOOP.get());
                output.accept(ItemsRegistration.METER.get());
            }).build());

    public ComplicatedBees(IEventBus modEventBus)
    {
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(ColorHandlers::registerItemColorHandlers);
        modEventBus.addListener(this::registerRegistries);
        modEventBus.addListener(this::registerDatapackRegistries);
        modEventBus.addListener(this::registerCapabilities);
        modEventBus.addListener(DataGenerators::generate);
        NeoForge.EVENT_BUS.addListener(this::serverStarted);

        ItemsRegistration.ITEMS.register(modEventBus);
        BlocksRegistration.BLOCKS.register(modEventBus);
        BlockEntitiesRegistration.BLOCK_ENTITIES.register(modEventBus);
        MenuRegistration.MENU_TYPES.register(modEventBus);
        GeneRegistry.GENES.register(modEventBus);

        CREATIVE_MODE_TABS.register(modEventBus);
    }

    @SubscribeEvent
    public void registerDatapackRegistries(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(
                SpeciesRegistry.SPECIES_REGISTRY_KEY,
                ComplicatedBeesCodecs.SPECIES_CODEC,
                ComplicatedBeesCodecs.SPECIES_CODEC
        );

        event.dataPackRegistry(
                CombRegistry.COMB_REGISTRY_KEY,
                ComplicatedBeesCodecs.COMB_CODEC,
                ComplicatedBeesCodecs.COMB_CODEC
        );

        event.dataPackRegistry(
                MutationRegistry.MUTATION_REGISTRY_KEY,
                ComplicatedBeesCodecs.MUTATION_CODEC,
                ComplicatedBeesCodecs.MUTATION_CODEC
        );
    }

    @SubscribeEvent
    public void registerRegistries(NewRegistryEvent event) {
        event.register(GeneRegistry.GENE_REGISTRY);
    }

    @SubscribeEvent
    public void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BlockEntitiesRegistration.APIARY_ENTITY.get(), (o, direction) -> {
            if (direction == null) {
                return o.getItemHandler().get();
            }
            if (direction == Direction.DOWN) {
                return o.getOutputItemHandler().get();
            }
            return o.getBeeItemHandler().get();
        });
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BlockEntitiesRegistration.CENTRIFUGE_ENTITY.get(), (o, direction) -> {
            if (direction == null) {
                return o.getItemHandler().get();
            }
            if (direction == Direction.DOWN) {
                return o.getOutputItemHandler().get();
            }
            return o.getInputItemHandler().get();
        });
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        //setup code
    }

    @SubscribeEvent
    public void serverStarted(ServerStartedEvent event) {
        LOGGER.info("Registered {} species", ServerLifecycleHooks.getCurrentServer().registryAccess().registry(SpeciesRegistry.SPECIES_REGISTRY_KEY).get().size());
        LOGGER.info("Registered {} combs", ServerLifecycleHooks.getCurrentServer().registryAccess().registry(CombRegistry.COMB_REGISTRY_KEY).get().size());
        LOGGER.info("Registered {} mutations", ServerLifecycleHooks.getCurrentServer().registryAccess().registry(MutationRegistry.MUTATION_REGISTRY_KEY).get().size());
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            event.enqueueWork(() -> {
                MenuScreens.register(MenuRegistration.CENTRIFUGE_MENU.get(), CentrifugeScreen::new);
                MenuScreens.register(MenuRegistration.APIARY_MENU.get(), ApiaryScreen::new);
            });
        }
    }
}
