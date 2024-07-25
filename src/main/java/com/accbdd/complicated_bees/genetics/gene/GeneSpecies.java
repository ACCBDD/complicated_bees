package com.accbdd.complicated_bees.genetics.gene;

import com.accbdd.complicated_bees.genetics.Species;
import com.accbdd.complicated_bees.registry.SpeciesRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class GeneSpecies extends Gene<Species> {
    public static final String TAG = "species";
    public static final ResourceLocation ID = new ResourceLocation(MODID, TAG);

    public GeneSpecies() {
        super(Species.INVALID, true);
    }

    public GeneSpecies(Species species, boolean dominant) {
        super(species, dominant);
    }

    @Override
    public CompoundTag serialize() {
        CompoundTag tag = new CompoundTag();
        RegistryAccess registryAccess = (Minecraft.getInstance().getConnection() == null) ? ServerLifecycleHooks.getCurrentServer().registryAccess() : Minecraft.getInstance().getConnection().registryAccess();
        if (registryAccess.registry(SpeciesRegistration.SPECIES_REGISTRY_KEY).get().getKey(this.get()) == null) {
            tag.put(DATA, StringTag.valueOf("complicated_bees:invalid"));
        } else {
            tag.put(DATA, StringTag.valueOf(registryAccess.registry(SpeciesRegistration.SPECIES_REGISTRY_KEY).get().getKey(this.get()).toString()));
        }
        tag.put(DOMINANT, ByteTag.valueOf(this.isDominant()));
        return tag;
    }

    @Override
    public GeneSpecies deserialize(CompoundTag tag) {
        RegistryAccess registryAccess = (Minecraft.getInstance().getConnection() == null) ? ServerLifecycleHooks.getCurrentServer().registryAccess() : Minecraft.getInstance().getConnection().registryAccess();
        Registry<Species> registry = registryAccess.registry(SpeciesRegistration.SPECIES_REGISTRY_KEY).get();

        return new GeneSpecies(registry.get(ResourceLocation.tryParse(tag.getString(DATA))), tag.getBoolean(DOMINANT));
    }
}
