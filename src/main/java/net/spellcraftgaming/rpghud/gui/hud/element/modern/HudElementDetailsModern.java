package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.spellcraftgaming.rpghud.gui.hud.element.vanilla.HudElementDetailsVanilla;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(EnvType.CLIENT)
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
	public void drawElement(DrawableHelper gui, MatrixStack ms, float zLevel, float partialTicks, int scaledWidth,
			int scaledHeight) {
		this.offset = (this.settings.getBoolValue(Settings.render_player_face) ? 0 : 16)
				+ ((this.settings.getBoolValue(Settings.show_numbers_health)
						&& this.settings.getBoolValue(Settings.show_numbers_food)) ? 0 : 8);
		int width = calculateWidth();
		if (this.settings.getBoolValue(Settings.show_armor)) {
			ms.translate(this.settings.getPositionValue(Settings.armor_det_position)[0],
					this.settings.getPositionValue(Settings.armor_det_position)[1], 0);
			drawArmorDetails(gui, ms, width);
			ms.translate(-this.settings.getPositionValue(Settings.armor_det_position)[0],
					-this.settings.getPositionValue(Settings.armor_det_position)[1], 0);
		}
		ms.translate(this.settings.getPositionValue(Settings.item_det_position)[0],
				this.settings.getPositionValue(Settings.item_det_position)[1], 0);
		drawItemDetails(gui, ms, Hand.MAIN_HAND, width);
		drawItemDetails(gui, ms, Hand.OFF_HAND, width);
		ms.translate(-this.settings.getPositionValue(Settings.item_det_position)[0],
				-this.settings.getPositionValue(Settings.item_det_position)[1], 0);
		if (this.settings.getBoolValue(Settings.show_arrow_count)) {
			ms.translate(this.settings.getPositionValue(Settings.arrow_det_position)[0],
					this.settings.getPositionValue(Settings.arrow_det_position)[1], 0);
			drawArrowCount(gui, ms, width);
			ms.translate(-this.settings.getPositionValue(Settings.arrow_det_position)[0],
					-this.settings.getPositionValue(Settings.arrow_det_position)[1], 0);
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
				for (int y = 0; y < x; y++) {
					item = this.mc.player.getInventory().getStack(y);
					if (item != ItemStack.EMPTY && Item.getRawId(item.getItem()) == Item
							.getRawId(this.mc.player.getMainHandStack().getItem())) {
						z += item.getCount();
					}
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
				ItemStack item2 = this.mc.player.getMainHandStack();
				if( !(item2 != ItemStack.EMPTY && Item.getRawId(item2.getItem()) == Item.getRawId(item.getItem())) ) {
					int x = this.mc.player.getInventory().size();
					int z = 0;
					for (int y = 0; y < x; y++) {
						item = this.mc.player.getInventory().getStack(y);
						if (item != ItemStack.EMPTY && Item.getRawId(item.getItem()) == Item
								.getRawId(this.mc.player.getOffHandStack().getItem())) {
							z += item.getCount();
						}
					}
					String s = "x " + z;
					int widthNew = this.mc.textRenderer.getWidth(s);
					if (widthNew > width)
						width = widthNew;
				}
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
	protected void drawArmorDetails(DrawableHelper gui, MatrixStack ms, int width) {
		for (int i = this.mc.player.getInventory().armor.size() - 1; i >= 0; i--) {
			if (this.mc.player.getInventory().getArmorStack(i) != ItemStack.EMPTY
					&& this.mc.player.getInventory().getArmorStack(i).getItem().isDamageable()) {
				drawRect(ms, 2, 30 + this.offset / 2, 10 + 6 + (width / 2), 10, 0xA0000000);
				ms.scale(0.5f, 0.5f, 0.5f);
				ItemStack item = this.mc.player.getInventory().getArmorStack(i);
				String s = (item.getMaxDamage() - item.getDamage()) + "/" + item.getMaxDamage();
				this.renderGuiItemHalfSizeModel(item, 6, 62 + this.offset);
				if (this.settings.getBoolValue(Settings.show_durability_bar))
					this.renderItemDurabilityBar(item, 6, 62 + this.offset, 0.5f);
				DrawableHelper.drawCenteredText(ms, this.mc.textRenderer, s, 32 + width / 2, 66 + this.offset, -1);
				ms.scale(2f, 2f, 2f);
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
	protected void drawItemDetails(DrawableHelper gui, MatrixStack ms, Hand hand, int width) {
		ItemStack item = this.mc.player.getStackInHand(hand);
		if (item != ItemStack.EMPTY) {
			if (this.settings.getBoolValue(Settings.show_item_durability) && item.isDamageable()) {
				drawRect(ms, 2, 30 + this.offset / 2, 10 + 6 + (width / 2), 10, 0xA0000000);
				String s = (item.getMaxDamage() - item.getDamage()) + "/" + item.getMaxDamage();
				ms.scale(0.5f, 0.5f, 0.5f);
				this.renderGuiItemHalfSizeModel(item, 6, 62 + this.offset);
				if (this.settings.getBoolValue(Settings.show_durability_bar))
					this.renderItemDurabilityBar(item, 6, 62 + this.offset, 0.5f);
				DrawableHelper.drawCenteredText(ms, this.mc.textRenderer, s, 32 + width / 2, 66 + this.offset, -1);
				ms.scale(2f, 2f, 2f);
				this.offset += 20;

			} else if (this.settings.getBoolValue(Settings.show_block_count) && item.getItem() instanceof BlockItem) {
				ItemStack item2 = this.mc.player.getStackInHand(Hand.MAIN_HAND);
				if( hand != Hand.MAIN_HAND && item2 != ItemStack.EMPTY && Item.getRawId(item2.getItem()) == Item.getRawId(item.getItem()))
					return;
				int x = this.mc.player.getInventory().size();
				int z = 0;
				for (int y = 0; y < x; y++) {
					ItemStack item3 = this.mc.player.getInventory().getStack(y);
					if (item3 != ItemStack.EMPTY && Item.getRawId(item3.getItem()) == Item
							.getRawId(this.mc.player.getStackInHand(hand).getItem())) {
						z += item3.getCount();
					}
				}

				drawRect(ms, 2, 30 + this.offset / 2, 10 + 6 + (width / 2), 10, 0xA0000000);
				String s = "x " + z;
				ms.scale(0.5f, 0.5f, 0.5f);
				this.renderGuiItemHalfSizeModel(item, 6, 62 + this.offset);
				DrawableHelper.drawCenteredText(ms, this.mc.textRenderer, s, 32 + width / 2, 66 + this.offset, -1);
				ms.scale(2f, 2f, 2f);
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
	protected void drawArrowCount(DrawableHelper gui, MatrixStack ms, int width) {
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
			drawRect(ms, 2, 30 + this.offset / 2, 10 + 6 + (width / 2), 10, 0xA0000000);
			String s = "x " + z;
			ms.scale(0.5f, 0.5f, 0.5f);
			if (this.itemArrow == ItemStack.EMPTY)
				this.itemArrow = new ItemStack(Items.ARROW);
			this.renderGuiItemHalfSizeModel(this.itemArrow, 6, 62 + this.offset);
			DrawableHelper.drawCenteredText(ms, this.mc.textRenderer, s, 32 + width / 2, 66 + this.offset, -1);
			ms.scale(2f, 2f, 2f);
			this.offset += 20;

		}
		if (item == ItemStack.EMPTY || item == null) {
			this.itemMainHandLastArrow = ItemStack.EMPTY;
		} else {
			this.itemMainHandLastArrow = item.copy();
		}
	}

}
