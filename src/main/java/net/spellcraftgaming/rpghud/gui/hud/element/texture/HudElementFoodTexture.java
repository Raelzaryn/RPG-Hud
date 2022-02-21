package net.spellcraftgaming.rpghud.gui.hud.element.texture;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementFoodTexture extends HudElement {

    public HudElementFoodTexture() {
        super(HudElementType.FOOD, 0, 0, 0, 0, true);
        parent = HudElementType.WIDGET;
    }

    @Override
    public boolean checkConditions() {
        return GameData.shouldDrawHUD();
    }

    @Override
    public void drawElement(Gui gui, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        bind(INTERFACE);
        GlStateManager.color(1f, 1f, 1f);
        int stamina = GameData.getPlayerFood();
        int staminaMax = GameData.getPlayerMaxFood();
        int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 49 : 25) + this.settings.getPositionValue(Settings.hunger_position)[0];
        int posY = (this.settings.getBoolValue(Settings.render_player_face) ? 22 : 18) + this.settings.getPositionValue(Settings.hunger_position)[1];
        ItemStack itemMain = GameData.getMainhand();
        ItemStack itemSec = GameData.getOffhand();

        if (GameData.doesPlayerNeedFood() && this.settings.getBoolValue(Settings.show_hunger_preview)) {
            float value = 0;
            if (itemMain != GameData.nullStack() && itemMain.getItem() instanceof ItemFood) {
                value = ((ItemFood) itemMain.getItem()).getHealAmount(itemMain);
            } else if (itemSec != GameData.nullStack() && itemSec.getItem() instanceof ItemFood) {
                value = ((ItemFood) itemSec.getItem()).getHealAmount(itemSec);
            }
            if (value > 0) {
                int bonusHunger = (int) (value + stamina);
                if (bonusHunger > staminaMax)
                    bonusHunger = staminaMax;
                gui.drawTexturedModalRect(posX, posY, 141, 148, (int) (110.0D * (bonusHunger / (double) staminaMax)), 12);
            }
        }

        if (GameData.isPlayerHungered()) {
            gui.drawTexturedModalRect(posX, posY, 141, 136, (int) (110.0D * (stamina / (double) staminaMax)), 12);
        } else {
            gui.drawTexturedModalRect(posX, posY, 110, 100, (int) (110.0D * (stamina / (double) staminaMax)), 12);
        }

        String staminaString = this.settings.getBoolValue(Settings.hunger_percentage) ? (int) Math.floor((double) stamina / (double) staminaMax * 100) + "%"
                : stamina + "/" + staminaMax;
        if (this.settings.getBoolValue(Settings.show_numbers_food))
            gui.drawCenteredString(GameData.getFontRenderer(), staminaString, posX + 55, posY + 2, -1);
        GlStateManager.color(1f, 1f, 1f);
        GameData.bindIcons();
    }

}
