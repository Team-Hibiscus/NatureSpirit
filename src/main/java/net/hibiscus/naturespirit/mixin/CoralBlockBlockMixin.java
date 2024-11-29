package net.hibiscus.naturespirit.mixin;

import java.util.Optional;
import net.hibiscus.naturespirit.config.NSConfig;
import net.hibiscus.naturespirit.registration.NSMiscBlocks;
import net.minecraft.block.AmethystClusterBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CoralBlockBlock;
import net.minecraft.fluid.Fluids;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(CoralBlockBlock.class)
@Debug(export = true)
public abstract class CoralBlockBlockMixin extends Block {

  public CoralBlockBlockMixin(Settings settings) {
    super(settings);
  }

  @Unique
  private static Optional<BlockPos> findColumnEnd(
      BlockView world,
      BlockPos pos,
      TagKey<Block> intermediateBlocks,
      Direction direction,
      Block endBlock,
      int searchLimit
  ) {
    BlockPos.Mutable mutable = pos.mutableCopy();

    BlockState blockState;
    int i = 0;
    do {
      mutable.move(direction);
      blockState = world.getBlockState(mutable);
      ++i;
    } while (blockState.isIn(intermediateBlocks) && i < searchLimit);

    return blockState.isOf(endBlock) ? Optional.of(mutable) : Optional.empty();
  }

  @Override
  public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
    if (NSConfig.calcite_generator) {
      if (findColumnEnd(world, pos, BlockTags.CORAL_BLOCKS, Direction.DOWN, Blocks.BUBBLE_COLUMN, 10).isPresent()) {
        for (Direction direction : Direction.Type.HORIZONTAL) {
          if (random.nextInt(25) == 0) {
            if (world.getBlockState(pos.offset(direction, 1)).isOf(Blocks.WATER)) {
              world.setBlockState(pos.offset(direction, 1),
                  NSMiscBlocks.SMALL_CALCITE_BUD.getDefaultState().with(AmethystClusterBlock.FACING, direction).with(AmethystClusterBlock.WATERLOGGED, true),
                  Block.NOTIFY_LISTENERS
              );
            } else if (world.getBlockState(pos.offset(direction, 1)).isOf(NSMiscBlocks.SMALL_CALCITE_BUD)) {
              world.setBlockState(pos.offset(direction, 1),
                  NSMiscBlocks.LARGE_CALCITE_BUD.getDefaultState().with(AmethystClusterBlock.FACING, direction)
                      .with(AmethystClusterBlock.WATERLOGGED, world.getBlockState(pos.offset(direction,
                          1
                      )).getFluidState().isOf(Fluids.WATER)),
                  Block.NOTIFY_LISTENERS
              );
            } else if (world.getBlockState(pos.offset(direction, 1)).isOf(NSMiscBlocks.LARGE_CALCITE_BUD)) {
              world.setBlockState(pos.offset(direction, 1),
                  NSMiscBlocks.CALCITE_CLUSTER.getDefaultState().with(AmethystClusterBlock.FACING, direction)
                      .with(AmethystClusterBlock.WATERLOGGED, world.getBlockState(pos.offset(direction,
                          1
                      )).getFluidState().isOf(Fluids.WATER)),
                  Block.NOTIFY_LISTENERS
              );
            }
          }
        }
      }
    }

  }

  @Override
  public boolean hasRandomTicks(BlockState state) {
    return true;
  }
}
