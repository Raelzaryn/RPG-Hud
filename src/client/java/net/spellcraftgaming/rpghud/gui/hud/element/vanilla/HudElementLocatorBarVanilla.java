package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import java.util.Map;
import java.util.function.Supplier;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.ImmutableMap;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.Optionull;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.contextualbar.ContextualBarRenderer;
import net.minecraft.client.gui.contextualbar.ExperienceBarRenderer;
import net.minecraft.client.gui.contextualbar.JumpableVehicleBarRenderer;
import net.minecraft.client.gui.contextualbar.LocatorBarRenderer;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementLocatorBarVanilla extends HudElement{

	private Pair<BarType, ContextualBarRenderer> currentBar = Pair.of(BarType.EMPTY, ContextualBarRenderer.EMPTY);
	private final Map<BarType, Supplier<ContextualBarRenderer>> bars;
	
	public HudElementLocatorBarVanilla() {
		super(HudElementType.EXPERIENCE, 0, 0, 0, 0, false);

		this.bars = ImmutableMap.of(
				BarType.EMPTY,
				() -> ContextualBarRenderer.EMPTY,
				BarType.EXPERIENCE,
				() -> new ExperienceBarRenderer(mc),
				BarType.LOCATOR,
				() -> new LocatorBarRenderer(mc),
				BarType.JUMPABLE_VEHICLE,
				() -> new JumpableVehicleBarRenderer(mc)
			);
	}

	@Override
	public void drawElement(GuiGraphicsExtractor dc, float zLevel, DeltaTracker partialTicks, int scaledWidth,
			int scaledHeight) {
		BarType barType = this.getCurrentBarType();
		if (barType != this.currentBar.getKey()) {
			this.currentBar  = Pair.of(barType, (ContextualBarRenderer)((Supplier)this.bars.get(barType)).get());
		}
		
		this.currentBar.getValue().extractBackground(dc, partialTicks);
		if (this.mc.gameMode.hasExperience() && this.mc.player.experienceLevel > 0) {
			ContextualBarRenderer.extractExperienceLevel(dc, this.mc.font, this.mc.player.experienceLevel);
		}

		this.currentBar.getValue().extractRenderState(dc, partialTicks);
		
	}

	private BarType getCurrentBarType() {
		boolean bl = this.mc.player.connection.getWaypointManager().hasWaypoints();
		boolean bl2 = this.mc.player.jumpableVehicle() != null;
		boolean bl3 = this.mc.gameMode.hasExperience();
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
		return this.mc.player.experienceDisplayStartTick + 100 > this.mc.player.tickCount;
	}
	
	private boolean shouldShowJumpBar() {
		return this.mc.player.getJumpRidingScale() > 0.0F
				|| (Integer)Optionull.mapOrDefault(this.mc.player.jumpableVehicle(), PlayerRideableJumping::getJumpCooldown, 0) > 0;
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
