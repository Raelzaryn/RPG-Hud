package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import static com.mojang.blaze3d.platform.GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA;
import static net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TippedArrowItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
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
		return !this.mc.options.renderDebug && !this.isChatOpen();
	}

	@Override
	public void drawElement(Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		this.offset = 0;
			if (this.settings.getBoolValue(Settings.show_armor)) {
				ms.translate(this.settings.getPositionValue(Settings.armor_det_position)[0], this.settings.getPositionValue(Settings.armor_det_position)[1], 0);
				drawArmorDetails(gui, ms, this.settings.getPositionValue(Settings.armor_det_position)[0], this.settings.getPositionValue(Settings.armor_det_position)[1]);
				ms.translate(-this.settings.getPositionValue(Settings.armor_det_position)[0], -this.settings.getPositionValue(Settings.armor_det_position)[1], 0);
			}
			ms.translate(this.settings.getPositionValue(Settings.item_det_position)[0], this.settings.getPositionValue(Settings.item_det_position)[1], 0);
			drawItemDetails(gui, ms, 0, this.settings.getPositionValue(Settings.item_det_position)[0], this.settings.getPositionValue(Settings.item_det_position)[1]);
			drawItemDetails(gui, ms, 1, this.settings.getPositionValue(Settings.item_det_position)[0], this.settings.getPositionValue(Settings.item_det_position)[1]);
			ms.translate(-this.settings.getPositionValue(Settings.item_det_position)[0], -this.settings.getPositionValue(Settings.item_det_position)[1], 0);
			if (this.settings.getBoolValue(Settings.show_arrow_count)) {
				ms.translate(this.settings.getPositionValue(Settings.arrow_det_position)[0], this.settings.getPositionValue(Settings.arrow_det_position)[1], 0);
				drawArrowCount(gui, ms, this.settings.getPositionValue(Settings.arrow_det_position)[0], this.settings.getPositionValue(Settings.arrow_det_position)[1]);
				ms.translate(-this.settings.getPositionValue(Settings.arrow_det_position)[0], -this.settings.getPositionValue(Settings.arrow_det_position)[1], 0);
			}
	}

	/**
	 * Draws the armor details
	 * 
	 * @param gui
	 *            the GUI to draw one
	 */
	protected void drawArmorDetails(Gui gui, PoseStack ms, int xOffset, int yOffset) {
		boolean reducedSize = this.settings.getBoolValue(Settings.reduce_size);
		if (reducedSize)
			ms.scale(0.5f, 0.5f, 0.5f);
		for (int i = this.mc.player.getInventory().armor.size() - 1; i >= 0; i--) {
			if (this.mc.player.getInventory().getArmor(i) != ItemStack.EMPTY && this.mc.player.getInventory().getArmor(i).getItem().isDamageable(null)) {
				ItemStack item = this.mc.player.getInventory().getArmor(i);
				String s = (item.getMaxDamage() - item.getDamageValue()) + "/" + item.getMaxDamage();
				this.renderGuiItemModel(item, (reducedSize ? 4 : 2) + xOffset, (reducedSize ? 124 + (typeOffset*2): 62 +typeOffset) + this.offset + yOffset, reducedSize
				);
				if(this.settings.getBoolValue(Settings.show_durability_bar)) this.renderItemDurabilityBar(item, reducedSize ? 4 : 2, (reducedSize ? 124 + typeOffset*2: 62+typeOffset) + this.offset, reducedSize? 0.5f : 1f);
				Gui.drawString(ms, this.mc.font, s, 23, (reducedSize ? 132 + (typeOffset*2): 66 + typeOffset) + this.offset, -1);
				this.offset += 16;
			}
		}
		if (reducedSize)
			ms.scale(2f, 2f, 2f);
	}

	/**
	 * Draws the held item details
	 * 
	 * @param gui
	 *            the GUI to draw on
	 * @param hand
	 *            the hand whose item should be detailed
	 */
	protected void drawItemDetails(Gui gui, PoseStack ms, int hand, int xOffset, int yOffset) {
		ItemStack item = getItemInHand(hand);
		boolean reducedSize = this.settings.getBoolValue(Settings.reduce_size);
		if (item != ItemStack.EMPTY) {
			if (this.settings.getBoolValue(Settings.show_item_durability) && item.isDamageableItem()) {
				if (reducedSize)
					ms.scale(0.5f, 0.5f, 0.5f);
				String s = (item.getMaxDamage() - item.getDamageValue()) + "/" + item.getMaxDamage();
				this.renderGuiItemModel(item, (reducedSize ? 4 : 2) + xOffset, ((reducedSize ? 124 + typeOffset*2 : 62 + typeOffset) + this.offset) + yOffset, reducedSize);
				if(this.settings.getBoolValue(Settings.show_durability_bar)) this.renderItemDurabilityBar(item, reducedSize ? 4 : 2, (reducedSize ? 124 + typeOffset*2 : 62 + typeOffset) + this.offset, reducedSize? 0.5f : 1f);
				Gui.drawString(ms, this.mc.font, s, 23, (reducedSize ? 132  + typeOffset*2: 66 + typeOffset) + this.offset, -1);
				this.offset += 16;
				if (reducedSize)
					ms.scale(2f, 2f, 2f);
			} else if (this.settings.getBoolValue(Settings.show_block_count) && item.getItem() instanceof BlockItem) {
				int x = this.mc.player.getInventory().getContainerSize();
				int z = 0;
				if ((hand == 0 ? ModRPGHud.renderDetailsAgain[0] : ModRPGHud.renderDetailsAgain[1]) || !ItemStack.isSame((hand == 0 ? this.itemMainHandLast : this.itemOffhandLast), item) || !ItemStack.isSame(this.itemMainHandLast, item)) {
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
					ms.scale(0.5f, 0.5f, 0.5f);
				this.renderGuiItemModel(item, reducedSize ? 4 : 2, (reducedSize ? 124 + typeOffset*2 : 62 + typeOffset) + this.offset, reducedSize);
				Gui.drawString(ms, this.mc.font, s, 23, (reducedSize ? 132 + typeOffset*2 : 66 + typeOffset) + this.offset, -1);
				if (reducedSize)
					ms.scale(2f, 2f, 2f);
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
	protected void drawArrowCount(Gui gui, PoseStack ms, int xOffset, int yOffset) {
		boolean reducedSize = this.settings.getBoolValue(Settings.reduce_size);
		ItemStack item = this.mc.player.getMainHandItem();
		if (this.settings.getBoolValue(Settings.show_arrow_count) && item != ItemStack.EMPTY && item.getItem() instanceof BowItem) {
			int x = this.mc.player.getInventory().getContainerSize();
			int z = 0;

			if (ModRPGHud.renderDetailsAgain[2] || !ItemStack.isSame(this.itemMainHandLastArrow, item)) {
				ModRPGHud.renderDetailsAgain[2] = false;

				item = findAmmo(this.mc.player);
				if (item != ItemStack.EMPTY) {
					this.itemArrow = item.copy();
					for (int y = 0; y < x; y++) {
						ItemStack item3 = this.mc.player.getInventory().getItem(y);
						if (ItemStack.isSame(item, item3)) {
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
				ms.scale(0.5f, 0.5f, 0.5f);
			if (this.itemArrow == ItemStack.EMPTY) {
				this.itemArrow = new ItemStack(Items.ARROW);
			}

			this.renderGuiItemModel(this.itemArrow, (reducedSize ? 4 : 2) + xOffset, ((reducedSize ? 124  + typeOffset*2: 62 + typeOffset) + this.offset) + yOffset, reducedSize);
			Gui.drawString(ms, this.mc.font, s, 23, (reducedSize ? 132  + typeOffset*2: 66 + typeOffset) + this.offset, -1);
			if (reducedSize)
				ms.scale(2f, 2f, 2f);
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
			return ItemStack.isSame(item, new ItemStack(Items.ARROW));
		}

		return false;
	}
	
	public static int addArrowStackIfCorrect(ItemStack item, ItemStack arrow) {
		Potion type1 = null;
		if (item.getItem() instanceof TippedArrowItem)
			type1 = PotionUtils.getPotion(item);
		if (item.getItem() instanceof TippedArrowItem) {
			Potion type2 = PotionUtils.getPotion(arrow);
			if (type1.getEffects() == type2.getEffects()) {
				return arrow.getCount();
			}
		} else {
			return arrow.getCount();
		}

		return arrow.getCount();
	}
	
	protected void renderGuiItemHalfSizeModel(ItemStack stack, int x, int y) {
		renderGuiItemModel(stack, x, y, true);
	}
	
	protected void renderGuiItemModel(ItemStack stack, int x, int y, boolean halfSize) {
		BakedModel model = this.mc.getItemRenderer().getModel(stack, null, null, 0);
		RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);
		RenderSystem.enableBlend();
		RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, ONE_MINUS_SRC_ALPHA);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		PoseStack PoseStack = RenderSystem.getModelViewStack();
		PoseStack.pushPose();
		if(halfSize) PoseStack.translate(x * 0.5f - 4, y * 0.5f - 4, (100.0F));
		else PoseStack.translate(x, y, (100.0F));
		PoseStack.translate(8.0D, 8.0D, 0.0D);
		PoseStack.scale(1.0F, -1.0F, 1.0F);
		if(halfSize) PoseStack.scale(0.5f, 0.5f, 0.5f);
		PoseStack.scale(16.0F, 16.0F, 16.0F);
		RenderSystem.applyModelViewMatrix();
		PoseStack PoseStack2 = new PoseStack();
		MultiBufferSource.BufferSource immediate = Minecraft.getInstance().renderBuffers()
				.bufferSource();
		boolean bl = !model.usesBlockLight();
		if (bl)
			Lighting.setupForFlatItems();
		this.mc.getItemRenderer().render(stack, ItemTransforms.TransformType.GUI, false, PoseStack2,
				 immediate, 15728880,
				NO_OVERLAY, model);
		immediate.endBatch();
		RenderSystem.enableDepthTest();
		if (bl)
			Lighting.setupFor3DItems();
		PoseStack.popPose();
		RenderSystem.applyModelViewMatrix();
	}

	public void renderItemDurabilityBar(ItemStack stack, int x, int y, float scale) {
		if (stack.isEmpty())
			return;
		if (stack.isBarVisible()) {
			PoseStack ms = new PoseStack();
			int i = stack.getBarWidth();
			int j = stack.getBarColor();
			ms.scale(scale, scale, scale);
			HudElement.drawRect(ms, x + 2, y + 13, 13, 2, 0x000000);
			HudElement.drawRect(ms, x + 2, y + 13, i, 1, j);
		}
	}

}
