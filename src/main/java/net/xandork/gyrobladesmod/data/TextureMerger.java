package net.xandork.gyrobladesmod.data;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.xandork.gyrobladesmod.GyrobladesMod;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

//@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TextureMerger implements PreparableReloadListener {

    private static final Minecraft minecraft = Minecraft.getInstance();
    private static final Map<List<ResourceLocation>, ResourceLocation> textureCache = new HashMap<>();

    public static NativeImage loadTexture(ResourceLocation textureLocation) {
        ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();

        try {
            Optional<Resource> optionalResource = resourceManager.getResource(textureLocation);
            if (optionalResource.isPresent()) {
                try (InputStream inputStream = optionalResource.get().open()) {
                    return NativeImage.read(inputStream);
                }
            } else {
                System.err.println("Texture not found: " + textureLocation);
                return null;
            }
        } catch (IOException e) {
            System.err.println("Failed to load texture: " + textureLocation + " - " + e.getMessage());
            return null;
        }
    }

    /**
     * Merges multiple textures pixel by pixel.
     */
    public static NativeImage mergeTextures(List<ResourceLocation> textureLocations) {
        if (textureLocations.isEmpty()) {
            System.err.println("No textures provided for merging.");
            return null;
        }

        NativeImage mergedImage = null;

        for (ResourceLocation textureLocation : textureLocations) {
            NativeImage textureImage = loadTexture(textureLocation);
            if (textureImage == null) {
                System.err.println("Skipping null texture: " + textureLocation);
                continue; // Skip missing textures instead of failing
            }

            if (mergedImage == null) {
                // If it's the first texture, use it as the base
                mergedImage = new NativeImage(textureImage.getWidth(), textureImage.getHeight(), true);
                mergedImage.copyFrom(textureImage);
            } else {
                int width = mergedImage.getWidth();
                int height = mergedImage.getHeight();

                if (textureImage.getWidth() != width || textureImage.getHeight() != height) {
                    System.err.println("Texture sizes don't match! Skipping merge: " + textureLocation);
                    continue;
                }

                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        int baseColor = mergedImage.getPixelRGBA(x, y);
                        int overlayColor = textureImage.getPixelRGBA(x, y);
                        int mergedColor = blendColors(baseColor, overlayColor);
                        mergedImage.setPixelRGBA(x, y, mergedColor);
                    }
                }
            }
        }

        return mergedImage;
    }

    public static ResourceLocation registerMergedTexture(List<ResourceLocation> textureLocations, NativeImage mergedImage) {
        if (mergedImage == null) return null;

        if (textureCache.containsKey(textureLocations)) {
            return textureCache.get(textureLocations);
        }

        String textureName = generateTextureName(textureLocations);
        ResourceLocation mergedTextureLocation = ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "textures/generated/" + textureName + ".png");

        // Save texture to disk
        saveTextureToFile(mergedImage, textureName);

        // Register texture dynamically
        DynamicTexture dynamicTexture = new DynamicTexture(mergedImage);
        minecraft.execute(() -> minecraft.getTextureManager().register(mergedTextureLocation, dynamicTexture));

        textureCache.put(textureLocations, mergedTextureLocation);
        return mergedTextureLocation;
    }

    private static void saveTextureToFile(NativeImage image, String textureName) {
        File file = new File(Minecraft.getInstance().gameDirectory, "run/generated/" + textureName + ".png");

        // Ensure the parent directory exists
        file.getParentFile().mkdirs();

        // Convert NativeImage to BufferedImage
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                bufferedImage.setRGB(x, y, image.getPixelRGBA(x, y));
            }
        }

        // Save the image to disk
        try {
            ImageIO.write(bufferedImage, "PNG", file);
            System.out.println("Saved merged texture to: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Failed to save merged texture: " + e.getMessage());
        }
    }

    /**
     * Generates a unique texture name based on the input texture list.
     */
    private static String generateTextureName(List<ResourceLocation> textureLocations) {
        StringBuilder name = new StringBuilder("merged");
        for (ResourceLocation loc : textureLocations) {
            name.append("_").append(loc.getPath().replace("item/", "").replace(".png", ""));
        }
        return name.toString();
    }

    /**
     * Simple alpha blending for smoother merging.
     */
    private static int blendColors(int base, int overlay) {
        int baseA = (base >> 24) & 0xFF;
        int baseR = (base >> 16) & 0xFF;
        int baseG = (base >> 8) & 0xFF;
        int baseB = base & 0xFF;

        int overlayA = (overlay >> 24) & 0xFF;
        int overlayR = (overlay >> 16) & 0xFF;
        int overlayG = (overlay >> 8) & 0xFF;
        int overlayB = overlay & 0xFF;

        // Alpha blending formula
        float alphaFactor = overlayA / 255.0f;
        int outR = (int) ((overlayR * alphaFactor) + (baseR * (1 - alphaFactor)));
        int outG = (int) ((overlayG * alphaFactor) + (baseG * (1 - alphaFactor)));
        int outB = (int) ((overlayB * alphaFactor) + (baseB * (1 - alphaFactor)));
        int outA = Math.max(baseA, overlayA); // Preserve highest alpha

        return (outA << 24) | (outR << 16) | (outG << 8) | outB;
    }

    /**
     * Merges and registers a new texture dynamically with multiple textures.
     */
    public static ResourceLocation mergeAndRegister(List<ResourceLocation> textureLocations) {
        if (textureCache.containsKey(textureLocations)) {
            return textureCache.get(textureLocations);
        }

        NativeImage mergedImage = mergeTextures(textureLocations);
        if (mergedImage == null) return null;

        return registerMergedTexture(textureLocations, mergedImage);
    }

    @Override
    public CompletableFuture<Void> reload(PreparableReloadListener.PreparationBarrier barrier, ResourceManager resourceManager, ProfilerFiller preparationsProfiler, ProfilerFiller reloadProfiler, Executor backgroundExecutor, Executor gameExecutor) {
        return CompletableFuture.runAsync(() -> {
            textureCache.clear();  // Clear texture cache on resource reload
            System.out.println("Texture cache cleared on resource reload.");
        }, gameExecutor).thenCompose(barrier::wait);
    }
}
