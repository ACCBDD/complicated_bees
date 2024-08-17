package com.accbdd.complicated_bees.genetics;

import com.accbdd.complicated_bees.item.CombItem;
import com.accbdd.complicated_bees.registry.CombRegistration;
import com.accbdd.complicated_bees.registry.ItemsRegistration;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;
import static com.accbdd.complicated_bees.util.ComplicatedBeesCodecs.HEX_STRING_CODEC;

public class Comb {
    public static final Codec<Comb> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    HEX_STRING_CODEC.fieldOf("outer_color").forGetter(Comb::getOuterColor),
                    HEX_STRING_CODEC.fieldOf("inner_color").forGetter(Comb::getInnerColor)
            ).apply(instance, Comb::new)
    );

    private final int outerColor;
    private final int innerColor;

    public static final Comb NULL = new Comb(0, 0);

    public Comb(int outerColor, int innerColor) {
        this.outerColor = outerColor;
        this.innerColor = innerColor;
    }

    public ResourceLocation getId() {
        try {
            return Minecraft.getInstance().getConnection().registryAccess().registry(CombRegistration.COMB_REGISTRY_KEY).get().getKey(this);
        } catch (NullPointerException e) {
            return new ResourceLocation(MODID, "null");
        }
    }

    public int getOuterColor() {
        return this.outerColor;
    }

    public int getInnerColor() {
        return this.innerColor;
    }

    @Deprecated
    public static ItemStack toStack(Comb comb) {
        ItemStack stack = new ItemStack(ItemsRegistration.COMB.get(), 1);
        CompoundTag tag = new CompoundTag();
        tag.putString(CombItem.COMB_TYPE_TAG, comb.getId().toString());
        stack.setTag(tag);
        return stack;
    }

    @Override
    public String toString() {
        return getId().toString();
    }
}
