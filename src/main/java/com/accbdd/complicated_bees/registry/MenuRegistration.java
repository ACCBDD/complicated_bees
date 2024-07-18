package com.accbdd.complicated_bees.registry;

import com.accbdd.complicated_bees.screen.ApiaryMenu;
import com.accbdd.complicated_bees.screen.CentrifugeMenu;
import com.accbdd.complicated_bees.screen.GeneratorMenu;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class MenuRegistration {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(BuiltInRegistries.MENU, MODID);

    public static final Supplier<MenuType<CentrifugeMenu>> CENTRIFUGE_MENU = MENU_TYPES.register("centrifuge",
            () -> IMenuTypeExtension.create((windowId, inv, data) -> new CentrifugeMenu(windowId, inv.player, data.readBlockPos())));
    public static final Supplier<MenuType<ApiaryMenu>> APIARY_MENU = MENU_TYPES.register("apiary",
            () -> IMenuTypeExtension.create((windowId, inv, data) -> new ApiaryMenu(windowId, inv.player, data.readBlockPos())));
    public static final Supplier<MenuType<GeneratorMenu>> GENERATOR_MENU = MENU_TYPES.register("generator",
            () -> IMenuTypeExtension.create(((windowId, inv, data) -> new GeneratorMenu(windowId, inv.player, data.readBlockPos()))));
}
