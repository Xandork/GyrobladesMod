package net.xandork.gyrobladesmod.data;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.BakedModelWrapper;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraft.util.RandomSource;
import net.minecraft.client.renderer.RenderType;
import net.xandork.gyrobladesmod.GyrobladesMod;

import java.util.List;

/**
 * Custom Baked Model to apply dynamically merged textures to an item.
 */
public class CustomBakedModel extends BakedModelWrapper<BakedModel> {

    private final ResourceLocation texture;

    public CustomBakedModel(BakedModel baseModel, ResourceLocation texture) {
        super(baseModel);
        this.texture = texture;
    }

    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction side, RandomSource random, ModelData modelData, RenderType renderType) {
        return originalModel.getQuads(state, side, random, modelData, renderType);
    }

    @Override
    public boolean useAmbientOcclusion() {
        return originalModel.useAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return originalModel.isGui3d();
    }

    @Override
    public boolean usesBlockLight() {
        return originalModel.usesBlockLight();
    }

    @Override
    public boolean isCustomRenderer() {
        return false; // Set to true if using a custom item renderer.
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return Minecraft.getInstance().getTextureAtlas(ResourceLocation.parse(GyrobladesMod.MOD_ID)).apply(texture);
    }
}
