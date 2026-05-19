package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.Hand;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.spellcraftgaming.rpghud.gui.hud.element.vanilla.HudElementDetailsVanilla;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(value=EnvType.CLIENT)
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
		return !this.mc.getDebugOverlay().showDebugScreen() && !this.isChatOpen();
	}

	@Override
	public void drawElement(GuiGraphicsExtractor dc, float zLevel, DeltaTracker partialTicks, int scaledWidth, int scaledHeight) {
		this.offset = (this.settings.getBoolValue(Settings.render_player_face) ? 0 : 16) + ((this.settings.getBoolValue(Settings.show_numbers_health) && this.settings.getBoolValue(Settings.show_numbers_food)) ? 0 : 8);
		int width = calculateWidth();
		if (this.settings.getBoolValue(Settings.show_armor)) {
			dc.pose().translate(this.settings.getPositionValue(Settings.armor_det_position)[0], this.settings.getPositionValue(Settings.armor_det_position)[1]);
			drawArmorDetails(dc, width);
			dc.pose().translate(-this.settings.getPositionValue(Settings.armor_det_position)[0], -this.settings.getPositionValue(Settings.armor_det_position)[1]);
		}
		dc.pose().translate(this.settings.getPositionValue(Settings.item_det_position)[0], this.settings.getPositionValue(Settings.item_det_position)[1]);
		drawItemDetails(dc, Hand.MAIN_HAND, width);
		drawItemDetails(dc, Hand.OFF_HAND, width);
		dc.pose().translate(-this.settings.getPositionValue(Settings.item_det_position)[0], -this.settings.getPositionValue(Settings.item_det_position)[1]);
		if (this.settings.getBoolValue(Settings.show_arrow_count)) {
			dc.pose().translate(this.settings.getPositionValue(Settings.arrow_det_position)[0], this.settings.getPositionValue(Settings.arrow_det_position)[1]);
			drawArrowCount(dc, width);
			dc.pose().translate(-this.settings.getPositionValue(Settings.arrow_det_position)[0], -this.settings.getPositionValue(Settings.arrow_det_position)[1]);
		}
	}

	/** Calculates the width for the element background */
	private int calculateWidth() {
		int width = 0;
		for (int i = PlayerInventory.EQUIPMENT_SLOTS.size() +35; i >= 36; i--) {
			if (this.mc.player.getInventory().getItem(i) != ItemStack.EMPTY
					&& this.mc.player.getInventory().getItem(i).contains(DataComponentTypes.DAMAGE)) {
				ItemStack item = this.mc.player.getInventory().getItem(i);
				String s = (item.getMaxDamage() - item.getDamage()) + "/" + item.getMaxDamage();
				int widthNew = this.mc.font.getWidth(s);
				if (widthNew > width)
					width = widthNew;
			}
		}
		ItemStack item = this.mc.player.getMainHandItem();
		if (item != ItemStack.EMPTY) {
			if (this.settings.getBoolValue(Settings.show_item_durability) && item.isDamageable()) {
				String s = (item.getMaxDamage() - item.getDamage()) + "/" + item.getMaxDamage();
				int widthNew = this.mc.font.getWidth(s);
				if (widthNew > width)
					width = widthNew;
			} else if (this.settings.getBoolValue(Settings.show_block_count) && item.getItem() instanceof BlockItem) {
				int x = this.mc.player.getInventory().size();
				int z = 0;
				if (ModRPGHud.renderDetailsAgain[0] || !ItemStack.isSameItem(this.itemMainHandLast, item)) {
					this.itemMainHandLast = item.copy();
					ModRPGHud.renderDetailsAgain[0] = false;

					for (int y = 0; y < x; y++) {
						item = this.mc.player.getInventory().getItem(y);
						if (item != ItemStack.EMPTY && Item.getId(item.getItem()) == Item
								.getId(this.mc.player.getMainHandItem().getItem())) {
							z += item.getCount();
						}
					}
					this.count1 = z;
				} else {
					z = this.count1;
				}

				String s = "x " + z;
				int widthNew = this.mc.font.getWidth(s);
				if (widthNew > width)
					width = widthNew;
			}
		}
		item = this.mc.player.getOffhandItem();
		if (item != ItemStack.EMPTY) {
			if (this.settings.getBoolValue(Settings.show_item_durability) && item.isDamageable()) {
				String s = (item.getMaxDamage() - item.getDamage()) + "/" + item.getMaxDamage();
				int widthNew = this.mc.font.getWidth(s);
				if (widthNew > width)
					width = widthNew;
			} else if (this.settings.getBoolValue(Settings.show_block_count) && item.getItem() instanceof BlockItem) {
				int x = this.mc.player.getInventory().size();
				int z = 0;
				if (ModRPGHud.renderDetailsAgain[1] || !ItemStack.isSameItem(this.itemOffhandLast, item)
						|| !ItemStack.isSameItem(this.itemMainHandLast, item)) {
					this.itemOffhandLast = item.copy();
					ModRPGHud.renderDetailsAgain[1] = false;
					for (int y = 0; y < x; y++) {
						item = this.mc.player.getInventory().getItem(y);
						if (item != ItemStack.EMPTY && Item.getId(item.getItem()) == Item
								.getId(this.mc.player.getOffhandItem().getItem())) {
							z += item.getCount();
						}
					}
					this.count2 = z;
				} else {
					z = this.count2;
				}
				String s = "x " + z;
				int widthNew = this.mc.font.getWidth(s);
				if (widthNew > width)
					width = widthNew;
			}
		}
		item = this.mc.player.getMainHandItem();
		if (this.settings.getBoolValue(Settings.show_arrow_count) && item != ItemStack.EMPTY
				&& this.mc.player.getMainHandItem().getItem() instanceof BowItem) {
			int x = this.mc.player.getInventory().size();
			int z = 0;

			if (ModRPGHud.renderDetailsAgain[2] || !ItemStack.isSameItem(this.itemMainHandLastArrow, item)) {
				ModRPGHud.renderDetailsAgain[2] = false;

				item = findAmmo(this.mc.player);
				if (item != ItemStack.EMPTY) {
					this.itemArrow = item.copy();
					for (int y = 0; y < x; y++) {
						ItemStack item3 = this.mc.player.getInventory().getItem(y);
						if (ItemStack.isSameItem(item, item3)) {
							z += addArrowStackIfCorrect(item, item3);
						}
					}
					this.count3 = z;
				}
				this.count3 = 0;
			} else {
				z = this.count3;
			}
			String s = "x " + z;
			int widthNew = this.mc.font.getWidth(s);
			if (widthNew > width)
				width = widthNew;
		}
		if (item == ItemStack.EMPTY || item == null) {
			this.itemMainHandLastArrow = ItemStack.EMPTY;
		} else {
			this.itemMainHandLastArrow = item.copy();
		}

		return width;
	}

	/**
	 * Draws the armor details
	 * 
	 * @param gui   the GUI to draw one
	 * @param width the width of the background
	 */
	protected void drawArmorDetails(GuiGraphicsExtractor dc, int width) {
		for (int i = PlayerInventory.EQUIPMENT_SLOTS.size() +35; i >= 36; i--) {
			if (this.mc.player.getInventory().getItem(i) != ItemStack.EMPTY
					&& this.mc.player.getInventory().getItem(i).contains(DataComponentTypes.DAMAGE)) {
				drawRect(dc, 2, 30 + this.offset / 2, 10 + 6 + (width / 2), 10, 0xA0000000);
				dc.pose().scale(0.5f, 0.5f);
				ItemStack item = this.mc.player.getInventory().getItem(i);
				String s = (item.getMaxDamage() - item.getDamage()) + "/" + item.getMaxDamage();
				this.renderGuiItemModel(dc, item, 6, 62 + this.offset, false);
				if (this.settings.getBoolValue(Settings.show_durability_bar))
					this.renderItemDurabilityBar(dc, item, 6, 62 + this.offset, 1f);
				dc.text( this.mc.font, s, 32 + width / 2, 66 + this.offset, -1);
				dc.pose().scale(2f, 2f);
				this.offset += 20;
			}
		}
	}

	/**
	 * Draws the held item details
	 * 
	 * @param gui   the GUI to draw on
	 * @param hand  the hand whose item should be detailed
	 * @param width the width of the background
	 */
	protected void drawItemDetails(GuiGraphicsExtractor dc, Hand hand, int width) {
		ItemStack item = this.mc.player.getItemInHand(hand);
		if (item != ItemStack.EMPTY) {
			if (this.settings.getBoolValue(Settings.show_item_durability) && item.isDamageable()) {
				drawRect(dc, 2, 30 + this.offset / 2, 10 + 6 + (width / 2), 10, 0xA0000000);
				String s = (item.getMaxDamage() - item.getDamage()) + "/" + item.getMaxDamage();
				dc.pose().scale(0.5f, 0.5f);
				this.renderGuiItemModel(dc, item, 6, 62 + this.offset, false);
				if (this.settings.getBoolValue(Settings.show_durability_bar))
					this.renderItemDurabilityBar(dc, item, 6, 62 + this.offset, 1f);
				dc.text( this.mc.font, s, 32 + width / 2, 66 + this.offset, -1);
				dc.pose().scale(2f, 2f);
				this.offset += 20;

			} else if (this.settings.getBoolValue(Settings.show_block_count) && item.getItem() instanceof BlockItem) {
				int x = this.mc.player.getInventory().size();
				int z = 0;
				if ((hand == Hand.MAIN_HAND ? ModRPGHud.renderDetailsAgain[0] : ModRPGHud.renderDetailsAgain[1])
						|| !ItemStack.isSameItem(
								(hand == Hand.MAIN_HAND ? this.itemMainHandLast : this.itemOffhandLast), item)
						|| !ItemStack.isSameItem(this.itemMainHandLast, item)) {
					if (hand == Hand.MAIN_HAND) {
						this.itemMainHandLast = item.copy();
						ModRPGHud.renderDetailsAgain[0] = false;
					} else {
						this.itemOffhandLast = item.copy();
						ModRPGHud.renderDetailsAgain[1] = false;
					}
					for (int y = 0; y < x; y++) {
						item = this.mc.player.getInventory().getItem(y);
						if (item != ItemStack.EMPTY && Item.getId(item.getItem()) == Item
								.getRawId(this.mc.player.getItemInHand(hand).getItem())) {
							z += item.getCount();
						}
					}
					if (hand == Hand.MAIN_HAND)
						this.count1 = z;
					else
						this.count2 = z;
				} else {
					if (hand == Hand.MAIN_HAND)
						z = this.count1;
					else
						z = this.count2;
				}

				item = this.mc.player.getItemInHand(hand);
				drawRect(dc, 2, 30 + this.offset / 2, 10 + 6 + (width / 2), 10, 0xA0000000);
				String s = "x " + z;

				dc.pose().scale(0.5f, 0.5f);
				this.renderGuiItemModel(dc, item, 6, 62 + this.offset, false);
				dc.text( this.mc.font, s, 32 + width / 2, 66 + this.offset, -1);
				dc.pose().scale(2f, 2f);
				this.offset += 20;
			}
		}
	}

	/**
	 * Draws the amount of arrows the player has in his inventory on the screen
	 * 
	 * @param gui   the GUI to draw on
	 * @param width the width of the background
	 */
	protected void drawArrowCount(GuiGraphicsExtractor dc, int width) {
		ItemStack item = this.mc.player.getMainHandItem();
		if (this.settings.getBoolValue(Settings.show_arrow_count) && item != ItemStack.EMPTY
				&& this.mc.player.getMainHandItem().getItem() instanceof BowItem) {
			int x = this.mc.player.getInventory().size();
			int z = 0;

			if (ModRPGHud.renderDetailsAgain[2] || !ItemStack.isSameItem(this.itemMainHandLastArrow, item)) {
				ModRPGHud.renderDetailsAgain[2] = false;

				item = findAmmo(this.mc.player);
				if (item != ItemStack.EMPTY) {
					this.itemArrow = item.copy();
					for (int y = 0; y < x; y++) {
						ItemStack item3 = this.mc.player.getInventory().getItem(y);
						if (ItemStack.isSameItem(item, item3)) {
							z += addArrowStackIfCorrect(item, item3);
						}
					}
					this.count3 = z;
				} else {
					this.count3 = 0;
				}
			} else {
				z = this.count3;
			}
			drawRect(dc, 2, 30 + this.offset / 2, 10 + 6 + (width / 2), 10, 0xA0000000);
			String s = "x " + z;
			dc.pose().scale(0.5f, 0.5f);
			if (this.itemArrow == ItemStack.EMPTY)
				this.itemArrow = new ItemStack(Items.ARROW);
			this.renderGuiItemModel(dc, this.itemArrow, 6, 62 + this.offset, false);
			dc.text( this.mc.font, s, 32 + width / 2, 66 + this.offset, -1);
			dc.pose().scale(2f, 2f);
			this.offset += 20;

		}
		if (item == ItemStack.EMPTY || item == null) {
			this.itemMainHandLastArrow = ItemStack.EMPTY;
		} else {
			this.itemMainHandLastArrow = item.copy();
		}
	}

}
