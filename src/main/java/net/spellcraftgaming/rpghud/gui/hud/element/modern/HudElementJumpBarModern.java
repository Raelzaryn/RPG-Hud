package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.EntityLivingBase;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementBarred;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementJumpBarModern extends HudElementBarred{

	public HudElementJumpBarModern() {
		super(HudElementType.JUMP_BAR, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return this.mc.thePlayer.getRidingEntity() instanceof EntityLivingBase;
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		ScaledResolution res = new ScaledResolution(this.mc);
		int height = res.getScaledHeight();
		int width = res.getScaledWidth();
		float jumpPower = this.mc.thePlayer.getHorseJumpPower();
		int value = (int) (jumpPower * 100.0F);
		drawRect(width / 2 - 72, height - 78, 144, 2, 0xA0000000);
		drawRect(width / 2 - 72, height - 70, 144, 2, 0xA0000000);
		drawRect(width / 2 - 72, height - 76, 2, 6, 0xA0000000);
		drawRect(width / 2 + 70, height - 76, 2, 6, 0xA0000000);
		drawRect(width / 2 - 70, height - 76, 140, 6, 0x20FFFFFF);
		drawRect(width / 2 - 70, height - 76, (int) (140 * (value / 100.0D)), 6, this.settings.color_jumpbar);
	}

}
