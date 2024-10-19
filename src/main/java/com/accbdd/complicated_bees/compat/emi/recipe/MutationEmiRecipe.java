package com.accbdd.complicated_bees.compat.emi.recipe;

import com.accbdd.complicated_bees.compat.emi.ComplicatedBeesEMI;
import com.accbdd.complicated_bees.genetics.mutation.Mutation;
import com.accbdd.complicated_bees.genetics.mutation.condition.IMutationCondition;
import com.accbdd.complicated_bees.registry.ItemsRegistration;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.TextWidget;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class MutationEmiRecipe implements EmiRecipe {
    private final Mutation mutation;
    private final ResourceLocation id;
    private final EmiIngredient first;
    private final EmiIngredient second;
    private final EmiStack result;
    private final List<EmiStack> extraResults;
    private final List<EmiIngredient> catalysts;

    public MutationEmiRecipe(Mutation mutation) {
        this.mutation = mutation;

        id = new ResourceLocation(MODID,
                "/mutation/first/" +
                mutation.getFirst().toString().replace(":", "/") +
                "/second/" +
                mutation.getSecond().toString().replace(":", "/") +
                "/result/" +
                mutation.getResult().toString().replace(":", "/")
        );
        first = EmiStack.of(mutation.getFirstSpecies().toStack(ItemsRegistration.QUEEN.get()));
        second = EmiStack.of(mutation.getSecondSpecies().toStack(ItemsRegistration.QUEEN.get()));
        result = EmiStack.of(mutation.getResultSpecies().toStack(ItemsRegistration.QUEEN.get())).setChance(mutation.getChance());
        extraResults = new ArrayList<>(List.of(result));
        extraResults.add(EmiStack.of(mutation.getResultSpecies().toStack(ItemsRegistration.PRINCESS.get())));
        extraResults.add(EmiStack.of(mutation.getResultSpecies().toStack(ItemsRegistration.DRONE.get())));
        catalysts = new ArrayList<>(List.of(ComplicatedBeesEMI.APIARY));
        catalysts.addAll(mutation.getFirstSpecies().toMembers().stream().map(EmiStack::of).toList());
        catalysts.addAll(mutation.getSecondSpecies().toMembers().stream().map(EmiStack::of).toList());
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return ComplicatedBeesEMI.MUTATION_CATEGORY;
    }

    @Override
    public @Nullable ResourceLocation getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return List.of(first, second);
    }

    @Override
    public List<EmiIngredient> getCatalysts() {
        return catalysts;
    }

    @Override
    public List<EmiStack> getOutputs() {
        ArrayList<EmiStack> emiStacks = new ArrayList<>(extraResults);
        emiStacks.add(result);
        return emiStacks;
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
        widgets.addTexture(new ResourceLocation(MODID, "textures/gui/jei/mutations.png"), 0, 0, 143, 40, 0, 0, 143, 40, 143, 40);
        widgets.addSlot(first, 11, 11)
                .drawBack(false);
        widgets.addSlot(second, 58, 11)
                .drawBack(false);
        widgets.addSlot(result, 114, 11)
                .drawBack(false)
                .recipeContext(this);

        String chanceString = mutation.getConditions().isEmpty() ? String.format("%.0f%%", mutation.getChance() * 100) : String.format("[%.0f%%]", mutation.getChance() * 100);
        widgets.addText(Component.literal(chanceString), 95, 1, -1, true)
                .horizontalAlign(TextWidget.Alignment.CENTER);

        if (!mutation.getConditions().isEmpty()) {
            List<Component> tips = new ArrayList<>();
            tips.add(Component.translatable("gui.complicated_bees.mutations.has_conditions"));
            for (IMutationCondition condition : mutation.getConditions()) {
                tips.add(condition.getDescription());
            }
            widgets.addTooltipText(tips, 81, 1, 106-81, 10-1);
        }
    }
}
