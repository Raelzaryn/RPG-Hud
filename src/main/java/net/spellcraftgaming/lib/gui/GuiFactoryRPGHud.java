package net.spellcraftgaming.lib.gui;

import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.spellcraftgaming.rpghud.gui.GuiSettingsMod;

public class GuiFactoryRPGHud implements IModGuiFactory {

    @Override
    public void initialize(Minecraft minecraftInstance) {
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return null;
    }

    @Override
    public boolean hasConfigGui() {
        return true;
    }

    @Override
    public GuiScreen createConfigGui(GuiScreen parentScreen) {
        return new GuiSettingsMod(parentScreen);
    }

    public Class<? extends GuiScreen> mainConfigGuiClass() {
        return GuiSettingsMod.class;
    }

}
