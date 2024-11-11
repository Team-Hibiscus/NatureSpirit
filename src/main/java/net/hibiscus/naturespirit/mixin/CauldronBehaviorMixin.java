package net.hibiscus.naturespirit.mixin;

import static net.hibiscus.naturespirit.util.NSCauldronBehavior.FILL_WITH_CHEESE;
import static net.hibiscus.naturespirit.util.NSCauldronBehavior.FILL_WITH_MILK;

import java.util.Map;
import net.hibiscus.naturespirit.registration.NSMiscBlocks;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CauldronBehavior.class)
public interface CauldronBehaviorMixin {
  @Inject(method = "registerBucketBehavior", at = @At("HEAD"))
  private static void registerNSBucketBehavior(Map<Item, CauldronBehavior> behavior, CallbackInfo ci) {
    behavior.put(NSMiscBlocks.CHEESE_BUCKET, FILL_WITH_CHEESE);
    behavior.put(Items.MILK_BUCKET, FILL_WITH_MILK);
  }
}
