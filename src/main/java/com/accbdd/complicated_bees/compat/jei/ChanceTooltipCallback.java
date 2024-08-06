package com.accbdd.complicated_bees.compat.jei;

import mezz.jei.api.gui.ingredient.IRecipeSlotTooltipCallback;
import mezz.jei.api.gui.ingredient.IRecipeSlotView;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.util.List;

public class ChanceTooltipCallback implements IRecipeSlotTooltipCallback {
    private final float chance;

    public ChanceTooltipCallback(float chance) {
        if (chance < 0) {
            chance = 0;
        } else if (chance > 1.0) {
            chance = 1.0f;
        }
        this.chance = chance * 100;
    }

    @Override
    public void onTooltip(IRecipeSlotView recipeSlotView, List<Component> tooltip) {
        Component text = Component.translatable("jei.complicated_bees.chance").append(": ").append(String.format("%.0f%%", chance)).withStyle(ChatFormatting.GRAY);
        tooltip.add(text);
    }
}
