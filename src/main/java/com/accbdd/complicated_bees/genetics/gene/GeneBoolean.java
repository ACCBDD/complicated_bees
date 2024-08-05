package com.accbdd.complicated_bees.genetics.gene;

import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class GeneBoolean extends Gene<Boolean> {
    public GeneBoolean(boolean data, boolean dominant) {
        super(data, dominant);
    }

    @Override
    public CompoundTag serialize() {
        CompoundTag tag = new CompoundTag();
        tag.put(DATA, ByteTag.valueOf(get()));
        tag.put(DOMINANT, ByteTag.valueOf(isDominant()));
        return tag;
    }

    @Override
    public GeneBoolean deserialize(CompoundTag tag) {
        return new GeneBoolean(tag.getBoolean(DATA), tag.getBoolean(DOMINANT));
    }

    @Override
    public MutableComponent getTranslationKey() {
        return geneData ? Component.translatable("gui.complicated_bees.boolean.true") : Component.translatable("gui.complicated_bees.boolean.false");
    }
}
