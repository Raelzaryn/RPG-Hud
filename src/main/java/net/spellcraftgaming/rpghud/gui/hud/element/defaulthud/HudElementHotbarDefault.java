package net.spellcraftgaming.rpghud.gui.hud.element.defaulthud;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.spellcraftgaming.rpghud.gui.GuiIngameRPGHud;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementHotbarDefault extends HudElement {

	protected static final ResourceLocation WIDGETS_TEX_PATH = new ResourceLocation("textures/gui/widgets.png");
	
	public HudElementHotbarDefault() {
		super(HudElementType.HOTBAR, 0, 0, 0, 0, true);
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		ScaledResolution res = new ScaledResolution(this.mc);
		if(this.mc.playerController.isSpectator()) {
			((GuiIngameRPGHud) gui).getSpectatorGui().renderTooltip(res, partialTicks);
		}
		if (this.mc.getRenderViewEntity() instanceof EntityPlayer)
        {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.mc.getTextureManager().bindTexture(WIDGETS_TEX_PATH);
            EntityPlayer entityplayer = (EntityPlayer)this.mc.getRenderViewEntity();
            ItemStack itemstack = entityplayer.getHeldItemOffhand();
            EnumHandSide enumhandside = entityplayer.getPrimaryHand().opposite();
            int i = res.getScaledWidth() / 2;
            float f = zLevel;
            zLevel = -90.0F;
            gui.drawTexturedModalRect(i - 91, res.getScaledHeight() - 22 - 9, 0, 0, 182, 22);
            gui.drawTexturedModalRect(i - 91 - 1 + entityplayer.inventory.currentItem * 20, res.getScaledHeight() - 22 - 1 - 9, 0, 22, 24, 22);

            if (!itemstack.isEmpty())
            {
                if (enumhandside == EnumHandSide.LEFT)
                {
                    gui.drawTexturedModalRect(i - 91 - 29, res.getScaledHeight() - 23 - 9, 24, 22, 29, 24);
                }
                else
                {
                    gui.drawTexturedModalRect(i + 91, res.getScaledHeight() - 23 - 9, 53, 22, 29, 24);
                }
            }

            zLevel = f;
            GlStateManager.enableRescaleNormal();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            RenderHelper.enableGUIStandardItemLighting();

            for (int l = 0; l < 9; ++l)
            {
                int i1 = i - 90 + l * 20 + 2;
                int j1 = res.getScaledHeight() - 16 - 3 - 9;
                this.renderHotbarItem(i1, j1, partialTicks, entityplayer, entityplayer.inventory.mainInventory.get(l));
            }

            if (!itemstack.isEmpty())
            {
                int l1 = res.getScaledHeight() - 16 - 3 - 9;

                if (enumhandside == EnumHandSide.LEFT)
                {
                    this.renderHotbarItem(i - 91 - 26, l1, partialTicks, entityplayer, itemstack);
                }
                else
                {
                    this.renderHotbarItem(i + 91 + 10, l1, partialTicks, entityplayer, itemstack);
                }
            }

            if (this.mc.gameSettings.attackIndicator == 2)
            {
                float f1 = this.mc.player.getCooledAttackStrength(0.0F);

                if (f1 < 1.0F)
                {
                    int i2 = res.getScaledHeight() - 20;
                    int j2 = i + 91 + 6;

                    if (enumhandside == EnumHandSide.RIGHT)
                    {
                        j2 = i - 91 - 22;
                    }

                    this.mc.getTextureManager().bindTexture(Gui.ICONS);
                    int k1 = (int)(f1 * 19.0F);
                    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                    gui.drawTexturedModalRect(j2, i2 - 9, 0, 94, 18, 18);
                    gui.drawTexturedModalRect(j2, i2 - 9 + 18 - k1, 18, 112 - k1, 18, k1);
                }
            }

            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableBlend();
        }
	}
	
    protected void renderHotbarItem(int p_184044_1_, int p_184044_2_, float p_184044_3_, EntityPlayer player, ItemStack stack)
    {
        if (!stack.isEmpty())
        {
            float f = stack.getAnimationsToGo() - p_184044_3_;

            if (f > 0.0F)
            {
                GlStateManager.pushMatrix();
                float f1 = 1.0F + f / 5.0F;
                GlStateManager.translate(p_184044_1_ + 8, p_184044_2_ + 12, 0.0F);
                GlStateManager.scale(1.0F / f1, (f1 + 1.0F) / 2.0F, 1.0F);
                GlStateManager.translate((-(p_184044_1_ + 8)), (-(p_184044_2_ + 12)), 0.0F);
            }

            this.mc.getRenderItem().renderItemAndEffectIntoGUI(player, stack, p_184044_1_, p_184044_2_);

            if (f > 0.0F)
            {
                GlStateManager.popMatrix();
            }

            this.mc.getRenderItem().renderItemOverlays(this.mc.fontRendererObj, stack, p_184044_1_, p_184044_2_);
        }
    }

}
