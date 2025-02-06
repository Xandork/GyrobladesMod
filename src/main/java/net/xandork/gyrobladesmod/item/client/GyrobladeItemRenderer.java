package net.xandork.gyrobladesmod.item.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.eventbus.api.IEventBus;
import net.xandork.gyrobladesmod.GyrobladesMod;
import net.xandork.gyrobladesmod.data.TextureMerger;
import net.xandork.gyrobladesmod.item.ModItems;
import net.xandork.gyrobladesmod.item.custom.GyrobladeItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GyrobladeItemRenderer extends BlockEntityWithoutLevelRenderer {

    public GyrobladeItemRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelSet modelSet) {
        super(dispatcher, modelSet);
        System.out.println("GyrobladeItemRenderer initialized!");
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext context, PoseStack poseStack, MultiBufferSource buffer, int light, int overlay) {
        System.out.println("Rendering gyroblade with custom texture!"); // Debug log

        // Check if the item is of type GyrobladeItem
        if (stack.getItem() instanceof GyrobladeItem gyrobladeItem) {
            // Fetch the merged texture from the item
            ResourceLocation texture = gyrobladeItem.getMergedTexture(stack);

            if (texture == null) {
                System.out.println("Texture merging failed!");
                return;
            }

            // Manually bind and use texture
            Minecraft.getInstance().getTextureManager().bindForSetup(texture);
            System.out.println("Bound merged texture: " + texture);

            // Render the item
            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            itemRenderer.render(stack, context, false, poseStack, buffer, light, overlay, itemRenderer.getModel(stack, Minecraft.getInstance().level, null, 0));
        }
    }

    public static void registerItemRenderer() {
        System.out.println("Registering custom item renderer for Gyroblade...");

        Minecraft minecraft = Minecraft.getInstance();
        ModItems.GYROBLADE_ITEM.get().initializeClient(stack -> new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return new GyrobladeItemRenderer(
                        minecraft.getBlockEntityRenderDispatcher(),
                        minecraft.getEntityModels()
                );
            }
        });
    }
}
