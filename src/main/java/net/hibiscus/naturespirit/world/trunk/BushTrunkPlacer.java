//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.hibiscus.naturespirit.world.trunk;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.BiConsumer;
import net.hibiscus.naturespirit.registration.NSWorldGen;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public class BushTrunkPlacer extends TrunkPlacer {
  public static final MapCodec<BushTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
    return fillTrunkPlacerFields(instance).apply(instance, BushTrunkPlacer::new);
  });

  public BushTrunkPlacer(int i, int j, int k) {
    super(i, j, k);
  }

  protected TrunkPlacerType<?> getType() {
    return NSWorldGen.BUSH_TRUNK_PLACER;
  }

  public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
    for(int i = 0; i < height; ++i) {
      this.getAndSetState(world, replacer, random, startPos.up(i), config);
    }

    return ImmutableList.of(new FoliagePlacer.TreeNode(startPos.up(height), 0, false));
  }
}
