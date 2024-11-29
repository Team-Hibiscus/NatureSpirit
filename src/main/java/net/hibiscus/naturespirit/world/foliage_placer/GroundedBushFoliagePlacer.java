package net.hibiscus.naturespirit.world.foliage_placer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Predicate;
import net.hibiscus.naturespirit.registration.NSWorldGen;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

public class GroundedBushFoliagePlacer extends FoliagePlacer {

  public static final MapCodec<GroundedBushFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
    return fillFoliagePlacerFields(instance).and(instance.group(IntProvider.createValidatingCodec(1, 512).fieldOf("foliage_height").forGetter((placer) -> {
      return placer.foliageHeight;
    }), Codec.intRange(0, 256).fieldOf("leaf_placement_attempts").forGetter((placer) -> {
      return placer.leafPlacementAttempts;
    }))).apply(instance, GroundedBushFoliagePlacer::new);
  });
  private final IntProvider foliageHeight;
  private final int leafPlacementAttempts;

  public GroundedBushFoliagePlacer(IntProvider radius, IntProvider offset, IntProvider foliageHeight, int leafPlacementAttempts) {
    super(radius, offset);
    this.foliageHeight = foliageHeight;
    this.leafPlacementAttempts = leafPlacementAttempts;
  }

  protected FoliagePlacerType<?> getType() {
    return NSWorldGen.GROUNDED_BUSH_PLACER_TYPE;
  }

  protected void generate(TestableWorld world, FoliagePlacer.BlockPlacer placer, Random random, TreeFeatureConfig config, int trunkHeight, FoliagePlacer.TreeNode treeNode,
      int foliageHeight, int radius, int offset) {
    BlockPos blockPos = treeNode.getCenter().down();
    BlockPos.Mutable mutable = blockPos.mutableCopy();

    for (int i = 0; i <= foliageHeight; i++) {
      for (int j = 0; j < this.leafPlacementAttempts / (i + 1); ++j) {
        mutable.set(blockPos, random.nextInt(radius) - random.nextInt(radius), i, random.nextInt(radius) - random.nextInt(radius));
        if (!world.testBlockState(mutable.down(), Predicate.isEqual(Blocks.AIR.getDefaultState()))) {
          placeFoliageBlock(world, placer, random, config, mutable);
        }
      }
    }

  }

  public int getRandomHeight(Random random, int trunkHeight, TreeFeatureConfig config) {
    return this.foliageHeight.get(random);
  }

  protected boolean isInvalidForLeaves(Random random, int dx, int y, int dz, int radius, boolean giantTrunk) {
    return false;
  }
}
