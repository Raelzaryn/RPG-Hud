package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import java.util.List;

import javax.annotation.Nullable;

import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementEntityInspectVanilla extends HudElement {

    protected static final ResourceLocation DAMAGE_INDICATOR = new ResourceLocation("rpghud:textures/entityinspect.png");

    @Override
    public boolean checkConditions() {
        return !this.mc.gameSettings.hideGUI && this.settings.getBoolValue(Settings.enable_entity_inspect);
    }

    public HudElementEntityInspectVanilla() {
        super(HudElementType.ENTITY_INSPECT, 0, 0, 0, 0, true);
    }

    @Override
    public void drawElement(AbstractGui gui, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        LivingEntity focused = getFocusedEntity(this.mc.player);
        if(focused != null) {
            int posX = (scaledWidth / 2) + this.settings.getPositionValue(Settings.inspector_position)[0];
            int posY = this.settings.getPositionValue(Settings.inspector_position)[1];
            this.mc.getTextureManager().bindTexture(DAMAGE_INDICATOR);
            gui.blit(posX - 62, 20 + posY, 0, 0, 128, 36);
            drawCustomBar(posX - 25, 34 + posY, 89, 8, (double) focused.getHealth() / (double) focused.getMaxHealth() * 100D,
                    this.settings.getIntValue(Settings.color_health), offsetColorPercent(this.settings.getIntValue(Settings.color_health), OFFSET_PERCENT));
            String stringHealth = ((double) Math.round(focused.getHealth() * 10)) / 10 + "/" + ((double) Math.round(focused.getMaxHealth() * 10)) / 10;
            GlStateManager.scaled(0.5, 0.5, 0.5);
            gui.drawCenteredString(this.mc.fontRenderer, stringHealth, (posX - 27 + 44) * 2, (36 + posY) * 2, -1);
            GlStateManager.scaled(2.0, 2.0, 2.0);

            int x = (posX - 29 + 44 - this.mc.fontRenderer.getStringWidth(focused.getName().getString()) / 2);
            int y = 25 + posY;
            this.mc.fontRenderer.drawString(focused.getName().getString(), x + 1, y, 0);
            this.mc.fontRenderer.drawString(focused.getName().getString(), x - 1, y, 0);
            this.mc.fontRenderer.drawString(focused.getName().getString(), x, y + 1, 0);
            this.mc.fontRenderer.drawString(focused.getName().getString(), x, y - 1, 0);
            this.mc.fontRenderer.drawString(focused.getName().getString(), x, y, -1);

            drawEntityOnScreen(posX - 60 + 16, 22 + 27 + posY, focused);
        }
    }

    public static void drawEntityOnScreen(int posX, int posY, LivingEntity ent) {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        int scale = 1;
        int s = (int) (22 / ent.getHeight());
        int s2 = (int) (22 / ent.getWidth());
        if(s < s2)
            scale = s;
        else
            scale = s2;

        int offset = 0;
        if(ent instanceof SquidEntity) {
            scale = 11;
            offset = -13;
        }
        posY += offset;
        GlStateManager.translatef(posX, posY, 50.0F);
        GlStateManager.scaled((-scale), scale, scale);
        GlStateManager.rotatef(180.0F, 0.0F, 0.0F, 1.0F);
        float f = ent.renderYawOffset;
        float f1 = ent.rotationYaw;
        float f2 = ent.rotationPitch;
        float f3 = ent.prevRotationYawHead;
        float f4 = ent.rotationYawHead;
        GlStateManager.rotatef(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotatef(-135.0F, 0.0F, 1.0F, 0.0F);

        GlStateManager.rotatef(-((float) Math.atan(0 / 40.0F)) * 20.0F, 1.0F, 0.0F, 0.0F);
        ent.renderYawOffset = (float) Math.atan(100 / 40.0F) * 20.0F;
        ent.rotationYaw = (float) Math.atan(25 / 40.0F) * 40.0F;
        ent.rotationPitch = -((float) Math.atan(0 / 40.0F)) * 20.0F;
        ent.rotationYawHead = ent.rotationYaw;
        ent.prevRotationYawHead = ent.rotationYaw;
        GlStateManager.translatef(0.0F, 0.0F, 0.0F);
        EntityRendererManager rendermanager = Minecraft.getInstance().getRenderManager();
        rendermanager.setPlayerViewY(180.0F);
        rendermanager.setRenderShadow(false);
        rendermanager.renderEntity(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
        rendermanager.setRenderShadow(true);
        ent.renderYawOffset = f;
        ent.rotationYaw = f1;
        ent.rotationPitch = f2;
        ent.prevRotationYawHead = f3;
        ent.rotationYawHead = f4;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.activeTexture(GLX.GL_TEXTURE1);
        GlStateManager.disableTexture();
        GlStateManager.activeTexture(GLX.GL_TEXTURE0);
    }

    public static LivingEntity getFocusedEntity(Entity watcher) {
        LivingEntity focusedEntity = null;
        double maxDistance = 64;
        Vec3d vec = new Vec3d(watcher.posX, watcher.posY, watcher.posZ);
        Vec3d posVec = watcher.getPositionVector();
        if(watcher instanceof PlayerEntity) {
            vec = vec.add(0D, watcher.getEyeHeight(), 0D);
            posVec = posVec.add(0D, watcher.getEyeHeight(), 0D);
        }

        Vec3d lookVec = watcher.getLookVec();
        Vec3d vec2 = vec.add(lookVec.normalize().scale(maxDistance));

        RayTraceResult ray = watcher.world
                .rayTraceBlocks(new RayTraceContext(vec, vec2, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, watcher));

        double distance = maxDistance;
        if(ray != null)
            distance = ray.getHitVec().distanceTo(posVec);
        Vec3d reachVector = posVec.add(lookVec.x * maxDistance, lookVec.y * maxDistance, lookVec.z * maxDistance);

        double currentDistance = distance;

        List<Entity> entitiesWithinMaxDistance = watcher.world.getEntitiesWithinAABBExcludingEntity(watcher,
                watcher.getBoundingBox().grow(lookVec.x * maxDistance, lookVec.y * maxDistance, lookVec.z * maxDistance).expand(1, 1, 1));
        for(Entity entity : entitiesWithinMaxDistance)
            if(entity instanceof LivingEntity) {
                float collisionBorderSize = entity.getCollisionBorderSize();
                AxisAlignedBB hitBox = entity.getBoundingBox().expand(collisionBorderSize, collisionBorderSize, collisionBorderSize);
                Vec3d hitVecIn = intercept(posVec, reachVector, hitBox);

                if(hitBox.contains(posVec)) {
                    if(currentDistance <= 0D) {
                        currentDistance = 0;
                        focusedEntity = (LivingEntity) entity;
                    }
                } else if(hitVecIn != null) {
                    Vec3d hitVec = new Vec3d(hitVecIn.x, hitVecIn.y, hitVecIn.z);
                    double distanceToEntity = posVec.distanceTo(hitVec);
                    if(distanceToEntity <= currentDistance) {
                        currentDistance = distanceToEntity;
                        focusedEntity = (LivingEntity) entity;
                    }
                }
            }
        return focusedEntity;
    }

    public static Vec3d intercept(Vec3d vecA, Vec3d vecB, AxisAlignedBB bb) {
        double[] adouble = new double[] { 1.0D };
        Direction enumfacing = null;
        double d0 = vecB.x - vecA.x;
        double d1 = vecB.y - vecA.y;
        double d2 = vecB.z - vecA.z;
        enumfacing = func_197741_a(bb, vecA, adouble, enumfacing, d0, d1, d2);
        if(enumfacing == null)
            return null;
        else {
            double d3 = adouble[0];
            return vecA.add(d3 * d0, d3 * d1, d3 * d2);
        }
    }

    @Nullable
    private static Direction func_197741_a(AxisAlignedBB aabb, Vec3d p_197741_1_, double[] p_197741_2_, @Nullable Direction facing, double p_197741_4_,
            double p_197741_6_, double p_197741_8_) {
        if(p_197741_4_ > 1.0E-7D)
            facing = func_197740_a(p_197741_2_, facing, p_197741_4_, p_197741_6_, p_197741_8_, aabb.minX, aabb.minY, aabb.maxY, aabb.minZ, aabb.maxZ, Direction.WEST,
                    p_197741_1_.x, p_197741_1_.y, p_197741_1_.z);
        else if(p_197741_4_ < -1.0E-7D)
            facing = func_197740_a(p_197741_2_, facing, p_197741_4_, p_197741_6_, p_197741_8_, aabb.maxX, aabb.minY, aabb.maxY, aabb.minZ, aabb.maxZ, Direction.EAST,
                    p_197741_1_.x, p_197741_1_.y, p_197741_1_.z);

        if(p_197741_6_ > 1.0E-7D)
            facing = func_197740_a(p_197741_2_, facing, p_197741_6_, p_197741_8_, p_197741_4_, aabb.minY, aabb.minZ, aabb.maxZ, aabb.minX, aabb.maxX, Direction.DOWN,
                    p_197741_1_.y, p_197741_1_.z, p_197741_1_.x);
        else if(p_197741_6_ < -1.0E-7D)
            facing = func_197740_a(p_197741_2_, facing, p_197741_6_, p_197741_8_, p_197741_4_, aabb.maxY, aabb.minZ, aabb.maxZ, aabb.minX, aabb.maxX, Direction.UP,
                    p_197741_1_.y, p_197741_1_.z, p_197741_1_.x);

        if(p_197741_8_ > 1.0E-7D)
            facing = func_197740_a(p_197741_2_, facing, p_197741_8_, p_197741_4_, p_197741_6_, aabb.minZ, aabb.minX, aabb.maxX, aabb.minY, aabb.maxY,
                    Direction.NORTH, p_197741_1_.z, p_197741_1_.x, p_197741_1_.y);
        else if(p_197741_8_ < -1.0E-7D)
            facing = func_197740_a(p_197741_2_, facing, p_197741_8_, p_197741_4_, p_197741_6_, aabb.maxZ, aabb.minX, aabb.maxX, aabb.minY, aabb.maxY,
                    Direction.SOUTH, p_197741_1_.z, p_197741_1_.x, p_197741_1_.y);

        return facing;
    }

    @Nullable
    private static Direction func_197740_a(double[] p_197740_0_, @Nullable Direction p_197740_1_, double p_197740_2_, double p_197740_4_, double p_197740_6_,
            double p_197740_8_, double p_197740_10_, double p_197740_12_, double p_197740_14_, double p_197740_16_, Direction p_197740_18_, double p_197740_19_,
            double p_197740_21_, double p_197740_23_) {
        double d0 = (p_197740_8_ - p_197740_19_) / p_197740_2_;
        double d1 = p_197740_21_ + d0 * p_197740_4_;
        double d2 = p_197740_23_ + d0 * p_197740_6_;
        if(0.0D < d0 && d0 < p_197740_0_[0] && p_197740_10_ - 1.0E-7D < d1 && d1 < p_197740_12_ + 1.0E-7D && p_197740_14_ - 1.0E-7D < d2
                && d2 < p_197740_16_ + 1.0E-7D) {
            p_197740_0_[0] = d0;
            return p_197740_18_;
        } else
            return p_197740_1_;
    }

}
