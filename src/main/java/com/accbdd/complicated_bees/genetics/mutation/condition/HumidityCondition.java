package com.accbdd.complicated_bees.genetics.mutation.condition;

import com.accbdd.complicated_bees.genetics.gene.enums.EnumHumidity;
import com.accbdd.complicated_bees.genetics.gene.enums.EnumTemperature;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

public class HumidityCondition extends MutationCondition {
    public static String ID = "humidity";
    EnumHumidity humidityMin;
    EnumHumidity humidityMax;

    public HumidityCondition(EnumHumidity humidityMin, EnumHumidity humidityMax) {
        this.humidityMin = humidityMin;
        this.humidityMax = humidityMax;
    }

    @Override
    public boolean check(Level level, BlockPos pos) {
        EnumHumidity checkedHumidity = EnumHumidity.getFromPosition(level, pos);
        return checkedHumidity.ordinal() >= humidityMin.ordinal() && checkedHumidity.ordinal() <= humidityMax.ordinal();
    }

    @Override
    public Component getDescription() {
        if (humidityMax.equals(humidityMin))
            return Component.translatable("gui.complicated_bees.mutations.humidity", humidityMin.getTranslationKey());
        else
            return Component.translatable("gui.complicated_bees.mutations.humidity_range", humidityMin.getTranslationKey(), humidityMax.getTranslationKey());
    }

    @Override
    public CompoundTag serialize() {
        CompoundTag tag = new CompoundTag();
        tag.put("min", StringTag.valueOf(humidityMin.toString()));
        tag.put("max", StringTag.valueOf(humidityMax.toString()));
        return tag;
    }

    @Override
    public HumidityCondition deserialize(CompoundTag tag) {
        return new HumidityCondition(EnumHumidity.getFromString(tag.getString("min")), EnumHumidity.getFromString(tag.getString("max")));
    }
}
