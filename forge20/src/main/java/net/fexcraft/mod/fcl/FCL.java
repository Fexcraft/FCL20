package net.fexcraft.mod.fcl;

import com.mojang.logging.LogUtils;
import net.fexcraft.mod.fcl.util.PassProvider;
import net.fexcraft.mod.fcl.util.PassengerUtil;
import net.fexcraft.mod.fcl.util.UIPacketF;
import net.fexcraft.mod.uni.ui.ContainerInterface;
import net.fexcraft.mod.uni.uimpl.UniCon;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
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
	public static final RegistryObject<MenuType<UniCon>> UNIVERSAL = CONTAINERS.register("universal", () -> IForgeMenuType.create(UniCon::new));
	public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder.named(new ResourceLocation("fcl", "channel"))
		.clientAcceptedVersions(pro -> true)
		.serverAcceptedVersions(pro -> true)
		.networkProtocolVersion(() -> "fcl")
		.simpleChannel();

	public FCL(){
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		FCL20.init(!FMLEnvironment.production, FMLLoader.getDist().isClient());
		PassengerUtil.GETTER = entity -> entity.getCapability(PassProvider.CAPABILITY).resolve().get();
		PassengerUtil.UI_OPENER = (player, ui, pos) -> {
			NetworkHooks.openScreen((ServerPlayer)player, new MenuProvider() {
				@Override
				public Component getDisplayName(){
					return Component.literal("Fexcraft Universal UI");
				}

				@Override
				public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player){
					return new UniCon(i, inventory, ui, null, pos);
				}
			}, buf -> {
				buf.writeInt(ui.length());
				buf.writeUtf(ui);
				buf.writeInt(pos.x);
				buf.writeInt(pos.y);
				buf.writeInt(pos.z);
			});
		};
		//
		CONTAINERS.register(bus);
		bus.addListener(this::commonSetup);
	}

	private void commonSetup(FMLCommonSetupEvent event){
		CHANNEL.registerMessage(1, UIPacketF.class, (packet, buffer) -> buffer.writeNbt(packet.com()), buffer -> new UIPacketF(buffer.readNbt()), (packet, context) -> {
			context.get().enqueueWork(() -> {
				if(context.get().getDirection().getOriginationSide().isClient()){
					ServerPlayer player = context.get().getSender();
					((UniCon)player.containerMenu).onPacket(packet.com(), false);
				}
				else{
					Player player = net.minecraft.client.Minecraft.getInstance().player;
					((UniCon)player.containerMenu).onPacket(packet.com(), true);
				}
			});
			context.get().setPacketHandled(true);
		});
		ContainerInterface.SEND_TO_CLIENT = (com, player) -> CHANNEL.send(PacketDistributor.PLAYER.with(() -> player.local()), new UIPacketF(com.local()));
		ContainerInterface.SEND_TO_SERVER = com -> CHANNEL.sendToServer(new UIPacketF(com.local()));
	}

	@Mod.EventBusSubscriber(modid = "fcl", bus = Mod.EventBusSubscriber.Bus.FORGE)
	public static class ForgeBusEvents {

		@SubscribeEvent
		public static void onAttachCaps(AttachCapabilitiesEvent<Entity> event){
			if(event.getObject() instanceof LivingEntity){
				event.addCapability(new ResourceLocation("fcl:passenger"), new PassProvider(event.getObject()));
			}
		}

	}

	@Mod.EventBusSubscriber(modid = "fcl", bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class ModBusEvents {

		@SubscribeEvent
		public void registerCaps(RegisterCapabilitiesEvent event){
			event.register(PassengerUtil.PASS_IMPL);
		}

	}

}
