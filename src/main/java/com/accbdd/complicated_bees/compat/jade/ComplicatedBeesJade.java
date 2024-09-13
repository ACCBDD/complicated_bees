package com.accbdd.complicated_bees.compat.jade;

import com.accbdd.complicated_bees.block.ApiaryBlock;
import com.accbdd.complicated_bees.block.entity.ApiaryBlockEntity;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

@WailaPlugin
public class ComplicatedBeesJade implements IWailaPlugin {

    public static ResourceLocation ERRORS_CONFIG = new ResourceLocation(MODID, "show_errors");
    public static ResourceLocation CYCLE_CONFIG = new ResourceLocation(MODID, "show_cycle_ticks");

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(ApiaryComponentProvider.INSTANCE, ApiaryBlockEntity.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(ApiaryComponentProvider.INSTANCE, ApiaryBlock.class);
        registration.addConfig(ERRORS_CONFIG, true);
        registration.addConfig(CYCLE_CONFIG, false);
    }
}
