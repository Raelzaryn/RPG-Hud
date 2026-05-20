package net.spellcraftgaming.rpghud.main;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.resources.Identifier;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;
import org.joml.Matrix3x2f;
import org.joml.Vector3f;

@Environment(value=EnvType.CLIENT)
public class RenderOverlay implements HudElement {

    private ModRPGHud rpgHud;
    private Minecraft mc;
    public static final Identifier RPG_HUD = Identifier.parse("rpghud:rpghud");
    		
    public RenderOverlay() {
        this.rpgHud = ModRPGHud.instance;
        this.mc = Minecraft.getInstance();
        HudElementRegistry.addFirst(RPG_HUD, this);
        HudElementRegistry.removeElement(VanillaHudElements.INFO_BAR);
        HudElementRegistry.removeElement(VanillaHudElements.EXPERIENCE_LEVEL);
        //HudRenderCallback.EVENT.register(this);
    }

    private void renderOverlay(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
        this.drawElement(HudElementType.WIDGET, graphics, deltaTracker);
        this.drawElement(HudElementType.CLOCK, graphics, deltaTracker);
        this.drawElement(HudElementType.DETAILS, graphics, deltaTracker);
        this.drawElement(HudElementType.COMPASS, graphics, deltaTracker);
        this.drawElement(HudElementType.ENTITY_INSPECT, graphics, deltaTracker);
        if(!shouldRenderVanilla(HudElementType.HEALTH)) this.drawElement(HudElementType.HEALTH, graphics, deltaTracker);
        if(!shouldRenderVanilla(HudElementType.ARMOR)) this.drawElement(HudElementType.ARMOR, graphics, deltaTracker);
        if(!shouldRenderVanilla(HudElementType.FOOD)) this.drawElement(HudElementType.FOOD, graphics, deltaTracker);
        if(!shouldRenderVanilla(HudElementType.HEALTH_MOUNT)) this.drawElement(HudElementType.HEALTH_MOUNT, graphics, deltaTracker);
        if(!shouldRenderVanilla(HudElementType.AIR)) this.drawElement(HudElementType.AIR, graphics, deltaTracker);
        if(!shouldRenderVanilla(HudElementType.JUMP_BAR)) this.drawElement(HudElementType.JUMP_BAR, graphics, deltaTracker);
        if(!shouldRenderVanilla(HudElementType.STATUS_EFFECTS)) this.drawElement(HudElementType.STATUS_EFFECTS, graphics, deltaTracker);
        if(!shouldRenderVanilla(HudElementType.EXPERIENCE)) {
            this.drawElement(HudElementType.EXPERIENCE, graphics, deltaTracker);
            this.drawElement(HudElementType.LEVEL, graphics, deltaTracker);
        }
        if(!shouldRenderVanilla(HudElementType.HOTBAR)) {
            this.drawElement(HudElementType.HOTBAR, graphics, deltaTracker);
        }
        this.drawElement(HudElementType.MISC, graphics, deltaTracker);
    }

    /**
     * Draw the specified HudElement of the HudElementType from the active Hud
     *
     * @param type         the HudElementType to be rendered
     * @param deltaTracker the deltaTracker to be used for animations
     */
    private void drawElement(HudElementType type, GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {

        if(this.rpgHud.getActiveHud().checkElementConditions(type)) {
            if(!preventElementRenderType(type)) {
                graphics.pose().pushMatrix();
                graphics.guiRenderState.nextStratum();
                this.rpgHud.getActiveHud().drawElement(type, graphics, deltaTracker, this.mc.getWindow().getGuiScaledWidth(),
                        this.mc.getWindow().getGuiScaledHeight());
                graphics.pose().popMatrix();
            }

        }
    }

    /**
     * Checks if the HudElementType has a setting to prevent it's rendering and if
     * it is activated
     */
    private boolean preventElementRenderType(HudElementType type) {
        String id = Settings.prevent_element_render + "_" + type.name().toLowerCase();
        if(this.rpgHud.settings.doesSettingExist(id)) {
            return this.rpgHud.settings.getBoolValue(id);
        }
        return false;
    }

    public static boolean shouldRenderVanilla(HudElementType type) {
        return isVanillaElement(type) || forceRenderTypeVanilla(type);
    }

    /**
     * Checks if the HudElementType has a setting to force the vanilla hud element
     * to be rendered and if it is activated
     */
    public static boolean forceRenderTypeVanilla(HudElementType type) {
        ModRPGHud rpgHud = ModRPGHud.instance;
        String id = Settings.render_vanilla + "_" + type.name().toLowerCase();
        if(rpgHud.settings.doesSettingExist(id)) {
            return rpgHud.settings.getBoolValue(id);
        }
        return false;
    }
    /**
     * Checks if the HudElementType has a setting to prevent the forge event and if
     * it is activated
     */
    public static boolean preventEventType(HudElementType type) {
        ModRPGHud rpgHud = ModRPGHud.instance;
        String id = Settings.prevent_event + "_" + type.name().toLowerCase();
        if(rpgHud.settings.doesSettingExist(id))
            return rpgHud.settings.getBoolValue(id);
        return false;
    }

    public static boolean isVanillaElement(HudElementType type) {
        return ModRPGHud.instance.getActiveHud().isVanillaElement(type);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
        renderOverlay(graphics, deltaTracker);
    }
}
