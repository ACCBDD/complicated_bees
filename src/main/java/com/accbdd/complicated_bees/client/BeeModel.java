package com.accbdd.complicated_bees.client;

import com.accbdd.complicated_bees.genetics.GeneticHelper;
import com.accbdd.complicated_bees.genetics.Species;
import com.accbdd.complicated_bees.registry.ItemsRegistration;
import com.google.common.collect.ImmutableList;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockElement;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.ForgeRenderTypes;
import net.minecraftforge.client.RenderTypeGroup;
import net.minecraftforge.client.model.CompositeModel;
import net.minecraftforge.client.model.ForgeFaceData;
import net.minecraftforge.client.model.geometry.IGeometryBakingContext;
import net.minecraftforge.client.model.geometry.IGeometryLoader;
import net.minecraftforge.client.model.geometry.IUnbakedGeometry;
import net.minecraftforge.client.model.geometry.UnbakedGeometryHelper;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class BeeModel implements IUnbakedGeometry<BeeModel> {
    public record Variant(BakedModel drone, BakedModel princess, BakedModel queen) {
    }

    private final Map<Species, Variant> cacheMap = new HashMap<>();
    private final Int2ObjectMap<ForgeFaceData> layerData;
    private final Int2ObjectMap<ResourceLocation> renderTypeNames;
    private ImmutableList<Material> textures = null;

    public BeeModel(Int2ObjectMap<ForgeFaceData> layerData, Int2ObjectMap<ResourceLocation> renderTypeNames) {
        this.layerData = layerData;
        this.renderTypeNames = renderTypeNames;
    }

    @Override
    public BakedModel bake(IGeometryBakingContext context, ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, ItemOverrides overrides, ResourceLocation modelLocation) {
        if (textures == null) {
            ImmutableList.Builder<Material> builder = ImmutableList.builder();
            for (int i = 0; context.hasMaterial("layer" + i); i++) {
                builder.add(context.getMaterial("layer" + i));
            }
            textures = builder.build();
        }
        TextureAtlasSprite particle = spriteGetter.apply(
                context.hasMaterial("particle") ? context.getMaterial("particle") : textures.get(0));

        ItemOverrides nbt_overrides = new ItemOverrides() {
            @Nullable
            @Override
            public BakedModel resolve(BakedModel pModel, ItemStack pStack, @Nullable ClientLevel pLevel, @Nullable LivingEntity pEntity, int pSeed) {
                Species species = GeneticHelper.getSpecies(pStack, true);
                if (!cacheMap.containsKey(species)) {
                    BakedModel[] bakedModels = new BakedModel[3];
                    for (int i = 0; i < 3; i++) {
                        ResourceLocation modelLoc = species.getModels().get(i);
                        var unbaked = baker.getModel(modelLoc);
                        unbaked.resolveParents(baker::getModel);
                        var baked = unbaked.bake(baker, spriteGetter, modelState, modelLoc);
                        bakedModels[i] = baked;
                    }
                    cacheMap.put(species, new Variant(bakedModels[0], bakedModels[1], bakedModels[2]));
                }
                if (pStack.is(ItemsRegistration.QUEEN.get()))
                    return cacheMap.get(species).queen;
                else if (pStack.is(ItemsRegistration.PRINCESS.get()))
                    return cacheMap.get(species).princess;
                else
                    return cacheMap.get(species).drone;
            }
        };

        RenderTypeGroup normalRenderTypes = new RenderTypeGroup(RenderType.translucent(), ForgeRenderTypes.ITEM_UNSORTED_TRANSLUCENT.get());
        CompositeModel.Baked.Builder builder = CompositeModel.Baked.builder(context, particle, nbt_overrides, context.getTransforms());
        for (int i = 0; i < textures.size(); i++) {
            TextureAtlasSprite sprite = spriteGetter.apply(textures.get(i));
            List<BlockElement> unbaked = UnbakedGeometryHelper.createUnbakedItemElements(i, sprite.contents(), this.layerData.get(i));
            List<BakedQuad> quads = UnbakedGeometryHelper.bakeElements(unbaked, $ -> sprite, modelState, modelLocation);
            ResourceLocation renderTypeName = renderTypeNames.get(i);
            RenderTypeGroup renderTypes = renderTypeName != null ? context.getRenderType(renderTypeName) : null;
            builder.addQuads(renderTypes != null ? renderTypes : normalRenderTypes, quads);
        }
        return builder.build();
    }

    public static class Loader implements IGeometryLoader<BeeModel> {
        public static final Loader INSTANCE = new Loader();
        public static final ResourceLocation ID = new ResourceLocation(MODID, "bee_model");

        @Override
        public BeeModel read(JsonObject jsonObject, JsonDeserializationContext deserializationContext) throws JsonParseException {
            var renderTypeNames = new Int2ObjectOpenHashMap<ResourceLocation>();
            if (jsonObject.has("render_types")) {
                var renderTypes = jsonObject.getAsJsonObject("render_types");
                for (Map.Entry<String, JsonElement> entry : renderTypes.entrySet()) {
                    var renderType = new ResourceLocation(entry.getKey());
                    for (var layer : entry.getValue().getAsJsonArray())
                        if (renderTypeNames.put(layer.getAsInt(), renderType) != null)
                            throw new JsonParseException("Registered duplicate render type for layer " + layer);
                }
            }

            var emissiveLayers = new Int2ObjectArrayMap<ForgeFaceData>();
            if (jsonObject.has("forge_data")) {
                JsonObject forgeData = jsonObject.get("forge_data").getAsJsonObject();
                readLayerData(forgeData, "layers", renderTypeNames, emissiveLayers, false);
            }

            return new BeeModel(emissiveLayers, renderTypeNames);
        }

        protected void readLayerData(JsonObject jsonObject, String name, Int2ObjectOpenHashMap<ResourceLocation> renderTypeNames, Int2ObjectMap<ForgeFaceData> layerData, boolean logWarning) {
            if (!jsonObject.has(name)) {
                return;
            }
            var fullbrightLayers = jsonObject.getAsJsonObject(name);
            for (var entry : fullbrightLayers.entrySet()) {
                int layer = Integer.parseInt(entry.getKey());
                var data = ForgeFaceData.read(entry.getValue(), ForgeFaceData.DEFAULT);
                layerData.put(layer, data);
            }
        }
    }
}
