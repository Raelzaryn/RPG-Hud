package net.spellcraftgaming.rpghud.main;

public class RPGHudCompatibility {

	public final boolean compatModMenu;
	public final boolean compatProspector;

	public RPGHudCompatibility() {
		this.compatModMenu = isClass("com.terraformersmc.modmenu.ModMenu");
		this.compatProspector = isClass("io.github.prospector.modmenu.ModMenu");
	}


	public static boolean isClass(String className) {
		try {
			Class.forName(className);
			return true;
		} catch(ClassNotFoundException e) {
			return false;
		}
	}
}
