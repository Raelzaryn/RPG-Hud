package net.spellcraftgaming.rpghud.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ChatComponent;
import net.spellcraftgaming.rpghud.gui.hud.HudHotbarWidget;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import org.joml.Matrix4fStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(value = EnvType.CLIENT)
@Mixin(ChatComponent.class)
public class ChatMixin {

	@Inject(at = @At("HEAD"), method = "extractRenderState")
	private void extractRenderStateChat(CallbackInfo into) {
		if(ModRPGHud.instance.getActiveHud() instanceof HudHotbarWidget) {
			Matrix4fStack ms = RenderSystem.getModelViewStack();
			ms.popMatrix();
			ms.pushMatrix();
			ms.translate(0.0F, (float) (Minecraft.getInstance().getWindow().getGuiScaledHeight() - 75), 0.0F);
		}
	}
}
