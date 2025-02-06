package net.xandork.gyrobladesmod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.xandork.gyrobladesmod.GyrobladesMod;
import net.xandork.gyrobladesmod.data.StringRenderer;
import net.xandork.gyrobladesmod.data.StringRendererParams;
import net.xandork.gyrobladesmod.entity.custom.BeigomaEntity;
import net.xandork.gyrobladesmod.entity.custom.BeigomaProjectileEntity;
import net.xandork.gyrobladesmod.item.custom.RipcordItem;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;

/*public class BeigomaProjectileRenderer extends EntityRenderer<BeigomaProjectileEntity> {
    private BeigomaModel model;
    public BeigomaProjectileRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.model = new BeigomaModel(pContext.bakeLayer(BeigomaModel.LAYER_LOCATION));
    }

    @Override
    public ResourceLocation getTextureLocation(BeigomaProjectileEntity pEntity) {
        return ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID,"textures/entity/beigoma.png");
    }

    @Override
    public void render(BeigomaProjectileEntity pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight) {
        pPoseStack.scale(0.25f,0.25f,0.25f);
        //pPoseStack.mulPose(Axis.XP.rotationDegrees(pEntity.groundedOffset.x));
        StringRenderer string = new StringRenderer(pEntity.getOwner(),pEntity,new ArrayList<>(),new Vector3f(),14);
        string.renderString(new StringRendererParams<>(pPartialTick, pPoseStack, pBufferSource, x -> x*x));
        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBufferSource, pPackedLight);
    }
}*/

