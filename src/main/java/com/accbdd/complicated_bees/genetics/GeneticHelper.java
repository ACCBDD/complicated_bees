package com.accbdd.complicated_bees.genetics;

import com.accbdd.complicated_bees.genetics.gene.GeneSpecies;
import com.accbdd.complicated_bees.genetics.gene.GeneTolerant;
import com.accbdd.complicated_bees.genetics.gene.IGene;
import com.accbdd.complicated_bees.genetics.gene.enums.EnumTolerance;
import com.accbdd.complicated_bees.genetics.mutation.Mutation;
import com.accbdd.complicated_bees.genetics.mutation.condition.IMutationCondition;
import com.accbdd.complicated_bees.item.PrincessItem;
import com.accbdd.complicated_bees.item.QueenItem;
import com.accbdd.complicated_bees.registry.FlowerRegistration;
import com.accbdd.complicated_bees.registry.MutationRegistration;
import com.accbdd.complicated_bees.registry.SpeciesRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

import java.util.Map;
import java.util.Random;

public class GeneticHelper {
    public static final String CHROMOSOME_A = "chromosome_a";
    public static final String CHROMOSOME_B = "chromosome_b";
    public static final String MATE = "mate";
    private static final Random rand = new Random();

    public static Chromosome getChromosome(ItemStack stack, boolean primary) {
        CompoundTag serializedGenome = stack.getOrCreateTag().getCompound(primary ? CHROMOSOME_A : CHROMOSOME_B);
        return Chromosome.deserialize(serializedGenome);
    }

    public static Genome getGenome(ItemStack stack) {
        CompoundTag genome_a = stack.getOrCreateTag().getCompound(CHROMOSOME_A);
        CompoundTag genome_b = stack.getOrCreateTag().getCompound(CHROMOSOME_B);

        return new Genome(Chromosome.deserialize(genome_a), Chromosome.deserialize(genome_b));
    }

    public static ItemStack setGenome(ItemStack stack, Chromosome chromosome, boolean primary) {
        stack.getOrCreateTag().put(primary ? CHROMOSOME_A : CHROMOSOME_B, chromosome.serialize());
        return stack;
    }

    public static ItemStack setGenome(ItemStack stack, Genome genome) {
        stack.getOrCreateTag().put(CHROMOSOME_A, genome.getPrimary().serialize());
        stack.getOrCreateTag().put(CHROMOSOME_B, genome.getSecondary().serialize());
        return stack;
    }

    public static void setMate(ItemStack stack, Genome genome) {
        CompoundTag tag = new CompoundTag();
        tag.put(CHROMOSOME_A, genome.getPrimary().serialize());
        tag.put(CHROMOSOME_B, genome.getSecondary().serialize());
        stack.getOrCreateTag().put(MATE, tag);
    }

    public static ItemStack setBothGenome(ItemStack stack, Chromosome chromosome) {
        stack.getOrCreateTag().put(CHROMOSOME_A, chromosome.serialize());
        stack.getOrCreateTag().put(CHROMOSOME_B, chromosome.serialize());
        return stack;
    }

    public static RegistryAccess getRegistryAccess() {
        if (Minecraft.getInstance().getConnection() == null) {
            if (ServerLifecycleHooks.getCurrentServer() == null)
                return null;
            return ServerLifecycleHooks.getCurrentServer().registryAccess();
        }
        return Minecraft.getInstance().getConnection().registryAccess();
    }

    public static MutableComponent getTranslationKey(Species species) {
        RegistryAccess registryAccess = getRegistryAccess();
        return Component.translatable("species.complicated_bees." + registryAccess.registry(SpeciesRegistration.SPECIES_REGISTRY_KEY).get().getKey(species));
    }

