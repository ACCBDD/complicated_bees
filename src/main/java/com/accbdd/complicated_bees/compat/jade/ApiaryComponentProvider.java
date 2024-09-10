package com.accbdd.complicated_bees.compat.jade;

import com.accbdd.complicated_bees.block.entity.ApiaryBlockEntity;
import com.accbdd.complicated_bees.util.enums.EnumErrorCodes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

import java.util.ArrayList;
import java.util.List;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class ApiaryComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    public static ApiaryComponentProvider INSTANCE = new ApiaryComponentProvider();

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig iPluginConfig) {
        if (accessor.getServerData().contains("errors")) {
            int errorFlags = accessor.getServerData().getInt("errors");
            List<Component> errors = new ArrayList<>();
            for (EnumErrorCodes errorCode : EnumErrorCodes.values()) {
                if ((errorFlags & errorCode.value) != 0)
                    errors.add(Component.translatable("gui.complicated_bees.error." + errorCode.name));
            }
            tooltip.addAll(errors);
        }
    }

    @Override
    public void appendServerData(CompoundTag tag, BlockAccessor accessor) {
        ApiaryBlockEntity apiary = (ApiaryBlockEntity) accessor.getBlockEntity();
        tag.putInt("errors", apiary.getData().get(2));
    }

    @Override
    public ResourceLocation getUid() {
        return new ResourceLocation(MODID, "apiary_errors");
    }
}
