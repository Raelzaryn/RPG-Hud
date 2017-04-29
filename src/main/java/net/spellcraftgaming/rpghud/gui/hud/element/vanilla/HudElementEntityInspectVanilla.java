package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementEntityInspectVanilla extends HudElement {

	protected static final ResourceLocation DAMAGE_INDICATOR = new ResourceLocation("rpghud:textures/entityinspect.png");

	@Override
	public boolean checkConditions() {
		return GameData.shouldDrawHUD() && this.settings.getBoolValue(Settings.enable_entity_inspect);
	}

	public HudElementEntityInspectVanilla() {
		super(HudElementType.ENTITY_INSPECT, 0, 0, 0, 0, true);
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks, double scale) {
		EntityLiving focused = GameData.getFocusedEntity(GameData.getPlayer());
		if (focused != null) {
			ScaledResolution res = new ScaledResolution(this.mc);
			int width = res.getScaledWidth() / 2;
			this.mc.getTextureManager().bindTexture(DAMAGE_INDICATOR);
			gui.drawTexturedModalRect(width - 64, 20, 0, 0, 128, 36);
			drawCustomBar(width - 27, 34, 89, 8, (double) focused.getHealth() / (double) focused.getMaxHealth() * 100D, this.settings.getIntValue(Settings.color_health), offsetColorPercent(this.settings.getIntValue(Settings.color_health), OFFSET_PERCENT));
			String stringHealth = ((double) Math.round(focused.getHealth() * 10)) / 10 + "/" + ((double) Math.round(focused.getMaxHealth() * 10)) / 10;
			GlStateManager.scale(0.5, 0.5, 0.5);
			gui.drawCenteredString(this.mc.fontRendererObj, stringHealth, (width - 28 + 44) * 2, 36 * 2, -1);
			GlStateManager.scale(2.0, 2.0, 2.0);

			int x = (width - 28 + 44 - this.mc.fontRendererObj.getStringWidth(focused.getName()) / 2);
			int y = 25;
			this.mc.fontRendererObj.drawString(focused.getName(), x, y, 0x585858);

			drawEntityOnScreen(width - 60 + 14, 22 + 27, focused);
		}
	}

	public static void drawEntityOnScreen(int posX, int posY, EntityLivingBase ent) {
		GlStateManager.enableColorMaterial();
		GlStateManager.pushMatrix();
		int scale = (int) (17 / ent.height);
		GlStateManager.translate((float) posX, (float) posY, 50.0F);
		GlStateManager.scale((float) (-scale), (float) scale, (float) scale);
		GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
		float f = ent.renderYawOffset;
		float f1 = ent.rotationYaw;
		float f2 = ent.rotationPitch;
		float f3 = ent.prevRotationYawHead;
		float f4 = ent.rotationYawHead;
		GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);

		GlStateManager.rotate(-((float) Math.atan((double) (0 / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
		ent.renderYawOffset = (float) Math.atan((double) (100 / 40.0F)) * 20.0F;
		ent.rotationYaw = (float) Math.atan((double) (25 / 40.0F)) * 40.0F;
		ent.rotationPitch = -((float) Math.atan((double) (0 / 40.0F))) * 20.0F;
		ent.rotationYawHead = ent.rotationYaw;
		ent.prevRotationYawHead = ent.rotationYaw;
		GlStateManager.translate(0.0F, 0.0F, 0.0F);
		RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
		rendermanager.setPlayerViewY(180.0F);
		rendermanager.setRenderShadow(false);
		rendermanager.doRenderEntity(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
		rendermanager.setRenderShadow(true);
		ent.renderYawOffset = f;
		ent.rotationYaw = f1;
		ent.rotationPitch = f2;
		ent.prevRotationYawHead = f3;
		ent.rotationYawHead = f4;
		GlStateManager.popMatrix();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableRescaleNormal();
		GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GlStateManager.disableTexture2D();
		GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}
}
