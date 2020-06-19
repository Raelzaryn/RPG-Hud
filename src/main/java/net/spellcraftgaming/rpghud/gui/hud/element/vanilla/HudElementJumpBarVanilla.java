package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLiving;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementJumpBarVanilla extends HudElement {

	public HudElementJumpBarVanilla() {
		super(HudElementType.JUMP_BAR, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return mc.player.getRidingEntity() instanceof EntityLiving;
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.disableBlend();
	    this.mc.getTextureManager().bindTexture(Gui.ICONS);
	    float f = this.mc.player.getHorseJumpPower();
	    int j = (int)(f * 183.0F);
	    int k = scaledHeight - 32 + 3;
	    gui.drawTexturedModalRect(scaledWidth / 2 - 91, k, 0, 84, 182, 5);
	    if (j > 0) {
	       gui.drawTexturedModalRect(scaledWidth / 2 - 91, k, 0, 89, j, 5);
	     }

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

}
