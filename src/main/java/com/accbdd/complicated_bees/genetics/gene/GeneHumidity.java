package com.accbdd.complicated_bees.genetics.gene;

import com.accbdd.complicated_bees.genetics.gene.enums.EnumHumidity;
import com.accbdd.complicated_bees.genetics.gene.enums.EnumTolerance;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class GeneHumidity extends GeneTolerant<EnumHumidity> {
    public static final String TAG = "humidity";
    public static final ResourceLocation ID = new ResourceLocation(MODID, TAG);

    public GeneHumidity() {
        this(EnumHumidity.NORMAL, EnumTolerance.NONE);
    }

    public GeneHumidity(EnumHumidity enumHumidity, EnumTolerance enumTolerance) {
        super(enumHumidity, enumTolerance);
    }

    @Override
    public GeneHumidity deserialize(CompoundTag tag) {
        return new GeneHumidity(EnumHumidity.getFromString(tag.getString(DATA)), EnumTolerance.getFromString(tag.getString(TOLERANCE)));
    }
}
