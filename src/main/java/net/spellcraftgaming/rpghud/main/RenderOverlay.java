package net.spellcraftgaming.rpghud.main;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.rpghud.gui.hud.HudHotbarWidget;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@SideOnly(Side.CLIENT)
public class RenderOverlay {

    private ModRPGHud rpgHud;
    private Minecraft mc;

    public RenderOverlay() {
        this.rpgHud = ModRPGHud.instance;
        this.mc = Minecraft.getMinecraft();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onGameOverlayRender(RenderGameOverlayEvent event) {
        ElementType type = event.getType();
        switch(type) {
            case ALL:
                if(event instanceof RenderGameOverlayEvent.Pre) break;
                renderOverlay(event.getPartialTicks());
                break;
            case AIR:
                if(!shouldRenderVanilla(HudElementType.AIR))
                    event.setCanceled(true);
                break;
            case ARMOR:
                if(!shouldRenderVanilla(HudElementType.ARMOR))
                    event.setCanceled(true);
                break;
            case EXPERIENCE:
                if(!shouldRenderVanilla(HudElementType.EXPERIENCE))
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
            case HEALTHMOUNT:
                if(!shouldRenderVanilla(HudElementType.HEALTH_MOUNT))
                    event.setCanceled(true);
                break;
            case HOTBAR:
                if(!shouldRenderVanilla(HudElementType.HOTBAR))
                    event.setCanceled(true);
                break;
            case JUMPBAR:
                if(!shouldRenderVanilla(HudElementType.JUMP_BAR))
                    event.setCanceled(true);
                break;
            case POTION_ICONS:
                if(!shouldRenderVanilla(HudElementType.STATUS_EFFECTS)) {
                    event.setCanceled(true);
                }
                break;
            default:
                break;

        }
    }
    
    @SubscribeEvent
    public void onGameOverlayRender(RenderGameOverlayEvent.Pre event) {
        switch(event.getType()) {
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
            case POTION_ICONS:
                if(preventEventType(HudElementType.STATUS_EFFECTS))
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
        this.drawElement(HudElementType.WIDGET, partialTicks);
        this.drawElement(HudElementType.CLOCK, partialTicks);
        this.drawElement(HudElementType.DETAILS, partialTicks);
        this.drawElement(HudElementType.COMPASS, partialTicks);
        this.drawElement(HudElementType.ENTITY_INSPECT, partialTicks);
        if(!shouldRenderVanilla(HudElementType.HEALTH))
            this.drawElement(HudElementType.HEALTH, partialTicks);
        if(!shouldRenderVanilla(HudElementType.ARMOR))
            this.drawElement(HudElementType.ARMOR, partialTicks);
        if(!shouldRenderVanilla(HudElementType.FOOD))
            this.drawElement(HudElementType.FOOD, partialTicks);
        if(!shouldRenderVanilla(HudElementType.HEALTH_MOUNT))
            this.drawElement(HudElementType.HEALTH_MOUNT, partialTicks);
        if(!shouldRenderVanilla(HudElementType.AIR))
            this.drawElement(HudElementType.AIR, partialTicks);
        if(!shouldRenderVanilla(HudElementType.JUMP_BAR))
            this.drawElement(HudElementType.JUMP_BAR, partialTicks);
        if(!shouldRenderVanilla(HudElementType.EXPERIENCE)) {
            this.drawElement(HudElementType.EXPERIENCE, partialTicks);
            this.drawElement(HudElementType.LEVEL, partialTicks);
        }
        if(!shouldRenderVanilla(HudElementType.HOTBAR))
            this.drawElement(HudElementType.HOTBAR, partialTicks);
        if(!shouldRenderVanilla(HudElementType.STATUS_EFFECTS)) {
            this.drawElement(HudElementType.STATUS_EFFECTS, partialTicks);
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
                ScaledResolution res = new ScaledResolution(mc);
                bind(GameData.icons());
                GlStateManager.pushMatrix();
                GlStateManager.enableBlend();
                this.rpgHud.getActiveHud().drawElement(type, this.mc.ingameGUI, partialTicks, partialTicks, res.getScaledWidth(), res.getScaledHeight());
                GlStateManager.popMatrix();
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

    private boolean shouldRenderVanilla(HudElementType type) {
        return isVanillaElement(type) || forceRenderTypeVanilla(type);
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

    private boolean isVanillaElement(HudElementType type) {
        return ModRPGHud.instance.getActiveHud().isVanillaElement(type);
    }
}
