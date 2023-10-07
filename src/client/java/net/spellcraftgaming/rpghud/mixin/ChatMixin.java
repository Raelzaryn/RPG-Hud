package net.spellcraftgaming.rpghud.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.util.math.MatrixStack;
import net.spellcraftgaming.rpghud.gui.hud.HudHotbarWidget;
import net.spellcraftgaming.rpghud.main.ModRPGHud;

@Environment(value=EnvType.CLIENT)
@Mixin(ChatHud.class)
public class ChatMixin {

    @Inject(at = @At("HEAD"), method = "render")
    private void renderChat(CallbackInfo into) {
        if(ModRPGHud.instance.getActiveHud() instanceof HudHotbarWidget) {
        	MatrixStack ms = RenderSystem.getModelViewStack();
        	ms.pop();
        	ms.push();
            ms.translate(0.0F, (float) (MinecraftClient.getInstance().getWindow().getScaledHeight() - 75), 0.0F);
        }
    }

}
