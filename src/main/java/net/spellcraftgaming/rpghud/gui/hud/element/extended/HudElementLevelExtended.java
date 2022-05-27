package net.spellcraftgaming.rpghud.gui.hud.element.extended;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.Gui;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@OnlyIn(Dist.CLIENT)
public class HudElementLevelExtended extends HudElement {

	public HudElementLevelExtended() {
		super(HudElementType.LEVEL, 0, 0, 0, 0, true);
		parent = HudElementType.WIDGET;
	}

	@Override
	public boolean checkConditions() {
		return !this.mc.options.hideGui;
	}

	@Override
	public void drawElement(Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		RenderSystem.disableBlend();
		String level = String.valueOf(this.mc.player.experienceLevel);
		Gui.drawString(ms, this.mc.font, level, (this.settings.getBoolValue(Settings.render_player_face) ? 38 : 13) + this.settings.getPositionValue(Settings.level_position)[0] - this.mc.font.width(level) / 2, (this.settings.getBoolValue(Settings.render_player_face) ? 38 : 18) + this.settings.getPositionValue(Settings.level_position)[1], 0x80FF20);
		RenderSystem.enableBlend();
	}

}
