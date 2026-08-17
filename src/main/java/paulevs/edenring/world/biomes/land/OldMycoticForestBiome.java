package paulevs.edenring.world.biomes.land;

import org.betterx.bclib.interfaces.SurfaceMaterialProvider;

import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import paulevs.edenring.registries.EdenBiomes;
import paulevs.edenring.registries.EdenFeatures;
import paulevs.edenring.registries.EdenSounds;
import paulevs.edenring.world.biomes.BiomesCommonMethods;
import paulevs.edenring.world.biomes.EdenBiomeBuilder;
import paulevs.edenring.world.biomes.EdenRingBiome;

public class OldMycoticForestBiome extends EdenRingBiome.Config {
    public OldMycoticForestBiome() {
        super(EdenBiomes.OLD_MYCOTIC_FOREST.location());
    }

    @Override
    protected void addCustomBuildData(EdenBiomeBuilder builder) {
        BiomesCommonMethods.addDefaultLandFeatures(builder);
        builder
                .fogDensity(1F)
                .grassColor(220, 130, 189)
                .plantsColor(152, 90, 131)
                .skyColor(113, 178, 255)
                .fogColor(178, 112, 143)
                .loop(EdenSounds.AMBIENCE_MYCOTIC_FOREST)
                .music(EdenSounds.MUSIC_COMMON)
                .feature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_FOREST)
                .feature(EdenFeatures.OLD_BALLOON_MUSHROOM_TREE)
                .feature(EdenFeatures.BALLOON_MUSHROOM_TREE)
                .feature(EdenFeatures.TALL_BALLOON_MUSHROOM)
                .feature(EdenFeatures.BALLOON_MUSHROOM_SMALL)
                .feature(EdenFeatures.LIMPHIUM)
                .feature(EdenFeatures.TALL_MYCOTIC_GRASS)
                .feature(EdenFeatures.MYCOTIC_GRASS)
                .feature(EdenFeatures.EDEN_VINE)
                .genChance(0.5F);
    }

    @Override
    protected SurfaceMaterialProvider surfaceMaterial() {
        return new EdenRingBiome.DefaultSurfaceMaterialProvider() {
            @Override
            public BlockState getTopMaterial() {
                return EDEN_MYCELIUM;
            }
        };
    }
}
