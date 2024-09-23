package com.accbdd.complicated_bees.datagen;

import com.accbdd.complicated_bees.registry.ItemsRegistration;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class ItemTagGenerator extends ItemTagsProvider {
    public static final TagKey<Item> SCOOP_TOOL = ItemTags.create(new ResourceLocation(MODID, "scoop_tool"));
    public static final TagKey<Item> BEE = ItemTags.create(new ResourceLocation(MODID, "bee"));
    public static final TagKey<Item> ROYAL = ItemTags.create(new ResourceLocation(MODID, "royal"));
    public static final TagKey<Item> FRAME = ItemTags.create(new ResourceLocation(MODID, "frame"));
    public static final TagKey<Item> ANALYZER_FUEL = ItemTags.create(new ResourceLocation(MODID, "analyzer_fuel"));

    public ItemTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, CompletableFuture<TagLookup<Block>> blockTagProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, blockTagProvider, MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {
        tag(SCOOP_TOOL).add(ItemsRegistration.SCOOP.get());
        tag(ROYAL).add(ItemsRegistration.PRINCESS.get(), ItemsRegistration.QUEEN.get());
        tag(BEE).add(ItemsRegistration.PRINCESS.get(), ItemsRegistration.QUEEN.get(), ItemsRegistration.DRONE.get());
        tag(ANALYZER_FUEL).add(ItemsRegistration.HONEY_DROPLET.get(), ItemsRegistration.ROYAL_JELLY.get());
        tag(FRAME).add(
                ItemsRegistration.FRAME.get(),
                ItemsRegistration.DEADLY_FRAME.get(),
                ItemsRegistration.DRY_FRAME.get(),
                ItemsRegistration.WET_FRAME.get(),
                ItemsRegistration.COLD_FRAME.get(),
                ItemsRegistration.HOT_FRAME.get(),
                ItemsRegistration.RESTRICTIVE_FRAME.get(),
                ItemsRegistration.THICK_FRAME.get()
        );
        tag(ItemTags.PLANKS).add(ItemsRegistration.HONEYED_PLANKS.get());
    }
}
