package paulevs.edenring.registries;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import paulevs.edenring.EdenRing;

public class EdenSounds {
	public static final SoundEvent BLOCK_ELECTRIC = register("block.electric");
	public static final Holder<SoundEvent> MUSIC_COMMON = registerReference("music.common");
	
	public static final Holder<SoundEvent> AMBIENCE_BRAINSTORM = registerReference("ambience.brainstorm");
	public static final Holder<SoundEvent> AMBIENCE_GOLDEN_FOREST = registerReference("ambience.golden_forest");
	public static final Holder<SoundEvent> AMBIENCE_LAKESIDE_DESSERT = registerReference("ambience.lakeside_dessert");
	public static final Holder<SoundEvent> AMBIENCE_MYCOTIC_FOREST = registerReference("ambience.mycotic_forest");
	public static final Holder<SoundEvent> AMBIENCE_PULSE_FOREST = registerReference("ambience.pulse_forest");
	public static final Holder<SoundEvent> AMBIENCE_WIND_VALLEY = registerReference("ambience.wind_valley");
	
	public static final SoundEvent DISKWING_AMBIENT = register("entity.diskwing.ambient");
	public static final SoundEvent DISKWING_DAMAGE = register("entity.diskwing.damage");

	public static SoundEvent register(String name) {
		Identifier id = EdenRing.of(name);
		return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
	}

	public static Holder.Reference<SoundEvent> registerReference(String name) {
		Identifier id = EdenRing.of(name);
		return Registry.registerForHolder(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
	}
	
	public static void init() {}
}
