package com.accbdd.complicated_bees.registry;

import com.accbdd.complicated_bees.genetics.BeeHousingModifier;
import com.accbdd.complicated_bees.genetics.gene.enums.EnumTolerance;
import com.accbdd.complicated_bees.item.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
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
    public static final DeferredItem<MeterItem> METER = ITEMS.registerItem("meter", MeterItem::new);
    public static final DeferredItem<AnalyzerItem> ANALYZER = ITEMS.registerItem("analyzer", AnalyzerItem::new);
    public static final DeferredItem<ExpDropItem> EXP_DROP = ITEMS.registerItem("exp_drop", ExpDropItem::new);

    public static final DeferredItem<Item> HONEY_DROPLET = ITEMS.registerSimpleItem("honey_droplet");
    public static final DeferredItem<Item> ROYAL_JELLY = ITEMS.registerSimpleItem("royal_jelly");
    public static final DeferredItem<Item> POLLEN = ITEMS.registerSimpleItem("pollen");
    public static final DeferredItem<Item> PROPOLIS = ITEMS.registerSimpleItem("propolis");
    public static final DeferredItem<BeeswaxItem> BEESWAX = ITEMS.registerItem("beeswax", BeeswaxItem::new);
    public static final DeferredItem<Item> PEARL_SHARD = ITEMS.registerSimpleItem("pearl_shard");

    public static final DeferredItem<FrameItem> FRAME = ITEMS.registerItem("frame",
            (prop) -> new FrameItem(prop.durability(50), new BeeHousingModifier(EnumTolerance.NONE, EnumTolerance.NONE, 1.1f, 1f)));
    public static final DeferredItem<FrameItem> THICK_FRAME = ITEMS.registerItem("thick_frame",
            (prop) -> new FrameItem(prop.durability(50), new BeeHousingModifier(EnumTolerance.NONE, EnumTolerance.NONE, 1f, 1.1f)));
    public static final DeferredItem<FrameItem> COLD_FRAME = ITEMS.registerItem("cold_frame",
            (prop) -> new FrameItem(prop.durability(50), new BeeHousingModifier(EnumTolerance.DOWN_1, EnumTolerance.NONE, 0.8f, 1f)));
    public static final DeferredItem<FrameItem> HOT_FRAME = ITEMS.registerItem("hot_frame",
            (prop) -> new FrameItem(prop.durability(50), new BeeHousingModifier(EnumTolerance.UP_1, EnumTolerance.NONE, 0.8f, 1f)));
    public static final DeferredItem<FrameItem> DRY_FRAME = ITEMS.registerItem("dry_frame",
            (prop) -> new FrameItem(prop.durability(50), new BeeHousingModifier(EnumTolerance.NONE, EnumTolerance.DOWN_1, 0.8f, 1f)));
    public static final DeferredItem<FrameItem> MOIST_FRAME = ITEMS.registerItem("moist_frame",
            (prop) -> new FrameItem(prop.durability(50), new BeeHousingModifier(EnumTolerance.NONE, EnumTolerance.UP_1, 0.8f, 1f)));
    public static final DeferredItem<FrameItem> DEADLY_FRAME = ITEMS.registerItem("deadly_frame",
            (prop) -> new FrameItem(prop.durability(50), new BeeHousingModifier(EnumTolerance.NONE, EnumTolerance.NONE, 0.1f, 1f)));
    public static final DeferredItem<FrameItem> RESTRICTIVE_FRAME = ITEMS.registerItem("restrictive_frame",
            (prop) -> new FrameItem(prop.durability(50), new BeeHousingModifier(EnumTolerance.NONE, EnumTolerance.NONE, 1f, 1f)));


    public static final DeferredItem<BlockItem> BEE_NEST = ITEMS.registerItem("bee_nest", BeeNestBlockItem::new);
    public static final DeferredItem<BlockItem> APIARY = ITEMS.registerSimpleBlockItem("apiary", BlocksRegistration.APIARY);
    public static final DeferredItem<BlockItem> CENTRIFUGE = ITEMS.registerSimpleBlockItem("centrifuge", BlocksRegistration.CENTRIFUGE);
    public static final DeferredItem<BlockItem> GENERATOR = ITEMS.registerSimpleBlockItem("generator", BlocksRegistration.GENERATOR);

}
