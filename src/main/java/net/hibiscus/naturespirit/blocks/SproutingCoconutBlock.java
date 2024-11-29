package net.hibiscus.naturespirit.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SaplingGenerator;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class SproutingCoconutBlock extends SaplingBlock {

  private static final VoxelShape OUTLINE_SHAPE = Block.createCuboidShape(3D, 0D, 3D, 13D, 10D, 13D);

  public SproutingCoconutBlock(SaplingGenerator generator, Settings settings) {
    super(generator, settings);
  }

  @Override
  protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
    return floor.isSideSolidFullSquare(world, pos, Direction.DOWN);
  }

  @Override
  public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    return OUTLINE_SHAPE;
  }
}
