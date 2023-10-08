package net.spellcraftgaming.rpghud.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.main.ModRPGHud.HeartTypeNew;
import net.spellcraftgaming.rpghud.main.RenderOverlay;

@Environment(value=EnvType.CLIENT)
@Mixin(InGameHud.class)
public class RenderOverlayMixin {
	
    private static final Identifier ICONS = new Identifier("textures/gui/icons.png");
    
    private int lastHealthValue;
    private int renderHealthValue;
    private long lastHealthCheckTime;
    private long heartJumpEndTick;
    private int ticks;

    @Inject(at = @At("HEAD"), method = "tick")
    private void tick(CallbackInfo info) {
        ++this.ticks;
        ;
    }

    @Inject(at = @At("HEAD"), method = "renderStatusBars", cancellable = true)
    private void renderStatusBars(DrawContext dc, CallbackInfo info) {
        MinecraftClient client = MinecraftClient.getInstance();
        int scaledWidth = client.getWindow().getScaledWidth();
        int scaledHeight = client.getWindow().getScaledHeight();
        Random random = new Random();
        int ac;
        int ab;
        int aa;
        int z;
        int y;
        int x;
        PlayerEntity playerEntity = this.getCameraPlayer();
        if (playerEntity == null) {
            return;
        }
        int i = MathHelper.ceil((float)playerEntity.getHealth());
        boolean bl = this.heartJumpEndTick > (long)this.ticks && (this.heartJumpEndTick - (long)this.ticks) / 3L % 2L == 1L;
        long l = Util.getMeasuringTimeMs();
        if (i < this.lastHealthValue && playerEntity.timeUntilRegen > 0) {
            this.lastHealthCheckTime = l;
            this.heartJumpEndTick = this.ticks + 20;
        } else if (i > this.lastHealthValue && playerEntity.timeUntilRegen > 0) {
            this.lastHealthCheckTime = l;
            this.heartJumpEndTick = this.ticks + 10;
        }
        if (l - this.lastHealthCheckTime > 1000L) {
            this.lastHealthValue = i;
            this.renderHealthValue = i;
            this.lastHealthCheckTime = l;
        }
        this.lastHealthValue = i;
        int j = this.renderHealthValue;
        random.setSeed((long)(this.ticks * 312871));
        HungerManager hungerManager = playerEntity.getHungerManager();
        int k = hungerManager.getFoodLevel();
        int m = scaledWidth / 2 - 91;
        int n = scaledWidth / 2 + 91;
        int o = scaledHeight - 39;
        float f = Math.max((float)playerEntity.getAttributeValue(EntityAttributes.GENERIC_MAX_HEALTH), (float)Math.max(j, i));
        int p = MathHelper.ceil((float)playerEntity.getAbsorptionAmount());
        int q = MathHelper.ceil((float)((f + (float)p) / 2.0f / 10.0f));
        int r = Math.max(10 - (q - 2), 3);
        int s = o - (q - 1) * r - 10;
        int t = o - 10;
        int u = playerEntity.getArmor();
        int v = -1;
        if (playerEntity.hasStatusEffect(StatusEffects.REGENERATION)) {
            v = this.ticks % MathHelper.ceil((float)(f + 5.0f));
        }
        if(RenderOverlay.shouldRenderVanilla(HudElementType.ARMOR)) {
	        client.getProfiler().push("armor");
	        for (int w = 0; w < 10; ++w) {
	            if (u <= 0) continue;
	            x = m + w * 8;
	            if (w * 2 + 1 < u) {
	                dc.drawTexture(ICONS, x, s, 34, 9, 9, 9);
	            }
	            if (w * 2 + 1 == u) {
	                dc.drawTexture(ICONS, x, s, 25, 9, 9, 9);
	            }
	            if (w * 2 + 1 <= u) continue;
	            dc.drawTexture(ICONS, x, s, 16, 9, 9, 9);
	        }
        }
        if(RenderOverlay.shouldRenderVanilla(HudElementType.HEALTH)) {
	        client.getProfiler().swap("health");
	        this.renderHealthBar(dc, random, playerEntity, m, o, r, v, f, i, j, p, bl);
        }
        LivingEntity livingEntity = this.getRiddenEntity();
        x = this.getHeartCount(livingEntity);
        if(RenderOverlay.shouldRenderVanilla(HudElementType.FOOD)) {
	        if (x == 0) {
	            client.getProfiler().swap("food");
	            for (y = 0; y < 10; ++y) {
	                z = o;
	                aa = 16;
	                ab = 0;
	                if (playerEntity.hasStatusEffect(StatusEffects.HUNGER)) {
	                    aa += 36;
	                    ab = 13;
	                }
	                if (playerEntity.getHungerManager().getSaturationLevel() <= 0.0f && this.ticks % (k * 3 + 1) == 0) {
	                    z += random.nextInt(3) - 1;
	                }
	                ac = n - y * 8 - 9;
	                dc.drawTexture(ICONS, ac, z, 16 + ab * 9, 27, 9, 9);
	                if (y * 2 + 1 < k) {
	                    dc.drawTexture(ICONS, ac, z, aa + 36, 27, 9, 9);
	                }
	                if (y * 2 + 1 != k) continue;
	                dc.drawTexture(ICONS, ac, z, aa + 45, 27, 9, 9);
	            }
	            t -= 10;
	        }
        }
        if(RenderOverlay.shouldRenderVanilla(HudElementType.AIR)) {
	        client.getProfiler().swap("air");
	        y = playerEntity.getMaxAir();
	        z = Math.min(playerEntity.getAir(), y);
	        if (playerEntity.isSubmergedIn(FluidTags.WATER) || z < y) {
	            aa = this.getHeartRows(x) - 1;
	            t -= aa * 10;
	            ab = MathHelper.ceil((double)((double)(z - 2) * 10.0 / (double)y));
	            ac = MathHelper.ceil((double)((double)z * 10.0 / (double)y)) - ab;
	            for (int ad = 0; ad < ab + ac; ++ad) {
	                if (ad < ab) {
	                    dc.drawTexture(ICONS, n - ad * 8 - 9, t, 16, 18, 9, 9);
	                    continue;
	                }
	                dc.drawTexture(ICONS, n - ad * 8 - 9, t, 25, 18, 9, 9);
	            }
	        }
        }
        client.getProfiler().pop();
        info.cancel();
    }


