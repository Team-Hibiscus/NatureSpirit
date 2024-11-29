//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

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
import net.minecraft.block.PillarBlock;
import net.minecraft.registry.RegistryCodecs;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Direction.Type;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer.TreeNode;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public class SugiTrunkPlacer extends TrunkPlacer {

  public static final MapCodec<SugiTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
    return fillTrunkPlacerFields(instance).and(instance.group(IntProvider.POSITIVE_CODEC.fieldOf("extra_branch_steps").forGetter((trunkPlacer) -> {
      return trunkPlacer.extraBranchSteps;
    }), Codec.floatRange(0.0F, 1.0F).fieldOf("place_branch_per_log_probability").forGetter((trunkPlacer) -> {
      return trunkPlacer.placeBranchPerLogProbability;
    }), IntProvider.NON_NEGATIVE_CODEC.fieldOf("extra_branch_length").forGetter((trunkPlacer) -> {
      return trunkPlacer.extraBranchLength;
    }), RegistryCodecs.entryList(RegistryKeys.BLOCK).fieldOf("can_grow_through").forGetter((trunkPlacer) -> {
      return trunkPlacer.canGrowThrough;
    }))).apply(instance, SugiTrunkPlacer::new);
  });
  private final IntProvider extraBranchSteps;
  private final float placeBranchPerLogProbability;
  private final IntProvider extraBranchLength;
  private final RegistryEntryList<Block> canGrowThrough;

  public SugiTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight, IntProvider extraBranchSteps, float placeBranchPerLogProbability,
      IntProvider extraBranchLength, RegistryEntryList<Block> canGrowThrough) {
    super(baseHeight, firstRandomHeight, secondRandomHeight);
    this.extraBranchSteps = extraBranchSteps;
    this.placeBranchPerLogProbability = placeBranchPerLogProbability;
    this.extraBranchLength = extraBranchLength;
    this.canGrowThrough = canGrowThrough;
  }

  protected TrunkPlacerType<?> getType() {
    return NSWorldGen.SUGI_TRUNK_PLACER;
  }

  public List<TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
    List<TreeNode> list = Lists.newArrayList();
    Mutable mutable = new Mutable();

    Direction direction = Type.HORIZONTAL.random(random);
    for (int i = 0; i < height; ++i) {
      int j = startPos.getY() + i;
      if (this.getAndSetState(world, replacer, random, mutable.set(startPos.getX(), j, startPos.getZ()), config) && i < height - 1 && i > 3) {
        direction = direction.rotateYClockwise();
        Direction direction2 = direction.rotateYClockwise();
        Direction direction3 = direction2.rotateYClockwise();
        int m = height - i - 2;
        this.generateExtraBranch(world, replacer, random, height, config, list, mutable, j, direction, Math.min(m, 5), i);
        if (random.nextFloat() < this.placeBranchPerLogProbability - 0.25) {
          this.generateExtraBranch(world, replacer, random, height, config, list, mutable, j, direction2, Math.min(m, 5), i);
        }
        if (random.nextFloat() < this.placeBranchPerLogProbability - 0.35) {
          this.generateExtraBranch(world, replacer, random, height, config, list, mutable, j, direction3, Math.min(m, 5), i);
        }
      }
      if (i == height - 1) {
        list.add(new TreeNode(mutable.set(startPos.getX(), j + 1, startPos.getZ()), 0, false));
        list.add(new TreeNode(mutable.set(startPos.getX(), j, startPos.getZ()), 0, false));
      }
    }

    return list;
  }

  private void generateExtraBranch(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, TreeFeatureConfig config, List<TreeNode> nodes,
      Mutable pos, int yOffset, Direction direction, int steps, int b) {
    int j = pos.getX();
    int k = pos.getZ();
    boolean bl = random.nextFloat() < .5F;
    Direction direction2 = bl ? direction.rotateYClockwise() : direction.rotateYCounterclockwise();

    for (int l = 0; l < height && steps > 0 && (steps < (bl ? 7 : 10)); --steps) {
      if (l >= 1) {
        j += direction.getOffsetX();
        if (bl) {
          k += Math.max(0, direction2.getOffsetZ() - random.nextInt(2));
        } else {
          k += direction.getOffsetZ();
        }
        this.getAndSetState(world, replacer, random, pos.set(j, yOffset, k), config, blockState -> blockState.withIfExists(PillarBlock.AXIS, direction.getAxis()));

        if (l == height - 1 || steps == 1) {
          BlockPos blockPos = new BlockPos(j, yOffset, k);
          nodes.add(new TreeNode(blockPos, b > 5 ? 0 : 1, false));
        }
      }
      ++l;
    }

  }

  protected boolean canReplace(TestableWorld world, BlockPos pos) {
    return super.canReplace(world, pos) || world.testBlockState(pos, (state) -> state.isIn(this.canGrowThrough));
  }
}
