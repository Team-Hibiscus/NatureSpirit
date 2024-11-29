package net.hibiscus.naturespirit.blocks;

import com.mojang.serialization.MapCodec;
import net.hibiscus.naturespirit.registration.NSTags;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CoralParentBlock;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.SideShapeType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class SucculentBlock extends CoralParentBlock implements Fertilizable {

  public static final MapCodec<SucculentBlock> CODEC = SucculentBlock.createCodec(SucculentBlock::new);
  private static final VoxelShape SHAPE = Block.createCuboidShape(2D, 0D, 2D, 14D, 4D, 14D);

  public MapCodec<? extends SucculentBlock> getCodec() {
    return CODEC;
  }

  public SucculentBlock(AbstractBlock.Settings settings) {
    super(settings);
    this.setDefaultState(this.stateManager.getDefaultState().with(WATERLOGGED, false));
  }

  @Override
  public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    return SHAPE;
  }

  @Override
  public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
    if (!(entity instanceof LivingEntity) || entity.isSneaking() || entity.getType().isIn(NSTags.EntityTypes.CANT_SUCCULENT_SLOWED)) {
      return;
    }
    entity.slowMovement(state, new Vec3d(0.8D, 0.75D, 0.8D));
  }

  @Override
  public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
    BlockPos blockPos = pos.down();
    return world.getBlockState(blockPos).isSideSolid(world, blockPos, Direction.UP, SideShapeType.CENTER) || world.getBlockState(blockPos)
        .isIn(NSTags.Blocks.SUCCULENT_VERTICAL_PLACEMENT_OVERRIDE);
  }

  @Override
  public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
    return true;
  }

  @Override
  public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
    return true;
  }

  @Override
  public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
    dropStack(world, pos, new ItemStack(this));
  }
}

