package com.accbdd.complicated_bees.datagen;

import com.accbdd.complicated_bees.registry.EsotericRegistration;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ParticleDescriptionProvider;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class ParticleDescriptionGenerator extends ParticleDescriptionProvider {
    protected ParticleDescriptionGenerator(PackOutput output, ExistingFileHelper fileHelper) {
        super(output, fileHelper);
    }

    @Override
    protected void addDescriptions() {
        sprite(EsotericRegistration.BEE_PARTICLE.get(), new ResourceLocation(MODID, "bee"));
    }
}
