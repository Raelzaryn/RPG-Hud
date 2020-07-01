package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.AbstractGui;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@OnlyIn(Dist.CLIENT)
public class HudElementClockVanilla extends HudElement {

    public HudElementClockVanilla() {
        super(HudElementType.CLOCK, 0, 0, 0, 0, true);
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
                (this.settings.getBoolValue(Settings.reduce_size) ? 104 : 52) + this.settings.getPositionValue(Settings.clock_position)[1], clockColor);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        if(this.settings.getBoolValue(Settings.reduce_size))
            GL11.glScaled(2.0D, 2.0D, 2.0D);
    }

    /** Returns the time of the minecraft world as a String */
    public String getTime() {
        long time = this.mc.player.world.getDayTime();
        long day = this.mc.player.world.getDayTime() / 24000L;
        long currentTime = time - (24000L * day);
        long currentHour = (currentTime / 1000L) + 6L;
        double currentTimeMin = currentTime - ((currentHour - 6L) * 1000L);
        currentTimeMin = currentTimeMin / (1000.0D / 60.0D);
        int currentMin = (int) currentTimeMin;
        if(currentHour > 24)
            currentHour -= 24L;
        if(this.settings.getStringValue(Settings.clock_time_format) == "time.24")
            return get24HourTimeForString(currentHour, currentMin);
        return get12HourTimeForString(currentHour, currentMin);
    }

    /**
     * Formats the parameter time into the 24 hour format and returns it as a String
     * 
     * @param currentHour the hour
     * @param currentMin  the minute
     */
    public static String get24HourTimeForString(long currentHour, long currentMin) {
        StringBuilder sb = new StringBuilder();
        if(currentHour == 24)
            currentHour = 0;
        if(currentHour < 10)
            sb.append("0");
        sb.append(currentHour);
        return sb.toString() + ":" + getMinuteForString(currentMin);
    }

    /**
     * Formats the parameter time into the 12 hour format and returns it as a string
     * 
     * @param currentHour the hour
     * @param currentMin  the minute
     */
    public static String get12HourTimeForString(long currentHour, long currentMin) {
        StringBuilder sb = new StringBuilder();
        String period = "am";
        if(currentHour == 12)
            period = "pm";
        if(currentHour == 24) {
            currentHour = 12;
            period = "am";
        }
        if(currentHour > 12) {
            currentHour -= 12;
            period = "pm";
        }
        if(currentHour < 10)
            sb.append(0);
        sb.append(currentHour);
        return sb.toString() + ":" + getMinuteForString(currentMin) + " " + period;
    }

    /**
     * Transforms the minute into a two digit String
     * 
     * @param currentMin the minute
     */
    public static String getMinuteForString(long currentMin) {
        StringBuilder sb = new StringBuilder();
        if(currentMin < 10)
            sb.append("0");
        sb.append(currentMin);
        return sb.toString();
    }

    public int getClockColor() {
        long time = this.mc.player.world.getDayTime();
        long day = this.mc.player.world.getDayTime() / 24000L;
        long currentTime = time - (24000L * day);
        if(currentTime < 1000)
            return 0xFFAF00;
        else if(currentTime < 6000)
            return 0xFFAF00;
        else if(currentTime < 11000)
            return 0xFFCF00;
        else if(currentTime < 12000)
            return 0xFFAF00;
        else if(currentTime < 13000)
            return 0xFFA200;
        else if(currentTime < 13500)
            return 0xE36E21;
        else if(currentTime < 18000)
            return 0x345D74;
        else if(currentTime < 21000)
            return 0x1F3847;
        else if(currentTime < 22250)
            return 0x345D74;
        else if(currentTime < 22500)
            return 0x775D74;
        else if(currentTime < 23000)
            return 0xE36E21;
        else
            return 0xFFA200;
    }

}
