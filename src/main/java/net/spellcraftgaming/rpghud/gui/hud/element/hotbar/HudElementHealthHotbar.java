package net.spellcraftgaming.rpghud.gui.hud.element.hotbar;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.init.MobEffects;
import net.minecraft.util.math.MathHelper;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementBarred;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementHealthHotbar extends HudElementBarred {

	public HudElementHealthHotbar() {
		super(HudElementType.HEALTH, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return this.mc.playerController.shouldDrawHUD() && !(this.mc.thePlayer.getRidingEntity() instanceof EntityLivingBase);
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		ScaledResolution res = new ScaledResolution(this.mc);
		int height = res.getScaledHeight();
		int health = MathHelper.ceiling_float_int(this.mc.thePlayer.getHealth());
		int posX = this.settings.render_player_face ? 49 : 25;
		IAttributeInstance attrMaxHealth = this.mc.thePlayer.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
		int maxHealth = (int) attrMaxHealth.getAttributeValue();
		if (this.mc.thePlayer.isPotionActive(MobEffects.POISON)) {
			drawCustomBar(posX, height - 56, 200, 10, (double) health / (double) maxHealth * 100D, -1, -1, this.settings.color_poison, offsetColorPercent(this.settings.color_poison, OFFSET_PERCENT));
		} else {
			drawCustomBar(posX, height - 56, 200, 10, (double) health / (double) maxHealth * 100D, -1, -1, this.settings.color_health, offsetColorPercent(this.settings.color_health, OFFSET_PERCENT));
		}
		String stringHealth = health + "/" + maxHealth;
		if (this.settings.show_numbers_health)
			gui.drawCenteredString(this.mc.fontRendererObj, stringHealth, posX + 100, height - 55, -1);
	}
}
