package net.hibiscus.naturespirit.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.hibiscus.naturespirit.registration.NSMiscBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BrushItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BrushItem.class)
public class BrushItemMixin {

  @Unique
  public long nextDustTime = 0;

  @Inject(method = "usageTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getBlockEntity(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/entity/BlockEntity;"))
  private void injectCalciteClusterBrushing(World world, LivingEntity user, ItemStack stack, int remainingUseTicks, CallbackInfo ci, @Local BlockState blockState, @Local BlockPos blockPos, @Local BlockHitResult blockHitResult, @Local
      PlayerEntity playerEntity) {
    if (blockState.isOf(NSMiscBlocks.SMALL_CALCITE_BUD) || blockState.isOf(NSMiscBlocks.LARGE_CALCITE_BUD) || blockState.isOf(NSMiscBlocks.CALCITE_CLUSTER)) {

      if (world.getTime() > nextDustTime) {
        nextDustTime = world.getTime() + 20L;
        ItemEntity itemEntity = getChalkPowder(world, blockPos);
        itemEntity.setVelocity(Vec3d.ZERO);
        world.spawnEntity(itemEntity);
        if (world.getRandom().nextFloat() < .3) {
          if (blockState.isOf(NSMiscBlocks.SMALL_CALCITE_BUD)) world.setBlockState(blockPos, blockState.getFluidState().getBlockState());
          if (blockState.isOf(NSMiscBlocks.LARGE_CALCITE_BUD)) world.setBlockState(blockPos, NSMiscBlocks.SMALL_CALCITE_BUD.getStateWithProperties(blockState));
          if (blockState.isOf(NSMiscBlocks.CALCITE_CLUSTER)) world.setBlockState(blockPos, NSMiscBlocks.LARGE_CALCITE_BUD.getStateWithProperties(blockState));
          EquipmentSlot equipmentSlot = stack.equals(playerEntity.getEquippedStack(EquipmentSlot.OFFHAND)) ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND;
          stack.damage(1, user, equipmentSlot);
        }
      }
    }
  }

  @Unique
  private ItemEntity getChalkPowder(World world, BlockPos blockPos) {
    double d = EntityType.ITEM.getWidth();
    double e = 1.0 - d;
    double f = d / 2.0;
    double g = (double) blockPos.getX() + 0.5 * e + f;
    double h = (double) blockPos.getY() + 0.25 + (double)(EntityType.ITEM.getHeight() / 2.0F);
    double i = (double) blockPos.getZ() + 0.5 * e + f;
    return new ItemEntity(world, g, h, i, new ItemStack(NSMiscBlocks.CHALK_POWDER, world.getRandom().nextBetween(1, 3)));
  }

  @Inject(method = "useOnBlock", at = @At("HEAD"))
  private void useOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
    nextDustTime = context.getWorld().getTime() + 10L;
  }

}
