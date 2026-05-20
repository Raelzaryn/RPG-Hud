package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.TextAlignment;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.spellcraftgaming.rpghud.gui.hud.element.vanilla.HudElementEntityInspectVanilla;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(value=EnvType.CLIENT)
public class HudElementEntityInspectModern extends HudElementEntityInspectVanilla {

    @Override
    public void drawElement(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, int scaledWidth, int scaledHeight) {
        LivingEntity focused = getFocusedEntity(this.mc.player, deltaTracker);
        if(focused != null) {
            int posX = (scaledWidth / 2) + this.settings.getPositionValue(Settings.inspector_position)[0];
            int posY = this.settings.getPositionValue(Settings.inspector_position)[1];
            float health = focused.getHealth();
            float maxHealth = focused.getMaxHealth();
            if (health > maxHealth) health = maxHealth;
            drawRect(graphics, posX - 62, 20 + posY, 32, 32, 0xA0000000);
            drawRect(graphics, posX - 60, 22 + posY, 28, 28, 0x20FFFFFF);
            drawRect(graphics, posX - 30, 20 + posY, 90, 12, 0xA0000000);
            drawTetragon(graphics, posX - 30, posX - 30, 32 + posY, 32 + posY, 90, 76, 10, 10, 0xA0000000);
            drawTetragon(graphics, posX - 30, posX - 30, 33 + posY, 33 + posY, 84, 74, 6, 6, 0x20FFFFFF);

            drawTetragon(graphics, posX - 30, posX - 30, 33 + posY, 33 + posY, (int) (84 * ((double) health / (double) maxHealth)),
                    (int) (84 * ((double) health / (double) maxHealth)) - 10, 6, 6, this.settings.getIntValue(Settings.color_health));

            String stringHealth = ((double) Math.round(health * 10)) / 10 + "/" + ((double) Math.round(maxHealth * 10)) / 10;

            graphics.pose().scale(0.5f, 0.5f);
            graphics.centeredText(this.mc.font, stringHealth, (posX - 29 + 44) * 2, (34 + posY) * 2, -1);
            graphics.pose().scale(2f, 2f);

            int x = (posX - 29 + 44 - this.mc.font.width(focused.getName().getString()) / 2);
            int y = 23 + posY;
            graphics.text(this.mc.font, focused.getName().getString(), x, y, -1, true);

            drawEntityOnScreen(graphics, posX - 60 + 14, 22 + 25 + posY, focused);

            if (settings.getBoolValue(Settings.show_entity_armor)) {
                int armor = focused.getArmorValue();
                if (armor > 0) {
                    String value = String.valueOf(armor);
                    drawRect(graphics, posX - 30, posY + 42, 8 + (mc.font.width(value) / 2), 6, 0xA0000000);
                    graphics.pose().scale(0.5f, 0.5f);
                    graphics.blitSprite(RenderPipelines.GUI_TEXTURED, ARMOR_FULL_TEXTURE, (posX - 30) * 2, (posY + 42) * 2, 9, 9);
                    graphics.text(this.mc.font, value, (posX - 24) * 2, (posY + 42) * 2 + 1, -1, true);
                    graphics.pose().scale(2f, 2f);
                }
            }
        }
    }

}
