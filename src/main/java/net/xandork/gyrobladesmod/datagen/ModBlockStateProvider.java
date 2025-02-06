package net.xandork.gyrobladesmod.datagen;


import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import net.xandork.gyrobladesmod.GyrobladesMod;
import net.xandork.gyrobladesmod.block.ModBlocks;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, GyrobladesMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.GYRITE_BLOCK);
        //blockWithItem(ModBlocks.RAW_ALEXANDRITE_BLOCK);

        blockWithItem(ModBlocks.GYRITE_ORE);
        blockWithItem(ModBlocks.GYRITE_DEEPSLATE_ORE);
        //blockWithItem(ModBlocks.BARREL_ARENA);
        //blockWithItem(ModBlocks.GYRO_ARENA);

        //blockWithItem(ModBlocks.MAGIC_BLOCK);


        //simpleBlock(ModBlocks.GYRO_BENCH.get(), new ModelFile(modLoc("block/gyro_bench")) {
        //    @Override
        //    protected boolean exists() {
        //        return false;
        //    }
        //});
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
