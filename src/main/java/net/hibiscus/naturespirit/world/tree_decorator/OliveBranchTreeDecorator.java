package net.hibiscus.naturespirit.world.tree_decorator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.List;
import net.hibiscus.naturespirit.blocks.CoconutBlock;
import net.hibiscus.naturespirit.blocks.OliveBranchBlock;
import net.hibiscus.naturespirit.registration.NSWoods;
import net.hibiscus.naturespirit.registration.NSWorldGen;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

public class OliveBranchTreeDecorator extends TreeDecorator {

  public static final MapCodec<OliveBranchTreeDecorator> CODEC = Codec.floatRange(0.0F, 1.0F).fieldOf("probability").xmap(OliveBranchTreeDecorator::new, (decorator) -> {
    return decorator.probability;
  });
  private final float probability;

  public OliveBranchTreeDecorator(float probability) {
    this.probability = probability;
  }

  protected TreeDecoratorType<?> getType() {
    return NSWorldGen.OLIVE_BRANCH_DECORATOR;
  }

  public void generate(Generator generator) {
    Random random = generator.getRandom();
      List<BlockPos> list = generator.getLogPositions();
      list.stream().filter((pos) -> pos.getY() > list.getFirst().getY() + 2).forEach((pos) -> {

        for (Direction direction : Direction.values()) {
          if (random.nextFloat() < this.probability) {
            Direction direction2 = direction.getOpposite();
            BlockPos blockPos = pos.add(direction2.getOffsetX(), direction2.getOffsetY(), direction2.getOffsetZ());
            if (generator.isAir(blockPos)) {
              generator.replace(blockPos, NSWoods.OLIVE_BRANCH.getDefaultState().with(
                  OliveBranchBlock.FACING,
                  direction.getOpposite()
              ).with(
                  OliveBranchBlock.AGE,
                  random.nextInt(4)
              ));
            }
          }
        }

      });
  }
}

