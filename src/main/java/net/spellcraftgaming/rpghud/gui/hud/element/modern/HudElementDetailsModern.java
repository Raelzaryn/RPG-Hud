package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTippedArrow;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.EnumHand;
import net.spellcraftgaming.rpghud.gui.GuiIngameRPGHud;
import net.spellcraftgaming.rpghud.gui.hud.element.vanilla.HudElementDetailsVanilla;
import net.spellcraftgaming.rpghud.main.ModRPGHud;

public class HudElementDetailsModern extends HudElementDetailsVanilla {

	public HudElementDetailsModern() {
		super();
		this.posX = 0;
		this.posY = 0;
		this.elementWidth = 0;
		this.elementHeight = 0;
		this.moveable = true;
	}

	@Override
	public boolean checkConditions() {
		return true;
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		this.offset = (this.settings.render_player_face ? 0 : 16) +((this.settings.show_numbers_health && this.settings.show_numbers_stamina) ? 0: 8);
		int width = calculateWidth();
		if (gui instanceof GuiIngameRPGHud) {
			if (this.settings.show_armor) {
				drawArmorDetails(gui, width);
			}
			drawItemDetails(gui, EnumHand.MAIN_HAND, width);
			drawItemDetails(gui, EnumHand.OFF_HAND, width);
			if (this.settings.show_arrowcount) {
				drawArrowCount(gui, width);
			}
		}
	}

	/** Calculates the width for the element background */
	private int calculateWidth() {
		int width = 0;
		for (int i = this.mc.thePlayer.inventory.armorInventory.length - 1; i >= 0; i--) {
			if (this.mc.thePlayer.inventory.armorItemInSlot(i) != null && this.mc.thePlayer.inventory.armorItemInSlot(i).getItem() instanceof ItemArmor) {
				this.mc.thePlayer.inventory.armorItemInSlot(i).getMaxDamage();
				ItemStack item = this.mc.thePlayer.inventory.armorItemInSlot(i);
				String s = (item.getMaxDamage() - item.getItemDamage()) + "/" + item.getMaxDamage();
				int widthNew = this.mc.fontRendererObj.getStringWidth(s);
				if(widthNew > width) width = widthNew;
			}
		}
		ItemStack item = this.mc.thePlayer.getHeldItemMainhand();
		if (item != null) {
			if (this.settings.show_itemdurability && item.isItemStackDamageable()) {
				String s = (item.getMaxDamage() - item.getItemDamage()) + "/" + item.getMaxDamage();
				width = this.mc.fontRendererObj.getStringWidth(s);
			} else if (this.settings.show_blockcount && item.getItem() instanceof ItemBlock) {
				int x = Minecraft.getMinecraft().thePlayer.inventory.getSizeInventory();
				int z = 0;
				if (ModRPGHud.renderDetailsAgain[0] || !ItemStack.areItemStacksEqual(this.itemMainHandLast, item)) {
					this.itemMainHandLast = item.copy();
					ModRPGHud.renderDetailsAgain[0] = false;

					for (int y = 0; y < x; y++) {
						item = Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(y);
						if (item != null && Item.getIdFromItem(item.getItem()) == Item.getIdFromItem(this.mc.thePlayer.getHeldItemMainhand().getItem())) {
							z += item.stackSize;
						}
					}
					this.count1 = z;
				} else {
					z = this.count1;
				}

				item = this.mc.thePlayer.getHeldItemMainhand();
				String s = "x " + z;
				int widthNew = this.mc.fontRendererObj.getStringWidth(s);
				if (widthNew > width)
					width = widthNew;
			}
		}
		item = this.mc.thePlayer.getHeldItemOffhand();
		if (item != null) {
			if (this.settings.show_itemdurability && item.isItemStackDamageable()) {
				String s = (item.getMaxDamage() - item.getItemDamage()) + "/" + item.getMaxDamage();
				int widthNew = this.mc.fontRendererObj.getStringWidth(s);
				if (widthNew > width)
					width = widthNew;
			} else if (this.settings.show_blockcount && item.getItem() instanceof ItemBlock) {
				int x = Minecraft.getMinecraft().thePlayer.inventory.getSizeInventory();
				int z = 0;
				if (ModRPGHud.renderDetailsAgain[1] || !ItemStack.areItemStacksEqual(this.itemOffhandLast, item) || !ItemStack.areItemStacksEqual(this.itemMainHandLast, item)) {
					this.itemOffhandLast = item.copy();
					ModRPGHud.renderDetailsAgain[1] = false;
					for (int y = 0; y < x; y++) {
						item = Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(y);
						if (item != null && Item.getIdFromItem(item.getItem()) == Item.getIdFromItem(this.mc.thePlayer.getHeldItemOffhand().getItem())) {
							z += item.stackSize;
						}
					}
					this.count2 = z;
				} else {
					z = this.count2;
				}
				item = this.mc.thePlayer.getHeldItemOffhand();
				String s = "x " + z;
				int widthNew = this.mc.fontRendererObj.getStringWidth(s);
				if (widthNew > width)
					width = widthNew;
			}
		}
		item = this.mc.thePlayer.getHeldItemMainhand();
		if (this.settings.show_arrowcount && item != null && this.mc.thePlayer.getHeldItemMainhand().getItem() instanceof ItemBow) {
			int x = Minecraft.getMinecraft().thePlayer.inventory.getSizeInventory();
			int z = 0;

			if (ModRPGHud.renderDetailsAgain[2] || !ItemStack.areItemStacksEqual(this.itemMainHandLastArrow, item)) {
				ModRPGHud.renderDetailsAgain[2] = false;

				item = findAmmo(this.mc.thePlayer);
				if(item != nullStack) {
					this.itemArrow = item.copy();
					PotionType type1 = null;
					if (item.getItem() instanceof ItemTippedArrow)
						type1 = PotionUtils.getPotionTypeFromNBT(item.getTagCompound());
					for (int y = 0; y < x; y++) {
						ItemStack item3 = Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(y);
						if (ItemStack.areItemsEqual(item, item3)) {
							if (item.getItem() instanceof ItemTippedArrow) {
								PotionType type2 = PotionUtils.getPotionTypeFromNBT(item3.getTagCompound());
								if (type1.getEffects() == type2.getEffects()) {
									z += item3.stackSize;
								}
							} else {
								z += item3.stackSize;
							}
						}
					}
					this.count3 = z;
				}
				this.count3 = 0;
			} else {
				z = this.count3;
			}
			String s = "x " + z;
			int widthNew = this.mc.fontRendererObj.getStringWidth(s);
			if (widthNew > width)
				width = widthNew;
		}
		if(item == nullStack || item == null) {
			this.itemMainHandLastArrow = nullStack;
		} else {
			this.itemMainHandLastArrow = item.copy();
		}

		return width;
	}

