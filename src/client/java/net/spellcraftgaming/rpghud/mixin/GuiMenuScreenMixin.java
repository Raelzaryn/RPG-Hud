package net.spellcraftgaming.rpghud.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.spellcraftgaming.rpghud.gui.GuiSettingsMod;
import net.spellcraftgaming.rpghud.main.ModRPGHud;

@Environment(value=EnvType.CLIENT)
@Mixin(GameMenuScreen.class)
public abstract class GuiMenuScreenMixin extends Screen{
    
    protected GuiMenuScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("TAIL"), method = "init")
    private void addModConfigButton(CallbackInfo info) {
        MinecraftClient mc = MinecraftClient.getInstance();
        Text s = Text.translatable("name.rpghud");
        this.addDrawableChild(ButtonWidget.builder(s, button -> {
            mc.setScreen(new GuiSettingsMod(this, Text.translatable("gui.settings.rpghud")));
        }).dimensions(this.width- mc.textRenderer.getWidth(s.getString()) - 8, ModRPGHud.screenOffset, mc.textRenderer.getWidth(s.getString()) + 8, 20).build());
    }

}
