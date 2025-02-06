package net.xandork.gyrobladesmod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.xandork.gyrobladesmod.GyrobladesMod;
import net.xandork.gyrobladesmod.block.ModBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, GyrobladesMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.GYRITE_BLOCK.get())
                //.add(ModBlocks.RAW_ALEXANDRITE_BLOCK.get())
                .add(ModBlocks.GYRITE_ORE.get())
                .add(ModBlocks.GYRITE_DEEPSLATE_ORE.get())
                .add(ModBlocks.GYRO_ARENA_CORE.get())
                .add(ModBlocks.GYRO_ARENA_EDGE.get())
                .add(ModBlocks.GYRO_ARENA_CORNER.get())
                //.add(ModBlocks.MAGIC_BLOCK.get());
        ;
        tag(BlockTags.MINEABLE_WITH_AXE).add(ModBlocks.BARREL_ARENA.get())
        ;

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.GYRO_ARENA_CORE.get())
                .add(ModBlocks.GYRO_ARENA_EDGE.get())
                .add(ModBlocks.GYRO_ARENA_CORNER.get());

        tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.GYRITE_DEEPSLATE_ORE.get())
                .add(ModBlocks.GYRITE_ORE.get())
                .add(ModBlocks.GYRITE_BLOCK.get())
                //.add(ModBlocks.RAW_ALEXANDRITE_BLOCK.get())
            ;
    }
}