    public static MutableComponent getGenusTaxonomyKey(Species species) {
        RegistryAccess registryAccess = getRegistryAccess();
        MutableComponent mutableComponent = Component.translatableWithFallback("species.complicated_bees." + registryAccess.registry(SpeciesRegistration.SPECIES_REGISTRY_KEY).get().getKey(species) + ".genus", "null");
        return mutableComponent.getString().equals("null") ? Component.translatable("gui.complicated_bees.no_genus") : mutableComponent;
    }

    public static MutableComponent getSpeciesTaxonomyKey(Species species) {
        RegistryAccess registryAccess = getRegistryAccess();
        MutableComponent mutableComponent = Component.translatableWithFallback("species.complicated_bees." + registryAccess.registry(SpeciesRegistration.SPECIES_REGISTRY_KEY).get().getKey(species) + ".species_taxonomy", "null");
        return mutableComponent.getString().equals("null") ? Component.translatable("gui.complicated_bees.no_species") : mutableComponent;
    }

    public static MutableComponent getFlavorTextKey(Species species) {
        RegistryAccess registryAccess = getRegistryAccess();
        MutableComponent mutableComponent = Component.translatableWithFallback("species.complicated_bees." + registryAccess.registry(SpeciesRegistration.SPECIES_REGISTRY_KEY).get().getKey(species) + ".flavor_text", "null");
        return mutableComponent.getString().equals("null") ? Component.translatable("gui.complicated_bees.no_flavor") : mutableComponent;
    }

    public static MutableComponent getFlavorTextAuthorKey(Species species) {
        RegistryAccess registryAccess = getRegistryAccess();
        MutableComponent mutableComponent = Component.translatableWithFallback("species.complicated_bees." + registryAccess.registry(SpeciesRegistration.SPECIES_REGISTRY_KEY).get().getKey(species) + ".flavor_author", "null");
        return mutableComponent.getString().equals("null") ? Component.translatable("gui.complicated_bees.no_author") : mutableComponent;
    }

    public static MutableComponent getAuthorityKey(Species species) {
        RegistryAccess registryAccess = getRegistryAccess();
        MutableComponent mutableComponent = Component.translatableWithFallback("species.complicated_bees." + registryAccess.registry(SpeciesRegistration.SPECIES_REGISTRY_KEY).get().getKey(species) + ".authority", "null");
        return mutableComponent.getString().equals("null") ? Component.translatable("gui.complicated_bees.no_authority") : mutableComponent;
    }

    public static MutableComponent getTranslationKey(Flower flower) {
        RegistryAccess registryAccess = getRegistryAccess();
        return Component.translatable("flower.complicated_bees." + registryAccess.registry(FlowerRegistration.FLOWER_REGISTRY_KEY).get().getKey(flower));
    }

    public static IGene<?> getGene(ItemStack stack, ResourceLocation id, boolean primary) {
        return getChromosome(stack, primary).getGene(id);
    }

    public static Species getSpecies(ItemStack stack, boolean primary) {
        return (Species) getGene(stack, GeneSpecies.ID, primary).get();
    }

    public static Object getGeneValue(ItemStack stack, ResourceLocation id, boolean primary) {
        return getGene(stack, id, primary).get();
    }

