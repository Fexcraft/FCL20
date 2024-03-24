package net.fexcraft.mod.fcl;

import net.fexcraft.mod.uni.uimpl.UniUI;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

import static net.neoforged.fml.common.Mod.EventBusSubscriber.Bus.MOD;

@Mod.EventBusSubscriber(modid = "fcl", bus = MOD, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void clientInit(RegisterMenuScreensEvent event){
        event.register(FCL.UNIVERSAL.get(), UniUI::new);
    }

}
