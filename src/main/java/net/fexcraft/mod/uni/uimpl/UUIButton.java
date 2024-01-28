package net.fexcraft.mod.uni.uimpl;

import net.fexcraft.app.json.JsonMap;
import net.fexcraft.lib.common.math.RGB;
import net.fexcraft.mod.uni.ui.UIButton;
import net.fexcraft.mod.uni.ui.UIElement;
import net.fexcraft.mod.uni.ui.UserInterface;
import net.minecraft.resources.ResourceLocation;

/**
 * @author Ferdinand Calo' (FEX___96)
 */
public class UUIButton extends UIButton {

	protected static RGB rgb;
	public String name;

	public UUIButton(UserInterface ui, JsonMap map) throws Exception{
		super(ui, map);
	}

	public void draw(Object gui, UIElement root, float ticks, int gl, int gt, int mx, int my){
		if(!visible) return;
		UniUI uui = (UniUI)gui;
		if(texture != null) uui.bindTexture(texture);
		rgb = enabled ? (hovered ? hcolor : ecolor) : dcolor;
		uui.matrix.setColor(1.0F, 1.0F, 1.0F, 1.0F);
		int u = enabled ? (hovered ? htx : tx) : dtx;
		int v = enabled ? (hovered ? hty : ty) : dty;
		uui.matrix.setColor((rgb.packed >> 16 & 0xFF) / 255.0F, (rgb.packed >> 8 & 0xFF) / 255.0F, (rgb.packed & 0xFF) / 255.0F, rgb.alpha);
		if(absolute){
			uui.matrix.blit((texture == null) ? (ResourceLocation)uui.tab.texture : (ResourceLocation)texture, (x < 0) ? (uui.getGuiLeft() + x) : x, (y < 0) ? (uui.getGuiTop() + y) : y, u, v, width, height);
		}
		else{
			uui.matrix.blit((texture == null) ? (ResourceLocation)uui.tab.texture : (ResourceLocation)texture, gl + x, gt + y, u, v, width, height);
		}
		uui.matrix.setColor(1.0F, 1.0F, 1.0F, 1.0F);
	}

}