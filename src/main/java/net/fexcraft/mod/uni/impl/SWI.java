package net.fexcraft.mod.uni.impl;

import net.fexcraft.mod.uni.item.ItemWrapper;
import net.fexcraft.mod.uni.item.StackWrapper;
import net.fexcraft.mod.uni.tag.TagCW;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class SWI extends StackWrapper {

	public ItemStack stack;

	public SWI(ItemWrapper item){
		super(item);
		stack = new ItemStack((ItemLike)item.direct());
	}

	public SWI(ItemStack stack){
		super(new IWI(stack.getItem()));
		this.stack = stack;
	}

	public ItemStack local(){
		return stack;
	}

	public Object direct(){
		return stack;
	}

	@Override
	public StackWrapper setTag(TagCW tag){
		stack.setTag(tag.local());
		return this;
	}

	@Override
	public TagCW getTag(){
		return TagCW.wrap(stack.getTag());
	}

	@Override
	public boolean hasTag(){
		return stack.hasTag();
	}

}