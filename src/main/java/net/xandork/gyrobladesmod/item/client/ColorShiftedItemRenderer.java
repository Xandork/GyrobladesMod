package net.xandork.gyrobladesmod.item.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.xandork.gyrobladesmod.GyrobladesMod;
import net.xandork.gyrobladesmod.component.ModDataComponentTypes;

public class ColorShiftedItemRenderer extends BlockEntityWithoutLevelRenderer {

    public ColorShiftedItemRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext context, PoseStack poseStack, MultiBufferSource buffer, int light, int overlay) {
        Minecraft mc = Minecraft.getInstance();
        /*
        float[] colorShift = GyroRingItem.getColor(stack);
        float redShift = colorShift[0];
        float greenShift = colorShift[1];
        float blueShift = colorShift[2];
        */
        // Get the original item texture (change path to match your item)
        ResourceLocation originalTexture = ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, stack.get(ModDataComponentTypes.PARTTEXTUREPATH.get()));

        // Apply a color shift dynamically (adjust RGB shift values)
        ResourceLocation shiftedTexture = net.xandork.gyrobladesmod.data.TextureColorModifier.modifyTextureHue(originalTexture, 1, 1, 1);

        // Get the model for the item
        BakedModel model = mc.getItemRenderer().getModel(stack, mc.level, null, 0);

        // Get the correct buffer for rendering
        VertexConsumer vertexConsumer = buffer.getBuffer(net.minecraft.client.renderer.RenderType.itemEntityTranslucentCull(shiftedTexture));

        // Render the model with the modified texture
        mc.getItemRenderer().renderModelLists(model, stack, light, OverlayTexture.NO_OVERLAY, poseStack, vertexConsumer);
    }
}

