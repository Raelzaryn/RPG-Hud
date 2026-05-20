package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.TextAlignment;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.*;
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
		return !this.mc.debugEntries.isOverlayVisible() && !this.isChatOpen();
	}

	@Override
    public void drawElement(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, int scaledWidth, int scaledHeight) {
		this.offset = (this.settings.getBoolValue(Settings.render_player_face) ? 0 : 16) + ((this.settings.getBoolValue(Settings.show_numbers_health) && this.settings.getBoolValue(Settings.show_numbers_food)) ? 0 : 8);
		int width = calculateWidth();
		if (this.settings.getBoolValue(Settings.show_armor)) {
            graphics.pose().translate(this.settings.getPositionValue(Settings.armor_det_position)[0], this.settings.getPositionValue(Settings.armor_det_position)[1]);
			drawArmorDetails(graphics, width);
            graphics.pose().translate(-this.settings.getPositionValue(Settings.armor_det_position)[0], -this.settings.getPositionValue(Settings.armor_det_position)[1]);
		}
        graphics.pose().translate(this.settings.getPositionValue(Settings.item_det_position)[0], this.settings.getPositionValue(Settings.item_det_position)[1]);
		drawItemDetails(graphics, InteractionHand.MAIN_HAND, width);
		drawItemDetails(graphics, InteractionHand.OFF_HAND, width);
        graphics.pose().translate(-this.settings.getPositionValue(Settings.item_det_position)[0], -this.settings.getPositionValue(Settings.item_det_position)[1]);
		if (this.settings.getBoolValue(Settings.show_arrow_count)) {
            graphics.pose().translate(this.settings.getPositionValue(Settings.arrow_det_position)[0], this.settings.getPositionValue(Settings.arrow_det_position)[1]);
			drawArrowCount(graphics, width);
            graphics.pose().translate(-this.settings.getPositionValue(Settings.arrow_det_position)[0], -this.settings.getPositionValue(Settings.arrow_det_position)[1]);
		}
	}

	/** Calculates the width for the element background */
	private int calculateWidth() {
		int width = 0;
		for (int i = Inventory.EQUIPMENT_SLOT_MAPPING.size() +35; i >= 36; i--) {
			if (this.mc.player.getInventory().getItem(i) != ItemStack.EMPTY
					&& this.mc.player.getInventory().getItem(i).has(DataComponents.DAMAGE)) {
				ItemStack item = this.mc.player.getInventory().getItem(i);
				String s = (item.getMaxDamage() - item.getDamageValue()) + "/" + item.getMaxDamage();
				int widthNew = this.mc.font.width(s);
				if (widthNew > width)
					width = widthNew;
			}
		}
		ItemStack item = this.mc.player.getMainHandItem();
		if (item != ItemStack.EMPTY) {
			if (this.settings.getBoolValue(Settings.show_item_durability) && item.isDamageableItem()) {
				String s = (item.getMaxDamage() - item.getDamageValue()) + "/" + item.getMaxDamage();
				int widthNew = this.mc.font.width(s);
				if (widthNew > width)
					width = widthNew;
			} else if (this.settings.getBoolValue(Settings.show_block_count) && item.getItem() instanceof BlockItem) {
				int x = this.mc.player.getInventory().getNonEquipmentItems().size();
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
				int widthNew = this.mc.font.width(s);
				if (widthNew > width)
					width = widthNew;
			}
		}
		item = this.mc.player.getOffhandItem();
		if (item != ItemStack.EMPTY) {
			if (this.settings.getBoolValue(Settings.show_item_durability) && item.isDamageableItem()) {
				String s = (item.getMaxDamage() - item.getDamageValue()) + "/" + item.getMaxDamage();
				int widthNew = this.mc.font.width(s);
				if (widthNew > width)
					width = widthNew;
			} else if (this.settings.getBoolValue(Settings.show_block_count) && item.getItem() instanceof BlockItem) {
				int x = this.mc.player.getInventory().getNonEquipmentItems().size();
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
				int widthNew = this.mc.font.width(s);
				if (widthNew > width)
					width = widthNew;
			}
		}
		item = this.mc.player.getMainHandItem();
		if (this.settings.getBoolValue(Settings.show_arrow_count) && item != ItemStack.EMPTY
				&& this.mc.player.getMainHandItem().getItem() instanceof BowItem) {
			int x = this.mc.player.getInventory().getNonEquipmentItems().size();
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
			int widthNew = this.mc.font.width(s);
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
	 * @param width the width of the background
	 */
	protected void drawArmorDetails(GuiGraphicsExtractor graphics, int width) {
		for (int i = Inventory.EQUIPMENT_SLOT_MAPPING.size() +35; i >= 36; i--) {
			if (this.mc.player.getInventory().getItem(i) != ItemStack.EMPTY
					&& this.mc.player.getInventory().getItem(i).has(DataComponents.DAMAGE)) {
				drawRect(graphics, 2, 30 + this.offset / 2, 10 + 6 + (width / 2), 10, 0xA0000000);
				graphics.pose().scale(0.5f, 0.5f);
				ItemStack item = this.mc.player.getInventory().getItem(i);
				String s = (item.getMaxDamage() - item.getDamageValue()) + "/" + item.getMaxDamage();
				this.renderGuiItemModel(graphics, item, 6, 62 + this.offset, false);
				if (this.settings.getBoolValue(Settings.show_durability_bar))
					this.renderItemDurabilityBar(graphics, item, 6, 62 + this.offset, 1f);
				graphics.centeredText(this.mc.font, s, 32 + width / 2, 66 + this.offset, -1);
				graphics.pose().scale(2f, 2f);
				this.offset += 20;
			}
		}
	}

	/**
	 * Draws the held item details
	 *
	 * @param hand  the hand whose item should be detailed
	 * @param width the width of the background
	 */
	protected void drawItemDetails(GuiGraphicsExtractor graphics, InteractionHand hand, int width) {
		final ItemStack item = InteractionHand.MAIN_HAND.equals(hand) ? this.mc.player.getMainHandItem() : this.mc.player.getOffhandItem();
		if (item != ItemStack.EMPTY) {
			if (this.settings.getBoolValue(Settings.show_item_durability) && item.isDamageableItem()) {
				drawRect(graphics, 2, 30 + this.offset / 2, 10 + 6 + (width / 2), 10, 0xA0000000);
				String s = (item.getMaxDamage() - item.getDamageValue()) + "/" + item.getMaxDamage();
				graphics.pose().scale(0.5f, 0.5f);
				this.renderGuiItemModel(graphics, item, 6, 62 + this.offset, false);
				if (this.settings.getBoolValue(Settings.show_durability_bar))
					this.renderItemDurabilityBar(graphics, item, 6, 62 + this.offset, 1f);
				graphics.centeredText(this.mc.font, s, 32 + width / 2, 66 + this.offset, -1);
				graphics.pose().scale(2f, 2f);
				this.offset += 20;

			} else if (this.settings.getBoolValue(Settings.show_block_count) && item.getItem() instanceof BlockItem) {
				int x = this.mc.player.getInventory().getNonEquipmentItems().size();
				int z = 0;
				if ((InteractionHand.MAIN_HAND.equals(hand) ? ModRPGHud.renderDetailsAgain[0] : ModRPGHud.renderDetailsAgain[1])
						|| !ItemStack.isSameItem(
						(InteractionHand.MAIN_HAND.equals(hand) ? this.itemMainHandLast : this.itemOffhandLast), item)
						|| !ItemStack.isSameItem(this.itemMainHandLast, item)) {
					if (InteractionHand.MAIN_HAND.equals(hand)) {
						this.itemMainHandLast = item.copy();
						ModRPGHud.renderDetailsAgain[0] = false;
					} else {
						this.itemOffhandLast = item.copy();
						ModRPGHud.renderDetailsAgain[1] = false;
					}
					for (int y = 0; y < x; y++) {
						ItemStack invItem = this.mc.player.getInventory().getItem(y);
						if (invItem != ItemStack.EMPTY && Item.getId(invItem.getItem()) == Item
								.getId(item.getItem())) {
							z += invItem.getCount();
						}
					}
					if (InteractionHand.MAIN_HAND.equals(hand))
						this.count1 = z;
					else
						this.count2 = z;
				} else {
					if (InteractionHand.MAIN_HAND.equals(hand))
						z = this.count1;
					else
						z = this.count2;
				}

				drawRect(graphics, 2, 30 + this.offset / 2, 10 + 6 + (width / 2), 10, 0xA0000000);
				String s = "x " + z;

				graphics.pose().scale(0.5f, 0.5f);
				this.renderGuiItemModel(graphics, item, 6, 62 + this.offset, false);
				graphics.centeredText(this.mc.font, s, 32 + width / 2, 66 + this.offset, -1);
				graphics.pose().scale(2f, 2f);
				this.offset += 20;
			}
		}
	}

	/**
	 * Draws the amount of arrows the player has in his inventory on the screen
	 *
	 * @param width the width of the background
	 */
	protected void drawArrowCount(GuiGraphicsExtractor graphics, int width) {
		ItemStack item = this.mc.player.getMainHandItem();
		if (this.settings.getBoolValue(Settings.show_arrow_count) && item != ItemStack.EMPTY
				&& this.mc.player.getMainHandItem().getItem() instanceof BowItem) {
			int x = this.mc.player.getInventory().getNonEquipmentItems().size();
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
			drawRect(graphics, 2, 30 + this.offset / 2, 10 + 6 + (width / 2), 10, 0xA0000000);
			String s = "x " + z;
			graphics.pose().scale(0.5f, 0.5f);
			if (this.itemArrow == ItemStack.EMPTY)
				this.itemArrow = new ItemStack(Items.ARROW);
			this.renderGuiItemModel(graphics, this.itemArrow, 6, 62 + this.offset, false);
			graphics.centeredText(this.mc.font, s, 32 + width / 2, 66 + this.offset, -1);
			graphics.pose().scale(2f, 2f);
			this.offset += 20;
		}

		if (item == ItemStack.EMPTY || item == null) {
			this.itemMainHandLastArrow = ItemStack.EMPTY;
		} else {
			this.itemMainHandLastArrow = item.copy();
		}
	}

}
