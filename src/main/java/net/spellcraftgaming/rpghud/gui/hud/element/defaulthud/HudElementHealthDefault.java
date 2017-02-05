package net.spellcraftgaming.rpghud.gui.hud.element.defaulthud;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.init.MobEffects;
import net.minecraft.util.math.MathHelper;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementBarred;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementHealthDefault extends HudElementBarred {

	public HudElementHealthDefault() {
		super(HudElementType.HEALTH, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return this.mc.playerController.shouldDrawHUD();
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		int health = MathHelper.ceil(this.mc.player.getHealth());
		IAttributeInstance attrMaxHealth = this.mc.player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
		int maxHealth = (int) attrMaxHealth.getAttributeValue();
		if (this.mc.player.isPotionActive(MobEffects.POISON)) {
			drawCustomBar(49, 13, 110, 12, (double) health / (double) maxHealth * 100D, -1, -1, this.settings.color_poison, offsetColorPercent(this.settings.color_poison, OFFSET_PERCENT));
		} else {
			drawCustomBar(49, 13, 110, 12, (double) health / (double) maxHealth * 100D, -1, -1, this.settings.color_health, offsetColorPercent(this.settings.color_health, OFFSET_PERCENT));
		}
		String stringHealth = health + "/" + maxHealth;
		if (this.settings.show_numbers_health)
			gui.drawCenteredString(this.mc.fontRendererObj, stringHealth, 49 + 55, 15, -1);
	}
}
