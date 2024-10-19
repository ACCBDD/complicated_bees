package com.accbdd.complicated_bees;

import com.accbdd.complicated_bees.block.BeeNestBlock;
import com.accbdd.complicated_bees.client.BeeModel;
import com.accbdd.complicated_bees.client.ColorHandlers;
import com.accbdd.complicated_bees.config.Config;
import com.accbdd.complicated_bees.datagen.DataGenerators;
import com.accbdd.complicated_bees.datagen.condition.ItemEnabledCondition;
import com.accbdd.complicated_bees.genetics.Comb;
import com.accbdd.complicated_bees.genetics.GeneticHelper;
import com.accbdd.complicated_bees.genetics.Species;
import com.accbdd.complicated_bees.genetics.effect.IBeeEffect;
import com.accbdd.complicated_bees.genetics.gene.IGene;
import com.accbdd.complicated_bees.genetics.mutation.Mutation;
import com.accbdd.complicated_bees.genetics.mutation.condition.IMutationCondition;
import com.accbdd.complicated_bees.item.CombItem;
import com.accbdd.complicated_bees.particle.BeeParticle;
import com.accbdd.complicated_bees.registry.*;
import com.accbdd.complicated_bees.screen.AnalyzerScreen;
import com.accbdd.complicated_bees.screen.ApiaryScreen;
import com.accbdd.complicated_bees.screen.CentrifugeScreen;
import com.accbdd.complicated_bees.screen.GeneratorScreen;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.registries.*;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.slf4j.Logger;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

@Mod(ComplicatedBees.MODID)
public class ComplicatedBees {
    public static final String MODID = "complicated_bees";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static Supplier<IForgeRegistry<IGene<?>>> GENE_REGISTRY;
    public static Supplier<IForgeRegistry<IBeeEffect>> BEE_EFFECT_REGISTRY;
    public static Supplier<IForgeRegistry<IMutationCondition>> MUTATION_CONDITION_REGISTRY;

    public static final RegistryObject<CreativeModeTab> BEES_TAB = CREATIVE_MODE_TABS.register("complicated_bees", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.complicated_bees"))
            .icon(() -> ItemsRegistration.DRONE.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ItemsRegistration.WAX_BLOCK.get());
                output.accept(ItemsRegistration.WAX_BLOCK_STAIRS.get());
                output.accept(ItemsRegistration.WAX_BLOCK_SLAB.get());
                output.accept(ItemsRegistration.WAX_BLOCK_WALL.get());
                output.accept(ItemsRegistration.SMOOTH_WAX.get());
                output.accept(ItemsRegistration.SMOOTH_WAX_STAIRS.get());
                output.accept(ItemsRegistration.SMOOTH_WAX_SLAB.get());
                output.accept(ItemsRegistration.SMOOTH_WAX_WALL.get());
                output.accept(ItemsRegistration.WAX_BRICKS.get());
                output.accept(ItemsRegistration.WAX_BRICK_STAIRS.get());
                output.accept(ItemsRegistration.WAX_BRICK_SLAB.get());
                output.accept(ItemsRegistration.WAX_BRICK_WALL.get());
                output.accept(ItemsRegistration.CHISELED_WAX.get());
                output.accept(ItemsRegistration.HONEYED_PLANKS.get());
                output.accept(ItemsRegistration.HONEYED_STAIRS.get());
                output.accept(ItemsRegistration.HONEYED_SLAB.get());
                output.accept(ItemsRegistration.HONEYED_FENCE.get());
                output.accept(ItemsRegistration.HONEYED_FENCE_GATE.get());
                output.accept(ItemsRegistration.HONEYED_BUTTON.get());
                output.accept(ItemsRegistration.HONEYED_PRESSURE_PLATE.get());
                output.accept(ItemsRegistration.HONEYED_DOOR.get());
                output.accept(ItemsRegistration.HONEYED_TRAPDOOR.get());
                output.accept(ItemsRegistration.APIARY.get());
                output.accept(ItemsRegistration.CENTRIFUGE.get());
                output.accept(ItemsRegistration.HONEY_DROPLET.get());
                output.accept(ItemsRegistration.BEESWAX.get());
                output.accept(ItemsRegistration.PROPOLIS.get());
                output.accept(ItemsRegistration.ROYAL_JELLY.get());
                output.accept(ItemsRegistration.POLLEN.get());
                output.accept(ItemsRegistration.SCOOP.get());
                output.accept(ItemsRegistration.METER.get());
                output.accept(ItemsRegistration.ANALYZER.get());
                output.accept(ItemsRegistration.GENERATOR.get());
                output.accept(ItemsRegistration.FRAME.get());
                output.accept(ItemsRegistration.WAXED_FRAME.get());
                output.accept(ItemsRegistration.HONEYED_FRAME.get());
                output.accept(ItemsRegistration.TWISTING_FRAME.get());
                output.accept(ItemsRegistration.SOOTHING_FRAME.get());
                output.accept(ItemsRegistration.HOT_FRAME.get());
                output.accept(ItemsRegistration.COLD_FRAME.get());
                output.accept(ItemsRegistration.DRY_FRAME.get());
                output.accept(ItemsRegistration.WET_FRAME.get());
                output.accept(ItemsRegistration.DEADLY_FRAME.get());
                output.accept(ItemsRegistration.RESTRICTIVE_FRAME.get());
                output.accept(ItemsRegistration.PEARL_SHARD.get());
                output.accept(ItemsRegistration.WAXED_STICK.get());
                output.accept(ItemsRegistration.HONEYED_STICK.get());
                output.accept(ItemsRegistration.EXP_DROP.get());
                output.accept(ItemsRegistration.SILK_WISP.get());
                output.accept(ItemsRegistration.WOVEN_MESH.get());
                output.accept(ItemsRegistration.APIARIST_HELMET.get());
                output.accept(ItemsRegistration.APIARIST_CHESTPLATE.get());
                output.accept(ItemsRegistration.APIARIST_LEGGINGS.get());
                output.accept(ItemsRegistration.APIARIST_BOOTS.get());
                output.accept(ItemsRegistration.BEE_STAFF.get());
                output.accept(ItemsRegistration.HONEY_BREAD.get());
                output.accept(ItemsRegistration.HONEY_PORKCHOP.get());
                output.accept(ItemsRegistration.AMBROSIA.get());
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

    public ComplicatedBees() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::registerSerializers);
        modEventBus.addListener(this::registerRegistries);
        modEventBus.addListener(this::registerDatapackRegistries);
        modEventBus.addListener(DataGenerators::generate);

