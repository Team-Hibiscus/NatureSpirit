package net.hibiscus.naturespirit.blocks;

import com.mojang.serialization.MapCodec;
import net.hibiscus.naturespirit.registration.NSMiscBlocks;
import net.hibiscus.naturespirit.registration.NSTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FacingBlock;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.RodBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.GameEvent.Emitter;

public class OliveBranchBlock extends RodBlock implements Fertilizable {

  public OliveBranchBlock(Settings settings) {
    super(settings);
    this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(FACING, Direction.UP).with(AGE, 0));
  }
  public static final IntProperty AGE = Properties.AGE_3;

  public BlockState getPlacementState(ItemPlacementContext ctx) {
    Direction direction = ctx.getSide();
    BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos().offset(direction.getOpposite()));
    return blockState.isOf(this) && blockState.get(FACING) == direction ? (BlockState)this.getDefaultState().with(FACING, direction.getOpposite()) : (BlockState)this.getDefaultState().with(FACING, direction);
  }

  @Override
  protected MapCodec<? extends RodBlock> getCodec() {
    return null;
  }

  @Override
  public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
    return state.get(AGE) < 3 || ((world.getBlockState(pos.offset(state.get(FACING), 1)).isOf(Blocks.AIR) || world.getBlockState(pos.offset(state.get(FACING).getOpposite(), 1)).isOf(Blocks.AIR)) && state.get(AGE) == 3);
  }

  @Override
  public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
    return true;
  }
  protected boolean canPathfindThrough(BlockState state, NavigationType type) {
    return type == NavigationType.AIR;
  }

  protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
    return stack.isOf(Items.BONE_MEAL) ? ItemActionResult.SKIP_DEFAULT_BLOCK_INTERACTION : super.onUseWithItem(stack, state, world, pos, player, hand, hit);
  }

  @Override
  public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
    int i = state.get(AGE);
    boolean bl = i == 3;
    if (i > 1) {
      int j = 1 + world.random.nextInt(2);
      dropStack(world, pos, new ItemStack(NSMiscBlocks.OLIVES, j + (bl ? 1 : 0)));
      world.playSound(null, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);
      world.setBlockState(pos, state.with(AGE, 0));
      world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
      return ActionResult.success(world.isClient());
    }
    return super.onUse(state, world, pos, player, hit);

  }

  @Override
  public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
    int i = state.get(AGE);
    if (world.getBaseLightLevel(pos, 0) >= 9 && i < 3) {
      if (random.nextInt(5) == 0) {
          state = state.with(AGE, i + 1);
          world.setBlockState(pos, state, Block.NOTIFY_LISTENERS);
          world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, Emitter.of(state));
      }
    }
  }

  @Override
  public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
    if (state.get(AGE) > 2) {
      BlockPos adjacentPos = pos.offset(state.get(FACING), 1);
      BlockState adjacentState = world.getBlockState(adjacentPos);
      BlockPos adjacentPos2 = pos.offset(state.get(FACING).getOpposite(), 1);
      BlockState adjacentState2 = world.getBlockState(adjacentPos2);
      if (adjacentState.isOf(Blocks.AIR)) {
        world.setBlockState(adjacentPos, state.with(AGE, 0), Block.NOTIFY_LISTENERS);
      } else
      if (adjacentState2.isOf(Blocks.AIR)) {
        world.setBlockState(adjacentPos2, state.with(AGE, 0), Block.NOTIFY_LISTENERS);
      }
    } else {
        world.setBlockState(pos, state.with(AGE, state.get(AGE) + 1));
    }
  }
  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    builder.add(AGE, FACING);
  }
}
