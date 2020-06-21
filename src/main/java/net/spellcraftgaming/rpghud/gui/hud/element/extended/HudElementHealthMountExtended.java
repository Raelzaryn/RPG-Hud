package net.spellcraftgaming.rpghud.gui.hud.element.extended;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementHealthMountExtended extends HudElement {

    public HudElementHealthMountExtended() {
        super(HudElementType.HEALTH_MOUNT, 0, 0, 0, 0, false);
        parent = HudElementType.WIDGET;
    }

    @Override
    public boolean checkConditions() {
        return GameData.isRidingLivingMount() && GameData.shouldDrawHUD();
    }

    @Override
    public void drawElement(Gui gui, float zLevel, float partialTicks) {
        EntityLivingBase mount = (EntityLivingBase) GameData.getMount();
        int health = (int) Math.ceil(mount.getHealth());
        int healthMax = (int) mount.getMaxHealth();
        int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 53 : 25) + this.settings.getPositionValue(Settings.mount_health_position)[0];
        int posY = (this.settings.getBoolValue(Settings.render_player_face) ? 54 : 49) + this.settings.getPositionValue(Settings.mount_health_position)[1];

        drawCustomBar(posX, posY, 88, 8, (double) health / (double) healthMax * 100.0D, -1, -1, this.settings.getIntValue(Settings.color_health),
                offsetColorPercent(this.settings.getIntValue(Settings.color_health), OFFSET_PERCENT));

        String stringHealth = this.settings.getBoolValue(Settings.mount_health_percentage) ? (int) Math.floor((double) health / (double) healthMax * 100) + "%"
                : health + "/" + healthMax;

        if(this.settings.getBoolValue(Settings.show_numbers_health)) {
            GlStateManager.scale(0.5, 0.5, 0.5);
            gui.drawCenteredString(GameData.getFontRenderer(), stringHealth, posX * 2 + 88, posY * 2 + 4, -1);
            GlStateManager.scale(2.0, 2.0, 2.0);
        }
    }

}
