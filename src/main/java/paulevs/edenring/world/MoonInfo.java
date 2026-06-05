package paulevs.edenring.world;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import org.joml.Vector3f;
import paulevs.edenring.misc.AllPurposeUtility;

@Environment(EnvType.CLIENT)
public final class MoonInfo {
	public final float orbitRadius;
	public final float orbitState;
	public final float orbitAngle;
	public final Vector3f color;
	public final float speed;
	public final float size;
	
	public MoonInfo(RandomSource random) {
		orbitState = random.nextFloat() * (float) Math.PI * 2;
		orbitRadius = AllPurposeUtility.randRange(10F, 30F, random);
		orbitAngle = AllPurposeUtility.randRange(-30F, 30F, random);
		speed = AllPurposeUtility.randRange(2F, 6F, random);
		size = AllPurposeUtility.randRange(0.5F, 1.5F, random);
		
		float r = AllPurposeUtility.randRange(0.7F, 1F, random);
		float b = AllPurposeUtility.randRange(0.7F, 1F, random);
		float g = Math.min(r, b) + AllPurposeUtility.randRange(0.01F, 0.05F, random);
		g = Mth.clamp(g, 0, 1);
		color = new Vector3f(r, g, b);
	}
}
