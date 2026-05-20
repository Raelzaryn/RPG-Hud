package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.TextAlignment;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.resources.WaypointStyle;
import net.minecraft.client.waypoints.ClientWaypointManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.waypoints.TrackedWaypoint;
import net.minecraft.world.waypoints.Waypoint;
import net.minecraft.world.waypoints.WaypointManager;
import net.minecraft.world.waypoints.WaypointStyleAsset;
import net.spellcraftgaming.rpghud.RPGHudUtils;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(value=EnvType.CLIENT)
public class HudElementCompassVanilla extends HudElement {

	private Minecraft client;
	private static final Identifier ARROW_UP = Identifier.withDefaultNamespace("hud/locator_bar_arrow_up");
	private static final Identifier ARROW_DOWN = Identifier.withDefaultNamespace("hud/locator_bar_arrow_down");
	
	public HudElementCompassVanilla() {
		super(HudElementType.COMPASS, 0, 0, 0, 0, true);
		client = Minecraft.getInstance();
	}

	@Override
	public boolean checkConditions() {
		return this.settings.getBoolValue(Settings.enable_compass) && (this.settings.getBoolValue(Settings.enable_immersive_compass) ? this.mc.player.getInventory().contains(new ItemStack(Items.COMPASS)) : true);
	}

	@Override
	public void drawElement(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, int scaledWidth, int scaledHeight) {
		int width = scaledWidth / 2 + this.settings.getPositionValue(Settings.compass_position)[0];
		int posY = this.settings.getPositionValue(Settings.compass_position)[1];
		int swapSides = this.settings.getBoolValue(Settings.invert_compass) ? -1 : 1;
		int rotation = Math.round(((this.mc.player.yHeadRot % 360) / 360) * 200);
		if (rotation < 0)
			rotation = 200 + rotation;

		graphics.blit(RenderPipelines.GUI_TEXTURED, INTERFACE, width - 56, posY, 34, 234, 112, 9, 256, 256);
		if(this.settings.getBoolValue(Settings.show_locator)) renderLocator(graphics, deltaTracker, width, posY +2);
		if (rotation > 0 && rotation <= 100) {
			graphics.centeredText(this.mc.font, "W", width + (50 * swapSides) - (rotation * swapSides), posY + 1, -1);
		}

		if (rotation > 25 && rotation <= 125) {
			graphics.centeredText(this.mc.font, ".", width + (75 * swapSides) - (rotation * swapSides), posY - 2, -1);
		}

		if (rotation > 50 && rotation <= 150) {
			graphics.centeredText(this.mc.font, "N", width + (100 * swapSides) - (rotation * swapSides), posY + 1, this.settings.getBoolValue(Settings.enable_compass_color) ? 0xFFE60909 : -1);
		}

		if (rotation > 75 && rotation <= 175) {
			graphics.centeredText(this.mc.font, ".", width + (125 * swapSides) - (rotation * swapSides), posY - 2, -1);
		}

		if (rotation > 100 && rotation <= 200) {
			graphics.centeredText(this.mc.font, "E", width + (150 * swapSides) - (rotation * swapSides), posY + 1, -1);
		}

		if (rotation >= 125) {
			graphics.centeredText(this.mc.font, ".", width + (175 * swapSides) - (rotation * swapSides), posY - 2, -1);
		} else if (rotation <= 25) {
			graphics.centeredText(this.mc.font, ".", width - (25 * swapSides) - (rotation * swapSides), posY - 2, -1);
		}

		if (rotation >= 150) {
			graphics.centeredText(this.mc.font, "S", width + (200 * swapSides) - (rotation * swapSides), posY + 1, -1);
		} else if (rotation <= 50) {
			graphics.centeredText(this.mc.font, "S", width - (rotation * swapSides), posY + 1, -1);
		}

		if (rotation >= 175) {
			graphics.centeredText(this.mc.font, ".", width + (225 * swapSides) - (rotation * swapSides), posY - 2, -1);
		} else if (rotation <= 75) {
			graphics.centeredText(this.mc.font, ".", width + (25 * swapSides) - (rotation * swapSides), posY - 2, -1);
		}

		if (this.settings.getBoolValue(Settings.enable_compass_coordinates)) {
			if (this.settings.getBoolValue(Settings.reduce_size))
				graphics.pose().scale(0.5f, 0.5f);
			int[] pos = getPlayerPos();
			graphics.text(this.mc.font, String.valueOf(pos[0]), (width - 50) * (this.settings.getBoolValue(Settings.reduce_size) ? 2 : 1), (posY + 11) * (this.settings.getBoolValue(Settings.reduce_size) ? 2 : 1), -1);
			graphics.centeredText(this.mc.font, String.valueOf(pos[1]), width * (this.settings.getBoolValue(Settings.reduce_size) ? 2 : 1), (posY + 11) * (this.settings.getBoolValue(Settings.reduce_size) ? 2 : 1), -1);
			graphics.text(this.mc.font, String.valueOf(pos[2]), (width + 50) * (this.settings.getBoolValue(Settings.reduce_size) ? 2 : 1) - mc.font.width(String.valueOf(pos[2])), (posY + 11) * (this.settings.getBoolValue(Settings.reduce_size) ? 2 : 1), -1);
			if (this.settings.getBoolValue(Settings.reduce_size))
				graphics.pose().scale(2f, 2f);
		}
	}

