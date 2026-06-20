package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionContents;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.settings.Settings;

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
		return !this.mc.gui.hud.isHidden() && !this.isChatOpen();
	}

	@Override
	public void drawElement(GuiGraphicsExtractor gg, float zLevel, DeltaTracker partialTicks, int scaledWidth, int scaledHeight) {
		this.offset = 0;
			if (this.settings.getBoolValue(Settings.show_armor)) {
				drawArmorDetails(gg);
			}
			drawItemDetails(gg, 0);
			drawItemDetails(gg, 1);
			if (this.settings.getBoolValue(Settings.show_arrow_count)) {
				drawArrowCount(gg);
			}
	}

	/**
	 * Draws the armor details
	 * 
	 * @param gg
	 *            the GUI to draw one
	 */
	protected void drawArmorDetails(GuiGraphicsExtractor gg) {
		int xOffset = this.settings.getPositionValue(Settings.armor_det_position)[0];
		int yOffset = this.settings.getPositionValue(Settings.armor_det_position)[1];
		boolean reducedSize = this.settings.getBoolValue(Settings.reduce_size);
		if (reducedSize) gg.pose().scale(0.5f, 0.5f);
		for (EquipmentSlot equipmentslot : EquipmentSlotGroup.ARMOR) {
			ItemStack item = this.mc.player.getItemBySlot(equipmentslot);
			if (item != ItemStack.EMPTY && item.isDamageableItem()) {
				String s = (item.getMaxDamage() - item.getDamageValue()) + "/" + item.getMaxDamage();
				this.renderGuiItemModel(gg, item, (reducedSize ? 4 : 2) + xOffset, (reducedSize ? 124 + (typeOffset*2): 62 +typeOffset) + this.offset + yOffset);
				if(this.settings.getBoolValue(Settings.show_durability_bar)) this.renderItemDurabilityBar(gg, item, reducedSize ? 5 : 2 + xOffset, (reducedSize ? 127 + typeOffset*2: 62+typeOffset) + this.offset + yOffset);
				gg.text(this.mc.font, s, 23 + xOffset, (reducedSize ? 128 + (typeOffset*2): 66 + typeOffset) + this.offset + yOffset, -1);
				this.offset += 16;
			}
		}
		if (reducedSize) gg.pose().scale(2f, 2f);
	}

	/**
	 * Draws the held item details
	 * 
	 * @param gg
	 *            the GUI to draw on
	 * @param hand
	 *            the hand whose item should be detailed
	 */
	protected void drawItemDetails(GuiGraphicsExtractor gg, int hand) {
		ItemStack item = getItemInHand(hand);
		int xOffset = this.settings.getPositionValue(Settings.item_det_position)[0];
		int yOffset = this.settings.getPositionValue(Settings.item_det_position)[1];
		boolean reducedSize = this.settings.getBoolValue(Settings.reduce_size);
		if (item != ItemStack.EMPTY) {
			if (this.settings.getBoolValue(Settings.show_item_durability) && item.isDamageableItem()) {
				if (reducedSize)
					gg.pose().scale(0.5f, 0.5f);
				String s = (item.getMaxDamage() - item.getDamageValue()) + "/" + item.getMaxDamage();
				this.renderGuiItemModel(gg, item, reducedSize ? 4 : 2 + xOffset, (reducedSize ? 124 + typeOffset*2 : 62 + typeOffset) + this.offset + yOffset);
				if(this.settings.getBoolValue(Settings.show_durability_bar)) this.renderItemDurabilityBar(gg, item, reducedSize ? 5 : 2 + xOffset, (reducedSize ? 127 + typeOffset*2 : 62 + typeOffset) + this.offset + yOffset);
				gg.text(this.mc.font, s, 23 + xOffset, (reducedSize ? 128  + typeOffset*2: 66 + typeOffset) + this.offset + yOffset, -1);
				this.offset += 16;
				if (reducedSize)
					gg.pose().scale(2f, 2f);
			} else if (this.settings.getBoolValue(Settings.show_block_count) && item.getItem() instanceof BlockItem) {
				int x = this.mc.player.getInventory().getContainerSize();
				int z = 0;
				if ((hand == 0 ? ModRPGHud.renderDetailsAgain[0] : ModRPGHud.renderDetailsAgain[1]) || !ItemStack.matches((hand == 0 ? this.itemMainHandLast : this.itemOffhandLast), item) || !ItemStack.matches(this.itemMainHandLast, item)) {
					if (hand == 0) {
						this.itemMainHandLast = item.copy();
						ModRPGHud.renderDetailsAgain[0] = false;
					} else {
						this.itemOffhandLast = item.copy();
						ModRPGHud.renderDetailsAgain[1] = false;
					}
					for (int y = 0; y < x; y++) {
						item = this.mc.player.getInventory().getItem(y);
						if (item != ItemStack.EMPTY && Item.getId(item.getItem()) == Item.getId(getItemInHand(hand).getItem())) {
							z += item.getCount();
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

				item = getItemInHand(hand);
				String s = "x " + z;
				if (reducedSize)
					gg.pose().scale(0.5f, 0.5f);
				this.renderGuiItemModel(gg, item, reducedSize ? 4 : 2 + xOffset, (reducedSize ? 124 + typeOffset*2 : 62 + typeOffset) + this.offset + yOffset);
				gg.text(this.mc.font, s, 23 + xOffset, (reducedSize ? 128 + typeOffset*2 : 66 + typeOffset) + this.offset + yOffset, -1);
				if (reducedSize)
					gg.pose().scale(2f, 2f);
				this.offset += 16;
			}
		}
	}

	/**
	 * Draws the amount of arrows the player has in his inventory on the screen
	 * 
	 * @param gg
	 *            the GUI to draw on
	 */
	protected void drawArrowCount(GuiGraphicsExtractor gg) {
		int xOffset = this.settings.getPositionValue(Settings.arrow_det_position)[0];
		int yOffset = this.settings.getPositionValue(Settings.arrow_det_position)[1];
		boolean reducedSize = this.settings.getBoolValue(Settings.reduce_size);
		ItemStack item = this.mc.player.getMainHandItem();
		if (this.settings.getBoolValue(Settings.show_arrow_count) && item != ItemStack.EMPTY && item.getItem() instanceof BowItem) {
			int x = this.mc.player.getInventory().getContainerSize();
			int z = 0;

			if (ModRPGHud.renderDetailsAgain[2] || !ItemStack.matches(this.itemMainHandLastArrow, item)) {
				ModRPGHud.renderDetailsAgain[2] = false;

				item = this.mc.player.getProjectile(this.mc.player.getMainHandItem());
				if (item != ItemStack.EMPTY) {
					this.itemArrow = item.copy();
					for (int y = 0; y < x; y++) {
						ItemStack item3 = this.mc.player.getInventory().getItem(y);
						if (ItemStack.matches(item, item3)) {
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

			String s = "x " + z;
			if (reducedSize)
				gg.pose().scale(0.5f, 0.5f);
			if (this.itemArrow == ItemStack.EMPTY) {
				this.itemArrow = new ItemStack(Items.ARROW);
			}
			this.renderGuiItemModel(gg, this.itemArrow, reducedSize ? 4 : 2 + xOffset, (reducedSize ? 124  + typeOffset*2: 62 + typeOffset) + this.offset + yOffset);
			gg.text(this.mc.font, s, 23 + xOffset, (reducedSize ? 128 + typeOffset*2: 66 + typeOffset) + this.offset + yOffset, -1);
			if (reducedSize)
				gg.pose().scale(2f, 2f);
			this.offset += 16;

		}
		if (item == ItemStack.EMPTY || item == null) {
			this.itemMainHandLastArrow = ItemStack.EMPTY;
		} else {
			this.itemMainHandLastArrow = item.copy();
		}
	}

	/**
	 * checks if the player has arrows in his inventory and picks the one the
	 * bow would fire
	 * 
	 * @param player
	 *            the player to search for arrow
	 * 
	 * @return returns the ItemStack of the arrow. If none can be found returns
	 *         ItemStack.EMPTY
	 */
	protected static ItemStack findAmmo(Player player) {
		Minecraft mc = Minecraft.getInstance();
		if (isArrow(mc.player.getOffhandItem())) {
			return mc.player.getOffhandItem();
		} else if (isArrow(mc.player.getMainHandItem())) {
			return mc.player.getMainHandItem();
		} else {
			for (int i = 0; i < player.getInventory().getContainerSize(); ++i) {
				ItemStack itemstack = player.getInventory().getItem(i);

				if (isArrow(itemstack)) {
					return itemstack;
				}
			}

			return ItemStack.EMPTY;
		}
	}
	
	public static ItemStack getItemInHand(int hand) {
        Minecraft mc = Minecraft.getInstance();
		if (hand == 0)
			return mc.player.getMainHandItem();
		else if (hand == 1)
			return mc.player.getOffhandItem();
		else
			return ItemStack.EMPTY;
	}
	
	public static int getOffhandSide() {
		if (Minecraft.getInstance().player.getMainArm() == HumanoidArm .RIGHT)
			return 0;
		else
			return 1;
	}
	
	public static boolean isArrow(ItemStack item) {
		if (item != ItemStack.EMPTY) {
			return ItemStack.matches(item, new ItemStack(Items.ARROW));
		}

		return false;
	}
	
	public static int addArrowStackIfCorrect(ItemStack item, ItemStack arrow) {
		PotionContents type1 = null;
		if (item.getItem() instanceof TippedArrowItem tai)
			type1 = item.get(DataComponents.POTION_CONTENTS);//PotionUtils.getPotion(item);
		if (item.getItem() instanceof TippedArrowItem) {
			PotionContents type2 = arrow.get(DataComponents.POTION_CONTENTS);//PotionContents.getPotion(arrow);
			if (type1.potion() == type2.potion()) {
				return arrow.getCount();
			}
		} else {
			return arrow.getCount();
		}

		return arrow.getCount();
	}

	protected void renderGuiItemModel(GuiGraphicsExtractor gg, ItemStack stack, int x, int y) {
		gg.item(stack, x, y);
	}

	public void renderItemDurabilityBar(GuiGraphicsExtractor gg,ItemStack stack, int x, int y) {
		if (stack.isEmpty())
			return;
		if (stack.isBarVisible()) {
			int i = stack.getBarWidth();
			int j = stack.getBarColor();
			HudElement.drawRect(gg, x + 2, y + 13, 13, 2, 0x000000);
			HudElement.drawRect(gg, x + 2, y + 13, i, 1, j);
		}
	}

}
