package net.spellcraftgaming.rpghud.gui.hud.element.defaulthud;

import net.minecraft.client.gui.Gui;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementFoodDefault extends HudElement {

    public HudElementFoodDefault() {
        super(HudElementType.FOOD, 0, 0, 0, 0, true);
        parent = HudElementType.WIDGET;
    }

    @Override
    public boolean checkConditions() {
        return GameData.shouldDrawHUD();
    }

    @Override
    public void drawElement(Gui gui, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        int stamina = GameData.getPlayerFood();
        int staminaMax = GameData.getPlayerMaxFood();
        int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 49 : 24) + this.settings.getPositionValue(Settings.hunger_position)[0];
        int posY = (this.settings.getBoolValue(Settings.render_player_face) ? 26 : 18) + this.settings.getPositionValue(Settings.hunger_position)[1];
        ItemStack itemMain = GameData.getMainhand();
        ItemStack itemSec = GameData.getOffhand();

        if(GameData.doesPlayerNeedFood() && this.settings.getBoolValue(Settings.show_hunger_preview)) {
            float value = 0;
            if(itemMain != GameData.nullStack() && itemMain.getItem() instanceof ItemFood) {
                value = ((ItemFood) itemMain.getItem()).getHealAmount(itemMain);
            } else if(itemSec != GameData.nullStack() && itemSec.getItem() instanceof ItemFood) {
                value = ((ItemFood) itemSec.getItem()).getHealAmount(itemSec);
            }
            if(value > 0) {
                int bonusHunger = (int) (value + stamina);
                if(bonusHunger > staminaMax)
                    bonusHunger = staminaMax;
                int colorPreview = offsetColor(this.settings.getIntValue(Settings.color_food), OFFSET_PREVIEW);
                drawCustomBar(posX, posY, 110, 12, bonusHunger / (double) staminaMax * 100.0D, -1, -1, colorPreview,
                        offsetColorPercent(colorPreview, OFFSET_PERCENT));
            }
        }

        if(GameData.isPlayerHungered()) {
            drawCustomBar(posX, posY, 110, 12, stamina / (double) staminaMax * 100.0D, -1, -1, this.settings.getIntValue(Settings.color_hunger),
                    offsetColorPercent(this.settings.getIntValue(Settings.color_hunger), OFFSET_PERCENT));
        } else {
            drawCustomBar(posX, posY, 110, 12, stamina / (double) staminaMax * 100.0D, -1, -1, this.settings.getIntValue(Settings.color_food),
                    offsetColorPercent(this.settings.getIntValue(Settings.color_food), OFFSET_PERCENT));
        }
        String staminaString = this.settings.getBoolValue(Settings.hunger_percentage) ? (int) Math.floor((double) stamina / (double) staminaMax * 100) + "%"
                : stamina + "/" + staminaMax;
        if(this.settings.getBoolValue(Settings.show_numbers_food))
            gui.drawCenteredString(GameData.getFontRenderer(), staminaString, posX + 55, posY + 2, -1);
    }

}
