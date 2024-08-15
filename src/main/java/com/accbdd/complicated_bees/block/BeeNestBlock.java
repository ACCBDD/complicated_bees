package com.accbdd.complicated_bees.block;

import com.accbdd.complicated_bees.block.entity.BeeNestBlockEntity;
import com.accbdd.complicated_bees.genetics.Species;
import com.accbdd.complicated_bees.registry.ItemsRegistration;
import com.accbdd.complicated_bees.registry.SpeciesRegistration;
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
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
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
        data.putString("species", SpeciesRegistration.getResourceLocation(species).toString());
        tag.put("BlockEntityTag", data);
        return stack;
    }

    public static int getItemColor(ItemStack stack, int tintIndex) {
        if (tintIndex == 1) {
            Species species = SpeciesRegistration.getFromResourceLocation(ResourceLocation.tryParse(stack.getOrCreateTag().getCompound("BlockEntityTag").getString("species")));
            if (species != null) {
                return species.getNestColor();
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
        if (!pPlayer.getMainHandItem().is(ItemTags.create(new ResourceLocation("complicated_bees", "scoop_tool"))) && pPlayer.canBeSeenAsEnemy()) {
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

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
        ItemStack nest = new ItemStack(ItemsRegistration.BEE_NEST.get());
        CompoundTag tag = nest.getOrCreateTag();
        CompoundTag data = new CompoundTag();
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof BeeNestBlockEntity ne)
            data.putString("species", SpeciesRegistration.getResourceLocation(ne.getSpecies()).toString());
        tag.put("BlockEntityTag", data);
        return nest;
    }
}
