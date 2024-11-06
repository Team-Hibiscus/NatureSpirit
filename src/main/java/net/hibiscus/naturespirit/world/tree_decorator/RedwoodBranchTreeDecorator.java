package net.hibiscus.naturespirit.world.tree_decorator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.hibiscus.naturespirit.registration.NSWorldGen;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

public class RedwoodBranchTreeDecorator extends TreeDecorator {

  public static final MapCodec<RedwoodBranchTreeDecorator> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
    return instance.group(
        Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter((treeDecorator) -> treeDecorator.probability),
        BlockStateProvider.TYPE_CODEC.fieldOf("leaf_provider").forGetter((treeDecorator) -> treeDecorator.leaf_provider)
    ).apply(instance, RedwoodBranchTreeDecorator::new);
  });
  private final float probability;
  private final BlockStateProvider leaf_provider;

  public RedwoodBranchTreeDecorator(float probability, BlockStateProvider leaf_provider) {
    this.probability = probability;
    this.leaf_provider = leaf_provider;
  }

  protected TreeDecoratorType<?> getType() {
    return NSWorldGen.REDWOOD_BRANCH_DECORATOR;
  }

  public void generate(Generator generator) {
    Random random = generator.getRandom();
    List<BlockPos> list = generator.getLogPositions();
    List<BlockPos> list2 = generator.getLeavesPositions();
    list.stream().filter((pos) -> pos.getY() < list2.get(0).getY() - 2 && pos.getY() > list.get(0).getY() + 2).forEach((pos) -> {

      if (random.nextFloat() <= this.probability) {
        Direction direction = Direction.Type.HORIZONTAL.random(random);
        BlockPos.Mutable blockPos = pos.add(direction.getOffsetX(), 0, direction.getOffsetZ()).mutableCopy();
        if (generator.isAir(blockPos)) {
          generator.replace(blockPos, leaf_provider.get(random, blockPos));
        }
        blockPos.move(direction.rotateYClockwise());
        if (generator.isAir(blockPos)) {
          generator.replace(blockPos, leaf_provider.get(random, blockPos));
        }
        blockPos.move(direction.rotateYClockwise().getOpposite(), 2);
        if (generator.isAir(blockPos)) {
          generator.replace(blockPos, leaf_provider.get(random, blockPos));
        }
        blockPos.move(direction.rotateYClockwise()).move(direction);
        if (generator.isAir(blockPos)) {
          generator.replace(blockPos, leaf_provider.get(random, blockPos));
        }
        blockPos.move(direction.getOpposite()).move(Direction.UP);
        if (generator.isAir(blockPos)) {
          generator.replace(blockPos, leaf_provider.get(random, blockPos));
        }
      }
    });
  }
}

