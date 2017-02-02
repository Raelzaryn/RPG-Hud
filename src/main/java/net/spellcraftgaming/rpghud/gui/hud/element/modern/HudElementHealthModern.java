package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.init.MobEffects;
import net.minecraft.util.math.MathHelper;
import net.spellcraftgaming.rpghud.gui.hud.HudModern;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementBarred;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementHealthModern extends HudElementBarred{

	public HudElementHealthModern() {
		super(HudElementType.HEALTH, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return this.mc.playerController.shouldDrawHUD();
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		int[] healthColor = getColor(this.settings.color_health);
		int health = MathHelper.ceil(this.mc.player.getHealth());
		IAttributeInstance attrMaxHealth = this.mc.player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
		int healthMax = (int) attrMaxHealth.getAttributeValue();
		int xOffset = ((HudModern) this.rpgHud.huds.get("modern")).getPosX();
		
		int width = this.mc.fontRendererObj.getStringWidth(healthMax + "/" + healthMax) / 2 + 4;
		((HudModern) this.rpgHud.huds.get("modern")).setPosX(width);
		String stringHealth = health + "/" + healthMax;
		if (this.settings.show_numbers_health) {
			drawRect(this.settings.render_player_face ? 23 : 2, 4, width, 8, 0xA0000000);
			GL11.glScaled(0.5D, 0.5D, 0.5D);
			gui.drawCenteredString(this.mc.fontRendererObj, stringHealth, 66, 12, -1);
			GL11.glScaled(2.0D, 2.0D, 2.0D);
		}
		
		int posX = (this.settings.render_player_face ? 24 : 2) + (this.settings.show_numbers_health ? xOffset - 1: 0);
		drawTetragon(posX, posX, 3, 3, 97, 83, 10, 10, 0xA0000000);
		drawTetragon(posX + 2, posX + 2, 5, 5, 89, 79, 6, 6, 0x20FFFFFF);
		
		if (this.mc.player.isPotionActive(MobEffects.POISON)) {
			drawTetragon(posX + 2, posX + 2, 5, 5, (int) (89 * ((double)health / (double) healthMax)), (int) (89 * ((double)health / (double) healthMax)) - 10, 6, 6, healthColor[1]);
		} else {
			drawTetragon(posX + 2, posX + 2, 5, 5, (int) (89 * ((double)health / (double) healthMax)), (int) (89 * ((double)health / (double) healthMax)) - 10, 6, 6, healthColor[0]);
		}
	}

	@Override
	public int[] getColor(int setting) {
		int[] color = new int[2];
		switch (this.settings.color_health) {
		case 0:
			color[0] = HudElementBarred.COLOR_RED[0];
			color[1] = HudElementBarred.COLOR_RED[0] + 0x4400;
			break;
		case 1:
			color[0] = HudElementBarred.COLOR_BLUE[0];
			color[1] = HudElementBarred.COLOR_BLUE[0] + 0x4400;
			break;
		case 2:
			color[0] = HudElementBarred.COLOR_GREEN[0];
			color[1] = HudElementBarred.COLOR_GREEN[0] + 0x440000;
			break;
		case 3:
			color[0] = HudElementBarred.COLOR_YELLOW[0];
			color[1] = (HudElementBarred.COLOR_YELLOW[0] + 0x1100);
			break;
		case 4:
			color[0] = HudElementBarred.COLOR_WHITE[0];
			color[1] = HudElementBarred.COLOR_WHITE[0] - 0x222222;
			break;
		case 5:
			color[0] = HudElementBarred.COLOR_GREY[0];
			color[1] = HudElementBarred.COLOR_GREY[0] - 0x222222;
		}
		return color;
	}

}
