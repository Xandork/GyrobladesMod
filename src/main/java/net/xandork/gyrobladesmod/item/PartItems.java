package net.xandork.gyrobladesmod.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.xandork.gyrobladesmod.GyrobladesMod;
import net.xandork.gyrobladesmod.item.custom.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.core.Registry;

public class PartItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, GyrobladesMod.MOD_ID);

    public static final RegistryObject<Item> TEMPLATE_ATTACK = ITEMS.register("template_attack",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TEMPLATE_BALANCE = ITEMS.register("template_balance",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TEMPLATE_CLAW = ITEMS.register("template_claw",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TEMPLATE_CRESCENT = ITEMS.register("template_crescent",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TEMPLATE_DEVIL = ITEMS.register("template_devil",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TEMPLATE_DISK0 = ITEMS.register("template_disk0",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TEMPLATE_DISK3 = ITEMS.register("template_disk3",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TEMPLATE_DISK4 = ITEMS.register("template_disk4",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TEMPLATE_GUST = ITEMS.register("template_gust",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TEMPLATE_RING1 = ITEMS.register("template_ring1",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TEMPLATE_RING2 = ITEMS.register("template_ring2",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TEMPLATE_RING3 = ITEMS.register("template_ring3",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TEMPLATE_TALON = ITEMS.register("template_talon",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TEMPLATE_VENOM = ITEMS.register("template_venom",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TEMPLATE_WAVE = ITEMS.register("template_wave",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> WOODEN_CLAW = ITEMS.register("wooden_claw",
            () -> new BladeItem(new Item.Properties(),0,0,0,0,1));
    public static final RegistryObject<Item> WOODEN_CRESCENT = ITEMS.register("wooden_crescent",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> WOODEN_DEVIL = ITEMS.register("wooden_devil",
            () -> new BladeItem(new Item.Properties(),0,0,0,0,1));
    public static final RegistryObject<Item> WOODEN_GUST = ITEMS.register("wooden_gust",
            () -> new BladeItem(new Item.Properties(),0,0,0,0,1));
    public static final RegistryObject<Item> WOODEN_TALON = ITEMS.register("wooden_talon",
            () -> new BladeItem(new Item.Properties(),0,0,0,0,1));
    public static final RegistryObject<Item> WOODEN_VENOM = ITEMS.register("wooden_venom",
            () -> new BladeItem(new Item.Properties(),0,0,0,0,1));
    public static final RegistryObject<Item> WOODEN_WAVE = ITEMS.register("wooden_wave",
            () -> new BladeItem(new Item.Properties(),0,0,0,0,1));
    public static final RegistryObject<Item> WOODEN_DISK0 = ITEMS.register("wooden_disk0",
            () -> new DiskItem(new Item.Properties(),0,0,0,0,1));
    public static final RegistryObject<Item> WOODEN_DISK3 = ITEMS.register("wooden_disk3",
            () -> new DiskItem(new Item.Properties(),0,0,0,0,1));
    public static final RegistryObject<Item> WOODEN_DISK4 = ITEMS.register("wooden_disk4",
            () -> new DiskItem(new Item.Properties(),0,0,0,0,1));
    public static final RegistryObject<Item> WOODEN_RING1 = ITEMS.register("wooden_ring1",
            () -> new RingItem(new Item.Properties(),0,0,0,0,1));
    public static final RegistryObject<Item> WOODEN_RING2 = ITEMS.register("wooden_ring2",
            () -> new RingItem(new Item.Properties(),0,0,0,0,1));
    public static final RegistryObject<Item> WOODEN_RING3 = ITEMS.register("wooden_ring3",
            () -> new RingItem(new Item.Properties(),0,0,0,0,1));
    public static final RegistryObject<Item> WOODEN_ATTACK = ITEMS.register("wooden_attack",
            () -> new DriverItem(new Item.Properties(),0,0,0,0,1));
    public static final RegistryObject<Item> WOODEN_BALANCE = ITEMS.register("wooden_balance",
            () -> new DriverItem(new Item.Properties(),0,0,0,0,1));

    public static final RegistryObject<Item> STONE_CLAW = ITEMS.register("stone_claw",
            () -> new BladeItem(new Item.Properties(),0,0,0,0,3));
    public static final RegistryObject<Item> STONE_CRESCENT = ITEMS.register("stone_crescent",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STONE_DEVIL = ITEMS.register("stone_devil",
            () -> new BladeItem(new Item.Properties(),0,0,0,0,3));
    public static final RegistryObject<Item> STONE_GUST = ITEMS.register("stone_gust",
            () -> new BladeItem(new Item.Properties(),0,0,0,0,3));
    public static final RegistryObject<Item> STONE_TALON = ITEMS.register("stone_talon",
            () -> new BladeItem(new Item.Properties(),0,0,0,0,3));
    public static final RegistryObject<Item> STONE_VENOM = ITEMS.register("stone_venom",
            () -> new BladeItem(new Item.Properties(),0,0,0,0,3));
    public static final RegistryObject<Item> STONE_WAVE = ITEMS.register("stone_wave",
            () -> new BladeItem(new Item.Properties(),0,0,0,0,3));
    public static final RegistryObject<Item> STONE_DISK0 = ITEMS.register("stone_disk0",
            () -> new DiskItem(new Item.Properties(),0,0,0,0,3));
    public static final RegistryObject<Item> STONE_DISK3 = ITEMS.register("stone_disk3",
            () -> new DiskItem(new Item.Properties(),0,0,0,0,3));
    public static final RegistryObject<Item> STONE_DISK4 = ITEMS.register("stone_disk4",
            () -> new DiskItem(new Item.Properties(),0,0,0,0,3));
    public static final RegistryObject<Item> STONE_RING1 = ITEMS.register("stone_ring1",
            () -> new RingItem(new Item.Properties(),0,0,0,0,3));
    public static final RegistryObject<Item> STONE_RING2 = ITEMS.register("stone_ring2",
            () -> new RingItem(new Item.Properties(),0,0,0,0,3));
    public static final RegistryObject<Item> STONE_RING3 = ITEMS.register("stone_ring3",
            () -> new RingItem(new Item.Properties(),0,0,0,0,3));
    public static final RegistryObject<Item> STONE_ATTACK = ITEMS.register("stone_attack",
            () -> new DriverItem(new Item.Properties(),0,0,0,0,3));
    public static final RegistryObject<Item> STONE_BALANCE = ITEMS.register("stone_balance",
            () -> new DriverItem(new Item.Properties(),0,0,0,0,3));

    public static final RegistryObject<Item> IRON_CLAW = ITEMS.register("iron_claw",
            () -> new BladeItem(new Item.Properties(),0,0,0,0,2));
    public static final RegistryObject<Item> IRON_CRESCENT = ITEMS.register("iron_crescent",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> IRON_DEVIL = ITEMS.register("iron_devil",
            () -> new BladeItem(new Item.Properties(),0,0,0,0,2));
    public static final RegistryObject<Item> IRON_GUST = ITEMS.register("iron_gust",
            () -> new BladeItem(new Item.Properties(),0,0,0,0,2));
    public static final RegistryObject<Item> IRON_TALON = ITEMS.register("iron_talon",
            () -> new BladeItem(new Item.Properties(),0,0,0,0,2));
    public static final RegistryObject<Item> IRON_VENOM = ITEMS.register("iron_venom",
            () -> new BladeItem(new Item.Properties(),0,0,0,0,2));
    public static final RegistryObject<Item> IRON_WAVE = ITEMS.register("iron_wave",
            () -> new BladeItem(new Item.Properties(),0,0,0,0,2));
    public static final RegistryObject<Item> IRON_DISK0 = ITEMS.register("iron_disk0",
            () -> new DiskItem(new Item.Properties(),0,0,0,0,2));
    public static final RegistryObject<Item> IRON_DISK3 = ITEMS.register("iron_disk3",
            () -> new DiskItem(new Item.Properties(),0,0,0,0,2));
    public static final RegistryObject<Item> IRON_DISK4 = ITEMS.register("iron_disk4",
            () -> new DiskItem(new Item.Properties(),0,0,0,0,2));
    public static final RegistryObject<Item> IRON_RING1 = ITEMS.register("iron_ring1",
            () -> new RingItem(new Item.Properties(),0,0,0,0,2));
    public static final RegistryObject<Item> IRON_RING2 = ITEMS.register("iron_ring2",
            () -> new RingItem(new Item.Properties(),0,0,0,0,2));
    public static final RegistryObject<Item> IRON_RING3 = ITEMS.register("iron_ring3",
            () -> new RingItem(new Item.Properties(),0,0,0,0,2));
    public static final RegistryObject<Item> IRON_ATTACK = ITEMS.register("iron_attack",
            () -> new DriverItem(new Item.Properties(),0,0,0,0,2));
    public static final RegistryObject<Item> IRON_BALANCE = ITEMS.register("iron_balance",
            () -> new DriverItem(new Item.Properties(),0,0,0,0,2));

    public static final RegistryObject<Item> GOLD_CLAW = ITEMS.register("gold_claw",
            () -> new BladeItem(new Item.Properties(),0,0,0,0,0));
    public static final RegistryObject<Item> GOLD_CRESCENT = ITEMS.register("gold_crescent",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GOLD_DEVIL = ITEMS.register("gold_devil",
            () -> new BladeItem(new Item.Properties(),0,0,0,0,0));
    public static final RegistryObject<Item> GOLD_GUST = ITEMS.register("gold_gust",
            () -> new BladeItem(new Item.Properties(),0,0,0,0,0));
    public static final RegistryObject<Item> GOLD_TALON = ITEMS.register("gold_talon",
            () -> new BladeItem(new Item.Properties(),0,0,0,0,0));
    public static final RegistryObject<Item> GOLD_VENOM = ITEMS.register("gold_venom",
            () -> new BladeItem(new Item.Properties(),0,0,0,0,0));
    public static final RegistryObject<Item> GOLD_WAVE = ITEMS.register("gold_wave",
            () -> new BladeItem(new Item.Properties(),0,0,0,0,0));
    public static final RegistryObject<Item> GOLD_DISK0 = ITEMS.register("gold_disk0",
            () -> new DiskItem(new Item.Properties(),0,0,0,0,0));
    public static final RegistryObject<Item> GOLD_DISK3 = ITEMS.register("gold_disk3",
            () -> new DiskItem(new Item.Properties(),0,0,0,0,0));
    public static final RegistryObject<Item> GOLD_DISK4 = ITEMS.register("gold_disk4",
            () -> new DiskItem(new Item.Properties(),0,0,0,0,0));
    public static final RegistryObject<Item> GOLD_RING1 = ITEMS.register("gold_ring1",
            () -> new RingItem(new Item.Properties(),0,0,0,0,0));
    public static final RegistryObject<Item> GOLD_RING2 = ITEMS.register("gold_ring2",
            () -> new RingItem(new Item.Properties(),0,0,0,0,0));
    public static final RegistryObject<Item> GOLD_RING3 = ITEMS.register("gold_ring3",
            () -> new RingItem(new Item.Properties(),0,0,0,0,0));
    public static final RegistryObject<Item> GOLD_ATTACK = ITEMS.register("gold_attack",
            () -> new DriverItem(new Item.Properties(),0,0,0,0,0));
    public static final RegistryObject<Item> GOLD_BALANCE = ITEMS.register("gold_balance",
            () -> new DriverItem(new Item.Properties(),0,0,0,0,0));

    public static final RegistryObject<Item> DIAMOND_CLAW = ITEMS.register("diamond_claw",
            () -> new BladeItem(new Item.Properties(),0,0,0,0,0));
    public static final RegistryObject<Item> DIAMOND_CRESCENT = ITEMS.register("diamond_crescent",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DIAMOND_DEVIL = ITEMS.register("diamond_devil",
            () -> new BladeItem(new Item.Properties(),0,0,0,0,0));
    public static final RegistryObject<Item> DIAMOND_GUST = ITEMS.register("diamond_gust",
            () -> new BladeItem(new Item.Properties(),0,0,0,0,0));
    public static final RegistryObject<Item> DIAMOND_TALON = ITEMS.register("diamond_talon",
            () -> new BladeItem(new Item.Properties(),0,0,0,0,0));
    public static final RegistryObject<Item> DIAMOND_VENOM = ITEMS.register("diamond_venom",
            () -> new BladeItem(new Item.Properties(),0,0,0,0,0));
    public static final RegistryObject<Item> DIAMOND_WAVE = ITEMS.register("diamond_wave",
            () -> new BladeItem(new Item.Properties(),0,0,0,0,0));
    public static final RegistryObject<Item> DIAMOND_DISK0 = ITEMS.register("diamond_disk0",
            () -> new DiskItem(new Item.Properties(),0,0,0,0,0));
    public static final RegistryObject<Item> DIAMOND_DISK3 = ITEMS.register("diamond_disk3",
            () -> new DiskItem(new Item.Properties(),0,0,0,0,0));
    public static final RegistryObject<Item> DIAMOND_DISK4 = ITEMS.register("diamond_disk4",
            () -> new DiskItem(new Item.Properties(),0,0,0,0,0));
    public static final RegistryObject<Item> DIAMOND_RING1 = ITEMS.register("diamond_ring1",
            () -> new RingItem(new Item.Properties(),0,0,0,0,0));
    public static final RegistryObject<Item> DIAMOND_RING2 = ITEMS.register("diamond_ring2",
            () -> new RingItem(new Item.Properties(),0,0,0,0,0));
    public static final RegistryObject<Item> DIAMOND_RING3 = ITEMS.register("diamond_ring3",
            () -> new RingItem(new Item.Properties(),0,0,0,0,0));
    public static final RegistryObject<Item> DIAMOND_ATTACK = ITEMS.register("diamond_attack",
            () -> new DriverItem(new Item.Properties(),0,0,0,0,0));
    public static final RegistryObject<Item> DIAMOND_BALANCE = ITEMS.register("diamond_balance",
            () -> new DriverItem(new Item.Properties(),0,0,0,0,0));

    public static final RegistryObject<Item> NETHERITE_CLAW = ITEMS.register("netherite_claw",
            () -> new BladeItem(new Item.Properties(),0,0,0,0,0));
    public static final RegistryObject<Item> NETHERITE_CRESCENT = ITEMS.register("netherite_crescent",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> NETHERITE_DEVIL = ITEMS.register("netherite_devil",
            () -> new BladeItem(new Item.Properties(),0,0,0,0,0));
    public static final RegistryObject<Item> NETHERITE_GUST = ITEMS.register("netherite_gust",
            () -> new BladeItem(new Item.Properties(),0,0,0,0,0));
    public static final RegistryObject<Item> NETHERITE_TALON = ITEMS.register("netherite_talon",
            () -> new BladeItem(new Item.Properties(),0,0,0,0,0));
    public static final RegistryObject<Item> NETHERITE_VENOM = ITEMS.register("netherite_venom",
            () -> new BladeItem(new Item.Properties(),0,0,0,0,0));
    public static final RegistryObject<Item> NETHERITE_WAVE = ITEMS.register("netherite_wave",
            () -> new BladeItem(new Item.Properties(),0,0,0,0,0));
    public static final RegistryObject<Item> NETHERITE_DISK0 = ITEMS.register("netherite_disk0",
            () -> new DiskItem(new Item.Properties(),0,0,0,0,0));
    public static final RegistryObject<Item> NETHERITE_DISK3 = ITEMS.register("netherite_disk3",
            () -> new DiskItem(new Item.Properties(),0,0,0,0,0));
    public static final RegistryObject<Item> NETHERITE_DISK4 = ITEMS.register("netherite_disk4",
            () -> new DiskItem(new Item.Properties(),0,0,0,0,0));
    public static final RegistryObject<Item> NETHERITE_RING1 = ITEMS.register("netherite_ring1",
            () -> new RingItem(new Item.Properties(),0,0,0,0,0));
    public static final RegistryObject<Item> NETHERITE_RING2 = ITEMS.register("netherite_ring2",
            () -> new RingItem(new Item.Properties(),0,0,0,0,0));
    public static final RegistryObject<Item> NETHERITE_RING3 = ITEMS.register("netherite_ring3",
            () -> new RingItem(new Item.Properties(),0,0,0,0,0));
    public static final RegistryObject<Item> NETHERITE_ATTACK = ITEMS.register("netherite_attack",
            () -> new DriverItem(new Item.Properties(),0,0,0,0,0));
    public static final RegistryObject<Item> NETHERITE_BALANCE = ITEMS.register("netherite_balance",
            () -> new DriverItem(new Item.Properties(),0,0,0,0,0));


    //public static final TagKey<Item> RING_TAG = TagKey.create(Registry.ITEM, ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "rings"));
    //public static final TagKey<Item> BLADE_TAG = TagKey.create(Registry.ITEM_REGISTRY, ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "blades"));
    //public static final TagKey<Item> DISK_TAG = TagKey.create(Registry.ITEM_REGISTRY, ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "disks"));
    //public static final TagKey<Item> DRIVER_TAG = TagKey.create(Registry.ITEM_REGISTRY, ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "drivers"));


    public static void register() {
        // Register the items in the mod event bus
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
