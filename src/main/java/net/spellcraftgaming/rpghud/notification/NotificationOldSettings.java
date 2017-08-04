package net.spellcraftgaming.rpghud.notification;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.rpghud.settings.Settings;

public class NotificationOldSettings extends Notification {

	private Settings settings;

	public NotificationOldSettings(Settings settings) {
		this.settings = settings;
		GuiScreen screen = Minecraft.getMinecraft().currentScreen;
		this.buttons.add(new NotificationButton(0, screen.width / 2 - 105, screen.height / 2 + 70 - 24, "Convert Settings"));
		this.buttons.add(new NotificationButton(1, screen.width / 2 + 5, screen.height / 2 + 70 - 24, "Don't show again"));
	}

	@Override
	public void draw(GuiScreen screen) {
		super.draw(screen);
		FontRenderer font = GameData.getFontRenderer();
		screen.drawCenteredString(font, "Old Settings", screen.width / 2, screen.height / 2 - 68, 0xFFD700);

		screen.drawString(font, "RPG-HUD has found the old settings files.", screen.width / 2 - 115, screen.height / 2 - 70 + 20, -1);
		screen.drawString(font, "Do you want to convert them to the new", screen.width / 2 - 115, screen.height / 2 - 70 + 30, -1);
		screen.drawString(font, "setting file system?", screen.width / 2 - 115, screen.height / 2 - 70 + 40, -1);
		screen.drawString(font, "The files will be deleted in the process!", screen.width / 2 - 115, screen.height / 2 - 70 + 50, -1);
		screen.drawString(font, "(You might want to make a backup of them)", screen.width / 2 - 115, screen.height / 2 - 70 + 60, -1);
		screen.drawString(font, "Affected files:", screen.width / 2 - 115, screen.height / 2 - 70 + 75, -1);

		int offset = 0;
		if (new File(Minecraft.getMinecraft().mcDataDir, "RPGHud_settings.txt").exists()) {
			screen.drawString(font, "- RPGHud_settings.txt", screen.width / 2 - 115, screen.height / 2 - 70 + 85, -1);
			offset = 10;
		}
		if (new File(Minecraft.getMinecraft().mcDataDir, "RPGHud_settings_Debug.txt").exists()) {
			screen.drawString(font, "- RPGHud_settings_debug.txt", screen.width / 2 - 115, screen.height / 2 - 70 + 85 + offset, -1);
		}
	}

	@Override
	public void performAction(NotificationButton button) {
		super.performAction(button);
		if (button.id == 0) {
			processSettings();
			this.shouldDestroy = true;
		} else if (button.id == 1) {
			this.shouldDestroy = true;
			this.settings.setSetting(Settings.show_convert_notification, false);
		}
	}

	public void processSettings() {
		if (new File(Minecraft.getMinecraft().mcDataDir, "RPGHud_settings.txt").exists()) {
			this.convertSettings();
			(new File(Minecraft.getMinecraft().mcDataDir, "RPGHud_settings.txt")).delete();
		}
		if (new File(Minecraft.getMinecraft().mcDataDir, "RPGHud_settings_debug.txt").exists()) {
			this.convertSettingsDebug();
			(new File(Minecraft.getMinecraft().mcDataDir, "RPGHud_settings_debug.txt")).delete();
		}
		this.settings.saveSettings();
	}

