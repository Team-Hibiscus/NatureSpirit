package net.hibiscus.naturespirit.world.tree_decorator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.hibiscus.naturespirit.registration.NSWorldGen;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

public class WisteriaVinesTreeDecorator extends TreeDecorator {

  public static final MapCodec<WisteriaVinesTreeDecorator> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
    return instance.group(Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter((treeDecorator) -> treeDecorator.probability),
        BlockStateProvider.TYPE_CODEC.fieldOf("block_provider").forGetter((treeDecorator) -> treeDecorator.blockProvider),
        BlockStateProvider.TYPE_CODEC.fieldOf("block_provider2").forGetter((treeDecorator) -> treeDecorator.blockProvider2),
        BlockStateProvider.TYPE_CODEC.fieldOf("block_provider3").forGetter((treeDecorator) -> treeDecorator.blockProvider3),
        BlockStateProvider.TYPE_CODEC.fieldOf("block_provider4").forGetter((treeDecorator) -> treeDecorator.blockProvider4),
        Codec.intRange(0, 10).fieldOf("number").forGetter((treeDecorator) -> treeDecorator.number)).apply(instance, WisteriaVinesTreeDecorator::new);
  });
  protected final BlockStateProvider blockProvider;
  protected final BlockStateProvider blockProvider2;
  protected final BlockStateProvider blockProvider3;
  protected final BlockStateProvider blockProvider4;
  private final float probability;
  protected int number;

  public WisteriaVinesTreeDecorator(float probability, BlockStateProvider blockProvider, BlockStateProvider blockProvider2, BlockStateProvider blockProvider3,
      BlockStateProvider blockProvider4, int number) {
    this.probability = probability;
    this.blockProvider = blockProvider;
    this.blockProvider2 = blockProvider2;
    this.blockProvider3 = blockProvider3;
    this.blockProvider4 = blockProvider4;
    this.number = number;
  }

  private static void placeVines(BlockPos pos, BlockStateProvider block, BlockStateProvider block2, BlockStateProvider block3, BlockStateProvider block4, Generator generator,
      int number) {
    Random random = generator.getRandom();
    generator.replace(pos, block3.get(random, pos));
    if (!generator.isAir(pos.up(2)) || !generator.isAir(pos.up(3))) {
      generator.replace(pos.up(), block4.get(random, pos));
    }
    for (pos = pos.down(); number > 0; --number) {
      if (generator.isAir(pos)) {
        if (number == 1 || !generator.isAir(pos.down()) || random.nextBoolean()) {
          generator.replace(pos, block2.get(random, pos));
          break;
        }
        generator.replace(pos, block.get(random, pos));
      }
      pos = pos.down();
    }

  }

  @Override
  protected TreeDecoratorType<?> getType() {
    return NSWorldGen.WISTERIA_VINES_TREE_DECORATOR;
  }

  public void generate(Generator context) {
    Random randomSource = context.getRandom();
    context.getLeavesPositions().forEach((blockPos) -> {
      BlockPos blockPos2;
      if (randomSource.nextFloat() < this.probability) {
        blockPos2 = blockPos.down();
        if (context.isAir(blockPos2)) {
          placeVines(blockPos2, blockProvider, blockProvider2, blockProvider4, blockProvider3, context, this.number);
        }
      }

    });
  }
}
