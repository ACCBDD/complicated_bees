package com.accbdd.complicated_bees.screen;

import com.accbdd.complicated_bees.block.entity.CentrifugeBlockEntity;
import com.accbdd.complicated_bees.block.entity.GeneratorBlockEntity;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class CentrifugeScreen extends AbstractContainerScreen<CentrifugeMenu> {
    private final ResourceLocation GUI = new ResourceLocation(MODID, "textures/gui/centrifuge.png");

    public CentrifugeScreen(CentrifugeMenu container, Inventory inventory, Component title) {
        super(container, inventory, title);
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTicks, int mouseX, int mouseY) {
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        graphics.blit(GUI, relX, relY, 0, 0, this.imageWidth, this.imageHeight);

        renderProgressArrow(graphics, relX, relY);
        renderPowerBar(graphics, relX, relY);
    }

    private void renderProgressArrow(GuiGraphics graphics, int x, int y) {
        if (menu.isCrafting()) {
            graphics.blit(GUI, x + 60, y + 35, 176, 0, menu.getScaledProgress(), 18);
        }
    }

    private void renderPowerBar(GuiGraphics graphics, int relX, int relY) {
        int powerScaled = getScaled(menu.getPower(), CentrifugeBlockEntity.CAPACITY, 70);
        graphics.blit(GUI,
                relX + 163,
                relY + 8 + (70 - powerScaled),
                196,
                70 - powerScaled,
                5,
                powerScaled);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);
        renderTooltip(graphics, mouseX, mouseY);
    }

    public int getScaled(int value, int max, int scaleTo) {
        if (max == 0) return 0;
        return (int) Math.ceil((double) (value * scaleTo) / max);
    }

    @Override
    protected void renderTooltip(GuiGraphics graphics, int pX, int pY) {
        super.renderTooltip(graphics, pX, pY);
        if (pX >= leftPos + 163 && pX < leftPos + 163 + 5 && pY >= topPos + 8 && pY < topPos + 8 + 70) {
            int power = menu.getPower();
            graphics.renderTooltip(this.font, Component.literal(power + " RF"), pX, pY);
        }
    }
}
