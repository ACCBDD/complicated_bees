package com.accbdd.complicated_bees.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class Config {
    public static ModConfigSpec CONFIG_SPEC;
    public static Config CONFIG;

    public final ModConfigSpec.ConfigValue<Integer> productionCycleLength, enviroCycleLength, centrifugeEnergy, generatorEnergy;

    Config(ModConfigSpec.Builder builder) {
        productionCycleLength = builder.comment("How long (in ticks) one bee cycle should take.").define("productionCycleLength", 200);
        enviroCycleLength = builder.comment("How long (in ticks) an apiary should wait between re-scanning the environment for appropriate conditions.").define("enviroCycleLength", 200);
        centrifugeEnergy = builder.comment("How much rf/tick a centrifuge should use while processing a recipe.").define("centrifugeEnergy", 20);
        generatorEnergy = builder.comment("How much rf/tick a centrifuge should produce while burning a fuel.").define("generatorEnergy", 20);
    }

    static {
        Pair<Config, ModConfigSpec> pair = new ModConfigSpec.Builder().configure(Config::new);

        CONFIG_SPEC = pair.getRight();
        CONFIG = pair.getLeft();
    }
}
