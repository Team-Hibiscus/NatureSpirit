//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.hibiscus.naturespirit.world.feature;

import com.mojang.serialization.Codec;
import java.util.function.Predicate;
import net.minecraft.block.BlockState;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class TurnipRootFeature extends Feature<TurnipRootFeatureConfig> {

  public TurnipRootFeature(Codec<TurnipRootFeatureConfig> codec) {
    super(codec);
  }

  private static boolean hasSpaceForTree(StructureWorldAccess world, TurnipRootFeatureConfig config, BlockPos pos) {
    Mutable mutable = pos.mutableCopy();

    for (int i = 1; i <= config.requiredVerticalSpaceForTree; ++i) {
      mutable.move(Direction.UP);
      BlockState blockState = world.getBlockState(mutable);
      if (!isAirOrWater(blockState, i, config.allowedVerticalWaterForTree)) {
        return false;
      }
    }

    return true;
  }

  private static boolean isAirOrWater(BlockState state, int height, int allowedVerticalWaterForTree) {
    if (state.isAir()) {
      return true;
    } else {
      int i = height + 1;
      return i <= allowedVerticalWaterForTree && state.getFluidState().isIn(FluidTags.WATER);
    }
  }

  private static boolean generateTreeAndRoots(StructureWorldAccess world, ChunkGenerator generator, TurnipRootFeatureConfig config, Random random, Mutable mutablePos,
      BlockPos pos) {
    for (int i = 0; i < config.maxRootColumnHeight; ++i) {
      mutablePos.move(Direction.UP);
      if (config.predicate.test(world, mutablePos) && hasSpaceForTree(world, config, mutablePos)) {
        BlockPos blockPos = mutablePos.down();
        if (world.getFluidState(blockPos).isIn(FluidTags.LAVA) || !world.getBlockState(blockPos).isSolid()) {
          return false;
        }

        if (config.feature.value().generateUnregistered(world, generator, random, mutablePos)) {
          generateRootsColumn(pos, pos.getY() + i, world, config, random);
          return true;
        }
      }
    }

    return false;
  }

  private static void generateRootsColumn(BlockPos pos, int maxY, StructureWorldAccess world, TurnipRootFeatureConfig config, Random random) {
    int i = pos.getX();
    int j = pos.getZ();
    Mutable mutable = pos.mutableCopy();

    for (int k = pos.getY(); k < maxY; ++k) {
      generateRoots(world, config, random, i, j, mutable.set(i, k, j));
    }

  }

  private static void generateRoots(StructureWorldAccess world, TurnipRootFeatureConfig config, Random random, int x, int z, Mutable mutablePos) {
    int i = config.rootRadius;
    Predicate<BlockState> predicate = (state) -> {
      return state.isIn(config.rootReplaceable);
    };

    for (int j = 0; j < config.rootPlacementAttempts; ++j) {
      mutablePos.set(mutablePos, random.nextInt(i) - random.nextInt(i), 0, random.nextInt(i) - random.nextInt(i));
      if (predicate.test(world.getBlockState(mutablePos))) {
        world.setBlockState(mutablePos, config.rootStateProvider.get(random, mutablePos), 2);
      }

      mutablePos.setX(x);
      mutablePos.setZ(z);
    }

  }

  private static void generateHangingRoots(StructureWorldAccess world, TurnipRootFeatureConfig config, Random random, BlockPos pos, Mutable mutablePos) {
    int i = config.hangingRootRadius;
    int j = config.hangingRootVerticalSpan;

    for (int k = 0; k < config.hangingRootPlacementAttempts; ++k) {
      mutablePos.set(pos, random.nextInt(i) - random.nextInt(i), random.nextInt(j) - random.nextInt(j), random.nextInt(i) - random.nextInt(i));
      if (world.isAir(mutablePos)) {
        BlockState blockState = config.hangingRootStateProvider.get(random, mutablePos);
        if (blockState.canPlaceAt(world, mutablePos) && world.getBlockState(mutablePos.up()).isSideSolidFullSquare(world, mutablePos, Direction.DOWN)) {
          world.setBlockState(mutablePos, blockState, 2);
        }
      }
    }

  }

  private static void generateTurnips(StructureWorldAccess world, TurnipRootFeatureConfig config, Random random, BlockPos pos, Mutable mutablePos) {
    int i = config.hangingRootRadius;
    int j = config.hangingRootVerticalSpan;

    for (int k = 0; k < config.turnipPlacementAttempts; ++k) {
      mutablePos.set(pos, random.nextInt(i) - random.nextInt(i), random.nextInt(j) - random.nextInt(j), random.nextInt(i) - random.nextInt(i));
      if (world.getBlockState(mutablePos.offset(Direction.UP, 1)) == config.rootStateProvider.get(random, mutablePos)) {
        BlockState blockState = config.turnipStateProvider.get(random, mutablePos);
        if (blockState.canPlaceAt(world, mutablePos) && world.getBlockState(mutablePos.up()).isSideSolidFullSquare(world, mutablePos, Direction.DOWN)) {
          world.setBlockState(mutablePos, blockState, 2);
        }
      }
    }

  }

  public boolean generate(FeatureContext<TurnipRootFeatureConfig> context) {
    StructureWorldAccess structureWorldAccess = context.getWorld();
    Random random = context.getRandom();
    BlockPos blockPos = context.getOrigin();
    TurnipRootFeatureConfig turnipRootFeatureConfig = context.getConfig();
    Mutable mutable = blockPos.mutableCopy();
    if (generateTreeAndRoots(structureWorldAccess, context.getGenerator(), turnipRootFeatureConfig, random, mutable, blockPos)) {
      generateHangingRoots(structureWorldAccess, turnipRootFeatureConfig, random, blockPos, mutable);
      generateTurnips(structureWorldAccess, turnipRootFeatureConfig, random, blockPos, mutable);
    }

    return true;
  }
}
