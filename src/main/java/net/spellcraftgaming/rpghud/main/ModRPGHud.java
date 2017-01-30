package net.spellcraftgaming.rpghud.main;

import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.spellcraftgaming.rpghud.event.ClientTickHandler;
import net.spellcraftgaming.rpghud.event.PlayerContainerHandler;
import net.spellcraftgaming.rpghud.gui.hud.Hud;
import net.spellcraftgaming.rpghud.gui.hud.HudDefault;
import net.spellcraftgaming.rpghud.gui.hud.HudExtendedWidget;
import net.spellcraftgaming.rpghud.gui.hud.HudVanilla;
import net.spellcraftgaming.rpghud.settings.ModDebugSettings;
import net.spellcraftgaming.rpghud.settings.ModSettings;

@Mod(	modid = ModRPGHud.MOD_ID, 
		version = ModRPGHud.VERSION, 
		name = ModRPGHud.NAME, 
		clientSideOnly = ModRPGHud.CLIENT_SIDE_ONLY, 
		canBeDeactivated = ModRPGHud.CAN_BE_DEACTIVATED,
		useMetadata = ModRPGHud.USE_METADATA,
		guiFactory = ModRPGHud.GUI_FACTORY)

public class ModRPGHud {
	
	//TODO 3.1: Create settings menu
	//TODO 3.1: Add Extended Widget HUD
	//TODO 3.1: Add Full Texture HUD
	//TODO 3.1: Add Hotbar HUD
	//TODO 3.1: Add Modern Style HUD
	
	//TODO 3.x: Make elements moveable onscreen
	//TODO 3.x: Make color set able by #HexCode
	//TODO 3.x: Add Enemy detail element
	//TODO 3.x: Add compass element
	//TODO 3.x: Simple debug settings configuration
	
	/** The mod ID of RPG-Hud*/
	public static final String MOD_ID = "rpghud";
	public static final String NAME = "RPG-Hud";
	public static final String VERSION = "3.1";
	public static final String GUI_FACTORY = "net.spellcraftgaming.rpghud.gui.GuiFactoryRPGHud";
	
	public static final boolean CLIENT_SIDE_ONLY = true;
	public static final boolean CAN_BE_DEACTIVATED = true;
	public static final boolean USE_METADATA = false;
	
	@Mod.Instance
	public static ModRPGHud instance;
	
	public ModDebugSettings settingsDebug;
	public ModSettings settings;
	
	public Map<String, Hud> huds = new LinkedHashMap<String, Hud>();
	
	public static boolean[] renderDetailsAgain =  {false, false, false};
	
	@EventHandler
	public void preInit(@SuppressWarnings("unused") FMLPreInitializationEvent event){
		this.settingsDebug = new ModDebugSettings(Minecraft.getMinecraft().mcDataDir);
		this.settings = new ModSettings(Minecraft.getMinecraft().mcDataDir);
	}
	
	@EventHandler
	public void init(@SuppressWarnings("unused") FMLInitializationEvent event){		
		MinecraftForge.EVENT_BUS.register(new ClientTickHandler());
		MinecraftForge.EVENT_BUS.register(new PlayerContainerHandler());
		this.registerHud(new HudVanilla(Minecraft.getMinecraft(), "vanilla", "Vanilla"));
		this.registerHud(new HudDefault(Minecraft.getMinecraft(), "default", "Default"));
		this.registerHud(new HudExtendedWidget(Minecraft.getMinecraft(), "extended", "Extended Widget"));
		this.registerHud(new HudDefault(Minecraft.getMinecraft(), "hotbar", "Hotbar Widget"));
		this.registerHud(new HudDefault(Minecraft.getMinecraft(), "texture", "Full Texture"));
		this.registerHud(new HudDefault(Minecraft.getMinecraft(), "modern", "Modern Style"));
	}
	
	@EventHandler
	public void postInit(@SuppressWarnings("unused") FMLPostInitializationEvent event){
		
	}
	
	public void registerHud(Hud hud) {
		this.huds.put(hud.getHudKey(), hud);
	}
	
	public Hud getActiveHud() {
		return this.huds.get(this.settings.hud_type);
	}
	
	public Hud getVanillaHud() {
		return this.huds.get("vanilla");
	}
}
