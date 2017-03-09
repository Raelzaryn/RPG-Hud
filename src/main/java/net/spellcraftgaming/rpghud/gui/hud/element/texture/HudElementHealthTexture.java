package net.spellcraftgaming.rpghud.gui.hud.element.texture;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.init.MobEffects;
import net.minecraft.util.math.MathHelper;
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
		int health = MathHelper.ceil(this.mc.player.getHealth());
		IAttributeInstance attrMaxHealth = this.mc.player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
		int healthMax = (int) attrMaxHealth.getAttributeValue();
		int posX = this.settings.render_player_face ? 49 : 25;
		int posY = this.settings.render_player_face ? 9 : 5;
		int absorption = MathHelper.floor(this.mc.player.getAbsorptionAmount());
		if(absorption > 1) gui.drawTexturedModalRect(posX, posY, 0, 88, (int) (110.0D * ((double) (health + absorption) / (double) (healthMax + absorption))), 12);
		if (this.mc.player.isPotionActive(MobEffects.POISON)) {
			gui.drawTexturedModalRect(posX, posY, 141, 160, (int) (110.0D * ((double) health / (double) (healthMax + absorption))), 12);
		} else {
			gui.drawTexturedModalRect(posX, posY, 0, 100, (int) (110.0D * ((double) health / (double) (healthMax + absorption))), 12);
		}

		String stringHealth = (health + absorption) + "/" + healthMax;
		if (this.settings.show_numbers_health)
			gui.drawCenteredString(this.mc.fontRendererObj, stringHealth, posX + 55, posY + 2, -1);
		GlStateManager.color(1f, 1f, 1f);
		bind(Gui.ICONS);
	}

}
