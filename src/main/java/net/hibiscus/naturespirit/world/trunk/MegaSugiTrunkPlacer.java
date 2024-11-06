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

public class MegaSugiTrunkPlacer extends TrunkPlacer {

  public static final MapCodec<MegaSugiTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> fillTrunkPlacerFields(instance).apply(instance, MegaSugiTrunkPlacer::new));

  public MegaSugiTrunkPlacer(int i, int j, int k) {
    super(i, j, k);
  }

  protected TrunkPlacerType<?> getType() {
    return NSWorldGen.MEGA_SUGI_TRUNK_PLACER;
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
    int m = 10;

    for (int i = 0; i < height; ++i) {
      this.setLog(world, replacer, random, mutable, config, startPos, 0, i, 0);
      int j = startPos.getY() + i;
      if (i < height - 1) {
        this.setLog(world, replacer, random, mutable, config, startPos, 1, i, 0);
        this.setLog(world, replacer, random, mutable, config, startPos, 1, i, 1);
        this.setLog(world, replacer, random, mutable, config, startPos, 0, i, 1);
        if (i > 4 && i % 2 == 0) {
          int randomInt = random.nextFloat() < .75f || j < height / 3 ? 1 : random.nextFloat() < .5 ? -1 : 0;
          m = m - randomInt;
          Direction direction = Direction.Type.HORIZONTAL.random(random);
          this.generateExtraBranch(world, replacer, random, height, config, nodes, startPos.offset(direction.rotateYClockwise(), 1), j, direction, m, i);
          if (random.nextFloat() < .85) {
            this.generateExtraBranch(world, replacer, random, height, config, nodes, startPos, j, direction.rotateYClockwise(), m, i);
          }
          if (random.nextFloat() < .85) {
            this.generateExtraBranch(world, replacer, random, height, config, nodes, startPos.offset(direction.rotateYClockwise(), 1), j,
                direction.rotateYClockwise().rotateYClockwise(), m, i);
          }
          this.generateExtraBranch(world, replacer, random, height, config, nodes, startPos, j, direction.rotateYClockwise().rotateYClockwise().rotateYClockwise(), m, i);
        }
      }
      if (i == height - 1) {
        nodes.add(new FoliagePlacer.TreeNode(mutable.set(startPos.getX(), j + 1, startPos.getZ()), 0, false));
        nodes.add(new FoliagePlacer.TreeNode(mutable.set(startPos.getX(), j, startPos.getZ()), 1, false));
      }
    }

    return nodes;
  }

  private void generateExtraBranch(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, TreeFeatureConfig config,
      List<FoliagePlacer.TreeNode> nodes, BlockPos pos, int yOffset, Direction direction, int steps, int b) {
    int j = pos.getX();
    int k = pos.getZ();
    boolean bl = random.nextFloat() < .5F;
    Direction direction2 = bl ? direction.rotateYClockwise() : direction.rotateYCounterclockwise();
    BlockPos.Mutable mutable = new BlockPos.Mutable().set(pos);

    for (int l = 0; steps > 0 && (steps < (bl ? 7 : 10)); --steps) {
      if (l >= 1) {
        j += direction.getOffsetX();
        if (bl) {
          k += Math.max(0, direction2.getOffsetZ() - random.nextInt(2));
        } else {
          k += direction.getOffsetZ();
        }
        this.getAndSetState(world, replacer, random, mutable.set(j, yOffset, k), config, blockState -> blockState.withIfExists(PillarBlock.AXIS, direction.getAxis()));

        if (l == height - 1 || steps == 1) {
          BlockPos blockPos = new BlockPos(j, yOffset, k);
          nodes.add(new FoliagePlacer.TreeNode(blockPos, b > height / 3 ? (b > height / 2 ? 0 : 1) : 2, false));
        }
      }
      ++l;
    }

  }

  private void setLog(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos.Mutable tmpPos, TreeFeatureConfig config, BlockPos startPos, int dx,
      int dy, int dz) {
    tmpPos.set(startPos, dx, dy, dz);
    this.trySetState(world, replacer, random, tmpPos, config);
  }
}
