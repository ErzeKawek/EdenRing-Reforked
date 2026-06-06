package paulevs.edenring.blocks.complex;

import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import paulevs.edenring.EdenRing;
import paulevs.edenring.registries.EdenBlocks;
import paulevs.edenring.registries.EdenSounds;

import java.util.Arrays;
import java.util.List;

public class BrainTreeWoodBlock {

    public static class BrainTreeWoodSet {

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
    public final Block shelf;
    // public final Block stool;
    public final List<Block> blocks;
    private final MapColor woodColor;
    private final SoundType logSounds;

    public BrainTreeWoodSet(String name, MapColor barkColor, MapColor woodColor) {
        this.baseName = name;
        this.woodColor = woodColor;

        this.woodType = createWoodType(baseName);
        this.logSounds = createWoodSoundGroup(baseName + "_log");

        log = EdenBlocks.register(baseName + "_log",
                settings -> new RotatedPillarBlock(
                        applyBrainstromLogSettings(
                                settings.mapColor(
                                        state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y
                                                ? woodColor
                                                : barkColor
                                )
                        )
                )
        );
        strippedLog = EdenBlocks.register(baseName + "_stripped_log",
                settings -> new RotatedPillarBlock(
                        applyBrainstromLogSettings(settings.mapColor(woodColor))
                )
        );
        wood = EdenBlocks.register(baseName + "_wood",
                settings -> new RotatedPillarBlock(
                        applyBrainstromLogSettings(settings.mapColor(barkColor))
                )
        );
        strippedWood = EdenBlocks.register(baseName + "_stripped_wood",
                settings -> new RotatedPillarBlock(
                        applyBrainstromLogSettings(settings.mapColor(woodColor))));

        StrippableBlockRegistry.register(log, strippedLog);
        StrippableBlockRegistry.register(wood, strippedWood);

        planks = EdenBlocks.register(
                baseName + "_planks",
                settings -> new Block(applyBrainstormPlankSettings(settings))
        );
        slab = EdenBlocks.register(
                baseName + "_slab",
                settings -> new SlabBlock(applyBrainstormPlankSettings(settings))
        );
        stairs = EdenBlocks.register(
                baseName + "_stairs",
                settings -> new StairBlock(planks.defaultBlockState(), applyBrainstormPlankSettings(settings))
        );

        door = EdenBlocks.register(
                baseName + "_door",
                settings -> new DoorBlock(
                        woodType.setType(),
                        settings.mapColor(planks.defaultMapColor())
                                .instrument(NoteBlockInstrument.BASS)
                                .strength(5.0F)
                                .noOcclusion()
                                .ignitedByLava()
                                .pushReaction(PushReaction.DESTROY)
                )
        );
        trapdoor = EdenBlocks.register(
                baseName + "_trapdoor",
                settings -> new TrapDoorBlock(
                        woodType.setType(),
                        settings.mapColor(planks.defaultMapColor())
                                .instrument(NoteBlockInstrument.BASS)
                                .strength(5.0F)
                                .noOcclusion()
                                .isValidSpawn(Blocks::never)
                                .ignitedByLava()
                )
        );
        fence = EdenBlocks.register(
                baseName + "_fence",
                settings -> new FenceBlock(
                        settings.mapColor(planks.defaultMapColor())
                                .instrument(NoteBlockInstrument.BASS)
                                .strength(2.0F, 3.0F)
                                .ignitedByLava()
                                .sound(woodType.soundType())
                )
        );
        gate = EdenBlocks.register(
                baseName + "_fence_gate",
                settings -> new FenceGateBlock(
                        woodType,
                        settings
                                .mapColor(planks.defaultMapColor())
                                .forceSolidOn()
                                .instrument(NoteBlockInstrument.BASS)
                                .strength(5.0F, 6.0F)
                                .ignitedByLava()
                )
        );
        button = EdenBlocks.register(
                baseName + "_button",
                settings -> new ButtonBlock(
                        woodType.setType(),
                        30,
                        settings.noCollision().strength(0.5F).pushReaction(PushReaction.DESTROY)
                )
        );
        pressurePlate = EdenBlocks.register(
                baseName + "_pressure_plate",
                settings -> new PressurePlateBlock(
                        woodType.setType(),
                        settings.mapColor(planks.defaultMapColor())
                                .forceSolidOn()
                                .instrument(NoteBlockInstrument.BASS)
                                .noCollision()
                                .strength(1F)
                                .ignitedByLava()
                                .pushReaction(PushReaction.DESTROY)
                )
        );
        ladder = EdenBlocks.register(
                baseName + "_ladder",
                settings -> new LadderBlock(
                        settings
                                .strength(0.8F)
                                .sound(SoundType.LADDER)
                                .noOcclusion()
                                .pushReaction(PushReaction.DESTROY)
                )
        );
        sign = EdenBlocks.register(
                baseName + "_sign",
                settings -> new Signs.EdenStandingSign(
                        woodType,
                        settings.mapColor(planks.defaultMapColor())
                ),
                false
        );
        wallSign = EdenBlocks.register(
                baseName + "_wall_sign",
                settings -> new Signs.EdenWallSignBlock(
                        woodType,
                        settings
                                .mapColor(planks.defaultMapColor())
                                .overrideLootTable(sign.getLootTable())
                                .overrideDescription(sign.getDescriptionId())
                ),
                false
        );
        Registry.register(
                BuiltInRegistries.ITEM,
                EdenRing.of(baseName + "_sign"),
                new SignItem(
                        sign,
                        wallSign,
                        new Item.Properties().stacksTo(16).setId(
                                ResourceKey.create(Registries.ITEM, EdenRing.of(baseName + "_sign"))
                        ).useBlockDescriptionPrefix()
                )
        );
        hangingSign = EdenBlocks.register(
                baseName + "_hanging_sign",
                settings -> new Signs.EdenCeilingHangingSignBlock(
                        woodType,
                        settings.mapColor(planks.defaultMapColor())
                ),
                false
        );
        wallHangingSign = EdenBlocks.register(
                baseName + "_wall_hanging_sign",
                settings -> new Signs.EdenWallHangingSignBlock(
                        woodType,
                        settings
                                .overrideLootTable(hangingSign.getLootTable())
                                .overrideDescription(hangingSign.getDescriptionId())
                                .mapColor(planks.defaultMapColor())
                ),
                false
        );
        Registry.register(
                BuiltInRegistries.ITEM,
                EdenRing.of(baseName + "_hanging_sign"),
                new HangingSignItem(
                        hangingSign,
                        wallHangingSign,
                        new Item.Properties().stacksTo(16).setId(
                                ResourceKey.create(Registries.ITEM, EdenRing.of(baseName + "_hanging_sign"))
                        ).useBlockDescriptionPrefix()
                )
        );
        shelf = EdenBlocks.register(
                baseName + "_shelf",
                settings -> new Shelfs(
                        settings.mapColor(planks.defaultMapColor())
                                .instrument(NoteBlockInstrument.BASS)
                                .sound(SoundType.SHELF)
                                .ignitedByLava()
                                .strength(5.0F, 6.0F)
                )
        );

        for (Block block : Arrays.asList(log, strippedLog, wood, strippedWood)) {
            FlammableBlockRegistry.getDefaultInstance().add(block, 5, 5);
        }
        for (Block block : Arrays.asList(planks, slab, stairs, fence, gate)) {
            FlammableBlockRegistry.getDefaultInstance().add(block, 5, 20);
        }
        FlammableBlockRegistry.getDefaultInstance().add(shelf, 30, 20);

        blocks = Arrays.asList(
                log,
                strippedLog,
                wood,
                strippedWood,
                planks,
                slab,
                stairs,
                door,
                trapdoor,
                fence,
                gate,
                button,
                pressurePlate,
                ladder,
                sign,
                hangingSign,
                shelf
        );
    }

