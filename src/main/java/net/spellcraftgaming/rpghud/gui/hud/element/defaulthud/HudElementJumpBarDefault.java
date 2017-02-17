package net.spellcraftgaming.rpghud.gui.hud.element.defaulthud;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.EntityLivingBase;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementBarred;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementJumpBarDefault extends HudElementBarred {

	public HudElementJumpBarDefault() {
		super(HudElementType.JUMP_BAR, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return this.mc.thePlayer.getRidingEntity() instanceof EntityLivingBase && (this.settings.limit_jumpbar ? this.mc.thePlayer.getHorseJumpPower() > 0F: true);
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		ScaledResolution res = new ScaledResolution(this.mc);
		int height = res.getScaledHeight();
		int center = res.getScaledWidth() / 2;
		float jumpPower = this.mc.thePlayer.getHorseJumpPower();
		int value = (int) (jumpPower * 100.0F);
		drawCustomBar(center - 70, height - 80, 141, 10, value / 100.0D * 100.0D, this.settings.color_jumpbar, offsetColorPercent(this.settings.color_jumpbar, OFFSET_PERCENT));
	}

}
