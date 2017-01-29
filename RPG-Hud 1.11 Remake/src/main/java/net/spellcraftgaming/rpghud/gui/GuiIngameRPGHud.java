package net.spellcraftgaming.rpghud.gui;

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

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
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
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.MinecraftForge;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.main.ModRPGHud;

public class GuiIngameRPGHud extends GuiIngame{

    //private static final ResourceLocation VIGNETTE     = new ResourceLocation("textures/misc/vignette.png");
    //private static final ResourceLocation WIDGITS      = new ResourceLocation("textures/gui/widgets.png");
    //private static final ResourceLocation PUMPKIN_BLUR = new ResourceLocation("textures/misc/pumpkinblur.png");

    private static final int WHITE = 0xFFFFFF;

    //Flags to toggle the rendering of certain aspects of the HUD, valid conditions
    //must be met for them to render normally. If those conditions are met, but this flag
    //is false, they will not be rendered.
    public static boolean renderHelmet = true;
    public static boolean renderPortal = true;
    public static boolean renderHotbar = true;
    public static boolean renderCrosshairs = true;
    public static boolean renderBossHealth = true;
    public static boolean renderHealth = true;
    public static boolean renderArmor = true;
    public static boolean renderFood = true;
    public static boolean renderHealthMount = true;
    public static boolean renderAir = true;
    public static boolean renderExperience = true;
    public static boolean renderJumpBar = true;
    public static boolean renderObjective = true;
    public static boolean renderClock = true;
    public static boolean renderDetails = true;

    public static int left_height = 39;
    public static int right_height = 39;

    private ScaledResolution res = null;
    private FontRenderer fontrenderer = null;
    private RenderGameOverlayEvent eventParent;
    //private static final String MC_VERSION = MinecraftForge.MC_VERSION;
    private GuiOverlayDebugForge debugOverlay;

    private ModRPGHud rpgHud;
    public GuiIngameRPGHud(Minecraft mc)
    {
        super(mc);
        this.debugOverlay = new GuiOverlayDebugForge(mc);
        this.rpgHud = ModRPGHud.instance;
    }

    @Override
    public void renderGameOverlay(float partialTicks)
    {
        this.res = new ScaledResolution(this.mc);
        this.eventParent = new RenderGameOverlayEvent(partialTicks, this.res);
        int width = this.res.getScaledWidth();
        int height = this.res.getScaledHeight();

        right_height = 39;
        left_height = 39;

        if (pre(ALL)) return;

        this.fontrenderer = this.mc.fontRendererObj;
        this.mc.entityRenderer.setupOverlayRendering();
        GlStateManager.enableBlend();

        if (Minecraft.isFancyGraphicsEnabled())
        {
            renderVignette(this.mc.player.getBrightness(partialTicks), this.res);
        }
        else
        {
            GlStateManager.enableDepth();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        }

        if (renderHelmet) renderHelmet(this.res, partialTicks);

        if (renderPortal && !this.mc.player.isPotionActive(MobEffects.NAUSEA))
        {
            renderPortal(this.res, partialTicks);
        }

        if (renderHotbar) this.drawElement(HudElementType.HOTBAR, partialTicks);

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.zLevel = -90.0F;
        this.rand.setSeed(this.updateCounter * 312871);

        this.drawElement(HudElementType.CROSSHAIR, partialTicks);
        
        if (renderBossHealth) renderBossHealth();

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        if (this.mc.playerController.shouldDrawHUD() && this.mc.getRenderViewEntity() instanceof EntityPlayer)
        {
            if (renderHealth) this.drawElement(HudElementType.HEALTH, partialTicks);
            if (renderArmor)  this.drawElement(HudElementType.ARMOR, partialTicks);
            if (renderFood)   this.drawElement(HudElementType.FOOD, partialTicks);
            if (renderHealthMount) this.drawElement(HudElementType.HEALTH_MOUNT, partialTicks);
            if (renderAir)    this.drawElement(HudElementType.AIR, partialTicks);
            if (renderClock) this.drawElement(HudElementType.CLOCK, partialTicks);
            if (renderDetails) this.drawElement(HudElementType.DETAILS, partialTicks);
        }

        renderSleepFade(width, height);

        if (renderExperience) this.drawElement(HudElementType.EXPERIENCE, partialTicks);
        if (renderExperience) this.drawElement(HudElementType.LEVEL, partialTicks);
        
        if (renderJumpBar) this.drawElement(HudElementType.JUMP_BAR, partialTicks);
        

        renderToolHighlight(this.res);
        renderHUDText(width);
        renderFPSGraph();
        renderPotionIcons(this.res);
        renderRecordOverlay(width, height, partialTicks);
        renderSubtitles();
        renderTitle(width, height, partialTicks);


        Scoreboard scoreboard = this.mc.world.getScoreboard();
        ScoreObjective objective = null;
        ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(this.mc.player.getName());
        if (scoreplayerteam != null)
        {
            int slot = scoreplayerteam.getChatFormat().getColorIndex();
            if (slot >= 0) objective = scoreboard.getObjectiveInDisplaySlot(3 + slot);
        }
        ScoreObjective scoreobjective1 = objective != null ? objective : scoreboard.getObjectiveInDisplaySlot(1);
        if (renderObjective && scoreobjective1 != null)
        {
            this.renderScoreboard(scoreobjective1, this.res);
        }

        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GlStateManager.disableAlpha();

        renderChat(height);

        renderPlayerList(width);

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableLighting();
        GlStateManager.enableAlpha();

        post(ALL);
    }

