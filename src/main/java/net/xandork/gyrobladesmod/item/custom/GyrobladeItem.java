package net.xandork.gyrobladesmod.item.custom;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.xandork.gyrobladesmod.GyrobladesMod;
import net.xandork.gyrobladesmod.data.TextureMerger;
import net.xandork.gyrobladesmod.item.client.GyrobladeItemRenderer;

import java.util.List;
import java.util.function.Consumer;

public class GyrobladeItem extends Item {
    private final ResourceLocation baseTexture = ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "textures/item/bai_shell.png");
    private final ResourceLocation overlayTexture = ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "textures/item/ripcord.png");
    private final ResourceLocation extraTexture = ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "textures/item/ripcord.png");

    public GyrobladeItem(Properties pProperties) {
        super(pProperties);
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public ResourceLocation getMergedTexture(ItemStack stack) {
        // Create a list of textures to merge
        List<ResourceLocation> texturesToMerge = List.of(baseTexture, overlayTexture, extraTexture);

        // Merge and register the textures
        return TextureMerger.mergeAndRegister(texturesToMerge);
    }
    @Override
    public boolean isFoil(ItemStack stack) {
        return true; // Force Minecraft to check for a custom render
    }
    @OnlyIn(Dist.CLIENT)
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                System.out.println("Custom renderer assigned to Gyroblade!");
                return new GyrobladeItemRenderer(
                        Minecraft.getInstance().getBlockEntityRenderDispatcher(),
                        Minecraft.getInstance().getEntityModels()
                );
            }
        });
    }
}
