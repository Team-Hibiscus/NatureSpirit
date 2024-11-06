package net.hibiscus.naturespirit.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.BlockPileFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class LargePumpkinFeature extends Feature<BlockPileFeatureConfig> {

  public LargePumpkinFeature(Codec<BlockPileFeatureConfig> configCodec) {
    super(configCodec);
  }

  @Override
  public boolean generate(FeatureContext<BlockPileFeatureConfig> context) {
    StructureWorldAccess worldAccess = context.getWorld();
    BlockPileFeatureConfig blockPileFeatureConfig = context.getConfig();
    BlockPos origin = context.getOrigin();
    Direction direction = Direction.Type.HORIZONTAL.random(worldAccess.getRandom());
    Direction direction2 = direction.rotateYClockwise();
    BlockState blockState = blockPileFeatureConfig.stateProvider.get(context.getRandom(), origin);
    if (worldAccess.isAir(origin.up()) && worldAccess.isAir(origin.offset(direction).up()) && worldAccess.isAir(origin.offset(direction2).up()) && worldAccess.isAir(
        origin.offset(direction2).offset(direction).up())) {

      worldAccess.setBlockState(origin, blockState, 1);
      worldAccess.setBlockState(origin.offset(direction), blockState, 1);
      worldAccess.setBlockState(origin.offset(direction2), blockState, 1);
      worldAccess.setBlockState(origin.offset(direction2).offset(direction), blockState, 1);

      if (worldAccess.isAir(origin.down()) || worldAccess.getBlockState(origin.down()).isIn(BlockTags.DIRT)) {
        worldAccess.setBlockState(origin.down(), blockState, 1);
        worldAccess.setBlockState(origin.down().offset(direction), blockState, 1);
        worldAccess.setBlockState(origin.down().offset(direction2), blockState, 1);
        worldAccess.setBlockState(origin.down().offset(direction2).offset(direction), blockState, 1);
      }

      if (context.getRandom().nextBoolean()) {
        worldAccess.setBlockState(origin.up(), blockState, 1);
      }
      if (context.getRandom().nextBoolean()) {
        worldAccess.setBlockState(origin.offset(direction).up(), blockState, 1);
      }
      if (context.getRandom().nextBoolean()) {
        worldAccess.setBlockState(origin.offset(direction2).up(), blockState, 1);
      }
      if (context.getRandom().nextBoolean()) {
        worldAccess.setBlockState(origin.offset(direction2).offset(direction).up(), blockState, 1);
      }

      return true;
    }
    return false;
  }
}
