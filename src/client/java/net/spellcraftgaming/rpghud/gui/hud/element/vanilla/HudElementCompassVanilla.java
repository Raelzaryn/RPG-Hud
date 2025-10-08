package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.resource.waypoint.WaypointStyleAsset;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.waypoint.TrackedWaypoint.Pitch;
import net.minecraft.world.waypoint.Waypoint.Config;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(value=EnvType.CLIENT)
public class HudElementCompassVanilla extends HudElement {

	private MinecraftClient client;
	private static final Identifier ARROW_UP = Identifier.ofVanilla("hud/locator_bar_arrow_up");
	private static final Identifier ARROW_DOWN = Identifier.ofVanilla("hud/locator_bar_arrow_down");
	
	public HudElementCompassVanilla() {
		super(HudElementType.COMPASS, 0, 0, 0, 0, true);
		client = MinecraftClient.getInstance();
	}

	@Override
	public boolean checkConditions() {
		return this.settings.getBoolValue(Settings.enable_compass) && (this.settings.getBoolValue(Settings.enable_immersive_compass) ? this.mc.player.getInventory().contains(new ItemStack(Items.COMPASS)) : true);
	}

	@Override
	public void drawElement(DrawContext dc, float zLevel, RenderTickCounter partialTicks, int scaledWidth, int scaledHeight) {
		int width = scaledWidth / 2 + this.settings.getPositionValue(Settings.compass_position)[0];
		int posY = this.settings.getPositionValue(Settings.compass_position)[1];
		int swapSides = this.settings.getBoolValue(Settings.invert_compass) ? -1 : 1;
		int rotation = Math.round(((this.mc.player.headYaw % 360) / 360) * 200);
		if (rotation < 0)
			rotation = 200 + rotation;

		dc.drawTexture(RenderPipelines.GUI_TEXTURED, INTERFACE, width - 56, posY, 34, 234, 112, 9, 256, 256);
		if(this.settings.getBoolValue(Settings.show_locator)) renderLocator(dc, partialTicks, width, posY +2);
		if (rotation > 0 && rotation <= 100) {
			dc.drawCenteredTextWithShadow( this.mc.textRenderer, "W", width + (50 * swapSides) - (rotation * swapSides), posY + 1, -1);
		}

		if (rotation > 25 && rotation <= 125) {
			dc.drawCenteredTextWithShadow( this.mc.textRenderer, ".", width + (75 * swapSides) - (rotation * swapSides), posY - 2, -1);
		}

		if (rotation > 50 && rotation <= 150) {
			dc.drawCenteredTextWithShadow( this.mc.textRenderer, "N", width + (100 * swapSides) - (rotation * swapSides), posY + 1, this.settings.getBoolValue(Settings.enable_compass_color) ? 0xFFE60909 : -1);
		}

		if (rotation > 75 && rotation <= 175) {
			dc.drawCenteredTextWithShadow( this.mc.textRenderer, ".", width + (125 * swapSides) - (rotation * swapSides), posY - 2, -1);
		}

		if (rotation > 100 && rotation <= 200) {
			dc.drawCenteredTextWithShadow( this.mc.textRenderer, "E", width + (150 * swapSides) - (rotation * swapSides), posY + 1, -1);
		}

		if (rotation >= 125) {
			dc.drawCenteredTextWithShadow( this.mc.textRenderer, ".", width + (175 * swapSides) - (rotation * swapSides), posY - 2, -1);
		} else if (rotation <= 25) {
			dc.drawCenteredTextWithShadow( this.mc.textRenderer, ".", width - (25 * swapSides) - (rotation * swapSides), posY - 2, -1);
		}

		if (rotation >= 150) {
			dc.drawCenteredTextWithShadow( this.mc.textRenderer, "S", width + (200 * swapSides) - (rotation * swapSides), posY + 1, -1);
		} else if (rotation <= 50) {
			dc.drawCenteredTextWithShadow( this.mc.textRenderer, "S", width - (rotation * swapSides), posY + 1, -1);
		}

		if (rotation >= 175) {
			dc.drawCenteredTextWithShadow( this.mc.textRenderer, ".", width + (225 * swapSides) - (rotation * swapSides), posY - 2, -1);
		} else if (rotation <= 75) {
			dc.drawCenteredTextWithShadow( this.mc.textRenderer, ".", width + (25 * swapSides) - (rotation * swapSides), posY - 2, -1);
		}

		if (this.settings.getBoolValue(Settings.enable_compass_coordinates)) {
			if (this.settings.getBoolValue(Settings.reduce_size))
				dc.getMatrices().scale(0.5f, 0.5f);
			int[] pos = getPlayerPos();
			dc.drawTextWithShadow(this.mc.textRenderer, String.valueOf(pos[0]), (width - 50) * (this.settings.getBoolValue(Settings.reduce_size) ? 2 : 1), (posY + 11) * (this.settings.getBoolValue(Settings.reduce_size) ? 2 : 1), -1);
			dc.drawCenteredTextWithShadow( this.mc.textRenderer, String.valueOf(pos[1]), width * (this.settings.getBoolValue(Settings.reduce_size) ? 2 : 1), (posY + 11) * (this.settings.getBoolValue(Settings.reduce_size) ? 2 : 1), -1);
			dc.drawTextWithShadow(this.mc.textRenderer, String.valueOf(pos[2]), (width + 50) * (this.settings.getBoolValue(Settings.reduce_size) ? 2 : 1) - mc.textRenderer.getWidth(String.valueOf(pos[2])), (posY + 11) * (this.settings.getBoolValue(Settings.reduce_size) ? 2 : 1), -1);
			if (this.settings.getBoolValue(Settings.reduce_size))
				dc.getMatrices().scale(2f, 2f);
		}
	}

