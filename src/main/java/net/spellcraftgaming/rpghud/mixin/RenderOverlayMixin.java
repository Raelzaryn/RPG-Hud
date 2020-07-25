package net.spellcraftgaming.rpghud.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.main.RenderOverlay;

@Mixin(InGameHud.class)
public class RenderOverlayMixin extends DrawableHelper {
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
    private void renderStatusBars(CallbackInfo info) {
        MinecraftClient client = MinecraftClient.getInstance();
        int scaledWidth = client.window.getScaledWidth();
        int scaledHeight = client.window.getScaledHeight();
        Random random = new Random();
        PlayerEntity playerEntity = !(client.getCameraEntity() instanceof PlayerEntity) ? null : (PlayerEntity) client.getCameraEntity();
        if(playerEntity != null) {
            random.setSeed((long) (this.ticks * 312871));
            int m = scaledWidth / 2 - 91;
            int n = scaledWidth / 2 + 91;
            int o = scaledHeight - 39;
            EntityAttributeInstance entityAttributeInstance = playerEntity.getAttributeInstance(EntityAttributes.MAX_HEALTH);
            float f = (float)entityAttributeInstance.getValue();
            int p = MathHelper.ceil(playerEntity.getAbsorptionAmount());
            int q = MathHelper.ceil((f + (float) p) / 2.0F / 10.0F);
            int r = Math.max(10 - (q - 2), 3);
            int s = o - (q - 1) * r - 10;
            int t = o - 10;
            int u = p;

            int z;
            int aa;

            if(RenderOverlay.shouldRenderVanilla(HudElementType.ARMOR)) {
                client.getProfiler().push("armor");
                int v = playerEntity.getArmor();
                for(z = 0; z < 10; ++z) {
                    if(v > 0) {
                        aa = m + z * 8;
                        if(z * 2 + 1 < v) {
                            this.blit(aa, s, 34, 9, 9, 9);
                        }

                        if(z * 2 + 1 == v) {
                            this.blit(aa, s, 25, 9, 9, 9);
                        }

                        if(z * 2 + 1 > v) {
                            this.blit(aa, s, 16, 9, 9, 9);
                        }
                    }
                }
            }
            int ai;
            int ad;
            int ae;
            if(RenderOverlay.shouldRenderVanilla(HudElementType.HEALTH)) {
                client.getProfiler().swap("health");
                int w = -1;
                if(playerEntity.hasStatusEffect(StatusEffects.REGENERATION)) {
                    w = this.ticks % MathHelper.ceil(f + 5.0F);
                }
                int i = MathHelper.ceil(playerEntity.getHealth());
                boolean bl = this.heartJumpEndTick > (long) this.ticks && (this.heartJumpEndTick - (long) this.ticks) / 3L % 2L == 1L;
                long l = Util.getMeasuringTimeMs();
                if(i < this.lastHealthValue && playerEntity.timeUntilRegen > 0) {
                    this.lastHealthCheckTime = l;
                    this.heartJumpEndTick = (long) (this.ticks + 20);
                } else if(i > this.lastHealthValue && playerEntity.timeUntilRegen > 0) {
                    this.lastHealthCheckTime = l;
                    this.heartJumpEndTick = (long) (this.ticks + 10);
                }

                if(l - this.lastHealthCheckTime > 1000L) {
                    this.lastHealthValue = i;
                    this.renderHealthValue = i;
                    this.lastHealthCheckTime = l;
                }

                this.lastHealthValue = i;
                int j = this.renderHealthValue;
                for(z = MathHelper.ceil((f + (float) p) / 2.0F) - 1; z >= 0; --z) {
                    aa = 16;
                    if(playerEntity.hasStatusEffect(StatusEffects.POISON)) {
                        aa += 36;
                    } else if(playerEntity.hasStatusEffect(StatusEffects.WITHER)) {
                        aa += 72;
                    }

                    int ab = 0;
                    if(bl) {
                        ab = 1;
                    }

                    ai = MathHelper.ceil((float) (z + 1) / 10.0F) - 1;
                    ad = m + z % 10 * 8;
                    ae = o - ai * r;
                    if(i <= 4) {
                        ae += random.nextInt(2);
                    }

                    if(u <= 0 && z == w) {
                        ae -= 2;
                    }

                    int af = 0;
                    if(playerEntity.world.getLevelProperties().isHardcore()) {
                        af = 5;
                    }

                    this.blit(ad, ae, 16 + ab * 9, 9 * af, 9, 9);
                    if (bl) {
                       if (z * 2 + 1 < j) {
                          this.blit(ad, ae, aa + 54, 9 * af, 9, 9);
                       }

                       if (z * 2 + 1 == j) {
                          this.blit(ad, ae, aa + 63, 9 * af, 9, 9);
                       }
                    }

                    if (u > 0) {
                       if (u == p && p % 2 == 1) {
                          this.blit(ad, ae, aa + 153, 9 * af, 9, 9);
                          --u;
                       } else {
                          this.blit(ad, ae, aa + 144, 9 * af, 9, 9);
                          u -= 2;
                       }
                    } else {
                       if (z * 2 + 1 < i) {
                          this.blit(ad, ae, aa + 36, 9 * af, 9, 9);
                       }

                       if (z * 2 + 1 == i) {
                          this.blit(ad, ae, aa + 45, 9 * af, 9, 9);
                       }
                    }
                }
            }
            LivingEntity livingEntity = this.getRiddenEntity();
            aa = this.getHeartCount(livingEntity);
            int ah;
            int al;

            if(RenderOverlay.shouldRenderVanilla(HudElementType.FOOD)) {
                HungerManager hungerManager = playerEntity.getHungerManager();
                int k = hungerManager.getFoodLevel();
                if(aa == 0) {
                    client.getProfiler().swap("food");

                    for(ah = 0; ah < 10; ++ah) {
                        ai = o;
                        ad = 16;
                        int ak = 0;
                        if(playerEntity.hasStatusEffect(StatusEffects.HUNGER)) {
                            ad += 36;
                            ak = 13;
                        }

                        if(playerEntity.getHungerManager().getSaturationLevel() <= 0.0F && this.ticks % (k * 3 + 1) == 0) {
                            ai = o + (random.nextInt(3) - 1);
                        }

                        al = n - ah * 8 - 9;
                        this.blit(al, ai, 16 + ak * 9, 27, 9, 9);
                        if (ah * 2 + 1 < k) {
                           this.blit(al, ai, ad + 36, 27, 9, 9);
                        }

                        if (ah * 2 + 1 == k) {
                           this.blit(al, ai, ad + 45, 27, 9, 9);
                        }
                    }

                    t -= 10;
                }
            }
            if(RenderOverlay.shouldRenderVanilla(HudElementType.AIR)) {
                client.getProfiler().swap("air");
                ah = playerEntity.getAir();
                ai = playerEntity.getMaxAir();
                if(playerEntity.isInFluid(FluidTags.WATER) || ah < ai) {
                    ad = this.getHeartRows(aa) - 1;
                    t -= ad * 10;
                    ae = MathHelper.ceil((double) (ah - 2) * 10.0D / (double) ai);
                    al = MathHelper.ceil((double) ah * 10.0D / (double) ai) - ae;

                    for(int ar = 0; ar < ae + al; ++ar) {
                        if(ar < ae) {
                            this.blit(n - ar * 8 - 9, t, 16, 18, 9, 9);
                        } else {
                           this.blit(n - ar * 8 - 9, t, 25, 18, 9, 9);
                        }
                    }
                }
            }
            client.getProfiler().pop();
        }
        ;
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

    @Inject(at = @At("TAIL"), method = "render")
    private void renderHUD(CallbackInfo info) {
        ModRPGHud.instance.overlay.onHudRender(MinecraftClient.getInstance().getTickDelta());
    }
    
    private int getHeartCount(LivingEntity entity) {
        if(entity != null && entity.isLiving()) {
            float f = entity.getMaximumHealth();
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
}
