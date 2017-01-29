package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementCrosshairVanilla extends HudElement{

	public HudElementCrosshairVanilla() {
		super(HudElementType.CROSSHAIR, -1, -1, 16, 16, false);
	}

	
	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		ScaledResolution res = new ScaledResolution(this.mc);
		
		GameSettings gamesettings = this.mc.gameSettings;

        if (gamesettings.thirdPersonView == 0)
        {
            if (this.mc.playerController.isSpectator() && this.mc.pointedEntity == null)
            {
                RayTraceResult raytraceresult = this.mc.objectMouseOver;

                if (raytraceresult == null || raytraceresult.typeOfHit != RayTraceResult.Type.BLOCK)
                {
                    return;
                }

                BlockPos blockpos = raytraceresult.getBlockPos();

                net.minecraft.block.state.IBlockState state = this.mc.world.getBlockState(blockpos);
                if (!state.getBlock().hasTileEntity(state) || !(this.mc.world.getTileEntity(blockpos) instanceof IInventory))
                {
                    return;
                }
            }

            int l = res.getScaledWidth();
            int i1 = res.getScaledHeight();

            if (gamesettings.showDebugInfo && !gamesettings.hideGUI && !this.mc.player.hasReducedDebug() && !gamesettings.reducedDebugInfo)
            {
                GlStateManager.pushMatrix();
                GlStateManager.translate(l / 2, i1 / 2, zLevel);
                Entity entity = this.mc.getRenderViewEntity();
                GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, -1.0F, 0.0F, 0.0F);
                GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks, 0.0F, 1.0F, 0.0F);
                GlStateManager.scale(-1.0F, -1.0F, -1.0F);
                OpenGlHelper.renderDirections(10);
                GlStateManager.popMatrix();
            }
            else
            {
                GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                GlStateManager.enableAlpha();
                gui.drawTexturedModalRect(l / 2 - 7, i1 / 2 - 7, 0, 0, 16, 16);

                if (this.mc.gameSettings.attackIndicator == 1)
                {
                    float f = this.mc.player.getCooledAttackStrength(0.0F);

                    if (f < 1.0F)
                    {
                        int i = i1 / 2 - 7 + 16;
                        int j = l / 2 - 7;
                        int k = (int)(f * 17.0F);
                        gui.drawTexturedModalRect(j, i, 36, 94, 16, 4);
                        gui.drawTexturedModalRect(j, i, 52, 94, k, 4);
                    }
                }
            }
        }
	}
}
