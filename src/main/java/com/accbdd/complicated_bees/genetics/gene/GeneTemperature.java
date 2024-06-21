package com.accbdd.complicated_bees.genetics.gene;

import com.accbdd.complicated_bees.genetics.Genome;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class GeneTemperature extends Gene {
    public static final String TAG = "temperature";

    private EnumTemperature temperature;

    public GeneTemperature() {
        this(EnumTemperature.NORMAL);
    }

    public GeneTemperature(EnumTemperature temperature) {
        this.temperature = temperature;
    }

    public static GeneTemperature get(Genome genome) {
        return genome.getGene(getId()) == null ? new GeneTemperature() : (GeneTemperature) genome.getGene(getId());
    }

    public static ResourceLocation getId() {
        return new ResourceLocation(MODID, TAG);
    }

    @Override
    public StringTag serialize() {
        return StringTag.valueOf(this.temperature.name);
    }

    @Override
    public Gene deserialize(Tag tag) {
        return new GeneTemperature(EnumTemperature.getFromString(tag.getAsString()));
    }

    public EnumTemperature getTemperature() {
        return this.temperature;
    }

    public void setTemperature(EnumTemperature temperature) {
        this.temperature = temperature;
    }
}
