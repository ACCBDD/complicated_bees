package com.accbdd.complicated_bees.registry;

import com.accbdd.complicated_bees.item.*;
import net.minecraft.world.item.BlockItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class ItemsRegistration {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    public static final DeferredItem<DroneItem> DRONE = ITEMS.registerItem("drone", DroneItem::new);
    public static final DeferredItem<PrincessItem> PRINCESS = ITEMS.registerItem("princess", PrincessItem::new);
    public static final DeferredItem<QueenItem> QUEEN = ITEMS.registerItem("queen", QueenItem::new);
    public static final DeferredItem<CombItem> COMB = ITEMS.registerItem("comb", CombItem::new);
    public static final DeferredItem<ScoopItem> SCOOP = ITEMS.registerItem("scoop", ScoopItem::new);

    public static final DeferredItem<BlockItem> BEE_NEST = ITEMS.registerSimpleBlockItem("bee_nest", BlocksRegistration.BEE_NEST);
    public static final DeferredItem<BlockItem> APIARY = ITEMS.registerSimpleBlockItem("apiary", BlocksRegistration.APIARY);
    public static final DeferredItem<BlockItem> CENTRIFUGE = ITEMS.registerSimpleBlockItem("centrifuge", BlocksRegistration.CENTRIFUGE);

}
