package net.spellcraftgaming.rpghud.gui.hud.element.hotbar;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.lib.gui.override.GuiIngameRPGHud;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementHotbarHotbar extends HudElement {

    protected static final ResourceLocation WIDGETS_TEX_PATH = new ResourceLocation("textures/gui/widgets.png");

    public HudElementHotbarHotbar() {
        super(HudElementType.HOTBAR, 0, 0, 0, 0, true);
        parent = HudElementType.WIDGET;
    }

    @Override
    public void drawElement(Gui gui, float zLevel, float partialTicks) {
        ScaledResolution res = new ScaledResolution(this.mc);
        if(this.mc.playerController.isSpectator()) {
            ((GuiIngameRPGHud) gui).getSpectatorGui().renderTooltip(res, partialTicks);
        } else if(this.mc.getRenderViewEntity() instanceof EntityPlayer) {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.mc.getTextureManager().bindTexture(WIDGETS_TEX_PATH);
            EntityPlayer entityplayer = (EntityPlayer) this.mc.getRenderViewEntity();
            ItemStack itemstack = GameData.getOffhand();
            int i = res.getScaledWidth() / 2;
            float f = zLevel;
            zLevel = -90.0F;
            int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 49 : 25) + this.settings.getPositionValue(Settings.hotbar_position)[0];
            int posY = this.settings.getPositionValue(Settings.hotbar_position)[1];
            gui.drawTexturedModalRect(posX, res.getScaledHeight() - 47 + posY, 0, 0, 182, 22);
            gui.drawTexturedModalRect(posX + entityplayer.inventory.currentItem * 20, res.getScaledHeight() - 47 - 1 + posY, 0, 22, 24, 22);

            gui.drawTexturedModalRect(posX + 181, res.getScaledHeight() - 47 + posY, 60, 23, 22, 22);

            zLevel = f;
            GlStateManager.enableRescaleNormal();
            GlStateManager.enableBlend();
            GameData.tryBlendFuncSeparate();
            RenderHelper.enableGUIStandardItemLighting();

            for(int l = 0; l < 9; ++l) {
                int i1 = posX + 1 + l * 20 + 2;
                int j1 = res.getScaledHeight() - 16 - 19 - 9 + posY;
                this.renderHotbarItem(i1, j1, partialTicks, entityplayer, GameData.getMainInventoryItemOfSlot(l));
            }

            int l1 = res.getScaledHeight() - 47 + 3 + posY;
            this.renderHotbarItem(posX + 184, l1, partialTicks, entityplayer, itemstack);

            if(GameData.getAttackIndicatorSetting() == 2) {
                float f1 = GameData.getCooledAttackStrength();

                if(f1 < 1.0F) {
                    int i2 = res.getScaledHeight() - 36 + posY;
                    int j2 = i + 40 + this.settings.getPositionValue(Settings.hotbar_position)[0];

                    GameData.bindIcons();
                    int k1 = (int) (f1 * 19.0F);
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

    /**
     * Renders an item on the screen
     * 
     * @param xPos         the x position on the screen
     * @param yPos         the y position on the screen
     * @param partialTicks the partial ticks (used for animation)
     * @param player       the player who should get the item rendered
     * @param item         the item (via ItemStack)
     */
    protected void renderHotbarItem(int xPos, int yPos, float partialTicks, EntityPlayer player, ItemStack item) {
        if(item != GameData.nullStack()) {
            float f = GameData.getItemAnimationsToGo(item) - partialTicks;

            if(f > 0.0F) {
                GlStateManager.pushMatrix();
                float f1 = 1.0F + f / 5.0F;
                GlStateManager.translate(xPos + 8, yPos + 12, 0.0F);
                GlStateManager.scale(1.0F / f1, (f1 + 1.0F) / 2.0F, 1.0F);
                GlStateManager.translate((-(xPos + 8)), (-(yPos + 12)), 0.0F);
            }

            GameData.renderItemIntoGUI(player, item, xPos, yPos);

            if(f > 0.0F) {
                GlStateManager.popMatrix();
            }

            this.mc.getRenderItem().renderItemOverlays(GameData.getFontRenderer(), item, xPos, yPos);
        }
    }

}
