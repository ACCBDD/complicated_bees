package com.accbdd.complicated_bees.worldgen;

import com.accbdd.complicated_bees.genetics.Species;
import com.accbdd.complicated_bees.registry.BlockEntitiesRegistration;
import com.accbdd.complicated_bees.registry.BlocksRegistration;
import com.accbdd.complicated_bees.registry.EsotericRegistration;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ComplicatedBeenestDecorator extends TreeDecorator {
    private static final Direction[] SPAWN_DIRECTIONS = Direction.Plane.HORIZONTAL
            .stream()
            .toArray(Direction[]::new);

    private final float probability;
    private final String speciesKey;

    public static final Codec<ComplicatedBeenestDecorator> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter(ComplicatedBeenestDecorator::getProbability),
                    Codec.STRING.fieldOf("species").forGetter(ComplicatedBeenestDecorator::getSpeciesKey)
            ).apply(instance, ComplicatedBeenestDecorator::new)
    );

    public ComplicatedBeenestDecorator(float probability, String speciesKey) {
        this.probability = probability;
        this.speciesKey = speciesKey;
    }

    public float getProbability() {
        return probability;
    }

    public String getSpeciesKey() {
        return speciesKey;
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return EsotericRegistration.COMPLICATED_BEENEST_DECORATOR.get();
    }

    @Override
    public void place(Context context) {
        RandomSource rand = context.random();
        if (!(rand.nextFloat() >= this.probability)) {
            List<BlockPos> leaves = context.leaves();
            List<BlockPos> logs = context.logs();
            int i = !leaves.isEmpty()
                    ? Math.max(leaves.get(0).getY() - 1, logs.get(0).getY() + 1)
                    : Math.min(logs.get(0).getY() + 1 + rand.nextInt(3), logs.get(logs.size() - 1).getY());
            List<BlockPos> list2 = logs.stream()
                    .filter(pos -> pos.getY() == i)
                    .flatMap(pos -> Stream.of(SPAWN_DIRECTIONS).map(pos::relative))
                    .collect(Collectors.toList());
            if (!list2.isEmpty()) {
                Collections.shuffle(list2);
                Optional<BlockPos> optional = list2.stream()
                        .filter(pos -> context.isAir(pos))
                        .findFirst();
                if (!optional.isEmpty()) {
                    context.setBlock(optional.get(), BlocksRegistration.BEE_NEST.get().defaultBlockState());
                    context.level().getBlockEntity(optional.get(), BlockEntitiesRegistration.BEE_NEST_ENTITY.get()).ifPresent(be -> {
                        be.setSpecies(Species.getFromResourceLocation(ResourceLocation.tryParse(this.speciesKey)));
                    });
                }
            }
        }
    }
}
