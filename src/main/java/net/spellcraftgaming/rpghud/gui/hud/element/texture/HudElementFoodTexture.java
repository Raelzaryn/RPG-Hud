package net.spellcraftgaming.rpghud.gui.hud.element.texture;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.FoodStats;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementFoodTexture extends HudElement {

    public HudElementFoodTexture() {
        super(HudElementType.FOOD, 0, 0, 0, 0, true);
        this.parent = HudElementType.WIDGET;
    }

    @Override
    public boolean checkConditions() {
        return !this.mc.gameSettings.hideGUI && this.mc.playerController.shouldDrawHUD();
    }

    @Override
    public void drawElement(Gui gui, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        this.bind(INTERFACE);
        GlStateManager.color3f(1f, 1f, 1f);
        FoodStats stats = this.mc.player.getFoodStats();
        int stamina = stats.getFoodLevel();
        int staminaMax = 20;
        int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 49 : 25) + this.settings.getPositionValue(Settings.hunger_position)[0];
        int posY = (this.settings.getBoolValue(Settings.render_player_face) ? 22 : 18) + this.settings.getPositionValue(Settings.hunger_position)[1];
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
                gui.drawTexturedModalRect(posX, posY, 141, 148, (int) (110.0D * (bonusHunger / (double) staminaMax)), 12);
            }
        }

        if(this.mc.player.isPotionActive(MobEffects.HUNGER))
            gui.drawTexturedModalRect(posX, posY, 141, 136, (int) (110.0D * (stamina / (double) staminaMax)), 12);
        else
            gui.drawTexturedModalRect(posX, posY, 110, 100, (int) (110.0D * (stamina / (double) staminaMax)), 12);

        String staminaString = this.settings.getBoolValue(Settings.hunger_percentage) ? (int) Math.floor((double) stamina / (double) staminaMax * 100) + "%"
                : stamina + "/" + staminaMax;
        if(this.settings.getBoolValue(Settings.show_numbers_food))
            gui.drawCenteredString(this.mc.fontRenderer, staminaString, posX + 55, posY + 2, -1);
        GlStateManager.color3f(1f, 1f, 1f);
        this.mc.getTextureManager().bindTexture(Gui.ICONS);
    }

}
