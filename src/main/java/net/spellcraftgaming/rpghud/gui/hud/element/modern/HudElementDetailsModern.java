package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.lib.gui.override.GuiIngameRPGHud;
import net.spellcraftgaming.rpghud.gui.hud.element.vanilla.HudElementDetailsVanilla;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.settings.Settings;

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
		return GameData.shouldDrawHUD() && !this.mc.gameSettings.showDebugInfo && !((GuiIngameRPGHud) this.mc.ingameGUI).getChat().getChatOpen();
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		this.offset = (this.settings.getBoolValue(Settings.render_player_face) ? 0 : 16) + ((this.settings.getBoolValue(Settings.show_numbers_health) && this.settings.getBoolValue(Settings.show_numbers_food)) ? 0 : 8);
		int width = calculateWidth();
		if (gui instanceof GuiIngameRPGHud) {
			if (this.settings.getBoolValue(Settings.show_armor)) {
				drawArmorDetails(gui, width);
			}
			drawItemDetails(gui, 0, width);
			drawItemDetails(gui, 1, width);
			if (this.settings.getBoolValue(Settings.show_arrow_count)) {
				drawArrowCount(gui, width);
			}
		}
	}

	/** Calculates the width for the element background */
	private int calculateWidth() {
		int width = 0;
		for (int i = GameData.getPlayerArmorInventoryLength() - 1; i >= 0; i--) {
			if (GameData.getArmorInSlot(i) != GameData.nullStack() && GameData.getArmorInSlot(i).getItem().isDamageable()) {
				ItemStack item = GameData.getArmorInSlot(i);
				String s = (item.getMaxDamage() - item.getItemDamage()) + "/" + item.getMaxDamage();
				int widthNew = GameData.getFontRenderer().getStringWidth(s);
				if (widthNew > width)
					width = widthNew;
			}
		}
		ItemStack item = GameData.getMainhand();
		if (item != GameData.nullStack()) {
			if (this.settings.getBoolValue(Settings.show_item_durability) && item.isItemStackDamageable()) {
				String s = (item.getMaxDamage() - item.getItemDamage()) + "/" + item.getMaxDamage();
				width = GameData.getFontRenderer().getStringWidth(s);
			} else if (this.settings.getBoolValue(Settings.show_block_count) && item.getItem() instanceof ItemBlock) {
				int x = GameData.getInventorySize();
				int z = 0;
				if (ModRPGHud.renderDetailsAgain[0] || !ItemStack.areItemStacksEqual(this.itemMainHandLast, item)) {
					this.itemMainHandLast = item.copy();
					ModRPGHud.renderDetailsAgain[0] = false;

					for (int y = 0; y < x; y++) {
						item = GameData.getItemInSlot(y);
						if (item != GameData.nullStack() && Item.getIdFromItem(item.getItem()) == Item.getIdFromItem(GameData.getMainhand().getItem())) {
							z += GameData.getItemStackSize(item);
						}
					}
					this.count1 = z;
				} else {
					z = this.count1;
				}

				item = GameData.getMainhand();
				String s = "x " + z;
				int widthNew = GameData.getFontRenderer().getStringWidth(s);
				if (widthNew > width)
					width = widthNew;
			}
		}
		item = GameData.getOffhand();
		if (item != GameData.nullStack()) {
			if (this.settings.getBoolValue(Settings.show_item_durability) && item.isItemStackDamageable()) {
				String s = (item.getMaxDamage() - item.getItemDamage()) + "/" + item.getMaxDamage();
				int widthNew = GameData.getFontRenderer().getStringWidth(s);
				if (widthNew > width)
					width = widthNew;
			} else if (this.settings.getBoolValue(Settings.show_block_count) && item.getItem() instanceof ItemBlock) {
				int x = GameData.getInventorySize();
				int z = 0;
				if (ModRPGHud.renderDetailsAgain[1] || !ItemStack.areItemStacksEqual(this.itemOffhandLast, item) || !ItemStack.areItemStacksEqual(this.itemMainHandLast, item)) {
					this.itemOffhandLast = item.copy();
					ModRPGHud.renderDetailsAgain[1] = false;
					for (int y = 0; y < x; y++) {
						item = GameData.getItemInSlot(y);
						if (item != GameData.nullStack() && Item.getIdFromItem(item.getItem()) == Item.getIdFromItem(GameData.getOffhand().getItem())) {
							z += GameData.getItemStackSize(item);
						}
					}
					this.count2 = z;
				} else {
					z = this.count2;
				}
				item = GameData.getOffhand();
				String s = "x " + z;
				int widthNew = GameData.getFontRenderer().getStringWidth(s);
				if (widthNew > width)
					width = widthNew;
			}
		}
		item = GameData.getMainhand();
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
				}
				this.count3 = 0;
			} else {
				z = this.count3;
			}
			String s = "x " + z;
			int widthNew = GameData.getFontRenderer().getStringWidth(s);
			if (widthNew > width)
				width = widthNew;
		}
		if (item == GameData.nullStack() || item == null) {
			this.itemMainHandLastArrow = GameData.nullStack();
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
		for (int i = GameData.getPlayerArmorInventoryLength() - 1; i >= 0; i--) {
			if (GameData.getArmorInSlot(i) != GameData.nullStack() && GameData.getArmorInSlot(i).getItem().isDamageable()) {
				drawRect(2, 30 + this.offset / 2, 10 + 6 + (width / 2), 10, 0xA0000000);
				GlStateManager.scale(0.5D, 0.5D, 0.5D);
				ItemStack item = GameData.getArmorInSlot(i);
				String s = (item.getMaxDamage() - item.getItemDamage()) + "/" + item.getMaxDamage();
				this.mc.getRenderItem().renderItemIntoGUI(item, 6, 62 + this.offset);
				if(this.settings.getBoolValue(Settings.show_durability_bar)) this.mc.getRenderItem().renderItemOverlays(GameData.getFontRenderer(), item, 6, 62 + this.offset);
				RenderHelper.disableStandardItemLighting();
				gui.drawCenteredString(GameData.getFontRenderer(), s, 32 + width / 2, 66 + this.offset, -1);
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
	protected void drawItemDetails(Gui gui, int hand, int width) {
		ItemStack item = GameData.getItemInHand(hand);
		if (item != GameData.nullStack()) {
			if (this.settings.getBoolValue(Settings.show_item_durability) && item.isItemStackDamageable()) {
				drawRect(2, 30 + this.offset / 2, 10 + 6 + (width / 2), 10, 0xA0000000);
				String s = (item.getMaxDamage() - item.getItemDamage()) + "/" + item.getMaxDamage();
				RenderHelper.enableGUIStandardItemLighting();
				GlStateManager.scale(0.5, 0.5, 0.5);
				this.mc.getRenderItem().renderItemIntoGUI(item, 6, 62 + this.offset);
				if(this.settings.getBoolValue(Settings.show_durability_bar)) this.mc.getRenderItem().renderItemOverlays(GameData.getFontRenderer(), item, 6, 62 + this.offset);
				RenderHelper.disableStandardItemLighting();
				gui.drawCenteredString(GameData.getFontRenderer(), s, 32 + width / 2, 66 + this.offset, -1);
				GlStateManager.scale(2.0, 2.0, 2.0);
				RenderHelper.disableStandardItemLighting();
				this.offset += 20;

			} else if (this.settings.getBoolValue(Settings.show_block_count) && item.getItem() instanceof ItemBlock) {
				int x = GameData.getInventorySize();
				int z = 0;
				if ((hand == 0 ? ModRPGHud.renderDetailsAgain[0] : ModRPGHud.renderDetailsAgain[1]) || !ItemStack.areItemStacksEqual((hand == 0 ? this.itemMainHandLast : this.itemOffhandLast), item) || !ItemStack.areItemStacksEqual(this.itemMainHandLast, item)) {
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
				drawRect(2, 30 + this.offset / 2, 10 + 6 + (width / 2), 10, 0xA0000000);
				String s = "x " + z;
				GlStateManager.scale(0.5D, 0.5D, 0.5D);
				RenderHelper.enableGUIStandardItemLighting();
				this.mc.getRenderItem().renderItemIntoGUI(item, 6, 62 + this.offset);
				if(this.settings.getBoolValue(Settings.show_durability_bar)) this.mc.getRenderItem().renderItemOverlays(GameData.getFontRenderer(), item, 6, 62 + this.offset);
				RenderHelper.disableStandardItemLighting();
				gui.drawCenteredString(GameData.getFontRenderer(), s, 32 + width / 2, 66 + this.offset, -1);
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
			drawRect(2, 30 + this.offset / 2, 10 + 6 + (width / 2), 10, 0xA0000000);
			String s = "x " + z;
			GlStateManager.scale(0.5D, 0.5D, 0.5D);
			RenderHelper.enableGUIStandardItemLighting();
			if (this.itemArrow == GameData.nullStack())
				this.itemArrow = GameData.arrowStack();
			this.mc.getRenderItem().renderItemIntoGUI(this.itemArrow, 6, 62 + this.offset);
			if(this.settings.getBoolValue(Settings.show_durability_bar)) this.mc.getRenderItem().renderItemOverlays(GameData.getFontRenderer(), this.itemArrow, 6, 62 + this.offset);
			RenderHelper.disableStandardItemLighting();
			gui.drawCenteredString(GameData.getFontRenderer(), s, 32 + width / 2, 66 + this.offset, -1);
			GlStateManager.scale(2.0D, 2.0D, 2.0D);
			this.offset += 20;

		}
		if (item == GameData.nullStack() || item == null) {
			this.itemMainHandLastArrow = GameData.nullStack();
		} else {
			this.itemMainHandLastArrow = item.copy();
		}
	}

}
