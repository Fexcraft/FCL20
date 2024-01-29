package net.fexcraft.mod.fcl;

import net.fexcraft.mod.uni.ui.ContainerInterface;
import net.fexcraft.mod.uni.ui.UserInterface;

import java.util.LinkedHashMap;

/**
 * @author Ferdinand Calo' (FEX___96)
 */
public class UniReg {

	public static LinkedHashMap<String, Class<? extends UserInterface>> GUI = new LinkedHashMap<>();
	public static LinkedHashMap<String, Class<? extends ContainerInterface>> MENU = new LinkedHashMap<>();
	public static LinkedHashMap<String, String> MENU_JSON = new LinkedHashMap<>();

	public static boolean registerUI(String key, Class<? extends UserInterface> ui){
		if(GUI.containsKey(key)) return false;
		GUI.put(key, ui);
		return true;
	}

	public static boolean registerMenu(String key, String loc, Class<? extends ContainerInterface> con){
		if(MENU.containsKey(key)) return false;
		MENU.put(key, con);
		MENU_JSON.put(key, loc);
		return true;
	}

}
