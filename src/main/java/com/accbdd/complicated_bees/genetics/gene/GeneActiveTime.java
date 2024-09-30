package com.accbdd.complicated_bees.genetics.gene;

import com.accbdd.complicated_bees.genetics.gene.enums.EnumActiveTime;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.tuple.Pair;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class GeneActiveTime extends Gene<EnumActiveTime> {
    public static final String TAG = "active_time";
    public static final ResourceLocation ID = new ResourceLocation(MODID, TAG);

    public GeneActiveTime() {
        this(EnumActiveTime.DIURNAL, true);
    }

    public GeneActiveTime(EnumActiveTime enumActiveTime, boolean dominant) {
        super(enumActiveTime, dominant);
    }

    public boolean isSatisfied(Level level) {
        boolean satisfied = false;
        if (geneData.equals(EnumActiveTime.CATHEMERAL))
            return level.random.nextBoolean();
        for (Pair<Integer, Integer> range : get().activeTimes) {
            long dayTicks = level.getDayTime() % 24000L;
            satisfied = satisfied || (dayTicks >= range.getLeft() && dayTicks <= range.getRight());
        }
        return satisfied;
    }

    @Override
    public GeneActiveTime deserialize(CompoundTag tag) {
        return new GeneActiveTime(EnumActiveTime.getFromString(tag.getString(DATA)), tag.getBoolean(DOMINANT));
    }

    @Override
    public MutableComponent getTranslationKey() {
        return geneData.getTranslationKey();
    }
}