	private void convertSettings() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader((new File(Minecraft.getMinecraft().mcDataDir, "RPGHud_settings.txt"))));
			String s = "";
			while ((s = reader.readLine()) != null) {
				try {
					String[] string = s.split(":");
					if (string[0].equals("button_tooltip_enabled")) {
						this.settings.setSetting(Settings.enable_button_tooltip, string[1].equals("true"));
					}
					if (string[0].equals("color_health")) {
						if (string[1].startsWith("#")) {
							this.settings.setSetting(Settings.color_health, Integer.parseInt(string[1].replace("#", ""), 16));
						}
					}
					if (string[0].equals("color_stamina")) {
						if (string[1].startsWith("#")) {
							this.settings.setSetting(Settings.color_food, Integer.parseInt(string[1].replace("#", ""), 16));
						}
					}
					if (string[0].equals("color_air")) {
						if (string[1].startsWith("#")) {
							this.settings.setSetting(Settings.color_air, Integer.parseInt(string[1].replace("#", ""), 16));
						}
					}
					if (string[0].equals("color_experience")) {
						if (string[1].startsWith("#")) {
							this.settings.setSetting(Settings.color_experience, Integer.parseInt(string[1].replace("#", ""), 16));
						}
					}
					if (string[0].equals("color_jumpbar")) {
						if (string[1].startsWith("#")) {
							this.settings.setSetting(Settings.color_jump_bar, Integer.parseInt(string[1].replace("#", ""), 16));
						}
					}
					if (string[0].equals("color_poison")) {
						this.settings.setSetting(Settings.color_poison, Integer.parseInt(string[1].replace("#", ""), 16));
					}
					if (string[0].equals("color_hunger")) {
						this.settings.setSetting(Settings.color_hunger, Integer.parseInt(string[1].replace("#", ""), 16));
					}
					if (string[0].equals("color_absorption")) {
						this.settings.setSetting(Settings.color_absorption, Integer.parseInt(string[1].replace("#", ""), 16));
					}
					if (string[0].equals("color_wither")) {
						this.settings.setSetting(Settings.color_wither, Integer.parseInt(string[1].replace("#", ""), 16));
					}
					if (string[0].equals("clock_time_format")) {
						if (Integer.parseInt(string[1]) == 0) {
							this.settings.setSetting(Settings.clock_time_format, "time.24");
						} else {
							this.settings.setSetting(Settings.clock_time_format, "time.12");
						}
					}
					if (string[0].equals("hud_type")) {
						this.settings.setSetting(Settings.hud_type, string[1]);
					}
					if (string[0].equals("show_armor")) {
						this.settings.setSetting(Settings.show_armor, string[1].equals("true"));
					}
					if (string[0].equals("show_blockcount")) {
						this.settings.setSetting(Settings.show_block_count, string[1].equals("true"));
					}
					if (string[0].equals("show_arrowcount")) {
						this.settings.setSetting(Settings.show_arrow_count, string[1].equals("true"));
					}
					if (string[0].equals("show_itemdurability")) {
						this.settings.setSetting(Settings.show_item_durability, string[1].equals("true"));
					}
					if (string[0].equals("enable_clock")) {
						this.settings.setSetting(Settings.enable_clock, string[1].equals("true"));
					}
					if (string[0].equals("enable_clock_color")) {
						this.settings.setSetting(Settings.enable_clock_color, string[1].equals("true"));
					}
					if (string[0].equals("enable_immersive_clock")) {
						this.settings.setSetting(Settings.enable_immersive_clock, string[1].equals("true"));
					}
					if (string[0].equals("enable_compass")) {
						this.settings.setSetting(Settings.enable_compass, string[1].equals("true"));
					}
					if (string[0].equals("enable_compass_color")) {
						this.settings.setSetting(Settings.enable_compass_color, string[1].equals("true"));
					}
					if (string[0].equals("enable_immersive_compass")) {
						this.settings.setSetting(Settings.enable_immersive_compass, string[1].equals("true"));
					}
					if (string[0].equals("render_player_face")) {
						this.settings.setSetting(Settings.render_player_face, string[1].equals("true"));
					}
					if (string[0].equals("show_numbers_health")) {
						this.settings.setSetting(Settings.show_numbers_health, string[1].equals("true"));
					}
					if (string[0].equals("show_numbers_stamina")) {
						this.settings.setSetting(Settings.show_numbers_food, string[1].equals("true"));
					}
					if (string[0].equals("show_numbers_experience")) {
						this.settings.setSetting(Settings.show_numbers_experience, string[1].equals("true"));
					}
					if (string[0].equals("show_hunger_preview")) {
						this.settings.setSetting(Settings.show_hunger_preview, string[1].equals("true"));
					}
					if (string[0].equals("reduce_size")) {
						this.settings.setSetting(Settings.reduce_size, string[1].equals("true"));
					}
					if (string[0].equals("enable_pickup")) {
						this.settings.setSetting(Settings.enable_pickup, string[1].equals("true"));
					}
					if (string[0].equals("pickup_duration")) {
						this.settings.setSetting(Settings.pickup_duration, Float.parseFloat(string[1]));
					}
					if (string[0].equals("limit_jumpbar")) {
						this.settings.setSetting(Settings.limit_jump_bar, string[1].equals("true"));
					}
					if (string[0].equals("invert_compass")) {
						this.settings.setSetting(Settings.invert_compass, string[1].equals("true"));
					}
					if (string[0].equals("enable_entity_inspect")) {
						this.settings.setSetting(Settings.enable_entity_inspect, string[1].equals("true"));
					}
					if (string[0].equals("enable_compass_coordinates")) {
						this.settings.setSetting(Settings.enable_compass_coordinates, string[1].equals("true"));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void convertSettingsDebug() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader((new File(Minecraft.getMinecraft().mcDataDir, "RPGHud_settings_debug.txt"))));
			String s = "";
			while ((s = reader.readLine()) != null) {
				try {
					String[] string = s.split(":");
					if (string[0].equals("forceRenderCrosshair")) {
						this.settings.setSetting(Settings.force_render + "_crosshair", string[1].equals("true"));
					}
					if (string[0].equals("renderVanillaCrosshair")) {
						this.settings.setSetting(Settings.render_vanilla + "_crosshair", string[1].equals("true"));
					}
					if (string[0].equals("preventEventCrosshair")) {
						this.settings.setSetting(Settings.prevent_event + "_crosshair", string[1].equals("true"));
					}
					if (string[0].equals("preventElementRenderCrosshair")) {
						this.settings.setSetting(Settings.prevent_element_render + "_crosshair", string[1].equals("true"));
					}

					if (string[0].equals("forceRenderArmor")) {
						this.settings.setSetting(Settings.force_render + "_armor", string[1].equals("true"));
					}
					if (string[0].equals("renderVanillaArmor")) {
						this.settings.setSetting(Settings.render_vanilla + "_armor", string[1].equals("true"));
					}
					if (string[0].equals("preventEventArmor")) {
						this.settings.setSetting(Settings.prevent_event + "_armor", string[1].equals("true"));
					}
					if (string[0].equals("preventElementRenderArmor")) {
						this.settings.setSetting(Settings.prevent_element_render + "_armor", string[1].equals("true"));
					}

					if (string[0].equals("forceRenderHotbar")) {
						this.settings.setSetting(Settings.force_render + "_hotbar", string[1].equals("true"));
					}
					if (string[0].equals("renderVanillaHotbar")) {
						this.settings.setSetting(Settings.render_vanilla + "_hotbar", string[1].equals("true"));
					}
					if (string[0].equals("preventEventHotbar")) {
						this.settings.setSetting(Settings.prevent_event + "_hotbar", string[1].equals("true"));
					}
					if (string[0].equals("preventElementRenderHotbar")) {
						this.settings.setSetting(Settings.prevent_element_render + "_hotbar", string[1].equals("true"));
					}

					if (string[0].equals("forceRenderAir")) {
						this.settings.setSetting(Settings.force_render + "_air", string[1].equals("true"));
					}
					if (string[0].equals("renderVanillaAir")) {
						this.settings.setSetting(Settings.render_vanilla + "_air", string[1].equals("true"));
					}
					if (string[0].equals("preventEventAir")) {
						this.settings.setSetting(Settings.prevent_event + "_air", string[1].equals("true"));
					}
					if (string[0].equals("preventElementRenderAir")) {
						this.settings.setSetting(Settings.prevent_element_render + "_air", string[1].equals("true"));
					}

					if (string[0].equals("forceRenderHealth")) {
						this.settings.setSetting(Settings.force_render + "_health", string[1].equals("true"));
					}
					if (string[0].equals("renderVanillaHealth")) {
						this.settings.setSetting(Settings.render_vanilla + "_health", string[1].equals("true"));
					}
					if (string[0].equals("preventEventHealth")) {
						this.settings.setSetting(Settings.prevent_event + "_health", string[1].equals("true"));
					}
					if (string[0].equals("preventElementRenderHealth")) {
						this.settings.setSetting(Settings.prevent_element_render + "_health", string[1].equals("true"));
					}

					if (string[0].equals("forceRenderFood")) {
						this.settings.setSetting(Settings.force_render + "_food", string[1].equals("true"));
					}
					if (string[0].equals("renderVanillaFood")) {
						this.settings.setSetting(Settings.render_vanilla + "_food", string[1].equals("true"));
					}
					if (string[0].equals("preventEventFood")) {
						this.settings.setSetting(Settings.prevent_event + "_food", string[1].equals("true"));
					}
					if (string[0].equals("preventElementRenderFood")) {
						this.settings.setSetting(Settings.prevent_element_render + "_food", string[1].equals("true"));
					}

					if (string[0].equals("forceRenderExp")) {
						this.settings.setSetting(Settings.force_render + "_experience", string[1].equals("true"));
					}
					if (string[0].equals("renderVanillaExp")) {
						this.settings.setSetting(Settings.render_vanilla + "_experience", string[1].equals("true"));
					}
					if (string[0].equals("preventEventExp")) {
						this.settings.setSetting(Settings.prevent_event + "_experience", string[1].equals("true"));
					}
					if (string[0].equals("preventElementRenderExp")) {
						this.settings.setSetting(Settings.prevent_element_render + "_experience", string[1].equals("true"));
					}

					if (string[0].equals("forceRenderExpLv")) {
						this.settings.setSetting(Settings.force_render + "_level", string[1].equals("true"));
					}
					if (string[0].equals("renderVanillaExpLv")) {
						this.settings.setSetting(Settings.render_vanilla + "_level", string[1].equals("true"));
					}
					if (string[0].equals("preventEventExpLv")) {
						this.settings.setSetting(Settings.prevent_event + "_level", string[1].equals("true"));
					}
					if (string[0].equals("preventElementRenderExpLv")) {
						this.settings.setSetting(Settings.prevent_element_render + "_level", string[1].equals("true"));
					}

					if (string[0].equals("forceRenderHealthMount")) {
						this.settings.setSetting(Settings.force_render + "_health_mount", string[1].equals("true"));
					}
					if (string[0].equals("renderVanillaHealthMount")) {
						this.settings.setSetting(Settings.render_vanilla + "_health_mount", string[1].equals("true"));
					}
					if (string[0].equals("preventEventHealthMount")) {
						this.settings.setSetting(Settings.prevent_event + "_health_mount", string[1].equals("true"));
					}
					if (string[0].equals("preventElementRenderHealthMount")) {
						this.settings.setSetting(Settings.prevent_element_render + "_health_mount", string[1].equals("true"));
					}

					if (string[0].equals("forceRenderJumpBar")) {
						this.settings.setSetting(Settings.force_render + "_jump_bar", string[1].equals("true"));
					}
					if (string[0].equals("renderVanillaJumpBar")) {
						this.settings.setSetting(Settings.render_vanilla + "_jump_bar", string[1].equals("true"));
					}
					if (string[0].equals("preventEventJumpBar")) {
						this.settings.setSetting(Settings.prevent_event + "_jump_bar", string[1].equals("true"));
					}
					if (string[0].equals("preventElementRenderJumpBar")) {
						this.settings.setSetting(Settings.prevent_element_render + "_jump_bar", string[1].equals("true"));
					}

					if (string[0].equals("forceRenderChat")) {
						this.settings.setSetting(Settings.force_render + "_chat", string[1].equals("true"));
					}
					if (string[0].equals("renderVanillaChat")) {
						this.settings.setSetting(Settings.render_vanilla + "_chat", string[1].equals("true"));
					}
					if (string[0].equals("preventEventChat")) {
						this.settings.setSetting(Settings.prevent_event + "_chat", string[1].equals("true"));
					}
					if (string[0].equals("preventElementRenderChat")) {
						this.settings.setSetting(Settings.prevent_element_render + "_chat", string[1].equals("true"));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
