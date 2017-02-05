package net.spellcraftgaming.rpghud.gui;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;

public class GuiChatRPGHud extends GuiNewChat {

	protected static final Splitter NEWLINE_SPLITTER = Splitter.on('\n');
	protected static final Joiner NEWLINE_STRING_JOINER = Joiner.on("\\n");
	protected static final Logger LOGGER = LogManager.getLogger();
	protected final Minecraft mc;
	protected final List<String> sentMessages = Lists.<String>newArrayList();
	protected final List<ChatLine> chatLines = Lists.<ChatLine>newArrayList();
	private final List<ChatLine> drawnChatLines = Lists.<ChatLine>newArrayList();
	protected int scrollPos;
	protected boolean isScrolled;
	protected int xOffset = 0;
	protected int yOffset = 0;

	public GuiChatRPGHud(Minecraft mc) {
		super(mc);
		this.mc = mc;
	}

	/**
	 * Clears the chat.
	 */
	public void clearChatMessages(boolean p_146231_1_) {
		this.getDrawnChatLines().clear();
		this.chatLines.clear();

		if (p_146231_1_) {
			this.sentMessages.clear();
		}
	}

	/**
	 * prints the ChatComponent to Chat.
	 */
	public void printChatMessage(IChatComponent chatComponent) {
		this.printChatMessageWithOptionalDeletion(chatComponent, 0);
	}

	/**
	 * prints the ChatComponent to Chat. If the ID is not 0, deletes an existing
	 * Chat Line of that ID from the GUI
	 */
	public void printChatMessageWithOptionalDeletion(IChatComponent chatComponent, int chatLineId) {
		this.setChatLine(chatComponent, chatLineId, this.mc.ingameGUI.getUpdateCounter(), false);
		LOGGER.info("[CHAT] {}", new Object[] { NEWLINE_STRING_JOINER.join(NEWLINE_SPLITTER.split(chatComponent.getUnformattedText())) });
	}

	private void setChatLine(IChatComponent chatComponent, int chatLineId, int updateCounter, boolean displayOnly) {
		if (chatLineId != 0) {
			this.deleteChatLine(chatLineId);
		}

		int i = MathHelper.floor_float(this.getChatWidth() / this.getChatScale());
		List<IChatComponent> list = GuiUtilRenderComponents.func_178908_a(chatComponent, i, this.mc.fontRendererObj, false, false);
		boolean flag = this.getChatOpen();

		for (IChatComponent itextcomponent : list) {
			if (flag && this.scrollPos > 0) {
				this.isScrolled = true;
				this.scroll(1);
			}

			this.getDrawnChatLines().add(0, new ChatLine(updateCounter, itextcomponent, chatLineId));
		}

		while (this.getDrawnChatLines().size() > 100) {
			this.getDrawnChatLines().remove(this.getDrawnChatLines().size() - 1);
		}

		if (!displayOnly) {
			this.chatLines.add(0, new ChatLine(updateCounter, chatComponent, chatLineId));

			while (this.chatLines.size() > 100) {
				this.chatLines.remove(this.chatLines.size() - 1);
			}
		}
	}

	public void refreshChat() {
		this.getDrawnChatLines().clear();
		this.resetScroll();

		for (int i = this.chatLines.size() - 1; i >= 0; --i) {
			ChatLine chatline = this.chatLines.get(i);
			this.setChatLine(chatline.getChatComponent(), chatline.getChatLineID(), chatline.getUpdatedCounter(), true);
		}
	}

	public List<String> getSentMessages() {
		return this.sentMessages;
	}

	/**
	 * Adds this string to the list of sent messages, for recall using the
	 * up/down arrow keys
	 */
	public void addToSentMessages(String message) {
		if (this.sentMessages.isEmpty() || !this.sentMessages.get(this.sentMessages.size() - 1).equals(message)) {
			this.sentMessages.add(message);
		}
	}

	/**
	 * Resets the chat scroll (executed when the GUI is closed, among others)
	 */
	public void resetScroll() {
		this.scrollPos = 0;
		this.isScrolled = false;
	}

