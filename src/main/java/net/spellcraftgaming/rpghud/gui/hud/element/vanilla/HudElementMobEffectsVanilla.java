package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import com.google.common.collect.Ordering;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

import java.util.Collection;

public class HudElementMobEffectsVanilla extends HudElement {

    protected static final Identifier EFFECT_BACKGROUND_AMBIENT_SPRITE = Identifier.withDefaultNamespace("hud/effect_background_ambient");
    protected static final Identifier EFFECT_BACKGROUND_SPRITE = Identifier.withDefaultNamespace("hud/effect_background");

    public HudElementMobEffectsVanilla() {
        super(HudElementType.STATUS_EFFECTS, 0, 0, 0, 0, true);
    }

    @Override
    public void drawElement(GuiGraphicsExtractor gg, float na, DeltaTracker partialTicks, int scaledWidth, int scaledHeight) {
        float scale = getScale();
        gg.pose().scale(scale, scale);
        Collection<MobEffectInstance> collection = this.mc.player.getActiveEffects();
        if(!collection.isEmpty()) {
            int i = 0;
            int j = 0;

            for(MobEffectInstance effectinstance : Ordering.natural().reverse().sortedCopy(collection)) {
                Holder<MobEffect> effect = effectinstance.getEffect();
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
                    float alpha = 1.0F;
                    int eDuration = effectinstance.getDuration();
                    if(effectinstance.isAmbient()) {
                        // Background Beacon
                        gg.blitSprite(RenderPipelines.GUI_TEXTURED, EFFECT_BACKGROUND_AMBIENT_SPRITE, k, l, 24, 24);
                    } else {
                        if(eDuration <= 200) {
                            int i1 = 10 - eDuration / 20;
                            alpha = Mth.clamp((float) eDuration / 10.0F / 5.0F * 0.5F, 0F, 0.5F)
                                    + Mth.cos((float) eDuration * (float) Math.PI / 5.0F)
                                            * Mth.clamp((float) i1 / 10.0F * 0.25F, 0.0F, 0.25F);
                        }
                        // Background Regular
                        gg.blitSprite(RenderPipelines.GUI_TEXTURED, EFFECT_BACKGROUND_SPRITE, k, l, 24, 24, ARGB.white(alpha));
                    }
                    gg.blitSprite(RenderPipelines.GUI_TEXTURED, Hud.getMobEffectSprite(effect), k + 3, l + 3, 18, 18, ARGB.white(alpha));

                    // Main
                    if(rpgHud.settings.getBoolValue(Settings.status_time) && !effectinstance.isAmbient()) {
                        int duration = eDuration/20;
                        String s = "*:**";
                        if(duration < 600) s = duration / 60 + ":" + (duration % 60 < 10 ? "0" + (duration % 60) : (duration % 60));
                        k -= mc.font.width(s)/2;
                        this.drawStringWithBackground(gg, s, k +12, l +14, -1, 0);
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
        return 1 + (this.settings.getPositionValue(Settings.status_position)[1]);
    }

    @Override
    public float getScale() {
        return (float) this.settings.getDoubleValue(Settings.status_scale);
    }
}
