package net.hibiscus.naturespirit.blocks;

import java.util.Optional;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.LeavesBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockLocating;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.NotNull;

public class VinesLeavesBlock extends LeavesBlock implements Fertilizable {

  public Block vinePlantBlock;
  public Block vineTipBlock;

  public VinesLeavesBlock(Settings properties, Block vinePlantBlockInput, Block vineTipBlockInput) {
    super(properties);
    vinePlantBlock = vinePlantBlockInput;
    vineTipBlock = vineTipBlockInput;
  }

  @Override
  public boolean isFertilizable(@NotNull WorldView levelReader, @NotNull BlockPos blockPos, BlockState state) {
    Optional<BlockPos> optional = BlockLocating.findColumnEnd(levelReader, blockPos, vinePlantBlock, Direction.DOWN, vineTipBlock);
    return (optional.isPresent() && levelReader.getBlockState(optional.get().offset(Direction.DOWN)).isAir()) || levelReader.getBlockState(blockPos.offset(Direction.DOWN)).isAir();
  }

  @Override
  public boolean canGrow(World level, Random randomSource, BlockPos blockPos, BlockState blockState) {
    return true;
  }

  @Override
  public void grow(ServerWorld serverLevel, Random randomSource, BlockPos blockPos, BlockState blockState) {
    Optional<BlockPos> optional = BlockLocating.findColumnEnd(serverLevel, blockPos, vinePlantBlock, Direction.DOWN, vineTipBlock);
    if (optional.isPresent()) {
      BlockState blockState2 = serverLevel.getBlockState(optional.get());
      ((DownwardVineBlock) blockState2.getBlock()).grow(serverLevel, randomSource, optional.get(), blockState2);
    }
    if (optional.isEmpty()) {
      serverLevel.setBlockState(blockPos.down(), vineTipBlock.getDefaultState(), Block.NOTIFY_LISTENERS);
    }
  }
}
