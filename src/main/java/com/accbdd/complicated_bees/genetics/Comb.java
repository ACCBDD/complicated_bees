package com.accbdd.complicated_bees.genetics;

import com.accbdd.complicated_bees.item.CombItem;
import com.accbdd.complicated_bees.registry.ItemsRegistration;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;
import static com.accbdd.complicated_bees.utils.ComplicatedBeesCodecs.HEX_STRING_CODEC;

public class Comb {
    public static final Codec<Comb> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("id").forGetter(Comb::getId),
                    HEX_STRING_CODEC.fieldOf("outer_color").forGetter(Comb::getOuterColor),
                    HEX_STRING_CODEC.fieldOf("inner_color").forGetter(Comb::getInnerColor),
                    Product.CODEC.listOf().fieldOf("products").forGetter(Comb::getProducts)
            ).apply(instance, Comb::new)
    );

    private final String id;
    private final int outerColor;
    private final int innerColor;
    private final List<Product> products;

    public static final Comb NULL = new Comb("null", 0, 0, new ArrayList<>());

    public Comb(String id, int outerColor, int innerColor, List<Product> products) {
        this.id = id;
        this.outerColor = outerColor;
        this.innerColor = innerColor;
        this.products = products;
    }

    public String getId() {
        return id;
    }

    public int getOuterColor() {
        return this.outerColor;
    }

    public int getInnerColor() {
        return this.innerColor;
    }

    public List<Product> getProducts() {
        return products;
    }

    public static ItemStack toStack(Comb comb) {
        ItemStack stack = new ItemStack(ItemsRegistration.COMB.get(), 1);
        CompoundTag tag = new CompoundTag();
        tag.putString(CombItem.COMB_TYPE_TAG, new ResourceLocation(MODID, comb.id).toString());
        stack.setTag(tag);
        return stack;
    }

    @Override
    public String toString() {
        return this.id;
    }
}
