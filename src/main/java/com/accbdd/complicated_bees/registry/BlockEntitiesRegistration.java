package com.accbdd.complicated_bees.registry;

import com.accbdd.complicated_bees.block.entity.ApiaryBlockEntity;
import com.accbdd.complicated_bees.block.entity.BeeNestBlockEntity;
import com.accbdd.complicated_bees.block.entity.CentrifugeBlockEntity;
import com.accbdd.complicated_bees.block.entity.GeneratorBlockEntity;
import com.accbdd.complicated_bees.genetics.Species;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class BlockEntitiesRegistration {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, MODID);

    public static final Supplier<BlockEntityType<ApiaryBlockEntity>> APIARY_ENTITY = BLOCK_ENTITIES.register("apiary",
            () -> BlockEntityType.Builder.of(ApiaryBlockEntity::new, BlocksRegistration.APIARY.get()).build(null));
    public static final Supplier<BlockEntityType<CentrifugeBlockEntity>> CENTRIFUGE_ENTITY = BLOCK_ENTITIES.register("centrifuge",
            () -> BlockEntityType.Builder.of(CentrifugeBlockEntity::new, BlocksRegistration.CENTRIFUGE.get()).build(null));
    public static final Supplier<BlockEntityType<BeeNestBlockEntity>> BEE_NEST_ENTITY = BLOCK_ENTITIES.register("bee_nest",
            () -> BlockEntityType.Builder.of(BeeNestBlockEntity::new, BlocksRegistration.BEE_NEST.get()).build(null));
    public static final Supplier<BlockEntityType<GeneratorBlockEntity>> GENERATOR_BLOCK_ENTITY = BLOCK_ENTITIES.register("generator",
            () -> BlockEntityType.Builder.of(GeneratorBlockEntity::new, BlocksRegistration.GENERATOR.get()).build(null));

}
