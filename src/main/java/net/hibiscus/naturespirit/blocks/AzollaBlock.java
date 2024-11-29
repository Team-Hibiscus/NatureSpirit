package net.hibiscus.naturespirit.blocks;


import net.hibiscus.naturespirit.registration.NSTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.EntityShapeContext;
import net.minecraft.block.FlowerbedBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class AzollaBlock extends FlowerbedBlock {

  public static final VoxelShape COLLISION_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 1, 16.0);

  public AzollaBlock(Settings settings) {
    super(settings);
    this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(FLOWER_AMOUNT, 1));
  }

  @Override
  public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
    if (!state.canPlaceAt(world, pos)) {
      world.breakBlock(pos, true);
    }
  }

  @Override
  protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
    FluidState fluidStateUp = world.getFluidState(pos.up());
    return fluidStateUp.isEmpty() && (super.canPlantOnTop(floor, world, pos) || floor.isOf(Blocks.FARMLAND) || isWater(world, pos));
  }

  @Override
  public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    if (context instanceof EntityShapeContext) {
      Entity entity = ((EntityShapeContext) context).getEntity();
      if (entity != null && entity.getType().isIn(NSTags.EntityTypes.IMPERMEABLE_TO_AZOLLA)) {
        return COLLISION_SHAPE;
      }
    }
    return VoxelShapes.empty();
  }

  @Override
  public boolean canReplace(BlockState state, ItemPlacementContext useContext) {
    return true;
  }

  private boolean isWater(BlockView world, BlockPos pos) {
    return world.getFluidState(pos).isEqualAndStill(Fluids.WATER);
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    super.appendProperties(builder);
  }
}
