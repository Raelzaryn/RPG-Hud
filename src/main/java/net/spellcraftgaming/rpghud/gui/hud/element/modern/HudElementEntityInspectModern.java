package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.spellcraftgaming.rpghud.gui.hud.element.vanilla.HudElementEntityInspectVanilla;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(EnvType.CLIENT)
public class HudElementEntityInspectModern extends HudElementEntityInspectVanilla {

    @Override
    public void drawElement(DrawableHelper gui, MatrixStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        LivingEntity focused = getFocusedEntity(this.mc.player);
        if(focused != null) {
            int posX = (scaledWidth / 2) + this.settings.getPositionValue(Settings.inspector_position)[0];
            int posY = this.settings.getPositionValue(Settings.inspector_position)[1];
            float health = focused.getHealth();
            float maxHealth = focused.getMaxHealth();
            if(health > maxHealth) health = maxHealth;
            drawRect(posX - 62, 20 + posY, 32, 32, 0xA0000000);
            drawRect(posX - 60, 22 + posY, 28, 28, 0x20FFFFFF);
            drawRect(posX - 30, 20 + posY, 90, 12, 0xA0000000);
            drawTetragon(posX - 30, posX - 30, 32 + posY, 32 + posY, 90, 76, 10, 10, 0xA0000000);
            drawTetragon(posX - 30, posX - 30, 33 + posY, 33 + posY, 84, 74, 6, 6, 0x20FFFFFF);

            drawTetragon(posX - 30, posX - 30, 33 + posY, 33 + posY, (int) (84 * ((double) health / (double) maxHealth)),
                    (int) (84 * ((double) health / (double) maxHealth)) - 10, 6, 6, this.settings.getIntValue(Settings.color_health));

            String stringHealth = ((double) Math.round(health * 10)) / 10 + "/" + ((double) Math.round(maxHealth * 10)) / 10;

            RenderSystem.scaled(0.5, 0.5, 0.5);
            DrawableHelper.drawCenteredString(ms, this.mc.textRenderer, stringHealth, (posX - 29 + 44) * 2, (34 + posY) * 2, -1);
            RenderSystem.scaled(2.0, 2.0, 2.0);

            int x = (posX - 29 + 44 - this.mc.textRenderer.getWidth(focused.getName().getString()) / 2);
            int y = 23 + posY;
            this.mc.textRenderer.draw(ms, focused.getName().getString(), x, y, -1);

            drawEntityOnScreen(posX - 60 + 14, 22 + 25 + posY, focused);

            if(settings.getBoolValue(Settings.show_entity_armor)) {
                int armor = focused.getArmor();
                if(armor > 0) {
                    this.mc.getTextureManager().bindTexture(DrawableHelper.GUI_ICONS_TEXTURE);
                    String value = String.valueOf(armor);
                    drawRect(posX - 30, posY + 42, 8 + (mc.textRenderer.getWidth(value) / 2), 6, 0xA0000000);
                    RenderSystem.scaled(0.5, 0.5, 0.5);
                    gui.drawTexture(ms, (posX - 30) * 2, (posY + 42) * 2, 34, 9, 9, 9);
                    this.mc.textRenderer.draw(ms, value, (posX - 24) * 2, (posY + 42) * 2 + 1, -1);
                    RenderSystem.scaled(2.0, 2.0, 2.0);
                }
            }
        }
    }

}
