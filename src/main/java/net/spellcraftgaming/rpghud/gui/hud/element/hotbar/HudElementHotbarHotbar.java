package net.spellcraftgaming.rpghud.gui.hud.element.hotbar;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.GameType;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementHotbarHotbar extends HudElement {

    protected static final ResourceLocation WIDGETS_TEX_PATH = new ResourceLocation("textures/gui/widgets.png");

    public HudElementHotbarHotbar() {
        super(HudElementType.HOTBAR, 0, 0, 0, 0, true);
        this.parent = HudElementType.WIDGET;
    }

    @Override
    public void drawElement(Gui gui, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        if(this.mc.playerController.getCurrentGameType() == GameType.SPECTATOR)
            this.mc.ingameGUI.getSpectatorGui().renderTooltip(partialTicks);
        else if(this.mc.getRenderViewEntity() instanceof EntityPlayer) {
            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.mc.getTextureManager().bindTexture(WIDGETS_TEX_PATH);
            EntityPlayer entityplayer = (EntityPlayer) this.mc.getRenderViewEntity();
            ItemStack itemstack = this.mc.player.getHeldItemOffhand();
            int i = scaledWidth / 2;
            float f = zLevel;
            zLevel = -90.0F;
            int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 49 : 25) + this.settings.getPositionValue(Settings.hotbar_position)[0];
            int posY = this.settings.getPositionValue(Settings.hotbar_position)[1];
            gui.drawTexturedModalRect(posX, scaledHeight - 47 + posY, 0, 0, 182, 22);
            gui.drawTexturedModalRect(posX + entityplayer.inventory.currentItem * 20, scaledHeight - 47 - 1 + posY, 0, 22, 24, 22);

            gui.drawTexturedModalRect(posX + 181, scaledHeight - 47 + posY, 60, 23, 22, 22);

            zLevel = f;
            GlStateManager.enableRescaleNormal();
            GlStateManager.enableBlend();
            GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
                    GlStateManager.DestFactor.ZERO);
            RenderHelper.enableGUIStandardItemLighting();

            for(int l = 0; l < 9; ++l) {
                int i1 = posX + 1 + l * 20 + 2;
                int j1 = scaledHeight - 16 - 19 - 9 + posY;
                this.renderHotbarItem(i1, j1, partialTicks, entityplayer, this.mc.player.inventory.mainInventory.get(l));
            }

            int l1 = scaledHeight - 47 + 3 + posY;
            this.renderHotbarItem(posX + 184, l1, partialTicks, entityplayer, itemstack);

            if(this.mc.gameSettings.attackIndicator == 2) {
                float f1 = this.mc.player.getCooledAttackStrength(0.0F);

                if(f1 < 1.0F) {
                    int i2 = scaledHeight - 36 + posY;
                    int j2 = i + 40 + this.settings.getPositionValue(Settings.hotbar_position)[0];

                    this.mc.getTextureManager().bindTexture(Gui.ICONS);
                    int k1 = (int) (f1 * 19.0F);
                    GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
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
    protected void renderHotbarItem(int x, int y, float partialTicks, EntityPlayer player, ItemStack item) {
        if(!item.isEmpty()) {
            float f = item.getAnimationsToGo() - partialTicks;

            if(f > 0.0F) {
                GlStateManager.pushMatrix();
                float f1 = 1.0F + f / 5.0F;
                GlStateManager.translatef(x + 8, y + 12, 0.0F);
                GlStateManager.scalef(1.0F / f1, (f1 + 1.0F) / 2.0F, 1.0F);
                GlStateManager.translatef((-(x + 8)), (-(y + 12)), 0.0F);
            }

            this.mc.getItemRenderer().renderItemAndEffectIntoGUI(player, item, x, y);

            if(f > 0.0F)
                GlStateManager.popMatrix();

            this.mc.getItemRenderer().renderItemOverlays(this.mc.fontRenderer, item, x, y);
        }
    }

}
