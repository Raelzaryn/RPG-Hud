package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementExperienceVanilla extends HudElement{

	public HudElementExperienceVanilla() {
		super(HudElementType.EXPERIENCE, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return !this.mc.player.isRidingHorse();
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
            int cap = this.mc.player.xpBarCap();
            int left = width / 2 - 91;

            if (cap > 0)
            {
                short barWidth = 182;
                int filled = (int)(this.mc.player.experience * (barWidth + 1));
                int top = height - 32 + 3;
                gui.drawTexturedModalRect(left, top, 0, 64, barWidth, 5);

                if (filled > 0)
                {
                    gui.drawTexturedModalRect(left, top, 0, 69, filled, 5);
                }
            }
        }
        GlStateManager.enableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	}

}
