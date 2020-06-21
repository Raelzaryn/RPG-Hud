package net.spellcraftgaming.rpghud.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiScreenSettings extends GuiScreen {

    GuiScreen parent;

    public GuiScreenSettings(GuiScreen parent) {
        this.parent = parent;
    }

    @Override
    protected void initGui() {
        super.initGui();
        this.addButton(new GuiButton(100, this.width / 2 - 100, this.height / 6 + 168, I18n.format("gui.done", new Object[0])) {
            @Override
            public void onClick(double mouseX, double mouseY) {
                GuiScreenSettings.this.mc.displayGuiScreen(GuiScreenSettings.this.parent);
            }
        });
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, I18n.format("gui.rpg.settings", new Object[0]), this.width / 2, 12, 16777215);
        super.render(mouseX, mouseY, partialTicks);
    }

    @Override
    public void tick() {
        super.tick();
    }
}
