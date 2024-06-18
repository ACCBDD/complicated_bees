package com.accbdd.complicated_bees.registry;

import com.accbdd.complicated_bees.block.ApiaryBlock;
import com.accbdd.complicated_bees.block.BeeNestBlock;
import com.accbdd.complicated_bees.block.CentrifugeBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class BlocksRegistration {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);

    public static final DeferredBlock<Block> BEE_NEST = BLOCKS.register("bee_nest",
            () -> new BeeNestBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEE_NEST)));

    public static final DeferredBlock<ApiaryBlock> APIARY = BLOCKS.register("apiary",
            ApiaryBlock::new);

    public static final DeferredBlock<CentrifugeBlock> CENTRIFUGE = BLOCKS.register("centrifuge",
            CentrifugeBlock::new);
}
