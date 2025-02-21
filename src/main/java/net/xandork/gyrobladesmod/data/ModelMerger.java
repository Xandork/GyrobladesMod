package net.xandork.gyrobladesmod.data;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ModelMerger implements BakedModel {
    private final List<BakedModel> modelsToMerge;

    // Constructor that accepts a list of BakedModels
    public ModelMerger(List<BakedModel> modelsToMerge) {
        this.modelsToMerge = modelsToMerge;
    }

    // Merge the provided list of models
    public static BakedModel mergeModels(List<BakedModel> models) {
        return new ModelMerger(models);
    }

    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction side, RandomSource random) {
        List<BakedQuad> mergedQuads = new ArrayList<>();

        // Iterate over each model and combine its quads
        for (BakedModel model : modelsToMerge) {
            mergedQuads.addAll(model.getQuads(state, side, random));
        }

        return mergedQuads;
    }

    @Override
    public boolean useAmbientOcclusion() {
        return true;
    }

    @Override
    public boolean isGui3d() {
        return true;
    }

    @Override
    public boolean usesBlockLight() {
        return false;
    }

    @Override
    public boolean isCustomRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return null;
    }

    @Override
    public ItemOverrides getOverrides() {
        return null;
    }

    public ModelState getRotation() {
        // Simply return null or a default ModelState (identity transformation is default)
        return new ModelState() {
            public PoseStack getModelTransform() {
                // Returning the identity matrix as a default for no transformation
                return new PoseStack(); // Default PoseStack (no transformation)
            }
        };
    }

    // Rendering logic - handle quads directly via VertexConsumer
    public void render(PoseStack matrixStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        for (BakedModel model : modelsToMerge) {
            List<BakedQuad> quads = model.getQuads(null, null, RandomSource.create());
            for (BakedQuad quad : quads) {
                // Iterate over the vertices in each quad
                for (int i = 0; i < 4; i++) {
                    // Each quad has an array of 4 vertices, each having 4 components
                    int[] vertexData = quad.getVertices();

                    // Extract vertex position and other data
                    float x = Float.intBitsToFloat(vertexData[i * 8]);
                    float y = Float.intBitsToFloat(vertexData[i * 8 + 1]);
                    float z = Float.intBitsToFloat(vertexData[i * 8 + 2]);

                    float u = Float.intBitsToFloat(vertexData[i * 8 + 4]);
                    float v = Float.intBitsToFloat(vertexData[i * 8 + 5]);

                    // Push the vertex data to the vertex consumer
                    vertexConsumer.addVertex(matrixStack.last().pose(), x, y, z)
                            .setColor(red, green, blue, alpha)
                            .setUv(u, v)
                            .setOverlay(packedOverlay)
                            .setLight(packedLight)
                            .setNormal(0f, 0f, 0f) // Adjust normals as needed
                            ;
                }
            }
        }
    }

    public boolean isSideLit() {
        return false;
    }

    public boolean isTranslucent() {
        return false;
    }

    public boolean isAmbientOcclusion() {
        return true;
    }
}

