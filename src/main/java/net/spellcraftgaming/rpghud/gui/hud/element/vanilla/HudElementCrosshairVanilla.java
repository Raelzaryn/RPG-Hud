package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.settings.AttackIndicatorStatus;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.gui.override.GuiIngameRPGHud;

public class HudElementCrosshairVanilla extends HudElement {

	public HudElementCrosshairVanilla() {
		super(HudElementType.CROSSHAIR, -1, -1, 16, 16, false);
	}

	@Override
	public void drawElement(AbstractGui gui, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
	      GameSettings gamesettings = this.mc.gameSettings;
	      if (gamesettings.thirdPersonView == 0) {
	         if (this.mc.playerController.getCurrentGameType() != GameType.SPECTATOR || this.func_212913_a(this.mc.objectMouseOver)) {
	            if (gamesettings.showDebugInfo && !gamesettings.hideGUI && !this.mc.player.hasReducedDebug() && !gamesettings.reducedDebugInfo) {
	               GlStateManager.pushMatrix();
	               GuiIngameRPGHud theGui = (GuiIngameRPGHud) gui;
	               GlStateManager.translatef((float)(scaledWidth / 2), (float)(scaledHeight / 2), (float)theGui.getBlitOffset());
	               ActiveRenderInfo activerenderinfo = this.mc.gameRenderer.getActiveRenderInfo();
	               GlStateManager.rotatef(activerenderinfo.getPitch(), -1.0F, 0.0F, 0.0F);
	               GlStateManager.rotatef(activerenderinfo.getYaw(), 0.0F, 1.0F, 0.0F);
	               GlStateManager.scalef(-1.0F, -1.0F, -1.0F);
	               GLX.renderCrosshair(10);
	               GlStateManager.popMatrix();
	            } else {
	               GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
	               gui.blit((scaledWidth - 15) / 2, (scaledHeight - 15) / 2, 0, 0, 15, 15);
	               if (this.mc.gameSettings.attackIndicator == AttackIndicatorStatus.CROSSHAIR) {
	                  float f = this.mc.player.getCooledAttackStrength(0.0F);
	                  boolean flag = false;
	                  if (this.mc.pointedEntity != null && this.mc.pointedEntity instanceof LivingEntity && f >= 1.0F) {
	                     flag = this.mc.player.getCooldownPeriod() > 5.0F;
	                     flag = flag & this.mc.pointedEntity.isAlive();
	                  }

	                  int j = scaledHeight / 2 - 7 + 16;
	                  int k = scaledWidth / 2 - 8;
	                  if (flag) {
	                     gui.blit(k, j, 68, 94, 16, 16);
	                  } else if (f < 1.0F) {
	                     int l = (int)(f * 17.0F);
	                     gui.blit(k, j, 36, 94, 16, 4);
	                     gui.blit(k, j, 52, 94, l, 4);
	                  }
	               }
	            }

	         }
	      }
	}
	
	   private boolean func_212913_a(RayTraceResult p_212913_1_) {
		      if (p_212913_1_ == null) {
		         return false;
		      } else if (p_212913_1_.getType() == RayTraceResult.Type.ENTITY) {
		         return ((EntityRayTraceResult)p_212913_1_).getEntity() instanceof INamedContainerProvider;
		      } else if (p_212913_1_.getType() == RayTraceResult.Type.BLOCK) {
		         BlockPos blockpos = ((BlockRayTraceResult)p_212913_1_).getPos();
		         World world = this.mc.world;
		         return world.getBlockState(blockpos).getContainer(world, blockpos) != null;
		      } else {
		         return false;
		      }
		   }
}
