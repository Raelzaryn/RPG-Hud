package net.spellcraftgaming.rpghud.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.BossBarHud;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(value=EnvType.CLIENT)
@Mixin(BossBarHud.class)
public class BossBarHudMixin {

    @Inject(at = @At("HEAD"), method = "render")
    private void renderBarStart(DrawContext context, CallbackInfo into) {
    	if(ModRPGHud.instance.settings.getBoolValue(Settings.shift_boss_bar) && ModRPGHud.instance.settings.getBoolValue(Settings.enable_compass))
    		context.getMatrices().translate(0, 20);
    }
    
    @Inject(at = @At("RETURN"), method = "render")
    private void renderBarEnd(DrawContext context, CallbackInfo into) {
    	if(ModRPGHud.instance.settings.getBoolValue(Settings.shift_boss_bar) && ModRPGHud.instance.settings.getBoolValue(Settings.enable_compass))
    		context.getMatrices().translate(0, -20);
    }
}
