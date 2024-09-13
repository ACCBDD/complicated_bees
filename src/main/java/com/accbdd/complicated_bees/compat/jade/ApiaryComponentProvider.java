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
import snownee.jade.api.config.IWailaConfig;

import java.util.ArrayList;
import java.util.List;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;
import static com.accbdd.complicated_bees.compat.jade.ComplicatedBeesJade.CYCLE_CONFIG;
import static com.accbdd.complicated_bees.compat.jade.ComplicatedBeesJade.ERRORS_CONFIG;

public class ApiaryComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    public static ApiaryComponentProvider INSTANCE = new ApiaryComponentProvider();

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig iPluginConfig) {
        if (accessor.getServerData().contains("cycle_ticks") && IWailaConfig.get().getPlugin().get(CYCLE_CONFIG)) {
            tooltip.add(Component.translatable("gui.complicated_bees.jade.cycle_ticks", accessor.getServerData().getInt("cycle_ticks")));
        }
        if (accessor.getServerData().contains("errors") && IWailaConfig.get().getPlugin().get(ERRORS_CONFIG)) {
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
        tag.putInt("cycle_ticks", apiary.getCycleProgress());
    }

    @Override
    public ResourceLocation getUid() {
        return new ResourceLocation(MODID, "apiary_errors");
    }
}
