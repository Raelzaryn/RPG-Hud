package net.spellcraftgaming.rpghud.event;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.pickup.ItemPickup;

public class ItemPickupHandler {

	private List<ItemPickup> pickups = new ArrayList<ItemPickup>();
	
	@SubscribeEvent
	public void onPickupItem(EntityItemPickupEvent event) {
		if(event.getEntityPlayer().equals(Minecraft.getMinecraft().thePlayer)){
			ItemStack item = event.getItem().getEntityItem();
			addPickup(item);
			ModRPGHud.renderDetailsAgain[0] = true;
			ModRPGHud.renderDetailsAgain[1] = true;
			ModRPGHud.renderDetailsAgain[2] = true;
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
			this.pickups.add(new ItemPickup(item));
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
