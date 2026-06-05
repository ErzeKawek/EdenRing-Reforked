package paulevs.edenring.misc.sdf.operators;

import paulevs.edenring.misc.sdf.SDF;

public class SDFUnion extends SDF.BinaryOperator {

    @Override
    public float getDistance(float x, float y, float z) {
        float a = this.sourceA.getDistance(x, y, z);
        float b = this.sourceB.getDistance(x, y, z);
        this.selectValue(a, b);
        return Math.min(a, b);
    }
}
