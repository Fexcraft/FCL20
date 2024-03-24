package net.fexcraft.mod.fcl.util;

import net.fexcraft.lib.common.math.V3D;
import net.fexcraft.lib.common.math.V3I;
import net.fexcraft.mod.uni.item.StackWrapper;
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
import org.jetbrains.annotations.Nullable;

/**
 * @author Ferdinand Calo' (FEX___96)
 */
public class Passenger implements EntityW {

	protected Entity entity;

	public Passenger(Entity iah){
		entity = iah;
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
	public void openUI(String id, V3I pos){
		PassengerUtil.UI_OPENER.open((Player)entity, id, pos);
	}

	@FunctionalInterface
	public static interface PassengerUIOpen {

		public void open(Player player, String ui, V3I pos);

	}

	@Override
	public String getName(){
		return entity.getName().getString();
	}

	@Override
	public void drop(StackWrapper stack, float height){
		entity.spawnAtLocation(stack.local(), height);
	}

	@Override
	public void send(String s){
		entity.sendSystemMessage(Component.translatable(s));
	}

	@Override
	public void send(String str, Object... args){
		entity.sendSystemMessage(Component.translatable(str, args));
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
