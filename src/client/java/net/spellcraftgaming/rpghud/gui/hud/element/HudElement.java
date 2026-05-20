package net.spellcraftgaming.rpghud.gui.hud.element;

import com.mojang.authlib.GameProfile;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.PlayerSkin;
import net.minecraft.world.item.ItemStack;
import net.spellcraftgaming.rpghud.gui.render.ColoredTetragonGuiElementRenderState;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.settings.Settings;
import org.joml.Matrix3x2f;

import java.util.UUID;

@Environment(value=EnvType.CLIENT)
public abstract class HudElement {
    /** The values of the color red */
    public static final int COLOR_RED = 0xFFC10000;

    /** The values of the color red */
    public static final int COLOR_PINK = 0xFFFF69B4;

    /** The values of the color red */
    public static final int COLOR_BROWN = 0xFF8b4513;

    /** The values of the color white */
    public static final int COLOR_WHITE = 0xFFF2F2F2;

    /** The values of the color white */
    public static final int COLOR_ORANGE = 0xFFFF8400;

    /** The values of the color green */
    public static final int COLOR_GREEN = 0xFF3BC200;

    /** The values of the color red */
    public static final int COLOR_PURPLE = 0xFFA400F0;

    /** The values of the color blue */
    public static final int COLOR_BLUE = 0xFF005BC2;

    /** The values of the color blue */
    public static final int COLOR_AQUA = 0xFF00FFFF;

    /** The value of the color black */
    public static final int COLOR_BLACK = 0xFF292929;

    /** The values of the color grey */
    public static final int COLOR_GREY = 0xFF8A8A8A;

    /** The values of the color yellow */
    public static final int COLOR_YELLOW = 0xFFEEEE00;

    /** The values of the color sickly green used by the hunger effect */
    public static final int COLOR_GREEN_FROST = 0xFF9BA067;
    
    /** The values of the default color */
    public static final int[] COLOR_DEFAULT = { 0xFF4C4C4C, 0xFF3D3D3D };

    /** ResourceLocation of the interface texture for the RPG-HUD */
    protected static final Identifier INTERFACE = Identifier.parse("rpghud:textures/interface.png");
    
