package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import java.util.List;

import javax.annotation.Nullable;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementEntityInspectVanilla extends HudElement {

    protected static final ResourceLocation DAMAGE_INDICATOR = new ResourceLocation("rpghud:textures/entityinspect.png");

    @Override
    public boolean checkConditions() {
        return this.settings.getBoolValue(Settings.enable_entity_inspect);
    }

    public HudElementEntityInspectVanilla() {
        super(HudElementType.ENTITY_INSPECT, 0, 0, 0, 0, true);
    }

    @Override
    public void drawElement(AbstractGui gui, MatrixStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        LivingEntity focused = getFocusedEntity(this.mc.player);
        if(focused != null) {
            int posX = (scaledWidth / 2) + this.settings.getPositionValue(Settings.inspector_position)[0];
            int posY = this.settings.getPositionValue(Settings.inspector_position)[1];
            this.mc.getTextureManager().bindTexture(DAMAGE_INDICATOR);
            gui.blit(ms, posX - 62, 20 + posY, 0, 0, 128, 36);
            float health = focused.getHealth();
            float maxHealth = focused.getMaxHealth();
            if(health > maxHealth) health = maxHealth;
            drawCustomBar(posX - 25, 34 + posY, 89, 8, (double) health / (double) maxHealth * 100D,
                    this.settings.getIntValue(Settings.color_health), offsetColorPercent(this.settings.getIntValue(Settings.color_health), OFFSET_PERCENT));
            String stringHealth = ((double) Math.round(health * 10)) / 10 + "/" + ((double) Math.round(maxHealth * 10)) / 10;
            RenderSystem.scaled(0.5, 0.5, 0.5);
            AbstractGui.drawCenteredString(ms, this.mc.fontRenderer, stringHealth, (posX - 27 + 44) * 2, (36 + posY) * 2, -1);
            RenderSystem.scaled(2.0, 2.0, 2.0);

            int x = (posX - 29 + 44 - this.mc.fontRenderer.getStringWidth(focused.getName().getString()) / 2);
            int y = 25 + posY;
            this.drawStringWithBackground(ms, focused.getName().getString(), x, y, -1, 0);

            drawEntityOnScreen(posX - 60 + 16, 22 + 27 + posY, focused);

            if(settings.getBoolValue(Settings.show_entity_armor)) {
                int armor = focused.getTotalArmorValue();
                if(armor > 0) {
                    String value = String.valueOf(armor);
                    this.mc.getTextureManager().bindTexture(DAMAGE_INDICATOR);
                    gui.blit(ms, posX - 26, posY+44, 0, 36, 19, 8);
                    this.mc.getTextureManager().bindTexture(AbstractGui.GUI_ICONS_LOCATION);
                    RenderSystem.scaled(0.5, 0.5, 0.5);
                    gui.blit(ms, (posX - 24) * 2 -1, (posY + 45) * 2, 34, 9, 9, 9);
                    this.drawStringWithBackground(ms,value, (posX - 18) * 2 -2, (posY + 45) * 2 + 1, -1, 0);
                    RenderSystem.scaled(2.0, 2.0, 2.0);
                }  
            }
        }
    }

    public static void drawEntityOnScreen(int posX, int posY, LivingEntity ent) {
        int scale = 1;
        int s1 = (int) (18 / ent.getHeight());
        int s3 = (int) (18 / ent.getRenderScale());
        int offset = 0;
        if(s1 > s3) {
            scale = s3;
        } else
            scale = s1;

        if(ent instanceof SquidEntity) {
            scale = 11;
            offset = -13;
        } else if(ent instanceof SpiderEntity) {
            scale = 11;
            offset = -5;
        }
        posY += offset;
        float lvt_6_1_ = (float) Math.atan((180 / 40.0F));
        float lvt_7_1_ = (float) Math.atan((0 / 40.0F));
        RenderSystem.pushMatrix();
        RenderSystem.translatef(posX, posY, 1050.0F);
        RenderSystem.scalef(1.0F, 1.0F, -1.0F);
        MatrixStack lvt_8_1_ = new MatrixStack();
        lvt_8_1_.translate(0.0D, 0.0D, 1000.0D);
        lvt_8_1_.scale(scale, scale, scale);
        Quaternion lvt_9_1_ = Vector3f.ZP.rotationDegrees(180.0F);
        Quaternion lvt_10_1_ = Vector3f.XP.rotationDegrees(lvt_7_1_ * 20.0F);
        lvt_9_1_.multiply(lvt_10_1_);
        lvt_8_1_.rotate(lvt_9_1_);
        float lvt_11_1_ = ent.renderYawOffset;
        float lvt_12_1_ = ent.rotationYaw;
        float lvt_13_1_ = ent.rotationPitch;
        float lvt_14_1_ = ent.prevRotationYawHead;
        float lvt_15_1_ = ent.rotationYawHead;
        ent.renderYawOffset = 180.0F + lvt_6_1_ * 20.0F;
        ent.rotationYaw = 180.0F + lvt_6_1_ * 40.0F;
        ent.rotationPitch = -lvt_7_1_ * 20.0F;
        ent.rotationYawHead = ent.rotationYaw - 25;
        ent.prevRotationYawHead = ent.rotationYaw;
        EntityRendererManager lvt_16_1_ = Minecraft.getInstance().getRenderManager();
        lvt_10_1_.conjugate();
        lvt_16_1_.setCameraOrientation(lvt_10_1_);
        lvt_16_1_.setRenderShadow(false);
        IRenderTypeBuffer.Impl lvt_17_1_ = Minecraft.getInstance().getRenderTypeBuffers().getBufferSource();
        lvt_16_1_.renderEntityStatic((Entity) ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, lvt_8_1_, (IRenderTypeBuffer) lvt_17_1_, 15728880);
        lvt_17_1_.finish();
        lvt_16_1_.setRenderShadow(true);
        ent.renderYawOffset = lvt_11_1_;
        ent.rotationYaw = lvt_12_1_;
        ent.rotationPitch = lvt_13_1_;
        ent.prevRotationYawHead = lvt_14_1_;
        ent.rotationYawHead = lvt_15_1_;
        RenderSystem.popMatrix();
    }

    public static LivingEntity getFocusedEntity(Entity watcher) {
        LivingEntity focusedEntity = null;
        double maxDistance = 64;
        Vector3d vec = new Vector3d(watcher.getPosX(), watcher.getPosY(), watcher.getPosZ());
        Vector3d posVec = watcher.getPositionVec();
        if(watcher instanceof PlayerEntity) {
            vec = vec.add(0D, watcher.getEyeHeight(), 0D);
            posVec = posVec.add(0D, watcher.getEyeHeight(), 0D);
        }

        Vector3d lookVec = watcher.getLookVec();
        Vector3d vec2 = vec.add(lookVec.normalize().scale(maxDistance));

        RayTraceResult ray = watcher.world
                .rayTraceBlocks(new RayTraceContext(vec, vec2, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, watcher));

        double distance = maxDistance;
        if(ray != null) {
            distance = ray.getHitVec().distanceTo(posVec);
        }
        Vector3d reachVector = posVec.add(lookVec.x * maxDistance, lookVec.y * maxDistance, lookVec.z * maxDistance);

        double currentDistance = distance;

        List<Entity> entitiesWithinMaxDistance = watcher.world.getEntitiesWithinAABBExcludingEntity(watcher,
                watcher.getBoundingBox().grow(lookVec.x * maxDistance, lookVec.y * maxDistance, lookVec.z * maxDistance).expand(1, 1, 1));
        for(Entity entity : entitiesWithinMaxDistance) {
            if(entity instanceof LivingEntity) {
                float collisionBorderSize = entity.getCollisionBorderSize();
                AxisAlignedBB hitBox = entity.getBoundingBox().expand(collisionBorderSize, collisionBorderSize, collisionBorderSize);
                Vector3d hitVecIn = intercept(posVec, reachVector, hitBox);

                if(hitBox.contains(posVec)) {
                    if(currentDistance <= 0D) {
                        currentDistance = 0;
                        focusedEntity = (LivingEntity) entity;
                    }
                } else if(hitVecIn != null) {
                    Vector3d hitVec = new Vector3d(hitVecIn.x, hitVecIn.y, hitVecIn.z);
                    double distanceToEntity = posVec.distanceTo(hitVec);
                    if(distanceToEntity <= currentDistance) {
                        currentDistance = distanceToEntity;
                        focusedEntity = (LivingEntity) entity;
                    }
                }
            }
        }
        return focusedEntity;
    }

    public static Vector3d intercept(Vector3d vecA, Vector3d vecB, AxisAlignedBB bb) {
        double[] adouble = new double[] { 1.0D };
        Direction enumfacing = null;
        double d0 = vecB.x - vecA.x;
        double d1 = vecB.y - vecA.y;
        double d2 = vecB.z - vecA.z;
        enumfacing = func_197741_a(bb, vecA, adouble, enumfacing, d0, d1, d2);
        if(enumfacing == null) {
            return null;
        } else {
            double d3 = adouble[0];
            return vecA.add(d3 * d0, d3 * d1, d3 * d2);
        }
    }

    @Nullable
    private static Direction func_197741_a(AxisAlignedBB aabb, Vector3d p_197741_1_, double[] p_197741_2_, @Nullable Direction facing, double p_197741_4_,
            double p_197741_6_, double p_197741_8_) {
        if(p_197741_4_ > 1.0E-7D) {
            facing = func_197740_a(p_197741_2_, facing, p_197741_4_, p_197741_6_, p_197741_8_, aabb.minX, aabb.minY, aabb.maxY, aabb.minZ, aabb.maxZ, Direction.WEST,
                    p_197741_1_.x, p_197741_1_.y, p_197741_1_.z);
        } else if(p_197741_4_ < -1.0E-7D) {
            facing = func_197740_a(p_197741_2_, facing, p_197741_4_, p_197741_6_, p_197741_8_, aabb.maxX, aabb.minY, aabb.maxY, aabb.minZ, aabb.maxZ, Direction.EAST,
                    p_197741_1_.x, p_197741_1_.y, p_197741_1_.z);
        }

        if(p_197741_6_ > 1.0E-7D) {
            facing = func_197740_a(p_197741_2_, facing, p_197741_6_, p_197741_8_, p_197741_4_, aabb.minY, aabb.minZ, aabb.maxZ, aabb.minX, aabb.maxX, Direction.DOWN,
                    p_197741_1_.y, p_197741_1_.z, p_197741_1_.x);
        } else if(p_197741_6_ < -1.0E-7D) {
            facing = func_197740_a(p_197741_2_, facing, p_197741_6_, p_197741_8_, p_197741_4_, aabb.maxY, aabb.minZ, aabb.maxZ, aabb.minX, aabb.maxX, Direction.UP,
                    p_197741_1_.y, p_197741_1_.z, p_197741_1_.x);
        }

        if(p_197741_8_ > 1.0E-7D) {
            facing = func_197740_a(p_197741_2_, facing, p_197741_8_, p_197741_4_, p_197741_6_, aabb.minZ, aabb.minX, aabb.maxX, aabb.minY, aabb.maxY,
                    Direction.NORTH, p_197741_1_.z, p_197741_1_.x, p_197741_1_.y);
        } else if(p_197741_8_ < -1.0E-7D) {
            facing = func_197740_a(p_197741_2_, facing, p_197741_8_, p_197741_4_, p_197741_6_, aabb.maxZ, aabb.minX, aabb.maxX, aabb.minY, aabb.maxY,
                    Direction.SOUTH, p_197741_1_.z, p_197741_1_.x, p_197741_1_.y);
        }

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
        } else {
            return p_197740_1_;
        }
    }

}
