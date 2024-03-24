package net.fexcraft.mod.fcl;

import net.fexcraft.mod.uni.EnvInfo;
import net.fexcraft.mod.uni.IDLManager;
import net.fexcraft.mod.uni.UniReg;
import net.fexcraft.mod.uni.impl.IDLM;
import net.fexcraft.mod.uni.impl.SWI;
import net.fexcraft.mod.uni.impl.TagCWI;
import net.fexcraft.mod.uni.impl.TagLWI;
import net.fexcraft.mod.uni.item.ItemWrapper;
import net.fexcraft.mod.uni.item.StackWrapper;
import net.fexcraft.mod.uni.tag.TagCW;
import net.fexcraft.mod.uni.tag.TagLW;
import net.fexcraft.mod.uni.ui.UIButton;
import net.fexcraft.mod.uni.ui.UIField;
import net.fexcraft.mod.uni.ui.UITab;
import net.fexcraft.mod.uni.ui.UIText;
import net.fexcraft.mod.uni.uimpl.UUIButton;
import net.fexcraft.mod.uni.uimpl.UUIField;
import net.fexcraft.mod.uni.uimpl.UUITab;
import net.fexcraft.mod.uni.uimpl.UUIText;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

/**
 * @author Ferdinand Calo' (FEX___96)
 */
public class FCL20 {

	public static void init(boolean dev, boolean client){
		EnvInfo.CLIENT = client;
		EnvInfo.DEV = dev;
		UniReg.LOADER_VERSION = "1.20";
		IDLManager.INSTANCE[0] = new IDLM();
		TagCW.WRAPPER[0] = com -> new TagCWI((CompoundTag)com);
		TagCW.SUPPLIER[0] = () -> new TagCWI();
		TagLW.SUPPLIER[0] = () -> new TagLWI();
		StackWrapper.SUPPLIER = obj -> {
			if(obj instanceof ItemWrapper) return new SWI((ItemWrapper)obj);
			if(obj instanceof ItemStack) return new SWI((ItemStack)obj);
			return null;
		};
		if(EnvInfo.CLIENT){
			UITab.IMPLEMENTATION = UUITab.class;
			UIText.IMPLEMENTATION = UUIText.class;
			UIField.IMPLEMENTATION = UUIField.class;
			UIButton.IMPLEMENTATION = UUIButton.class;
		}
	}

}