    private static Genome mixGenomes(Genome left, Genome right, Level level, BlockPos pos) {
        Chromosome chromosome_a = new Chromosome();
        Chromosome chromosome_b = new Chromosome();
        Chromosome mutated_a = null;
        Chromosome mutated_b = null;

        for (Map.Entry<ResourceLocation, IGene<?>> geneEntry : chromosome_a.getGenes().entrySet()) {
            ResourceLocation key = geneEntry.getKey();
            IGene<?> geneA = (rand.nextFloat() < 0.5 ? left.getPrimary() : left.getSecondary()).getGene(key);
            IGene<?> geneB = (rand.nextFloat() < 0.5 ? right.getPrimary() : right.getSecondary()).getGene(key);

            if (geneEntry.getValue() instanceof GeneTolerant) {
                EnumTolerance toleranceA = ((GeneTolerant<?>) (rand.nextFloat() < 0.5 ? left.getPrimary() : left.getSecondary()).getGene(key)).getTolerance();
                EnumTolerance toleranceB = ((GeneTolerant<?>) (rand.nextFloat() < 0.5 ? right.getPrimary() : right.getSecondary()).getGene(key)).getTolerance();
                geneA = ((GeneTolerant<?>) geneA).setTolerance(toleranceA);
                geneB = ((GeneTolerant<?>) geneB).setTolerance(toleranceB);
            } else if (geneEntry.getValue() instanceof GeneSpecies) {
                Species speciesA = (Species) geneA.get();
                Species speciesB = (Species) geneB.get();
                for (Mutation mutation : ServerLifecycleHooks.getCurrentServer().registryAccess().registry(MutationRegistration.MUTATION_REGISTRY_KEY).get().stream().toList()) {
                    if ((mutation.getFirstSpecies() == speciesA && mutation.getSecondSpecies() == speciesB) || (mutation.getSecondSpecies() == speciesA && mutation.getFirstSpecies() == speciesB)) {
                        boolean canMutate = true;
                        for (IMutationCondition condition : mutation.getConditions())
                            canMutate = canMutate && condition.check(level, pos);
                        if (canMutate) {
                            if (rand.nextFloat() < mutation.getChance())
                                mutated_a = mutation.getResultSpecies().getDefaultChromosome();
                            if (rand.nextFloat() < mutation.getChance())
                                mutated_b = mutation.getResultSpecies().getDefaultChromosome();
                        }
                    }
                }
            }

            chromosome_a.setGene(key, geneA);
            chromosome_b.setGene(key, geneB);
        }

        //set default chromosome if mutation found
        if (mutated_a != null) chromosome_a = mutated_a.copy();
        if (mutated_b != null) chromosome_b = mutated_b.copy();

        //sort genome so that dominant genes are always in a
        for (Map.Entry<ResourceLocation, IGene<?>> entry : chromosome_a.getGenes().entrySet()) {
            IGene<?> gene = entry.getValue();
            if (!entry.getValue().isDominant()) {
                chromosome_a.setGene(entry.getKey(), chromosome_b.getGene(entry.getKey()));
                chromosome_b.setGene(entry.getKey(), gene);
            } else if (chromosome_b.getGene(entry.getKey()).isDominant()) {
                //both are dominant, random shuffle
                if (rand.nextFloat() < 0.5) {
                    chromosome_a.setGene(entry.getKey(), chromosome_b.getGene(entry.getKey()));
                    chromosome_b.setGene(entry.getKey(), gene);
                }
            }
        }

        return new Genome(chromosome_a, chromosome_b);
    }

    /**
     * Gets an offspring from an ItemStack with a genome. If the ItemStack also has a mate set, the offspring is mutated according to mixGenomes.
     *
     * @param bee        an ItemStack to get an offspring from
     * @param resultType the Item an offspring should be
     * @param level      the level the offspring is generating in (for mutation conditions)
     * @param pos        the blockpos the offspring is generating in (for mutation conditions)
     * @return an ItemStack of type resultType with a genome set
     */
    public static ItemStack getOffspring(ItemStack bee, Item resultType, Level level, BlockPos pos) {
        ItemStack result = new ItemStack(resultType);
        CompoundTag eggs = bee.getOrCreateTag().getCompound(MATE);

        Genome genome = getGenome(bee);
        Genome mate = new Genome(Chromosome.deserialize(eggs.getCompound(CHROMOSOME_A)), Chromosome.deserialize(eggs.getCompound(CHROMOSOME_B)));
        if (!eggs.equals(new CompoundTag())) {
            setGenome(result, mixGenomes(genome, mate, level, pos));
        } else {
            setGenome(result, genome);
        }
        if (resultType instanceof PrincessItem)
            PrincessItem.setGeneration(result, QueenItem.getGeneration(bee) + 1);
        return result;
    }
}
