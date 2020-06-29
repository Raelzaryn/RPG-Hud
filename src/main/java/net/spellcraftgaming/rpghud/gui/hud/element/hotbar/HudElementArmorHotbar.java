package net.spellcraftgaming.rpghud.gui.hud.element.hotbar;

import net.minecraft.client.gui.Gui;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementArmorHotbar extends HudElement {

    public HudElementArmorHotbar() {
        super(HudElementType.ARMOR, 0, 0, 0, 0, true);
    }

    @Override
    public boolean checkConditions() {
        return GameData.shouldDrawHUD();
    }

    @Override
    public void drawElement(Gui gui, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        int left = (this.settings.getBoolValue(Settings.render_player_face) ? 46 : 22) + this.settings.getPositionValue(Settings.armor_position)[0];
        int top = scaledHeight - 64 + this.settings.getPositionValue(Settings.armor_position)[1];
        int offset = GameData.getHotbarWidgetWidthOffset() / 2;
        int level = GameData.getPlayerArmor();
        for(int i = 1; level > 0 && i < 20; i += 2) {
            if(i < level) {
                gui.drawTexturedModalRect(left + 62 + offset, top - 2, 34, 9, 9, 9);
            } else if(i == level) {
                gui.drawTexturedModalRect(left + 62 + offset, top - 2, 25, 9, 9, 9);
            } else if(i > level) {
                gui.drawTexturedModalRect(left + 62 + offset, top - 2, 16, 9, 9, 9);
            }
            left += 8;
        }
    }
    
}
