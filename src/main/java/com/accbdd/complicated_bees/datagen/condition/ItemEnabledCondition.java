package com.accbdd.complicated_bees.datagen.condition;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

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
    public ResourceLocation getID() {
        return new ResourceLocation(MODID, "item_enabled");
    }

    @Override
    public boolean test(IContext context) {
        return BuiltInRegistries.ITEM.get(getItem()).isEnabled(FeatureFlagSet.of());
    }

    public ResourceLocation getItem() {
        return item;
    }

    @Override
    public String toString() {
        return "item_enabled(\"" + item + "\")";
    }

    public static class Serializer implements IConditionSerializer<ItemEnabledCondition> {
        public static Serializer INSTANCE = new Serializer();

        @Override
        public void write(JsonObject json, ItemEnabledCondition value) {
            ItemEnabledCondition.CODEC.encode(value, JsonOps.INSTANCE, json);
        }

        @Override
        public ItemEnabledCondition read(JsonObject json) {
            var result = CODEC.decode(JsonOps.INSTANCE, json);
            var completedResult = result.getOrThrow(false, (string) -> {
                throw new RuntimeException("error reading ItemEnabledCondition: " + string);
            });
            return completedResult.getFirst();
        }

        @Override
        public ResourceLocation getID() {
            return new ResourceLocation(MODID, "item_enabled");
        }
    }
}
