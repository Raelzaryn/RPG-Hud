package net.spellcraftgaming.rpghud.gui.hud;

import static net.spellcraftgaming.rpghud.gui.hud.element.HudElementType.AIR;
import static net.spellcraftgaming.rpghud.gui.hud.element.HudElementType.ARMOR;
import static net.spellcraftgaming.rpghud.gui.hud.element.HudElementType.CLOCK;
import static net.spellcraftgaming.rpghud.gui.hud.element.HudElementType.COMPASS;
import static net.spellcraftgaming.rpghud.gui.hud.element.HudElementType.DETAILS;
import static net.spellcraftgaming.rpghud.gui.hud.element.HudElementType.ENTITY_INSPECT;
import static net.spellcraftgaming.rpghud.gui.hud.element.HudElementType.EXPERIENCE;
import static net.spellcraftgaming.rpghud.gui.hud.element.HudElementType.FOOD;
import static net.spellcraftgaming.rpghud.gui.hud.element.HudElementType.HEALTH;
import static net.spellcraftgaming.rpghud.gui.hud.element.HudElementType.HEALTH_MOUNT;
import static net.spellcraftgaming.rpghud.gui.hud.element.HudElementType.HOTBAR;
import static net.spellcraftgaming.rpghud.gui.hud.element.HudElementType.JUMP_BAR;
import static net.spellcraftgaming.rpghud.gui.hud.element.HudElementType.LEVEL;
import static net.spellcraftgaming.rpghud.gui.hud.element.HudElementType.MISC;
import static net.spellcraftgaming.rpghud.gui.hud.element.HudElementType.STATUS_EFFECTS;
import static net.spellcraftgaming.rpghud.gui.hud.element.HudElementType.WIDGET;

import java.util.HashMap;
import java.util.Map;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

@Environment(value=EnvType.CLIENT)
public abstract class Hud {

	/** Hud key for registering */
	private final String hudKey;

	/** Hud name for display in settings */
	private final String hudName;

	protected Map<HudElementType, HudElement> elements = new HashMap<HudElementType, HudElement>();

	/** Minecraft instance */
	protected MinecraftClient mc;

	public int chatOffset = 0;
	public Hud(MinecraftClient mc, String hudKey, String hudName) {
		this.mc = mc;
		this.hudKey = hudKey;
		this.hudName = hudName;
		this.setElements();
	}

	/**
	 * Function to register all elements to this HUD.<br>
	 * Has to be run in order for this HUD to function properly
	 */
	public void setElements() {
		this.elements.put(HOTBAR, setElementHotbar());
		this.elements.put(HEALTH, setElementHealth());
		this.elements.put(ARMOR, setElementArmor());
		this.elements.put(FOOD, setElementFood());
		this.elements.put(HEALTH_MOUNT, setElementHealthMount());
		this.elements.put(AIR, setElementAir());
		this.elements.put(JUMP_BAR, setElementJumpBar());
		this.elements.put(EXPERIENCE, setElementExperience());
		this.elements.put(LEVEL, setElementLevel());
		this.elements.put(CLOCK, setElementClock());
		this.elements.put(DETAILS, setElementDetails());
		this.elements.put(WIDGET, setElementWidget());
		this.elements.put(COMPASS, setElementCompass());
		this.elements.put(ENTITY_INSPECT, setElementEntityInspect());
	    this.elements.put(STATUS_EFFECTS, setElementStatusEffects());
	    this.elements.put(MISC, setElementMisc());

	}

	/** get the key (String) of this HUD */
	public String getHudKey() {
		return this.hudKey;
	}

	/** get the name of this HUD */
	public String getHudName() {
		return this.hudName;
	}
	/** Function which returns a new element which is the hotbar element */
	protected abstract HudElement setElementHotbar();

	/** Function which returns a new element which is the health element */
	protected abstract HudElement setElementHealth();

	/** Function which returns a new element which is the food element */
	protected abstract HudElement setElementFood();

	/** Function which returns a new element which is the armor element */
	protected abstract HudElement setElementArmor();

	/** Function which returns a new element which is the air element */
	protected abstract HudElement setElementAir();

	/** Function which returns a new element which is the experience element */
	protected abstract HudElement setElementExperience();

	/**
	 * Function which returns a new element which is the experience level
	 * element
	 */
	protected abstract HudElement setElementLevel();

	/** Function which returns a new element which is the jump bar element */
	protected abstract HudElement setElementJumpBar();

	/**
	 * Function which returns a new element which is the mount health element
	 */
	protected abstract HudElement setElementHealthMount();

	/** Function which returns a new element which is the clock element */
	protected abstract HudElement setElementClock();

	/** Function which returns a new element which is the details element */
	protected abstract HudElement setElementDetails();

	/** Function which returns a new element which is the widget element */
	protected abstract HudElement setElementWidget();

	/** Function which returns a new element which is the compass element */
	protected abstract HudElement setElementCompass();

	/** Function which returns a new element which is the item pickup element */
	protected abstract HudElement setElementEntityInspect();

    protected abstract HudElement setElementStatusEffects();
    
    protected abstract HudElement setElementMisc();
	/**
	 * Draws the an element of the HudElementType type on the screen
	 * 
	 * @param type
	 *            The type of the Element
	 * @param gui
	 *            The gui to draw on
	 * @param zLevel
	 *            The zLevel to draw at
	 * @param partialTicks
	 *            The partialTicks for animations
	 */
	public void drawElement(HudElementType type, DrawContext dc, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		this.elements.get(type).draw(dc, zLevel, partialTicks, scaledWidth, scaledHeight);
	}

	/**
	 * Checks whether the element of the specified type should be rendered
	 * 
	 * @param type
	 *            The HudElementType to get checked
	 * @return true if it should be rendered, false if not
	 */
	public boolean checkElementConditions(HudElementType type) {
		return this.elements.get(type).checkConditions();
	}
	
	public boolean isVanillaElement(HudElementType type) {
	    return this.elements.get(type) == null;
	}
}
