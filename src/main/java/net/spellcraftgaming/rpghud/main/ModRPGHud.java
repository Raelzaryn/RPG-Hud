package net.spellcraftgaming.rpghud.main;

import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.spellcraftgaming.rpghud.event.ClientTickHandler;
import net.spellcraftgaming.rpghud.gui.hud.Hud;
import net.spellcraftgaming.rpghud.gui.hud.HudVanilla;
import net.spellcraftgaming.rpghud.gui.override.GuiIngameRPGHud;
import net.spellcraftgaming.rpghud.settings.Settings;

@Mod("rpg-hud")
public class ModRPGHud {

	public static ModRPGHud instance;
    
	public static boolean[] renderDetailsAgain = { false, false, false };
	
	public ModRPGHud() {
		instance = this;
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        
        MinecraftForge.EVENT_BUS.register(this);
	}
	public Settings settings;

	/** Map of all registered HUDs */
	public Map<String, Hud> huds = new LinkedHashMap<String, Hud>();
	
    private void setup(final FMLCommonSetupEvent event)
    {
		this.settings = new Settings();
		this.registerHud(new HudVanilla(Minecraft.getInstance(), "vanilla", "Vanilla"));
		/*this.registerHud(new HudDefault(Minecraft.getInstance(), "default", "Default"));
		this.registerHud(new HudExtendedWidget(Minecraft.getInstance(), "extended", "Extended Widget"));
		this.registerHud(new HudFullTexture(Minecraft.getInstance(), "texture", "Full Texture"));
		this.registerHud(new HudHotbarWidget(Minecraft.getInstance(), "hotbar", "Hotbar Widget"));
		this.registerHud(new HudModern(Minecraft.getInstance(), "modern", "Modern Style"));
		*/
		if (!isHudKeyValid(this.settings.getStringValue(Settings.hud_type))) {
			this.settings.setSetting(Settings.hud_type, "vanilla");
		}
    }
    
    private void doClientStuff(final FMLClientSetupEvent event) {
    	ClientTickHandler.init();
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

	/** Checks if a Hud with the specified key is registered */
	public boolean isHudKeyValid(String key) {
		return this.huds.containsKey(key);
	}
}
