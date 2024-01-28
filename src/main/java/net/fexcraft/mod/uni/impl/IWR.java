package net.fexcraft.mod.uni.impl;

import net.fexcraft.mod.uni.item.ItemWrapper;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

/**
 * @author Ferdinand Calo' (FEX___96)
 */
public class IWR extends ItemWrapper {

	public DeferredItem<Item> item;

	public IWR(DeferredItem<Item> item){
		this.item = item;
	}

	public void linkContainer(){}

	public void regToDict(){}

	public Item local(){
		return (Item)this.item.get();
	}

	public Object direct(){
		return this.item.get();
	}

}