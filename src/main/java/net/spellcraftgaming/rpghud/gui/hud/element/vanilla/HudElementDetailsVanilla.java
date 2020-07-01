package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTippedArrow;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.settings.Settings;

@OnlyIn(Dist.CLIENT)
public class HudElementDetailsVanilla extends HudElement {

    protected int offset = 0;
    protected int typeOffset = 0;
    protected int count1;
    protected int count2;
    protected int count3;
    protected ItemStack itemMainHandLast = ItemStack.EMPTY;
    protected ItemStack itemOffhandLast = ItemStack.EMPTY;
    protected ItemStack itemMainHandLastArrow = ItemStack.EMPTY;
    protected ItemStack itemArrow = ItemStack.EMPTY;

    public HudElementDetailsVanilla() {
        super(HudElementType.DETAILS, 0, 0, 0, 0, true);
    }

    @Override
    public boolean checkConditions() {
        return !this.mc.gameSettings.hideGUI && !this.mc.gameSettings.showDebugInfo && !this.mc.ingameGUI.getChatGUI().getChatOpen();
    }

    @Override
    public void drawElement(Gui gui, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        this.offset = 0;
        if(this.settings.getBoolValue(Settings.show_armor)) {
            GL11.glTranslated(this.settings.getPositionValue(Settings.armor_det_position)[0], this.settings.getPositionValue(Settings.armor_det_position)[1], 0);
            this.drawArmorDetails(gui);
            GL11.glTranslated(-this.settings.getPositionValue(Settings.armor_det_position)[0], -this.settings.getPositionValue(Settings.armor_det_position)[1], 0);
        }
        GL11.glTranslated(this.settings.getPositionValue(Settings.item_det_position)[0], this.settings.getPositionValue(Settings.item_det_position)[1], 0);
        this.drawItemDetails(gui, 0);
        this.drawItemDetails(gui, 1);
        GL11.glTranslated(-this.settings.getPositionValue(Settings.item_det_position)[0], -this.settings.getPositionValue(Settings.item_det_position)[1], 0);
        if(this.settings.getBoolValue(Settings.show_arrow_count)) {
            GL11.glTranslated(this.settings.getPositionValue(Settings.arrow_det_position)[0], this.settings.getPositionValue(Settings.arrow_det_position)[1], 0);
            this.drawArrowCount(gui);
            GL11.glTranslated(-this.settings.getPositionValue(Settings.arrow_det_position)[0], -this.settings.getPositionValue(Settings.arrow_det_position)[1], 0);
        }
    }

    /**
     * Draws the armor details
     * 
     * @param gui the GUI to draw one
     */
    protected void drawArmorDetails(Gui gui) {
        this.mc.profiler.startSection("armor_details");
        if(this.settings.getBoolValue(Settings.reduce_size))
            GL11.glScaled(0.5D, 0.5D, 0.5D);
        for(int i = this.mc.player.inventory.armorInventory.size() - 1; i >= 0; i--)
            if(this.mc.player.inventory.armorItemInSlot(i) != ItemStack.EMPTY && this.mc.player.inventory.armorItemInSlot(i).getItem().isDamageable()) {
                ItemStack item = this.mc.player.inventory.armorItemInSlot(i);
                String s = (item.getMaxDamage() - item.getDamage()) + "/" + item.getMaxDamage();
                this.mc.getItemRenderer().renderItemIntoGUI(item, this.settings.getBoolValue(Settings.reduce_size) ? 4 : 2,
                        (this.settings.getBoolValue(Settings.reduce_size) ? 124 + (this.typeOffset * 2) : 62 + this.typeOffset) + this.offset);
                if(this.settings.getBoolValue(Settings.show_durability_bar))
                    this.mc.getItemRenderer().renderItemOverlays(this.mc.fontRenderer, item, this.settings.getBoolValue(Settings.reduce_size) ? 4 : 2,
                            (this.settings.getBoolValue(Settings.reduce_size) ? 124 + this.typeOffset * 2 : 62 + this.typeOffset) + this.offset);
                GL11.glDisable(GL11.GL_LIGHTING);
                gui.drawString(this.mc.fontRenderer, s, 23,
                        (this.settings.getBoolValue(Settings.reduce_size) ? 132 + (this.typeOffset * 2) : 66 + this.typeOffset) + this.offset, -1);
                this.offset += 16;
            }
        if(this.settings.getBoolValue(Settings.reduce_size))
            GL11.glScaled(2.0D, 2.0D, 2.0D);
        this.mc.profiler.endSection();
    }

