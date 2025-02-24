package net.xandork.gyrobladesmod.item.custom;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.xandork.gyrobladesmod.GyrobladesMod;
import net.xandork.gyrobladesmod.component.ModDataComponentTypes;
import net.xandork.gyrobladesmod.data.StringRenderer;
import net.xandork.gyrobladesmod.data.StringRendererParams;
import net.xandork.gyrobladesmod.data.TextureMerger;
import net.xandork.gyrobladesmod.entity.custom.BeigomaProjectileEntity;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class RipcordItem extends Item {
    private final ResourceLocation baseTexture = ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "textures/item/bai_shell.png");
    private final ResourceLocation overlayTexture = ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "textures/item/ripcord.png");

    public RipcordItem(Properties pProperties){
        super(pProperties);;
    }

    @OnlyIn(Dist.CLIENT)
    public ResourceLocation getMergedTexture(ItemStack stack) {
        List<ResourceLocation> texturesToMerge = List.of(baseTexture, overlayTexture);

        return TextureMerger.mergeAndRegister(texturesToMerge);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        ItemStack beigomaItem = itemstack.get(ModDataComponentTypes.WRAPPEDBEIGOMA.get());
        if (beigomaItem != null && beigomaItem != ItemStack.EMPTY) {
            pLevel.playSound((Player)null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.NEUTRAL, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
            if (!pLevel.isClientSide) {
                BeigomaProjectileEntity $$4 = new BeigomaProjectileEntity(pLevel, pPlayer);
                //$$4.setOwner(pPlayer);
                $$4.setItem(beigomaItem);
                $$4.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 0.5F, 0.9F);
                pLevel.addFreshEntity($$4);
                $$4.setItem(beigomaItem);
                itemstack.set(ModDataComponentTypes.WRAPPEDBEIGOMA.get(), null);
            }
            pPlayer.awardStat(Stats.ITEM_USED.get(this));
            return InteractionResultHolder.sidedSuccess(pPlayer.getItemInHand(pHand), pLevel.isClientSide());
        } else if (beigomaItem == null || beigomaItem == ItemStack.EMPTY) {
            if(pPlayer.getOffhandItem().getItem() instanceof BeigomaItem){
                //beigomaItem = new ItemStack(pPlayer.getOffhandItem().getItem());
                itemstack.set(ModDataComponentTypes.WRAPPEDBEIGOMA.get(), new ItemStack(pPlayer.getOffhandItem().getItem()));
                pPlayer.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
                return InteractionResultHolder.sidedSuccess(pPlayer.getItemInHand(pHand), pLevel.isClientSide());
            } else {
                for (int i = 0; i < pPlayer.inventoryMenu.getItems().size(); i++){
                    if (pPlayer.inventoryMenu.getItems().get(i).getItem() instanceof BeigomaItem){
                        //beigomaItem = new ItemStack(pPlayer.inventoryMenu.getItems().get(i).getItem());
                        itemstack.set(ModDataComponentTypes.WRAPPEDBEIGOMA.get(), new ItemStack(pPlayer.inventoryMenu.getItems().get(i).getItem()));
                        pPlayer.inventoryMenu.getItems().get(i).shrink(1);
                        return InteractionResultHolder.sidedSuccess(pPlayer.getItemInHand(pHand), pLevel.isClientSide());
                    }
                }
            }
        } else {
            return InteractionResultHolder.fail(itemstack);
        }
        return InteractionResultHolder.fail(itemstack);
    }

    private void loadBeigoma() {

    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        //if (getUseDuration(pStack,pLivingEntity)>=5){
            System.out.println("Finished wrapping");
        //}
        super.releaseUsing(pStack, pLevel, pLivingEntity, pTimeCharged);
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        if(Screen.hasShiftDown()){
            pTooltipComponents.add(Component.translatable("tooltip.gyrobladesmod.ripcord.shift_down"));
        } else{
            pTooltipComponents.add(Component.translatable("tooltip.gyrobladesmod.ripcord"));
        }
        if (pStack.get(ModDataComponentTypes.WRAPPEDBEIGOMA.get()) != null){
            //pTooltipComponents.add(Component.translatable("Wrapped around " + pStack.get(ModDataComponentTypes.WRAPPEDBEIGOMA.get()));
        }
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }
}
