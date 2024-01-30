package net.fexcraft.mod.uni.uimpl;

import net.fexcraft.app.json.JsonHandler;
import net.fexcraft.app.json.JsonMap;
import net.fexcraft.lib.common.math.V3I;
import net.fexcraft.mod.fcl.FCL;
import net.fexcraft.mod.fcl.util.UIPacketReceiver;
import net.fexcraft.mod.uni.UniReg;
import net.fexcraft.mod.fcl.UniversalAttachments;
import net.fexcraft.mod.fcl.util.Passenger;
import net.fexcraft.mod.fcl.util.UIPacket;
import net.fexcraft.mod.uni.EnvInfo;
import net.fexcraft.mod.uni.tag.TagCW;
import net.fexcraft.mod.uni.ui.ContainerInterface;
import net.fexcraft.mod.uni.world.EntityW;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;

import java.io.IOException;

/**
 * @author Ferdinand Calo' (FEX___96)
 */
public class UniCon extends AbstractContainerMenu implements UIPacketReceiver {

	protected ItemStack stack;
	protected UniUI screen;
	protected Player player;

	public UniCon(int id, Inventory inv, String coninpos, FriendlyByteBuf buffer, V3I pos){
		super(FCL.UNIVERSAL.get(), id);
		stack = inv.player.getItemInHand(InteractionHand.MAIN_HAND);
		player = inv.player;
		ui_type = coninpos == null ? buffer.readUtf(buffer.readInt()) : coninpos;
		JsonMap map = getJson(UniReg.MENU_JSON.get(ui_type) + ".json");
		pos = buffer == null ? pos : new V3I(buffer.readInt(), buffer.readInt(), buffer.readInt());
		Passenger pass = inv.player.getData(UniversalAttachments.PASSENGER);
		try{
			con = UniReg.MENU.get(ui_type).getConstructor(JsonMap.class, EntityW.class, V3I.class).newInstance(map, pass, pos);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		con.SEND_TO_CLIENT = com -> PacketDistributor.PLAYER.with((ServerPlayer)player).send(new UIPacket(com.local()));;
		con.SEND_TO_SERVER = com -> PacketDistributor.SERVER.noArg().send(new UIPacket(com.local()));
	}

	protected ContainerInterface con;
	protected String ui_type;
	protected UniUI uni;

	public UniCon(int id, Inventory inv, FriendlyByteBuf buffer){
		this(id, inv, buffer.readUtf(buffer.readInt()), buffer, null);
	}

	@Override
	public boolean stillValid(Player player){
		return (player != null);
	}

	@Override
	public ItemStack quickMoveStack(Player p_38941_, int p_38942_){
		return ItemStack.EMPTY;
	}

	@Override
	public void removed(Player player){
		super.removed(player);
		con.onClosed();
	}

	public void setup(UniUI ui){
		uni = ui;
	}

	public ContainerInterface container(){
		return con;
	}

	public static JsonMap getJson(String loc){
		try{
			return JsonHandler.parse(FCL.class.getClassLoader().getResourceAsStream(loc));
		}
		catch(IOException e){
			e.printStackTrace();
			if(EnvInfo.DEV) throw new RuntimeException(e);
			return new JsonMap();
		}
	}

	@Override
	public void onPacket(CompoundTag com, boolean client){
		con.packet(TagCW.wrap(com), client);
	}

}