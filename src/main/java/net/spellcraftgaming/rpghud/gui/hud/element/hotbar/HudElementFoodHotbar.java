package net.spellcraftgaming.rpghud.gui.hud.element.hotbar;

import net.minecraft.client.gui.Gui;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.FoodStats;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@OnlyIn(Dist.CLIENT)
public class HudElementFoodHotbar extends HudElement {

    public HudElementFoodHotbar() {
        super(HudElementType.FOOD, 0, 0, 0, 0, true);
        this.parent = HudElementType.WIDGET;
    }

    @Override
    public boolean checkConditions() {
        return !this.mc.gameSettings.hideGUI && this.mc.playerController.shouldDrawHUD();
    }

    @Override
    public void drawElement(Gui gui, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        FoodStats stats = this.mc.player.getFoodStats();
        int stamina = stats.getFoodLevel();
        int staminaMax = 20;
        int height = scaledHeight + this.settings.getPositionValue(Settings.hunger_position)[1];
        int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 49 : 25) + this.settings.getPositionValue(Settings.hunger_position)[0];
        ItemStack itemMain = this.mc.player.getHeldItemMainhand();
        ItemStack itemSec = this.mc.player.getHeldItemOffhand();

        if(stats.needFood() && this.settings.getBoolValue(Settings.show_hunger_preview)) {
            float value = 0;
            if(itemMain != ItemStack.EMPTY && itemMain.getItem() instanceof ItemFood)
                value = ((ItemFood) itemMain.getItem()).getHealAmount(itemMain);
            else if(itemSec != ItemStack.EMPTY && itemSec.getItem() instanceof ItemFood)
                value = ((ItemFood) itemSec.getItem()).getHealAmount(itemSec);
            if(value > 0) {
                int bonusHunger = (int) (value + stamina);
                if(bonusHunger > staminaMax)
                    bonusHunger = staminaMax;
                int colorPreview = offsetColor(this.settings.getIntValue(Settings.color_food), OFFSET_PREVIEW);
                drawCustomBar(posX, height - 26, 200, 10, bonusHunger / (double) staminaMax * 100.0D, -1, -1, colorPreview,
                        offsetColorPercent(colorPreview, OFFSET_PERCENT));
            }
        }

        if(this.mc.player.isPotionActive(MobEffects.HUNGER))
            drawCustomBar(posX, height - 26, 200, 10, stamina / (double) staminaMax * 100.0D, -1, -1, this.settings.getIntValue(Settings.color_hunger),
                    offsetColorPercent(this.settings.getIntValue(Settings.color_hunger), OFFSET_PERCENT));
        else
            drawCustomBar(posX, height - 26, 200, 10, stamina / (double) staminaMax * 100.0D, -1, -1, this.settings.getIntValue(Settings.color_food),
                    offsetColorPercent(this.settings.getIntValue(Settings.color_food), OFFSET_PERCENT));

        String staminaString = this.settings.getBoolValue(Settings.hunger_percentage) == true ? (int) Math.floor((double) stamina / (double) staminaMax * 100) + "%"
                : stamina + "/" + staminaMax;
        if(this.settings.getBoolValue(Settings.show_numbers_food))
            gui.drawCenteredString(this.mc.fontRenderer, staminaString, posX + 100, height - 25, -1);
    }

}
