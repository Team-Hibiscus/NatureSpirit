package net.hibiscus.naturespirit.registration.sets;

import java.util.ArrayList;
import java.util.List;
import net.hibiscus.naturespirit.blocks.LargeFlowerBlock;
import net.hibiscus.naturespirit.blocks.MidFlowerBlock;
import net.hibiscus.naturespirit.registration.NSRegistryHelper;

import static net.hibiscus.naturespirit.registration.NSRegistryHelper.*;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.TallFlowerBlock;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.BlockSoundGroup;

public class FlowerSet {

  private final String name;
  private final Item dyeColor;
  private final RegistryEntry<StatusEffect> statusEffect;
  private final Item itemBefore;
  private final FlowerPreset preset;
  private Block flowerBlock;
  private Block pottedFlowerBlock;
  private final List<Block> registeredBlocksList = new ArrayList<>();
  private final List<Item> registeredItemsList = new ArrayList<>();

  public FlowerSet(String name, Item dyeColor, RegistryEntry<StatusEffect> statusEffect, Item itemBefore, FlowerPreset preset) {
    this.name = name;
    this.dyeColor = dyeColor;
    this.statusEffect = statusEffect;
    this.itemBefore = itemBefore;
    this.preset = preset;
    this.registerFlower();
  }

  public FlowerSet(String name, Item dyeColor, Item itemBefore, FlowerPreset preset) {
    this.name = name;
    this.dyeColor = dyeColor;
    this.statusEffect = null;
    this.itemBefore = itemBefore;
    this.preset = preset;
    this.registerFlower();
  }

  public FlowerSet(String name, RegistryEntry<StatusEffect> statusEffect, Item itemBefore, FlowerPreset preset) {
    this.name = name;
    this.dyeColor = null;
    this.statusEffect = statusEffect;
    this.itemBefore = itemBefore;
    this.preset = preset;
    this.registerFlower();
  }

  public FlowerSet(String name, Item itemBefore, FlowerPreset preset) {
    this.name = name;
    this.dyeColor = null;
    this.statusEffect = null;
    this.itemBefore = itemBefore;
    this.preset = preset;
    this.registerFlower();
  }

  private void registerFlower() {
    if (isTall()) {
      flowerBlock = registerTallPlantBlock(name,
          new TallFlowerBlock(AbstractBlock.Settings
              .create()
              .noCollision()
              .breakInstantly()
              .sounds(BlockSoundGroup.GRASS)
              .burnable()
              .offset(AbstractBlock.OffsetType.XZ)
              .pistonBehavior(PistonBehavior.DESTROY)),
          itemBefore,
          0.4f
      );

    } else if (preset == FlowerPreset.BIG_SMALL) {
      flowerBlock = registerPlantBlock(name, new LargeFlowerBlock(
          statusEffect,
          7,
          AbstractBlock.Settings
              .create()
              .mapColor(MapColor.DARK_GREEN)
              .noCollision()
              .breakInstantly()
              .sounds(BlockSoundGroup.GRASS)
              .offset(AbstractBlock.OffsetType.XZ)
              .pistonBehavior(PistonBehavior.DESTROY)
      ), itemBefore, 0.4f);
    } else if (preset == FlowerPreset.SMALL) {
      flowerBlock = registerPlantBlock(name, new FlowerBlock(
          statusEffect,
          7,
          AbstractBlock.Settings
              .create()
              .mapColor(MapColor.DARK_GREEN)
              .noCollision()
              .breakInstantly()
              .sounds(BlockSoundGroup.GRASS)
              .offset(AbstractBlock.OffsetType.XZ)
              .pistonBehavior(PistonBehavior.DESTROY)
      ), itemBefore, 0.3f);
    } else if (preset == FlowerPreset.MID_SMALL) {
      flowerBlock = registerPlantBlock(name, new MidFlowerBlock(
          statusEffect,
          7,
          AbstractBlock.Settings
              .create()
              .mapColor(MapColor.DARK_GREEN)
              .noCollision()
              .breakInstantly()
              .sounds(BlockSoundGroup.GRASS)
              .offset(AbstractBlock.OffsetType.XZ)
              .pistonBehavior(PistonBehavior.DESTROY)
      ), itemBefore, 0.3f);
    }
    if (hasFlowerPot()) {
      pottedFlowerBlock = registerTransparentBlockWithoutTab("potted_" + name,
          new FlowerPotBlock(flowerBlock, AbstractBlock.Settings.create().breakInstantly().nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));
      registeredBlocksList.add(pottedFlowerBlock);
    }
    registeredBlocksList.add(flowerBlock);
    NSRegistryHelper.FlowerHashMap.put(name, this);
  }

  public int getDyeNumber() {
    switch (preset) {
      case TALL, BIG_SMALL -> {
        return 2;
      }
      case BIG_TALL -> {
        return 4;
      }
      default -> {
        return 1;
      }
    }
  }

  public List<Block> getRegisteredBlocksList() {
    return registeredBlocksList;
  }

  public List<Item> getRegisteredItemsList() {
    return registeredItemsList;
  }

  public FlowerPreset getPreset() {
    return preset;
  }

  public boolean hasFlowerPot() {
    return getPreset() == FlowerPreset.SMALL || getPreset() == FlowerPreset.BIG_SMALL || getPreset() == FlowerPreset.MID_SMALL;
  }

  public boolean isTall() {
    return getPreset() == FlowerPreset.TALL || getPreset() == FlowerPreset.BIG_TALL;
  }

  public Item getDyeColor() {
    return dyeColor;
  }

  public Block getFlowerBlock() {
    return flowerBlock;
  }

  public Block getPottedFlowerBlock() {
    return pottedFlowerBlock;
  }

  public String getName() {
    return name;
  }

  public enum FlowerPreset {
    SMALL, MID_SMALL,
    TALL,
    BIG_SMALL,
    BIG_TALL
  }
}
