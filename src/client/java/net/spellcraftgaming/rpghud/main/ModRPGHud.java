package net.spellcraftgaming.rpghud.main;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.spellcraftgaming.rpghud.gui.hud.*;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(value=EnvType.CLIENT)
public class ModRPGHud implements ClientModInitializer{
    public final String MODID = "rpg-hud";

	public static ModRPGHud instance;
    
	public static boolean[] renderDetailsAgain = { false, false, false };

	public static int screenOffsetAll = 0;
	public static int screenOffsetTitle = 0;

	public RPGHudCompatibility compatibility;
	
	public Settings settings;

	/** Map of all registered HUDs */
	public Map<String, Hud> huds = new LinkedHashMap<>();
	
    public void onInitializeClient()
    {
        instance = this;
		this.settings = new Settings();

	    this.compatibility = new RPGHudCompatibility();

		this.registerHud(new HudVanilla(MinecraftClient.getInstance(), "vanilla", "Vanilla"));
		this.registerHud(new HudSimple(MinecraftClient.getInstance(), "simple", "Simplified"));
		this.registerHud(new HudDefault(MinecraftClient.getInstance(), "default", "Default"));
		this.registerHud(new HudExtendedWidget(MinecraftClient.getInstance(), "extended", "Extended Widget"));
		this.registerHud(new HudFullTexture(MinecraftClient.getInstance(), "texture", "Full Texture"));
		this.registerHud(new HudHotbarWidget(MinecraftClient.getInstance(), "hotbar", "Hotbar Widget"));
		this.registerHud(new HudModern(MinecraftClient.getInstance(), "modern", "Modern Style"));

		if (!isHudKeyValid(this.settings.getStringValue(Settings.hud_type))) {
			this.settings.setSetting(Settings.hud_type, "vanilla");
		}
        new RenderOverlay();

	    if(compatibility.compatProspector) screenOffsetAll = 12;
	    if(compatibility.compatModMenu) screenOffsetTitle = -12;
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
	    return Objects.equals(this.settings.getStringValue(Settings.hud_type), "vanilla");
	}
	
	/** Checks if a Hud with the specified key is registered */
	public boolean isHudKeyValid(String key) {
		return this.huds.containsKey(key);
	}
	
