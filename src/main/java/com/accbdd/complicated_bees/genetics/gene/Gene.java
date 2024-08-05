package com.accbdd.complicated_bees.genetics.gene;

import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTag;

public abstract class Gene<T> implements IGene<T> {
    public static final String DATA = "data";
    public static final String DOMINANT = "dominant";

    private final boolean dominant;
    public T geneData;

    public Gene(T geneData, boolean dominant) {
        this.geneData = geneData;
        this.dominant = dominant;
    }

    @Override
    public T get() {
        return geneData;
    }

    @Override
    public Gene<T> set(T value) {
        this.geneData = value;
        return this;
    }

    @Override
    public boolean isDominant() {
        return this.dominant;
    }

    @Override
    public CompoundTag serialize() {
        CompoundTag tag = new CompoundTag();
        tag.put(DATA, StringTag.valueOf(geneData.toString()));
        tag.put(DOMINANT, ByteTag.valueOf(dominant));
        return tag;
    }
}
