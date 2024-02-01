package net.fexcraft.mod.fcl.util;

import net.fexcraft.lib.common.math.V3D;
import net.fexcraft.lib.common.math.V3I;
import net.fexcraft.mod.uni.uimpl.UniCon;
import net.fexcraft.mod.uni.world.EntityW;
import net.fexcraft.mod.uni.world.WorldW;
import net.fexcraft.mod.uni.world.WrapperHolder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import org.jetbrains.annotations.Nullable;

/**
 * @author Ferdinand Calo' (FEX___96)
 */
public class Passenger extends EntityW {

	private Entity entity;

	public Passenger(IAttachmentHolder iah){
		entity = (Entity)iah;
	}

	@Override
	public boolean isOnClient(){
		return entity.level().isClientSide;
	}

	@Override
	public int getId(){
		return entity.getId();
	}

	@Override
	public WorldW getWorld(){
		return WrapperHolder.getWorld(entity.level());
	}

	@Override
	public boolean isPlayer(){
		return entity instanceof Player;
	}

	@Override
	public boolean isAnimal(){
		return entity instanceof Animal;
	}

	@Override
	public boolean isHostile(){
		return entity instanceof Mob;
	}

	@Override
	public boolean isLiving(){
		return entity instanceof LivingEntity;
	}

	@Override
	public boolean isRiding(){
		return entity.isPassenger();
	}

	@Override
	public String getRegName(){
		return BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString();
	}

	@Override
	public <E> E local(){
		return (E)entity;
	}

	@Override
	public Object direct(){
		return entity;
	}

	@Override
	public V3D getPos(){
		return new V3D(entity.position().x, entity.position().y, entity.position().z);
	}

	@Override
	public void decreaseXZMotion(double x){
		//
	}

	@Override
	public void setYawPitch(float oyaw, float opitch, float yaw, float pitch){
		//
	}

	@Override
	public void openUI(int ui, WorldW world, V3I pos){
		//
	}

	@Override
	public void openUI(String id, V3I pos){
		((Player)entity).openMenu(new MenuProvider(){
			@Override
			public Component getDisplayName(){
				return Component.literal("Fexcraft Universal UI");
			}
			@Nullable
			@Override
			public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player){
				return new UniCon(i, inventory, id, null, pos);
			}
		}, buf -> {
			buf.writeInt(id.length());
			buf.writeUtf(id);
			buf.writeInt(pos.x);
			buf.writeInt(pos.y);
			buf.writeInt(pos.z);
		});
	}

	@Override
	public String getName(){
		return entity.getName().getString();
	}

	@Override
	public void send(String s){
		entity.sendSystemMessage(Component.translatable(s));
	}

	@Override
	public void bar(String s){
		entity.sendSystemMessage(Component.translatable(s));//TODO
	}

	@Override
	public void dismount(){
		entity.unRide();
	}

}