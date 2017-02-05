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
		this.offset = 0;
		int width = calculateWidth();
		if (gui instanceof GuiIngameRPGHud) {
			if (this.settings.show_armor) {
				drawArmorDetails(gui, width);
			}
			drawItemDetails(gui, width);
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
				width = this.mc.fontRendererObj.getStringWidth(s);
			}
		}
		ItemStack item = this.mc.thePlayer.getHeldItem();
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
						if (item != null && Item.getIdFromItem(item.getItem()) == Item.getIdFromItem(this.mc.thePlayer.getHeldItem().getItem())) {
							z += item.stackSize;
						}
					}
					this.count1 = z;
				} else {
					z = this.count1;
				}

				item = this.mc.thePlayer.getHeldItem();
				String s = "x " + z;
				int widthNew = this.mc.fontRendererObj.getStringWidth(s);
				if (widthNew > width)
					width = widthNew;
			}
		}
		item = this.mc.thePlayer.getHeldItem();
		if (this.settings.show_arrowcount && item != null && this.mc.thePlayer.getHeldItem().getItem() instanceof ItemBow) {
			int x = Minecraft.getMinecraft().thePlayer.inventory.getSizeInventory();
			int z = 0;

			if (ModRPGHud.renderDetailsAgain[2] || !ItemStack.areItemStacksEqual(this.itemMainHandLastArrow, item)) {
				ModRPGHud.renderDetailsAgain[2] = false;

				item = findAmmo(this.mc.thePlayer);
				if(item != null) {
					this.itemArrow = item.copy();
					for (int y = 0; y < x; y++) {
						ItemStack item3 = Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(y);
						if (ItemStack.areItemsEqual(item, item3)) {
								z += item3.stackSize;
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
		if(item == null) {
			this.itemMainHandLastArrow = null;
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
	protected void drawItemDetails(Gui gui, int width) {
		ItemStack item = this.mc.thePlayer.getHeldItem();
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
				if (ModRPGHud.renderDetailsAgain[0] || !ItemStack.areItemStacksEqual(this.itemMainHandLast, item) || !ItemStack.areItemStacksEqual(this.itemMainHandLast, item)) {
						this.itemMainHandLast = item.copy();
						ModRPGHud.renderDetailsAgain[0] = false;
					for (int y = 0; y < x; y++) {
						item = Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(y);
						if (item != null && Item.getIdFromItem(item.getItem()) == Item.getIdFromItem(this.mc.thePlayer.getHeldItem().getItem())) {
							z += item.stackSize;
						}
					}
					this.count1 = z;
				} else {
					z = this.count1;
				}

				item = this.mc.thePlayer.getHeldItem();
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
		ItemStack item = this.mc.thePlayer.getHeldItem();
		if (this.settings.show_arrowcount && item != null && this.mc.thePlayer.getHeldItem().getItem() instanceof ItemBow) {
			int x = Minecraft.getMinecraft().thePlayer.inventory.getSizeInventory();
			int z = 0;

			if (ModRPGHud.renderDetailsAgain[2] || !ItemStack.areItemStacksEqual(this.itemMainHandLastArrow, item)) {
				ModRPGHud.renderDetailsAgain[2] = false;

				item = findAmmo(this.mc.thePlayer);
				if(item != null) {
					this.itemArrow = item.copy();
					for (int y = 0; y < x; y++) {
						ItemStack item3 = Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(y);
						if (ItemStack.areItemsEqual(item, item3)) {
							z += item3.stackSize;
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
			if (this.itemArrow == null)
				this.itemArrow = new ItemStack(Items.arrow);
			this.mc.getRenderItem().renderItemIntoGUI(item, 6, 62 + this.offset);
			RenderHelper.disableStandardItemLighting();
			gui.drawCenteredString(this.mc.fontRendererObj, s, 32 + width / 2, 66 + this.offset, -1);
			GlStateManager.scale(2.0D, 2.0D, 2.0D);
			this.offset += 20;

		}
		if(item == null) {
			this.itemMainHandLastArrow = null;
		} else {
			this.itemMainHandLastArrow = item.copy();
		}
	}

}
