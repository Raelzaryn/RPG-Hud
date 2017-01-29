package net.spellcraftgaming.rpghud.settings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.client.FMLClientHandler;

public class ModDebugSettings {

	protected Minecraft mc;
	private File settingFile;
	
    public boolean forceRenderCrosshair = false;
    public boolean renderVanillaCrosshair = false;
    public boolean preventEventCrosshair = false;
    public boolean preventElementRenderCrosshair = false;
    
    public boolean forceRenderArmor = false;
    public boolean renderVanillaArmor = false;
    public boolean preventEventArmor = false;
    public boolean preventElementRenderArmor = false;
    
    public boolean forceRenderHotbar = false;
    public boolean renderVanillaHotbar = false;
    public boolean preventEventHotbar = false;
    public boolean preventElementRenderHotbar = false;
    
    public boolean forceRenderAir = false;
    public boolean renderVanillaAir = false;
    public boolean preventEventAir = false;
    public boolean preventElementRenderAir = false;
    
    public boolean forceRenderHealth = false;
    public boolean renderVanillaHealth = false;
    public boolean preventEventHealth = false;
    public boolean preventElementRenderHealth = false;
    
    public boolean forceRenderFood = false;
    public boolean renderVanillaFood = false;
    public boolean preventEventFood = false;
    public boolean preventElementRenderFood = false;
    
    public boolean forceRenderExp = false;
    public boolean renderVanillaExp = false;
    public boolean preventEventExp = false;
    public boolean preventElementRenderExp = false;
    
    public boolean forceRenderExpLv = false;
    public boolean renderVanillaExpLv = false;
    public boolean preventEventExpLv = false;
    public boolean preventElementRenderExpLv = false;
    
    public boolean forceRenderHealthMount = false;
    public boolean renderVanillaHealthMount = false;
    public boolean preventEventHealthMount = false;
    public boolean preventElementRenderHealthMount = false;
    
    public boolean forceRenderJumpBar = false;
    public boolean renderVanillaJumpBar = false;
    public boolean preventEventJumpBar = false;
    public boolean preventElementRenderJumpBar = false;
    
    public ModDebugSettings(File file) {
		this.mc = Minecraft.getMinecraft();
		this.settingFile = new File(file, "RPGHud_settings_debug.txt");
		loadOptions();
	}
    
