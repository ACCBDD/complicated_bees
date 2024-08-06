package com.accbdd.complicated_bees.compat.jei;

import com.accbdd.complicated_bees.genetics.Comb;
import com.accbdd.complicated_bees.genetics.GeneticHelper;
import com.accbdd.complicated_bees.genetics.Species;
import com.accbdd.complicated_bees.genetics.gene.GeneSpecies;
import com.accbdd.complicated_bees.item.CombItem;
import com.accbdd.complicated_bees.registry.EsotericRegistration;
import com.accbdd.complicated_bees.registry.ItemsRegistration;
import com.accbdd.complicated_bees.registry.MutationRegistration;
import com.accbdd.complicated_bees.registry.SpeciesRegistration;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.neoforged.neoforge.common.util.Lazy;
import org.jetbrains.annotations.NotNull;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

@JeiPlugin
public class ComplicatedBeesJEI implements IModPlugin {
    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return new ResourceLocation(MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new CentrifugeRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new BeeProduceRecipeCategory());
        registration.addRecipeCategories(new MutationRecipeCategory());
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager manager = Minecraft.getInstance().getConnection().getRecipeManager();
        registration.addRecipes(CentrifugeRecipeCategory.TYPE, manager.getAllRecipesFor(EsotericRegistration.CENTRIFUGE_RECIPE.get()).stream().map(RecipeHolder::value).toList());
        registration.addRecipes(BeeProduceRecipeCategory.TYPE, Minecraft.getInstance().getConnection().registryAccess().registry(SpeciesRegistration.SPECIES_REGISTRY_KEY).get().stream().toList());
        registration.addRecipes(MutationRecipeCategory.TYPE, Minecraft.getInstance().getConnection().registryAccess().registry(MutationRegistration.MUTATION_REGISTRY_KEY).get().stream().toList());
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        IIngredientSubtypeInterpreter<ItemStack> speciesInterpreter = (stack, context) -> {
            Lazy<Species> species = Lazy.of(() -> ((GeneSpecies) GeneticHelper.getChromosome(stack, true).getGene(GeneSpecies.ID)).get());
            return Minecraft.getInstance().getConnection().registryAccess().registry(SpeciesRegistration.SPECIES_REGISTRY_KEY).get().getKey(species.get()).toString();
        };

        IIngredientSubtypeInterpreter<ItemStack> combInterpreter = (stack, context) -> {
            Lazy<Comb> comb = Lazy.of(() -> CombItem.getComb(stack));
            return comb.get().toString();
        };
        registration.registerSubtypeInterpreter(ItemsRegistration.DRONE.get(), speciesInterpreter);
        registration.registerSubtypeInterpreter(ItemsRegistration.QUEEN.get(), speciesInterpreter);
        registration.registerSubtypeInterpreter(ItemsRegistration.PRINCESS.get(), speciesInterpreter);
        registration.registerSubtypeInterpreter(ItemsRegistration.COMB.get(), combInterpreter);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(ItemsRegistration.APIARY.get().getDefaultInstance(), BeeProduceRecipeCategory.TYPE);
        registration.addRecipeCatalyst(ItemsRegistration.APIARY.get().getDefaultInstance(), MutationRecipeCategory.TYPE);
        registration.addRecipeCatalyst(ItemsRegistration.CENTRIFUGE.get().getDefaultInstance(), CentrifugeRecipeCategory.TYPE);
    }

    public static IDrawable createDrawable(ResourceLocation location, int uOffset, int vOffset, int width, int height, int textureWidth, int textureHeight) {
        return new IDrawable() {
            @Override
            public int getWidth() {
                return width;
            }

            @Override
            public int getHeight() {
                return height;
            }

            @Override
            public void draw(GuiGraphics guiGraphics, int xOffset, int yOffset) {
                guiGraphics.blit(location, xOffset, yOffset, uOffset, vOffset, getWidth(), getHeight(), textureWidth, textureHeight);
            }
        };
    }
}
