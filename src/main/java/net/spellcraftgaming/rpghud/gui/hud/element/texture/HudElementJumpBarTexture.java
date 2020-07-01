package net.spellcraftgaming.rpghud.gui.hud.element.texture;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@OnlyIn(Dist.CLIENT)
public class HudElementJumpBarTexture extends HudElement {

    public HudElementJumpBarTexture() {
        super(HudElementType.JUMP_BAR, 0, 0, 0, 0, true);
    }

    @Override
    public boolean checkConditions() {
        return this.mc.player.getRidingEntity() instanceof EntityLivingBase
                && (this.settings.getBoolValue(Settings.limit_jump_bar) ? this.mc.player.getHorseJumpPower() > 0F : true);
    }

    @Override
    public void drawElement(Gui gui, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        this.bind(INTERFACE);
        int height = scaledHeight + this.settings.getPositionValue(Settings.jump_bar_position)[1];
        int adjustedWidth = (scaledWidth / 2) + this.settings.getPositionValue(Settings.jump_bar_position)[0];
        float var14 = this.mc.player.getHorseJumpPower();
        int color = (int) (var14 * 100.0F);
        GlStateManager.color3f(1f, 1f, 1f);
        gui.drawTexturedModalRect(adjustedWidth - 71, height - 80, 0, 160, 141, 10);
        gui.drawTexturedModalRect(adjustedWidth - 71, height - 80, 0, 150, (int) (141.0D * (color / 100.0D)), 10);
        GlStateManager.color3f(1f, 1f, 1f);
        this.mc.getTextureManager().bindTexture(Gui.ICONS);
    }

}
