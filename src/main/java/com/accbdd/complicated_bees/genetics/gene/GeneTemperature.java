package com.accbdd.complicated_bees.genetics.gene;

import com.accbdd.complicated_bees.genetics.Genome;
import net.minecraft.nbt.*;
import net.minecraft.resources.ResourceLocation;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class GeneTemperature extends Gene<EnumTemperature> {
    public static final String TAG = "temperature";
    public static final ResourceLocation ID = new ResourceLocation(MODID, TAG);

    public GeneTemperature() {
        super(EnumTemperature.NORMAL);
    }

    public GeneTemperature(EnumTemperature temperature) {
        super(temperature);
    }

    @Override
    public GeneTemperature deserialize(CompoundTag tag) {
        return new GeneTemperature(EnumTemperature.getFromString(tag.getString(DATA)));
    }
}
