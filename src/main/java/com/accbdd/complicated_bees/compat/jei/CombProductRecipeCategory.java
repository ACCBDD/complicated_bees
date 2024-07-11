package com.accbdd.complicated_bees.compat.jei;

import com.accbdd.complicated_bees.genetics.Comb;
import com.accbdd.complicated_bees.genetics.Product;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CombProductRecipeCategory implements IRecipeCategory<Comb> {

    public static final ResourceLocation ID = new ResourceLocation(MODID, "jei/comb_product");
    public static final RecipeType<Comb> TYPE = new RecipeType<>(ID, Comb.class);

    private static final Component TITLE = Component.translatable("gui.complicated_bees.jei.comb_products");

    public final IDrawable ICON = ComplicatedBeesJEI.createDrawable(new ResourceLocation(MODID, "textures/item/comb.png"), 0, 0, 16, 16, 16, 16);
    public final IDrawable BACKGROUND = ComplicatedBeesJEI.createDrawable(new ResourceLocation(MODID, "textures/gui/jei/comb_products.png"), 0, 0, 128, 64, 128, 64);

    @Override
    public RecipeType<Comb> getRecipeType() {
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
        return ICON;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, Comb recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 15, 24)
                .setSlotName("input_comb")
                .addIngredient(VanillaTypes.ITEM_STACK, Comb.toStack(recipe));

        for (int i = 0; i < recipe.getProducts().size(); i++) {
            Product product = recipe.getProducts().get(i);
            builder.addSlot(RecipeIngredientRole.OUTPUT, 61 + 18*(i % 3), 6 + 18*(i / 3))
                    .setSlotName("comb_output")
                    .addIngredient(VanillaTypes.ITEM_STACK, product.getStack())
                    .addTooltipCallback(new ChanceTooltipCallback(product.getChance()));
        }
    }
}
