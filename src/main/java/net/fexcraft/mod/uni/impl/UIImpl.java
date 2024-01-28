package net.fexcraft.mod.uni.impl;

import net.fexcraft.app.json.JsonMap;
import net.fexcraft.mod.uni.ui.UIButton;
import net.fexcraft.mod.uni.ui.UserInterface;
import net.fexcraft.mod.uni.world.EntityW;

/**
 * @author Ferdinand Calo' (FEX___96)
 */
public class UIImpl extends UserInterface {

	public UIImpl(JsonMap map, EntityW player) throws Exception{
		super(map, new CIImpl(map, player));
	}

	public boolean onAction(UIButton button, String id, int l, int t, int x, int y, int b){
		return false;
	}

	public boolean onScroll(UIButton button, String id, int gl, int gt, int mx, int my, int am){
		return false;
	}

	public void predraw(float ticks, int mx, int my){}

	public void postdraw(float ticks, int mx, int my){}

	public void scrollwheel(int am, int mx, int my){}

}