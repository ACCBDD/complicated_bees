package com.accbdd.complicated_bees.registry;

import com.accbdd.complicated_bees.genetics.*;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ComplicatedBeesCodecs {
    //hex string parser (no alpha)
    public static final Codec<Integer> HEX_STRING_CODEC = Codec.STRING.comapFlatMap(
            str -> {
                try {
                    return DataResult.success(Integer.valueOf(str, 16));
                } catch (NumberFormatException e) {
                    return DataResult.error(() -> str + " is not hexadecimal.");
                }
            },
            Integer::toHexString
    );

    //BeeProducts parser
    public static final Codec<BeeProducts> BEE_PRODUCTS_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    ItemStack.ITEM_WITH_COUNT_CODEC.fieldOf("primary").forGetter(BeeProducts::getPrimary),
                    Codec.FLOAT.optionalFieldOf("primary_chance", 1.0f).forGetter(BeeProducts::getPrimaryChance),
                    ItemStack.ITEM_WITH_COUNT_CODEC.optionalFieldOf("secondary", Items.AIR.getDefaultInstance()).forGetter(BeeProducts::getSecondary),
                    Codec.FLOAT.optionalFieldOf("secondary_chance", 1.0f).forGetter(BeeProducts::getSecondaryChance),
                    ItemStack.ITEM_WITH_COUNT_CODEC.optionalFieldOf("specialty", Items.AIR.getDefaultInstance()).forGetter(BeeProducts::getSpecialty),
                    Codec.FLOAT.optionalFieldOf("specialty_chance", 1.0f).forGetter(BeeProducts::getSpecialtyChance)
            ).apply(instance, BeeProducts::new)
    );

    //CombProducts parser
    public static final Codec<CombProducts> COMB_PRODUCTS_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    ItemStack.ITEM_WITH_COUNT_CODEC.fieldOf("primary").forGetter(CombProducts::getPrimary),
                    Codec.FLOAT.optionalFieldOf("primary_chance", 1.0f).forGetter(CombProducts::getPrimaryChance),
                    ItemStack.ITEM_WITH_COUNT_CODEC.optionalFieldOf("secondary", Items.AIR.getDefaultInstance()).forGetter(CombProducts::getSecondary),
                    Codec.FLOAT.optionalFieldOf("secondary_chance", 1.0f).forGetter(CombProducts::getSecondaryChance)
            ).apply(instance, CombProducts::new)
    );

    public static final Codec<Species> SPECIES_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    HEX_STRING_CODEC.fieldOf("color").forGetter(Species::getColor),
                    BEE_PRODUCTS_CODEC.fieldOf("products").forGetter(Species::getProducts),
                    CompoundTag.CODEC.optionalFieldOf("genome", new Chromosome().serialize()).forGetter((species -> species.getDefaultChromosome().serialize()))
            ).apply(instance, Species::new)
    );

    public static final Codec<Comb> COMB_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("id").forGetter(Comb::getId),
                    HEX_STRING_CODEC.fieldOf("outer_color").forGetter(Comb::getOuterColor),
                    HEX_STRING_CODEC.fieldOf("inner_color").forGetter(Comb::getInnerColor),
                    COMB_PRODUCTS_CODEC.fieldOf("products").forGetter(Comb::getProducts)
            ).apply(instance, Comb::new)
    );
}
