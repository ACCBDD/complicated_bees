package com.accbdd.complicated_bees.recipe;

import com.accbdd.complicated_bees.ComplicatedBees;
import com.accbdd.complicated_bees.genetics.Product;
import com.accbdd.complicated_bees.registry.EsotericRegistration;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class CentrifugeRecipe implements Recipe<Container> {
    private final ItemStack input;
    private final List<Product> outputs;
    private final ResourceLocation id;

    public static final RecipeSerializer<CentrifugeRecipe> SERIALIZER = new RecipeSerializer<>() {

        @Override
        public CentrifugeRecipe fromNetwork(ResourceLocation loc, FriendlyByteBuf pBuffer) {
            ItemStack input = pBuffer.readItem();
            List<Product> outputs = new ArrayList<>();
            int listSize = pBuffer.readInt();
            for (int i = 0; i < listSize; i++) {
                outputs.add(Product.fromNetwork(pBuffer));
            }
            return new CentrifugeRecipe(loc, input, outputs);
        }

        @Override
        public CentrifugeRecipe fromJson(ResourceLocation location, JsonObject json) {
            JsonObject inputJson = json.getAsJsonObject("input");
            ResourceLocation inputItemLocation = ResourceLocation.tryParse(inputJson.get("item").getAsString());
            Item inputItem = ForgeRegistries.ITEMS.getValue(inputItemLocation);
            if (inputItem == null) throw new JsonParseException("could not parse input for " + location);
            ItemStack input = new ItemStack(inputItem);
            if (inputJson.has("nbt"))
                input.setTag(CraftingHelper.getNBT(inputJson.getAsJsonObject("nbt")));

            List<Product> outputs = new ArrayList<>();
            if (json.has("outputs")) {
                JsonArray outputsJson = json.getAsJsonArray("outputs");
                for (JsonElement element : outputsJson.asList()) {
                    var result = Product.CODEC.decode(JsonOps.INSTANCE, element);
                    outputs.add(result.result().get().getFirst());
                }
            }
            return new CentrifugeRecipe(location, input, outputs);
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

    public CentrifugeRecipe(ResourceLocation id, ItemStack input, List<Product> outputs) {
        this.id = id;
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
        ComplicatedBees.LOGGER.debug("tried to use assemble on a CentrifugeRecipe! Use getOutputs instead");
        return ItemStack.EMPTY;
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
        ComplicatedBees.LOGGER.debug("tried to use getResultItem on a CentrifugeRecipe! Use getOutputs instead");
        return ItemStack.EMPTY;
    }

    @Override
    public ResourceLocation getId() {
        return id;
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
