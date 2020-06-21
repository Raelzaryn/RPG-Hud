package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.LivingEntity;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementJumpBarVanilla extends HudElement {

    public HudElementJumpBarVanilla() {
        super(HudElementType.JUMP_BAR, 0, 0, 0, 0, true);
    }

    @Override
    public boolean checkConditions() {
        return this.mc.player.getRidingEntity() instanceof LivingEntity;
    }

    @Override
    public void drawElement(AbstractGui gui, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableBlend();
        this.mc.getTextureManager().bindTexture(AbstractGui.GUI_ICONS_LOCATION);
        float f = this.mc.player.getHorseJumpPower();
        int j = (int) (f * 183.0F);
        int k = scaledHeight - 32 + 3;
        gui.blit(scaledWidth / 2 - 91, k, 0, 84, 182, 5);
        if(j > 0)
            gui.blit(scaledWidth / 2 - 91, k, 0, 89, j, 5);

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

}
