package net.hibiscus.naturespirit.blocks;

import com.mojang.serialization.MapCodec;
import net.hibiscus.naturespirit.registration.NSCriteria;
import net.hibiscus.naturespirit.registration.NSWoods;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;

public class CoconutBlock extends FallingBlock implements Fertilizable, Waterloggable {

  public static final DirectionProperty FACING;
  public static final BooleanProperty FILLED;
  public static final VoxelShape SHAPE;
  public static final VoxelShape NORTH_SHAPE;
  public static final VoxelShape SOUTH_SHAPE;
  public static final BooleanProperty WATERLOGGED;
  public static final VoxelShape WEST_SHAPE;
  public static final VoxelShape EAST_SHAPE;
  public static final VoxelShape CEILING_SHAPE;
  private static final float FALLING_BLOCK_ENTITY_DAMAGE_MULTIPLIER = 1.2F;
  private static final int FALLING_BLOCK_ENTITY_MAX_DAMAGE = 20;

  public CoconutBlock(Settings settings) {
    super(settings);
    this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.UP).with(WATERLOGGED, false).with(FILLED, true));
  }

  @Override
  protected MapCodec<? extends FallingBlock> getCodec() {
    return null;
  }

  @Override
  public BlockState getPlacementState(ItemPlacementContext ctx) {
    Direction direction = ctx.getSide();
    FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
    return this.getDefaultState().with(FACING, direction).with(WATERLOGGED, fluidState.isIn(FluidTags.WATER) && fluidState.getLevel() == 8);
  }

  @Override
  protected void configureFallingBlockEntity(FallingBlockEntity entity) {
    entity.setHurtEntities(FALLING_BLOCK_ENTITY_DAMAGE_MULTIPLIER, FALLING_BLOCK_ENTITY_MAX_DAMAGE);
  }

  public static boolean canFallThrough(BlockState state) {
    return state.isAir() || state.isIn(BlockTags.FIRE) || state.isLiquid() || state.isReplaceable();
  }

  @Override
  public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
    return world.getBlockState(pos.offset(state.get(FACING).getOpposite())).isSideSolidFullSquare(world, pos.offset(state.get(FACING).getOpposite()), state.get(FACING));
  }

  @Override
  public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
    Entity entity = projectile.getOwner();
    if (entity instanceof ServerPlayerEntity serverPlayerEntity) {
      NSCriteria.COCONUT_HIT_CRITERION.trigger(serverPlayerEntity, projectile);
    }
    if (canFallThrough(world.getBlockState(hit.getBlockPos().down())) && !state.get(WATERLOGGED)) {
      world.setBlockState(hit.getBlockPos(), state.with(FACING, Direction.UP));
    } else {
      world.breakBlock(hit.getBlockPos(), true);
    }
  }

  @Override
  public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
    ItemStack itemStack = player.getStackInHand(player.getActiveHand());
    boolean bl = state.get(FILLED);
    Item item = itemStack.getItem();
    if (itemStack.isOf(Items.BUCKET) && bl) {
      itemStack.decrement(1);
      world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_COW_MILK, SoundCategory.BLOCKS, 1.0F, 1.0F);
      if (itemStack.isEmpty()) {
        player.setStackInHand(player.getActiveHand(), new ItemStack(Items.MILK_BUCKET));
      } else if (!player.getInventory().insertStack(new ItemStack(Items.MILK_BUCKET))) {
        player.dropItem(new ItemStack(Items.MILK_BUCKET), false);
      }

      state.with(FILLED, false);
      world.emitGameEvent(player, GameEvent.FLUID_PICKUP, pos);
    }
    if (!world.isClient() && bl) {
      player.incrementStat(Stats.USED.getOrCreateStat(item));
    }
    return super.onUse(state, world, pos, player, hit);

  }

  @Override
  public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
    if (canFallThrough(world.getBlockState(pos.down())) && pos.getY() >= world.getBottomY() && world.getBlockState(pos.offset(state.get(FACING).getOpposite())).isAir()) {
      state = state.with(FACING, Direction.UP);
      FallingBlockEntity fallingBlockEntity = FallingBlockEntity.spawnFromBlock(world, pos, state);
      this.configureFallingBlockEntity(fallingBlockEntity);
    }
    if (!canFallThrough(world.getBlockState(pos.down())) && world.getBlockState(pos.offset(state.get(FACING).getOpposite())).isAir()) {
      state.with(FACING, Direction.UP);
    }
  }

  @Override
  public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    return switch (state.get(FACING)) {
      case DOWN -> CEILING_SHAPE;
      case UP -> SHAPE;
      case NORTH -> NORTH_SHAPE;
      case SOUTH -> SOUTH_SHAPE;
      case WEST -> WEST_SHAPE;
      case EAST -> EAST_SHAPE;
    };
  }

  @Override
  public FluidState getFluidState(BlockState state) {
    return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
  }

  @Override
  public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
  }

  @Override
  public BlockState rotate(BlockState state, BlockRotation rotation) {
    return state.with(FACING, rotation.rotate(state.get(FACING)));
  }

  @Override
  public BlockState mirror(BlockState state, BlockMirror mirror) {
    return state.with(FACING, mirror.apply(state.get(FACING)));
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    builder.add(FACING, FILLED, WATERLOGGED);
  }


  @Override
  public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
    return state.get(FACING) == Direction.UP;
  }

  @Override
  public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
    return true;
  }

  @Override
  public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
    world.setBlockState(pos, NSWoods.COCONUT_SPROUT.getDefaultState());
  }

  static {
    WATERLOGGED = Properties.WATERLOGGED;
    FACING = Properties.FACING;
    FILLED = BooleanProperty.of("filled");
    SHAPE = Block.createCuboidShape(3.0, 0.0, 3.0, 13.0, 10.0, 13.0);
    EAST_SHAPE = Block.createCuboidShape(0.0, 3.0, 3.0, 10.0, 13.0, 13.0);
    WEST_SHAPE = Block.createCuboidShape(6.0, 3.0, 3.0, 16.0, 13.0, 13.0);
    NORTH_SHAPE = Block.createCuboidShape(3.0, 3.0, 6.0, 13.0, 13.0, 16.0);
    SOUTH_SHAPE = Block.createCuboidShape(3.0, 3.0, 0.0, 13.0, 13.0, 10.0);
    CEILING_SHAPE = Block.createCuboidShape(3.0, 3.0, 3.0, 13.0, 13.0, 13.0);
  }
}
