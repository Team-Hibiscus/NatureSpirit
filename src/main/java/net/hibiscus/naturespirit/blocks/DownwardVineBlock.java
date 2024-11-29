package net.hibiscus.naturespirit.blocks;

import com.mojang.serialization.MapCodec;
import java.util.function.Supplier;
import net.minecraft.block.AbstractPlantStemBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.VineLogic;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.WorldView;


public class DownwardVineBlock extends AbstractPlantStemBlock {

  protected static final VoxelShape SHAPE = Block.createCuboidShape(4D, 9D, 4D, 12D, 16D, 12D);

  private final Supplier<Block> vinesPlantBlock;

  public DownwardVineBlock(Settings properties, Supplier<Block> vinesPlantBlock) {
    super(properties, Direction.DOWN, SHAPE, false, 0.1D);
    this.vinesPlantBlock = vinesPlantBlock;
  }

  @Override
  protected MapCodec<? extends AbstractPlantStemBlock> getCodec() {
    return null;
  }

  @Override
  protected int getGrowthLength(Random randomSource) {
    return VineLogic.getGrowthLength(randomSource);
  }

  @Override
  public Block getPlant() {
    return vinesPlantBlock.get();
  }

  @Override
  public boolean chooseStemState(BlockState state) {
    return state.isAir();
  }

  @Override
  public boolean canPlaceAt(BlockState state, WorldView levelReader, BlockPos pos) {
    BlockPos blockPos = pos.offset(this.growthDirection.getOpposite());
    BlockState blockState = levelReader.getBlockState(blockPos);
    if (!this.canAttachTo(blockState)) {
      return false;
    } else {
      return blockState.isOf(this.getStem()) || blockState.isOf(this.getPlant()) || blockState.isSideSolidFullSquare(levelReader,
          blockPos,
          this.growthDirection
      ) || blockState.isIn(BlockTags.LEAVES);
    }
  }
}
