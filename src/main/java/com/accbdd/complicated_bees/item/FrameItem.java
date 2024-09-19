package com.accbdd.complicated_bees.item;

import com.accbdd.complicated_bees.genetics.BeeHousingModifier;
import com.accbdd.complicated_bees.genetics.gene.enums.EnumTolerance;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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
                components.add(Component.translatable("gene.complicated_bees.lifespan_label")
                        .append(": ")
                        .append(Component.literal(modifier.getLifespanMod() + "x"))
                        .withStyle(ChatFormatting.GRAY));
            if (modifier.getProductivityMod() != 1)
                components.add(Component.translatable("gene.complicated_bees.productivity_label")
                        .append(": ")
                        .append(Component.literal(modifier.getProductivityMod() + "x"))
                        .withStyle(ChatFormatting.GRAY));
            if (!modifier.getTemperatureMod().equals(EnumTolerance.NONE))
                components.add(Component.translatable("gene.complicated_bees.temperature_label")
                        .append(": ")
                        .append(modifier.getTemperatureMod().getTranslationKey())
                        .withStyle(ChatFormatting.GRAY));
            if (!modifier.getHumidityMod().equals(EnumTolerance.NONE))
                components.add(Component.translatable("gene.complicated_bees.humidity_label")
                        .append(": ")
                        .append(modifier.getHumidityMod().getTranslationKey())
                        .withStyle(ChatFormatting.GRAY));
        }
    }
}

