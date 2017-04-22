package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementBarred;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.main.ModRPGHud;

public class HudElementEntityInspectVanilla extends HudElementBarred {

	protected static final ResourceLocation DAMAGE_INDICATOR = new ResourceLocation("rpghud:textures/damageindicator.png");
	
	@Override
	public boolean checkConditions() {
		return GameData.shouldDrawHUD() && ModRPGHud.instance.settings.enable_entity_inpect;
	}
	
	public HudElementEntityInspectVanilla() {
		super(HudElementType.ENTITY_INSPECT, 0, 0, 0, 0, true);
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		EntityLiving focused = getFocusedEntity(GameData.getPlayer());
		if(focused != null) {
			ScaledResolution res = new ScaledResolution(this.mc);
			int width = res.getScaledWidth() / 2;
			this.mc.getTextureManager().bindTexture(DAMAGE_INDICATOR);
			gui.drawTexturedModalRect(width - 62, 20, 0, 0, 124, 32);
			drawCustomBar(width - 29, 32, 89, 8, (double) focused.getHealth() / (double) focused.getMaxHealth() * 100D, this.settings.color_health, offsetColorPercent(this.settings.color_health, OFFSET_PERCENT));
			String stringHealth = ((double)Math.round(focused.getHealth() * 10)) / 10 + "/" + ((double)Math.round(focused.getMaxHealth() * 10)) / 10;
			GlStateManager.scale(0.5, 0.5, 0.5);
			gui.drawCenteredString(this.mc.fontRendererObj, stringHealth, (width - 29 + 44) *2, 34 *2, -1);
			GlStateManager.scale(2.0, 2.0, 2.0);
			
			int x = (width - 29 + 44- this.mc.fontRendererObj.getStringWidth(focused.getName()) / 2);
			int y = 23;
			this.mc.fontRendererObj.drawString(focused.getName(), x + 1, y, 0);
			this.mc.fontRendererObj.drawString(focused.getName(), x - 1, y, 0);
			this.mc.fontRendererObj.drawString(focused.getName(), x, y + 1, 0);
			this.mc.fontRendererObj.drawString(focused.getName(), x, y - 1, 0);
			this.mc.fontRendererObj.drawString(focused.getName(), x, y, -1);
			
			drawEntityOnScreen(width - 60 + 14, 22 + 25, focused);
		}
	}

	public EntityLiving getFocusedEntity(Entity watcher){
		EntityLiving focusedEntity = null;
		double maxDistance = 64;
		Vec3 vec = new Vec3(watcher.posX, watcher.posY, watcher.posZ);
		Vec3 posVec = watcher.getPositionVector();
		if(watcher instanceof EntityPlayer){
			vec = vec.addVector(0D, watcher.getEyeHeight(), 0D);
			posVec = posVec.addVector(0D, watcher.getEyeHeight(), 0D);
		}
		Vec3 lookVec = watcher.getLookVec();
		Vec3 vec2 = vec.add(GameData.scaleVec(lookVec.normalize(), maxDistance));
		MovingObjectPosition ray = GameData.getWorldOfEntity(watcher).rayTraceBlocks(vec, vec2);
		
		double distance = maxDistance;
		if(ray != null){
			distance = ray.hitVec.distanceTo(posVec);
		}
		Vec3 reachVector = posVec.addVector(lookVec.xCoord * maxDistance, lookVec.yCoord * maxDistance, lookVec.zCoord * maxDistance);
		
		double currentDistance = distance;
		
		List<Entity> entitiesWithinMaxDistance = GameData.getWorldOfEntity(watcher).getEntitiesWithinAABBExcludingEntity(watcher, watcher.getEntityBoundingBox().addCoord(lookVec.xCoord * maxDistance, lookVec.yCoord * maxDistance, lookVec.zCoord * maxDistance).expand(1, 1, 1));
		for(Entity entity : entitiesWithinMaxDistance){
			if(entity instanceof EntityLiving){
				float collisionBorderSize = entity.getCollisionBorderSize();
				AxisAlignedBB hitBox = entity.getEntityBoundingBox().expand(collisionBorderSize, collisionBorderSize, collisionBorderSize);
				MovingObjectPosition intercept = hitBox.calculateIntercept(posVec, reachVector);
				if(hitBox.isVecInside(posVec)){
					if(currentDistance <= 0D){
						currentDistance = 0;
						focusedEntity = (EntityLiving) entity;
					}
				} else if (intercept != null){
					double distanceToEntity = posVec.distanceTo(intercept.hitVec);
					if(distanceToEntity <= currentDistance) {
						currentDistance = distanceToEntity;
						focusedEntity = (EntityLiving) entity;
					}
				}
			}
		}
		return focusedEntity;
	}
	
    public static void drawEntityOnScreen(int posX, int posY, EntityLivingBase ent)
    {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        int scale = (int) (18 / ent.height);
        GlStateManager.translate((float)posX, (float)posY, 50.0F);
        GlStateManager.scale((float)(-scale), (float)scale, (float)scale);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        float f = ent.renderYawOffset;
        float f1 = ent.rotationYaw;
        float f2 = ent.rotationPitch;
        float f3 = ent.prevRotationYawHead;
        float f4 = ent.rotationYawHead;
        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        
        GlStateManager.rotate(-((float)Math.atan((double)(0 / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
        ent.renderYawOffset = (float)Math.atan((double)(100 / 40.0F)) * 20.0F;
        ent.rotationYaw = (float)Math.atan((double)(25 / 40.0F)) * 40.0F;
        ent.rotationPitch = -((float)Math.atan((double)(0 / 40.0F))) * 20.0F;
        ent.rotationYawHead = ent.rotationYaw;
        ent.prevRotationYawHead = ent.rotationYaw;
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        rendermanager.setPlayerViewY(180.0F);
        rendermanager.setRenderShadow(false);
        rendermanager.doRenderEntity(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
        rendermanager.setRenderShadow(true);
        ent.renderYawOffset = f;
        ent.rotationYaw = f1;
        ent.rotationPitch = f2;
        ent.prevRotationYawHead = f3;
        ent.rotationYawHead = f4;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
}
