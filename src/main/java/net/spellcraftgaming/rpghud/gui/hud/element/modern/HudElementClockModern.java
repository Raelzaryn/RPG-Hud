package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.AbstractGui;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.spellcraftgaming.rpghud.gui.hud.element.vanilla.HudElementClockVanilla;
import net.spellcraftgaming.rpghud.settings.Settings;

@OnlyIn(Dist.CLIENT)
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
        return this.settings.getBoolValue(Settings.enable_clock) && !this.mc.gameSettings.showDebugInfo
                && (this.settings.getBoolValue(Settings.enable_immersive_clock) ? this.mc.player.inventory.hasItemStack(new ItemStack(Items.CLOCK)) : true);
    }

    @Override
    public void drawElement(AbstractGui gui, MatrixStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        double scale = getScale();
        RenderSystem.scaled(scale, scale, scale);

        int yOffset = getPosY(scaledHeight);
        int xOffset = getPosX(scaledWidth);
        int clockColor = 0xFFFFFF;

        int width = getWidth(scaledWidth);
        int height = getHeight(scaledHeight);
        if(this.settings.getBoolValue(Settings.enable_clock_color)) {
            clockColor = getClockColor();
        }
        drawRect(xOffset, yOffset, width, height, 0xA0000000);
        gui.func_238471_a_(ms, this.mc.fontRenderer, getTime(), xOffset + (width / 2), yOffset + 2, clockColor);


        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        scale = getInvertedScale();
        RenderSystem.scaled(scale, scale, scale);
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
    public double getScale() {
        return 0.5;
    }
}
