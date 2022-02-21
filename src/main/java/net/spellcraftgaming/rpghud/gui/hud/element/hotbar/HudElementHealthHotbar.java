package net.spellcraftgaming.rpghud.gui.hud.element.hotbar;

import net.minecraft.client.gui.Gui;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementHealthHotbar extends HudElement {

    public HudElementHealthHotbar() {
        super(HudElementType.HEALTH, 0, 0, 0, 0, true);
        parent = HudElementType.WIDGET;
    }

    @Override
    public boolean checkConditions() {
        return GameData.shouldDrawHUD() && !(GameData.isRidingLivingMount());
    }

    @Override
    public void drawElement(Gui gui, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        int height = scaledHeight + this.settings.getPositionValue(Settings.health_position)[1];
        int health = GameData.getPlayerHealth();
        int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 49 : 25) + this.settings.getPositionValue(Settings.health_position)[0];
        int healthMax = GameData.getPlayerMaxHealth();
        int absorption = GameData.getPlayerAbsorption();
        int offset = GameData.getHotbarWidgetWidthOffset();

        if (absorption > 1)
            drawCustomBar(posX, height - 56, 200 + offset, 10, (double) (health + absorption) / (double) (healthMax + absorption) * 100D, -1, -1,
                    this.settings.getIntValue(Settings.color_absorption), offsetColorPercent(this.settings.getIntValue(Settings.color_absorption), OFFSET_PERCENT));

        if (GameData.isPlayerPoisoned()) {
            drawCustomBar(posX, height - 56, 200 + offset, 10, (double) health / (double) (healthMax + absorption) * 100D, -1, -1,
                    this.settings.getIntValue(Settings.color_poison), offsetColorPercent(this.settings.getIntValue(Settings.color_poison), OFFSET_PERCENT));
        } else if (GameData.isPlayerWithering()) {
            drawCustomBar(posX, height - 56, 200 + offset, 10, (double) health / (double) (healthMax + absorption) * 100D, -1, -1,
                    this.settings.getIntValue(Settings.color_wither), offsetColorPercent(this.settings.getIntValue(Settings.color_wither), OFFSET_PERCENT));
        } else {
            drawCustomBar(posX, height - 56, 200 + offset, 10, (double) health / (double) (healthMax + absorption) * 100D, -1, -1,
                    this.settings.getIntValue(Settings.color_health), offsetColorPercent(this.settings.getIntValue(Settings.color_health), OFFSET_PERCENT));
        }

        String stringHealth = this.settings.getBoolValue(Settings.health_percentage) ? (int) Math.floor((double) health / (double) healthMax * 100) + "%"
                : (health + absorption) + "/" + healthMax;
        if (this.settings.getBoolValue(Settings.show_numbers_health))
            gui.drawCenteredString(GameData.getFontRenderer(), stringHealth, posX + 100 + (offset / 2), height - 55, -1);
    }
}
