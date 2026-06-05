package paulevs.edenring.misc.sdf.operators;

import paulevs.edenring.misc.sdf.SDF;

public class SDFScale3D extends SDF.UnaryOperator {

    private float x;
    private float y;
    private float z;

    public SDFScale3D setScale(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    @Override
    public float getDistance(float x, float y, float z) {
        return source.getDistance(x / this.x, y / this.y, z / this.z);
    }
}
