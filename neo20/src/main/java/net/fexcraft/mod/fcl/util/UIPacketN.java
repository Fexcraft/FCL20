package net.fexcraft.mod.fcl.util;

import net.fexcraft.mod.fcl.FCL;
import net.fexcraft.mod.uni.uimpl.UniCon;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * @author Ferdinand Calo' (FEX___96)
 */
public record UIPacketN(CompoundTag com) implements CustomPacketPayload {

    @Override
    public void write(FriendlyByteBuf buf){
        buf.writeNbt(com);
    }

    @Override
    public ResourceLocation id(){
        return FCL.UI_PACKET;
    }

	public static UIPacketN read(FriendlyByteBuf buf){
		return new UIPacketN(buf.readNbt());
	}

	public void handle_client(IPayloadContext context){
		context.workHandler().submitAsync(() -> {
			try{
				((UIPacketReceiver)net.minecraft.client.Minecraft.getInstance().player.containerMenu).onPacket(com, true);
			}
			catch(Exception e){
				FCL.LOGGER.error("Packet Error: " + com.toString());
				if(net.minecraft.client.Minecraft.getInstance().player.containerMenu instanceof UniCon == false){
					net.minecraft.client.Minecraft.getInstance().player.closeContainer();
				}
				for(StackTraceElement elm : e.getStackTrace()){
					FCL.LOGGER.error(elm.toString());
				}
			}
		});
	}

	public void handle_server(IPayloadContext context){
		context.workHandler().submitAsync(() -> {
			try{
				((UIPacketReceiver)context.player().get().containerMenu).onPacket(com, false);
			}
			catch(Exception e){
				FCL.LOGGER.info("Packet Error: " + com.toString());
				if(context.player().get().containerMenu instanceof UniCon == false){
					context.player().get().closeContainer();
				}
				else e.printStackTrace();
			}
		});
	}

}