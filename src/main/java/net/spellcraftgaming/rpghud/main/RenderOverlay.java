package net.spellcraftgaming.rpghud.main;

import com.mojang.blaze3d.platform.GlStateManager;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.util.Identifier;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(EnvType.CLIENT)
public class RenderOverlay {

    private ModRPGHud rpgHud;
    private MinecraftClient mc;

    public RenderOverlay() {
        this.rpgHud = ModRPGHud.instance;
        this.mc = MinecraftClient.getInstance();
    }

    /*@SubscribeEvent
    public void onIngameHudDraw(float partialTicks) {
        ElementType type = event.getType();
        switch(type) {
            case AIR:
                if(!shouldRenderVanilla(HudElementType.AIR))
                    event.setCanceled(true);
                break;
            case ARMOR:
                if(!shouldRenderVanilla(HudElementType.ARMOR))
                    event.setCanceled(true);
                break;
            case FOOD:
                if(!shouldRenderVanilla(HudElementType.FOOD))
                    event.setCanceled(true);
                break;
            case HEALTH:
                if(!shouldRenderVanilla(HudElementType.HEALTH))
                    event.setCanceled(true);
                break;
            default:
                break;

        }
    }*/

    private void renderOverlay(float partialTicks) {
        this.drawElement(HudElementType.WIDGET, partialTicks);
        this.drawElement(HudElementType.CLOCK, partialTicks);
        this.drawElement(HudElementType.DETAILS, partialTicks);
        this.drawElement(HudElementType.COMPASS, partialTicks);
        this.drawElement(HudElementType.ENTITY_INSPECT, partialTicks);
        if(!shouldRenderVanilla(HudElementType.HEALTH)) this.drawElement(HudElementType.HEALTH, partialTicks);
        if(!shouldRenderVanilla(HudElementType.ARMOR)) this.drawElement(HudElementType.ARMOR, partialTicks);
        if(!shouldRenderVanilla(HudElementType.FOOD)) this.drawElement(HudElementType.FOOD, partialTicks);
        if(!shouldRenderVanilla(HudElementType.HEALTH_MOUNT)) this.drawElement(HudElementType.HEALTH_MOUNT, partialTicks);
        if(!shouldRenderVanilla(HudElementType.AIR)) this.drawElement(HudElementType.AIR, partialTicks);
        if(!shouldRenderVanilla(HudElementType.JUMP_BAR)) this.drawElement(HudElementType.JUMP_BAR, partialTicks);
        if(!shouldRenderVanilla(HudElementType.EXPERIENCE)) {
            this.drawElement(HudElementType.EXPERIENCE, partialTicks);
            this.drawElement(HudElementType.LEVEL, partialTicks);
        }
        if(!shouldRenderVanilla(HudElementType.HOTBAR)) {
            this.drawElement(HudElementType.HOTBAR, partialTicks);
            
            
        }
    }

    /**
     * Draw the specified HudElement of the HudElementType from the active Hud
     * 
     * @param type         the HudElementType to be rendered
     * @param partialTicks the partialTicks to be used for animations
     */
    private void drawElement(HudElementType type, float partialTicks) {

        if(this.rpgHud.getActiveHud().checkElementConditions(type)) {
            if(!preventElementRenderType(type)) {
                bind(DrawableHelper.GUI_ICONS_LOCATION);
                GlStateManager.enableBlend();
                this.rpgHud.getActiveHud().drawElement(type, this.mc.inGameHud, partialTicks, partialTicks, this.mc.window.getScaledWidth(),
                        this.mc.window.getScaledHeight());
                GlStateManager.disableBlend();
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
    
    private void bind(Identifier res) {
        mc.getTextureManager().bindTexture(res);
    }
    
    public static boolean isVanillaElement(HudElementType type) {
        return ModRPGHud.instance.getActiveHud().isVanillaElement(type);
    }

    public void onHudRender(float tickDelta) {
        renderOverlay(tickDelta);
        
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
