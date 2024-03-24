package net.fexcraft.mod.fcl.util;

import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Ferdinand Calo' (FEX___96)
 */
public class PassProvider implements ICapabilityProvider {

	public static final Capability<? extends Passenger> CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});
	private LazyOptional<? extends Passenger> optional;
	private Passenger passenger;

	public PassProvider(Entity entity){
		try{
			passenger = PassengerUtil.PASS_IMPL.getConstructor(Entity.class).newInstance(entity);
		}
		catch(Exception e){
			throw new RuntimeException(e);
		}
		optional = LazyOptional.of(() -> passenger);
	}

	@Override
	public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction){
		return optional.cast();
	}

}
