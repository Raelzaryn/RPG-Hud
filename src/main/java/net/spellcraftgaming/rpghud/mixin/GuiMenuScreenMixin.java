package net.spellcraftgaming.rpghud.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.spellcraftgaming.rpghud.gui.GuiSettingsMod;

@Mixin(GameMenuScreen.class)
public abstract class GuiMenuScreenMixin extends Screen{
    
    protected GuiMenuScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("TAIL"), method = "init")
    private void addModConfigButton(CallbackInfo info) {
        MinecraftClient mc = MinecraftClient.getInstance();
        String s = new TranslatableText("name.rpghud").asString();
        this.addButton(new ButtonWidget(this.width- mc.textRenderer.getStringWidth(s) - 8, 0, mc.textRenderer.getStringWidth(s.toString()) + 8, 20, s, button -> {
            mc.openScreen(new GuiSettingsMod(this, new TranslatableText("gui.settings.rpghud")));
        }));
    }

}
