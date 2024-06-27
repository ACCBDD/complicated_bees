package com.accbdd.complicated_bees.genetics;

import com.accbdd.complicated_bees.genetics.gene.Gene;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Map;
import java.util.Random;

public class BreedingHelper {
    private static Random rand = new Random();

    public static ItemStack breed(ItemStack princess, ItemStack drone, Item resultType) {
        Genome princess_a = GeneticHelper.getGenome(princess, true);
        Genome princess_b = GeneticHelper.getGenome(princess, false);
        Genome drone_a = GeneticHelper.getGenome(drone, true);
        Genome drone_b = GeneticHelper.getGenome(drone, false);

        ItemStack result = new ItemStack(resultType);

        Genome new_a = breedGenomes(princess_a, drone_a);
        Genome new_b = breedGenomes(princess_b, drone_b);

        return GeneticHelper.setGenome(result, new_a, new_b);
    }

    private static Genome breedGenomes(Genome first, Genome second) {
        Genome result = new Genome();

        for (Map.Entry<ResourceLocation, Gene<?>> entry : first.getGenes().entrySet()) {
            //todo: implement random mutations
            result.setGene(entry.getKey(), rand.nextFloat() < 0.5 ? second.getGene(entry.getKey()) : entry.getValue());
        }

        return result;
    }
}
