package com.accbdd.complicated_bees.item;

import com.accbdd.complicated_bees.block.entity.ApiaryBlockEntity;
import com.accbdd.complicated_bees.screen.AnalyzerMenu;
import com.accbdd.complicated_bees.screen.ApiaryMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class AnalyzerItem extends Item {
    public static String SCREEN_ANALYZER = "gui.complicated_bees.analyzer";

    public AnalyzerItem(Properties pProperties) {
        super(pProperties.stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand pUsedHand) {
        if (!level.isClientSide && pUsedHand == InteractionHand.MAIN_HAND) {
            MenuProvider containerProvider = new MenuProvider() {
                @Override
                public Component getDisplayName() {
                    return Component.translatable(SCREEN_ANALYZER);
                }

                @Override
                public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player player) {
                    return new AnalyzerMenu(windowId, player, playerInventory.selected);
                }
            };
            player.openMenu(containerProvider, (buf -> buf.writeInt(player.getInventory().selected)));
        }
        return InteractionResultHolder.sidedSuccess(player.getItemInHand(pUsedHand), true);
    }
}
