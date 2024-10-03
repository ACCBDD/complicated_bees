package com.accbdd.complicated_bees.registry;

import com.accbdd.complicated_bees.config.Config;
import com.accbdd.complicated_bees.genetics.BeeHousingModifier;
import com.accbdd.complicated_bees.genetics.gene.enums.EnumTolerance;
import com.accbdd.complicated_bees.item.ArmorMaterials;
import com.accbdd.complicated_bees.item.*;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class ItemsRegistration {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    public static final RegistryObject<DroneItem> DRONE = ITEMS.register("drone", () -> new DroneItem(new Item.Properties()));
    public static final RegistryObject<PrincessItem> PRINCESS = ITEMS.register("princess", () -> new PrincessItem(new Item.Properties()));
    public static final RegistryObject<QueenItem> QUEEN = ITEMS.register("queen", () -> new QueenItem(new Item.Properties()));
    public static final RegistryObject<CombItem> COMB = ITEMS.register("comb", () -> new CombItem(new Item.Properties()));
    public static final RegistryObject<ScoopItem> SCOOP = ITEMS.register("scoop", () -> new ScoopItem(new Item.Properties()));
    public static final RegistryObject<MeterItem> METER = ITEMS.register("meter", () -> new MeterItem(new Item.Properties()));
    public static final RegistryObject<AnalyzerItem> ANALYZER = ITEMS.register("analyzer", () -> new AnalyzerItem(new Item.Properties()));
    public static final RegistryObject<ExpDropItem> EXP_DROP = ITEMS.register("exp_drop", () -> new ExpDropItem(new Item.Properties()));
    public static final RegistryObject<BeeswaxItem> BEESWAX = ITEMS.register("beeswax", () -> new BeeswaxItem(new Item.Properties()));

    public static final RegistryObject<Item> HONEY_DROPLET = registerSimpleItem("honey_droplet");
    public static final RegistryObject<Item> ROYAL_JELLY = registerSimpleItem("royal_jelly");
    public static final RegistryObject<Item> POLLEN = registerSimpleItem("pollen");
    public static final RegistryObject<Item> PROPOLIS = registerSimpleItem("propolis");
    public static final RegistryObject<Item> SILK_WISP = registerSimpleItem("silk_wisp");
    public static final RegistryObject<Item> WOVEN_MESH = registerSimpleItem("woven_mesh");
    public static final RegistryObject<Item> PEARL_SHARD = registerSimpleItem("pearl_shard");
    public static final RegistryObject<Item> WAXED_STICK = registerSimpleItem("waxed_stick");
    public static final RegistryObject<Item> HONEYED_STICK = registerSimpleItem("honeyed_stick");
    public static final RegistryObject<Item> BEE_STAFF = ITEMS.register("bee_staff", () -> new BeeStaffItem(new Item.Properties(), Config.CONFIG.beeStaff));
    public static final RegistryObject<Item> HONEY_BREAD = ITEMS.register("honey_bread", () -> new DisableableItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationMod(0.4f).build()), Config.CONFIG.honeyBread));
    public static final RegistryObject<Item> HONEY_PORKCHOP = ITEMS.register("honey_porkchop", () -> new DisableableItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(12).saturationMod(0.5f).build()), Config.CONFIG.honeyPorkchop));
    public static final RegistryObject<Item> AMBROSIA = ITEMS.register("ambrosia", () -> new DisableableItem(new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(6)
            .saturationMod(1.2F)
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 400, 1), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.JUMP, 1200, 2), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 2400, 1), 1.0F)
            .alwaysEat()
            .build()).rarity(Rarity.RARE), Config.CONFIG.ambrosia) {
        @Override
        public boolean isFoil(ItemStack pStack) {
            return true;
        }
    });

    public static final RegistryObject<FrameItem> FRAME = ITEMS.register("frame",
            () -> new FrameItem(new Item.Properties().durability(80), new BeeHousingModifier.Builder().productivity(1.1f).build(), Config.CONFIG.frame));
    public static final RegistryObject<FrameItem> WAXED_FRAME = ITEMS.register("waxed_frame",
            () -> new FrameItem(new Item.Properties().durability(240), new BeeHousingModifier.Builder().productivity(1.25f).build(), Config.CONFIG.waxedFrame));
    public static final RegistryObject<FrameItem> HONEYED_FRAME = ITEMS.register("honeyed_frame",
            () -> new FrameItem(new Item.Properties().durability(80), new BeeHousingModifier.Builder().productivity(1.35f).lifespan(0.9f).build(), Config.CONFIG.honeyFrame));
    public static final RegistryObject<FrameItem> TWISTING_FRAME = ITEMS.register("twisting_frame",
            () -> new FrameItem(new Item.Properties().durability(80), new BeeHousingModifier.Builder().productivity(0.6f).lifespan(0.75f).mutation(1.5f).build(), Config.CONFIG.twistingFrame));
    public static final RegistryObject<FrameItem> SOOTHING_FRAME = ITEMS.register("soothing_frame",
            () -> new FrameItem(new Item.Properties().durability(80), new BeeHousingModifier.Builder().productivity(0.75f).lifespan(1.5f).mutation(0.8f).build(), Config.CONFIG.soothingFrame));
    public static final RegistryObject<FrameItem> COLD_FRAME = ITEMS.register("cold_frame",
            () -> new FrameItem(new Item.Properties().durability(80), new BeeHousingModifier.Builder().temperature(EnumTolerance.DOWN_1).lifespan(0.8f).build(), Config.CONFIG.coldFrame));
    public static final RegistryObject<FrameItem> HOT_FRAME = ITEMS.register("hot_frame",
            () -> new FrameItem(new Item.Properties().durability(80), new BeeHousingModifier.Builder().temperature(EnumTolerance.UP_1).lifespan(0.8f).build(), Config.CONFIG.hotFrame));
    public static final RegistryObject<FrameItem> DRY_FRAME = ITEMS.register("dry_frame",
            () -> new FrameItem(new Item.Properties().durability(80), new BeeHousingModifier.Builder().humidity(EnumTolerance.DOWN_1).lifespan(0.8f).build(), Config.CONFIG.dryFrame));
    public static final RegistryObject<FrameItem> WET_FRAME = ITEMS.register("wet_frame",
            () -> new FrameItem(new Item.Properties().durability(80), new BeeHousingModifier.Builder().humidity(EnumTolerance.UP_1).lifespan(0.8f).build(), Config.CONFIG.wetFrame));
    public static final RegistryObject<FrameItem> DEADLY_FRAME = ITEMS.register("deadly_frame",
            () -> new FrameItem(new Item.Properties().durability(80), new BeeHousingModifier.Builder().lifespan(0.1f).build(), Config.CONFIG.deadlyFrame));
    public static final RegistryObject<FrameItem> RESTRICTIVE_FRAME = ITEMS.register("restrictive_frame",
            () -> new FrameItem(new Item.Properties().durability(80), new BeeHousingModifier.Builder().territory(0.5f).lifespan(0.75f).productivity(0.75f).build(), Config.CONFIG.restrictiveFrame));

    public static final RegistryObject<ArmorItem> APIARIST_HELMET = ITEMS.register("apiarist_helmet",
            () -> new ArmorItem(ArmorMaterials.APIARIST, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<ArmorItem> APIARIST_CHESTPLATE = ITEMS.register("apiarist_chestplate",
            () -> new ArmorItem(ArmorMaterials.APIARIST, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<ArmorItem> APIARIST_LEGGINGS = ITEMS.register("apiarist_leggings",
            () -> new ArmorItem(ArmorMaterials.APIARIST, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<ArmorItem> APIARIST_BOOTS = ITEMS.register("apiarist_boots",
            () -> new ArmorItem(ArmorMaterials.APIARIST, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<BlockItem> BEE_NEST = ITEMS.register("bee_nest", () -> new BeeNestBlockItem(new Item.Properties()));
    public static final RegistryObject<BlockItem> APIARY = registerSimpleBlockItem("apiary", BlocksRegistration.APIARY.get());
    public static final RegistryObject<BlockItem> CENTRIFUGE = registerSimpleBlockItem("centrifuge", BlocksRegistration.CENTRIFUGE.get());
    public static final RegistryObject<BlockItem> GENERATOR = registerSimpleBlockItem("generator", BlocksRegistration.GENERATOR.get());
    public static final RegistryObject<BlockItem> WAX_BLOCK = registerSimpleBlockItem("wax_block", BlocksRegistration.WAX_BLOCK.get());
    public static final RegistryObject<BlockItem> WAX_BLOCK_STAIRS = registerSimpleBlockItem("wax_block_stairs", BlocksRegistration.WAX_BLOCK_STAIRS.get());
    public static final RegistryObject<BlockItem> WAX_BLOCK_SLAB = registerSimpleBlockItem("wax_block_slab", BlocksRegistration.WAX_BLOCK_SLAB.get());
    public static final RegistryObject<BlockItem> WAX_BLOCK_WALL = registerSimpleBlockItem("wax_block_wall", BlocksRegistration.WAX_BLOCK_WALL.get());
    public static final RegistryObject<BlockItem> SMOOTH_WAX = registerSimpleBlockItem("smooth_wax", BlocksRegistration.SMOOTH_WAX.get());
    public static final RegistryObject<BlockItem> SMOOTH_WAX_STAIRS = registerSimpleBlockItem("smooth_wax_stairs", BlocksRegistration.SMOOTH_WAX_STAIRS.get());
    public static final RegistryObject<BlockItem> SMOOTH_WAX_SLAB = registerSimpleBlockItem("smooth_wax_slab", BlocksRegistration.SMOOTH_WAX_SLAB.get());
    public static final RegistryObject<BlockItem> SMOOTH_WAX_WALL = registerSimpleBlockItem("smooth_wax_wall", BlocksRegistration.SMOOTH_WAX_WALL.get());
    public static final RegistryObject<BlockItem> WAX_BRICKS = registerSimpleBlockItem("wax_bricks", BlocksRegistration.WAX_BRICKS.get());
    public static final RegistryObject<BlockItem> WAX_BRICK_STAIRS = registerSimpleBlockItem("wax_brick_stairs", BlocksRegistration.WAX_BRICK_STAIRS.get());
    public static final RegistryObject<BlockItem> WAX_BRICK_SLAB = registerSimpleBlockItem("wax_brick_slab", BlocksRegistration.WAX_BRICK_SLAB.get());
    public static final RegistryObject<BlockItem> WAX_BRICK_WALL = registerSimpleBlockItem("wax_brick_wall", BlocksRegistration.WAX_BRICK_WALL.get());
    public static final RegistryObject<BlockItem> CHISELED_WAX = registerSimpleBlockItem("chiseled_wax", BlocksRegistration.CHISELED_WAX.get());
    public static final RegistryObject<BlockItem> HONEYED_PLANKS = registerSimpleBlockItem("honeyed_planks", BlocksRegistration.HONEYED_PLANKS.get());
    public static final RegistryObject<BlockItem> HONEYED_STAIRS = registerSimpleBlockItem("honeyed_stairs", BlocksRegistration.HONEYED_STAIRS.get());
    public static final RegistryObject<BlockItem> HONEYED_SLAB = registerSimpleBlockItem("honeyed_slab", BlocksRegistration.HONEYED_SLAB.get());
    public static final RegistryObject<BlockItem> HONEYED_FENCE = registerSimpleBlockItem("honeyed_fence", BlocksRegistration.HONEYED_FENCE.get());
    public static final RegistryObject<BlockItem> HONEYED_FENCE_GATE = registerSimpleBlockItem("honeyed_fence_gate", BlocksRegistration.HONEYED_FENCE_GATE.get());
    public static final RegistryObject<BlockItem> HONEYED_BUTTON = registerSimpleBlockItem("honeyed_button", BlocksRegistration.HONEYED_BUTTON.get());
    public static final RegistryObject<BlockItem> HONEYED_PRESSURE_PLATE = registerSimpleBlockItem("honeyed_pressure_plate", BlocksRegistration.HONEYED_PRESSURE_PLATE.get());
    public static final RegistryObject<BlockItem> HONEYED_DOOR = ITEMS.register("honeyed_door", () -> new DoubleHighBlockItem(BlocksRegistration.HONEYED_DOOR.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> HONEYED_TRAPDOOR = registerSimpleBlockItem("honeyed_trapdoor", BlocksRegistration.HONEYED_TRAPDOOR.get());

    private static RegistryObject<Item> registerSimpleItem(String name) {
        return ITEMS.register(name, () -> new Item(new Item.Properties()));
    }

    private static RegistryObject<BlockItem> registerSimpleBlockItem(String name, Block block) {
        return ITEMS.register(name, () -> new BlockItem(block, new Item.Properties()));
    }
}
