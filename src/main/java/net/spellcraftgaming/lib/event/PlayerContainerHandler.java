package net.spellcraftgaming.lib.event;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;

public class PlayerContainerHandler {

    @SubscribeEvent
    public void removeFromBus(RenderTickEvent e) {
        MinecraftForge.EVENT_BUS.unregister(this);
    }
}
