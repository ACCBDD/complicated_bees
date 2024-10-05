package com.accbdd.complicated_bees.loot;

import com.accbdd.complicated_bees.block.entity.BeeNestBlockEntity;
import com.accbdd.complicated_bees.genetics.Species;
import com.accbdd.complicated_bees.registry.EsotericRegistration;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.apache.commons.lang3.ArrayUtils;

public class InheritHiveCombFunction extends LootItemConditionalFunction {
    public InheritHiveCombFunction(LootItemCondition[] pPredicates) {
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
        public static Serializer INSTANCE = new Serializer();

        @Override
        public void serialize(JsonObject json, InheritHiveCombFunction function, JsonSerializationContext context) {
            if (!ArrayUtils.isEmpty(function.predicates)) {
                json.add("conditions", context.serialize(function.predicates));
            }
        }

        @Override
        public final InheritHiveCombFunction deserialize(JsonObject p_80719_, JsonDeserializationContext p_80720_) {
            LootItemCondition[] alootitemcondition = GsonHelper.getAsObject(p_80719_, "conditions", new LootItemCondition[0], p_80720_, LootItemCondition[].class);
            return new InheritHiveCombFunction(alootitemcondition);
        }
    }
}
