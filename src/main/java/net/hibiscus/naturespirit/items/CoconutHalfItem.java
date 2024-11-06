package net.hibiscus.naturespirit.items;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class CoconutHalfItem extends Item {

  private final Item LeftOverItem;

  public CoconutHalfItem(Item.Settings settings, Item item) {
    super(settings);
    LeftOverItem = item;
  }

  @Override
  public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
    super.finishUsing(stack, world, user);
    if (user instanceof ServerPlayerEntity serverPlayerEntity) {
      Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
      serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
    }

    if (!world.isClient) {
      user.clearStatusEffects();
    }

    if (stack.isEmpty()) {
      return new ItemStack(LeftOverItem);
    } else {
      if (user instanceof PlayerEntity playerEntity && !playerEntity.getAbilities().creativeMode) {
        ItemStack itemStack = new ItemStack(LeftOverItem);
        if (!playerEntity.getInventory().insertStack(itemStack)) {
          playerEntity.dropItem(itemStack, false);
        }
      }

      return stack;
    }
  }

  @Override
  public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
    return ItemUsage.consumeHeldItem(world, user, hand);
  }
}
