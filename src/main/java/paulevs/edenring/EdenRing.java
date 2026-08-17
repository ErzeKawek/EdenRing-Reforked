package paulevs.edenring;

import org.betterx.bclib.api.v2.datafixer.DataFixerAPI;
import org.betterx.bclib.api.v2.datafixer.ForcedLevelPatch;
import org.betterx.bclib.api.v2.datafixer.MigrationProfile;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.betterx.wover.core.api.Logger;
import org.betterx.wover.core.api.ModCore;
import org.betterx.wover.state.api.WorldConfig;
import de.ambertation.wunderlib.utils.Version;
import paulevs.edenring.config.Configs;
import paulevs.edenring.paintings.EdenPaintings;
import paulevs.edenring.registries.*;
import paulevs.edenring.tab.EdenCreativeTabs;
import paulevs.edenring.world.EdenPortal;
import paulevs.edenring.world.generator.EdenBiomeSource;
import paulevs.edenring.world.generator.GeneratorOptions;

public class EdenRing implements ModInitializer {
  public static final ModCore C = ModCore.create("edenring");
  public static final String MOD_ID = C.namespace;
  public static final Logger LOGGER = C.LOG;


public static final ResourceKey<DimensionType> EDEN_RING_TYPE_KEY = ResourceKey.create(Registries.DIMENSION_TYPE, C.mk(MOD_ID));
public static final ResourceKey<Level> EDEN_RING_KEY = ResourceKey.create(Registries.DIMENSION, C.mk(MOD_ID));
	
@Override
public void onInitialize() {
  WorldConfig.registerMod(C); //idk

  GeneratorOptions.init();
  EdenSounds.init();
  EdenBlocks.init();
  EdenBlockEntities.init();
  EdenBiomes.register();
  EdenPaintings.init();
  EdenEntities.init();
  EdenItems.init();
  EdenFeatures.register();
  EdenCreativeTabs.register();
  // EdenRecipes.register(); Use data generation
  EdenParticles.ensureStaticallyLoadedServerside();
  Configs.saveConfigs();
  
  Registry.register(BuiltInRegistries.BIOME_SOURCE, C.mk("biome_source"), EdenBiomeSource.CODEC);
  EdenPortal.init();
  
  DataFixerAPI.registerPatch(() -> new ForcedLevelPatch(C, new Version("0.2.0")) {
    @Override
    protected Boolean runLevelDatPatch(CompoundTag root, MigrationProfile profile) {
      CompoundTag worldGenSettings = root.getCompound("Data").getCompound("WorldGenSettings");
      CompoundTag dimensions = worldGenSettings.getCompound("dimensions");
      String dimensionKey = EDEN_RING_KEY.location().toString();
      
      if (!dimensions.contains(dimensionKey)) {
        long seed = worldGenSettings.getLong("seed");
        CompoundTag dimRoot = new CompoundTag();
        dimensions.put(dimensionKey, dimRoot);
        dimRoot.putString("type", dimensionKey);
        
        CompoundTag generator = new CompoundTag();
        dimRoot.put("generator", generator);
        
        generator.putString("settings", "minecraft:floating_islands");
        generator.putString("type", "minecraft:noise");
        generator.putLong("seed", seed);
        
        CompoundTag biomeSource = new CompoundTag();
        generator.put("biome_source", biomeSource);
        biomeSource.putString("type", "edenring:biome_source");
        
        return true;
      }
      else {
        CompoundTag dimRoot = dimensions.getCompound(dimensionKey);
        CompoundTag generator = dimRoot.getCompound("generator");
        String settings = generator.getString("settings");
        if (!settings.equals("minecraft:floating_islands")) {
          generator.putString("settings", "minecraft:floating_islands");
          return true;
        }
      }
      
      return false;
    }
  });
  
  final ResourceLocation[] possibleLocations = new ResourceLocation[] {
    ResourceLocation.parse("chests/end_city_treasure"),
    ResourceLocation.parse("chests/buried_treasure"),
    ResourceLocation.parse("chests/desert_pyramid"),
    ResourceLocation.parse("chests/jungle_temple"),
    ResourceLocation.parse("chests/pillager_outpost"),
    ResourceLocation.parse("chests/shipwreck_treasure"),
    ResourceLocation.parse("chests/simple_dungeon")
  };
  LootTableEvents.MODIFY.register((id, table, source) -> {
    for (ResourceLocation resourceLocation: possibleLocations) {
      if (id.location().equals(resourceLocation)) {
        LootPool.Builder builder = LootPool.lootPool();
        builder.setRolls(ConstantValue.exactly(1));
        builder.conditionally(LootItemRandomChanceCondition.randomChance(0.4f).build());
        builder.add(LootItem.lootTableItem(EdenItems.GUIDE_BOOK));
        table.withPool(builder);
        break;
      }
    }
  });
}

public static ResourceLocation makeID(String path) {
  return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
}
}
