package net.spellcraftgaming.rpghud.main;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.gui.GuiLayer;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.common.NeoForge;
import net.spellcraftgaming.rpghud.gui.hud.HudHotbarWidget;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class RenderOverlay implements GuiLayer {

    private final ModRPGHud rpgHud;
    private final Minecraft mc;

    public RenderOverlay() {
        this.rpgHud = ModRPGHud.instance;
        this.mc = Minecraft.getInstance();

        NeoForge.EVENT_BUS.register(this);
    }

	@Override
	public void render(GuiGraphics gg, DeltaTracker partialTicks) {
        this.drawElement(HudElementType.WIDGET, gg, partialTicks);
        this.drawElement(HudElementType.CLOCK, gg, partialTicks);
        this.drawElement(HudElementType.DETAILS, gg, partialTicks);
        this.drawElement(HudElementType.COMPASS, gg, partialTicks);
        this.drawElement(HudElementType.ENTITY_INSPECT, gg, partialTicks);
        if (!shouldRenderVanilla(HudElementType.HEALTH)) this.drawElement(HudElementType.HEALTH, gg, partialTicks);
        if (!shouldRenderVanilla(HudElementType.ARMOR)) this.drawElement(HudElementType.ARMOR, gg, partialTicks);
        if (!shouldRenderVanilla(HudElementType.FOOD)) this.drawElement(HudElementType.FOOD, gg, partialTicks);
        if (!shouldRenderVanilla(HudElementType.HEALTH_MOUNT))
            this.drawElement(HudElementType.HEALTH_MOUNT, gg, partialTicks);
        if (!shouldRenderVanilla(HudElementType.AIR)) this.drawElement(HudElementType.AIR, gg, partialTicks);
        if (!shouldRenderVanilla(HudElementType.JUMP_BAR)) this.drawElement(HudElementType.JUMP_BAR, gg, partialTicks);
        if (!shouldRenderVanilla(HudElementType.STATUS_EFFECTS))
            this.drawElement(HudElementType.STATUS_EFFECTS, gg, partialTicks);
        if (!shouldRenderVanilla(HudElementType.EXPERIENCE)) {
            this.drawElement(HudElementType.EXPERIENCE, gg, partialTicks);
            this.drawElement(HudElementType.LEVEL, gg, partialTicks);
        }
        if (!shouldRenderVanilla(HudElementType.HOTBAR)) {
            this.drawElement(HudElementType.HOTBAR, gg, partialTicks);
        }
        this.drawElement(HudElementType.MISC, gg, partialTicks);
	}

    /**
     * Draw the specified HudElement of the HudElementType from the active Hud
     *
     * @param type         the HudElementType to be rendered
     * @param partialTicks the partialTicks to be used for animations
     */
    private void drawElement(HudElementType type, GuiGraphics gg, DeltaTracker partialTicks) {

        if (this.rpgHud.getActiveHud().checkElementConditions(type)) {
            if (!preventElementRenderType(type)) {
                gg.pose().pushMatrix();
                this.rpgHud.getActiveHud().drawElement(type, gg, 0, partialTicks, this.mc.getWindow().getGuiScaledWidth(),
                        this.mc.getWindow().getGuiScaledHeight());
                gg.pose().popMatrix();
            }

        }
    }

    /**
     * Checks if the HudElementType has a setting to prevent it's rendering and if
     * it is activated
     */
    private boolean preventElementRenderType(HudElementType type) {
        String id = Settings.prevent_element_render + "_" + type.name().toLowerCase();
        if (this.rpgHud.settings.doesSettingExist(id)) {
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
        if (rpgHud.settings.doesSettingExist(id)) {
            return rpgHud.settings.getBoolValue(id);
        }
        return false;
    }

    /**
     * Checks if the HudElementType has a setting to prevent the forge event and if
     * it is activated
     */
    public static boolean preventEventType(HudElementType type) {
        if(!shouldRenderVanilla(type))return true;

        ModRPGHud rpgHud = ModRPGHud.instance;
        String id = Settings.prevent_event + "_" + type.name().toLowerCase();
        if (rpgHud.settings.doesSettingExist(id))
            return rpgHud.settings.getBoolValue(id);
        return false;
    }

    public static boolean isVanillaElement(HudElementType type) {
        return ModRPGHud.instance.getActiveHud().isVanillaElement(type);
    }

    @SubscribeEvent
    public void onGameOverlayRenderPre(RenderGuiLayerEvent.Pre event) {
        ResourceLocation overlay = event.getName();
        if (VanillaGuiLayers.AIR_LEVEL == overlay) {
            if (preventEventType(HudElementType.AIR))
                event.setCanceled(true);
        } else if (VanillaGuiLayers.ARMOR_LEVEL == overlay) {
            if(preventEventType(HudElementType.ARMOR))
                event.setCanceled(true);
        } else if (VanillaGuiLayers.CONTEXTUAL_INFO_BAR_BACKGROUND == overlay) {
            if (preventEventType(HudElementType.EXPERIENCE))
                event.setCanceled(true);
            if (preventEventType(HudElementType.JUMP_BAR))
                event.setCanceled(true);
        } else if (VanillaGuiLayers.FOOD_LEVEL == overlay) {
            if (preventEventType(HudElementType.FOOD))
                event.setCanceled(true);
        } else if (overlay == VanillaGuiLayers.PLAYER_HEALTH) {
        	if (preventEventType(HudElementType.HEALTH)) {
                event.setCanceled(true);}
        } else if (VanillaGuiLayers.VEHICLE_HEALTH == overlay) {
            if (preventEventType(HudElementType.HEALTH_MOUNT))
                event.setCanceled(true);
        } else if (VanillaGuiLayers.HOTBAR == overlay) {
            if (preventEventType(HudElementType.HOTBAR))
                event.setCanceled(true);
        } else if (VanillaGuiLayers.CONTEXTUAL_INFO_BAR == overlay) {
            if (preventEventType(HudElementType.EXPERIENCE))
                event.setCanceled(true);
            if (preventEventType(HudElementType.JUMP_BAR))
                event.setCanceled(true);
        } else if (VanillaGuiLayers.EFFECTS == overlay) {
            if(preventEventType(HudElementType.STATUS_EFFECTS))
                event.setCanceled(true);
        } else if (VanillaGuiLayers.CHAT == overlay) {
        	 if (ModRPGHud.instance.getActiveHud() instanceof HudHotbarWidget) {
        		 event.getGuiGraphics().pose().translate(0, -22);
             }
        }
    }
    
    @SubscribeEvent
    public void onGameOverlayRenderPost(RenderGuiLayerEvent.Post event) {
        ResourceLocation overlay = event.getName();
        if (VanillaGuiLayers.CHAT== overlay) {
        	 if (ModRPGHud.instance.getActiveHud() instanceof HudHotbarWidget) {
        		 event.getGuiGraphics().pose().translate(0, 22);
             }
        }
    }

}
