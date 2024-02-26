package net.fexcraft.mod.fcl;

import net.fexcraft.mod.fcl.util.Passenger;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

/**
 * @author Ferdinand Calo' (FEX___96)
 */
public class UniversalAttachments {

	private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, "fsmm");
	public static final Class<? extends Passenger> PASS_IMPL = Passenger.class;
	public static final Supplier<AttachmentType<Passenger>> PASSENGER = ATTACHMENT_TYPES
		.register("passenger", () -> AttachmentType.builder(iah -> {
			try{
				return PASS_IMPL.getConstructor(IAttachmentHolder.class).newInstance(iah);
			}
			catch(Exception e){
				e.printStackTrace();
				return new Passenger(iah);
			}
		}).build());

	public static void register(IEventBus modbus){
		ATTACHMENT_TYPES.register(modbus);
	}

}
