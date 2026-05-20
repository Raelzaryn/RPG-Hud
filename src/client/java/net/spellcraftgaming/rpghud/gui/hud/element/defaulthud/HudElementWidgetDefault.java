package net.spellcraftgaming.rpghud.gui.hud.element.defaulthud;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.world.entity.LivingEntity;
import net.spellcraftgaming.rpghud.RPGHudUtils;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(value=EnvType.CLIENT)
public class HudElementWidgetDefault extends HudElement {

	public HudElementWidgetDefault() {
		super(HudElementType.WIDGET, 0, 0, 0, 0, true);
	}

    @Override
	public boolean checkConditions() {
        return RPGHudUtils.isSurvival();
	}

    @Override
    public void drawElement(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, int scaledWidth, int scaledHeight) {
        int posX = this.settings.getPositionValue(Settings.widget_position)[0];
        int posY = this.settings.getPositionValue(Settings.widget_position)[1];
        graphics.blit(RenderPipelines.GUI_TEXTURED, INTERFACE,posX + (this.settings.getBoolValue(Settings.render_player_face) ? 50 : 25), posY + (this.settings.getBoolValue(Settings.render_player_face) ? 8 : 0), 0, 0, 114, 35, 256, 256);
        if (this.mc.player.getVehicle() instanceof LivingEntity) {
            graphics.blit(RenderPipelines.GUI_TEXTURED, INTERFACE,posX + (this.settings.getBoolValue(Settings.render_player_face) ? 51 : 31), posY + (this.settings.getBoolValue(Settings.render_player_face) ? 39 : 30), 164, 0, 92, 20, 256, 256);
        }

        int facePosX = this.settings.getPositionValue(Settings.face_position)[0];
        int facePosY = this.settings.getPositionValue(Settings.face_position)[1];
        if (this.settings.getBoolValue(Settings.render_player_face)) {
            graphics.blit(RenderPipelines.GUI_TEXTURED, INTERFACE,posX + facePosX, posY + facePosY, 114, 0, 50, 50, 256, 256);
            graphics.pose().scale(0.5f, 0.5f);
            graphics.blit(RenderPipelines.GUI_TEXTURED, this.playerSkinId ,posX * 2 + 34 + facePosX * 2, posY * 2 + 34 + facePosY * 2, 32, 32, 32, 32, 256, 256);
            graphics.blit(RenderPipelines.GUI_TEXTURED, this.playerSkinId,posX * 2 + 34 + facePosX * 2, posY * 2 + 34 + facePosY * 2, 160, 32, 32, 32, 256, 256);
            graphics.pose().scale(2f, 2f);
        } else {
            graphics.blit(RenderPipelines.GUI_TEXTURED, INTERFACE,posX, posY + (this.settings.getBoolValue(Settings.render_player_face) ? 11 : 3), 114, 50, 25, 29, 256, 256);
        }
    }
}