    protected static final Identifier CROSSHAIR_TEXTURE = Identifier.withDefaultNamespace("hud/crosshair");
    protected static final Identifier CROSSHAIR_ATTACK_INDICATOR_FULL_TEXTURE = Identifier.withDefaultNamespace("hud/crosshair_attack_indicator_full");
    protected static final Identifier CROSSHAIR_ATTACK_INDICATOR_BACKGROUND_TEXTURE = Identifier.withDefaultNamespace("hud/crosshair_attack_indicator_background");
    protected static final Identifier CROSSHAIR_ATTACK_INDICATOR_PROGRESS_TEXTURE = Identifier.withDefaultNamespace("hud/crosshair_attack_indicator_progress");
    protected static final Identifier EFFECT_BACKGROUND_AMBIENT_TEXTURE = Identifier.withDefaultNamespace("hud/effect_background_ambient");
    protected static final Identifier EFFECT_BACKGROUND_TEXTURE = Identifier.withDefaultNamespace("hud/effect_background");
    protected static final Identifier HOTBAR_TEXTURE = Identifier.withDefaultNamespace("hud/hotbar");
    protected static final Identifier HOTBAR_SELECTION_TEXTURE = Identifier.withDefaultNamespace("hud/hotbar_selection");
    protected static final Identifier HOTBAR_OFFHAND_LEFT_TEXTURE = Identifier.withDefaultNamespace("hud/hotbar_offhand_left");
    protected static final Identifier HOTBAR_OFFHAND_RIGHT_TEXTURE = Identifier.withDefaultNamespace("hud/hotbar_offhand_right");
    protected static final Identifier HOTBAR_ATTACK_INDICATOR_BACKGROUND_TEXTURE = Identifier.withDefaultNamespace("hud/hotbar_attack_indicator_background");
    protected static final Identifier HOTBAR_ATTACK_INDICATOR_PROGRESS_TEXTURE = Identifier.withDefaultNamespace("hud/hotbar_attack_indicator_progress");
    protected static final Identifier JUMP_BAR_BACKGROUND_TEXTURE = Identifier.withDefaultNamespace("hud/jump_bar_background");
    protected static final Identifier JUMP_BAR_COOLDOWN_TEXTURE = Identifier.withDefaultNamespace("hud/jump_bar_cooldown");
    protected static final Identifier JUMP_BAR_PROGRESS_TEXTURE = Identifier.withDefaultNamespace("hud/jump_bar_progress");
    protected static final Identifier EXPERIENCE_BAR_BACKGROUND_TEXTURE = Identifier.withDefaultNamespace("hud/experience_bar_background");
    protected static final Identifier EXPERIENCE_BAR_PROGRESS_TEXTURE = Identifier.withDefaultNamespace("hud/experience_bar_progress");
    protected static final Identifier ARMOR_EMPTY_TEXTURE = Identifier.withDefaultNamespace("hud/armor_empty");
    protected static final Identifier ARMOR_HALF_TEXTURE = Identifier.withDefaultNamespace("hud/armor_half");
    protected static final Identifier ARMOR_FULL_TEXTURE = Identifier.withDefaultNamespace("hud/armor_full");
    protected static final Identifier FOOD_EMPTY_HUNGER_TEXTURE = Identifier.withDefaultNamespace("hud/food_empty_hunger");
    protected static final Identifier FOOD_HALF_HUNGER_TEXTURE = Identifier.withDefaultNamespace("hud/food_half_hunger");
    protected static final Identifier FOOD_FULL_HUNGER_TEXTURE = Identifier.withDefaultNamespace("hud/food_full_hunger");
    protected static final Identifier FOOD_EMPTY_TEXTURE = Identifier.withDefaultNamespace("hud/food_empty");
    protected static final Identifier FOOD_HALF_TEXTURE = Identifier.withDefaultNamespace("hud/food_half");
    protected static final Identifier FOOD_FULL_TEXTURE = Identifier.withDefaultNamespace("hud/food_full");
    protected static final Identifier AIR_TEXTURE = Identifier.withDefaultNamespace("hud/air");
    protected static final Identifier AIR_BURSTING_TEXTURE = Identifier.withDefaultNamespace("hud/air_bursting");
    protected static final Identifier VEHICLE_CONTAINER_HEART_TEXTURE = Identifier.withDefaultNamespace("hud/heart/vehicle_container");
    protected static final Identifier VEHICLE_FULL_HEART_TEXTURE = Identifier.withDefaultNamespace("hud/heart/vehicle_full");
    protected static final Identifier VEHICLE_HALF_HEART_TEXTURE = Identifier.withDefaultNamespace("hud/heart/vehicle_half");
    protected static final Identifier VIGNETTE_TEXTURE = Identifier.withDefaultNamespace("textures/misc/vignette.png");
    protected static final Identifier PUMPKIN_BLUR = Identifier.withDefaultNamespace("textures/misc/pumpkinblur.png");
    protected static final Identifier SPYGLASS_SCOPE = Identifier.withDefaultNamespace("textures/misc/spyglass_scope.png");
    protected static final Identifier POWDER_SNOW_OUTLINE = Identifier.withDefaultNamespace("textures/misc/powder_snow_outline.png");

    public static final int OFFSET_PERCENT = 25;

    public static final int OFFSET_PREVIEW = 0x5A5A5A;

    /** The x coordinate the element will be rendered at on the screen */
    protected int posX;
    /** The y coordinate the element will be rendered at on the screen */
    protected int posY;

    /**
     * The default x coordinate the element will be rendered at on the screen
     */
    protected final int defaultPosX;
    /**
     * The default y coordinate the element will be rendered at on the screen
     */
    protected final int defaultPosY;

    /** The width of this element */
    protected int elementWidth;
    /** The height of this element */
    protected int elementHeight;
    /** Whether this element can be moved */
    protected boolean moveable;
    /** The Type of this element */
    protected HudElementType type;

    /** The Minecraft instance */
    protected Minecraft mc;

