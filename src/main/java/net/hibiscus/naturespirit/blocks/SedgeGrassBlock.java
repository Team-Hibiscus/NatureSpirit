package net.hibiscus.naturespirit.blocks;

import net.hibiscus.naturespirit.registration.NSMiscBlocks;
import net.hibiscus.naturespirit.registration.NSTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShortPlantBlock;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class SedgeGrassBlock extends ShortPlantBlock implements Waterloggable {

  public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
  protected static final VoxelShape SHAPE = Block.createCuboidShape(2D, 0D, 2D, 14D, 16D, 14D);

  public SedgeGrassBlock(Settings settings) {
    super(settings);
    this.setDefaultState(this.stateManager.getDefaultState().with(WATERLOGGED, false));
  }

  @Override
  protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
    if (world.getFluidState(pos.up()).isIn(FluidTags.WATER)) {
      return floor.isSideSolidFullSquare(world, pos, Direction.UP) && !floor.isOf(Blocks.MAGMA_BLOCK);
    } else {
      return floor.isIn(NSTags.Blocks.TURNIP_STEM_GROWS_ON) || floor.isOf(Blocks.FARMLAND);
    }
  }

  @Override
  public boolean canReplace(BlockState state, ItemPlacementContext useContext) {
    return !useContext.getStack().isOf(NSMiscBlocks.AZOLLA_ITEM);
  }

  @Override
  public BlockState getPlacementState(ItemPlacementContext context) {
    FluidState fluidState = context.getWorld().getFluidState(context.getBlockPos());
    return this.getDefaultState().with(WATERLOGGED, fluidState.isIn(FluidTags.WATER) && fluidState.getLevel() == 8);
  }

  @Override
  public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
    TallPlantBlock tallPlantBlock = (TallPlantBlock) NSMiscBlocks.TALL_SEDGE_GRASS;
    if (tallPlantBlock.getDefaultState().canPlaceAt(world, pos) && world.isAir(pos.up())) {
      TallPlantBlock.placeAt(world, tallPlantBlock.getDefaultState(), pos, 2);
    }
  }

  @Override
  public boolean canPlaceAt(BlockState state, WorldView level, BlockPos pos) {
    BlockPos blockPos = pos.down();
    BlockPos blockPos2 = pos.up();
    if (state.get(WATERLOGGED)) {
      return super.canPlaceAt(state, level, pos) && level.getBlockState(blockPos).isSideSolidFullSquare(level, blockPos, Direction.UP) && !level.getFluidState(blockPos2)
          .isIn(FluidTags.WATER);
    } else {
      return super.canPlaceAt(state, level, pos) && this.canPlantOnTop(level.getBlockState(blockPos), level, blockPos);
    }

  }

  @Override
  public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
    if (state.get(WATERLOGGED)) {
      world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
    }

    return direction == Direction.DOWN && !this.canPlaceAt(state, world, pos) ? Blocks.AIR.getDefaultState()
        : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    builder.add(WATERLOGGED);
  }

  @Override
  public FluidState getFluidState(BlockState state) {
    return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
  }
}
