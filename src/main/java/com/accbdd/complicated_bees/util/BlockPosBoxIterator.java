package com.accbdd.complicated_bees.util;

import com.google.common.collect.AbstractIterator;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.Nullable;

public class BlockPosBoxIterator extends AbstractIterator<BlockPos> {
    private final BlockPos center;
    private final BlockPos maxPos;
    private final BlockPos minPos;

    private BlockPos current;

    public BlockPosBoxIterator(BlockPos center, int hRadius, int vRadius) {
        this.center = center;
        this.maxPos = new BlockPos(center.getX() + hRadius, center.getY() + vRadius, center.getZ() + hRadius);
        this.minPos = new BlockPos(center.getX() - hRadius, center.getY() - vRadius, center.getZ() - hRadius);
        this.current = null;
    }

    public BlockPos getCenter() {
        return center;
    }

    @Nullable
    @Override
    protected BlockPos computeNext() {
        if (current == null) {
            current = minPos;
        } else {
            int x = current.getX();
            int y = current.getY();
            int z = current.getZ();
            if (x < maxPos.getX()) {
                ++x;
            } else if (z < maxPos.getZ()) {
                x = minPos.getX();
                ++z;
            } else {
                x = minPos.getX();
                z = minPos.getZ();
                ++y;
            }

            current = new BlockPos(x, y, z);
        }
        return (current.getY() > maxPos.getY()) ? this.endOfData() : current;
    }
}
