package net.hibiscus.naturespirit.world.trunk;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.BiConsumer;
import net.hibiscus.naturespirit.registration.NSWorldGen;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.RegistryCodecs;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public class WisteriaTrunkPlacer extends TrunkPlacer {

  public static final MapCodec<WisteriaTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
    return fillTrunkPlacerFields(instance).and(instance.group(IntProvider.POSITIVE_CODEC.fieldOf("extra_branch_steps").forGetter((WisteriaTrunkPlacer) -> {
      return WisteriaTrunkPlacer.extraBranchSteps;
    }), Codec.floatRange(0.0F, 1.0F).fieldOf("place_branch_per_log_probability").forGetter((WisteriaTrunkPlacer) -> {
      return WisteriaTrunkPlacer.placeBranchPerLogProbability;
    }), IntProvider.NON_NEGATIVE_CODEC.fieldOf("extra_branch_length").forGetter((WisteriaTrunkPlacer) -> {
      return WisteriaTrunkPlacer.extraBranchLength;
    }), RegistryCodecs.entryList(RegistryKeys.BLOCK).fieldOf("can_grow_through").forGetter((WisteriaTrunkPlacer) -> {
      return WisteriaTrunkPlacer.canGrowThrough;
    }))).apply(instance, WisteriaTrunkPlacer::new);
  });
  private final IntProvider extraBranchSteps;
  private final float placeBranchPerLogProbability;
  private final IntProvider extraBranchLength;
  private final RegistryEntryList<Block> canGrowThrough;

  public WisteriaTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight, IntProvider extraBranchSteps, float placeBranchPerLogProbability,
      IntProvider extraBranchLength, RegistryEntryList<Block> canGrowThrough) {
    super(baseHeight, firstRandomHeight, secondRandomHeight);
    this.extraBranchSteps = extraBranchSteps;
    this.placeBranchPerLogProbability = placeBranchPerLogProbability;
    this.extraBranchLength = extraBranchLength;
    this.canGrowThrough = canGrowThrough;
  }

  protected TrunkPlacerType<?> getType() {
    return NSWorldGen.WISTERIA_TRUNK_PLACER;
  }

  public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos,
      TreeFeatureConfig config) {
    List<FoliagePlacer.TreeNode> list = Lists.newArrayList();
    BlockPos.Mutable mutable = new BlockPos.Mutable();

    for (int i = 0; i < height; ++i) {
      int j = startPos.getY() + i;
      if (this.getAndSetState(world,
          replacer,
          random,
          mutable.set(startPos.getX(), j, startPos.getZ()),
          config
      ) && i < height - 3 && i > 2 && random.nextFloat() < this.placeBranchPerLogProbability) {
        Direction direction = Direction.Type.HORIZONTAL.random(random);
        int k = this.extraBranchLength.get(random);
        int l = Math.max(0, k - this.extraBranchLength.get(random) - 1);
        int m = this.extraBranchSteps.get(random);
        int g = startPos.getY() + i - 1;
        this.placeBranch(world, replacer, random, height, config, list, mutable, j, direction, l, m);
      }
      if (i + 1 == height) {
        list.add(new FoliagePlacer.TreeNode(mutable.set(startPos.getX(), j, startPos.getZ()), -1, false));
      }
    }

    return list;
  }

  private void placeBranch(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, TreeFeatureConfig config, List<FoliagePlacer.TreeNode> nodes,
      BlockPos.Mutable pos, int yOffset, Direction direction, int length, int steps) {
    int i = yOffset + length;
    int j = pos.getX();
    int k = pos.getZ();

    for (int l = length; l < height && steps > 0; --steps) {
      if (l >= 1) {
        int m = yOffset + l;
        j += direction.getOffsetX();
        k += direction.getOffsetZ();
        i = m;
        if (this.getAndSetState(world, replacer, random, pos.set(j, m, k), config)) {
          i = m + 1;
        }
      }

      ++l;
    }

    if (i - yOffset > 1) {
      BlockPos blockPos = new BlockPos(j, i, k);
      nodes.add(new FoliagePlacer.TreeNode(blockPos, 0, false));
    }

  }

  protected boolean canReplace(TestableWorld levelSimulatedReader, BlockPos blockPos) {
    return super.canReplace(levelSimulatedReader, blockPos) || levelSimulatedReader.testBlockState(blockPos, (blockState) -> {
      return blockState.isIn(this.canGrowThrough);
    });
  }
}
