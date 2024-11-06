package net.hibiscus.naturespirit.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.block.Waterloggable;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;

public class TallSedgeGrassBlock extends TallPlantBlock implements Waterloggable {

  public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
  protected static final float AABB_OFFSET = 6F;
  protected static final VoxelShape SHAPE = Block.createCuboidShape(2D, 0D, 2D, 14D, 16D, 14D);

  public TallSedgeGrassBlock(Settings properties) {
    super(properties);
    this.setDefaultState(this.stateManager.getDefaultState().with(WATERLOGGED, false).with(HALF, DoubleBlockHalf.LOWER));
  }

  @Override
  protected boolean canPlantOnTop(BlockState state, BlockView level, BlockPos pos) {
    if (level.getFluidState(pos.up()).isIn(FluidTags.WATER)) {
      return state.isSideSolidFullSquare(level, pos, Direction.UP) && !state.isOf(Blocks.MAGMA_BLOCK);
    } else {
      return state.isIn(BlockTags.DIRT) || state.isOf(Blocks.FARMLAND) || state.isOf(Blocks.CLAY);
    }
  }

  @Override
  public BlockState getPlacementState(ItemPlacementContext context) {
    FluidState fluidState = context.getWorld().getFluidState(context.getBlockPos());
    return super.getPlacementState(context) != null ? this.getDefaultState().with(WATERLOGGED, fluidState.isIn(FluidTags.WATER) && fluidState.getLevel() == 8) : null;
  }

  @Override
  public boolean canPlaceAt(BlockState state, WorldView level, BlockPos pos) {
    if (state.get(HALF) == DoubleBlockHalf.UPPER) {
      BlockState blockState = level.getBlockState(pos.down());
      return blockState.isOf(this) && blockState.get(HALF) == DoubleBlockHalf.LOWER;
    } else {
      BlockPos blockPos = pos.down();
      BlockPos blockPos2 = pos.up();
      if (state.get(WATERLOGGED)) {
        return super.canPlaceAt(state, level, pos) && level.getBlockState(blockPos).isSideSolidFullSquare(level, blockPos, Direction.UP) && !level.getFluidState(blockPos2)
            .isIn(FluidTags.WATER);
      } else {
        return super.canPlaceAt(state, level, pos) && this.canPlantOnTop(level.getBlockState(blockPos), level, blockPos);
      }
    }
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    super.appendProperties(builder);
    builder.add(WATERLOGGED);
  }

  @Override
  public FluidState getFluidState(BlockState state) {
    return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
  }
}
