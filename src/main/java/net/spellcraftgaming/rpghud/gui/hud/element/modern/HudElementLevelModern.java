package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.rpghud.gui.hud.HudModern;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;
import org.lwjgl.opengl.GL11;

public class HudElementLevelModern extends HudElement {

    public HudElementLevelModern() {
        super(HudElementType.LEVEL, 0, 0, 0, 0, true);
        parent = HudElementType.WIDGET;
    }

    @Override
    public boolean checkConditions() {
        return GameData.shouldDrawHUD();
    }

    @Override
    public void drawElement(Gui gui, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        String level = String.valueOf(GameData.getPlayerXPLevel());

        int xOffset = ((HudModern) this.rpgHud.huds.get("modern")).getPosX();
        int width = GameData.getFontRenderer().getStringWidth(level);
        if (width < 16)
            width = 16;
        if (width < xOffset)
            width = xOffset;
        else
            ((HudModern) this.rpgHud.huds.get("modern")).setPosX(width);

        if (GameData.getFontRenderer().getStringWidth(level) > (width + 2))
            width = GameData.getFontRenderer().getStringWidth(level) + 2;

        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();

        int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 23 : 2) + this.settings.getPositionValue(Settings.level_position)[0];
        int posY = ((this.settings.getBoolValue(Settings.show_numbers_health) && this.settings.getBoolValue(Settings.show_numbers_food)) ? 22 : 26)
                + this.settings.getPositionValue(Settings.level_position)[1];

        if (this.settings.getStringValue(Settings.clock_time_format) == "time.24" || !this.settings.getBoolValue(Settings.render_player_face)) {
            drawRect(posX, posY, width, 7, 0xA0000000);
        } else {
            drawRect(26 + this.settings.getPositionValue(Settings.level_position)[0], posY, width, 7, 0xA0000000);
        }
        GL11.glScaled(0.5D, 0.5D, 0.5D);

        if (this.settings.getStringValue(Settings.clock_time_format) == "time.24" || !this.settings.getBoolValue(Settings.render_player_face)) {
            gui.drawCenteredString(GameData.getFontRenderer(), level, (posX * 2) + width, posY * 2 + 3, 0x80FF20);
        } else {
            gui.drawCenteredString(GameData.getFontRenderer(), level, 70 + this.settings.getPositionValue(Settings.level_position)[0] * 2, posY * 2 + 3, 0x80FF20);
        }
        GL11.glScaled(2.0D, 2.0D, 2.0D);
        GlStateManager.enableBlend();
    }

}
