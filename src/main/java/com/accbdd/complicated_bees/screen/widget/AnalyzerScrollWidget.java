package com.accbdd.complicated_bees.screen.widget;

import com.accbdd.complicated_bees.datagen.ItemTagGenerator;
import com.accbdd.complicated_bees.genetics.GeneticHelper;
import com.accbdd.complicated_bees.genetics.gene.GeneTolerant;
import com.accbdd.complicated_bees.genetics.gene.IGene;
import com.accbdd.complicated_bees.registry.GeneRegistration;
import com.accbdd.complicated_bees.screen.AnalyzerMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractScrollWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class AnalyzerScrollWidget extends AbstractScrollWidget {
    private static final ResourceLocation GUI = new ResourceLocation(MODID, "textures/gui/analyzer.png");
    private static final int ACTIVE_COL = 84;
    private static final int INACTIVE_COL = 150;
    private static final int TOLERANCE_INDENT = 10;
    private static final int LINE_HEIGHT = 12;

    private final AnalyzerMenu menu;

    public AnalyzerScrollWidget(int pX, int pY, int pWidth, int pHeight, AnalyzerMenu menu) {
        super(pX, pY, pWidth, pHeight, Component.empty());
        this.menu = menu;
    }

    @Override
    protected int getInnerHeight() {
        return 240;
    }

    @Override
    protected double scrollRate() {
        return 5;
    }

    @Override
    protected void renderBorder(GuiGraphics pGuiGraphics, int pX, int pY, int pWidth, int pHeight) {

    }

    @Override
    protected void renderContents(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderText(pGuiGraphics);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {

    }

    private void renderText(GuiGraphics graphics) {
        ItemStack bee = menu.getSlot(1).getItem();
        if (!bee.is(ItemTagGenerator.BEE)) {
            drawText(graphics, Component.literal("Need bee to analyze!"), 2, 2, 0xFFFFFF);
            return;
        }
        drawCommonGenes(graphics,bee);
    }

    private void drawCommonGenes(GuiGraphics graphics, ItemStack bee) {
        drawText(graphics, Component.literal("Active"), ACTIVE_COL, 2, 0xFFFFFF);
        drawText(graphics, Component.literal("Inactive"), INACTIVE_COL, 2, 0xFFFFFF);

        drawGeneValues(graphics, Component.translatable("gui.complicated_bees.species_label"), bee, GeneRegistration.SPECIES.get(), ACTIVE_COL, INACTIVE_COL, 16);
        drawGeneValues(graphics, Component.translatable("gui.complicated_bees.lifespan_label"), bee, GeneRegistration.LIFESPAN.get(), ACTIVE_COL, INACTIVE_COL, 28);
        drawGeneValues(graphics, Component.translatable("gui.complicated_bees.productivity_label"), bee, GeneRegistration.PRODUCTIVITY.get(), ACTIVE_COL, INACTIVE_COL, 40);
        drawGeneValues(graphics, Component.translatable("gui.complicated_bees.flower_label"), bee, GeneRegistration.FLOWER.get(), ACTIVE_COL, INACTIVE_COL, 52);

        drawTolerantGeneValues(graphics, Component.translatable("gui.complicated_bees.humidity_label"), bee, GeneRegistration.HUMIDITY.get(), ACTIVE_COL, INACTIVE_COL, 64);
        drawTolerantGeneValues(graphics, Component.translatable("gui.complicated_bees.temperature_label"), bee, GeneRegistration.TEMPERATURE.get(), ACTIVE_COL, INACTIVE_COL, 88);
    }

    private void drawText(GuiGraphics graphics, Component component, int x, int y, int color) {
        graphics.drawString(Minecraft.getInstance().font, component, x + getX(), y + getY(), color, false);
    }

    private void drawGeneValues(GuiGraphics graphics, Component label, ItemStack bee, IGene<?> gene, int x, int x2, int y) {
        IGene<?> active = GeneticHelper.getGene(bee, GeneRegistration.GENE_REGISTRY.getKey(gene), true);
        IGene<?> inactive = GeneticHelper.getGene(bee, GeneRegistration.GENE_REGISTRY.getKey(gene), false);
        drawText(graphics, label, 2, y, 0xFFFFFF);
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
        x += getX();
        x2 += getX();
        y += getY() - 2;
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
