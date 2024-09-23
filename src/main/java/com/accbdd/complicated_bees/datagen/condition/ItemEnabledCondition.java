package com.accbdd.complicated_bees.datagen.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlagSet;
import net.neoforged.neoforge.common.conditions.ICondition;

public class ItemEnabledCondition implements ICondition {
    public static Codec<ItemEnabledCondition> CODEC = RecordCodecBuilder.create(
            builder -> builder
                    .group(
                            ResourceLocation.CODEC.fieldOf("item").forGetter(ItemEnabledCondition::getItem))
                    .apply(builder, ItemEnabledCondition::new));

    private final ResourceLocation item;

    public ItemEnabledCondition(String location) {
        this(new ResourceLocation(location));
    }

    public ItemEnabledCondition(String namespace, String path) {
        this(new ResourceLocation(namespace, path));
    }

    public ItemEnabledCondition(ResourceLocation item) {
        this.item = item;
    }

    @Override
    public boolean test(IContext context) {
        return BuiltInRegistries.ITEM.get(getItem()).isEnabled(FeatureFlagSet.of());
    }

    @Override
    public Codec<? extends ICondition> codec() {
        return CODEC;
    }

    public ResourceLocation getItem() {
        return item;
    }

    @Override
    public String toString() {
        return "item_enabled(\"" + item + "\")";
    }
}
