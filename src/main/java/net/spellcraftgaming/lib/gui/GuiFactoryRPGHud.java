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

	public boolean hasConfigGui() {
		// TODO Auto-generated method stub
		return true;
	}

	public GuiScreen createConfigGui(GuiScreen parentScreen) {
		// TODO Auto-generated method stub
		return new GuiSettingsMod(parentScreen);
	}
	
	@SuppressWarnings("deprecation")
	public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
		return null;
	}
	
	public Class<? extends GuiScreen> mainConfigGuiClass() {
		return GuiSettingsMod.class;
	}

}
