package net.xandork.gyrobladesmod.events;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.xandork.gyrobladesmod.GyrobladesMod;
import net.xandork.gyrobladesmod.data.TextureMerger;
import net.xandork.gyrobladesmod.entity.client.BeigomaModel;
import net.xandork.gyrobladesmod.item.ModItems;
import net.xandork.gyrobladesmod.item.client.ColorShiftedItemRenderer;
import net.xandork.gyrobladesmod.item.client.GyrobladeItemRenderer;

import java.util.List;
import java.util.function.Consumer;

@Mod.EventBusSubscriber(modid = GyrobladesMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {
    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(BeigomaModel.LAYER_LOCATION, BeigomaModel::createBodyLayer);
    }
    private static final Minecraft minecraft = Minecraft.getInstance();

    /*
    // Method to register item renderers
    public static void register(IEventBus modEventBus) {
        modEventBus.addListener(ModEventBusClientEvents::registerRenderers);
    }
    // This method will be called to register the renderers
    private static void registerRenderers(IEventBus eventBus) {
        // Get the item renderer from Minecraft's instance
        ItemRenderer itemRenderer = minecraft.getItemRenderer();

        // Create the Gyroblade item renderer
        GyrobladeItemRenderer gyrobladeItemRenderer = new GyrobladeItemRenderer(itemRenderer);

        // Register the custom renderer for the Gyroblade item
        GyrobladeItemRenderer.register(ModItems.GYROBLADE_ITEM, gyrobladeItemRenderer);
    }

    @SubscribeEvent
    public static void onClientSetup(IEventBus modEventBus) {
        // Register the custom item renderer for the Gyroblade item
        registerItemRenderers();
    }*/

    // Register item renderers
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        // Register the custom renderer for the Gyroblade item
        GyrobladeItemRenderer.registerItemRenderer();

        System.out.println("Client setup running...");

        // Register any additional textures or initialization logic (if necessary)
        registerMergedTextures();

        // Any other client-side initialization for items can go here

        System.out.println("Client setup executed!");

        System.out.println("=== Loaded Recipes ===");
    }

    private static void registerMergedTextures() {
        // Example textures that you want to merge
        List<ResourceLocation> textureList = List.of(
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "textures/item/bai_shell.png"),
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "textures/item/ripcord.png")
        );

        // Merge and register the textures
        ResourceLocation mergedTexture = TextureMerger.mergeAndRegister(textureList);
        if (mergedTexture != null) {
            System.out.println("Successfully registered merged texture: " + mergedTexture);
        }
    }
    @SubscribeEvent
    public static void registerReloadListeners(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(new TextureMerger());
    }
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        System.out.println("ClientEventHandler: Registering item renderers!");

        ItemProperties.register(
                ModItems.GYROBLADE_ITEM.get(),
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "gyroblade"),
                (stack, world, entity, seed) -> 1.0f
        );
        /*ItemProperties.register(
                ModItems.GYRO_RING.get(),
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "gyro_ring"),
                (stack, world, entity, seed) -> 1.0f
        );*/
    }
}
