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

public class PaloVerdeTrunkPlacer extends TrunkPlacer {

  public static final MapCodec<PaloVerdeTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
    return fillTrunkPlacerFields(instance).and(instance.group(IntProvider.POSITIVE_CODEC.fieldOf("extra_branch_steps").forGetter((trunkPlacer) -> {
      return trunkPlacer.extraBranchSteps;
    }), Codec.floatRange(0.0F, 1.0F).fieldOf("place_branch_per_log_probability").forGetter((trunkPlacer) -> {
      return trunkPlacer.placeBranchPerLogProbability;
    }), IntProvider.NON_NEGATIVE_CODEC.fieldOf("extra_branch_length").forGetter((trunkPlacer) -> {
      return trunkPlacer.extraBranchLength;
    }), RegistryCodecs.entryList(RegistryKeys.BLOCK).fieldOf("can_grow_through").forGetter((trunkPlacer) -> {
      return trunkPlacer.canGrowThrough;
    }))).apply(instance, PaloVerdeTrunkPlacer::new);
  });
  private final IntProvider extraBranchSteps;
  private final float placeBranchPerLogProbability;
  private final IntProvider extraBranchLength;
  private final RegistryEntryList<Block> canGrowThrough;

  public PaloVerdeTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight, IntProvider extraBranchSteps, float placeBranchPerLogProbability,
      IntProvider extraBranchLength, RegistryEntryList<Block> canGrowThrough) {
    super(baseHeight, firstRandomHeight, secondRandomHeight);
    this.extraBranchSteps = extraBranchSteps;
    this.placeBranchPerLogProbability = placeBranchPerLogProbability;
    this.extraBranchLength = extraBranchLength;
    this.canGrowThrough = canGrowThrough;
  }

  protected TrunkPlacerType<?> getType() {
    return NSWorldGen.PALO_VERDE_TRUNK_PLACER;
  }

  public List<TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
    List<TreeNode> list = Lists.newArrayList();
    Mutable mutable = new Mutable();
    Mutable mutable2 = new Mutable();
    Direction trunkOffset = Type.HORIZONTAL.random(random);
    Direction trunkOffset2 = Type.HORIZONTAL.random(random);
    boolean bl = random.nextBoolean();

    for (int i = 0; i < height; ++i) {
      int j = startPos.getY() + i;
      Direction direction = Type.HORIZONTAL.random(random);
      Mutable mutablePos1 = mutable.set(startPos.getX(), j, startPos.getZ());
      BlockPos pos2 = mutablePos1.offset(trunkOffset);
      BlockPos pos3 = pos2.offset(trunkOffset2);
      BlockPos blockPos = i >= 2 ? (trunkOffset != trunkOffset2 ? pos3 : pos2) : mutablePos1;
      int l = Math.toIntExact(Math.round(Math.pow(random.nextGaussian(), .15F) * 3.5)) - 1;
      float float1 = random.nextFloat();
      float float2 = random.nextFloat();

      if (i == 2) {
        this.getAndSetState(world, replacer, random, mutablePos1, config);
      }
      if (this.getAndSetState(world, replacer, random, blockPos, config) && i < height - 1) {
        int m = height + i - 5;
        if (i > 1 && l > 0) {
          this.generateExtraBranch(world, replacer, random, config, list, blockPos, height, j, direction, l, m);

          if (float1 < this.placeBranchPerLogProbability - 0.35) {
            this.generateExtraBranch(world, replacer, random, config, list, blockPos, height, j, direction.rotateYClockwise(), l, m);
          }
          if (float1 < this.placeBranchPerLogProbability - 0.65) {
            this.generateExtraBranch(world, replacer, random, config, list, blockPos, height, j, direction.rotateYClockwise().rotateYClockwise(), l, m);
          }
        }
      }
      if (i == height - 1) {
        list.add(new TreeNode(blockPos.up(1), 0, false));
      }
      if (bl && i >= 2) {
        Direction forkedDirection = Type.HORIZONTAL.random(random);

        Mutable forkedMutablePos1 = mutable2.set(startPos.getX(), j, startPos.getZ());
        BlockPos forkedPos2 = forkedMutablePos1.offset(trunkOffset.getOpposite()).offset(Direction.DOWN);
        BlockPos forkedPos3 = forkedPos2.offset(trunkOffset2.getOpposite());
        BlockPos forkedBlockPos = trunkOffset.getOpposite() != trunkOffset2.getOpposite() ? forkedPos3 : forkedPos2;

        if (i == 2) {
          this.getAndSetState(world, replacer, random, forkedMutablePos1, config);
        }
        if (this.getAndSetState(world, replacer, random, forkedBlockPos, config) && i < height - 1) {
          int m = height + i - 5;
          if (i > 1 && l > 0) {
            this.generateExtraBranch(world, replacer, random, config, list, forkedBlockPos, height, j, forkedDirection, l, m);

            if (float2 < this.placeBranchPerLogProbability - 0.35) {
              this.generateExtraBranch(world, replacer, random, config, list, forkedBlockPos, height, j, forkedDirection.rotateYClockwise(), l, m);
            }
            if (float2 < this.placeBranchPerLogProbability - 0.65) {
              this.generateExtraBranch(world, replacer, random, config, list, forkedBlockPos, height, j, forkedDirection.rotateYClockwise().rotateYClockwise(), l, m);
            }
          }
        }
        if (i == height - 2) {
          list.add(new TreeNode(forkedBlockPos.up(1), 0, false));
        }
      }
    }

    return list;
  }

  private void generateExtraBranch(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, TreeFeatureConfig config, List<TreeNode> nodes, BlockPos pos,
      int height, int yOffset, Direction direction, int length, int steps) {
    int i = yOffset + length;
    int j = pos.getX();
    int k = pos.getZ();
    Mutable mutable = new Mutable();
    boolean bl = random.nextFloat() < .5F;
    Direction direction2 = random.nextFloat() < .5F ? direction.rotateYClockwise() : direction.rotateYCounterclockwise();

    for (int l = length; l < height - 3 && steps > 0; --steps) {
      if (l >= 1) {
        int m = Math.round(yOffset + l / 5);
        j += direction.getOffsetX();
        if (bl) {
          k += Math.max(0, direction2.getOffsetZ() - random.nextInt(2));
        } else {
          k += direction.getOffsetZ();
        }
        if (this.getAndSetState(world, replacer, random, mutable.set(j, m, k), config, blockState -> blockState.withIfExists(PillarBlock.AXIS, direction.getAxis()))) {
          i = m + 1;
        }
      }
      ++l;
    }
    if (i - yOffset >= 1) {
      BlockPos blockPos = new BlockPos(j, i, k);
      nodes.add(new TreeNode(blockPos, 0, false));
    }

  }

  protected boolean canReplace(TestableWorld world, BlockPos pos) {
    return super.canReplace(world, pos) || world.testBlockState(pos, (state) -> {
      return state.isIn(this.canGrowThrough);
    });
  }
}
