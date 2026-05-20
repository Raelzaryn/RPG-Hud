package net.spellcraftgaming.rpghud.mixin;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.BossHealthOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(value=EnvType.CLIENT)
@Mixin(BossHealthOverlay.class)
public class BossBarHudMixin {

    @Inject(at = @At("HEAD"), method = "extractRenderState")
    private void extractRenderStateStart(GuiGraphicsExtractor graphics, CallbackInfo into) {
    	if(ModRPGHud.instance.settings.getBoolValue(Settings.shift_boss_bar) && ModRPGHud.instance.settings.getBoolValue(Settings.enable_compass))
            graphics.pose().translate(0, 20);
    }
    
    @Inject(at = @At("RETURN"), method = "extractRenderState")
    private void extractRenderStateEnd(GuiGraphicsExtractor graphics, CallbackInfo into) {
    	if(ModRPGHud.instance.settings.getBoolValue(Settings.shift_boss_bar) && ModRPGHud.instance.settings.getBoolValue(Settings.enable_compass))
            graphics.pose().translate(0, -20);
    }
}
