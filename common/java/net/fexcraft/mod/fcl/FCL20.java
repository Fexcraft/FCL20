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
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

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
			Item item = stack.getItem().local();
			if(item instanceof BlockItem){
				Block block = ((BlockItem)item).getBlock();
				BlockPos pos = new BlockPos(ctx.pos.x, ctx.pos.y, ctx.pos.z);
				BlockHitResult res = new BlockHitResult(new Vec3(ctx.pos.x, ctx.pos.y, ctx.pos.z), Direction.UP, pos, true);
				BlockPlaceContext btx = new BlockPlaceContext(ctx.world.local(), ctx.placer.local(), ctx.main ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND, stack.local(), res);
				return StateWrapper.of(block.getStateForPlacement(btx));
			}
			else return StateWrapper.DEFAULT;
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
