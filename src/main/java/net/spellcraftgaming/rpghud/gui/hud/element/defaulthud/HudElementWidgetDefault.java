package net.spellcraftgaming.rpghud.gui.hud.element.defaulthud;

import net.minecraft.client.gui.Gui;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;
import org.lwjgl.opengl.GL11;

public class HudElementWidgetDefault extends HudElement {

    public HudElementWidgetDefault() {
        super(HudElementType.WIDGET, 0, 0, 0, 0, true);
    }

    @Override
    public boolean checkConditions() {
        return GameData.shouldDrawHUD();
    }

    @Override
    public void drawElement(Gui gui, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        bind(INTERFACE);
        int posX = this.settings.getPositionValue(Settings.widget_position)[0];
        int posY = this.settings.getPositionValue(Settings.widget_position)[1];
        gui.drawTexturedModalRect(posX + (this.settings.getBoolValue(Settings.render_player_face) ? 50 : 25),
                posY + (this.settings.getBoolValue(Settings.render_player_face) ? 8 : 0), 0, 0, 114, 35);
        if (GameData.isRidingLivingMount()) {
            gui.drawTexturedModalRect(posX + (this.settings.getBoolValue(Settings.render_player_face) ? 51 : 31),
                    posY + (this.settings.getBoolValue(Settings.render_player_face) ? 39 : 30), 164, 0, 92, 20);
        }

        int facePosX = this.settings.getPositionValue(Settings.face_position)[0];
        int facePosY = this.settings.getPositionValue(Settings.face_position)[1];
        if (this.settings.getBoolValue(Settings.render_player_face)) {
            gui.drawTexturedModalRect(posX + facePosX, posY + facePosY, 114, 0, 50, 50);
            bind(getPlayerSkin(GameData.getPlayer()));
            GL11.glScaled(0.5D, 0.5D, 0.5D);
            gui.drawTexturedModalRect(posX * 2 + 34 + facePosX * 2, posY * 2 + 34 + facePosY * 2, 32, 32, 32, 32);
            gui.drawTexturedModalRect(posX * 2 + 34 + facePosX * 2, posY * 2 + 34 + facePosY * 2, 160, 32, 32, 32);
            GL11.glScaled(2.0D, 2.0D, 2.0D);
            GameData.bindIcons();
        } else {
            gui.drawTexturedModalRect(posX, posY + (this.settings.getBoolValue(Settings.render_player_face) ? 11 : 3), 114, 50, 25, 29);
        }
    }

}
