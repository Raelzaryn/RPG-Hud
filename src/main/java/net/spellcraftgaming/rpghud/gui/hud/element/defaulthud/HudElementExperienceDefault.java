package net.spellcraftgaming.rpghud.gui.hud.element.defaulthud;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementExperienceDefault extends HudElement {

    public HudElementExperienceDefault() {
        super(HudElementType.EXPERIENCE, 0, 0, 0, 0, true);
    }

    @Override
    public boolean checkConditions() {
        return GameData.shouldDrawHUD();
    }

    @Override
    public void drawElement(Gui gui, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        int exp = GameData.getPlayerXP();
        int expCap = GameData.getPlayerXPCap();
        double full = 100D / expCap;
        int posX = this.settings.getPositionValue(Settings.experience_position)[0];
        int posY = this.settings.getPositionValue(Settings.experience_position)[1];
        GlStateManager.disableLighting();
        drawCustomBar(posX, scaledHeight - 10 + posY, scaledWidth, 10, exp * full, this.settings.getIntValue(Settings.color_experience),
                offsetColorPercent(this.settings.getIntValue(Settings.color_experience), 25));

        String stringExp = this.settings.getBoolValue(Settings.experience_percentage)
                ? (int) Math.floor((double) exp / (double) GameData.getPlayerXPCap() * 100) + "%"
                : exp + "/" + GameData.getPlayerXPCap();

        int var7 = scaledWidth / 2;
        if(this.settings.getBoolValue(Settings.show_numbers_experience))
            gui.drawCenteredString(GameData.getFontRenderer(), stringExp, var7 + posX, scaledHeight - 9 + posY, -1);
    }

}
