package net.spellcraftgaming.rpghud.gui.hud.element.extended;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.AbstractGui;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.spellcraftgaming.rpghud.gui.hud.element.vanilla.HudElementClockVanilla;
import net.spellcraftgaming.rpghud.settings.Settings;

@OnlyIn(Dist.CLIENT)
public class HudElementClockExtended extends HudElementClockVanilla {

    public HudElementClockExtended() {
        super();
    }

    @Override
    public boolean checkConditions() {
        return super.checkConditions() && this.settings.getBoolValue(Settings.enable_clock) && !this.mc.gameSettings.showDebugInfo
                && (this.settings.getBoolValue(Settings.enable_immersive_clock) ? this.mc.player.inventory.hasItemStack(new ItemStack(Items.CLOCK)) : true);
    }

    @Override
    public void drawElement(AbstractGui gui, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        int clockColor = 0xFFFFFF;
        if(this.settings.getBoolValue(Settings.enable_clock_color))
            clockColor = this.getClockColor();
        if(this.settings.getBoolValue(Settings.reduce_size))
            GL11.glScaled(0.5D, 0.5D, 0.5D);
        gui.drawString(this.mc.fontRenderer, this.getTime(),
                (this.settings.getBoolValue(Settings.reduce_size) ? 8 : 4) + this.settings.getPositionValue(Settings.clock_position)[0],
                (this.settings.getBoolValue(Settings.reduce_size) ? 124 : 62) + this.settings.getPositionValue(Settings.clock_position)[1], clockColor);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        if(this.settings.getBoolValue(Settings.reduce_size))
            GL11.glScaled(2.0D, 2.0D, 2.0D);
    }

}
