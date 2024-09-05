package com.accbdd.complicated_bees.client;

import com.accbdd.complicated_bees.genetics.GeneticHelper;
import com.accbdd.complicated_bees.genetics.Species;
import com.accbdd.complicated_bees.registry.SpeciesRegistration;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
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
import net.neoforged.neoforge.client.NeoForgeRenderTypes;
import net.neoforged.neoforge.client.RenderTypeGroup;
import net.neoforged.neoforge.client.model.CompositeModel;
import net.neoforged.neoforge.client.model.ExtraFaceData;
import net.neoforged.neoforge.client.model.ItemLayerModel;
import net.neoforged.neoforge.client.model.geometry.IGeometryBakingContext;
import net.neoforged.neoforge.client.model.geometry.IGeometryLoader;
import net.neoforged.neoforge.client.model.geometry.IUnbakedGeometry;
import net.neoforged.neoforge.client.model.geometry.UnbakedGeometryHelper;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class BeeModel implements IUnbakedGeometry<BeeModel> {
    private record Variant(ResourceLocation species) {}

    private final Map<Variant, ItemLayerModel> modelMap;
    private final Int2ObjectMap<ExtraFaceData> layerData;
    private final Int2ObjectMap<ResourceLocation> renderTypeNames;
    private ImmutableList<Material> textures = null;

    public BeeModel(Map<Variant, ItemLayerModel> modelMap, Int2ObjectMap<ExtraFaceData> layerData, Int2ObjectMap<ResourceLocation> renderTypeNames) {
        this.modelMap = modelMap;
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
        var rootTransform = context.getRootTransform();
        if (!rootTransform.isIdentity())
            modelState = UnbakedGeometryHelper.composeRootTransformIntoModelState(modelState, rootTransform);
        TextureAtlasSprite particle = spriteGetter.apply(
                context.hasMaterial("particle") ? context.getMaterial("particle") : textures.get(0));

        ModelState finalModelState = modelState;
        Map<Variant, BakedModel> bakedMap = Map.copyOf(Maps.transformValues(modelMap, model -> model.bake(context, baker, spriteGetter, finalModelState, overrides, modelLocation)));
        ItemOverrides nbt_overrides = new ItemOverrides() {
            @Nullable
            @Override
            public BakedModel resolve(BakedModel pModel, ItemStack pStack, @Nullable ClientLevel pLevel, @Nullable LivingEntity pEntity, int pSeed) {
                Species species = GeneticHelper.getSpecies(pStack, true);
                Variant variant = new Variant(GeneticHelper.getRegistryAccess().registry(SpeciesRegistration.SPECIES_REGISTRY_KEY).get().getKey(species));

                BakedModel model = bakedMap.get(variant);
                if (model == null) return pModel;
                return model.getOverrides().resolve(model, pStack, pLevel, pEntity, pSeed);
            }
        };

        RenderTypeGroup normalRenderTypes = new RenderTypeGroup(RenderType.translucent(), NeoForgeRenderTypes.ITEM_UNSORTED_TRANSLUCENT.get());
        CompositeModel.Baked.Builder builder = CompositeModel.Baked.builder(context, particle, nbt_overrides, context.getTransforms());
        for (int i = 0; i < textures.size(); i++) {
            TextureAtlasSprite sprite = spriteGetter.apply(textures.get(i));
            List<BlockElement> unbaked = UnbakedGeometryHelper.createUnbakedItemElements(i, sprite, this.layerData.get(i));
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
            Map<Variant, ItemLayerModel> variantMap = new HashMap<>();
            var variantsJson = jsonObject.getAsJsonObject("variants");
            for (Map.Entry<String, JsonElement> entry : variantsJson.entrySet()) {
                ResourceLocation loc = ResourceLocation.tryParse(entry.getKey());
                ItemLayerModel model = deserializationContext.deserialize(entry.getValue(), ItemLayerModel.class);
                variantMap.put(new Variant(loc), model);
            }

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

            var emissiveLayers = new Int2ObjectArrayMap<ExtraFaceData>();
            if (jsonObject.has("forge_data")) throw new JsonParseException("forge_data should be replaced by neoforge_data");
            if (jsonObject.has("neoforge_data")) {
                JsonObject forgeData = jsonObject.get("neoforge_data").getAsJsonObject();
                readLayerData(forgeData, "layers", renderTypeNames, emissiveLayers, false);
            };

            return new BeeModel(variantMap, emissiveLayers, renderTypeNames);
        }

        protected void readLayerData(JsonObject jsonObject, String name, Int2ObjectOpenHashMap<ResourceLocation> renderTypeNames, Int2ObjectMap<ExtraFaceData> layerData, boolean logWarning) {
            if (!jsonObject.has(name)) {
                return;
            }
            var fullbrightLayers = jsonObject.getAsJsonObject(name);
            for (var entry : fullbrightLayers.entrySet()) {
                int layer = Integer.parseInt(entry.getKey());
                var data = ExtraFaceData.read(entry.getValue(), ExtraFaceData.DEFAULT);
                layerData.put(layer, data);
            }
        }
    }
}
