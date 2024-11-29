package net.hibiscus.naturespirit.blocks;

import java.util.Optional;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockLocating;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class LotusStemBlock extends Block implements Waterloggable {

  public static final IntProperty AGE;
  public static final BooleanProperty WATERLOGGED;
  public static final VoxelShape SHAPE = Block.createCuboidShape(1D, 0D, 1D, 15D, 16D, 15D);
  public static final Direction GROWTH_DIRECTION;

  static {
    GROWTH_DIRECTION = Direction.UP;
    AGE = Properties.AGE_3;
    WATERLOGGED = Properties.WATERLOGGED;
  }

  public final Block headBlock;

  public LotusStemBlock(Settings properties, Block headBlock) {
    super(properties);
    this.headBlock = headBlock;
    this.setDefaultState(this.stateManager.getDefaultState());
  }

  @Nullable
  public BlockState getPlacementState(ItemPlacementContext ctx) {
    BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos().offset(GROWTH_DIRECTION));
    FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
    boolean waterlogged = fluidState.isIn(FluidTags.WATER) && fluidState.getLevel() == 8;
    return !blockState.isOf(this.asBlock()) && !blockState.isOf(headBlock) ?
        this.getRandomGrowthState(ctx.getWorld()).with(WATERLOGGED, waterlogged) :
        this.asBlock().getDefaultState().with(WATERLOGGED, waterlogged);
  }

  @Override
  public boolean canPlaceAt(BlockState state, WorldView levelReader, BlockPos pos) {
    BlockPos blockPos = pos.offset(GROWTH_DIRECTION.getOpposite());
    BlockState blockState = levelReader.getBlockState(blockPos);
    if (levelReader.getFluidState(pos).isIn(FluidTags.WATER)) {
      return (blockState.isSideSolidFullSquare(levelReader, pos, Direction.UP) || blockState.isOf(this.asBlock())) && !blockState.isOf(Blocks.MAGMA_BLOCK);
    }
    return blockState.isOf(this.asBlock()) || blockState.isIn(BlockTags.DIRT) || blockState.isOf(Blocks.CLAY) || blockState.isOf(Blocks.FARMLAND);
  }

  @Override
  public boolean hasRandomTicks(BlockState state) {
    return state.get(AGE) < 3;
  }

  @Override
  public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
    double growthChance = 0.1D;
    if (state.get(AGE) < 3 && random.nextDouble() < growthChance && this.isFertilizable(world, pos, state, world.isClient())) {
      BlockPos blockPos = pos.offset(GROWTH_DIRECTION);
      if (world.getBlockState(blockPos).isAir()) {
        world.setBlockState(blockPos, headBlock.getDefaultState());
      } else if (world.getFluidState(blockPos).isIn(FluidTags.WATER)) {
        world.setBlockState(blockPos, this.getDefaultState().with(WATERLOGGED, world.getFluidState(blockPos).isIn(FluidTags.WATER)).with(AGE, 2));
        if (world.getBlockState(blockPos.up()).isAir()) {
          world.setBlockState(blockPos.up(), headBlock.getDefaultState());
        }
      } else if (world.getBlockState(blockPos).isOf(headBlock)) {
        if (!world.getFluidState(pos).isIn(FluidTags.WATER)) {
          world.setBlockState(blockPos, this.getDefaultState().with(WATERLOGGED, world.getFluidState(blockPos).isIn(FluidTags.WATER)).with(AGE, Math.min(state.get(AGE) + 1, 3)));
          world.setBlockState(blockPos.up(), headBlock.getDefaultState());
        } else {
          world.setBlockState(pos, this.getDefaultState().with(WATERLOGGED, world.getFluidState(pos).isIn(FluidTags.WATER)).with(AGE, 3));
        }
      }
    }

  }

  @Override
  public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
    if (direction == GROWTH_DIRECTION.getOpposite() && !state.canPlaceAt(world, pos)) {
      world.scheduleBlockTick(pos, this.asBlock(), 1);
    }
    if (direction == GROWTH_DIRECTION && neighborState.isAir()) {
      world.setBlockState(pos, state.with(LotusStemBlock.AGE, 2), Block.NOTIFY_LISTENERS);
    }
    return state;
  }

  public BlockState getRandomGrowthState(WorldAccess world) {
    return this.getDefaultState().with(AGE, world.getRandom().nextInt(3));
  }

  protected BlockState age(BlockState state, Random random) {
    return state.cycle(AGE);
  }

  public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
    Optional<BlockPos> optional = this.getFlowerHeadPos(world, pos, this.asBlock());
    Optional<BlockPos> optional2 = this.getStemHeadPos(world, pos, this.asBlock());
    Optional<BlockPos> optional3 = this.getWaterPos(world, pos, this.asBlock());
    BlockPos blockPos = optional2.isPresent() ? optional2.get().down() : optional3.isPresent() ? optional3.get().down() : optional.orElse(pos);
    boolean lotusPowered = false;
    if (optional.isPresent()) {
      BlockPos blockPos2 = optional.get();
      LotusFlowerBlock lotusFlowerBlock = (LotusFlowerBlock) world.getBlockState(blockPos2).getBlock();
      lotusPowered = lotusFlowerBlock.isPowered(world, blockPos2);
    }
    return (world.getBlockState(blockPos.offset(GROWTH_DIRECTION)).isAir() || world.getFluidState(blockPos.offset(GROWTH_DIRECTION)).isIn(FluidTags.WATER)) && !lotusPowered;
  }

  public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
    return true;
  }

  public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
    Optional<BlockPos> optional = this.getFlowerHeadPos(world, pos, this.asBlock());
    Optional<BlockPos> optional2 = this.getStemHeadPos(world, pos, this.asBlock());
    Optional<BlockPos> optional3 = this.getWaterPos(world, pos, this.asBlock());
    BlockPos blockPos = optional2.isPresent() ? optional2.get().down() : optional3.isPresent() ? optional3.get().down() : optional.orElse(pos);
    int i = Math.min(state.get(AGE) + 1, 3);

    if (world.getBlockState(blockPos).isAir() || world.getBlockState(blockPos).isOf(headBlock) || world.getBlockState(blockPos).isOf(this.asBlock()) || world.getFluidState(
        blockPos).isIn(FluidTags.WATER)) {
      world.setBlockState(blockPos, state.with(AGE, i).with(WATERLOGGED, world.getFluidState(blockPos).isIn(FluidTags.WATER)));
      if (world.getBlockState(blockPos.up()).isOf(Blocks.AIR)) {
        world.setBlockState(blockPos.offset(GROWTH_DIRECTION, 1), headBlock.getDefaultState());
      }
      if (world.getBlockState(blockPos.up()).isOf(Blocks.WATER)) {
        world.setBlockState(blockPos.offset(GROWTH_DIRECTION, 1),
            this.asBlock().getDefaultState().with(AGE, i).with(WATERLOGGED, world.getFluidState(blockPos.offset(GROWTH_DIRECTION, 1)).isIn(FluidTags.WATER)));
      }
      if (optional.isPresent()) {
        BlockPos blockPos2 = optional.get();
        PlayerEntity player = world.getClosestPlayer(blockPos2.getX(), blockPos2.getY(), blockPos2.getZ(), 1.25D, false);
        if (player != null) {
          player.move(MovementType.SHULKER_BOX, new Vec3d(0, 1.01, 0));
        }
      }
    }

  }

  private Optional<BlockPos> getFlowerHeadPos(BlockView world, BlockPos pos, Block block) {
    return BlockLocating.findColumnEnd(world, pos, block, GROWTH_DIRECTION, headBlock);
  }

  private Optional<BlockPos> getStemHeadPos(BlockView world, BlockPos pos, Block block) {
    return BlockLocating.findColumnEnd(world, pos, block, GROWTH_DIRECTION, Blocks.AIR);
  }

  public static Optional<BlockPos> getStemHeadWaterPos(BlockView world, BlockPos pos, Block block) {
    return BlockLocating.findColumnEnd(world, pos, Blocks.WATER, GROWTH_DIRECTION, block);
  }

  private Optional<BlockPos> getWaterPos(BlockView world, BlockPos pos, Block block) {
    return BlockLocating.findColumnEnd(world, pos, block, GROWTH_DIRECTION, Blocks.WATER);
  }

  @Override
  public FluidState getFluidState(BlockState state) {
    return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
  }

  @Override
  public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
    if (!state.canPlaceAt(world, pos)) {
      world.breakBlock(pos, true);
    }
  }

  @Override
  public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    return SHAPE;
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    builder.add(AGE).add(WATERLOGGED);
  }

}
