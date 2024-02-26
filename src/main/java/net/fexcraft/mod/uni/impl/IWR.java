package net.fexcraft.mod.uni.impl;

import net.fexcraft.mod.uni.item.ItemWrapper;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;

/**
 * @author Ferdinand Calo' (FEX___96)
 */
public class IWR extends ItemWrapper {

	public DeferredHolder<Item, Item> item;

	public IWR(DeferredHolder<Item, Item> item){
		this.item = item;
	}

	public void linkContainer(){}

	public void regToDict(){}

	public Item local(){
		return item.get();
	}

	public Object direct(){
		return item.get();
	}

}