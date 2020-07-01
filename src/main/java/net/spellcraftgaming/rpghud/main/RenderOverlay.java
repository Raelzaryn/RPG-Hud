package net.spellcraftgaming.rpghud.main;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.spellcraftgaming.rpghud.gui.hud.HudHotbarWidget;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@OnlyIn(Dist.CLIENT)
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
            default:
                break;

        }
    }
    
    @SubscribeEvent
    public void onGameOverlayRender(RenderGameOverlayEvent.Pre event) {
        switch(event.getType()) {
            case ALL:
                renderOverlay(event.getMatrixStack(), event.getPartialTicks());
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

    private void renderOverlay(MatrixStack ms, float partialTicks) {
        this.drawElement(HudElementType.WIDGET, ms, partialTicks);
        this.drawElement(HudElementType.CLOCK, ms, partialTicks);
        this.drawElement(HudElementType.DETAILS, ms, partialTicks);
        this.drawElement(HudElementType.COMPASS, ms, partialTicks);
        this.drawElement(HudElementType.ENTITY_INSPECT, ms, partialTicks);
        if(!shouldRenderVanilla(HudElementType.HEALTH)) this.drawElement(HudElementType.HEALTH, ms, partialTicks);
        if(!shouldRenderVanilla(HudElementType.ARMOR)) this.drawElement(HudElementType.ARMOR, ms, partialTicks);
        if(!shouldRenderVanilla(HudElementType.FOOD)) this.drawElement(HudElementType.FOOD, ms, partialTicks);
        if(!shouldRenderVanilla(HudElementType.HEALTH_MOUNT)) this.drawElement(HudElementType.HEALTH_MOUNT, ms, partialTicks);
        if(!shouldRenderVanilla(HudElementType.AIR)) this.drawElement(HudElementType.AIR, ms, partialTicks);
        if(!shouldRenderVanilla(HudElementType.JUMP_BAR)) this.drawElement(HudElementType.JUMP_BAR, ms, partialTicks);
        if(!shouldRenderVanilla(HudElementType.EXPERIENCE)) {
            this.drawElement(HudElementType.EXPERIENCE, ms, partialTicks);
            this.drawElement(HudElementType.LEVEL, ms, partialTicks);
        }
        if(!shouldRenderVanilla(HudElementType.HOTBAR)) {
            this.drawElement(HudElementType.HOTBAR, ms, partialTicks);
            
            
        }
    }

    /**
     * Draw the specified HudElement of the HudElementType from the active Hud
     * 
     * @param type         the HudElementType to be rendered
     * @param partialTicks the partialTicks to be used for animations
     */
    private void drawElement(HudElementType type, MatrixStack ms, float partialTicks) {

        if(this.rpgHud.getActiveHud().checkElementConditions(type)) {
            if(!preventElementRenderType(type)) {
                bind(AbstractGui.field_230665_h_);
                RenderSystem.enableBlend();
                this.rpgHud.getActiveHud().drawElement(type, this.mc.ingameGUI, ms, partialTicks, partialTicks, this.mc.getMainWindow().getScaledWidth(),
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
