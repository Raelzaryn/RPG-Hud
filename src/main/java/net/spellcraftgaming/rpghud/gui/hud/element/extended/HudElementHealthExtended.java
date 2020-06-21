package net.spellcraftgaming.rpghud.gui.hud.element.extended;

import net.minecraft.client.gui.AbstractGui;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.MathHelper;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementHealthExtended extends HudElement {

    public HudElementHealthExtended() {
        super(HudElementType.HEALTH, 0, 0, 0, 0, true);
        this.parent = HudElementType.WIDGET;
    }

    @Override
    public boolean checkConditions() {
        return !this.mc.gameSettings.hideGUI && this.mc.playerController.shouldDrawHUD();
    }

    @Override
    public void drawElement(AbstractGui gui, float zLevel, float partialTicks, int scaledHeight, int scaledWidth) {
        int health = MathHelper.ceil(this.mc.player.getHealth());
        int absorption = MathHelper.ceil(this.mc.player.getAbsorptionAmount());
        int healthMax = MathHelper.ceil(this.mc.player.getMaxHealth());
        int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 49 : 25) + this.settings.getPositionValue(Settings.health_position)[0];
        int posY = (this.settings.getBoolValue(Settings.render_player_face) ? 9 : 5) + this.settings.getPositionValue(Settings.health_position)[1];

        if(absorption > 1)
            drawCustomBar(posX, posY, 110, 12, (double) (health + absorption) / (double) (healthMax + absorption) * 100D, -1, -1,
                    this.settings.getIntValue(Settings.color_absorption), offsetColorPercent(this.settings.getIntValue(Settings.color_absorption), OFFSET_PERCENT));

        if(this.mc.player.isPotionActive(Effects.POISON))
            drawCustomBar(posX, posY, 110, 12, (double) health / (double) (healthMax + absorption) * 100D, -1, -1, this.settings.getIntValue(Settings.color_poison),
                    offsetColorPercent(this.settings.getIntValue(Settings.color_poison), OFFSET_PERCENT));
        else if(this.mc.player.isPotionActive(Effects.WITHER))
            drawCustomBar(posX, posY, 110, 12, (double) health / (double) (healthMax + absorption) * 100D, -1, -1, this.settings.getIntValue(Settings.color_wither),
                    offsetColorPercent(this.settings.getIntValue(Settings.color_wither), OFFSET_PERCENT));
        else
            drawCustomBar(posX, posY, 110, 12, (double) health / (double) (healthMax + absorption) * 100D, -1, -1, this.settings.getIntValue(Settings.color_health),
                    offsetColorPercent(this.settings.getIntValue(Settings.color_health), OFFSET_PERCENT));
        String stringHealth = this.settings.getBoolValue(Settings.health_percentage) ? (int) Math.floor((double) health / (double) healthMax * 100) + "%"
                : (health + absorption) + "/" + healthMax;
        if(this.settings.getBoolValue(Settings.show_numbers_health))
            gui.drawCenteredString(this.mc.fontRenderer, stringHealth, posX + 55, posY + 2, -1);
    }
}