	/**
	 * Draws the armor details
	 * 
	 * @param gui
	 *            the GUI to draw one
	 * @param width
	 *            the width of the background
	 */
	protected void drawArmorDetails(Gui gui, int width) {
		for (int i = this.mc.thePlayer.inventory.armorInventory.length - 1; i >= 0; i--) {
			if (this.mc.thePlayer.inventory.armorItemInSlot(i) != null && this.mc.thePlayer.inventory.armorItemInSlot(i).getItem() instanceof ItemArmor) {
				this.mc.thePlayer.inventory.armorItemInSlot(i).getMaxDamage();
				drawRect(2, 30 + this.offset / 2, 10 + 6 + (width / 2), 10, 0xA0000000);
				GlStateManager.scale(0.5D, 0.5D, 0.5D);
				ItemStack item = this.mc.thePlayer.inventory.armorItemInSlot(i);
				String s = (item.getMaxDamage() - item.getItemDamage()) + "/" + item.getMaxDamage();
				this.mc.getRenderItem().renderItemIntoGUI(item, 6, 62 + this.offset);
				RenderHelper.disableStandardItemLighting();
				gui.drawCenteredString(this.mc.fontRendererObj, s, 32 + width / 2, 66 + this.offset, -1);
				GlStateManager.scale(2.0D, 2.0D, 2.0D);
				this.offset += 20;
			}
		}
	}