    /** The Mod instance */
    protected ModRPGHud rpgHud;

    /** The Mod settings */
    protected Settings settings;

    protected float scale;
    protected float scaleInverted;

    public HudElementType parent;
    protected Identifier playerSkinId = Identifier.withDefaultNamespace("textures/entity/player/slim/steve.png");

    /**
     * Constructor
     * 
     * @param type
     *            The HudElementType of this element
     * @param posX
     *            The initial position of this element (not yet implemented)
     * @param posY
     *            The initial position of this element (not yet implemented)
     * @param width
     *            The width of this element (not yet implemented)
     * @param height
     *            The height of this element (not yet implemented)
     * @param moveable
     *            Whether this element should be allowed to be moved around
     */
    public HudElement(HudElementType type, int posX, int posY, int width, int height, boolean moveable) {
        this.type = type;
        this.posX = posX;
        this.posY = posY;
        this.defaultPosX = posX;
        this.defaultPosY = posY;
        this.elementWidth = width;
        this.elementHeight = height;
        this.moveable = moveable;
        this.mc = Minecraft.getInstance();
        this.rpgHud = ModRPGHud.instance;
        this.settings = this.rpgHud.settings;
        this.scale = 1f;
        this.scaleInverted = 1f / this.scale;
        this.parent = type;
    }

    /**
     * Function called to draw this element on the screen
     */
    public void draw(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, int scaledWidth, int scaledHeight) {
        this.drawElement(graphics, deltaTracker, scaledWidth, scaledHeight);
    }

    public abstract void drawElement(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, int scaledWidth, int scaledHeight);

    /**
     * Returns the x coordinate of this element
     * 
     * @return x coordinate
     */
    public int getPosX(int scaledWidth) {
        return this.posX;
    }

    /**
     * Returns the y coordinate of this element
     * 
     * @return y coordinate
     */
    public int getPosY(int scaledHeight) {
        return this.posY;
    }

    /**
     * Returns the width of this element
     * 
     * @return width
     */
    public int getWidth(int scaledWidth) {
        return this.elementWidth;
    }

    /**
     * Returns the height of this element
     * 
     * @return height
     */
    public int getHeight(int scaledHeight) {
        return this.elementHeight;
    }

    public float getScale() {
        return 1f;
    }
    
    public float getInvertedScale() {
        return 1f / getScale();
    }
    /**
     * Returns whether this element can be moved or not
     * 
     * @return moveable
     */
    public boolean isMoveable() {
        return this.moveable;
    }

    /**
     * Returns the type of this element
     * 
     * @return type
     */
    public HudElementType getType() {
        return this.type;
    }

    /**
     * Sets the position of this element to posX and posY if they are valid
     * 
     * @param posX
     * @param posY
     * @return whether the position is valid or not
     */
    public boolean setPos(int posX, int posY) {
        boolean xValid = false;
        boolean yValid = false;
        if (posX >= 0 && posX < (this.mc.getWindow().getGuiScaledWidth() - this.elementWidth)) {
            xValid = true;
        }
        if (posY >= 0 && posY < (this.mc.getWindow().getGuiScaledHeight() - this.elementHeight)) {
            yValid = true;
        }
        if (xValid && yValid) {
            this.posX = posX;
            this.posY = posY;
        }
        return xValid && yValid;
    }

    /**
     * Resets the position of this element to it's default position
     */
    public void setPositionToDefault() {
        this.posX = this.defaultPosX;
        this.posY = this.defaultPosY;
    }

    /**
     * This function must return true when the element should be rendered<br>
     * For example:<br>
     * For the air element this should return true whenever the player is in
     * survival/adventure and under water
     */
    public boolean checkConditions() {
        return true;
    }

    /**
     * Draws a rectangle on the screen
     * 
     * @param posX
     *            the x positon on the screen
     * @param posY
     *            the y positon on the screen
     * @param width
     *            the width of the rectangle
     * @param height
     *            the height of the rectangle
     * @param color
     *            the color of the rectangle
     */
    public static void drawRect(GuiGraphicsExtractor graphics, int posX, int posY, int width, int height, int color) {
    	if (color == -1)
            return;
        if (color <= 0xFFFFFF && color >= 0)
            color = color + 0xFF000000;
        graphics.fill(posX, posY, posX + width, posY+height, color);
    }

