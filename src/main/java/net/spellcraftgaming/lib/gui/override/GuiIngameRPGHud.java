package net.spellcraftgaming.lib.gui.override;

import static net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType.ALL;
import static net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType.BOSSHEALTH;
import static net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType.CHAT;
import static net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType.DEBUG;
import static net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType.FPS_GRAPH;
import static net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType.HELMET;
import static net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType.PLAYER_LIST;
import static net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType.PORTAL;
import static net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType.POTION_ICONS;
import static net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType.SUBTITLES;
import static net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType.TEXT;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiOverlayDebug;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.MinecraftForge;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.settings.EnumOptionsDebugMod;
import net.spellcraftgaming.rpghud.settings.EnumOptionsDebugMod.EnumOptionsType;

public class GuiIngameRPGHud extends GuiIngameForge {

	/** Constant for the color white */
	private static final int WHITE = 0xFFFFFF;

	/** left height of this gui screen */
	public static int left_height = 39;
	/** right height of this gui screen */
	public static int right_height = 39;

	/** The scaled resolution of minecraft */
	private ScaledResolution res = null;

	/** Instance of Minecraft's font renderer */
	private FontRenderer fontrenderer = null;

	/** Event used by forge to allow modders influence render events */
	private RenderGameOverlayEvent eventParent;

	/**
	 * Gui that contains all the debug information to be rendered on the screen
	 * by pressing F3 (default)
	 */
	private GuiOverlayDebugForge debugOverlay;

	/** Instance of the RPG-Hud mod */
	private ModRPGHud rpgHud;

	/** The GuiChat of Minecraft */
	private final GuiChatRPGHud chat;

	/**
	 * Constructor
	 * 
	 * @param mc
	 *            Instance of Minecraft
	 */
	public GuiIngameRPGHud(Minecraft mc) {
		super(mc);
		this.debugOverlay = new GuiOverlayDebugForge(mc);
		this.rpgHud = ModRPGHud.instance;
		this.chat = new GuiChatRPGHud(mc);
	}

	/**
	 * Main render function
	 * 
	 * @param partialTicks
	 *            the ticks used by animations
	 */
	@Override
	public void renderGameOverlay(float partialTicks) {
		this.res = new ScaledResolution(this.mc);
		this.eventParent = new RenderGameOverlayEvent(partialTicks, this.res);
		int width = this.res.getScaledWidth();
		int height = this.res.getScaledHeight();

		right_height = 39;
		left_height = 39;

		if (pre(ALL))
			return;

		this.fontrenderer = this.mc.fontRendererObj;
		this.mc.entityRenderer.setupOverlayRendering();
		GlStateManager.enableBlend();

		if (Minecraft.isFancyGraphicsEnabled()) {
			renderVignette(this.mc.thePlayer.getBrightness(partialTicks), this.res);
		} else {
			GlStateManager.enableDepth();
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		}

		renderHelmet(this.res, partialTicks);

		if (!this.mc.thePlayer.isPotionActive(MobEffects.NAUSEA)) {
			renderPortalMod(this.res, partialTicks);
		}

		this.drawElement(HudElementType.WIDGET, partialTicks);

		this.drawElement(HudElementType.HOTBAR, partialTicks);

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.zLevel = -90.0F;
		this.rand.setSeed(this.updateCounter * 312871);

		this.drawElement(HudElementType.CROSSHAIR, partialTicks);

		renderBossHealthMod();

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		if (this.mc.getRenderViewEntity() instanceof EntityPlayer) {
			this.drawElement(HudElementType.HEALTH, partialTicks);
			this.drawElement(HudElementType.ARMOR, partialTicks);
			this.drawElement(HudElementType.FOOD, partialTicks);
			this.drawElement(HudElementType.HEALTH_MOUNT, partialTicks);
			this.drawElement(HudElementType.AIR, partialTicks);
			this.drawElement(HudElementType.CLOCK, partialTicks);
			this.drawElement(HudElementType.DETAILS, partialTicks);
			this.drawElement(HudElementType.COMPASS, partialTicks);
			this.drawElement(HudElementType.PICKUP, partialTicks);
		}

		renderSleepFadeMod(width, height);

		this.drawElement(HudElementType.EXPERIENCE, partialTicks);
		this.drawElement(HudElementType.LEVEL, partialTicks);

		this.drawElement(HudElementType.JUMP_BAR, partialTicks);

		renderToolHighlightMod(this.res);
		renderHUDText(width);
		renderFPSGraphMod();
		renderPotionIconsMod(this.res);
		this.drawElement(HudElementType.RECORD_OVERLAY, partialTicks);
		renderSubtitles();
		renderTitleMod(width, height, partialTicks);

		Scoreboard scoreboard = this.mc.theWorld.getScoreboard();
		ScoreObjective objective = null;
		ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(this.mc.thePlayer.getName());
		if (scoreplayerteam != null) {
			int slot = scoreplayerteam.getChatFormat().getColorIndex();
			if (slot >= 0)
				objective = scoreboard.getObjectiveInDisplaySlot(3 + slot);
		}
		ScoreObjective scoreobjective1 = objective != null ? objective : scoreboard.getObjectiveInDisplaySlot(1);
		if (scoreobjective1 != null) {
			this.renderScoreboard(scoreobjective1, this.res);
		}

		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
		GlStateManager.disableAlpha();

		this.drawElementChat(partialTicks);

		renderPlayerList(width);

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.disableLighting();
		GlStateManager.enableAlpha();

		post(ALL);
	}

