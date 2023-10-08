package net.spellcraftgaming.rpghud.gui.hud.element.defaulthud;

import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.AttackIndicator;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameMode;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(value=EnvType.CLIENT)
public class HudElementHotbarDefault extends HudElement {

    protected static final Identifier WIDGETS_TEX_PATH = new Identifier("textures/gui/widgets.png");

    public int offset = -9;
    
	public HudElementHotbarDefault() {
        super(HudElementType.HOTBAR, 0, 0, 0, 0, true);
    }

    @Override
    public void drawElement(DrawContext dc, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        if(this.mc.interactionManager.getCurrentGameMode() == GameMode.SPECTATOR)
            this.mc.inGameHud.getSpectatorHud().render(dc);
        else if(this.mc.getCameraEntity() instanceof PlayerEntity) {
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            ItemStack itemstack = this.mc.player.getOffHandStack();
            Arm arm = this.mc.player.getMainArm().getOpposite();
            int i = scaledWidth / 2 + this.settings.getPositionValue(Settings.hotbar_position)[0];
            int posY = this.settings.getPositionValue(Settings.hotbar_position)[1] + this.offset;
            float f = zLevel;
            zLevel = -90.0F;
            dc.drawTexture(WIDGETS_TEX_PATH, i - 91, scaledHeight - 22 + posY, 0, 0, 182, 22);
            dc.drawTexture(WIDGETS_TEX_PATH, i - 91 - 1 + this.mc.player.getInventory().selectedSlot * 20, scaledHeight - 22 + posY - 1, 0, 22, 24, 22);
            if(!itemstack.isEmpty())
                if(arm == Arm.LEFT)
                    dc.drawTexture(WIDGETS_TEX_PATH, i - 91 - 29, scaledHeight - 23 + posY, 24, 22, 29, 24);
                else
                    dc.drawTexture(WIDGETS_TEX_PATH, i + 91, scaledHeight - 23 + posY, 53, 22, 29, 24);

            zLevel = f;
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();

            for(int l = 0; l < 9; ++l) {
                int i1 = i - 90 + l * 20 + 2;
                int j1 = scaledHeight - 16 - 3 + posY;
                this.renderHotbarItem(dc, i1, j1, partialTicks, this.mc.player, this.mc.player.getInventory().main.get(l));
            }

            if(!itemstack.isEmpty()) {
                int l1 = scaledHeight - 16 - 3 + posY;
                if(arm == Arm.LEFT)
                    this.renderHotbarItem(dc, i - 91 - 26, l1, partialTicks, this.mc.player, itemstack);
                else
                    this.renderHotbarItem(dc, i + 91 + 10, l1, partialTicks, this.mc.player, itemstack);
            }

            if(this.mc.options.getAttackIndicator().getValue() == AttackIndicator.HOTBAR) {
                float f1 = this.mc.player.getAttackCooldownProgress(0.0F);
                if(f1 < 1.0F) {
                    int i2 = scaledHeight - 20 + posY;
                    int j2 = i + 91 + 6;
                    if(arm == Arm.RIGHT)
                        j2 = i - 91 - 22;

                    int k1 = (int) (f1 * 19.0F);
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                    dc.drawTexture(ICONS, j2, i2, 0, 94, 18, 18);
                    dc.drawTexture(ICONS, j2, i2 + 18 - k1, 18, 112 - k1, 18, k1);
                }
            }

            RenderSystem.disableBlend();
        }
    }
}
