package com.accbdd.complicated_bees.genetics;

import com.accbdd.complicated_bees.registry.SpeciesRegistration;
import net.minecraft.core.IdMap;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.util.Set;

public class SpeciesDiscoveredAttachment implements CustomPacketPayload {
    Set<String> data;

    public SpeciesDiscoveredAttachment(Set<String> data) {
        this.data = data;
    }

    public SpeciesDiscoveredAttachment(final FriendlyByteBuf buffer) {
        String[] strings = buffer.readArray(String[]::new, buffer.readById());
    }

    private String readSpeciesStringFromBuffer(FriendlyByteBuf buffer) {
        IdMap<Species> map = GeneticHelper.getRegistryAccess().registry(SpeciesRegistration.SPECIES_REGISTRY_KEY).orElseThrow();
        return GeneticHelper.buffer.readById(map);
    }

    @Override
    public void write(FriendlyByteBuf pBuffer) {

    }

    @Override
    public ResourceLocation id() {
        return null;
    }
}
