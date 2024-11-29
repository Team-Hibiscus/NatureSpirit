package net.hibiscus.naturespirit.world.foliage_placer;

import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.hibiscus.naturespirit.registration.NSWorldGen;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

public class BirchFoliagePlacer extends FoliagePlacer {

  public static final MapCodec<BirchFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
    return createCodec(instance).apply(instance, BirchFoliagePlacer::new);
  });
  protected final int height;

  protected static <P extends BirchFoliagePlacer> Products.P3<RecordCodecBuilder.Mu<P>, IntProvider, IntProvider, Integer> createCodec(RecordCodecBuilder.Instance<P> builder) {
    return fillFoliagePlacerFields(builder).and(Codec.intRange(0, 16).fieldOf("height").forGetter((placer) -> {
      return placer.height;
    }));
  }

  public BirchFoliagePlacer(IntProvider radius, IntProvider offset, int height) {
    super(radius, offset);
    this.height = height;
  }

  protected FoliagePlacerType<?> getType() {
    return NSWorldGen.BIRCH_FOLIAGE_PLACER_TYPE;
  }

  protected void generate(TestableWorld world, FoliagePlacer.BlockPlacer placer, Random random, TreeFeatureConfig config, int trunkHeight, FoliagePlacer.TreeNode treeNode,
      int foliageHeight, int radius, int offset) {
    for (int i = offset; i >= offset - foliageHeight; --i) {
      int j = Math.max(Math.min(radius + treeNode.getFoliageRadius() - 1 - i / 2, 3), 0);
      if (i == offset - foliageHeight) {
        this.generateDiagonal(world, placer, random, config, treeNode.getCenter(), radius, i, treeNode.isGiantTrunk());
      } else if (j == 2 && Math.max(Math.min(radius + treeNode.getFoliageRadius() - 2 - i / 2, 3), 0) == 1) {
        this.generateCircle(world, placer, random, config, treeNode.getCenter(), j, i, treeNode.isGiantTrunk());
      } else if (j == 3) {
        this.generateCircularSquare(world, placer, random, config, treeNode.getCenter(), j, i, treeNode.isGiantTrunk());
      } else {
        this.generateSquare(world, placer, random, config, treeNode.getCenter(), j, i, treeNode.isGiantTrunk());
      }

    }

  }

  protected void generateDiagonal(TestableWorld world, BlockPlacer placer, Random random, TreeFeatureConfig config, BlockPos centerPos, int radius, int y, boolean giantTrunk) {
    int i = giantTrunk ? 1 : 0;
    BlockPos.Mutable mutable = new BlockPos.Mutable();

    for (int j = -radius; j <= radius + i; ++j) {
      for (int k = -radius; k <= radius + i; ++k) {
        if (!this.validateDiagonal(random, j, y, k, radius, giantTrunk)) {
          mutable.set(centerPos, j, y, k);
          placeFoliageBlock(world, placer, random, config, mutable);
        }
      }
    }

  }

  protected void generateCircularSquare(TestableWorld world, BlockPlacer placer, Random random, TreeFeatureConfig config, BlockPos centerPos, int radius, int y,
      boolean giantTrunk) {
    int i = giantTrunk ? 1 : 0;
    BlockPos.Mutable mutable = new BlockPos.Mutable();

    for (int j = -radius; j <= radius + i; ++j) {
      for (int k = -radius; k <= radius + i; ++k) {
        if (!this.validateCircularSquare(random, j, y, k, radius, giantTrunk)) {
          mutable.set(centerPos, j, y, k);
          placeFoliageBlock(world, placer, random, config, mutable);
        }
      }
    }

  }

  protected void generateCircle(TestableWorld world, BlockPlacer placer, Random random, TreeFeatureConfig config, BlockPos centerPos, int radius, int y, boolean giantTrunk) {
    int i = giantTrunk ? 1 : 0;
    BlockPos.Mutable mutable = new BlockPos.Mutable();

    for (int j = -radius; j <= radius + i; ++j) {
      for (int k = -radius; k <= radius + i; ++k) {
        if (!this.validateCircle(random, j, y, k, radius, giantTrunk)) {
          mutable.set(centerPos, j, y, k);
          placeFoliageBlock(world, placer, random, config, mutable);
        }
      }
    }

  }

  protected boolean validateCircularSquare(Random random, int dx, int y, int dz, int radius, boolean giantTrunk) {
    dx = Math.abs(dx);
    dz = Math.abs(dz);
    return ((dx == radius || dz == radius) && (dx != 0 && dz != 0 || random.nextInt(3) == 0)) || (dx == radius - 1 && dz == radius - 1 && random.nextInt(2) == 0);
  }

  protected boolean validateCircle(Random random, int dx, int y, int dz, int radius, boolean giantTrunk) {
    dx = Math.abs(dx);
    dz = Math.abs(dz);
    return dx == radius && dz == radius && radius > 0 && random.nextFloat() < .7f;
  }

  protected boolean validateDiagonal(Random random, int dx, int y, int dz, int radius, boolean giantTrunk) {
    dx = Math.abs(dx);
    dz = Math.abs(dz);
    return (dx > 1 || dz > 1) && dx != 0 && dz != 0;
  }

  public int getRandomHeight(Random random, int trunkHeight, TreeFeatureConfig config) {
    return this.height;
  }

  protected boolean isInvalidForLeaves(Random random, int dx, int y, int dz, int radius, boolean giantTrunk) {
    return dx == radius && dz == radius && (random.nextInt(2) == 0 || y == 0);
  }
}
