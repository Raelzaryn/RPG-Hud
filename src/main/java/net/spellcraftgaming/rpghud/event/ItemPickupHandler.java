package net.spellcraftgaming.rpghud.event;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemPickupEvent;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.pickup.ItemPickup;

public class ItemPickupHandler {

	private List<ItemPickup> pickups = new ArrayList<ItemPickup>();
	
	private static ItemStack storedItem;
	
	@SubscribeEvent
	public void onPickupItem(ItemPickupEvent event) {
		if(!event.isCanceled() && event.player.equals(Minecraft.getMinecraft().thePlayer) && storedItem != null && ItemStack.areItemsEqual(event.pickedUp.getEntityItem(), storedItem)){
			addPickup(storedItem.copy());
			storedItem = null;
			ModRPGHud.renderDetailsAgain[0] = true;
			ModRPGHud.renderDetailsAgain[1] = true;
			ModRPGHud.renderDetailsAgain[2] = true;
		}
	}
	
	@SubscribeEvent
	public void onEntityPickupItem(EntityItemPickupEvent event){
		if(event.entityPlayer.equals(Minecraft.getMinecraft().thePlayer)){
			storedItem = event.item.getEntityItem().copy();
		}
	}
	
	public void addPickup(ItemStack item){
		boolean added = false;
		for(int i = 0; i < this.pickups.size(); i++){
			if(ItemStack.areItemsEqual(item, this.pickups.get(i).getItem())){
				this.pickups.get(i).addItems(item.stackSize);
				added = true;
			}
		}
		if(!added){
			this.pickups.add(new ItemPickup(item.copy()));
		}
	}
	
	public void onUpdate(){
		for(int i = 0; i < this.pickups.size(); i++){
			if(this.pickups.get(i).onUpdate()) this.pickups.remove(i);
		}
	}
	
	public List<ItemPickup> getPickups(){
		return this.pickups;
	}
}
