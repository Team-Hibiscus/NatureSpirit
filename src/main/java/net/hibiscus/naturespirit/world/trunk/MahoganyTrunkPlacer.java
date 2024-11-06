package net.hibiscus.naturespirit.world.trunk;

import com.google.common.collect.Lists;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.BiConsumer;
import net.hibiscus.naturespirit.registration.NSWorldGen;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public class MahoganyTrunkPlacer extends TrunkPlacer {

  public static final MapCodec<MahoganyTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> fillTrunkPlacerFields(instance).apply(instance, MahoganyTrunkPlacer::new));

  public MahoganyTrunkPlacer(int i, int j, int k) {
    super(i, j, k);
  }

  protected TrunkPlacerType<?> getType() {
    return NSWorldGen.MAHOGANY_TRUNK_PLACER;
  }

  public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos,
      TreeFeatureConfig config) {
    BlockPos blockPos = startPos.down();
    setToDirt(world, replacer, random, blockPos, config);
    setToDirt(world, replacer, random, blockPos.east(), config);
    setToDirt(world, replacer, random, blockPos.south(), config);
    setToDirt(world, replacer, random, blockPos.south().east(), config);
    BlockPos.Mutable mutable = new BlockPos.Mutable();
    List<FoliagePlacer.TreeNode> nodes = Lists.newArrayList();

    for (int i = 0; i < height; ++i) {
      this.setLog(world, replacer, random, mutable, config, startPos, 0, i, 0);
      if (i < height - 1) {
        this.setLog(world, replacer, random, mutable, config, startPos, 1, i, 0);
        this.setLog(world, replacer, random, mutable, config, startPos, 1, i, 1);
        this.setLog(world, replacer, random, mutable, config, startPos, 0, i, 1);
        if (i > height - 9 && random.nextFloat() < .85 && i % 2 == 0) {
          Direction direction = Direction.Type.HORIZONTAL.random(random);
          this.generateExtraBranch(world, replacer, random, config, nodes, startPos.offset(direction.rotateYClockwise(), 1), i, direction, random.nextBetween(6, 9), 1);
          if (random.nextFloat() < .75) {
            this.generateExtraBranch(world, replacer, random, config, nodes, startPos, i, direction.rotateYClockwise(), random.nextBetween(6, 9), 1);
          }
          if (random.nextFloat() < .75) {
            this.generateExtraBranch(world, replacer, random, config, nodes, startPos.offset(direction.rotateYClockwise(), 1), i, direction.rotateYClockwise().rotateYClockwise(),
                random.nextBetween(6, 9), 1);
          }
          this.generateExtraBranch(world, replacer, random, config, nodes, startPos, i, direction.rotateYClockwise().rotateYClockwise().rotateYClockwise(),
              random.nextBetween(6, 9), 1);
        }
      }
    }
    this.generateExtraBranch(world, replacer, random, config, nodes, startPos.north(1), height - 1, Direction.EAST, random.nextBetween(5, 7), 0);
    this.generateExtraBranch(world, replacer, random, config, nodes, startPos, height - 1, Direction.WEST, random.nextBetween(5, 7), 0);
    this.generateExtraBranch(world, replacer, random, config, nodes, startPos.east(1), height - 1, Direction.SOUTH, random.nextBetween(5, 7), 0);
    this.generateExtraBranch(world, replacer, random, config, nodes, startPos, height - 1, Direction.NORTH, random.nextBetween(5, 7), 0);

    return nodes;
  }

  private void generateExtraBranch(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, TreeFeatureConfig config, List<FoliagePlacer.TreeNode> nodes,
      BlockPos pos, int yOffset, Direction direction, int length, int foliageRadius) {
    int j = pos.getX();
    int k = pos.getZ();
    BlockPos.Mutable mutable = new BlockPos.Mutable();
    Direction direction2 = random.nextFloat() < .5F ? direction.rotateYClockwise() : direction.rotateYCounterclockwise();
    int nextBetween = random.nextBetween(2, 4);
    boolean bl = random.nextFloat() < .5F;
    boolean bl2 = false;
    boolean bl3 = random.nextBoolean();
    boolean bl4 = false;

    for (int l = 0; l < length; ++l) {
      int m = pos.getY() + (yOffset + (l / nextBetween));
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
        BlockPos blockPos = new BlockPos(j, m, k);
        nodes.add(new FoliagePlacer.TreeNode(blockPos, 0, false));
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

  private void generateSecondaryBranch(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, TreeFeatureConfig config, List<FoliagePlacer.TreeNode> nodes,
      BlockPos pos, int yOffset, Direction direction) {
    int j = pos.getX();
    int k = pos.getZ();
    BlockPos.Mutable mutable = new BlockPos.Mutable();

    for (int l = 0; l < 4; ++l) {
      j += direction.getOffsetX();
      k += direction.getOffsetZ();
      this.getAndSetState(world, replacer, random, mutable.set(j, yOffset, k), config, blockState -> blockState.withIfExists(PillarBlock.AXIS, direction.getAxis()));
      if (l + 1 == 4) {
        BlockPos blockPos = new BlockPos(j, yOffset, k);
        nodes.add(new FoliagePlacer.TreeNode(blockPos, 0, false));
      }
    }


  }

  private void setLog(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos.Mutable tmpPos, TreeFeatureConfig config, BlockPos startPos, int dx,
      int dy, int dz) {
    tmpPos.set(startPos, dx, dy, dz);
    this.trySetState(world, replacer, random, tmpPos, config);
  }
}
