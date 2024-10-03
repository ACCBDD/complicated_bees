package com.accbdd.complicated_bees.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class Config {
    public static ForgeConfigSpec CONFIG_SPEC;
    public static Config CONFIG;

    public final ForgeConfigSpec.ConfigValue<Integer> productionCycleLength, enviroCycleLength, centrifugeEnergy, generatorEnergy;
    public final ForgeConfigSpec.ConfigValue<Boolean> frame, waxedFrame, honeyFrame, twistingFrame, soothingFrame, coldFrame, hotFrame, dryFrame, wetFrame, deadlyFrame, restrictiveFrame;
    public final ForgeConfigSpec.ConfigValue<Boolean> honeyBread, honeyPorkchop, ambrosia;
    public final ForgeConfigSpec.ConfigValue<Boolean> beeStaff;

    Config(ForgeConfigSpec.Builder builder) {
        builder.push("cycle_length");
        productionCycleLength = builder.comment("How long (in ticks) one bee cycle should take.").define("productionCycleLength", 200);
        enviroCycleLength = builder.comment("How long (in ticks) an apiary should wait between re-scanning the environment for appropriate conditions.").define("enviroCycleLength", 200);
        builder.pop();
        builder.push("rf");
        centrifugeEnergy = builder.comment("How much rf/tick a centrifuge should use while processing a recipe.").define("centrifugeEnergy", 20);
        generatorEnergy = builder.comment("How much rf/tick a centrifuge should produce while burning a fuel.").define("generatorEnergy", 20);
        builder.pop();
        builder.push("items");
        builder.push("frames");
        frame = builder.comment("Enable the basic frame").define("frameEnabled", true);
        waxedFrame = builder.comment("Enable the waxed frame").define("waxedFrameEnabled", true);
        honeyFrame = builder.comment("Enable the honeyed frame").define("honeyFrameEnabled", true);
        twistingFrame = builder.comment("Enable the twisting frame").define("twistingFrameEnabled", true);
        soothingFrame = builder.comment("Enable the soothing frame").define("soothingFrameEnabled", true);
        coldFrame = builder.comment("Enable the cold frame").define("coldFrameEnabled", true);
        hotFrame = builder.comment("Enable the hot frame").define("hotFrameEnabled", true);
        dryFrame = builder.comment("Enable the dry frame").define("dryFrameEnabled", true);
        wetFrame = builder.comment("Enable the wet frame").define("wetFrameEnabled", true);
        deadlyFrame = builder.comment("Enable the deadly frame").define("deadlyFrameEnabled", true);
        restrictiveFrame = builder.comment("Enable the restrictive frame").define("restrictiveFrameEnabled", true);
        builder.pop();
        builder.push("foods");
        honeyBread = builder.comment("Enable honey bread").define("honeyBreadEnabled", true);
        honeyPorkchop = builder.comment("Enable honey porkchop").define("honeyPorkchopEnabled", true);
        ambrosia = builder.comment("Enable ambrosia").define("ambrosiaEnabled", true);
        builder.pop();
        builder.push("misc");
        beeStaff = builder.comment("Enable bee staff").define("staffEnabled", true);
        builder.pop(2);

        //todo: add config option for inheritance? i.e. which genes are tied to species
    }

    static {
        Pair<Config, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(Config::new);

        CONFIG_SPEC = pair.getRight();
        CONFIG = pair.getLeft();
    }
}
