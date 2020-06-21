package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.lib.gui.override.GuiIngameRPGHud;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementArmorModern extends HudElement {

    public HudElementArmorModern() {
        super(HudElementType.ARMOR, 0, 0, 0, 0, true);
    }

    @Override
    public boolean checkConditions() {
        return GameData.shouldDrawHUD();
    }

    @Override
    public void drawElement(Gui gui, float zLevel, float partialTicks) {
        ScaledResolution res = new ScaledResolution(this.mc);
        int width = res.getScaledWidth();
        int height = res.getScaledHeight();
        int left = width / 2 - 91 + this.settings.getPositionValue(Settings.armor_position)[0];
        int top = height - GuiIngameRPGHud.left_height + 2 + this.settings.getPositionValue(Settings.armor_position)[1];

        int level = GameData.getPlayerArmor();
        if(level > 0) {
            int width2 = 1 + 9 + 2 + GameData.getFontRenderer().getStringWidth(String.valueOf(level)) + 2;
            drawRect(left, top, width2, 10, 0xA0000000);
            GameData.getFontRenderer().drawString(String.valueOf(level), left + 12, top + 2, -1);
            GameData.bindIcons();
            gui.drawTexturedModalRect(left + 1, top + 1, 34, 9, 9, 9);
        }
        GuiIngameRPGHud.left_height += 10;
    }

}
