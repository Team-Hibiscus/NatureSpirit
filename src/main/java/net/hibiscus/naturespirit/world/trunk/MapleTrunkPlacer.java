//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.hibiscus.naturespirit.world.trunk;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import net.hibiscus.naturespirit.registration.NSWorldGen;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Direction.Type;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer.TreeNode;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public class MapleTrunkPlacer extends TrunkPlacer {

  private static final Codec<UniformIntProvider> BRANCH_START_OFFSET_FROM_TOP_CODEC;
  public static final MapCodec<MapleTrunkPlacer> CODEC;
  private final IntProvider branchCount;
  private final IntProvider branchHorizontalLength;
  private final UniformIntProvider branchStartOffsetFromTop;
  private final UniformIntProvider secondBranchStartOffsetFromTop;
  private final IntProvider branchEndOffsetFromTop;

  public MapleTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight, IntProvider branchCount, IntProvider branchHorizontalLength,
      UniformIntProvider branchStartOffsetFromTop, IntProvider branchEndOffsetFromTop) {
    super(baseHeight, firstRandomHeight, secondRandomHeight);
    this.branchCount = branchCount;
    this.branchHorizontalLength = branchHorizontalLength;
    this.branchStartOffsetFromTop = branchStartOffsetFromTop;
    this.secondBranchStartOffsetFromTop = UniformIntProvider.create(branchStartOffsetFromTop.getMin(), branchStartOffsetFromTop.getMax() - 1);
    this.branchEndOffsetFromTop = branchEndOffsetFromTop;
  }

  protected TrunkPlacerType<?> getType() {
    return NSWorldGen.MAPLE_TRUNK_PLACER;
  }

  public List<TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
    setToDirt(world, replacer, random, startPos.down(), config);
    int i = Math.max(0, height - 1 + this.branchStartOffsetFromTop.get(random));
    int j = Math.max(0, height - 1 + this.secondBranchStartOffsetFromTop.get(random));
    if (j >= i) {
      ++j;
    }

    int k = this.branchCount.get(random);
    boolean bl = k >= 3;
    boolean bl2 = k >= 2;
    boolean bl3 = k >= 4;
    boolean bl4 = k == 5;
    int l;
    if (bl) {
      l = height;
    } else if (bl2) {
      l = Math.max(i, j) + 1;
    } else {
      l = i + 1;
    }

    for (int m = 0; m < l; ++m) {
      this.getAndSetState(world, replacer, random, startPos.up(m), config);
    }

    List<TreeNode> list = new ArrayList();
    if (bl) {
      list.add(new TreeNode(startPos.up(l), 0, false));
    }

    Mutable mutable = new Mutable();
    Direction direction = Type.HORIZONTAL.random(random);
    Direction direction2 = direction.rotateClockwise(Direction.Axis.Y);
    Function<BlockState, BlockState> function = (state) -> {
      return (BlockState) state.withIfExists(PillarBlock.AXIS, direction.getAxis());
    };
    Function<BlockState, BlockState> function2 = (state) -> {
      return (BlockState) state.withIfExists(PillarBlock.AXIS, direction2.getAxis());
    };
    list.add(this.generateBranch(world, replacer, random, height, startPos, config, function, direction, i, i < l - 1, mutable));
    if (bl2) {
      list.add(this.generateBranch(world, replacer, random, height, startPos, config, function, direction.getOpposite(), j, j < l - 1, mutable));
    }
    if (bl3) {
      list.add(this.generateBranch(world, replacer, random, height, startPos, config, function2, direction2, j, j < l - 1, mutable));
    }
    if (bl4) {
      list.add(this.generateBranch(world, replacer, random, height, startPos, config, function2, direction2.getOpposite(), j, j < l - 1, mutable));
    }

    return list;
  }

  private TreeNode generateBranch(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, TreeFeatureConfig config,
      Function<BlockState, BlockState> withAxisFunction, Direction direction, int branchStartOffset, boolean branchBelowHeight, Mutable mutablePos) {
    mutablePos.set(startPos).move(Direction.UP, branchStartOffset);
    int i = height - 1 + this.branchEndOffsetFromTop.get(random);
    boolean bl = branchBelowHeight || i < branchStartOffset;
    int j = this.branchHorizontalLength.get(random) + (bl ? 1 : 0);
    BlockPos blockPos = startPos.offset(direction, j).up(i);
    int k = bl ? 2 : 1;

    for (int l = 0; l < k; ++l) {
      this.getAndSetState(world, replacer, random, mutablePos.move(direction), config, withAxisFunction);
    }

    Direction direction2 = blockPos.getY() > mutablePos.getY() ? Direction.UP : Direction.DOWN;

    while (true) {
      int m = mutablePos.getManhattanDistance(blockPos);
      if (m == 0) {
        return new TreeNode(blockPos.up(), 0, false);
      }

      float f = (float) Math.abs(blockPos.getY() - mutablePos.getY()) / (float) m;
      boolean bl2 = random.nextFloat() < f;
      mutablePos.move(bl2 ? direction2 : direction);
      this.getAndSetState(world, replacer, random, mutablePos, config, bl2 ? Function.identity() : withAxisFunction);
    }
  }

  static {
    BRANCH_START_OFFSET_FROM_TOP_CODEC = UniformIntProvider.CODEC.codec().validate((branchStartOffsetFromTop) -> {
      return branchStartOffsetFromTop.getMax() - branchStartOffsetFromTop.getMin() < 1 ? DataResult.error(() -> {
        return "Need at least 2 blocks variation for the branch starts to fit both branches";
      }) : DataResult.success(branchStartOffsetFromTop);
    });
    CODEC = RecordCodecBuilder.mapCodec((instance) -> {
      return fillTrunkPlacerFields(instance).and(instance.group(IntProvider.createValidatingCodec(1, 5).fieldOf("branch_count").forGetter((trunkPlacer) -> {
        return trunkPlacer.branchCount;
      }), IntProvider.createValidatingCodec(1, 16).fieldOf("branch_horizontal_length").forGetter((trunkPlacer) -> {
        return trunkPlacer.branchHorizontalLength;
      }), IntProvider.createValidatingCodec(-16, 0, BRANCH_START_OFFSET_FROM_TOP_CODEC).fieldOf("branch_start_offset_from_top").forGetter((trunkPlacer) -> {
        return trunkPlacer.branchStartOffsetFromTop;
      }), IntProvider.createValidatingCodec(-16, 16).fieldOf("branch_end_offset_from_top").forGetter((trunkPlacer) -> {
        return trunkPlacer.branchEndOffsetFromTop;
      }))).apply(instance, MapleTrunkPlacer::new);
    });
  }
}
