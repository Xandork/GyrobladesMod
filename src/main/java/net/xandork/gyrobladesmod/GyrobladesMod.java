package net.xandork.gyrobladesmod;

import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
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
import net.xandork.gyrobladesmod.item.ModCreativeModeTabs;
import net.xandork.gyrobladesmod.item.ModItems;
import net.xandork.gyrobladesmod.item.PartItems;
import net.xandork.gyrobladesmod.recipe.ModRecipes;
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
        PartItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModEntities.register(modEventBus);
        ModDataComponentTypes.register(modEventBus);

        // Register event listeners for server and client
        ModRecipes.register(modEventBus);

        // Register event listeners
        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(ClientModEvents::onClientSetup);
        modEventBus.addListener(this::commonSetup);

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Common setup logic
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModTags.registerTags(FMLJavaModLoadingContext.get().getModEventBus());
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

    @Mod.EventBusSubscriber(modid = GyrobladesMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                // Register entity renderer for BeigomaProjectileEntity
                EntityRenderers.register(ModEntities.BEIGOMAPROJECTILE.get(), BeigomaProjectileRenderer::new);

                // Register entity renderer for BeigomaProjectileEntity
                EntityRenderers.register(ModEntities.BEIGOMA.get(), BeigomaRenderer::new);

                // Register custom rendering for specific items
                ItemProperties.register(ModItems.GYROBLADE_ITEM.get(),
                        ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "custom_renderer"),
                        (stack, level, entity, seed) -> 0
                );

                /*ItemProperties.register(ModItems.GYRO_RING.get(),
                        ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "custom_renderer"),
                        (stack, level, entity, seed) -> 0
                );*/
            });
        }
    }
    @Mod.EventBusSubscriber(modid = GyrobladesMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public class ModEventSubscriber {
        @SubscribeEvent
        public static void onRegisterItems(final FMLCommonSetupEvent event) {
            // Register items
            ModItems.register(FMLJavaModLoadingContext.get().getModEventBus());
        }
    }
}
