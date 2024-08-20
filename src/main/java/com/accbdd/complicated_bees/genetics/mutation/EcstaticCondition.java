package com.accbdd.complicated_bees.genetics.mutation;

import com.accbdd.complicated_bees.block.entity.ApiaryBlockEntity;
import com.accbdd.complicated_bees.util.enums.EnumErrorCodes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class EcstaticCondition extends MutationCondition {
    public static String ID = "ecstatic";

    public EcstaticCondition() {

    }

    @Override
    public boolean check(Level level, BlockPos pos) {
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof ApiaryBlockEntity apiary) {
            return apiary.getData().get(2) == EnumErrorCodes.ECSTATIC.value;
        }
        return false;
    }

    @Override
    public Component getDescription() {
        return Component.translatable("gui.complicated_bees.mutations.ecstatic");
    }

    @Override
    public CompoundTag serialize() {
        return new CompoundTag();
    }

    @Override
    public EcstaticCondition deserialize(CompoundTag tag) {
        return new EcstaticCondition();
    }
}
