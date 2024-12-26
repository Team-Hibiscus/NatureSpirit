package net.hibiscus.naturespirit.registration.sets;

import java.util.ArrayList;
import java.util.List;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.hibiscus.naturespirit.registration.NSRegistryHelper;

import static net.hibiscus.naturespirit.registration.NSRegistryHelper.StoneHashMap;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.WallBlock;

import static net.minecraft.data.family.BlockFamilies.register;

import net.minecraft.data.family.BlockFamily;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.util.Identifier;

public class StoneSet {


  private Block cobbled;
  private BlockFamily cobbledFamily;
  private Block cobbledStairs;
  private Block cobbledSlab;
  private Block cobbledWall;
  private Block mossyCobbled;
  private BlockFamily mossyCobbledFamily;
  private Block mossyCobbledStairs;
  private Block mossyCobbledSlab;
  private Block mossyCobbledWall;
  private Block base;
  private BlockFamily baseFamily;
  private Block baseStairs;
  private Block baseSlab;
  private Block polished;
  private BlockFamily polishedFamily;
  private Block polishedStairs;
  private Block polishedSlab;
  private Block polishedWall;
  private Block tiles;
  private BlockFamily tileFamily;
  private Block tilesStairs;
  private Block tilesSlab;
  private Block tilesWall;
  private Block bricks;
  private BlockFamily brickFamily;
  private Block bricksStairs;
  private Block bricksSlab;
  private Block bricksWall;
  private Block chiseled;
  private Block crackedBricks;
  private Block crackedTiles;
  private Block mossyBricks;
  private BlockFamily mossyBrickFamily;
  private Block mossyBricksStairs;
  private Block mossyBricksSlab;
  private Block mossyBricksWall;
  private final List<Block> registeredBlocksList = new ArrayList<>();

  private final Identifier name;
  private final MapColor mapColor;
  private final Item itemBefore;
  private final Item item2Before;
  private final boolean hasTiles;
  private final boolean hasCobbled;
  private final boolean hasCracked;
  private final boolean hasMossy;
  private boolean isRotatable;
  private final float hardness;


  private void registerStone() {

    base = isRotatable ? createRotatable(getName(), Blocks.ANDESITE) : createBasic(getName(), Blocks.ANDESITE);
    baseStairs = createStairs(getName(), base);
    baseSlab = createSlab(getName(), base);
    chiseled = createBasic("chiseled_" + getName(), Blocks.CHISELED_STONE_BRICKS);

    if (hasCobbled()) {
      cobbled = createBasic("cobbled_" + getName(), Blocks.COBBLESTONE);
      cobbledStairs = createStairs("cobbled_" + getName(), cobbled);
      cobbledSlab = createSlab("cobbled_" + getName(), cobbled);
      cobbledWall = createWall("cobbled_" + getName(), cobbled);
      cobbledFamily = register(cobbled).slab(cobbledSlab).stairs(cobbledStairs).chiseled(chiseled).wall(cobbledWall).build();
    }
    if (hasMossy() && hasCobbled()) {
      mossyCobbled = createBasic("mossy_cobbled_" + getName(), Blocks.MOSSY_COBBLESTONE);
      mossyCobbledStairs = createStairs("mossy_cobbled_" + getName(), mossyCobbled);
      mossyCobbledSlab = createSlab("mossy_cobbled_" + getName(), mossyCobbled);
      mossyCobbledWall = createWall("mossy_cobbled_" + getName(), mossyCobbled);
      mossyCobbledFamily = register(mossyCobbled).slab(mossyCobbledSlab).stairs(mossyCobbledStairs).wall(mossyCobbledWall).build();
    }

    polished = createBasic("polished_" + getName(), Blocks.POLISHED_ANDESITE);
    polishedStairs = createStairs("polished_" + getName(), polished);
    polishedSlab = createSlab("polished_" + getName(), polished);
    polishedWall = createWall("polished_" + getName(), polished);
    polishedFamily = register(polished).slab(polishedSlab).stairs(polishedStairs).wall(polishedWall).build();

    bricks = createBasic(getName() + "_bricks", Blocks.STONE_BRICKS);
    bricksStairs = createStairs(getName() + "_brick", bricks);
    bricksSlab = createSlab(getName() + "_brick", bricks);
    bricksWall = createWall(getName() + "_brick", bricks);

    if (hasMossy()) {
      mossyBricks = createBasic("mossy_" + getName() + "_bricks", Blocks.MOSSY_STONE_BRICKS);
      mossyBricksStairs = createStairs("mossy_" + getName() + "_brick", bricks);
      mossyBricksSlab = createSlab("mossy_" + getName() + "_brick", bricks);
      mossyBricksWall = createWall("mossy_" + getName() + "_brick", bricks);
      mossyBrickFamily = register(mossyBricks).slab(mossyBricksSlab).stairs(mossyBricksStairs).wall(mossyBricksWall).build();
    }
    if (hasCracked()) {
      crackedBricks = createBasic("cracked_" + getName() + "_bricks", Blocks.CRACKED_STONE_BRICKS);
      brickFamily = register(bricks).slab(bricksSlab).stairs(bricksStairs).wall(bricksWall).cracked(crackedBricks).build();
    } else {
      brickFamily = register(bricks).slab(bricksSlab).stairs(bricksStairs).wall(bricksWall).build();
    }
    if (hasTiles()) {
      tiles = createBasic(getName() + "_tiles", Blocks.COBBLESTONE);
      tilesStairs = createStairs(getName() + "_tile", polished);
      tilesSlab = createSlab(getName() + "_tile", polished);
      tilesWall = createWall(getName() + "_tile", polished);
      if (hasCracked()) {
        crackedTiles = createBasic("cracked_" + getName() + "_tiles", Blocks.CRACKED_STONE_BRICKS);
        tileFamily = register(tiles).slab(tilesSlab).stairs(tilesStairs).wall(tilesWall).cracked(crackedTiles).build();
      } else {
        tileFamily = register(tiles).slab(tilesSlab).stairs(tilesStairs).wall(tilesWall).build();
      }
    }

    if (hasCobbled()) {
      baseFamily = register(base).slab(baseSlab).stairs(baseStairs).polished(polished).build();
    } else {
      baseFamily = register(base).slab(baseSlab).stairs(baseStairs).polished(polished).chiseled(chiseled).build();
    }
    addToBuildingTab(itemBefore, item2Before, this);
    StoneHashMap.put(getName(), this);
  }

