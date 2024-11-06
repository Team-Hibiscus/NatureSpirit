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
import net.minecraft.client.MinecraftClient;
import net.minecraft.registry.RegistryCodecs;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Direction.Axis;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer.TreeNode;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public class OliveTrunkPlacer extends TrunkPlacer {

  public static final MapCodec<OliveTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
    return fillTrunkPlacerFields(instance).and(instance.group(IntProvider.POSITIVE_CODEC.fieldOf("extra_branch_steps").forGetter((trunkPlacer) -> {
      return trunkPlacer.extraBranchSteps;
    }), Codec.floatRange(0.0F, 1.0F).fieldOf("place_branch_per_log_probability").forGetter((trunkPlacer) -> {
      return trunkPlacer.placeBranchPerLogProbability;
    }), IntProvider.NON_NEGATIVE_CODEC.fieldOf("extra_branch_length").forGetter((trunkPlacer) -> {
      return trunkPlacer.extraBranchLength;
    }), RegistryCodecs.entryList(RegistryKeys.BLOCK).fieldOf("can_grow_through").forGetter((trunkPlacer) -> {
      return trunkPlacer.canGrowThrough;
    }))).apply(instance, OliveTrunkPlacer::new);
  });
  private final IntProvider extraBranchSteps;
  private final float placeBranchPerLogProbability;
  private final IntProvider extraBranchLength;
  private final RegistryEntryList<Block> canGrowThrough;

  public OliveTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight, IntProvider extraBranchSteps, float placeBranchPerLogProbability,
      IntProvider extraBranchLength, RegistryEntryList<Block> canGrowThrough) {
    super(baseHeight, firstRandomHeight, secondRandomHeight);
    this.extraBranchSteps = extraBranchSteps;
    this.placeBranchPerLogProbability = placeBranchPerLogProbability;
    this.extraBranchLength = extraBranchLength;
    this.canGrowThrough = canGrowThrough;
  }

  protected TrunkPlacerType<?> getType() {
    return NSWorldGen.OLIVE_TRUNK_PLACER;
  }

  public List<TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
    List<TreeNode> list = Lists.newArrayList();
    BlockPos blockPos = startPos.down();
    setToDirt(world, replacer, random, blockPos, config);
    setToDirt(world, replacer, random, blockPos.east(), config);
    setToDirt(world, replacer, random, blockPos.south(), config);
    setToDirt(world, replacer, random, blockPos.south().east(), config);
    Mutable mutable = new Mutable();
    for (int i = 0; i < height; ++i) {
      int j = startPos.getY() + i;
      Mutable mutablePos1 = mutable.set(startPos.getX(), j, startPos.getZ());
      this.getAndSetState(world, replacer, random, mutablePos1, config);
      this.getAndSetState(world, replacer, random, mutablePos1.east(), config);
      this.getAndSetState(world, replacer, random, mutablePos1.south(), config);
      this.getAndSetState(world, replacer, random, mutablePos1.south().east(), config);

      if (i == 0) {
        this.getAndSetState(world, replacer, random, getRoot(random, mutablePos1), config);
        this.getAndSetState(world, replacer, random, getRoot(random, mutablePos1), config);
      }

      if (i == height - 1) {
        this.generateExtraBranch(world, replacer, random, config, list, mutablePos1.north(1), j, Direction.NORTH, this.extraBranchSteps.get(random));
        if (random.nextFloat() < this.placeBranchPerLogProbability - .65) {
          this.generateExtraBranch(world, replacer, random, config, list, mutablePos1.west(1), j, Direction.WEST, this.extraBranchSteps.get(random));
        }

        this.generateExtraBranch(world, replacer, random, config, list, mutablePos1.south(2), j, Direction.SOUTH, this.extraBranchSteps.get(random));
        if (random.nextFloat() <  this.placeBranchPerLogProbability - .65) {
          this.generateExtraBranch(world, replacer, random, config, list, mutablePos1.south(2), j, Direction.WEST, this.extraBranchSteps.get(random));
        }

        this.generateExtraBranch(world, replacer, random, config, list, mutablePos1.east(2), j, Direction.EAST, this.extraBranchSteps.get(random));
        if (random.nextFloat() < this.placeBranchPerLogProbability - .65) {
          this.generateExtraBranch(world, replacer, random, config, list, mutablePos1.east(2), j, Direction.NORTH, this.extraBranchSteps.get(random));
        }

        this.generateExtraBranch(world, replacer, random, config, list, mutablePos1.south(2).east(), j, Direction.WEST, this.extraBranchSteps.get(random));
        if (random.nextFloat() < this.placeBranchPerLogProbability - .65) {
          this.generateExtraBranch(world, replacer, random, config, list, mutablePos1.south().east(2), j, Direction.SOUTH, this.extraBranchSteps.get(random));
        }
      }
    }

    return list;
  }

  private void generateExtraBranch(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, TreeFeatureConfig config, List<TreeNode> nodes,
      BlockPos pos, int yOffset, Direction direction, int steps) {
    int m = yOffset;
    int j = pos.getX();
    int k = pos.getZ();
    Mutable mutable = new Mutable();
    boolean bl = random.nextBoolean();

    Direction direction2 = random.nextBoolean() ? direction.rotateYClockwise() : direction.rotateYCounterclockwise();

    for (int l = 0; steps > 0; --steps, ++l) {
      boolean bl2 = random.nextBoolean();
      m = yOffset + l;
      if (bl2 && l != 0) {
        j += direction.getOffsetX();
        if (bl) {
          k += Math.max(0, direction2.getOffsetZ() - random.nextInt(2));
        } else {
          k += direction.getOffsetZ();
        }
      }
      int finalL = l;
      this.getAndSetState(world, replacer, random, mutable.set(j, m, k), config, blockState -> blockState.withIfExists(PillarBlock.AXIS, bl2 || finalL == 0 ? direction.getAxis() : Axis.Y));
    }
    if (m - yOffset >= 1) {
      nodes.add(new TreeNode(new BlockPos(j, m, k), 0, false));
    }
  }

  protected boolean canReplace(TestableWorld world, BlockPos pos) {
    return super.canReplace(world, pos) || world.testBlockState(pos, (state) -> state.isIn(this.canGrowThrough));
  }
  protected BlockPos getRoot(Random random, Mutable mutablePos1) {
    return switch (random.nextInt( 7)) {
      case 1 -> mutablePos1.east(2);
      case 2 -> mutablePos1.south(2);
      case 3 -> mutablePos1.south(2).east();
      case 4 -> mutablePos1.west();
      case 5 -> mutablePos1.east().north();
      case 6 -> mutablePos1.south().east(2);
      case 7 -> mutablePos1.south().west();
      default -> mutablePos1.north();
    };
  }
}
