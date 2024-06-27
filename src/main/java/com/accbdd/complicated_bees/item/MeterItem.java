package com.accbdd.complicated_bees.item;

import com.accbdd.complicated_bees.genetics.gene.enums.EnumHumidity;
import com.accbdd.complicated_bees.genetics.gene.enums.EnumTemperature;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class MeterItem extends Item {
    public MeterItem(Properties prop) {
        super(prop.stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if(pLevel.isClientSide()) {
            pPlayer.getCooldowns().addCooldown(this, 40);
            EnumHumidity humidity = EnumHumidity.getFromPosition(pLevel, pPlayer.getOnPos());
            EnumTemperature temperature = EnumTemperature.getFromPosition(pLevel, pPlayer.getOnPos());
            pPlayer.displayClientMessage(Component.translatable("gui.complicated_bees.humidity_label")
                    .append(": ")
                    .append(Component.translatable("gui.complicated_bees.humidity." + humidity.toString())), false);
            pPlayer.displayClientMessage(Component.translatable("gui.complicated_bees.temperature_label")
                    .append(": ")
                    .append(Component.translatable("gui.complicated_bees.temperature." + temperature.toString())), false);
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
