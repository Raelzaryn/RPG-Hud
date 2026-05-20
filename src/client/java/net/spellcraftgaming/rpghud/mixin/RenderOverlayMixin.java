package net.spellcraftgaming.rpghud.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.metrics.profiling.ProfilerSamplerAdapter;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.main.RenderOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Environment(value=EnvType.CLIENT)
@Mixin(Gui.class)
public abstract class RenderOverlayMixin {
	
    private static final Identifier ARMOR_EMPTY_TEXTURE = Identifier.withDefaultNamespace("hud/armor_empty");
    private static final Identifier ARMOR_HALF_TEXTURE = Identifier.withDefaultNamespace("hud/armor_half");
    private static final Identifier ARMOR_FULL_TEXTURE = Identifier.withDefaultNamespace("hud/armor_full");
    private static final Identifier FOOD_EMPTY_HUNGER_TEXTURE =Identifier.withDefaultNamespace("hud/food_empty_hunger");
    private static final Identifier FOOD_HALF_HUNGER_TEXTURE = Identifier.withDefaultNamespace("hud/food_half_hunger");
    private static final Identifier FOOD_FULL_HUNGER_TEXTURE = Identifier.withDefaultNamespace("hud/food_full_hunger");
    private static final Identifier FOOD_EMPTY_TEXTURE = Identifier.withDefaultNamespace("hud/food_empty");
    private static final Identifier FOOD_HALF_TEXTURE = Identifier.withDefaultNamespace("hud/food_half");
    private static final Identifier FOOD_FULL_TEXTURE = Identifier.withDefaultNamespace("hud/food_full");
    private static final Identifier AIR_TEXTURE = Identifier.withDefaultNamespace("hud/air");
    private static final Identifier AIR_BURSTING_TEXTURE = Identifier.withDefaultNamespace("hud/air_bursting");
    
    private int lastHealthValue;
    private int renderHealthValue;
    private long lastHealthCheckTime;
    private long heartJumpEndTick;
    private int ticks;

    @Inject(at = @At("HEAD"), method = "tick")
    private void tick(CallbackInfo info) {
        ++this.ticks;
    }

