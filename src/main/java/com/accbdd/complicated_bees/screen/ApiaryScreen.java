package com.accbdd.complicated_bees.screen;

import com.accbdd.complicated_bees.genetics.GeneticHelper;
import com.accbdd.complicated_bees.genetics.gene.GeneLifespan;
import com.accbdd.complicated_bees.genetics.gene.enums.EnumLifespan;
import com.accbdd.complicated_bees.item.BeeItem;
import com.accbdd.complicated_bees.util.enums.EnumErrorCodes;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class ApiaryScreen extends AbstractContainerScreen<ApiaryMenu> {
    private final ResourceLocation GUI = new ResourceLocation(MODID, "textures/gui/apiary.png");

    public ApiaryScreen(ApiaryMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageHeight = 187;
        this.inventoryLabelY = this.imageHeight - 10000;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTicks, int mouseX, int mouseY) {
        renderBackground(graphics);
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        graphics.blit(GUI, relX, relY, 0, 0, this.imageWidth, this.imageHeight);

        renderStatusBar(graphics, relX, relY);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);
        renderTooltip(graphics, mouseX, mouseY);
    }

    public void renderStatusBar(GuiGraphics graphics, int x, int y) {
        if (menu.hasQueen()) {
            ItemStack queen = menu.getQueen();
            int lifespan = ((EnumLifespan) GeneticHelper.getChromosome(queen, true).getGene(GeneLifespan.ID).get()).value;
            int progress = menu.getScaledProgress(BeeItem.getAge(queen), lifespan);
            int errorCode = menu.getData().get(2);
            boolean errorStatus = errorCode > 0 && errorCode != EnumErrorCodes.ECSTATIC.value;
            int barColor = errorStatus ? 182 : errorCode == 128 ? 176 : 179;
            graphics.blit(GUI,
                    x + 18,
                    y + 36 + (errorStatus ? 0 : progress),
                    barColor,
                    errorStatus ? 0 : progress,
                    3,
                    45 - (errorStatus ? 0 : progress));
        } else if (menu.isBreeding()) {
            int progress = menu.getScaledProgress(menu.getData().get(0), menu.getData().get(1));
            graphics.blit(GUI,
                    x + 18,
                    y + 81 - progress,
                    179,
                    0,
                    3,
                    progress);
        } else if (menu.hasQueue()) {
            graphics.blit(GUI,
                    x + 18,
                    y + 36,
                    182,
                    0,
                    3,
                    45);
        }
    }

    @Override
    protected void renderTooltip(GuiGraphics pGuiGraphics, int pX, int pY) {
        super.renderTooltip(pGuiGraphics, pX, pY);
        int errorFlags = menu.getData().get(2);
        int relMouseX = pX - (this.width - this.imageWidth) / 2;
        int relMouseY = pY - (this.height - this.imageHeight) / 2;
        if (errorFlags > 0 && (16 < relMouseX) && (relMouseX < 22) && (34 < relMouseY) && (relMouseY < 82)) {
            List<Component> errors = new ArrayList<>();
            for (EnumErrorCodes errorCode : EnumErrorCodes.values()) {
                if ((errorFlags & errorCode.value) != 0)
                    errors.add(Component.translatable("gui.complicated_bees.error." + errorCode.name));
            }
            pGuiGraphics.renderTooltip(this.font, errors, Optional.empty(), pX, pY);
        }

    }
}
