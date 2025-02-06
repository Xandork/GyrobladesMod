package net.xandork.gyrobladesmod.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.xandork.gyrobladesmod.GyrobladesMod;
import net.xandork.gyrobladesmod.block.custom.*;
import net.xandork.gyrobladesmod.item.ModItems;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, GyrobladesMod.MOD_ID);

    public static final RegistryObject<Block> GYRO_TABLE = registerBlock("gyro_table",
            () -> new GyroTableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL).sound(SoundType.AMETHYST)));

    public static final RegistryObject<Block> GYRITE_BLOCK = registerBlock("gyrite_block",
            () -> new GyroTableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_BLOCK).sound(SoundType.AMETHYST)));

    public static final RegistryObject<Block> GYRITE_ORE = registerBlock("gyrite_ore",
            () -> new GyroTableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE).sound(SoundType.AMETHYST)));

    public static final RegistryObject<Block> GYRITE_DEEPSLATE_ORE = registerBlock("gyrite_deepslate_ore",
            () -> new GyroTableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_DIAMOND_ORE).sound(SoundType.AMETHYST)));

    public static final RegistryObject<Block> GYRO_ARENA_CORE = registerBlock("gyro_arena_core",
            () -> new GyroArenaCore(BlockBehaviour.Properties.ofFullCopy(Blocks.ICE).friction(0.98f).strength(0.98f).noOcclusion().sound(SoundType.STONE)));

    public static final RegistryObject<Block> GYRO_ARENA_EDGE = registerBlock("gyro_arena_edge",
            () -> new GyroArenaEdge(BlockBehaviour.Properties.ofFullCopy(Blocks.ICE).friction(0.98f).strength(0.98f).noOcclusion().sound(SoundType.STONE)));

    public static final RegistryObject<Block> GYRO_ARENA_CORNER = registerBlock("gyro_arena_corner",
            () -> new GyroArenaCorner(BlockBehaviour.Properties.ofFullCopy(Blocks.ICE).friction(0.98f).strength(0.98f).noOcclusion().sound(SoundType.STONE)));

    public static final RegistryObject<Block> BARREL_ARENA = registerBlock("barrel_arena",
            () -> new BarrelArena(BlockBehaviour.Properties.ofFullCopy(Blocks.ICE).friction(0.98f).strength(0.98f).noOcclusion().sound(SoundType.WOOD)));

    public static final RegistryObject<Block> GYRO_BENCH = registerBlock("gyro_bench",
            () -> new GyroBench(BlockBehaviour.Properties.ofFullCopy(Blocks.CRAFTING_TABLE).sound(SoundType.WOOD)));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name,toReturn);
        return toReturn;
    }

    private static <T extends Block>RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> blocks){
        return ModItems.ITEMS.register(name, () -> new BlockItem(blocks.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
