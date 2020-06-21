package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.tags.FluidTags;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementAirModern extends HudElement {

    public HudElementAirModern() {
        super(HudElementType.AIR, 0, 0, 0, 0, true);
    }

    @Override
    public boolean checkConditions() {
        return (this.mc.player.areEyesInFluid(FluidTags.WATER) || this.mc.player.getAir() < this.mc.player.getMaxAir()) && !this.mc.gameSettings.hideGUI
                && this.mc.playerController.shouldDrawHUD();
    }

    @Override
    public void drawElement(Gui gui, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        int height = scaledHeight + this.settings.getPositionValue(Settings.air_position)[1];
        int airAmount = this.mc.player.getAir();
        int posX = this.settings.getPositionValue(Settings.air_position)[0];
        GlStateManager.disableLighting();
        drawRect(scaledWidth / 2 - 72 + posX, height - 78, 144, 2, 0xA0000000);
        drawRect(scaledWidth / 2 - 72 + posX, height - 70, 144, 2, 0xA0000000);
        drawRect(scaledWidth / 2 - 72 + posX, height - 76, 2, 6, 0xA0000000);
        drawRect(scaledWidth / 2 + 70 + posX, height - 76, 2, 6, 0xA0000000);
        drawRect(scaledWidth / 2 - 70 + posX, height - 76, 140, 6, 0x20FFFFFF);
        drawRect(scaledWidth / 2 - 70 + posX, height - 76, (int) (140 * (airAmount / 300.0D)), 6, this.settings.getIntValue(Settings.color_air));
    }
}
