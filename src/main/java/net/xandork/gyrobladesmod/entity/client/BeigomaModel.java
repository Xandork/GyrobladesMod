package net.xandork.gyrobladesmod.entity.client;// Made with Blockbench 4.12.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.ResourceLocationException;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.xandork.gyrobladesmod.GyrobladesMod;
import net.xandork.gyrobladesmod.entity.animations.ModAnimationDefinitions;
import net.xandork.gyrobladesmod.entity.custom.BeigomaEntity;

public class BeigomaModel extends HierarchicalModel<BeigomaEntity> {
	public static final ModelLayerLocation LAYER_LOCATION =
			new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID,"beigoma_model"),"main");
	private final ModelPart body;

	public BeigomaModel(ModelPart root) {
		this.body = root.getChild("body");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(8, 2).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 3).addBox(-2.0F, -3.0F, 1.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 5).addBox(-1.0F, -3.0F, -2.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(8, 0).addBox(-3.0F, -2.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -1.0F, -2.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r2 = body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 7).addBox(-3.0F, -2.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -1.0F, -1.0F, 0.0F, 1.5708F, 0.0F));

		return LayerDefinition.create(meshdefinition, 16, 16);
	}

	//@Override
	//public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	//	this.root().getAllParts().forEach(ModelPart::resetPose);
	//	this.animateWalk(ModAnimationDefinitions.beigoma_spin_animation,limbSwing,limbSwingAmount,2f,2.5f);
	//	this.animate(((BeigomaEntity) entity).idleAnimationState, ModAnimationDefinitions.beigoma_idle_animation, ageInTicks, 10f);
	//}

	@Override
	public ModelPart root() {
		return this.body;
	}

	@Override
	public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, int pColor) {
		super.renderToBuffer(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, pColor);
	}

	@Override
	public void setupAnim(BeigomaEntity pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animateWalk(ModAnimationDefinitions.beigoma_spin_animation,pLimbSwing,pLimbSwingAmount,2f,2.5f);
		this.animate(pEntity.idleAnimationState, ModAnimationDefinitions.beigoma_idle_animation, pAgeInTicks, 10f);
		//System.out.println("Setup Animation Method called");
	}


}