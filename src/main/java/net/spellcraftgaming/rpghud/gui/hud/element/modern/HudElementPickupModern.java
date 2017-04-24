package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import java.util.List;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.pickup.ItemPickup;

public class HudElementPickupModern extends HudElement {

	public HudElementPickupModern() {
		super(HudElementType.PICKUP, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return this.mc.playerController.shouldDrawHUD() && ModRPGHud.instance.settings.enable_pickup && !ModRPGHud.instance.pickupHandler.getPickups().isEmpty();
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		ScaledResolution res = new ScaledResolution(this.mc);
		int width = res.getScaledWidth();
		int height = res.getScaledHeight();
		List<ItemPickup> pickups = ModRPGHud.instance.pickupHandler.getPickups();
		for (int i = 0; i < pickups.size(); i++) {
			ItemPickup pickup = pickups.get(i);
			ItemStack item = pickup.getItem();
			int count = pickup.getCount();
			drawRect(width - 68, height - 28 - 20 * i, 67, 20, 0xA0000000);
			// gui.drawTexturedModalRect(width - 76, height - 32 - i * 32, 162,
			// 20, 76, 32);
			RenderHelper.enableGUIStandardItemLighting();
			this.mc.getRenderItem().renderItemIntoGUI(item, width - 68 + 8, height - 28 - 20 * i + 2);
			gui.drawString(this.mc.fontRendererObj, "x " + count, width - 68 + 32, height - 28 - 20 * i + 6, -1);
			RenderHelper.disableStandardItemLighting();
		}
	}

}