    public void setOptionValue(EnumOptionsDebugMod options) {
		if (options.ordinal() == 1) {
			this.forceRenderCrosshair = !this.forceRenderCrosshair;
			System.out.println(options + ":" + this.forceRenderCrosshair);
		}
		if (options.ordinal() == 2) {
			this.renderVanillaCrosshair = !this.renderVanillaCrosshair;
			System.out.println(options + ":" + this.renderVanillaCrosshair);
		}
		if (options.ordinal() == 3) {
			this.preventEventCrosshair = !this.preventEventCrosshair;
			System.out.println(options + ":" + this.preventEventCrosshair);
		}
		if (options.ordinal() == 4) {
			this.preventElementRenderCrosshair = !this.preventElementRenderCrosshair;
			System.out.println(options + ":" + this.preventElementRenderCrosshair);
		}
		
		if (options.ordinal() == 5) {
			this.forceRenderArmor = !this.forceRenderArmor;
			System.out.println(options + ":" + this.forceRenderArmor);
		}
		if (options.ordinal() == 6) {
			this.renderVanillaArmor = !this.renderVanillaArmor;
			System.out.println(options + ":" + this.renderVanillaArmor);
		}
		if (options.ordinal() == 7) {
			this.preventEventArmor = !this.preventEventArmor;
			System.out.println(options + ":" + this.preventEventArmor);
		}
		if (options.ordinal() == 8) {
			this.preventElementRenderArmor = !this.preventElementRenderArmor;
			System.out.println(options + ":" + this.preventElementRenderArmor);
		}
		
		if (options.ordinal() == 9) {
			this.forceRenderHotbar = !this.forceRenderHotbar;
			System.out.println(options + ":" + this.forceRenderHotbar);
		}
		if (options.ordinal() == 10) {
			this.renderVanillaHotbar = !this.renderVanillaHotbar;
			System.out.println(options + ":" + this.renderVanillaHotbar);
		}
		if (options.ordinal() == 11) {
			this.preventEventHotbar = !this.preventEventHotbar;
			System.out.println(options + ":" + this.preventEventHotbar);
		}
		if (options.ordinal() == 12) {
			this.preventElementRenderHotbar = !this.preventElementRenderHotbar;
			System.out.println(options + ":" + this.preventElementRenderHotbar);
		}
		
		if (options.ordinal() == 13) {
			this.forceRenderAir = !this.forceRenderAir;
			System.out.println(options + ":" + this.forceRenderAir);
		}
		if (options.ordinal() == 14) {
			this.renderVanillaAir = !this.renderVanillaAir;
			System.out.println(options + ":" + this.renderVanillaAir);
		}
		if (options.ordinal() == 15) {
			this.preventEventAir = !this.preventEventAir;
			System.out.println(options + ":" + this.preventEventAir);
		}
		if (options.ordinal() == 16) {
			this.preventElementRenderAir = !this.preventElementRenderAir;
			System.out.println(options + ":" + this.preventElementRenderAir);
		}
		
		if (options.ordinal() == 17) {
			this.forceRenderHealth = !this.forceRenderHealth;
			System.out.println(options + ":" + this.forceRenderHealth);
		}
		if (options.ordinal() == 18) {
			this.renderVanillaHealth = !this.renderVanillaHealth;
			System.out.println(options + ":" + this.renderVanillaHealth);
		}
		if (options.ordinal() == 19) {
			this.preventEventHealth = !this.preventEventHealth;
			System.out.println(options + ":" + this.preventEventHealth);
		}
		if (options.ordinal() == 20) {
			this.preventElementRenderHealth = !this.preventElementRenderHealth;
			System.out.println(options + ":" + this.preventElementRenderHealth);
		}
		
		if (options.ordinal() == 21) {
			this.forceRenderFood = !this.forceRenderFood;
			System.out.println(options + ":" + this.forceRenderFood);
		}
		if (options.ordinal() == 22) {
			this.renderVanillaFood = !this.renderVanillaFood;
			System.out.println(options + ":" + this.renderVanillaFood);
		}
		if (options.ordinal() == 23) {
			this.preventEventFood = !this.preventEventFood;
			System.out.println(options + ":" + this.preventEventFood);
		}
		if (options.ordinal() == 24) {
			this.preventElementRenderFood = !this.preventElementRenderFood;
			System.out.println(options + ":" + this.preventElementRenderFood);
		}
		
		if (options.ordinal() == 25) {
			this.forceRenderExp = !this.forceRenderExp;
			System.out.println(options + ":" + this.forceRenderExp);
		}
		if (options.ordinal() == 26) {
			this.renderVanillaExp = !this.renderVanillaExp;
			System.out.println(options + ":" + this.renderVanillaExp);
		}
		if (options.ordinal() == 27) {
			this.preventEventExp = !this.preventEventExp;
			System.out.println(options + ":" + this.preventEventExp);
		}
		if (options.ordinal() == 28) {
			this.preventElementRenderExp = !this.preventElementRenderExp;
			System.out.println(options + ":" + this.preventElementRenderExp);
		}
		
		if (options.ordinal() == 29) {
			this.forceRenderExpLv = !this.forceRenderExpLv;
			System.out.println(options + ":" + this.forceRenderExpLv);
		}
		if (options.ordinal() == 30) {
			this.renderVanillaExpLv = !this.renderVanillaExpLv;
			System.out.println(options + ":" + this.renderVanillaExpLv);
		}
		if (options.ordinal() == 31) {
			this.preventEventExpLv = !this.preventEventExpLv;
			System.out.println(options + ":" + this.preventEventExpLv);
		}
		if (options.ordinal() == 32) {
			this.preventElementRenderExpLv = !this.preventElementRenderExpLv;
			System.out.println(options + ":" + this.preventElementRenderExpLv);
		}
		
		if (options.ordinal() == 33) {
			this.forceRenderHealthMount = !this.forceRenderHealthMount;
			System.out.println(options + ":" + this.forceRenderHealthMount);
		}
		if (options.ordinal() == 34) {
			this.renderVanillaHealthMount = !this.renderVanillaHealthMount;
			System.out.println(options + ":" + this.renderVanillaHealthMount);
		}
		if (options.ordinal() == 35) {
			this.preventEventHealthMount = !this.preventEventHealthMount;
			System.out.println(options + ":" + this.preventEventHealthMount);
		}
		if (options.ordinal() == 36) {
			this.preventElementRenderHealthMount = !this.preventElementRenderHealthMount;
			System.out.println(options + ":" + this.preventElementRenderHealthMount);
		}
		
		if (options.ordinal() == 37) {
			this.forceRenderJumpBar = !this.forceRenderJumpBar;
			System.out.println(options + ":" + this.forceRenderJumpBar);
		}
		if (options.ordinal() == 38) {
			this.renderVanillaJumpBar = !this.renderVanillaJumpBar;
			System.out.println(options + ":" + this.renderVanillaJumpBar);
		}
		if (options.ordinal() == 39) {
			this.preventEventJumpBar = !this.preventEventJumpBar;
			System.out.println(options + ":" + this.preventEventJumpBar);
		}
		if (options.ordinal() == 40) {
			this.preventElementRenderJumpBar = !this.preventElementRenderJumpBar;
			System.out.println(options + ":" + this.preventElementRenderJumpBar);
		}
		
		saveOptions();
    }
		
