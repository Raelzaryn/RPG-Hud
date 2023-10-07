package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.LivingEntity;
import net.spellcraftgaming.rpghud.gui.hud.element.vanilla.HudElementEntityInspectVanilla;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(value=EnvType.CLIENT)
public class HudElementEntityInspectModern extends HudElementEntityInspectVanilla {

    @Override
    public void drawElement(DrawContext dc, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        LivingEntity focused = getFocusedEntity(this.mc.player);
        if(focused != null) {
            int posX = (scaledWidth / 2) + this.settings.getPositionValue(Settings.inspector_position)[0];
            int posY = this.settings.getPositionValue(Settings.inspector_position)[1];
            float health = focused.getHealth();
            float maxHealth = focused.getMaxHealth();
            if(health > maxHealth) health = maxHealth;
            drawRect(dc, posX - 62, 20 + posY, 32, 32, 0xA0000000);
            drawRect(dc, posX - 60, 22 + posY, 28, 28, 0x20FFFFFF);
            drawRect(dc, posX - 30, 20 + posY, 90, 12, 0xA0000000);
            drawTetragon(posX - 30, posX - 30, 32 + posY, 32 + posY, 90, 76, 10, 10, 0xA0000000);
            drawTetragon(posX - 30, posX - 30, 33 + posY, 33 + posY, 84, 74, 6, 6, 0x20FFFFFF);

            drawTetragon(posX - 30, posX - 30, 33 + posY, 33 + posY, (int) (84 * ((double) health / (double) maxHealth)),
                    (int) (84 * ((double) health / (double) maxHealth)) - 10, 6, 6, this.settings.getIntValue(Settings.color_health));

            String stringHealth = ((double) Math.round(health * 10)) / 10 + "/" + ((double) Math.round(maxHealth * 10)) / 10;

            dc.getMatrices().scale(0.5f, 0.5f, 0.5f);
            dc.drawCenteredTextWithShadow( this.mc.textRenderer, stringHealth, (posX - 29 + 44) * 2, (34 + posY) * 2, -1);
            dc.getMatrices().scale(2f, 2f, 2f);

            int x = (posX - 29 + 44 - this.mc.textRenderer.getWidth(focused.getName().getString()) / 2);
            int y = 23 + posY;
            dc.drawText(this.mc.textRenderer, focused.getName().getString(), x, y, -1, false);

            drawEntityOnScreen(dc, posX - 60 + 14, 22 + 25 + posY, focused);

            if(settings.getBoolValue(Settings.show_entity_armor)) {
                int armor = focused.getArmor();
                if(armor > 0) {
                    String value = String.valueOf(armor);
                    drawRect(dc, posX - 30, posY + 42, 8 + (mc.textRenderer.getWidth(value) / 2), 6, 0xA0000000);
                    dc.getMatrices().scale(0.5f, 0.5f, 0.5f);
                    dc.drawGuiTexture(ARMOR_FULL_TEXTURE, (posX - 30) * 2, (posY + 42) * 2, 9, 9);
                    dc.drawText(this.mc.textRenderer, value, (posX - 24) * 2, (posY + 42) * 2 + 1, -1, false);
                    dc.getMatrices().scale(2f, 2f, 2f);
                }
            }
        }
    }

}