        if(FMLLoader.getDist().isClient()) {
            modEventBus.addListener(ColorHandlers::registerItemColorHandlers);
            modEventBus.addListener(ColorHandlers::registerBlockColorHandlers);
        }

        BlocksRegistration.BLOCKS.register(modEventBus);
        ItemsRegistration.ITEMS.register(modEventBus);
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
        EsotericRegistration.PARTICLE_TYPE.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

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
        GENE_REGISTRY = event.create(GeneRegistration.GENE_REGISTRY);
        BEE_EFFECT_REGISTRY = event.create(BeeEffectRegistration.BEE_EFFECT_REGISTRY);
        MUTATION_CONDITION_REGISTRY = event.create(MutationRegistration.MUTATION_CONDITION_REGISTRY);
    }

    @SubscribeEvent
    public void registerSerializers(RegisterEvent event) {
        event.register(ForgeRegistries.Keys.RECIPE_SERIALIZERS,
                helper -> CraftingHelper.register(ItemEnabledCondition.Serializer.INSTANCE));
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

            event.enqueueWork(() -> {
                MenuScreens.register(MenuRegistration.CENTRIFUGE_MENU.get(), CentrifugeScreen::new);
                MenuScreens.register(MenuRegistration.APIARY_MENU.get(), ApiaryScreen::new);
                MenuScreens.register(MenuRegistration.GENERATOR_MENU.get(), GeneratorScreen::new);
                MenuScreens.register(MenuRegistration.ANALYZER_MENU.get(), AnalyzerScreen::new);
            });
        }

        @SubscribeEvent
        public static void registerGeometryLoaders(ModelEvent.RegisterGeometryLoaders event) {
            event.register(BeeModel.Loader.ID.getPath(), BeeModel.Loader.INSTANCE);
        }

        @SubscribeEvent
        public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
            event.registerSpriteSet(EsotericRegistration.BEE_PARTICLE.get(),
                    BeeParticle.Provider::new);
        }
    }
}
