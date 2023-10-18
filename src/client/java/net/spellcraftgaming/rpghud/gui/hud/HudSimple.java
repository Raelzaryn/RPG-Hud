package net.spellcraftgaming.rpghud.gui.hud;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.modern.HudElementAirModern;
import net.spellcraftgaming.rpghud.gui.hud.element.modern.HudElementCompassModern;
import net.spellcraftgaming.rpghud.gui.hud.element.modern.HudElementEntityInspectModern;
import net.spellcraftgaming.rpghud.gui.hud.element.modern.HudElementJumpBarModern;
import net.spellcraftgaming.rpghud.gui.hud.element.simple.HudElementArmorSimple;
import net.spellcraftgaming.rpghud.gui.hud.element.simple.HudElementExperienceSimple;
import net.spellcraftgaming.rpghud.gui.hud.element.simple.HudElementFoodSimple;
import net.spellcraftgaming.rpghud.gui.hud.element.simple.HudElementHealthMountSimple;
import net.spellcraftgaming.rpghud.gui.hud.element.simple.HudElementHealthSimple;
import net.spellcraftgaming.rpghud.gui.hud.element.simple.HudElementHotbarSimple;
import net.spellcraftgaming.rpghud.gui.hud.element.simple.HudElementLevelSimple;

@Environment(value=EnvType.CLIENT)
public class HudSimple extends HudVanilla{

	public HudSimple(MinecraftClient mc, String hudKey, String hudName) {
		super(mc, hudKey, hudName);
	}
	
	@Override
	public HudElement setElementAir() {
		return new HudElementAirModern();
	}
	
	@Override
	public HudElement setElementExperience() {
		return new HudElementExperienceSimple();
	}
	
	@Override
	public HudElement setElementArmor() {
		return new HudElementArmorSimple();
	}

	@Override
	public HudElement setElementFood() {
		return new HudElementFoodSimple();
	}
	
	@Override
	public HudElement setElementHealth() {
		return new HudElementHealthSimple();
	}
	
	@Override
	public HudElement setElementHealthMount() {
		return new HudElementHealthMountSimple();
	}
	
	@Override
	protected HudElement setElementCompass() {
		return new HudElementCompassModern();
	}
	
	@Override
	public HudElement setElementLevel() {
		return new HudElementLevelSimple();
	}
	
	@Override
	public HudElement setElementHotbar() {
		return new HudElementHotbarSimple();
	}
	
	@Override
	protected HudElement setElementEntityInspect() {
		return new HudElementEntityInspectModern();
	}
	
	@Override
	public HudElement setElementJumpBar() {
		return new HudElementJumpBarModern();
	}
}
