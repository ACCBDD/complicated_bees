package com.accbdd.complicated_bees.loot;

import com.accbdd.complicated_bees.block.entity.BeeNestBlockEntity;
import com.accbdd.complicated_bees.genetics.GeneticHelper;
import com.accbdd.complicated_bees.genetics.Genome;
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

public class InheritHiveSpeciesFunction extends LootItemConditionalFunction {

    public InheritHiveSpeciesFunction(LootItemCondition[] pPredicates) {
        super(pPredicates);
    }

    @Override
    public LootItemFunctionType getType() {
        return EsotericRegistration.INHERIT_HIVE.get();
    }

    @Override
    protected ItemStack run(ItemStack pStack, LootContext pContext) {
        BlockEntity blockEntity = pContext.getParam(LootContextParams.BLOCK_ENTITY);
        if (blockEntity instanceof BeeNestBlockEntity) {
            Species species = ((BeeNestBlockEntity) blockEntity).getSpecies();
            return GeneticHelper.setGenome(pStack, new Genome(species.getDefaultChromosome()));
        }
        return pStack;
    }

    public static LootItemConditionalFunction.Builder<?> set() {
        return simpleBuilder(InheritHiveSpeciesFunction::new);
    }

    public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<InheritHiveSpeciesFunction> {
        public static InheritHiveSpeciesFunction.Serializer INSTANCE = new Serializer();

        @Override
        public void serialize(JsonObject json, InheritHiveSpeciesFunction function, JsonSerializationContext context) {
            if (!ArrayUtils.isEmpty(function.predicates)) {
                json.add("conditions", context.serialize(function.predicates));
            }
        }

        @Override
        public final InheritHiveSpeciesFunction deserialize(JsonObject p_80719_, JsonDeserializationContext p_80720_) {
            LootItemCondition[] alootitemcondition = GsonHelper.getAsObject(p_80719_, "conditions", new LootItemCondition[0], p_80720_, LootItemCondition[].class);
            return new InheritHiveSpeciesFunction(alootitemcondition);
        }
    }
}
