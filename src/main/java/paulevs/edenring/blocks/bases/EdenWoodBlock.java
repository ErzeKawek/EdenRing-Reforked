package paulevs.edenring.blocks.bases;

import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import paulevs.edenring.EdenRing;

import java.util.List;

public static class Wood {

    public final String baseName;
    public final WoodType woodType;
    public final Block log;
    public final Block strippedLog;
    public final Block wood;
    public final Block strippedWood;
    public final Block planks;
    public final Block slab;
    public final Block stairs;
    public final Block door;
    public final Block trapdoor;
    public final Block fence;
    public final Block gate;
    public final Block button;
    public final Block pressurePlate;
    public final Block ladder;
    public final Block sign;
    public final Block wallSign;
    public final Block hangingSign;
    public final Block wallHangingSign;
    // public final Block stool;
    public final List<Block> blocks;
    private final MapColor woodColor;


    public Wood(String name, MapColor barkColor, MapColor woodColor) {
        this.baseName = name;
        this.woodColor = woodColor;

        // TODO: Add individual sound sets (BlockSetType)
        woodType = new WoodTypeBuilder().register(
                EdenRing.of(baseName),
                BlockSetType.CHERRY
        );

        log = register(baseName + "_log", settings -> new PillarBlock(applyLogSettings(
                settings.mapColor(
                        state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? woodColor : barkColor))));
        strippedLog = register(baseName + "_stripped_log",
                settings -> new PillarBlock(applyLogSettings(settings.mapColor(woodColor))));
        wood = register(baseName + "_wood",
                settings -> new PillarBlock(applyLogSettings(settings.mapColor(barkColor))));
        strippedWood = register(baseName + "_stripped_wood",
                settings -> new PillarBlock(applyLogSettings(settings.mapColor(woodColor))));

        StrippableBlockRegistry.register(log, strippedLog);
        StrippableBlockRegistry.register(wood, strippedWood);

        planks = register(baseName + "_planks", settings -> new Block(applyPlankSettings(settings)));
        slab = register(baseName + "_slab", settings -> new SlabBlock(applyPlankSettings(settings)));
        stairs = register(baseName + "_stairs",
                settings -> new StairsBlock(planks.getDefaultState(), applyPlankSettings(settings)));

        door = register(baseName + "_door", settings -> new DoorBlock(BlockSetType.CHERRY,
                settings.mapColor(planks.getDefaultMapColor())
                        .instrument(NoteBlockInstrument.BASS)
                        .strength(3.0F)
                        .nonOpaque()
                        .burnable()
                        .pistonBehavior(PistonBehavior.DESTROY)));
        trapdoor = register(baseName + "_trapdoor", settings -> new TrapdoorBlock(BlockSetType.CHERRY,
                settings.mapColor(planks.getDefaultMapColor())
                        .instrument(NoteBlockInstrument.BASS)
                        .strength(3.0F)
                        .nonOpaque()
                        .allowsSpawning(Blocks::never)
                        .burnable()));
        fence = register(baseName + "_fence",
                settings -> new FenceBlock(settings.mapColor(planks.getDefaultMapColor())
                        .instrument(NoteBlockInstrument.BASS)
                        .strength(2.0F, 3.0F)
                        .burnable()
                        .sounds(BlockSoundGroup.CHERRY_WOOD)));
        gate = register(baseName + "_fence_gate", settings -> new FenceGateBlock(woodType,
                settings.mapColor(planks.getDefaultMapColor()).solid()
                        .instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).burnable()));
        button = register(baseName + "_button", settings -> new ButtonBlock(BlockSetType.CHERRY, 30,
                settings.noCollision().strength(0.5F).pistonBehavior(PistonBehavior.DESTROY)));
        pressurePlate = register(baseName + "_pressure_plate",
                settings -> new PressurePlateBlock(BlockSetType.CHERRY,
                        settings.mapColor(planks.getDefaultMapColor())
                                .solid()
                                .instrument(NoteBlockInstrument.BASS)
                                .noCollision()
                                .strength(0.5F)
                                .burnable()
                                .pistonBehavior(PistonBehavior.DESTROY)));
        ladder = register(baseName + "_ladder", settings -> new LadderBlock(
                settings.strength(0.4F).sounds(BlockSoundGroup.LADDER).nonOpaque()
                        .pistonBehavior(PistonBehavior.DESTROY)));
        sign = register(baseName + "_sign",
                settings -> new Signs.LighterEndStandingSignBlock(woodType,
                        settings.mapColor(planks.getDefaultMapColor())), false);
        wallSign = register(baseName + "_wall_sign",
                settings -> new Signs.LighterEndWallSignBlock(woodType,
                        settings.mapColor(planks.getDefaultMapColor()).lootTable(sign.getLootTableKey())
                                .overrideTranslationKey(sign.getTranslationKey())), false);
        Registry.register(Registries.ITEM, LighterEnd.of(baseName + "_sign"),
                new SignItem(sign, wallSign, new Item.Settings().maxCount(16).registryKey(
                                RegistryKey.of(RegistryKeys.ITEM,
                                        LighterEnd.of(baseName + "_sign")))
                        .useBlockPrefixedTranslationKey()));
        hangingSign = register(baseName + "_hanging_sign",
                settings -> new Signs.LighterEndCeilingHangingSignBlock(woodType,
                        settings.mapColor(planks.getDefaultMapColor())), false);
        wallHangingSign = register(baseName + "_wall_hanging_sign",
                settings -> new Signs.LighterEndWallHangingSignBlock(woodType,
                        settings.lootTable(hangingSign.getLootTableKey())
                                .overrideTranslationKey(hangingSign.getTranslationKey())
                                .mapColor(planks.getDefaultMapColor())), false);
        Registry.register(Registries.ITEM,
                LighterEnd.of(baseName + "_hanging_sign"),
                new HangingSignItem(hangingSign, wallHangingSign,
                        new Item.Settings().maxCount(16).registryKey(
                                        RegistryKey.of(RegistryKeys.ITEM,
                                                LighterEnd.of(baseName + "_hanging_sign")))
                                .useBlockPrefixedTranslationKey()));

        for (Block block : Arrays.asList(log, strippedLog, wood, strippedWood)) {
            FlammableBlockRegistry.getDefaultInstance().add(block, 5, 5);
        }
        for (Block block : Arrays.asList(planks, slab, stairs, fence, gate)) {
            FlammableBlockRegistry.getDefaultInstance().add(block, 5, 20);
        }

        blocks = Arrays.asList(log, strippedLog, wood, strippedWood, planks, slab, stairs, door,
                trapdoor, fence, gate, button, pressurePlate, sign, hangingSign);
    }

    public Settings applyLogSettings(Settings settings) {
        return settings
                .instrument(NoteBlockInstrument.BASS)
                .strength(2.0F)
                .sounds(BlockSoundGroup.WOOD)
                .burnable();
    }

    public Settings applyPlankSettings(Settings settings) {
        return settings
                .mapColor(this.woodColor)
                .instrument(NoteBlockInstrument.BASS)
                .strength(2.0F, 3.0F)
                .sounds(BlockSoundGroup.WOOD)
                .burnable();
    }
