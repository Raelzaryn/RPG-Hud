package net.spellcraftgaming.rpghud.main;

import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.FMLNetworkConstants;
import net.spellcraftgaming.rpghud.event.ClientEventHandler;
import net.spellcraftgaming.rpghud.gui.hud.Hud;
import net.spellcraftgaming.rpghud.gui.hud.HudDefault;
import net.spellcraftgaming.rpghud.gui.hud.HudExtendedWidget;
import net.spellcraftgaming.rpghud.gui.hud.HudFullTexture;
import net.spellcraftgaming.rpghud.gui.hud.HudHotbarWidget;
import net.spellcraftgaming.rpghud.gui.hud.HudModern;
import net.spellcraftgaming.rpghud.gui.hud.HudVanilla;
import net.spellcraftgaming.rpghud.settings.Settings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("rpghud")
public class ModRPGHud {

	public static ModRPGHud instance;
    
	public static boolean[] renderDetailsAgain = { false, false, false };

	public Settings settings;

	/** Map of all registered HUDs */
	public Map<String, Hud> huds = new LinkedHashMap<String, Hud>();

	public static final Logger LOGGER = LogManager.getLogger();
	
	public ModRPGHud() {
		instance = this;
		ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.DISPLAYTEST, () -> Pair.of(() -> FMLNetworkConstants.IGNORESERVERONLY, (a, b) -> true));
		if (FMLEnvironment.dist == Dist.CLIENT) {
			FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
			FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
		} else {
			LOGGER.warn("RPG-Hud is a client-side-only mod and should not be installed server-side, please remove it from your server");
		}
	}
	
    private void setup(final FMLCommonSetupEvent event)
    {
		this.settings = new Settings();
		this.registerHud(new HudVanilla(Minecraft.getInstance(), "vanilla", "Vanilla"));
		this.registerHud(new HudDefault(Minecraft.getInstance(), "default", "Default"));
		this.registerHud(new HudExtendedWidget(Minecraft.getInstance(), "extended", "Extended Widget"));
		this.registerHud(new HudFullTexture(Minecraft.getInstance(), "texture", "Full Texture"));
		this.registerHud(new HudHotbarWidget(Minecraft.getInstance(), "hotbar", "Hotbar Widget"));
		this.registerHud(new HudModern(Minecraft.getInstance(), "modern", "Modern Style"));

		if (!isHudKeyValid(this.settings.getStringValue(Settings.hud_type))) {
			this.settings.setSetting(Settings.hud_type, "vanilla");
		}
    }
    
    private void doClientStuff(final FMLClientSetupEvent event) {
    	ClientEventHandler.init();
    	new RenderOverlay();
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
}
