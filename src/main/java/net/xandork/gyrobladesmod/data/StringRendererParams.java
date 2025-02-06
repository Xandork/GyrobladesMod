package net.xandork.gyrobladesmod.data;

import com.mojang.blaze3d.vertex.PoseStack;
import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import net.minecraft.client.renderer.MultiBufferSource;

public record StringRendererParams<T extends Float2FloatFunction>(float partialTick, PoseStack poseStack,
                                                                  MultiBufferSource bufferSource, T yPosCurve) {
}
