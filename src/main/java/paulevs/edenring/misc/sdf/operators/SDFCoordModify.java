package paulevs.edenring.misc.sdf.operators;

import java.util.function.Consumer;
import org.joml.Vector3f;
import paulevs.edenring.misc.sdf.SDF;

public class SDFCoordModify extends SDF.UnaryOperator {

    private final Vector3f pos = new Vector3f();
    private Consumer<Vector3f> function;

    public SDFCoordModify setFunction(Consumer<Vector3f> function) {
        this.function = function;
        return this;
    }

    @Override
    public float getDistance(float x, float y, float z) {
        pos.set(x, y, z);
        function.accept(pos);
        return this.source.getDistance(pos.x(), pos.y(), pos.z());
    }

}
