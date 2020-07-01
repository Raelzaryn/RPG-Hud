package net.spellcraftgaming.rpghud.gui.hud.element.defaulthud;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@OnlyIn(Dist.CLIENT)
public class HudElementHealthMountDefault extends HudElement {

    public HudElementHealthMountDefault() {
        super(HudElementType.HEALTH_MOUNT, 0, 0, 0, 0, false);
        this.parent = HudElementType.WIDGET;
    }

    @Override
    public boolean checkConditions() {
        return this.mc.player.getRidingEntity() instanceof EntityLivingBase && !this.mc.gameSettings.hideGUI && this.mc.playerController.shouldDrawHUD();
    }

    @Override
    public void drawElement(Gui gui, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        EntityLivingBase mount = (EntityLivingBase) this.mc.player.getRidingEntity();
        int health = MathHelper.ceil(mount.getHealth());
        int healthMax = MathHelper.ceil(mount.getMaxHealth());
        int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 53 : 33) + this.settings.getPositionValue(Settings.mount_health_position)[0];
        int posY = (this.settings.getBoolValue(Settings.render_player_face) ? 49 : 40) + this.settings.getPositionValue(Settings.mount_health_position)[1];
        drawCustomBar(posX, posY, 88, 8, (double) health / (double) healthMax * 100.0D, -1, -1, this.settings.getIntValue(Settings.color_health),
                offsetColorPercent(this.settings.getIntValue(Settings.color_health), OFFSET_PERCENT));
        String stringHealth = this.settings.getBoolValue(Settings.mount_health_percentage) ? (int) Math.floor((double) health / (double) healthMax * 100) + "%"
                : health + "/" + healthMax;

        if(this.settings.getBoolValue(Settings.show_numbers_health)) {
            GlStateManager.scaled(0.5, 0.5, 0.5);
            gui.drawCenteredString(this.mc.fontRenderer, stringHealth, posX * 2 + 88, posY * 2 + 4, -1);
            GlStateManager.scaled(2.0, 2.0, 2.0);
        }
    }

}
