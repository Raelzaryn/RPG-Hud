package net.spellcraftgaming.rpghud.gui.hud.element.simple;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.TextAlignment;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.spellcraftgaming.rpghud.RPGHudUtils;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(value=EnvType.CLIENT)
public class HudElementHealthMountSimple extends HudElement {

	public HudElementHealthMountSimple() {
		super(HudElementType.HEALTH_MOUNT, 0, 0, 0, 0, true);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean checkConditions() {
		return this.mc.player.getVehicle() instanceof LivingEntity && RPGHudUtils.isSurvival();
	}
	
	@Override
	public void drawElement(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, int scaledWidth, int scaledHeight) {
		LivingEntity mount = (LivingEntity) this.mc.player.getVehicle();
		int health = Mth.ceil(mount.getHealth());
		int healthMax = Mth.ceil(mount.getMaxHealth());
		if(health > healthMax) health = healthMax;
		
		int width = 84;
		
		int posX = ((scaledWidth) / 2) + 7 + this.settings.getPositionValue(Settings.mount_health_position)[0];
		int posY = scaledHeight - 32 - 17 + this.settings.getPositionValue(Settings.mount_health_position)[1];
		
		drawCustomBar(graphics, posX, posY, width, 8, (double) health / (double) healthMax * 100.0D, 0xA0000000, 0xA0000000, this.settings.getIntValue(Settings.color_health), offsetColorPercent(this.settings.getIntValue(Settings.color_health), OFFSET_PERCENT), 0xA0000000);
		
		String stringHealth = this.settings.getBoolValue(Settings.mount_health_percentage) ? Mth.floor((double) health / (double) healthMax * 100) + "%" : health + "/" + healthMax;

		if (this.settings.getBoolValue(Settings.show_numbers_health)) {
            float scale = 0.5f;
            if (this.settings.getBoolValue(Settings.debug_number_size)) scale = 0.666666666f;
            float invertedScale = 1f / scale;
            graphics.pose().scale(scale, scale);
            graphics.centeredText(this.mc.font, stringHealth, Math.round((posX + (width / 2)) * invertedScale), Math.round(((posY) * invertedScale) + (invertedScale * 4 - 4)), -1);
            graphics.pose().scale(invertedScale, invertedScale);
        }
	}
}
