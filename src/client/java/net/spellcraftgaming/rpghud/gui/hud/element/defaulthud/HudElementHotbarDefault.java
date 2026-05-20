package net.spellcraftgaming.rpghud.gui.hud.element.defaulthud;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(value=EnvType.CLIENT)
public class HudElementHotbarDefault extends HudElement {

    public int offset = -9;
    
	public HudElementHotbarDefault() {
        super(HudElementType.HOTBAR, 0, 0, 0, 0, true);
    }

    @Override
    public boolean checkConditions() {
        return !this.mc.gameMode.isSpectator();
    }

    @Override
    public void drawElement(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, int scaledWidth, int scaledHeight) {
        if(this.mc.getCameraEntity() instanceof Player) {
            int posY = this.settings.getPositionValue(Settings.hotbar_position)[1] + this.offset;
            ItemStack itemstack = this.mc.player.getOffhandItem();
            HumanoidArm arm = this.mc.player.getMainArm().getOpposite();
            int i = scaledWidth / 2 + this.settings.getPositionValue(Settings.hotbar_position)[0];

            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, HOTBAR_TEXTURE, i - 91, scaledHeight - 22 + posY, 182, 22);
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, HOTBAR_SELECTION_TEXTURE, i - 91 - 1 + this.mc.player.getInventory().getSelectedSlot() * 20, scaledHeight - 22 + posY - 1, 24, 23);
            if(!itemstack.isEmpty())
                if(arm == HumanoidArm.LEFT)
                    graphics.blitSprite(RenderPipelines.GUI_TEXTURED, HOTBAR_OFFHAND_LEFT_TEXTURE, i - 91 - 29, scaledHeight - 23 + posY, 29, 24);
                else
                    graphics.blitSprite(RenderPipelines.GUI_TEXTURED, HOTBAR_OFFHAND_RIGHT_TEXTURE, i + 91, scaledHeight - 23 + posY, 29, 24);

            int s = 1;

            for(int l = 0; l < 9; ++l) {
                int i1 = i - 90 + l * 20 + 2;
                int j1 = scaledHeight - 16 - 3 + posY;
                this.renderHotbarItem(graphics, i1, j1, deltaTracker, this.mc.player, this.mc.player.getInventory().getItem(l), s++);
            }

            if(!itemstack.isEmpty()) {
                int l1 = scaledHeight - 16 - 3 + posY;
                if(arm == HumanoidArm.LEFT)
                    this.renderHotbarItem(graphics, i - 91 - 26, l1, deltaTracker, this.mc.player, itemstack, s++);
                else
                    this.renderHotbarItem(graphics, i + 91 + 10, l1, deltaTracker, this.mc.player, itemstack, s++);
            }

            if(this.mc.options.attackIndicator().get() == AttackIndicatorStatus.HOTBAR) {
                float f1 = this.mc.player.getAttackAnim(0.0f);
                if(f1 < 1.0F) {
                    int i2 = scaledHeight - 20 + posY;
                    int j2 = i + 91 + 6;
                    if(arm == HumanoidArm.RIGHT)
                        j2 = i - 91 - 22;

                    int k1 = (int) (f1 * 19.0F);
                    graphics.blitSprite(RenderPipelines.GUI_TEXTURED, HOTBAR_ATTACK_INDICATOR_BACKGROUND_TEXTURE, j2, i2, 18, 18);
                    graphics.blitSprite(RenderPipelines.GUI_TEXTURED, HOTBAR_ATTACK_INDICATOR_PROGRESS_TEXTURE, j2, i2 + 18 - k1, 18, k1);
                }
            }
        }
    }
}
