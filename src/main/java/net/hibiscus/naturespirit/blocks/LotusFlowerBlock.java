package net.hibiscus.naturespirit.blocks;

import com.mojang.serialization.MapCodec;
import net.hibiscus.naturespirit.registration.NSMiscBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.SideShapeType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
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
import org.jetbrains.annotations.Nullable;

public class LotusFlowerBlock extends PlantBlock implements Fertilizable {

  protected static final VoxelShape SHAPE = Block.createCuboidShape(2D, 0D, 2D, 14D, 1.5D, 14D);

  public LotusFlowerBlock(Settings properties) {
    super(properties);
  }

  @Override
  protected MapCodec<? extends PlantBlock> getCodec() {
    return null;
  }

  @Override
  public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
    super.onEntityCollision(state, world, pos, entity);

    if (!world.isClient) {
      if (entity instanceof BoatEntity) {
        world.breakBlock(new BlockPos(pos), true, entity);
      }
      BlockState blockstate = world.getBlockState(pos.down());
      if (blockstate.isOf(NSMiscBlocks.LOTUS_STEM) && isEntityAbove(pos, entity) && !isPowered(world, pos)) {
        world.scheduleBlockTick(pos, this, 4);
      }
    }

  }


  private int getRedstonePower(WorldView world, BlockPos pos) {
    BlockPos.Mutable mutable = pos.mutableCopy();

    BlockState blockState;
    do {
      mutable.move(Direction.DOWN);
      blockState = world.getBlockState(mutable);
      if (world.isReceivingRedstonePower(mutable)) {
        return world.getReceivedRedstonePower(mutable);
      }
    } while (blockState.isOf(NSMiscBlocks.LOTUS_STEM));

    return 0;
  }


  public boolean isPowered(WorldView world, BlockPos pos) {
    return getRedstonePower(world, pos) > 0;
  }

  @Override
  public boolean hasComparatorOutput(BlockState state) {
    return true;
  }

  @Override
  public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
    return getRedstonePower(world, pos);
  }

  @Override
  public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
    super.scheduledTick(state, world, pos, random);

    BlockState blockstate = world.getBlockState(pos.down());
    if (blockstate.isOf(NSMiscBlocks.LOTUS_STEM) && isEntityAbove(pos, world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 1.25D, false)) && !isPowered(world, pos)) {
      world.breakBlock(pos, false);
      world.setBlockState(pos.down(), this.getDefaultState());
    }
  }

  private static boolean isEntityAbove(BlockPos pos, @Nullable Entity entity) {
    if (entity == null) {
      return false;
    }
    return entity.isOnGround() && entity.getPos().y > (double) ((float) pos.getY()) && entity.isSneaking();
  }

  @Override
  public ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state) {
    return new ItemStack(NSMiscBlocks.LOTUS_FLOWER_ITEM);
  }

  @Override
  public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    return SHAPE;
  }

  @Override
  protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
    FluidState fluidState = world.getFluidState(pos);
    FluidState fluidState2 = world.getFluidState(pos.up());
    return ((fluidState.getFluid() == Fluids.WATER || floor.isSideSolid(world, pos, Direction.UP, SideShapeType.CENTER)) && fluidState2.getFluid() == Fluids.EMPTY) || floor.isOf(
        NSMiscBlocks.LOTUS_STEM);
  }

  @Override
  public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
    return world.getBlockState(pos.up()).isAir() && !world.getBlockState(pos.down()).isOf(Blocks.WATER) && !isPowered(world, pos);
  }

  @Override
  public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
    return true;
  }

  @Override
  public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
    PlayerEntity player = world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 1.25D, false);
    if (player != null) {
      player.move(MovementType.SHULKER_BOX, new Vec3d(0D, 1.01D, 0D));
    }

    if (world.getBlockState(pos.down()).isOf(NSMiscBlocks.LOTUS_STEM)) {
      LotusStemBlock lotusStemBlock = (LotusStemBlock) world.getBlockState(pos.down()).getBlock();
      lotusStemBlock.grow(world, random, pos.down(), world.getBlockState(pos.down()));
    } else {
      world.setBlockState(pos, NSMiscBlocks.LOTUS_STEM.getDefaultState().with(LotusStemBlock.WATERLOGGED, false));
      world.setBlockState(pos.up(), this.getDefaultState());
    }

  }
}
