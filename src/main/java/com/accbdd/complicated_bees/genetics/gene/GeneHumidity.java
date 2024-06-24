package com.accbdd.complicated_bees.genetics.gene;

import com.accbdd.complicated_bees.genetics.gene.enums.EnumHumidity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class GeneHumidity extends Gene<EnumHumidity> {
    public static final String TAG = "humidity";
    public static final ResourceLocation ID = new ResourceLocation(MODID, TAG);

    public GeneHumidity() {
        this(EnumHumidity.NORMAL);
    }

    public GeneHumidity(EnumHumidity humidity) {
        super(humidity);
    }

    @Override
    public GeneHumidity deserialize(CompoundTag tag) {
        return new GeneHumidity(EnumHumidity.getFromString(tag.getString(DATA)));
    }
}
