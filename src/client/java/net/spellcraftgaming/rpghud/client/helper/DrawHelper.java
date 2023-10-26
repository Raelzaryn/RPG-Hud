package net.spellcraftgaming.rpghud.client.helper;

import org.joml.Matrix4f;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class DrawHelper {

    public static final int COLOR_RED = 0xC10000;
    public static final int COLOR_PINK = 0xFF69B4;
    public static final int COLOR_BROWN = 0x8b4513;
	public static final int COLOR_WHITE = 0xF2F2F2;
	public static final int COLOR_ORANGE = 0xFF8400;
	public static final int COLOR_GREEN = 0x3BC200;
	public static final int COLOR_PURPLE = 0xA400F0;
	public static final int COLOR_BLUE = 0x005BC2;
	public static final int COLOR_AQUA = 0x00FFFF;
	public static final int COLOR_BLACK = 0x292929;
	public static final int COLOR_GREY = 0x8A8A8A;
	public static final int COLOR_YELLOW = 0xEEEE00;
	public static final int COLOR_BACKDROP = 0xA0000000;
	public static final int[] COLOR_DEFAULT = { 0x4C4C4C, 0x3D3D3D };
	

    public static final Identifier HOTBAR_ATTACK_INDICATOR_BACKGROUND_TEXTURE = new Identifier("hud/hotbar_attack_indicator_background");
    public static final Identifier HOTBAR_ATTACK_INDICATOR_PROGRESS_TEXTURE = new Identifier("hud/hotbar_attack_indicator_progress");
    public static final Identifier WIDGETS_TEX_PATH = new Identifier("textures/gui/widgets.png");
    public static final Identifier ARMOR_EMPTY_TEXTURE = new Identifier("hud/armor_empty");
    public static final Identifier ARMOR_HALF_TEXTURE = new Identifier("hud/armor_half");
    public static final Identifier ARMOR_FULL_TEXTURE = new Identifier("hud/armor_full");
    
    /**
     * Draws a rectangle on the screen
     * 
     * @param dc
     *            the DrawContext passed through the method
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
        float f3;
        if (color <= 0xFFFFFF && color >= 0)
            f3 = 1.0F;
        else
            f3 = (color >> 24 & 255) / 255.0F;
        float f = (color >> 16 & 255) / 255.0F;
        float f1 = (color >> 8 & 255) / 255.0F;
        float f2 = (color & 255) / 255.0F;
        
        Matrix4f matrix4f = dc.getMatrices().peek().getPositionMatrix();
        
        BufferBuilder vertexbuffer = Tessellator.getInstance().getBuffer();
        
        vertexbuffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        vertexbuffer.vertex(matrix4f, posX, posY + height, 0).color(f, f1, f2, f3).next();
        vertexbuffer.vertex(matrix4f, posX + width, posY + height, 0).color(f, f1, f2, f3).next();
        vertexbuffer.vertex(matrix4f, posX + width, posY, 0).color(f, f1, f2, f3).next();
        vertexbuffer.vertex(matrix4f, posX, posY, 0).color(f, f1, f2, f3).next();
        
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        RenderSystem.disableDepthTest();
        RenderSystem.enableBlend();
        BufferRenderer.drawWithGlobalProgram(vertexbuffer.end());
        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
    }
    
    protected static void drawOutline(DrawContext dc, int x, int y, int width, int height, int color) {
        drawRect(dc, x, y, width, 1, color);
        drawRect(dc, x, y + 1, 1, height - 2, color);
        drawRect(dc, x + width - 1, y + 1, 1, height - 2, color);
        drawRect(dc, x, y + height - 1, width, 1, color);
    }

    public static void drawCustomBar(DrawContext dc, int x, int y, int width, int height, double value, int colorBarLight, int colorBarDark) {
        drawCustomBar(dc, x, y, width, height, value, COLOR_DEFAULT[0], COLOR_DEFAULT[1], colorBarLight, colorBarDark, true, 0x000000);
    }

    public static void drawCustomBar(DrawContext dc, int x, int y, int width, int height, double value, int colorGroundLight, int colorGroundDark, int colorBarLight, int colorBarDark) {
        drawCustomBar(dc, x, y, width, height, value, colorGroundLight, colorGroundDark, colorBarLight, colorBarDark, true, 0x000000);
    }

    public static void drawCustomBar(DrawContext dc, int x, int y, int width, int height, double value, int colorGroundLight, int colorGroundDark, int colorBarLight, int colorBarDark, boolean outlined) {
        drawCustomBar(dc, x, y, width, height, value, colorGroundLight, colorGroundDark, colorBarLight, colorBarDark, outlined, 0x000000);
    }

    public static void drawCustomBar(DrawContext dc, int x, int y, int width, int height, double value, int colorGroundLight, int colorGroundDark, int colorBarLight, int colorBarDark, int colorOutline) {
        drawCustomBar(dc, x, y, width, height, value, colorGroundLight, colorGroundDark, colorBarLight, colorBarDark, true, colorOutline);
    }

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
    
    public static void drawCustomBarBackdrop(DrawContext dc, int x, int y, int width, int height, double value, int colorBarLight, int colorBarDark) {
    	drawRect(dc, x, y, width, height, COLOR_BACKDROP);
    	drawCustomBar(dc, x, y, width, height, value, -1, -1, colorBarLight, colorBarDark, false);
    }
    
    public static void renderHotbarItem(DrawContext dc, int x, int y, float partialTicks, PlayerEntity player, ItemStack item) {
        if (!item.isEmpty()) {
        	MinecraftClient instance = MinecraftClient.getInstance();
        	
        	MatrixStack matrixStack = dc.getMatrices();
        	
            float f = (float)item.getBobbingAnimationTime() - partialTicks;

            if (f > 0.0F) {
                matrixStack.push();
                float f1 = 1.0F + f / 5.0F;
                matrixStack.translate(x + 8, y + 12, 0.0F);
                matrixStack.scale(1.0F / f1, (f1 + 1.0F) / 2.0F, 1.0F);
                matrixStack.translate((-(x + 8)), (-(y + 12)), 0.0F);
            }
            
            dc.drawItem(item, x, y);

            if (f > 0.0F) {
                matrixStack.pop();
            }
            
            dc.drawItemInSlot(instance.textRenderer, item, x, y);
        }
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

}
