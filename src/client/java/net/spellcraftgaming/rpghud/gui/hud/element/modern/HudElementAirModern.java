package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.tags.FluidTags;
import net.spellcraftgaming.rpghud.RPGHudUtils;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(value=EnvType.CLIENT)
public class HudElementAirModern extends HudElement {

    public HudElementAirModern() {
        super(HudElementType.AIR, 0, 0, 0, 0, true);
    }

    @Override
    public boolean checkConditions() {
        return (this.mc.player.isEyeInFluid(FluidTags.WATER) || this.mc.player.getAirSupply() < this.mc.player.getMaxAirSupply()) && RPGHudUtils.isSurvival();
    }

    @Override
    public void drawElement(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, int scaledWidth, int scaledHeight) {
        float scale = getScale();
        graphics.pose().scale(scale, scale);

        int airAmount = this.mc.player.getAirSupply();
        double maxAir = this.mc.player.getMaxAirSupply();
        if(airAmount < 0)
            airAmount = 0;
        int x = getPosX(scaledWidth);
        int y = getPosY(scaledHeight);
        int x2 = getWidth(scaledWidth);
        int y2 = getHeight(scaledHeight);
        drawRect(graphics, x, y, x2, 2, 0xA0000000);
        drawRect(graphics, x, y + y2 - 2, x2, 2, 0xA0000000);
        drawRect(graphics, x, y + 2, 2, y2 - 4, 0xA0000000);
        drawRect(graphics, x + x2 - 2, y + 2, 2, y2 - 4, 0xA0000000);
        drawRect(graphics, x + 2, y + 2, x2 - 4, y2 - 4, 0x20FFFFFF);
        drawRect(graphics, x + 2, y + 2, (int) ((x2 - 4) * (airAmount / maxAir)), y2 - 4, this.settings.getIntValue(Settings.color_air));

        scale = getInvertedScale();
        graphics.pose().scale(scale, scale);
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