  public StoneSet(Identifier name, MapColor mapColor, Item itemBefore, Item item2Before, float hardness, boolean hasCobbled, boolean hasCracked, boolean hasMossy,
      boolean hasTiles) {
    this.name = name;
    this.mapColor = mapColor;
    this.itemBefore = itemBefore;
    this.item2Before = item2Before;
    this.hardness = hardness;
    this.hasTiles = hasTiles;
    this.hasCobbled = hasCobbled;
    this.hasCracked = hasCracked;
    this.hasMossy = hasMossy;
    registerStone();
  }

  public StoneSet(Identifier name, MapColor mapColor, Item itemBefore, Item item2Before, float hardness, boolean hasCobbled, boolean hasCracked, boolean hasMossy, boolean hasTiles,
      boolean isRotatable) {
    this.name = name;
    this.mapColor = mapColor;
    this.itemBefore = itemBefore;
    this.item2Before = item2Before;
    this.hardness = hardness;
    this.hasTiles = hasTiles;
    this.hasCobbled = hasCobbled;
    this.hasCracked = hasCracked;
    this.hasMossy = hasMossy;
    this.isRotatable = isRotatable;
    registerStone();
  }

  private Block createBlockWithItem(String blockID, Block block) {
    Block listBlock = NSRegistryHelper.registerBlock(blockID, block);
    registeredBlocksList.add(listBlock);
    return listBlock;
  }

  public String getName() {
    return name.getPath();
  }

  public List<Block> getRegisteredBlocksList() {
    return registeredBlocksList;
  }

  private Block createBasic(String name, Block template) {
    return createBlockWithItem(name, new Block(AbstractBlock.Settings.copy(template).hardness(hardness).mapColor(getMapColor())));
  }

  private Block createRotatable(String name, Block template) {
    return createBlockWithItem(name, new PillarBlock(AbstractBlock.Settings.copy(template).hardness(hardness).mapColor(getMapColor())));
  }

  private Block createStairs(String name, Block template) {
    return createBlockWithItem(name + "_stairs", new StairsBlock(template.getDefaultState(), AbstractBlock.Settings.copy(template)));
  }

  private Block createSlab(String name, Block template) {
    return createBlockWithItem(name + "_slab", new SlabBlock(AbstractBlock.Settings.copy(template)));
  }

  private Block createWall(String name, Block template) {
    return createBlockWithItem(name + "_wall", new WallBlock(AbstractBlock.Settings.copy(template).solid()));
  }

  public boolean hasTiles() {
    return hasTiles;
  }

  public boolean hasCracked() {
    return hasCracked;
  }

  public boolean hasCobbled() {
    return hasCobbled;
  }

  public boolean hasMossy() {
    return hasMossy;
  }

  public boolean isRotatable() {
    return isRotatable;
  }