	/**
	 * Returns the resolution of Minecraft
	 * 
	 * @return the scaled resolution of Minecraft
	 */
	@Override
	public ScaledResolution getResolution() {
		return this.res;
	}

	/** Function that calls the renderPotionIcons function of GuiIngame */
	private void renderPotionIconsMod(ScaledResolution resolution) {
		if (pre(POTION_ICONS))
			return;
		super.renderPotionEffects(resolution);
		post(POTION_ICONS);
	}

	/** Function that calls the renderSubtitles function of GuiIngame */
	private void renderSubtitles() {
		if (pre(SUBTITLES))
			return;
		this.overlaySubtitle.renderSubtitles(this.res);
		post(SUBTITLES);
	}

	/** Function that renders the boss health via overlayBoss */
	private void renderBossHealthMod() {
		if (pre(BOSSHEALTH))
			return;
		bind(Gui.ICONS);
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		this.mc.mcProfiler.startSection("bossHealth");
		GlStateManager.enableBlend();
		this.overlayBoss.renderBossHealth();
		GlStateManager.disableBlend();
		this.mc.mcProfiler.endSection();
		post(BOSSHEALTH);
	}

	/** Function that renders the helmet screen overlay */
	private void renderHelmet(ScaledResolution res, float partialTicks) {
		if (pre(HELMET))
			return;

		ItemStack itemstack = this.mc.thePlayer.inventory.armorItemInSlot(3);

		if (this.mc.gameSettings.thirdPersonView == 0 && itemstack != null && itemstack.getItem() != null) {
			if (itemstack.getItem() == Item.getItemFromBlock(Blocks.PUMPKIN)) {
				renderPumpkinOverlay(res);
			} else {
				itemstack.getItem().renderHelmetOverlay(itemstack, this.mc.thePlayer, res, partialTicks);
			}
		}

		post(HELMET);
	}

	/** Function that renders the portal screen overlay */
	private void renderPortalMod(ScaledResolution res, float partialTicks) {
		if (pre(PORTAL))
			return;

		float f1 = this.mc.thePlayer.prevTimeInPortal + (this.mc.thePlayer.timeInPortal - this.mc.thePlayer.prevTimeInPortal) * partialTicks;

		if (f1 > 0.0F) {
			renderPortal(f1, res);
		}

		post(PORTAL);
	}

