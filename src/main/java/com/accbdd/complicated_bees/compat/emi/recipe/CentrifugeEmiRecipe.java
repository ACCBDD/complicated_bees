package com.accbdd.complicated_bees.compat.emi.recipe;

import com.accbdd.complicated_bees.compat.emi.ComplicatedBeesEMI;
import com.accbdd.complicated_bees.recipe.CentrifugeRecipe;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class CentrifugeEmiRecipe implements EmiRecipe {
    private final ResourceLocation id;
    private final EmiIngredient input;
    private final List<EmiStack> outputs;

    public CentrifugeEmiRecipe(CentrifugeRecipe recipe) {
        this.id = recipe.getId();

        this.input = EmiStack.of(recipe.getInput());
        this.outputs = recipe.getOutputs()
                .stream()
                .map(p -> EmiStack.of(p.getStack()).setChance(p.getChance()))
                .toList();
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return ComplicatedBeesEMI.CENTRIFUGE_CATEGORY;
    }

    @Override
    public @Nullable ResourceLocation getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return List.of(input);
    }

    @Override
    public List<EmiStack> getOutputs() {
        return outputs;
    }

    @Override
    public int getDisplayWidth() {
        return 128;
    }

    @Override
    public int getDisplayHeight() {
        return 64;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(new ResourceLocation(MODID, "textures/gui/jei/centrifuge_products.png"), 0, 0, 128, 64, 0, 0, 128, 64, 128, 64);

        widgets.addSlot(input, 14, 23);

        for (int i = 0; i < outputs.size(); i++) {
            widgets.addSlot(outputs.get(i), 60 + 18 * (i % 3), 5 + 18 * (i / 3))
                    .recipeContext(this);
        }
    }
}
