package com.accbdd.complicated_bees.genetics.gene;

import com.accbdd.complicated_bees.genetics.gene.enums.EnumLifespan;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceLocation;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class GeneLifespan extends Gene<EnumLifespan> {
    public static final String TAG = "lifespan";
    public static final ResourceLocation ID = new ResourceLocation(MODID, TAG);

    public GeneLifespan() {
        this(EnumLifespan.SHORTEST, true);
    }

    public GeneLifespan(EnumLifespan data, boolean dominant) {
        super(data, dominant);
    }

    @Override
    public CompoundTag serialize() {
        CompoundTag tag = new CompoundTag();
        tag.put(DATA, StringTag.valueOf(get().name));
        tag.put(DOMINANT, ByteTag.valueOf(isDominant()));
        return tag;
    }

    @Override
    public Gene<EnumLifespan> deserialize(CompoundTag tag) {
        return new GeneLifespan(EnumLifespan.getFromString(tag.getString(DATA)), tag.getBoolean(DOMINANT));
    }
}
