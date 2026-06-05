package paulevs.edenring.blocks.complex;

import paulevs.edenring.registries.EdenBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.ShelfBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ShelfBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class Shelfs extends ShelfBlock {

    public Shelfs(Properties settings) {
        super(settings
        );
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new Entity(pos, state);
    }

    public static class Entity extends ShelfBlockEntity {

        public Entity(BlockPos pos, BlockState state) {
            super(pos, state);
        }

        @Override
        public BlockEntityType<?> getType() {
            return EdenBlockEntities.SHELF;
        }

        @Override
        public boolean isValidBlockState(BlockState blockState) {
            return this.getType().isValid(blockState);
        }
    }
}