    public enum HeartTypeNew {
		CONTAINER(Identifier.ofVanilla("hud/heart/container"),
				Identifier.ofVanilla("hud/heart/container_blinking"),
				Identifier.ofVanilla("hud/heart/container"),
				Identifier.ofVanilla("hud/heart/container_blinking"),
				Identifier.ofVanilla("hud/heart/container_hardcore"),
				Identifier.ofVanilla("hud/heart/container_hardcore_blinking"),
				Identifier.ofVanilla("hud/heart/container_hardcore"),
				Identifier.ofVanilla("hud/heart/container_hardcore_blinking")
		),
		NORMAL(
			Identifier.ofVanilla("hud/heart/full"),
			Identifier.ofVanilla("hud/heart/full_blinking"),
			Identifier.ofVanilla("hud/heart/half"),
			Identifier.ofVanilla("hud/heart/half_blinking"),
			Identifier.ofVanilla("hud/heart/hardcore_full"),
			Identifier.ofVanilla("hud/heart/hardcore_full_blinking"),
			Identifier.ofVanilla("hud/heart/hardcore_half"),
			Identifier.ofVanilla("hud/heart/hardcore_half_blinking")
		),
		POISONED(
			Identifier.ofVanilla("hud/heart/poisoned_full"),
			Identifier.ofVanilla("hud/heart/poisoned_full_blinking"),
			Identifier.ofVanilla("hud/heart/poisoned_half"),
			Identifier.ofVanilla("hud/heart/poisoned_half_blinking"),
			Identifier.ofVanilla("hud/heart/poisoned_hardcore_full"),
			Identifier.ofVanilla("hud/heart/poisoned_hardcore_full_blinking"),
			Identifier.ofVanilla("hud/heart/poisoned_hardcore_half"),
			Identifier.ofVanilla("hud/heart/poisoned_hardcore_half_blinking")
		),
		WITHERED(
			Identifier.ofVanilla("hud/heart/withered_full"),
			Identifier.ofVanilla("hud/heart/withered_full_blinking"),
			Identifier.ofVanilla("hud/heart/withered_half"),
			Identifier.ofVanilla("hud/heart/withered_half_blinking"),
			Identifier.ofVanilla("hud/heart/withered_hardcore_full"),
			Identifier.ofVanilla("hud/heart/withered_hardcore_full_blinking"),
			Identifier.ofVanilla("hud/heart/withered_hardcore_half"),
			Identifier.ofVanilla("hud/heart/withered_hardcore_half_blinking")
		),
		ABSORBING(
			Identifier.ofVanilla("hud/heart/absorbing_full"),
			Identifier.ofVanilla("hud/heart/absorbing_full_blinking"),
			Identifier.ofVanilla("hud/heart/absorbing_half"),
			Identifier.ofVanilla("hud/heart/absorbing_half_blinking"),
			Identifier.ofVanilla("hud/heart/absorbing_hardcore_full"),
			Identifier.ofVanilla("hud/heart/absorbing_hardcore_full_blinking"),
			Identifier.ofVanilla("hud/heart/absorbing_hardcore_half"),
			Identifier.ofVanilla("hud/heart/absorbing_hardcore_half_blinking")
		),
		FROZEN(
			Identifier.ofVanilla("hud/heart/frozen_full"),
			Identifier.ofVanilla("hud/heart/frozen_full_blinking"),
			Identifier.ofVanilla("hud/heart/frozen_half"),
			Identifier.ofVanilla("hud/heart/frozen_half_blinking"),
			Identifier.ofVanilla("hud/heart/frozen_hardcore_full"),
			Identifier.ofVanilla("hud/heart/frozen_hardcore_full_blinking"),
			Identifier.ofVanilla("hud/heart/frozen_hardcore_half"),
			Identifier.ofVanilla("hud/heart/frozen_hardcore_half_blinking")
		);
        private final Identifier fullTexture;
        private final Identifier fullBlinkingTexture;
        private final Identifier halfTexture;
        private final Identifier halfBlinkingTexture;
        private final Identifier hardcoreFullTexture;
        private final Identifier hardcoreFullBlinkingTexture;
        private final Identifier hardcoreHalfTexture;
        private final Identifier hardcoreHalfBlinkingTexture;

        HeartTypeNew(Identifier fullTexture, Identifier fullBlinkingTexture, Identifier halfTexture, Identifier halfBlinkingTexture, Identifier hardcoreFullTexture, Identifier hardcoreFullBlinkingTexture, Identifier hardcoreHalfTexture, Identifier hardcoreHalfBlinkingTexture) {
            this.fullTexture = fullTexture;
            this.fullBlinkingTexture = fullBlinkingTexture;
            this.halfTexture = halfTexture;
            this.halfBlinkingTexture = halfBlinkingTexture;
            this.hardcoreFullTexture = hardcoreFullTexture;
            this.hardcoreFullBlinkingTexture = hardcoreFullBlinkingTexture;
            this.hardcoreHalfTexture = hardcoreHalfTexture;
            this.hardcoreHalfBlinkingTexture = hardcoreHalfBlinkingTexture;
        }

        public Identifier getTexture(boolean hardcore, boolean half, boolean blinking) {
            if (!hardcore) {
                if (half) {
                    return blinking ? this.halfBlinkingTexture : this.halfTexture;
                }
                return blinking ? this.fullBlinkingTexture : this.fullTexture;
            }
            if (half) {
                return blinking ? this.hardcoreHalfBlinkingTexture : this.hardcoreHalfTexture;
            }
            return blinking ? this.hardcoreFullBlinkingTexture : this.hardcoreFullTexture;
        }

        public static HeartTypeNew fromPlayerState(PlayerEntity player) {
	        return player.hasStatusEffect(StatusEffects.POISON) ? POISONED : (player.hasStatusEffect(StatusEffects.WITHER) ? WITHERED : (player.isFrozen() ? FROZEN : NORMAL));
        }
    }
}
