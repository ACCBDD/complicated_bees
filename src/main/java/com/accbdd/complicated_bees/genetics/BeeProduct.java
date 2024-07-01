package com.accbdd.complicated_bees.genetics;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.Random;

public class BeeProduct {
    public static final Codec<BeeProduct> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(bp -> bp.getStack().getItem()),
                    ExtraCodecs.strictOptionalField(ExtraCodecs.POSITIVE_INT, "count", 1).forGetter(bp -> bp.getStack().getCount()),
                    ExtraCodecs.strictOptionalField(net.neoforged.neoforge.common.crafting.CraftingHelper.TAG_CODEC, "nbt").forGetter(bp -> java.util.Optional.ofNullable(net.neoforged.neoforge.common.crafting.CraftingHelper.getTagForWriting(bp.getStack()))),
                    Codec.FLOAT.optionalFieldOf("chance", 1f).forGetter(BeeProduct::getChance)
            ).apply(instance, (item, ct, nbt, chance) -> new BeeProduct(new ItemStack(item, ct, nbt), chance))
    );

    public static final BeeProduct EMPTY = new BeeProduct(Items.AIR.getDefaultInstance(), 0);

    public static final Random rand = new Random();

    private final ItemStack stack;
    private final float chance;

    public BeeProduct(ItemStack stack, float chance) {
        this.stack = stack;
        this.chance = chance;
    }

    public float getChance() {
        return chance;
    }

    public ItemStack getStack() {
        return stack.copy();
    }

    public ItemStack getStackResult() {
        return rand.nextFloat() < this.getChance() ? this.getStack() : ItemStack.EMPTY;
    }
}
