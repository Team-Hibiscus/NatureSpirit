package net.hibiscus.naturespirit.blocks;

import net.hibiscus.naturespirit.registration.NSMiscBlocks;
import net.hibiscus.naturespirit.registration.NSTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MushroomPlantBlock;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldView;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class ShiitakeMushroomPlantBlock extends MushroomPlantBlock {

  public ShiitakeMushroomPlantBlock(Settings settings, RegistryKey<ConfiguredFeature<?, ?>> featureKey) {
    super(featureKey, settings);
  }

  public static boolean getCompletdCircle(ServerWorld world, BlockPos pos) {

    return isMushroom(pos, world) && isMushroom(pos.west(), world) && isMushroom(pos.west(2), world) && isMushroom(pos.east().north(), world) && isMushroom(pos.east().north(2),
        world) && isMushroom(pos.east().north(3),
        world
    ) && isMushroom(pos.north(4), world) && isMushroom(pos.north(4).west(), world) && isMushroom(pos.north(4).west(2), world) && isMushroom(
        pos.north(3).west(3),
        world
    ) && isMushroom(pos.north(2).west(3), world) && isMushroom(pos.north().west(3), world);
  }

  public static boolean getCompletedPodzol(ServerWorld world, BlockPos pos) {
    pos = pos.down();
    return isPodzol(pos.north(), world) && isPodzol(pos.west().north(), world) && isPodzol(pos.west(2).north(), world) && isPodzol(pos.north(2), world) && isPodzol(
        pos.west().north(2),
        world
    ) && isPodzol(pos.west(2).north(2), world) && isPodzol(pos.north(3), world) && isPodzol(pos.west().north(3), world) && isPodzol(pos.west(2).north(3), world);
  }

  public static boolean getCompletedCoarseDirt(ServerWorld world, BlockPos pos) {
    pos = pos.down();
    return isDirt(pos.north(), world) && isDirt(pos.west().north(), world) && isDirt(pos.west(2).north(), world) && isDirt(pos.north(2), world) && isDirt(pos.west().north(2),
        world
    ) && isDirt(pos.west(2).north(2), world) && isDirt(pos.north(3), world) && isDirt(pos.west().north(3), world) && isDirt(pos.west(2).north(3), world);
  }

  public static boolean isMushroom(BlockPos pos, ServerWorld world) {
    return world.getBlockState(pos).isOf(NSMiscBlocks.SHIITAKE_MUSHROOM);
  }

  public static boolean isPodzol(BlockPos pos, ServerWorld world) {
    return world.getBlockState(pos).isOf(Blocks.PODZOL) || world.getBlockState(pos).isIn(BlockTags.DIRT) && (!world.getBlockState(pos).isOf(Blocks.GRASS_BLOCK)
        && !world.getBlockState(pos).isOf(
        NSMiscBlocks.SANDY_SOIL));
  }

  public static boolean isDirt(BlockPos pos, ServerWorld world) {
    return world.getBlockState(pos).isOf(Blocks.COARSE_DIRT) || world.getBlockState(pos).isOf(NSMiscBlocks.SANDY_SOIL);
  }

  @Override
  public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
    BlockPos blockPos = pos.down();
    BlockState blockState = world.getBlockState(blockPos);
    if (blockState.isIn(NSTags.Blocks.SHIITAKE_MUSHROOM_GROW_BLOCK)) {
      return true;
    } else {
      return world.getBaseLightLevel(pos, 0) < 14 && this.canPlantOnTop(blockState, world, blockPos);
    }
  }

  @Override
  public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
    if (random.nextInt(25) == 0) {
      int i = 5;

      for (BlockPos blockPos : BlockPos.iterate(pos.add(-4, -1, -4), pos.add(4, 1, 4))) {
        if (world.getBlockState(blockPos).isOf(this)) {
          --i;
          if (i <= 0) {
            return;
          }
        }
      }

      BlockPos blockPos2 = pos.add(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2), random.nextInt(3) - 1);

      for (int k = 0; k < 4; ++k) {
        if (world.isAir(blockPos2) && state.canPlaceAt(world, blockPos2)) {
          pos = blockPos2;
        }

        blockPos2 = pos.add(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2), random.nextInt(3) - 1);
      }

      if (world.isAir(blockPos2) && state.canPlaceAt(world, blockPos2)) {
        world.setBlockState(blockPos2, state, 2);
      }
    }

    if (getCompletdCircle(world, pos)) {
      BlockPos blockPos = pos.north(3).west(2).down();
      for (int i = 0; i < 3; ++i) {
        for (int j = 0; j < 3; ++j) {
          BlockPos blockPos2 = blockPos.add(i, 0, j);
          if (world.getBlockState(blockPos2).isOf(Blocks.GRASS_BLOCK)) {
            if (random.nextInt(25) == 0) {
              world.setBlockState(blockPos2, Blocks.PODZOL.getDefaultState(), Block.NOTIFY_LISTENERS);

            }
          }
          if (getCompletedPodzol(world, pos)) {
            if (random.nextInt(25) == 0) {
              world.setBlockState(blockPos2, Blocks.COARSE_DIRT.getDefaultState(), Block.NOTIFY_LISTENERS);
            }
          }
          if (getCompletedCoarseDirt(world, pos)) {
            if (random.nextInt(25) == 0) {
              world.setBlockState(blockPos2, NSMiscBlocks.SANDY_SOIL.getDefaultState(), Block.NOTIFY_LISTENERS);
            }
          }
        }
      }
    }

  }
}
