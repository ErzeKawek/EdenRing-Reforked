package paulevs.edenring.blocks;

import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import paulevs.edenring.blocks.bases.PottableSaplings;
import paulevs.edenring.interfaces.SurvivesOnEdenGrass;
import paulevs.edenring.registries.EdenFeatures;
import paulevs.edenring.world.features.trees.AuritisTreeFeature;

public class AuritisSaplingBlock extends PottableSaplings<AuritisTreeFeature, NoneFeatureConfiguration> implements SurvivesOnEdenGrass {
    public AuritisSaplingBlock() {
        super((level, pos, state, rnd) -> EdenFeatures.placeInWorld(EdenFeatures.AURITIS_TREE, level, pos, rnd));
    }
}
