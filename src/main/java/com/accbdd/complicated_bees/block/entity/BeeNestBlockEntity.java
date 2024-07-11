package com.accbdd.complicated_bees.block.entity;

import com.accbdd.complicated_bees.genetics.GeneticHelper;
import com.accbdd.complicated_bees.genetics.Product;
import com.accbdd.complicated_bees.genetics.Species;
import com.accbdd.complicated_bees.registry.BlockEntitiesRegistration;
import com.accbdd.complicated_bees.registry.ItemsRegistration;
import com.accbdd.complicated_bees.registry.SpeciesRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public class BeeNestBlockEntity extends BlockEntity{
    private Species species;

    public BeeNestBlockEntity(Species species, BlockPos pPos, BlockState pBlockState) {
        super(BlockEntitiesRegistration.BEE_NEST_ENTITY.get(), pPos, pBlockState);
        this.species = species;
    }

    public Species getSpecies() {
        return species;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        RegistryAccess registryAccess = (Minecraft.getInstance().getConnection() == null) ? ServerLifecycleHooks.getCurrentServer().registryAccess() : Minecraft.getInstance().getConnection().registryAccess();
        if (registryAccess.registry(SpeciesRegistry.SPECIES_REGISTRY_KEY).get().getKey(getSpecies()) == null) {
            tag.put("species", StringTag.valueOf("complicated_bees:invalid"));
        } else {
            tag.put("species", StringTag.valueOf(registryAccess.registry(SpeciesRegistry.SPECIES_REGISTRY_KEY).get().getKey(getSpecies()).toString()));
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.species = SpeciesRegistry.getFromResourceLocation(ResourceLocation.tryParse(tag.getString("species")));
    }
}
