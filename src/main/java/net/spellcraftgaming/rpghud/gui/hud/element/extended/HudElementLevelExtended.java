package net.spellcraftgaming.rpghud.gui.hud.element.extended;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementLevelExtended extends HudElement {

    public HudElementLevelExtended() {
        super(HudElementType.LEVEL, 0, 0, 0, 0, true);
        parent = HudElementType.WIDGET;
    }

    @Override
    public boolean checkConditions() {
        return this.mc.playerController.shouldDrawHUD();
    }

    @Override
    public void drawElement(Gui gui, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        GlStateManager.disableBlend();
        String level = String.valueOf(GameData.getPlayerXPLevel());
        GameData.getFontRenderer().drawStringWithShadow(level,
                (this.settings.getBoolValue(Settings.render_player_face) ? 38 : 13) + this.settings.getPositionValue(Settings.level_position)[0]
                        - GameData.getFontRenderer().getStringWidth(level) / 2,
                (this.settings.getBoolValue(Settings.render_player_face) ? 38 : 18) + this.settings.getPositionValue(Settings.level_position)[1], 0x80FF20);
        GlStateManager.enableBlend();
    }

}
