package net.hibiscus.naturespirit.world.foliage_placer;

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

public class RedwoodFoliagePlacer extends FoliagePlacer {

  public static final MapCodec<RedwoodFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
    return fillFoliagePlacerFields(instance).and(IntProvider.createValidatingCodec(0, 32).fieldOf("trunk_height").forGetter((placer) -> {
      return placer.trunkHeight;
    })).apply(instance, RedwoodFoliagePlacer::new);
  });
  private final IntProvider trunkHeight;

  public RedwoodFoliagePlacer(IntProvider intProvider, IntProvider intProvider2, IntProvider trunkHeight) {
    super(intProvider, intProvider2);
    this.trunkHeight = trunkHeight;
  }

  protected FoliagePlacerType<?> getType() {
    return NSWorldGen.REDWOOD_FOLIAGE_PLACER_TYPE;
  }

  protected void generate(TestableWorld world, BlockPlacer placer, Random random, TreeFeatureConfig config, int trunkHeight, TreeNode treeNode, int foliageHeight, int radius,
      int offset) {
    BlockPos blockPos = treeNode.getCenter();
    BlockPos.Mutable mutable = blockPos.mutableCopy();
    int layers = random.nextBetween(1, 6);
    int middleLayers = layers / 2;
    int largeLayers = layers - middleLayers;
    offset++;

    mutable.set(blockPos, 0, offset, 0);
    this.generateSquare(world, placer, random, config, mutable, 0, 0, treeNode.isGiantTrunk());
    mutable.set(blockPos, 0, offset - 1, 0);
    this.generateSquare(world, placer, random, config, mutable, 0, 0, treeNode.isGiantTrunk());
    mutable.set(blockPos, 0, offset - 2, 0);
    this.generateSquare(world, placer, random, config, mutable, 0, 0, treeNode.isGiantTrunk());
    this.generateCross(world, placer, random, config, blockPos, 2, offset - 3, treeNode.isGiantTrunk());
    this.generateCross(world, placer, random, config, blockPos, 1, offset - 4, treeNode.isGiantTrunk());

    int middeY = offset - 5;
    for (int a = middleLayers; a > 0; --a) {
      this.generateCircle(world, placer, random, config, blockPos, 2, middeY, treeNode.isGiantTrunk());
      middeY--;
      this.generateCross(world, placer, random, config, blockPos, 1, middeY, treeNode.isGiantTrunk());
      middeY--;
    }
    for (int a = largeLayers; a > 0; --a) {
      this.generateBigCircularSquare(world, placer, random, config, blockPos, 3, middeY, treeNode.isGiantTrunk());
      middeY--;
      if (a - 1 != 0) {
        this.generateCross(world, placer, random, config, blockPos, 2, middeY, treeNode.isGiantTrunk());
      } else {
        this.generateCircularSquare(world, placer, random, config, blockPos, 3, middeY, treeNode.isGiantTrunk());
        middeY--;
        this.generateBigCircularSquare(world, placer, random, config, blockPos, 4, middeY, treeNode.isGiantTrunk());
        middeY--;
        this.generateCross(world, placer, random, config, blockPos, 1, middeY, treeNode.isGiantTrunk());
        middeY--;
        this.generateCircle(world, placer, random, config, blockPos, 2, middeY, treeNode.isGiantTrunk());
      }
      middeY--;
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

  protected void generateCross(TestableWorld world, BlockPlacer placer, Random random, TreeFeatureConfig config, BlockPos centerPos, int radius, int y, boolean giantTrunk) {
    int i = giantTrunk ? 1 : 0;
    BlockPos.Mutable mutable = new BlockPos.Mutable();

    for (int j = -radius; j <= radius + i; ++j) {
      for (int k = -radius; k <= radius + i; ++k) {
        if (!this.validateCross(random, j, y, k, radius, giantTrunk)) {
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

  protected void generateBigCircularSquare(TestableWorld world, BlockPlacer placer, Random random, TreeFeatureConfig config, BlockPos centerPos, int radius, int y,
      boolean giantTrunk) {
    int i = giantTrunk ? 1 : 0;
    BlockPos.Mutable mutable = new BlockPos.Mutable();

    for (int j = -radius; j <= radius + i; ++j) {
      for (int k = -radius; k <= radius + i; ++k) {
        if (!this.validateBigCircularSquare(random, j, y, k, radius, giantTrunk)) {
          mutable.set(centerPos, j, y, k);
          placeFoliageBlock(world, placer, random, config, mutable);
        }
      }
    }

  }


  public int getRandomHeight(Random random, int trunkHeight, TreeFeatureConfig config) {
    return Math.max(8, trunkHeight - this.trunkHeight.get(random));
  }

  @Override
  protected boolean isInvalidForLeaves(Random random, int dx, int y, int dz, int radius, boolean giantTrunk) {
    return false;
  }

  protected boolean validateCircle(Random random, int dx, int y, int dz, int radius, boolean giantTrunk) {
    if (giantTrunk) {
      dx = Math.min(Math.abs(dx), Math.abs(dx - 1));
      dz = Math.min(Math.abs(dz), Math.abs(dz - 1));
    } else {
      dx = Math.abs(dx);
      dz = Math.abs(dz);
    }
    return dx == radius && dz == radius && radius > 0;
  }

  protected boolean validateCircularSquare(Random random, int dx, int y, int dz, int radius, boolean giantTrunk) {
    if (giantTrunk) {
      dx = Math.min(Math.abs(dx), Math.abs(dx - 1));
      dz = Math.min(Math.abs(dz), Math.abs(dz - 1));
    } else {
      dx = Math.abs(dx);
      dz = Math.abs(dz);
    }
    return ((dx == radius || dz == radius) && (dx > 0 && dz > 0));
  }

  protected boolean validateBigCircularSquare(Random random, int dx, int y, int dz, int radius, boolean giantTrunk) {
    if (giantTrunk) {
      dx = Math.min(Math.abs(dx), Math.abs(dx - 1));
      dz = Math.min(Math.abs(dz), Math.abs(dz - 1));
    } else {
      dx = Math.abs(dx);
      dz = Math.abs(dz);
    }
    return ((dx == radius || dz == radius) && (dx > 1 && dz > 1));
  }

  protected boolean validateCross(Random random, int dx, int y, int dz, int radius, boolean giantTrunk) {
    if (giantTrunk) {
      dx = Math.min(Math.abs(dx), Math.abs(dx - 1));
      dz = Math.min(Math.abs(dz), Math.abs(dz - 1));
    } else {
      dx = Math.abs(dx);
      dz = Math.abs(dz);
    }
    return (dx > 0 || dz > 0) && dx != 0 && dz != 0;
  }
}
