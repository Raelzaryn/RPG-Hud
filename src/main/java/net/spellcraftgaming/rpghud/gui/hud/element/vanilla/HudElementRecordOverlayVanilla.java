package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;
import net.spellcraftgaming.rpghud.gui.GuiIngameRPGHud;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementRecordOverlayVanilla extends HudElement {

	public HudElementRecordOverlayVanilla() {
		super(HudElementType.RECORD_OVERLAY, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return ((GuiIngameRPGHud)this.mc.ingameGUI).getRecordPlayingUpFor() > 0;
	}
	
	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		GuiIngameRPGHud guiIngame = (GuiIngameRPGHud) gui;
    	ScaledResolution scaledresolution = new ScaledResolution(this.mc);
        int i = scaledresolution.getScaledWidth();
        int j = scaledresolution.getScaledHeight();
        float f2 = (float)guiIngame.getRecordPlayingUpFor() - partialTicks;
        int l1 = (int)(f2 * 255.0F / 20.0F);

        if (l1 > 255)
        {
            l1 = 255;
        }

        if (l1 > 8)
        {
            GlStateManager.pushMatrix();
            GlStateManager.translate((float)(i / 2), (float)(j - 68), 0.0F);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            int l = 16777215;

            if (guiIngame.getRecordIsPlaying())
            {
                l = MathHelper.hsvToRGB(f2 / 50.0F, 0.7F, 0.6F) & 16777215;
            }

            this.mc.fontRendererObj.drawString(guiIngame.getRecordPlaying(), -this.mc.fontRendererObj.getStringWidth(guiIngame.getRecordPlaying()) / 2, -4, l + (l1 << 24 & -16777216));
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
	}

}
