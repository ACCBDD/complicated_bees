package com.accbdd.complicated_bees.compat.emi.recipe;

import com.accbdd.complicated_bees.ComplicatedBees;
import com.accbdd.complicated_bees.compat.emi.ComplicatedBeesEMI;
import com.accbdd.complicated_bees.genetics.Species;
import com.accbdd.complicated_bees.registry.ItemsRegistration;
import com.accbdd.complicated_bees.registry.SpeciesRegistration;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Stream;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class BeeProduceEmiRecipe implements EmiRecipe {
    private final ResourceLocation id;
    private final EmiIngredient beeInput;
    private final List<EmiStack> products;
    private final List<EmiStack> specialtyProducts;

    public BeeProduceEmiRecipe(Species species) {
        ResourceLocation speciesId = Minecraft.getInstance().level.registryAccess().registryOrThrow(SpeciesRegistration.SPECIES_REGISTRY_KEY).getKey(species);
        this.id = new ResourceLocation(ComplicatedBees.MODID, "/bee_produce/" + speciesId.toString().replace(":", "/"));

        this.beeInput = EmiStack.of(species.toStack(ItemsRegistration.QUEEN));

        this.products = species.getProducts().stream().map(p -> EmiStack.of(p.getStack()).setChance(p.getChance())).toList();
        this.specialtyProducts = species.getSpecialtyProducts().stream().map(p -> EmiStack.of(p.getStack()).setChance(p.getChance())).toList();
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return ComplicatedBeesEMI.BEE_PRODUCE_CATEGORY;
    }

    @Override
    public @Nullable ResourceLocation getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return List.of(beeInput);
    }

    @Override
    public List<EmiStack> getOutputs() {
        return Stream.concat(products.stream(), specialtyProducts.stream()).toList();
    }

    @Override
    public int getDisplayWidth() {
        return 160;
    }

    @Override
    public int getDisplayHeight() {
        return 64;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(new ResourceLocation(MODID, "textures/gui/jei/bee_products.png"), 0, 0, 160, 64, 0, 0, 160, 64, 160, 64);

        widgets.addSlot(beeInput, 13, 23)
                .drawBack(false);

        for (int i = 0; i < products.size(); i++) {
            widgets.addSlot(products.get(i), 65 + 18 * i, 13)
                    .recipeContext(this);
        }
        for (int i = 0; i < specialtyProducts.size(); i++) {
            widgets.addSlot(specialtyProducts.get(i), 65 + 18 * i, 34)
                    .recipeContext(this);
        }
    }
}
