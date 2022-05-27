package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.Gui;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.spellcraftgaming.rpghud.gui.hud.element.vanilla.HudElementEntityInspectVanilla;
import net.spellcraftgaming.rpghud.settings.Settings;

@OnlyIn(Dist.CLIENT)
public class HudElementEntityInspectModern extends HudElementEntityInspectVanilla {

    @Override
    public void drawElement(Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        LivingEntity focused = getFocusedEntity(this.mc.player);
        if(focused != null) {
            int posX = (scaledWidth / 2) + this.settings.getPositionValue(Settings.inspector_position)[0];
            int posY = this.settings.getPositionValue(Settings.inspector_position)[1];
            float health = focused.getHealth();
            float maxHealth = focused.getMaxHealth();
            if(health > maxHealth) health = maxHealth;
            drawRect(ms, posX - 62, 20 + posY, 32, 32, 0xA0000000);
            drawRect(ms, posX - 60, 22 + posY, 28, 28, 0x20FFFFFF);
            drawRect(ms, posX - 30, 20 + posY, 90, 12, 0xA0000000);
            drawTetragon(posX - 30, posX - 30, 32 + posY, 32 + posY, 90, 76, 10, 10, 0xA0000000);
            drawTetragon(posX - 30, posX - 30, 33 + posY, 33 + posY, 84, 74, 6, 6, 0x20FFFFFF);

            drawTetragon(posX - 30, posX - 30, 33 + posY, 33 + posY, (int) (84 * ((double) health / (double) maxHealth)),
                    (int) (84 * ((double) health / (double) maxHealth)) - 10, 6, 6, this.settings.getIntValue(Settings.color_health));

            String stringHealth = ((double) Math.round(health * 10)) / 10 + "/" + ((double) Math.round(maxHealth * 10)) / 10;

            ms.scale(0.5f, 0.5f, 0.5f);
            Gui.drawCenteredString(ms, this.mc.font, stringHealth, (posX - 29 + 44) * 2, (34 + posY) * 2, -1);
            ms.scale(2f, 2f, 2f);

            int x = (posX - 29 + 44 - this.mc.font.width(focused.getName().getString()) / 2);
            int y = 23 + posY;
            this.mc.font.draw(ms, focused.getName().getString(), x, y, -1);

            drawEntityOnScreen(posX - 60 + 14, 22 + 25 + posY, focused);

            if(settings.getBoolValue(Settings.show_entity_armor)) {
                int armor = focused.getArmorValue();
                if(armor > 0) {
                    bind(Gui.GUI_ICONS_LOCATION);
                    String value = String.valueOf(armor);
                    drawRect(ms, posX - 30, posY + 42, 8 + (mc.font.width(value) / 2), 6, 0xA0000000);
                    ms.scale(0.5f, 0.5f, 0.5f);
                    gui.blit(ms, (posX - 30) * 2, (posY + 42) * 2, 34, 9, 9, 9);
                    this.mc.font.draw(ms, value, (posX - 24) * 2, (posY + 42) * 2 + 1, -1);
                    ms.scale(2f, 2f, 2f);
                }
            }
        }
    }

}