    /**
     * Draws the held item details
     * 
     * @param gui  the GUI to draw on
     * @param hand the hand whose item should be detailed
     */
    protected void drawItemDetails(Gui gui, int hand) {
        ItemStack item = getItemInHand(hand);
        if(item != ItemStack.EMPTY)
            if(this.settings.getBoolValue(Settings.show_item_durability) && item.isDamageable()) {
                if(this.settings.getBoolValue(Settings.reduce_size))
                    GL11.glScaled(0.5D, 0.5D, 0.5D);
                String s = (item.getMaxDamage() - item.getDamage()) + "/" + item.getMaxDamage();
                RenderHelper.enableGUIStandardItemLighting();
                this.mc.getItemRenderer().renderItemIntoGUI(item, this.settings.getBoolValue(Settings.reduce_size) ? 4 : 2,
                        (this.settings.getBoolValue(Settings.reduce_size) ? 124 + this.typeOffset * 2 : 62 + this.typeOffset) + this.offset);
                if(this.settings.getBoolValue(Settings.show_durability_bar))
                    this.mc.getItemRenderer().renderItemOverlays(this.mc.fontRenderer, item, this.settings.getBoolValue(Settings.reduce_size) ? 4 : 2,
                            (this.settings.getBoolValue(Settings.reduce_size) ? 124 + this.typeOffset * 2 : 62 + this.typeOffset) + this.offset);
                GL11.glDisable(GL11.GL_LIGHTING);
                gui.drawString(this.mc.fontRenderer, s, 23,
                        (this.settings.getBoolValue(Settings.reduce_size) ? 132 + this.typeOffset * 2 : 66 + this.typeOffset) + this.offset, -1);
                RenderHelper.disableStandardItemLighting();
                this.offset += 16;
                if(this.settings.getBoolValue(Settings.reduce_size))
                    GL11.glScaled(2.0D, 2.0D, 2.0D);
            } else if(this.settings.getBoolValue(Settings.show_block_count) && item.getItem() instanceof ItemBlock) {
                int x = this.mc.player.inventory.getSizeInventory();
                int z = 0;
                if((hand == 0 ? ModRPGHud.renderDetailsAgain[0] : ModRPGHud.renderDetailsAgain[1])
                        || !ItemStack.areItemStacksEqual((hand == 0 ? this.itemMainHandLast : this.itemOffhandLast), item)
                        || !ItemStack.areItemStacksEqual(this.itemMainHandLast, item)) {
                    if(hand == 0) {
                        this.itemMainHandLast = item.copy();
                        ModRPGHud.renderDetailsAgain[0] = false;
                    } else {
                        this.itemOffhandLast = item.copy();
                        ModRPGHud.renderDetailsAgain[1] = false;
                    }
                    for(int y = 0; y < x; y++) {
                        item = this.mc.player.inventory.getStackInSlot(y);
                        if(item != ItemStack.EMPTY && Item.getIdFromItem(item.getItem()) == Item.getIdFromItem(getItemInHand(hand).getItem()))
                            z += item.getCount();
                    }
                    if(hand == 0)
                        this.count1 = z;
                    else
                        this.count2 = z;
                } else if(hand == 0)
                    z = this.count1;
                else
                    z = this.count2;

                item = getItemInHand(hand);
                String s = "x " + z;
                if(this.settings.getBoolValue(Settings.reduce_size))
                    GL11.glScaled(0.5D, 0.5D, 0.5D);
                RenderHelper.enableGUIStandardItemLighting();
                this.mc.getItemRenderer().renderItemIntoGUI(item, this.settings.getBoolValue(Settings.reduce_size) ? 4 : 2,
                        (this.settings.getBoolValue(Settings.reduce_size) ? 124 + this.typeOffset * 2 : 62 + this.typeOffset) + this.offset);
                RenderHelper.disableStandardItemLighting();
                GL11.glDisable(GL11.GL_LIGHTING);
                gui.drawString(this.mc.fontRenderer, s, 23,
                        (this.settings.getBoolValue(Settings.reduce_size) ? 132 + this.typeOffset * 2 : 66 + this.typeOffset) + this.offset, -1);
                if(this.settings.getBoolValue(Settings.reduce_size))
                    GL11.glScaled(2.0D, 2.0D, 2.0D);
                this.offset += 16;
            }
    }

