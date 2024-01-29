package net.fexcraft.mod.fcl.util;

import net.fexcraft.mod.fcl.FCL;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * @author Ferdinand Calo' (FEX___96)
 */
public record UIPacket(CompoundTag com) implements CustomPacketPayload {

    @Override
    public void write(FriendlyByteBuf buf){
        buf.writeNbt(com);
    }

    @Override
    public ResourceLocation id(){
        return FCL.UI_PACKET;
    }

	public static UIPacket read(FriendlyByteBuf buf){
		return new UIPacket(buf.readNbt());
	}

	public void handle_client(IPayloadContext context){
		context.workHandler().submitAsync(() -> {
			((UIPacketReceiver)net.minecraft.client.Minecraft.getInstance().player.containerMenu).onPacket(com, true);
		});
	}

	public void handle_server(IPayloadContext context){
		context.workHandler().submitAsync(() -> {
			((UIPacketReceiver)context.player().get().containerMenu).onPacket(com, false);
		});
	}

}
