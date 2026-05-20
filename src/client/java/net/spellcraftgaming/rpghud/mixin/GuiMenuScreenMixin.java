package net.spellcraftgaming.rpghud.mixin;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.spellcraftgaming.rpghud.gui.GuiSettingsMod;
import net.spellcraftgaming.rpghud.main.ModRPGHud;

@Environment(value=EnvType.CLIENT)
@Mixin(PauseScreen.class)
public abstract class GuiMenuScreenMixin extends Screen {
    
    protected GuiMenuScreenMixin(Component title) {
        super(title);
    }

    @Inject(at = @At("TAIL"), method = "init")
    private void addModConfigButton(CallbackInfo info) {
        Minecraft mc = Minecraft.getInstance();
        Component s = Component.translatable("name.rpghud");
        this.addRenderableWidget(Button.builder(s, button -> {
            mc.setScreen(new GuiSettingsMod(this, Component.translatable("gui.settings.rpghud")));
        }).bounds(this.width- mc.font.width(s.getString()) - 8, ModRPGHud.screenOffset, mc.font.width(s.getString()) + 8, 20).build());
    }

}
