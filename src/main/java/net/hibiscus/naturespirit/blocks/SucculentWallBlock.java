package net.hibiscus.naturespirit.blocks;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.serialization.MapCodec;
import java.util.Map;
import net.hibiscus.naturespirit.registration.NSTags;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.SideShapeType;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class SucculentWallBlock
    extends SucculentBlock {

  public static final MapCodec<SucculentWallBlock> CODEC = SucculentWallBlock.createCodec(SucculentWallBlock::new);
  public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
  private static final Map<Direction, VoxelShape> FACING_TO_SHAPE = Maps.newEnumMap(
      ImmutableMap.of(Direction.NORTH, Block.createCuboidShape(0D, 4D, 5D, 16D, 12D, 16D), Direction.SOUTH, Block.createCuboidShape(0D, 4D, 0D, 16D, 12D, 11D), Direction.WEST,
          Block.createCuboidShape(5D, 4D, 0D, 16D, 12D, 16D), Direction.EAST, Block.createCuboidShape(0D, 4D, 0D, 11D, 12D, 16D)));

  public MapCodec<? extends SucculentWallBlock> getCodec() {
    return CODEC;
  }

  public SucculentWallBlock(AbstractBlock.Settings settings) {
    super(settings);
    this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(WATERLOGGED, false));
  }

  @Override
  public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    return FACING_TO_SHAPE.get(state.get(FACING));
  }

  @Override
  public BlockState rotate(BlockState state, BlockRotation rotation) {
    return state.with(FACING, rotation.rotate(state.get(FACING)));
  }

  @Override
  public BlockState mirror(BlockState state, BlockMirror mirror) {
    return state.rotate(mirror.getRotation(state.get(FACING)));
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    builder.add(FACING, WATERLOGGED);
  }

  @Override
  public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
    if (state.get(WATERLOGGED)) {
      world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
    }
    if (direction.getOpposite() == state.get(FACING) && !state.canPlaceAt(world, pos)) {
      return Blocks.AIR.getDefaultState();
    }
    return state;
  }

  @Override
  public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
    Direction direction = state.get(FACING);
    BlockPos blockPos = pos.offset(direction.getOpposite());
    BlockState blockState = world.getBlockState(blockPos);
    return blockState.isSideSolid(world, blockPos, direction, SideShapeType.CENTER) || blockState.isIn(NSTags.Blocks.SUCCULENT_HORIZONTAL_PLACEMENT_OVERRIDE);
  }

  @Override
  @Nullable
  public BlockState getPlacementState(ItemPlacementContext ctx) {
    BlockState blockState = super.getPlacementState(ctx);
    World worldView = ctx.getWorld();
    BlockPos blockPos = ctx.getBlockPos();
    for (Direction direction : ctx.getPlacementDirections()) {
      if (!direction.getAxis().isHorizontal() || !(blockState = blockState.with(FACING, direction.getOpposite())).canPlaceAt(worldView, blockPos)) {
        continue;
      }
      return blockState;
    }
    return null;
  }
}