	/** Function that renders the sleep fade screen overlay */
	private void renderSleepFadeMod(int width, int height) {
		if (this.mc.thePlayer.getSleepTimer() > 0) {
			this.mc.mcProfiler.startSection("sleep");
			GlStateManager.disableDepth();
			GlStateManager.disableAlpha();
			int sleepTime = this.mc.thePlayer.getSleepTimer();
			float opacity = sleepTime / 100.0F;

			if (opacity > 1.0F) {
				opacity = 1.0F - (sleepTime - 100) / 10.0F;
			}

			int color = (int) (220.0F * opacity) << 24 | 1052704;
			drawRect(0, 0, width, height, color);
			GlStateManager.enableAlpha();
			GlStateManager.enableDepth();
			this.mc.mcProfiler.endSection();
		}
	}

	/**
	 * Function that renders the tool highlight on the screen upn equipping it
	 */
	private void renderToolHighlightMod(ScaledResolution res) {
		if (this.mc.gameSettings.heldItemTooltips && !this.mc.playerController.isSpectator()) {
			this.mc.mcProfiler.startSection("toolHighlight");

			if (this.remainingHighlightTicks > 0 && this.highlightingItemStack != null) {
				String name = this.highlightingItemStack.getDisplayName();
				if (this.highlightingItemStack.hasDisplayName())
					name = TextFormatting.ITALIC + name;

				name = this.highlightingItemStack.getItem().getHighlightTip(this.highlightingItemStack, name);

				int opacity = (int) (this.remainingHighlightTicks * 256.0F / 10.0F);
				if (opacity > 255)
					opacity = 255;

				if (opacity > 0) {
					int y = res.getScaledHeight() - 59;
					if (!this.mc.playerController.shouldDrawHUD())
						y += 14;

					GlStateManager.pushMatrix();
					GlStateManager.enableBlend();
					GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
					FontRenderer font = this.highlightingItemStack.getItem().getFontRenderer(this.highlightingItemStack);
					if (font != null) {
						int x = (res.getScaledWidth() - font.getStringWidth(name)) / 2;
						font.drawStringWithShadow(name, x, y, WHITE | (opacity << 24));
					} else {
						int x = (res.getScaledWidth() - this.fontrenderer.getStringWidth(name)) / 2;
						this.fontrenderer.drawStringWithShadow(name, x, y, WHITE | (opacity << 24));
					}
					GlStateManager.disableBlend();
					GlStateManager.popMatrix();
				}
			}

			this.mc.mcProfiler.endSection();
		} else if (this.mc.thePlayer.isSpectator()) {
			this.spectatorGui.renderSelectedItem(res);
		}
	}

	/**
	 * Renders the HUD text on the screen
	 * 
	 * @param width
	 *            screen width
	 */
	private void renderHUDText(int width) {
		this.mc.mcProfiler.startSection("forgeHudText");
		OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
		ArrayList<String> listL = new ArrayList<String>();
		ArrayList<String> listR = new ArrayList<String>();

		if (this.mc.isDemo()) {
			long time = this.mc.theWorld.getTotalWorldTime();
			if (time >= 120500L) {
				listR.add(I18n.format("demo.demoExpired"));
			} else {
				listR.add(I18n.format("demo.remainingTime", StringUtils.ticksToElapsedTime((int) (120500L - time))));
			}
		}

		if (this.mc.gameSettings.showDebugInfo && !pre(DEBUG)) {
			listL.addAll(this.debugOverlay.getLeft());
			listR.addAll(this.debugOverlay.getRight());
			post(DEBUG);
		}

		RenderGameOverlayEvent.Text event = new RenderGameOverlayEvent.Text(this.eventParent, listL, listR);
		if (!MinecraftForge.EVENT_BUS.post(event)) {
			int top = 2;
			for (String msg : listL) {
				if (msg == null)
					continue;
				drawRect(1, top - 1, 2 + this.fontrenderer.getStringWidth(msg) + 1, top + this.fontrenderer.FONT_HEIGHT - 1, -1873784752);
				this.fontrenderer.drawString(msg, 2, top, 14737632);
				top += this.fontrenderer.FONT_HEIGHT;
			}

			top = 2;
			for (String msg : listR) {
				if (msg == null)
					continue;
				int w = this.fontrenderer.getStringWidth(msg);
				int left = width - 2 - w;
				drawRect(left - 1, top - 1, left + w + 1, top + this.fontrenderer.FONT_HEIGHT - 1, -1873784752);
				this.fontrenderer.drawString(msg, left, top, 14737632);
				top += this.fontrenderer.FONT_HEIGHT;
			}
		}

		this.mc.mcProfiler.endSection();
		post(TEXT);
	}

