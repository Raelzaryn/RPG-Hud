package net.spellcraftgaming.rpghud.gui.hud.element.hotbar;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@OnlyIn(Dist.CLIENT)
public class HudElementLevelHotbar extends HudElement {

    public HudElementLevelHotbar() {
        super(HudElementType.LEVEL, 0, 0, 0, 0, true);
        this.parent = HudElementType.WIDGET;
    }

    @Override
    public boolean checkConditions() {
        return this.mc.playerController.shouldDrawHUD();
    }

    @Override
    public void drawElement(Gui gui, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        GlStateManager.disableBlend();
        String level = String.valueOf(this.mc.player.experienceLevel);
        this.mc.fontRenderer.drawStringWithShadow(level,
                (this.settings.getBoolValue(Settings.render_player_face) ? 25 : 13) + this.settings.getPositionValue(Settings.level_position)[0]
                        - this.mc.fontRenderer.getStringWidth(level) / 2,
                scaledHeight - (this.settings.getBoolValue(Settings.render_player_face) ? 22 : 40) + this.settings.getPositionValue(Settings.level_position)[1],
                0x80FF20);
        GlStateManager.enableBlend();
    }

}
