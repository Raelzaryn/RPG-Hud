package net.spellcraftgaming.rpghud.main;

import java.util.LinkedHashMap;
import java.util.Map;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.spellcraftgaming.rpghud.gui.hud.Hud;
import net.spellcraftgaming.rpghud.gui.hud.HudDefault;
import net.spellcraftgaming.rpghud.gui.hud.HudExtendedWidget;
import net.spellcraftgaming.rpghud.gui.hud.HudFullTexture;
import net.spellcraftgaming.rpghud.gui.hud.HudHotbarWidget;
import net.spellcraftgaming.rpghud.gui.hud.HudModern;
import net.spellcraftgaming.rpghud.gui.hud.HudVanilla;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(EnvType.CLIENT)

public class ModRPGHud implements ClientModInitializer {
    public final String MODID = "rpg-hud";

    public static ModRPGHud instance;

    public static boolean[] renderDetailsAgain = { false, false, false };

    public static int screenOffset = 0;

    public Settings settings;

    /** Map of all registered HUDs */
    public Map<String, Hud> huds = new LinkedHashMap<String, Hud>();

    public void onInitializeClient() {
        instance = this;
        this.settings = new Settings();
        this.registerHud(new HudVanilla(MinecraftClient.getInstance(), "vanilla", "Vanilla"));
        this.registerHud(new HudDefault(MinecraftClient.getInstance(), "default", "Default"));
        this.registerHud(new HudExtendedWidget(MinecraftClient.getInstance(), "extended", "Extended Widget"));
        this.registerHud(new HudFullTexture(MinecraftClient.getInstance(), "texture", "Full Texture"));
        this.registerHud(new HudHotbarWidget(MinecraftClient.getInstance(), "hotbar", "Hotbar Widget"));
        this.registerHud(new HudModern(MinecraftClient.getInstance(), "modern", "Modern Style"));

        if(!isHudKeyValid(this.settings.getStringValue(Settings.hud_type))) {
            this.settings.setSetting(Settings.hud_type, "vanilla");
        }
        new RenderOverlay();
        if(isClass("io.github.prospector.modmenu.ModMenu"))
            screenOffset = 12;
    }

    /**
     * Register a new HUD
     * 
     * @param hud the hud to be registered
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
        try {
            Class.forName(className);
            return true;
        } catch(ClassNotFoundException e) {
            return false;
        }
    }
}
