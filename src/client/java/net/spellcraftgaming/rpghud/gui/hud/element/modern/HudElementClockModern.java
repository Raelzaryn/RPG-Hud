package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.spellcraftgaming.rpghud.gui.hud.element.vanilla.HudElementClockVanilla;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(value=EnvType.CLIENT)
public class HudElementClockModern extends HudElementClockVanilla {

    public HudElementClockModern() {
        super();
        this.posX = 0;
        this.posY = 0;
        this.elementWidth = 0;
        this.elementHeight = 0;
        this.moveable = true;
    }

    @Override
    public boolean checkConditions() {
        return this.settings.getBoolValue(Settings.enable_clock) && !this.mc.options.debugEnabled
                && (this.settings.getBoolValue(Settings.enable_immersive_clock) ? this.mc.player.getInventory().contains(new ItemStack(Items.CLOCK)) : true);
    }

    @Override
    public void drawElement(DrawContext dc, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        float scale = getScale();
        dc.getMatrices().scale(scale, scale, scale);
        int yOffset = getPosY(scaledHeight);
        int xOffset = getPosX(scaledWidth);
        int clockColor = 0xFFFFFF;

        int width = getWidth(scaledWidth);
        int height = getHeight(scaledHeight);
        if(this.settings.getBoolValue(Settings.enable_clock_color)) {
            clockColor = getClockColor();
        }
        drawRect(dc, xOffset, yOffset, width, height, 0xA0000000);
        dc.drawCenteredTextWithShadow( this.mc.textRenderer, getTime(), xOffset + (width / 2), yOffset + 2, clockColor);

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        scale = getInvertedScale();
        dc.getMatrices().scale(scale, scale, scale);
    }

    @Override
    public int getPosX(int scaledWidth) {
        return (int) (this.settings.getPositionValue(Settings.clock_position)[0] + ((2 + this.settings.getStringValue(Settings.clock_time_format) == "time.24" ? 0 :2)*getInvertedScale()));
    }

    @Override
    public int getPosY(int scaledHeight) {
        return (int) (((this.settings.getBoolValue(Settings.render_player_face) ? 0 : 8)
                + ((this.settings.getBoolValue(Settings.show_numbers_health) && this.settings.getBoolValue(Settings.show_numbers_food)) ? 0 : 4) + 23) * getInvertedScale()
                + this.settings.getPositionValue(Settings.clock_position)[1]);
    }

    @Override
    public int getWidth(int scaledWidth) {
        return this.settings.getStringValue(Settings.clock_time_format) == "time.24" ? 40 : 46;
    }

    public int getHeight(int scaledHeight) {
        return 12;
    }

    @Override
    public float getScale() {
        return 0.5f;
    }
}
