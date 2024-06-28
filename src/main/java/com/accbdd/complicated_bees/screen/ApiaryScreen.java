package com.accbdd.complicated_bees.screen;

import com.accbdd.complicated_bees.genetics.GeneticHelper;
import com.accbdd.complicated_bees.genetics.gene.GeneLifespan;
import com.accbdd.complicated_bees.item.BeeItem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

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
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        graphics.blit(GUI, relX, relY, 0, 0, this.imageWidth, this.imageHeight);

        renderStatusBar(graphics, relX, relY);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        renderBg(graphics, partialTick, mouseX, mouseY);
        super.render(graphics, mouseX, mouseY, partialTick);
        renderTooltip(graphics, mouseX, mouseY);
    }

    public void renderStatusBar(GuiGraphics graphics, int x, int y) {
        if (menu.hasQueen()) {
            ItemStack queen = menu.getQueen();
            int lifespan = (int) GeneticHelper.getChromosome(queen, true).getGene(GeneLifespan.ID).get();
            int progress = menu.getScaledProgress(BeeItem.getAge(queen), lifespan);
            graphics.blit(GUI,
                    x + 18,
                    y + 36 + progress,
                    176 + (menu.getData().get(2) * 3),
                    progress,
                    3,
                    45 - progress);
        } else if (menu.isBreeding()) {
            int progress = menu.getScaledProgress(menu.getData().get(0), menu.getData().get(1));
            graphics.blit(GUI,
                    x + 18,
                    y + 81 - progress,
                    176 + (menu.getData().get(2) * 3),
                    0,
                    3,
                    progress);
        }
    }
}
