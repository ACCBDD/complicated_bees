package com.accbdd.complicated_bees.compat.emi;

import com.accbdd.complicated_bees.compat.emi.recipe.BeeProduceEmiRecipe;
import com.accbdd.complicated_bees.compat.emi.recipe.CentrifugeEmiRecipe;
import com.accbdd.complicated_bees.compat.emi.recipe.MutationEmiRecipe;
import com.accbdd.complicated_bees.genetics.GeneticHelper;
import com.accbdd.complicated_bees.item.BeeNestBlockItem;
import com.accbdd.complicated_bees.item.CombItem;
import com.accbdd.complicated_bees.registry.EsotericRegistration;
import com.accbdd.complicated_bees.registry.ItemsRegistration;
import com.accbdd.complicated_bees.registry.MutationRegistration;
import com.accbdd.complicated_bees.registry.SpeciesRegistration;
import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiRenderable;
import dev.emi.emi.api.stack.Comparison;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.client.Minecraft;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

@EmiEntrypoint
public class ComplicatedBeesEMI implements EmiPlugin {
    public static final EmiStack CENTRIFUGE = EmiStack.of(ItemsRegistration.CENTRIFUGE);
    public static final EmiStack APIARY = EmiStack.of(ItemsRegistration.APIARY);
    public static final EmiRecipeCategory CENTRIFUGE_CATEGORY = new ComplicatedBeesRecipeCategory("centrifuge", CENTRIFUGE, Component.translatable("gui.complicated_bees.jei.centrifuge"));
    public static final EmiRecipeCategory BEE_PRODUCE_CATEGORY = new ComplicatedBeesRecipeCategory("bee_produce", APIARY, Component.translatable("gui.complicated_bees.jei.bee_products"));
    public static final EmiRecipeCategory MUTATION_CATEGORY = new ComplicatedBeesRecipeCategory("mutation", APIARY, Component.translatable("gui.complicated_bees.jei.mutations"));
    public static final Comparison COMPARE_BEE
            = Comparison.compareData(stack -> GeneticHelper.getSpecies(stack.getItemStack(), true));
    @Override
    public void register(EmiRegistry registry) {
        registry.setDefaultComparison(ItemsRegistration.DRONE.get(), COMPARE_BEE);
        registry.setDefaultComparison(ItemsRegistration.PRINCESS.get(), COMPARE_BEE);
        registry.setDefaultComparison(ItemsRegistration.QUEEN.get(), COMPARE_BEE);
        registry.setDefaultComparison(ItemsRegistration.COMB.get(), Comparison.compareData(s -> CombItem.getComb(s.getItemStack())));
        registry.setDefaultComparison(ItemsRegistration.BEE_NEST.get(), Comparison.compareData(s -> BeeNestBlockItem.getBlockEntityData(s.getItemStack()).getString("species")));

        registry.addCategory(CENTRIFUGE_CATEGORY);
        registry.addWorkstation(CENTRIFUGE_CATEGORY, CENTRIFUGE);
        registry.addCategory(BEE_PRODUCE_CATEGORY);
        registry.addWorkstation(BEE_PRODUCE_CATEGORY, APIARY);
        registry.addCategory(MUTATION_CATEGORY);
        registry.addWorkstation(MUTATION_CATEGORY, APIARY);

        RecipeManager manager = registry.getRecipeManager();
        RegistryAccess registryAccess = Minecraft.getInstance().level.registryAccess();

        manager.getAllRecipesFor(EsotericRegistration.CENTRIFUGE_RECIPE.get())
                .stream()
                .map(CentrifugeEmiRecipe::new)
                .forEach(registry::addRecipe);

        registryAccess.registryOrThrow(MutationRegistration.MUTATION_REGISTRY_KEY)
                .stream()
                .map(MutationEmiRecipe::new)
                .forEach(registry::addRecipe);

        registryAccess.registryOrThrow(SpeciesRegistration.SPECIES_REGISTRY_KEY)
                .stream()
                .map(BeeProduceEmiRecipe::new)
                .forEach(registry::addRecipe);
    }

    private static class ComplicatedBeesRecipeCategory extends EmiRecipeCategory {
        private final Component name;

        public ComplicatedBeesRecipeCategory(String path, EmiRenderable icon, Component name) {
            super(new ResourceLocation(MODID, path), icon);

            this.name = name;
        }

        @Override
        public Component getName() {
            return name;
        }
    }
}
