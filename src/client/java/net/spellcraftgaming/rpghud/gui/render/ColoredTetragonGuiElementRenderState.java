package net.spellcraftgaming.rpghud.gui.render;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.renderer.state.gui.GuiElementRenderState;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix3x2f;

@Environment(EnvType.CLIENT)
public record ColoredTetragonGuiElementRenderState(
	RenderPipeline pipeline,
	TextureSetup textureSetup,
	Matrix3x2f pose,
	int posX1, 
	int posX2, 
	int posY1, 
	int posY2, 
	int width1, 
	int width2, 
	int height1, 
	int height2, 
	int color,
	@Nullable ScreenRectangle scissorArea,
	@Nullable ScreenRectangle bounds
) implements GuiElementRenderState {
	public ColoredTetragonGuiElementRenderState(
		RenderPipeline pipeline, TextureSetup textureSetup, Matrix3x2f pose, int posX1, int posX2, int posY1, int posY2, int width1, int width2, int height1, int height2, int color, @Nullable ScreenRectangle scissorArea
	) {
		this(pipeline, textureSetup, pose, posX1, posX2, posY1, posY2, width1, width2, height1, height2, color, scissorArea, createBounds(posX1, posX2, posY1, posY2, width1, width2, height1, height2, pose, scissorArea));
	}

    @Override
    public void buildVertices(VertexConsumer vertexConsumer) {
        int convertedcolor = color;
        if (color == -1)
            return;
        if (color <= 0xFFFFFF && color >= 0)
            convertedcolor = color + 0xFF000000;

        vertexConsumer.addVertexWith2DPose(this.pose(), (float)posX1, (float) posY1).setColor(convertedcolor);
        vertexConsumer.addVertexWith2DPose(this.pose(), (float)posX2, (float) posY1+height1).setColor(convertedcolor);
        vertexConsumer.addVertexWith2DPose(this.pose(), (float)posX2+width2, (float)posY2+height2).setColor(convertedcolor);
        vertexConsumer.addVertexWith2DPose(this.pose(), (float)posX1+width1, (float)posY2).setColor(convertedcolor);
    }

	@Nullable
	private static ScreenRectangle createBounds(int posX1, int posX2, int posY1, int posY2, int width1, int width2, int height1, int height2, Matrix3x2f pose, @Nullable ScreenRectangle scissorArea) {
		int left = 0;
		int right = 0;
		int top = 0;
		int bottom = 0;
		if (posX1 > posX2)
			left = posX1;
		else left = posX2;
		if ((posX1+width1)>(posX2+width2))
			right = posX1+width1;
		else right = posX2+width2;
		if (posY1 > posY2)
			top = posY1;
		else top = posY2;
		if ((posY1+height1)>(posY2+height2))
			bottom = (posY1+height1);
		else bottom = (posY2+height2);
		ScreenRectangle screenRect = new ScreenRectangle(left, top, right - left, bottom - top).transformMaxBounds(pose);
		return scissorArea != null ? scissorArea.intersection(screenRect) : screenRect;
	}
}
