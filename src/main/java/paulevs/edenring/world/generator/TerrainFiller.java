package paulevs.edenring.world.generator;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;
import paulevs.edenring.noise.InterpolationCell;

public class TerrainFiller {
	private static final BlockState STONE = Blocks.STONE.defaultBlockState();
	
	public static InterpolationCell fill(ChunkAccess chunkAccess) {
		short minY = (short) chunkAccess.getMinY();
		short maxY = (short) chunkAccess.getMaxY();
		ChunkPos chunkPos = chunkAccess.getPos();
		final BlockPos origin = new BlockPos(chunkPos.getMinBlockX(), chunkAccess.getMinY(), chunkPos.getMinBlockZ());
		final short maxCell = (short) ((maxY - minY) / 8 + 1);
		
		TerrainGenerator generator = MultiThreadGenerator.getTerrainGenerator();
		InterpolationCell cellTerrain = new InterpolationCell(generator, 3, maxCell, 8, 8, origin);
		InterpolationCell cellTerrain2 = new InterpolationCell(generator, 4, maxCell + 1, 8, 8, origin.offset(-4, -4, -4));
		
		MutableBlockPos pos = new MutableBlockPos();
		MutableBlockPos wpos = new MutableBlockPos();
		
		short newMinY = (short) Math.min(cellTerrain.getMinY(), cellTerrain2.getMinY());
		short newMaxY = (short) Math.max(cellTerrain.getMaxY(), cellTerrain2.getMaxY());
		minY = (short) Math.max(minY, newMinY);
		maxY = (short) Math.min(maxY, newMaxY);
		
		for (short y = minY; y < maxY; y++) {
			pos.setY(y);
			wpos.setY(y);
			short sectionY = (short) chunkAccess.getSectionIndexFromSectionY(y >> 4);
			if (sectionY < 0 || sectionY >= chunkAccess.getMaxSectionY()) continue;
			LevelChunkSection section = chunkAccess.getSection(sectionY);
			for (int x = 0; x < 16; x++) {
				pos.setX(x);
				wpos.setX(x | origin.getX());
				for (int z = 0; z < 16; z++) {
					pos.setZ(z);
					wpos.setZ(z | origin.getZ());
					float d1 = cellTerrain.get(pos, true);
					float d2 = cellTerrain2.get(wpos, false);
					if ((d1 + d2) * 0.5F > 0) {
						section.setBlockState(x, y & 15, z, STONE, false);
					}
				}
			}
		}
		
		return cellTerrain;
	}
}
