package net.spellcraftgaming.rpghud.gui.hud;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.extended.HudElementClockExtended;
import net.spellcraftgaming.rpghud.gui.hud.element.extended.HudElementDetailsExtended;
import net.spellcraftgaming.rpghud.gui.hud.element.extended.HudElementExperienceExtended;
import net.spellcraftgaming.rpghud.gui.hud.element.extended.HudElementFoodExtended;
import net.spellcraftgaming.rpghud.gui.hud.element.extended.HudElementHealthExtended;
import net.spellcraftgaming.rpghud.gui.hud.element.extended.HudElementHealthMountExtended;
import net.spellcraftgaming.rpghud.gui.hud.element.extended.HudElementLevelExtended;
import net.spellcraftgaming.rpghud.gui.hud.element.extended.HudElementWidgetExtended;

@Environment(value=EnvType.CLIENT)
public class HudExtendedWidget extends HudDefault {

	public HudExtendedWidget(MinecraftClient mc, String hudKey, String hudName) {
		super(mc, hudKey, hudName);
	}

	@Override
	public HudElement setElementExperience() {
		return new HudElementExperienceExtended();
	}

	@Override
	public HudElement setElementHealthMount() {
		return new HudElementHealthMountExtended();
	}

	@Override
	public HudElement setElementHotbar() {
		return null;
	}

	@Override
	public HudElement setElementFood() {
		return new HudElementFoodExtended();
	}

	@Override
	public HudElement setElementHealth() {
		return new HudElementHealthExtended();
	}

	@Override
	public HudElement setElementWidget() {
		return new HudElementWidgetExtended();
	}
	
	@Override
	public HudElement setElementClock() {
		return new HudElementClockExtended();
	}

	@Override
	public HudElement setElementDetails() {
		return new HudElementDetailsExtended();
	}

	@Override
	public HudElement setElementLevel() {
		return new HudElementLevelExtended();
	}
}
