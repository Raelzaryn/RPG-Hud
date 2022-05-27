package net.spellcraftgaming.rpghud.main;

import com.mojang.blaze3d.systems.RenderSystem;


import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.gui.IIngameOverlay;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.spellcraftgaming.rpghud.gui.hud.HudHotbarWidget;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

import static net.minecraft.client.gui.GuiComponent.GUI_ICONS_LOCATION;
import static net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType.ALL;
import static net.minecraftforge.client.gui.ForgeIngameGui.*;

@OnlyIn(Dist.CLIENT)
public class RenderOverlay {

    private ModRPGHud rpgHud;
    private Minecraft mc;

    public RenderOverlay() {
        this.rpgHud = ModRPGHud.instance;
        this.mc = Minecraft.getInstance();
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void renderOverlay(PoseStack ms, float partialTicks) {
        this.drawElement(HudElementType.WIDGET, ms, partialTicks);
        this.drawElement(HudElementType.CLOCK, ms, partialTicks);
        this.drawElement(HudElementType.DETAILS, ms, partialTicks);
        this.drawElement(HudElementType.COMPASS, ms, partialTicks);
        this.drawElement(HudElementType.ENTITY_INSPECT, ms, partialTicks);
        if (!shouldRenderVanilla(HudElementType.HEALTH)) this.drawElement(HudElementType.HEALTH, ms, partialTicks);
        if (!shouldRenderVanilla(HudElementType.ARMOR)) this.drawElement(HudElementType.ARMOR, ms, partialTicks);
        if (!shouldRenderVanilla(HudElementType.FOOD)) this.drawElement(HudElementType.FOOD, ms, partialTicks);
        if (!shouldRenderVanilla(HudElementType.HEALTH_MOUNT))
            this.drawElement(HudElementType.HEALTH_MOUNT, ms, partialTicks);
        if (!shouldRenderVanilla(HudElementType.AIR)) this.drawElement(HudElementType.AIR, ms, partialTicks);
        if (!shouldRenderVanilla(HudElementType.JUMP_BAR)) this.drawElement(HudElementType.JUMP_BAR, ms, partialTicks);
        if (!shouldRenderVanilla(HudElementType.STATUS_EFFECTS))
            this.drawElement(HudElementType.STATUS_EFFECTS, ms, partialTicks);
        if (!shouldRenderVanilla(HudElementType.EXPERIENCE)) {
            this.drawElement(HudElementType.EXPERIENCE, ms, partialTicks);
            this.drawElement(HudElementType.LEVEL, ms, partialTicks);
        }
        if (!shouldRenderVanilla(HudElementType.HOTBAR)) {
            this.drawElement(HudElementType.HOTBAR, ms, partialTicks);


        }
    }

    /**
     * Draw the specified HudElement of the HudElementType from the active Hud
     *
     * @param type         the HudElementType to be rendered
     * @param partialTicks the partialTicks to be used for animations
     */
    private void drawElement(HudElementType type, PoseStack ms, float partialTicks) {

        if (this.rpgHud.getActiveHud().checkElementConditions(type)) {
            if (!preventElementRenderType(type)) {
                bind(GUI_ICONS_LOCATION);
                ms.pushPose();
                RenderSystem.enableBlend();
                this.rpgHud.getActiveHud().drawElement(type, this.mc.gui, ms, partialTicks, partialTicks, this.mc.getWindow().getGuiScaledWidth(),
                        this.mc.getWindow().getGuiScaledHeight());
                ms.popPose();
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

    private void bind(ResourceLocation res) {
        mc.getTextureManager().bindForSetup(res);
    }

    public static boolean isVanillaElement(HudElementType type) {
        return ModRPGHud.instance.getActiveHud().isVanillaElement(type);
    }

    @SubscribeEvent
    public void onChatRender(RenderGameOverlayEvent.Chat event) {
        if (ModRPGHud.instance.getActiveHud() instanceof HudHotbarWidget) {
            event.setPosY(event.getPosY() - 22);
        }
    }

    @SubscribeEvent
    public void onGameOverlayRender(RenderGameOverlayEvent.Pre event) {
        if (event.getType() == ALL) {
            renderOverlay(event.getMatrixStack(), event.getPartialTicks());
        }
    }

    @SubscribeEvent
    public void onGameOverlayRender(RenderGameOverlayEvent.PreLayer event) {
        if (event.getType() == ALL) {
            renderOverlay(event.getMatrixStack(), event.getPartialTicks());
            return;
        }
        IIngameOverlay overlay = event.getOverlay();
        if (AIR_LEVEL_ELEMENT.equals(overlay)) {
            if (preventEventType(HudElementType.AIR))
                event.setCanceled(true);
        } else if (ARMOR_LEVEL_ELEMENT.equals(overlay)) {
            if (preventEventType(HudElementType.ARMOR))
                event.setCanceled(true);
        } else if (EXPERIENCE_BAR_ELEMENT.equals(overlay)) {
            if (preventEventType(HudElementType.EXPERIENCE))
                event.setCanceled(true);
        } else if (FOOD_LEVEL_ELEMENT.equals(overlay)) {
            if (preventEventType(HudElementType.FOOD))
                event.setCanceled(true);
        } else if (PLAYER_HEALTH_ELEMENT.equals(overlay)) {
            if (preventEventType(HudElementType.HEALTH))
                event.setCanceled(true);
        } else if (MOUNT_HEALTH_ELEMENT.equals(overlay)) {
            if (preventEventType(HudElementType.HEALTH_MOUNT))
                event.setCanceled(true);
        } else if (HOTBAR_ELEMENT.equals(overlay)) {
            if (preventEventType(HudElementType.HOTBAR))
                event.setCanceled(true);
        } else if (JUMP_BAR_ELEMENT.equals(overlay)) {
            if (preventEventType(HudElementType.JUMP_BAR))
                event.setCanceled(true);
        } else if (POTION_ICONS_ELEMENT.equals(overlay)) {
            if (preventEventType(HudElementType.STATUS_EFFECTS))
                event.setCanceled(true);
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
