package net.spellcraftgaming.rpghud.gui.hud.element.extended;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.init.MobEffects;
import net.minecraft.util.math.MathHelper;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementBarred;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementHealthExtended extends HudElementBarred{

	public HudElementHealthExtended() {
		super(HudElementType.HEALTH, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return !this.mc.gameSettings.showDebugInfo && this.mc.playerController.shouldDrawHUD();
	}
	
	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		int[] healthColor = getColor(this.settings.color_health);
		int health = MathHelper.ceil(this.mc.player.getHealth());
		IAttributeInstance attrMaxHealth = this.mc.player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
		int maxHealth = (int) attrMaxHealth.getAttributeValue();
		if (!this.mc.player.isPotionActive(MobEffects.POISON)) {
			drawCustomBar(49, 9, 110, 12, (double) health / (double) maxHealth * 100D, 0, 0, healthColor[0], healthColor[1]);
		} else {
			drawCustomBar(49, 9, 110, 12, (double) health / (double) maxHealth * 100D, 0, 0, healthColor[2], healthColor[3]);
		}
		String stringHealth = health + "/" + maxHealth;
		if(this.settings.show_numbers_health) gui.drawCenteredString(this.mc.fontRendererObj, stringHealth, 49 + 55, 11, -1);
	}

	@Override
	public int[] getColor(int setting) {
		int[] color = new int[4];
		switch (this.settings.color_health){
		case 0:
			color[0] = this.colorRed[0];
			color[1] = this.colorRed[1];
			color[2] = this.colorRed[0] + 0x4400;
			color[3] = this.colorRed[1] + 0x4400;
			break;
		case 1:
			color[0] = this.colorBlue[0];
			color[1] = this.colorBlue[1];
			color[2] = this.colorBlue[0] + 0x4400;
			color[3] = this.colorBlue[1] + 0x4400;
			break;
		case 2:
			color[0] = this.colorGreen[0];
			color[1] = this.colorGreen[1];
			color[2] = this.colorGreen[0] + 0x440000;
			color[3] = this.colorGreen[1] + 0x440000;
			break;
		case 3:
			color[0] = this.colorYellow[0];
			color[1] = this.colorYellow[1];
			color[2] = (this.colorYellow[0] + 0x1100);
			color[3] = (this.colorYellow[1] + 0x2200);
			break;
		case 4:
			color[0] = this.colorWhite[0];
			color[1] = this.colorWhite[1];
			color[2] = this.colorWhite[0] - 0x222222;
			color[3] = this.colorWhite[1] - 0x222222;
			break;
		case 5:
			color[0] = this.colorGrey[0];
			color[1] = this.colorGrey[1];
			color[2] = this.colorGrey[0] - 0x222222;
			color[3] = this.colorGrey[1] - 0x222222;
		}
		return color;
	}
}
