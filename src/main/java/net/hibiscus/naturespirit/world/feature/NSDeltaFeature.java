package net.hibiscus.naturespirit.world.feature;


import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.Iterator;
import net.hibiscus.naturespirit.registration.NSMiscBlocks;
import net.hibiscus.naturespirit.registration.NSWoods;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.DeltaFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class NSDeltaFeature extends Feature<DeltaFeatureConfig> {

  private static final ImmutableList<Block> CANNOT_REPLACE;
  private static final ImmutableList<Block> CAN_REPLACE;
  private static final Direction[] DIRECTIONS;
  private static final double RIM_SPAWN_CHANCE = 0.9D;

  static {
    CANNOT_REPLACE = ImmutableList.of(
        Blocks.BEDROCK,
        Blocks.NETHER_BRICKS,
        Blocks.NETHER_BRICK_FENCE,
        Blocks.NETHER_BRICK_STAIRS,
        Blocks.NETHER_WART,
        Blocks.CHEST,
        Blocks.SPAWNER,
        NSWoods.WISTERIA.getBlueLeaves(),
        NSWoods.WISTERIA.getPurpleLeaves(),
        NSWoods.WISTERIA.getWhiteLeaves(),
        NSWoods.WISTERIA.getPinkLeaves(),
        NSWoods.WILLOW.getLeaves()
    );
    CAN_REPLACE = ImmutableList.of(Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.GRASS_BLOCK, Blocks.MUD, Blocks.SAND, NSMiscBlocks.PINK_SAND);
    DIRECTIONS = Direction.values();
  }

  public NSDeltaFeature(Codec<DeltaFeatureConfig> codec) {
    super(codec);
  }

  private static boolean isClear(WorldAccess level, BlockPos pos, DeltaFeatureConfig config) {
    BlockState blockState = level.getBlockState(pos);
    if (blockState.isOf(config.getContents().getBlock())) {
      return false;
    } else if (CANNOT_REPLACE.contains(blockState.getBlock())) {
      return false;
    } else if (CAN_REPLACE.contains(blockState.getBlock()) && !blockState.isOf(config.getContents().getBlock())) {

      for (Direction direction : DIRECTIONS) {
        boolean bl = level.getBlockState(pos.offset(direction)).isAir();
        boolean bl2 = !level.getFluidState(pos.offset(direction).down()).isIn(FluidTags.WATER);
        if ((bl && direction != Direction.UP && (bl2 || level.getRandom().nextFloat() < .7)) || !bl && direction == Direction.UP) {
          return false;
        }
      }

      return true;
    } else {
      return false;
    }
  }

  public boolean generate(FeatureContext<DeltaFeatureConfig> context) {
    boolean bl = false;
    Random randomSource = context.getRandom();
    StructureWorldAccess worldGenLevel = context.getWorld();
    DeltaFeatureConfig deltaFeatureConfiguration = context.getConfig();
    BlockPos blockPos = context.getOrigin();
    boolean bl2 = randomSource.nextDouble() < 0.9D;
    int i = bl2 ? deltaFeatureConfiguration.getRimSize().get(randomSource) : 0;
    int j = bl2 ? deltaFeatureConfiguration.getRimSize().get(randomSource) : 0;
    boolean bl3 = bl2 && i != 0 && j != 0;
    int k = deltaFeatureConfiguration.getSize().get(randomSource);
    int l = deltaFeatureConfiguration.getSize().get(randomSource);
    int m = Math.max(k, l);
    Iterator var14 = BlockPos.iterateOutwards(blockPos, k, 0, l).iterator();

    while (var14.hasNext()) {
      BlockPos blockPos2 = (BlockPos) var14.next();
      if (blockPos2.getManhattanDistance(blockPos) > m) {
        break;
      }

      if (isClear(worldGenLevel, blockPos2, deltaFeatureConfiguration)) {
        if (bl3) {
          bl = true;
          worldGenLevel.setBlockState(blockPos2, deltaFeatureConfiguration.getRim(), 3);
        }

        BlockPos blockPos3 = blockPos2.add(i, 0, j);
        if (isClear(worldGenLevel, blockPos3, deltaFeatureConfiguration)) {
          bl = true;
          worldGenLevel.setBlockState(blockPos3, deltaFeatureConfiguration.getContents(), 3);
          worldGenLevel.scheduleFluidTick(blockPos3, Fluids.WATER, 1);
        }
      }
    }

    return bl;
  }
}