    public ScaledResolution getResolution()
    {
        return this.res;
    }

    private void renderPotionIcons(ScaledResolution resolution)
    {
        if (pre(POTION_ICONS)) return;
        super.renderPotionEffects(resolution);
        post(POTION_ICONS);
    }

    private void renderSubtitles()
    {
        if (pre(SUBTITLES)) return;
        this.overlaySubtitle.renderSubtitles(this.res);
        post(SUBTITLES);
    }

    //@Override
    private void renderBossHealth()
    {
        if (pre(BOSSHEALTH)) return;
        bind(Gui.ICONS);
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        this.mc.mcProfiler.startSection("bossHealth");
        GlStateManager.enableBlend();
        this.overlayBoss.renderBossHealth();
        GlStateManager.disableBlend();
        this.mc.mcProfiler.endSection();
        post(BOSSHEALTH);
    }

    private void renderHelmet(ScaledResolution res, float partialTicks)
    {
        if (pre(HELMET)) return;

        ItemStack itemstack = this.mc.player.inventory.armorItemInSlot(3);

        if (this.mc.gameSettings.thirdPersonView == 0 && itemstack != null && itemstack.getItem() != null)
        {
            if (itemstack.getItem() == Item.getItemFromBlock(Blocks.PUMPKIN))
            {
                renderPumpkinOverlay(res);
            }
            else
            {
                itemstack.getItem().renderHelmetOverlay(itemstack, this.mc.player, res, partialTicks);
            }
        }

        post(HELMET);
    }
    
    private void renderPortal(ScaledResolution res, float partialTicks)
    {
        if (pre(PORTAL)) return;

        float f1 = this.mc.player.prevTimeInPortal + (this.mc.player.timeInPortal - this.mc.player.prevTimeInPortal) * partialTicks;

        if (f1 > 0.0F)
        {
            renderPortal(f1, res);
        }

        post(PORTAL);
    }

    private void renderSleepFade(int width, int height)
    {
        if (this.mc.player.getSleepTimer() > 0)
        {
            this.mc.mcProfiler.startSection("sleep");
            GlStateManager.disableDepth();
            GlStateManager.disableAlpha();
            int sleepTime = this.mc.player.getSleepTimer();
            float opacity = sleepTime / 100.0F;

            if (opacity > 1.0F)
            {
                opacity = 1.0F - (sleepTime - 100) / 10.0F;
            }

            int color = (int)(220.0F * opacity) << 24 | 1052704;
            drawRect(0, 0, width, height, color);
            GlStateManager.enableAlpha();
            GlStateManager.enableDepth();
            this.mc.mcProfiler.endSection();
        }
    }
    
