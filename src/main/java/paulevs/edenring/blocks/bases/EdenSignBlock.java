package paulevs.edenring.blocks.bases;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import paulevs.edenring.registries.EdenBlockEntities;

public class EdenSignBlock{

    public static class EdenStandingSignBlock extends StandingSignBlock {

        public static final MapCodec<StandingSignBlock> CODEC =
                RecordCodecBuilder.mapCodec((instance) ->
                        instance.group(
                                        WoodType.CODEC.fieldOf("wood_type")
                                                .forGetter((obj) -> obj.type()),
                                        propertiesCodec())
                                .apply(instance, EdenStandingSignBlock::new));

        public EdenStandingSignBlock(WoodType woodType, Properties properties) {
            super(
                    woodType,
                    properties
                            .forceSolidOn()
                            .instrument(NoteBlockInstrument.BASS)
                            .noCollision()
                            .strength(1.0F)
                            .ignitedByLava()
            );
        }

        @Override
        public MapCodec<StandingSignBlock> codec() {
            return CODEC;
        }

        @Override
        public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return new LighterEndSignBlockEntity(pos, state);
        }
    }

    public static class EdenWallSignBlock extends WallSignBlock {

        public static final MapCodec<WallSignBlock> CODEC =
                RecordCodecBuilder.mapCodec((instance) ->
                        instance.group(
                                        WoodType.CODEC.fieldOf("wood_type")
                                                .forGetter((obj) -> obj.type()),
                                        propertiesCodec())
                                .apply(instance, EdenWallSignBlock::new));

        public EdenWallSignBlock(WoodType woodType, Properties properties) {
            super(
                    woodType,
                    properties
                            .forceSolidOn()
                            .instrument(NoteBlockInstrument.BASS)
                            .noCollision()
                            .strength(1.0F)
                            .ignitedByLava()
            );
        }

        @Override
        public MapCodec<WallSignBlock> codec() {
            return CODEC;
        }

        @Override
        public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return new LighterEndSignBlockEntity(pos, state);
        }
    }

    public static class EdenCeilingHangingSignBlock extends CeilingHangingSignBlock {

        public static final MapCodec<CeilingHangingSignBlock> CODEC =
                RecordCodecBuilder.mapCodec((instance) ->
                        instance.group(
                                        WoodType.CODEC.fieldOf("wood_type")
                                                .forGetter((obj) -> obj.type()),
                                        propertiesCodec())
                                .apply(instance, EdenCeilingHangingSignBlock::new));

        public EdenCeilingHangingSignBlock(WoodType woodType, Properties properties) {
            super(
                    woodType,
                    properties
                            .forceSolidOn()
                            .instrument(NoteBlockInstrument.BASS)
                            .noCollision()
                            .strength(1.0F)
                            .ignitedByLava()
            );
        }

        @Override
        public MapCodec<CeilingHangingSignBlock> codec() {
            return CODEC;
        }

        @Override
        public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return new LighterEndHangingSignBlockEntity(pos, state);
        }
    }

    public static class EdenWallHangingSignBlock extends WallHangingSignBlock {

        public static final MapCodec<WallHangingSignBlock> CODEC =
                RecordCodecBuilder.mapCodec((instance) ->
                        instance.group(
                                        WoodType.CODEC.fieldOf("wood_type")
                                                .forGetter((obj) -> obj.type()),
                                        propertiesCodec())
                                .apply(instance, EdenWallHangingSignBlock::new));

        public EdenWallHangingSignBlock(WoodType woodType, Properties properties) {
            super(
                    woodType,
                    properties
                            .forceSolidOn()
                            .instrument(NoteBlockInstrument.BASS)
                            .noCollision()
                            .strength(1.0F)
                            .ignitedByLava()
            );
        }

        @Override
        public MapCodec<WallHangingSignBlock> codec() {
            return CODEC;
        }

        @Override
        public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return new EdenHangingSignBlockEntity(pos, state);
        }
    }

    public static class EdenSignBlockEntity extends SignBlockEntity {

        public EdenSignBlockEntity(BlockPos pos, BlockState state) {
            super(EdenBlockEntities.SIGN, pos, state);
        }
    }

    public static class EdenHangingSignBlockEntity extends HangingSignBlockEntity {

        public EdenHangingSignBlockEntity(BlockPos pos, BlockState state) {
            super(pos, state);
        }

        @Override
        public BlockEntityType<?> getType() {
            return EdenBlockEntities.HANGING_SIGN;
        }

        @Override
        public boolean isValidBlockState(BlockState blockState) {
            return this.getType().isValid(blockState);
        }
    }
}
