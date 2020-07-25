package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import java.util.List;

import com.mojang.blaze3d.platform.GlStateManager;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RayTraceContext;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(EnvType.CLIENT)
public class HudElementEntityInspectVanilla extends HudElement {

    protected static final Identifier DAMAGE_INDICATOR = new Identifier("rpghud:textures/entityinspect.png");

    @Override
    public boolean checkConditions() {
        return this.settings.getBoolValue(Settings.enable_entity_inspect);
    }

    public HudElementEntityInspectVanilla() {
        super(HudElementType.ENTITY_INSPECT, 0, 0, 0, 0, true);
    }

    @Override
    public void drawElement(DrawableHelper gui, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        LivingEntity focused = getFocusedEntity(this.mc.player);
        if(focused != null) {
            int posX = (scaledWidth / 2) + this.settings.getPositionValue(Settings.inspector_position)[0];
            int posY = this.settings.getPositionValue(Settings.inspector_position)[1];
            
            float health = focused.getHealth();
            float maxHealth = focused.getMaximumHealth();
            if(health > maxHealth) health = maxHealth;
            
            this.mc.getTextureManager().bindTexture(DAMAGE_INDICATOR);
            gui.blit(posX - 62, 20 + posY, 0, 0, 128, 36);
            drawCustomBar(posX - 25, 34 + posY, 89, 8, (double) health / (double) maxHealth * 100D,
                    this.settings.getIntValue(Settings.color_health), offsetColorPercent(this.settings.getIntValue(Settings.color_health), OFFSET_PERCENT));
            String stringHealth = ((double) Math.round(health * 10)) / 10 + "/" + ((double) Math.round(maxHealth * 10)) / 10;
            GlStateManager.scaled(0.5, 0.5, 0.5);
            gui.drawCenteredString(this.mc.textRenderer, stringHealth, (posX - 27 + 44) * 2, (36 + posY) * 2, -1);
            GlStateManager.scaled(2.0, 2.0, 2.0);

            int x = (posX - 29 + 44 - this.mc.textRenderer.getStringWidth(focused.getName().getString()) / 2);
            int y = 25 + posY;
            this.drawStringWithBackground(focused.getName().getString(), x, y, -1, 0);

            drawEntityOnScreen(posX - 60 + 16, 22 + 27 + posY, focused);

            if(settings.getBoolValue(Settings.show_entity_armor)) {
                int armor = focused.getArmor();
                if(armor > 0) {
                    String value = String.valueOf(armor);
                    this.mc.getTextureManager().bindTexture(DAMAGE_INDICATOR);
                    gui.blit(posX - 26, posY+44, 0, 36, 19, 8);
                    this.mc.getTextureManager().bindTexture(DrawableHelper.GUI_ICONS_LOCATION);
                    GlStateManager.scaled(0.5, 0.5, 0.5);
                    gui.blit((posX - 24) * 2 -1, (posY + 45) * 2, 34, 9, 9, 9);
                    gui.drawString(mc.textRenderer, value, (posX - 18) * 2 -2, (posY + 45) * 2 + 1, -1);
                    GlStateManager.scaled(2.0, 2.0, 2.0);
                }  
            }
        }
    }

