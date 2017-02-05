package net.spellcraftgaming.rpghud.pickup;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.spellcraftgaming.rpghud.main.ModRPGHud;

public class ItemPickup {
	
	private ItemStack item;
	
	private int timer;
	private int TIMER_TOTAL = 60;
	
	public ItemPickup(ItemStack item) {
		this.item = item;
		this.timer = this.TIMER_TOTAL;
	}
	
	public boolean onUpdate(){
		this.timer--;
		if(this.timer <= 0) {
			return true;
		}
		return false;
	}
	
	public void addItems(int count){
		this.item.setCount(this.item.getCount() + count);
		this.timer = this.TIMER_TOTAL * MathHelper.ceil(ModRPGHud.instance.settings.pickup_duration);
	}
	
	public ItemStack getItem(){
		return this.item;
	}
	
	public int getCount(){
		return this.item.getCount();
	}
	
	public int getTimer() {
		return this.timer;
	}

}
