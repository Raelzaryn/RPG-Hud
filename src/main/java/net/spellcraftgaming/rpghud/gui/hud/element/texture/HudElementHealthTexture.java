package net.spellcraftgaming.rpghud.gui.hud.element.texture;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementTexture;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementHealthTexture extends HudElementTexture {

	public HudElementHealthTexture() {
		super(HudElementType.HEALTH, 0, 0, 0, 0, false);
	}

	@Override
	public boolean checkConditions() {
		return this.mc.playerController.shouldDrawHUD();
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		bind(INTERFACE);
		GlStateManager.color(1f, 1f, 1f);
		int health = MathHelper.ceiling_float_int(this.mc.thePlayer.getHealth());
		IAttributeInstance attrMaxHealth = this.mc.thePlayer.getEntityAttribute(SharedMonsterAttributes.maxHealth);
		int maxHealth = (int) attrMaxHealth.getAttributeValue();
		int posX = this.settings.render_player_face ? 49 : 25;
		int posY = this.settings.render_player_face ? 9 : 5;
		if (this.mc.thePlayer.isPotionActive(Potion.poison)) {
			gui.drawTexturedModalRect(posX, posY, 34, 222, (int) (110.0D * ((double) health / (double) maxHealth)), 12);
		} else {
			gui.drawTexturedModalRect(posX, posY, 0, 100, (int) (110.0D * ((double) health / (double) maxHealth)), 12);
		}

		String stringHealth = health + "/" + maxHealth;
		if (this.settings.show_numbers_health)
			gui.drawCenteredString(this.mc.fontRendererObj, stringHealth, posX + 55, posY + 2, -1);
		GlStateManager.color(1f, 1f, 1f);
		bind(Gui.icons);
	}

}