    /**
     * Draw an outline on the screen
     * 
     * @param x
     *            the x position on the screen
     * @param y
     *            the y position on the screen
     * @param width
     *            the width of the outline
     * @param height
     *            the height of the outline
     * @param color
     */
    protected static void drawOutline(GuiGraphicsExtractor graphics, int x, int y, int width, int height, int color) {
        drawRect(graphics, x, y, width, 1, color);
        drawRect(graphics, x, y+1, 1, height-2, color);
        drawRect(graphics, x + width - 1, y+1, 1, height-2, color);
        drawRect(graphics, x, y + height - 1, width, 1, color);
    }

    /**
     * Draws a bar on the screen
     * 
     * @param x
     *            the x position on the screen
     * @param y
     *            the y position on the screen
     * @param width
     *            the width of the bar (with outline)
     * @param height
     *            the height of the bar (with outline)
     * @param value
     *            the converted value of the bar (maxed at 100.0D)
     * @param colorBarLight
     *            the color for the bar (light)
     * @param colorBarDark
     *            the color for the bar (dark
     */
    public static void drawCustomBar(GuiGraphicsExtractor graphics, int x, int y, int width, int height, double value, int colorBarLight, int colorBarDark) {
        drawCustomBar(graphics, x, y, width, height, value, HudElement.COLOR_DEFAULT[0], HudElement.COLOR_DEFAULT[1], colorBarLight, colorBarDark, true, 0x000000);
    }

    /**
     * Draws a bar on the screen
     * 
     * @param x
     *            the x position on the screen
     * @param y
     *            the y position on the screen
     * @param width
     *            the width of the bar (with outline)
     * @param height
     *            the height of the bar (with outline)
     * @param value
     *            the converted value of the bar (maxed at 100.0D)
     * @param colorGroundLight
     *            the color for the background (light)
     * @param colorGroundDark
     *            the color for the background (dark)
     * @param colorBarLight
     *            the color for the bar (light)
     * @param colorBarDark
     *            the color for the bar (dark
     */
    public static void drawCustomBar(GuiGraphicsExtractor graphics, int x, int y, int width, int height, double value, int colorGroundLight, int colorGroundDark, int colorBarLight, int colorBarDark) {
        drawCustomBar(graphics, x, y, width, height, value, colorGroundLight, colorGroundDark, colorBarLight, colorBarDark, true, 0x000000);
    }

    /**
     * Draws a bar on the screen
     * 
     * @param x
     *            the x position on the screen
     * @param y
     *            the y position on the screen
     * @param width
     *            the width of the bar (with outline)
     * @param height
     *            the height of the bar (with outline)
     * @param value
     *            the converted value of the bar (maxed at 100.0D)
     * @param colorGroundLight
     *            the color for the background (light)
     * @param colorGroundDark
     *            the color for the background (dark)
     * @param colorBarLight
     *            the color for the bar (light)
     * @param colorBarDark
     *            the color for the bar (dark
     * @param outlined
     *            whether this bar has an outline or not
     */
    public static void drawCustomBar(GuiGraphicsExtractor graphics, int x, int y, int width, int height, double value, int colorGroundLight, int colorGroundDark, int colorBarLight, int colorBarDark, boolean outlined) {
        drawCustomBar(graphics, x, y, width, height, value, colorGroundLight, colorGroundDark, colorBarLight, colorBarDark, outlined, 0x000000);
    }

