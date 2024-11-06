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
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;

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

  @Override
  public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
    if (state.get(AGE) == 3 && !player.getStackInHand(player.getActiveHand()).isOf(Items.BONE_MEAL)) {
      ItemStack itemStack = new ItemStack(NSMiscBlocks.GREEN_OLIVES, world.getRandom().nextBetween(1, 3));
      if (!player.giveItemStack(itemStack)) {
        player.dropItem(itemStack, false);
      }
      world.setBlockState(pos, state.with(AGE, 0));
      world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
      return ActionResult.success(world.isClient());
    }
    return super.onUse(state, world, pos, player, hit);

  }

  @Override
  public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
    if (world.getBaseLightLevel(pos, 0) >= 9) {
      if (random.nextInt(4) == 0) {
        int i = state.get(AGE);
        if (i < 3) {
          state = state.with(AGE, i + 1);
          world.setBlockState(pos, state, Block.NOTIFY_LISTENERS);
        }
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
