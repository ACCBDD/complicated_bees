package com.accbdd.complicated_bees.block;

import com.accbdd.complicated_bees.block.entity.BeeNestBlockEntity;
import com.accbdd.complicated_bees.genetics.Species;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Containers;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BeeNestBlock extends BaseEntityBlock {
    private Species species;

    public BeeNestBlock(Properties prop) {
        super(prop.requiresCorrectToolForDrops());
        this.species = Species.INVALID;
    }

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public @NotNull BlockState playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        if (!pPlayer.getMainHandItem().is(ItemTags.create(new ResourceLocation("complicated_bees", "scoop_tool")))) {
            pPlayer.addEffect(new MobEffectInstance(MobEffects.POISON, 100));
        }
        return super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new BeeNestBlockEntity(getSpecies(), pPos, pState);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        Containers.dropContentsOnDestroy(pState, pNewState, pLevel, pPos);
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }
}
