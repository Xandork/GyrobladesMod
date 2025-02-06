package net.xandork.gyrobladesmod.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.xandork.gyrobladesmod.block.ModBlocks;

public class GyroArenaItem extends Item {

    public GyroArenaItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        ItemStack $$3 = pContext.getPlayer().getItemInHand(pContext.getHand());
        BlockPos blockpos = pContext.getClickedPos().above();
        String a = level.getBlockState(blockpos).getBlock().getName().toString();
        if(
            /*level.getBlockState(blockpos).getBlock().getName().toString().endsWith("air") &
            level.getBlockState(blockpos.north()).getBlock().getName().toString().endsWith("air") &
            level.getBlockState(blockpos.south()).getBlock().getName().toString().endsWith("air") &
            level.getBlockState(blockpos.east()).getBlock().getName().toString().endsWith("air") &
            level.getBlockState(blockpos.west()).getBlock().getName().toString().endsWith("air") &
            level.getBlockState(blockpos.north().east()).getBlock().getName().toString().endsWith("air") &
            level.getBlockState(blockpos.north().west()).getBlock().getName().toString().endsWith("air") &
            level.getBlockState(blockpos.south().east()).getBlock().getName().toString().endsWith("air") &
            level.getBlockState(blockpos.south().west()).getBlock().getName().toString().endsWith("air")*/
            BaseFireBlock.canBePlacedAt(level, blockpos, pContext.getHorizontalDirection()) &
            BaseFireBlock.canBePlacedAt(level, blockpos.north(), pContext.getHorizontalDirection()) &
            BaseFireBlock.canBePlacedAt(level, blockpos.south(), pContext.getHorizontalDirection()) &
            BaseFireBlock.canBePlacedAt(level, blockpos.east(), pContext.getHorizontalDirection()) &
            BaseFireBlock.canBePlacedAt(level, blockpos.west(), pContext.getHorizontalDirection()) &
            BaseFireBlock.canBePlacedAt(level, blockpos.north().east(), pContext.getHorizontalDirection()) &
            BaseFireBlock.canBePlacedAt(level, blockpos.north().west(), pContext.getHorizontalDirection()) &
            BaseFireBlock.canBePlacedAt(level, blockpos.south().east(), pContext.getHorizontalDirection()) &
            BaseFireBlock.canBePlacedAt(level, blockpos.south().west(), pContext.getHorizontalDirection())
        ){
            level.setBlockAndUpdate(blockpos, ModBlocks.GYRO_ARENA_CORE.get().defaultBlockState());
            level.setBlockAndUpdate(blockpos.north(), ModBlocks.GYRO_ARENA_EDGE.get().defaultBlockState().rotate(level, blockpos, Rotation.CLOCKWISE_180));
            level.setBlockAndUpdate(blockpos.south(), ModBlocks.GYRO_ARENA_EDGE.get().defaultBlockState());
            level.setBlockAndUpdate(blockpos.east(), ModBlocks.GYRO_ARENA_EDGE.get().defaultBlockState().rotate(level, blockpos, Rotation.COUNTERCLOCKWISE_90));
            level.setBlockAndUpdate(blockpos.west(), ModBlocks.GYRO_ARENA_EDGE.get().defaultBlockState().rotate(level, blockpos, Rotation.CLOCKWISE_90));
            level.setBlockAndUpdate(blockpos.north().east(), ModBlocks.GYRO_ARENA_CORNER.get().defaultBlockState().rotate(level, blockpos, Rotation.CLOCKWISE_180));
            level.setBlockAndUpdate(blockpos.north().west(), ModBlocks.GYRO_ARENA_CORNER.get().defaultBlockState().rotate(level, blockpos, Rotation.CLOCKWISE_90));
            level.setBlockAndUpdate(blockpos.south().east(), ModBlocks.GYRO_ARENA_CORNER.get().defaultBlockState().rotate(level, blockpos, Rotation.COUNTERCLOCKWISE_90));
            level.setBlockAndUpdate(blockpos.south().west(), ModBlocks.GYRO_ARENA_CORNER.get().defaultBlockState());
            $$3.shrink(1);
            return InteractionResult.CONSUME;
        }
        return InteractionResult.FAIL;
    }


    /*protected boolean placeBlock(BlockPlaceContext pContext, BlockState pState) {
        Level level = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos().above();
        //if (BaseFireBlock.canBePlacedAt(level, blockpos.above(), pContext.getHorizontalDirection())) {
        //if (level.getBlockState(blockpos.above()).getBlock().getName().toString().endsWith("AIR")){
            level.setBlock(blockpos.above(), ModBlocks.GYRO_ARENA_CORE.get().defaultBlockState(), 1);
            level.setBlock(blockpos.above().north(), ModBlocks.GYRO_ARENA_EDGE.get().defaultBlockState(), 1);
            level.setBlock(blockpos.above().south(), ModBlocks.GYRO_ARENA_EDGE.get().defaultBlockState(), 1);
            level.setBlock(blockpos.above().east(), ModBlocks.GYRO_ARENA_EDGE.get().defaultBlockState(), 1);
            level.setBlock(blockpos.above().west(), ModBlocks.GYRO_ARENA_EDGE.get().defaultBlockState(), 1);
            level.setBlock(blockpos.above().north().east(), ModBlocks.GYRO_ARENA_CORNER.get().defaultBlockState(), 1);
            level.setBlock(blockpos.above().north().west(), ModBlocks.GYRO_ARENA_CORNER.get().defaultBlockState(), 1);
            level.setBlock(blockpos.above().south().east(), ModBlocks.GYRO_ARENA_CORNER.get().defaultBlockState(), 1);
            level.setBlock(blockpos.above().south().west(), ModBlocks.GYRO_ARENA_CORNER.get().defaultBlockState(), 1);
        //}
        return true;//super.placeBlock(pContext, pState);
    }*/
}

