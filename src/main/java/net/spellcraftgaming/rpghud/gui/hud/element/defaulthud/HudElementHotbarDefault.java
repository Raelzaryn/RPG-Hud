package net.spellcraftgaming.rpghud.gui.hud.element.defaulthud;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementHotbarDefault extends HudElement {

    protected static final ResourceLocation WIDGETS_TEX_PATH = new ResourceLocation("textures/gui/widgets.png");

    public HudElementHotbarDefault() {
        super(HudElementType.HOTBAR, 0, 0, 0, 0, true);
    }

    @Override
    public boolean checkConditions() {
        return true;
    }

    @Override
    public void drawElement(Gui gui, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        if (this.mc.playerController.isSpectator()) {
            this.mc.ingameGUI.getSpectatorGui().renderTooltip(new ScaledResolution(mc), partialTicks);
        } else if (this.mc.getRenderViewEntity() instanceof EntityPlayer) {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.mc.getTextureManager().bindTexture(WIDGETS_TEX_PATH);
            EntityPlayer entityplayer = (EntityPlayer) this.mc.getRenderViewEntity();
            ItemStack itemstack = GameData.getOffhand();
            int enumhandside = GameData.getOffhandSide();
            int i = (scaledWidth / 2) + this.settings.getPositionValue(Settings.hotbar_position)[0];
            int posY = this.settings.getPositionValue(Settings.hotbar_position)[1];
            float f = zLevel;
            zLevel = -90.0F;
            gui.drawTexturedModalRect(i - 91, scaledHeight - 22 - 9 + posY, 0, 0, 182, 22);
            gui.drawTexturedModalRect(i - 91 - 1 + entityplayer.inventory.currentItem * 20, scaledHeight - 22 - 1 - 9 + posY, 0, 22, 24, 22);

            if (itemstack != GameData.nullStack()) {
                if (enumhandside == 0) {
                    gui.drawTexturedModalRect(i - 91 - 29, scaledHeight - 23 - 9 + posY, 24, 22, 29, 24);
                } else {
                    gui.drawTexturedModalRect(i + 91, scaledHeight - 23 - 9 + posY, 53, 22, 29, 24);
                }
            }

            zLevel = f;
            GlStateManager.enableRescaleNormal();
            GlStateManager.enableBlend();
            GameData.tryBlendFuncSeparate();
            RenderHelper.enableGUIStandardItemLighting();

            for (int l = 0; l < 9; ++l) {
                int i1 = i - 90 + l * 20 + 2;
                int j1 = scaledHeight - 16 - 3 - 9 + posY;
                this.renderHotbarItem(i1, j1, partialTicks, entityplayer, GameData.getMainInventoryItemOfSlot(l));
            }

            if (itemstack != GameData.nullStack()) {
                int l1 = scaledHeight - 16 - 3 - 9 + posY;

                if (enumhandside == 0) {
                    this.renderHotbarItem(i - 91 - 26, l1, partialTicks, entityplayer, itemstack);
                } else {
                    this.renderHotbarItem(i + 91 + 10, l1, partialTicks, entityplayer, itemstack);
                }
            }

            if (GameData.getAttackIndicatorSetting() == 2) {
                float f1 = GameData.getCooledAttackStrength();

                if (f1 < 1.0F) {
                    int i2 = scaledHeight - 20 + posY;
                    int j2 = i + 91 + 6;

                    if (enumhandside == 1) {
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
        if (item != GameData.nullStack()) {
            float f = GameData.getItemAnimationsToGo(item) - partialTicks;

            if (f > 0.0F) {
                GlStateManager.pushMatrix();
                float f1 = 1.0F + f / 5.0F;
                GlStateManager.translate(xPos + 8, yPos + 12, 0.0F);
                GlStateManager.scale(1.0F / f1, (f1 + 1.0F) / 2.0F, 1.0F);
                GlStateManager.translate((-(xPos + 8)), (-(yPos + 12)), 0.0F);
            }

            GameData.renderItemIntoGUI(player, item, xPos, yPos);

            if (f > 0.0F) {
                GlStateManager.popMatrix();
            }

            this.mc.getRenderItem().renderItemOverlays(GameData.getFontRenderer(), item, xPos, yPos);
        }
    }

}
