package net.hibiscus.naturespirit.items;

import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.ItemStack;

public class DesertTurnipItem extends AliasedBlockItem {

  public DesertTurnipItem(Block block, Settings settings) {
    super(block, settings);
  }

  @Override
  public int getMaxUseTime(ItemStack stack, LivingEntity user) {
    return 24;
  }
}
