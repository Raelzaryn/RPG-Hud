package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySquid;
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
    public void drawElement(Gui gui, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        EntityLiving focused = GameData.getFocusedEntity(GameData.getPlayer());
        if (focused != null) {
            int posX = (scaledWidth / 2) + this.settings.getPositionValue(Settings.inspector_position)[0];
            int posY = this.settings.getPositionValue(Settings.inspector_position)[1];
            this.mc.getTextureManager().bindTexture(DAMAGE_INDICATOR);
            gui.drawTexturedModalRect(posX - 62, 20 + posY, 0, 0, 128, 36);
            drawCustomBar(posX - 25, 34 + posY, 89, 8, (double) focused.getHealth() / (double) focused.getMaxHealth() * 100D,
                    this.settings.getIntValue(Settings.color_health), offsetColorPercent(this.settings.getIntValue(Settings.color_health), OFFSET_PERCENT));
            String stringHealth = ((double) Math.round(focused.getHealth() * 10)) / 10 + "/" + ((double) Math.round(focused.getMaxHealth() * 10)) / 10;
            GlStateManager.scale(0.5, 0.5, 0.5);
            gui.drawCenteredString(GameData.getFontRenderer(), stringHealth, (posX - 27 + 44) * 2, (36 + posY) * 2, -1);
            GlStateManager.scale(2.0, 2.0, 2.0);

            int x = (posX - 29 + 44 - GameData.getFontRenderer().getStringWidth(focused.getName()) / 2);
            int y = 25 + posY;
            GameData.getFontRenderer().drawString(focused.getName(), x + 1, y, 0);
            GameData.getFontRenderer().drawString(focused.getName(), x - 1, y, 0);
            GameData.getFontRenderer().drawString(focused.getName(), x, y + 1, 0);
            GameData.getFontRenderer().drawString(focused.getName(), x, y - 1, 0);
            GameData.getFontRenderer().drawString(focused.getName(), x, y, -1);

            drawEntityOnScreen(posX - 60 + 16, 22 + 27 + posY, focused);

            if (settings.getBoolValue(Settings.show_entity_armor)) {
                int armor = focused.getTotalArmorValue();
                if (armor > 0) {
                    String value = String.valueOf(armor);
                    this.mc.getTextureManager().bindTexture(DAMAGE_INDICATOR);
                    gui.drawTexturedModalRect(posX - 26, posY + 44, 0, 36, 19, 8);
                    //drawRect(posX - 30, posY + 42, 8 + (mc.fontRenderer.getStringWidth(value) / 2), 6, 0xA0000000);
                    this.mc.getTextureManager().bindTexture(GameData.icons());
                    GlStateManager.scale(0.5, 0.5, 0.5);
                    gui.drawTexturedModalRect((posX - 24) * 2 - 1, (posY + 45) * 2, 34, 9, 9, 9);
                    x = (posX - 18) * 2 - 2;
                    y = (posY + 45) * 2 + 1;
                    GameData.getFontRenderer().drawString(value, x + 1, y, 0);
                    GameData.getFontRenderer().drawString(value, x - 1, y, 0);
                    GameData.getFontRenderer().drawString(value, x, y + 1, 0);
                    GameData.getFontRenderer().drawString(value, x, y - 1, 0);
                    GameData.getFontRenderer().drawString(value, x, y, -1);
                    GlStateManager.scale(2.0, 2.0, 2.0);
                }
            }
        }
    }

    public static void drawEntityOnScreen(int posX, int posY, EntityLivingBase ent) {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        int scale = 1;
        int s = (int) (22 / ent.height);
        int s2 = (int) (22 / ent.width);
        if (s < s2)
            scale = s;
        else
            scale = s2;

        int offset = 0;
        if (ent instanceof EntitySquid) {
            scale = 11;
            offset = -13;
        }
        posY += offset;
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
        rendermanager.renderEntity(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
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
