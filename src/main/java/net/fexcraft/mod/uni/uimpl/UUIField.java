package net.fexcraft.mod.uni.uimpl;

import net.fexcraft.app.json.JsonMap;
import net.fexcraft.mod.uni.ui.UIElement;
import net.fexcraft.mod.uni.ui.UIField;
import net.fexcraft.mod.uni.ui.UserInterface;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;

/**
 * @author Ferdinand Calo' (FEX___96)
 */
public class UUIField extends UIField {

	protected EditBox field;

	public UUIField(UserInterface ui, JsonMap map) throws Exception{
		super(ui, map);
	}

	public void draw(Object gui, UIElement root, float ticks, int gl, int gt, int mx, int my){
		if(this.field == null || !visible()) return;
		field.setX(this.absolute ? ((this.x < 0) ? (this.ui.screen_width + this.x) : this.x) : (gl + this.x));
		field.setY(this.absolute ? ((this.y < 0) ? (this.ui.screen_height + this.y) : this.y) : (gt + this.y));
	}

	public void init(){
		field = new EditBox(Minecraft.getInstance().font, 0, 0, this.width, this.height, null){
			@Override
			public void setValue(String text){
				if(UUIField.this.regex != null) text = text.replaceAll(UUIField.this.regex, "");
				super.setValue(text);
			}
		};
		field.setBordered(background);
		field.setTextColor(color);
		field.setValue(value);
		field.active = enabled;
		field.setVisible(visible);
	}

	public boolean visible(){
		return field.isVisible();
	}


	public void visible(boolean bool){
		field.setVisible(visible = bool);
	}

	public void enabled(boolean bool){
		field.active = enabled = bool;
	}

	public boolean onclick(int mx, int my, int mb){
		boolean bool = field.mouseClicked(mx, my, mb);
		field.setFocused(true);
		for(UIField uif : ui.fields.values()){
			UUIField uuif = (UUIField)uif;
			if(uuif.field.isFocused() && uuif.field != this.field){
				uuif.field.setFocused(false);
			}
		}
		return bool;
	}

	public boolean keytyped(char c, int code){
		return field.keyPressed(c, code, 0);
	}

	public void text(String text){
		field.setValue(value = text);
	}

	public String text(){
		return field.getValue();
	}

}