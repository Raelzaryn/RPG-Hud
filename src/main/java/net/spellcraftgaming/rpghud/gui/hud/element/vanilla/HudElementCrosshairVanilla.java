package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.GameType;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementCrosshairVanilla extends HudElement {

    public HudElementCrosshairVanilla() {
        super(HudElementType.CROSSHAIR, -1, -1, 16, 16, false);
    }

    @Override
    public void drawElement(Gui gui, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        GameSettings gamesettings = this.mc.gameSettings;
        if(gamesettings.thirdPersonView == 0) {
            if(this.mc.playerController.getCurrentGameType() == GameType.SPECTATOR && this.mc.pointedEntity == null) {
                RayTraceResult raytraceresult = this.mc.objectMouseOver;
                if(raytraceresult == null || raytraceresult.type != RayTraceResult.Type.BLOCK)
                    return;

                BlockPos blockpos = raytraceresult.getBlockPos();
                if(!this.mc.world.getBlockState(blockpos).hasTileEntity() || !(this.mc.world.getTileEntity(blockpos) instanceof IInventory))
                    return;
            }

            if(gamesettings.showDebugInfo && !gamesettings.hideGUI && !this.mc.player.hasReducedDebug() && !gamesettings.reducedDebugInfo) {
                GlStateManager.pushMatrix();
                GlStateManager.translatef(scaledWidth / 2, scaledHeight / 2, zLevel);
                Entity entity = this.mc.getRenderViewEntity();
                GlStateManager.rotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, -1.0F, 0.0F, 0.0F);
                GlStateManager.rotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks, 0.0F, 1.0F, 0.0F);
                GlStateManager.scalef(-1.0F, -1.0F, -1.0F);
                OpenGlHelper.renderDirections(10);
                GlStateManager.popMatrix();
            } else {
                GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR,
                        GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                gui.drawTexturedModalRect(scaledWidth / 2.0F - 7.5F, scaledHeight / 2.0F - 7.5F, 0, 0, 15, 15);
                if(this.mc.gameSettings.attackIndicator == 1) {
                    float f = this.mc.player.getCooledAttackStrength(0.0F);
                    boolean flag = false;
                    if(this.mc.pointedEntity != null && this.mc.pointedEntity instanceof EntityLivingBase && f >= 1.0F) {
                        flag = this.mc.player.getCooldownPeriod() > 5.0F;
                        flag = flag & this.mc.pointedEntity.isAlive();
                    }

                    int i = scaledHeight / 2 - 7 + 16;
                    int j = scaledWidth / 2 - 8;
                    if(flag)
                        gui.drawTexturedModalRect(j, i, 68, 94, 16, 16);
                    else if(f < 1.0F) {
                        int k = (int) (f * 17.0F);
                        gui.drawTexturedModalRect(j, i, 36, 94, 16, 4);
                        gui.drawTexturedModalRect(j, i, 52, 94, k, 4);
                    }
                }
            }

        }
    }
}
