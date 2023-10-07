package net.spellcraftgaming.rpghud.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.main.RenderOverlay;

@Mixin(InGameHud.class)
public class RenderOverlayMixin {
	
    private static final Identifier ARMOR_EMPTY_TEXTURE = new Identifier("hud/armor_empty");
    private static final Identifier ARMOR_HALF_TEXTURE = new Identifier("hud/armor_half");
    private static final Identifier ARMOR_FULL_TEXTURE = new Identifier("hud/armor_full");
    private static final Identifier FOOD_EMPTY_HUNGER_TEXTURE = new Identifier("hud/food_empty_hunger");
    private static final Identifier FOOD_HALF_HUNGER_TEXTURE = new Identifier("hud/food_half_hunger");
    private static final Identifier FOOD_FULL_HUNGER_TEXTURE = new Identifier("hud/food_full_hunger");
    private static final Identifier FOOD_EMPTY_TEXTURE = new Identifier("hud/food_empty");
    private static final Identifier FOOD_HALF_TEXTURE = new Identifier("hud/food_half");
    private static final Identifier FOOD_FULL_TEXTURE = new Identifier("hud/food_full");
    private static final Identifier AIR_TEXTURE = new Identifier("hud/air");
    private static final Identifier AIR_BURSTING_TEXTURE = new Identifier("hud/air_bursting");
    
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
	                dc.drawGuiTexture(ARMOR_FULL_TEXTURE, x, s, 9, 9);
	            }
	            if (w * 2 + 1 == u) {
	                dc.drawGuiTexture(ARMOR_HALF_TEXTURE, x, s, 9, 9);
	            }
	            if (w * 2 + 1 <= u) continue;
	            dc.drawGuiTexture(ARMOR_EMPTY_TEXTURE, x, s, 9, 9);
	        }
        }
        if(RenderOverlay.shouldRenderVanilla(HudElementType.HEALTH)) {
        	client.getProfiler().swap("health");
        	renderHealthBar(dc, random, playerEntity, m, o, r, v, f, i, j, p, bl);
        }
        LivingEntity livingEntity = this.getRiddenEntity();
        x = this.getHeartCount(livingEntity);
        if(RenderOverlay.shouldRenderVanilla(HudElementType.FOOD)) {
	        if (x == 0) {
	            client.getProfiler().swap("food");
	            for (y = 0; y < 10; ++y) {
	                Identifier identifier3;
	                Identifier identifier2;
	                Identifier identifier;
	                z = o;
	                if (playerEntity.hasStatusEffect(StatusEffects.HUNGER)) {
	                    identifier = FOOD_EMPTY_HUNGER_TEXTURE;
	                    identifier2 = FOOD_HALF_HUNGER_TEXTURE;
	                    identifier3 = FOOD_FULL_HUNGER_TEXTURE;
	                } else {
	                    identifier = FOOD_EMPTY_TEXTURE;
	                    identifier2 = FOOD_HALF_TEXTURE;
	                    identifier3 = FOOD_FULL_TEXTURE;
	                }
	                if (playerEntity.getHungerManager().getSaturationLevel() <= 0.0f && this.ticks % (k * 3 + 1) == 0) {
	                    z += random.nextInt(3) - 1;
	                }
	                aa = n - y * 8 - 9;
	                dc.drawGuiTexture(identifier, aa, z, 9, 9);
	                if (y * 2 + 1 < k) {
	                    dc.drawGuiTexture(identifier3, aa, z, 9, 9);
	                }
	                if (y * 2 + 1 != k) continue;
	                dc.drawGuiTexture(identifier2, aa, z, 9, 9);
	            }
	            t -= 10;
	        }
        }
        if(RenderOverlay.shouldRenderVanilla(HudElementType.AIR)) {
	        client.getProfiler().swap("air");
	        y = playerEntity.getMaxAir();
	        z = Math.min(playerEntity.getAir(), y);
	        if (playerEntity.isSubmergedIn(FluidTags.WATER) || z < y) {
	            int ab = this.getHeartRows(x) - 1;
	            t -= ab * 10;
	            int ac = MathHelper.ceil((double)((double)(z - 2) * 10.0 / (double)y));
	            int ad = MathHelper.ceil((double)((double)z * 10.0 / (double)y)) - ac;
	            for (aa = 0; aa < ac + ad; ++aa) {
	                if (aa < ac) {
	                    dc.drawGuiTexture(AIR_TEXTURE, n - aa * 8 - 9, t, 9, 9);
	                    continue;
	                }
	                dc.drawGuiTexture(AIR_BURSTING_TEXTURE, n - aa * 8 - 9, t, 9, 9);
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
    	ModRPGHud.HeartTypeNew HeartTypeNew = ModRPGHud.HeartTypeNew.fromPlayerState(player);
        boolean bl = player.getWorld().getLevelProperties().isHardcore();
        int i = MathHelper.ceil((double)((double)maxHealth / 2.0));
        int j = MathHelper.ceil((double)((double)absorption / 2.0));
        int k = i * 2;
        for (int l = i + j - 1; l >= 0; --l) {
            boolean bl4;
            int r;
            boolean bl2;
            int m = l / 10;
            int n = l % 10;
            int o = x + n * 8;
            int p = y - m * lines;
            if (lastHealth + absorption <= 4) {
                p += random.nextInt(2);
            }
            if (l < i && l == regeneratingHeartIndex) {
                p -= 2;
            }
            this.drawHeart(context, ModRPGHud.HeartTypeNew.CONTAINER, o, p, bl, blinking, false);
            int q = l * 2;
            bl2 = l >= i;
            if (bl2 && (r = q - k) < absorption) {
                boolean bl32 = r + 1 == absorption;
                this.drawHeart(context, HeartTypeNew == ModRPGHud.HeartTypeNew.WITHERED ? HeartTypeNew : ModRPGHud.HeartTypeNew.ABSORBING, o, p, bl, false, bl32);
            }
            if (blinking && q < health) {
                bl4 = q + 1 == health;
                this.drawHeart(context, HeartTypeNew, o, p, bl, true, bl4);
            }
            if (q >= lastHealth) continue;
            bl4 = q + 1 == lastHealth;
            this.drawHeart(context, HeartTypeNew, o, p, bl, false, bl4);
        }
    }
    
    private void drawHeart(DrawContext context, ModRPGHud.HeartTypeNew type, int x, int y, boolean hardcore, boolean blinking, boolean half) {
        context.drawGuiTexture(type.getTexture(hardcore, half, blinking), x, y, 9, 9);
    }
}
