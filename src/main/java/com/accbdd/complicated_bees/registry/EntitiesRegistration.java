package com.accbdd.complicated_bees.registry;

import com.accbdd.complicated_bees.entity.BeeStaffProjectile;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class EntitiesRegistration {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MODID);

    public static final Supplier<EntityType<BeeStaffProjectile>> BEE_STAFF_MOUNT = ENTITY_TYPE.register("bee_staff_mount",
            () -> EntityType.Builder.<BeeStaffProjectile>of(BeeStaffProjectile::new, MobCategory.MISC).sized(0.5f, 0.5f).build("bee_staff_mount"));
}
