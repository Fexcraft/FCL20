package net.fexcraft.mod.fcl.util;

import net.fexcraft.mod.uni.UniEntity;
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
public class UniEntityProvider implements ICapabilityProvider {

	public static final Capability<UniEntity> CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});
	private LazyOptional<UniEntity> optional;
	private UniEntity unient;

	public UniEntityProvider(Entity entity){
		unient = new UniEntity().set(entity);
		optional = LazyOptional.of(() -> unient);
	}

	@Override
	public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction){
		return capability == CAPABILITY ? optional.cast() : LazyOptional.empty();
	}

}
