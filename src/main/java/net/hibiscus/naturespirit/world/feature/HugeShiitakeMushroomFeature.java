package net.hibiscus.naturespirit.world.feature;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.MushroomBlock;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.HugeMushroomFeature;
import net.minecraft.world.gen.feature.HugeMushroomFeatureConfig;

public class HugeShiitakeMushroomFeature extends HugeMushroomFeature {

  public HugeShiitakeMushroomFeature(Codec<HugeMushroomFeatureConfig> codec) {
    super(codec);
  }

  protected void generateCap(WorldAccess world, Random random, BlockPos start, int y, Mutable mutable, HugeMushroomFeatureConfig config) {
    for (int i = y - 3; i <= y + 1; ++i) {
      int j = i < y ? config.foliageRadius : config.foliageRadius - 1;
      int k = config.foliageRadius - 2;

      for (int l = -j; l <= j; ++l) {
        for (int m = -j; m <= j; ++m) {
          boolean bl = l == -j; //first
          boolean bl2 = l == j; //last
          boolean bl3 = m == -j; //first
          boolean bl4 = m == j; //last
          boolean bl5 = bl || bl2; //first or last
          boolean bl6 = bl3 || bl4; // first or last
          if (i >= y || (bl5 != bl6 && (i == y - 2 || i == y - 1))) {
            mutable.set(start, l, i, m); //-radius, y. positive radius (corner)
            if (!world.getBlockState(mutable).isOpaqueFullCube(world, mutable)) {
              BlockState blockState = config.capProvider.get(random, start);
              if (blockState.contains(MushroomBlock.WEST) && blockState.contains(MushroomBlock.EAST) && blockState.contains(MushroomBlock.NORTH) && blockState.contains(
                  MushroomBlock.SOUTH) && blockState.contains(
                  MushroomBlock.UP)) {
                blockState = blockState
                    .with(MushroomBlock.UP, i >= y - 1)
                    .with(MushroomBlock.WEST, l < -k)
                    .with(MushroomBlock.EAST, l > k)
                    .with(MushroomBlock.NORTH, m < -k)
                    .with(MushroomBlock.SOUTH, m > k);
              }

              this.setBlockState(world, mutable, blockState);
            }
          }
        }
      }
    }

  }

  protected boolean canGenerate(WorldAccess world, BlockPos pos, int height, Mutable mutablePos, HugeMushroomFeatureConfig config) {
    int i = pos.getY();
    if (i >= world.getBottomY() + 1 && i + height + 1 < world.getTopY()) {
      BlockState blockState = world.getBlockState(pos.down());
      if (!isSoil(blockState) && !blockState.isIn(BlockTags.MUSHROOM_GROW_BLOCK)) {
        return false;
      } else {
        for (int j = 0; j <= height; ++j) {
          int k = this.getCapSize(-1, -1, config.foliageRadius, j);
        }

        return true;
      }
    } else {
      return false;
    }
  }

  protected int getCapSize(int i, int j, int capSize, int y) {
    int k = 0;
    if (y < j && y >= j - 4) {
      k = capSize;
    } else if (y == j) {
      k = capSize;
    }

    return k;
  }
}
