package net.spellcraftgaming.rpghud.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.hud.InGameHud;

@Mixin(InGameHud.class)
public class RenderOverlayMixin {
    
    @Inject(at = @At("HEAD"), method = "renderStatusBars")
    private void test(CallbackInfo info) {
        return;
    }

}
