package net.hibiscus.naturespirit.blocks;

import java.util.Optional;
import net.hibiscus.naturespirit.NatureSpirit;
import net.hibiscus.naturespirit.registration.NSMiscBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CakeBlock;
import net.minecraft.block.FluidDrainable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.event.GameEvent;

public class CheeseBlock extends CakeBlock implements FluidDrainable {

  public CheeseBlock(Settings settings) {
    super(settings);
  }

  @Override
  public ItemStack tryDrainFluid(PlayerEntity player, WorldAccess world, BlockPos pos, BlockState state) {
    if (world.getBlockState(pos).get(BITES) == 0) {
      world.setBlockState(pos, Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL_AND_REDRAW);
      if (!world.isClient()) {
        world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, pos, Block.getRawIdFromState(state));
      }

      return new ItemStack(NSMiscBlocks.CHEESE_BUCKET);
    }
    return new ItemStack(Items.BUCKET);
  }
  protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
    return stack.isOf(Items.BUCKET) && state.get(BITES) == 0 ? ItemActionResult.SKIP_DEFAULT_BLOCK_INTERACTION : super.onUseWithItem(stack, state, world, pos, player, hand, hit);
  }
  @Override
  public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
    if (world.isClient) {
      if (tryEat(world, pos, state, player).isAccepted()) {
        return ActionResult.SUCCESS;
      }

      if (player.getStackInHand(Hand.MAIN_HAND).isEmpty()) {
        return ActionResult.CONSUME;
      }
    }

    return tryEat(world, pos, state, player);
  }

  protected static ActionResult tryEat(WorldAccess world, BlockPos pos, BlockState state, PlayerEntity player) {
    if (!player.canConsume(false)) {
      return ActionResult.PASS;
    } else {
      player.incrementStat(NatureSpirit.EAT_CHEESE);
      player.getHungerManager().add(2, 0.1F);
      int i = state.get(BITES);
      world.emitGameEvent(player, GameEvent.EAT, pos);
      if (i < 6) {
        world.setBlockState(pos, state.with(BITES, i + 1), Block.NOTIFY_ALL);
      } else {
        world.removeBlock(pos, false);
        world.emitGameEvent(player, GameEvent.BLOCK_DESTROY, pos);
      }

      return ActionResult.SUCCESS;
    }
  }

  @Override
  public Optional<SoundEvent> getBucketFillSound() {
    return Optional.of(SoundEvents.ITEM_BUCKET_FILL);
  }
}
