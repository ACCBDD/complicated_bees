package com.accbdd.complicated_bees.item;

import com.accbdd.complicated_bees.ComplicatedBees;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ComplicatedBeesItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ComplicatedBees.MODID);

    public static final DeferredItem<Item> BEE = ITEMS.registerSimpleItem("bee");

}
