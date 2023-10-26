package net.spellcraftgaming.rpghud.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.spellcraftgaming.rpghud.client.gui.overlay.OverlayElement;
import net.spellcraftgaming.rpghud.client.gui.overlay.OverlayElementType;
import net.spellcraftgaming.rpghud.client.main.RPGHudClient;

@Mixin(InGameHud.class)
public class InGameHudMixin {
	
	@Inject(at = @At("TAIL"), method = "render")
	private void render(DrawContext dc, float tickDelta, CallbackInfo info) {
		for(OverlayElement e : RPGHudClient.currentHud.elements.values()) {
			if(e.type != OverlayElementType.DEBUG) {
				RPGHudClient.currentHud.renderElement(e.type, dc, tickDelta);
				//e.render(dc, tickDelta);
			}
		}
	}
	
    @Inject(at = @At("HEAD"), method = "renderHotbar", cancellable = true)
    private void renderHotbar(CallbackInfo info) {
        info.cancel();
    }
    
    @Inject(at = @At("HEAD"), method = "renderExperienceBar", cancellable = true)
    private void renderExperienceBar(CallbackInfo info) {
        info.cancel();
    }
    
    @Inject(at = @At("HEAD"), method = "renderStatusBars", cancellable = true)
    private void renderStatusBars(CallbackInfo info) {
        info.cancel();
    }
}