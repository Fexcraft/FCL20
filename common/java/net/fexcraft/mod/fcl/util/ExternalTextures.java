package net.fexcraft.mod.fcl.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.HttpTexture;
import net.minecraft.resources.ResourceLocation;

import java.io.File;
import java.util.*;

/**
 * @author Ferdinand Calo' (FEX___96)
 */
public class ExternalTextures {

	private static final Map<String, ResourceLocation> MAP = new HashMap<String, ResourceLocation>();
	private static final HashSet<String> KEY = new HashSet<>();
	static{ KEY.add("documents"); }

	public static ResourceLocation get(String mid, String url){
		if(MAP.containsKey(url)) return MAP.get(url);
		ResourceLocation texture = new ResourceLocation(mid, url.replaceAll("[^a-z0-9_.-]", ""));
		MAP.put(url, texture);
		File file = new File("./temp/fcl_download/" + texture.getPath());
		if(!file.getParentFile().exists()) file.getParentFile().mkdirs();
		file.deleteOnExit();
		Minecraft.getInstance().textureManager.register(texture, new HttpTexture(file, url, texture, false, null));
		return texture;
	}

}
