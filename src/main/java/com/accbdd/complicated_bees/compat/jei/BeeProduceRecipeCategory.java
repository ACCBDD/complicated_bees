package com.accbdd.complicated_bees.compat.jei;

import com.accbdd.complicated_bees.genetics.BeeProduct;
import com.accbdd.complicated_bees.genetics.Comb;
import com.accbdd.complicated_bees.genetics.Species;
import com.accbdd.complicated_bees.item.BeeItem;
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

import java.util.List;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BeeProduceRecipeCategory implements IRecipeCategory<Species> {

    public static final ResourceLocation ID = new ResourceLocation(MODID, "jei/bee_product");
    public static final RecipeType<Species> TYPE = new RecipeType<>(ID, Species.class);

    private static final Component TITLE = Component.translatable("gui.complicated_bees.jei.bee_products");

    public final IDrawable ICON = ComplicatedBeesJEI.createDrawable(new ResourceLocation(MODID, "textures/item/bee.png"), 0, 0, 16, 16, 16, 16);
    public final IDrawable BACKGROUND = ComplicatedBeesJEI.createDrawable(new ResourceLocation(MODID, "textures/gui/jei/bee_products.png"), 0, 0, 160, 64, 160, 64);

    @Override
    public RecipeType<Species> getRecipeType() {
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
    public void setRecipe(IRecipeLayoutBuilder builder, Species species, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 14, 24)
                .setSlotName("input_species")
                .addIngredients(VanillaTypes.ITEM_STACK, species.toMembers());

        List<BeeProduct> products = species.getProducts();

        for (int i = 0; i < products.size(); i++) {
            builder.addSlot(RecipeIngredientRole.OUTPUT, 66+(18*i), 14)
                    .setSlotName("output_"+String.valueOf(i))
                    .addIngredient(VanillaTypes.ITEM_STACK, products.get(i).getStack())
                    .addTooltipCallback(new ChanceTooltipCallback(products.get(i).getChance()));
        }
    }
}
