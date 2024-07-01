package com.accbdd.complicated_bees.utils;

import com.accbdd.complicated_bees.genetics.*;
import com.accbdd.complicated_bees.genetics.mutation.Mutation;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;

public class ComplicatedBeesCodecs {
    //todo: move these into appropriate classes
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
                    Codec.BOOL.optionalFieldOf("dominant", true).forGetter(Species::isDominant),
                    HEX_STRING_CODEC.fieldOf("color").forGetter(Species::getColor),
                    BeeProduct.CODEC.listOf().optionalFieldOf("products", new ArrayList<>()).forGetter(Species::getProducts),
                    BeeProduct.CODEC.listOf().optionalFieldOf("specialty_products", new ArrayList<>()).forGetter(Species::getSpecialtyProducts),
                    CompoundTag.CODEC.optionalFieldOf("default_chromosome", new Chromosome().serialize()).forGetter(species -> species.getDefaultChromosome().serialize())
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

    public static final Codec<Mutation> MUTATION_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    ResourceLocation.CODEC.fieldOf("first").forGetter(Mutation::getFirst),
                    ResourceLocation.CODEC.fieldOf("second").forGetter(Mutation::getSecond),
                    ResourceLocation.CODEC.fieldOf("result").forGetter(Mutation::getResult),
                    Codec.FLOAT.fieldOf("chance").forGetter(Mutation::getChance)
            ).apply(instance, Mutation::new)
    );
}
