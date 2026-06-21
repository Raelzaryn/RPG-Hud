package net.spellcraftgaming.rpghud.gui.hud.element;

import org.joml.Matrix3x2f;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.texture.TextureSetup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.spellcraftgaming.rpghud.gui.render.ColoredTetragonGuiElementRenderState;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.settings.Settings;

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
    protected static final Identifier INTERFACE = Identifier.of("rpghud", "textures/interface.png");
    
    protected static final Identifier CROSSHAIR_TEXTURE = Identifier.ofVanilla("hud/crosshair");
    protected static final Identifier CROSSHAIR_ATTACK_INDICATOR_FULL_TEXTURE = Identifier.ofVanilla("hud/crosshair_attack_indicator_full");
    protected static final Identifier CROSSHAIR_ATTACK_INDICATOR_BACKGROUND_TEXTURE = Identifier.ofVanilla("hud/crosshair_attack_indicator_background");
    protected static final Identifier CROSSHAIR_ATTACK_INDICATOR_PROGRESS_TEXTURE = Identifier.ofVanilla("hud/crosshair_attack_indicator_progress");
    protected static final Identifier EFFECT_BACKGROUND_AMBIENT_TEXTURE = Identifier.ofVanilla("hud/effect_background_ambient");
    protected static final Identifier EFFECT_BACKGROUND_TEXTURE = Identifier.ofVanilla("hud/effect_background");
    protected static final Identifier HOTBAR_TEXTURE = Identifier.ofVanilla("hud/hotbar");
    protected static final Identifier HOTBAR_SELECTION_TEXTURE = Identifier.ofVanilla("hud/hotbar_selection");
    protected static final Identifier HOTBAR_OFFHAND_LEFT_TEXTURE = Identifier.ofVanilla("hud/hotbar_offhand_left");
    protected static final Identifier HOTBAR_OFFHAND_RIGHT_TEXTURE = Identifier.ofVanilla("hud/hotbar_offhand_right");
    protected static final Identifier HOTBAR_ATTACK_INDICATOR_BACKGROUND_TEXTURE = Identifier.ofVanilla("hud/hotbar_attack_indicator_background");
    protected static final Identifier HOTBAR_ATTACK_INDICATOR_PROGRESS_TEXTURE = Identifier.ofVanilla("hud/hotbar_attack_indicator_progress");
    protected static final Identifier JUMP_BAR_BACKGROUND_TEXTURE = Identifier.ofVanilla("hud/jump_bar_background");
    protected static final Identifier JUMP_BAR_COOLDOWN_TEXTURE = Identifier.ofVanilla("hud/jump_bar_cooldown");
    protected static final Identifier JUMP_BAR_PROGRESS_TEXTURE = Identifier.ofVanilla("hud/jump_bar_progress");
    protected static final Identifier EXPERIENCE_BAR_BACKGROUND_TEXTURE = Identifier.ofVanilla("hud/experience_bar_background");
    protected static final Identifier EXPERIENCE_BAR_PROGRESS_TEXTURE = Identifier.ofVanilla("hud/experience_bar_progress");
    protected static final Identifier ARMOR_EMPTY_TEXTURE = Identifier.ofVanilla("hud/armor_empty");
    protected static final Identifier ARMOR_HALF_TEXTURE = Identifier.ofVanilla("hud/armor_half");
    protected static final Identifier ARMOR_FULL_TEXTURE = Identifier.ofVanilla("hud/armor_full");
    protected static final Identifier FOOD_EMPTY_HUNGER_TEXTURE = Identifier.ofVanilla("hud/food_empty_hunger");
    protected static final Identifier FOOD_HALF_HUNGER_TEXTURE = Identifier.ofVanilla("hud/food_half_hunger");
    protected static final Identifier FOOD_FULL_HUNGER_TEXTURE = Identifier.ofVanilla("hud/food_full_hunger");
    protected static final Identifier FOOD_EMPTY_TEXTURE = Identifier.ofVanilla("hud/food_empty");
    protected static final Identifier FOOD_HALF_TEXTURE = Identifier.ofVanilla("hud/food_half");
    protected static final Identifier FOOD_FULL_TEXTURE = Identifier.ofVanilla("hud/food_full");
    protected static final Identifier AIR_TEXTURE = Identifier.ofVanilla("hud/air");
    protected static final Identifier AIR_BURSTING_TEXTURE = Identifier.ofVanilla("hud/air_bursting");
    protected static final Identifier VEHICLE_CONTAINER_HEART_TEXTURE = Identifier.ofVanilla("hud/heart/vehicle_container");
    protected static final Identifier VEHICLE_FULL_HEART_TEXTURE = Identifier.ofVanilla("hud/heart/vehicle_full");
    protected static final Identifier VEHICLE_HALF_HEART_TEXTURE = Identifier.ofVanilla("hud/heart/vehicle_half");
    protected static final Identifier VIGNETTE_TEXTURE = Identifier.ofVanilla("textures/misc/vignette.png");
    protected static final Identifier PUMPKIN_BLUR = Identifier.ofVanilla("textures/misc/pumpkinblur.png");
    protected static final Identifier SPYGLASS_SCOPE = Identifier.ofVanilla("textures/misc/spyglass_scope.png");
    protected static final Identifier POWDER_SNOW_OUTLINE = Identifier.ofVanilla("textures/misc/powder_snow_outline.png");

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
    protected MinecraftClient mc;

    /** The Mod instance */
    protected ModRPGHud rpgHud;

    /** The Mod settings */
    protected Settings settings;

    protected float scale;
    protected float scaleInverted;

    public HudElementType parent;
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
        this.mc = MinecraftClient.getInstance();
        this.rpgHud = ModRPGHud.instance;
        this.settings = this.rpgHud.settings;
        this.scale = 1f;
        this.scaleInverted = 1f / this.scale;
        this.parent = type;
    }

    /**
     * Function called to draw this element on the screen
     */
    public void draw(DrawContext dc, float zLevel, RenderTickCounter partialTicks, int scaledWidth, int scaledHeight) {
        this.drawElement(dc, zLevel, partialTicks, scaledWidth, scaledHeight);
    }

    public abstract void drawElement(DrawContext dc, float zLevel, RenderTickCounter partialTicks, int scaledWidth, int scaledHeight);

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
        if (posX >= 0 && posX < (this.mc.getWindow().getScaledWidth() - this.elementWidth)) {
            xValid = true;
        }
        if (posY >= 0 && posY < (this.mc.getWindow().getScaledHeight() - this.elementHeight)) {
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
    public static void drawRect(DrawContext dc, int posX, int posY, int width, int height, int color) {
    	if (color == -1)
            return;
        if (color <= 0xFFFFFF && color >= 0)
            color = color + 0xFF000000;
        dc.fill(posX, posY, posX + width, posY+height, color);
        
        //BufferRenderer.drawWithGlobalProgram(vertexbuffer.end());
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
    protected static void drawOutline(DrawContext dc, int x, int y, int width, int height, int color) {
        drawRect(dc, x, y, width, 1, color);
        drawRect(dc, x, y+1, 1, height-2, color);
        drawRect(dc, x + width - 1, y+1, 1, height-2, color);
        drawRect(dc, x, y + height - 1, width, 1, color);
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
    public static void drawCustomBar(DrawContext dc, int x, int y, int width, int height, double value, int colorBarLight, int colorBarDark) {
        drawCustomBar(dc, x, y, width, height, value, HudElement.COLOR_DEFAULT[0], HudElement.COLOR_DEFAULT[1], colorBarLight, colorBarDark, true, 0x000000);
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
    public static void drawCustomBar(DrawContext dc, int x, int y, int width, int height, double value, int colorGroundLight, int colorGroundDark, int colorBarLight, int colorBarDark) {
        drawCustomBar(dc, x, y, width, height, value, colorGroundLight, colorGroundDark, colorBarLight, colorBarDark, true, 0x000000);
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
    public static void drawCustomBar(DrawContext dc, int x, int y, int width, int height, double value, int colorGroundLight, int colorGroundDark, int colorBarLight, int colorBarDark, boolean outlined) {
        drawCustomBar(dc, x, y, width, height, value, colorGroundLight, colorGroundDark, colorBarLight, colorBarDark, outlined, 0x000000);
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
    public static void drawCustomBar(DrawContext dc, int x, int y, int width, int height, double value, int colorGroundLight, int colorGroundDark, int colorBarLight, int colorBarDark, int colorOutline) {
        drawCustomBar(dc, x, y, width, height, value, colorGroundLight, colorGroundDark, colorBarLight, colorBarDark, true, colorOutline);
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
    public static void drawCustomBar(DrawContext dc, int x, int y, int width, int height, double value, int colorGroundLight, int colorGroundDark, int colorBarLight, int colorBarDark, boolean outlined, int colorOutline) {
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

        int percentFilled = (int) Math.round(value / 100.0D * filledWidth);

        if (outlined)
            drawOutline(dc, x, y, width, height, colorOutline);
        int halfedFilledHeight = filledHeight / 2;

        drawRect(dc, x + offset, y + offset, percentFilled, halfedFilledHeight, colorBarLight);
        drawRect(dc, x + offset, y + offset + halfedFilledHeight, percentFilled, filledHeight - halfedFilledHeight, colorBarDark);

        if (colorGroundDark != -1 && colorGroundLight != -1 && filledWidth - percentFilled > 0) {
            drawRect(dc, x + offset + percentFilled, y + offset, filledWidth - percentFilled, halfedFilledHeight, colorGroundLight);
            drawRect(dc, x + offset + percentFilled, y + offset + halfedFilledHeight, filledWidth - percentFilled, filledHeight - halfedFilledHeight, colorGroundDark);
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
    public void drawTetragon(DrawContext dc, int posX1, int posX2, int posY1, int posY2, int width1, int width2, int height1, int height2, int color) {
        dc.state
			.addSimpleElement(
				new ColoredTetragonGuiElementRenderState(
					RenderPipelines.GUI, TextureSetup.empty(), new Matrix3x2f(dc.getMatrices()), posX1, posX2, posY1, posY2, width1, width2, height1, height2, color, dc.scissorStack.peekLast()
				)
			);
        
    	//dc.state.
        /*if (color == -1)
            return;
        if(width1 < 0) width1 = 0;
        if(width2 < 0) width2 = 0;
        float f3;
        if (color <= 0xFFFFFF && color >= 0)
            f3 = 1.0F;
        else
            f3 = (color >> 24 & 255) / 255.0F;
        float f = (color >> 16 & 255) / 255.0F;
        float f1 = (color >> 8 & 255) / 255.0F;
        float f2 = (color & 255) / 255.0F;
        BufferBuilder vertexbuffer = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        vertexbuffer.vertex(posX1, (float) posY1 + height1, 0F).color(f, f1, f2, f3);
        vertexbuffer.vertex((float) posX2 + width2, (float) posY2 + height2, 0F).color(f, f1, f2, f3);
        vertexbuffer.vertex((float) posX1 + width1, posY2, 0F).color(f, f1, f2, f3);
        vertexbuffer.vertex(posX2, posY1, 0F).color(f, f1, f2, f3);
        BufferRenderer.drawWithGlobalProgram(vertexbuffer.end());*/
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
     * Returns the ResourceLocation for the skin of the player
     * 
     * @param player
     *            the player whose skin should be returned
     * @return the ResourceLocation
     */
    protected static Identifier getPlayerSkin(ClientPlayerEntity player) {
        return player.getSkinTextures().texture();
    }
    
       /**
     * Renders an item on the screen
     * 
     * @param x
     *            the x position on the screen
     * @param y
     *            the y position on the screen
     * @param tickCounter
     *            the partial ticks (used for animation)
     * @param player
     *            the player who should get the item rendered
     * @param stack
     *            the item (via ItemStack)
     */
    protected void renderHotbarItem(DrawContext context, int x, int y, RenderTickCounter tickCounter, PlayerEntity player, ItemStack stack, int seed) {
		if (!stack.isEmpty()) {
			float f = (float)stack.getBobbingAnimationTime() - tickCounter.getTickProgress(false);
			if (f > 0.0F) {
				float g = 1.0F + f / 5.0F;
				context.getMatrices().pushMatrix();
				context.getMatrices().translate((float)(x + 8), (float)(y + 12));
				context.getMatrices().scale(1.0F / g, (g + 1.0F) / 2.0F);
				context.getMatrices().translate((float)(-(x + 8)), (float)(-(y + 12)));
			}

			context.drawItem(player, stack, x, y, seed);
			if (f > 0.0F) {
				context.getMatrices().popMatrix();
			}

			context.drawStackOverlay(this.mc.textRenderer, stack, x, y);
		}
    }
    
    protected void drawStringWithBackground(DrawContext dc, String text, int posX, int posY, int colorMain, int colorBackground) {
        dc.drawText(this.mc.textRenderer,text, posX + 1, posY, colorBackground, false);
        dc.drawText(this.mc.textRenderer,text, posX - 1, posY, colorBackground, false);
        dc.drawText(this.mc.textRenderer,text, posX, posY + 1, colorBackground, false);
        dc.drawText(this.mc.textRenderer,text, posX, posY - 1, colorBackground, false);
        dc.drawText(this.mc.textRenderer,text, posX, posY, colorMain, false);
    }
    
    public boolean isChatOpen() {
        return this.mc.currentScreen instanceof net.minecraft.client.gui.screen.ChatScreen;
    }
}
