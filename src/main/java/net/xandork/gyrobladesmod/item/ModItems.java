package net.xandork.gyrobladesmod.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.xandork.gyrobladesmod.GyrobladesMod;
import net.xandork.gyrobladesmod.item.custom.*;
import net.xandork.gyrobladesmod.item.custom.RingItem;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, GyrobladesMod.MOD_ID);

    public static final RegistryObject<Item> RING_ITEM = ITEMS.register("ring_item", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DISK_ITEM = ITEMS.register("disk_item", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DRIVER_ITEM = ITEMS.register("driver_item", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BLADE_ITEM = ITEMS.register("blade_item", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> RIPCORD = ITEMS.register("ripcord",
            () -> new RipcordItem(new Item.Properties().durability(500)));

    public static final RegistryObject<Item> STONE_BEIGOMA = ITEMS.register("stone_beigoma",
            () -> new BeigomaItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> SHELL_BEIGOMA = ITEMS.register("shell_beigoma",
            () -> new BeigomaItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> RAW_GYRITE = ITEMS.register("raw_gyrite",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> GYRITE_CRYSTAL = ITEMS.register("gyrite_crystal",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BAI_SHELL = ITEMS.register("bai_shell",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> GYRO_ARENA_ITEM = ITEMS.register("gyro_arena_item",
            () -> new GyroArenaItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> GYROBLADE_ITEM = ITEMS.register("gyroblade_item",
            () -> new GyrobladeItem(new Item.Properties().stacksTo(1).fireResistant())
    );

    public static void register() {
        // Register the items in the mod event bus
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