	@SuppressWarnings("unused")
	static final class SwitchOptions {
		static final int[] optionIds = new int[EnumOptionsDebugMod.values().length];
		
		static {
			try {
				optionIds[EnumOptionsDebugMod.FORCE_RENDER_CROSSHAIR.ordinal()] = 0;
			} catch (NoSuchFieldError e) {
				;
			}
			try {
				optionIds[EnumOptionsDebugMod.RENDER_VANILLA_CROSSHAIR.ordinal()] = 1;
			} catch (NoSuchFieldError e) {
				;
			}
			try {
				optionIds[EnumOptionsDebugMod.PREVENT_EVENT_CROSSHAIR.ordinal()] = 2;
			} catch (NoSuchFieldError e) {
				;
			}
			try {
				optionIds[EnumOptionsDebugMod.PREVENT_ELEMENT_RENDER_CROSSHAIR.ordinal()] = 3;
			} catch (NoSuchFieldError e) {
				;
			}
			try {
				optionIds[EnumOptionsDebugMod.FORCE_RENDER_ARMOR.ordinal()] = 4;
			} catch (NoSuchFieldError e) {
				;
			}
			try {
				optionIds[EnumOptionsDebugMod.RENDER_VANILLA_ARMOR.ordinal()] = 5;
			} catch (NoSuchFieldError e) {
				;
			}
			try {
				optionIds[EnumOptionsDebugMod.PREVENT_EVENT_ARMOR.ordinal()] = 6;
			} catch (NoSuchFieldError e) {
				;
			}
			try {
				optionIds[EnumOptionsDebugMod.PREVENT_ELEMENT_RENDER_ARMOR.ordinal()] = 7;
			} catch (NoSuchFieldError e) {
				;
			}
			try {
				optionIds[EnumOptionsDebugMod.FORCE_RENDER_HOTBAR.ordinal()] = 8;
			} catch (NoSuchFieldError e) {
				;
			}
			try {
				optionIds[EnumOptionsDebugMod.RENDER_VANILLA_HOTBAR.ordinal()] = 9;
			} catch (NoSuchFieldError e) {
				;
			}
			try {
				optionIds[EnumOptionsDebugMod.PREVENT_EVENT_HOTBAR.ordinal()] = 10;
			} catch (NoSuchFieldError e) {
				;
			}
			try {
				optionIds[EnumOptionsDebugMod.PREVENT_ELEMENT_RENDER_HOTBAR.ordinal()] = 11;
			} catch (NoSuchFieldError e) {
				;
			}
			try {
				optionIds[EnumOptionsDebugMod.FORCE_RENDER_AIR.ordinal()] = 12;
			} catch (NoSuchFieldError e) {
				;
			}
			try {
				optionIds[EnumOptionsDebugMod.RENDER_VANILLA_AIR.ordinal()] = 13;
			} catch (NoSuchFieldError e) {
				;
			}
			try {
				optionIds[EnumOptionsDebugMod.PREVENT_EVENT_AIR.ordinal()] = 14;
			} catch (NoSuchFieldError e) {
				;
			}
			try {
				optionIds[EnumOptionsDebugMod.PREVENT_ELEMENT_RENDER_AIR.ordinal()] = 15;
			} catch (NoSuchFieldError e) {
				;
			}
			try {
				optionIds[EnumOptionsDebugMod.FORCE_RENDER_HEALTH.ordinal()] = 16;
			} catch (NoSuchFieldError e) {
				;
			}
			try {
				optionIds[EnumOptionsDebugMod.RENDER_VANILLA_HEALTH.ordinal()] = 17;
			} catch (NoSuchFieldError e) {
				;
			}
			try {
				optionIds[EnumOptionsDebugMod.PREVENT_EVENT_HEALTH.ordinal()] = 18;
			} catch (NoSuchFieldError e) {
				;
			}
			try {
				optionIds[EnumOptionsDebugMod.PREVENT_ELEMENT_RENDER_HEALTH.ordinal()] = 19;
			} catch (NoSuchFieldError e) {
				;
			}
			try {
				optionIds[EnumOptionsDebugMod.FORCE_RENDER_FOOD.ordinal()] = 20;
			} catch (NoSuchFieldError e) {
				;
			}
			try {
				optionIds[EnumOptionsDebugMod.RENDER_VANILLA_FOOD.ordinal()] = 21;
			} catch (NoSuchFieldError e) {
				;
			}
			try {
				optionIds[EnumOptionsDebugMod.PREVENT_EVENT_FOOD.ordinal()] = 22;
			} catch (NoSuchFieldError e) {
				;
			}
			try {
				optionIds[EnumOptionsDebugMod.PREVENT_ELEMENT_RENDER_FOOD.ordinal()] = 23;
			} catch (NoSuchFieldError e) {
				;
			}
			try {
				optionIds[EnumOptionsDebugMod.FORCE_RENDER_EXPERIENCE.ordinal()] = 24;
			} catch (NoSuchFieldError e) {
				;
			}
			try {
				optionIds[EnumOptionsDebugMod.RENDER_VANILLA_EXPERIENCE.ordinal()] = 25;
			} catch (NoSuchFieldError e) {
				;
			}
			try {
				optionIds[EnumOptionsDebugMod.PREVENT_EVENT_EXPERIENCE.ordinal()] = 26;
			} catch (NoSuchFieldError e) {
				;
			}
			try {
				optionIds[EnumOptionsDebugMod.PREVENT_ELEMENT_RENDER_EXPERIENCE.ordinal()] = 27;
			} catch (NoSuchFieldError e) {
				;
			}
			try {
				optionIds[EnumOptionsDebugMod.FORCE_RENDER_LEVEL.ordinal()] = 28;
			} catch (NoSuchFieldError e) {
				;
			}
			try {
				optionIds[EnumOptionsDebugMod.RENDER_VANILLA_LEVEL.ordinal()] = 29;
			} catch (NoSuchFieldError e) {
				;
			}
			try {
				optionIds[EnumOptionsDebugMod.PREVENT_EVENT_LEVEL.ordinal()] = 30;
			} catch (NoSuchFieldError e) {
				;
			}
			try {
				optionIds[EnumOptionsDebugMod.PREVENT_ELEMENT_RENDER_LEVEL.ordinal()] = 31;
			} catch (NoSuchFieldError e) {
				;
			}
			try {
				optionIds[EnumOptionsDebugMod.FORCE_RENDER_HEALTH_MOUNT.ordinal()] = 32;
			} catch (NoSuchFieldError e) {
				;
			}
			try {
				optionIds[EnumOptionsDebugMod.RENDER_VANILLA_HEALTH_MOUNT.ordinal()] = 33;
			} catch (NoSuchFieldError e) {
				;
			}
			try {
				optionIds[EnumOptionsDebugMod.PREVENT_EVENT_HEALTH_MOUNT.ordinal()] = 34;
			} catch (NoSuchFieldError e) {
				;
			}
			try {
				optionIds[EnumOptionsDebugMod.PREVENT_ELEMENT_RENDER_HEALTH_MOUNT.ordinal()] = 35;
			} catch (NoSuchFieldError e) {
				;
			}
			try {
				optionIds[EnumOptionsDebugMod.FORCE_RENDER_JUMP_BAR.ordinal()] = 36;
			} catch (NoSuchFieldError e) {
				;
			}
			try {
				optionIds[EnumOptionsDebugMod.RENDER_VANILLA_JUMP_BAR.ordinal()] = 37;
			} catch (NoSuchFieldError e) {
				;
			}
			try {
				optionIds[EnumOptionsDebugMod.PREVENT_EVENT_JUMP_BAR.ordinal()] = 38;
			} catch (NoSuchFieldError e) {
				;
			}
			try {
				optionIds[EnumOptionsDebugMod.PREVENT_ELEMENT_RENDER_JUMP_BAR.ordinal()] = 39;
			} catch (NoSuchFieldError e) {
				;
			}
		}
    }
    
