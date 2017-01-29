package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
public class HudElementExperienceLevelVanilla extends HudElement{

	public HudElementExperienceLevelVanilla() {
		super(HudElementType.LEVEL, 0, 0, 0, 0, true);
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableBlend();
		ScaledResolution res = new ScaledResolution(this.mc);
		int width = res.getScaledWidth();
        int height = res.getScaledHeight();
        if (this.mc.playerController.gameIsSurvivalOrAdventure())
        {
            if (this.mc.playerController.gameIsSurvivalOrAdventure() && this.mc.player.experienceLevel > 0)
            {
                this.mc.mcProfiler.startSection("expLevel");
                boolean flag1 = false;
                int color = flag1 ? 16777215 : 8453920;
                String text = "" + this.mc.player.experienceLevel;
                int x = (width - this.mc.fontRendererObj.getStringWidth(text)) / 2;
                int y = height - 31 - 4;
                this.mc.fontRendererObj.drawString(text, x + 1, y, 0);
                this.mc.fontRendererObj.drawString(text, x - 1, y, 0);
                this.mc.fontRendererObj.drawString(text, x, y + 1, 0);
                this.mc.fontRendererObj.drawString(text, x, y - 1, 0);
                this.mc.fontRendererObj.drawString(text, x, y, color);
                this.mc.mcProfiler.endSection();
            }
        }
        GlStateManager.enableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	}

}
