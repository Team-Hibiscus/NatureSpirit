package net.hibiscus.naturespirit.mixin.entity;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
  @Inject(method = "canTarget(Lnet/minecraft/entity/LivingEntity;)Z", at = @At("HEAD"), cancellable = true)
  private void target(LivingEntity target, CallbackInfoReturnable<Boolean> cir) {
    if (((LivingEntity)(Object)this).getEquippedStack(EquipmentSlot.HEAD).isOf(Items.DIAMOND_HELMET))
      cir.setReturnValue(false);
  }
  @Inject(method = "shouldDropLoot", at = @At("HEAD"), cancellable = true)
  private void loot(CallbackInfoReturnable<Boolean> cir) {
    if (((LivingEntity)(Object)this).getEquippedStack(EquipmentSlot.HEAD).isOf(Items.DIAMOND_HELMET))
      cir.setReturnValue(false);
  }
}
