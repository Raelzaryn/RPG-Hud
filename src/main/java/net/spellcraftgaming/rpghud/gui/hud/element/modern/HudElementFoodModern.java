package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.Gui;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.FoodStats;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.spellcraftgaming.rpghud.gui.hud.HudModern;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@OnlyIn(Dist.CLIENT)
public class HudElementFoodModern extends HudElement {

    public HudElementFoodModern() {
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
        int xOffset = ((HudModern) this.rpgHud.huds.get("modern")).getPosX();
        String staminaString = this.settings.getBoolValue(Settings.hunger_percentage) ? (int) Math.floor((double) stamina / (double) staminaMax * 100) + "%"
                : stamina + "/" + staminaMax;
        int width = this.mc.fontRenderer.getStringWidth(staminaString) / 2 + 4;
        if(width < xOffset)
            width = xOffset;
        else
            ((HudModern) this.rpgHud.huds.get("modern")).setPosX(width);

        int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 24 : 2)
                + ((this.settings.getBoolValue(Settings.show_numbers_health) && this.settings.getBoolValue(Settings.show_numbers_food)) ? xOffset : 0)
                + this.settings.getPositionValue(Settings.hunger_position)[0];
        int textPosX = this.settings.getPositionValue(Settings.hunger_position)[0];
        int posY = this.settings.getPositionValue(Settings.hunger_position)[1];

        if(this.settings.getBoolValue(Settings.show_numbers_health) && this.settings.getBoolValue(Settings.show_numbers_food)) {
            drawRect(textPosX + (this.settings.getBoolValue(Settings.render_player_face) ? 23 : 2), posY + 12, width, 8, 0xA0000000);
            GL11.glScaled(0.5D, 0.5D, 0.5D);
            gui.drawCenteredString(this.mc.fontRenderer, staminaString,
                    textPosX * 2 + (this.settings.getBoolValue(Settings.render_player_face) ? 42 : 0) + width + 4, posY * 2 + 28, -1);
            GL11.glScaled(2.0D, 2.0D, 2.0D);
        }

        this.drawTetragon(posX, posX, 13 + posY, 13 + posY, 70, 58, 8, 8, 0xA0000000);
        this.drawTetragon(posX + 2, posX + 2, 13 + posY, 13 + posY, 64, 54, 6, 6, 0x20FFFFFF);

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
                this.drawTetragon(posX + 2, posX + 2, 13 + posY, 13 + posY, (int) (64 * ((double) bonusHunger / (double) staminaMax)),
                        (int) (63 * ((double) bonusHunger / (double) 20)) - 10, 6, 6, offsetColor(this.settings.getIntValue(Settings.color_food), OFFSET_PREVIEW));
            }
        }

        if(this.mc.player.isPotionActive(MobEffects.HUNGER))
            this.drawTetragon(posX + 2, posX + 2, 13 + posY, 13 + posY, (int) (64 * ((double) stamina / (double) staminaMax)),
                    (int) (64 * ((double) stamina / (double) 20)) - 10, 6, 6, this.settings.getIntValue(Settings.color_hunger));
        else
            this.drawTetragon(posX + 2, posX + 2, 13 + posY, 13 + posY, (int) (64 * ((double) stamina / (double) staminaMax)),
                    (int) (64 * ((double) stamina / (double) 20)) - 10, 6, 6, this.settings.getIntValue(Settings.color_food));
    }

}
