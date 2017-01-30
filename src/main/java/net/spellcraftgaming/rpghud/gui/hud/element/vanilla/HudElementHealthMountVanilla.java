package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.spellcraftgaming.rpghud.gui.GuiIngameRPGHud;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementHealthMountVanilla extends HudElement{

	public HudElementHealthMountVanilla() {
		super(HudElementType.HEALTH_MOUNT, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return this.mc.player.getRidingEntity() instanceof EntityLivingBase;
	}
	
	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		ScaledResolution res = new ScaledResolution(this.mc);
        EntityPlayer player = (EntityPlayer)this.mc.getRenderViewEntity();
        Entity tmp = player.getRidingEntity();
        if (!(tmp instanceof EntityLivingBase)) return;

        boolean unused = false;
        int left_align = res.getScaledWidth() / 2 + 91;

        this.mc.mcProfiler.endStartSection("mountHealth");
        EntityLivingBase mount = (EntityLivingBase)tmp;
        int health = (int)Math.ceil(mount.getHealth());
        float healthMax = mount.getMaxHealth();
        int hearts = (int)(healthMax + 0.5F) / 2;

        if (hearts > 30) hearts = 30;

        final int MARGIN = 52;
        final int BACKGROUND = MARGIN + (unused ? 1 : 0);
        final int HALF = MARGIN + 45;
        final int FULL = MARGIN + 36;

        for (int heart = 0; hearts > 0; heart += 20)
        {
            int top = res.getScaledHeight() - GuiIngameRPGHud.right_height;

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

            GuiIngameRPGHud.right_height += 10;
        }
	}

}
