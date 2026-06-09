package net.spellcraftgaming.rpghud.gui.hud.element.defaulthud;

import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementHotbarDefault extends HudElement {

    protected static final Identifier HOTBAR_SPRITE = Identifier.withDefaultNamespace("hud/hotbar");
    protected static final Identifier HOTBAR_SELECTION_SPRITE = Identifier.withDefaultNamespace("hud/hotbar_selection");
    protected static final Identifier HOTBAR_OFFHAND_LEFT_SPRITE = Identifier.withDefaultNamespace("hud/hotbar_offhand_left");
    protected static final Identifier HOTBAR_OFFHAND_RIGHT_SPRITE = Identifier.withDefaultNamespace("hud/hotbar_offhand_right");
    protected static final Identifier HOTBAR_ATTACK_INDICATOR_BACKGROUND_SPRITE = Identifier.withDefaultNamespace("hud/hotbar_attack_indicator_background");
    protected static final Identifier HOTBAR_ATTACK_INDICATOR_PROGRESS_SPRITE = Identifier.withDefaultNamespace("hud/hotbar_attack_indicator_progress");

    public final int offset = -9;

    public HudElementHotbarDefault() {
        super(HudElementType.HOTBAR, 0, 0, 0, 0, true);
    }

    @Override
    public void drawElement(GuiGraphicsExtractor gg, float zLevel, DeltaTracker partialTicks, int scaledWidth, int scaledHeight) {
        if (this.mc.gameMode.getPlayerMode() == GameType.SPECTATOR)
            this.mc.gui.getSpectatorGui().extractHotbar(gg);
        else if (this.mc.getCameraEntity() instanceof Player) {
            ItemStack itemstack = this.mc.player.getOffhandItem();
            HumanoidArm arm = this.mc.player.getMainArm().getOpposite();
            int i = scaledWidth / 2 + this.settings.getPositionValue(Settings.hotbar_position)[0];
            int posY = this.settings.getPositionValue(Settings.hotbar_position)[1] + this.offset;
            int posX = this.settings.getPositionValue(Settings.hotbar_position)[0];

            gg.blitSprite(RenderPipelines.GUI_TEXTURED, HOTBAR_SPRITE, i - 91 + posX, scaledHeight - 22 + posY, 182, 22);
            gg.blitSprite(RenderPipelines.GUI_TEXTURED, HOTBAR_SELECTION_SPRITE, i - 91 - 1 + this.mc.player.getInventory().getSelectedSlot() * 20 + posX, scaledHeight - 22 + posY - 1, 24, 22);
            if (!itemstack.isEmpty())
                if (arm == HumanoidArm.LEFT)
                    gg.blitSprite(RenderPipelines.GUI_TEXTURED, HOTBAR_OFFHAND_LEFT_SPRITE, i - 91 - 29, scaledHeight - 23 + posY, 29, 24);
                else
                	gg.blitSprite(RenderPipelines.GUI_TEXTURED, HOTBAR_OFFHAND_RIGHT_SPRITE, i + 91, scaledHeight - 23 + posY, 29, 24);

            for (int l = 0; l < 9; ++l) {
                int i1 = i - 90 + l * 20 + 2;
                int j1 = scaledHeight - 16 - 3 + posY;
                this.renderHotbarItem(gg, i1, j1, partialTicks, this.mc.player, this.mc.player.getInventory().getItem(l));
            }

            if (!itemstack.isEmpty()) {
                int l1 = scaledHeight - 16 - 3 + posY;
                if (arm == HumanoidArm.LEFT)
                    this.renderHotbarItem(gg, i - 91 - 26, l1, partialTicks, this.mc.player, itemstack);
                else
                    this.renderHotbarItem(gg, i + 91 + 10, l1, partialTicks, this.mc.player, itemstack);
            }

            if (this.mc.options.attackIndicator().get() == AttackIndicatorStatus.HOTBAR) {
                int j2 = i + 91 + 6;
                if(arm == HumanoidArm.RIGHT) {
                    j2 = i - 91 - 22;
                }
                renderAttackIndicator(gg, j2 + posX, scaledHeight - 20 + posY);
            }
        }
    }

    protected void renderAttackIndicator(GuiGraphicsExtractor gg, int posX, int posY) {
        float f = this.mc.player.getAttackStrengthScale(0.0F);
        if (f < 1.0F) {

            int l1 = (int)(f * 19.0F);
            gg.blitSprite(RenderPipelines.GUI_TEXTURED, HOTBAR_ATTACK_INDICATOR_BACKGROUND_SPRITE, posX, posY, 18, 18);
            gg.blitSprite(RenderPipelines.GUI_TEXTURED, HOTBAR_ATTACK_INDICATOR_PROGRESS_SPRITE, 18, 18, 0, 18 - l1, posX, posY + 18 - l1, 18, l1);
        }
    }
}
