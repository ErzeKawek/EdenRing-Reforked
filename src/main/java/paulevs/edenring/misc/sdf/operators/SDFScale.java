package paulevs.edenring.misc.sdf.operators;

import paulevs.edenring.misc.sdf.SDF;

public class SDFScale extends SDF.UnaryOperator {

    private float scale;

    public SDFScale setScale(float scale) {
        this.scale = scale;
        return this;
    }

    @Override
    public float getDistance(float x, float y, float z) {
        return source.getDistance(x / scale, y / scale, z / scale) * scale;
    }
}
