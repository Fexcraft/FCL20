package net.fexcraft.mod.fcl;

import com.mojang.logging.LogUtils;
import net.fexcraft.mod.fcl.util.UIPacket;
import net.fexcraft.mod.uni.EnvInfo;
import net.fexcraft.mod.uni.UniReg;
import net.fexcraft.mod.uni.uimpl.UniCon;
import net.fexcraft.mod.uni.uimpl.UniUI;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

/**
 * @author Ferdinand Calo' (FEX___96)
 */
@Mod(FCL.MODID)
public class FCL {

	public static final String MODID = "fcl";
	private static final Logger LOGGER = LogUtils.getLogger();
    public static final ResourceLocation UI_PACKET = new ResourceLocation(MODID, "ui");
	public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(Registries.MENU, MODID);
    public static final DeferredHolder<MenuType<?>, MenuType<UniCon>> UNIVERSAL = CONTAINERS.register("editor", () -> IMenuTypeExtension.create(UniCon::new));

	public FCL(IEventBus bus){
		EnvInfo.CLIENT = FMLLoader.getDist().isClient();
		EnvInfo.DEV = !FMLEnvironment.production;
		UniReg.LOADER_VERSION = "1.20";
		CONTAINERS.register(bus);
		UniversalAttachments.register(bus);
	}

	@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {

        @SubscribeEvent
        public static void register(final RegisterPayloadHandlerEvent event) {
            final IPayloadRegistrar registrar = event.registrar(MODID).versioned("1.0.0").optional();
            registrar.common(UI_PACKET, UIPacket::read, handler -> {
                handler.server(UIPacket::handle_server);
                handler.client(UIPacket::handle_client);
            });
        }

    }

}
