package net.spellcraftgaming.rpghud.pickup;

import net.minecraft.item.ItemStack;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.rpghud.main.ModRPGHud;

public class ItemPickup {
	
	private ItemStack item;
	
	private int timer;
	private int TIMER_TOTAL = 60;
	
	public ItemPickup(ItemStack item) {
		this.item = item;
		this.timer = this.TIMER_TOTAL * GameData.ceil(ModRPGHud.instance.settings.pickup_duration);
	}
	
	public boolean onUpdate(){
		this.timer--;
		if(this.timer <= 0) {
			return true;
		}
		return false;
	}
	
	public void addItems(int count){
		this.item = GameData.setItemStackSize(this.item, GameData.getItemStackSize(this.item) + count);
		this.timer = this.TIMER_TOTAL * GameData.ceil(ModRPGHud.instance.settings.pickup_duration);
	}
	
	public ItemStack getItem(){
		return this.item;
	}
	
	public int getCount(){
		return GameData.getItemStackSize(this.item);
	}
	
	public int getTimer() {
		return this.timer;
	}

}
