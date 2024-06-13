package com.accbdd.complicated_bees.registry;

import com.accbdd.complicated_bees.block.entity.ApiaryBlockEntity;
import com.accbdd.complicated_bees.block.entity.CentrifugeBlockEntity;
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

}
