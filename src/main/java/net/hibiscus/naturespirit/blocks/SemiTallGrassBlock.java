package net.hibiscus.naturespirit.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class SemiTallGrassBlock extends TallPlantBlock {

  private static final VoxelShape LOWER_SHAPE = Block.createCuboidShape(2D, 0D, 2D, 14D, 16D, 14D);
  private static final VoxelShape UPPER_SHAPE = Block.createCuboidShape(2D, 0D, 2D, 14D, 8D, 14D);

  public SemiTallGrassBlock(Settings settings) {
    super(settings);
  }

  @Override
  public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    return state.get(HALF) == DoubleBlockHalf.UPPER ? UPPER_SHAPE : LOWER_SHAPE;
  }
}
