package com.accbdd.complicated_bees.registry;

import com.accbdd.complicated_bees.bees.BeeHousingModifier;
import com.accbdd.complicated_bees.bees.MachineModifier;
import com.accbdd.complicated_bees.bees.gene.enums.EnumTolerance;
import com.accbdd.complicated_bees.config.CommonConfig;
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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class ItemsRegistration {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final List<RegistryObject<? extends Item>> CREATIVE_TAB_ITEMS = new ArrayList<>();

    //no creative tab for these :)
    public static final RegistryObject<DroneItem> DRONE = ITEMS.register("drone", () -> new DroneItem(new Item.Properties()));
    public static final RegistryObject<PrincessItem> PRINCESS = ITEMS.register("princess", () -> new PrincessItem(new Item.Properties()));
    public static final RegistryObject<QueenItem> QUEEN = ITEMS.register("queen", () -> new QueenItem(new Item.Properties()));
    public static final RegistryObject<CombItem> COMB = ITEMS.register("comb", () -> new CombItem(new Item.Properties()));
    public static final RegistryObject<Item> BEE_NEST = ITEMS.register("bee_nest", () -> new BeeNestBlockItem(new Item.Properties())); //no tab for this :)

    public static final RegistryObject<ScoopItem> SCOOP = register("scoop", () -> new ScoopItem(new Item.Properties()));
    public static final RegistryObject<MeterItem> METER = register("meter", () -> new MeterItem(new Item.Properties()));
    public static final RegistryObject<AnalyzerItem> ANALYZER = register("analyzer", () -> new AnalyzerItem(new Item.Properties()));
    public static final RegistryObject<ExpDropItem> EXP_DROP = register("exp_drop", () -> new ExpDropItem(new Item.Properties()));
    public static final RegistryObject<BeeswaxItem> BEESWAX = register("beeswax", () -> new BeeswaxItem(new Item.Properties()));

    public static final RegistryObject<Item> HONEY_DROPLET = registerSimpleItem("honey_droplet");
    public static final RegistryObject<Item> ROYAL_JELLY = registerSimpleItem("royal_jelly");
    public static final RegistryObject<Item> POLLEN = registerSimpleItem("pollen");
    public static final RegistryObject<Item> PROPOLIS = registerSimpleItem("propolis");
    public static final RegistryObject<Item> SILK_WISP = registerSimpleItem("silk_wisp");
    public static final RegistryObject<Item> WOVEN_MESH = registerSimpleItem("woven_mesh");
    public static final RegistryObject<Item> PEARL_SHARD = registerSimpleItem("pearl_shard");
    public static final RegistryObject<Item> WAXED_STICK = registerSimpleItem("waxed_stick");
    public static final RegistryObject<Item> HONEYED_STICK = registerSimpleItem("honeyed_stick");
    public static final RegistryObject<Item> MELLARIUM_PANEL = registerSimpleItem("mellarium_panel");
    public static final RegistryObject<Item> BASIC_UPGRADE = register("basic_upgrade", () -> new UpgradeItem(new Item.Properties(), new MachineModifier(1.1f, 1.1f, 1f, 0)));
    public static final RegistryObject<Item> WAXED_UPGRADE = register("waxed_upgrade", () -> new UpgradeItem(new Item.Properties(), new MachineModifier(1f, 1.25f, 1f, 0)));
    public static final RegistryObject<Item> HONEYED_UPGRADE = register("honeyed_upgrade", () -> new UpgradeItem(new Item.Properties(), new MachineModifier(1.25f, 1f, 1f, 0)));
    public static final RegistryObject<Item> TWISTED_UPGRADE = register("twisted_upgrade", () -> new UpgradeItem(new Item.Properties(), new MachineModifier(2f, 0.85f, 1f, 0)));
    public static final RegistryObject<Item> SILKY_UPGRADE = register("silky_upgrade", () -> new UpgradeItem(new Item.Properties(), new MachineModifier(0.85f, 2f, 1f, 0)));
    public static final RegistryObject<Item> ROYAL_UPGRADE = register("royal_upgrade", () -> new UpgradeItem(new Item.Properties(), new MachineModifier(1.5f, 1.5f, 1f, 0)));
    public static final RegistryObject<Item> ENDENIC_UPGRADE = register("endenic_upgrade", () -> new UpgradeItem(new Item.Properties().rarity(Rarity.UNCOMMON), new MachineModifier(0.5f, 4f, 1f, 0)));
    public static final RegistryObject<Item> WITHERED_UPGRADE = register("withered_upgrade", () -> new UpgradeItem(new Item.Properties().rarity(Rarity.UNCOMMON), new MachineModifier(4f, 0.5f, 1f, 0)));
    public static final RegistryObject<Item> BEE_STAFF = register("bee_staff", () -> new BeeStaffItem(new Item.Properties(), CommonConfig.COMMON_CONFIG.beeStaff));
    public static final RegistryObject<Item> HONEY_BREAD = register("honey_bread", () -> new DisableableItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationMod(0.4f).build()), CommonConfig.COMMON_CONFIG.honeyBread));
    public static final RegistryObject<Item> HONEY_PORKCHOP = register("honey_porkchop", () -> new DisableableItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(12).saturationMod(0.5f).build()), CommonConfig.COMMON_CONFIG.honeyPorkchop));
    public static final RegistryObject<Item> AMBROSIA = register("ambrosia", () -> new DisableableItem(new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(6)
            .saturationMod(1.2F)
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 400, 1), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.JUMP, 1200, 2), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 2400, 1), 1.0F)
            .alwaysEat()
            .build()).rarity(Rarity.RARE), CommonConfig.COMMON_CONFIG.ambrosia) {
        @Override
        public boolean isFoil(ItemStack pStack) {
            return true;
        }
    });
    public static final RegistryObject<Item> RESEARCH_NOTE = register("research_note", ResearchNoteItem::new);

    public static final RegistryObject<FrameItem> FRAME = register("frame",
            () -> new FrameItem(new Item.Properties().durability(80), new BeeHousingModifier.Builder().productivity(1.25f).build(), CommonConfig.COMMON_CONFIG.frame));
    public static final RegistryObject<FrameItem> WAXED_FRAME = register("waxed_frame",
            () -> new FrameItem(new Item.Properties().durability(240), new BeeHousingModifier.Builder().productivity(1.5f).build(), CommonConfig.COMMON_CONFIG.waxedFrame));
    public static final RegistryObject<FrameItem> HONEYED_FRAME = register("honeyed_frame",
            () -> new FrameItem(new Item.Properties().durability(80), new BeeHousingModifier.Builder().productivity(1.75f).lifespan(0.9f).build(), CommonConfig.COMMON_CONFIG.honeyFrame));
    public static final RegistryObject<FrameItem> TWISTING_FRAME = register("twisting_frame",
            () -> new FrameItem(new Item.Properties().durability(80), new BeeHousingModifier.Builder().productivity(0.6f).lifespan(0.75f).mutation(1.25f).build(), CommonConfig.COMMON_CONFIG.twistingFrame));
    public static final RegistryObject<FrameItem> SOOTHING_FRAME = register("soothing_frame",
            () -> new FrameItem(new Item.Properties().durability(80), new BeeHousingModifier.Builder().productivity(0.75f).lifespan(1.5f).mutation(0.8f).build(), CommonConfig.COMMON_CONFIG.soothingFrame));
    public static final RegistryObject<FrameItem> COLD_FRAME = register("cold_frame",
            () -> new FrameItem(new Item.Properties().durability(60), new BeeHousingModifier.Builder().temperature(EnumTolerance.DOWN_1).lifespan(0.6f).build(), CommonConfig.COMMON_CONFIG.coldFrame));
    public static final RegistryObject<FrameItem> HOT_FRAME = register("hot_frame",
            () -> new FrameItem(new Item.Properties().durability(60), new BeeHousingModifier.Builder().temperature(EnumTolerance.UP_1).lifespan(0.6f).build(), CommonConfig.COMMON_CONFIG.hotFrame));
    public static final RegistryObject<FrameItem> DRY_FRAME = register("dry_frame",
            () -> new FrameItem(new Item.Properties().durability(60), new BeeHousingModifier.Builder().humidity(EnumTolerance.DOWN_1).lifespan(0.8f).build(), CommonConfig.COMMON_CONFIG.dryFrame));
    public static final RegistryObject<FrameItem> WET_FRAME = register("wet_frame",
            () -> new FrameItem(new Item.Properties().durability(60), new BeeHousingModifier.Builder().humidity(EnumTolerance.UP_1).lifespan(0.8f).build(), CommonConfig.COMMON_CONFIG.wetFrame));
    public static final RegistryObject<FrameItem> DEADLY_FRAME = register("deadly_frame",
            () -> new FrameItem(new Item.Properties().durability(80), new BeeHousingModifier.Builder().lifespan(0.1f).build(), CommonConfig.COMMON_CONFIG.deadlyFrame));
    public static final RegistryObject<FrameItem> RESTRICTIVE_FRAME = register("restrictive_frame",
            () -> new FrameItem(new Item.Properties().durability(80), new BeeHousingModifier.Builder().territory(0.5f).lifespan(0.75f).productivity(0.75f).build(), CommonConfig.COMMON_CONFIG.restrictiveFrame));

    public static final RegistryObject<ArmorItem> APIARIST_HELMET = register("apiarist_helmet",
            () -> new ArmorItem(ArmorMaterials.APIARIST, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<ArmorItem> APIARIST_CHESTPLATE = register("apiarist_chestplate",
            () -> new ArmorItem(ArmorMaterials.APIARIST, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<ArmorItem> APIARIST_LEGGINGS = register("apiarist_leggings",
            () -> new ArmorItem(ArmorMaterials.APIARIST, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<ArmorItem> APIARIST_BOOTS = register("apiarist_boots",
            () -> new ArmorItem(ArmorMaterials.APIARIST, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> APIARY = registerSimpleBlockItem("apiary", BlocksRegistration.APIARY);
    public static final RegistryObject<Item> CENTRIFUGE = registerSimpleBlockItem("centrifuge", BlocksRegistration.CENTRIFUGE);
    public static final RegistryObject<Item> FURNACE_GENERATOR = registerSimpleBlockItem("furnace_generator", BlocksRegistration.FURNACE_GENERATOR);
    public static final RegistryObject<Item> HONEY_GENERATOR = registerSimpleBlockItem("honey_generator", BlocksRegistration.HONEY_GENERATOR);
    public static final RegistryObject<Item> MICROSCOPE = registerSimpleBlockItem("microscope", BlocksRegistration.MICROSCOPE);
    public static final RegistryObject<Item> BEE_SORTER = registerSimpleBlockItem("bee_sorter", BlocksRegistration.BEE_SORTER);
    public static final RegistryObject<Item> MELLARIUM_BASE = registerSimpleBlockItem("mellarium_base", BlocksRegistration.MELLARIUM_BASE);
    public static final RegistryObject<Item> MELLARIUM_TEMP_UNIT = registerSimpleBlockItem("mellarium_temp_unit", BlocksRegistration.MELLARIUM_TEMP_UNIT);
    public static final RegistryObject<Item> MELLARIUM_RAIN_SHIELD = registerSimpleBlockItem("mellarium_rain_shield", BlocksRegistration.MELLARIUM_RAIN_SHIELD);
    public static final RegistryObject<Item> MELLARIUM_FRAME_HOUSING_1 = registerSimpleBlockItem("mellarium_frame_housing_1", BlocksRegistration.MELLARIUM_FRAME_HOUSING_1);
    public static final RegistryObject<Item> MELLARIUM_FRAME_HOUSING_2 = registerSimpleBlockItem("mellarium_frame_housing_2", BlocksRegistration.MELLARIUM_FRAME_HOUSING_2);
    public static final RegistryObject<Item> MELLARIUM_FRAME_HOUSING_3 = registerSimpleBlockItem("mellarium_frame_housing_3", BlocksRegistration.MELLARIUM_FRAME_HOUSING_3);
    public static final RegistryObject<Item> MELLARIUM_MUTATOR = registerSimpleBlockItem("mellarium_mutator", BlocksRegistration.MELLARIUM_MUTATOR);
    public static final RegistryObject<Item> MELLARIUM_HYDROREGULATOR = registerSimpleBlockItem("mellarium_hydroregulator", BlocksRegistration.MELLARIUM_HYDROREGULATOR);
    public static final RegistryObject<Item> MELLARIUM_ENERGY_CELL = register("mellarium_energy_cell", MellariumEnergyCellBlockItem::new);
    public static final RegistryObject<Item> MELLARIUM_SKYBOX = registerSimpleBlockItem("mellarium_skybox", BlocksRegistration.MELLARIUM_SKYBOX);
    public static final RegistryObject<Item> MELLARIUM_TEMPORAL_SIMULATOR = registerSimpleBlockItem("mellarium_temporal_simulator", BlocksRegistration.MELLARIUM_TEMPORAL_SIMULATOR);
    public static final RegistryObject<Item> GYROFUGE_BASE = registerSimpleBlockItem("gyrofuge_base", BlocksRegistration.GYROFUGE_BASE);
    public static final RegistryObject<Item> GYROFUGE_ENERGY_CELL = register("gyrofuge_energy_cell", GyrofugeEnergyCellBlockItem::new);
    public static final RegistryObject<Item> GYROFUGE_BASIC_PROCESSING_UNIT = registerSimpleBlockItem("gyrofuge_basic_processing_unit", BlocksRegistration.GYROFUGE_BASIC_PROCESSING_UNIT);
    public static final RegistryObject<Item> GYROFUGE_PROCESSING_UNIT = registerSimpleBlockItem("gyrofuge_processing_unit", BlocksRegistration.GYROFUGE_PROCESSING_UNIT);
    public static final RegistryObject<Item> GYROFUGE_ADVANCED_PROCESSING_UNIT = registerSimpleBlockItem("gyrofuge_advanced_processing_unit", BlocksRegistration.GYROFUGE_ADVANCED_PROCESSING_UNIT);
    public static final RegistryObject<Item> GYROFUGE_SPEED_UNIT = registerSimpleBlockItem("gyrofuge_speed_unit", BlocksRegistration.GYROFUGE_SPEED_UNIT);
    public static final RegistryObject<Item> GYROFUGE_EFFICIENCY_UNIT = registerSimpleBlockItem("gyrofuge_efficiency_unit", BlocksRegistration.GYROFUGE_EFFICIENCY_UNIT);
    public static final RegistryObject<Item> GYROFUGE_EXTRACTION_UNIT = registerSimpleBlockItem("gyrofuge_extraction_unit", BlocksRegistration.GYROFUGE_EXTRACTION_UNIT);
    public static final RegistryObject<Item> GYROFUGE_OUTPUT_HATCH = registerSimpleBlockItem("gyrofuge_output_hatch", BlocksRegistration.GYROFUGE_OUTPUT_HATCH);
    public static final RegistryObject<Item> GYROFUGE_INPUT_HATCH = registerSimpleBlockItem("gyrofuge_input_hatch", BlocksRegistration.GYROFUGE_INPUT_HATCH);
    public static final RegistryObject<Item> APID_LIBRARY = registerSimpleBlockItem("apid_library", BlocksRegistration.APID_LIBRARY);
    public static final RegistryObject<Item> WAX_BLOCK = registerSimpleBlockItem("wax_block", BlocksRegistration.WAX_BLOCK);
    public static final RegistryObject<Item> WAX_BLOCK_STAIRS = registerSimpleBlockItem("wax_block_stairs", BlocksRegistration.WAX_BLOCK_STAIRS);
    public static final RegistryObject<Item> WAX_BLOCK_SLAB = registerSimpleBlockItem("wax_block_slab", BlocksRegistration.WAX_BLOCK_SLAB);
    public static final RegistryObject<Item> WAX_BLOCK_WALL = registerSimpleBlockItem("wax_block_wall", BlocksRegistration.WAX_BLOCK_WALL);
    public static final RegistryObject<Item> SMOOTH_WAX = registerSimpleBlockItem("smooth_wax", BlocksRegistration.SMOOTH_WAX);
    public static final RegistryObject<Item> SMOOTH_WAX_STAIRS = registerSimpleBlockItem("smooth_wax_stairs", BlocksRegistration.SMOOTH_WAX_STAIRS);
    public static final RegistryObject<Item> SMOOTH_WAX_SLAB = registerSimpleBlockItem("smooth_wax_slab", BlocksRegistration.SMOOTH_WAX_SLAB);
    public static final RegistryObject<Item> SMOOTH_WAX_WALL = registerSimpleBlockItem("smooth_wax_wall", BlocksRegistration.SMOOTH_WAX_WALL);
    public static final RegistryObject<Item> WAX_BRICKS = registerSimpleBlockItem("wax_bricks", BlocksRegistration.WAX_BRICKS);
    public static final RegistryObject<Item> WAX_BRICK_STAIRS = registerSimpleBlockItem("wax_brick_stairs", BlocksRegistration.WAX_BRICK_STAIRS);
    public static final RegistryObject<Item> WAX_BRICK_SLAB = registerSimpleBlockItem("wax_brick_slab", BlocksRegistration.WAX_BRICK_SLAB);
    public static final RegistryObject<Item> WAX_BRICK_WALL = registerSimpleBlockItem("wax_brick_wall", BlocksRegistration.WAX_BRICK_WALL);
    public static final RegistryObject<Item> CHISELED_WAX = registerSimpleBlockItem("chiseled_wax", BlocksRegistration.CHISELED_WAX);
    public static final RegistryObject<Item> HONEYED_PLANKS = registerSimpleBlockItem("honeyed_planks", BlocksRegistration.HONEYED_PLANKS);
    public static final RegistryObject<Item> HONEYED_STAIRS = registerSimpleBlockItem("honeyed_stairs", BlocksRegistration.HONEYED_STAIRS);
    public static final RegistryObject<Item> HONEYED_SLAB = registerSimpleBlockItem("honeyed_slab", BlocksRegistration.HONEYED_SLAB);
    public static final RegistryObject<Item> HONEYED_FENCE = registerSimpleBlockItem("honeyed_fence", BlocksRegistration.HONEYED_FENCE);
    public static final RegistryObject<Item> HONEYED_FENCE_GATE = registerSimpleBlockItem("honeyed_fence_gate", BlocksRegistration.HONEYED_FENCE_GATE);
    public static final RegistryObject<Item> HONEYED_BUTTON = registerSimpleBlockItem("honeyed_button", BlocksRegistration.HONEYED_BUTTON);
    public static final RegistryObject<Item> HONEYED_PRESSURE_PLATE = registerSimpleBlockItem("honeyed_pressure_plate", BlocksRegistration.HONEYED_PRESSURE_PLATE);
    public static final RegistryObject<BlockItem> HONEYED_DOOR = register("honeyed_door", () -> new DoubleHighBlockItem(BlocksRegistration.HONEYED_DOOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> HONEYED_TRAPDOOR = registerSimpleBlockItem("honeyed_trapdoor", BlocksRegistration.HONEYED_TRAPDOOR);
    public static final RegistryObject<Item> HONEYED_SIGN = register("honeyed_sign",
            () -> new SignItem(new Item.Properties().stacksTo(16), BlocksRegistration.HONEYED_SIGN.get(), BlocksRegistration.HONEYED_WALL_SIGN.get()));
    public static final RegistryObject<Item> HONEYED_HANGING_SIGN = register("honeyed_hanging_sign",
            () -> new HangingSignItem(BlocksRegistration.HONEYED_HANGING_SIGN.get(), BlocksRegistration.HONEYED_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));

    private static <T extends Item> RegistryObject<T> register(String name, Supplier<T> itemSupplier) {
        var register = ITEMS.register(name, itemSupplier);
        CREATIVE_TAB_ITEMS.add(register);
        return register;
    }

    private static RegistryObject<Item> registerSimpleItem(String name) {
        return register(name, () -> new Item(new Item.Properties()));
    }

    private static <T extends Block> RegistryObject<Item> registerSimpleBlockItem(String name, RegistryObject<T> block) {
        return register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }
}
