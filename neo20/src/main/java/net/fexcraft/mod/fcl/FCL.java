package net.fexcraft.mod.fcl;

import com.mojang.logging.LogUtils;
import net.fexcraft.mod.fcl.util.PassengerGetter;
import net.fexcraft.mod.fcl.util.UIPacketN;
import net.fexcraft.mod.uni.ui.ContainerInterface;
import net.fexcraft.mod.uni.uimpl.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.PacketDistributor;
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
		PassengerGetter.GETTER = entity -> entity.getData(UniversalAttachments.PASSENGER);
		FCL20.init(!FMLEnvironment.production, FMLLoader.getDist().isClient());
		ContainerInterface.SEND_TO_CLIENT = (com, player) -> PacketDistributor.PLAYER.with(player.local()).send(new UIPacketN(com.local()));
		ContainerInterface.SEND_TO_SERVER = com -> PacketDistributor.SERVER.noArg().send(new UIPacketN(com.local()));
		//
		UniversalAttachments.register(bus);
		CONTAINERS.register(bus);
	}

	@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistryEvents {

		@SubscribeEvent
		public static void register(final RegisterPayloadHandlerEvent event){
			final IPayloadRegistrar registrar = event.registrar(MODID).versioned("1.0.0").optional();
			registrar.common(UI_PACKET, UIPacketN::read, handler -> {
				handler.server(UIPacketN::handle_server);
				handler.client(UIPacketN::handle_client);
			});
		}

	}

}
