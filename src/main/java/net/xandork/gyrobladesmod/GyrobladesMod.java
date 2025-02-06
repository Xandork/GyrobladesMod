package net.xandork.gyrobladesmod;

import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.xandork.gyrobladesmod.block.ModBlocks;
import net.xandork.gyrobladesmod.component.ModDataComponentTypes;
import net.xandork.gyrobladesmod.entity.ModEntities;
import net.xandork.gyrobladesmod.entity.client.BeigomaProjectileRenderer;
import net.xandork.gyrobladesmod.entity.client.BeigomaRenderer;
import net.xandork.gyrobladesmod.entity.custom.BeigomaProjectileEntity;
import net.xandork.gyrobladesmod.events.ModEventBusClientEvents;
import net.xandork.gyrobladesmod.item.ModCreativeModeTabs;
import net.xandork.gyrobladesmod.item.ModItems;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(GyrobladesMod.MOD_ID)
public class GyrobladesMod {
    public static final String MOD_ID = "gyrobladesmod";
    public static final Logger LOGGER = LogUtils.getLogger();

    public GyrobladesMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register items, blocks, entities, etc.
        ModCreativeModeTabs.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModEntities.register(modEventBus);
        ModDataComponentTypes.register(modEventBus);

        // Register event listeners for server and client
        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);

        // Register client-side event listener
        modEventBus.addListener(ClientModEvents::onClientSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Common setup logic
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(ModItems.RIPCORD);
            event.accept(ModItems.STONE_BEIGOMA);
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Server startup logic
    }

    // Client-side setup
    private void setupClient(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            System.out.println("FMLClientSetupEvent called!");
            // If necessary, register item properties here (no custom renderer registration)
            ItemProperties.register(ModItems.GYROBLADE_ITEM.get(),
                    ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "gyroblade"),
                    (stack, world, entity, seed) -> 1.0F
            );
        });
    }

    @Mod.EventBusSubscriber(modid = GyrobladesMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(final FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                System.out.println("Manually setting renderer for Gyroblade");
                ItemProperties.register(ModItems.GYROBLADE_ITEM.get(),
                        ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "gyroblade"),
                        (stack, world, entity, seed) -> 1.0F
                );
            });
        }
    }
}
