package com.accbdd.complicated_bees.compat.jade;

import com.accbdd.complicated_bees.block.ApiaryBlock;
import com.accbdd.complicated_bees.block.entity.ApiaryBlockEntity;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class ComplicatedBeesJade implements IWailaPlugin {

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(ApiaryComponentProvider.INSTANCE, ApiaryBlockEntity.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(ApiaryComponentProvider.INSTANCE, ApiaryBlock.class);
    }
}
