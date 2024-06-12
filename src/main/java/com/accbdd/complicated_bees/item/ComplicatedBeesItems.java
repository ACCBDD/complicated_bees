package com.accbdd.complicated_bees.item;

import com.accbdd.complicated_bees.block.ComplicatedBeesBlocks;
import net.minecraft.world.item.BlockItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class ComplicatedBeesItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    public static final DeferredItem<BeeItem> DRONE = ITEMS.registerItem("drone", BeeItem::new);
    public static final DeferredItem<BeeItem> PRINCESS = ITEMS.registerItem("princess", BeeItem::new);
    public static final DeferredItem<BeeItem> QUEEN = ITEMS.registerItem("queen", BeeItem::new);
    public static final DeferredItem<CombItem> COMB = ITEMS.registerItem("comb", CombItem::new);
    public static final DeferredItem<ScoopItem> SCOOP = ITEMS.registerItem("scoop", ScoopItem::new);
    public static final DeferredItem<BlockItem> BEE_NEST = ITEMS.registerSimpleBlockItem("bee_nest", ComplicatedBeesBlocks.BEE_NEST);

}
