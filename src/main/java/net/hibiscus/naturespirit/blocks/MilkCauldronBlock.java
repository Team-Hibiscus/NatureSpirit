package net.hibiscus.naturespirit.blocks;

import com.mojang.serialization.MapCodec;
import net.hibiscus.naturespirit.registration.NSMiscBlocks;
import net.hibiscus.naturespirit.registration.NSParticleTypes;
import net.hibiscus.naturespirit.registration.NSTags;
import net.hibiscus.naturespirit.util.NSCauldronBehavior;
import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.NotNull;

public class MilkCauldronBlock extends AbstractCauldronBlock {

  public final BooleanProperty AGE_INTO_CHEESE = BooleanProperty.of("age_into_cheese");

  public MilkCauldronBlock(Settings settings) {
    super(settings, NSCauldronBehavior.MILK_CAULDRON_BEHAVIOR);
    this.setDefaultState(this.stateManager.getDefaultState().with(AGE_INTO_CHEESE, false));
  }

  @Override
  protected MapCodec<? extends AbstractCauldronBlock> getCodec() {
    return null;
  }

  @Override
  protected double getFluidHeight(BlockState state) {
    return 0.9375D;
  }

  @Override
  public boolean hasRandomTicks(@NotNull BlockState state) {
    return state.get(AGE_INTO_CHEESE);
  }

  @Override
  public ActionResult onUse(BlockState state, World world, BlockPos pos, @NotNull PlayerEntity player, BlockHitResult hit) {
    if (player.getStackInHand(player.getActiveHand()).isIn(NSTags.Items.CHEESE_MAKER) && !state.get(AGE_INTO_CHEESE)) {
      world.setBlockState(pos, state.with(AGE_INTO_CHEESE, true), Block.NOTIFY_LISTENERS);
      BlockState blockState = world.getBlockState(pos);
      world.playSound(null, pos, SoundEvents.BLOCK_BUBBLE_COLUMN_BUBBLE_POP, SoundCategory.BLOCKS, 1F, 1F);
      double d = blockState.getOutlineShape(world, pos).getEndingCoord(Direction.Axis.Y, 0.5D, 0.5D) + 0.03125D;
      Random random = world.getRandom();

      for (int i = 0; i < 10; ++i) {
        double g = random.nextGaussian() * 0.02D;
        double h = random.nextGaussian() * 0.02D;
        double j = random.nextGaussian() * 0.02D;
        world.addParticle(
            NSParticleTypes.MILK_PARTICLE,
            (double) pos.getX() + 0.13124999403953552D + 0.737500011920929D * random.nextDouble(),
            (double) pos.getY() + d + 1D + random.nextDouble() * (1D - d),
            (double) pos.getZ() + 0.13124999403953552D + 0.737500011920929D * random.nextDouble(),
            g,
            h,
            j
        );
      }
      if (!player.isCreative() && !player.isSpectator()) {
        ItemStack itemStack = player.getStackInHand(player.getActiveHand()).getRecipeRemainder();
        player.getStackInHand(player.getActiveHand()).decrement(1);
        if (player.getStackInHand(player.getActiveHand()).isEmpty()) {
          player.setStackInHand(player.getActiveHand(), itemStack);
        } else {
          if (player.getInventory().insertStack(itemStack)) {
            player.dropItem(itemStack, false);
          }
        }
      }
      return ActionResult.SUCCESS;
    }
    return super.onUse(state, world, pos, player, hit);
  }

  @Override
  public void randomTick(BlockState state, ServerWorld world, BlockPos pos, @NotNull Random random) {
    if (random.nextInt(25) == 0) {
      world.setBlockState(pos, NSMiscBlocks.CHEESE_CAULDRON.getDefaultState(), Block.NOTIFY_LISTENERS);
    }
    super.randomTick(state, world, pos, random);
  }

  @Override
  public ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state) {
    return new ItemStack(Blocks.CAULDRON);
  }

  @Override
  public boolean isFull(BlockState state) {
    return true;
  }

  @Override
  public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
    return 3;
  }

  @Override
  protected void appendProperties(StateManager.@NotNull Builder<Block, BlockState> builder) {
    builder.add(AGE_INTO_CHEESE);
  }
}
