package com.accbdd.complicated_bees.genetics.gene;

import com.accbdd.complicated_bees.genetics.gene.enums.EnumTolerance;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTag;

public abstract class GeneTolerant<T extends Enum<T>> extends Gene<T> {
    public static final String TOLERANCE = "tolerance";

    private EnumTolerance tolerance;

    public GeneTolerant(T data, EnumTolerance tolerance, boolean dominant) {
        super(data, dominant);
        this.tolerance = tolerance;
    }

    public EnumTolerance getTolerance() {
        return tolerance;
    }

    public GeneTolerant<T> setTolerance(EnumTolerance tolerance) {
        this.tolerance = tolerance;
        return this;
    }

    public boolean withinTolerance(T condition) {
        if (condition == null) return false;
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
