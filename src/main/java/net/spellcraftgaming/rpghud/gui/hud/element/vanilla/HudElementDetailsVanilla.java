package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.settings.Settings;
import org.lwjgl.opengl.GL11;

public class HudElementDetailsVanilla extends HudElement {

    protected int offset = 0;
    protected int count1;
    protected int count2;
    protected int count3;
    protected ItemStack itemMainHandLast = GameData.nullStack();
    protected ItemStack itemOffhandLast = GameData.nullStack();
    protected ItemStack itemMainHandLastArrow = GameData.nullStack();
    protected ItemStack itemArrow = GameData.nullStack();

    public HudElementDetailsVanilla() {
        super(HudElementType.DETAILS, 0, 0, 0, 0, true);
    }

    @Override
    public boolean checkConditions() {
        return GameData.shouldDrawHUD() && !this.mc.gameSettings.showDebugInfo && !this.mc.ingameGUI.getChatGUI().getChatOpen();
    }

    @Override
    public void drawElement(Gui gui, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        this.offset = 0;
        if (this.settings.getBoolValue(Settings.show_armor)) {
            GL11.glTranslated(this.settings.getPositionValue(Settings.armor_det_position)[0], this.settings.getPositionValue(Settings.armor_det_position)[1], 0);
            drawArmorDetails(gui);
            GL11.glTranslated(-this.settings.getPositionValue(Settings.armor_det_position)[0], -this.settings.getPositionValue(Settings.armor_det_position)[1],
                    0);
        }
        GL11.glTranslated(this.settings.getPositionValue(Settings.item_det_position)[0], this.settings.getPositionValue(Settings.item_det_position)[1], 0);
        drawItemDetails(gui, 0);
        drawItemDetails(gui, 1);
        GL11.glTranslated(-this.settings.getPositionValue(Settings.item_det_position)[0], -this.settings.getPositionValue(Settings.item_det_position)[1], 0);
        if (this.settings.getBoolValue(Settings.show_arrow_count)) {
            GL11.glTranslated(this.settings.getPositionValue(Settings.arrow_det_position)[0], this.settings.getPositionValue(Settings.arrow_det_position)[1], 0);
            drawArrowCount(gui);
            GL11.glTranslated(-this.settings.getPositionValue(Settings.arrow_det_position)[0], -this.settings.getPositionValue(Settings.arrow_det_position)[1],
                    0);
        }
    }

    /**
     * Draws the armor details
     *
     * @param gui the GUI to draw one
     */
    protected void drawArmorDetails(Gui gui) {
        this.mc.mcProfiler.startSection("armor_details");
        if (this.settings.getBoolValue(Settings.reduce_size))
            GL11.glScaled(0.5D, 0.5D, 0.5D);
        for (int i = GameData.getPlayerArmorInventoryLength() - 1; i >= 0; i--) {
            if (GameData.getArmorInSlot(i) != GameData.nullStack() && GameData.getArmorInSlot(i).getItem().isDamageable()) {
                ItemStack item = GameData.getArmorInSlot(i);
                String s = (item.getMaxDamage() - item.getItemDamage()) + "/" + item.getMaxDamage();
                this.mc.getRenderItem().renderItemIntoGUI(item, this.settings.getBoolValue(Settings.reduce_size) ? 4 : 2,
                        (this.settings.getBoolValue(Settings.reduce_size) ? 124 : 62) + this.offset);
                if (this.settings.getBoolValue(Settings.show_durability_bar))
                    this.mc.getRenderItem().renderItemOverlays(GameData.getFontRenderer(), item, this.settings.getBoolValue(Settings.reduce_size) ? 4 : 2,
                            (this.settings.getBoolValue(Settings.reduce_size) ? 124 : 62) + this.offset);
                GL11.glDisable(GL11.GL_LIGHTING);
                gui.drawString(GameData.getFontRenderer(), s, 23, (this.settings.getBoolValue(Settings.reduce_size) ? 132 : 66) + this.offset, -1);
                this.offset += 16;
            }
        }
        if (this.settings.getBoolValue(Settings.reduce_size))
            GL11.glScaled(2.0D, 2.0D, 2.0D);
        this.mc.mcProfiler.endSection();
    }

