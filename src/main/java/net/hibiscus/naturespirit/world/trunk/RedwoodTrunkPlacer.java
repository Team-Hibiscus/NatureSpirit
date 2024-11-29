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

public class RedwoodTrunkPlacer extends TrunkPlacer {

  public static final MapCodec<RedwoodTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
    return fillTrunkPlacerFields(instance).apply(instance, RedwoodTrunkPlacer::new);
  });

  public RedwoodTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
    super(baseHeight, firstRandomHeight, secondRandomHeight);
  }

  @Override
  protected TrunkPlacerType<?> getType() {
    return NSWorldGen.REDWOOD_TRUNK_PLACER;
  }

  public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos,
      TreeFeatureConfig config) {
    BlockPos blockPos = startPos.down();
    setToDirt(world, replacer, random, blockPos.north().east(), config);
    setToDirt(world, replacer, random, blockPos.north().west(), config);
    setToDirt(world, replacer, random, blockPos.north(), config);
    setToDirt(world, replacer, random, blockPos.west(), config);
    setToDirt(world, replacer, random, blockPos, config);
    setToDirt(world, replacer, random, blockPos.east(), config);
    setToDirt(world, replacer, random, blockPos.south(), config);
    setToDirt(world, replacer, random, blockPos.south().east(), config);
    setToDirt(world, replacer, random, blockPos.south().west(), config);
    BlockPos.Mutable mutable = new BlockPos.Mutable();

    for (int i = 0; i < height; ++i) {
      this.setLog(world, replacer, random, mutable, config, startPos, 0, i, 0);
      if (i < height - 1) {
        this.setLog(world, replacer, random, mutable, config, startPos, 1, i, 0);
        this.setLog(world, replacer, random, mutable, config, startPos, 1, i, -1);
        this.setLog(world, replacer, random, mutable, config, startPos, 1, i, 1);
        this.setLog(world, replacer, random, mutable, config, startPos, 0, i, 1);
        this.setLog(world, replacer, random, mutable, config, startPos, -1, i, 1);
        this.setLog(world, replacer, random, mutable, config, startPos, -1, i, 0);
        this.setLog(world, replacer, random, mutable, config, startPos, 0, i, -1);
        this.setLog(world, replacer, random, mutable, config, startPos, -1, i, -1);
      }
    }

    return ImmutableList.of(new FoliagePlacer.TreeNode(startPos.up(height), 0, false));
  }

  private void setLog(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos.Mutable tmpPos, TreeFeatureConfig config, BlockPos startPos, int dx,
      int dy, int dz) {
    tmpPos.set(startPos, dx, dy, dz);
    this.trySetState(world, replacer, random, tmpPos, config);
  }
}