	/**
	 * Draws the held item details
	 * 
	 * @param gui
	 *            the GUI to draw on
	 * @param hand
	 *            the hand whose item should be detailed
	 * @param width
	 *            the width of the background
	 */
	protected void drawItemDetails(Gui gui, EnumHand hand, int width) {
		ItemStack item = this.mc.thePlayer.getHeldItem(hand);
		if (item != null) {
			if (this.settings.show_itemdurability && item.isItemStackDamageable()) {
				drawRect(2, 30 + this.offset / 2, 10 + 6 + (width / 2), 10, 0xA0000000);
				String s = (item.getMaxDamage() - item.getItemDamage()) + "/" + item.getMaxDamage();
				RenderHelper.enableGUIStandardItemLighting();
				GlStateManager.scale(0.5, 0.5, 0.5);
				this.mc.getRenderItem().renderItemIntoGUI(item, 6, 62 + this.offset);
				RenderHelper.disableStandardItemLighting();
				gui.drawCenteredString(this.mc.fontRendererObj, s, 32 + width / 2, 66 + this.offset, -1);
				GlStateManager.scale(2.0, 2.0, 2.0);
				RenderHelper.disableStandardItemLighting();
				this.offset += 20;

			} else if (this.settings.show_blockcount && item.getItem() instanceof ItemBlock) {
				int x = Minecraft.getMinecraft().thePlayer.inventory.getSizeInventory();
				int z = 0;
				if ((hand == EnumHand.MAIN_HAND ? ModRPGHud.renderDetailsAgain[0] : ModRPGHud.renderDetailsAgain[1]) || !ItemStack.areItemStacksEqual((hand == EnumHand.MAIN_HAND ? this.itemMainHandLast : this.itemOffhandLast), item) || !ItemStack.areItemStacksEqual(this.itemMainHandLast, item)) {
					if (hand == EnumHand.MAIN_HAND) {
						this.itemMainHandLast = item.copy();
						ModRPGHud.renderDetailsAgain[0] = false;
					} else {
						this.itemOffhandLast = item.copy();
						ModRPGHud.renderDetailsAgain[1] = false;
					}
					for (int y = 0; y < x; y++) {
						item = Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(y);
						if (item != null && Item.getIdFromItem(item.getItem()) == Item.getIdFromItem(this.mc.thePlayer.getHeldItem(hand).getItem())) {
							z += item.stackSize;
						}
					}
					if (hand == EnumHand.MAIN_HAND)
						this.count1 = z;
					else
						this.count2 = z;
				} else {
					if (hand == EnumHand.MAIN_HAND)
						z = this.count1;
					else
						z = this.count2;
				}

				item = this.mc.thePlayer.getHeldItem(hand);
				drawRect(2, 30 + this.offset / 2, 10 + 6 + (width / 2), 10, 0xA0000000);
				String s = "x " + z;
				GlStateManager.scale(0.5D, 0.5D, 0.5D);
				RenderHelper.enableGUIStandardItemLighting();
				this.mc.getRenderItem().renderItemIntoGUI(item, 6, 62 + this.offset);
				RenderHelper.disableStandardItemLighting();
				gui.drawCenteredString(this.mc.fontRendererObj, s, 32 + width / 2, 66 + this.offset, -1);
				GlStateManager.scale(2.0D, 2.0D, 2.0D);
				this.offset += 20;
			}
		}
	}

	/**
	 * Draws the amount of arrows the player has in his inventory on the screen
	 * 
	 * @param gui
	 *            the GUI to draw on
	 * @param width
	 *            the width of the background
	 */
	protected void drawArrowCount(Gui gui, int width) {
		ItemStack item = this.mc.thePlayer.getHeldItemMainhand();
		if (this.settings.show_arrowcount && item != null && this.mc.thePlayer.getHeldItemMainhand().getItem() instanceof ItemBow) {
			int x = Minecraft.getMinecraft().thePlayer.inventory.getSizeInventory();
			int z = 0;

			if (ModRPGHud.renderDetailsAgain[2] || !ItemStack.areItemStacksEqual(this.itemMainHandLastArrow, item)) {
				ModRPGHud.renderDetailsAgain[2] = false;

				item = findAmmo(this.mc.thePlayer);
				if(item != nullStack) {
					this.itemArrow = item.copy();
					PotionType type1 = null;
					if (item.getItem() instanceof ItemTippedArrow)
						type1 = PotionUtils.getPotionTypeFromNBT(item.getTagCompound());
					for (int y = 0; y < x; y++) {
						ItemStack item3 = Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(y);
						if (ItemStack.areItemsEqual(item, item3)) {
							if (item.getItem() instanceof ItemTippedArrow) {
								PotionType type2 = PotionUtils.getPotionTypeFromNBT(item3.getTagCompound());
								if (type1.getEffects() == type2.getEffects()) {
									z += item3.stackSize;
								}
							} else {
								z += item3.stackSize;
							}
						}
					}
					this.count3 = z;
				} else {
					this.count3 = 0;
				}
			} else {
				z = this.count3;
			}
			drawRect(2, 30 + this.offset / 2, 10 + 6 + (width / 2), 10, 0xA0000000);
			String s = "x " + z;
			GlStateManager.scale(0.5D, 0.5D, 0.5D);
			RenderHelper.enableGUIStandardItemLighting();
			if (this.itemArrow == nullStack)
				this.itemArrow = new ItemStack(Items.ARROW);
			this.mc.getRenderItem().renderItemIntoGUI(this.itemArrow, 6, 62 + this.offset);
			RenderHelper.disableStandardItemLighting();
			gui.drawCenteredString(this.mc.fontRendererObj, s, 32 + width / 2, 66 + this.offset, -1);
			GlStateManager.scale(2.0D, 2.0D, 2.0D);
			this.offset += 20;

		}
		if(item == nullStack || item == null) {
			this.itemMainHandLastArrow = nullStack;
		} else {
			this.itemMainHandLastArrow = item.copy();
		}
	}

}
