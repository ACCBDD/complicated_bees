package com.accbdd.complicated_bees.item;

import com.accbdd.complicated_bees.block.ComplicatedBeesBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class ComplicatedBeesItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    public static final DeferredItem<Item> DRONE = ITEMS.registerSimpleItem("drone");
    public static final DeferredItem<Item> PRINCESS = ITEMS.registerSimpleItem("princess");
    public static final DeferredItem<Item> QUEEN = ITEMS.registerSimpleItem("queen");
    public static final DeferredItem<Item> SCOOP = ITEMS.registerSimpleItem("scoop");
    public static final DeferredItem<BlockItem> BEE_NEST = ITEMS.registerSimpleBlockItem("bee_nest", ComplicatedBeesBlocks.BEE_NEST);

}
