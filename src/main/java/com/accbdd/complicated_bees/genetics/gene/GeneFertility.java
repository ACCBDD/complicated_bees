package com.accbdd.complicated_bees.genetics.gene;

import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class GeneFertility extends Gene<Integer> {
    public static final String TAG = "fertility";
    public static final ResourceLocation ID = new ResourceLocation(MODID, TAG);

    public GeneFertility() {
        this(2, true);
    }

    public GeneFertility(int data, boolean dominant) {
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
        return new GeneFertility(tag.getInt(DATA), tag.getBoolean(DOMINANT));
    }

    @Override
    public MutableComponent getTranslationKey() {
        return Component.literal(geneData.toString());
    }
}