@OnlyIn(Dist.CLIENT)
public class BeigomaProjectileRenderer extends EntityRenderer<BeigomaProjectileEntity> {
    //private static final ResourceLocation TEXTURE_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/beigoma.png");
    //private static final RenderType RENDER_TYPE = RenderType.entityCutout(TEXTURE_LOCATION);
    private static final double VIEW_BOBBING_SCALE = 960.0;
    private BeigomaModel model;
    public BeigomaProjectileRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.model = new BeigomaModel(pContext.bakeLayer(BeigomaModel.LAYER_LOCATION));
    }

    @Override
    public ResourceLocation getTextureLocation(BeigomaProjectileEntity pEntity) {
        return ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID,"textures/entity/beigoma.png");
    }

    public void render(BeigomaProjectileEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        Player player = pEntity.getPlayerOwner();
        if (player != null) {
            pPoseStack.pushPose();
            pPoseStack.pushPose();
            pPoseStack.scale(0.002F, 0.002F, 0.002F);
            pPoseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
            PoseStack.Pose posestack$pose = pPoseStack.last();
            //VertexConsumer vertexconsumer = pBuffer.getBuffer(RENDER_TYPE);
            //vertex(vertexconsumer, posestack$pose, pPackedLight, 0.0F, 0, 0, 1);
            //vertex(vertexconsumer, posestack$pose, pPackedLight, 1.0F, 0, 1, 1);
            //vertex(vertexconsumer, posestack$pose, pPackedLight, 1.0F, 1, 1, 0);
            //vertex(vertexconsumer, posestack$pose, pPackedLight, 0.0F, 1, 0, 0);
            pPoseStack.popPose();
            float f = player.getAttackAnim(pPartialTicks);
            float f1 = Mth.sin(Mth.sqrt(f) * (float) Math.PI);
            Vec3 vec3 = this.getPlayerHandPos(player, f1, pPartialTicks);
            Vec3 vec31 = pEntity.getPosition(pPartialTicks).add(0.0, 0.25, 0.0);
            float f2 = (float)(vec3.x - vec31.x);
            float f3 = (float)(vec3.y - vec31.y);
            float f4 = (float)(vec3.z - vec31.z);
            VertexConsumer vertexconsumer1 = pBuffer.getBuffer(RenderType.lineStrip());
            PoseStack.Pose posestack$pose1 = pPoseStack.last();
            int i = 16;

            for (int j = 0; j <= 16; j++) {
                if(player.getMainHandItem().getItem() instanceof RipcordItem){
                    stringVertex(-f2, f3, f4, vertexconsumer1, posestack$pose1, fraction(j, 16), fraction(j + 1, 16));
                } else{
                    stringVertex(f2, f3, f4, vertexconsumer1, posestack$pose1, fraction(j, 16), fraction(j + 1, 16));
                }
            }
            // STUFF I ADDED
            //pPoseStack.pushPose();
            pPoseStack.translate(0,1.5,0);
            pPoseStack.rotateAround(new Quaternionf(1,0,0,0),180,0,0);
            VertexConsumer vertexConsumer = ItemRenderer.getFoilBufferDirect(
                    pBuffer,this.model.renderType(this.getTextureLocation(pEntity)),false,false);
            this.model.renderToBuffer(pPoseStack,vertexConsumer,pPackedLight,OverlayTexture.NO_OVERLAY);
            //

            pPoseStack.popPose();
            super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
        }
    }

    private Vec3 getPlayerHandPos(Player pPlayer, float p_328369_, float pPartialTick) {
        int i = pPlayer.getMainArm() == HumanoidArm.RIGHT ? 1 : -1;
        ItemStack itemstack = pPlayer.getMainHandItem();
        if (!itemstack.canPerformAction(net.minecraftforge.common.ToolActions.FISHING_ROD_CAST)) {
            i = -i;
        }

        if (this.entityRenderDispatcher.options.getCameraType().isFirstPerson() && pPlayer == Minecraft.getInstance().player) {
            double d4 = 960.0 / (double)this.entityRenderDispatcher.options.fov().get().intValue();
            Vec3 vec3 = this.entityRenderDispatcher
                    .camera
                    .getNearPlane()
                    .getPointOnPlane((float)i * 0.525F, -0.1F)
                    .scale(d4)
                    .yRot(p_328369_ * 0.5F)
                    .xRot(-p_328369_ * 0.7F);
            return pPlayer.getEyePosition(pPartialTick).add(vec3);
        } else {
            float f = Mth.lerp(pPartialTick, pPlayer.yBodyRotO, pPlayer.yBodyRot) * (float) (Math.PI / 180.0);
            double d0 = (double)Mth.sin(f);
            double d1 = (double)Mth.cos(f);
            float f1 = pPlayer.getScale();
            double d2 = (double)i * 0.35 * (double)f1;
            double d3 = 0.8 * (double)f1;
            float f2 = pPlayer.isCrouching() ? -0.1875F : 0.0F;
            return pPlayer.getEyePosition(pPartialTick).add(-d1 * d2 - d0 * d3, (double)f2 - 0.45 * (double)f1, -d0 * d2 + d1 * d3);
        }
    }

    private static float fraction(int pNumerator, int pDenominator) {
        return (float)pNumerator / (float)pDenominator;
    }

    private static void vertex(
            VertexConsumer pConsumer, PoseStack.Pose pPose, int pPackedLight, float pX, int pY, int pU, int pV
    ) {
        pConsumer.addVertex(pPose, pX - 0.5F, (float)pY - 0.5F, 0.0F)
                .setColor(-1)
                .setUv((float)pU, (float)pV)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(pPackedLight)
                .setNormal(pPose, 0.0F, 1.0F, 0.0F);
    }

    private static void stringVertex(
            float pX, float pY, float pZ, VertexConsumer pConsumer, PoseStack.Pose pPose, float pStringFraction, float pNextStringFraction
    ) {
        float f = pX * pStringFraction;
        float f1 = pY * (pStringFraction * pStringFraction + pStringFraction) * 0.5F + 0.25F;
        float f2 = pZ * pStringFraction;
        float f3 = pX * pNextStringFraction - f;
        float f4 = pY * (pNextStringFraction * pNextStringFraction + pNextStringFraction) * 0.5F + 0.25F - f1;
        float f5 = pZ * pNextStringFraction - f2;
        float f6 = Mth.sqrt(f3 * f3 + f4 * f4 + f5 * f5);
        f3 /= f6;
        f4 /= f6;
        f5 /= f6;
        pConsumer.addVertex(pPose, f, f1, f2).setColor(-16777216).setNormal(pPose, f3, f4, f5);
    }

    //public ResourceLocation getTextureLocation(FishingHook pEntity) {
        //return TEXTURE_LOCATION;
    //}
}

