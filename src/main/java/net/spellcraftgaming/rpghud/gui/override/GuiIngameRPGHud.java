package net.spellcraftgaming.rpghud.gui.override;

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
import static net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType.VIGNETTE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.overlay.DebugOverlayGui;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.PotionSpriteUploader;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.GameType;
import net.minecraft.world.border.WorldBorder;
import net.minecraftforge.client.ForgeIngameGui;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.MinecraftForge;
import net.spellcraftgaming.rpghud.gui.hud.HudHotbarWidget;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.settings.Settings;

public class GuiIngameRPGHud extends ForgeIngameGui {
    /** Instance of the RPG-Hud mod */
    private ModRPGHud rpgHud;

    private FontRenderer fontrenderer = null;
    private RenderGameOverlayEvent eventParent;
    // private static final String MC_VERSION = MinecraftForge.MC_VERSION;
    private GuiOverlayDebugForge debugOverlay;

    public GuiIngameRPGHud(Minecraft mc) {
        super(mc);
        this.debugOverlay = new GuiOverlayDebugForge(mc);
        this.rpgHud = ModRPGHud.instance;
    }

    @Override
    public void renderGameOverlay(float partialTicks) {
        this.scaledWidth = this.mc.mainWindow.getScaledWidth();
        this.scaledHeight = this.mc.mainWindow.getScaledHeight();
        this.eventParent = new RenderGameOverlayEvent(partialTicks, this.mc.mainWindow);
        renderHealthMount = this.mc.player.getRidingEntity() instanceof LivingEntity;
        renderFood = this.mc.player.getRidingEntity() == null;
        renderJumpBar = this.mc.player.isRidingHorse();

        right_height = 39;
        left_height = 39;

        if(this.pre(ALL))
            return;

        this.fontrenderer = this.mc.fontRenderer;
        // mc.entityRenderer.setupOverlayRendering();
        GlStateManager.enableBlend();
        if(renderVignette && Minecraft.isFancyGraphicsEnabled())
            this.renderVignette(this.mc.getRenderViewEntity());
        else {
            GlStateManager.enableDepthTest();
            GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
                    GlStateManager.DestFactor.ZERO);
        }

        if(renderHelmet)
            this.renderHelmet(partialTicks);

        if(renderPortal && !this.mc.player.isPotionActive(Effects.NAUSEA))
            this.renderPortal(partialTicks);

        this.drawElement(HudElementType.WIDGET, partialTicks);

        this.drawElement(HudElementType.HOTBAR, partialTicks);

        if(!this.mc.gameSettings.hideGUI) {
            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.blitOffset = -90;
            this.rand.setSeed(this.ticks * 312871);

            this.drawElement(HudElementType.CROSSHAIR, partialTicks);

            if(renderBossHealth)
                this.renderBossHealth();

            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            if(this.mc.playerController.shouldDrawHUD() && this.mc.getRenderViewEntity() instanceof PlayerEntity) {
                this.drawElement(HudElementType.HEALTH, partialTicks);
                this.drawElement(HudElementType.ARMOR, partialTicks);
                this.drawElement(HudElementType.FOOD, partialTicks);
                this.drawElement(HudElementType.HEALTH_MOUNT, partialTicks);
                this.drawElement(HudElementType.AIR, partialTicks);
                this.drawElement(HudElementType.CLOCK, partialTicks);
                this.drawElement(HudElementType.DETAILS, partialTicks);
                this.drawElement(HudElementType.COMPASS, partialTicks);
                this.drawElement(HudElementType.ENTITY_INSPECT, partialTicks);
            }

            this.drawElement(HudElementType.EXPERIENCE, partialTicks);
            this.drawElement(HudElementType.LEVEL, partialTicks);

            this.drawElement(HudElementType.JUMP_BAR, partialTicks);

            if(this.mc.gameSettings.heldItemTooltips && this.mc.playerController.getCurrentGameType() != GameType.SPECTATOR)
                this.renderSelectedItem();
            else if(this.mc.player.isSpectator())
                this.spectatorGui.renderSelectedItem();
        }

