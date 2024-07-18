package com.accbdd.complicated_bees.block;

import com.accbdd.complicated_bees.ComplicatedBees;
import com.accbdd.complicated_bees.block.entity.BeeNestBlockEntity;
import com.accbdd.complicated_bees.genetics.GeneticHelper;
import com.accbdd.complicated_bees.genetics.Species;
import com.accbdd.complicated_bees.registry.ItemsRegistration;
import com.accbdd.complicated_bees.registry.SpeciesRegistry;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Containers;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BeeNestBlock extends BaseEntityBlock {

    public BeeNestBlock() {
        super(BlockBehaviour.Properties.of()
                .requiresCorrectToolForDrops()
                .lightLevel(state -> 15)
                .strength(0.6f)
                .sound(SoundType.WOOD));
    }

    public static ItemStack stackNest(ItemStack stack, Species species) {
        CompoundTag tag = stack.getOrCreateTag();
        CompoundTag data = new CompoundTag();
        data.putString("species", SpeciesRegistry.getResourceLocation(species).toString());
        tag.put("BlockEntityTag", data);
        return stack;
    }

    public static int getItemColor(ItemStack stack, int tintIndex) {
        if (tintIndex == 1) {
            Species species = SpeciesRegistry.getFromResourceLocation(ResourceLocation.tryParse(stack.getOrCreateTag().getCompound("BlockEntityTag").getString("species")));
            if (species != null) {
                return species.getColor();
            }
            return 0;
        }
        return 0xFFFFFF;
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
        if (!pPlayer.getMainHandItem().is(ItemTags.create(new ResourceLocation("complicated_bees", "scoop_tool"))) && !pPlayer.isInvulnerable()) {
            pPlayer.addEffect(new MobEffectInstance(MobEffects.POISON, 100));
        }
        return super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new BeeNestBlockEntity(pPos, pState);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        Containers.dropContentsOnDestroy(pState, pNewState, pLevel, pPos);
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }
}