	public boolean getOptionOrdinalValue(EnumOptionsDebugMod options) {
		switch (ModDebugSettings.SwitchOptions.optionIds[options.ordinal()]) {
		case 0: 
			return this.forceRenderCrosshair;
		case 1: 
			 return this.renderVanillaCrosshair;
	    case 2: 
	    	return this.preventEventCrosshair;
	    case 3: 
	    	return this.preventElementRenderCrosshair;
	    
	    case 4: 
	    	return this.forceRenderArmor;
	    case 5: 
	    	return this.renderVanillaArmor;
	    case 6: 
	    	return this.preventEventArmor;
	    case 7: 
	    	return this.preventElementRenderArmor;
	    
	    case 8: 
	    	return this.forceRenderHotbar;
	    case 9: 
	    	return this.renderVanillaHotbar;
	    case 10: 
	    	return this.preventEventHotbar;
	    case 11: 
	    	return this.preventElementRenderHotbar;
	    
	    case 12: 
	    	return this.forceRenderAir;
	    case 13: 
	    	return this.renderVanillaAir;
	    case 14: 
	    	return this.preventEventAir;
	    case 15: 
	    	return this.preventElementRenderAir;
	    
	    case 16: 
	    	return this.forceRenderHealth;
	    case 17: 
	    	return this.renderVanillaHealth;
	    case 18: 
	    	return this.preventEventHealth;
	    case 19: 
	    	return this.preventElementRenderHealth;
	    
	    case 20: 
	    	return this.forceRenderFood;
	    case 21: 
	    	return this.renderVanillaFood;
	    case 22:
	    	return this.preventEventFood;
	    case 23: 
	    	return this.preventElementRenderFood;
	    
	    case 24: 
	    	return this.forceRenderExp;
	    case 25: 
	    	return this.renderVanillaExp;
	    case 26: 
	    	return this.preventEventExp;
	    case 27: 
	    	return this.preventElementRenderExp;
	    
	    case 28: 
	    	return this.forceRenderExpLv;
	    case 29: 
	    	return this.renderVanillaExpLv;
	    case 30: 
	    	return this.preventEventExpLv;
	    case 31: 
	    	return this.preventElementRenderExpLv;
	    
	    case 32: 
	    	return this.forceRenderHealthMount;
	    case 33: 
	    	return this.renderVanillaHealthMount;
	    case 34: 
	    	return this.preventEventHealthMount;
	    case 35: 
	    	return this.preventElementRenderHealthMount;
	    
	    case 36: 
	    	return this.forceRenderJumpBar;
	    case 37: 
	    	return this.renderVanillaJumpBar;
	    case 38: 
	    	return this.preventEventJumpBar;
	    case 39: 
	    	return this.preventElementRenderJumpBar;
		    
		default:
			return false;
		}
	}
	
