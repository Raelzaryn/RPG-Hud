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
		int[] healthColor = getColor(this.settings.color_health);
		int health = MathHelper.ceiling_float_int(this.mc.thePlayer.getHealth());
		IAttributeInstance attrMaxHealth = this.mc.thePlayer.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
		int maxHealth = (int) attrMaxHealth.getAttributeValue();
		if (this.mc.thePlayer.isPotionActive(MobEffects.POISON)) {
			drawCustomBar(49, 13, 110, 12, (double) health / (double) maxHealth * 100D, 0, 0, healthColor[2], healthColor[3]);
		} else {
			drawCustomBar(49, 13, 110, 12, (double) health / (double) maxHealth * 100D, 0, 0, healthColor[0], healthColor[1]);
		}
		String stringHealth = health + "/" + maxHealth;
		if (this.settings.show_numbers_health)
			gui.drawCenteredString(this.mc.fontRendererObj, stringHealth, 49 + 55, 15, -1);
	}

	@Override
	public int[] getColor(int setting) {
		int[] color = new int[4];
		switch (this.settings.color_health) {
		case 0:
			color[0] = HudElementBarred.COLOR_RED[0];
			color[1] = HudElementBarred.COLOR_RED[1];
			color[2] = HudElementBarred.COLOR_RED[0] + 0x4400;
			color[3] = HudElementBarred.COLOR_RED[1] + 0x4400;
			break;
		case 1:
			color[0] = HudElementBarred.COLOR_BLUE[0];
			color[1] = HudElementBarred.COLOR_BLUE[1];
			color[2] = HudElementBarred.COLOR_BLUE[0] + 0x4400;
			color[3] = HudElementBarred.COLOR_BLUE[1] + 0x4400;
			break;
		case 2:
			color[0] = HudElementBarred.COLOR_GREEN[0];
			color[1] = HudElementBarred.COLOR_GREEN[1];
			color[2] = HudElementBarred.COLOR_GREEN[0] + 0x440000;
			color[3] = HudElementBarred.COLOR_GREEN[1] + 0x440000;
			break;
		case 3:
			color[0] = HudElementBarred.COLOR_YELLOW[0];
			color[1] = HudElementBarred.COLOR_YELLOW[1];
			color[2] = (HudElementBarred.COLOR_YELLOW[0] + 0x1100);
			color[3] = (HudElementBarred.COLOR_YELLOW[1] + 0x2200);
			break;
		case 4:
			color[0] = HudElementBarred.COLOR_WHITE[0];
			color[1] = HudElementBarred.COLOR_WHITE[1];
			color[2] = HudElementBarred.COLOR_WHITE[0] - 0x222222;
			color[3] = HudElementBarred.COLOR_WHITE[1] - 0x222222;
			break;
		case 5:
			color[0] = HudElementBarred.COLOR_GREY[0];
			color[1] = HudElementBarred.COLOR_GREY[1];
			color[2] = HudElementBarred.COLOR_GREY[0] - 0x222222;
			color[3] = HudElementBarred.COLOR_GREY[1] - 0x222222;
		}
		return color;
	}
}
