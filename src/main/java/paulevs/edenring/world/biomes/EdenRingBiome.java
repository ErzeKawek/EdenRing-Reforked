package paulevs.edenring.world.biomes;

import com.mojang.serialization.MapCodec;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.SurfaceRules;
import org.betterx.bclib.interfaces.SurfaceMaterialProvider;
import org.betterx.wover.biome.api.data.BiomeData;
import org.betterx.wover.biome.api.data.BiomeGenerationDataContainer;
import org.betterx.wover.generator.api.biomesource.WoverBiomeData;
import org.betterx.wover.surface.api.Conditions;
import org.betterx.wover.surface.api.SurfaceRuleBuilder;
import org.betterx.wover.surface.impl.BaseSurfaceRuleBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import paulevs.edenring.EdenRing;
import paulevs.edenring.registries.EdenBlocks;

public class EdenRingBiome extends WoverBiomeData implements SurfaceMaterialProvider {
    @SuppressWarnings("null")
    public static final MapCodec<EdenRingBiome> CODEC = codec(
            SurfaceMaterialProvider.CODEC.fieldOf("surface")
                    .orElse(Config.DEFAULT_MATERIAL)
                    .forGetter(o -> o.surfMatProv),
            EdenRingBiome::new
    );

    public static final KeyDispatchDataCodec<EdenRingBiome> KEY_CODEC = KeyDispatchDataCodec.of(CODEC);

    protected EdenRingBiome (
            float fogDensity,
            @NotNull ResourceKey<Biome> biome,
            @NotNull BiomeGenerationDataContainer generatorData,
            float terrainHeight,
            float genChance,
            int edgeSize,
            boolean vertical,
            @Nullable ResourceKey<Biome> edge,
            @Nullable ResourceKey<Biome> parent,
            SurfaceMaterialProvider surface
    ) {
        super(
                fogDensity, biome, generatorData, terrainHeight,
                genChance, edgeSize, vertical, edge, parent
        );
        this.surfMatProv = surface;
    }

    public void datagenSetup(BootstrapContext<BiomeData> dataContext) {
    }

    public KeyDispatchDataCodec<? extends WoverBiomeData> codec() {
        return KEY_CODEC;
    }

    public static class DefaultSurfaceMaterialProvider implements SurfaceMaterialProvider {
        public static final BlockState DIRT = Blocks.DIRT.defaultBlockState();
        public static final BlockState DRIPSTONE_BLOCK = Blocks.DRIPSTONE_BLOCK.defaultBlockState();
        public static final BlockState EDEN_GRASS_BLOCK = EdenBlocks.EDEN_GRASS_BLOCK.defaultBlockState();
        public static final BlockState EDEN_MOSS = EdenBlocks.EDEN_MOSS.defaultBlockState();
        public static final BlockState EDEN_MYCELIUM = EdenBlocks.EDEN_MYCELIUM.defaultBlockState();
        public static final BlockState STONE = Blocks.STONE.defaultBlockState();

        @Override
        public BlockState getTopMaterial() {
            return EDEN_GRASS_BLOCK;
        }

        @Override
        public BlockState getAltTopMaterial() {
            return getTopMaterial();
        }

        @Override
        public BlockState getUnderMaterial() {
            return DIRT;
        }

        public int subSurfaceDepth() {
            return 3;
        }

        @Override
        public boolean generateFloorRule() {
            return true;
        }

        public boolean generateSubSurfaceRule() {
            return true;
        }

        @Override
        public SurfaceRuleBuilder surface() {
            SurfaceRuleBuilder builder = SurfaceRuleBuilder.start();

            if (generateFloorRule() && getTopMaterial() != getUnderMaterial()) {
                if (getTopMaterial() == getAltTopMaterial()) {
                    builder.surface(getTopMaterial());
                } else {
                    SurfaceRules.RuleSource chanced = SurfaceRules.ifTrue(
                            SurfaceRules.ON_FLOOR,
                            SurfaceRules.sequence(
                                    SurfaceRules.ifTrue(Conditions.DOUBLE_BLOCK_SURFACE_NOISE, SurfaceRules.state(getTopMaterial())),
                                    SurfaceRules.state(getAltTopMaterial())
                            )
                    );
                    builder.rule(chanced, BaseSurfaceRuleBuilder.TOP_SURFACE_PRIORITY);
                }
            }
            if (generateSubSurfaceRule()) {
                builder.subsurface(getUnderMaterial(), subSurfaceDepth());
            }
            return builder;
        }
    }

    public abstract static class Config {
        public static final SurfaceMaterialProvider DEFAULT_MATERIAL = new DefaultSurfaceMaterialProvider();

        public final ResourceLocation ID;

        protected Config(String name) {
            this.ID = EdenRing.makeID(name);
        }

        protected Config(ResourceLocation ID) {
            this.ID = ID;
        }

        protected abstract void addCustomBuildData(EdenBiomeBuilder builder);

        public EdenBiomeBuilder.BiomeFactory getSupplier() {
            return EdenRingBiome::new;
        }

        protected SurfaceMaterialProvider surfaceMaterial() {
            return DEFAULT_MATERIAL;
        }
    }

    protected SurfaceMaterialProvider surfMatProv = Config.DEFAULT_MATERIAL;

    protected void setSurfaceMaterial(SurfaceMaterialProvider prov) {
        this.surfMatProv = prov;
    }

    @Override
    public BlockState getTopMaterial() {
        return this.surfMatProv.getTopMaterial();
    }

    @Override
    public BlockState getUnderMaterial() {
        return this.surfMatProv.getUnderMaterial();
    }

    @Override
    public BlockState getAltTopMaterial() {
        return this.surfMatProv.getAltTopMaterial();
    }

    @Override
    public boolean generateFloorRule() {
        return this.surfMatProv.generateFloorRule();
    }

    @Override
    public SurfaceRuleBuilder surface() {
        return this.surfMatProv.surface();
    }
}
