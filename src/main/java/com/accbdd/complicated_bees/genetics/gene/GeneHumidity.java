package com.accbdd.complicated_bees.genetics.gene;

import com.accbdd.complicated_bees.genetics.gene.enums.EnumHumidity;
import com.accbdd.complicated_bees.genetics.gene.enums.EnumTolerance;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class GeneHumidity extends GeneTolerant<EnumHumidity> {
    public static final String TAG = "humidity";
    public static final ResourceLocation ID = new ResourceLocation(MODID, TAG);

    public GeneHumidity() {
        this(EnumHumidity.NORMAL, EnumTolerance.NONE, false);
    }

    public GeneHumidity(EnumHumidity enumHumidity, EnumTolerance enumTolerance, boolean dominant) {
        super(enumHumidity, enumTolerance, dominant);
    }

    @Override
    public GeneHumidity deserialize(CompoundTag tag) {
        return new GeneHumidity(EnumHumidity.getFromString(tag.getString(DATA)), EnumTolerance.getFromString(tag.getString(TOLERANCE)), tag.getBoolean(DOMINANT));
    }

    @Override
    public MutableComponent getTranslationKey() {
        return geneData.getTranslationKey();
    }
}
