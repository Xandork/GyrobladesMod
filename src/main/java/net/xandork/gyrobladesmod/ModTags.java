package net.xandork.gyrobladesmod;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.Tags;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.xandork.gyrobladesmod.item.ModItems;
import net.xandork.gyrobladesmod.recipe.GyrobladeRecipe;

public class ModTags {

    // Define custom tags for items using TagKey<Item>
    public static class Items {
        public static final TagKey<Item> RING_TAG = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "ring_tag"));
        public static final TagKey<Item> DISK_TAG = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "disk_tag"));
        public static final TagKey<Item> DRIVER_TAG = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "driver_tag"));
        public static final TagKey<Item> BLADE_TAG = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "blade_tag"));
    }

    // Register tags for items
    @SubscribeEvent
    public static void registerTags(IEventBus eventBus) {
        // Tags should not be added directly here, we need data generation for that
        // This method remains empty for now as tags are handled in data providers
    }

    // To wire the tags to the event bus
    public static void register(IEventBus eventBus) {
        eventBus.addListener(ModTags::registerTags); // Register registerTags method to the event bus
    }
}