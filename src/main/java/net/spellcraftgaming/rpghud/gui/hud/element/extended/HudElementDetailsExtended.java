package net.spellcraftgaming.rpghud.gui.hud.element.extended;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.lib.gui.override.GuiIngameRPGHud;
import net.spellcraftgaming.rpghud.gui.hud.element.vanilla.HudElementDetailsVanilla;
import net.spellcraftgaming.rpghud.main.ModRPGHud;

public class HudElementDetailsExtended extends HudElementDetailsVanilla {

	protected int offset = 0;
	protected int count1;
	protected int count2;
	protected int count3;
	protected ItemStack itemMainHandLast = GameData.nullStack();
	protected ItemStack itemOffhandLast = GameData.nullStack();
	protected ItemStack itemMainHandLastArrow = GameData.nullStack();
	protected ItemStack itemArrow = GameData.nullStack();

	public HudElementDetailsExtended() {
		super();
	}

	@Override
	public boolean checkConditions() {
		return GameData.shouldDrawHUD() && !this.mc.gameSettings.showDebugInfo && !((GuiIngameRPGHud) this.mc.ingameGUI).getChat().getChatOpen();
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		this.offset = 0;
		if (gui instanceof GuiIngameRPGHud) {
			if (this.settings.show_armor) {
				drawArmorDetails(gui);
			}
			drawItemDetails(gui, 0);
			drawItemDetails(gui, 1);
			if (this.settings.show_arrowcount) {
				drawArrowCount(gui);
			}
		}
	}

	/**
	 * Draws the armor details
	 * 
	 * @param gui
	 *            the GUI to draw one
	 */
	@Override
	protected void drawArmorDetails(Gui gui) {
		this.mc.mcProfiler.startSection("armor_details");
		if (this.settings.reduce_size)
			GL11.glScaled(0.5D, 0.5D, 0.5D);
		for (int i = GameData.getPlayerArmorInventoryLength() - 1; i >= 0; i--) {
			if (GameData.getArmorInSlot(i) != null && GameData.getArmorInSlot(i).getItem() instanceof ItemArmor) {
				ItemStack item = GameData.getArmorInSlot(i);
				String s = (item.getMaxDamage() - item.getItemDamage()) + "/" + item.getMaxDamage();
				this.mc.getRenderItem().renderItemIntoGUI(item, this.settings.reduce_size ? 4 : 2, (this.settings.reduce_size ? 144 : 72) + this.offset);
				this.mc.getRenderItem().renderItemOverlays(this.mc.fontRendererObj, item, this.settings.reduce_size ? 4 : 2, (this.settings.reduce_size ? 144 : 72) + this.offset);
				GL11.glDisable(GL11.GL_LIGHTING);
				gui.drawString(this.mc.fontRendererObj, s, 23, (this.settings.reduce_size ? 152 : 76) + this.offset, -1);
				this.offset += 16;
			}
		}
		if (this.settings.reduce_size)
			GL11.glScaled(2.0D, 2.0D, 2.0D);
		this.mc.mcProfiler.endSection();
	}

	/**
	 * Draws the held item details
	 * 
	 * @param gui
	 *            the GUI to draw on
	 * @param hand
	 *            the hand whose item should be detailed
	 */
	@Override
	protected void drawItemDetails(Gui gui, int hand) {
		ItemStack item = GameData.getItemInHand(hand);
		if (item != null) {
			if (this.settings.show_itemdurability && item.isItemStackDamageable()) {
				if (this.settings.reduce_size)
					GL11.glScaled(0.5D, 0.5D, 0.5D);
				String s = (item.getMaxDamage() - item.getItemDamage()) + "/" + item.getMaxDamage();
				RenderHelper.enableGUIStandardItemLighting();
				this.mc.getRenderItem().renderItemIntoGUI(item, this.settings.reduce_size ? 4 : 2, (this.settings.reduce_size ? 144 : 72) + this.offset);
				this.mc.getRenderItem().renderItemOverlays(this.mc.fontRendererObj, item, this.settings.reduce_size ? 4 : 2, (this.settings.reduce_size ? 144 : 72) + this.offset);
				GL11.glDisable(GL11.GL_LIGHTING);
				gui.drawString(this.mc.fontRendererObj, s, 23, (this.settings.reduce_size ? 152 : 76) + this.offset, -1);
				RenderHelper.disableStandardItemLighting();
				this.offset += 16;
				if (this.settings.reduce_size)
					GL11.glScaled(2.0D, 2.0D, 2.0D);
			} else if (this.settings.show_blockcount && item.getItem() instanceof ItemBlock) {
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
						if (item != null && Item.getIdFromItem(item.getItem()) == Item.getIdFromItem(GameData.getItemInHand(hand).getItem())) {
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
				String s = "x " + z;
				if (this.settings.reduce_size)
					GL11.glScaled(0.5D, 0.5D, 0.5D);
				RenderHelper.enableGUIStandardItemLighting();
				this.mc.getRenderItem().renderItemIntoGUI(item, this.settings.reduce_size ? 4 : 2, (this.settings.reduce_size ? 144 : 72) + this.offset);
				this.mc.getRenderItem().renderItemOverlays(this.mc.fontRendererObj, item, this.settings.reduce_size ? 4 : 2, (this.settings.reduce_size ? 144 : 72) + this.offset);
				GL11.glDisable(GL11.GL_LIGHTING);
				gui.drawString(this.mc.fontRendererObj, s, 23, (this.settings.reduce_size ? 152 : 76) + this.offset, -1);
				if (this.settings.reduce_size)
					GL11.glScaled(2.0D, 2.0D, 2.0D);
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
	@Override
	protected void drawArrowCount(Gui gui) {
		ItemStack item = GameData.getMainhand();
		if (this.settings.show_arrowcount && item != GameData.nullStack() && item.getItem() instanceof ItemBow) {
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

			String s = "x " + z;
			if (this.settings.reduce_size)
				GL11.glScaled(0.5D, 0.5D, 0.5D);
			RenderHelper.enableGUIStandardItemLighting();
			if (this.itemArrow == GameData.nullStack()) {
				this.itemArrow = GameData.arrowStack();
			}

			this.mc.getRenderItem().renderItemIntoGUI(this.itemArrow, this.settings.reduce_size ? 4 : 2, (this.settings.reduce_size ? 144 : 72) + this.offset);
			this.mc.getRenderItem().renderItemOverlays(this.mc.fontRendererObj, this.itemArrow, this.settings.reduce_size ? 4 : 2, (this.settings.reduce_size ? 144 : 72) + this.offset);
			GL11.glDisable(GL11.GL_LIGHTING);
			gui.drawString(this.mc.fontRendererObj, s, 23, (this.settings.reduce_size ? 152 : 76) + this.offset, -1);
			if (this.settings.reduce_size)
				GL11.glScaled(2.0D, 2.0D, 2.0D);
			this.offset += 16;

		}
		if (item == GameData.nullStack() || item == null) {
			this.itemMainHandLastArrow = GameData.nullStack();
		} else {
			this.itemMainHandLastArrow = item.copy();
		}
	}

}
