package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementExperienceModern extends HudElement {

    public HudElementExperienceModern() {
        super(HudElementType.EXPERIENCE, 0, 0, 0, 0, true);
    }

    @Override
    public boolean checkConditions() {
        return GameData.shouldDrawHUD();
    }

    @Override
    public void drawElement(Gui gui, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        int exp = GameData.getPlayerXP();
        double full = ((double) (scaledWidth - 2)) / GameData.getPlayerXPCap();
        int posX = this.settings.getPositionValue(Settings.experience_position)[0];
        int posY = this.settings.getPositionValue(Settings.experience_position)[1];

        drawRect(posX, scaledHeight - 7 + posY, scaledWidth, 7, 0xA0000000);
        drawRect(1 + posX, scaledHeight - 6 + posY, (int) (exp * full), 4, this.settings.getIntValue(Settings.color_experience));

        String stringExp = this.settings.getBoolValue(Settings.experience_percentage)
                ? (int) Math.floor((double) exp / (double) GameData.getPlayerXPCap() * 100) + "%"
                : exp + "/" + GameData.getPlayerXPCap();

        if(this.settings.getBoolValue(Settings.show_numbers_experience)) {
            int width2 = GameData.getFontRenderer().getStringWidth(stringExp) / 2;
            drawRect(1 + posX, scaledHeight - 15 + posY, width2 + 4, 8, 0xA0000000);
            GlStateManager.scale(0.5D, 0.5D, 0.5D);
            gui.drawCenteredString(GameData.getFontRenderer(), stringExp, 6 + width2 + posX * 2, (scaledHeight - 12) * 2 - 1 + posY * 2, -1);
            GlStateManager.scale(2.0D, 2.0D, 2.0D);
        }
    }

}