    /**
     * Draws a bar on the screen
     * 
     * @param x
     *            the x position on the screen
     * @param y
     *            the y position on the screen
     * @param width
     *            the width of the bar (with outline)
     * @param height
     *            the height of the bar (with outline)
     * @param value
     *            the converted value of the bar (maxed at 100.0D)
     * @param colorGroundLight
     *            the color for the background (light)
     * @param colorGroundDark
     *            the color for the background (dark)
     * @param colorBarLight
     *            the color for the bar (light)
     * @param colorBarDark
     *            the color for the bar (dark
     * @param colorOutline
     *            the color of the outline
     */
    public static void drawCustomBar(GuiGraphicsExtractor graphics, int x, int y, int width, int height, double value, int colorGroundLight, int colorGroundDark, int colorBarLight, int colorBarDark, int colorOutline) {
        drawCustomBar(graphics, x, y, width, height, value, colorGroundLight, colorGroundDark, colorBarLight, colorBarDark, true, colorOutline);
    }

    /**
     * Draws a bar on the screen
     * 
     * @param x
     *            the x position on the screen
     * @param y
     *            the y position on the screen
     * @param width
     *            the width of the bar (with outline)
     * @param height
     *            the height of the bar (with outline)
     * @param value
     *            the converted value of the bar (maxed at 100.0D)
     * @param colorGroundLight
     *            the color for the background (light)
     * @param colorGroundDark
     *            the color for the background (dark)
     * @param colorBarLight
     *            the color for the bar (light)
     * @param colorBarDark
     *            the color for the bar (dark
     * @param outlined
     *            whether this bar has an outline or not
     * @param colorOutline
     *            the color of the outline
     */
    public static void drawCustomBar(GuiGraphicsExtractor graphics, int x, int y, int width, int height, double value, int colorGroundLight, int colorGroundDark, int colorBarLight, int colorBarDark, boolean outlined, int colorOutline) {
        if (value < 0.0D) {
            value = 0.0D;
        }else if (value > 100D) {
            value = 100D;
        }

        int offset = 1;

        int filledWidth = width;
        filledWidth = width - (offset * 2);
        if (filledWidth < 0)
            filledWidth = 0;
        int filledHeight = width;
        filledHeight = height - (offset * 2);
        if (filledHeight < 0)
            filledHeight = 0;

        int percentFilled = Math.toIntExact(Math.round(value / 100.0D * filledWidth));

        if (outlined)
            drawOutline(graphics, x, y, width, height, colorOutline);
        int halfedFilledHeight = filledHeight / 2;

        drawRect(graphics, x + offset, y + offset, percentFilled, halfedFilledHeight, colorBarLight);
        drawRect(graphics, x + offset, y + offset + halfedFilledHeight, percentFilled, filledHeight - halfedFilledHeight, colorBarDark);

        if (colorGroundDark != -1 && colorGroundLight != -1 && filledWidth - percentFilled > 0) {
            drawRect(graphics, x + offset + percentFilled, y + offset, filledWidth - percentFilled, halfedFilledHeight, colorGroundLight);
            drawRect(graphics, x + offset + percentFilled, y + offset + halfedFilledHeight, filledWidth - percentFilled, filledHeight - halfedFilledHeight, colorGroundDark);
        }
    }

    /**
     * Draws a tetragon on the screen
     * 
     * @param posX1
     *            x position of the upper left corner
     * @param posX2
     *            x position of the lower left corner
     * @param posY1
     *            y position of the upper left corner
     * @param posY2
     *            y position of the lower left corner
     * @param width1
     *            width of the top edge
     * @param width2
     *            width of the bottom edge
     * @param height1
     *            height of the left edge
     * @param height2
     *            height of the right edge
     * @param color
     *            color of the tetragon (hexa format 0xAARRGGBB)
     */
    public void drawTetragon(GuiGraphicsExtractor graphics, int posX1, int posX2, int posY1, int posY2, int width1, int width2, int height1, int height2, int color) {
        graphics.guiRenderState.addGuiElement(new ColoredTetragonGuiElementRenderState(
                RenderPipelines.GUI, TextureSetup.noTexture(), new Matrix3x2f(graphics.pose()), posX1, posX2, posY1, posY2, width1, width2, height1, height2, color, graphics.scissorStack.peek()
        ));
    }

