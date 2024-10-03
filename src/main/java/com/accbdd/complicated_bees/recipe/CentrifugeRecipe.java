package com.accbdd.complicated_bees.recipe;

import com.accbdd.complicated_bees.genetics.Product;
import com.accbdd.complicated_bees.registry.EsotericRegistration;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class CentrifugeRecipe implements Recipe<Container> {
    private final ItemStack input;
    private final List<Product> outputs;

    public static final Codec<CentrifugeRecipe> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    ItemStack.CODEC.fieldOf("input").forGetter(CentrifugeRecipe::getInput),
                    Product.CODEC.listOf().fieldOf("outputs").forGetter(CentrifugeRecipe::getOutputs)
            ).apply(instance, CentrifugeRecipe::new)
    );

    public static final RecipeSerializer<CentrifugeRecipe> SERIALIZER = new RecipeSerializer<>() {

        @Override
        public CentrifugeRecipe fromNetwork(ResourceLocation loc, FriendlyByteBuf pBuffer) {
            ItemStack input = pBuffer.readItem();
            List<Product> outputs = new ArrayList<>();
            int listSize = pBuffer.readInt();
            for (int i = 0; i < listSize; i++) {
                outputs.add(Product.fromNetwork(pBuffer));
            }
            return new CentrifugeRecipe(input, outputs);
        }

        @Override
        public CentrifugeRecipe fromJson(ResourceLocation location, JsonObject json) {
            var decoded = CODEC.decode(JsonOps.INSTANCE, json);
            return decoded.result().orElseThrow().getFirst();
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, CentrifugeRecipe pRecipe) {
            pBuffer.writeItem(pRecipe.input);
            pBuffer.writeInt(pRecipe.outputs.size());
            for (Product prod : pRecipe.outputs) {
                prod.toNetwork(pBuffer);
            }
        }
    };

    public CentrifugeRecipe(ItemStack input, List<Product> outputs) {
        this.input = input;
        this.outputs = outputs;
    }

    @Override
    public boolean matches(Container pContainer, Level pLevel) {
        ItemStack containerInput = pContainer.getItem(0);
        boolean itemMatch = input.is(containerInput.getItem());
        boolean countMatch = input.getCount() <= containerInput.getCount();
        boolean nbtMatch = input.getOrCreateTag().equals(containerInput.getOrCreateTag());
        return (countMatch && nbtMatch && itemMatch);
    }

    @Override
    public ItemStack assemble(Container pContainer, RegistryAccess pRegistryAccess) {
        throw new UnsupportedOperationException("Centrifuge recipes do not use assemble! Use getOutputs instead");
    }

    public List<Product> getOutputs() {
        return outputs;
    }

    public ItemStack getInput() {
        return input;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return pWidth * pHeight >= 1;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        throw new UnsupportedOperationException("Centrifuge recipes do not use getResultItem! Use getOutputs instead");
    }

    @Override
    public ResourceLocation getId() {
        return new ResourceLocation(MODID, "centrifuge");
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return EsotericRegistration.CENTRIFUGE_RECIPE.get();
    }


}
