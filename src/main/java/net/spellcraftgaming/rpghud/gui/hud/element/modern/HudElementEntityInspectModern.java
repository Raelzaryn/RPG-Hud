package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLiving;
import net.spellcraftgaming.rpghud.gui.hud.element.vanilla.HudElementEntityInspectVanilla;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementEntityInspectModern extends HudElementEntityInspectVanilla {

    @Override
    public void drawElement(Gui gui, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        EntityLiving focused = getFocusedEntity(this.mc.player);
        if(focused != null) {
            int posX = (scaledWidth / 2) + this.settings.getPositionValue(Settings.inspector_position)[0];
            int posY = this.settings.getPositionValue(Settings.inspector_position)[1];

            drawRect(posX - 62, 20 + posY, 32, 32, 0xA0000000);
            drawRect(posX - 60, 22 + posY, 28, 28, 0x20FFFFFF);
            drawRect(posX - 30, 20 + posY, 90, 12, 0xA0000000);
            this.drawTetragon(posX - 30, posX - 30, 32 + posY, 32 + posY, 90, 76, 10, 10, 0xA0000000);
            this.drawTetragon(posX - 30, posX - 30, 33 + posY, 33 + posY, 84, 74, 6, 6, 0x20FFFFFF);

            this.drawTetragon(posX - 30, posX - 30, 33 + posY, 33 + posY, (int) (84 * ((double) focused.getHealth() / (double) focused.getMaxHealth())),
                    (int) (84 * ((double) focused.getHealth() / (double) focused.getMaxHealth())) - 10, 6, 6, this.settings.getIntValue(Settings.color_health));

            String stringHealth = ((double) Math.round(focused.getHealth() * 10)) / 10 + "/" + ((double) Math.round(focused.getMaxHealth() * 10)) / 10;

            GlStateManager.scaled(0.5, 0.5, 0.5);
            gui.drawCenteredString(this.mc.fontRenderer, stringHealth, (posX - 29 + 44) * 2, (34 + posY) * 2, -1);
            GlStateManager.scaled(2.0, 2.0, 2.0);

            int x = (posX - 29 + 44 - this.mc.fontRenderer.getStringWidth(focused.getName().getString()) / 2);
            int y = 23 + posY;
            this.mc.fontRenderer.drawString(focused.getName().getString(), x, y, -1);

            drawEntityOnScreen(posX - 60 + 14, 22 + 25 + posY, focused);
            
            if(settings.getBoolValue(Settings.show_entity_armor)) {
                int armor = focused.getTotalArmorValue();
                if(armor > 0) {
                    this.mc.getTextureManager().bindTexture(Gui.ICONS);
                    String value = String.valueOf(armor);
                    drawRect(posX - 30, posY + 42, 8 + (mc.fontRenderer.getStringWidth(value) / 2), 6, 0xA0000000);
                    GlStateManager.scaled(0.5, 0.5, 0.5);
                    gui.drawTexturedModalRect((posX - 30) * 2, (posY + 42) * 2, 34, 9, 9, 9);
                    this.mc.fontRenderer.drawString(value, (posX - 24) * 2, (posY + 42) * 2 + 1, -1);
                    GlStateManager.scaled(2.0, 2.0, 2.0);
                }
            }
        }
    }

}
