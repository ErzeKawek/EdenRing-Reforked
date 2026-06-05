package paulevs.edenring.misc.sdf.operators;

import net.minecraft.util.Mth;
import paulevs.edenring.misc.sdf.SDF;

public class SDFSmoothUnion extends SDF.BinaryOperator {

    private float radius;

    public SDFSmoothUnion setRadius(float radius) {
        this.radius = radius;
        return this;
    }

    @Override
    public float getDistance(float x, float y, float z) {
        float a = this.sourceA.getDistance(x, y, z);
        float b = this.sourceB.getDistance(x, y, z);
        this.selectValue(a, b);
        float h = Mth.clamp(0.5F + 0.5F * (b - a) / radius, 0F, 1F);
        return Mth.lerp(h, b, a) - radius * h * (1F - h);
    }
}
