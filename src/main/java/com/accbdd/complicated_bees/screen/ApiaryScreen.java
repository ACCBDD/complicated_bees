package com.accbdd.complicated_bees.screen;

import com.accbdd.complicated_bees.ComplicatedBees;
import com.accbdd.complicated_bees.genetics.gene.GeneLifespan;
import com.accbdd.complicated_bees.item.BeeItem;
import com.accbdd.complicated_bees.item.QueenItem;
import com.accbdd.complicated_bees.registry.ItemsRegistration;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

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
            int progress = menu.getScaledProgress(BeeItem.getAge(menu.getQueen()), GeneLifespan.get(BeeItem.getGenome(menu.getQueen())).getLifespan());
            graphics.blit(GUI,
                    x + 18,
                    y + 36 + progress,
                    179,
                    progress,
                    3,
                    45 - progress);
        }
    }
}
