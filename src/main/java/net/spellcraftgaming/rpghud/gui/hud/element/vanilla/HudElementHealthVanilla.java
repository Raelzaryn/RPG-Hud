package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import java.util.Random;

import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effects;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.ForgeIngameGui;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.gui.override.GuiIngameRPGHud;

public class HudElementHealthVanilla extends HudElement {

    protected int playerHealth;
    protected long lastSystemTime;
    protected long healthUpdateCounter;
    protected int lastPlayerHealth;
    protected Random rand = new Random();

    public HudElementHealthVanilla() {
        super(HudElementType.HEALTH, 0, 0, 0, 0, true);
    }

    @Override
    public boolean checkConditions() {
        return !this.mc.gameSettings.hideGUI && this.mc.playerController.shouldDrawHUD();
    }

    @Override
    public void drawElement(AbstractGui gui, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        PlayerEntity player = (PlayerEntity) this.mc.getRenderViewEntity();
        GuiIngameRPGHud theGui = ((GuiIngameRPGHud) gui);
        int ticks = theGui.getTicks();
        int health = MathHelper.ceil(player.getHealth());
        boolean highlight = this.healthUpdateCounter > ticks && (this.healthUpdateCounter - ticks) / 3L % 2L == 1L;

        if(health < this.playerHealth && player.hurtResistantTime > 0) {
            this.lastSystemTime = Util.milliTime();
            this.healthUpdateCounter = ticks + 20;
        } else if(health > this.playerHealth && player.hurtResistantTime > 0) {
            this.lastSystemTime = Util.milliTime();
            this.healthUpdateCounter = ticks + 10;
        }

        if(Util.milliTime() - this.lastSystemTime > 1000L) {
            this.playerHealth = health;
            this.lastPlayerHealth = health;
            this.lastSystemTime = Util.milliTime();
        }

        this.playerHealth = health;
        int healthLast = this.lastPlayerHealth;

        IAttributeInstance attrMaxHealth = player.getAttribute(SharedMonsterAttributes.MAX_HEALTH);
        float healthMax = (float) attrMaxHealth.getValue();
        float absorb = MathHelper.ceil(player.getAbsorptionAmount());

        int healthRows = MathHelper.ceil((healthMax + absorb) / 2.0F / 10.0F);
        int rowHeight = Math.max(10 - (healthRows - 2), 3);

        this.rand.setSeed(ticks * 312871);

        int left = scaledWidth / 2 - 91;
        int top = scaledHeight - ForgeIngameGui.left_height;
        ForgeIngameGui.left_height += (healthRows * rowHeight);
        if(rowHeight != 10)
            ForgeIngameGui.left_height += 10 - rowHeight;

        int regen = -1;
        if(player.isPotionActive(Effects.REGENERATION))
            regen = ticks % 25;

        final int TOP = 9 * (this.mc.world.getWorldInfo().isHardcore() ? 5 : 0);
        final int BACKGROUND = (highlight ? 25 : 16);
        int MARGIN = 16;
        if(player.isPotionActive(Effects.POISON))
            MARGIN += 36;
        else if(player.isPotionActive(Effects.WITHER))
            MARGIN += 72;
        float absorbRemaining = absorb;

        for(int i = MathHelper.ceil((healthMax + absorb) / 2.0F) - 1; i >= 0; --i) {
            // int b0 = (highlight ? 1 : 0);
            int row = MathHelper.ceil((i + 1) / 10.0F) - 1;
            int x = left + i % 10 * 8;
            int y = top - row * rowHeight;

            if(health <= 4)
                y += this.rand.nextInt(2);
            if(i == regen)
                y -= 2;

            gui.blit(x, y, BACKGROUND, TOP, 9, 9);

            if(highlight)
                if(i * 2 + 1 < healthLast)
                    gui.blit(x, y, MARGIN + 54, TOP, 9, 9); // 6
                else if(i * 2 + 1 == healthLast)
                    gui.blit(x, y, MARGIN + 63, TOP, 9, 9); // 7

            if(absorbRemaining > 0.0F) {
                if(absorbRemaining == absorb && absorb % 2.0F == 1.0F) {
                    gui.blit(x, y, MARGIN + 153, TOP, 9, 9); // 17
                    absorbRemaining -= 1.0F;
                } else {
                    gui.blit(x, y, MARGIN + 144, TOP, 9, 9); // 16
                    absorbRemaining -= 2.0F;
                }
            } else if(i * 2 + 1 < health)
                gui.blit(x, y, MARGIN + 36, TOP, 9, 9); // 4
            else if(i * 2 + 1 == health)
                gui.blit(x, y, MARGIN + 45, TOP, 9, 9); // 5
        }
    }

}
