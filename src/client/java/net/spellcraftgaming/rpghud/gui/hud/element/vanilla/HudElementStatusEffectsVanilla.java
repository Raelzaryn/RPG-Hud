package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import java.util.Collection;

import com.google.common.collect.Ordering;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.Holder;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.phys.AABB;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(value=EnvType.CLIENT)
public class HudElementStatusEffectsVanilla extends HudElement {

    public HudElementStatusEffectsVanilla() {
        super(HudElementType.STATUS_EFFECTS, 0, 0, 0, 0, true);
    }

    @Override
    public void drawElement(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, int scaledWidth, int scaledHeight) {
        float scale = getScale();
        graphics.pose().scale(scale, scale);
        Collection<MobEffectInstance> collection = this.mc.player.getActiveEffects();
        if(!collection.isEmpty()) {
            int i = 0;
            int j = 0;

            for(MobEffectInstance effectinstance : Ordering.natural().reverse().sortedCopy(collection)) {
                Holder<MobEffect> effect = effectinstance.getEffect();
                // Rebind in case previous renderHUDEffect changed texture
                if(effectinstance.showIcon()) {
                	
                    int k = getPosX(scaledWidth);
                    int l = getPosY(scaledHeight);
                    if(this.mc.isDemo()) {
                        l += 15;
                    }
                    
                    if(effect.value().isBeneficial()) {
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
                    float f = 1.0F;
                    if(effectinstance.isAmbient()) {
                        // Background Beacon
                        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, EFFECT_BACKGROUND_AMBIENT_TEXTURE, k, l, 24, 24);
                    } else {
                        // Background Regular
                    	
                        if(effectinstance.getDuration() <= 200) {
                            int i1 = 10 - effectinstance.getDuration() / 20;
                            f = Mth.clamp((float) effectinstance.getDuration() / 10.0F / 5.0F * 0.5F, 0.5F, 1F)
                                    + Mth.cos((float) effectinstance.getDuration() * (float) Math.PI / 7F)
                                            * Mth.clamp((float) i1 / 10.0F * 0.25F, 0.1F, 0.25F);
                        }
                        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, EFFECT_BACKGROUND_TEXTURE, k, l, 24, 24);
                    }
                    
                    graphics.blitSprite(RenderPipelines.GUI_TEXTURED, Gui.getMobEffectSprite(effect), k + 3, l + 3, 18, 18, ARGB.white(f));
                    // Main
                    if(rpgHud.settings.getBoolValue(Settings.status_time) && !effectinstance.isAmbient()) {
                        int duration = effectinstance.getDuration()/20;
                        String s = "*:**";
                        if(duration < 600) s = duration / 60 + ":" + (duration % 60 < 10 ? "0" + (duration % 60) : (duration % 60));
                        k -= mc.font.width(s)/2;
                        this.drawStringWithBackground(graphics, s, k +12, l +14, -1, 0);
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
