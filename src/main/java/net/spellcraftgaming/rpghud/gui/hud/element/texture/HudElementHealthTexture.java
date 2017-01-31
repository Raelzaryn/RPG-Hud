package net.spellcraftgaming.rpghud.gui.hud.element.texture;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.init.MobEffects;
import net.minecraft.util.math.MathHelper;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementTexture;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementHealthTexture extends HudElementTexture{

	public HudElementHealthTexture() {
		super(HudElementType.HEALTH, 0, 0, 0, 0, false);
	}

	@Override
	public boolean checkConditions() {
		return !this.mc.gameSettings.showDebugInfo && this.mc.playerController.shouldDrawHUD();
	}
	
	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		bind(INTERFACE);
		int health = MathHelper.ceil(this.mc.player.getHealth());
		IAttributeInstance attrMaxHealth = this.mc.player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
		int maxHealth = (int) attrMaxHealth.getAttributeValue();
		if (this.mc.player.isPotionActive(MobEffects.POISON)) {
			gui.drawTexturedModalRect(49, 9, 141, 160, (int) (110.0D * ((double) health / (double) maxHealth)), 12);
		} else {
			gui.drawTexturedModalRect(49, 9, 0, 100, (int) (110.0D * ((double) health / (double) maxHealth)), 12);
		}
	
		String stringHealth = health + "/" + maxHealth;
		if(this.settings.show_numbers_health) gui.drawCenteredString(this.mc.fontRendererObj, stringHealth, 49 + 55, 11, -1);
		bind(Gui.ICONS);
	}

}