	/** Renders the FPS graph of the debug overlay */
	private void renderFPSGraphMod() {
		if (this.mc.gameSettings.showDebugInfo && this.mc.gameSettings.showLagometer && !pre(FPS_GRAPH)) {
			this.debugOverlay.renderLagometer();
			post(FPS_GRAPH);
		}
	}

	/** Renders the title and subtitle */
	private void renderTitleMod(int width, int height, float partialTicks) {
		if (this.titlesTimer > 0) {
			this.mc.mcProfiler.startSection("titleAndSubtitle");
			float age = this.titlesTimer - partialTicks;
			int opacity = 255;

			if (this.titlesTimer > this.titleFadeOut + this.titleDisplayTime) {
				float f3 = this.titleFadeIn + this.titleDisplayTime + this.titleFadeOut - age;
				opacity = (int) (f3 * 255.0F / this.titleFadeIn);
			}
			if (this.titlesTimer <= this.titleFadeOut)
				opacity = (int) (age * 255.0F / this.titleFadeOut);

			opacity = MathHelper.clamp_int(opacity, 0, 255);

			if (opacity > 8) {
				GlStateManager.pushMatrix();
				GlStateManager.translate(width / 2, height / 2, 0.0F);
				GlStateManager.enableBlend();
				GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
				GlStateManager.pushMatrix();
				GlStateManager.scale(4.0F, 4.0F, 4.0F);
				int l = opacity << 24 & -16777216;
				this.getFontRenderer().drawString(this.displayedTitle, -this.getFontRenderer().getStringWidth(this.displayedTitle) / 2, -10.0F, 16777215 | l, true);
				GlStateManager.popMatrix();
				GlStateManager.pushMatrix();
				GlStateManager.scale(2.0F, 2.0F, 2.0F);
				this.getFontRenderer().drawString(this.displayedSubTitle, -this.getFontRenderer().getStringWidth(this.displayedSubTitle) / 2, 5.0F, 16777215 | l, true);
				GlStateManager.popMatrix();
				GlStateManager.disableBlend();
				GlStateManager.popMatrix();
			}

			this.mc.mcProfiler.endSection();
		}
	}

	/** Renders the player list via it's class */
	private void renderPlayerList(int width) {
		ScoreObjective scoreobjective = this.mc.theWorld.getScoreboard().getObjectiveInDisplaySlot(0);
		NetHandlerPlayClient handler = this.mc.thePlayer.connection;

		if (this.mc.gameSettings.keyBindPlayerList.isKeyDown() && (!this.mc.isIntegratedServerRunning() || handler.getPlayerInfoMap().size() > 1 || scoreobjective != null)) {
			this.overlayPlayerList.updatePlayerList(true);
			if (pre(PLAYER_LIST))
				return;
			this.overlayPlayerList.renderPlayerlist(width, this.mc.theWorld.getScoreboard(), scoreobjective);
			post(PLAYER_LIST);
		} else {
			this.overlayPlayerList.updatePlayerList(false);
		}
	}

