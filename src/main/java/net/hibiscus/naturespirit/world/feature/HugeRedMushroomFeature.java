package net.hibiscus.naturespirit.world.feature;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.MushroomBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;

public class HugeRedMushroomFeature extends HugeMushroomFeature {

  public HugeRedMushroomFeature(Codec<HugeMushroomFeatureConfig> codec) {
    super(codec);
  }

  protected void generateCap(WorldAccess world, Random random, BlockPos start, int y, BlockPos.Mutable mutable, HugeMushroomFeatureConfig config) {
    for (int i = y - 3; i <= y; ++i) {
      int j = i < y ? config.foliageRadius : config.foliageRadius - 1;
      int k = config.foliageRadius - 2;

      for (int l = -j; l <= j; ++l) {
        for (int m = -j; m <= j; ++m) {
          int x = Math.abs(l);
          int z = Math.abs(m);
          boolean bl;
          if (i < y - 1) {
            bl = (x == j) != (z == j);
          } else if (i == y - 1) {
            bl = (x == j && z < j - 1) || (z == j && x < j - 1) || (x == j - 1 && z == j - 1);
          } else {
            bl = !(x == j && z == j);
          }

          if (bl) {
            mutable.set(start, l, i, m);
            if (!world.getBlockState(mutable).isOpaqueFullCube(world, mutable)) {
              BlockState blockState = config.capProvider.get(random, start);
              if (blockState.contains(MushroomBlock.WEST) && blockState.contains(MushroomBlock.EAST) && blockState.contains(MushroomBlock.NORTH) && blockState.contains(
                  MushroomBlock.SOUTH) && blockState.contains(MushroomBlock.UP)) {
                blockState = blockState.with(MushroomBlock.UP,
                    i >= y - 1 || (i == y - 2 && (x == j - 1 || z == j - 1))).with(MushroomBlock.WEST, l < -k || (i >= y - 1 && x == k)).with(MushroomBlock.EAST,
                    l > k || (i >= y - 1 && x == k)).with(MushroomBlock.NORTH, m < -k || (i >= y - 1 && z == k)).with(MushroomBlock.SOUTH, m > k || (i >= y - 1 && z == k));
              }

              this.setBlockState(world, mutable, blockState);
            }
          }

        }
      }
    }

  }

  protected int getCapSize(int i, int j, int capSize, int y) {
    int k = 0;
    if (y < j && y >= j - 3) {
      k = capSize;
    } else if (y == j) {
      k = capSize;
    }

    return k;
  }
}
