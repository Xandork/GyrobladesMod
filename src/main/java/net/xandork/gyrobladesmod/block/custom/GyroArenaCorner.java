package net.xandork.gyrobladesmod.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class GyroArenaCorner extends HorizontalDirectionalBlock {
    protected static final VoxelShape southwest = Stream.of(
            Block.box(0, 0, 12, 4, 2, 16),
            Block.box(4, 0, 8, 8, 3, 16),
            Block.box(0, 0, 8, 4, 3, 12),
            Block.box(8, 0, 4, 12, 4, 16),
            Block.box(0, 0, 4, 8, 4, 8),
            Block.box(12, 0, 0, 16, 8, 16),
            Block.box(0, 0, 0, 12, 8, 4)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    protected static final VoxelShape westnorth = Stream.of(
            Block.box(0, 0, 0, 4, 2, 4),
            Block.box(0, 0, 4, 8, 3, 8),
            Block.box(4, 0, 0, 8, 3, 4),
            Block.box(0, 0, 8, 12, 4, 12),
            Block.box(8, 0, 0, 12, 4, 8),
            Block.box(0, 0, 12, 16, 8, 16),
            Block.box(12, 0, 0, 16, 8, 12)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    protected static final VoxelShape northeast = Stream.of(
            Block.box(12, 0, 0, 16, 2, 4),
            Block.box(8, 0, 0, 12, 3, 8),
            Block.box(12, 0, 4, 16, 3, 8),
            Block.box(4, 0, 0, 8, 4, 12),
            Block.box(8, 0, 8, 16, 4, 12),
            Block.box(0, 0, 0, 4, 8, 16),
            Block.box(4, 0, 12, 16, 8, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    protected static final VoxelShape eastsouth = Stream.of(
            Block.box(12, 0, 12, 16, 2, 16),
            Block.box(8, 0, 8, 16, 3, 12),
            Block.box(8, 0, 12, 12, 3, 16),
            Block.box(4, 0, 4, 16, 4, 8),
            Block.box(4, 0, 8, 8, 4, 16),
            Block.box(0, 0, 0, 16, 8, 4),
            Block.box(0, 0, 4, 4, 8, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static final MapCodec<GyroArenaCorner> CODEC = simpleCodec(GyroArenaCorner::new);
    //public static final VoxelShape SHAPE = Block.box(-16,0,-16,16,1,16);
    //public static final VoxelShape SHAPE = Block.(-16,0,-16,16,1,16);


    public GyroArenaCorner(Properties pProperties) {
        super(pProperties);
    }


    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return switch (pState.getValue(FACING)) {
            case NORTH:{
                yield northeast;
            }
            case SOUTH:{
                yield southwest;
            }
            case EAST:{
                yield eastsouth;
            }
            case WEST:{
                yield westnorth;
            }
            case UP:{
                yield northeast;
            }
            case DOWN:{
                yield northeast;
            }
            default:{
                yield northeast;
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
        if (pLevel.getBlockState(pPos.north().east()).getBlock() instanceof GyroArenaCore){
            pLevel.destroyBlock(pPos.north().east(),true);
            //pLevel.getBlockState(pPos.north().east()).getBlock().destroy(pLevel,pPos,pNewState);
        }
        if (pLevel.getBlockState(pPos.north().west()).getBlock() instanceof GyroArenaCore){
            pLevel.destroyBlock(pPos.north().west(),true);
            //pLevel.getBlockState(pPos.north().west()).getBlock().destroy(pLevel,pPos,pNewState);
        }
        if (pLevel.getBlockState(pPos.south().east()).getBlock() instanceof GyroArenaCore){
            pLevel.destroyBlock(pPos.south().east(),true);
            //pLevel.getBlockState(pPos.south().east()).getBlock().destroy(pLevel,pPos,pNewState);
        }
        if (pLevel.getBlockState(pPos.south().west()).getBlock() instanceof GyroArenaCore){
            pLevel.destroyBlock(pPos.south().west(),true);
            //pLevel.getBlockState(pPos.south().west()).getBlock().destroy(pLevel,pPos,pNewState);
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }
}