    @Inject(at = @At("HEAD"), method = "extractPlayerHealth", cancellable = true)
    private void extractPlayerHealth(GuiGraphicsExtractor graphics, CallbackInfo info) {
        Minecraft client = Minecraft.getInstance();
        int scaledWidth = client.getWindow().getGuiScaledWidth();
        int scaledHeight = client.getWindow().getGuiScaledHeight();
        Random random = new Random();
        int aa;
        int z;
        int y;
        int x;
        Player playerEntity = this.getCameraPlayer();
        if (playerEntity == null) {
            return;
        }
        int i = Mth.ceil(playerEntity.getHealth());
        boolean bl = this.heartJumpEndTick > (long)this.ticks && (this.heartJumpEndTick - (long)this.ticks) / 3L % 2L == 1L;
        long l = Util.getMillis();
        if (i < this.lastHealthValue && playerEntity.invulnerableTime > 0) {
            this.lastHealthCheckTime = l;
            this.heartJumpEndTick = this.ticks + 20;
        } else if (i > this.lastHealthValue && playerEntity.invulnerableTime > 0) {
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
        FoodData hungerManager = playerEntity.getFoodData();
        int k = hungerManager.getFoodLevel();
        int m = scaledWidth / 2 - 91;
        int n = scaledWidth / 2 + 91;
        int o = scaledHeight - 39;
        float f = Math.max((float)playerEntity.getAttributeValue(Attributes.MAX_HEALTH), (float)Math.max(j, i));
        int p = Mth.ceil(playerEntity.getAbsorptionAmount());
        int q = Mth.ceil((f + (float)p) / 2.0f / 10.0f);
        int r = Math.max(10 - (q - 2), 3);
        int s = o - (q - 1) * r - 10;
        int t = o - 10;
        int u = playerEntity.getArmorValue();
        int v = -1;
        if (playerEntity.hasEffect(MobEffects.REGENERATION)) {
            v = (int) (this.ticks % Math.ceil(f + 5.0f));
        }
        if(RenderOverlay.shouldRenderVanilla(HudElementType.ARMOR)) {
	        Profiler.get().push("armor");
	        for (int w = 0; w < 10; ++w) {
	            if (u <= 0) continue;
	            x = m + w * 8;
	            if (w * 2 + 1 < u) {
	                graphics.blitSprite(RenderPipelines.GUI_TEXTURED, ARMOR_FULL_TEXTURE, x, s, 9, 9);
	            }
	            if (w * 2 + 1 == u) {
	                graphics.blitSprite(RenderPipelines.GUI_TEXTURED, ARMOR_HALF_TEXTURE, x, s, 9, 9);
	            }
	            if (w * 2 + 1 <= u) continue;
	            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, ARMOR_EMPTY_TEXTURE, x, s, 9, 9);
	        }
        }
        if(RenderOverlay.shouldRenderVanilla(HudElementType.HEALTH)) {
        	Profiler.get().popPush("health");
            this.extractHearts(graphics, playerEntity, m, o, r, v, f, i, j, p, bl);
        }
        LivingEntity livingEntity = this.getPlayerVehicleWithHealth();
        x = this.getVehicleMaxHearts(livingEntity);
        if(RenderOverlay.shouldRenderVanilla(HudElementType.FOOD)) {
	        if (x == 0) {
	            Profiler.get().popPush("food");
	            for (y = 0; y < 10; ++y) {
	                Identifier identifier3;
	                Identifier identifier2;
	                Identifier identifier;
	                z = o;
	                if (playerEntity.hasEffect(MobEffects.HUNGER)) {
	                    identifier = FOOD_EMPTY_HUNGER_TEXTURE;
	                    identifier2 = FOOD_HALF_HUNGER_TEXTURE;
	                    identifier3 = FOOD_FULL_HUNGER_TEXTURE;
	                } else {
	                    identifier = FOOD_EMPTY_TEXTURE;
	                    identifier2 = FOOD_HALF_TEXTURE;
	                    identifier3 = FOOD_FULL_TEXTURE;
	                }
	                if (playerEntity.getFoodData().getSaturationLevel() <= 0.0f && this.ticks % (k * 3 + 1) == 0) {
	                    z += random.nextInt(3) - 1;
	                }
	                aa = n - y * 8 - 9;
	                graphics.blitSprite(RenderPipelines.GUI_TEXTURED, identifier, aa, z, 9, 9);
	                if (y * 2 + 1 < k) {
	                    graphics.blitSprite(RenderPipelines.GUI_TEXTURED, identifier3, aa, z, 9, 9);
	                }
	                if (y * 2 + 1 != k) continue;
	                graphics.blitSprite(RenderPipelines.GUI_TEXTURED, identifier2, aa, z, 9, 9);
	            }
	            t -= 10;
	        }
        }
        if(RenderOverlay.shouldRenderVanilla(HudElementType.AIR)) {
	        Profiler.get().popPush("air");
	        y = playerEntity.getMaxAirSupply();
	        z = Math.min(playerEntity.getAirSupply(), y);
	        if (playerEntity.isEyeInFluid(FluidTags.WATER) || z < y) {
	            int ab = this.getVisibleVehicleHeartRows(x) - 1;
	            t -= ab * 10;
	            int ac = Mth.ceil((double)((double)(z - 2) * 10.0 / (double)y));
	            int ad = Mth.ceil((double)((double)z * 10.0 / (double)y)) - ac;
	            for (aa = 0; aa < ac + ad; ++aa) {
	                if (aa < ac) {
	                    graphics.blitSprite(RenderPipelines.GUI_TEXTURED, AIR_TEXTURE, n - aa * 8 - 9, t, 9, 9);
	                    continue;
	                }
	                graphics.blitSprite(RenderPipelines.GUI_TEXTURED, AIR_BURSTING_TEXTURE, n - aa * 8 - 9, t, 9, 9);
	            }
	        }
        }
        Profiler.get().pop();
        info.cancel();
    }


    @Inject(at = @At("HEAD"), method = "extractItemHotbar", cancellable = true)
    private void extractItemHotbar(CallbackInfo info) {
        if(!RenderOverlay.shouldRenderVanilla(HudElementType.HOTBAR))
            info.cancel();
    }

    @Inject(at = @At("HEAD"), method = "extractVehicleHealth", cancellable = true)
    private void extractVehicleHealth(CallbackInfo info) {
        if(!RenderOverlay.shouldRenderVanilla(HudElementType.HEALTH_MOUNT))
            info.cancel();
    }
    
    @Inject(at = @At("HEAD"), method = "extractEffects", cancellable = true)
    private void extractEffects(CallbackInfo info) {
        if(!RenderOverlay.shouldRenderVanilla(HudElementType.STATUS_EFFECTS))
            info.cancel();
    }

    @Shadow
    protected abstract int getVehicleMaxHearts(LivingEntity entity);

    @Shadow
    protected abstract int getVisibleVehicleHeartRows(int heartCount);

    @Shadow
    protected abstract Player getCameraPlayer();

    @Shadow
    protected abstract LivingEntity getPlayerVehicleWithHealth();

    @Shadow
    protected abstract void extractHearts(GuiGraphicsExtractor graphics, Player player, int xLeft, int yLineBase, int healthRowHeight, int heartOffsetIndex, float maxHealth, int currentHealth, int oldHealth, int absorption, boolean blink);
}
