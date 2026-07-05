package paulevs.edenring.registries;

import com.google.common.collect.Maps;
import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.registry.TillableBlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import paulevs.edenring.EdenRing;
import paulevs.edenring.blocks.*;
import paulevs.edenring.blocks.complex.*;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.PushReaction;
import paulevs.edenring.world.features.plants.AquatusFeature;
import paulevs.edenring.world.features.trees.AuritisTreeFeature;
import paulevs.edenring.world.features.trees.BalloonMushroomTreeFeature;
import paulevs.edenring.world.features.trees.PulseTreeFeature;

import java.util.Map;
import java.util.function.Function;

public class EdenBlocks {
	
	public static Block EDEN_GRASS_BLOCK = register(
			"eden_grass",
			EdenGrassBlock::new
	);


	public static Block EDEN_MYCELIUM = register("eden_mycelium", TexturedTerrainBlock::new);

	public static Block AQUATUS_BLOCK = register("aquatus_block",
			settings -> new RotatedPillarBlock(settings.instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WART_BLOCK)));
	public static Block AQUATUS_OUTER_LEAVES = register("aquatus_outer_leaves", AquatusOuterLeaves::new);
	public static Block AQUATUS_ROOTS = register("aquatus_roots", AquatusRoots::new);
	public static Block MOSSY_STONE = register("mossy_stone", MossyStoneBlock::new);
	public static Block GRAVILITE_BLOCK = register("gravilite_block",
			settings -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_BLOCK).lightLevel((blockState) -> 15)));
	public static Block GRAVILITE_LAMP = register("gravilite_lamp",
			settings -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN).lightLevel((blockState) -> 15)));
	public static Block SYMBIOTIC_MOLD = register("symbiotic_mold",
			settings -> new CeilPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_ROOTS).offsetType(BlockBehaviour.OffsetType.NONE)));
	public static Block SYMBIOTIC_MOLD_EMISSIVE = register("symbiotic_mold_emissive",
			settings -> new CeilPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_ROOTS).lightLevel((blockState) -> 13).offsetType(BlockBehaviour.OffsetType.NONE)));
	public static Block VOLVOX_BLOCK = register("volvox_block",
			settings -> new SlimeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SLIME_BLOCK).strength(0.5F)));
	public static Block VOLVOX_BLOCK_DENSE = register("volvox_block_dense",
			settings -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD).strength(1F).sound(SoundType.SLIME_BLOCK)));
	public static Block VOLVOX_BLOCK_DENSE_MOSSY = register("volvox_block_dense_mossy",
			settings -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD).strength(1F).sound(SoundType.SLIME_BLOCK)));
	/*public static Block TALL_BALLOON_MUSHROOM = register("tall_balloon_mushroom",
			settings -> new DoublePlantBlock(BlockBehaviour.Properties.ofFullCopy()))
	public static Block PARIGNUM = register("parignum",
			settings -> new SixSidePlant(BlockBehaviour.Properties.ofFullCopy(Blocks.VINE)));*/
	public static Block GOLDEN_GRASS = register("golden_grass", GoldenGrass::new);





	public static Block AURITIS_SAPLING = register("auritis_sapling",
			settings -> new EdenSaplings(AuritisTreeFeature::new, settings.mapColor(MapColor.GOLD)));
	public static Block BALLOON_MUSHROOM_SMALL = register(
			"balloon_mushroom_small",
			settings -> new EdenSaplings(
					BalloonMushroomTreeFeature::new,
					settings.mapColor(MapColor.COLOR_PINK)
			));
	public static Block PULSE_TREE_SAPLING = register(
			"pulse_tree_sapling",
			settings -> new EdenSaplings(
					PulseTreeFeature::new,
					settings.mapColor(MapColor.COLOR_LIGHT_BLUE)
			)
	);
	public static Block AQUATUS_SAPLING = register("aquatus_sapling",
			settings -> new EdenSaplings(AquatusFeature::new, settings.mapColor(MapColor.GOLD)));

	public static Block AURITIS_LEAVES = register(
			"auritis_leaves",
			settings -> new TintedParticleLeavesBlock(
					0.01F,
					applyLeafSettings(settings.mapColor(MapColor.GOLD))
			)
	);
	// Woodsets
	public static EdenWoodBlocks.EdenWoodSet AURITIS_MATERIAL = new EdenWoodBlocks.EdenWoodSet("auritis", MapColor.COLOR_BROWN, MapColor.GOLD);
	public static EdenWoodBlocks.EdenWoodSet BALLOON_MUSHROOM_MATERIAL = new EdenWoodBlocks.EdenWoodSet("balloon_mushroom", MapColor.COLOR_PURPLE, MapColor.COLOR_PURPLE);
	public static EdenWoodBlocks.EdenWoodSet PULSE_TREE_MATERIAL = new EdenWoodBlocks.EdenWoodSet("pulse_tree", MapColor.COLOR_CYAN, MapColor.COLOR_CYAN);
	public static BrainTreeWoodBlock.BrainTreeWoodSet BRAIN_TREE_MATERIAL = new BrainTreeWoodBlock.BrainTreeWoodSet("brain_tree", MapColor.COLOR_GRAY, MapColor.COLOR_LIGHT_GRAY);

	// Balloon Mushroom Things
	public static Block BALLOON_MUSHROOM_BLOCK = register("balloon_mushroom_block", BalloonMushroomBlock::new, true);

	//public static final Block METAL_SPONGE = register("metal_sponge", new MetalSpongeBlock());
	//public static final Block SOAKED_METAL_SPONGE = register("metal_sponge_soaked", new SoakedMetalSpongeBlock());


	public static void init() {}
	public static Block register(String name, Function<Properties, Block> factory) {
		return register(name, factory, true);
	}

	public static Block register(String name, Function<Properties, Block> factory, boolean hasItem) {
		return register(name, factory, Properties.of(), hasItem);
	}

	private static Block register(String name, Function<Properties, Block> factory, Properties settings) {
		return register(name, factory, settings, true);
	}

	private static Block register(
			String name,
			Function<Properties, Block> factory,
			Properties settings,
			boolean hasItem
	) {
		Identifier id = EdenRing.of(name);
		Block block = factory.apply(settings.setId(ResourceKey.create(Registries.BLOCK, id)));

		if (hasItem) {
			ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, id);
			Registry.register(BuiltInRegistries.ITEM, itemKey,
					new BlockItem(block, new Item.Properties().setId(itemKey)));
		}
		ResourceKey<Block> blockKey = ResourceKey.create(Registries.BLOCK, id);
		return Registry.register(BuiltInRegistries.BLOCK, blockKey, block);
	}

	public static Properties applyLeafSettings(Properties settings) {
		return settings
				.strength(0.2F)
				.randomTicks()
				.sound(SoundType.GRASS)
				.noOcclusion()
				.isValidSpawn(Blocks::ocelotOrParrot)
				.isSuffocating(Blocks::never)
				.isViewBlocking(Blocks::never)
				.ignitedByLava()
				.pushReaction(PushReaction.DESTROY)
				.isRedstoneConductor(Blocks::never);
	}
}
