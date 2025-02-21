package net.xandork.gyrobladesmod.item.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.eventbus.api.IEventBus;
import net.xandork.gyrobladesmod.GyrobladesMod;
import net.xandork.gyrobladesmod.data.ModelMerger;
import net.xandork.gyrobladesmod.data.TextureMerger;
import net.xandork.gyrobladesmod.item.ModItems;
import net.xandork.gyrobladesmod.item.custom.GyrobladeItem;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GyrobladeItemRenderer extends BlockEntityWithoutLevelRenderer {
    private static final ModelResourceLocation GUI_MODEL = new ModelResourceLocation(ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID,"gyroblade"), "inventory");
    private static final ModelResourceLocation HANDHELD_MODEL = new ModelResourceLocation(ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID,"gyroblade_3d"),"normal");

    public GyrobladeItemRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelSet modelSet) {
        super(dispatcher, modelSet);
        //System.out.println("GyrobladeItemRenderer initialized!");
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext context, PoseStack poseStack, MultiBufferSource buffer, int light, int overlay) {
        if (stack.getItem() instanceof GyrobladeItem gyrobladeItem) {
            Minecraft mc = Minecraft.getInstance();

            if (context == ItemDisplayContext.GUI) {
                ResourceLocation texture = gyrobladeItem.getMergedTexture(stack);
                if (texture == null) {
                    return;
                }

                BakedModel model = mc.getItemRenderer().getModel(stack, mc.level, null, 0);
                if (model == null) {
                    return;
                }

                VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(texture));
                mc.getItemRenderer().renderModelLists(model, stack, light, overlay, poseStack, vertexConsumer);

                PoseStack.Pose lastPose = poseStack.last();
                Matrix4f matrix = lastPose.pose();
                Matrix3f normalMatrix = lastPose.normal();

                poseStack.pushPose();

                // Render front face
                vertexConsumer.addVertex(matrix, -0.5f, -0.5f, 0.0f).setColor(255, 255, 255, 255).setUv(0, 0).setOverlay(overlay).setUv2(light, light).setNormal(lastPose, 0, 0f, 1f);
                vertexConsumer.addVertex(matrix, 0.5f, -0.5f, 0.0f).setColor(255, 255, 255, 255).setUv(1, 0).setOverlay(overlay).setUv2(light, light).setNormal(lastPose, 0f, 0f, 1f);
                vertexConsumer.addVertex(matrix, 0.5f, 0.5f, 0.0f).setColor(255, 255, 255, 255).setUv(1, 1).setOverlay(overlay).setUv2(light, light).setNormal(lastPose, 0f, 0f, 1f);
                vertexConsumer.addVertex(matrix, -0.5f, 0.5f, 0.0f).setColor(255, 255, 255, 255).setUv(0, 1).setOverlay(overlay).setUv2(light, light).setNormal(lastPose, 0f, 0f, 1f);

                // Render back face
                vertexConsumer.addVertex(matrix, -0.5f, -0.5f, -0.1f).setColor(255, 255, 255, 255).setUv(0, 0).setOverlay(overlay).setUv2(light, light).setNormal(lastPose, 0f, 0f, -1f);
                vertexConsumer.addVertex(matrix, -0.5f, 0.5f, -0.1f).setColor(255, 255, 255, 255).setUv(0, 1).setOverlay(overlay).setUv2(light, light).setNormal(lastPose, 0f, 0f, -1f);
                vertexConsumer.addVertex(matrix, 0.5f, 0.5f, -0.1f).setColor(255, 255, 255, 255).setUv(1, 1).setOverlay(overlay).setUv2(light, light).setNormal(lastPose, 0f, 0f, -1f);
                vertexConsumer.addVertex(matrix, 0.5f, -0.5f, -0.1f).setColor(255, 255, 255, 255).setUv(1, 0).setOverlay(overlay).setUv2(light, light).setNormal(lastPose, 0f, 0f, -1f);

                poseStack.popPose();

            } else { // Non-GUI rendering
                int fullBright = 0xF000F0;

                //BakedModel model = mc.getModelManager().getModel(
                //        new ModelResourceLocation(ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID,"stone_beigoma_3d"),"inventory")
                //);
                //VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.itemEntityTranslucentCull(
                //        ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "entity/beigoma.png")
                //));


                BakedModel ironSwordModel = mc.getItemRenderer().getItemModelShaper().getItemModel(Items.IRON_SWORD);
                BakedModel cookedBeefModel = mc.getItemRenderer().getItemModelShaper().getItemModel(Items.COOKED_BEEF);  // Assuming you have a custom telescope item

                BakedModel mergedModel = new ModelMerger(List.of(ironSwordModel, cookedBeefModel));

                VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityTranslucent(
                        mc.getItemRenderer().getItemModelShaper().getItemModel(Items.IRON_SWORD).getParticleIcon().atlasLocation()
                ));

                // Render the merged model using the merged BakedModel
                ((ModelMerger) mergedModel).render(poseStack, vertexConsumer, light, overlay, 1.0f, 1.0f, 1.0f, 1.0f);

                //test sword
                BakedModel model = mc.getItemRenderer().getModel(new ItemStack(Items.IRON_SWORD), mc.level, null, 0);
                //VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityTranslucent(
                //        mc.getItemRenderer().getItemModelShaper().getItemModel(Items.IRON_SWORD).getParticleIcon().atlasLocation()
                //));
                if (model == null) {
                    System.out.println("ERROR: Model not found!");
                    return;
                }
                if (model == Minecraft.getInstance().getModelManager().getMissingModel()) {
                    System.out.println("ERROR: Model is missing!");
                    //System.out.println(mc.getModelManager().getModel(new ModelResourceLocation(ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID,"gyroblade_3d"), "inventory")));
                }
                poseStack.pushPose();
                mc.getItemRenderer().renderModelLists(model, stack, fullBright, overlay, poseStack, vertexConsumer);
                switch (context) {
                    case FIRST_PERSON_RIGHT_HAND, FIRST_PERSON_LEFT_HAND -> poseStack.translate(1, -0.9, 1.0);
                    case THIRD_PERSON_RIGHT_HAND, THIRD_PERSON_LEFT_HAND -> poseStack.translate(0.1, -0.4, -0.1);
                    case HEAD -> poseStack.translate(0.0, 0.0, 0.0);
                    default -> poseStack.translate(0.0, -0.25, 0.0);
                }
                poseStack.popPose();
            }
        }
    }

    /**
     * Registers the custom renderer for the Gyroblade item.
     */
    public static void registerItemRenderer() {
        System.out.println("Registering custom item renderer for Gyroblade...");

        Minecraft minecraft = Minecraft.getInstance();

        // Register the custom renderer using IClientItemExtensions
        ModItems.GYROBLADE_ITEM.get().initializeClient(stack -> new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return new GyrobladeItemRenderer(
                        minecraft.getBlockEntityRenderDispatcher(),
                        minecraft.getEntityModels()
                );
            }
        });

        System.out.println("Custom renderer for Gyroblade registered!");
    }
}