    /**
     * Draws the amount of arrows the player has in his inventory on the screen
     * 
     * @param gui the GUI to draw on
     */
    protected void drawArrowCount(Gui gui) {
        ItemStack item = this.mc.player.getHeldItemMainhand();
        if(this.settings.getBoolValue(Settings.show_arrow_count) && item != ItemStack.EMPTY && item.getItem() instanceof ItemBow) {
            int x = this.mc.player.inventory.getSizeInventory();
            int z = 0;

            if(ModRPGHud.renderDetailsAgain[2] || !ItemStack.areItemStacksEqual(this.itemMainHandLastArrow, item)) {
                ModRPGHud.renderDetailsAgain[2] = false;

                item = findAmmo(this.mc.player);
                if(item != ItemStack.EMPTY) {
                    this.itemArrow = item.copy();
                    for(int y = 0; y < x; y++) {
                        ItemStack item3 = this.mc.player.inventory.getStackInSlot(y);
                        if(ItemStack.areItemsEqual(item, item3))
                            z += addArrowStackIfCorrect(item, item3);
                    }
                    this.count3 = z;
                } else
                    this.count3 = 0;
            } else
                z = this.count3;

            String s = "x " + z;
            if(this.settings.getBoolValue(Settings.reduce_size))
                GL11.glScaled(0.5D, 0.5D, 0.5D);
            RenderHelper.enableGUIStandardItemLighting();
            if(this.itemArrow == ItemStack.EMPTY)
                this.itemArrow = new ItemStack(Items.ARROW);

            this.mc.getItemRenderer().renderItemIntoGUI(this.itemArrow, this.settings.getBoolValue(Settings.reduce_size) ? 4 : 2,
                    (this.settings.getBoolValue(Settings.reduce_size) ? 124 + this.typeOffset * 2 : 62 + this.typeOffset) + this.offset);
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(GL11.GL_LIGHTING);
            gui.drawString(this.mc.fontRenderer, s, 23,
                    (this.settings.getBoolValue(Settings.reduce_size) ? 132 + this.typeOffset * 2 : 66 + this.typeOffset) + this.offset, -1);
            if(this.settings.getBoolValue(Settings.reduce_size))
                GL11.glScaled(2.0D, 2.0D, 2.0D);
            this.offset += 16;

        }
        if(item == ItemStack.EMPTY || item == null)
            this.itemMainHandLastArrow = ItemStack.EMPTY;
        else
            this.itemMainHandLastArrow = item.copy();
    }

    /**
     * checks if the player has arrows in his inventory and picks the one the bow
     * would fire
     * 
     * @param player the player to search for arrow
     * 
     * @return returns the ItemStack of the arrow. If none can be found returns
     *         ItemStack.EMPTY
     */
    protected static ItemStack findAmmo(EntityPlayer player) {
        if(isArrow(Minecraft.getInstance().player.getHeldItemOffhand()))
            return Minecraft.getInstance().player.getHeldItemOffhand();
        else if(isArrow(Minecraft.getInstance().player.getHeldItemMainhand()))
            return Minecraft.getInstance().player.getHeldItemMainhand();
        else {
            for(int i = 0; i < player.inventory.getSizeInventory(); ++i) {
                ItemStack itemstack = player.inventory.getStackInSlot(i);

                if(isArrow(itemstack))
                    return itemstack;
            }

            return ItemStack.EMPTY;
        }
    }

    public static ItemStack getItemInHand(int hand) {
        if(hand == 0)
            return Minecraft.getInstance().player.getHeldItemMainhand();
        else if(hand == 1)
            return Minecraft.getInstance().player.getHeldItemOffhand();
        else
            return ItemStack.EMPTY;
    }

    public static int getOffhandSide() {
        if(Minecraft.getInstance().player.getPrimaryHand() == EnumHandSide.RIGHT)
            return 0;
        else
            return 1;
    }

    public static boolean isArrow(ItemStack item) {
        if(item != ItemStack.EMPTY)
            return ItemStack.areItemsEqual(item, new ItemStack(Items.ARROW));

        return false;
    }

    public static int addArrowStackIfCorrect(ItemStack item, ItemStack arrow) {
        PotionType type1 = null;
        if(item.getItem() instanceof ItemTippedArrow)
            type1 = PotionUtils.getPotionTypeFromNBT(item.getTag());
        if(item.getItem() instanceof ItemTippedArrow) {
            PotionType type2 = PotionUtils.getPotionTypeFromNBT(arrow.getTag());
            if(type1.getEffects() == type2.getEffects())
                return arrow.getCount();
        } else
            return arrow.getCount();

        return arrow.getCount();
    }

}