    private void renderToolHighlight(ScaledResolution res)
    {
        if (this.mc.gameSettings.heldItemTooltips && !this.mc.playerController.isSpectator())
        {
            this.mc.mcProfiler.startSection("toolHighlight");

            if (this.remainingHighlightTicks > 0 && this.highlightingItemStack != null)
            {
                String name = this.highlightingItemStack.getDisplayName();
                if (this.highlightingItemStack.hasDisplayName())
                    name = TextFormatting.ITALIC + name;

                name = this.highlightingItemStack.getItem().getHighlightTip(this.highlightingItemStack, name);

                int opacity = (int)(this.remainingHighlightTicks * 256.0F / 10.0F);
                if (opacity > 255) opacity = 255;

                if (opacity > 0)
                {
                    int y = res.getScaledHeight() - 59;
                    if (!this.mc.playerController.shouldDrawHUD()) y += 14;

                    GlStateManager.pushMatrix();
                    GlStateManager.enableBlend();
                    GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                    FontRenderer font = this.highlightingItemStack.getItem().getFontRenderer(this.highlightingItemStack);
                    if (font != null)
                    {
                        int x = (res.getScaledWidth() - font.getStringWidth(name)) / 2;
                        font.drawStringWithShadow(name, x, y, WHITE | (opacity << 24));
                    }
                    else
                    {
                        int x = (res.getScaledWidth() - this.fontrenderer.getStringWidth(name)) / 2;
                        this.fontrenderer.drawStringWithShadow(name, x, y, WHITE | (opacity << 24));
                    }
                    GlStateManager.disableBlend();
                    GlStateManager.popMatrix();
                }
            }

            this.mc.mcProfiler.endSection();
        }
        else if (this.mc.player.isSpectator())
        {
            this.spectatorGui.renderSelectedItem(res);
        }
    }

