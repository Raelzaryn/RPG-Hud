package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import java.util.List;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.TextAlignment;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.squid.Squid;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.monster.spider.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

import static net.minecraft.world.level.ClipContext.Fluid.SOURCE_ONLY;

@Environment(value=EnvType.CLIENT)
public class HudElementEntityInspectVanilla extends HudElement {

    protected static final Identifier DAMAGE_INDICATOR = Identifier.parse("rpghud:textures/entityinspect.png");

    @Override
    public boolean checkConditions() {
        return this.settings.getBoolValue(Settings.enable_entity_inspect);
    }

    public HudElementEntityInspectVanilla() {
        super(HudElementType.ENTITY_INSPECT, 0, 0, 0, 0, true);
    }

    @Override
    public void drawElement(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, int scaledWidth, int scaledHeight) {
        LivingEntity focused = getFocusedEntity(this.mc.player, deltaTracker);
        if(focused != null && !(focused instanceof ArmorStand) && !focused.hasEffect(MobEffects.INVISIBILITY)) {
            int posX = (scaledWidth / 2) + this.settings.getPositionValue(Settings.inspector_position)[0];
            int posY = this.settings.getPositionValue(Settings.inspector_position)[1];
            graphics.blit(RenderPipelines.GUI_TEXTURED, DAMAGE_INDICATOR, posX - 62, 20 + posY, 0, 0, 128, 36, 256, 256);
            float health = focused.getHealth();
            float maxHealth = focused.getMaxHealth();
            if (health > maxHealth) health = maxHealth;
            drawCustomBar(graphics, posX - 25, 34 + posY, 89, 8, (double) health / (double) maxHealth * 100D,
                    this.settings.getIntValue(Settings.color_health), offsetColorPercent(this.settings.getIntValue(Settings.color_health), OFFSET_PERCENT));
            String stringHealth = ((double) Math.round(health * 10)) / 10 + "/" + ((double) Math.round(maxHealth * 10)) / 10;
            graphics.pose().scale(0.5f, 0.5f);
            graphics.centeredText(this.mc.font, stringHealth, (posX - 27 + 44) * 2, (36 + posY) * 2, -1);
            graphics.pose().scale(2f, 2f);

            int x = (posX - 29 + 44 - this.mc.font.width(focused.getName().getString()) / 2);
            int y = 25 + posY;
            this.drawStringWithBackground(graphics, focused.getName().getString(), x, y, -1, 0);

            drawEntityOnScreen(graphics, posX - 60 + 16, 22 + 27 + posY, focused);

            if (settings.getBoolValue(Settings.show_entity_armor)) {
                int armor = focused.getArmorValue();
                if (armor > 0) {
                    String value = String.valueOf(armor);
                    graphics.blit(RenderPipelines.GUI_TEXTURED, DAMAGE_INDICATOR, posX - 26, posY + 44, 0, 36, 19, 8, 256, 256);
                    graphics.pose().scale(0.5f, 0.5f);
                    graphics.blitSprite(RenderPipelines.GUI_TEXTURED, ARMOR_FULL_TEXTURE, (posX - 24) * 2 - 1, (posY + 45) * 2, 9, 9);
                    this.drawStringWithBackground(graphics, value, (posX - 18) * 2 - 2, (posY + 45) * 2 + 1, -1, 0);
                    graphics.pose().scale(2f, 2f);
                }
            }
        }
    }

    public static void drawEntityOnScreen(GuiGraphicsExtractor graphics, int posX, int posY, LivingEntity entity) {
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
        
        int x1 = posX - 24;
        int x2 = posX + 24;
        int y1 = posY - 25;
        int y2 = posY + 24;
        graphics.enableScissor(posX - 14, posY - 25 - offset, posX+15, posY+3 - offset);
		
		float h = (float)Math.atan(180 / 40.0F);
		float i = (float)Math.atan(0/ 40.0F);
		Quaternionf quaternionf = new Quaternionf().rotateZ((float) Math.PI);
		Quaternionf quaternionf2 = new Quaternionf().rotateX(i * 20.0F * (float) (Math.PI / 180.0));
		quaternionf.mul(quaternionf2);
		float j = entity.yBodyRot;
		float k = entity.getYRot();
		float l = entity.getXRot();
		float m = entity.yHeadRotO;
		float n = entity.yHeadRot;
		float o = entity.walkAnimation.speed();
		entity.walkAnimation.setSpeed(0);
		entity.yBodyRot = 180.0F + h * 20.0F;
		entity.setYRot(180.0F + h * 40.0F);
		entity.setXRot(-i * 20.0F);
		entity.yHeadRot = entity.getYRot() -35f;
		entity.yHeadRotO = entity.getYRot();
		Vector3f vector3f = new Vector3f(0.0F, 0, 0.0F);

        EntityRenderDispatcher entityRenderDispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        EntityRenderer<? super LivingEntity, ?> entityRenderer = entityRenderDispatcher.getRenderer(entity);
		EntityRenderState entityRenderState = entityRenderer.createRenderState(entity, 1.0F);

		graphics.entity(entityRenderState, scale, vector3f, quaternionf, quaternionf2, x1, y1, x2, y2);
		entity.yBodyRot = j;
		entity.setYRot(k);
		entity.setXRot(l);
		entity.yHeadRotO = m;
		entity.yHeadRot = n;
		entity.walkAnimation.setSpeed(o);
		graphics.disableScissor();
    }

    public static LivingEntity getFocusedEntity(Entity watcher, DeltaTracker deltaTracker) {
        LivingEntity focusedEntity = null;
        double maxDistance = 64;
        Vec3 vec = new Vec3(watcher.getX(), watcher.getY(), watcher.getZ());
        Vec3 posVec = watcher.getPosition(deltaTracker.getGameTimeDeltaTicks());
        if(watcher instanceof Player) {
            vec = vec.add(0D, watcher.getEyeHeight(), 0D);
            posVec = posVec.add(0D, watcher.getEyeHeight(), 0D);
        }

        Vec3 lookVec = watcher.getLookAngle();
        Vec3 vec2 = vec.add(lookVec.normalize().scale(maxDistance));

        Level level = watcher.level();
        BlockHitResult ray = level.clip(new ClipContext(vec, vec2, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, watcher));

        double distance = maxDistance;
        if(ray != null) {
            distance = ray.getLocation().distanceTo(posVec);
        }
        Vec3 reachVector = posVec.add(lookVec.x * maxDistance, lookVec.y * maxDistance, lookVec.z * maxDistance);

        double currentDistance = distance;

        List<Entity> entitiesWithinMaxDistance = level.getEntities(watcher,
                watcher.getBoundingBox().expandTowards(lookVec.x * maxDistance, lookVec.y * maxDistance, lookVec.z * maxDistance).inflate(1, 1, 1));
        for(Entity entity : entitiesWithinMaxDistance) {
            if(entity instanceof LivingEntity) {
                float collisionBorderSize = entity.getPickRadius();
                AABB hitBox = entity.getBoundingBox().inflate(collisionBorderSize, collisionBorderSize, collisionBorderSize);
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
        if (focusedEntity instanceof ArmorStand) return null;
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
