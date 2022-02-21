package net.spellcraftgaming.rpghud.main;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.spellcraftgaming.lib.event.PlayerContainerHandler;
import net.spellcraftgaming.rpghud.gui.hud.*;
import net.spellcraftgaming.rpghud.settings.Settings;

import java.util.LinkedHashMap;
import java.util.Map;

@Mod(modid = ModRPGHud.MOD_ID,
        version = ModRPGHud.VERSION,
        name = ModRPGHud.NAME,
        clientSideOnly = ModRPGHud.CLIENT_SIDE_ONLY,
        guiFactory = ModRPGHud.GUI_FACTORY,
        updateJSON = ModRPGHud.UPDATE_JSON)

public class ModRPGHud {

    /**
     * The mod ID of this mod
     */
    public static final String MOD_ID = "rpghud";
    /**
     * The mod name of this mod
     */
    public static final String NAME = "RPG-Hud";
    /**
     * The mod version of this mod
     */
    public static final String VERSION = "3.8.1";
    /**
     * Path to GuiFactory class of this mod
     */
    public static final String GUI_FACTORY = "net.spellcraftgaming.lib.gui.GuiFactoryRPGHud";

    /**
     * The URL to the update Json file
     */
    public static final String UPDATE_JSON = "http://download.spellcraftgaming.net/rpghud/version/update.json";
    /**
     * If this mod is client side only
     */
    public static final boolean CLIENT_SIDE_ONLY = true;

    /**
     * The instance of this mod
     */
    @Mod.Instance
    public static ModRPGHud instance;

    public Settings settings;

    /**
     * Map of all registered HUDs
     */
    public Map<String, Hud> huds = new LinkedHashMap<String, Hud>();

    /**
     * If the HudElementDetails should be rendered again
     */
    public static boolean[] renderDetailsAgain = {false, false, false};

    /**
     * The function to be run before the initialization
     *
     * @param event FMLPreInitializationEvent
     */
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        this.settings = new Settings();
        this.registerHud(new HudVanilla(Minecraft.getMinecraft(), "vanilla", "Vanilla"));
        this.registerHud(new HudDefault(Minecraft.getMinecraft(), "default", "Default"));
        this.registerHud(new HudExtendedWidget(Minecraft.getMinecraft(), "extended", "Extended Widget"));
        this.registerHud(new HudFullTexture(Minecraft.getMinecraft(), "texture", "Full Texture"));
        this.registerHud(new HudHotbarWidget(Minecraft.getMinecraft(), "hotbar", "Hotbar Widget"));
        this.registerHud(new HudModern(Minecraft.getMinecraft(), "modern", "Modern Style"));
    }

    /**
     * The function to be run with the initialization
     *
     * @param event FMLInitializationEvent
     */
    @EventHandler
    public void init(FMLInitializationEvent event) {
        new RenderOverlay();
        MinecraftForge.EVENT_BUS.register(new PlayerContainerHandler());
    }

    /**
     * The function to be run after the initialization
     *
     * @param event FMLPostInitializationEvent
     */
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        if (!isHudKeyValid(this.settings.getStringValue(Settings.hud_type))) {
            this.settings.setSetting(Settings.hud_type, "vanilla");
        }
    }

    /**
     * Register a new HUD
     *
     * @param hud the hud to be registered
     */
    public void registerHud(Hud hud) {
        this.huds.put(hud.getHudKey(), hud);
    }

    /**
     * Returns the active HUD
     */
    public Hud getActiveHud() {
        return this.huds.get(this.settings.getStringValue(Settings.hud_type));
    }

    /**
     * Returns the vanilla HUD
     */
    public Hud getVanillaHud() {
        return this.huds.get("vanilla");
    }

    /**
     * Checks if a Hud with the specified key is registered
     */
    public boolean isHudKeyValid(String key) {
        return this.huds.containsKey(key);
    }
}
