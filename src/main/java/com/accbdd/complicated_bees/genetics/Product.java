package com.accbdd.complicated_bees.genetics;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;
import java.util.Random;

public class Product {
    public static final Codec<Product> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(bp -> bp.getStack().getItem()),
                    ExtraCodecs.POSITIVE_INT.optionalFieldOf("count", 1).forGetter(bp -> bp.getStack().getCount()),
                    CompoundTag.CODEC.optionalFieldOf("nbt", new CompoundTag()).forGetter(bp -> bp.getStack().getOrCreateTag()),
                    Codec.FLOAT.optionalFieldOf("chance", 1f).forGetter(Product::getChance)
            ).apply(instance, (item, ct, nbt, chance) -> {
                ItemStack stack = new ItemStack(item, ct);
                stack.setTag(nbt);
                return new Product(stack, chance);
            })
    );

    public static final List<Product> EMPTY = List.of(new Product(Items.AIR.getDefaultInstance(), 0));
    public static final Random rand = new Random();

    private final ItemStack stack;
    private final float chance;

    public Product(ItemStack stack, float chance) {
        this.stack = stack;
        this.chance = chance;
    }

    public float getChance() {
        return chance;
    }

    public ItemStack getStack() {
        return stack.copy();
    }

    public ItemStack getStackResult(float... modifiers) {
        float stackChance = this.getChance();
        for (float modifier : modifiers) {
            stackChance *= modifier;
        }
        return rand.nextFloat() < stackChance ? this.getStack() : ItemStack.EMPTY;
    }

    public void toNetwork(FriendlyByteBuf buf) {
        buf.writeFloat(chance);
        buf.writeItem(stack);
    }

    public static Product fromNetwork(FriendlyByteBuf buf) {
        float chance = buf.readFloat();
        ItemStack item = buf.readItem();
        return new Product(item, chance);
    }
}
