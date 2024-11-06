package net.hibiscus.naturespirit.items;

import net.hibiscus.naturespirit.entity.CheeseArrowEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CheeseArrowItem extends ArrowItem {

  public CheeseArrowItem(Item.Settings settings) {
    super(settings);
  }

  @Override
  public PersistentProjectileEntity createArrow(World world, ItemStack stack, LivingEntity shooter, @Nullable ItemStack shotFrom) {
    return new CheeseArrowEntity(world, shooter, stack, shotFrom);
  }
}
