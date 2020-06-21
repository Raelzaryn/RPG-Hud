package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.gui.AbstractGui;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementLevelVanilla extends HudElement {

    public HudElementLevelVanilla() {
        super(HudElementType.LEVEL, 0, 0, 0, 0, true);
    }

    @Override
    public boolean checkConditions() {
        return this.mc.playerController.shouldDrawHUD() && this.mc.player.experienceLevel > 0;
    }

    @Override
    public void drawElement(AbstractGui gui, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        GlStateManager.disableBlend();
        String level = String.valueOf(this.mc.player.experienceLevel);
        int i1 = (scaledWidth - this.mc.fontRenderer.getStringWidth(level)) / 2;
        int j1 = scaledHeight - 31 - 4;
        this.mc.fontRenderer.drawString(level, i1 + 1, j1, 0);
        this.mc.fontRenderer.drawString(level, i1 - 1, j1, 0);
        this.mc.fontRenderer.drawString(level, i1, j1 + 1, 0);
        this.mc.fontRenderer.drawString(level, i1, j1 - 1, 0);
        this.mc.fontRenderer.drawString(level, i1, j1, 8453920);
        GlStateManager.enableBlend();
    }

}