    /**
     * Draws the held item details
     *
     * @param gui  the GUI to draw on
     * @param hand the hand whose item should be detailed
     */
    protected void drawItemDetails(Gui gui, int hand) {
        ItemStack item = GameData.getItemInHand(hand);
        if (item != GameData.nullStack()) {
            if (this.settings.getBoolValue(Settings.show_item_durability) && item.isItemStackDamageable()) {
                if (this.settings.getBoolValue(Settings.reduce_size))
                    GL11.glScaled(0.5D, 0.5D, 0.5D);
                String s = (item.getMaxDamage() - item.getItemDamage()) + "/" + item.getMaxDamage();
                RenderHelper.enableGUIStandardItemLighting();
                this.mc.getRenderItem().renderItemIntoGUI(item, this.settings.getBoolValue(Settings.reduce_size) ? 4 : 2,
                        (this.settings.getBoolValue(Settings.reduce_size) ? 124 : 62) + this.offset);
                if (this.settings.getBoolValue(Settings.show_durability_bar))
                    this.mc.getRenderItem().renderItemOverlays(GameData.getFontRenderer(), item, this.settings.getBoolValue(Settings.reduce_size) ? 4 : 2,
                            (this.settings.getBoolValue(Settings.reduce_size) ? 124 : 62) + this.offset);
                GL11.glDisable(GL11.GL_LIGHTING);
                gui.drawString(GameData.getFontRenderer(), s, 23, (this.settings.getBoolValue(Settings.reduce_size) ? 132 : 66) + this.offset, -1);
                RenderHelper.disableStandardItemLighting();
                this.offset += 16;
                if (this.settings.getBoolValue(Settings.reduce_size))
                    GL11.glScaled(2.0D, 2.0D, 2.0D);
            } else if (this.settings.getBoolValue(Settings.show_block_count) && item.getItem() instanceof ItemBlock) {
                int x = GameData.getInventorySize();
                int z = 0;
                if ((hand == 0 ? ModRPGHud.renderDetailsAgain[0] : ModRPGHud.renderDetailsAgain[1])
                        || !ItemStack.areItemStacksEqual((hand == 0 ? this.itemMainHandLast : this.itemOffhandLast), item)
                        || !ItemStack.areItemStacksEqual(this.itemMainHandLast, item)) {
                    if (hand == 0) {
                        this.itemMainHandLast = item.copy();
                        ModRPGHud.renderDetailsAgain[0] = false;
                    } else {
                        this.itemOffhandLast = item.copy();
                        ModRPGHud.renderDetailsAgain[1] = false;
                    }
                    for (int y = 0; y < x; y++) {
                        item = GameData.getItemInSlot(y);
                        if (item != GameData.nullStack() && Item.getIdFromItem(item.getItem()) == Item.getIdFromItem(GameData.getItemInHand(hand).getItem())) {
                            z += GameData.getItemStackSize(item);
                        }
                    }
                    if (hand == 0)
                        this.count1 = z;
                    else
                        this.count2 = z;
                } else {
                    if (hand == 0)
                        z = this.count1;
                    else
                        z = this.count2;
                }

                item = GameData.getItemInHand(hand);
                String s = "x " + z;
                if (this.settings.getBoolValue(Settings.reduce_size))
                    GL11.glScaled(0.5D, 0.5D, 0.5D);
                RenderHelper.enableGUIStandardItemLighting();
                this.mc.getRenderItem().renderItemIntoGUI(item, this.settings.getBoolValue(Settings.reduce_size) ? 4 : 2,
                        (this.settings.getBoolValue(Settings.reduce_size) ? 124 : 62) + this.offset);
                if (this.settings.getBoolValue(Settings.show_durability_bar))
                    this.mc.getRenderItem().renderItemOverlays(GameData.getFontRenderer(), item, this.settings.getBoolValue(Settings.reduce_size) ? 4 : 2,
                            (this.settings.getBoolValue(Settings.reduce_size) ? 124 : 62) + this.offset);
                RenderHelper.disableStandardItemLighting();
                GL11.glDisable(GL11.GL_LIGHTING);
                gui.drawString(GameData.getFontRenderer(), s, 23, (this.settings.getBoolValue(Settings.reduce_size) ? 132 : 66) + this.offset, -1);
                if (this.settings.getBoolValue(Settings.reduce_size))
                    GL11.glScaled(2.0D, 2.0D, 2.0D);
                this.offset += 16;
            }
        }
    }

