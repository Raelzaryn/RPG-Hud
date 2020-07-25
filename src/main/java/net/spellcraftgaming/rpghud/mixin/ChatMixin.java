package net.spellcraftgaming.rpghud.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.spellcraftgaming.rpghud.gui.hud.HudHotbarWidget;
import net.spellcraftgaming.rpghud.main.ModRPGHud;

@Mixin(ChatHud.class)
public class ChatMixin {

    @Inject(at = @At("HEAD"), method = "render")
    private void renderChat(CallbackInfo into) {
        if(ModRPGHud.instance.getActiveHud() instanceof HudHotbarWidget) {
            MinecraftClient client = MinecraftClient.getInstance();
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.translatef(0.0F, (float) (client.window.getScaledHeight() - 75), 0.0F);
        }
    }

}
