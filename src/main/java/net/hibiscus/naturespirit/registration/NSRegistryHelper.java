package net.hibiscus.naturespirit.registration;

import java.util.HashMap;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.hibiscus.naturespirit.NatureSpirit;
import net.hibiscus.naturespirit.config.NSConfig;
import net.hibiscus.naturespirit.registration.sets.FlowerSet;
import net.hibiscus.naturespirit.registration.sets.StoneSet;
import net.hibiscus.naturespirit.registration.sets.WoodSet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.TallBlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class NSRegistryHelper {

  public static HashMap<String, FlowerSet> FlowerHashMap = new HashMap<>();
  public static HashMap<String, WoodSet> WoodHashMap = new HashMap<>();
  public static HashMap<String, StoneSet> StoneHashMap = new HashMap<>();
  public static HashMap<String, Block[]> SaplingHashMap = new HashMap<>();
  public static HashMap<String, Block> LeavesHashMap = new HashMap<>();
  public static HashMap<String, Block> RenderLayerHashMap = new HashMap<>();
  public static HashMap<String, Item> NatureSpiritItemHashMap = new HashMap<>();


  public static Boolean never(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
    return false;
  }

  public static <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(String name, BlockEntityType.Builder<T> factory) {
    return Registry.register(
        Registries.BLOCK_ENTITY_TYPE,
        Identifier.of(NatureSpirit.MOD_ID, name),
        factory.build()
    );
  }


  public static Block registerBlockWithoutTab(String name, Block block) {
    return Registry.register(Registries.BLOCK, Identifier.of(NatureSpirit.MOD_ID, name), block);
  }

  public static Block registerBlock(String name, Block block) {
    registerItem(name, new BlockItem(block, new Item.Settings()));
    return registerBlockWithoutTab(name, block);
  }

  public static Block registerBlock(String name, Block block, ItemConvertible blockBefore) {
    Block block1 = registerBlockWithoutTab(name, block);
    registerItemWithoutTab(name, new BlockItem(block, new Item.Settings()));
    if (NSConfig.creative_tab) {
      ItemGroupEvents.modifyEntriesEvent(NSItemGroups.NS_ITEM_GROUP).register(entries -> entries.addAfter(blockBefore, block1.asItem()));
    }
    return block1;
  }

  public static Block registerBlock(String name, Block block, ItemConvertible blockBefore, RegistryKey<ItemGroup> secondaryTab) {
    Block block1 = registerBlock(name, block);
    ItemGroupEvents.modifyEntriesEvent(secondaryTab).register(entries -> entries.addAfter(blockBefore, block1.asItem()));
    return block1;
  }

  public static Block registerBlock(String name, Block block, ItemConvertible blockBefore, RegistryKey<ItemGroup> secondaryTab, ItemConvertible blockBefore2,
      RegistryKey<ItemGroup> thirdTab) {
    Block block1 = registerBlock(name, block, blockBefore, secondaryTab);
    ItemGroupEvents.modifyEntriesEvent(thirdTab).register(entries -> entries.addAfter(blockBefore2, block1.asItem()));
    return block1;
  }


  public static Block registerTransparentBlockWithoutTab(String name, Block block) {
    Block block1 = registerBlockWithoutTab(name, block);
    RenderLayerHashMap.put(name, block1);
    return block1;
  }

  public static Block registerTransparentBlock(String name, Block block) {
    Block block1 = registerBlock(name, block);
    RenderLayerHashMap.put(name, block1);
    return block1;
  }

  public static Block registerTransparentBlock(String name, Block block, ItemConvertible itemBefore, RegistryKey<ItemGroup> secondaryTab) {
    Block block1 = registerBlock(name, block, itemBefore, secondaryTab);
    RenderLayerHashMap.put(name, block1);
    return block1;
  }

  public static Block registerTransparentBlock(String name, Block block, ItemConvertible itemBefore, RegistryKey<ItemGroup> secondaryTab, ItemConvertible blockBefore2,
      RegistryKey<ItemGroup> thirdTab) {
    Block block1 = registerBlock(name, block, itemBefore, secondaryTab, blockBefore2, thirdTab);
    RenderLayerHashMap.put(name, block1);
    return block1;
  }


  public static Block registerPlantBlock(String name, Block block, ItemConvertible itemBefore, float compost) {
    Block plant = registerTransparentBlock(name, block, itemBefore, ItemGroups.NATURAL);
    CompostingChanceRegistry.INSTANCE.add(block, compost);
    return plant;
  }


  public static Item registerItemWithoutTab(String name, Item item) {
    Item item1 = Registry.register(Registries.ITEM, Identifier.of(NatureSpirit.MOD_ID, name), item);
    NatureSpiritItemHashMap.put(name, item1);
    return item1;
  }

  public static Item registerItem(String name, Item item) {
    Item item1 = Registry.register(Registries.ITEM, Identifier.of(NatureSpirit.MOD_ID, name), item);
    if (NSConfig.creative_tab) {
      ItemGroupEvents.modifyEntriesEvent(NSItemGroups.NS_ITEM_GROUP).register(entries -> entries.add(item1));
    }
    NatureSpiritItemHashMap.put(name, item1);
    return item1;
  }

  public static Item registerItem(String name, Item item, ItemConvertible itemBefore, RegistryKey<ItemGroup> secondaryTab) {
    ItemGroupEvents.modifyEntriesEvent(secondaryTab).register(entries -> entries.addAfter(itemBefore, item));
    return registerItem(name, item);
  }


  public static Item registerPlantItem(String name, Item item, ItemConvertible itemBefore, RegistryKey<ItemGroup> secondaryTab, float compost) {
    CompostingChanceRegistry.INSTANCE.add(item, compost);
    return registerItem(name, item, itemBefore, secondaryTab);
  }

  public static Block registerTallPlantBlock(String name, Block block, ItemConvertible itemBefore, float compost) {
    Block plant = registerBlockWithoutTab(name, block);
    registerItem(name, new TallBlockItem(block, new Item.Settings()));
    ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> entries.addAfter(itemBefore, plant.asItem()));
    RenderLayerHashMap.put(name, plant);
    CompostingChanceRegistry.INSTANCE.add(block, compost);
    return plant;
  }
}
