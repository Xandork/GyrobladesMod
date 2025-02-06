package net.xandork.gyrobladesmod.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.xandork.gyrobladesmod.entity.custom.BeigomaEntity;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class GyroArenaEdge extends HorizontalDirectionalBlock {
    protected static final VoxelShape south = Stream.of(
            Block.box(0, 0, 12, 16, 2, 16),
            Block.box(0, 0, 8, 16, 3, 12),
            Block.box(0, 0, 4, 16, 4, 8),
            Block.box(0, 0, 0, 16, 8, 4)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    protected static final VoxelShape north = Stream.of(
            Block.box(0, 0, 0, 16, 2, 4),
            Block.box(0, 0, 4, 16, 3, 8),
            Block.box(0, 0, 8, 16, 4, 12),
            Block.box(0, 0, 12, 16, 8, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    protected static final VoxelShape west = Stream.of(
            Block.box(0, 0, 0, 4, 2, 16),
            Block.box(4, 0, 0, 8, 3, 16),
            Block.box(8, 0, 0, 12, 4, 16),
            Block.box(12, 0, 0, 16, 8, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    protected static final VoxelShape east = Stream.of(
            Block.box(12, 0, 0, 16, 2, 16),
            Block.box(8, 0, 0, 12, 3, 16),
            Block.box(4, 0, 0, 8, 4, 16),
            Block.box(0, 0, 0, 4, 8, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public static final MapCodec<GyroArenaEdge> CODEC = simpleCodec(GyroArenaEdge::new);
    //public static final VoxelShape SHAPE = Block.box(-16,0,-16,16,1,16);
    //public static final VoxelShape SHAPE = Block.(-16,0,-16,16,1,16);


    public GyroArenaEdge(Properties pProperties) {
        super(pProperties);
    }


    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return switch (pState.getValue(FACING)) {
            case NORTH:{
                yield north;
            }
            case SOUTH:{
                yield south;
            }
            case EAST:{
                yield east;
            }
            case WEST:{
                yield west;
            }
            case UP:{
                yield north;
            }
            case DOWN:{
                yield north;
            }
            default:{
                yield north;
            }
        };
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
    protected void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if (pLevel.getBlockState(pPos.north()).getBlock() instanceof GyroArenaCore){
            pLevel.destroyBlock(pPos.north(),true);
            //pLevel.getBlockState(pPos.north()).getBlock().destroy(pLevel,pPos,pNewState);
        }
        if (pLevel.getBlockState(pPos.south()).getBlock() instanceof GyroArenaCore){
            pLevel.destroyBlock(pPos.south(),true);
            //pLevel.getBlockState(pPos.south()).getBlock().destroy(pLevel,pPos,pNewState);
        }
        if (pLevel.getBlockState(pPos.east()).getBlock() instanceof GyroArenaCore){
            pLevel.destroyBlock(pPos.east(),true);
            //pLevel.getBlockState(pPos.east()).getBlock().destroy(pLevel,pPos,pNewState);
        }
        if (pLevel.getBlockState(pPos.west()).getBlock() instanceof GyroArenaCore){
            pLevel.destroyBlock(pPos.west(),true);
            //pLevel.getBlockState(pPos.west()).getBlock().destroy(pLevel,pPos,pNewState);
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }
}
