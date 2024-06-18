package com.accbdd.complicated_bees.item;

import com.accbdd.complicated_bees.ComplicatedBees;
import com.accbdd.complicated_bees.genetics.Comb;
import com.accbdd.complicated_bees.registry.CombRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class CombItem extends Item {

    public static final String COMB_TYPE_TAG = "comb_type";

    public CombItem(Properties pProperties) {
        super(pProperties);
    }

    public static Comb getComb(ItemStack stack) {
        //get comb string from nbt, return comb from registry
        if (FMLEnvironment.dist.isClient()) {
            if (Minecraft.getInstance().getConnection() == null) {
                return Comb.NULL;
            }
            Comb comb = Minecraft.getInstance().getConnection().registryAccess().registry(CombRegistry.COMB_REGISTRY_KEY).get().get(ResourceLocation.tryParse(stack.getOrCreateTag().getString(COMB_TYPE_TAG)));
            return comb;
        } else {
            return ServerLifecycleHooks.getCurrentServer().registryAccess().registry(CombRegistry.COMB_REGISTRY_KEY).get().get(ResourceLocation.tryParse(stack.getOrCreateTag().getString(COMB_TYPE_TAG)));
        }
    }

    public static ItemStack setComb(ItemStack stack, ResourceLocation comb) {
        stack.getOrCreateTag().putString(COMB_TYPE_TAG, comb.toString());
        return stack;
    }

    @Override
    public @NotNull Component getName(ItemStack stack) {
        return Component.translatable("comb.complicated_bees." +
                        stack.getOrCreateTag().getString(COMB_TYPE_TAG))
                .append(" ")
                .append(Component.translatable(getDescriptionId()));
    }

    public static int getItemColor(ItemStack stack, int tintIndex) {
        ResourceLocation combLocation = ResourceLocation.tryParse(stack.getOrCreateTag().getString(COMB_TYPE_TAG));
        Registry<Comb> registry = Objects.requireNonNull(Minecraft.getInstance().getConnection()).registryAccess().registry(CombRegistry.COMB_REGISTRY_KEY).get();
        if (combLocation != null) {
            switch (tintIndex) {
                case 0:
                    return registry.containsKey(combLocation) ? Objects.requireNonNull(registry.get(combLocation)).getOuterColor() : 0;
                case 1:
                    return registry.containsKey(combLocation) ? Objects.requireNonNull(registry.get(combLocation)).getInnerColor() : 0;
            }
        }
        return 0xFFFFFF;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level pLevel, @NotNull List<Component> components, @NotNull TooltipFlag isAdvanced) {
        if (Minecraft.getInstance().level != null) {
            Comb comb = getComb(stack);
            ItemStack primary = comb.getProducts().getPrimary();
            ItemStack secondary = comb.getProducts().getSecondary();
            components.add(Component.translatable("gui.complicated_bees.primary_produce").append(": ").append(primary.getHoverName()).append(String.format(" @ %.0f%%", comb.getProducts().getPrimaryChance()*100)));
            if (secondary.getItem() != Items.AIR)
                components.add(Component.translatable("gui.complicated_bees.secondary_produce").append(": ").append(secondary.getHoverName()).append(String.format(" @ %.0f%%", comb.getProducts().getSecondaryChance()*100)));
        }
        super.appendHoverText(stack, pLevel, components, isAdvanced);
    }
}
