package com.accbdd.complicated_bees.item;

import com.accbdd.complicated_bees.genetics.Chromosome;
import com.accbdd.complicated_bees.genetics.GeneticHelper;
import com.accbdd.complicated_bees.genetics.Species;
import com.accbdd.complicated_bees.genetics.gene.GeneHumidity;
import com.accbdd.complicated_bees.genetics.gene.GeneSpecies;
import com.accbdd.complicated_bees.genetics.gene.GeneTemperature;
import com.accbdd.complicated_bees.genetics.gene.GeneTolerant;
import com.accbdd.complicated_bees.genetics.gene.enums.EnumHumidity;
import com.accbdd.complicated_bees.genetics.gene.enums.EnumTemperature;
import com.accbdd.complicated_bees.genetics.gene.enums.EnumTolerance;
import com.accbdd.complicated_bees.registry.GeneRegistry;
import com.accbdd.complicated_bees.registry.SpeciesRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
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
        Species primary = GeneticHelper.getSpecies(stack, true);
        Species secondary = GeneticHelper.getSpecies(stack, false);;
        MutableComponent component = Component.empty();

        component.append(GeneticHelper.getTranslationKey(primary));

        if (!primary.equals(secondary)) {
            component.append("-").append(GeneticHelper.getTranslationKey(secondary));
        }
        component.append(" ").append(Component.translatable(getDescriptionId()));

        return component;
    }

    public static int getItemColor(ItemStack stack, int tintIndex) {
        if (tintIndex == 1) {
            Species species = GeneticHelper.getSpecies(stack, true);
            if (species != null) {
                return species.getColor();
            }
            return 0xFFFFFF;
        }
        return 0xFFFFFF;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level pLevel, @NotNull List<Component> components, @NotNull TooltipFlag isAdvanced) {
        GeneSpecies geneSpecies = (GeneSpecies) GeneticHelper.getGene(stack, new ResourceLocation(MODID, GeneSpecies.TAG), true);
        if (geneSpecies == null) {
            //broken nbt
            components.add(Component.literal("INVALID ITEM"));
        } else if (geneSpecies.get() == null) {
            //species doesn't exist in registry
            components.add(Component.literal("INVALID SPECIES"));
        } else if (!Screen.hasShiftDown()) {
            components.add(Component.translatable("gui.complicated_bees.more_info").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
        } else if (Minecraft.getInstance().level != null) {
            Chromosome primary = GeneticHelper.getChromosome(stack, true);
            components.add(Component.translatable("gui.complicated_bees.temperature_label.short")
                    .append(": ")
                    .append(((EnumTemperature)primary.getGene(GeneTemperature.ID).get()).getTranslationKey())
                    .append(" / ")
                    .append(((GeneTolerant<?>)primary.getGene(GeneTemperature.ID)).getTolerance().getTranslationKey())
                    .withStyle(ChatFormatting.GREEN));
            components.add(Component.translatable("gui.complicated_bees.humidity_label.short")
                    .append(": ")
                    .append(((EnumHumidity)primary.getGene(GeneHumidity.ID).get()).getTranslationKey())
                    .append(" / ")
                    .append(((GeneTolerant<?>)primary.getGene(GeneHumidity.ID)).getTolerance().getTranslationKey())
                    .withStyle(ChatFormatting.GREEN));
        }
        super.appendHoverText(stack, pLevel, components, isAdvanced);
    }
}
