package com.accbdd.complicated_bees.genetics.gene;

import com.accbdd.complicated_bees.genetics.Genome;
import com.accbdd.complicated_bees.genetics.Species;
import com.accbdd.complicated_bees.registry.SpeciesRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class GeneLifespan extends Gene {
    public static final String TAG = "lifespan";

    private int lifespan;

    public GeneLifespan() {
        this(10);
    }

    public GeneLifespan(int lifespan) {
        this.lifespan = lifespan;
    }

    public static GeneLifespan get(Genome genome) {
        return genome.getGene(getId()) == null ? new GeneLifespan() : (GeneLifespan) genome.getGene(getId());
    }

    public static ResourceLocation getId() {
        return new ResourceLocation(MODID, TAG);
    }

    @Override
    public IntTag serialize() {
        return IntTag.valueOf(this.lifespan);
    }

    @Override
    public Gene deserialize(Tag tag) {
        return new GeneLifespan(((NumericTag)tag).getAsInt());
    }

    public int getLifespan() {
        return this.lifespan;
    }

    public void setLifespan(int lifespan) {
        this.lifespan = lifespan;
    }
}
