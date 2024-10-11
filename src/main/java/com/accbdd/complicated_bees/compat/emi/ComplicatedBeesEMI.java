package com.accbdd.complicated_bees.compat.emi;

import com.accbdd.complicated_bees.genetics.GeneticHelper;
import com.accbdd.complicated_bees.genetics.gene.GeneSpecies;
import com.accbdd.complicated_bees.genetics.mutation.Mutation;
import com.accbdd.complicated_bees.registry.ItemsRegistration;
import com.accbdd.complicated_bees.registry.MutationRegistration;
import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.Comparison;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

@EmiEntrypoint
public class ComplicatedBeesEMI implements EmiPlugin {
    public static final EmiStack CENTRIFUGE = EmiStack.of(ItemsRegistration.CENTRIFUGE);
    public static final EmiStack APIARY = EmiStack.of(ItemsRegistration.APIARY);
    public static final EmiRecipeCategory CENTRIFUGE_CATEGORY = new EmiRecipeCategory(new ResourceLocation(MODID, "centrifuge"), CENTRIFUGE);
    public static final EmiRecipeCategory BEE_PRODUCE_CATEGORY = new EmiRecipeCategory(new ResourceLocation(MODID, "bee_produce"), APIARY);
    public static final EmiRecipeCategory MUTATION_CATEGORY = new EmiRecipeCategory(new ResourceLocation(MODID, "mutation"), APIARY);
    public static final Comparison COMPARE_BEE
            = Comparison.compareData(stack -> GeneticHelper.getSpecies(stack.getItemStack(), true));
    @Override
    public void register(EmiRegistry registry) {
        registry.setDefaultComparison(ItemsRegistration.DRONE, COMPARE_BEE);
        registry.setDefaultComparison(ItemsRegistration.PRINCESS, COMPARE_BEE);
        registry.setDefaultComparison(ItemsRegistration.QUEEN, COMPARE_BEE);

        registry.addCategory(CENTRIFUGE_CATEGORY);
        registry.addWorkstation(CENTRIFUGE_CATEGORY, CENTRIFUGE);
        registry.addCategory(BEE_PRODUCE_CATEGORY);
        registry.addWorkstation(BEE_PRODUCE_CATEGORY, APIARY);
        registry.addCategory(MUTATION_CATEGORY);
        registry.addWorkstation(MUTATION_CATEGORY, APIARY);

        for (Mutation mutation : Minecraft.getInstance().getConnection().registryAccess().registry(MutationRegistration.MUTATION_REGISTRY_KEY).get().stream().toList()) {
            registry.addRecipe(new MutationRecipe(mutation));
        }
    }
}