        this.renderSleepFade(this.scaledWidth, this.scaledHeight);

        this.renderHUDText(this.scaledWidth, this.scaledHeight);
        this.renderFPSGraph();
        this.renderPotionEffects();
        if(!this.mc.gameSettings.hideGUI) {
            this.drawElement(HudElementType.RECORD_OVERLAY, partialTicks);
            this.renderSubtitles();
            this.renderTitle(this.scaledWidth, this.scaledHeight, partialTicks);
        }

        Scoreboard scoreboard = this.mc.world.getScoreboard();
        ScoreObjective objective = null;
        ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(this.mc.player.getScoreboardName());
        if(scoreplayerteam != null) {
            int slot = scoreplayerteam.getColor().getColorIndex();
            if(slot >= 0)
                objective = scoreboard.getObjectiveInDisplaySlot(3 + slot);
        }
        ScoreObjective scoreobjective1 = objective != null ? objective : scoreboard.getObjectiveInDisplaySlot(1);
        if(renderObjective && scoreobjective1 != null)
            this.renderScoreboard(scoreobjective1);

        GlStateManager.enableBlend();
        GlStateManager.blendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GlStateManager.disableAlphaTest();

        this.renderChat(this.scaledWidth, this.scaledHeight);

        this.renderPlayerList(this.scaledWidth, this.scaledHeight);

        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableLighting();
        GlStateManager.enableAlphaTest();

