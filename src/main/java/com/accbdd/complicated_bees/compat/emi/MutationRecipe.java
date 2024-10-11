package com.accbdd.complicated_bees.compat.emi;

import com.accbdd.complicated_bees.genetics.mutation.Mutation;
import com.accbdd.complicated_bees.registry.SpeciesRegistration;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class MutationRecipe implements EmiRecipe {
    private final Mutation mutation;

    public MutationRecipe(Mutation mutation) {
        this.mutation = mutation;
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return ComplicatedBeesEMI.MUTATION_CATEGORY;
    }

    @Override
    public @Nullable ResourceLocation getId() {
        return new ResourceLocation(MODID, mutation.getFirst().getPath() + "-" + mutation.getSecond().getPath() + "-" + mutation.getResult().getPath());
    }

    @Override
    public List<EmiIngredient> getInputs() {
        List<EmiIngredient> inputs = new ArrayList<>();
        inputs.add(EmiIngredient.of(new ArrayList<>(mutation.getFirstSpecies().toMembers().stream().map(stack -> EmiIngredient.of(Ingredient.of(stack))).toList())));
        inputs.add(EmiIngredient.of(new ArrayList<>(mutation.getSecondSpecies().toMembers().stream().map(stack -> EmiIngredient.of(Ingredient.of(stack))).toList())));
        return inputs;
    }

    @Override
    public List<EmiIngredient> getCatalysts() {
        return List.of(ComplicatedBeesEMI.APIARY);
    }

    @Override
    public List<EmiStack> getOutputs() {
        return new ArrayList<>(mutation.getResultSpecies().toMembers().stream().map(EmiStack::of).toList());
    }

    @Override
    public int getDisplayWidth() {
        return 143;
    }

    @Override
    public int getDisplayHeight() {
        return 40;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(new ResourceLocation(MODID, "textures/gui/jei/mutations.png"), 0, 0, 143, 40, 143, 40);
        widgets.addSlot(getInputs().get(0), 12, 12);
        widgets.addSlot(getInputs().get(1), 59, 12);
        widgets.addSlot(getOutputs().get(0), 115, 12).recipeContext(this);
    }
}
