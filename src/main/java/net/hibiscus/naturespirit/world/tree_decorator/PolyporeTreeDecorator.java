package net.hibiscus.naturespirit.world.tree_decorator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.hibiscus.naturespirit.blocks.PolyporeBlock;
import net.hibiscus.naturespirit.registration.NSWorldGen;
import net.minecraft.block.MushroomBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

public class PolyporeTreeDecorator extends TreeDecorator {

  public static final MapCodec<PolyporeTreeDecorator> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
    return instance.group(
        Codec.floatRange(0.0F, 1.0F).fieldOf("big_probability").forGetter((treeDecorator) -> treeDecorator.big_probability),
        Codec.floatRange(0.0F, 1.0F).fieldOf("small_probability").forGetter((treeDecorator) -> treeDecorator.small_probability),
        Codec.floatRange(0.0F, 1.0F).fieldOf("chance").forGetter((treeDecorator) -> treeDecorator.chance),
        BlockStateProvider.TYPE_CODEC.fieldOf("block_provider").forGetter((treeDecorator) -> treeDecorator.block_provider),
        BlockStateProvider.TYPE_CODEC.fieldOf("polypore_provider").forGetter((treeDecorator) -> treeDecorator.polypore_provider)
    ).apply(instance, PolyporeTreeDecorator::new);
  });
  private final float big_probability;
  private final float small_probability;
  private final float chance;
  private final BlockStateProvider block_provider;
  private final BlockStateProvider polypore_provider;

  public PolyporeTreeDecorator(float big_probability, float small_probability, float chance, BlockStateProvider block_provider, BlockStateProvider polypore_provider) {
    this.chance = chance;
    this.big_probability = big_probability;
    this.small_probability = small_probability;
    this.block_provider = block_provider;
    this.polypore_provider = polypore_provider;
  }

  protected TreeDecoratorType<?> getType() {
    return NSWorldGen.POLYPORE_DECORATOR;
  }

  public void generate(Generator generator) {
    Random random = generator.getRandom();
    if (random.nextFloat() < this.chance) {
      List<BlockPos> list = generator.getLogPositions();
      list.stream().filter((pos) -> pos.getY() < list.get(0).getY() + 6 && pos.getY() > list.get(0).getY()).forEach((pos) -> {
        if (random.nextFloat() <= this.big_probability) {
          for (Direction direction : Direction.Type.HORIZONTAL.getShuffled(random)) {
            if (generator.isAir(pos.offset(direction))) {
              Direction direction2 = direction.rotateYClockwise();
              Direction direction3 = direction.rotateYCounterclockwise();
              int radius = random.nextBetween(1, 2);
              if (generator.isAir(pos.offset(direction2)) && generator.isAir(pos.offset(direction2).offset(direction))) {
                generateSquare(generator, pos, radius, direction, direction2, random);
                break;
              } else if (generator.isAir(pos.offset(direction3)) && generator.isAir(pos.offset(direction3).offset(direction))) {
                generateSquare(generator, pos, radius, direction, direction3, random);
                break;
              }
            }
          }
        }
        if (random.nextFloat() <= this.small_probability) {
          for (Direction direction : Direction.Type.HORIZONTAL.getShuffled(random)) {
            if (generator.isAir(pos.offset(direction))) {
              Direction direction2 = direction.rotateYClockwise();
              generator.replace(pos.offset(direction), polypore_provider.get(random, pos.offset(direction)).withIfExists(PolyporeBlock.FACING, direction));
              if (generator.isAir(pos.offset(direction2))) {
                generator.replace(pos.offset(direction2), polypore_provider.get(random, pos.offset(direction2)).withIfExists(PolyporeBlock.FACING, direction2));
              }
              break;
            }
          }
        }
      });
    }
  }

  protected void generateSquare(Generator generator, BlockPos cornerPos, int radius, Direction direction1, Direction direction2, Random random) {
    BlockPos.Mutable mutable = new BlockPos.Mutable().set(cornerPos);
    BlockPos blockPos;
    for (int j = 0; j <= radius; ++j) {
      for (int k = 0; k <= radius; ++k) {
        blockPos = mutable.offset(direction2, k).offset(direction1, j);
        if (generator.isAir(blockPos)) {
          generator.replace(blockPos, block_provider.get(random, blockPos).withIfExists(MushroomBlock.DOWN, false));
        }
      }
    }

  }
}

