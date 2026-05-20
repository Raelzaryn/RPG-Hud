package net.spellcraftgaming.rpghud.main;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.spellcraftgaming.rpghud.gui.hud.*;
import net.spellcraftgaming.rpghud.settings.Settings;

import java.util.LinkedHashMap;
import java.util.Map;

@Environment(value=EnvType.CLIENT)
public class ModRPGHud implements ClientModInitializer{
    public final String MODID = "rpg-hud";

	public static ModRPGHud instance;
    
	public static boolean[] renderDetailsAgain = { false, false, false };

	public static int screenOffset = 0;
	
	public Settings settings;

	/** Map of all registered HUDs */
	public Map<String, Hud> huds = new LinkedHashMap<String, Hud>();
	
    public void onInitializeClient()
    {
        instance = this;
		this.settings = new Settings();
		this.registerHud(new HudVanilla(Minecraft.getInstance(), "vanilla", "Vanilla"));
		this.registerHud(new HudSimple(Minecraft.getInstance(), "simple", "Simplified"));
		this.registerHud(new HudDefault(Minecraft.getInstance(), "default", "Default"));
		this.registerHud(new HudExtendedWidget(Minecraft.getInstance(), "extended", "Extended Widget"));
		this.registerHud(new HudFullTexture(Minecraft.getInstance(), "texture", "Full Texture"));
		//this.registerHud(new HudHotbarWidget(Minecraft.getInstance(), "hotbar", "Hotbar Widget"));
		this.registerHud(new HudModern(Minecraft.getInstance(), "modern", "Modern Style"));

		if (!isHudKeyValid(this.settings.getStringValue(Settings.hud_type))) {
			this.settings.setSetting(Settings.hud_type, "vanilla");
		}
        new RenderOverlay();
        if (isClass("io.github.prospector.modmenu.ModMenu")) screenOffset = 12;
    }
    
	/**
	 * Register a new HUD
	 * 
	 * @param hud
	 *            the hud to be registered
	 */
	public void registerHud(Hud hud) {
		this.huds.put(hud.getHudKey(), hud);
	}

	/** Returns the active HUD */
	public Hud getActiveHud() {
		return this.huds.get(this.settings.getStringValue(Settings.hud_type));
	}

	/** Returns the vanilla HUD */
	public Hud getVanillaHud() {
		return this.huds.get("vanilla");
	}

	public boolean isVanillaHud() {
	    return this.settings.getStringValue(Settings.hud_type) == "vanilla";
	}
	
	/** Checks if a Hud with the specified key is registered */
	public boolean isHudKeyValid(String key) {
		return this.huds.containsKey(key);
	}
	
	public static boolean isClass(String className) {
	    try  {
	        Class.forName(className);
	        return true;
	    }  catch (ClassNotFoundException e) {
	        return false;
	    }
	}
}
