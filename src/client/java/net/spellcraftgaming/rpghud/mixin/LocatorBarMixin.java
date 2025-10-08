package net.spellcraftgaming.rpghud.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.hud.bar.LocatorBar;

@Environment(value=EnvType.CLIENT)
@Mixin(LocatorBar.class)
public class LocatorBarMixin {
	
    @Inject(at = @At("HEAD"), method = "renderAddons", cancellable = true)
    private void renderAddons(CallbackInfo info) {
    	info.cancel();
    }
}
