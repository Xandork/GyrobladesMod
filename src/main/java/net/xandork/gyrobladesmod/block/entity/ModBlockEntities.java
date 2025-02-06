package net.xandork.gyrobladesmod.block.entity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.xandork.gyrobladesmod.GyrobladesMod;
import net.xandork.gyrobladesmod.block.ModBlocks;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, GyrobladesMod.MOD_ID);

    //public static final RegistryObject<BlockEntityType<GyroBenchBlockEntity>> GYRO_BENCH_BE =
            //BLOCK_ENTITIES.register("gyro_bench_be",() -> BlockEntityType.Builder.of(GyroBenchBlockEntity::new, ModBlocks.GYRO_BENCH.get()).build(null);
}