    public Properties applyBrainstromLogSettings(Properties settings) {
        return settings
                .instrument(NoteBlockInstrument.IRON_XYLOPHONE)
                .sound(this.logSounds)
                .strength(5.0F);
    }

    public Properties applyBrainstormPlankSettings(Properties settings) {
        return settings
                .mapColor(this.woodColor)
                .instrument(NoteBlockInstrument.IRON_XYLOPHONE)
                .sound(this.woodType.soundType())
                .strength(3.0F, 3.0F);
    }
}

    public static WoodType createWoodType(String name) {
        SoundType soundGroup = createWoodSoundGroup(name);

        return (new WoodTypeBuilder())
                .soundType(soundGroup)
                .hangingSignSoundType(createWoodSoundGroup(name + "_hanging_sign"))
                .fenceGateCloseSound(EdenSounds.register("block." + name + "_fence_gate.close"))
                .fenceGateOpenSound(EdenSounds.register("block." + name + "_fence_gate.open"))
                .register(EdenRing.of(name), createWoodSetType(name, soundGroup));
    }

    private static BlockSetType createWoodSetType(String name, SoundType soundGroup) {
        return (new BlockSetTypeBuilder())
                .openableByHand(true)
                .openableByWindCharge(true)
                .buttonActivatedByArrows(true)
                .pressurePlateActivationRule(BlockSetType.IRON.pressurePlateSensitivity())
                .soundType(soundGroup)
                .doorCloseSound(EdenSounds.register("block." + name + "_door.close"))
                .doorOpenSound(EdenSounds.register("block." + name + "_door.open"))
                .trapdoorCloseSound(EdenSounds.register("block." + name + "_trapdoor.close"))
                .trapdoorCloseSound(EdenSounds.register("block." + name + "_trapdoor.open"))
                .pressurePlateClickOffSound(
                        EdenSounds.register("block." + name + "_pressure_plate.click_off")
                ).pressurePlateClickOnSound(
                        EdenSounds.register("block." + name + "_pressure_plate.click_on")
                ).register(EdenRing.of(name));

    }

    private static SoundType createWoodSoundGroup(String name) {
        return new SoundType(
                1.0F,
                1.0F,
                EdenSounds.register("block." + name + ".break"),
                EdenSounds.register("block." + name + ".step"),
                EdenSounds.register("block." + name + ".place"),
                EdenSounds.register("block." + name + ".hit"),
                EdenSounds.register("block." + name + ".fall")
        );
    }


}
