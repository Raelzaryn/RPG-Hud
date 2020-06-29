package net.spellcraftgaming.rpghud.gui.hud.element.hotbar;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.EntityLivingBase;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementHealthMountHotbar extends HudElement {

    public HudElementHealthMountHotbar() {
        super(HudElementType.HEALTH_MOUNT, 0, 0, 0, 0, false);
        parent = HudElementType.WIDGET;
    }

    @Override
    public boolean checkConditions() {
        return GameData.isRidingLivingMount() && GameData.shouldDrawHUD();
    }

    @Override
    public void drawElement(Gui gui, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        int height = scaledHeight + this.settings.getPositionValue(Settings.mount_health_position)[1];
        EntityLivingBase mount = (EntityLivingBase) GameData.getMount();
        int health = (int) Math.ceil(mount.getHealth());
        int healthMax = (int) mount.getMaxHealth();
        int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 49 : 25) + this.settings.getPositionValue(Settings.mount_health_position)[0];
        int offset = GameData.getHotbarWidgetWidthOffset();
        drawCustomBar(posX, height - 56, 200 + offset, 10, (double) health / (double) healthMax * 100.0D, -1, -1, this.settings.getIntValue(Settings.color_health),
                offsetColorPercent(this.settings.getIntValue(Settings.color_health), OFFSET_PERCENT));

        String stringHealth = this.settings.getBoolValue(Settings.mount_health_percentage) ? (int) Math.floor((double) health / (double) healthMax * 100) + "%"
                : health + "/" + healthMax;

        if(this.settings.getBoolValue(Settings.show_numbers_health))
            gui.drawCenteredString(GameData.getFontRenderer(), stringHealth, posX + 100 + offset, height - 55, -1);
    }

}
