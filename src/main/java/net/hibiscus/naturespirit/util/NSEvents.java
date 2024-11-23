package net.hibiscus.naturespirit.util;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.hibiscus.naturespirit.blocks.BranchingTrunkBlock;
import net.hibiscus.naturespirit.registration.NSMiscBlocks;
import net.hibiscus.naturespirit.registration.NSWoods;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.event.GameEvent;

public class NSEvents {

  public static void registerEvents() {
    UseBlockCallback.EVENT.register(((player, world, hand, hitResult) -> {
      BlockPos blockPos = hitResult.getBlockPos();
      BlockState blockState = world.getBlockState(blockPos);
      if ((blockState.isOf(NSWoods.JOSHUA.getLog()) || blockState.isOf(NSMiscBlocks.ALLUAUDIA)) && player.getStackInHand(hand).isIn(ItemTags.AXES)) {
        Block block = blockState.isOf(NSWoods.JOSHUA.getLog()) ? NSWoods.JOSHUA.getStrippedLog() : NSMiscBlocks.STRIPPED_ALLUAUDIA;
        BlockState blockState2 = block.getDefaultState().with(BranchingTrunkBlock.DOWN, blockState.get(BranchingTrunkBlock.DOWN)).with(
            BranchingTrunkBlock.UP,
            blockState.get(BranchingTrunkBlock.UP)
        ).with(BranchingTrunkBlock.NORTH, blockState.get(BranchingTrunkBlock.NORTH)).with(
            BranchingTrunkBlock.SOUTH,
            blockState.get(BranchingTrunkBlock.SOUTH)
        ).with(BranchingTrunkBlock.EAST, blockState.get(BranchingTrunkBlock.EAST)).with(BranchingTrunkBlock.WEST, blockState.get(BranchingTrunkBlock.WEST)).with(
            BranchingTrunkBlock.WATERLOGGED,
            blockState.get(BranchingTrunkBlock.WATERLOGGED)
        ).with(BranchingTrunkBlock.SHEARED, blockState.get(BranchingTrunkBlock.SHEARED));
        world.playSound(player, blockPos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);

        if (player instanceof ServerPlayerEntity) {
          Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, blockPos, player.getStackInHand(hand));
        }

        world.setBlockState(blockPos, blockState2, 11);
        world.emitGameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Emitter.of(player, blockState2));
        if (player != null) {
          player.getStackInHand(hand).damage(1, player, LivingEntity.getSlotForHand(player.getActiveHand()));
        }

        return ActionResult.success(world.isClient);
      }
      if (blockState.isIn(BlockTags.CAULDRONS) && player.getStackInHand(hand).isOf(Items.MILK_BUCKET) && !blockState.isOf(NSMiscBlocks.MILK_CAULDRON)) {
        world.playSound(player, blockPos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
        if (player instanceof ServerPlayerEntity) {
          Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, blockPos, player.getStackInHand(hand));
        }

        world.setBlockState(blockPos, NSMiscBlocks.MILK_CAULDRON.getDefaultState(), 11);
        world.emitGameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Emitter.of(player, NSMiscBlocks.MILK_CAULDRON.getDefaultState()));
        if (!player.isCreative() && !player.isSpectator()) {
          player.setStackInHand(hand, new ItemStack(Items.BUCKET));
        }

        return ActionResult.success(world.isClient);
      }
      if (blockState.isIn(BlockTags.CAULDRONS) && player.getStackInHand(hand).isOf(NSMiscBlocks.CHEESE_BUCKET) && !blockState.isOf(NSMiscBlocks.CHEESE_CAULDRON)) {
        world.playSound(player, blockPos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
        if (player instanceof ServerPlayerEntity) {
          Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, blockPos, player.getStackInHand(hand));
        }

        world.setBlockState(blockPos, NSMiscBlocks.CHEESE_CAULDRON.getDefaultState(), 11);
        world.emitGameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Emitter.of(player, NSMiscBlocks.CHEESE_CAULDRON.getDefaultState()));
        if (!player.isCreative() && !player.isSpectator()) {
          player.setStackInHand(hand, new ItemStack(Items.BUCKET));
        }

        return ActionResult.success(world.isClient);
      }
      return ActionResult.PASS;
    }));

  }
}
