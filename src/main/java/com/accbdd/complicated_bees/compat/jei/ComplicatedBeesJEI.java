package com.accbdd.complicated_bees.compat.jei;

import com.accbdd.complicated_bees.genetics.Comb;
import com.accbdd.complicated_bees.genetics.GeneticHelper;
import com.accbdd.complicated_bees.genetics.Species;
import com.accbdd.complicated_bees.genetics.gene.GeneSpecies;
import com.accbdd.complicated_bees.item.CombItem;
import com.accbdd.complicated_bees.registry.CombRegistry;
import com.accbdd.complicated_bees.registry.ItemsRegistration;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
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
        registration.addRecipeCategories(new CombProductRecipeCategory());
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(CombProductRecipeCategory.TYPE, Minecraft.getInstance().getConnection().registryAccess().registry(CombRegistry.COMB_REGISTRY_KEY).get().stream().toList());
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        IIngredientSubtypeInterpreter<ItemStack> speciesInterpreter = (stack, context) -> {
            Lazy<Species> species = Lazy.of(() -> ((GeneSpecies) GeneticHelper.getGenome(stack, true).getGene(GeneSpecies.ID)).get());
            return species.toString();
        };

        IIngredientSubtypeInterpreter<ItemStack> combInterpreter = (stack, context) -> {
            Lazy<Comb> comb = Lazy.of(() -> CombItem.getComb(stack));
            return comb.get().getId();
        };
        registration.registerSubtypeInterpreter(ItemsRegistration.DRONE.get(), speciesInterpreter);
        registration.registerSubtypeInterpreter(ItemsRegistration.QUEEN.get(), speciesInterpreter);
        registration.registerSubtypeInterpreter(ItemsRegistration.PRINCESS.get(), speciesInterpreter);
        registration.registerSubtypeInterpreter(ItemsRegistration.COMB.get(), combInterpreter);
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
