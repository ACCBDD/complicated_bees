package com.accbdd.complicated_bees.genetics.gene;

import com.accbdd.complicated_bees.ComplicatedBees;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.resources.ResourceLocation;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class GeneLifespan extends Gene<Integer> {
    public static final String TAG = "lifespan";
    public static final ResourceLocation ID = new ResourceLocation(MODID, TAG);

    public GeneLifespan() {
        this(10, true);
    }

    public GeneLifespan(int data, boolean dominant) {
        super(data, dominant);
    }

    @Override
    public CompoundTag serialize() {
        CompoundTag tag = new CompoundTag();
        tag.put(DATA, IntTag.valueOf(get()));
        tag.put(DOMINANT, ByteTag.valueOf(isDominant()));
        return tag;
    }

    @Override
    public Gene<Integer> deserialize(CompoundTag tag) {
        return new GeneLifespan(tag.getInt(DATA), tag.getBoolean(DOMINANT));
    }
}
