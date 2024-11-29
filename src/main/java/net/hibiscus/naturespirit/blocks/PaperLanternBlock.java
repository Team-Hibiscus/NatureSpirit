package net.hibiscus.naturespirit.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LanternBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;

public class PaperLanternBlock extends LanternBlock {

  protected static final VoxelShape STANDING_SHAPE = VoxelShapes.union(
      Block.createCuboidShape(5D, 0D, 5D, 11D, 14D, 11D),
      Block.createCuboidShape(2D, 2D, 2D, 14D, 12D, 14D)
  );

  public PaperLanternBlock(Settings settings) {
    super(settings);
  }

  @Override
  public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    return STANDING_SHAPE;
  }

  @Override
  public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
    Direction direction = LanternBlock.attachedDirection(state).getOpposite();
    return Block.sideCoversSmallSquare(world, pos.offset(direction), direction.getOpposite()) || world.getBlockState(pos.offset(direction)).isIn(BlockTags.LEAVES);
  }
}
