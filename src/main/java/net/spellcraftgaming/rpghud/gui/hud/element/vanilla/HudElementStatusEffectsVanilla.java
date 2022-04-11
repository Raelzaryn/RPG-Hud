package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import java.util.Collection;

import com.google.common.collect.Ordering;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.texture.PotionSpriteUploader;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.MathHelper;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementStatusEffectsVanilla extends HudElement {

    public HudElementStatusEffectsVanilla() {
        super(HudElementType.STATUS_EFFECTS, 0, 0, 0, 0, true);
    }

    @Override
    public void drawElement(AbstractGui gui, MatrixStack ms, float na, float partialTicks, int scaledWidth, int scaledHeight) {
        double scale = getScale();
        RenderSystem.scaled(scale, scale, scale);
        Collection<EffectInstance> collection = this.mc.player.getActivePotionEffects();
        if(!collection.isEmpty()) {
            RenderSystem.enableBlend();
            int i = 0;
            int j = 0;
            PotionSpriteUploader potionspriteuploader = this.mc.getPotionSpriteUploader();
            this.mc.getTextureManager().bindTexture(ContainerScreen.INVENTORY_BACKGROUND);

            for(EffectInstance effectinstance : Ordering.natural().reverse().sortedCopy(collection)) {
                Effect effect = effectinstance.getPotion();
                if(!effectinstance.shouldRenderHUD())
                    continue;
                // Rebind in case previous renderHUDEffect changed texture
                this.mc.getTextureManager().bindTexture(ContainerScreen.INVENTORY_BACKGROUND);
                if(effectinstance.isShowIcon()) {
                    int k = getPosX(scaledWidth);
                    int l = getPosY(scaledHeight);
                    if(this.mc.isDemo()) {
                        l += 15;
                    }
                    
                    if(effect.isBeneficial()) {
                        ++i;
                        if(rpgHud.settings.getBoolValue(Settings.status_vertical)) {
                            k -= 25;
                            l += 25 * (i - 1);
                        } else {
                            k -= 25 * i;
                        }


                    } else {
                        ++j;
                        if(rpgHud.settings.getBoolValue(Settings.status_vertical)) {
                            k -= 50; 
                            l += 25 * (j - 1);
                            
                        } else {
                            k -= 25 * j;
                            l += 25;
                        }

                    }

                    RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                    float f = 1.0F;
                    if(effectinstance.isAmbient()) {
                        // Background Beacon
                        gui.blit(ms, k, l, 165, 166, 24, 24);
                    } else {
                        // Background Regular
                        gui.blit(ms, k, l, 141, 166, 24, 24);
                        if(effectinstance.getDuration() <= 200) {
                            int i1 = 10 - effectinstance.getDuration() / 20;
                            f = MathHelper.clamp((float) effectinstance.getDuration() / 10.0F / 5.0F * 0.5F, 0.0F, 0.5F)
                                    + MathHelper.cos((float) effectinstance.getDuration() * (float) Math.PI / 5.0F)
                                            * MathHelper.clamp((float) i1 / 10.0F * 0.25F, 0.0F, 0.25F);
                        }
                    }
                    TextureAtlasSprite textureatlassprite = potionspriteuploader.getSprite(effect);
                    this.mc.getTextureManager().bindTexture(textureatlassprite.getAtlasTexture().getTextureLocation());
                    RenderSystem.color4f(1.0F, 1.0F, 1.0F, f);
                    AbstractGui.blit(ms, k + 3, l + 3, gui.getBlitOffset(), 18, 18, textureatlassprite);
                    // Main
                    effectinstance.renderHUDEffect(gui, ms, k, l, gui.getBlitOffset(), f);
                    if(rpgHud.settings.getBoolValue(Settings.status_time) && !effectinstance.isAmbient()) {
                        int duration = effectinstance.getDuration()/20;
                        String s = "*:**";
                        if(duration < 600) s = String.valueOf(duration / 60 + ":" + (duration % 60 < 10 ? "0" + (duration % 60) : (duration % 60)));
                        k -= mc.fontRenderer.getStringWidth(s)/2;
                        this.drawStringWithBackground(ms, s, k +12, l +14, -1, 0);
                    }
                }
            }
        }
        scale = getInvertedScale();
    }

    @Override
    public int getPosX(int scaledWidth) {
        return (int) (scaledWidth * getInvertedScale() + this.settings.getPositionValue(Settings.status_position)[0]);
    }

    @Override
    public int getPosY(int scaledHeight) {
        return (int) 1 + (this.settings.getPositionValue(Settings.status_position)[1]);
    }

    @Override
    public double getScale() {
        double scale = this.settings.getDoubleValue(Settings.status_scale);
        //if(scale != 0)
        return scale;
        //return 1;
    }
}
