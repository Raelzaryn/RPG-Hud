package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import org.joml.Matrix4f;

import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.TippedArrowItem;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.util.Arm;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
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
		return !this.mc.options.debugEnabled && !this.isChatOpen();
	}

	@Override
	public void drawElement(DrawContext dc, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		this.offset = 0;
			if (this.settings.getBoolValue(Settings.show_armor)) {
				dc.getMatrices().translate(this.settings.getPositionValue(Settings.armor_det_position)[0], this.settings.getPositionValue(Settings.armor_det_position)[1], 0);
				drawArmorDetails(dc);
				dc.getMatrices().translate(-this.settings.getPositionValue(Settings.armor_det_position)[0], -this.settings.getPositionValue(Settings.armor_det_position)[1], 0);
			}
			dc.getMatrices().translate(this.settings.getPositionValue(Settings.item_det_position)[0], this.settings.getPositionValue(Settings.item_det_position)[1], 0);
			drawItemDetails(dc, 0);
			drawItemDetails(dc, 1);
			dc.getMatrices().translate(-this.settings.getPositionValue(Settings.item_det_position)[0], -this.settings.getPositionValue(Settings.item_det_position)[1], 0);
			if (this.settings.getBoolValue(Settings.show_arrow_count)) {
				dc.getMatrices().translate(this.settings.getPositionValue(Settings.arrow_det_position)[0], this.settings.getPositionValue(Settings.arrow_det_position)[1], 0);
				drawArrowCount(dc);
				dc.getMatrices().translate(-this.settings.getPositionValue(Settings.arrow_det_position)[0], -this.settings.getPositionValue(Settings.arrow_det_position)[1], 0);
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
			dc.getMatrices().scale(0.5f, 0.5f, 0.5f);
		for (int i = this.mc.player.getInventory().armor.size() - 1; i >= 0; i--) {
			if (this.mc.player.getInventory().getArmorStack(i) != ItemStack.EMPTY && this.mc.player.getInventory().getArmorStack(i).getItem().isDamageable()) {
				ItemStack item = this.mc.player.getInventory().getArmorStack(i);
				String s = (item.getMaxDamage() - item.getDamage()) + "/" + item.getMaxDamage();
				this.renderGuiItemModel(dc, item, reducedSize ? 4 : 2, (reducedSize ? 124 + (typeOffset*2): 62 +typeOffset) + this.offset, reducedSize);
				if(this.settings.getBoolValue(Settings.show_durability_bar)) this.renderItemDurabilityBar(dc, item, reducedSize ? 4 : 2, (reducedSize ? 124 + typeOffset*2: 62+typeOffset) + this.offset, 1f);
				dc.drawTextWithShadow(this.mc.textRenderer, s, 23, (reducedSize ? 132 + (typeOffset*2): 66 + typeOffset) + this.offset, -1);
				this.offset += 16;
			}
		}
		if (reducedSize)
			dc.getMatrices().scale(2f, 2f, 2f);
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
			if (this.settings.getBoolValue(Settings.show_item_durability) && item.isDamageable()) {
				if (reducedSize)
					dc.getMatrices().scale(0.5f, 0.5f, 0.5f);
				String s = (item.getMaxDamage() - item.getDamage()) + "/" + item.getMaxDamage();
				this.renderGuiItemModel(dc, item, reducedSize ? 4 : 2, (reducedSize ? 124 + typeOffset*2 : 62 + typeOffset) + this.offset, reducedSize);
				if(this.settings.getBoolValue(Settings.show_durability_bar)) this.renderItemDurabilityBar(dc, item, reducedSize ? 4 : 2, (reducedSize ? 124 + typeOffset*2 : 62 + typeOffset) + this.offset, 1f);
				dc.drawTextWithShadow(this.mc.textRenderer, s, 23, (reducedSize ? 132  + typeOffset*2: 66 + typeOffset) + this.offset, -1);
				this.offset += 16;
				if (reducedSize)
					dc.getMatrices().scale(2f, 2f, 2f);
			} else if (this.settings.getBoolValue(Settings.show_block_count) && item.getItem() instanceof BlockItem) {
				int x = this.mc.player.getInventory().size();
				int z = 0;
				if ((hand == 0 ? ModRPGHud.renderDetailsAgain[0] : ModRPGHud.renderDetailsAgain[1]) || !ItemStack.areItemsEqual((hand == 0 ? this.itemMainHandLast : this.itemOffhandLast), item) || !ItemStack.areItemsEqual(this.itemMainHandLast, item)) {
					if (hand == 0) {
						this.itemMainHandLast = item.copy();
						ModRPGHud.renderDetailsAgain[0] = false;
					} else {
						this.itemOffhandLast = item.copy();
						ModRPGHud.renderDetailsAgain[1] = false;
					}
					for (int y = 0; y < x; y++) {
						item = this.mc.player.getInventory().getStack(y);
						if (item != ItemStack.EMPTY && Item.getRawId(item.getItem()) == Item.getRawId(getItemInHand(hand).getItem())) {
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
					dc.getMatrices().scale(0.5f, 0.5f, 0.5f);
				this.renderGuiItemModel(dc, item, reducedSize ? 4 : 2, (reducedSize ? 124 + typeOffset*2 : 62 + typeOffset) + this.offset, reducedSize);
				dc.drawTextWithShadow(this.mc.textRenderer, s, 23, (reducedSize ? 132 + typeOffset*2 : 66 + typeOffset) + this.offset, -1);
				if (reducedSize)
					dc.getMatrices().scale(2f, 2f, 2f);
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
		ItemStack item = this.mc.player.getMainHandStack();
		if (this.settings.getBoolValue(Settings.show_arrow_count) && item != ItemStack.EMPTY && item.getItem() instanceof BowItem) {
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

			String s = "x " + z;
			if (reducedSize)
				dc.getMatrices().scale(0.5f, 0.5f, 0.5f);
			if (this.itemArrow == ItemStack.EMPTY) {
				this.itemArrow = new ItemStack(Items.ARROW);
			}

			//dc.drawItemInSlot(null, item, x, z);
			this.renderGuiItemModel(dc, this.itemArrow, reducedSize ? 4 : 2, (reducedSize ? 124  + typeOffset*2: 62 + typeOffset) + this.offset, reducedSize);
			dc.drawTextWithShadow(this.mc.textRenderer, s, 23, (reducedSize ? 132  + typeOffset*2: 66 + typeOffset) + this.offset, -1);
			if (reducedSize)
				dc.getMatrices().scale(2f, 2f, 2f);
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
	protected static ItemStack findAmmo(PlayerEntity player) {
		MinecraftClient mc = MinecraftClient.getInstance();
		if (isArrow(mc.player.getOffHandStack())) {
			return mc.player.getOffHandStack();
		} else if (isArrow(mc.player.getMainHandStack())) {
			return mc.player.getMainHandStack();
		} else {
			for (int i = 0; i < player.getInventory().size(); ++i) {
				ItemStack itemstack = player.getInventory().getStack(i);

				if (isArrow(itemstack)) {
					return itemstack;
				}
			}

			return ItemStack.EMPTY;
		}
	}
	
	public static ItemStack getItemInHand(int hand) {
        MinecraftClient mc = MinecraftClient.getInstance();
		if (hand == 0)
			return mc.player.getMainHandStack();
		else if (hand == 1)
			return mc.player.getOffHandStack();
		else
			return ItemStack.EMPTY;
	}
	
	public static int getOffhandSide() {
		if (MinecraftClient.getInstance().player.getMainArm() == Arm.RIGHT)
			return 0;
		else
			return 1;
	}
	
	public static boolean isArrow(ItemStack item) {
		if (item != ItemStack.EMPTY) {
			return ItemStack.areItemsEqual(item, new ItemStack(Items.ARROW));
		}

		return false;
	}
	
	public static int addArrowStackIfCorrect(ItemStack item, ItemStack arrow) {
		Potion type1 = null;
		if (item.getItem() instanceof TippedArrowItem)
			type1 = PotionUtil.getPotion(item);
		if (item.getItem() instanceof TippedArrowItem) {
			Potion type2 = PotionUtil.getPotion(arrow);
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
		MatrixStack matrices = dc.getMatrices();
		BakedModel bakedModel = this.mc.getItemRenderer().getModel(stack, null, null, 0);
        matrices.push();
		matrices.translate(x + 8, y + 8, 150);
		
        try {
            boolean bl;
            matrices.multiplyPositionMatrix(new Matrix4f().scaling(1.0f, -1.0f, 1.0f));
    		if(halfSize) matrices.scale(0.5f, 0.5f, 0.5f);
    		matrices.scale(16.0F, 16.0F, 16.0F);
            bl = !bakedModel.isSideLit();
            if (bl) {
                DiffuseLighting.disableGuiDepthLighting();
            }
            this.mc.getItemRenderer().renderItem(stack, ModelTransformationMode.GUI, false, matrices, dc.getVertexConsumers(), 0xF000F0, OverlayTexture.DEFAULT_UV, bakedModel);
            RenderSystem.disableDepthTest();
            dc.getVertexConsumers().draw();
            RenderSystem.enableDepthTest();
            if (bl) {
                DiffuseLighting.enableGuiDepthLighting();
            }
        } catch (Throwable throwable) {
            CrashReport crashReport = CrashReport.create((Throwable)throwable, (String)"Rendering item");
            CrashReportSection crashReportSection = crashReport.addElement("Item being rendered");
            crashReportSection.add("Item Type", () -> String.valueOf(stack.getItem()));
            crashReportSection.add("Item Damage", () -> String.valueOf(stack.getDamage()));
            crashReportSection.add("Item NBT", () -> String.valueOf(stack.getNbt()));
            crashReportSection.add("Item Foil", () -> String.valueOf(stack.hasGlint()));
            throw new CrashException(crashReport);
        }
        matrices.pop();
	}

	public void renderItemDurabilityBar(DrawContext dc, ItemStack stack, int x, int y, float scale) {
		dc.drawItem(stack, x, y);
		if (stack.isEmpty())
			return;
		if (stack.isItemBarVisible()) {
			int i = stack.getItemBarStep();
			int j = stack.getItemBarColor();
			dc.getMatrices().scale(scale, scale, scale);
			HudElement.drawRect(dc, x + 2, y + 13, 13, 2, 0x000000);
			HudElement.drawRect(dc, x + 2, y + 13, i, 1, j);
		}
	}

}
