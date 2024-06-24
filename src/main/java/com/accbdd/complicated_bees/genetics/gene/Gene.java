package com.accbdd.complicated_bees.genetics.gene;

import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTag;

import java.util.function.Supplier;

public class Gene<T> implements IGene<T> {
    public static String DATA = "data";
    public static String DOMINANT = "dominant";

    private final boolean dominant;
    final T geneData;

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
