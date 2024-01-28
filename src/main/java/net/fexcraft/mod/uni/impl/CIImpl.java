package net.fexcraft.mod.uni.impl;

import net.fexcraft.app.json.JsonMap;
import net.fexcraft.mod.uni.tag.TagCW;
import net.fexcraft.mod.uni.ui.ContainerInterface;
import net.fexcraft.mod.uni.world.EntityW;

/**
 * @author Ferdinand Calo' (FEX___96)
 */
public class CIImpl extends ContainerInterface {

	public CIImpl(JsonMap map, EntityW player){
		super(map, player);
	}

	public Object get(String key, Object... objs){
		return null;
	}

	public void packet(TagCW com, boolean client){}

	public void onClosed(){}

}