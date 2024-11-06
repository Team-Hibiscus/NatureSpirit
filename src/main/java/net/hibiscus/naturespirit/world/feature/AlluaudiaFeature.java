package net.hibiscus.naturespirit.world.feature;

import com.mojang.serialization.Codec;
import java.util.Iterator;
import net.hibiscus.naturespirit.blocks.GrowingBranchingTrunkBlock;
import net.hibiscus.naturespirit.registration.NSMiscBlocks;
import net.minecraft.block.Block;
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

public class AlluaudiaFeature extends Feature<DefaultFeatureConfig> {

  public AlluaudiaFeature(Codec<DefaultFeatureConfig> codec) {
    super(codec);
  }

  public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
    StructureWorldAccess structureWorldAccess = context.getWorld();
    BlockPos blockPos = context.getOrigin();
    Random random = context.getRandom();
    generate2(structureWorldAccess, blockPos, random, blockPos, 6, 0);
    return true;
  }

  private static boolean isSurroundedByAir(WorldView world, BlockPos pos, @Nullable Direction exceptDirection) {
    Iterator var3 = Direction.Type.HORIZONTAL.iterator();

    Direction direction;
    do {
      if (!var3.hasNext()) {
        return true;
      }

      direction = (Direction) var3.next();
    } while (direction == exceptDirection || world.isAir(pos.offset(direction)));

    return false;
  }

  private static void generate2(WorldAccess world, BlockPos pos, Random random, BlockPos rootPos, int size, int layer) {
    GrowingBranchingTrunkBlock branchingTrunkBlock = (GrowingBranchingTrunkBlock) NSMiscBlocks.ALLUAUDIA;
    int i = random.nextInt(1) + 2;
    if (layer == 0) {
      ++i;
    }

    for (int j = 0; j < i; ++j) {
      BlockPos blockPos = pos.up(j + 1);
      if (layer > 0) {
        blockPos = pos.up(j == 0 ? 1 : (int) (j / .93));
      }
      if (!isSurroundedByAir(world, blockPos, Direction.random(random))) {
        return;
      }
      world.setBlockState(blockPos, branchingTrunkBlock.withConnectionProperties(world, blockPos), Block.NOTIFY_LISTENERS);
      world.setBlockState(blockPos.down(), branchingTrunkBlock.withConnectionProperties(world, blockPos.down()), Block.NOTIFY_LISTENERS);
    }

    if (layer < 2) {
      int k = random.nextInt(2) + 4;
      if (layer == 0) {
        ++k;
      }

      for (int l = 0; l < k; ++l) {
        Direction direction = Direction.Type.HORIZONTAL.random(random);
        int m = random.nextInt(2);
        int n = i - m == 0 ? 1 : i - m;
        BlockPos blockPos2 = pos.up(n).offset(direction, 1);
        if (Math.abs(blockPos2.getX() - rootPos.getX()) < size && Math.abs(blockPos2.getZ() - rootPos.getZ()) < size && world.isAir(blockPos2) && world.isAir(blockPos2.down())) {

          world.setBlockState(blockPos2, branchingTrunkBlock.withHorizontalConnectingProperties(world, blockPos2), Block.NOTIFY_LISTENERS);

          world.setBlockState(blockPos2.offset(direction.rotateYClockwise(), 1).up(),
              branchingTrunkBlock.withConnectionPropertiesVertical(world, blockPos2.offset(direction.rotateYClockwise(), 1).up()), Block.NOTIFY_LISTENERS);
          world.setBlockState(blockPos2.offset(direction.rotateYClockwise(), 1),
              branchingTrunkBlock.withConnectionPropertiesVertical(world, blockPos2.offset(direction.rotateYClockwise(), 1)), Block.NOTIFY_LISTENERS);
          world.setBlockState(blockPos2.offset(direction.rotateYClockwise(), 1).up(2),
              branchingTrunkBlock.withConnectionPropertiesVertical(world, blockPos2.offset(direction.rotateYClockwise(), 1).up(2)), Block.NOTIFY_LISTENERS);
          world.setBlockState(blockPos2.offset(direction.rotateYClockwise(), 1).up(),
              branchingTrunkBlock.withConnectionPropertiesVertical(world, blockPos2.offset(direction.rotateYClockwise(), 1).up()), Block.NOTIFY_LISTENERS);
          world.setBlockState(blockPos2, branchingTrunkBlock.withConnectionProperties(world, blockPos2), Block.NOTIFY_LISTENERS);
          world.setBlockState(blockPos2.offset(direction.getOpposite()), branchingTrunkBlock.withConnectionProperties(world, blockPos2.offset(direction.getOpposite())),
              Block.NOTIFY_LISTENERS);
          world.setBlockState(blockPos2.offset(direction.rotateYClockwise(), 1),
              branchingTrunkBlock.withConnectionProperties(world, blockPos2.offset(direction.rotateYClockwise(), 1)), Block.NOTIFY_LISTENERS);
          world.setBlockState(blockPos2, branchingTrunkBlock.withHorizontalConnectingProperties(world, blockPos2), Block.NOTIFY_LISTENERS);

          generate2(world, pos.up(n + 1), random, rootPos, size, layer + 1);
//               bl = false;
//               if(world.isAir(blockPos2.up())) {
//
//                  world.setBlockState(blockPos2.up(), branchingTrunkBlock.withConnectionProperties(world, blockPos2.up()), Block.NOTIFY_LISTENERS);
//                  world.setBlockState(blockPos2, branchingTrunkBlock.withConnectionProperties(world, blockPos2), Block.NOTIFY_LISTENERS);
//
//                  Direction direction2 = Direction.Type.HORIZONTAL.random(random);
//                  if(random.nextBoolean() && world.isAir(blockPos2.offset(direction2, 1))) {
//                     world.setBlockState(blockPos2.offset(direction2), branchingTrunkBlock.withConnectionProperties(world, blockPos2.offset(direction2)), Block.NOTIFY_LISTENERS);
//                     world.setBlockState(blockPos2.offset(direction), branchingTrunkBlock.withConnectionProperties(world, blockPos2.offset(direction)), Block.NOTIFY_LISTENERS);
//                  }
//               }
//
//               else if((world.isAir(blockPos2.up(2)) && !world.isAir(blockPos2.up()))) {
//
//                  world.setBlockState(blockPos2.up(1), branchingTrunkBlock.withConnectionProperties(world, blockPos2.up(1)), Block.NOTIFY_LISTENERS);
//
          if (random.nextBoolean()) {
            world.setBlockState(blockPos2.offset(direction.rotateYClockwise()).offset(direction).up(),
                branchingTrunkBlock.withConnectionProperties(world, blockPos2.offset(direction.rotateYClockwise()).offset(direction).up()), Block.NOTIFY_LISTENERS);
            world.setBlockState(blockPos2.offset(direction.rotateYClockwise(), 1).up(),
                branchingTrunkBlock.withConnectionProperties(world, blockPos2.offset(direction.rotateYClockwise(), 1).up()), Block.NOTIFY_LISTENERS);
          } else {
            world.setBlockState(blockPos2.offset(direction.rotateYClockwise(), 2).up(),
                branchingTrunkBlock.withConnectionProperties(world, blockPos2.offset(direction.rotateYClockwise(), 2).up()), Block.NOTIFY_LISTENERS);
            world.setBlockState(blockPos2.offset(direction.rotateYClockwise(), 1).up(),
                branchingTrunkBlock.withConnectionProperties(world, blockPos2.offset(direction.rotateYClockwise(), 1).up()), Block.NOTIFY_LISTENERS);
          }
//               }
        }
      }
    }
//      if(bl) {
//         world.setBlockState(pos.up(i - 2), branchingTrunkBlock.withConnectionProperties(world, pos.up(i - 2)), Block.NOTIFY_LISTENERS);
//
//         Direction direction = Direction.Type.HORIZONTAL.random(random);
//
//         if(random.nextBoolean() && world.isAir(pos.up(i - 1).offset(direction, 1))) {
//            world.setBlockState(pos.up(i - 1).offset(direction), branchingTrunkBlock.withConnectionProperties(world, pos.up(i - 1).offset(direction)), Block.NOTIFY_LISTENERS);
//            world.setBlockState(pos.up(i - 1), branchingTrunkBlock.withConnectionProperties(world, pos.up(i - 1)), Block.NOTIFY_LISTENERS);
//         }
//      }

  }
}
