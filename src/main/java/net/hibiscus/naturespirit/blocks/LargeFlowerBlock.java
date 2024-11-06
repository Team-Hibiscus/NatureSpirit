package net.hibiscus.naturespirit.blocks;


import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class LargeFlowerBlock extends FlowerBlock {

  protected static final VoxelShape SHAPE = Block.createCuboidShape(2D, 0D, 2D, 14D, 16D, 14D);

  @Override
  public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    return SHAPE;
  }

  public LargeFlowerBlock(RegistryEntry<StatusEffect> mobEffect, int i, Settings properties) {
    super(mobEffect, i, properties);
  }
}