    public static void drawEntityOnScreen(int posX, int posY, LivingEntity entity) {
        int scale = 1;
        int s1 = (int) (18 / entity.getHeight());
        int s3 = (int) (18 / entity.getScaleFactor());
        int offset = 0;
        if(s1 > s3) {
            scale = s3;
        } else
            scale = s1;

        if(entity instanceof SquidEntity) {
            scale = 11;
            offset = -13;
        } else if(entity instanceof SpiderEntity) {
            scale = 11;
            offset = -5;
        }
        posY += offset;
        float f = (float) Math.atan((180 / 40.0F));
        float g = (float) Math.atan((0 / 40.0F));
        GlStateManager.pushMatrix();
        GlStateManager.translatef(posX, posY, 50.0F);
        GlStateManager.scaled(-scale, -scale, -scale);
        float h = entity.field_6283;
        float i = entity.yaw;
        float j = entity.pitch;
        float k = entity.prevHeadYaw;
        float l = entity.headYaw;
        entity.field_6283 = 180.0F + f * 20.0F;
        entity.yaw = 180.0F + f * 40.0F;
        entity.pitch = -g * 20.0F;
        entity.headYaw = entity.yaw;
        entity.prevHeadYaw = entity.yaw;
        entity.field_6220 = 0;
        EntityRenderDispatcher entityRenderDispatcher = MinecraftClient.getInstance().getEntityRenderManager();
        entityRenderDispatcher.method_3945(180.0F);
        entityRenderDispatcher.setRenderShadows(false);
        entityRenderDispatcher.render(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
        entityRenderDispatcher.setRenderShadows(true);
        entity.field_6283 = h;
        entity.yaw = i;
        entity.pitch = j;
        entity.prevHeadYaw = k;
        entity.headYaw = l;
        GlStateManager.popMatrix();
    }

    public static LivingEntity getFocusedEntity(Entity watcher) {
        LivingEntity focusedEntity = null;
        double maxDistance = 64;
        Vec3d vec = new Vec3d(watcher.x, watcher.y, watcher.z);
        Vec3d posVec = watcher.getPos();
        if(watcher instanceof PlayerEntity) {
            vec = vec.add(0D, watcher.getStandingEyeHeight(), 0D);
            posVec = posVec.add(0D, watcher.getStandingEyeHeight(), 0D);
        }

        Vec3d lookVec = watcher.getRotationVector();
        Vec3d vec2 = vec.add(lookVec.normalize().multiply(maxDistance));

        BlockHitResult ray = watcher.world
                .rayTrace(new RayTraceContext(vec, vec2, RayTraceContext.ShapeType.OUTLINE, RayTraceContext.FluidHandling.NONE, watcher));

        double distance = maxDistance;
        if(ray != null) {
            distance = ray.getPos().distanceTo(posVec);
        }
        Vec3d reachVector = posVec.add(lookVec.x * maxDistance, lookVec.y * maxDistance, lookVec.z * maxDistance);

        double currentDistance = distance;

        List<Entity> entitiesWithinMaxDistance = watcher.world.getEntities(watcher,
                watcher.getBoundingBox().stretch(lookVec.x * maxDistance, lookVec.y * maxDistance, lookVec.z * maxDistance).expand(1, 1, 1));
        for(Entity entity : entitiesWithinMaxDistance) {
            if(entity instanceof LivingEntity) {
                float collisionBorderSize = entity.getTargetingMargin();
                Box hitBox = entity.getBoundingBox().expand(collisionBorderSize, collisionBorderSize, collisionBorderSize);
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
        }
        return focusedEntity;
    }

    public static Vec3d intercept(Vec3d vecA, Vec3d vecB, Box bb) {
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

    private static Direction func_197741_a(Box aabb, Vec3d p_197741_1_, double[] p_197741_2_, Direction facing, double p_197741_4_,
            double p_197741_6_, double p_197741_8_) {
        if(p_197741_4_ > 1.0E-7D) {
            facing = func_197740_a(p_197741_2_, facing, p_197741_4_, p_197741_6_, p_197741_8_, aabb.x1, aabb.y1, aabb.y2, aabb.z1, aabb.z2, Direction.WEST,
                    p_197741_1_.x, p_197741_1_.y, p_197741_1_.z);
        } else if(p_197741_4_ < -1.0E-7D) {
            facing = func_197740_a(p_197741_2_, facing, p_197741_4_, p_197741_6_, p_197741_8_, aabb.x2, aabb.y1, aabb.y2, aabb.z1, aabb.z2, Direction.EAST,
                    p_197741_1_.x, p_197741_1_.y, p_197741_1_.z);
        }

        if(p_197741_6_ > 1.0E-7D) {
            facing = func_197740_a(p_197741_2_, facing, p_197741_6_, p_197741_8_, p_197741_4_, aabb.y1, aabb.z1, aabb.z2, aabb.x1, aabb.x2, Direction.DOWN,
                    p_197741_1_.y, p_197741_1_.z, p_197741_1_.x);
        } else if(p_197741_6_ < -1.0E-7D) {
            facing = func_197740_a(p_197741_2_, facing, p_197741_6_, p_197741_8_, p_197741_4_, aabb.y2, aabb.z1, aabb.z2, aabb.x1, aabb.x2, Direction.UP,
                    p_197741_1_.y, p_197741_1_.z, p_197741_1_.x);
        }

        if(p_197741_8_ > 1.0E-7D) {
            facing = func_197740_a(p_197741_2_, facing, p_197741_8_, p_197741_4_, p_197741_6_, aabb.z1, aabb.x1, aabb.x2, aabb.y1, aabb.y2,
                    Direction.NORTH, p_197741_1_.z, p_197741_1_.x, p_197741_1_.y);
        } else if(p_197741_8_ < -1.0E-7D) {
            facing = func_197740_a(p_197741_2_, facing, p_197741_8_, p_197741_4_, p_197741_6_, aabb.z2, aabb.x1, aabb.x2, aabb.y1, aabb.y2,
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
