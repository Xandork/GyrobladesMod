package net.xandork.gyrobladesmod.data;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.xandork.gyrobladesmod.GyrobladesMod;

import java.io.IOException;
import java.util.Optional;

/**
 * Utility class for modifying textures by applying a hue shift based on RGB values.
 */
public class TextureColorModifier {

    /**
     * Creates a new texture with a modified hue.
     *
     * @param originalTexture The original texture location.
     * @param redShift        Red channel adjustment (0.0 to 1.0).
     * @param greenShift      Green channel adjustment (0.0 to 1.0).
     * @param blueShift       Blue channel adjustment (0.0 to 1.0).
     * @return The new dynamically generated texture.
     */
    public static ResourceLocation modifyTextureHue(ResourceLocation originalTexture, float redShift, float greenShift, float blueShift) {
        Minecraft mc = Minecraft.getInstance();
        ResourceManager resourceManager = mc.getResourceManager();

        try {
            // Check if the texture exists
            Optional<Resource> resourceOptional = resourceManager.getResource(originalTexture);
            if (resourceOptional.isEmpty()) {
                System.err.println("❌ Texture not found: " + originalTexture);
                return originalTexture; // Return the original texture instead of crashing
            }

            // Load the original texture as a NativeImage
            NativeImage image = NativeImage.read(resourceOptional.get().open());

            // Modify each pixel's hue
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    int color = image.getPixelRGBA(x, y);
                    int alpha = (color >> 24) & 0xFF;
                    int red = (color >> 16) & 0xFF;
                    int green = (color >> 8) & 0xFF;
                    int blue = (color) & 0xFF;

                    // Apply hue shift
                    red = (int) (red * redShift);
                    green = (int) (green * greenShift);
                    blue = (int) (blue * blueShift);

                    // Ensure values stay within range
                    red = Math.min(255, Math.max(0, red));
                    green = Math.min(255, Math.max(0, green));
                    blue = Math.min(255, Math.max(0, blue));

                    // Reconstruct the pixel color
                    int newColor = (alpha << 24) | (red << 16) | (green << 8) | blue;
                    image.setPixelRGBA(x, y, newColor);
                }
            }

            // Create a new dynamic texture
            DynamicTexture dynamicTexture = new DynamicTexture(image);
            ResourceLocation newTextureLocation = ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "textures/dynamic/hue_shifted.png");

            // Register it in the game's texture manager
            mc.getTextureManager().register(newTextureLocation, dynamicTexture);

            return newTextureLocation;
        } catch (IOException e) {
            e.printStackTrace();
            return originalTexture; // If an error occurs, return the original texture
        }
    }

}

