package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import java.util.Map;
import java.util.function.Supplier;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.ImmutableMap;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.bar.Bar;
import net.minecraft.client.gui.hud.bar.ExperienceBar;
import net.minecraft.client.gui.hud.bar.JumpBar;
import net.minecraft.client.gui.hud.bar.LocatorBar;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.JumpingMount;
import net.minecraft.util.Nullables;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementLocatorBarVanilla extends HudElement{

	private final Map<BarType, Supplier<Bar>> bars;
	private Pair<BarType, Bar> currentBar = Pair.of(BarType.EMPTY, Bar.EMPTY);
	private MinecraftClient client;
	
	public HudElementLocatorBarVanilla() {
		super(HudElementType.EXPERIENCE, 0, 0, 0, 0, false);
		client = MinecraftClient.getInstance();
		this.bars = ImmutableMap.of(
				BarType.EMPTY,
				() -> Bar.EMPTY,
				BarType.EXPERIENCE,
				() -> new ExperienceBar(client),
				BarType.LOCATOR,
				() -> new LocatorBar(client),
				BarType.JUMPABLE_VEHICLE,
				() -> new JumpBar(client)
			);
	}

	@Override
	public void drawElement(DrawContext dc, float zLevel, RenderTickCounter partialTicks, int scaledWidth,
			int scaledHeight) {
		BarType barType = this.getCurrentBarType();
		if (barType != this.currentBar.getKey()) {
			this.currentBar = Pair.of(barType, (Bar)((Supplier<Bar>)this.bars.get(barType)).get());
		}
		
		this.currentBar.getValue().renderBar(dc, partialTicks);
		if (this.client.interactionManager.hasExperienceBar() && this.client.player.experienceLevel > 0) {
			Bar.drawExperienceLevel(dc, this.client.textRenderer, this.client.player.experienceLevel);
		}

		this.currentBar.getValue().renderAddons(dc, partialTicks);
		
	}

	private BarType getCurrentBarType() {
		boolean bl = this.client.player.networkHandler.getWaypointHandler().hasWaypoint();
		boolean bl2 = this.client.player.getJumpingMount() != null;
		boolean bl3 = this.client.interactionManager.hasExperienceBar();
		if (bl) {
			if (bl2 && this.shouldShowJumpBar()) {
				return BarType.JUMPABLE_VEHICLE;
			} else {
				if(this.settings.getBoolValue(Settings.show_locator)) return BarType.EXPERIENCE;
				return bl3 && this.shouldShowExperienceBar() ? BarType.EXPERIENCE : BarType.LOCATOR;
			}
		} else if (bl2) {
			return BarType.JUMPABLE_VEHICLE;
		} else {
			return bl3 ? BarType.EXPERIENCE : BarType.EMPTY;
		}
	}
	
	private boolean shouldShowExperienceBar() {
		return this.client.player.experienceBarDisplayStartTime + 100 > this.client.player.age;
	}
	
	private boolean shouldShowJumpBar() {
		return this.client.player.getMountJumpStrength() > 0.0F
			|| (Integer)Nullables.mapOrElse(this.client.player.getJumpingMount(), JumpingMount::getJumpCooldown, 0) > 0;
	}
	
	@Environment(EnvType.CLIENT)
	static enum BarType {
		EMPTY,
		EXPERIENCE,
		LOCATOR,
		JUMPABLE_VEHICLE;
	}
	
	@Override
	public boolean checkConditions() {
		return this.mc.interactionManager.hasStatusBars();
	}
}
