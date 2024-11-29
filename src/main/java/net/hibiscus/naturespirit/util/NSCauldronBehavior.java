package net.hibiscus.naturespirit.util;

import static net.minecraft.block.cauldron.CauldronBehavior.CauldronBehaviorMap;
import static net.minecraft.block.cauldron.CauldronBehavior.createMap;
import static net.minecraft.block.cauldron.CauldronBehavior.emptyCauldron;
import static net.minecraft.block.cauldron.CauldronBehavior.fillCauldron;
import static net.minecraft.block.cauldron.CauldronBehavior.registerBucketBehavior;

import net.hibiscus.naturespirit.registration.NSMiscBlocks;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;

public interface NSCauldronBehavior {

  CauldronBehaviorMap MILK_CAULDRON_BEHAVIOR = createMap("milk");
  CauldronBehavior FILL_WITH_MILK = (state, world, pos, player, hand, stack) -> fillCauldron(
      world,
      pos,
      player,
      hand,
      stack,
      NSMiscBlocks.MILK_CAULDRON.getDefaultState(),
      SoundEvents.ITEM_BUCKET_EMPTY
  );
  CauldronBehaviorMap CHEESE_CAULDRON_BEHAVIOR = createMap("cheese");
  CauldronBehavior FILL_WITH_CHEESE = (state, world, pos, player, hand, stack) -> fillCauldron(
      world,
      pos,
      player,
      hand,
      stack,
      NSMiscBlocks.CHEESE_CAULDRON.getDefaultState(),
      SoundEvents.ITEM_BUCKET_EMPTY
  );

  static void registerBehavior() {
    MILK_CAULDRON_BEHAVIOR.map().put(
        Items.BUCKET,
        (state, world, pos, player, hand, stack) -> emptyCauldron(state,
            world,
            pos,
            player,
            hand,
            stack,
            new ItemStack(Items.MILK_BUCKET),
            (statex) -> true,
            SoundEvents.ENTITY_COW_MILK
        )
    );
    registerBucketBehavior(MILK_CAULDRON_BEHAVIOR.map());
    CHEESE_CAULDRON_BEHAVIOR.map().put(
        Items.BUCKET,
        (state, world, pos, player, hand, stack) -> emptyCauldron(state,
            world,
            pos,
            player,
            hand,
            stack,
            new ItemStack(NSMiscBlocks.CHEESE_BUCKET),
            (statex) -> true,
            SoundEvents.ITEM_BUCKET_FILL
        )
    );
    registerBucketBehavior(CHEESE_CAULDRON_BEHAVIOR.map());
  }


}