    @Inject(at = @At("HEAD"), method = "renderHotbar", cancellable = true)
    private void renderHotbar(CallbackInfo info) {
        if(!RenderOverlay.shouldRenderVanilla(HudElementType.HOTBAR))
            info.cancel();
    }

    @Inject(at = @At("HEAD"), method = "renderMountJumpBar", cancellable = true)
    private void renderMountJumpBar(CallbackInfo info) {
        if(!RenderOverlay.shouldRenderVanilla(HudElementType.JUMP_BAR))
            info.cancel();
    }

    @Inject(at = @At("HEAD"), method = "renderExperienceBar", cancellable = true)
    private void renderExperienceBar(CallbackInfo info) {
        if(!RenderOverlay.shouldRenderVanilla(HudElementType.EXPERIENCE))
            info.cancel();
    }

    @Inject(at = @At("HEAD"), method = "renderMountHealth", cancellable = true)
    private void renderMountHealth(CallbackInfo info) {
        if(!RenderOverlay.shouldRenderVanilla(HudElementType.HEALTH_MOUNT))
            info.cancel();
    }
    
    @Inject(at = @At("HEAD"), method = "renderStatusEffectOverlay", cancellable = true)
    private void renderStatusEffectOverlay(CallbackInfo info) {
        if(!RenderOverlay.shouldRenderVanilla(HudElementType.STATUS_EFFECTS))
            info.cancel();
    }

    private int getHeartCount(LivingEntity entity) {
        if(entity != null && entity.isLiving()) {
            float f = entity.getMaxHealth();
            int i = (int) (f + 0.5F) / 2;
            if(i > 30) {
                i = 30;
            }

            return i;
        } else {
            return 0;
        }
    }

    private int getHeartRows(int heartCount) {
        return (int) Math.ceil((double) heartCount / 10.0D);
    }

    private PlayerEntity getCameraPlayer() {
        MinecraftClient client = MinecraftClient.getInstance();
        return !(client.getCameraEntity() instanceof PlayerEntity) ? null : (PlayerEntity) client.getCameraEntity();
    }

    private LivingEntity getRiddenEntity() {
        PlayerEntity playerEntity = this.getCameraPlayer();
        if(playerEntity != null) {
            Entity entity = playerEntity.getVehicle();
            if(entity == null) {
                return null;
            }

            if(entity instanceof LivingEntity) {
                return (LivingEntity) entity;
            }
        }

        return null;
    }
    
    private void renderHealthBar(DrawContext context, Random random, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking) {
        HeartTypeNew heartType = HeartTypeNew.fromPlayerState(player);
        int i = 9 * (player.getWorld().getLevelProperties().isHardcore() ? 5 : 0);
        int j = MathHelper.ceil((double)((double)maxHealth / 2.0));
        int k = MathHelper.ceil((double)((double)absorption / 2.0));
        int l = j * 2;
        for (int m = j + k - 1; m >= 0; --m) {
            boolean bl3;
            int s;
            boolean bl;
            int n = m / 10;
            int o = m % 10;
            int p = x + o * 8;
            int q = y - n * lines;
            if (lastHealth + absorption <= 4) {
                q += random.nextInt(2);
            }
            if (m < j && m == regeneratingHeartIndex) {
                q -= 2;
            }
            this.drawHeart(context, HeartTypeNew.CONTAINER, p, q, i, blinking, false);
            int r = m * 2;
            bl = m >= j;
            if (bl && (s = r - l) < absorption) {
                boolean bl22 = s + 1 == absorption;
                this.drawHeart(context, heartType == HeartTypeNew.WITHERED ? heartType : HeartTypeNew.ABSORBING, p, q, i, false, bl22);
            }
            if (blinking && r < health) {
                bl3 = r + 1 == health;
                this.drawHeart(context, heartType, p, q, i, true, bl3);
            }
            if (r >= lastHealth) continue;
            bl3 = r + 1 == lastHealth;
            this.drawHeart(context, heartType, p, q, i, false, bl3);
        }
    }
    
    private void drawHeart(DrawContext context, HeartTypeNew type, int x, int y, int v, boolean blinking, boolean halfHeart) {
        context.drawTexture(ICONS, x, y, type.getU(halfHeart, blinking), v, 9, 9);
    }
   
}
