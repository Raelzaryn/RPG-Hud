package net.spellcraftgaming.rpghud.main;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.spellcraftgaming.rpghud.gui.hud.HudHotbarWidget;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class RenderOverlay implements IGuiOverlay{

    private ModRPGHud rpgHud;
    private Minecraft mc;

    public RenderOverlay() {
        this.rpgHud = ModRPGHud.instance;
        this.mc = Minecraft.getInstance();
        
        MinecraftForge.EVENT_BUS.register(this);
    }

	@Override
	public void render(ForgeGui forgeGui, GuiGraphics gg, float partialTicks, int screenWidth, int screenHeight) {
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
		
	}
	
    @SubscribeEvent
    public void registerOverlay(RegisterGuiOverlaysEvent event) {
    	event.registerAboveAll("rpg_hud", this);
    	
    }

    /**
     * Draw the specified HudElement of the HudElementType from the active Hud
     *
     * @param type         the HudElementType to be rendered
     * @param partialTicks the partialTicks to be used for animations
     */
    private void drawElement(HudElementType type, GuiGraphics gg, float partialTicks) {

        if (this.rpgHud.getActiveHud().checkElementConditions(type)) {
            if (!preventElementRenderType(type)) {
                gg.pose().pushPose();
                RenderSystem.enableBlend();
                this.rpgHud.getActiveHud().drawElement(type, gg, 0, partialTicks, this.mc.getWindow().getGuiScaledWidth(),
                        this.mc.getWindow().getGuiScaledHeight());
                gg.pose().popPose();
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

    /*TODO:
    @SubscribeEvent
    public void onChatRender(RenderGameOverlayEvent.Chat event) {
        if (ModRPGHud.instance.getActiveHud() instanceof HudHotbarWidget) {
            event.setPosY(event.getPosY() - 22);
        }
    }*/

    
    @SubscribeEvent
    public void onGameOverlayRenderPre(RenderGuiOverlayEvent.Pre event) {
        ResourceLocation overlay = event.getOverlay().id();
        if (VanillaGuiOverlay.AIR_LEVEL.id() == overlay) {
            if (preventEventType(HudElementType.AIR))
                event.setCanceled(true);
        } else if (VanillaGuiOverlay.ARMOR_LEVEL.id() == overlay) {
            if (preventEventType(HudElementType.ARMOR))
                event.setCanceled(true);
        } else if (VanillaGuiOverlay.EXPERIENCE_BAR.id() == overlay) {
            if (preventEventType(HudElementType.EXPERIENCE))
                event.setCanceled(true);
        } else if (VanillaGuiOverlay.FOOD_LEVEL.id() == overlay) {
            if (preventEventType(HudElementType.FOOD))
                event.setCanceled(true);
        } else if (overlay == VanillaGuiOverlay.PLAYER_HEALTH.id()) {
        	if (preventEventType(HudElementType.HEALTH)) {
                event.setCanceled(true);}
        } else if (VanillaGuiOverlay.MOUNT_HEALTH.id() == overlay) {
            if (preventEventType(HudElementType.HEALTH_MOUNT))
                event.setCanceled(true);
        } else if (VanillaGuiOverlay.HOTBAR.id() == overlay) {
            if (preventEventType(HudElementType.HOTBAR))
                event.setCanceled(true);
        } else if (VanillaGuiOverlay.JUMP_BAR.id() == overlay) {
            if (preventEventType(HudElementType.JUMP_BAR))
                event.setCanceled(true);
        } else if (VanillaGuiOverlay.POTION_ICONS.id() == overlay) {
            if (preventEventType(HudElementType.STATUS_EFFECTS))
                event.setCanceled(true);
         }else if (VanillaGuiOverlay.CHAT_PANEL.id() == overlay) {
        	 if (ModRPGHud.instance.getActiveHud() instanceof HudHotbarWidget) {
        		 event.getGuiGraphics().pose().translate(0, -22, 0);
             }
        }
    }
    
    @SubscribeEvent
    public void onGameOverlayRenderPost(RenderGuiOverlayEvent.Post event) {
        ResourceLocation overlay = event.getOverlay().id();
        if (VanillaGuiOverlay.CHAT_PANEL.id() == overlay) {
        	 if (ModRPGHud.instance.getActiveHud() instanceof HudHotbarWidget) {
        		 event.getGuiGraphics().pose().translate(0, 22, 0);
             }
        }
    }

    /*
    @Override
    public void onHudRender(PoseStack matrixStack, float tickDelta) {
        renderOverlay(matrixStack, tickDelta);

    }*/

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
