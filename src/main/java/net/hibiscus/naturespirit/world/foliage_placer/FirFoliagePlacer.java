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

public class FirFoliagePlacer extends FoliagePlacer {

  public static final MapCodec<FirFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
    return fillFoliagePlacerFields(instance).and(IntProvider.createValidatingCodec(0, 24).fieldOf("trunk_height").forGetter((placer) -> {
      return placer.trunkHeight;
    })).apply(instance, FirFoliagePlacer::new);
  });
  private final IntProvider trunkHeight;

  public FirFoliagePlacer(IntProvider intProvider, IntProvider intProvider2, IntProvider trunkHeight) {
    super(intProvider, intProvider2);
    this.trunkHeight = trunkHeight;
  }

  protected FoliagePlacerType<?> getType() {
    return NSWorldGen.FIR_FOLIAGE_PLACER_TYPE;
  }

  protected void generate(TestableWorld world, BlockPlacer placer, Random random, TreeFeatureConfig config, int trunkHeight, TreeNode treeNode, int foliageHeight, int radius,
      int offset) {
    BlockPos blockPos = treeNode.getCenter();
    BlockPos.Mutable mutable = blockPos.mutableCopy();
    boolean nextBoolean = random.nextBoolean();
    boolean nextBoolean2 = random.nextBoolean();
    int i = random.nextInt(3);
    int j = 1;
    int k = 0;

    for (int l = offset; l >= -foliageHeight - 2; --l) {
      if (l >= offset - 2) {
        mutable.set(blockPos, 0, l, 0);
        if ((nextBoolean && !nextBoolean2) && l == offset) {
          placeFoliageBlock(world, placer, random, config, mutable);
        }
        if ((nextBoolean || nextBoolean2) && l == offset - 1) {
          placeFoliageBlock(world, placer, random, config, mutable);
        }
        if (l == offset - 2) {
          placeFoliageBlock(world, placer, random, config, mutable);
        }
      } else if (l == offset - 3) {
        mutable.set(blockPos, 0, l, 0);
        if (random.nextBoolean()) {
          placeFoliageBlock(world, placer, random, config, mutable.offset(Direction.random(random), 1).offset(Direction.random(random), 1).up(1));
        }
        this.generateSquare(world, placer, random, config, blockPos, 1, l, treeNode.isGiantTrunk());
      } else if (l == offset - 4) {
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
        this.generateSquare(world, placer, random, config, blockPos, 1, l, treeNode.isGiantTrunk());
      } else if (l == offset - 5) {
        mutable.set(blockPos, 0, l, 0);
        if (random.nextBoolean()) {
          Direction m = random.nextBoolean() ? Direction.NORTH : Direction.SOUTH;
          Direction n = random.nextBoolean() ? Direction.EAST : Direction.WEST;
          placeFoliageBlock(world, placer, random, config, mutable.offset(m, 1).offset(n, 1));
          placeFoliageBlock(world, placer, random, config, mutable.offset(m.getOpposite(), 1).offset(n.getOpposite(), 1));
        }
        if (random.nextBoolean()) {
          Direction m = random.nextBoolean() ? Direction.NORTH : Direction.SOUTH;
          Direction n = random.nextBoolean() ? Direction.EAST : Direction.WEST;
          placeFoliageBlock(world, placer, random, config, mutable.offset(m, 1).offset(n, 1));
          placeFoliageBlock(world, placer, random, config, mutable.offset(m.getOpposite(), 1).offset(n.getOpposite(), 1));
        }
      } else if (l == offset - 6) {
        mutable.set(blockPos, 0, l, 0);
        if (random.nextBoolean()) {
          Direction m = random.nextBoolean() ? Direction.NORTH : Direction.SOUTH;
          Direction n = random.nextBoolean() ? Direction.EAST : Direction.WEST;
          placeFoliageBlock(world, placer, random, config, mutable.offset(m, 1).offset(n, 1).up(1));
        }
        this.generateSquare(world, placer, random, config, blockPos, 1, l, treeNode.isGiantTrunk());
      } else if (l == offset - 7) {
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
        this.generateSquare(world, placer, random, config, blockPos, 1, l, treeNode.isGiantTrunk());
      } else if (l == offset - 8) {
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
        placeFoliageBlock(world, placer, random, config, mutable.offset(Direction.NORTH, 2));
        placeFoliageBlock(world, placer, random, config, mutable.offset(Direction.EAST, 2));
        placeFoliageBlock(world, placer, random, config, mutable.offset(Direction.SOUTH, 2));
        placeFoliageBlock(world, placer, random, config, mutable.offset(Direction.WEST, 2));
        this.generateSquare(world, placer, random, config, blockPos, 1, l, treeNode.isGiantTrunk());
      } else if (l == offset - 9) {
        this.generateSquare(world, placer, random, config, blockPos, 2, l, treeNode.isGiantTrunk());
      } else if (l == offset - 10) {
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
        placeFoliageBlock(world, placer, random, config, mutable.offset(Direction.NORTH, 2).offset(Direction.EAST, 1));
        placeFoliageBlock(world, placer, random, config, mutable.offset(Direction.NORTH, 2).offset(Direction.WEST, 1));
        placeFoliageBlock(world, placer, random, config, mutable.offset(Direction.EAST, 2).offset(Direction.NORTH, 1));
        placeFoliageBlock(world, placer, random, config, mutable.offset(Direction.EAST, 2).offset(Direction.SOUTH, 1));
        placeFoliageBlock(world, placer, random, config, mutable.offset(Direction.SOUTH, 2).offset(Direction.EAST, 1));
        placeFoliageBlock(world, placer, random, config, mutable.offset(Direction.SOUTH, 2).offset(Direction.WEST, 1));
        placeFoliageBlock(world, placer, random, config, mutable.offset(Direction.WEST, 2).offset(Direction.NORTH, 1));
        placeFoliageBlock(world, placer, random, config, mutable.offset(Direction.WEST, 2).offset(Direction.SOUTH, 1));
        this.generateSquare(world, placer, random, config, blockPos, 1, l, treeNode.isGiantTrunk());
      } else if (l == offset - 11) {
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


  public int getRandomHeight(Random random, int trunkHeight, TreeFeatureConfig config) {
    return Math.max(8, trunkHeight - this.trunkHeight.get(random));
  }

  protected boolean isInvalidForLeaves(Random random, int dx, int y, int dz, int radius, boolean giantTrunk) {
    return dx == radius && dz == radius && radius > 0;
  }
}
