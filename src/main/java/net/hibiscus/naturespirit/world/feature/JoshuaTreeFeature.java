package net.hibiscus.naturespirit.world.feature;

import com.mojang.serialization.Codec;
import java.util.Iterator;
import net.hibiscus.naturespirit.blocks.BranchingTrunkBlock;
import net.hibiscus.naturespirit.registration.NSWoods;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import org.jetbrains.annotations.Nullable;

public class JoshuaTreeFeature extends Feature<DefaultFeatureConfig> {

  public JoshuaTreeFeature(Codec<DefaultFeatureConfig> codec) {
    super(codec);
  }

  public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
    StructureWorldAccess structureWorldAccess = context.getWorld();
    BlockPos blockPos = context.getOrigin();
    Random random = context.getRandom();
    return generate2(structureWorldAccess, blockPos, random, 0);
  }

  private static boolean isSurroundedByAir(WorldView world, BlockPos pos, @Nullable Direction exceptDirection) {
    Iterator<Direction> var3 = Direction.Type.HORIZONTAL.iterator();

    Direction direction;
    do {
      if (!var3.hasNext()) {
        return true;
      }

      direction = var3.next();
    } while (direction == exceptDirection || world.isAir(pos.offset(direction)));

    return false;
  }

  private static boolean generate2(WorldAccess world, BlockPos pos, Random random, int layer) {
    BranchingTrunkBlock branchingTrunkBlock = (BranchingTrunkBlock) NSWoods.JOSHUA.getLog();
    int i = random.nextBetween(1, 3);
    if (layer == 0) i += 2;


    for (int j = 0; j < i; ++j) {
      BlockPos blockPos = pos.up(j + 1);
      if (!isSurroundedByAir(world, blockPos, Direction.DOWN)) return false;
      world.setBlockState(blockPos, branchingTrunkBlock.withConnectionProperties(world, blockPos), 2);
      world.setBlockState(blockPos.down(), branchingTrunkBlock.withConnectionProperties(world, blockPos.down()), 2);
      world.setBlockState(blockPos, branchingTrunkBlock.withConnectionProperties(world, blockPos), 2);
      if (layer > 0) {
        world.setBlockState(blockPos.down(2), branchingTrunkBlock.withConnectionProperties(world, blockPos.down(2)), 2);
      }
    }

    if (layer < 2) {
      int k = random.nextBetween(3, 5);
      if (layer == 0) ++k;

      for (int l = 0; l < k; ++l) {

        Direction direction = Direction.Type.HORIZONTAL.random(random);
        int m = random.nextBetween(1, 2);
        int n = Math.max(1, i - m);
        BlockPos blockPos2 = pos.up(n).offset(direction, m);

        if (world.isAir(blockPos2) && isSurroundedByAir(world, blockPos2, direction.getOpposite())) {

          world.setBlockState(blockPos2, branchingTrunkBlock.withConnectionProperties(world, blockPos2), 2);
          world.setBlockState(blockPos2.offset(direction.getOpposite()), branchingTrunkBlock.withConnectionProperties(world, blockPos2.offset(direction.getOpposite())), 2);
          for (int p = m; p > 0; --p) {
            world.setBlockState(blockPos2.offset(direction.getOpposite(), p), branchingTrunkBlock.withConnectionProperties(world, blockPos2.offset(direction.getOpposite(), p)), 2);
          }

          generate2(world, blockPos2.up(), random, layer + 1);

          if (world.isAir(blockPos2.up())) {

            world.setBlockState(blockPos2.up(), NSWoods.JOSHUA.getLeaves().getDefaultState().with(LeavesBlock.DISTANCE, 1), 2);
            world.setBlockState(blockPos2, ((BranchingTrunkBlock) NSWoods.JOSHUA.getLog()).withConnectionProperties(world, blockPos2), 2);

            Direction direction2 = Direction.Type.HORIZONTAL.random(random);
            if (random.nextFloat() < .65F && world.isAir(blockPos2.offset(direction, 1))) {
              world.setBlockState(blockPos2.offset(direction2, 1), NSWoods.JOSHUA.getLeaves().getDefaultState().with(LeavesBlock.DISTANCE, 1), 2);
              world.setBlockState(blockPos2, ((BranchingTrunkBlock) NSWoods.JOSHUA.getLog()).withConnectionProperties(world, blockPos2), 2);
            }

          }
          else if (world.isAir(blockPos2.up(2)) && !world.isAir(blockPos2.up())) {

            world.setBlockState(blockPos2.up(2), NSWoods.JOSHUA.getLeaves().getDefaultState().with(LeavesBlock.DISTANCE, 1), 2);
            world.setBlockState(blockPos2.up(1), ((BranchingTrunkBlock) NSWoods.JOSHUA.getLog()).withConnectionProperties(world, blockPos2.up()), 2);

            Direction direction2 = Direction.Type.HORIZONTAL.random(random);
            if (random.nextFloat() < .65F && world.isAir(blockPos2.up().offset(direction, 1))) {
              world.setBlockState(blockPos2.up().offset(direction2, 1), NSWoods.JOSHUA.getLeaves().getDefaultState().with(LeavesBlock.DISTANCE, 1), 2);
              world.setBlockState(blockPos2.up(), ((BranchingTrunkBlock) NSWoods.JOSHUA.getLog()).withConnectionProperties(world, blockPos2.up()), 2);
            }

          }
        }
      }
      return true;
    }
    world.setBlockState(pos.up(i), NSWoods.JOSHUA.getLeaves().getDefaultState().with(LeavesBlock.DISTANCE, 1), 2);
    world.setBlockState(pos.up(i - 1), ((BranchingTrunkBlock) NSWoods.JOSHUA.getLog()).withConnectionProperties(world, pos.up(i - 1)), 2);
    Direction direction = Direction.Type.HORIZONTAL.random(random);
    if (world.isAir(pos.up(i - 1).offset(direction, 1))) {
      world.setBlockState(pos.up(i - 1).offset(direction, 1), NSWoods.JOSHUA.getLeaves().getDefaultState().with(LeavesBlock.DISTANCE, 1), 2);
      world.setBlockState(pos.up(i - 1), ((BranchingTrunkBlock) NSWoods.JOSHUA.getLog()).withConnectionProperties(world, pos.up(i - 1)), 2);
    }
    return true;
  }
}
