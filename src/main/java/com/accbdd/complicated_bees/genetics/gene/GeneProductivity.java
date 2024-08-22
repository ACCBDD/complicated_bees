package com.accbdd.complicated_bees.genetics.gene;

import com.accbdd.complicated_bees.genetics.gene.enums.EnumProductivity;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class GeneProductivity extends Gene<EnumProductivity> {
    public static final String TAG = "productivity";
    public static final ResourceLocation ID = new ResourceLocation(MODID, TAG);

    public GeneProductivity() {
        this(EnumProductivity.SLOWEST, true);
    }

    public GeneProductivity(EnumProductivity data, boolean dominant) {
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
    public GeneProductivity deserialize(CompoundTag tag) {
        return new GeneProductivity(EnumProductivity.getFromString(tag.getString(DATA)), tag.getBoolean(DOMINANT));
    }

    @Override
    public MutableComponent getTranslationKey() {
        return geneData.getTranslationKey();
    }
}
