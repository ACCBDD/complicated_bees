package com.accbdd.complicated_bees.registry;

import com.accbdd.complicated_bees.genetics.gene.enums.EnumHumidity;
import com.accbdd.complicated_bees.genetics.gene.enums.EnumTemperature;
import com.accbdd.complicated_bees.genetics.mutation.Mutation;
import com.accbdd.complicated_bees.genetics.mutation.condition.*;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryBuilder;

import java.util.function.Supplier;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class MutationRegistration {
    public static final ResourceKey<Registry<Mutation>> MUTATION_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(MODID, "mutation"));

    public static final ResourceKey<Registry<IMutationCondition>> MUTATION_CONDITION_KEY = ResourceKey.createRegistryKey(new ResourceLocation(MODID, "mutation_condition"));
    public static final Registry<IMutationCondition> MUTATION_CONDITION_REGISTRY = new RegistryBuilder<>(MUTATION_CONDITION_KEY)
            .create();

    public static final DeferredRegister<IMutationCondition> MUTATION_CONDITIONS = DeferredRegister.create(MUTATION_CONDITION_REGISTRY, MODID);

    public static final Supplier<IMutationCondition> BLOCK_UNDER = MUTATION_CONDITIONS.register(BlockUnderCondition.ID, () -> new BlockUnderCondition(Blocks.AIR));
    public static final Supplier<IMutationCondition> ECSTATIC = MUTATION_CONDITIONS.register(EcstaticCondition.ID, EcstaticCondition::new);
    public static final Supplier<IMutationCondition> DAYTIME = MUTATION_CONDITIONS.register(DaytimeCondition.ID, DaytimeCondition::new);
    public static final Supplier<IMutationCondition> NIGHTTIME = MUTATION_CONDITIONS.register(NighttimeCondition.ID, NighttimeCondition::new);
    public static final Supplier<IMutationCondition> DOWNFALL = MUTATION_CONDITIONS.register(DownfallCondition.ID, DownfallCondition::new);
    public static final Supplier<IMutationCondition> HUMIDITY = MUTATION_CONDITIONS.register(HumidityCondition.ID, () -> new HumidityCondition(EnumHumidity.NORMAL, EnumHumidity.NORMAL));
    public static final Supplier<IMutationCondition> TEMPERATURE = MUTATION_CONDITIONS.register(TemperatureCondition.ID, () -> new TemperatureCondition(EnumTemperature.NORMAL, EnumTemperature.NORMAL));
}
