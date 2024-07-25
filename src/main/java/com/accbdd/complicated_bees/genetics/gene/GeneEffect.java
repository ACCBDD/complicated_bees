package com.accbdd.complicated_bees.genetics.gene;

import com.accbdd.complicated_bees.genetics.effect.IBeeEffect;
import com.accbdd.complicated_bees.registry.BeeEffectRegistration;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceLocation;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class GeneEffect extends Gene<IBeeEffect> {
    public static final String TAG = "effect";
    public static final ResourceLocation ID = new ResourceLocation(MODID, TAG);

    public GeneEffect() {
        super(null, true);
    }

    public GeneEffect(IBeeEffect effect, boolean dominant) {
        super(effect, dominant);
    }

    @Override
    public CompoundTag serialize() {
        CompoundTag tag = new CompoundTag();
        ResourceLocation effectKey = BeeEffectRegistration.BEE_EFFECT_REGISTRY.getKey(get());
        tag.put(DATA, StringTag.valueOf(effectKey == null ? "INVALID" : effectKey.toString()));
        tag.put(DOMINANT, ByteTag.valueOf(isDominant()));
        return tag;
    }

    @Override
    public GeneEffect deserialize(CompoundTag tag) {
        String effectKeyString = tag.getString(DATA);
        return new GeneEffect(effectKeyString.equals("INVALID") ? null : BeeEffectRegistration.BEE_EFFECT_REGISTRY.get(ResourceLocation.tryParse(effectKeyString)), tag.getBoolean(DOMINANT));
    }
}
