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

public class AspenFoliagePlacer extends FoliagePlacer {

  public static final MapCodec<AspenFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
    return fillFoliagePlacerFields(instance).and(IntProvider.createValidatingCodec(0, 24).fieldOf("trunk_height").forGetter((placer) -> {
      return placer.trunkHeight;
    })).apply(instance, AspenFoliagePlacer::new);
  });
  private final IntProvider trunkHeight;

  public AspenFoliagePlacer(IntProvider intProvider, IntProvider intProvider2, IntProvider trunkHeight) {
    super(intProvider, intProvider2);
    this.trunkHeight = trunkHeight;
  }

  protected FoliagePlacerType<?> getType() {
    return NSWorldGen.ASPEN_FOLIAGE_PLACER_TYPE;
  }

  protected void generate(TestableWorld world, BlockPlacer placer, Random random, TreeFeatureConfig config, int trunkHeight, TreeNode treeNode, int foliageHeight, int radius,
      int offset) {
    BlockPos blockPos = treeNode.getCenter();
    BlockPos.Mutable mutable = blockPos.up(offset).mutableCopy();
    int i = random.nextBetween(0, 3);
    int j = 1;
    int k = 0;

    for (int l = offset; l >= -foliageHeight - 2; --l) {
      this.generateSquare(world, placer, random, config, blockPos, (l >= offset ? 0 : i), l, treeNode.isGiantTrunk());
      j = Math.min(j + 1, radius + treeNode.getFoliageRadius());
      i = l <= -foliageHeight - 1 ? Math.max(i - 2, 1) : (l >= offset - 1 ? 0 : (l >= offset - 3 ? 1 : (i * 1.25 >= j ? Math.max(i - 1, 1) : (l >= offset - 4 ? 2 : i + 1))));
    }

  }


  public int getRandomHeight(Random random, int trunkHeight, TreeFeatureConfig config) {
    return Math.max(6, trunkHeight - this.trunkHeight.get(random));
  }

  protected boolean isInvalidForLeaves(Random random, int dx, int y, int dz, int radius, boolean giantTrunk) {
    return dx == radius && dz == radius && radius > 0;
  }
}
