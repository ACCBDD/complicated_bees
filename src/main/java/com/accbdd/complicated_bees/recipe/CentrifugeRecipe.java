package com.accbdd.complicated_bees.recipe;

import com.accbdd.complicated_bees.genetics.Product;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.List;

public class CentrifugeRecipe implements Recipe<Container> {
    private final Ingredient input;
    private final List<Product> outputs;

    public CentrifugeRecipe(Ingredient input, List<Product> outputs) {
        this.input = input;
        this.outputs = outputs;
    }

    @Override
    public boolean matches(Container pContainer, Level pLevel) {
        return input.test(pContainer.getItem(0));
    }

    @Override
    public ItemStack assemble(Container pContainer, RegistryAccess pRegistryAccess) {
        throw new UnsupportedOperationException("Centrifuge recipes do not use assemble! Use getOutputs instead");
    }

    public List<Product> getOutputs(Container pContainer, RegistryAccess pRegistryAccess) {
        return outputs;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        throw new UnsupportedOperationException("Centrifuge recipes do not use getResultItem! Use getOutputs instead");
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return null;
    }

    @Override
    public RecipeType<?> getType() {
        return null;
    }
}
