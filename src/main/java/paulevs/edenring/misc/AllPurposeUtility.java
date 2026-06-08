package paulevs.edenring.misc;

import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import org.joml.Vector3f;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public class AllPurposeUtility {

    public static class Flags {
        public static final int FLAG_UPDATE_BLOCK = 1;
        public static final int FLAG_SEND_CLIENT_CHANGES = 2;
        public static final int FLAG_NO_RERENDER = 4;
        public static final int FORSE_RERENDER = 8;
        public static final int FLAG_IGNORE_OBSERVERS = 16;

        public static final int SILENT = FLAG_IGNORE_OBSERVERS | FLAG_SEND_CLIENT_CHANGES;
        public static final int SET_OBSERV = FLAG_UPDATE_BLOCK | FLAG_SEND_CLIENT_CHANGES;

    }

    public static class DirectionalUtility {
        public static final Direction[] HORIZONTAL = makeHorizontal();
        public static final Direction[] DIRECTIONS = Direction.values();

        public static Direction[] makeHorizontal() {
            return new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
        }
    }

    public static int randRange(int min, int max, RandomSource random) {
        return min + random.nextInt(max - min + 1);
    }

    public static double randRange(double min, double max, RandomSource random) {
        return min + random.nextDouble() * (max - min);
    }

    public static float randRange(float min, float max, RandomSource random) {
        return min + random.nextFloat() * (max - min);
    }

    public static float lengthSqr(float x, float y, float z) {
        return x * x + y * y + z * z;
    }

    public static double lengthSqr(double x, double y, double z) {
        return x * x + y * y + z * z;
    }

    public static float length(float x, float y, float z) {
        return (float) Math.sqrt(lengthSqr(x, y, z));
    }

    public static double length(double x, double y, double z) {
        return Math.sqrt(lengthSqr(x, y, z));
    }

    public static float lengthSqr(float x, float y) {
        return x * x + y * y;
    }

    public static double lengthSqr(double x, double y) {
        return x * x + y * y;
    }

    public static float length(float x, float y) {
        return (float) Math.sqrt(lengthSqr(x, y));
    }

    public static double length(double x, double y) {
        return Math.sqrt(lengthSqr(x, y));
    }

    public static int getSeed(int seed, int x, int y) {
        int h = seed + x * 374761393 + y * 668265263;
        h = (h ^ (h >> 13)) * 1274126177;
        return h ^ (h >> 16);
    }

    public static int getSeed(int seed, int x, int y, int z) {
        int h = seed + x * 374761393 + y * 668265263 + z;
        h = (h ^ (h >> 13)) * 1274126177;
        return h ^ (h >> 16);
    }

    public static void offset(List<Vector3f> spline, Vector3f offset) {
        for (Vector3f v : spline) {
            v.set(offset.x() + v.x(), offset.y() + v.y(), offset.z() + v.z());
        }
    }

    public static int floor(double x) {
        return x < 0 ? (int) (x - 1) : (int) x;
    }

    public static int min(int a, int b) {
        return a < b ? a : b;
    }

    public static int min(int a, int b, int c) {
        return min(a, min(b, c));
    }

    public static int max(int a, int b) {
        return a > b ? a : b;
    }

    public static float min(float a, float b) {
        return a < b ? a : b;
    }

    public static float max(float a, float b) {
        return a > b ? a : b;
    }

    public static float max(float a, float b, float c) {
        return max(a, max(b, c));
    }

    public static int max(int a, int b, int c) {
        return max(a, max(b, c));
    }

    public static <T> void shuffle(T[] array, RandomSource random) {
        for (int i = 0; i < array.length; i++) {
            int i2 = random.nextInt(array.length);
            T element = array[i];
            array[i] = array[i2];
            array[i2] = element;
        }
    }

    private static final int ALPHA = 255 << 24;

    public static int color(int r, int g, int b) {
        return ALPHA | (r << 16) | (g << 8) | b;
    }

    public static int color(String hex) {
        int r = Integer.parseInt(hex.substring(0, 2), 16);
        int g = Integer.parseInt(hex.substring(2, 4), 16);
        int b = Integer.parseInt(hex.substring(4, 6), 16);
        return color(r, g, b);
    }

    public class SplineMath {
        public static void fillLineForce(
                Vector3f start,
                Vector3f end,
                WorldGenLevel level,
                BlockState state,
                BlockPos pos,
                Function<BlockState, Boolean> replace
        ) {
            float dx = end.x() - start.x();
            float dy = end.y() - start.y();
            float dz = end.z() - start.z();
            float max = AllPurposeUtility.max(Math.abs(dx), Math.abs(dy), Math.abs(dz));
            int count = AllPurposeUtility.floor(max + 1);
            dx /= max;
            dy /= max;
            dz /= max;
            float x = start.x();
            float y = start.y();
            float z = start.z();
            boolean down = Math.abs(dy) > 0.2;

            BlockState bState;
            BlockPos.MutableBlockPos bPos = new BlockPos.MutableBlockPos();
            for (int i = 0; i < count; i++) {
                bPos.set(x + pos.getX(), y + pos.getY(), z + pos.getZ());
                bState = level.getBlockState(bPos);
                if (replace.apply(bState)) {
                    level.setBlock(bPos, state, Flags.SILENT);
                    bPos.setY(bPos.getY() - 1);
                    bState = level.getBlockState(bPos);
                    if (down && replace.apply(bState)) {
                        level.setBlock(bPos, state, Flags.SILENT);
                    }
                }
                x += dx;
                y += dy;
                z += dz;
            }
            bPos.set(end.x() + pos.getX(), end.y() + pos.getY(), end.z() + pos.getZ());
            bState = level.getBlockState(bPos);
            if (replace.apply(bState)) {
                level.setBlock(bPos, state, Flags.SILENT);
                bPos.setY(bPos.getY() - 1);
                bState = level.getBlockState(bPos);
                if (down && replace.apply(bState)) {
                    level.setBlock(bPos, state, Flags.SILENT);
                }
            }
        }
        public static void fillSplineForce(
                List<Vector3f> spline,
                WorldGenLevel level,
                BlockState state,
                BlockPos pos,
                Function<BlockState, Boolean> replace
        ) {
            Vector3f startPos = spline.get(0);
            for (int i = 1; i < spline.size(); i++) {
                Vector3f endPos = spline.get(i);
                fillLineForce(startPos, endPos, level, state, pos, replace);
                startPos = endPos;
            }
        }
    }

    public class StrW {
        private final Map<ChunkPos, Part> parts = Maps.newHashMap();
        private ChunkPos lastPos;
        private Part lastPart;
        private int minX = Integer.MAX_VALUE;
        private int minY = Integer.MAX_VALUE;
        private int minZ = Integer.MAX_VALUE;
        private int maxX = Integer.MIN_VALUE;
        private int maxY = Integer.MIN_VALUE;
        private int maxZ = Integer.MIN_VALUE;

        public StrW() {
        }

        public StrW(CompoundTag tag) {
            minX = tag.getIntOr("minX", Integer.MAX_VALUE);
            maxX = tag.getIntOr("maxX", Integer.MIN_VALUE);
            minY = tag.getIntOr("minY", Integer.MAX_VALUE);
            maxY = tag.getIntOr("maxY", Integer.MIN_VALUE);
            minZ = tag.getIntOr("minZ", Integer.MAX_VALUE);
            maxZ = tag.getIntOr("maxZ", Integer.MIN_VALUE);

            ListTag map = tag.getList("parts").get();
            map.forEach((element) -> {
                CompoundTag compound = (CompoundTag) element;
                Part part = new Part(compound);
                int x = compound.getInt("x").get();
                int z = compound.getInt("z").get();
                parts.put(new ChunkPos(x, z), part);
            });
        }

        public void setBlock(BlockPos pos, BlockState state) {
            ChunkPos cPos = new ChunkPos(pos.getX(), pos.getZ());

            if (cPos.equals(lastPos)) {
                lastPart.addBlock(pos, state);
                return;
            }

            Part part = parts.get(cPos);
            if (part == null) {
                part = new Part();
                parts.put(cPos, part);

                if (cPos.x() < minX) {
                    minX = cPos.x();
                }
                if (cPos.x() > maxX) {
                    maxX = cPos.x();
                }
                if (cPos.z() < minZ) {
                    minZ = cPos.z();
                }
                if (cPos.z() > maxZ) {
                    maxZ = cPos.z();
                }
            }
            if (pos.getY() < minY) {
                minY = pos.getY();
            }
            if (pos.getY() > maxY) {
                maxY = pos.getY();
            }
            part.addBlock(pos, state);

            lastPos = cPos;
            lastPart = part;
        }

        public boolean placeChunk(WorldGenLevel world, ChunkPos chunkPos) {
            Part part = parts.get(chunkPos);
            if (part != null) {
                ChunkAccess chunk = world.getChunk(chunkPos.x(), chunkPos.z());
                part.placeChunk(chunk);
                return true;
            }
            return false;
        }

        public CompoundTag toBNT() {
            CompoundTag tag = new CompoundTag();
            tag.putInt("minX", minX);
            tag.putInt("maxX", maxX);
            tag.putInt("minY", minY);
            tag.putInt("maxY", maxY);
            tag.putInt("minZ", minZ);
            tag.putInt("maxZ", maxZ);
            ListTag map = new ListTag();
            tag.put("parts", map);
            parts.forEach((pos, part) -> {
                map.add(part.toNBT(pos.x(), pos.z()));
            });
            return tag;
        }

        public BoundingBox getBounds() {
            if (minX == Integer.MAX_VALUE || maxX == Integer.MIN_VALUE || minZ == Integer.MAX_VALUE
                    || maxZ == Integer.MIN_VALUE) {
                return BoundingBox.infinite();
            }
            return new BoundingBox(minX << 4, minY, minZ << 4, (maxX << 4) | 15, maxY, (maxZ << 4) | 15);
        }

        private static final class Part {

            Map<BlockPos, BlockState> blocks = Maps.newHashMap();

            public Part() {
            }

            public Part(CompoundTag tag) {
                ListTag map = tag.getList("blocks").get();
                ListTag map2 = tag.getList("states").get();
                BlockState[] states = new BlockState[map2.size()];
                for (int i = 0; i < states.length; i++) {
                    states[i] = NbtUtils.readBlockState(
                            BuiltInRegistries.BLOCK.freeze(),
                            (CompoundTag) map2.get(i)
                    );
                }

                map.forEach((element) -> {
                    CompoundTag block = (CompoundTag) element;
                    BlockPos pos = toBlockPos(block, "pos").orElse(null);
                    if (pos != null) {
                        int stateID = block.getInt("state").get();
                        BlockState state =
                                stateID < states.length ? states[stateID] : Block.stateById(stateID);
                        blocks.put(pos, state);
                    }
                });
            }

            void addBlock(BlockPos pos, BlockState state) {
                BlockPos inner = new BlockPos(pos.getX() & 15, pos.getY(), pos.getZ() & 15);
                blocks.put(inner, state);
            }

            void placeChunk(ChunkAccess chunk) {
                blocks.forEach((pos, state) -> {
                    chunk.setBlockState(pos, state);
                });
            }

            CompoundTag toNBT(int x, int z) {
                CompoundTag tag = new CompoundTag();
                tag.putInt("x", x);
                tag.putInt("z", z);
                ListTag map = new ListTag();
                tag.put("blocks", map);
                ListTag stateMap = new ListTag();
                tag.put("states", stateMap);

                int[] id = new int[1];
                Map<BlockState, Integer> states = Maps.newHashMap();

                blocks.forEach((pos, state) -> {
                    int stateID = states.getOrDefault(states, -1);
                    if (stateID < 0) {
                        stateID = id[0]++;
                        states.put(state, stateID);
                        stateMap.add(NbtUtils.writeBlockState(state));
                    }

                    CompoundTag block = new CompoundTag();
                    block.put("pos", fromBlockPos(pos));
                    block.putInt("state", stateID);
                    map.add(block);
                });

                return tag;
            }
        }

        public static Optional<BlockPos> toBlockPos(CompoundTag compoundTag, String string) {
            int[] is = compoundTag.getIntArray(string).get();
            return is.length == 3 ? Optional.of(new BlockPos(is[0], is[1], is[2])) : Optional.empty();
        }

        public static IntArrayTag fromBlockPos(BlockPos blockPos) {
            return new IntArrayTag(new int[]{blockPos.getX(), blockPos.getY(), blockPos.getZ()});
        }
    }
}
