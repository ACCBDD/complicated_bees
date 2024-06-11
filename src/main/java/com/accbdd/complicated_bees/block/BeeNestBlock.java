package com.accbdd.complicated_bees.block;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class BeeNestBlock extends Block {
    public BeeNestBlock(Properties prop) {
        super(prop.requiresCorrectToolForDrops());
    }

    @Override
    public @NotNull BlockState playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        if (!pPlayer.getMainHandItem().is(ItemTags.create(new ResourceLocation("complicated_bees", "scoop_tool")))) {
            pPlayer.addEffect(new MobEffectInstance(MobEffects.POISON, 100));
        }
        return super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
    }
}
