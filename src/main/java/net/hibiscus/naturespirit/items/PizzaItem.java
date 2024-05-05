package net.hibiscus.naturespirit.items;

import net.hibiscus.naturespirit.NatureSpirit;
import net.hibiscus.naturespirit.blocks.PizzaBlock;
import net.hibiscus.naturespirit.registration.block_registration.HibiscusMiscBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BlockStateComponent;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class PizzaItem extends AliasedBlockItem {
   public PizzaItem(Block block, Settings settings) {
      super(block, settings);
   }


   public void addBitesToPizza(ItemStack pizza) {
      BlockStateComponent blockStateComponent = pizza.getOrDefault(DataComponentTypes.BLOCK_STATE, BlockStateComponent.DEFAULT);
      NbtComponent blockEntityComponent = pizza.get(DataComponentTypes.BLOCK_ENTITY_DATA);
      int pizzaSlice = this.asItem() == HibiscusMiscBlocks.WHOLE_PIZZA ? 0 : this.asItem() == HibiscusMiscBlocks.THREE_QUARTERS_PIZZA ? 1 : this.asItem() == HibiscusMiscBlocks.HALF_PIZZA ? 2 : 3;
      pizza.set(DataComponentTypes.BLOCK_STATE, blockStateComponent.with(PizzaBlock.BITES, pizzaSlice));
      if (blockEntityComponent != null) {
         blockEntityComponent.apply(nbtCompound -> nbtCompound.putInt("pizza_bites", pizzaSlice));
      } else {
         NbtCompound nbtCompound = new NbtCompound();
         nbtCompound.putInt("pizza_bites", pizzaSlice);
         nbtCompound.putString("id", "natures_spirit:pizza_block_entity");
         pizza.set(DataComponentTypes.BLOCK_ENTITY_DATA, NbtComponent.of(nbtCompound));
      }
   }

   public void getAllToppings(ItemStack pizza) {
      NbtComponent nbtComponent = pizza.get(DataComponentTypes.BLOCK_ENTITY_DATA);
      assert nbtComponent != null;
      nbtComponent.apply(nbtCompound -> {
         NbtList nbtList = ((NbtList) nbtCompound.get("topping_types"));
         if (nbtList != null) nbtCompound.putInt("toppings_number", nbtList.size());
      });
   }

   @Override public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
      addBitesToPizza(stack);
      getAllToppings(stack);
   }

   public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
      super.appendTooltip(stack, context, tooltip, type);


      NbtComponent nbtComponent = stack.get(DataComponentTypes.BLOCK_ENTITY_DATA);
      if (nbtComponent != null) {
         nbtComponent.apply(nbtCompound -> {
            NbtList nbtList = (NbtList) nbtCompound.get("topping_types");
            if (nbtList != null) {
               int j = nbtList.size();
               for(int i = 0; i < j; ++i) {
                  tooltip.add(Text.translatable("block.natures_spirit.pizza." + nbtList.getString(i).replace(":", ".")).formatted(Formatting.GRAY));
               }
            }
         });
      }
   }

   public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
      ItemStack itemStack = super.finishUsing(stack, world, user);
      Item pizzaSlice = this.asItem() == HibiscusMiscBlocks.WHOLE_PIZZA ? HibiscusMiscBlocks.THREE_QUARTERS_PIZZA : this.asItem() == HibiscusMiscBlocks.THREE_QUARTERS_PIZZA ? HibiscusMiscBlocks.HALF_PIZZA : this.asItem() == HibiscusMiscBlocks.HALF_PIZZA ? HibiscusMiscBlocks.QUARTER_PIZZA : Items.AIR;

      PlayerEntity holder = (PlayerEntity) user;
      holder.incrementStat(NatureSpirit.EAT_PIZZA_SLICE);
      NbtComponent nbtComponent = stack.get(DataComponentTypes.BLOCK_ENTITY_DATA);
      if (nbtComponent != null) {
         nbtComponent.apply(nbtCompound -> {
            NbtList nbtList = (NbtList) nbtCompound.get("topping_types");
            int foodAmount = 2;
            float saturationModifier = 0.2F;
            if (nbtList != null) {
               int j = nbtList.size();
               for(int i = 0; i < j; i++) {
                  foodAmount++;
                  saturationModifier = saturationModifier + 0.1F;
               }
            }
            holder.getHungerManager().add(foodAmount, saturationModifier);
         });
      }
      if(((PlayerEntity) user).getAbilities().creativeMode) {
         return itemStack;
      }
      else {
         ItemStack itemStack1 = new ItemStack(pizzaSlice, 1);
         itemStack1.copyComponentsToNewStack(stack.getItem(), 1);
         return itemStack1;
      }
   }
}
