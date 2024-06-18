package com.accbdd.complicated_bees.item;

import com.accbdd.complicated_bees.genetics.Species;
import com.accbdd.complicated_bees.registry.SpeciesRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class BeeItem extends Item {

    public static final String SPECIES_TAG = "species";

    public BeeItem(Properties prop) {
        super(prop);
    }

    public static Species getSpecies(ItemStack stack) {
        //get species string from nbt, return species from registry
        if (FMLEnvironment.dist.isClient()) {
            if (Minecraft.getInstance().getConnection() == null) {
                return Species.NULL;
            }
            return Minecraft.getInstance().getConnection().registryAccess().registry(SpeciesRegistry.SPECIES_REGISTRY_KEY).get().get(ResourceLocation.tryParse(stack.getOrCreateTag().getString(SPECIES_TAG)));
        } else {
            return ServerLifecycleHooks.getCurrentServer().registryAccess().registry(SpeciesRegistry.SPECIES_REGISTRY_KEY).get().get(ResourceLocation.tryParse(stack.getOrCreateTag().getString(SPECIES_TAG)));
        }
    }

    public static ItemStack setSpecies(ItemStack stack, ResourceLocation species) {
        stack.getOrCreateTag().putString(SPECIES_TAG, species.toString());
        return stack;
    }

    @Override
    public @NotNull Component getName(ItemStack stack) {
        return Component.translatable("species.complicated_bees." +
                stack.getOrCreateTag().getString(SPECIES_TAG))
                .append(" ")
                .append(Component.translatable(getDescriptionId()));
    }

    public static int getItemColor(ItemStack stack, int tintIndex) {
        if (tintIndex == 1) {
            ResourceLocation speciesLocation = ResourceLocation.tryParse(stack.getOrCreateTag().getString(SPECIES_TAG));
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
        if (Minecraft.getInstance().level != null) {
            Species species = getSpecies(stack);
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
}
