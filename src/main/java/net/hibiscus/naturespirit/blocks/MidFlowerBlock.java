package net.hibiscus.naturespirit.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class MidFlowerBlock extends FlowerBlock {

  protected static final VoxelShape SHAPE = Block.createCuboidShape(5D, 0D, 5D, 11D, 16D, 11D);

  public MidFlowerBlock(RegistryEntry<StatusEffect> mobEffect, int i, Settings properties) {
    super(mobEffect, i, properties);
  }

  @Override
  public VoxelShape getOutlineShape(BlockState state, BlockView level, BlockPos pos, ShapeContext context) {
    Vec3d vec3 = state.getModelOffset(level, pos);
    return SHAPE.offset(vec3.x, vec3.y, vec3.z);
  }
}
