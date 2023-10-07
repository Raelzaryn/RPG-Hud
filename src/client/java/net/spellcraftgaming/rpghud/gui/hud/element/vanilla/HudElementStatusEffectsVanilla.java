package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import java.util.Collection;

import com.google.common.collect.Ordering;
import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.StatusEffectSpriteManager;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.math.MathHelper;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(value=EnvType.CLIENT)
public class HudElementStatusEffectsVanilla extends HudElement {

    public HudElementStatusEffectsVanilla() {
        super(HudElementType.STATUS_EFFECTS, 0, 0, 0, 0, true);
    }

    @Override
    public void drawElement(DrawContext dc, float na, float partialTicks, int scaledWidth, int scaledHeight) {
        float scale = getScale();
        dc.getMatrices().scale(scale, scale, scale);
        Collection<StatusEffectInstance> collection = this.mc.player.getStatusEffects();
        if(!collection.isEmpty()) {
            RenderSystem.enableBlend();
            int i = 0;
            int j = 0;
            StatusEffectSpriteManager potionspriteuploader = this.mc.getStatusEffectSpriteManager();

            for(StatusEffectInstance effectinstance : Ordering.natural().reverse().sortedCopy(collection)) {
                StatusEffect effect = effectinstance.getEffectType();
                // Rebind in case previous renderHUDEffect changed texture
                if(effectinstance.shouldShowIcon()) {
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
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                    float f = 1.0F;
                    if(effectinstance.isAmbient()) {
                        // Background Beacon
                        dc.drawGuiTexture(EFFECT_BACKGROUND_AMBIENT_TEXTURE, k, l, 24, 24);
                    } else {
                        // Background Regular
                    	dc.drawGuiTexture(EFFECT_BACKGROUND_TEXTURE, k, l, 24, 24);
                        if(effectinstance.getDuration() <= 200) {
                            int i1 = 10 - effectinstance.getDuration() / 20;
                            f = MathHelper.clamp((float) effectinstance.getDuration() / 10.0F / 5.0F * 0.5F, 0.0F, 0.5F)
                                    + MathHelper.cos((float) effectinstance.getDuration() * (float) Math.PI / 5.0F)
                                            * MathHelper.clamp((float) i1 / 10.0F * 0.25F, 0.0F, 0.25F);
                        }
                    }
                    Sprite textureatlassprite = potionspriteuploader.getSprite(effect);
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, f);
                    //TODO:
                    dc.drawSprite(k + 3, l + 3, 0, 18, 18, textureatlassprite);
                    // Main
                    if(rpgHud.settings.getBoolValue(Settings.status_time) && !effectinstance.isAmbient()) {
                        int duration = effectinstance.getDuration()/20;
                        String s = "*:**";
                        if(duration < 600) s = String.valueOf(duration / 60 + ":" + (duration % 60 < 10 ? "0" + (duration % 60) : (duration % 60)));
                        k -= mc.textRenderer.getWidth(s)/2;
                        this.drawStringWithBackground(dc, s, k +12, l +14, -1, 0);
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
    public float getScale() {
        float scale = (float)this.settings.getDoubleValue(Settings.status_scale);
        //if(scale != 0)
        return scale;
        //return 1;
    }
}
