package com.accbdd.complicated_bees.genetics.gene;

import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class GeneFlower extends Gene<ResourceLocation> {
    public static final String TAG = "flower";
    public static final ResourceLocation ID = new ResourceLocation(MODID, TAG);

    public GeneFlower() {
        super(new ResourceLocation(MODID, "invalid"), true);
    }

    public GeneFlower(ResourceLocation flower, boolean dominant) {
        super(flower, dominant);
    }

    @Override
    public CompoundTag serialize() {
        CompoundTag tag = new CompoundTag();
        tag.put(DATA, StringTag.valueOf(get().toString()));
        tag.put(DOMINANT, ByteTag.valueOf(isDominant()));
        return tag;
    }

    @Override
    public GeneFlower deserialize(CompoundTag tag) {
        return new GeneFlower(ResourceLocation.tryParse(tag.getString(DATA)), tag.getBoolean(DOMINANT));
    }

    @Override
    public MutableComponent getTranslationKey() {
        return Component.translatable("flower.complicated_bees." + geneData.toString());
    }
}
