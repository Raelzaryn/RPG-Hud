package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.gui.override.GuiIngameRPGHud;

public class HudElementHealthMountVanilla extends HudElement {

	public HudElementHealthMountVanilla() {
		super(HudElementType.HEALTH_MOUNT, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return mc.player.getRidingEntity() instanceof EntityLiving;
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        EntityPlayer player = (EntityPlayer)mc.getRenderViewEntity();
        Entity tmp = player.getRidingEntity();
		GuiIngameRPGHud theGui = ((GuiIngameRPGHud) gui);

        bind(Gui.ICONS);


        boolean unused = false;
        int left_align = scaledWidth / 2 + 91;

        mc.profiler.endStartSection("mountHealth");
        GlStateManager.enableBlend();
        EntityLivingBase mount = (EntityLivingBase)tmp;
        int health = (int)Math.ceil((double)mount.getHealth());
        float healthMax = mount.getMaxHealth();
        int hearts = (int)(healthMax + 0.5F) / 2;

        if (hearts > 30) hearts = 30;

        final int MARGIN = 52;
        final int BACKGROUND = MARGIN + (unused ? 1 : 0);
        final int HALF = MARGIN + 45;
        final int FULL = MARGIN + 36;

        for (int heart = 0; hearts > 0; heart += 20)
        {
            int top = scaledHeight - theGui.right_height;

            int rowCount = Math.min(hearts, 10);
            hearts -= rowCount;

            for (int i = 0; i < rowCount; ++i)
            {
                int x = left_align - i * 8 - 9;
                gui.drawTexturedModalRect(x, top, BACKGROUND, 9, 9, 9);

                if (i * 2 + 1 + heart < health)
                    gui.drawTexturedModalRect(x, top, FULL, 9, 9, 9);
                else if (i * 2 + 1 + heart == health)
                    gui.drawTexturedModalRect(x, top, HALF, 9, 9, 9);
            }

            theGui.right_height += 10;
        }
        GlStateManager.disableBlend();
	}

}
