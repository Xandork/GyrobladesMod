package net.xandork.gyrobladesmod.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.xandork.gyrobladesmod.block.ModBlocks;
import net.xandork.gyrobladesmod.entity.custom.BeigomaEntity;
import org.jetbrains.annotations.Nullable;

public class GyroArenaCore extends HorizontalDirectionalBlock {
    protected static final VoxelShape shape = Block.box(0, 0, 0, 16, 1, 16);

    public static final MapCodec<GyroArenaCore> CODEC = simpleCodec(GyroArenaCore::new);
    //public static final VoxelShape SHAPE = Block.box(-16,0,-16,16,1,16);
    //public static final VoxelShape SHAPE = Block.(-16,0,-16,16,1,16);


    public GyroArenaCore(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        for(BeigomaEntity entity: pLevel.getEntitiesOfClass(BeigomaEntity.class,new AABB(new Vec3(-1,0,-1),new Vec3(1,1,1)))){
            Vec3 Homing = new Vec3(entity.getX()-pPos.getX(),entity.getY()-pPos.getY(),entity.getZ()-pPos.getZ());
            entity.setPos(new Vec3(entity.getX()+((Homing.x)/10),entity.getY()+((Homing.y)/10),entity.getZ()+((Homing.z)/10)));
        }
        super.tick(pState, pLevel, pPos, pRandom);
    }


    @Override
    protected void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if (pEntity instanceof BeigomaEntity) {

        }

        super.entityInside(pState, pLevel, pPos, pEntity);
    }


    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return shape;
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return null;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING,pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    @Override
    public void destroy(LevelAccessor pLevel, BlockPos pPos, BlockState pState) {
        if(pLevel.getBlockState(pPos.north()).getBlock() instanceof GyroArenaEdge){
            pLevel.destroyBlock(pPos.north(),false);
        }
        if(pLevel.getBlockState(pPos.south()).getBlock() instanceof GyroArenaEdge){
            pLevel.destroyBlock(pPos.south(),false);
        }
        if(pLevel.getBlockState(pPos.east()).getBlock() instanceof GyroArenaEdge){
            pLevel.destroyBlock(pPos.east(),false);
        }
        if(pLevel.getBlockState(pPos.west()).getBlock() instanceof GyroArenaEdge){
            pLevel.destroyBlock(pPos.west(),false);
        }
        if(pLevel.getBlockState(pPos.north().east()).getBlock() instanceof GyroArenaCorner){
            pLevel.destroyBlock(pPos.north().east(),false);
        }
        if(pLevel.getBlockState(pPos.north().west()).getBlock() instanceof GyroArenaCorner){
            pLevel.destroyBlock(pPos.north().west(),false);
        }
        if(pLevel.getBlockState(pPos.south().east()).getBlock() instanceof GyroArenaCorner){
            pLevel.destroyBlock(pPos.south().east(),false);
        }
        if(pLevel.getBlockState(pPos.south().west()).getBlock() instanceof GyroArenaCorner){
            pLevel.destroyBlock(pPos.south().west(),false);
        }
        super.destroy(pLevel, pPos, pState);
    }

    @Override
    protected void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if(pLevel.getBlockState(pPos.north()).getBlock() instanceof GyroArenaEdge){
            pLevel.destroyBlock(pPos.north(),false);
        }
        if(pLevel.getBlockState(pPos.south()).getBlock() instanceof GyroArenaEdge){
            pLevel.destroyBlock(pPos.south(),false);
        }
        if(pLevel.getBlockState(pPos.east()).getBlock() instanceof GyroArenaEdge){
            pLevel.destroyBlock(pPos.east(),false);
        }
        if(pLevel.getBlockState(pPos.west()).getBlock() instanceof GyroArenaEdge){
            pLevel.destroyBlock(pPos.west(),false);
        }
        if(pLevel.getBlockState(pPos.north().east()).getBlock() instanceof GyroArenaCorner){
            pLevel.destroyBlock(pPos.north().east(),false);
        }
        if(pLevel.getBlockState(pPos.north().west()).getBlock() instanceof GyroArenaCorner){
            pLevel.destroyBlock(pPos.north().west(),false);
        }
        if(pLevel.getBlockState(pPos.south().east()).getBlock() instanceof GyroArenaCorner){
            pLevel.destroyBlock(pPos.south().east(),false);
        }
        if(pLevel.getBlockState(pPos.south().west()).getBlock() instanceof GyroArenaCorner){
            pLevel.destroyBlock(pPos.south().west(),false);
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }
}
