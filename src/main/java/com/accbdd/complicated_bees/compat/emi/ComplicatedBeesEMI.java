package com.accbdd.complicated_bees.compat.emi;

import com.accbdd.complicated_bees.registry.ItemsRegistration;
import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.resources.ResourceLocation;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

@EmiEntrypoint
public class ComplicatedBeesEMI implements EmiPlugin {
    public static final EmiRecipeCategory CENTRIFUGE_CATEGORY
            = new EmiRecipeCategory(new ResourceLocation(MODID, "centrifuge"), EmiStack.of(ItemsRegistration.CENTRIFUGE));

    @Override
    public void register(EmiRegistry registry) {
        registry.addCategory(CENTRIFUGE_CATEGORY);
    }
}
