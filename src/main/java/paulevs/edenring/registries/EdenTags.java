package paulevs.edenring.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import paulevs.edenring.EdenRing;

public class EdenTags {

    public static final TagKey<Biome> EDEN = TagKey.create(
            Registries.BIOME,
            EdenRing.of("is_eden")
    );
}
