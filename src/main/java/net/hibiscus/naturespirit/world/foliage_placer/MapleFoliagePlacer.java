//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.hibiscus.naturespirit.world.foliage_placer;

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

public class MapleFoliagePlacer extends FoliagePlacer {

  public static final MapCodec<MapleFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
    return fillFoliagePlacerFields(instance).and(instance.group(IntProvider.createValidatingCodec(4, 16).fieldOf("height").forGetter((foliagePlacer) -> {
      return foliagePlacer.height;
    }), Codec.floatRange(0.0F, 1.0F).fieldOf("hanging_leaves_chance").forGetter((foliagePlacer) -> {
      return foliagePlacer.hangingLeavesChance;
    }), Codec.floatRange(0.0F, 1.0F).fieldOf("hanging_leaves_extension_chance").forGetter((foliagePlacer) -> {
      return foliagePlacer.hangingLeavesExtensionChance;
    }))).apply(instance, MapleFoliagePlacer::new);
  });
  private final IntProvider height;
  private final float hangingLeavesChance;
  private final float hangingLeavesExtensionChance;

  public MapleFoliagePlacer(IntProvider radius, IntProvider offset, IntProvider height, float hangingLeavesChance, float hangingLeavesExtensionChance) {
    super(radius, offset);
    this.height = height;
    this.hangingLeavesChance = hangingLeavesChance;
    this.hangingLeavesExtensionChance = hangingLeavesExtensionChance;
  }

  protected FoliagePlacerType<?> getType() {
    return NSWorldGen.MAPLE_FOLIAGE_PLACER_TYPE;
  }

  protected void generate(TestableWorld world, BlockPlacer placer, Random random, TreeFeatureConfig config, int trunkHeight, TreeNode treeNode, int foliageHeight, int radius,
      int offset) {
    boolean bl = treeNode.isGiantTrunk();
    BlockPos blockPos = treeNode.getCenter().up(offset);
    int i = radius + treeNode.getFoliageRadius() - 1;
    this.generateSquare(world, placer, random, config, blockPos, i - 2, foliageHeight - 3, bl);
    this.generateSquare(world, placer, random, config, blockPos, i - 1, foliageHeight - 4, bl);

    for (int j = foliageHeight - 5; j >= 0; --j) {
      this.generateSquare(world, placer, random, config, blockPos, i, j, bl);
    }

    this.generateSquareWithHangingLeaves(world, placer, random, config, blockPos, i, -1, bl, this.hangingLeavesChance, this.hangingLeavesExtensionChance);
    this.generateSquareWithHangingLeaves(world, placer, random, config, blockPos, i - 1, -2, bl, this.hangingLeavesChance, this.hangingLeavesExtensionChance);
  }

  public int getRandomHeight(Random random, int trunkHeight, TreeFeatureConfig config) {
    return this.height.get(random);
  }

  protected boolean isInvalidForLeaves(Random random, int dx, int y, int dz, int radius, boolean giantTrunk) {
    if (y >= -1 && y <= 0 && (dx == radius || dz == radius || (dx == radius - y || dz == radius - y))) {
      return true;
    }
    boolean bl = dx == radius && dz == radius;
    boolean bl2 = radius > 2;
    if (bl2) {
      return bl || dx + dz > radius * 2 - 2;
    } else {
      return bl;
    }
  }
}