        this.post(ALL);
    }

    @Override
    protected void renderPotionEffects() {
        if(this.pre(POTION_ICONS))
            return;
        Collection<EffectInstance> collection = this.mc.player.getActivePotionEffects();
        if(!collection.isEmpty()) {
            GlStateManager.enableBlend();
            int i = 0;
            int j = 0;
            PotionSpriteUploader potionspriteuploader = this.mc.getPotionSpriteUploader();
            List<Runnable> list = Lists.newArrayListWithExpectedSize(collection.size());
            this.mc.getTextureManager().bindTexture(ContainerScreen.INVENTORY_BACKGROUND);

            for(EffectInstance effectinstance : Ordering.natural().reverse().sortedCopy(collection)) {
                Effect effect = effectinstance.getPotion();
                if(!effect.shouldRenderHUD(effectinstance))
                    continue;
                // Rebind in case previous renderHUDEffect changed texture
                this.mc.getTextureManager().bindTexture(ContainerScreen.INVENTORY_BACKGROUND);
                if(effectinstance.isShowIcon()) {
                    int k = this.scaledWidth;
                    int l = 1;
                    if(this.mc.isDemo())
                        l += 15;

                    if(effect.isBeneficial()) {
                        ++i;
                        k = k - 25 * i;
                    } else {
                        ++j;
                        k = k - 25 * j;
                        l += 26;
                    }

                    GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                    float f = 1.0F;
                    if(effectinstance.isAmbient())
                        this.blit(k, l, 165, 166, 24, 24);
                    else {
                        this.blit(k, l, 141, 166, 24, 24);
                        if(effectinstance.getDuration() <= 200) {
                            int i1 = 10 - effectinstance.getDuration() / 20;
                            f = MathHelper.clamp(effectinstance.getDuration() / 10.0F / 5.0F * 0.5F, 0.0F, 0.5F)
                                    + MathHelper.cos(effectinstance.getDuration() * (float) Math.PI / 5.0F) * MathHelper.clamp(i1 / 10.0F * 0.25F, 0.0F, 0.25F);
                        }
                    }

                    float f_f = f;
                    int k_f = k;
                    int l_f = l;

                    TextureAtlasSprite textureatlassprite = potionspriteuploader.getSprite(effect);
                    list.add(() -> {
                        GlStateManager.color4f(1.0F, 1.0F, 1.0F, f_f);
                        blit(k_f + 3, l_f + 3, this.blitOffset, 18, 18, textureatlassprite);
                    });
                    effect.renderHUDEffect(effectinstance, this, k, l, this.blitOffset, f);
                }
            }

            this.mc.getTextureManager().bindTexture(AtlasTexture.LOCATION_EFFECTS_TEXTURE);
            list.forEach(Runnable::run);
        }
        this.post(POTION_ICONS);
    }

    @Override
    protected void renderSubtitles() {
        if(this.pre(SUBTITLES))
            return;
        this.overlaySubtitle.render();
        this.post(SUBTITLES);
    }

    @Override
    protected void renderBossHealth() {
        if(this.pre(BOSSHEALTH))
            return;
        this.bind(AbstractGui.GUI_ICONS_LOCATION);
        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
                GlStateManager.DestFactor.ZERO);
        this.mc.getProfiler().startSection("bossHealth");
        GlStateManager.enableBlend();
        this.overlayBoss.render();
        GlStateManager.disableBlend();
        this.mc.getProfiler().endSection();
        this.post(BOSSHEALTH);
    }

    @Override
    protected void renderVignette(Entity entity) {
        if(this.pre(VIGNETTE)) {
            // Need to put this here, since Vanilla assumes this state after the vignette
            // was rendered.
            GlStateManager.enableDepthTest();
            GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
                    GlStateManager.DestFactor.ZERO);
            return;
        }
        WorldBorder worldborder = this.mc.world.getWorldBorder();
        float f = (float) worldborder.getClosestDistance(entity);
        double d0 = Math.min(worldborder.getResizeSpeed() * worldborder.getWarningTime() * 1000.0D,
                Math.abs(worldborder.getTargetSize() - worldborder.getDiameter()));
        double d1 = Math.max(worldborder.getWarningDistance(), d0);
        if(f < d1)
            f = 1.0F - (float) (f / d1);
        else
            f = 0.0F;

        GlStateManager.disableDepthTest();
        GlStateManager.depthMask(false);
        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE,
                GlStateManager.DestFactor.ZERO);
        if(f > 0.0F)
            GlStateManager.color4f(0.0F, f, f, 1.0F);
        else
            GlStateManager.color4f(this.prevVignetteBrightness, this.prevVignetteBrightness, this.prevVignetteBrightness, 1.0F);

        this.mc.getTextureManager().bindTexture(VIGNETTE_TEX_PATH);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(0.0D, this.scaledHeight, -90.0D).tex(0.0D, 1.0D).endVertex();
        bufferbuilder.pos(this.scaledWidth, this.scaledHeight, -90.0D).tex(1.0D, 1.0D).endVertex();
        bufferbuilder.pos(this.scaledWidth, 0.0D, -90.0D).tex(1.0D, 0.0D).endVertex();
        bufferbuilder.pos(0.0D, 0.0D, -90.0D).tex(0.0D, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepthTest();
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
                GlStateManager.DestFactor.ZERO);
        this.post(VIGNETTE);
    }

    private void renderHelmet(float partialTicks) {
        if(this.pre(HELMET))
            return;

        ItemStack itemstack = this.mc.player.inventory.armorItemInSlot(3);

        if(this.mc.gameSettings.thirdPersonView == 0 && !itemstack.isEmpty()) {
            Item item = itemstack.getItem();
            if(item == Blocks.CARVED_PUMPKIN.asItem())
                this.renderPumpkinOverlay();
            else
                item.renderHelmetOverlay(itemstack, this.mc.player, this.scaledWidth, this.scaledHeight, partialTicks);
        }

        this.post(HELMET);
    }

    @Override
    protected void renderPortal(float timeInPortal) {
        if(this.pre(PORTAL))
            return;

        float time = this.mc.player.prevTimeInPortal + (this.mc.player.timeInPortal - this.mc.player.prevTimeInPortal) * timeInPortal;

        if(time > 0.0F) {
            if(timeInPortal < 1.0F) {
                timeInPortal = timeInPortal * timeInPortal;
                timeInPortal = timeInPortal * timeInPortal;
                timeInPortal = timeInPortal * 0.8F + 0.2F;
            }

            GlStateManager.disableAlphaTest();
            GlStateManager.disableDepthTest();
            GlStateManager.depthMask(false);
            GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
                    GlStateManager.DestFactor.ZERO);
            GlStateManager.color4f(1.0F, 1.0F, 1.0F, timeInPortal);
            this.mc.getTextureManager().bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);

            TextureAtlasSprite textureatlassprite = this.mc.getBlockRendererDispatcher().getBlockModelShapes().getModel(Blocks.NETHER_PORTAL.getDefaultState())
                    .getParticleTexture(null);
            float f = textureatlassprite.getMinU();
            float f1 = textureatlassprite.getMinV();
            float f2 = textureatlassprite.getMaxU();
            float f3 = textureatlassprite.getMaxV();
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
            bufferbuilder.pos(0.0D, this.scaledHeight, -90.0D).tex(f, f3).endVertex();
            bufferbuilder.pos(this.scaledWidth, this.scaledHeight, -90.0D).tex(f2, f3).endVertex();
            bufferbuilder.pos(this.scaledWidth, 0.0D, -90.0D).tex(f2, f1).endVertex();
            bufferbuilder.pos(0.0D, 0.0D, -90.0D).tex(f, f1).endVertex();
            tessellator.draw();
            GlStateManager.depthMask(true);
            GlStateManager.enableDepthTest();
            GlStateManager.enableAlphaTest();
            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        }

        this.post(PORTAL);
    }

    @Override
    public void setOverlayMessage(ITextComponent component, boolean animateColor) {
        this.setOverlayMessage(component.getFormattedText(), animateColor);
    }

    @Override
    protected void renderSleepFade(int width, int height) {
        if(this.mc.player.getSleepTimer() > 0) {
            this.mc.getProfiler().startSection("sleep");
            GlStateManager.disableDepthTest();
            GlStateManager.disableAlphaTest();
            int sleepTime = this.mc.player.getSleepTimer();
            float opacity = sleepTime / 100.0F;

            if(opacity > 1.0F)
                opacity = 1.0F - (sleepTime - 100) / 10.0F;

            int color = (int) (220.0F * opacity) << 24 | 1052704;
            fill(0, 0, width, height, color);
            GlStateManager.enableAlphaTest();
            GlStateManager.enableDepthTest();
            this.mc.getProfiler().endSection();
        }
    }

    @Override
    protected void renderHUDText(int width, int height) {
        this.mc.getProfiler().startSection("forgeHudText");
        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
                GlStateManager.DestFactor.ZERO);
        ArrayList<String> listL = new ArrayList<>();
        ArrayList<String> listR = new ArrayList<>();

        if(this.mc.isDemo()) {
            long time = this.mc.world.getGameTime();
            if(time >= 120500L)
                listR.add(I18n.format("demo.demoExpired"));
            else
                listR.add(I18n.format("demo.remainingTime", StringUtils.ticksToElapsedTime((int) (120500L - time))));
        }

        if(this.mc.gameSettings.showDebugInfo && !this.pre(DEBUG)) {
            this.debugOverlay.update();
            listL.addAll(this.debugOverlay.getLeft());
            listR.addAll(this.debugOverlay.getRight());
            this.post(DEBUG);
        }

        RenderGameOverlayEvent.Text event = new RenderGameOverlayEvent.Text(this.eventParent, listL, listR);
        if(!MinecraftForge.EVENT_BUS.post(event)) {
            int top = 2;
            for(String msg : listL) {
                if(msg == null)
                    continue;
                fill(1, top - 1, 2 + this.fontrenderer.getStringWidth(msg) + 1, top + this.fontrenderer.FONT_HEIGHT - 1, -1873784752);
                this.fontrenderer.drawStringWithShadow(msg, 2, top, 14737632);
                top += this.fontrenderer.FONT_HEIGHT;
            }

            top = 2;
            for(String msg : listR) {
                if(msg == null)
                    continue;
                int w = this.fontrenderer.getStringWidth(msg);
                int left = width - 2 - w;
                fill(left - 1, top - 1, left + w + 1, top + this.fontrenderer.FONT_HEIGHT - 1, -1873784752);
                this.fontrenderer.drawStringWithShadow(msg, left, top, 14737632);
                top += this.fontrenderer.FONT_HEIGHT;
            }
        }

        this.mc.getProfiler().endSection();
        this.post(TEXT);
    }

    @Override
    protected void renderFPSGraph() {
        if(this.mc.gameSettings.showDebugInfo && this.mc.gameSettings.showLagometer && !this.pre(FPS_GRAPH)) {
            this.debugOverlay.render();
            this.post(FPS_GRAPH);
        }
    }

    @Override
    protected void renderTitle(int width, int height, float partialTicks) {
        if(this.titlesTimer > 0) {
            this.mc.getProfiler().startSection("titleAndSubtitle");
            float age = this.titlesTimer - partialTicks;
            int opacity = 255;

            if(this.titlesTimer > this.titleFadeOut + this.titleDisplayTime) {
                float f3 = this.titleFadeIn + this.titleDisplayTime + this.titleFadeOut - age;
                opacity = (int) (f3 * 255.0F / this.titleFadeIn);
            }
            if(this.titlesTimer <= this.titleFadeOut)
                opacity = (int) (age * 255.0F / this.titleFadeOut);

            opacity = MathHelper.clamp(opacity, 0, 255);

            if(opacity > 8) {
                GlStateManager.pushMatrix();
                GlStateManager.translatef(width / 2, height / 2, 0.0F);
                GlStateManager.enableBlend();
                GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                        GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                GlStateManager.pushMatrix();
                GlStateManager.scalef(4.0F, 4.0F, 4.0F);
                int l = opacity << 24 & -16777216;
                this.getFontRenderer().drawStringWithShadow(this.displayedTitle, -this.getFontRenderer().getStringWidth(this.displayedTitle) / 2, -10.0F,
                        16777215 | l);
                GlStateManager.popMatrix();
                GlStateManager.pushMatrix();
                GlStateManager.scalef(2.0F, 2.0F, 2.0F);
                this.getFontRenderer().drawStringWithShadow(this.displayedSubTitle, -this.getFontRenderer().getStringWidth(this.displayedSubTitle) / 2, 5.0F,
                        16777215 | l);
                GlStateManager.popMatrix();
                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            }

            this.mc.getProfiler().endSection();
        }
    }

    @Override
    protected void renderChat(int width, int height) {
        this.mc.getProfiler().startSection("chat");

        int offset = 0;
        if(ModRPGHud.instance.getActiveHud() instanceof HudHotbarWidget)
            offset = -22;
        RenderGameOverlayEvent.Chat event = new RenderGameOverlayEvent.Chat(this.eventParent, 0, height - 48 + offset);
        if(MinecraftForge.EVENT_BUS.post(event))
            return;

        GlStateManager.pushMatrix();
        GlStateManager.translatef(event.getPosX(), event.getPosY(), 0.0F);
        this.persistantChatGUI.render(this.ticks);
        GlStateManager.popMatrix();

        this.post(CHAT);

        this.mc.getProfiler().endSection();
    }

    @Override
    protected void renderPlayerList(int width, int height) {
        ScoreObjective scoreobjective = this.mc.world.getScoreboard().getObjectiveInDisplaySlot(0);
        ClientPlayNetHandler handler = this.mc.player.connection;

        if(this.mc.gameSettings.keyBindPlayerList.isKeyDown()
                && (!this.mc.isIntegratedServerRunning() || handler.getPlayerInfoMap().size() > 1 || scoreobjective != null)) {
            this.overlayPlayerList.setVisible(true);
            if(this.pre(PLAYER_LIST))
                return;
            this.overlayPlayerList.render(width, this.mc.world.getScoreboard(), scoreobjective);
            this.post(PLAYER_LIST);
        } else
            this.overlayPlayerList.setVisible(false);
    }

    // Helper macros
    private boolean pre(ElementType type) {
        return MinecraftForge.EVENT_BUS.post(new RenderGameOverlayEvent.Pre(this.eventParent, type));
    }

    private void post(ElementType type) {
        MinecraftForge.EVENT_BUS.post(new RenderGameOverlayEvent.Post(this.eventParent, type));
    }

    private void bind(ResourceLocation res) {
        this.mc.getTextureManager().bindTexture(res);
    }

    private class GuiOverlayDebugForge extends DebugOverlayGui {
        private Minecraft mc;

        private GuiOverlayDebugForge(Minecraft mc) {
            super(mc);
            this.mc = mc;
        }

        public void update() {
            Entity entity = this.mc.getRenderViewEntity();
            this.rayTraceBlock = entity.func_213324_a(rayTraceDistance, 0.0F, false);
            this.rayTraceFluid = entity.func_213324_a(rayTraceDistance, 0.0F, true);
        }

        @Override
        protected void renderDebugInfoLeft() {
        }

        @Override
        protected void renderDebugInfoRight() {
        }

        private List<String> getLeft() {
            List<String> ret = this.call();
            ret.add("");
            ret.add("Debug: Pie [shift]: " + (this.mc.gameSettings.showDebugProfilerChart ? "visible" : "hidden") + " FPS [alt]: "
                    + (this.mc.gameSettings.showLagometer ? "visible" : "hidden"));
            ret.add("For help: press F3 + Q");
            return ret;
        }

        private List<String> getRight() {
            return this.getDebugInfoRight();
        }
    }

    public int getBlitOffset() {
        return this.blitOffset;
    }

    public String getOverlayMessage() {
        return this.overlayMessage;
    }

    /** Returns the overlayMessageTime variable */
    public int getOverlayMessageTime() {
        return this.overlayMessageTime;
    }

    /** Returns the animateOverlayMessageColor variable */
    public boolean getAnimateOverlayMessageColor() {
        return this.animateOverlayMessageColor;
    }

    /**
     * Draw the specified HudElement of the HudElementType from the active Hud
     * 
     * @param type         the HudElementType to be rendered
     * @param partialTicks the partialTicks to be used for animations
     */
    private void drawElement(HudElementType type, float partialTicks) {
        // Check if conditions for rendering the element are met
        if(this.rpgHud.getActiveHud().checkElementConditions(type)) {
            // Get RenderOverlayEvent alias for the type of this element
            ElementType alias = getEventAlias(type);

            // Check if the debug setting "force render" for this type is
            // activated
            if(this.forceRenderType(type)) {
                // Check if the debug setting "force vanilla" for this type is
                // activated
                if(this.forceRenderTypeVanilla(type)) {
                    // Check if the debug setting "prevent element from
                    // rendering" for this type is activated
                    if(!this.preventElementRenderType(type)) {
                        this.bind(AbstractGui.GUI_ICONS_LOCATION);
                        GlStateManager.enableBlend();

                        // Draws the element of this type of the vanilla HUD
                        this.rpgHud.getVanillaHud().drawElement(type, this, partialTicks, partialTicks, this.scaledWidth, this.scaledHeight);

                        GlStateManager.disableBlend();
                    }

                    // Check if this type has an RenderOverlayEvent alias and if
                    // the event should be prevented
                    if(alias != null && !this.preventEventType(type)) {
                        // Initiates the event
                        if(this.pre(alias))
                            return;
                        this.post(alias);
                    }
                } else {
                    // Check if the debug setting "prevent element from
                    // rendering" for this type is activated
                    if(!this.preventElementRenderType(type)) {
                        this.bind(AbstractGui.GUI_ICONS_LOCATION);
                        GlStateManager.enableBlend();

                        // Draws the element of this type of the active HUD
                        this.rpgHud.getActiveHud().drawElement(type, this, partialTicks, partialTicks, this.scaledWidth, this.scaledHeight);

                        GlStateManager.disableBlend();
                    }

                    // Check if this type has an RenderOverlayEvent alias and if
                    // the event should be prevented
                    if(alias != null && !this.preventEventType(type)) {
                        // Initiates the event
                        if(this.pre(alias))
                            return;
                        this.post(alias);
                    }
                }
            } else // Check if the debug setting "force vanilla" for this type is
            // activated
            if(this.forceRenderTypeVanilla(type)) {
                // Check if this type has an RenderOverlayEvent alias and if
                // the event should be prevented
                if(alias != null && !this.preventEventType(type))
                    if(this.pre(alias))
                        return;

                // Check if the debug setting "prevent element from
                // rendering" for this type is activated
                if(!this.preventElementRenderType(type)) {
                    this.bind(AbstractGui.GUI_ICONS_LOCATION);
                    GlStateManager.enableBlend();

                    // Draws the element of this type of the vanilla HUD
                    this.rpgHud.getVanillaHud().drawElement(type, this, partialTicks, partialTicks, this.scaledWidth, this.scaledHeight);

                    GlStateManager.disableBlend();
                }

                // Check if this type has an RenderOverlayEvent alias and if
                // the event should be prevented
                if(alias != null && !this.preventEventType(type))
                    this.post(alias);
            } else {
                // Check if this type has an RenderOverlayEvent alias and if
                // the event should be prevented
                if(alias != null && !this.preventEventType(type))
                    if(this.pre(alias))
                        return;

                // Check if the debug setting "prevent element from
                // rendering" for this type is activated
                if(!this.preventElementRenderType(type)) {
                    this.bind(AbstractGui.GUI_ICONS_LOCATION);
                    GlStateManager.enableBlend();

                    // Draws the element of this type of the active HUD
                    this.rpgHud.getActiveHud().drawElement(type, this, partialTicks, partialTicks, this.scaledWidth, this.scaledHeight);

                    GlStateManager.disableBlend();
                }

                // Check if this type has an RenderOverlayEvent alias and if
                // the event should be prevented
                if(alias != null && !this.preventEventType(type))
                    this.post(alias);
            }
        }

    }

    /**
     * Checks if the HudElementType has a setting to force it to be rendered
     * regardless of the forge event and if it is activated
     */
    private boolean forceRenderType(HudElementType type) {
        String id = Settings.force_render + "_" + type.name().toLowerCase();
        if(this.rpgHud.settings.doesSettingExist(id))
            return this.rpgHud.settings.getBoolValue(id);
        return false;
    }

    /**
     * Checks if the HudElementType has a setting to prevent it's rendering and if
     * it is activated
     */
    private boolean preventElementRenderType(HudElementType type) {
        String id = Settings.prevent_element_render + "_" + type.name().toLowerCase();
        if(this.rpgHud.settings.doesSettingExist(id))
            return this.rpgHud.settings.getBoolValue(id);
        return false;
    }

    /**
     * Checks if the HudElementType has a setting to force the vanilla hud element
     * to be rendered and if it is activated
     */
    private boolean forceRenderTypeVanilla(HudElementType type) {
        String id = Settings.force_render + "_" + type.name().toLowerCase();
        if(this.rpgHud.settings.doesSettingExist(id))
            return this.rpgHud.settings.getBoolValue(id);
        return false;
    }

    /**
     * Checks if the HudElementType has a setting to prevent the forge event and if
     * it is activated
     */
    private boolean preventEventType(HudElementType type) {
        String id = Settings.prevent_event + "_" + type.name().toLowerCase();
        if(this.rpgHud.settings.doesSettingExist(id))
            return this.rpgHud.settings.getBoolValue(id);
        return false;
    }

    /**
     * Returns the Forge RenderOverlayEvent alias of the respective HudElementType.
     * Returns null if it has none
     * 
     * @param type the HudElementType
     * @return RenderOverlayEvent type
     */
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