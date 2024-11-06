package net.hibiscus.naturespirit.world.foliage_placer;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.hibiscus.naturespirit.registration.NSWorldGen;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

public class CoconutFoliagePlacer extends FoliagePlacer {

  public static final MapCodec<CoconutFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
    return fillFoliagePlacerFields(instance).apply(instance, CoconutFoliagePlacer::new);
  });

  public CoconutFoliagePlacer(IntProvider intProvider, IntProvider intProvider2) {
    super(intProvider, intProvider2);
  }

  protected FoliagePlacerType<?> getType() {
    return NSWorldGen.COCONUT_FOLIAGE_PLACER_TYPE;
  }

  protected void generate(TestableWorld world, BlockPlacer placer, Random random, TreeFeatureConfig config, int trunkHeight, TreeNode treeNode, int foliageHeight, int radius,
      int offset) {
    BlockPos blockPos = treeNode.getCenter();
    BlockPos.Mutable mutable = blockPos.mutableCopy();

    for (int l = -2; l <= foliageHeight; ++l) {
      if (l == -2) {
        mutable.set(blockPos, 0, l, 0);
        placeFoliageBlock(world, placer, random, config, mutable.add(2, 0, 0));
        placeFoliageBlock(world, placer, random, config, mutable.add(0, 0, 2));
        placeFoliageBlock(world, placer, random, config, mutable.add(0, 0, -2));
        placeFoliageBlock(world, placer, random, config, mutable.add(-2, 0, 0));
      } else if (l == -1) {
        mutable.set(blockPos, 0, l, 0);

        placeFoliageBlock(world, placer, random, config, mutable.add(2, 0, 0));
        placeFoliageBlock(world, placer, random, config, mutable.add(0, 0, 2));
        placeFoliageBlock(world, placer, random, config, mutable.add(0, 0, -2));
        placeFoliageBlock(world, placer, random, config, mutable.add(-2, 0, 0));

        placeFoliageBlock(world, placer, random, config, mutable.add(1, 0, 0));
        placeFoliageBlock(world, placer, random, config, mutable.add(0, 0, 1));
        placeFoliageBlock(world, placer, random, config, mutable.add(0, 0, -1));
        placeFoliageBlock(world, placer, random, config, mutable.add(-1, 0, 0));

        placeFoliageBlock2(world, placer, random, config, mutable.add(2, 0, 2));
        placeFoliageBlock2(world, placer, random, config, mutable.add(-2, 0, 2));
        placeFoliageBlock2(world, placer, random, config, mutable.add(-2, 0, -2));
        placeFoliageBlock2(world, placer, random, config, mutable.add(2, 0, -2));
      } else if (l == 0) {
        mutable.set(blockPos, 0, l, 0);
        generateSquare(world, placer, random, config, mutable, 1, 0, false);
        placeFoliageBlock2(world, placer, random, config, mutable.add(2, 0, 2));
        placeFoliageBlock2(world, placer, random, config, mutable.add(-2, 0, 2));
        placeFoliageBlock2(world, placer, random, config, mutable.add(-2, 0, -2));
        placeFoliageBlock2(world, placer, random, config, mutable.add(2, 0, -2));
      } else if (l == 1) {
        mutable.set(blockPos, 0, l, 0);
        generateSquare(world, placer, random, config, mutable, 1, 0, false);
        placeFoliageBlock(world, placer, random, config, mutable.add(2, 0, 0));
        placeFoliageBlock(world, placer, random, config, mutable.add(0, 0, 2));
        placeFoliageBlock(world, placer, random, config, mutable.add(0, 0, -2));
        placeFoliageBlock(world, placer, random, config, mutable.add(-2, 0, 0));
      } else if (l == 2) {
        mutable.set(blockPos, 0, l, 0);
        placeFoliageBlock(world, placer, random, config, mutable.add(2, 0, 0));
        placeFoliageBlock(world, placer, random, config, mutable.add(0, 0, 2));
        placeFoliageBlock(world, placer, random, config, mutable.add(0, 0, -2));
        placeFoliageBlock(world, placer, random, config, mutable.add(-2, 0, 0));

        placeFoliageBlock(world, placer, random, config, mutable.add(3, 0, 0));
        placeFoliageBlock(world, placer, random, config, mutable.add(0, 0, 3));
        placeFoliageBlock(world, placer, random, config, mutable.add(0, 0, -3));
        placeFoliageBlock(world, placer, random, config, mutable.add(-3, 0, 0));

        placeFoliageBlock2(world, placer, random, config, mutable.add(2, 0, 2));
        placeFoliageBlock2(world, placer, random, config, mutable.add(-2, 0, 2));
        placeFoliageBlock2(world, placer, random, config, mutable.add(-2, 0, -2));
        placeFoliageBlock2(world, placer, random, config, mutable.add(2, 0, -2));
      }
    }

  }

  protected void generateSquare(TestableWorld world, BlockPlacer placer, Random random, TreeFeatureConfig config, BlockPos centerPos, int radius, int y, boolean giantTrunk) {
    int i = giantTrunk ? 1 : 0;
    BlockPos.Mutable mutable = new BlockPos.Mutable();

    for (int j = -radius; j <= radius + i; ++j) {
      for (int k = -radius; k <= radius + i; ++k) {
        mutable.set(centerPos, j, y, k);
        placeFoliageBlock(world, placer, random, config, mutable);
      }
    }

  }

  protected static boolean placeFoliageBlock2(TestableWorld world, BlockPlacer placer, Random random, TreeFeatureConfig config, BlockPos pos) {
    if (!TreeFeature.canReplace(world, pos)) {
      return false;
    } else {
      BlockState blockState = config.foliageProvider.get(random, pos);
      if (blockState.contains(Properties.WATERLOGGED)) {
        blockState = blockState.with(Properties.WATERLOGGED, world.testFluidState(pos, (fluidState) -> fluidState.isEqualAndStill(Fluids.WATER)));
      }
      if (blockState.contains(LeavesBlock.DISTANCE)) {
        blockState = blockState.with(LeavesBlock.DISTANCE, 5);
      }

      placer.placeBlock(pos, blockState);
      return true;
    }
  }


  public int getRandomHeight(Random random, int trunkHeight, TreeFeatureConfig config) {
    return 2;
  }

  protected boolean isInvalidForLeaves(Random random, int dx, int y, int dz, int radius, boolean giantTrunk) {
    return false;
  }
}
