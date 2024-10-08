package com.accbdd.complicated_bees;

import com.accbdd.complicated_bees.block.BeeNestBlock;
import com.accbdd.complicated_bees.client.BeeModel;
import com.accbdd.complicated_bees.client.ColorHandlers;
import com.accbdd.complicated_bees.config.Config;
import com.accbdd.complicated_bees.datagen.DataGenerators;
import com.accbdd.complicated_bees.genetics.Comb;
import com.accbdd.complicated_bees.genetics.GeneticHelper;
import com.accbdd.complicated_bees.genetics.Species;
import com.accbdd.complicated_bees.genetics.mutation.Mutation;
import com.accbdd.complicated_bees.item.CombItem;
import com.accbdd.complicated_bees.particle.BeeParticle;
import com.accbdd.complicated_bees.registry.*;
import com.accbdd.complicated_bees.screen.AnalyzerScreen;
import com.accbdd.complicated_bees.screen.ApiaryScreen;
import com.accbdd.complicated_bees.screen.CentrifugeScreen;
import com.accbdd.complicated_bees.screen.GeneratorScreen;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.slf4j.Logger;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Mod(ComplicatedBees.MODID)
public class ComplicatedBees {
    public static final String MODID = "complicated_bees";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);


    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BEES_TAB = CREATIVE_MODE_TABS.register("complicated_bees", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.complicated_bees"))
            .icon(() -> ItemsRegistration.DRONE.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ItemsRegistration.WAX_BLOCK);
                output.accept(ItemsRegistration.WAX_BLOCK_STAIRS);
                output.accept(ItemsRegistration.WAX_BLOCK_SLAB);
                output.accept(ItemsRegistration.WAX_BLOCK_WALL);
                output.accept(ItemsRegistration.SMOOTH_WAX);
                output.accept(ItemsRegistration.SMOOTH_WAX_STAIRS);
                output.accept(ItemsRegistration.SMOOTH_WAX_SLAB);
                output.accept(ItemsRegistration.SMOOTH_WAX_WALL);
                output.accept(ItemsRegistration.WAX_BRICKS);
                output.accept(ItemsRegistration.WAX_BRICK_STAIRS);
                output.accept(ItemsRegistration.WAX_BRICK_SLAB);
                output.accept(ItemsRegistration.WAX_BRICK_WALL);
                output.accept(ItemsRegistration.CHISELED_WAX);
                output.accept(ItemsRegistration.HONEYED_PLANKS);
                output.accept(ItemsRegistration.HONEYED_STAIRS);
                output.accept(ItemsRegistration.HONEYED_SLAB);
                output.accept(ItemsRegistration.HONEYED_FENCE);
                output.accept(ItemsRegistration.HONEYED_FENCE_GATE);
                output.accept(ItemsRegistration.HONEYED_BUTTON);
                output.accept(ItemsRegistration.HONEYED_PRESSURE_PLATE);
                output.accept(ItemsRegistration.HONEYED_DOOR);
                output.accept(ItemsRegistration.HONEYED_TRAPDOOR);
                output.accept(ItemsRegistration.APIARY);
                output.accept(ItemsRegistration.CENTRIFUGE);
                output.accept(ItemsRegistration.HONEY_DROPLET);
                output.accept(ItemsRegistration.BEESWAX);
                output.accept(ItemsRegistration.PROPOLIS);
                output.accept(ItemsRegistration.ROYAL_JELLY);
                output.accept(ItemsRegistration.POLLEN);
                output.accept(ItemsRegistration.SCOOP);
                output.accept(ItemsRegistration.METER);
                output.accept(ItemsRegistration.ANALYZER);
                output.accept(ItemsRegistration.GENERATOR);
                output.accept(ItemsRegistration.FRAME);
                output.accept(ItemsRegistration.WAXED_FRAME);
                output.accept(ItemsRegistration.HONEYED_FRAME);
                output.accept(ItemsRegistration.TWISTING_FRAME);
                output.accept(ItemsRegistration.SOOTHING_FRAME);
                output.accept(ItemsRegistration.HOT_FRAME);
                output.accept(ItemsRegistration.COLD_FRAME);
                output.accept(ItemsRegistration.DRY_FRAME);
                output.accept(ItemsRegistration.WET_FRAME);
                output.accept(ItemsRegistration.DEADLY_FRAME);
                output.accept(ItemsRegistration.RESTRICTIVE_FRAME);
                output.accept(ItemsRegistration.PEARL_SHARD);
                output.accept(ItemsRegistration.WAXED_STICK);
                output.accept(ItemsRegistration.HONEYED_STICK);
                output.accept(ItemsRegistration.EXP_DROP);
                output.accept(ItemsRegistration.SILK_WISP);
                output.accept(ItemsRegistration.WOVEN_MESH);
                output.accept(ItemsRegistration.APIARIST_HELMET);
                output.accept(ItemsRegistration.APIARIST_CHESTPLATE);
                output.accept(ItemsRegistration.APIARIST_LEGGINGS);
                output.accept(ItemsRegistration.APIARIST_BOOTS);
                output.accept(ItemsRegistration.BEE_STAFF);
                output.accept(ItemsRegistration.HONEY_BREAD);
                output.accept(ItemsRegistration.HONEY_PORKCHOP);
                output.accept(ItemsRegistration.AMBROSIA);
                Set<Map.Entry<ResourceKey<Species>, Species>> speciesSet = Objects.requireNonNull(Minecraft.getInstance().getConnection()).registryAccess().registry(SpeciesRegistration.SPECIES_REGISTRY_KEY).get().entrySet();
                for (Map.Entry<ResourceKey<Species>, Species> entry : speciesSet) {
                    output.accept(GeneticHelper.setBothGenome(ItemsRegistration.DRONE.get().getDefaultInstance(), entry.getValue().getDefaultChromosome()));
                    output.accept(GeneticHelper.setBothGenome(ItemsRegistration.PRINCESS.get().getDefaultInstance(), entry.getValue().getDefaultChromosome()));
                    output.accept(GeneticHelper.setBothGenome(ItemsRegistration.QUEEN.get().getDefaultInstance(), entry.getValue().getDefaultChromosome()));
                }
                for (ResourceLocation id : Minecraft.getInstance().getConnection().registryAccess().registry(CombRegistration.COMB_REGISTRY_KEY).get().keySet()) {
                    output.accept(CombItem.setComb(ItemsRegistration.COMB.get().getDefaultInstance(), id));
                }
                for (Map.Entry<ResourceKey<Species>, Species> entry : speciesSet) {
                    output.accept(BeeNestBlock.stackNest(ItemsRegistration.BEE_NEST.get().getDefaultInstance(), entry.getValue()));
                }
            }).build());

    public ComplicatedBees(IEventBus modEventBus) {
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(ColorHandlers::registerItemColorHandlers);
        modEventBus.addListener(ColorHandlers::registerBlockColorHandlers);
        modEventBus.addListener(this::registerRegistries);
        modEventBus.addListener(this::registerDatapackRegistries);
        modEventBus.addListener(this::registerCapabilities);
        modEventBus.addListener(DataGenerators::generate);
        NeoForge.EVENT_BUS.addListener(this::serverStarted);

        ItemsRegistration.ITEMS.register(modEventBus);
        BlocksRegistration.BLOCKS.register(modEventBus);
        BlockEntitiesRegistration.BLOCK_ENTITIES.register(modEventBus);
        MenuRegistration.MENU_TYPES.register(modEventBus);
        GeneRegistration.GENES.register(modEventBus);
        BeeEffectRegistration.EFFECTS.register(modEventBus);
        MutationRegistration.MUTATION_CONDITIONS.register(modEventBus);
        EntitiesRegistration.ENTITY_TYPE.register(modEventBus);
        EsotericRegistration.LOOT_ITEM_FUNCTION_REGISTER.register(modEventBus);
        EsotericRegistration.TREE_DECORATOR_REGISTER.register(modEventBus);
        EsotericRegistration.FEATURE_REGISTER.register(modEventBus);
        EsotericRegistration.RECIPE_TYPE_REGISTER.register(modEventBus);
        EsotericRegistration.RECIPE_SERIALIZER_REGISTER.register(modEventBus);
        EsotericRegistration.CONDITION_SERIALIZERS.register(modEventBus);
        EsotericRegistration.PARTICLE_TYPE.register(modEventBus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.CONFIG_SPEC);

        CREATIVE_MODE_TABS.register(modEventBus);
    }

    @SubscribeEvent
    public void registerDatapackRegistries(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(
                SpeciesRegistration.SPECIES_REGISTRY_KEY,
                Species.SPECIES_CODEC,
                Species.SPECIES_CODEC
        );

        event.dataPackRegistry(
                CombRegistration.COMB_REGISTRY_KEY,
                Comb.CODEC,
                Comb.CODEC
        );

        event.dataPackRegistry(
                MutationRegistration.MUTATION_REGISTRY_KEY,
                Mutation.MUTATION_CODEC,
                Mutation.MUTATION_CODEC
        );

        event.dataPackRegistry(
                FlowerRegistration.FLOWER_REGISTRY_KEY,
                FlowerRegistration.CODEC,
                FlowerRegistration.CODEC
        );
    }

    @SubscribeEvent
    public void registerRegistries(NewRegistryEvent event) {
        event.register(GeneRegistration.GENE_REGISTRY);
        event.register(BeeEffectRegistration.BEE_EFFECT_REGISTRY);
        event.register(MutationRegistration.MUTATION_CONDITION_REGISTRY);
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
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BlockEntitiesRegistration.GENERATOR_BLOCK_ENTITY.get(), (be, dir) -> be.getItemHandler());
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, BlockEntitiesRegistration.GENERATOR_BLOCK_ENTITY.get(), (be, dir) -> be.getEnergyHandler());
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, BlockEntitiesRegistration.CENTRIFUGE_ENTITY.get(), (be, dir) -> be.getEnergyHandler());
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        //setup code
    }

    @SubscribeEvent
    public void serverStarted(ServerStartedEvent event) {
        LOGGER.info("Registered {} species", ServerLifecycleHooks.getCurrentServer().registryAccess().registry(SpeciesRegistration.SPECIES_REGISTRY_KEY).get().size());
        LOGGER.info("Registered {} combs", ServerLifecycleHooks.getCurrentServer().registryAccess().registry(CombRegistration.COMB_REGISTRY_KEY).get().size());
        LOGGER.info("Registered {} mutations", ServerLifecycleHooks.getCurrentServer().registryAccess().registry(MutationRegistration.MUTATION_REGISTRY_KEY).get().size());
        LOGGER.info("Registered {} flowers", ServerLifecycleHooks.getCurrentServer().registryAccess().registry(FlowerRegistration.FLOWER_REGISTRY_KEY).get().size());
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(EntitiesRegistration.BEE_STAFF_MOUNT.get(), (context) -> new ThrownItemRenderer<>(context, 1.0f, true));
        }

        @SubscribeEvent
        public static void registerMenus(RegisterMenuScreensEvent event) {
            event.register(MenuRegistration.CENTRIFUGE_MENU.get(), CentrifugeScreen::new);
            event.register(MenuRegistration.APIARY_MENU.get(), ApiaryScreen::new);
            event.register(MenuRegistration.GENERATOR_MENU.get(), GeneratorScreen::new);
            event.register(MenuRegistration.ANALYZER_MENU.get(), AnalyzerScreen::new);
        }

        @SubscribeEvent
        public static void registerGeometryLoaders(ModelEvent.RegisterGeometryLoaders event) {
            event.register(BeeModel.Loader.ID, BeeModel.Loader.INSTANCE);
        }

        @SubscribeEvent
        public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
            event.registerSpriteSet(EsotericRegistration.BEE_PARTICLE.get(),
                    BeeParticle.Provider::new);
        }
    }
}
