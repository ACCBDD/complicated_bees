package com.accbdd.complicated_bees.genetics.gene;

import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTag;

public class Gene<T> implements IGene<T> {
    public static String DATA = "data";
    public static String DOMINANT = "dominant";

    private final boolean dominant;
    public T geneData;

    public Gene(T geneData) {
        this(geneData, true);
    }

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

    @Override
    public Gene<T> deserialize(CompoundTag tag) {
        return new Gene<T>((T) tag.getString(DATA), tag.getBoolean(DOMINANT));
    }
}
