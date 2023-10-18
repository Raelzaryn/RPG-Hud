package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import static net.minecraft.world.level.ClipContext.Block.OUTLINE;
import static net.minecraft.world.level.ClipContext.Fluid.NONE;

import java.util.List;

import org.joml.Quaternionf;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
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
    public void drawElement(Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        LivingEntity focused = getFocusedEntity(this.mc.player);
        if(focused != null) {
            int posX = (scaledWidth / 2) + this.settings.getPositionValue(Settings.inspector_position)[0];
            int posY = this.settings.getPositionValue(Settings.inspector_position)[1];
            bind(DAMAGE_INDICATOR);
            GuiComponent.blit(ms, posX - 62, 20 + posY, 0, 0, 128, 36);
            float health = focused.getHealth();
            float maxHealth = focused.getMaxHealth();
            if(health > maxHealth) health = maxHealth;
            drawCustomBar(ms, posX - 25, 34 + posY, 89, 8, (double) health / (double) maxHealth * 100D,
                    this.settings.getIntValue(Settings.color_health), offsetColorPercent(this.settings.getIntValue(Settings.color_health), OFFSET_PERCENT));
            String stringHealth = ((double) Math.round(health * 10)) / 10 + "/" + ((double) Math.round(maxHealth * 10)) / 10;
            ms.scale(0.5f, 0.5f, 0.5f);
            Gui.drawCenteredString(ms, this.mc.font, stringHealth, (posX - 27 + 44) * 2, (36 + posY) * 2, -1);
            ms.scale(2f, 2f, 2f);

            int x = (posX - 29 + 44 - this.mc.font.width(focused.getName().getString()) / 2);
            int y = 25 + posY;
            this.drawStringWithBackground(ms, focused.getName().getString(), x, y, -1, 0);

            drawEntityOnScreen(posX - 60 + 16, 22 + 27 + posY, focused);

            if(settings.getBoolValue(Settings.show_entity_armor)) {
                int armor = focused.getArmorValue();
                if(armor > 0) {
                    String value = String.valueOf(armor);
                    bind(DAMAGE_INDICATOR);
                    GuiComponent.blit(ms, posX - 26, posY+44, 0, 36, 19, 8);
                    bind(Gui.GUI_ICONS_LOCATION);
                    ms.scale(0.5f, 0.5f, 0.5f);
                    GuiComponent.blit(ms, (posX - 24) * 2 -1, (posY + 45) * 2, 34, 9, 9, 9);
                    this.drawStringWithBackground(ms,value, (posX - 18) * 2 -2, (posY + 45) * 2 + 1, -1, 0);
                    ms.scale(2f, 2f, 2f);
                }  
            }
        }
    }

    public static void drawEntityOnScreen(int posX, int posY, LivingEntity entity) {
        int scale = 1;
        int s1 = (int) (18 / entity.getBbHeight());
        int s3 = (int) (18 / entity.getScale());
        int offset = 0;
        if(s1 > s3) {
            scale = s3;
        } else
            scale = s1;

        if(entity instanceof Squid) {
            scale = 11;
            offset = -13;
        } else if(entity instanceof Spider) {
            scale = 11;
            offset = -5;
        }
        posY += offset;
        float f = (float) Math.atan((180 / 40.0F));
        float g = (float) Math.atan((0 / 40.0F));
        PoseStack ms = RenderSystem.getModelViewStack();
        ms.pushPose();
        ms.translate(posX, posY, 1050.0F);
        ms.scale(1.0F, 1.0F, -1.0F);
        RenderSystem.applyModelViewMatrix();
        PoseStack poseStack = new PoseStack();
        poseStack.translate(0.0D, 0.0D, 1000.0D);
        poseStack.scale(scale, scale, scale);
        Quaternionf quaternion = new Quaternionf().rotationZ((float) Math.PI);
        Quaternionf quaternion2 = new Quaternionf().rotationX((float) Math.toRadians(g * 20f));
        quaternion.mul(quaternion2);
        poseStack.mulPose(quaternion);
        float h = entity.yBodyRot;
        float i = entity.getYRot();
        float j = entity.getXRot();
        float k = entity.yHeadRotO;
        float l = entity.yHeadRot;
        float m = entity.walkAnimation.speed();


        entity.setYBodyRot(180.0F + f * 20.0F);
        entity.setYRot(180.0F + f * 40.0F);
        entity.setXRot(-g * 20.0F);
        entity.yHeadRot = entity.getYRot()-35f;
        entity.yHeadRotO = entity.getYRot();
        entity.walkAnimation.setSpeed(0);

        Lighting.setupForEntityInInventory();
        EntityRenderDispatcher entityRenderDispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        quaternion2.conjugate();
        entityRenderDispatcher.overrideCameraOrientation(quaternion2);
        entityRenderDispatcher.setRenderShadow(false);
        MultiBufferSource.BufferSource immediate = Minecraft.getInstance().renderBuffers().bufferSource();
        entityRenderDispatcher.render(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, poseStack, immediate, 15728880);
        immediate.endBatch();
        entityRenderDispatcher.setRenderShadow(true);


        entity.yBodyRot = h;
        entity.setYRot(i);
        entity.setXRot(j);
        entity.yHeadRotO = k;
        entity.yHeadRot = l;
        entity.walkAnimation.setSpeed(m);


        ms.popPose();
        RenderSystem.applyModelViewMatrix();
        Lighting.setupFor3DItems();
    }

    public static LivingEntity getFocusedEntity(Entity watcher) {
        LivingEntity focusedEntity = null;
        double maxDistance = 64;
        Vec3 vec = new Vec3(watcher.getX(), watcher.getY(), watcher.getZ());
        Vec3 posVec = watcher.position();
        if(watcher instanceof Player) {
            vec = vec.add(0D, watcher.getEyeHeight(), 0D);
            posVec = posVec.add(0D, watcher.getEyeHeight(), 0D);
        }

        Vec3 lookVec = Vec3.directionFromRotation(watcher.getRotationVector());
        Vec3 vec2 = vec.add(lookVec.normalize().multiply(maxDistance,maxDistance,maxDistance));

        BlockHitResult ray = watcher.level
                .clip(new ClipContext(vec, vec2, OUTLINE, NONE, watcher));

        double distance = maxDistance;
        if(ray != null) {
            distance = ray.getBlockPos().distToCenterSqr(posVec);
        }
        Vec3 reachVector = posVec.add(lookVec.x * maxDistance, lookVec.y * maxDistance, lookVec.z * maxDistance);

        double currentDistance = distance;

        List<Entity> entitiesWithinMaxDistance = watcher.level.getEntities(watcher,
                watcher.getBoundingBox().expandTowards(lookVec.x * maxDistance, lookVec.y * maxDistance, lookVec.z * maxDistance).expandTowards(1, 1, 1));
        for(Entity entity : entitiesWithinMaxDistance) {
            if(entity instanceof LivingEntity) {
                float collisionBorderSize = entity.getPickRadius();
                AABB hitBox = entity.getBoundingBox().expandTowards(collisionBorderSize, collisionBorderSize, collisionBorderSize);
                Vec3 hitVecIn = intercept(posVec, reachVector, hitBox);
                if(hitBox.contains(posVec)) {
                    if(currentDistance <= 0D) {
                        currentDistance = 0;
                        focusedEntity = (LivingEntity) entity;
                    }
                } else if(hitVecIn != null) {
                    Vec3 hitVec = new Vec3(hitVecIn.x, hitVecIn.y, hitVecIn.z);
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

    public static Vec3 intercept(Vec3 vecA, Vec3 vecB, AABB bb) {
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

    private static Direction func_197741_a(AABB aabb, Vec3 p_197741_1_, double[] p_197741_2_, Direction facing, double p_197741_4_,
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

    private static Direction func_197740_a(double[] p_197740_0_, Direction p_197740_1_, double p_197740_2_, double p_197740_4_, double p_197740_6_,
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
