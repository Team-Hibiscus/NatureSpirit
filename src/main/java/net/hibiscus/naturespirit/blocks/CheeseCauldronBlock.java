package net.hibiscus.naturespirit.blocks;

import com.mojang.serialization.MapCodec;
import net.hibiscus.naturespirit.util.NSCauldronBehavior;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class CheeseCauldronBlock extends AbstractCauldronBlock {

  public CheeseCauldronBlock(AbstractBlock.Settings settings) {
    super(settings, NSCauldronBehavior.CHEESE_CAULDRON_BEHAVIOR);
  }

  @Override
  protected MapCodec<? extends AbstractCauldronBlock> getCodec() {
    return null;
  }

  @Override
  public boolean isFull(BlockState state) {
    return true;
  }

  @Override
  public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    return VoxelShapes.fullCube();
  }

  @Override
  public ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state) {
    return new ItemStack(Blocks.CAULDRON);
  }

  @Override
  public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
    return 4;
  }
}
