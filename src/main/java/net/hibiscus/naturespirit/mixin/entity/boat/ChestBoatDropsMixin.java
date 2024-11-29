package net.hibiscus.naturespirit.mixin.entity.boat;

import net.hibiscus.naturespirit.registration.NSBoatTypes;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.entity.vehicle.ChestBoatEntity;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChestBoatEntity.class)
public class ChestBoatDropsMixin {

  //CREDIT TO nyuppo/fabric-boat-example ON GITHUB

  @Inject(method = "asItem", at = @At("RETURN"), cancellable = true)
  public void getNaturesSpiritBoats(CallbackInfoReturnable<Item> info) {
    NSBoatTypes.getChestBoatItem(BoatEntity.class.cast(this).getVariant()).ifPresent(info::setReturnValue);
  }

}
