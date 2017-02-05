package net.spellcraftgaming.rpghud.gui;

import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.IModGuiFactory;

public class GuiFactoryRPGHud implements IModGuiFactory {

	@Override
	public void initialize(Minecraft minecraftInstance) {
	}

	/**
	 * Links to the Settings Gui of this mod. Is called by the Forge Mod Menu.
	 */
	@Override
	public Class<? extends GuiScreen> mainConfigGuiClass() {
		return GuiSettingsMod.class;
	}

	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
		return null;
	}

	@Override
	public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
		return null;
	}

}
