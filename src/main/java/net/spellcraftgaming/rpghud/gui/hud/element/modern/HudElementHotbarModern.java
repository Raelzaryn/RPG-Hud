package net.spellcraftgaming.rpghud.gui.hud.element.modern;

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

public class HudElementHotbarModern extends HudElement {

    protected static final ResourceLocation WIDGETS_TEX_PATH = new ResourceLocation("textures/gui/widgets.png");

    public HudElementHotbarModern() {
        super(HudElementType.HOTBAR, 0, 0, 0, 0, true);
    }

    @Override
    public boolean checkConditions() {
        return true;
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
            int posX = this.settings.getPositionValue(Settings.hotbar_position)[0];
            int posY = this.settings.getPositionValue(Settings.hotbar_position)[1];
            int enumhandside = GameData.getOffhandSide();
            int width = res.getScaledWidth();
            int height = res.getScaledHeight() + posY;
            int i = (width / 2) + posX;
            float f = zLevel;
            zLevel = -90.0F;
            drawRect(width / 2 - 91 + posX, height - 22 - 5, 182, 2, 0xA0000000);
            if(GameData.isPlayerCreative())
                drawRect(width / 2 - 91 + posX, height - 7, 182, 2, 0xA0000000);
            for(int x = 0; x < 10; x++) {
                drawRect(width / 2 - 91 + (x * 20) + posX, height - 22 - 3, 2, 18, 0xA0000000);
                if(x < 9) {
                    drawRect(width / 2 - 91 + 2 + (x * 20) + posX, height - 22 - 3, 18, 18, 0x60000000);
                }
            }
            drawRect(width / 2 - 91 + 2 + (entityplayer.inventory.currentItem * 20) + posX, height - 22 - 3, 18, 18, 0x40FFFFFF);
            GlStateManager.enableAlpha();
            if(itemstack != GameData.nullStack()) {
                if(enumhandside == 0) {
                    drawRect(width / 2 - 91 - 24 + posX, height - 22 - 5, 22, 2, 0xA0000000);
                    drawRect(width / 2 - 91 - 24 + posX, height - 22 - 3, 2, 18, 0xA0000000);
                    drawRect(width / 2 - 91 - 4 + posX, height - 22 - 3, 2, 18, 0xA0000000);
                    drawRect(width / 2 - 91 + 2 - 24 + posX, height - 22 - 3, 18, 18, 0x60000000);
                    if(GameData.isPlayerCreative())
                        drawRect(width / 2 - 91 - 24 + posX, height - 7, 22, 2, 0xA0000000);
                } else {
                    drawRect(width / 2 - 91 - 24 + 209 + posX, height - 22 - 5, 22, 2, 0xA0000000);
                    drawRect(width / 2 - 91 - 24 + 209 + posX, height - 22 - 3, 2, 18, 0xA0000000);
                    drawRect(width / 2 - 91 - 4 + 209 + posX, height - 22 - 3, 2, 18, 0xA0000000);
                    drawRect(width / 2 - 91 + 2 - 24 + 209 + posX, height - 22 - 3, 18, 18, 0x60000000);
                    if(GameData.isPlayerCreative())
                        drawRect(width / 2 - 91 - 24 + 209 + posX, height - 7, 22, 2, 0xA0000000);
                }
            }

            zLevel = f;
            GlStateManager.enableRescaleNormal();
            GlStateManager.enableBlend();
            GameData.tryBlendFuncSeparate();
            RenderHelper.enableGUIStandardItemLighting();

            for(int l = 0; l < 9; ++l) {
                int i1 = i - 90 + l * 20 + 2;
                int j1 = res.getScaledHeight() - 16 - 3 - 9 + 4 + posY;
                this.renderHotbarItem(i1, j1, partialTicks, entityplayer, GameData.getMainInventoryItemOfSlot(l));
            }

            if(itemstack != GameData.nullStack()) {
                int l1 = res.getScaledHeight() - 16 - 3 - 9 + posY;

                if(enumhandside == 0) {
                    this.renderHotbarItem(i - 91 - 26 + 5, l1 + 4, partialTicks, entityplayer, itemstack);
                } else {
                    this.renderHotbarItem(i + 91 + 10 - 4, l1 + 4, partialTicks, entityplayer, itemstack);
                }
            }

            if(GameData.getAttackIndicatorSetting() == 2) {
                float f1 = GameData.getCooledAttackStrength();

                if(f1 < 1.0F) {
                    int i2 = res.getScaledHeight() - 20 + posY;
                    int j2 = i + 91 + 6;

                    if(enumhandside == 1) {
                        j2 = i - 91 - 22;
                    }

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