	/** Checks if the event should be run and runs the pre forge event */
	private boolean pre(ElementType type) {
		return MinecraftForge.EVENT_BUS.post(new RenderGameOverlayEvent.Pre(this.eventParent, type));
	}

	/** Runs the forge event */
	private void post(ElementType type) {
		MinecraftForge.EVENT_BUS.post(new RenderGameOverlayEvent.Post(this.eventParent, type));
	}

	/** Binds a texture to the engine to be used */
	public void bind(ResourceLocation res) {
		this.mc.getTextureManager().bindTexture(res);
	}

	private class GuiOverlayDebugForge extends GuiOverlayDebug {
		private Minecraft mc;

		GuiOverlayDebugForge(Minecraft mc) {
			super(mc);
			this.mc = mc;
		}

		@Override
		protected void renderDebugInfoLeft() {
		}

		@Override
		protected void renderDebugInfoRight(ScaledResolution res) {
		}

		List<String> getLeft() {
			List<String> ret = this.call();
			ret.add("");
			ret.add("Debug: Pie [shift]: " + (this.mc.gameSettings.showDebugProfilerChart ? "visible" : "hidden") + " FPS [alt]: " + (this.mc.gameSettings.showLagometer ? "visible" : "hidden"));
			ret.add("For help: press F3 + Q");
			return ret;
		}

		List<String> getRight() {
			return this.getDebugInfoRight();
		}
	}

	/** Returns the updateCounter Variable */
	@Override
	public int getUpdateCounter() {
		return this.updateCounter;
	}

	/** Returns the overlayMessageTime variable */
	public boolean getRecordIsPlaying() {
		return this.recordIsPlaying;
	}

	/** Returns the animateOverlayMessageColor variable */
	public int getRecordPlayingUpFor() {
		return this.recordPlayingUpFor;
	}

	/** Return the currently playing record */
	public String getRecordPlaying() {
		return this.recordPlaying;
	}

	/** Returns the chat instance as a GuiNewChat object */
	@Override
	public GuiNewChat getChatGUI() {
		if(!forceRenderTypeVanilla(HudElementType.CHAT)) return this.chat;
		else return this.persistantChatGUI;
	}

	/** Returns the chat instance as a GuiChatRPGHud object */
	public GuiChatRPGHud getChat() {
		return this.chat;
	}

