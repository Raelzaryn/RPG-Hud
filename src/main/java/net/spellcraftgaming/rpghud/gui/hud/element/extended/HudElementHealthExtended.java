package net.spellcraftgaming.rpghud.gui.hud.element.extended;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementBarred;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementHealthExtended extends HudElementBarred {

	public HudElementHealthExtended() {
		super(HudElementType.HEALTH, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return this.mc.playerController.shouldDrawHUD();
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		int health = MathHelper.ceiling_float_int(this.mc.thePlayer.getHealth());
		IAttributeInstance attrMaxHealth = this.mc.thePlayer.getEntityAttribute(SharedMonsterAttributes.maxHealth);
		int maxHealth = (int) attrMaxHealth.getAttributeValue();
		int posX = this.settings.render_player_face ? 49 : 25;
		int posY = this.settings.render_player_face ? 9 : 5;
		if (this.mc.thePlayer.isPotionActive(Potion.poison)) {
			drawCustomBar(posX, posY, 110, 12, (double) health / (double) maxHealth * 100D, -1, -1, this.settings.color_poison, offsetColorPercent(this.settings.color_poison, OFFSET_PERCENT));
		} else {
			drawCustomBar(posX, posY, 110, 12, (double) health / (double) maxHealth * 100D, -1, -1, this.settings.color_health, offsetColorPercent(this.settings.color_health, OFFSET_PERCENT));
		}
		String stringHealth = health + "/" + maxHealth;
		if (this.settings.show_numbers_health)
			gui.drawCenteredString(this.mc.fontRendererObj, stringHealth, posX + 55, posY + 2, -1);
	}
}
