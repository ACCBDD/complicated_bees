package com.accbdd.complicated_bees.util;

import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.attachment.IAttachmentSerializer;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;

public class SpeciesDiscoveredSerializer implements IAttachmentSerializer<ListTag, HashSet<String>> {
    @Override
    public HashSet<String> read(IAttachmentHolder holder, ListTag tag) {
        HashSet<String> set = new HashSet<>();
        tag.forEach((str) -> set.add(str.getAsString()));
        return set;
    }

    @Override
    public @Nullable ListTag write(HashSet<String> attachment) {
        ListTag tag = new ListTag();
        for (String str : attachment) {
            tag.add(StringTag.valueOf(str));
        }
        return tag;
    }
}
