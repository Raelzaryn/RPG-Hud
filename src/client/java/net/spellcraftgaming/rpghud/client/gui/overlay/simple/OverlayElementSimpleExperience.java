package net.spellcraftgaming.rpghud.client.gui.overlay.simple;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.MathHelper;
import net.spellcraftgaming.rpghud.client.gui.overlay.OverlayElement;
import net.spellcraftgaming.rpghud.client.gui.overlay.OverlayElementType;
import net.spellcraftgaming.rpghud.client.gui.overlay.OverlayParentAnchor;
import net.spellcraftgaming.rpghud.client.helper.DrawHelper;

public class OverlayElementSimpleExperience extends OverlayElement{

	public OverlayElementSimpleExperience() {
		super(OverlayElementType.EXPERIENCE, 182, 10);
	}

	public boolean show_numbers_experience;
	public boolean experience_percentage;
	public int color_experience;
	
	@Override
	public void initialize() {
		this.anchoredX = 0; //TODO: SETTING
		this.anchoredY = 0; //TODO: SETTING
		this.scale = 2; //TODO: SETTING
		this.isGuiScale = true; //TODO: SETTING
			
		this.yAnchor = Y_ANCHOR_BOTTOM; // TODO: SETTING
		this.xAnchor = X_ANCHOR_CENTER; // TODO: SETTING
		this.parentAnchor = new OverlayParentAnchor(OverlayElementType.HOTBAR, X_ANCHOR_CENTER, Y_ANCHOR_TOP, 0, -1);
		
		this.show_numbers_experience = true; // TODO: SETTING
		this.experience_percentage = false; // TODO: SETTING
		this.color_experience = DrawHelper.COLOR_YELLOW; // TODO: SETTING
	}
	
	@Override
	public boolean shouldRender() {
		MinecraftClient instance = MinecraftClient.getInstance();
		return instance.interactionManager.hasStatusBars();
	}
	
	@Override
	public void render(DrawContext dc, float tickDelta) {
		MinecraftClient instance = MinecraftClient.getInstance();
		int exp = MathHelper.ceil(instance.player.getNextLevelExperience() * instance.player.experienceProgress);
		int expCap = instance.player.getNextLevelExperience();
		double full = 100D / expCap;
		int posX = getPosX();
		int posY = getPosY();
		
		DrawHelper.drawCustomBarBackdrop(dc, posX, posY, width, height, exp * full, color_experience, DrawHelper.offsetColorPercent(color_experience, 25));
		
		String stringExp = experience_percentage ? (int) Math.floor((double) exp / (double) expCap * 100) + "%" : exp + "/" + expCap;
		
		boolean reduceByOne = true;
		
		if(reduceByOne) {
			float scaleLower  = getScale();
			if (scaleLower > 1) {
				scaleLower -= 1;
			}
		}
		if (show_numbers_experience) {
			dc.drawCenteredTextWithShadow( instance.textRenderer, stringExp, posX + (width/2), posY + 1, -1);
		}
		
	}



}
