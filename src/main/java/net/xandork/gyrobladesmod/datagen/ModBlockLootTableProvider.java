package net.xandork.gyrobladesmod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import net.xandork.gyrobladesmod.block.ModBlocks;
import net.xandork.gyrobladesmod.item.ModItems;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider(HolderLookup.Provider pRegistries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), pRegistries);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.GYRITE_BLOCK.get());
        dropSelf(ModBlocks.GYRO_TABLE.get());
        dropSelf(ModBlocks.BARREL_ARENA.get());
        //dropSelf(ModBlocks.GYRO_ARENA_CORE.get());
        dropSelf(ModBlocks.GYRO_BENCH.get());
        dropOther(ModBlocks.GYRO_ARENA_CORE.get(),ModItems.GYRO_ARENA_ITEM.get());
        dropOther(ModBlocks.GYRO_ARENA_EDGE.get(), Items.AIR);
        dropOther(ModBlocks.GYRO_ARENA_CORNER.get(),Items.AIR);
        //dropSelf(ModBlocks.RAW_ALEXANDRITE_BLOCK.get());
        // dropSelf(ModBlocks.MAGIC_BLOCK.get());

        this.add(ModBlocks.GYRITE_ORE.get(),
                block -> createOreDrop(ModBlocks.GYRITE_ORE.get(), ModItems.RAW_GYRITE.get()));
        this.add(ModBlocks.GYRITE_DEEPSLATE_ORE.get(),
                block -> createMultipleOreDrops(ModBlocks.GYRITE_DEEPSLATE_ORE.get(), ModItems.RAW_GYRITE.get(), 2, 6));
    }

    protected LootTable.Builder createMultipleOreDrops(Block pBlock, Item item, float minDrops, float maxDrops) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(
                pBlock, this.applyExplosionDecay(
                        pBlock, LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                                .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))
                )
        );
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
