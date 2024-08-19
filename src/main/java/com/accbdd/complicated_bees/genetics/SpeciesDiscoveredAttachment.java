package com.accbdd.complicated_bees.genetics;

import com.accbdd.complicated_bees.registry.SpeciesRegistration;
import net.minecraft.core.IdMap;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class SpeciesDiscoveredAttachment implements CustomPacketPayload {
    public static final ResourceLocation ID = new ResourceLocation(MODID, "species_discovered");
    Set<Species> data;

    public SpeciesDiscoveredAttachment(Set<Species> data) {
        this.data = data;
    }

    public SpeciesDiscoveredAttachment(final FriendlyByteBuf buffer) {
        Species[] strings = buffer.readArray(Species[]::new, this::readSpeciesFromBuffer);
        this.data = new HashSet<>(Arrays.asList(strings));
    }

    private Species readSpeciesFromBuffer(FriendlyByteBuf buffer) {
        IdMap<Species> map = GeneticHelper.getRegistryAccess().registry(SpeciesRegistration.SPECIES_REGISTRY_KEY).orElseThrow();
        return buffer.readById(map);
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        IdMap<Species> map = GeneticHelper.getRegistryAccess().registry(SpeciesRegistration.SPECIES_REGISTRY_KEY).orElseThrow();
        for (Species species : this.data) {
            buffer.writeId(map, species);
        }
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }
}
