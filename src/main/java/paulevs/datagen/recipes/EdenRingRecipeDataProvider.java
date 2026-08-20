package paulevs.datagen.recipes;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.betterx.bclib.complexmaterials.WoodenComplexMaterial;
import paulevs.edenring.EdenRing;
import paulevs.edenring.blocks.VolvoxBlock;
import paulevs.edenring.registries.EdenBlocks;
import paulevs.edenring.registries.EdenItems;
import paulevs.edenring.registries.EdenItems.*;
import static net.minecraft.data.recipes.ShapedRecipeBuilder.shaped;
import static net.minecraft.data.recipes.ShapelessRecipeBuilder.shapeless;
import static net.minecraft.world.item.Items.*;

import java.util.concurrent.CompletableFuture;

public class EdenRingRecipeDataProvider extends FabricRecipeProvider {
    public EdenRingRecipeDataProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> future) {
        super(output, future);
    }

    @Override
    public void buildRecipes(RecipeOutput output) {

        shaped(RecipeCategory.MISC, EdenBlocks.GRAVILITE_BLOCK)
                .define('#', EdenBlocks.GRAVILITE_SHARDS)
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_gravilite_shards", has(EdenBlocks.GRAVILITE_SHARDS))
                .save(output);

        shapeless(RecipeCategory.MISC, EdenBlocks.GRAVILITE_SHARDS, 4)
                .requires(EdenBlocks.GRAVILITE_BLOCK)
                .group("gravilite")
                .unlockedBy("has_gravilite_block", has(EdenBlocks.GRAVILITE_BLOCK))
                .save(output);

        shaped(RecipeCategory.BUILDING_BLOCKS, EdenBlocks.GRAVILITE_LAMP)
                .define('#', EdenBlocks.GRAVILITE_BLOCK)
                .define('I', IRON_INGOT)
                .pattern(" I ")
                .pattern("I#I")
                .pattern(" I ")
                .unlockedBy("has_gravilite_block", has(EdenBlocks.GRAVILITE_BLOCK))
                .save(output);

        shaped(RecipeCategory.BUILDING_BLOCKS, EdenBlocks.GRAVILITE_LANTERN, 2)
                .define('#', EdenBlocks.GRAVILITE_SHARDS)
                .define('I', Items.IRON_INGOT)
                .pattern(" I ")
                .pattern("I#I")
                .pattern(" I ")
                .unlockedBy("has_gravilite_block", has(EdenBlocks.GRAVILITE_BLOCK))
                .save(output);

        shaped(RecipeCategory.BUILDING_BLOCKS, EdenBlocks.GRAVILITE_LANTERN_TALL)
                .define('#', EdenBlocks.GRAVILITE_SHARDS)
                .define('I', IRON_INGOT)
                .pattern(" I ")
                .pattern(" # ")
                .pattern(" I ")
                .unlockedBy("has_gravilite_block", has(EdenBlocks.GRAVILITE_BLOCK))
                .save(output);

        Block log = EdenBlocks.BALLOON_MUSHROOM_MATERIAL.getBlock(WoodenComplexMaterial.BLOCK_LOG);
        shaped(RecipeCategory.MISC, log)
                .define('#', EdenBlocks.BALLOON_MUSHROOM_STEM)
                .pattern("##")
                .pattern("##")
                .group("balloon_mushroom")
                .unlockedBy("has_balloon_mushroom", has(EdenBlocks.BALLOON_MUSHROOM_STEM))
                .save(output);

        shapeless(RecipeCategory.MISC, EdenBlocks.BALLOON_MUSHROOM_BRANCH)
                .requires(EdenBlocks.BALLOON_MUSHROOM_STEM)
                .unlockedBy("has_balloon_mushroom_stem", has(EdenBlocks.BALLOON_MUSHROOM_STEM))
                .save(output);

        shapeless(RecipeCategory.MISC, EdenBlocks.BALLOON_MUSHROOM_STEM)
                .requires(EdenBlocks.BALLOON_MUSHROOM_BRANCH)
                .unlockedBy("has_balloon_mushroom_branch", has(EdenBlocks.BALLOON_MUSHROOM_BRANCH))
                .save(output);

        shaped(RecipeCategory.MISC, EdenBlocks.BALLOON_MUSHROOM_STEM, 8)
                .define('#', log)
                .pattern("#")
                .pattern("#")
                .unlockedBy("has_balloon_mushroom_log", has(log))
                .save(output, ResourceLocation.fromNamespaceAndPath("edenring", "balloon_mushroom_stem_from_log"));

        //Block pulselog = EdenBlocks.PULSE_TREE_MATERIAL.getBlock(WoodenComplexMaterial.BLOCK_LOG);
        shaped(RecipeCategory.MISC, EdenBlocks.PULSE_TREE_MATERIAL.getBlock(WoodenComplexMaterial.BLOCK_LOG))
                .define('#', EdenBlocks.PULSE_TREE)
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_pulse_tree", has(EdenBlocks.PULSE_TREE))
                .save(output);

        shapeless(RecipeCategory.DECORATIONS, YELLOW_DYE)
                .requires(EdenBlocks.GOLDEN_GRASS)
                .unlockedBy("has_golden_grass", has(EdenBlocks.GOLDEN_GRASS))
                .save(output);

        shapeless(RecipeCategory.DECORATIONS, MAGENTA_DYE)
                .requires(EdenBlocks.VIOLUM)
                .unlockedBy("has_violum", has(EdenBlocks.VIOLUM))
                .save(output);

        shaped(RecipeCategory.MISC, EdenBlocks.GRAVITY_COMPRESSOR)
                .define('#', EdenBlocks.GRAVILITE_BLOCK)
                .define('P', Blocks.PISTON)
                .define('I', Items.IRON_INGOT)
                .define('C', Items.COPPER_INGOT)
                .define('R', Items.REDSTONE)
                .pattern("IPI")
                .pattern("###")
                .pattern("RCR")
                .unlockedBy("has_gravilite_block", has(EdenBlocks.GRAVILITE_BLOCK))
                .save(output);

        shaped(RecipeCategory.MISC, PAPER, 3)
                .define('#', EdenItems.LIMPHIUM_LEAF_DRYED)
                .pattern("###")
                .unlockedBy("has_dried_limphium_leaf", has(EdenItems.LIMPHIUM_LEAF_DRYED))
                .save(output);


        log = EdenBlocks.BRAIN_TREE_MATERIAL.getBlock(WoodenComplexMaterial.BLOCK_LOG);
        Block log2 = EdenBlocks.BRAIN_TREE_MATERIAL.getBlock(WoodenComplexMaterial.BLOCK_STRIPPED_LOG);
        Block log3 = EdenBlocks.BRAIN_TREE_MATERIAL.getBlock(WoodenComplexMaterial.BLOCK_STRIPPED_BARK);
        Block log4 = EdenBlocks.BRAIN_TREE_MATERIAL.getBlock(WoodenComplexMaterial.BLOCK_BARK);

        shaped(RecipeCategory.MISC, EdenBlocks.COPPER_FRAMED_BRAIN_TREE_LOG, 9)
                .define('M', EdenBlocks.BRAIN_TREE_BLOCK_COPPER)
                .define('T', Ingredient.of(log, log2, log3, log4))
                .pattern("MTM")
                .pattern("MTM")
                .pattern("MTM")
                .unlockedBy("has_brain_tree_block_copper", has(EdenBlocks.BRAIN_TREE_BLOCK_COPPER))
                .save(output);

        shaped(RecipeCategory.MISC, EdenBlocks.IRON_FRAMED_BRAIN_TREE_LOG, 9)
                .define('M', EdenBlocks.BRAIN_TREE_BLOCK_IRON)
                .define('T', Ingredient.of(log, log2, log3, log4))
                .pattern("MTM")
                .pattern("MTM")
                .pattern("MTM")
                .unlockedBy("has_brain_tree_block_iron", has(EdenBlocks.BRAIN_TREE_BLOCK_IRON))
                .save(output);

        shaped(RecipeCategory.MISC, EdenBlocks.GOLD_FRAMED_BRAIN_TREE_LOG, 9)
                .define('M', EdenBlocks.BRAIN_TREE_BLOCK_GOLD)
                .define('T', Ingredient.of(log, log2, log3, log4))
                .pattern("MTM")
                .pattern("MTM")
                .pattern("MTM")
                .unlockedBy("has_brain_tree_block_gold", has(EdenBlocks.BRAIN_TREE_BLOCK_GOLD))
                .save(output);

        shaped(RecipeCategory.MISC, EdenBlocks.VOLVOX_BLOCK_DENSE)
                .define('#', EdenBlocks.VOLVOX_BLOCK)
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_volvox_block", has(EdenBlocks.VOLVOX_BLOCK))
                .save(output);

        shapeless(RecipeCategory.MISC, EdenBlocks.VOLVOX_BLOCK, 4)
                .requires(EdenBlocks.VOLVOX_BLOCK_DENSE)
                .unlockedBy("has_volvox_block_dense", has(EdenBlocks.VOLVOX_BLOCK_DENSE))
                .save(output);

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(EdenBlocks.VOLVOX_BLOCK), RecipeCategory.MISC, SLIME_BALL, 0.7F, 4);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(EdenItems.LIMPHIUM_LEAF), RecipeCategory.MISC, EdenItems.LIMPHIUM_LEAF_DRYED, 0.5F, 1);

        Block[] coloredBlocks = EdenBlocks.MYCOTIC_LANTERN_COLORED.values().toArray(new Block[16]);
        EdenBlocks.MYCOTIC_LANTERN_COLORED.forEach(((color, block) -> {
                    shaped(RecipeCategory.MISC, block, 8)
                            .define('#', Ingredient.of(coloredBlocks))
                            .define('D', DyeItem.byColor(color))
                            .pattern("###")
                            .pattern("#D#")
                            .pattern("###")
                            .group("eden_balloon_mushroom_sporocarp")
                            .unlockedBy("has_sporocarp", has(EdenBlocks.BALLOON_MUSHROOM_BRANCH))
                            .save(output);
                }));

        EdenBlocks.BALLOON_MUSHROOM_SPOROCARP_COLORED.values().toArray(coloredBlocks);
        EdenBlocks.BALLOON_MUSHROOM_SPOROCARP_COLORED.forEach((color, block) -> {
            shaped(RecipeCategory.MISC, block, 8)
                    .define('#', Ingredient.of(coloredBlocks))
                    .define('D', DyeItem.byColor(color))
                    .pattern("###")
                    .pattern("#D#")
                    .pattern("###")
                    .group("eden_balloon_mushroom_sporocarp")
                    .unlockedBy("has_sporocarp", has(EdenBlocks.BALLOON_MUSHROOM_BRANCH))
                    .save(output);
        });

        shaped(RecipeCategory.MISC, EdenItems.GUIDE_BOOK, 2)
                .define('B', BOOK)
                .define('E', EdenItems.GUIDE_BOOK)
                .pattern("BE")
                .unlockedBy("has_guidebook", has(EdenItems.GUIDE_BOOK))
                .save(output);
    }
}
