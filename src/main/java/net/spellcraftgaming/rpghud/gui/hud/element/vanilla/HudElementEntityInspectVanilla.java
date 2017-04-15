package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import java.util.List;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementBarred;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementEntityInspectVanilla extends HudElementBarred {

	public HudElementEntityInspectVanilla() {
		super(HudElementType.ENTITY_INSPECT, 0, 0, 0, 0, true);
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		Entity focused = getFocusedEntity(GameData.getPlayer());
		if(focused != null) {
			if(focused instanceof EntityLiving) {
				EntityLiving l = (EntityLiving) focused;
				System.out.println(l.getName() + " | " + l.getHealth() + "/" + l.getMaxHealth());
			} else {
				System.out.println(focused);
			}
		}
	}

	public EntityLiving getFocusedEntity(Entity watcher){
		EntityLiving focusedEntity = null;
		double maxDistance = 64;
		Vec3d vec = new Vec3d(watcher.posX, watcher.posY, watcher.posZ);
		Vec3d posVec = watcher.getPositionVector();
		if(watcher instanceof EntityPlayer){
			vec = vec.addVector(0D, watcher.getEyeHeight(), 0D);
			posVec = posVec.addVector(0D, watcher.getEyeHeight(), 0D);
		}
		Vec3d lookVec = watcher.getLookVec();
		Vec3d vec2 = vec.add(lookVec.normalize().scale(maxDistance));
		RayTraceResult ray = watcher.world.rayTraceBlocks(vec, vec2);
		
		double distance = maxDistance;
		if(ray != null){
			distance = ray.hitVec.distanceTo(posVec);
		}
		Vec3d reachVector = posVec.addVector(lookVec.xCoord * maxDistance, lookVec.yCoord * maxDistance, lookVec.zCoord * maxDistance);
		
		double currentDistance = distance;
		
		List<Entity> entitiesWithinMaxDistance = watcher.world.getEntitiesWithinAABBExcludingEntity(watcher, watcher.getEntityBoundingBox().addCoord(lookVec.xCoord * maxDistance, lookVec.yCoord * maxDistance, lookVec.zCoord * maxDistance).expandXyz(1));
		for(Entity entity : entitiesWithinMaxDistance){
			if(entity instanceof EntityLiving){
				float collisionBorderSize = entity.getCollisionBorderSize();
				AxisAlignedBB hitBox = entity.getEntityBoundingBox().expand(collisionBorderSize, collisionBorderSize, collisionBorderSize);
				RayTraceResult intercept = hitBox.calculateIntercept(posVec, reachVector);
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
}
