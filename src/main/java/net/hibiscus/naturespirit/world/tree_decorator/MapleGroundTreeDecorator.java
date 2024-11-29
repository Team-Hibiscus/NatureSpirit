package net.hibiscus.naturespirit.world.tree_decorator;

import com.google.common.collect.Lists;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.hibiscus.naturespirit.registration.NSMiscBlocks;
import net.hibiscus.naturespirit.registration.NSWorldGen;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

public class MapleGroundTreeDecorator extends TreeDecorator {

  public static final MapCodec<MapleGroundTreeDecorator> CODEC;
  private final BlockStateProvider provider;
  private final BlockStateProvider provider2;

  public MapleGroundTreeDecorator(BlockStateProvider provider, BlockStateProvider provider2) {
    this.provider = provider;
    this.provider2 = provider2;
  }

  protected TreeDecoratorType<?> getType() {
    return NSWorldGen.MAPLE_GROUND_TREE_DECORATOR;
  }

  public void generate(Generator generator) {
    List<BlockPos> list = Lists.newArrayList();
    List<BlockPos> list3 = generator.getLogPositions();
    list.addAll(list3);
    if (!list.isEmpty()) {
      int i = list.get(0).getY();
      list.stream().filter((pos) -> pos.getY() == i).forEach((pos) -> {
        this.setArea(generator, pos);
      });
    }
  }

  private void setArea(Generator generator, BlockPos origin) {
    for (int i = -2; i <= 2; ++i) {
      for (int j = -2; j <= 2; ++j) {
        if (Math.abs(i) != 2 || Math.abs(j) != 2) {
          if (Math.abs(i) == 2 || Math.abs(j) == 2) {
            this.setColumn2(generator, origin.add(i, 0, j));
          } else {
            this.setColumn(generator, origin.add(i, 0, j));
          }
        }
      }
    }

  }

  private void setColumn(Generator generator, BlockPos origin) {
    for (int i = 2; i >= -3; --i) {
      BlockPos blockPos = origin.up(i);
      if (Feature.isSoil(generator.getWorld(), blockPos)) {
        generator.replace(blockPos, this.provider.get(generator.getRandom(), origin));
        break;
      }

      if (!generator.isAir(blockPos) && i < 0) {
        break;
      }
    }

  }

  private void setColumn2(Generator generator, BlockPos origin) {
    for (int i = 2; i >= -3; --i) {
      BlockPos blockPos = origin.up(i);
      if (Feature.isSoil(generator.getWorld(), blockPos)) {

        generator.replace(blockPos, this.provider2.get(generator.getRandom(), origin));

        if (generator.isAir(blockPos.up(1)) && generator.getRandom().nextInt(50) == 0) {
          generator.replace(blockPos.up(1), NSMiscBlocks.SHIITAKE_MUSHROOM.getDefaultState());
        }
        break;
      }

      if (!generator.isAir(blockPos) && i < 0) {
        break;
      }
    }

  }

  static {
    CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
        BlockStateProvider.TYPE_CODEC.fieldOf("inner_block").forGetter((treeDecorator) -> treeDecorator.provider),
        BlockStateProvider.TYPE_CODEC.fieldOf("outer_block").forGetter((treeDecorator) -> treeDecorator.provider2)
    ).apply(instance, MapleGroundTreeDecorator::new));
  }
}
