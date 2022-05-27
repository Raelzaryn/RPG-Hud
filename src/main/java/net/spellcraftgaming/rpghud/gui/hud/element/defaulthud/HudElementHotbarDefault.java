package net.spellcraftgaming.rpghud.gui.hud.element.defaulthud;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.client.gui.Gui;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementHotbarDefault extends HudElement {

    protected static final ResourceLocation WIDGETS_TEX_PATH = new ResourceLocation("textures/gui/widgets.png");

    public int offset = -9;

    public HudElementHotbarDefault() {
        super(HudElementType.HOTBAR, 0, 0, 0, 0, true);
    }

    @Override
    public void drawElement(Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        if (this.mc.gameMode.getPlayerMode() == GameType.SPECTATOR)
            this.mc.gui.getSpectatorGui().renderHotbar(ms);
        else if (this.mc.getCameraEntity() instanceof Player) {
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            bind(WIDGETS_TEX_PATH);
            ItemStack itemstack = this.mc.player.getOffhandItem();
            HumanoidArm arm = this.mc.player.getMainArm().getOpposite();
            int i = scaledWidth / 2 + this.settings.getPositionValue(Settings.hotbar_position)[0];
            int posY = this.settings.getPositionValue(Settings.hotbar_position)[1] + this.offset;
            float f = zLevel;
            zLevel = -90.0F;
            gui.blit(ms, i - 91, scaledHeight - 22 + posY, 0, 0, 182, 22);
            gui.blit(ms, i - 91 - 1 + this.mc.player.getInventory().selected * 20, scaledHeight - 22 + posY - 1, 0, 22, 24, 22);
            if (!itemstack.isEmpty())
                if (arm == HumanoidArm.LEFT)
                    gui.blit(ms, i - 91 - 29, scaledHeight - 23 + posY, 24, 22, 29, 24);
                else
                    gui.blit(ms, i + 91, scaledHeight - 23 + posY, 53, 22, 29, 24);

            zLevel = f;
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();

            for (int l = 0; l < 9; ++l) {
                int i1 = i - 90 + l * 20 + 2;
                int j1 = scaledHeight - 16 - 3 + posY;
                this.renderHotbarItem(i1, j1, partialTicks, this.mc.player, this.mc.player.getInventory().items.get(l));
            }

            if (!itemstack.isEmpty()) {
                int l1 = scaledHeight - 16 - 3 + posY;
                if (arm == HumanoidArm.LEFT)
                    this.renderHotbarItem(i - 91 - 26, l1, partialTicks, this.mc.player, itemstack);
                else
                    this.renderHotbarItem(i + 91 + 10, l1, partialTicks, this.mc.player, itemstack);
            }

            if (this.mc.options.attackIndicator == AttackIndicatorStatus.HOTBAR) {
                float f1 = this.mc.player.getAttackAnim(0.0F);
                if (f1 < 1.0F) {
                    int i2 = scaledHeight - 20 + posY;
                    int j2 = i + 91 + 6;
                    if (arm == HumanoidArm.RIGHT)
                        j2 = i - 91 - 22;

                    bind(Gui.GUI_ICONS_LOCATION);
                    int k1 = (int) (f1 * 19.0F);
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                    gui.blit(ms, j2, i2, 0, 94, 18, 18);
                    gui.blit(ms, j2, i2 + 18 - k1, 18, 112 - k1, 18, k1);
                }
            }

            RenderSystem.disableBlend();
        }
    }
}
