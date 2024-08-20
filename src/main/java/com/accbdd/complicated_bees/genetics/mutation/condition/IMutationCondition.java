package com.accbdd.complicated_bees.genetics.mutation.condition;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

public interface IMutationCondition {
    /**
     * @param level the level to check this condition in
     * @param pos   the position to check this condition in
     * @return whether this mutation condition is satisfied
     */
    boolean check(Level level, BlockPos pos);

    /**
     * @return a description to show in a recipe viewer that explains this mutation condition
     */
    Component getDescription();

    /**
     * Serializes this condition into a compound tag.
     *
     * @return a serialized version of this condition
     */
    CompoundTag serialize();

    /**
     * Creates a deserialized condition from a serialized compound tag.
     *
     * @param tag the nbt serialized version of this condition
     * @return a deserialized version of this condition from the specified tag
     */
    IMutationCondition deserialize(CompoundTag tag);
}
