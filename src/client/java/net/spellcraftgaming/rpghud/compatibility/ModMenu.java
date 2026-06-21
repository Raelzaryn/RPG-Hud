package net.spellcraftgaming.rpghud.compatibility;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.spellcraftgaming.rpghud.gui.GuiSettingsMod;

public class ModMenu implements ModMenuApi {

	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return GuiSettingsMod::new;
	}
}
