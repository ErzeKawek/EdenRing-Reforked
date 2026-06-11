package paulevs.edenring.blocks;

import com.google.common.collect.Maps;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class MossyStoneBlock extends GrassBlock implements BonemealableBlock {
	public MossyStoneBlock(Properties settings) {
		super(settings
				.mapColor(MapColor.GRASS)
				.strength(1.5F, 6)
				.sound(SoundType.STONE)
				.pushReaction(PushReaction.BLOCK)
				.instrument(NoteBlockInstrument.BASEDRUM)
		);
	}


	@Override
	public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
		BlockState up = level.getBlockState(pos.above());
		return up.isAir() || up.is(Blocks.STONE);
	}

	@Override
	public boolean isBonemealSuccess(Level var1, RandomSource var2, BlockPos var3, BlockState var4) {
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
		for (int i = 1; i < 4; i++) {
			for (int j = 1; j < 2; j++) {
				for (Direction.Axis axis : Direction.Axis.values()) {
					Direction direction =
					switch (axis) {
						case X -> random.nextFloat() < .5F ? Direction.EAST : Direction.WEST;
						case Z -> random.nextFloat() < .5F ? Direction.NORTH : Direction.SOUTH;
						default -> Direction.NORTH;
					};
					Direction yPos = random.nextFloat() < .2F ? Direction.DOWN : Direction.UP;
					BlockPos pos2 = pos.relative(direction, i).relative(yPos, j);
					BlockState state2 = level.getBlockState(pos2);
					if (state2.is(Blocks.STONE)) {
						level.setBlock(pos2, state2, 2);
					}
				}
			}
		}
	}
}
