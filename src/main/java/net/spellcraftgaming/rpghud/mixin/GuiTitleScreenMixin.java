package net.spellcraftgaming.rpghud.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.spellcraftgaming.rpghud.gui.GuiSettingsMod;

@Mixin(TitleScreen.class)
public abstract class GuiTitleScreenMixin extends Screen{
    
    protected GuiTitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("TAIL"), method = "init")
    private void addModConfigButton(CallbackInfo info) {
        MinecraftClient mc = MinecraftClient.getInstance();
        Text s = new TranslatableText("name.rpghud");
        this.addButton(new ButtonWidget(this.width- mc.textRenderer.getWidth(s.getString()) - 8, 0, mc.textRenderer.getWidth(s.getString()) + 8, 20, s, button -> {
            mc.openScreen(new GuiSettingsMod(this, new TranslatableText("gui.settings.rpghud")));
        }));
    }

}
