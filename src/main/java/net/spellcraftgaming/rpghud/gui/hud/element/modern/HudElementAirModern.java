package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.Gui;
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
        return (this.mc.player.isEyeInFluid(FluidTags.WATER) || this.mc.player.getAirSupply() < this.mc.player.getMaxAirSupply()) && !this.mc.options.hideGui;
    }

    @Override
    public void drawElement(Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        float scale = getScale();
        ms.scale(scale, scale, scale);

        int airAmount = this.mc.player.getAirSupply();
        double maxAir = this.mc.player.getMaxAirSupply();
        if(airAmount < 0)
            airAmount = 0;
        int x = getPosX(scaledWidth);
        int y = getPosY(scaledHeight);
        int x2 = getWidth(scaledWidth);
        int y2 = getHeight(scaledHeight);
        drawRect(ms, x, y, x2, 2, 0xA0000000);
        drawRect(ms, x, y + y2 - 2, x2, 2, 0xA0000000);
        drawRect(ms, x, y + 2, 2, y2 - 4, 0xA0000000);
        drawRect(ms, x + x2 - 2, y + 2, 2, y2 - 4, 0xA0000000);
        drawRect(ms, x + 2, y + 2, x2 - 4, y2 - 4, 0x20FFFFFF);
        drawRect(ms, x + 2, y + 2, (int) ((x2 - 4) * (airAmount / maxAir)), y2 - 4, this.settings.getIntValue(Settings.color_air));

        scale = getInvertedScale();
        ms.scale(scale, scale, scale);
    }

    @Override
    public int getPosX(int scaledWidth) {
        return (int) (((scaledWidth - (getWidth(scaledWidth) / getInvertedScale())) / 2 + this.settings.getPositionValue(Settings.air_position)[0])
                * getInvertedScale());
    }

    @Override
    public int getPosY(int scaledHeight) {
        return (int) ((scaledHeight - 78 + this.settings.getPositionValue(Settings.air_position)[1]) * getInvertedScale());
    }

    @Override
    public int getWidth(int scaledWidth) {
        return 144;
    }

    public int getHeight(int scaledHeight) {
        return 10;
    }

    @Override
    public float getScale() {
        return 1;
    }
}
