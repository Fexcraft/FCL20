package net.fexcraft.mod.fcl;

import com.mojang.logging.LogUtils;
import net.fexcraft.mod.fcl.util.UIPacket;
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
import net.fexcraft.mod.uni.uimpl.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

/**
 * @author Ferdinand Calo' (FEX___96)
 */
@Mod(FCL.MODID)
public class FCL {

	public static final String MODID = "fcl";
	public static final Logger LOGGER = LogUtils.getLogger();
	public static final ResourceLocation UI_PACKET = new ResourceLocation(MODID, "ui");
	public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(Registries.MENU, MODID);
	public static final DeferredHolder<MenuType<?>, MenuType<UniCon>> UNIVERSAL = CONTAINERS.register("editor", () -> IMenuTypeExtension.create(UniCon::new));

	public FCL(IEventBus bus){
		EnvInfo.CLIENT = FMLLoader.getDist().isClient();
		EnvInfo.DEV = !FMLEnvironment.production;
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
		//
		UniversalAttachments.register(bus);
		CONTAINERS.register(bus);
	}

	@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistryEvents {

		@SubscribeEvent
		public static void register(final RegisterPayloadHandlerEvent event){
			final IPayloadRegistrar registrar = event.registrar(MODID).versioned("1.0.0").optional();
			registrar.common(UI_PACKET, UIPacket::read, handler -> {
				handler.server(UIPacket::handle_server);
				handler.client(UIPacket::handle_client);
			});
		}

	}

}
