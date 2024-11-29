package net.hibiscus.naturespirit.blocks;


import net.hibiscus.naturespirit.registration.NSTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShortPlantBlock;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class LargeDesertFernBlock extends ShortPlantBlock {

  protected static final VoxelShape SHAPE = Block.createCuboidShape(2D, 0D, 2D, 14D, 16D, 14D);
  private final TallPlantBlock tallBlock;

  public LargeDesertFernBlock(Settings properties, TallPlantBlock tallBlock) {
    super(properties);
    this.tallBlock = tallBlock;
  }

  @Override
  protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
    return floor.isIn(NSTags.Blocks.TURNIP_STEM_GROWS_ON) || floor.isOf(Blocks.FARMLAND);
  }

  @Override
  public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
    TallPlantBlock tallPlantBlock = tallBlock;
    if (tallPlantBlock.getDefaultState().canPlaceAt(world, pos) && world.isAir(pos.up())) {
      TallPlantBlock.placeAt(world, tallPlantBlock.getDefaultState(), pos, 2);
    }

  }
}