    public static int offsetColorPercent(int color, int offsetPercent) {
        int colorOffset;

        int colorPart = (color >> 16 & 255);
        colorPart -= colorPart / (100 / offsetPercent);
        if (colorPart > 0xFF)
            colorPart = 0xFF;
        else if (colorPart < 0)
            colorPart = 0;

        colorOffset = colorPart << 16;
        colorPart = (color >> 8 & 255);
        colorPart -= colorPart / (100 / offsetPercent);
        if (colorPart > 0xFF)
            colorPart = 0xFF;
        else if (colorPart < 0)
            colorPart = 0;

        colorOffset += colorPart << 8;
        colorPart = (color & 255);
        colorPart -= colorPart / (100 / offsetPercent);
        if (colorPart > 0xFF)
            colorPart = 0xFF;
        else if (colorPart < 0)
            colorPart = 0;
        colorOffset += colorPart;
        return colorOffset;
    }

    public static int offsetColor(int color, int offset) {
        int colorOffset;

        int colorPart = (color >> 16 & 255);
        colorPart += (offset >> 16 & 255);
        if (colorPart > 0xFF)
            colorPart = 0xFF;
        else if (colorPart < 0)
            colorPart = 0;

        colorOffset = colorPart << 16;
        colorPart = (color >> 8 & 255);
        colorPart += (offset >> 8 & 255);
        if (colorPart > 0xFF)
            colorPart = 0xFF;
        else if (colorPart < 0)
            colorPart = 0;

        colorOffset += colorPart << 8;
        colorPart = (color & 255);
        colorPart += (offset & 255);
        if (colorPart > 0xFF)
            colorPart = 0xFF;
        else if (colorPart < 0)
            colorPart = 0;
        colorOffset += colorPart;
        return colorOffset;
    }

    /**
     * Fetch the skin of the player and save the ResourceLocation in playerSkinId
     * 
     * @param player
     *            the player whose skin should be returned
     * @return the ResourceLocation
     */
    private void fetchPlayerSkin(LocalPlayer player) {
        Minecraft instance = Minecraft.getInstance();
        SkinManager skinProvider = instance.getSkinManager();
        GameProfile profile = player.getGameProfile();

        skinProvider.get(profile).thenAccept(playerSkin -> {
            playerSkin.ifPresent(skin -> this.playerSkinId = skin.body().texturePath());
        });
    }

       /**
     * Renders an item on the screen
     * 
     * @param x
     *            the x position on the screen
     * @param y
     *            the y position on the screen
     * @param deltaTracker
     *            the partial ticks (used for animation)
     * @param player
     *            the player who should get the item rendered
     * @param stack
     *            the item (via ItemStack)
     */
    protected void renderHotbarItem(GuiGraphicsExtractor graphics, int x, int y, DeltaTracker deltaTracker, LocalPlayer player, ItemStack stack, int seed) {
		if (!stack.isEmpty()) {
			float f = (float)stack.getPopTime() - deltaTracker.getGameTimeDeltaPartialTick(false);
			if (f > 0.0F) {
				float g = 1.0F + f / 5.0F;
                graphics.pose().pushMatrix();
                graphics.pose().translate((float)(x + 8), (float)(y + 12));
                graphics.pose().scale(1.0F / g, (g + 1.0F) / 2.0F);
                graphics.pose().translate((float)(-(x + 8)), (float)(-(y + 12)));
			}

            graphics.item(player, stack, x, y, seed);
			if (f > 0.0F) {
                graphics.pose().popMatrix();
			}

			graphics.itemDecorations(Minecraft.getInstance().font, stack, x, y);
		}
    }
    
    protected void drawStringWithBackground(GuiGraphicsExtractor graphics, String text, int posX, int posY, int colorMain, int colorBackground) {
        graphics.text(this.mc.font, text, posX + 1, posY, colorBackground, true);
        graphics.text(this.mc.font, text, posX - 1, posY, colorBackground, true);
        graphics.text(this.mc.font, text, posX, posY + 1, colorBackground, true);
        graphics.text(this.mc.font, text, posX, posY - 1, colorBackground, true);
        graphics.text(this.mc.font, text, posX, posY, colorMain, true);
    }
    
    public boolean isChatOpen() {
        return this.mc.screen instanceof ChatScreen;
    }
}
