package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.lib.gui.override.GuiIngameRPGHud;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementRecordOverlayVanilla extends HudElement {

	public HudElementRecordOverlayVanilla() {
		super(HudElementType.RECORD_OVERLAY, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return GameData.overlayMessageTime((GuiIngameRPGHud)this.mc.ingameGUI) > 0;
	}
	
	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		GuiIngameRPGHud guiIngame = (GuiIngameRPGHud) gui;
    	ScaledResolution scaledresolution = new ScaledResolution(this.mc);
        int i = scaledresolution.getScaledWidth();
        int j = scaledresolution.getScaledHeight();
        float f2 = GameData.overlayMessageTime(guiIngame) - partialTicks;
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
            GameData.tryBlendFuncSeparate();
            int l = GameData.overlayColor(guiIngame, f2);

            if (GameData.isRecordPlaying(guiIngame))
            {
                l = GameData.hsvToRGB(f2 / 50.0F, 0.7F, 0.6F) & 16777215;
            }

            this.mc.fontRendererObj.drawString(GameData.getOverlayText(guiIngame), -this.mc.fontRendererObj.getStringWidth(GameData.getOverlayText(guiIngame)) / 2, -4, l + (l1 << 24 & -16777216));
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
	}

}
