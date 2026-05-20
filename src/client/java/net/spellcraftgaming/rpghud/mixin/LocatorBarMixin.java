package net.spellcraftgaming.rpghud.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.contextualbar.LocatorBarRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(value=EnvType.CLIENT)
@Mixin(LocatorBarRenderer.class)
public class LocatorBarMixin {
	
    @Inject(at = @At("HEAD"), method = "extractRenderState", cancellable = true)
    private void extractRenderState(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo info) {
    	info.cancel();
    }
}
