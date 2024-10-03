package com.accbdd.complicated_bees.loot;

import com.accbdd.complicated_bees.block.entity.BeeNestBlockEntity;
import com.accbdd.complicated_bees.genetics.Species;
import com.accbdd.complicated_bees.registry.EsotericRegistration;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.Serializer;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.List;

public class InheritHiveCombFunction extends LootItemConditionalFunction {
    public static final Codec<InheritHiveCombFunction> CODEC = RecordCodecBuilder.create(
            instance -> commonFields(instance).apply(instance, InheritHiveCombFunction::new)
    );

    public InheritHiveCombFunction(List<LootItemCondition> pPredicates) {
        super(pPredicates);
    }

    @Override
    public LootItemFunctionType getType() {
        return EsotericRegistration.INHERIT_COMB.get();
    }

    @Override
    protected ItemStack run(ItemStack pStack, LootContext pContext) {
        BlockEntity blockEntity = pContext.getParam(LootContextParams.BLOCK_ENTITY);
        if (blockEntity instanceof BeeNestBlockEntity) {
            Species species = ((BeeNestBlockEntity) blockEntity).getSpecies();
            return species.getProducts().get(0).getStack();
        }
        return pStack;
    }

    public static Builder<?> set() {
        return simpleBuilder(InheritHiveCombFunction::new);
    }

    public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<InheritHiveCombFunction> {

        @Override
        public void serialize(JsonObject json, InheritHiveCombFunction func, JsonSerializationContext context) {
            CODEC.encode(func, JsonOps.INSTANCE, json);
        }

        @Override
        public InheritHiveCombFunction deserialize(JsonObject json, JsonDeserializationContext context) {
            var result = CODEC.decode(JsonOps.INSTANCE, json);
            var completedResult = result.getOrThrow(false, (string) -> {
                throw new RuntimeException("error reading ItemEnabledCondition: " + string);
            });
            return completedResult.getFirst();
        }
    }
}
