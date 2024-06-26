package com.accbdd.complicated_bees.genetics.gene;

import com.accbdd.complicated_bees.genetics.gene.enums.EnumTolerance;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTag;

public abstract class GeneTolerant<T extends Enum<T>> extends Gene<T> {
    public static final String TOLERANCE = "tolerance";

    private final EnumTolerance tolerance;

    public GeneTolerant(T geneData) {
        this(geneData, EnumTolerance.NONE);
    }

    public GeneTolerant(T data, EnumTolerance tolerance) {
        super(data);
        this.tolerance = tolerance;
    }

    public EnumTolerance getTolerance() {
        return tolerance;
    }

    public boolean withinTolerance(T condition) {
        int ordinal = condition.ordinal();
        return (ordinal >= (get().ordinal() - getTolerance().down)) && (ordinal <= (get().ordinal() + getTolerance().up));
    }

    @Override
    public CompoundTag serialize() {
        CompoundTag tag = new CompoundTag();
        tag.put(DATA, StringTag.valueOf(get().toString()));
        tag.put(DOMINANT, ByteTag.valueOf(this.isDominant()));
        tag.put(TOLERANCE, StringTag.valueOf(getTolerance().toString()));
        return tag;
    }

    @Override
    public abstract GeneTolerant<T> deserialize(CompoundTag tag);
}