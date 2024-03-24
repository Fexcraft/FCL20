package net.fexcraft.mod.fcl;

import net.fexcraft.mod.fcl.util.Passenger;
import net.fexcraft.mod.fcl.util.PassengerUtil;
import net.fexcraft.mod.uni.world.EntityW;
import net.minecraft.world.entity.Entity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

/**
 * @author Ferdinand Calo' (FEX___96)
 */
public class UniversalAttachments {

	private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, "fcl");
	public static final Supplier<AttachmentType<? extends EntityW>> PASSENGER = ATTACHMENT_TYPES
		.register("passenger", () -> AttachmentType.builder(iah -> {
			try{
				return PassengerUtil.PASS_IMPL.getConstructor(Entity.class).newInstance(iah);
			}
			catch(Exception e){
				e.printStackTrace();
				return new Passenger((Entity)iah);
			}
		}).build());

	public static void register(IEventBus modbus){
		ATTACHMENT_TYPES.register(modbus);
	}

}
