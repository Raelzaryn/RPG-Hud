package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
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
		return !this.mc.options.debugEnabled && !this.isChatOpen();
	}

	@Override
	public void drawElement(DrawContext dc, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		this.offset = (this.settings.getBoolValue(Settings.render_player_face) ? 0 : 16) + ((this.settings.getBoolValue(Settings.show_numbers_health) && this.settings.getBoolValue(Settings.show_numbers_food)) ? 0 : 8);
		int width = calculateWidth();
		if (this.settings.getBoolValue(Settings.show_armor)) {
			dc.getMatrices().translate(this.settings.getPositionValue(Settings.armor_det_position)[0], this.settings.getPositionValue(Settings.armor_det_position)[1], 0);
			drawArmorDetails(dc, width);
			dc.getMatrices().translate(-this.settings.getPositionValue(Settings.armor_det_position)[0], -this.settings.getPositionValue(Settings.armor_det_position)[1], 0);
		}
		dc.getMatrices().translate(this.settings.getPositionValue(Settings.item_det_position)[0], this.settings.getPositionValue(Settings.item_det_position)[1], 0);
		drawItemDetails(dc, Hand.MAIN_HAND, width);
		drawItemDetails(dc, Hand.OFF_HAND, width);
		dc.getMatrices().translate(-this.settings.getPositionValue(Settings.item_det_position)[0], -this.settings.getPositionValue(Settings.item_det_position)[1], 0);
		if (this.settings.getBoolValue(Settings.show_arrow_count)) {
			dc.getMatrices().translate(this.settings.getPositionValue(Settings.arrow_det_position)[0], this.settings.getPositionValue(Settings.arrow_det_position)[1], 0);
			drawArrowCount(dc, width);
			dc.getMatrices().translate(-this.settings.getPositionValue(Settings.arrow_det_position)[0], -this.settings.getPositionValue(Settings.arrow_det_position)[1], 0);
		}
	}

	/** Calculates the width for the element background */
	private int calculateWidth() {
		int width = 0;
		for (int i = this.mc.player.getInventory().armor.size() - 1; i >= 0; i--) {
			if (this.mc.player.getInventory().getArmorStack(i) != ItemStack.EMPTY
					&& this.mc.player.getInventory().getArmorStack(i).getItem().isDamageable()) {
				ItemStack item = this.mc.player.getInventory().getArmorStack(i);
				String s = (item.getMaxDamage() - item.getDamage()) + "/" + item.getMaxDamage();
				int widthNew = this.mc.textRenderer.getWidth(s);
				if (widthNew > width)
					width = widthNew;
			}
		}
		ItemStack item = this.mc.player.getMainHandStack();
		if (item != ItemStack.EMPTY) {
			if (this.settings.getBoolValue(Settings.show_item_durability) && item.isDamageable()) {
				String s = (item.getMaxDamage() - item.getDamage()) + "/" + item.getMaxDamage();
				int widthNew = this.mc.textRenderer.getWidth(s);
				if (widthNew > width)
					width = widthNew;
			} else if (this.settings.getBoolValue(Settings.show_block_count) && item.getItem() instanceof BlockItem) {
				int x = this.mc.player.getInventory().size();
				int z = 0;
				if (ModRPGHud.renderDetailsAgain[0] || !ItemStack.areItemsEqual(this.itemMainHandLast, item)) {
					this.itemMainHandLast = item.copy();
					ModRPGHud.renderDetailsAgain[0] = false;

					for (int y = 0; y < x; y++) {
						item = this.mc.player.getInventory().getStack(y);
						if (item != ItemStack.EMPTY && Item.getRawId(item.getItem()) == Item
								.getRawId(this.mc.player.getMainHandStack().getItem())) {
							z += item.getCount();
						}
					}
					this.count1 = z;
				} else {
					z = this.count1;
				}

				String s = "x " + z;
				int widthNew = this.mc.textRenderer.getWidth(s);
				if (widthNew > width)
					width = widthNew;
			}
		}
		item = this.mc.player.getOffHandStack();
		if (item != ItemStack.EMPTY) {
			if (this.settings.getBoolValue(Settings.show_item_durability) && item.isDamageable()) {
				String s = (item.getMaxDamage() - item.getDamage()) + "/" + item.getMaxDamage();
				int widthNew = this.mc.textRenderer.getWidth(s);
				if (widthNew > width)
					width = widthNew;
			} else if (this.settings.getBoolValue(Settings.show_block_count) && item.getItem() instanceof BlockItem) {
				int x = this.mc.player.getInventory().size();
				int z = 0;
				if (ModRPGHud.renderDetailsAgain[1] || !ItemStack.areItemsEqual(this.itemOffhandLast, item)
						|| !ItemStack.areItemsEqual(this.itemMainHandLast, item)) {
					this.itemOffhandLast = item.copy();
					ModRPGHud.renderDetailsAgain[1] = false;
					for (int y = 0; y < x; y++) {
						item = this.mc.player.getInventory().getStack(y);
						if (item != ItemStack.EMPTY && Item.getRawId(item.getItem()) == Item
								.getRawId(this.mc.player.getOffHandStack().getItem())) {
							z += item.getCount();
						}
					}
					this.count2 = z;
				} else {
					z = this.count2;
				}
				String s = "x " + z;
				int widthNew = this.mc.textRenderer.getWidth(s);
				if (widthNew > width)
					width = widthNew;
			}
		}
		item = this.mc.player.getMainHandStack();
		if (this.settings.getBoolValue(Settings.show_arrow_count) && item != ItemStack.EMPTY
				&& this.mc.player.getMainHandStack().getItem() instanceof BowItem) {
			int x = this.mc.player.getInventory().size();
			int z = 0;

			if (ModRPGHud.renderDetailsAgain[2] || !ItemStack.areItemsEqual(this.itemMainHandLastArrow, item)) {
				ModRPGHud.renderDetailsAgain[2] = false;

				item = findAmmo(this.mc.player);
				if (item != ItemStack.EMPTY) {
					this.itemArrow = item.copy();
					for (int y = 0; y < x; y++) {
						ItemStack item3 = this.mc.player.getInventory().getStack(y);
						if (ItemStack.areItemsEqual(item, item3)) {
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
			int widthNew = this.mc.textRenderer.getWidth(s);
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
	protected void drawArmorDetails(DrawContext dc, int width) {
		for (int i = this.mc.player.getInventory().armor.size() - 1; i >= 0; i--) {
			if (this.mc.player.getInventory().getArmorStack(i) != ItemStack.EMPTY
					&& this.mc.player.getInventory().getArmorStack(i).getItem().isDamageable()) {
				drawRect(dc, 2, 30 + this.offset / 2, 10 + 6 + (width / 2), 10, 0xA0000000);
				dc.getMatrices().scale(0.5f, 0.5f, 0.5f);
				ItemStack item = this.mc.player.getInventory().getArmorStack(i);
				String s = (item.getMaxDamage() - item.getDamage()) + "/" + item.getMaxDamage();
				this.renderGuiItemHalfSizeModel(dc, item, 6, 62 + this.offset);
				if (this.settings.getBoolValue(Settings.show_durability_bar))
					this.renderItemDurabilityBar(dc, item, 6, 62 + this.offset, 1f);
				dc.drawCenteredTextWithShadow( this.mc.textRenderer, s, 32 + width / 2, 66 + this.offset, -1);
				dc.getMatrices().scale(2f, 2f, 2f);
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
	protected void drawItemDetails(DrawContext dc, Hand hand, int width) {
		ItemStack item = this.mc.player.getStackInHand(hand);
		if (item != ItemStack.EMPTY) {
			if (this.settings.getBoolValue(Settings.show_item_durability) && item.isDamageable()) {
				drawRect(dc, 2, 30 + this.offset / 2, 10 + 6 + (width / 2), 10, 0xA0000000);
				String s = (item.getMaxDamage() - item.getDamage()) + "/" + item.getMaxDamage();
				dc.getMatrices().scale(0.5f, 0.5f, 0.5f);
				this.renderGuiItemHalfSizeModel(dc, item, 6, 62 + this.offset);
				if (this.settings.getBoolValue(Settings.show_durability_bar))
					this.renderItemDurabilityBar(dc, item, 6, 62 + this.offset, 1f);
				dc.drawCenteredTextWithShadow( this.mc.textRenderer, s, 32 + width / 2, 66 + this.offset, -1);
				dc.getMatrices().scale(2f, 2f, 2f);
				this.offset += 20;

			} else if (this.settings.getBoolValue(Settings.show_block_count) && item.getItem() instanceof BlockItem) {
				int x = this.mc.player.getInventory().size();
				int z = 0;
				if ((hand == Hand.MAIN_HAND ? ModRPGHud.renderDetailsAgain[0] : ModRPGHud.renderDetailsAgain[1])
						|| !ItemStack.areItemsEqual(
								(hand == Hand.MAIN_HAND ? this.itemMainHandLast : this.itemOffhandLast), item)
						|| !ItemStack.areItemsEqual(this.itemMainHandLast, item)) {
					if (hand == Hand.MAIN_HAND) {
						this.itemMainHandLast = item.copy();
						ModRPGHud.renderDetailsAgain[0] = false;
					} else {
						this.itemOffhandLast = item.copy();
						ModRPGHud.renderDetailsAgain[1] = false;
					}
					for (int y = 0; y < x; y++) {
						item = this.mc.player.getInventory().getStack(y);
						if (item != ItemStack.EMPTY && Item.getRawId(item.getItem()) == Item
								.getRawId(this.mc.player.getStackInHand(hand).getItem())) {
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

				item = this.mc.player.getStackInHand(hand);
				drawRect(dc, 2, 30 + this.offset / 2, 10 + 6 + (width / 2), 10, 0xA0000000);
				String s = "x " + z;
				dc.getMatrices().scale(0.5f, 0.5f, 0.5f);
				this.renderGuiItemHalfSizeModel(dc, item, 6, 62 + this.offset);
				dc.drawCenteredTextWithShadow( this.mc.textRenderer, s, 32 + width / 2, 66 + this.offset, -1);
				dc.getMatrices().scale(2f, 2f, 2f);
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
	protected void drawArrowCount(DrawContext dc, int width) {
		ItemStack item = this.mc.player.getMainHandStack();
		if (this.settings.getBoolValue(Settings.show_arrow_count) && item != ItemStack.EMPTY
				&& this.mc.player.getMainHandStack().getItem() instanceof BowItem) {
			int x = this.mc.player.getInventory().size();
			int z = 0;

			if (ModRPGHud.renderDetailsAgain[2] || !ItemStack.areItemsEqual(this.itemMainHandLastArrow, item)) {
				ModRPGHud.renderDetailsAgain[2] = false;

				item = findAmmo(this.mc.player);
				if (item != ItemStack.EMPTY) {
					this.itemArrow = item.copy();
					for (int y = 0; y < x; y++) {
						ItemStack item3 = this.mc.player.getInventory().getStack(y);
						if (ItemStack.areItemsEqual(item, item3)) {
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
			dc.getMatrices().scale(0.5f, 0.5f, 0.5f);
			if (this.itemArrow == ItemStack.EMPTY)
				this.itemArrow = new ItemStack(Items.ARROW);
			this.renderGuiItemHalfSizeModel(dc, this.itemArrow, 6, 62 + this.offset);
			dc.drawCenteredTextWithShadow( this.mc.textRenderer, s, 32 + width / 2, 66 + this.offset, -1);
			dc.getMatrices().scale(2f, 2f, 2f);
			this.offset += 20;

		}
		if (item == ItemStack.EMPTY || item == null) {
			this.itemMainHandLastArrow = ItemStack.EMPTY;
		} else {
			this.itemMainHandLastArrow = item.copy();
		}
	}

}
