package net.spellcraftgaming.rpghud.gui.hud;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.hotbar.HudElementArmorHotbar;
import net.spellcraftgaming.rpghud.gui.hud.element.hotbar.HudElementFoodHotbar;
import net.spellcraftgaming.rpghud.gui.hud.element.hotbar.HudElementHealthHotbar;
import net.spellcraftgaming.rpghud.gui.hud.element.hotbar.HudElementHealthMountHotbar;
import net.spellcraftgaming.rpghud.gui.hud.element.hotbar.HudElementHotbarHotbar;
import net.spellcraftgaming.rpghud.gui.hud.element.hotbar.HudElementLevelHotbar;
import net.spellcraftgaming.rpghud.gui.hud.element.hotbar.HudElementWidgetHotbar;

@Environment(EnvType.CLIENT)
public class HudHotbarWidget extends HudDefault {

	public HudHotbarWidget(MinecraftClient mc, String hudKey, String hudName) {
		super(mc, hudKey, hudName);
		this.chatOffset = -22;
	}

	@Override
	public HudElement setElementArmor() {
		return new HudElementArmorHotbar();
	}

	@Override
	public HudElement setElementFood() {
		return new HudElementFoodHotbar();
	}

	@Override
	public HudElement setElementHealth() {
		return new HudElementHealthHotbar();
	}

	@Override
	public HudElement setElementHealthMount() {
		return new HudElementHealthMountHotbar();
	}

	@Override
	public HudElement setElementLevel() {
		return new HudElementLevelHotbar();
	}

	@Override
	public HudElement setElementWidget() {
		return new HudElementWidgetHotbar();
	}

	@Override
	public HudElement setElementHotbar() {
		return new HudElementHotbarHotbar();
	}
}
