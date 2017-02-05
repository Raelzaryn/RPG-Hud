package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.spellcraftgaming.rpghud.gui.GuiIngameRPGHud;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementChatVanilla extends HudElement {

	public HudElementChatVanilla() {
		super(HudElementType.CHAT, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return this.mc.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN;
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		GuiIngameRPGHud guiIngame = (GuiIngameRPGHud) gui;
		int i = guiIngame.getChat().getLineCount();
		int j = guiIngame.getChat().getDrawnChatLines().size();
		float f = this.mc.gameSettings.chatOpacity * 0.9F + 0.1F;
		if (j > 0) {
			boolean flag = false;

			if (guiIngame.getChat().getChatOpen()) {
				flag = true;
			}

			float f1 = guiIngame.getChat().getChatScale();
			int k = MathHelper.ceiling_float_int(guiIngame.getChat().getChatWidth() / f1);
			GlStateManager.pushMatrix();
			GlStateManager.translate(2.0F, 8.0F, 0.0F);
			GlStateManager.scale(f1, f1, 1.0F);
			int l = 0;

			for (int i1 = 0; i1 + guiIngame.getChat().getScrollPos() < guiIngame.getChat().getDrawnChatLines().size() && i1 < i; ++i1) {
				ChatLine chatline = guiIngame.getChat().getDrawnChatLines().get(i1 + guiIngame.getChat().getScrollPos());

				if (chatline != null) {
					int j1 = guiIngame.getUpdateCounter() - chatline.getUpdatedCounter();

					if (j1 < 200 || flag) {
						double d0 = j1 / 200.0D;
						d0 = 1.0D - d0;
						d0 = d0 * 10.0D;
						d0 = MathHelper.clamp_double(d0, 0.0D, 1.0D);
						d0 = d0 * d0;
						int l1 = (int) (255.0D * d0);

						if (flag) {
							l1 = 255;
						}

						l1 = (int) (l1 * f);
						++l;
						if (l1 > 3) {
							int j2 = -i1 * 9;
							Gui.drawRect(guiIngame.getChat().getXOffset() - 2, guiIngame.getChat().getYOffset() + j2 - 9, guiIngame.getChat().getXOffset() + k + 4, guiIngame.getChat().getYOffset() + j2, l1 / 2 << 24);
							String s = chatline.getChatComponent().getFormattedText();
							GlStateManager.enableBlend();
							this.mc.fontRendererObj.drawStringWithShadow(s, guiIngame.getChat().getXOffset(), guiIngame.getChat().getYOffset() + j2 - 8, 16777215 + (l1 << 24));
							GlStateManager.disableAlpha();
							GlStateManager.disableBlend();
						}
					}
				}
			}

			if (flag) {
				int k2 = this.mc.fontRendererObj.FONT_HEIGHT;
				GlStateManager.translate(-3.0F, 0.0F, 0.0F);
				int l2 = j * k2 + j;
				int i3 = l * k2 + l;
				int j3 = guiIngame.getChat().getScrollPos() * i3 / j;
				int k1 = i3 * i3 / l2;

				if (l2 != i3) {
					int k3 = j3 > 0 ? 170 : 96;
					int l3 = guiIngame.getChat().isScrolled() ? 13382451 : 3355562;
					Gui.drawRect(0, -j3, 2, -j3 - k1, l3 + (k3 << 24));
					Gui.drawRect(2, -j3, 1, -j3 - k1, 13421772 + (k3 << 24));
				}
			}

			GlStateManager.popMatrix();
		}
	}

}