    private void renderHUDText(int width)
    {
        this.mc.mcProfiler.startSection("forgeHudText");
        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        ArrayList<String> listL = new ArrayList<String>();
        ArrayList<String> listR = new ArrayList<String>();

        if (this.mc.isDemo())
        {
            long time = this.mc.world.getTotalWorldTime();
            if (time >= 120500L)
            {
                listR.add(I18n.format("demo.demoExpired"));
            }
            else
            {
                listR.add(I18n.format("demo.remainingTime", StringUtils.ticksToElapsedTime((int)(120500L - time))));
            }
        }

        if (this.mc.gameSettings.showDebugInfo && !pre(DEBUG))
        {
            listL.addAll(this.debugOverlay.getLeft());
            listR.addAll(this.debugOverlay.getRight());
            post(DEBUG);
        }

        RenderGameOverlayEvent.Text event = new RenderGameOverlayEvent.Text(this.eventParent, listL, listR);
        if (!MinecraftForge.EVENT_BUS.post(event))
        {
            int top = 2;
            for (String msg : listL)
            {
                if (msg == null) continue;
                drawRect(1, top - 1, 2 + this.fontrenderer.getStringWidth(msg) + 1, top + this.fontrenderer.FONT_HEIGHT - 1, -1873784752);
                this.fontrenderer.drawString(msg, 2, top, 14737632);
                top += this.fontrenderer.FONT_HEIGHT;
            }

            top = 2;
            for (String msg : listR)
            {
                if (msg == null) continue;
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

    private void renderFPSGraph()
    {
        if (this.mc.gameSettings.showDebugInfo && this.mc.gameSettings.showLagometer && !pre(FPS_GRAPH))
        {
            this.debugOverlay.renderLagometer();
            post(FPS_GRAPH);
        }
    }

    private void renderRecordOverlay(int width, int height, float partialTicks)
    {
        if (overlayMessageTime > 0)
        {
            this.mc.mcProfiler.startSection("overlayMessage");
            float hue = this.overlayMessageTime - partialTicks;
            int opacity = (int)(hue * 256.0F / 20.0F);
            if (opacity > 255) opacity = 255;

            if (opacity > 0)
            {
                GlStateManager.pushMatrix();
                GlStateManager.translate(width / 2, height - 68, 0.0F);
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                int color = (this.animateOverlayMessageColor ? Color.HSBtoRGB(hue / 50.0F, 0.7F, 0.6F) & WHITE : WHITE);
                this.fontrenderer.drawString(this.overlayMessage, -this.fontrenderer.getStringWidth(this.overlayMessage) / 2, -4, color | (opacity << 24));
                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            }

            this.mc.mcProfiler.endSection();
        }
    }

    private void renderTitle(int width, int height, float partialTicks)
    {
        if (this.titlesTimer > 0)
        {
            this.mc.mcProfiler.startSection("titleAndSubtitle");
            float age = this.titlesTimer - partialTicks;
            int opacity = 255;

            if (this.titlesTimer > this.titleFadeOut + this.titleDisplayTime)
            {
                float f3 = this.titleFadeIn + this.titleDisplayTime + this.titleFadeOut - age;
                opacity = (int)(f3 * 255.0F / this.titleFadeIn);
            }
            if (this.titlesTimer <= this.titleFadeOut) opacity = (int)(age * 255.0F / this.titleFadeOut);

            opacity = MathHelper.clamp(opacity, 0, 255);

            if (opacity > 8)
            {
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

    private void renderChat(int height)
    {
        this.mc.mcProfiler.startSection("chat");

        RenderGameOverlayEvent.Chat event = new RenderGameOverlayEvent.Chat(this.eventParent, 0, height - 48);
        if (MinecraftForge.EVENT_BUS.post(event)) return;

        GlStateManager.pushMatrix();
        GlStateManager.translate(event.getPosX(), event.getPosY(), 0.0F);
        this.persistantChatGUI.drawChat(this.updateCounter);
        GlStateManager.popMatrix();

        post(CHAT);

        this.mc.mcProfiler.endSection();
    }

    private void renderPlayerList(int width)
    {
        ScoreObjective scoreobjective = this.mc.world.getScoreboard().getObjectiveInDisplaySlot(0);
        NetHandlerPlayClient handler = this.mc.player.connection;

        if (this.mc.gameSettings.keyBindPlayerList.isKeyDown() && (!this.mc.isIntegratedServerRunning() || handler.getPlayerInfoMap().size() > 1 || scoreobjective != null))
        {
            this.overlayPlayerList.updatePlayerList(true);
            if (pre(PLAYER_LIST)) return;
            this.overlayPlayerList.renderPlayerlist(width, this.mc.world.getScoreboard(), scoreobjective);
            post(PLAYER_LIST);
        }
        else
        {
            this.overlayPlayerList.updatePlayerList(false);
        }
    }

    //Helper macros
    private boolean pre(ElementType type)
    {
        return MinecraftForge.EVENT_BUS.post(new RenderGameOverlayEvent.Pre(this.eventParent, type));
    }
    
    private void post(ElementType type)
    {
        MinecraftForge.EVENT_BUS.post(new RenderGameOverlayEvent.Post(this.eventParent, type));
    }
    
    public void bind(ResourceLocation res)
    {
        this.mc.getTextureManager().bindTexture(res);
    }

    private class GuiOverlayDebugForge extends GuiOverlayDebug
    {
        private Minecraft mc;
        private GuiOverlayDebugForge(Minecraft mc)
        {
            super(mc);
            this.mc = mc;
        }
        @Override protected void renderDebugInfoLeft(){}
        @Override protected void renderDebugInfoRight(ScaledResolution res){}
        private List<String> getLeft()
        {
            List<String> ret = this.call();
            ret.add("");
            ret.add("Debug: Pie [shift]: " + (this.mc.gameSettings.showDebugProfilerChart ? "visible" : "hidden") + " FPS [alt]: " + (this.mc.gameSettings.showLagometer ? "visible" : "hidden"));
            ret.add("For help: press F3 + Q");
            return ret;
        }
        private List<String> getRight(){ return this.getDebugInfoRight(); }
    }
    
    public int getUpdateCounter(){
		return this.updateCounter;
    	
    }
    
    
    private void drawElement(HudElementType type, float partialTicks)
    {
    	//Check if conditions for rendering the element are met
    	if(this.rpgHud.getActiveHud().checkElementConditions(type)) {
    		//Get RenderOverlayEvent alias for the type of this element
        	ElementType alias = getEventAlias(type);
        	
        	//Check if the debug setting "force render" for this type is activated
        	if(forceRenderType(type)) {
        		//Check if the debug setting "force vanilla" for this type is activated
        		if(forceRenderTypeVanilla(type)){
        			//Check if the debug setting "prevent element from rendering" for this type is activated
        	        if(!preventElementRenderType(type)) {
        	        	bind(Gui.ICONS);
            	        GlStateManager.enableBlend();
            	        
            	        //Draws the element of this type of the vanilla HUD
        	        	this.rpgHud.getVanillaHud().drawElement(type, this, partialTicks, partialTicks);
        	        	
        	        	GlStateManager.disableBlend();
        	        }
        	        
        	        //Check if this type has an RenderOverlayEvent alias and if the event should be prevented
        	        if(alias != null && !preventEventType(type)) {
        	        	//Initiates the event
        	        	if (pre(alias)) return;
            	        post(alias);
        	        }
        		} else {
        			//Check if the debug setting "prevent element from rendering" for this type is activated
        	        if(!preventElementRenderType(type)) {
        	        	bind(Gui.ICONS);
            	        GlStateManager.enableBlend();
            	        
            	        //Draws the element of this type of the active HUD
        	        	this.rpgHud.getActiveHud().drawElement(type, this, partialTicks, partialTicks);
        	        	
        	        	GlStateManager.disableBlend();
        	        }
        	        
        	        //Check if this type has an RenderOverlayEvent alias and if the event should be prevented
        	        if(alias != null && !preventEventType(type)) {
        	        	//Initiates the event
        	        	if (pre(alias)) return;
            	        post(alias);
        	        }
        		}
        	} else {
        		//Check if the debug setting "force vanilla" for this type is activated
        		if(forceRenderTypeVanilla(type)){
        			//Check if this type has an RenderOverlayEvent alias and if the event should be prevented
        			if(alias != null && !preventEventType(type)) {
        				if (pre(alias)) return;
        			}
        	        
        			//Check if the debug setting "prevent element from rendering" for this type is activated
        	        if(!preventElementRenderType(type)) {
        	        	bind(Gui.ICONS);
            	        GlStateManager.enableBlend();
            	        
            	        //Draws the element of this type of the vanilla HUD
        	        	this.rpgHud.getVanillaHud().drawElement(type, this, partialTicks, partialTicks);
        	        	
        	        	GlStateManager.disableBlend();
        	        }
        	        
        	        //Check if this type has an RenderOverlayEvent alias and if the event should be prevented
        	        if(alias != null && !preventEventType(type)) {
        	        	post(alias);
        	        }
        		} else {
        			//Check if this type has an RenderOverlayEvent alias and if the event should be prevented
        			if(alias != null && !preventEventType(type)) {
        				if (pre(alias)) return;
        			}
        	        
        			//Check if the debug setting "prevent element from rendering" for this type is activated
        	        if(!preventElementRenderType(type)) {
        	        	bind(Gui.ICONS);
            	        GlStateManager.enableBlend();
            	        
            	        //Draws the element of this type of the active HUD
        	        	this.rpgHud.getActiveHud().drawElement(type, this, partialTicks, partialTicks);
        	        	
        	        	GlStateManager.disableBlend();
        	        }
        	        
        	        //Check if this type has an RenderOverlayEvent alias and if the event should be prevented
        	        if(alias != null && !preventEventType(type)) {
        	        	post(alias);
        	        }
        		}
        	}
    	}
    	
    }
    
    private boolean forceRenderType(HudElementType type) {
    	switch(type) {
    	case HOTBAR:
    		return this.rpgHud.settingsDebug.forceRenderHotbar;
    	case CROSSHAIR:
    		return this.rpgHud.settingsDebug.forceRenderCrosshair;
    	case HEALTH:
    		return this.rpgHud.settingsDebug.forceRenderHealth;
    	case ARMOR:
    		return this.rpgHud.settingsDebug.forceRenderArmor;
    	case FOOD:
    		return this.rpgHud.settingsDebug.forceRenderFood;
    	case HEALTH_MOUNT:
    		return this.rpgHud.settingsDebug.forceRenderHealthMount;
    	case AIR:
    		return this.rpgHud.settingsDebug.forceRenderAir;
    	case JUMP_BAR:
    		return this.rpgHud.settingsDebug.forceRenderJumpBar;
    	case EXPERIENCE:
    		return this.rpgHud.settingsDebug.forceRenderExp;
    	case LEVEL:
    		return this.rpgHud.settingsDebug.forceRenderExpLv;
    	default: 
    		return false;
    	}
    }
    
    private boolean preventElementRenderType(HudElementType type) {
    	switch(type) {
    	case HOTBAR:
    		return this.rpgHud.settingsDebug.preventElementRenderHotbar;
    	case CROSSHAIR:
    		return this.rpgHud.settingsDebug.preventElementRenderCrosshair;
    	case HEALTH:
    		return this.rpgHud.settingsDebug.preventElementRenderHealth;
    	case ARMOR:
    		return this.rpgHud.settingsDebug.preventElementRenderArmor;
    	case FOOD:
    		return this.rpgHud.settingsDebug.preventElementRenderFood;
    	case HEALTH_MOUNT:
    		return this.rpgHud.settingsDebug.preventElementRenderHealthMount;
    	case AIR:
    		return this.rpgHud.settingsDebug.preventElementRenderAir;
    	case JUMP_BAR:
    		return this.rpgHud.settingsDebug.preventElementRenderJumpBar;
    	case EXPERIENCE:
    		return this.rpgHud.settingsDebug.preventElementRenderExp;
    	case LEVEL:
    		return this.rpgHud.settingsDebug.preventElementRenderExpLv;
    	default: 
    		return false;
    	}
    }
    
    private boolean forceRenderTypeVanilla(HudElementType type) {
    	switch(type) {
    	case HOTBAR:
    		return this.rpgHud.settingsDebug.renderVanillaHotbar;
    	case CROSSHAIR:
    		return this.rpgHud.settingsDebug.renderVanillaCrosshair;
    	case HEALTH:
    		return this.rpgHud.settingsDebug.renderVanillaHealth;
    	case ARMOR:
    		return this.rpgHud.settingsDebug.renderVanillaArmor;
    	case FOOD:
    		return this.rpgHud.settingsDebug.renderVanillaFood;
    	case HEALTH_MOUNT:
    		return this.rpgHud.settingsDebug.renderVanillaHealthMount;
    	case AIR:
    		return this.rpgHud.settingsDebug.renderVanillaAir;
    	case JUMP_BAR:
    		return this.rpgHud.settingsDebug.renderVanillaJumpBar;
    	case EXPERIENCE:
    		return this.rpgHud.settingsDebug.renderVanillaExp;
    	case LEVEL:
    		return this.rpgHud.settingsDebug.renderVanillaExpLv;
    	default: 
    		return false;
    	}
    }
    
    private boolean preventEventType(HudElementType type) {
    	switch(type) {
    	case HOTBAR:
    		return this.rpgHud.settingsDebug.preventEventHotbar;
    	case CROSSHAIR:
    		return this.rpgHud.settingsDebug.preventEventCrosshair;
    	case HEALTH:
    		return this.rpgHud.settingsDebug.preventEventHealth;
    	case ARMOR:
    		return this.rpgHud.settingsDebug.preventEventArmor;
    	case FOOD:
    		return this.rpgHud.settingsDebug.preventEventFood;
    	case HEALTH_MOUNT:
    		return this.rpgHud.settingsDebug.preventEventHealthMount;
    	case AIR:
    		return this.rpgHud.settingsDebug.preventEventAir;
    	case JUMP_BAR:
    		return this.rpgHud.settingsDebug.preventEventJumpBar;
    	case EXPERIENCE:
    		return this.rpgHud.settingsDebug.preventEventExp;
    	case LEVEL:
    		return this.rpgHud.settingsDebug.preventEventExpLv;
    	default: 
    		return false;
    	}
    }
    
    private static ElementType getEventAlias(HudElementType type) {
    	switch(type) {
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
