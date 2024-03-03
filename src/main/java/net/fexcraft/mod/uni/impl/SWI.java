package net.fexcraft.mod.uni.impl;

import net.fexcraft.mod.uni.item.ItemWrapper;
import net.fexcraft.mod.uni.item.StackWrapper;
import net.fexcraft.mod.uni.tag.TagCW;
import net.minecraft.nbt.CompoundTag;
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

	@Override
	public String getName(){
		return stack.getDisplayName().getString();
	}

	@Override
	public int maxsize(){
		return stack.getMaxStackSize();
	}

	@Override
	public int damage(){
		return stack.getDamageValue();
	}

	@Override
	public int count(){
		return stack.getCount();
	}

	@Override
	public void count(int am){
		stack.setCount(am);
	}

	@Override
	public StackWrapper copy(){
		return new SWI(stack.copy());
	}

	@Override
	public void save(TagCW com){
		stack.save(com.local());
	}

	@Override
	public boolean empty(){
		return stack.isEmpty();
	}

	@Override
	public void createTagIfMissing(){
		if(!stack.hasTag()) stack.setTag(new CompoundTag());
	}

}