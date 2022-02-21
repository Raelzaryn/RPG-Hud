package net.spellcraftgaming.rpghud.gui.hud;

import net.minecraft.client.Minecraft;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.defaulthud.*;

public class HudDefault extends HudVanilla {

    public HudDefault(Minecraft mc, String hudKey, String hudName) {
        super(mc, hudKey, hudName);
    }

    @Override
    public HudElement setElementAir() {
        return new HudElementAirDefault();
    }

    @Override
    public HudElement setElementArmor() {
        return new HudElementArmorDefault();
    }

    @Override
    public HudElement setElementExperience() {
        return new HudElementExperienceDefault();
    }

    @Override
    public HudElement setElementLevel() {
        return new HudElementLevelDefault();
    }

    @Override
    public HudElement setElementFood() {
        return new HudElementFoodDefault();
    }

    @Override
    public HudElement setElementHealth() {
        return new HudElementHealthDefault();
    }

    @Override
    public HudElement setElementHealthMount() {
        return new HudElementHealthMountDefault();
    }

    @Override
    public HudElement setElementJumpBar() {
        return new HudElementJumpBarDefault();
    }

    @Override
    public HudElement setElementHotbar() {
        return new HudElementHotbarDefault();
    }

    @Override
    public HudElement setElementWidget() {
        return new HudElementWidgetDefault();
    }
}
