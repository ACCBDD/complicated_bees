package com.accbdd.complicated_bees.compat.jei;

import com.accbdd.complicated_bees.genetics.mutation.IMutationCondition;
import com.accbdd.complicated_bees.genetics.mutation.Mutation;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.ParametersAreNonnullByDefault;

import java.util.ArrayList;
import java.util.List;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class MutationRecipeCategory implements IRecipeCategory<Mutation> {

    public static final ResourceLocation ID = new ResourceLocation(MODID, "jei/mutation");
    public static final RecipeType<Mutation> TYPE = new RecipeType<>(ID, Mutation.class);

    private static final Component TITLE = Component.translatable("gui.complicated_bees.jei.mutations");

    public final IDrawable ICON = ComplicatedBeesJEI.createDrawable(new ResourceLocation(MODID, "textures/item/bee.png"), 0, 0, 16, 16, 16, 16);
    public final IDrawable BACKGROUND = ComplicatedBeesJEI.createDrawable(new ResourceLocation(MODID, "textures/gui/jei/mutations.png"), 0, 0, 143, 40, 143, 40);

    @Override
    public RecipeType<Mutation> getRecipeType() {
        return TYPE;
    }

    @Override
    public void draw(Mutation recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
        String chanceString = recipe.getConditions().isEmpty() ? String.format("%.0f%%", recipe.getChance() * 100) : String.format("[%.0f%%]", recipe.getChance() * 100);
        guiGraphics.drawCenteredString(Minecraft.getInstance().font, chanceString, 95, 1, 0xFFFFFF);
    }

    @Override
    public List<Component> getTooltipStrings(Mutation recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        List<Component> tips = new ArrayList<>();
        if (mouseX >= 81 && mouseX <= 106 && mouseY >= 1 && mouseY <= 10) {
            for (IMutationCondition condition : recipe.getConditions()) {
                tips.add(condition.getDescription());
            }
        }
        return tips;
    }

    @Override
    public Component getTitle() {
        return TITLE;
    }

    @Override
    public IDrawable getBackground() {
        return BACKGROUND;
    }

    @Override
    public IDrawable getIcon() {
        return ICON;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, Mutation mutation, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 12, 12)
                .setSlotName("first_species")
                .addIngredients(VanillaTypes.ITEM_STACK, mutation.getFirstSpecies().toMembers());

        builder.addSlot(RecipeIngredientRole.INPUT, 59, 12)
                .setSlotName("second_species")
                .addIngredients(VanillaTypes.ITEM_STACK, mutation.getSecondSpecies().toMembers());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 115, 12)
                .setSlotName("output_species")
                .addIngredients(VanillaTypes.ITEM_STACK, mutation.getResultSpecies().toMembers());
    }
}
