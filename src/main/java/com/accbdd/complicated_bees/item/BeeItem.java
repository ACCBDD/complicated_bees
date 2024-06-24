package com.accbdd.complicated_bees.item;

import com.accbdd.complicated_bees.genetics.GenomeHelper;
import com.accbdd.complicated_bees.genetics.Species;
import com.accbdd.complicated_bees.genetics.gene.GeneSpecies;
import com.accbdd.complicated_bees.registry.SpeciesRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class BeeItem extends Item {
    public static final String AGE_TAG = "bee_age";

    public BeeItem(Properties prop) {
        super(prop);
    }

    public static int getAge(ItemStack stack) {
        return stack.getOrCreateTag().getInt(AGE_TAG);
    }

    public static void setAge(ItemStack stack, int age) {
        stack.getOrCreateTag().putInt(AGE_TAG, age);
    }

    @Override
    public @NotNull Component getName(ItemStack stack) {
        return Component.translatable("species.complicated_bees." +
                (getSpeciesResourceLocation(stack) == null ? "invalid" : getSpeciesResourceLocation(stack).toString()))
                .append(" ")
                .append(Component.translatable(getDescriptionId()));
    }

    public static int getItemColor(ItemStack stack, int tintIndex) {
        if (tintIndex == 1) {
            ResourceLocation speciesLocation = getSpeciesResourceLocation(stack);
            Registry<Species> registry = Objects.requireNonNull(Minecraft.getInstance().getConnection()).registryAccess().registry(SpeciesRegistry.SPECIES_REGISTRY_KEY).get();
            if (speciesLocation != null) {
                return registry.containsKey(speciesLocation) ? Objects.requireNonNull(registry.get(speciesLocation)).getColor() : 0xFFFFFF;
            }
            return 0;
        }
        return 0xFFFFFF;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level pLevel, @NotNull List<Component> components, @NotNull TooltipFlag isAdvanced) {
        GeneSpecies geneSpecies = (GeneSpecies) GenomeHelper.getGene(stack, new ResourceLocation(MODID, GeneSpecies.TAG), true);
        if (geneSpecies == null) {
            //broken nbt
            components.add(Component.literal("INVALID ITEM"));
        } else if (geneSpecies.get() == null) {
            //species doesn't exist in registry
            components.add(Component.literal("INVALID SPECIES"));
        } else if (Minecraft.getInstance().level != null) {
            Species species = geneSpecies.get();
            ItemStack primary = species.getProducts().getPrimary();
            ItemStack secondary = species.getProducts().getSecondary();
            ItemStack specialty = species.getProducts().getSpecialty();
            components.add(Component.translatable("gui.complicated_bees.primary_produce").append(": ").append(primary.getHoverName()).append(String.format(" @ %.0f%%", species.getProducts().getPrimaryChance()*100)));
            if (secondary.getItem() != Items.AIR)
                components.add(Component.translatable("gui.complicated_bees.secondary_produce").append(": ").append(secondary.getHoverName()).append(String.format(" @ %.0f%%", species.getProducts().getSecondaryChance()*100)));
            if (specialty.getItem() != Items.AIR)
                components.add(Component.translatable("gui.complicated_bees.specialty_produce").append(": ").append(specialty.getHoverName()).append(String.format(" @ %.0f%%", species.getProducts().getSpecialtyChance()*100)));
        }
        super.appendHoverText(stack, pLevel, components, isAdvanced);
    }

    private static ResourceLocation getSpeciesResourceLocation(ItemStack stack) {
        Species species = ((GeneSpecies) GenomeHelper.getGene(stack, GeneSpecies.ID, true)).get();
        RegistryAccess registryAccess = (Minecraft.getInstance().getConnection() == null) ? ServerLifecycleHooks.getCurrentServer().registryAccess() : Minecraft.getInstance().getConnection().registryAccess();
        return registryAccess.registry(SpeciesRegistry.SPECIES_REGISTRY_KEY).get().getKey(species);
    }
}
