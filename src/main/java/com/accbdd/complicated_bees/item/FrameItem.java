package com.accbdd.complicated_bees.item;

import com.accbdd.complicated_bees.genetics.BeeHousingModifier;
import com.accbdd.complicated_bees.genetics.Chromosome;
import com.accbdd.complicated_bees.genetics.GeneticHelper;
import com.accbdd.complicated_bees.genetics.gene.*;
import com.accbdd.complicated_bees.genetics.gene.enums.EnumHumidity;
import com.accbdd.complicated_bees.genetics.gene.enums.EnumProductivity;
import com.accbdd.complicated_bees.genetics.gene.enums.EnumTemperature;
import com.accbdd.complicated_bees.genetics.gene.enums.EnumTolerance;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class FrameItem extends Item {
    private final BeeHousingModifier modifier;

    public FrameItem(Properties pProperties, BeeHousingModifier modifier) {
        super(pProperties);
        this.modifier = modifier;
    }

    public BeeHousingModifier getModifier() {
        return modifier;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag pIsAdvanced) {
        if (Minecraft.getInstance().level != null) {
            if (modifier.getLifespanMod() != 1)
                components.add(Component.translatable("gui.complicated_bees.lifespan_label")
                        .append(": ")
                        .append(Component.literal(modifier.getLifespanMod() + "x"))
                        .withStyle(ChatFormatting.GRAY));
            if (modifier.getProductivityMod() != 1)
                components.add(Component.translatable("gui.complicated_bees.productivity_label")
                        .append(": ")
                        .append(Component.literal(modifier.getProductivityMod() + "x"))
                        .withStyle(ChatFormatting.GRAY));
            if (!modifier.getTemperatureMod().equals(EnumTolerance.NONE))
                components.add(Component.translatable("gui.complicated_bees.temperature_label")
                        .append(": ")
                        .append(modifier.getTemperatureMod().getTranslationKey())
                        .withStyle(ChatFormatting.GRAY));
            if (!modifier.getHumidityMod().equals(EnumTolerance.NONE))
                components.add(Component.translatable("gui.complicated_bees.humidity_label")
                        .append(": ")
                        .append(modifier.getHumidityMod().getTranslationKey())
                        .withStyle(ChatFormatting.GRAY));
        }
    }
}

