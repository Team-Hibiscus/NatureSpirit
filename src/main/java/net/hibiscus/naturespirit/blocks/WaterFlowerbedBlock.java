package net.hibiscus.naturespirit.blocks;

import com.mojang.serialization.MapCodec;
import net.hibiscus.naturespirit.registration.NSMiscBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.IceBlock;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class WaterFlowerbedBlock extends PlantBlock implements Fertilizable {

  public static final VoxelShape COLLISION_SHAPE = Block.createCuboidShape(0D, 0D, 0D, 16D, 1.5, 16D);
  public static final VoxelShape OUTLINE_SHAPE = Block.createCuboidShape(0D, 0D, 0D, 16D, 3D, 16D);
  public static final IntProperty FLOWER_AMOUNT = IntProperty.of("water_flower_amount", 0, 4);
  public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

  public WaterFlowerbedBlock(Settings settings) {
    super(settings);
    this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(FLOWER_AMOUNT, 0));
  }

  @Override
  protected MapCodec<? extends PlantBlock> getCodec() {
    return null;
  }

  @Override
  public BlockState rotate(BlockState state, BlockRotation rotation) {
    return state.with(FACING, rotation.rotate(state.get(FACING)));
  }

  @Override
  public BlockState mirror(BlockState state, BlockMirror mirror) {
    return state.rotate(mirror.getRotation(state.get(FACING)));
  }

  @Override
  public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    return COLLISION_SHAPE;
  }

  @Override
  public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    return OUTLINE_SHAPE;
  }

  @Override
  public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
    Hand hand = player.getActiveHand();
    boolean bl = player.getStackInHand(hand).isOf(NSMiscBlocks.HELVOLA_FLOWER_ITEM) && state.get(FLOWER_AMOUNT) < 4;
    if (bl) {
      world.setBlockState(pos, state.with(FLOWER_AMOUNT, Math.min(4, state.get(FLOWER_AMOUNT) + 1)));
      if (!player.isCreative() && !player.isSpectator()) {
        player.getStackInHand(hand).decrement(1);
      }
    }
    return bl ? ActionResult.SUCCESS : super.onUse(state, world, pos, player, hit);
  }

  @Override
  public BlockState getPlacementState(ItemPlacementContext ctx) {
    return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
  }

  @Override
  protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
    FluidState fluidState = world.getFluidState(pos);
    FluidState fluidState2 = world.getFluidState(pos.up());
    return (fluidState.getFluid() == Fluids.WATER || floor.getBlock() instanceof IceBlock) && fluidState2.getFluid() == Fluids.EMPTY;
  }

  @Override
  public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
    super.onEntityCollision(state, world, pos, entity);
    if (world instanceof ServerWorld && entity instanceof BoatEntity) {
      world.breakBlock(new BlockPos(pos), true, entity);
    }
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    builder.add(FACING, FLOWER_AMOUNT);
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
    int i = state.get(FLOWER_AMOUNT);
    if (i < 4) {
      world.setBlockState(pos, state.with(FLOWER_AMOUNT, i + 1), Block.NOTIFY_LISTENERS);
    } else {
      dropStack(world, pos, new ItemStack(this));
    }
  }
}
