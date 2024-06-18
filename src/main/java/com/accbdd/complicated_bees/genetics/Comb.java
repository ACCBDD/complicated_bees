package com.accbdd.complicated_bees.genetics;

import com.accbdd.complicated_bees.item.CombItem;
import com.accbdd.complicated_bees.registry.ItemsRegistration;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class Comb {
    private final String id;
    private final int outerColor;
    private final int innerColor;
    private final CombProducts products;

    public static final Comb NULL = new Comb("null", 0, 0, new CombProducts(Items.AIR.getDefaultInstance(), 0, Items.AIR.getDefaultInstance(), 0));

    public Comb(String id, int outerColor, int innerColor, CombProducts products) {
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

    public CombProducts getProducts() {
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
