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

public class BanyanTrunkPlacer extends TrunkPlacer {

  public static final MapCodec<BanyanTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
    return fillTrunkPlacerFields(instance).and(instance.group(IntProvider.POSITIVE_CODEC.fieldOf("extra_branch_steps").forGetter((trunkPlacer) -> {
      return trunkPlacer.extraBranchSteps;
    }), Codec.floatRange(0.0F, 1.0F).fieldOf("place_branch_per_log_probability").forGetter((trunkPlacer) -> {
      return trunkPlacer.placeBranchPerLogProbability;
    }), IntProvider.NON_NEGATIVE_CODEC.fieldOf("extra_branch_length").forGetter((trunkPlacer) -> {
      return trunkPlacer.extraBranchLength;
    }), RegistryCodecs.entryList(RegistryKeys.BLOCK).fieldOf("can_grow_through").forGetter((trunkPlacer) -> {
      return trunkPlacer.canGrowThrough;
    }))).apply(instance, BanyanTrunkPlacer::new);
  });
  private final IntProvider extraBranchSteps;
  private final float placeBranchPerLogProbability;
  private final IntProvider extraBranchLength;
  private final RegistryEntryList<Block> canGrowThrough;

  public BanyanTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight, IntProvider extraBranchSteps, float placeBranchPerLogProbability,
      IntProvider extraBranchLength, RegistryEntryList<Block> canGrowThrough) {
    super(baseHeight, firstRandomHeight, secondRandomHeight);
    this.extraBranchSteps = extraBranchSteps;
    this.placeBranchPerLogProbability = placeBranchPerLogProbability;
    this.extraBranchLength = extraBranchLength;
    this.canGrowThrough = canGrowThrough;
  }

  protected TrunkPlacerType<?> getType() {
    return NSWorldGen.BANYAN_TRUNK_PLACER;
  }

  public List<TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
    List<TreeNode> list = Lists.newArrayList();
    Mutable mutable = new Mutable();
    Mutable mutable2 = new Mutable();
    Direction trunkOffset = Type.HORIZONTAL.random(random);
    Direction trunkOffset2 = Type.HORIZONTAL.random(random);
    Direction directionUnvalidated = Type.HORIZONTAL.random(random);
    Direction direction = directionUnvalidated == trunkOffset.getOpposite() ? directionUnvalidated.getOpposite() : directionUnvalidated;
    Direction forkedDirectionUnvalidated = Type.HORIZONTAL.random(random);
    Direction forkedDirection =
        forkedDirectionUnvalidated == direction || forkedDirectionUnvalidated == trunkOffset ? forkedDirectionUnvalidated.getOpposite() : forkedDirectionUnvalidated;

    for (int i = 0; i < height; ++i) {
      int j = startPos.getY() + i;
      Mutable mutablePos1 = mutable.set(startPos.getX(), j, startPos.getZ());
      BlockPos pos2 = mutablePos1.offset(trunkOffset);
      BlockPos pos3 = pos2.offset(trunkOffset2);
      BlockPos blockPos = i >= 2 ? (trunkOffset != trunkOffset2 ? pos3 : pos2) : mutablePos1;
      int l = Math.min(13, Math.toIntExact(Math.round(Math.pow(.8, i - 1) * height)));
      float float1 = random.nextFloat();
      float float2 = random.nextFloat();

      Mutable forkedMutablePos1 = mutable2.set(startPos.getX(), j, startPos.getZ());
      BlockPos forkedPos2 = forkedMutablePos1.offset(trunkOffset.getOpposite()).offset(Direction.DOWN);
      BlockPos forkedPos3 = forkedPos2.offset(trunkOffset2.getOpposite());
      BlockPos forkedBlockPos = trunkOffset.getOpposite() != trunkOffset2.getOpposite() ? forkedPos3 : forkedPos2;
      boolean validator = i > 1 && l > 2;

      if (i == 2) {
        this.getAndSetState(world, replacer, random, blockPos, config);
        this.getAndSetState(world, replacer, random, forkedBlockPos, config);
      }

      this.getAndSetState(world, replacer, random, blockPos, config);
      if (i >= 2) {
        this.getAndSetState(world, replacer, random, forkedBlockPos, config);
      }
      if (validator) {
        this.generateExtraBranch(world, replacer, random, config, list, blockPos, j, direction, l);
        this.generateExtraBranch(world, replacer, random, config, list, forkedBlockPos, j, forkedDirection, l);

        if (float1 < this.placeBranchPerLogProbability - 0.35) {
          this.generateExtraBranch(world, replacer, random, config, list, blockPos, j, direction.rotateYClockwise(), l);
        }
        if (float2 < this.placeBranchPerLogProbability - 0.35) {
          this.generateExtraBranch(world, replacer, random, config, list, forkedBlockPos, j, forkedDirection.rotateYClockwise(), l);
        }
      }
      if (i == height - 1) {
        list.add(new TreeNode(blockPos.up(1), 0, false));
      }
      if (i == height - 2) {
        list.add(new TreeNode(forkedBlockPos.up(1), 0, false));
      }
      direction = direction.rotateYClockwise().rotateYClockwise();
      forkedDirection = forkedDirection.rotateYClockwise().rotateYClockwise();
    }

    return list;
  }

  private void generateExtraBranch(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, TreeFeatureConfig config, List<TreeNode> nodes, BlockPos pos,
      int yOffset, Direction direction, int length) {
    int j = pos.getX();
    int k = pos.getZ();
    Mutable mutable = new Mutable();
    Direction direction2 = random.nextFloat() < .5F ? direction.rotateYClockwise() : direction.rotateYCounterclockwise();
    int nextBetween = random.nextBetween(2, 4);
    boolean bl = random.nextFloat() < .5F;
    boolean bl2 = false;
    boolean bl3 = random.nextBoolean();
    boolean bl4 = false;

    for (int l = 0; l < length; ++l) {
      int m = yOffset + (l / nextBetween);
      Direction.Axis axis;
      if (bl) {
        k += direction2.getOffsetZ();
        if (bl3) {
          j += direction2.getOffsetX();
        }
        bl = random.nextFloat() < .8F;
        axis = direction2.getAxis();
      } else {
        axis = direction.getAxis();
        k += direction.getOffsetZ();
        if (bl3) {
          j += direction.getOffsetX();
        }
      }
      this.getAndSetState(world, replacer, random, mutable.set(j, m, k), config, blockState -> blockState.withIfExists(PillarBlock.AXIS, axis));
      if (l + 1 == length) {
        BlockPos blockPos = new BlockPos(j, m + 1, k);
        nodes.add(new TreeNode(blockPos, 0, false));
      }
      if (random.nextFloat() < .4f && !bl2 && l > length / 3) {
        this.generateSecondaryBranch(world, replacer, random, config, nodes, mutable, m, direction2.getOpposite());
        bl2 = true;
      }
      if (random.nextFloat() < .4f && !bl4 && l > length / 3) {
        this.generateSecondaryBranch(world, replacer, random, config, nodes, mutable, m, direction2);
        bl4 = true;
      }
    }


  }

  private void generateSecondaryBranch(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, TreeFeatureConfig config, List<TreeNode> nodes, BlockPos pos,
      int yOffset, Direction direction) {
    int j = pos.getX();
    int k = pos.getZ();
    Mutable mutable = new Mutable();

    for (int l = 0; l < 3; ++l) {
      j += direction.getOffsetX();
      k += direction.getOffsetZ();
      this.getAndSetState(world, replacer, random, mutable.set(j, yOffset, k), config, blockState -> blockState.withIfExists(PillarBlock.AXIS, direction.getAxis()));
      if (l + 1 == 3) {
        BlockPos blockPos = new BlockPos(j, yOffset + 1, k);
        nodes.add(new TreeNode(blockPos, 0, false));
      }
    }


  }

  protected boolean canReplace(TestableWorld world, BlockPos pos) {
    return super.canReplace(world, pos) || world.testBlockState(pos, (state) -> state.isIn(this.canGrowThrough));
  }
}
