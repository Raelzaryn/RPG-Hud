package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(value=EnvType.CLIENT)
public class HudElementMiscVanilla extends HudElement{

	public HudElementMiscVanilla() {
		super(HudElementType.MISC, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return !this.mc.getDebugHud().shouldShowDebugHud() && this.settings.getBoolValue(Settings.enable_fps);
	}
	
	@Override
	public void drawElement(DrawContext dc, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		renderFPs(dc, scaledWidth, scaledHeight);
		
	}
	
	private void renderFPs(DrawContext dc, int scaledWidth, int scaledHeight) {
		float scale = getScale();
		dc.getMatrices().scale(scale, scale, scale);
		String fps = this.mc.fpsDebugString.split(" ")[0];
		dc.drawText(this.mc.textRenderer, fps, getPosX(scaledWidth), getPosY(scaledHeight), 0xFF505050, false);
		scale = getInvertedScale();
		dc.getMatrices().scale(scale, scale, scale);
	}

    @Override
    public int getPosX(int scaledWidth) {
        return (int) ((1 + this.settings.getPositionValue(Settings.fps_position)[0]) * getInvertedScale());
    }

    @Override
    public int getPosY(int scaledHeight) {
        return (int) ((1 + this.settings.getPositionValue(Settings.fps_position)[1]) * getInvertedScale());
    }

    @Override
    public float getScale() {
        float scale = (float)this.settings.getDoubleValue(Settings.fps_scale);
        return scale;
    }
}
