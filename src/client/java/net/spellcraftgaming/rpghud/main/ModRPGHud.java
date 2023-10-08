package net.spellcraftgaming.rpghud.main;

import java.util.LinkedHashMap;
import java.util.Map;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.spellcraftgaming.rpghud.gui.hud.Hud;
import net.spellcraftgaming.rpghud.gui.hud.HudDefault;
import net.spellcraftgaming.rpghud.gui.hud.HudExtendedWidget;
import net.spellcraftgaming.rpghud.gui.hud.HudFullTexture;
import net.spellcraftgaming.rpghud.gui.hud.HudModern;
import net.spellcraftgaming.rpghud.gui.hud.HudSimple;
import net.spellcraftgaming.rpghud.gui.hud.HudVanilla;
import net.spellcraftgaming.rpghud.settings.Settings;

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
		this.registerHud(new HudVanilla(MinecraftClient.getInstance(), "vanilla", "Vanilla"));
		this.registerHud(new HudSimple(MinecraftClient.getInstance(), "simple", "Simplified"));
		this.registerHud(new HudDefault(MinecraftClient.getInstance(), "default", "Default"));
		this.registerHud(new HudExtendedWidget(MinecraftClient.getInstance(), "extended", "Extended Widget"));
		this.registerHud(new HudFullTexture(MinecraftClient.getInstance(), "texture", "Full Texture"));
		//this.registerHud(new HudHotbarWidget(MinecraftClient.getInstance(), "hotbar", "Hotbar Widget"));
		this.registerHud(new HudModern(MinecraftClient.getInstance(), "modern", "Modern Style"));

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
	
    @Environment(value=EnvType.CLIENT)
    public static enum HeartTypeNew {
        CONTAINER(0, false),
        NORMAL(2, true),
        POISONED(4, true),
        WITHERED(6, true),
        ABSORBING(8, false),
        FROZEN(9, false);

        private final int textureIndex;
        private final boolean hasBlinkingTexture;

        HeartTypeNew(int textureIndex, boolean hasBlinkingTexture) {
            this.textureIndex = textureIndex;
            this.hasBlinkingTexture = hasBlinkingTexture;
        }

        public int getU(boolean halfHeart, boolean blinking) {
            int i;
            if (this == CONTAINER) {
                i = blinking ? 1 : 0;
            } else {
                int j = halfHeart ? 1 : 0;
                int k = this.hasBlinkingTexture && blinking ? 2 : 0;
                i = j + k;
            }
            return 16 + (this.textureIndex * 2 + i) * 9;
        }

        public static HeartTypeNew fromPlayerState(PlayerEntity player) {
            HeartTypeNew heartType = player.hasStatusEffect(StatusEffects.POISON) ? POISONED : (player.hasStatusEffect(StatusEffects.WITHER) ? WITHERED : (player.isFrozen() ? FROZEN : NORMAL));
            return heartType;
        }
    }
}
