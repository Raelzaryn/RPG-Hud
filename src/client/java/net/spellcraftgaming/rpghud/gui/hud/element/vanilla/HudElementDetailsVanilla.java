package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import com.mojang.authlib.minecraft.client.MinecraftClient;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.Arm;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TippedArrowItem;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(value=EnvType.CLIENT)
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
		return !this.mc.getDebugOverlay().showDebugScreen() && !this.isChatOpen();
	}

	@Override
	public void drawElement(DrawContext dc, float zLevel, RenderTickCounter partialTicks, int scaledWidth, int scaledHeight) {
		
		this.offset = 0;
			if (this.settings.getBoolValue(Settings.show_armor)) {
				dc.getMatrices().translate(this.settings.getPositionValue(Settings.armor_det_position)[0], this.settings.getPositionValue(Settings.armor_det_position)[1]);
				drawArmorDetails(dc);
				dc.getMatrices().translate(-this.settings.getPositionValue(Settings.armor_det_position)[0], -this.settings.getPositionValue(Settings.armor_det_position)[1]);
			}
			dc.getMatrices().translate(this.settings.getPositionValue(Settings.item_det_position)[0], this.settings.getPositionValue(Settings.item_det_position)[1]);
			drawItemDetails(dc, 0);
			drawItemDetails(dc, 1);
			dc.getMatrices().translate(-this.settings.getPositionValue(Settings.item_det_position)[0], -this.settings.getPositionValue(Settings.item_det_position)[1]);
			if (this.settings.getBoolValue(Settings.show_arrow_count)) {
				dc.getMatrices().translate(this.settings.getPositionValue(Settings.arrow_det_position)[0], this.settings.getPositionValue(Settings.arrow_det_position)[1]);
				drawArrowCount(dc);
				dc.getMatrices().translate(-this.settings.getPositionValue(Settings.arrow_det_position)[0], -this.settings.getPositionValue(Settings.arrow_det_position)[1]);
			}
	}

	/**
	 * Draws the armor details
	 * 
	 * @param gui
	 *            the GUI to draw one
	 */
	protected void drawArmorDetails(DrawContext dc) {
		boolean reducedSize = this.settings.getBoolValue(Settings.reduce_size);
		if (reducedSize)
			dc.getMatrices().scale(0.5f, 0.5f);
		
		//36=boots
		//37=legs
		//38=chest
		//39=helmet
		for (int i = Inventory.EQUIPMENT_SLOT_MAPPING.size() +35; i >= 36; i--) {
			if (this.mc.player.getInventory().getItem(i) != ItemStack.EMPTY && this.mc.player.getInventory().getItem(i).contains(DataComponentTypes.DAMAGE)) {
				ItemStack item = this.mc.player.getInventory().getItem(i);
				String s = (item.getMaxDamage() - item.getDamageValue()) + "/" + item.getMaxDamage();
				this.renderGuiItemModel(dc, item, reducedSize ? 4 : 2, (reducedSize ? 124 + (typeOffset*2): 62 +typeOffset) + this.offset, reducedSize);
				if(this.settings.getBoolValue(Settings.show_durability_bar)) this.renderItemDurabilityBar(dc, item, reducedSize ? 4 : 2, (reducedSize ? 124 + typeOffset*2: 62+typeOffset) + this.offset, 1f);
				dc.drawTextWithShadow(this.mc.textRenderer, s, 23, (reducedSize ? 132 + (typeOffset*2): 66 + typeOffset) + this.offset, -1);
				this.offset += 16;
			}
		}
		if (reducedSize)
			dc.getMatrices().scale(2f, 2f);
	}
	


	/**
	 * Draws the held item details
	 * 
	 * @param gui
	 *            the GUI to draw on
	 * @param hand
	 *            the hand whose item should be detailed
	 */
	protected void drawItemDetails(DrawContext dc, int hand) {
		ItemStack item = getItemInHand(hand);
		boolean reducedSize = this.settings.getBoolValue(Settings.reduce_size);
		if (item != ItemStack.EMPTY) {
			if (this.settings.getBoolValue(Settings.show_item_durability) && item.isDamageableItem()) {
				if (reducedSize)
					dc.getMatrices().scale(0.5f, 0.5f);
				String s = (item.getMaxDamage() - item.getDamageValue()) + "/" + item.getMaxDamage();
				this.renderGuiItemModel(dc, item, reducedSize ? 4 : 2, (reducedSize ? 124 + typeOffset*2 : 62 + typeOffset) + this.offset, reducedSize);
				if(this.settings.getBoolValue(Settings.show_durability_bar)) this.renderItemDurabilityBar(dc, item, reducedSize ? 4 : 2, (reducedSize ? 124 + typeOffset*2 : 62 + typeOffset) + this.offset, 1f);
				dc.drawTextWithShadow(this.mc.textRenderer, s, 23, (reducedSize ? 132  + typeOffset*2: 66 + typeOffset) + this.offset, -1);
				this.offset += 16;
				if (reducedSize)
					dc.getMatrices().scale(2f, 2f);
			} else if (this.settings.getBoolValue(Settings.show_block_count) && item.getItem() instanceof BlockItem) {
				int x = this.mc.player.getInventory().getContainerSize();
				int z = 0;
				if ((hand == 0 ? ModRPGHud.renderDetailsAgain[0] : ModRPGHud.renderDetailsAgain[1]) || !ItemStack.isSameItem((hand == 0 ? this.itemMainHandLast : this.itemOffhandLast), item) || !ItemStack.isSameItem(this.itemMainHandLast, item)) {
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
					dc.getMatrices().scale(0.5f, 0.5f);
				this.renderGuiItemModel(dc, item, reducedSize ? 4 : 2, (reducedSize ? 124 + typeOffset*2 : 62 + typeOffset) + this.offset, reducedSize);
				dc.drawTextWithShadow(this.mc.textRenderer, s, 23, (reducedSize ? 132 + typeOffset*2 : 66 + typeOffset) + this.offset, -1);
				if (reducedSize)
					dc.getMatrices().scale(2f, 2f);
				this.offset += 16;
			}
		}
	}

	/**
	 * Draws the amount of arrows the player has in his inventory on the screen
	 * 
	 * @param gui
	 *            the GUI to draw on
	 */
	protected void drawArrowCount(DrawContext dc) {
		boolean reducedSize = this.settings.getBoolValue(Settings.reduce_size);
		ItemStack item = this.mc.player.getMainHandItem();
		if (this.settings.getBoolValue(Settings.show_arrow_count) && item != ItemStack.EMPTY && item.getItem() instanceof BowItem) {
			int x = this.mc.player.getInventory().getContainerSize();
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

			String s = "x " + z;
			if (reducedSize)
				dc.getMatrices().scale(0.5f, 0.5f);
			if (this.itemArrow == ItemStack.EMPTY) {
				this.itemArrow = new ItemStack(Items.ARROW);
			}

			//dc.drawItemInSlot(null, item, x, z);
			this.renderGuiItemModel(dc, this.itemArrow, reducedSize ? 4 : 2, (reducedSize ? 124  + typeOffset*2: 62 + typeOffset) + this.offset, reducedSize);
			dc.drawTextWithShadow(this.mc.textRenderer, s, 23, (reducedSize ? 132  + typeOffset*2: 66 + typeOffset) + this.offset, -1);
			if (reducedSize)
				dc.getMatrices().scale(2f, 2f);
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
		Minecraft mc = Minecraft.getInstance();
		if (mc.player.getMainArm() == HumanoidArm.RIGHT)
			return 0;
		else
			return 1;
	}
	
	public static boolean isArrow(ItemStack item) {
		if (item != ItemStack.EMPTY) {
			return ItemStack.isSameItem(item, new ItemStack(Items.ARROW));
		}

		return false;
	}
	
	public static int addArrowStackIfCorrect(ItemStack item, ItemStack arrow) {
		PotionContentsComponent type1 = null;
		if (item.getItem() instanceof TippedArrowItem)
			type1 = item.get(DataComponentTypes.POTION_CONTENTS);
		if (item.getItem() instanceof TippedArrowItem) {
			PotionContentsComponent type2 = arrow.get(DataComponentTypes.POTION_CONTENTS);
			if (type1.getEffects() == type2.getEffects()) {
				return arrow.getCount();
			}
		} else {
			return arrow.getCount();
		}

		return arrow.getCount();
	}
	
	protected void renderGuiItemHalfSizeModel(DrawContext dc, ItemStack stack, int x, int y) {
		renderGuiItemModel(dc, stack, x, y, true);
	}
	
	protected void renderGuiItemModel(DrawContext dc, ItemStack stack, int x, int y, boolean halfSize) {
		dc.drawItem(stack, x, y);
	}

	public void renderItemDurabilityBar(GuiGraphicsExtractor dc, ItemStack stack, int x, int y, float scale) {
		if (stack.isEmpty())
			return;
		if (stack.isBarVisible()) {
			int i = stack.getItemBarStep();
			int j = stack.getItemBarColor();
			dc.pose().scale(scale, scale);
			HudElement.drawRect(dc, x + 2, y + 13, 13, 2, 0x000000);
			HudElement.drawRect(dc, x + 2, y + 13, i, 1, j);
		}
	}

}
