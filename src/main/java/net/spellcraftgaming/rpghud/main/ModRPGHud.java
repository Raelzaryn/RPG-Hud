package net.spellcraftgaming.rpghud.main;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.spellcraftgaming.rpghud.event.ClientEventHandler;
import net.spellcraftgaming.rpghud.gui.GuiSettingsMod;
import net.spellcraftgaming.rpghud.gui.hud.*;
import net.spellcraftgaming.rpghud.settings.Settings;
import org.slf4j.Logger;

import java.util.LinkedHashMap;
import java.util.Map;


@Mod(value = ModRPGHud.MODID, dist = Dist.CLIENT)
public class ModRPGHud {

	public static final String MODID = "rpghud";

	public static ModRPGHud instance;

	public final static boolean[] renderDetailsAgain = { false, false, false };

	public Settings settings;

	/** Map of all registered HUDs */
	public final Map<String, Hud> huds = new LinkedHashMap<>();

	public static final Logger LOGGER = LogUtils.getLogger();

	public ModRPGHud(IEventBus modEventBus, ModContainer container) {
		instance = this;
		if (FMLEnvironment.getDist() == Dist.CLIENT) {
			modEventBus.addListener(this::setup);
			modEventBus.addListener(this::doClientStuff);
			container.registerExtensionPoint(IConfigScreenFactory.class, (mc, parent) -> new GuiSettingsMod(parent, Component.translatable("gui.rpg.settings")));
		} else {
			LOGGER.warn("RPG-Hud is a client-side-only mod and should not be installed server-side, please remove it from your server");
		}
	}

	private void setup(FMLCommonSetupEvent event)
	{
		this.settings = new Settings();
		this.registerHud(new HudVanilla(Minecraft.getInstance(), "vanilla", "Vanilla"));
		this.registerHud(new HudSimple(Minecraft.getInstance(), "simple", "Simplified"));
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
		//new RenderOverlay();
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
	
    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = ModRPGHud.MODID, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiLayersEvent event) {
        	event.registerAboveAll(Identifier.fromNamespaceAndPath(ModRPGHud.MODID, ModRPGHud.MODID), new RenderOverlay());
        }
    }
}
