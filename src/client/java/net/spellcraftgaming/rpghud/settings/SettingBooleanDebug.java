package net.spellcraftgaming.rpghud.settings;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.resources.language.I18n;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

@Environment(value=EnvType.CLIENT)
public class SettingBooleanDebug extends SettingBoolean{

	public static final String force_render = "force_render";
	public static final String render_vanilla = "render_vanilla";
	public static final String prevent_event = "prevent_event";
	public static final String prevent_element_render = "prevent_element_render";
	
	public SettingBooleanDebug(String ID, HudElementType type, boolean defaultValue) {
		super(ID, type, defaultValue);
	}
	
	public String getName() {
		if(this.ID.contains(prevent_event))
			return I18n.get("name." + prevent_event);
		else if(this.ID.contains(prevent_element_render))
			return I18n.get("name." + prevent_element_render);
		else if(this.ID.contains(render_vanilla))
			return I18n.get("name." + render_vanilla);
		else if(this.ID.contains(force_render))
			return I18n.get("name." + force_render);
		else return I18n.get("name." + this.ID + "error");
	}

	public String getTooltip() {
		if(this.ID.contains(prevent_event))
			return I18n.get("tooltip." + prevent_event);
		else if(this.ID.contains(prevent_element_render))
			return I18n.get("tooltip." + prevent_element_render);
		else if(this.ID.contains(render_vanilla))
			return I18n.get("tooltip." + render_vanilla);
		else if(this.ID.contains(force_render))
			return I18n.get("tooltip." + force_render);
		else return I18n.get("tooltip." + this.ID + "error");
	}
	
	public String getFormatedTooltip() {
		if(this.ID.contains(prevent_event))
			return I18n.get("tooltip." + prevent_event).replace("/n", " ");
		else if(this.ID.contains(prevent_element_render))
			return I18n.get("tooltip." + prevent_element_render).replace("/n", " ");
		else if(this.ID.contains(render_vanilla))
			return I18n.get("tooltip." + render_vanilla).replace("/n", " ");
		else if(this.ID.contains(force_render))
			return I18n.get("tooltip." + force_render).replace("/n", " ");
		else return I18n.get("tooltip." + this.ID + "error").replace("/n", " ");
	}
}