	public static int[] getPlayerPos() {
		MinecraftClient mc = MinecraftClient.getInstance();
		int[] pos = new int[3];
		pos[0] = (int) mc.player.getX();
		pos[1] = (int) mc.player.getY();
		pos[2] = (int) mc.player.getZ();
		return pos;
	}
	
	public void renderLocator(DrawContext context, RenderTickCounter tickCounter, int posX, int posY) {
		World world = this.client.cameraEntity.getWorld();
		this.client
			.player
			.networkHandler
			.getWaypointHandler()
			.forEachWaypoint(
				this.client.cameraEntity,
				waypoint -> {
					if (!(Boolean)waypoint.getSource().left().map(uuid -> uuid.equals(this.client.cameraEntity.getUuid())).orElse(false)) {
						double d = waypoint.getRelativeYaw(world, this.client.gameRenderer.getCamera()) / 1.25;
						if (!(d <= -61.0) && !(d > 60.0)) {
							int j = MathHelper.ceil((context.getScaledWindowWidth() - 9) / 2.0F);
							Config config = waypoint.getConfig();
							WaypointStyleAsset waypointStyleAsset = this.client.getWaypointStyleAssetManager().get(config.style);
							float f = MathHelper.sqrt((float)waypoint.squaredDistanceTo(this.client.cameraEntity));
							Identifier identifier = waypointStyleAsset.getSpriteForDistance(f);
							int k = (Integer)config.color
								.orElseGet(
									() -> waypoint.getSource()
										.map(
											uuid -> ColorHelper.withBrightness(ColorHelper.withAlpha(255, uuid.hashCode()), 0.9F),
											name -> ColorHelper.withBrightness(ColorHelper.withAlpha(255, name.hashCode()), 0.9F)
										)
								);
							int l = (int)(d * 100.0 / 2.0 / 60.0);
							context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, identifier, j + l, posY - 2, 9, 9, k);
							Pitch pitch = waypoint.getPitch(world, this.client.gameRenderer);
							if (pitch != Pitch.NONE) {
								int m;
								Identifier identifier2;
								if (pitch == Pitch.DOWN) {
									m = 6;
									identifier2 = ARROW_DOWN;
								} else {
									m = 6;
									identifier2 = ARROW_UP;
								}

								context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, identifier2, j + l + 1, posY + m, 7, 5);
							}
						}
					}
				}
			);
	}
}