  public static void addToBuildingTab(Item proceedingItem, Item naturalStonePlacement, StoneSet stoneSet) {
    ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
      entries.addAfter(proceedingItem, stoneSet.getBase(), stoneSet.getBaseStairs(), stoneSet.getBaseSlab(),
          stoneSet.getChiseled(), stoneSet.getPolished(), stoneSet.getPolishedStairs(), stoneSet.getPolishedSlab(), stoneSet.getPolishedWall(), stoneSet.getBricks(),
          stoneSet.getBricksStairs(), stoneSet.getBricksSlab(), stoneSet.getBricksWall());
      if (stoneSet.hasCobbled()) {
        entries.addAfter(stoneSet.getBaseSlab(), stoneSet.getCobbled(), stoneSet.getCobbledStairs(), stoneSet.getCobbledSlab(), stoneSet.getCobbledWall());
        if (stoneSet.hasMossy()) {
          entries.addAfter(stoneSet.getCobbledWall(), stoneSet.getMossyCobbled(), stoneSet.getMossyCobbledStairs(), stoneSet.getMossyCobbledSlab(), stoneSet.getMossyCobbledWall());
        }
      }
      if (stoneSet.hasCracked()) {
        entries.addAfter(stoneSet.getBricks(), stoneSet.getCrackedBricks());
      }
      if (stoneSet.hasTiles()) {
        entries.addAfter(stoneSet.getBricksWall(), stoneSet.getTiles(), stoneSet.getTilesStairs(), stoneSet.getTilesSlab(), stoneSet.getTilesWall());
        if (stoneSet.hasCracked()) {
          entries.addAfter(stoneSet.getTiles(), stoneSet.getCrackedTiles());
        }
      }
      if (stoneSet.hasMossy()) {
        entries.addAfter(stoneSet.getBricksWall(), stoneSet.getMossyBricks(), stoneSet.getMossyBricksStairs(), stoneSet.getMossyBricksSlab(), stoneSet.getMossyBricksWall());
      }
    });
    ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> entries.addAfter(naturalStonePlacement, stoneSet.getBase()));
  }

  public Block getCobbled() {
    return cobbled;
  }

  public Block getCobbledStairs() {
    return cobbledStairs;
  }

  public Block getCobbledSlab() {
    return cobbledSlab;
  }

  public Block getCobbledWall() {
    return cobbledWall;
  }

  public Block getBase() {
    return base;
  }

  public Block getBaseStairs() {
    return baseStairs;
  }

  public Block getBaseSlab() {
    return baseSlab;
  }

  public Block getPolished() {
    return polished;
  }

  public Block getPolishedStairs() {
    return polishedStairs;
  }

  public Block getPolishedSlab() {
    return polishedSlab;
  }

  public Block getPolishedWall() {
    return polishedWall;
  }

  public Block getTiles() {
    return tiles;
  }

  public Block getTilesStairs() {
    return tilesStairs;
  }

  public Block getTilesSlab() {
    return tilesSlab;
  }

  public Block getTilesWall() {
    return tilesWall;
  }

  public Block getBricks() {
    return bricks;
  }

  public Block getBricksStairs() {
    return bricksStairs;
  }

  public Block getBricksSlab() {
    return bricksSlab;
  }

  public Block getBricksWall() {
    return bricksWall;
  }

  public Block getChiseled() {
    return chiseled;
  }

  public Block getCrackedBricks() {
    return crackedBricks;
  }

  public Block getCrackedTiles() {
    return crackedTiles;
  }

  public Block getMossyBricks() {
    return mossyBricks;
  }

  public Block getMossyBricksStairs() {
    return mossyBricksStairs;
  }

  public Block getMossyBricksSlab() {
    return mossyBricksSlab;
  }

  public Block getMossyBricksWall() {
    return mossyBricksWall;
  }

  public Block getMossyCobbled() {
    return mossyCobbled;
  }

  public Block getMossyCobbledStairs() {
    return mossyCobbledStairs;
  }

  public Block getMossyCobbledSlab() {
    return mossyCobbledSlab;
  }

  public Block getMossyCobbledWall() {
    return mossyCobbledWall;
  }

  public MapColor getMapColor() {
    return mapColor;
  }

  public BlockFamily getMossyBrickFamily() {
    return mossyBrickFamily;
  }

  public BlockFamily getBrickFamily() {
    return brickFamily;
  }

  public BlockFamily getTileFamily() {
    return tileFamily;
  }

  public BlockFamily getPolishedFamily() {
    return polishedFamily;
  }

  public BlockFamily getBaseFamily() {
    return baseFamily;
  }

  public BlockFamily getCobbledFamily() {
    return cobbledFamily;
  }

  public BlockFamily getMossyCobbledFamily() {
    return mossyCobbledFamily;
  }
}
