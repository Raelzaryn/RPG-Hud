package net.spellcraftgaming.rpghud.gui.hud.element.extended;

import net.minecraft.client.gui.Gui;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementHealthExtended extends HudElement {

    public HudElementHealthExtended() {
        super(HudElementType.HEALTH, 0, 0, 0, 0, true);
        parent = HudElementType.WIDGET;
    }

    @Override
    public boolean checkConditions() {
        return GameData.shouldDrawHUD();
    }

    @Override
    public void drawElement(Gui gui, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        int health = GameData.getPlayerHealth();
        int absorption = GameData.getPlayerAbsorption();
        int healthMax = GameData.getPlayerMaxHealth();
        int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 49 : 25) + this.settings.getPositionValue(Settings.health_position)[0];
        int posY = (this.settings.getBoolValue(Settings.render_player_face) ? 9 : 5) + this.settings.getPositionValue(Settings.health_position)[1];

        if(absorption > 1)
            drawCustomBar(posX, posY, 110, 12, (double) (health + absorption) / (double) (healthMax + absorption) * 100D, -1, -1,
                    this.settings.getIntValue(Settings.color_absorption), offsetColorPercent(this.settings.getIntValue(Settings.color_absorption), OFFSET_PERCENT));

        if(GameData.isPlayerPoisoned()) {
            drawCustomBar(posX, posY, 110, 12, (double) health / (double) (healthMax + absorption) * 100D, -1, -1, this.settings.getIntValue(Settings.color_poison),
                    offsetColorPercent(this.settings.getIntValue(Settings.color_poison), OFFSET_PERCENT));
        } else if(GameData.isPlayerWithering()) {
            drawCustomBar(posX, posY, 110, 12, (double) health / (double) (healthMax + absorption) * 100D, -1, -1, this.settings.getIntValue(Settings.color_wither),
                    offsetColorPercent(this.settings.getIntValue(Settings.color_wither), OFFSET_PERCENT));
        } else {
            drawCustomBar(posX, posY, 110, 12, (double) health / (double) (healthMax + absorption) * 100D, -1, -1, this.settings.getIntValue(Settings.color_health),
                    offsetColorPercent(this.settings.getIntValue(Settings.color_health), OFFSET_PERCENT));
        }
        String stringHealth = this.settings.getBoolValue(Settings.health_percentage) ? (int) Math.floor((double) health / (double) healthMax * 100) + "%"
                : (health + absorption) + "/" + healthMax;
        if(this.settings.getBoolValue(Settings.show_numbers_health))
            gui.drawCenteredString(GameData.getFontRenderer(), stringHealth, posX + 55, posY + 2, -1);
    }
}
