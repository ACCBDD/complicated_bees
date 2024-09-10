package com.accbdd.complicated_bees.compat.jade;

import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class ComplicatedBeesJade implements IWailaPlugin {

    @Override
    public void register(IWailaCommonRegistration registration) {
        //todo: register data providers
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        //todo: register component providers, icon providers, callbacks, and config options
    }
}
