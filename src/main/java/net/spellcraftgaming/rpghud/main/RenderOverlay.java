package net.spellcraftgaming.rpghud.main;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.spellcraftgaming.rpghud.gui.hud.HudHotbarWidget;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class RenderOverlay {

    private ModRPGHud rpgHud;
    private Minecraft mc;

    public RenderOverlay() {
        this.rpgHud = ModRPGHud.instance;
        this.mc = Minecraft.getInstance();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onGameOverlayRender(RenderGameOverlayEvent event) {
        switch(event.getType()) {
            case AIR:
                if(!forceRenderTypeVanilla(HudElementType.AIR))
                    event.setCanceled(true);
                break;
            case ARMOR:
                if(!forceRenderTypeVanilla(HudElementType.ARMOR))
                    event.setCanceled(true);
                break;
            case EXPERIENCE:
                if(!forceRenderTypeVanilla(HudElementType.EXPERIENCE))
                    event.setCanceled(true);
                break;
            case FOOD:
                if(!forceRenderTypeVanilla(HudElementType.FOOD))
                    event.setCanceled(true);
                break;
            case HEALTH:
                if(!forceRenderTypeVanilla(HudElementType.HEALTH))
                    event.setCanceled(true);
                break;
            case HEALTHMOUNT:
                if(!forceRenderTypeVanilla(HudElementType.HEALTH_MOUNT))
                    event.setCanceled(true);
                break;
            case HOTBAR:
                if(!forceRenderTypeVanilla(HudElementType.HOTBAR))
                    event.setCanceled(true);
                break;
            case JUMPBAR:
                if(!forceRenderTypeVanilla(HudElementType.JUMP_BAR))
                    event.setCanceled(true);
                break;
            default:
                break;

        }
    }
    
    @SubscribeEvent
    public void onGameOverlayRender(RenderGameOverlayEvent.Pre event) {
        switch(event.getType()) {
            case ALL:
                renderOverlay(event.getPartialTicks());
                break;
            case AIR:
                if(preventEventType(HudElementType.AIR))
                    event.setCanceled(true);
                break;
            case ARMOR:
                if(preventEventType(HudElementType.ARMOR))
                    event.setCanceled(true);
                break;
            case EXPERIENCE:
                if(preventEventType(HudElementType.EXPERIENCE))
                    event.setCanceled(true);
                break;
            case FOOD:
                if(preventEventType(HudElementType.FOOD))
                    event.setCanceled(true);
                break;
            case HEALTH:
                if(preventEventType(HudElementType.HEALTH))
                    event.setCanceled(true);
                break;
            case HEALTHMOUNT:
                if(preventEventType(HudElementType.HEALTH_MOUNT))
                    event.setCanceled(true);
                break;
            case HOTBAR:
                if(preventEventType(HudElementType.HOTBAR))
                    event.setCanceled(true);
                break;
            case JUMPBAR:
                if(preventEventType(HudElementType.JUMP_BAR))
                    event.setCanceled(true);
                break;
            default:
                break;

        }
    }

    @SubscribeEvent
    public void onChatRender(RenderGameOverlayEvent.Chat event) {
        if(ModRPGHud.instance.getActiveHud() instanceof HudHotbarWidget) {
            event.setPosY(event.getPosY() - 22);
        }
    }

    private void renderOverlay(float partialTicks) {
        if(preventElementRenderType(HudElementType.WIDGET)) this.drawElement(HudElementType.WIDGET, partialTicks);
        if(preventElementRenderType(HudElementType.CLOCK)) this.drawElement(HudElementType.CLOCK, partialTicks);
        if(preventElementRenderType(HudElementType.DETAILS)) this.drawElement(HudElementType.DETAILS, partialTicks);
        if(preventElementRenderType(HudElementType.COMPASS)) this.drawElement(HudElementType.COMPASS, partialTicks);
        if(preventElementRenderType(HudElementType.ENTITY_INSPECT)) this.drawElement(HudElementType.ENTITY_INSPECT, partialTicks);
        if(preventElementRenderType(HudElementType.HEALTH)) this.drawElement(HudElementType.HEALTH, partialTicks);
        if(preventElementRenderType(HudElementType.ARMOR)) this.drawElement(HudElementType.ARMOR, partialTicks);
        if(preventElementRenderType(HudElementType.FOOD)) this.drawElement(HudElementType.FOOD, partialTicks);
        if(preventElementRenderType(HudElementType.HEALTH_MOUNT)) this.drawElement(HudElementType.HEALTH_MOUNT, partialTicks);
        if(preventElementRenderType(HudElementType.AIR)) this.drawElement(HudElementType.AIR, partialTicks);
        if(preventElementRenderType(HudElementType.JUMP_BAR)) this.drawElement(HudElementType.JUMP_BAR, partialTicks);
        if(preventElementRenderType(HudElementType.EXPERIENCE)) this.drawElement(HudElementType.EXPERIENCE, partialTicks);
        if(preventElementRenderType(HudElementType.HOTBAR)) this.drawElement(HudElementType.HOTBAR, partialTicks);
        if(preventElementRenderType(HudElementType.LEVEL))  this.drawElement(HudElementType.LEVEL, partialTicks);
    }

    /**
     * Draw the specified HudElement of the HudElementType from the active Hud
     * 
     * @param type         the HudElementType to be rendered
     * @param partialTicks the partialTicks to be used for animations
     */
    private void drawElement(HudElementType type, float partialTicks) {

        if(this.rpgHud.getActiveHud().checkElementConditions(type)) {
            if(!forceRenderTypeVanilla(type) && !preventElementRenderType(type)) {
                bind(AbstractGui.GUI_ICONS_LOCATION);
                RenderSystem.enableBlend();
                this.rpgHud.getActiveHud().drawElement(type, this.mc.ingameGUI, partialTicks, partialTicks, this.mc.getMainWindow().getScaledWidth(),
                        this.mc.getMainWindow().getScaledHeight());
                RenderSystem.disableBlend();
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

    /**
     * Checks if the HudElementType has a setting to force the vanilla hud element
     * to be rendered and if it is activated
     */
    private boolean forceRenderTypeVanilla(HudElementType type) {
        String id = Settings.render_vanilla + "_" + type.name().toLowerCase();
        if(this.rpgHud.settings.doesSettingExist(id)) {
            return this.rpgHud.settings.getBoolValue(id);
        }
        return false;
    }

    /**
     * Checks if the HudElementType has a setting to prevent the forge event and if
     * it is activated
     */
    private boolean preventEventType(HudElementType type) {
        String id = Settings.prevent_event + "_" + type.name().toLowerCase();
        if(this.rpgHud.settings.doesSettingExist(id))
            return this.rpgHud.settings.getBoolValue(id);
        return false;
    }
    
    private void bind(ResourceLocation res) {
        mc.getTextureManager().bindTexture(res);
    }
}
