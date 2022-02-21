package net.spellcraftgaming.rpghud.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.common.ForgeVersion.CheckResult;
import net.minecraftforge.common.ForgeVersion.Status;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.notification.Notification;
import net.spellcraftgaming.rpghud.notification.NotificationOldSettings;
import net.spellcraftgaming.rpghud.notification.NotificationUpdate;
import net.spellcraftgaming.rpghud.settings.Settings;
import org.lwjgl.input.Mouse;

import java.io.File;

public class NotificationHandler {

    public Notification notification = null;

    public static boolean updateCheck = true;
    public static boolean oldSettingCheck = true;

    public static boolean shouldFireMouse = true;

    @SubscribeEvent
    public void onGuiScreenTick(GuiScreenEvent event) {
        if (GameData.getGuiOfEvent(event) instanceof GuiMainMenu) {
            CheckResult vercheck = ForgeVersion.getResult(Loader.instance().getIndexedModList().get(ModRPGHud.MOD_ID));
            boolean outdated = vercheck.status == Status.OUTDATED || (vercheck.status == Status.BETA_OUTDATED);
            if (ModRPGHud.instance.settings.getBoolValue(Settings.show_update_notification) && updateCheck && this.notification == null && outdated) {
                updateCheck = false;
                this.notification = new NotificationUpdate();
            }
            if (ModRPGHud.instance.settings.getBoolValue(Settings.show_convert_notification) && oldSettingCheck && this.notification == null
                    && (new File(Minecraft.getMinecraft().mcDataDir, "RPGHud_settings.txt").exists()
                    || new File(Minecraft.getMinecraft().mcDataDir, "RPGHud_settings_debug.txt").exists())) {
                oldSettingCheck = false;
                this.notification = new NotificationOldSettings(ModRPGHud.instance.settings);
            }
            if (this.notification != null) {
                this.notification.drawOnScreen(GameData.getGuiOfEvent(event));
            }
        }
    }

    @SubscribeEvent
    public void onMouseInput(GuiScreenEvent.MouseInputEvent event) {
        if (this.notification != null) {
            if (Mouse.getEventButtonState() && shouldFireMouse) {
                this.notification.handleMouse();
                shouldFireMouse = false;
            }
            if (!Mouse.getEventButtonState() && !shouldFireMouse)
                shouldFireMouse = true;
            if (this.notification.shouldDestroy)
                this.notification = null;
        }
        if (!shouldFireMouse && !Mouse.getEventButtonState())
            shouldFireMouse = true;
    }

    @SubscribeEvent
    public void onActionPerformed(ActionPerformedEvent event) {
        if (GameData.getGuiOfEvent(event) instanceof GuiMainMenu && (this.notification != null || !shouldFireMouse)) {
            event.setCanceled(true);
        }
    }
}
