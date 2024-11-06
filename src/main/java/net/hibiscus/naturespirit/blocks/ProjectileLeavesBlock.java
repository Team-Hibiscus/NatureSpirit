package net.hibiscus.naturespirit.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;

public class ProjectileLeavesBlock extends LeavesBlock {

  private final Block END_BLOCK;

  public ProjectileLeavesBlock(Settings settings, Block block) {
    super(settings);
    this.END_BLOCK = block;
  }

  @Override
  public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
    if (projectile instanceof SnowballEntity) {
      Entity entity = projectile.getOwner();
      if (entity instanceof ServerPlayerEntity serverPlayerEntity) {
        serverPlayerEntity.incrementStat(Stats.TARGET_HIT);
      }
      world.setBlockState(hit.getBlockPos(), this.END_BLOCK.getStateWithProperties(state));
    }
  }
}
