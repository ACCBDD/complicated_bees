package com.accbdd.complicated_bees.screen;

import com.accbdd.complicated_bees.block.entity.GeneratorBlockEntity;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class GeneratorScreen extends AbstractContainerScreen<GeneratorMenu> {
    private final int ENERGY_LEFT = 163;
    private final int ENERGY_TOP = 8;
    private final int ENERGY_WIDTH = 5;
    private final int ENERGY_HEIGHT = 46;
    private final ResourceLocation GUI = new ResourceLocation(MODID, "textures/gui/generator.png");

    public GeneratorScreen(GeneratorMenu container, Inventory inventory, Component title) {
        super(container, inventory, title);
        this.imageWidth = 176;
        this.imageHeight = 143;
        this.inventoryLabelY = this.imageHeight - 96;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTicks, int mouseX, int mouseY) {
        renderBackground(graphics);
        graphics.blit(GUI, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight);
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        renderPowerBar(graphics, relX, relY);
        renderBurnBar(graphics, relX, relY);
    }

    @Override
    public void render(GuiGraphics graphics, int mousex, int mousey, float partialTick) {
        super.render(graphics, mousex, mousey, partialTick);
        // Render tooltip with power if in the energy box
        renderTooltip(graphics, mousex, mousey);
    }

    public void renderPowerBar(GuiGraphics graphics, int x, int y) {
        int powerScaled = getScaled(menu.getPower(), GeneratorBlockEntity.CAPACITY, ENERGY_HEIGHT);
        graphics.blit(GUI,
                x + ENERGY_LEFT,
                y + ENERGY_TOP + (ENERGY_HEIGHT - powerScaled),
                190,
                ENERGY_HEIGHT - powerScaled,
                ENERGY_WIDTH,
                powerScaled);
    }

    public void renderBurnBar(GuiGraphics graphics, int x, int y) {
        int burnScaled = getScaled(menu.getBurnTime(), menu.getMaxBurnTime(), 14);
        graphics.blit(GUI,
                x + 81,
                y + 15 + (14 - burnScaled),
                176,
                14 - burnScaled,
                14,
                burnScaled);
    }

    public int getScaled(int value, int max, int scaleTo) {
        if (max == 0) return 0;
        return (int) Math.ceil((double) (value * scaleTo) / max);
    }

    @Override
    protected void renderTooltip(GuiGraphics graphics, int mousex, int mousey) {
        super.renderTooltip(graphics, mousex, mousey);
        if (mousex >= leftPos + ENERGY_LEFT && mousex < leftPos + ENERGY_LEFT + ENERGY_WIDTH && mousey >= topPos + ENERGY_TOP && mousey < topPos + ENERGY_TOP + ENERGY_HEIGHT) {
            int power = menu.getPower();
            graphics.renderTooltip(this.font, Component.literal(power + " RF"), mousex, mousey);
        }
    }
}
