package net.hibiscus.naturespirit.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class LeveledRandomPatch extends Feature<RandomPatchFeatureConfig> {

  public LeveledRandomPatch(Codec<RandomPatchFeatureConfig> codec) {
    super(codec);
  }

  public boolean generate(FeatureContext<RandomPatchFeatureConfig> context) {
    RandomPatchFeatureConfig randomPatchFeatureConfig = context.getConfig();
    Random random = context.getRandom();
    BlockPos blockPos = context.getOrigin();
    StructureWorldAccess structureWorldAccess = context.getWorld();
    int i = 0;
    BlockPos.Mutable mutable = new BlockPos.Mutable();
    int j = randomPatchFeatureConfig.xzSpread() + 1;
    int k = randomPatchFeatureConfig.ySpread() + 1;

    for (int l = 0; l < randomPatchFeatureConfig.tries(); ++l) {
      int y = random.nextInt(k) - random.nextInt(k);
      mutable.set(blockPos, random.nextInt(j) - random.nextInt(j), y, random.nextInt(j) - random.nextInt(j));
      if (mutable.getY() > structureWorldAccess.getSeaLevel() + 1) {
        if (randomPatchFeatureConfig.feature().value().generateUnregistered(structureWorldAccess, context.getGenerator(), random, mutable)) {
          ++i;
        }
      }
    }

    return i > 0;
  }
}