    /**
     * Draws the amount of arrows the player has in his inventory on the screen
     *
     * @param gui the GUI to draw on
     */
    protected void drawArrowCount(Gui gui) {
        ItemStack item = GameData.getMainhand();
        if (this.settings.getBoolValue(Settings.show_arrow_count) && item != GameData.nullStack() && GameData.getMainhand().getItem() instanceof ItemBow) {
            int x = GameData.getInventorySize();
            int z = 0;

            if (ModRPGHud.renderDetailsAgain[2] || !ItemStack.areItemStacksEqual(this.itemMainHandLastArrow, item)) {
                ModRPGHud.renderDetailsAgain[2] = false;

                item = findAmmo(GameData.getPlayer());
                if (item != GameData.nullStack()) {
                    this.itemArrow = item.copy();
                    for (int y = 0; y < x; y++) {
                        ItemStack item3 = GameData.getItemInSlot(y);
                        if (ItemStack.areItemsEqual(item, item3)) {
                            z += GameData.addArrowStackIfCorrect(item, item3);
                        }
                    }
                    this.count3 = z;
                } else {
                    this.count3 = 0;
                }
            } else {
                z = this.count3;
            }

            String s = "x " + z;
            if (this.settings.getBoolValue(Settings.reduce_size))
                GL11.glScaled(0.5D, 0.5D, 0.5D);
            RenderHelper.enableGUIStandardItemLighting();
            if (this.itemArrow == GameData.nullStack()) {
                this.itemArrow = GameData.arrowStack();
            }

            this.mc.getRenderItem().renderItemIntoGUI(this.itemArrow, this.settings.getBoolValue(Settings.reduce_size) ? 4 : 2,
                    (this.settings.getBoolValue(Settings.reduce_size) ? 124 : 62) + this.offset);
            if (this.settings.getBoolValue(Settings.show_durability_bar))
                this.mc.getRenderItem().renderItemOverlays(GameData.getFontRenderer(), this.itemArrow, this.settings.getBoolValue(Settings.reduce_size) ? 4 : 2,
                        (this.settings.getBoolValue(Settings.reduce_size) ? 124 : 62) + this.offset);
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(GL11.GL_LIGHTING);
            gui.drawString(GameData.getFontRenderer(), s, 23, (this.settings.getBoolValue(Settings.reduce_size) ? 132 : 66) + this.offset, -1);
            if (this.settings.getBoolValue(Settings.reduce_size))
                GL11.glScaled(2.0D, 2.0D, 2.0D);
            this.offset += 16;

        }
        if (item == GameData.nullStack() || item == null) {
            this.itemMainHandLastArrow = GameData.nullStack();
        } else {
            this.itemMainHandLastArrow = item.copy();
        }
    }

    /**
     * checks if the player has arrows in his inventory and picks the one the bow
     * would fire
     *
     * @param player the player to search for arrow
     * @return returns the ItemStack of the arrow. If none can be found returns
     * ItemStack.EMPTY
     */
    protected static ItemStack findAmmo(EntityPlayer player) {
        if (GameData.isArrow(GameData.getOffhand())) {
            return GameData.getOffhand();
        } else if (GameData.isArrow(GameData.getMainhand())) {
            return GameData.getMainhand();
        } else {
            for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
                ItemStack itemstack = player.inventory.getStackInSlot(i);

                if (GameData.isArrow(itemstack)) {
                    return itemstack;
                }
            }

            return GameData.nullStack();
        }
    }

}
