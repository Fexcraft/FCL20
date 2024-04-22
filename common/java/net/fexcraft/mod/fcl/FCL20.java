package net.fexcraft.mod.fcl;

import net.fexcraft.lib.common.math.AxisRotator;
import net.fexcraft.mod.fcl.util.Axis3DL;
import net.fexcraft.mod.uni.EnvInfo;
import net.fexcraft.mod.uni.IDLManager;
import net.fexcraft.mod.uni.UniReg;
import net.fexcraft.mod.uni.impl.*;
import net.fexcraft.mod.uni.item.ItemWrapper;
import net.fexcraft.mod.uni.item.StackWrapper;
import net.fexcraft.mod.uni.tag.TagCW;
import net.fexcraft.mod.uni.tag.TagLW;
import net.fexcraft.mod.uni.ui.*;
import net.fexcraft.mod.uni.uimpl.UUIButton;
import net.fexcraft.mod.uni.uimpl.UUIField;
import net.fexcraft.mod.uni.uimpl.UUITab;
import net.fexcraft.mod.uni.uimpl.UUIText;
import net.fexcraft.mod.uni.world.StateWrapper;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

/**
 * @author Ferdinand Calo' (FEX___96)
 */
public class FCL20 {

	public static void init(boolean dev, boolean client){
		EnvInfo.CLIENT = client;
		EnvInfo.DEV = dev;
		UniReg.LOADER_VERSION = "1.20";
		if(client){
			AxisRotator.DefHolder.DEF_IMPL = Axis3DL.class;
		}
		IDLManager.INSTANCE[0] = new IDLM();
		TagCW.WRAPPER[0] = com -> new TagCWI((CompoundTag)com);
		TagCW.SUPPLIER[0] = () -> new TagCWI();
		TagLW.SUPPLIER[0] = () -> new TagLWI();
		StackWrapper.SUPPLIER = obj -> {
			if(obj instanceof ItemWrapper) return new SWI((ItemWrapper)obj);
			if(obj instanceof ItemStack) return new SWI((ItemStack)obj);
			return null;
		};
		StateWrapper.DEFAULT = new StateWrapperI(Blocks.AIR.defaultBlockState());
		StateWrapper.STATE_WRAPPER = state -> new StateWrapperI((BlockState)state);
		StateWrapper.STACK_WRAPPER = (stack, ctx) ->{
			//TODO
			return StateWrapper.DEFAULT;
		};
		if(EnvInfo.CLIENT){
			UITab.IMPLEMENTATION = UUITab.class;
			UIText.IMPLEMENTATION = UUIText.class;
			UIField.IMPLEMENTATION = UUIField.class;
			UIButton.IMPLEMENTATION = UUIButton.class;
			ContainerInterface.TRANSLATOR = str -> I18n.get(str);
			ContainerInterface.TRANSFORMAT = (str, objs) -> I18n.get(str, objs);
		}
	}

}
