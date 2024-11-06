package net.hibiscus.naturespirit.blocks;


import com.mojang.serialization.MapCodec;
import net.hibiscus.naturespirit.registration.NSTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ConnectingBlock;
import net.minecraft.block.SideShapeType;
import net.minecraft.block.Waterloggable;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class BranchingTrunkBlock extends ConnectingBlock implements Waterloggable {

  public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

  public BranchingTrunkBlock(Settings settings) {
    super(0.3125F, settings);
    this.setDefaultState(
        this.stateManager.getDefaultState().with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false).with(UP, false).with(DOWN, false).with(WATERLOGGED, false));
  }

  @Override
  public BlockState getPlacementState(ItemPlacementContext ctx) {
    FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
    return this.withConnectionProperties(ctx.getWorld(), ctx.getBlockPos()).with(WATERLOGGED, fluidState.isIn(FluidTags.WATER) && fluidState.getLevel() == 8);
  }

  public BlockState withConnectionProperties(BlockView world, BlockPos pos) {
    BlockState blockState = world.getBlockState(pos.down());
    BlockState blockState2 = world.getBlockState(pos.up());
    BlockState blockState3 = world.getBlockState(pos.north());
    BlockState blockState4 = world.getBlockState(pos.east());
    BlockState blockState5 = world.getBlockState(pos.south());
    BlockState blockState6 = world.getBlockState(pos.west());
    return this.getDefaultState().with(DOWN,
        blockState.isIn(BlockTags.LEAVES) || (blockState.getBlock() instanceof BranchingTrunkBlock && !(blockState.getBlock() instanceof GrowingBranchingTrunkBlock)) || (
            blockState.getBlock() instanceof GrowingBranchingTrunkBlock && !blockState.get(GrowingBranchingTrunkBlock.SHEARED)) || blockState.isSideSolid(world, pos, Direction.UP,
            SideShapeType.CENTER)
    ).with(
        UP,
        blockState2.isIn(BlockTags.LEAVES) || (blockState2.getBlock() instanceof BranchingTrunkBlock && !(blockState2.getBlock() instanceof GrowingBranchingTrunkBlock)) || (
            blockState2.getBlock() instanceof GrowingBranchingTrunkBlock && !blockState2.get(GrowingBranchingTrunkBlock.SHEARED)) || blockState2.isSideSolid(world, pos,
            Direction.DOWN, SideShapeType.CENTER) || blockState2.isIn(
            NSTags.Blocks.SUCCULENTS)
    ).with(NORTH,
        blockState3.isIn(BlockTags.LEAVES) || (blockState3.getBlock() instanceof BranchingTrunkBlock && !(blockState3.getBlock() instanceof GrowingBranchingTrunkBlock)) || (
            blockState3.getBlock() instanceof GrowingBranchingTrunkBlock && !blockState3.get(GrowingBranchingTrunkBlock.SHEARED)) || blockState3.isSideSolid(world, pos,
            Direction.SOUTH, SideShapeType.CENTER)).with(
        EAST,
        blockState4.isIn(BlockTags.LEAVES) || (blockState4.getBlock() instanceof BranchingTrunkBlock && !(blockState4.getBlock() instanceof GrowingBranchingTrunkBlock)) || (
            blockState4.getBlock() instanceof GrowingBranchingTrunkBlock && !blockState4.get(GrowingBranchingTrunkBlock.SHEARED)) || blockState4.isSideSolid(world, pos,
            Direction.WEST, SideShapeType.CENTER)
    ).with(SOUTH,
        blockState5.isIn(BlockTags.LEAVES) || (blockState5.getBlock() instanceof BranchingTrunkBlock && !(blockState5.getBlock() instanceof GrowingBranchingTrunkBlock)) || (
            blockState5.getBlock() instanceof GrowingBranchingTrunkBlock && !blockState5.get(GrowingBranchingTrunkBlock.SHEARED)) || blockState5.isSideSolid(world, pos,
            Direction.NORTH, SideShapeType.CENTER)).with(
        WEST,
        blockState6.isIn(BlockTags.LEAVES) || (blockState6.getBlock() instanceof BranchingTrunkBlock && !(blockState6.getBlock() instanceof GrowingBranchingTrunkBlock)) || (
            blockState6.getBlock() instanceof GrowingBranchingTrunkBlock && !blockState6.get(GrowingBranchingTrunkBlock.SHEARED)) || blockState6.isSideSolid(world, pos,
            Direction.EAST, SideShapeType.CENTER)
    );
  }

  public BlockState withHorizontalConnectingProperties(BlockView world, BlockPos pos) {
    BlockState blockState3 = world.getBlockState(pos.north());
    BlockState blockState4 = world.getBlockState(pos.east());
    BlockState blockState5 = world.getBlockState(pos.south());
    BlockState blockState6 = world.getBlockState(pos.west());
    BlockState blockState = world.getBlockState(pos).getBlock() instanceof BranchingTrunkBlock ? world.getBlockState(pos) : this.getDefaultState();
    return blockState.with(NORTH,
            blockState3.isIn(BlockTags.LEAVES) || blockState3.getBlock() instanceof BranchingTrunkBlock || blockState3.isSideSolid(world, pos, Direction.SOUTH, SideShapeType.CENTER))
        .with(
            EAST,
            blockState4.isIn(BlockTags.LEAVES) || blockState4.getBlock() instanceof BranchingTrunkBlock || blockState4.isSideSolid(world, pos, Direction.WEST, SideShapeType.CENTER)
        ).with(SOUTH, blockState5.isIn(BlockTags.LEAVES) || blockState5.getBlock() instanceof BranchingTrunkBlock || blockState5.isSideSolid(world, pos, Direction.NORTH,
            SideShapeType.CENTER)).with(
            WEST,
            blockState6.isIn(BlockTags.LEAVES) || blockState6.getBlock() instanceof BranchingTrunkBlock || blockState6.isSideSolid(world, pos, Direction.EAST, SideShapeType.CENTER)
        );
  }

  public BlockState withConnectionPropertiesVertical(BlockView world, BlockPos pos) {
    BlockState blockState = world.getBlockState(pos.down());
    BlockState blockState2 = world.getBlockState(pos.up());
    BlockState blockState3 = world.getBlockState(pos).getBlock() instanceof BranchingTrunkBlock ? world.getBlockState(pos) : this.getDefaultState();
    return blockState3.with(DOWN,
        blockState.isIn(BlockTags.LEAVES) || blockState.getBlock() instanceof BranchingTrunkBlock || blockState.isSideSolid(world, pos, Direction.UP, SideShapeType.CENTER)
    ).with(
        UP,
        blockState2.isIn(BlockTags.LEAVES) || blockState2.getBlock() instanceof BranchingTrunkBlock || blockState2.isSideSolid(world, pos, Direction.DOWN, SideShapeType.CENTER)
    );
  }

  @Override
  public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
    boolean bl =
        neighborState.isSideSolid(world, pos, direction.getOpposite(), SideShapeType.CENTER) || neighborState.getBlock() instanceof BranchingTrunkBlock || neighborState.isIn(
            BlockTags.LEAVES) || (direction == Direction.UP && neighborState.isIn(
            NSTags.Blocks.SUCCULENTS));
    return state.with(FACING_PROPERTIES.get(direction), bl);
  }

  @Override
  public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
    if (!state.canPlaceAt(world, pos)) {
      world.breakBlock(pos, true);
    }

  }

  @Override
  public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
    return true;
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN, WATERLOGGED);
  }

  @Override
  protected boolean canPathfindThrough(BlockState state, NavigationType type) {
    return false;
  }

  @Override
  public FluidState getFluidState(BlockState state) {
    return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
  }

  @Override
  protected MapCodec<? extends ConnectingBlock> getCodec() {
    return null;
  }
}
