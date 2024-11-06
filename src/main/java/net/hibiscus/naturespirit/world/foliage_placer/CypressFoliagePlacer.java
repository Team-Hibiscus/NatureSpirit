package net.hibiscus.naturespirit.world.foliage_placer;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.hibiscus.naturespirit.registration.NSWorldGen;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

public class CypressFoliagePlacer extends FoliagePlacer {

  public static final MapCodec<CypressFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
    return fillFoliagePlacerFields(instance).and(IntProvider.createValidatingCodec(0, 24).fieldOf("trunk_height").forGetter((placer) -> {
      return placer.trunkHeight;
    })).apply(instance, CypressFoliagePlacer::new);
  });
  private final IntProvider trunkHeight;

  public CypressFoliagePlacer(IntProvider intProvider, IntProvider intProvider2, IntProvider trunkHeight) {
    super(intProvider, intProvider2);
    this.trunkHeight = trunkHeight;
  }

  protected FoliagePlacerType<?> getType() {
    return NSWorldGen.CYPRESS_FOLIAGE_PLACER_TYPE;
  }

  protected void generate(TestableWorld world, BlockPlacer placer, Random random, TreeFeatureConfig config, int trunkHeight, TreeNode treeNode, int foliageHeight, int radius,
      int offset) {
    BlockPos blockPos = treeNode.getCenter();
    BlockPos.Mutable mutable = blockPos.mutableCopy();
    boolean nextBoolean = random.nextBoolean();
    boolean nextBoolean2 = random.nextBoolean();
    boolean nextBoolean3 = random.nextBoolean();
    int i = random.nextInt(3);
    int j = 1;
    int k = 0;

    for (int l = offset; l >= -foliageHeight - 5; --l) {
      if (l <= offset - 4 && l >= offset - 14) {
        mutable.set(blockPos, 0, l, 0);
        this.generateSquare(world, placer, random, config, blockPos, 1, l, treeNode.isGiantTrunk());
      }
      if (l >= offset - 5) {
        mutable.set(blockPos, 0, l, 0);
        if ((nextBoolean) && l == offset) {
          placeFoliageBlock(world, placer, random, config, mutable);
        }
        if ((nextBoolean || nextBoolean2) && l == offset - 1) {
          placeFoliageBlock(world, placer, random, config, mutable);
        }
        if ((nextBoolean || nextBoolean2 || nextBoolean3) && l == offset - 2) {
          placeFoliageBlock(world, placer, random, config, mutable);
        }
        if (l <= offset - 3) {
          placeFoliageBlock(world, placer, random, config, mutable);
        }
      } else if (l == offset - 5) {
        mutable.set(blockPos, 0, l, 0);
        if (random.nextBoolean()) {
          placeFoliageBlock(world, placer, random, config, mutable.offset(Direction.random(random), 1).offset(Direction.random(random), 1).up(1));
        }
      } else if (l >= offset - 10) {
        mutable.set(blockPos, 0, l, 0);
        if (random.nextBoolean()) {
          placeFoliageBlock(world, placer, random, config, mutable.offset(Direction.NORTH, 1).offset(Direction.EAST, 1));
        }
        if (random.nextBoolean()) {
          placeFoliageBlock(world, placer, random, config, mutable.offset(Direction.NORTH, 1).offset(Direction.WEST, 1));
        }
        if (random.nextBoolean()) {
          placeFoliageBlock(world, placer, random, config, mutable.offset(Direction.SOUTH, 1).offset(Direction.EAST, 1));
        }
        if (random.nextBoolean()) {
          placeFoliageBlock(world, placer, random, config, mutable.offset(Direction.SOUTH, 1).offset(Direction.WEST, 1));
        }
      } else if (l >= offset - 13) {
        this.generateDiagonalSqaure(world, placer, random, config, blockPos, 2, l, treeNode.isGiantTrunk());
      }
    }

  }

  protected void generateSquare(TestableWorld world, BlockPlacer placer, Random random, TreeFeatureConfig config, BlockPos centerPos, int radius, int y, boolean giantTrunk) {
    int i = giantTrunk ? 1 : 0;
    BlockPos.Mutable mutable = new BlockPos.Mutable();

    for (int j = -radius; j <= radius + i; ++j) {
      for (int k = -radius; k <= radius + i; ++k) {
        if (!this.isPositionInvalid(random, j, y, k, radius, giantTrunk)) {
          mutable.set(centerPos, j, y, k);
          placeFoliageBlock(world, placer, random, config, mutable);
        }
      }
    }

  }

  protected void generateDiagonalSqaure(TestableWorld world, BlockPlacer placer, Random random, TreeFeatureConfig config, BlockPos centerPos, int radius, int y,
      boolean giantTrunk) {
    int i = giantTrunk ? 1 : 0;
    BlockPos.Mutable mutable = new BlockPos.Mutable();

    for (int j = -radius; j <= radius + i; ++j) {
      for (int k = -radius; k <= radius + i; ++k) {
        if (!this.isPositionInvalid2(random, j, y, k, radius, giantTrunk)) {
          mutable.set(centerPos, j, y, k);
          placeFoliageBlock(world, placer, random, config, mutable);
        }
      }
    }

  }

  protected boolean isPositionInvalid2(Random random, int dx, int y, int dz, int radius, boolean giantTrunk) {
    int i;
    int j;
    if (giantTrunk) {
      i = Math.min(Math.abs(dx), Math.abs(dx - 1));
      j = Math.min(Math.abs(dz), Math.abs(dz - 1));
    } else {
      i = Math.abs(dx);
      j = Math.abs(dz);
    }

    return this.isInvalidForLeaves2(random, i, y, j, radius, giantTrunk);
  }


  public int getRandomHeight(Random random, int trunkHeight, TreeFeatureConfig config) {
    return Math.max(8, trunkHeight - this.trunkHeight.get(random));
  }

  protected boolean isInvalidForLeaves(Random random, int dx, int y, int dz, int radius, boolean giantTrunk) {
    return dx == radius && dz == radius && radius > 0;
  }

  protected boolean isInvalidForLeaves2(Random random, int dx, int y, int dz, int radius, boolean giantTrunk) {
    int trunkHeight = this.trunkHeight.get(random);
    return (dx > 1 || dz > 1) && dx != 0 && dz != 0 && trunkHeight - y != trunkHeight;
  }
}
