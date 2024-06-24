package com.accbdd.complicated_bees.genetics.gene;

import com.accbdd.complicated_bees.genetics.gene.enums.EnumHumidity;
import com.accbdd.complicated_bees.genetics.gene.enums.EnumTolerance;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class GeneHumidity extends Gene<EnumHumidity> {
    public static final String TOLERANCE = "tolerance";
    public static final String TAG = "humidity";
    public static final ResourceLocation ID = new ResourceLocation(MODID, TAG);

    private final EnumTolerance tolerance;

    public GeneHumidity() {
        this(EnumHumidity.NORMAL, EnumTolerance.BOTH_1);
    }

    public GeneHumidity(EnumHumidity humidity, EnumTolerance tolerance) {
        super(humidity);
        this.tolerance = tolerance;
    }

    @Override
    public CompoundTag serialize() {
        CompoundTag tag = new CompoundTag();
        tag.put(DATA, StringTag.valueOf(geneData.toString()));
        tag.put(DOMINANT, ByteTag.valueOf(this.isDominant()));
        tag.put(TOLERANCE, StringTag.valueOf(tolerance.toString()));
        return tag;
    }

    @Override
    public GeneHumidity deserialize(CompoundTag tag) {
        return new GeneHumidity(EnumHumidity.getFromString(tag.getString(DATA)), EnumTolerance.getFromString(tag.getString(TOLERANCE)));
    }

    public EnumTolerance getTolerance() {
        return tolerance;
    }

    public boolean withinTolerance(EnumHumidity humidity) {
        int ordinal = humidity.ordinal();
        return (ordinal >= (get().ordinal() - getTolerance().down)) && (ordinal <= (get().ordinal() + getTolerance().up));
    }
}
