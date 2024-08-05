package com.accbdd.complicated_bees.screen;

import com.accbdd.complicated_bees.datagen.ItemTagGenerator;
import com.accbdd.complicated_bees.genetics.GeneticHelper;
import com.accbdd.complicated_bees.genetics.gene.GeneTolerant;
import com.accbdd.complicated_bees.genetics.gene.IGene;
import com.accbdd.complicated_bees.registry.GeneRegistration;
import com.accbdd.complicated_bees.screen.widget.AnalyzerScrollWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

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
        renderTooltip(graphics, mousex, mousey);
    }

    @Override
    protected void renderTooltip(GuiGraphics graphics, int mousex, int mousey) {
        super.renderTooltip(graphics, mousex, mousey);
    }
}
