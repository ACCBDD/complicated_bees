package com.accbdd.complicated_bees.registry;

import com.accbdd.complicated_bees.genetics.Species;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ComplicatedBeesCodecs {
    //hex string parser
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

    public static final Codec<Species> SPECIES_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("id").forGetter(Species::getId),
                    HEX_STRING_CODEC.fieldOf("color").forGetter(Species::getColor),
                    ResourceLocation.CODEC.fieldOf("primary_produce").forGetter((s) -> new ResourceLocation(s.getPrimaryProduce().toString())),
                    ResourceLocation.CODEC.optionalFieldOf("secondary_produce").forGetter((s) -> java.util.Optional.of(new ResourceLocation(s.getSecondaryProduce().toString()))),
                    ResourceLocation.CODEC.optionalFieldOf("specialty_produce").forGetter((s) -> java.util.Optional.of(new ResourceLocation(s.getSpecialtyProduce().toString())))
            ).apply(instance, Species::new)
    );
}
