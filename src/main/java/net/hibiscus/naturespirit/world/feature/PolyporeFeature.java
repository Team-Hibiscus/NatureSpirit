package net.hibiscus.naturespirit.world.feature;

import com.mojang.serialization.Codec;
import net.hibiscus.naturespirit.registration.NSMiscBlocks;
import net.minecraft.block.MushroomBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class PolyporeFeature extends Feature<DefaultFeatureConfig> {

  public PolyporeFeature(Codec<DefaultFeatureConfig> codec) {
    super(codec);
  }

  public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
    StructureWorldAccess world = context.getWorld();
    BlockPos pos = context.getOrigin();
    Random random = context.getRandom();
    for (Direction direction : Direction.Type.HORIZONTAL.getShuffled(random)) {
      BlockPos pos2 = pos.offset(direction);
      if (!world.isAir(pos2)) {
        Direction direction2 = direction.rotateYClockwise();
        Direction direction3 = direction.rotateYCounterclockwise();
        int radius = random.nextBetween(1, 3);
        if (world.isAir(pos2.offset(direction2)) && world.isAir(pos.offset(direction2))) {
          generateSquare(world, pos2, radius, direction.getOpposite(), direction2);
          return true;
        } else if (world.isAir(pos2.offset(direction3)) && world.isAir(pos.offset(direction3))) {
          generateSquare(world, pos2, radius, direction.getOpposite(), direction3);
          return true;
        }
      }
    }
    return false;
  }

  protected static void generateSquare(WorldAccess world, BlockPos cornerPos, int radius, Direction direction1, Direction direction2) {
    BlockPos.Mutable mutable = new BlockPos.Mutable().set(cornerPos);
    BlockPos blockPos;
    for (int j = 0; j <= radius; ++j) {
      for (int k = 0; k <= radius; ++k) {
        blockPos = mutable.offset(direction2, k).offset(direction1, j);
        if (world.isAir(blockPos) || world.getBlockState(blockPos).isOf(NSMiscBlocks.GRAY_POLYPORE)) {
          world.setBlockState(blockPos, NSMiscBlocks.GRAY_POLYPORE_BLOCK.getDefaultState().with(MushroomBlock.DOWN, false), 2);
        }
      }
    }

  }
}