	/**
	 * Scrolls the chat by the given number of lines.
	 */
	public void scroll(int amount) {
		this.scrollPos += amount;
		int i = this.getDrawnChatLines().size();

		if (this.scrollPos > i - this.getLineCount()) {
			this.scrollPos = i - this.getLineCount();
		}

		if (this.scrollPos <= 0) {
			this.scrollPos = 0;
			this.isScrolled = false;
		}
	}

	/**
	 * Gets the chat component under the mouse
	 */
	@Nullable
	public IChatComponent getChatComponent(int mouseX, int mouseY) {
		if (!this.getChatOpen()) {
			return null;
		}
		ScaledResolution scaledresolution = new ScaledResolution(this.mc);
		int i = scaledresolution.getScaleFactor();
		float f = this.getChatScale();
		int j = mouseX / i - 2;
		int k = mouseY / i - 40;
		j = MathHelper.floor_float(j / f);
		k = MathHelper.floor_float(k / f);

		if (j >= 0 && k >= 0) {
			int l = Math.min(this.getLineCount(), this.getDrawnChatLines().size());

			if (j <= MathHelper.floor_float(this.getChatWidth() / this.getChatScale()) && k < this.mc.fontRendererObj.FONT_HEIGHT * l + l) {
				int i1 = k / this.mc.fontRendererObj.FONT_HEIGHT + this.scrollPos;

				if (i1 >= 0 && i1 < this.getDrawnChatLines().size()) {
					ChatLine chatline = this.getDrawnChatLines().get(i1);
					int j1 = 0;

					for (IChatComponent itextcomponent : chatline.getChatComponent()) {
						if (itextcomponent instanceof ChatComponentText) {
							j1 += this.mc.fontRendererObj.getStringWidth(GuiUtilRenderComponents.func_178909_a(((ChatComponentText) itextcomponent).getChatComponentText_TextValue(), false));

							if (j1 > j) {
								return itextcomponent;
							}
						}
					}
				}

				return null;
			}
			return null;
		}
		return null;
	}

	/**
	 * Returns true if the chat GUI is open
	 */
	public boolean getChatOpen() {
		return this.mc.currentScreen instanceof GuiChat;
	}

	/**
	 * finds and deletes a Chat line by ID
	 */
	public void deleteChatLine(int id) {
		Iterator<ChatLine> iterator = this.getDrawnChatLines().iterator();

		while (iterator.hasNext()) {
			ChatLine chatline = iterator.next();

			if (chatline.getChatLineID() == id) {
				iterator.remove();
			}
		}

		iterator = this.chatLines.iterator();

		while (iterator.hasNext()) {
			ChatLine chatline1 = iterator.next();

			if (chatline1.getChatLineID() == id) {
				iterator.remove();
				break;
			}
		}
	}

	public int getChatWidth() {
		return calculateChatboxWidth(this.mc.gameSettings.chatWidth);
	}

	public int getChatHeight() {
		return calculateChatboxHeight(this.getChatOpen() ? this.mc.gameSettings.chatHeightFocused : this.mc.gameSettings.chatHeightUnfocused);
	}

	/**
	 * Returns the chatscale from mc.gameSettings.chatScale
	 */
	public float getChatScale() {
		return this.mc.gameSettings.chatScale;
	}

	public static int calculateChatboxWidth(float scale) {
		return MathHelper.floor_float(scale * 280.0F + 40.0F);
	}

	public static int calculateChatboxHeight(float scale) {
		return MathHelper.floor_float(scale * 160.0F + 20.0F);
	}

	public int getLineCount() {
		return this.getChatHeight() / 9;
	}

	public List<ChatLine> getDrawnChatLines() {
		return this.drawnChatLines;
	}

	public List<ChatLine> getChatLines() {
		return this.chatLines;
	}

	public int getScrollPos() {
		return this.scrollPos;
	}

	public boolean isScrolled() {
		return this.isScrolled;
	}

	public int getXOffset() {
		return this.xOffset;
	}

	public void setXOffset(int xOffset) {
		this.xOffset = xOffset;
	}

	public int getYOffset() {
		return this.yOffset;
	}

	public void setYOffset(int yOffset) {
		this.yOffset = yOffset;
	}

}