//@Override
    /*public InteractionResult useOn(UseOnContext pContext) {
        Player player = pContext.getPlayer();
        Level level = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos();
        BlockState blockstate = level.getBlockState(blockpos);
        BlockState blockabove = level.getBlockState(blockpos.above());
        //if (blockabove.getBlock().getName().toString().endsWith("AIR")){
        if (BaseFireBlock.canBePlacedAt(level, blockpos.above(), pContext.getHorizontalDirection())) {
            level.setBlock(blockpos.above(), ModBlocks.GYRO_ARENA_CORE.get().defaultBlockState(),1);
        }*/

        /*if (!CampfireBlock.canLight(blockstate) && !CandleBlock.canLight(blockstate) && !CandleCakeBlock.canLight(blockstate)) {
            BlockPos blockpos1 = blockpos.relative(pContext.getClickedFace());
            if (BaseFireBlock.canBePlacedAt(level, blockpos1, pContext.getHorizontalDirection())) {
                level.playSound(player, blockpos1, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
                BlockState blockstate1 = BaseFireBlock.getState(level, blockpos1);
                level.setBlock(blockpos1, blockstate1, 11);
                level.gameEvent(player, GameEvent.BLOCK_PLACE, blockpos);
                ItemStack itemstack = pContext.getItemInHand();
                if (player instanceof ServerPlayer) {
                    CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)player, blockpos1, itemstack);
                    itemstack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(pContext.getHand()));
                }

                return InteractionResult.sidedSuccess(level.isClientSide());
            } else {
                return InteractionResult.FAIL;
            }
        } else {
            level.playSound(player, blockpos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
            level.setBlock(blockpos, blockstate.setValue(BlockStateProperties.LIT, Boolean.valueOf(true)), 11);
            level.gameEvent(player, GameEvent.BLOCK_CHANGE, blockpos);
            if (player != null) {
                pContext.getItemInHand().hurtAndBreak(1, player, LivingEntity.getSlotForHand(pContext.getHand()));
            }

            return InteractionResult.sidedSuccess(level.isClientSide());
        }
        return InteractionResult.sidedSuccess(level.isClientSide());*/