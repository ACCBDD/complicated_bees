package com.accbdd.complicated_bees.screen;

import com.accbdd.complicated_bees.datagen.ItemTagGenerator;
import com.accbdd.complicated_bees.genetics.GeneticHelper;
import com.accbdd.complicated_bees.genetics.gene.GeneTolerant;
import com.accbdd.complicated_bees.genetics.gene.IGene;
import com.accbdd.complicated_bees.registry.GeneRegistration;
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
    private static final int ACTIVE_COL = 84;
    private static final int INACTIVE_COL = 150;
    private static final int TOLERANCE_INDENT = 10;
    private static final int LINE_HEIGHT = 12;

    public AnalyzerScreen(AnalyzerMenu container, Inventory inventory, Component title) {
        super(container, inventory, title);
        this.imageWidth = 248;
        this.imageHeight = 216;
    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTicks, int mouseX, int mouseY) {
        graphics.blit(GUI, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight);
        renderText(graphics);
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

    private void renderText(GuiGraphics graphics) {
        ItemStack bee = getMenu().getSlot(1).getItem();
        if (!bee.is(ItemTagGenerator.BEE)) {
            drawText(graphics, Component.literal("Need bee to analyze!"), 9, 9, 0xFFFFFF);
            return;
        }
        drawCommonGenes(graphics,bee);
    }

    private void drawCommonGenes(GuiGraphics graphics, ItemStack bee) {
        drawText(graphics, Component.literal("Active"), ACTIVE_COL, 9, 0xFFFFFF);
        drawText(graphics, Component.literal("Inactive"), INACTIVE_COL, 9, 0xFFFFFF);

        drawGeneValues(graphics, Component.translatable("gui.complicated_bees.species_label"), bee, GeneRegistration.SPECIES.get(), ACTIVE_COL, INACTIVE_COL, 24);
        drawGeneValues(graphics, Component.translatable("gui.complicated_bees.lifespan_label"), bee, GeneRegistration.LIFESPAN.get(), ACTIVE_COL, INACTIVE_COL, 36);
        drawGeneValues(graphics, Component.translatable("gui.complicated_bees.productivity_label"), bee, GeneRegistration.PRODUCTIVITY.get(), ACTIVE_COL, INACTIVE_COL, 48);
        drawGeneValues(graphics, Component.translatable("gui.complicated_bees.flower_label"), bee, GeneRegistration.FLOWER.get(), ACTIVE_COL, INACTIVE_COL, 60);

        drawTolerantGeneValues(graphics, Component.translatable("gui.complicated_bees.humidity_label"), bee, GeneRegistration.HUMIDITY.get(), ACTIVE_COL, INACTIVE_COL, 72);
        drawTolerantGeneValues(graphics, Component.translatable("gui.complicated_bees.temperature_label"), bee, GeneRegistration.TEMPERATURE.get(), ACTIVE_COL, INACTIVE_COL, 96);
    }

    private void drawText(GuiGraphics graphics, Component component, int x, int y, int color) {
        graphics.drawString(Minecraft.getInstance().font, component, x + leftPos, y + topPos, color, false);
    }

    private void drawGeneValues(GuiGraphics graphics, Component label, ItemStack bee, IGene<?> gene, int x, int x2, int y) {
        IGene<?> active = GeneticHelper.getGene(bee, GeneRegistration.GENE_REGISTRY.getKey(gene), true);
        IGene<?> inactive = GeneticHelper.getGene(bee, GeneRegistration.GENE_REGISTRY.getKey(gene), false);
        drawText(graphics, label, 9, y, 0xFFFFFF);
        drawText(graphics, active.getTranslationKey(), x, y, active.isDominant() ? 0xE63225 : 0x257FE6);
        drawText(graphics, inactive.getTranslationKey(), x2, y, inactive.isDominant() ? 0xE63225 : 0x257FE6);
    }

    private void drawGeneTolerance(GuiGraphics graphics, ItemStack bee, GeneTolerant<?> gene, int x, int x2, int y) {
        GeneTolerant<?> active = (GeneTolerant<?>) GeneticHelper.getGene(bee, GeneRegistration.GENE_REGISTRY.getKey(gene), true);
        GeneTolerant<?> inactive = (GeneTolerant<?>) GeneticHelper.getGene(bee, GeneRegistration.GENE_REGISTRY.getKey(gene), false);
        drawText(graphics, active.getTolerance().getTranslationKey(), x, y, active.isDominant() ? 0xE63225 : 0x257FE6);
        drawText(graphics, inactive.getTolerance().getTranslationKey(), x2, y, inactive.isDominant() ? 0xE63225 : 0x257FE6);
    }

    private void drawToleranceIcons(GuiGraphics graphics, ItemStack bee, GeneTolerant<?> gene, int x, int x2, int y) {
        x += leftPos;
        x2 += leftPos;
        y += topPos - 2;
        GeneTolerant<?> active = (GeneTolerant<?>) GeneticHelper.getGene(bee, GeneRegistration.GENE_REGISTRY.getKey(gene), true);
        GeneTolerant<?> inactive = (GeneTolerant<?>) GeneticHelper.getGene(bee, GeneRegistration.GENE_REGISTRY.getKey(gene), false);
        if (active.getTolerance().down != 0 && active.getTolerance().up != 0) {
            graphics.blit(GUI, x, y, 14, 246, 7, 10);
        } else if (active.getTolerance().down != 0) {
            graphics.blit(GUI, x, y, 7, 246, 7, 10);
        } else if (active.getTolerance().up != 0) {
            graphics.blit(GUI, x, y, 0, 246, 7, 10);
        } else {
            graphics.blit(GUI, x, y, 21, 246, 7, 10);
        }

        if (inactive.getTolerance().down != 0 && inactive.getTolerance().up != 0) {
            graphics.blit(GUI, x2, y, 14, 246, 7, 10);
        } else if (inactive.getTolerance().down != 0) {
            graphics.blit(GUI, x2, y, 7, 246, 7, 10);
        } else if (inactive.getTolerance().up != 0) {
            graphics.blit(GUI, x2, y, 0, 246, 7, 10);
        } else {
            graphics.blit(GUI, x2, y, 21, 246, 7, 10);
        }
    }

    private void drawTolerantGeneValues(GuiGraphics graphics, Component label, ItemStack bee, GeneTolerant<?> gene, int x, int x2, int y) {
        drawGeneValues(graphics, label, bee, gene, x, x2, y);
        drawText(graphics, Component.translatable("gui.complicated_bees.tolerance_label"), 9 + TOLERANCE_INDENT, y + LINE_HEIGHT, 0xFFFFFF);
        drawToleranceIcons(graphics, bee, gene, x, x2, y + LINE_HEIGHT);
        drawGeneTolerance(graphics, bee, gene, x + 10, x2 + 10, y + LINE_HEIGHT);
    }
}
