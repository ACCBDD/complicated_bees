package com.accbdd.complicated_bees.item;

import com.accbdd.complicated_bees.bees.mutation.Mutation;
import com.accbdd.complicated_bees.bees.tracking.BreedingTracker;
import com.accbdd.complicated_bees.registry.MutationRegistration;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ResearchNoteItem extends Item {
    public static final String RESEARCH_TAG = "research";

    public ResearchNoteItem() {
        super(new Properties().rarity(Rarity.UNCOMMON));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);
        Mutation toResearch = getFromStack(stack);
        if (!pLevel.isClientSide() && toResearch != null) {
            BreedingTracker tracker = BreedingTracker.getTracker(pPlayer);
            if (tracker == null)
                return InteractionResultHolder.fail(stack);
            if (tracker.isResearched(toResearch)) {
                pPlayer.displayClientMessage(Component.translatable("gui.complicated_bees.already_researched"), true);
                return InteractionResultHolder.fail(stack);
            }
            tracker.research(toResearch);
            pPlayer.getItemInHand(pUsedHand).shrink(1);
            return InteractionResultHolder.sidedSuccess(stack, false);
        }
        return InteractionResultHolder.sidedSuccess(stack, true);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        Mutation mutation = getFromStack(pStack);
        if (mutation != null)
            pTooltipComponents.add(Component.translatable("gui.complicated_bees.research_note_tooltip",
                    Component.translatable("species.complicated_bees." + mutation.getFirst()).withStyle(ChatFormatting.AQUA),
                    Component.translatable("species.complicated_bees." + mutation.getSecond()).withStyle(ChatFormatting.LIGHT_PURPLE),
                    Component.translatable("species.complicated_bees." + mutation.getResult()).withStyle(ChatFormatting.GREEN)));
    }

    @Nullable
    public static Mutation getFromStack(ItemStack stack) {
        if (stack.hasTag() && stack.getTag().contains(RESEARCH_TAG)) {
            Mutation mutation = MutationRegistration.getFromResourceLocation(ResourceLocation.tryParse(stack.getTag().getString(RESEARCH_TAG)));
            return mutation;
        }
        return null;
    }
}
