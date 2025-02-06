package net.xandork.gyrobladesmod.data;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import it.unimi.dsi.fastutil.floats.Float2IntFunction;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.ArrayList;

public final class StringRenderer {
    private static final int SEGMENTS = 24;
    private static final float STRING_WIDTH = 0.0125f;
    private static final float OFFSET_MOD_FACTOR = STRING_WIDTH * 0.5f;
    Entity holder;
    Entity end;
    ArrayList<Vec3> midpoints;
    Vector3f color;
    int minLight;
    public StringRenderer(Entity holder, Entity end, ArrayList<Vec3> midpoints, Vector3f color, int minLight) {
        this.midpoints = midpoints;
        this.holder = holder;
        this.end = end;
        this.color = color;
        this.minLight = minLight;
    }
    public <T extends Float2FloatFunction>
    void renderString(StringRendererParams<T> stringRendererParams) {
        var tick = stringRendererParams.partialTick();
        double eyeHeight = end.getEyeHeight();
        Vec3 entityPos = end.getPosition(tick);
        Vec3 verticalTrans = new Vec3(0, eyeHeight, 0);
        if (midpoints.isEmpty()) {
            renderStringBetween(
                    holder.getRopeHoldPosition(tick), entityPos,
                    verticalTrans,
                    stringRendererParams, holder.isOnFire(), color, end.level(), minLight
            );
            return;
        }

        Vec3 start = holder.getRopeHoldPosition(tick);
        for (Vec3 end : midpoints) {
            // the estrogen in the water is turning the frogs gay
            Vec3 trans = end.subtract(entityPos).add(verticalTrans);
            renderStringBetween(
                    start, end, trans, stringRendererParams,
                    holder.isOnFire(), color, this.end.level(), minLight
            );
            start = end;
        }
        renderStringBetween(
                start, end.getPosition(tick),
                verticalTrans, stringRendererParams, false, color, end.level(), minLight
        );
    }
    public static <T extends Float2FloatFunction>
    void renderStringBetween(Vec3 startPos, Vec3 endPos, Vec3 transVec,
                             StringRendererParams<T> params,
                             boolean startOnFire, Vector3f color, Level level, int minLight) {

        Vector3f dif = startPos.subtract(endPos).toVector3f();
        float xDif = dif.x;
        float zDif = dif.z;
        // scale the offsets according to the width of the string
        float offsetMod = Mth.invSqrt(xDif * xDif + zDif * zDif) * OFFSET_MOD_FACTOR;
        float xOffset = zDif * offsetMod;
        float zOffset = xDif * offsetMod;
        Float2IntFunction lightCalculator = packedLightCalculator(level, startPos, endPos, startOnFire, minLight);
        VertexConsumer vertexConsumer = params.bufferSource().getBuffer(RenderType.leash());

        params.poseStack().pushPose();
        params.poseStack().translate(transVec.x, transVec.y, transVec.z);
        Matrix4f posMatrix = new Matrix4f(params.poseStack().last().pose());
        // if it's the identity, don't bother making a bunch of useless vertices
        int segments = params.yPosCurve() == Float2FloatFunction.identity() ? 1 : SEGMENTS;
        // intellij, I know what I'm doing passing in the string width as the y value please just stfu
        Vector3f offset1 = new Vector3f(xOffset, STRING_WIDTH, zOffset);
        Vector3f offset2 = new Vector3f(xOffset, 0, zOffset);
        int segment;
        for(segment = 0; segment <= segments; ++segment) {
            // joml's vector ops mutate, not copy, so we need to copy
            // screw you joml.
            var newColor = new Vector3f(color);
            renderStringPiece(segment, vertexConsumer, posMatrix, dif, offset1,
                    lightCalculator, params.yPosCurve(), newColor);
        }
        for(segment = segments; segment >= 0; --segment) {
            var newColor = new Vector3f(color);
            renderStringPiece(segment, vertexConsumer, posMatrix, dif, offset2,
                    lightCalculator, params.yPosCurve(), newColor);
        }


        params.poseStack().popPose();
    }

    private static <T extends Float2FloatFunction>
    void renderStringPiece(int segment, VertexConsumer buffer, Matrix4f positionMatrix,
                           Vector3f dif, Vector3f offset, Float2IntFunction lightCalculator,
                           T yPosCurve, Vector3f color) {
        float piecePosPercent = (float)segment / SEGMENTS;
        int packedLight = lightCalculator.get(piecePosPercent);
        float colorVariation = segment % 2 == 0 ? 0.93f : 1.0f;
        color = color.mul(colorVariation);
        float red = color.x;
        float green = color.y;
        float blue = color.z;

        float x = dif.x * piecePosPercent;
        float y = dif.y > 0.0F ? dif.y * yPosCurve.get(piecePosPercent)
                               : dif.y * (1.0f - yPosCurve.get(1.0f - piecePosPercent));
        float z = dif.z * piecePosPercent;
        float xOffset = offset.x;
        float yOffset = offset.y;
        float zOffset = offset.z;
        buffer.addVertex(positionMatrix, x - xOffset, y + yOffset, z + zOffset)
                .setColor(red, green, blue, 1f)
                .setUv2(packedLight, packedLight);
        buffer.addVertex(positionMatrix, x + xOffset, y - yOffset + STRING_WIDTH, z - zOffset)
                .setColor(red, green, blue, 1f)
                .setUv2(packedLight, packedLight);

    }
    private static Float2IntFunction packedLightCalculator(Level level, Vec3 start, Vec3 end, boolean startOnFire, int minLight) {
        return piecePosPercent -> {
            BlockPos entityEyePos = BlockPos.containing(start);
            BlockPos holderEyePos = BlockPos.containing(end);

            int startBlockLight = startOnFire ? 15 : level.getBrightness(LightLayer.BLOCK, holderEyePos);
            int startSkyLight = level.getBrightness(LightLayer.SKY, holderEyePos);
            int endBlockLight = level.getBrightness(LightLayer.BLOCK, entityEyePos);
            int endSkyLight = level.getBrightness(LightLayer.SKY, entityEyePos);
            int lerpBlockLight = (int) Mth.lerp(piecePosPercent, (float) endBlockLight, (float) startBlockLight);
            int lerpSkyLight = (int) Mth.lerp(piecePosPercent, (float) endSkyLight, (float) startSkyLight);
            return LightTexture.pack(Math.max(minLight, lerpBlockLight),
                    Math.max(minLight, lerpSkyLight));
        };
    }
}
