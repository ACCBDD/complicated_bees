package com.accbdd.complicated_bees.genetics.gene;

import com.accbdd.complicated_bees.genetics.Genome;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class GeneHumidity extends Gene {
    public static final String TAG = "humidity";

    private EnumHumidity humidity;

    public GeneHumidity() {
        this(EnumHumidity.NORMAL);
    }

    public GeneHumidity(EnumHumidity humidity) {
        this.humidity = humidity;
    }

    public static GeneHumidity get(Genome genome) {
        return genome.getGene(getId()) == null ? new GeneHumidity() : (GeneHumidity) genome.getGene(getId());
    }

    public static ResourceLocation getId() {
        return new ResourceLocation(MODID, TAG);
    }

    @Override
    public StringTag serialize() {
        return StringTag.valueOf(this.humidity.name);
    }

    @Override
    public Gene deserialize(Tag tag) {
        return new GeneHumidity(EnumHumidity.getFromString(tag.getAsString()));
    }

    public EnumHumidity getHumidity() {
        return this.humidity;
    }

    public void setHumidity(EnumHumidity humidity) {
        this.humidity = humidity;
    }
}
