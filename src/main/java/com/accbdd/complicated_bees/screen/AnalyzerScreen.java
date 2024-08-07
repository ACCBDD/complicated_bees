package com.accbdd.complicated_bees.screen;

import com.accbdd.complicated_bees.screen.widget.AnalyzerScrollWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class AnalyzerScreen extends AbstractContainerScreen<AnalyzerMenu> {
    private final ResourceLocation GUI = new ResourceLocation(MODID, "textures/gui/analyzer.png");

    public AnalyzerScreen(AnalyzerMenu container, Inventory inventory, Component title) {
        super(container, inventory, title);
        this.imageWidth = 248;
        this.imageHeight = 216;
    }

    @Override
    protected void init() {
        super.init();

        addRenderableWidget(new AnalyzerScrollWidget(leftPos + 8, topPos + 8, 206, 120, getMenu()));
    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTicks, int mouseX, int mouseY) {
        graphics.blit(GUI, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    public void render(GuiGraphics graphics, int mousex, int mousey, float partialTick) {
        super.render(graphics, mousex, mousey, partialTick);
        if (!menu.isBeeAnalyzed()) {
            drawText(graphics, Component.translatable("gui.complicated_bees.analyzer_line_1"), 9, 9, 0xFFFFFF);
            drawText(graphics, Component.translatable("gui.complicated_bees.analyzer_line_2"), 9, 21, 0xFFFFFF);
            drawText(graphics, Component.translatable("gui.complicated_bees.analyzer_line_3"), 9, 39, 0xFFFFFF);
            drawText(graphics, Component.translatable("gui.complicated_bees.analyzer_line_4"), 9, 51, 0xFFFFFF);
            drawText(graphics, Component.translatable("gui.complicated_bees.analyzer_line_5"), 9, 69, 0xFFFFFF);
            drawText(graphics, Component.translatable("gui.complicated_bees.analyzer_line_6"), 9, 81, 0xFFFFFF);
        }
        renderTooltip(graphics, mousex, mousey);
    }

    @Override
    protected void renderTooltip(GuiGraphics graphics, int mousex, int mousey) {
        super.renderTooltip(graphics, mousex, mousey);
    }

    private void drawText(GuiGraphics graphics, Component component, int x, int y, int color) {
        graphics.drawString(Minecraft.getInstance().font, component, x + leftPos, y + topPos, color, false);
    }
}
