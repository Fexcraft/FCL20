package net.fexcraft.mod.fcl.util;

import net.fexcraft.mod.uni.world.EntityW;
import net.minecraft.world.entity.Entity;

import java.util.function.Function;

/**
 * @author Ferdinand Calo' (FEX___96)
 */
public class PassengerUtil {

	public static Class<? extends Passenger> PASS_IMPL = Passenger.class;
	public static Function<Entity, ? extends EntityW> GETTER = null;
	public static Passenger.PassengerUIOpen UI_OPENER = null;

	public static <E extends EntityW> E get(Entity entity){
		return (E)GETTER.apply(entity);
	}

}
