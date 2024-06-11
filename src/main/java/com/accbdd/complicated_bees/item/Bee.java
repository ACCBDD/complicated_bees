package com.accbdd.complicated_bees.item;

import com.accbdd.complicated_bees.genetics.Species;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static com.accbdd.complicated_bees.genetics.SpeciesRegistry.SPECIES_REGISTRY;

public class Bee extends Item {
    public Bee(Properties prop) {
        super(prop);
    }

    public static Species getSpecies(ItemStack stack) {
        //get species string from nbt, return species from registry
        return SPECIES_REGISTRY.get(ResourceLocation.tryParse(stack.getOrCreateTag().getString("species")));
    }

    public static ItemStack setSpecies(ItemStack stack, ResourceLocation species) {
        stack.getOrCreateTag().putString("species", species.toString());
        return stack;
    }

    @Override
    public @NotNull Component getName(ItemStack stack) {
        return Component.translatable("species.complicated_bees." +
                stack.getOrCreateTag().getString("species"))
                .append(" ")
                .append(Component.translatable(getDescriptionId()));
    }

    public static int getItemColor(ItemStack stack, int tintIndex) {
        if (tintIndex == 1) {
            ResourceLocation speciesLocation = ResourceLocation.tryParse(stack.getOrCreateTag().getString("species"));
            if (speciesLocation != null) {
                return SPECIES_REGISTRY.containsKey(speciesLocation) ? Objects.requireNonNull(SPECIES_REGISTRY.get(speciesLocation)).getColor() : 0xFFFFFF;
            }
            return 0;
        }
        return 0xFFFFFF;
    }
}
