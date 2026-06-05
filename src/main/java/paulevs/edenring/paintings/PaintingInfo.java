package paulevs.edenring.paintings;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.Nullable;

public class PaintingInfo {
	private final PaintingColorProvider<ClientLevel, BlockPos> provider;
	private final Identifier texture;
	private final Identifier id;
	private final int width;
	private final int height;
	private final int cells;
	private final int rawID;
	
	public PaintingInfo(int rawID, Identifier id, Identifier texture, int width, int height, @Nullable PaintingColorProvider<ClientLevel, BlockPos> provider) {
		this.provider = provider;
		this.texture = texture;
		this.height = height;
		this.width = width;
		this.rawID = rawID;
		this.id = id;
		this.cells = (width >> 4) * (height >> 4);
	}
	
	@Nullable
	public PaintingColorProvider<ClientLevel, BlockPos> getProvider() {
		return provider;
	}
	
	public Identifier getId() {
		return id;
	}
	
	public int getRawID() {
		return rawID;
	}
	
	public Identifier getTexture() {
		return texture;
	}
	
	public int getCells() {
		return cells;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}