	/**
	 * Draw the specified HudElement of the HudElementType from the active Hud
	 * 
	 * @param type
	 *            the HudElementType to be rendered
	 * @param partialTicks
	 *            the partialTicks to be used for animations
	 */
	private void drawElement(HudElementType type, float partialTicks) {
		// Check if conditions for rendering the element are met
		if (this.rpgHud.getActiveHud().checkElementConditions(type)) {
			// Get RenderOverlayEvent alias for the type of this element
			ElementType alias = getEventAlias(type);

			// Check if the debug setting "force render" for this type is
			// activated
			if (forceRenderType(type)) {
				// Check if the debug setting "force vanilla" for this type is
				// activated
				if (forceRenderTypeVanilla(type)) {
					// Check if the debug setting "prevent element from
					// rendering" for this type is activated
					if (!preventElementRenderType(type)) {
						bind(Gui.ICONS);
						GlStateManager.enableBlend();

						// Draws the element of this type of the vanilla HUD
						this.rpgHud.getVanillaHud().drawElement(type, this, partialTicks, partialTicks);

						GlStateManager.disableBlend();
					}

					// Check if this type has an RenderOverlayEvent alias and if
					// the event should be prevented
					if (alias != null && !preventEventType(type)) {
						// Initiates the event
						if (pre(alias))
							return;
						post(alias);
					}
				} else {
					// Check if the debug setting "prevent element from
					// rendering" for this type is activated
					if (!preventElementRenderType(type)) {
						bind(Gui.ICONS);
						GlStateManager.enableBlend();

						// Draws the element of this type of the active HUD
						this.rpgHud.getActiveHud().drawElement(type, this, partialTicks, partialTicks);

						GlStateManager.disableBlend();
					}

					// Check if this type has an RenderOverlayEvent alias and if
					// the event should be prevented
					if (alias != null && !preventEventType(type)) {
						// Initiates the event
						if (pre(alias))
							return;
						post(alias);
					}
				}
			} else {
				// Check if the debug setting "force vanilla" for this type is
				// activated
				if (forceRenderTypeVanilla(type)) {
					// Check if this type has an RenderOverlayEvent alias and if
					// the event should be prevented
					if (alias != null && !preventEventType(type)) {
						if (pre(alias))
							return;
					}

					// Check if the debug setting "prevent element from
					// rendering" for this type is activated
					if (!preventElementRenderType(type)) {
						bind(Gui.ICONS);
						GlStateManager.enableBlend();

						// Draws the element of this type of the vanilla HUD
						this.rpgHud.getVanillaHud().drawElement(type, this, partialTicks, partialTicks);

						GlStateManager.disableBlend();
					}

					// Check if this type has an RenderOverlayEvent alias and if
					// the event should be prevented
					if (alias != null && !preventEventType(type)) {
						post(alias);
					}
				} else {
					// Check if this type has an RenderOverlayEvent alias and if
					// the event should be prevented
					if (alias != null && !preventEventType(type)) {
						if (pre(alias))
							return;
					}

					// Check if the debug setting "prevent element from
					// rendering" for this type is activated
					if (!preventElementRenderType(type)) {
						bind(Gui.ICONS);
						GlStateManager.enableBlend();

						// Draws the element of this type of the active HUD
						this.rpgHud.getActiveHud().drawElement(type, this, partialTicks, partialTicks);

						GlStateManager.disableBlend();
					}

					// Check if this type has an RenderOverlayEvent alias and if
					// the event should be prevented
					if (alias != null && !preventEventType(type)) {
						post(alias);
					}
				}
			}
		}

	}

	/**
	 * Draw the Chat HudElement from the active Hud
	 * 
	 * @param partialTicks
	 *            the partialTicks to be used for animations
	 */
	private void drawElementChat(float partialTicks) {
		if (this.rpgHud.getActiveHud().checkElementConditions(HudElementType.CHAT)) {
			RenderGameOverlayEvent.Chat event = new RenderGameOverlayEvent.Chat(this.eventParent, 0, this.res.getScaledHeight() - 48);
			this.chat.setXOffset(event.getPosX());
			this.chat.setYOffset(event.getPosY());
			if (forceRenderType(HudElementType.CHAT)) {
				if (forceRenderTypeVanilla(HudElementType.CHAT)) {
					if (!preventElementRenderType(HudElementType.CHAT)) {
						bind(Gui.ICONS);
						GlStateManager.enableBlend();
				        GlStateManager.pushMatrix();
				        GlStateManager.translate((float) event.getPosX(), (float) event.getPosY(), 0.0F);
				        this.persistantChatGUI.drawChat(this.updateCounter);
				        GlStateManager.popMatrix();
						GlStateManager.disableBlend();
					}

					if (!preventEventType(HudElementType.CHAT)) {
						if (MinecraftForge.EVENT_BUS.post(event))
							return;
						post(CHAT);
					}
				} else {
					if (!preventElementRenderType(HudElementType.CHAT)) {
						bind(Gui.ICONS);
						GlStateManager.enableBlend();
						this.rpgHud.getActiveHud().drawElement(HudElementType.CHAT, this, this.zLevel, partialTicks);

						GlStateManager.disableBlend();
					}
					if (!preventEventType(HudElementType.CHAT)) {
						if (MinecraftForge.EVENT_BUS.post(event))
							return;
						post(CHAT);
					}
				}
			} else {
				if (forceRenderTypeVanilla(HudElementType.CHAT)) {
					if (!preventEventType(HudElementType.CHAT)) {
						if (MinecraftForge.EVENT_BUS.post(event))
							return;
					}
					if (!preventElementRenderType(HudElementType.CHAT)) {
						bind(Gui.ICONS);
						GlStateManager.enableBlend();
				        GlStateManager.pushMatrix();
				        GlStateManager.translate((float) event.getPosX(), (float) event.getPosY(), 0.0F);
				        this.persistantChatGUI.drawChat(this.updateCounter);
				        GlStateManager.popMatrix();
						GlStateManager.disableBlend();
					}
					if (!preventEventType(HudElementType.CHAT)) {
						post(CHAT);
					}
				} else {
					if (!preventEventType(HudElementType.CHAT)) {
						if (MinecraftForge.EVENT_BUS.post(event))
							return;
					}
					if (!preventElementRenderType(HudElementType.CHAT)) {
						bind(Gui.ICONS);
						GlStateManager.enableBlend();
						this.rpgHud.getActiveHud().drawElement(HudElementType.CHAT, this, this.zLevel, partialTicks);

						GlStateManager.disableBlend();
					}
					if (!preventEventType(HudElementType.CHAT)) {
						post(CHAT);
					}
				}
			}
		}
	}

