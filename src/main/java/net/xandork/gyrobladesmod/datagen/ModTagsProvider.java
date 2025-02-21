package net.xandork.gyrobladesmod.datagen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagBuilder;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.xandork.gyrobladesmod.GyrobladesMod;
import net.xandork.gyrobladesmod.ModTags;
import net.xandork.gyrobladesmod.item.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModTagsProvider extends TagsProvider<Item> {

    // Corrected constructor signature
    public ModTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, ForgeRegistries.ITEMS.getRegistryKey(), lookupProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // Create ResourceKey<Item> for each item
        ResourceKey<Item> ringKey = ResourceKey.create(ForgeRegistries.ITEMS.getRegistryKey(), ModItems.RING_ITEM.getId());
        ResourceKey<Item> diskKey = ResourceKey.create(ForgeRegistries.ITEMS.getRegistryKey(), ModItems.DISK_ITEM.getId());
        ResourceKey<Item> driverKey = ResourceKey.create(ForgeRegistries.ITEMS.getRegistryKey(), ModItems.DRIVER_ITEM.getId());
        ResourceKey<Item> bladeKey = ResourceKey.create(ForgeRegistries.ITEMS.getRegistryKey(), ModItems.BLADE_ITEM.getId());

        // Use ResourceKey<Item> to add items to their respective tags
        TagAppender<Item> ringTag = this.tag(ModTags.Items.RING_TAG);
        ringTag.add(ringKey);

        TagAppender<Item> diskTag = this.tag(ModTags.Items.DISK_TAG);
        diskTag.add(diskKey);

        TagAppender<Item> driverTag = this.tag(ModTags.Items.DRIVER_TAG);
        driverTag.add(driverKey);

        TagAppender<Item> bladeTag = this.tag(ModTags.Items.BLADE_TAG);
        bladeTag.add(bladeKey);
    }

    @Override
    public String getName() {
        return "Gyroblades Mod Tags";
    }
}