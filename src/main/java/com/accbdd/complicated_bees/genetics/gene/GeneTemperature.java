package com.accbdd.complicated_bees.genetics.gene;

import com.accbdd.complicated_bees.ComplicatedBees;
import com.accbdd.complicated_bees.genetics.gene.enums.EnumTemperature;
import com.accbdd.complicated_bees.genetics.gene.enums.EnumTolerance;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class GeneTemperature extends GeneTolerant<EnumTemperature> {
    public static final String TAG = "temperature";
    public static final ResourceLocation ID = new ResourceLocation(MODID, TAG);

    public GeneTemperature() {
        super(EnumTemperature.NORMAL, EnumTolerance.NONE);
    }

    public GeneTemperature(EnumTemperature temperature, EnumTolerance tolerance) {
        super(temperature, tolerance);
    }

    @Override
    public GeneTolerant<EnumTemperature> deserialize(CompoundTag tag) {
        return new GeneTemperature(EnumTemperature.getFromString(tag.getString(DATA)), EnumTolerance.getFromString(tag.getString(TOLERANCE)));
    }
}