	/**
	 * Checks if the HudElementType has a setting to force it to be rendered
	 * regardless of the forge event and if it is activated
	 */
	private boolean forceRenderType(HudElementType type) {
		EnumOptionsDebugMod option = EnumOptionsDebugMod.getEnumOptionOfWith(type, EnumOptionsType.FORCE_RENDER);
		if (option != null) {
			return this.rpgHud.settingsDebug.getOptionOrdinalValue(option);
		}
		return false;
	}

	/**
	 * Checks if the HudElementType has a setting to prevent it's rendering and
	 * if it is activated
	 */
	private boolean preventElementRenderType(HudElementType type) {
		EnumOptionsDebugMod option = EnumOptionsDebugMod.getEnumOptionOfWith(type, EnumOptionsType.PREVENT_RENDER);
		if (option != null) {
			return this.rpgHud.settingsDebug.getOptionOrdinalValue(option);
		}
		return false;
	}

	/**
	 * Checks if the HudElementType has a setting to force the vanilla hud
	 * element to be rendered and if it is activated
	 */
	private boolean forceRenderTypeVanilla(HudElementType type) {
		EnumOptionsDebugMod option = EnumOptionsDebugMod.getEnumOptionOfWith(type, EnumOptionsType.RENDER_VANILLA);
		if (option != null) {
			return this.rpgHud.settingsDebug.getOptionOrdinalValue(option);
		}
		return false;
	}

	/**
	 * Checks if the HudElementType has a setting to prevent the forge event and
	 * if it is activated
	 */
	private boolean preventEventType(HudElementType type) {
		EnumOptionsDebugMod option = EnumOptionsDebugMod.getEnumOptionOfWith(type, EnumOptionsType.PREVENT_EVENT);
		if (option != null) {
			return this.rpgHud.settingsDebug.getOptionOrdinalValue(option);
		}
		return false;
	}

	/**
	 * Returns the Forge RenderOverlayEvent alias of the respective
	 * HudElementType. Returns null if it has none
	 * 
	 * @param type
	 *            the HudElementType
	 * @return RenderOverlayEvent type
	 */
	private static ElementType getEventAlias(HudElementType type) {
		switch (type) {
		case HOTBAR:
			return ElementType.HOTBAR;
		case CROSSHAIR:
			return ElementType.CROSSHAIRS;
		case HEALTH:
			return ElementType.HEALTH;
		case ARMOR:
			return ElementType.ARMOR;
		case FOOD:
			return ElementType.FOOD;
		case HEALTH_MOUNT:
			return ElementType.HEALTHMOUNT;
		case AIR:
			return ElementType.AIR;
		case JUMP_BAR:
			return ElementType.JUMPBAR;
		case EXPERIENCE:
			return ElementType.EXPERIENCE;
		default:
			return null;
		}
	}
}
