package net.xandork.gyrobladesmod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.xandork.gyrobladesmod.GyrobladesMod;
import net.xandork.gyrobladesmod.entity.custom.BeigomaEntity;

public class BeigomaRenderer extends MobRenderer<BeigomaEntity,BeigomaModel> {
    public BeigomaRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new BeigomaModel(pContext.bakeLayer(BeigomaModel.LAYER_LOCATION)), 0.1f);
    }

    @Override
    public ResourceLocation getTextureLocation(BeigomaEntity pEntity) {
        return ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID,"textures/entity/beigoma.png");
    }

    @Override
    public void render(BeigomaEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {
        if(pEntity.isBaby()){
            pMatrixStack.scale(0.5f,0.5f,0.5f);
        }


        this.setupRotations(pEntity,pMatrixStack,1,pEntity.getRenderingRotation(),pPartialTicks,1);
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight); //+pEntity.getRenderingRotation()
    }
}
