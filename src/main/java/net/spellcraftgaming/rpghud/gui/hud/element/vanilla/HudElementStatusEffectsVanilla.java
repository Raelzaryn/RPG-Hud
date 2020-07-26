package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import java.util.Collection;

import com.google.common.collect.Ordering;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@OnlyIn(Dist.CLIENT)
public class HudElementStatusEffectsVanilla extends HudElement {

    public HudElementStatusEffectsVanilla() {
        super(HudElementType.STATUS_EFFECTS, 0, 0, 0, 0, true);
    }

    @Override
    public void drawElement(Gui gui, float na, float partialTicks, int scaledWidth, int scaledHeight) {
        double scale = getScale();
        GlStateManager.scaled(scale, scale, scale);
        Collection<PotionEffect> collection = this.mc.player.getActivePotionEffects();
        if(!collection.isEmpty()) {
            GlStateManager.enableBlend();
            int i = 0;
            int j = 0;
            this.mc.getTextureManager().bindTexture(GuiContainer.INVENTORY_BACKGROUND);
            for(PotionEffect effectinstance : Ordering.natural().reverse().sortedCopy(collection)) {
                Potion effect = effectinstance.getPotion();
                if(!effect.shouldRenderHUD(effectinstance))
                    continue;
                // Rebind in case previous renderHUDEffect changed texture
                this.mc.getTextureManager().bindTexture(GuiContainer.INVENTORY_BACKGROUND);
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
                    GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                    float f = 1.0F;
                    if(effectinstance.isAmbient()) {
                        // Background Beacon
                        gui.drawTexturedModalRect(k, l, 165, 166, 24, 24);
                    } else {
                        // Background Regular
                        gui.drawTexturedModalRect(k, l, 141, 166, 24, 24);
                        if(effectinstance.getDuration() <= 200) {
                            int i1 = 10 - effectinstance.getDuration() / 20;
                            f = MathHelper.clamp((float) effectinstance.getDuration() / 10.0F / 5.0F * 0.5F, 0.0F, 0.5F)
                                    + MathHelper.cos((float) effectinstance.getDuration() * (float) Math.PI / 5.0F)
                                            * MathHelper.clamp((float) i1 / 10.0F * 0.25F, 0.0F, 0.25F);
                        }
                    }
                    GlStateManager.color4f(1.0F, 1.0F, 1.0F, f);
                    int i1 = effect.getStatusIconIndex();
                    int l1 = i1 % 12;
                    int k1 = i1 / 12;
                    if (effect.hasStatusIcon())
                    gui.drawTexturedModalRect(k + 3, l + 3, l1 * 18, 198 + k1 * 18, 18, 18);
                    // Main
                    effect.renderHUDEffect(effectinstance, gui, k, l, -90, f);
                    if(rpgHud.settings.getBoolValue(Settings.status_time) && !effectinstance.isAmbient()) {
                        int duration = effectinstance.getDuration()/20;
                        String s = "*:**";
                        if(duration < 600) s = String.valueOf(duration / 60 + ":" + (duration % 60 < 10 ? "0" + (duration % 60) : (duration % 60)));
                        k -= mc.fontRenderer.getStringWidth(s)/2;
                        this.drawStringWithBackground(s, k +12, l +14, -1, 0);
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
