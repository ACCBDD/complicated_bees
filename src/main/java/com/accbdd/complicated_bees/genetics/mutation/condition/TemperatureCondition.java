package com.accbdd.complicated_bees.genetics.mutation.condition;

import com.accbdd.complicated_bees.genetics.gene.enums.EnumTemperature;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

public class TemperatureCondition extends MutationCondition {
    public static String ID = "temperature";
    EnumTemperature tempMin;
    EnumTemperature tempMax;

    public TemperatureCondition(EnumTemperature tempMin, EnumTemperature tempMax) {
        this.tempMin = tempMin;
        this.tempMax = tempMax;
    }

    @Override
    public boolean check(Level level, BlockPos pos) {
        EnumTemperature checkedTemp = EnumTemperature.getFromPosition(level, pos);
        return checkedTemp.ordinal() >= tempMin.ordinal() && checkedTemp.ordinal() <= tempMax.ordinal();
    }

    @Override
    public Component getDescription() {
        if (tempMax.equals(tempMin))
            return Component.translatable("gui.complicated_bees.mutations.temperature", tempMin.getTranslationKey());
        else
            return Component.translatable("gui.complicated_bees.mutations.temperature_range", tempMin.getTranslationKey(), tempMax.getTranslationKey());
    }

    @Override
    public CompoundTag serialize() {
        CompoundTag tag = new CompoundTag();
        tag.put("min", StringTag.valueOf(tempMin.toString()));
        tag.put("max", StringTag.valueOf(tempMax.toString()));
        return tag;
    }

    @Override
    public TemperatureCondition deserialize(CompoundTag tag) {
        return new TemperatureCondition(EnumTemperature.getFromString(tag.getString("min")), EnumTemperature.getFromString(tag.getString("max")));
    }
}
