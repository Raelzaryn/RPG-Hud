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
		int health = MathHelper.ceiling_float_int(this.mc.thePlayer.getHealth());
		IAttributeInstance attrMaxHealth = this.mc.thePlayer.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
		int absorption = MathHelper.floor_float(this.mc.thePlayer.getAbsorptionAmount());
		int healthMax = (int) attrMaxHealth.getAttributeValue();
		int posX = this.settings.render_player_face ? 49 : 24;
		int posY = this.settings.render_player_face ? 13 : 5;
		
		if(absorption > 1) drawCustomBar(posX, posY, 110, 12, (double) (health + absorption)/ (double) (healthMax + absorption) * 100D, -1, -1, this.settings.color_absorption, offsetColorPercent(this.settings.color_absorption, OFFSET_PERCENT));
		
		if (this.mc.thePlayer.isPotionActive(MobEffects.POISON)) {
			drawCustomBar(posX, posY, 110, 12, (double) health / (double) (healthMax + absorption) * 100D, -1, -1, this.settings.color_poison, offsetColorPercent(this.settings.color_poison, OFFSET_PERCENT));
		} else {
			drawCustomBar(posX, posY, 110, 12, (double) health / (double) (healthMax + absorption) * 100D, -1, -1, this.settings.color_health, offsetColorPercent(this.settings.color_health, OFFSET_PERCENT));
		}
		
		String stringHealth = (health + absorption) + "/" + healthMax;
		if (this.settings.show_numbers_health)
			gui.drawCenteredString(this.mc.fontRendererObj, stringHealth, posX + 55, posY + 2, -1);
	}
}
