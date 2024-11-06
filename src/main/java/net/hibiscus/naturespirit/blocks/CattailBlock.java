package net.hibiscus.naturespirit.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.block.Waterloggable;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class CattailBlock extends TallPlantBlock implements Waterloggable, Fertilizable {

  public static final EnumProperty<DoubleBlockHalf> HALF;
  public static final BooleanProperty WATERLOGGED;

  protected static final VoxelShape SHAPE;

  static {
    WATERLOGGED = Properties.WATERLOGGED;
    HALF = TallPlantBlock.HALF;
    SHAPE = Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
  }

  public CattailBlock(Settings properties) {
    super(properties);
    this.setDefaultState(this.stateManager.getDefaultState().with(WATERLOGGED, false).with(HALF, DoubleBlockHalf.LOWER));
  }

  public boolean canReplace(BlockState state, ItemPlacementContext useContext) {
    return false;
  }

  public boolean isFertilizable(WorldView levelReader, BlockPos blockPos, BlockState blockState) {
    return true;
  }

  public boolean canGrow(World level, Random randomSource, BlockPos blockPos, BlockState blockState) {
    return true;
  }

  public void grow(ServerWorld serverLevel, Random randomSource, BlockPos blockPos, BlockState blockState) {
    dropStack(serverLevel, blockPos, new ItemStack(this));
  }

  protected boolean canPlantOnTop(BlockState state, BlockView level, BlockPos pos) {
    if (level.getFluidState(pos.up()).isIn(FluidTags.WATER)) {
      return state.isSideSolidFullSquare(level, pos, Direction.UP) && !state.isOf(Blocks.MAGMA_BLOCK);
    } else {
      return state.isIn(BlockTags.DIRT) || state.isOf(Blocks.FARMLAND) || state.isOf(Blocks.SAND) || state.isOf(Blocks.RED_SAND);
    }
  }

  public BlockState getPlacementState(ItemPlacementContext context) {
    FluidState fluidState = context.getWorld().getFluidState(context.getBlockPos());
    return super.getPlacementState(context) != null ? this.getDefaultState().with(WATERLOGGED, fluidState.isIn(FluidTags.WATER) && fluidState.getLevel() == 8) : null;
  }

  public boolean canPlaceAt(BlockState state, WorldView level, BlockPos pos) {
    if (state.get(HALF) == DoubleBlockHalf.UPPER) {
      BlockState blockState = level.getBlockState(pos.down());
      return blockState.isOf(this) && blockState.get(HALF) == DoubleBlockHalf.LOWER;
    } else {
      BlockPos blockPos = pos.down();
      BlockPos blockPos2 = pos.up();
      if (state.get(WATERLOGGED)) {
        return super.canPlaceAt(state, level, pos) && this.canPlantOnTop(level.getBlockState(blockPos), level, blockPos) && !level.getFluidState(blockPos2).isIn(FluidTags.WATER);
      } else {
        return super.canPlaceAt(state, level, pos) && this.canPlantOnTop(level.getBlockState(blockPos), level, blockPos);
      }
    }
  }

  public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
    if (state.get(WATERLOGGED)) {
      world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
    }

    return direction == Direction.DOWN && !this.canPlaceAt(state, world, pos) ? Blocks.AIR.getDefaultState()
        : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
  }

  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    builder.add(WATERLOGGED);
    builder.add(HALF);
  }

  public FluidState getFluidState(BlockState state) {
    return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
  }
}