    public void loadOptions() {
		try {
			if (!this.settingFile.exists()) {
				return;
			}
			BufferedReader reader = new BufferedReader(new FileReader(
					this.settingFile));
			String s = "";
			while ((s = reader.readLine()) != null) {
				try {
					String[] string = s.split(":");
					if (string[0].equals("forceRenderCrosshair")) {
						this.forceRenderCrosshair = string[1].equals("true");
					}
					if (string[0].equals("renderVanillaCrosshair")) {
						this.renderVanillaCrosshair = string[1].equals("true");
					}
					if (string[0].equals("preventEventCrosshair")) {
						this.preventEventCrosshair = string[1].equals("true");
					}
					if (string[0].equals("preventElementRenderCrosshair")) {
						this.preventElementRenderCrosshair = string[1].equals("true");
					}
					
					if (string[0].equals("forceRenderArmor")) {
						this.forceRenderArmor = string[1].equals("true");
					}
					if (string[0].equals("renderVanillaArmor")) {
						this.renderVanillaArmor = string[1].equals("true");
					}
					if (string[0].equals("preventEventArmor")) {
						this.preventEventArmor = string[1].equals("true");
					}
					if (string[0].equals("preventElementRenderArmor")) {
						this.preventElementRenderArmor = string[1].equals("true");
					}
					
					if (string[0].equals("forceRenderHotbar")) {
						this.forceRenderHotbar = string[1].equals("true");
					}
					if (string[0].equals("renderVanillaHotbar")) {
						this.renderVanillaHotbar = string[1].equals("true");
					}
					if (string[0].equals("preventEventHotbar")) {
						this.preventEventHotbar = string[1].equals("true");
					}
					if (string[0].equals("preventElementRenderHotbar")) {
						this.preventElementRenderHotbar = string[1].equals("true");
					}
					
					if (string[0].equals("forceRenderAir")) {
						this.forceRenderAir = string[1].equals("true");
					}
					if (string[0].equals("renderVanillaAir")) {
						this.renderVanillaAir = string[1].equals("true");
					}
					if (string[0].equals("preventEventAir")) {
						this.preventEventAir = string[1].equals("true");
					}
					if (string[0].equals("preventElementRenderAir")) {
						this.preventElementRenderAir = string[1].equals("true");
					}
					
					if (string[0].equals("forceRenderHealth")) {
						this.forceRenderHealth = string[1].equals("true");
					}
					if (string[0].equals("renderVanillaHealth")) {
						this.renderVanillaHealth = string[1].equals("true");
					}
					if (string[0].equals("preventEventHealth")) {
						this.preventEventHealth = string[1].equals("true");
					}
					if (string[0].equals("preventElementRenderHealth")) {
						this.preventElementRenderHealth = string[1].equals("true");
					}
					
					if (string[0].equals("forceRenderFood")) {
						this.forceRenderFood = string[1].equals("true");
					}
					if (string[0].equals("renderVanillaFood")) {
						this.renderVanillaFood = string[1].equals("true");
					}
					if (string[0].equals("preventEventFood")) {
						this.preventEventFood = string[1].equals("true");
					}
					if (string[0].equals("preventElementRenderFood")) {
						this.preventElementRenderFood = string[1].equals("true");
					}
					
					if (string[0].equals("forceRenderExp")) {
						this.forceRenderExp = string[1].equals("true");
					}
					if (string[0].equals("renderVanillaExp")) {
						this.renderVanillaExp = string[1].equals("true");
					}
					if (string[0].equals("preventEventExp")) {
						this.preventEventExp = string[1].equals("true");
					}
					if (string[0].equals("preventElementRenderExp")) {
						this.preventElementRenderExp = string[1].equals("true");
					}
					
					if (string[0].equals("forceRenderExpLv")) {
						this.forceRenderExpLv = string[1].equals("true");
					}
					if (string[0].equals("renderVanillaExpLv")) {
						this.renderVanillaExpLv = string[1].equals("true");
					}
					if (string[0].equals("preventEventExpLv")) {
						this.preventEventExpLv = string[1].equals("true");
					}
					if (string[0].equals("preventElementRenderExpLv")) {
						this.preventElementRenderExpLv = string[1].equals("true");
					}
					
					if (string[0].equals("forceRenderHealthMount")) {
						this.forceRenderHealthMount = string[1].equals("true");
					}
					if (string[0].equals("renderVanillaHealthMount")) {
						this.renderVanillaHealthMount = string[1].equals("true");
					}
					if (string[0].equals("preventEventHealthMount")) {
						this.preventEventHealthMount = string[1].equals("true");
					}
					if (string[0].equals("preventElementRenderHealthMount")) {
						this.preventElementRenderHealthMount = string[1].equals("true");
					}
					
					if (string[0].equals("forceRenderJumpBar")) {
						this.forceRenderJumpBar = string[1].equals("true");
					}
					if (string[0].equals("renderVanillaJumpBar")) {
						this.renderVanillaJumpBar = string[1].equals("true");
					}
					if (string[0].equals("preventEventJumpBar")) {
						this.preventEventJumpBar = string[1].equals("true");
					}
					if (string[0].equals("preventElementRenderJumpBar")) {
						this.preventElementRenderJumpBar = string[1].equals("true");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
	public String getKeyBinding(EnumOptionsDebugMod par1EnumOptions) {
		String s = I18n.format(par1EnumOptions.getEnumString(), new Object[0])
				+ ": ";
		boolean flag = this.getOptionOrdinalValue(par1EnumOptions);
		return flag ? s + I18n.format("options.on", new Object[0]) : s + I18n.format("options.off", new Object[0]);
	}
	
	public void saveOptions() {
		if (!FMLClientHandler.instance().isLoading()) {
			try {
				PrintWriter exception = new PrintWriter(new FileWriter(
						this.settingFile));
				exception.println("forceRenderCrosshair:" + this.forceRenderCrosshair);
				exception.println("renderVanillaCrosshair:" + this.renderVanillaCrosshair);
				exception.println("preventEventCrosshair:" + this.preventEventCrosshair);
				exception.println("preventElementRenderCrosshair:" + this.preventElementRenderCrosshair);
				
				exception.println("forceRenderArmor:" + this.forceRenderArmor);
				exception.println("renderVanillaArmor:" + this.renderVanillaArmor);
				exception.println("preventEventArmor:" + this.preventEventArmor);
				exception.println("preventElementRenderArmor:" + this.preventElementRenderArmor);
				
				exception.println("forceRenderHotbar:" + this.forceRenderHotbar);
				exception.println("renderVanillaHotbar:" + this.renderVanillaHotbar);
				exception.println("preventEventHotbar:" + this.preventEventHotbar);
				exception.println("preventElementRenderHotbar:" + this.preventElementRenderHotbar);
				
				exception.println("forceRenderAir:" + this.forceRenderAir);
				exception.println("renderVanillaAir:" + this.renderVanillaAir);
				exception.println("preventEventAir:" + this.preventEventAir);
				exception.println("preventElementRenderAir:" + this.preventElementRenderAir);
				
				exception.println("forceRenderHealth:" + this.forceRenderHealth);
				exception.println("renderVanillaHealth:" + this.renderVanillaHealth);
				exception.println("preventEventHealth:" + this.preventEventHealth);
				exception.println("preventElementRenderHealth:" + this.preventElementRenderHealth);
				
				exception.println("forceRenderFood:" + this.forceRenderFood);
				exception.println("renderVanillaFood:" + this.renderVanillaFood);
				exception.println("preventEventFood:" + this.preventEventFood);
				exception.println("preventElementRenderFood:" + this.preventElementRenderFood);
				
				exception.println("forceRenderExp:" + this.forceRenderExp);
				exception.println("renderVanillaExp:" + this.renderVanillaExp);
				exception.println("preventEventExp:" + this.preventEventExp);
				exception.println("preventElementRenderExp:" + this.preventElementRenderExp);
				
				exception.println("forceRenderExpLv:" + this.forceRenderExpLv);
				exception.println("renderVanillaExpLv:" + this.renderVanillaExpLv);
				exception.println("preventEventExpLv:" + this.preventEventExpLv);
				exception.println("preventElementRenderExpLv:" + this.preventElementRenderExpLv);
				
				exception.println("forceRenderHealthMount:" + this.forceRenderHealthMount);
				exception.println("renderVanillaHealthMount:" + this.renderVanillaHealthMount);
				exception.println("preventEventHealthMount:" + this.preventEventHealthMount);
				exception.println("preventElementRenderHealthMount:" + this.preventElementRenderHealthMount);
				
				exception.println("forceRenderJumpBar:" + this.forceRenderJumpBar);
				exception.println("renderVanillaJumpBar:" + this.renderVanillaJumpBar);
				exception.println("preventEventJumpBar:" + this.preventEventJumpBar);
				exception.println("preventElementRenderJumpBar:" + this.preventElementRenderJumpBar);
				
				exception.close();
			} catch (Exception var2) {
				var2.printStackTrace();
			}
		}
	}
}