	public static int[] getPlayerPos() {
		Minecraft mc = Minecraft.getInstance();
		int[] pos = new int[3];
		pos[0] = (int) mc.player.getX();
		pos[1] = (int) mc.player.getY();
		pos[2] = (int) mc.player.getZ();
		return pos;
	}
	
	public void renderLocator(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, int posX, int posY) {
		Level level = this.mc.level;
		this.mc.player.connection.getWaypointManager().forEachWaypoint(this.client.getCameraEntity(), waypoint -> {
			if (!(Boolean)waypoint.id().left().map(uuid -> uuid.equals(this.client.getCameraEntity().getUUID())).orElse(false)) {
				double d = waypoint.yawAngleToCamera(level, this.client.gameRenderer.getMainCamera(), entity -> deltaTracker.getGameTimeDeltaPartialTick(false)) / 1.25;
				if (!(d <= -61.0) && !(d > 60.0)) {
					int j = Mth.ceil((this.client.getWindow().getGuiScaledWidth() - 9) / 2.0F);
					this.client.getWaypointStyles().get(waypoint.icon().style);
					WaypointStyle waypointStyleAsset = this.client.getWaypointStyles().get(waypoint.icon().style);
					float f = Mth.sqrt((float)waypoint.distanceSquared(this.client.getCameraEntity()));
					Identifier identifier = waypointStyleAsset.sprite(f);

					int k = waypoint.icon().color
							.orElseGet(
									() -> waypoint.id()
											.map(
													uuid -> ARGB.setBrightness(ARGB.color(255, uuid.hashCode()), 0.9F),
													name -> ARGB.setBrightness(ARGB.color(255, name.hashCode()), 0.9F)
											)
							);
					int l = (int)(d * 100.0 / 2.0 / 60.0);
					graphics.blitSprite(RenderPipelines.GUI_TEXTURED, identifier, j + l, posY - 2, 9, 9, k);
					TrackedWaypoint.PitchDirection pitch = waypoint.pitchDirectionToCamera(level, this.client.gameRenderer, entity -> deltaTracker.getGameTimeDeltaPartialTick(false));
					if (pitch != TrackedWaypoint.PitchDirection.NONE) {
						int m;
						Identifier identifier2;
						if (pitch == TrackedWaypoint.PitchDirection.DOWN) {
							m = 6;
							identifier2 = ARROW_DOWN;
						} else {
							m = 6;
							identifier2 = ARROW_UP;
						}

						graphics.blitSprite(RenderPipelines.GUI_TEXTURED, identifier2, j + l + 1, posY + m, 7, 5);
					}
				}
			}
		});
	}
}
