package net.xandork.gyrobladesmod.item.custom;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.xandork.gyrobladesmod.GyrobladesMod;
import net.xandork.gyrobladesmod.component.ModDataComponentTypes;
import net.xandork.gyrobladesmod.data.TextureMerger;
import net.xandork.gyrobladesmod.entity.custom.BeigomaProjectileEntity;

import java.util.List;

public class BeigomaItem extends Item {
    public BeigomaItem(Properties pProperties){
        super(pProperties);
    }
    private final ResourceLocation baseTexture = ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "textures/item/bai_shell.png");
    private final ResourceLocation overlayTexture = ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "textures/item/ripcord.png");
    private final ResourceLocation extraTexture = ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "textures/item/ripcord.png");
    @Override
    public void onCraftedBy(ItemStack pStack, Level pLevel, Player pPlayer) {
        pStack.set(ModDataComponentTypes.BEIWEIGHT.get(), 1);
        super.onCraftedBy(pStack, pLevel, pPlayer);
    }
    @OnlyIn(Dist.CLIENT)
    public ResourceLocation getMergedTexture(ItemStack stack) {
        // Create a list of textures to merge
        List<ResourceLocation> texturesToMerge = List.of(baseTexture, overlayTexture, extraTexture);

        // Merge and register the textures
        return TextureMerger.mergeAndRegister(texturesToMerge);
    }



    /*public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack $$3 = pPlayer.getItemInHand(pHand);
        pLevel.playSound((Player)null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
        if (!pLevel.isClientSide) {
            BeigomaProjectileEntity $$4 = new BeigomaProjectileEntity(pLevel, pPlayer);
            $$4.setItem($$3);
            $$4.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 0.5F, 0.9F);
            pLevel.addFreshEntity($$4);
            //$$4.setAttributes(this.getName($$3),weightstability,speedmaneuverability,jumpheight,attackdamage,knockback);
        }

        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        if (!pPlayer.getAbilities().instabuild) {
            $$3.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess($$3, pLevel.isClientSide());
    }*/


}
