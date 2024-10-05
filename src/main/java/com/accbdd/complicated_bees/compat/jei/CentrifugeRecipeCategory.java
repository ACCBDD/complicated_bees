package com.accbdd.complicated_bees.compat.jei;

import com.accbdd.complicated_bees.genetics.Product;
import com.accbdd.complicated_bees.recipe.CentrifugeRecipe;
import com.accbdd.complicated_bees.registry.ItemsRegistration;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class CentrifugeRecipeCategory implements IRecipeCategory<CentrifugeRecipe> {

    public static final ResourceLocation ID = new ResourceLocation(MODID, "jei/comb_product");
    public static final RecipeType<CentrifugeRecipe> TYPE = new RecipeType<>(ID, CentrifugeRecipe.class);

    private static final Component TITLE = Component.translatable("gui.complicated_bees.jei.centrifuge");

    public final IDrawable icon;
    public final IDrawable BACKGROUND = ComplicatedBeesJEI.createDrawable(new ResourceLocation(MODID, "textures/gui/jei/centrifuge_products.png"), 0, 0, 128, 64, 128, 64);

    public CentrifugeRecipeCategory(IGuiHelper helper) {
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ItemsRegistration.CENTRIFUGE.get()));
    }

    @Override
    public RecipeType<CentrifugeRecipe> getRecipeType() {
        return TYPE;
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
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CentrifugeRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 15, 24)
                .setSlotName("input")
                .addIngredient(VanillaTypes.ITEM_STACK, recipe.getInput());

        for (int i = 0; i < recipe.getOutputs().size(); i++) {
            Product product = recipe.getOutputs().get(i);
            builder.addSlot(RecipeIngredientRole.OUTPUT, 61 + 18 * (i % 3), 6 + 18 * (i / 3))
                    .setSlotName("output")
                    .addIngredient(VanillaTypes.ITEM_STACK, product.getStack())
                    .addRichTooltipCallback(new ChanceTooltipCallback(product.getChance()));
        }
    }
}
