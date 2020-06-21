package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementCrosshairVanilla extends HudElement {

    public HudElementCrosshairVanilla() {
        super(HudElementType.CROSSHAIR, -1, -1, 16, 16, false);
    }

    @Override
    public void drawElement(Gui gui, float zLevel, float partialTicks) {
        ScaledResolution res = new ScaledResolution(this.mc);

        GameSettings gamesettings = this.mc.gameSettings;
        GlStateManager.enableBlend();
        if(gamesettings.thirdPersonView == 0) {
            if(GameData.spectatorStuff())
                return;

            int l = res.getScaledWidth();
            int i1 = res.getScaledHeight();

            if(gamesettings.showDebugInfo && !gamesettings.hideGUI && !GameData.getPlayer().hasReducedDebug() && !gamesettings.reducedDebugInfo) {

                GlStateManager.pushMatrix();
                GlStateManager.translate((float) (l / 2), (float) (i1 / 2), zLevel);
                Entity entity = this.mc.getRenderViewEntity();
                GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, -1.0F, 0.0F, 0.0F);
                GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks, 0.0F, 1.0F, 0.0F);
                GlStateManager.scale(-1.0F, -1.0F, -1.0F);
                GameData.doRenderDirections();
                GlStateManager.popMatrix();
            } else {
                GameData.tryBlendFuncCrosshair();
                GlStateManager.enableAlpha();
                gui.drawTexturedModalRect(l / 2 - 7, i1 / 2 - 7, 0, 0, 16, 16);
                if(GameData.getAttackIndicatorSetting() == 1) {

                    float f = GameData.getCooledAttackStrength();

                    boolean flag = false;

                    if(this.mc.pointedEntity != null && this.mc.pointedEntity instanceof EntityLivingBase && f >= 1.0F) {
                        flag = GameData.getCooldownPeriod() > 5.0F;
                        flag = flag & ((EntityLivingBase) this.mc.pointedEntity).isEntityAlive();
                    }

                    int i = i1 / 2 - 7 + 16;
                    int j = l / 2 - 7;

                    if(flag) {
                        gui.drawTexturedModalRect(j, i, 68, 94, 16, 16);
                    } else if(f < 1.0F) {

                        int k = (int) (f * 17.0F);
                        gui.drawTexturedModalRect(j, i, 36, 94, 16, 4);
                        gui.drawTexturedModalRect(j, i, 52, 94, k, 4);
                    }
                }
            }
        }
    }
}
