package net.hibiscus.naturespirit.mixin;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Debug(export = true)
@Mixin(AxeItem.class)
public class AxeItemMixin {

  @Inject(method = "useOnBlock", at = @At("HEAD"))
  private void isValid(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
    ActionResult result = UseBlockCallback.EVENT.invoker().interact(context.getPlayer(),
        context.getWorld(),
        context.getHand(),
        new BlockHitResult(context.getHitPos(), context.getSide(), context.getBlockPos(), context.hitsInsideBlock())
    );

    if (result == ActionResult.SUCCESS) {
      cir.cancel();
    }
  }
}
