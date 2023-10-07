package net.spellcraftgaming.rpghud.main;

import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(value=EnvType.CLIENT)
public class RenderOverlay implements HudRenderCallback{

    private ModRPGHud rpgHud;
    private MinecraftClient mc;

    public RenderOverlay() {
        this.rpgHud = ModRPGHud.instance;
        this.mc = MinecraftClient.getInstance();
        HudRenderCallback.EVENT.register(this);
    }

    private void renderOverlay(DrawContext dc, float partialTicks) {
        this.drawElement(HudElementType.WIDGET, dc, partialTicks);
        this.drawElement(HudElementType.CLOCK, dc, partialTicks);
        this.drawElement(HudElementType.DETAILS, dc, partialTicks);
        this.drawElement(HudElementType.COMPASS, dc, partialTicks);
        this.drawElement(HudElementType.ENTITY_INSPECT, dc, partialTicks);
        if(!shouldRenderVanilla(HudElementType.HEALTH)) this.drawElement(HudElementType.HEALTH, dc, partialTicks);
        if(!shouldRenderVanilla(HudElementType.ARMOR)) this.drawElement(HudElementType.ARMOR, dc, partialTicks);
        if(!shouldRenderVanilla(HudElementType.FOOD)) this.drawElement(HudElementType.FOOD, dc, partialTicks);
        if(!shouldRenderVanilla(HudElementType.HEALTH_MOUNT)) this.drawElement(HudElementType.HEALTH_MOUNT, dc, partialTicks);
        if(!shouldRenderVanilla(HudElementType.AIR)) this.drawElement(HudElementType.AIR, dc, partialTicks);
        if(!shouldRenderVanilla(HudElementType.JUMP_BAR)) this.drawElement(HudElementType.JUMP_BAR, dc, partialTicks);
        if(!shouldRenderVanilla(HudElementType.STATUS_EFFECTS)) this.drawElement(HudElementType.STATUS_EFFECTS, dc, partialTicks);
        if(!shouldRenderVanilla(HudElementType.EXPERIENCE)) {
            this.drawElement(HudElementType.EXPERIENCE, dc, partialTicks);
            this.drawElement(HudElementType.LEVEL, dc, partialTicks);
        }
        if(!shouldRenderVanilla(HudElementType.HOTBAR)) {
            this.drawElement(HudElementType.HOTBAR, dc, partialTicks);
            
            
        }
    }

    /**
     * Draw the specified HudElement of the HudElementType from the active Hud
     * 
     * @param type         the HudElementType to be rendered
     * @param partialTicks the partialTicks to be used for animations
     */
    private void drawElement(HudElementType type, DrawContext dc, float partialTicks) {

        if(this.rpgHud.getActiveHud().checkElementConditions(type)) {
            if(!preventElementRenderType(type)) {
               	dc.getMatrices().push();
                RenderSystem.enableBlend();
                this.rpgHud.getActiveHud().drawElement(type, dc, partialTicks, partialTicks, this.mc.getWindow().getScaledWidth(),
                        this.mc.getWindow().getScaledHeight());
                dc.getMatrices().pop();
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
    public void onHudRender(DrawContext dc, float tickDelta) {
        renderOverlay(dc, tickDelta);
        
    }
    
    /*private static HudElementType getEventAlias(ElementType type) {
        switch(type) {
            case HOTBAR:
                return HudElementType.HOTBAR;
            case HEALTH:
                return HudElementType.HEALTH;
            case ARMOR:
                return HudElementType.ARMOR;
            case FOOD:
                return HudElementType.FOOD;
            case HEALTHMOUNT:
                return HudElementType.HEALTH_MOUNT;
            case AIR:
                return HudElementType.AIR;
            case JUMPBAR:
                return HudElementType.JUMP_BAR;
            case EXPERIENCE:
                return HudElementType.EXPERIENCE;
            default:
                return null;
        }
    }*/
}
