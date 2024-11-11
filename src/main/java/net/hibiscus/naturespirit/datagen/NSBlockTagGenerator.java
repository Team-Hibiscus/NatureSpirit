package net.hibiscus.naturespirit.datagen;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

import static net.hibiscus.naturespirit.registration.NSMiscBlocks.*;

import net.hibiscus.naturespirit.registration.NSRegistryHelper;
import net.hibiscus.naturespirit.registration.NSTags;
import net.hibiscus.naturespirit.registration.NSTags.Blocks;
import net.hibiscus.naturespirit.registration.NSWoods;
import net.hibiscus.naturespirit.registration.sets.FlowerSet;
import net.hibiscus.naturespirit.registration.sets.StoneSet;
import net.hibiscus.naturespirit.registration.sets.WoodSet;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

public class NSBlockTagGenerator extends FabricTagProvider.BlockTagProvider {

  public NSBlockTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
    super(output, registriesFuture);
  }

  private void addWoodTags(HashMap<String, WoodSet> woods) {
    for (WoodSet woodSet : woods.values()) {

      getOrCreateTagBuilder(BlockTags.PLANKS).add(new Block[]{woodSet.getPlanks()});
      getOrCreateTagBuilder(BlockTags.WOODEN_BUTTONS).add(new Block[]{woodSet.getButton()});
      getOrCreateTagBuilder(BlockTags.WOODEN_DOORS).add(new Block[]{woodSet.getDoor()});
      getOrCreateTagBuilder(BlockTags.WOODEN_STAIRS).add(new Block[]{woodSet.getStairs()});
      getOrCreateTagBuilder(BlockTags.WOODEN_SLABS).add(new Block[]{woodSet.getSlab()});
      getOrCreateTagBuilder(BlockTags.WOODEN_FENCES).add(new Block[]{woodSet.getFence()});
      getOrCreateTagBuilder(woodSet.getBlockLogsTag()).add(woodSet.getStrippedLog(), woodSet.getLog());
      getOrCreateTagBuilder(NSTags.Blocks.STRIPPED_LOGS).add(woodSet.getStrippedLog());
      if (woodSet.hasBark()) {
        getOrCreateTagBuilder(woodSet.getBlockLogsTag()).add(woodSet.getStrippedWood(), woodSet.getWood());
        getOrCreateTagBuilder(NSTags.Blocks.STRIPPED_LOGS).add(woodSet.getStrippedWood());
      }
      if (woodSet.hasMosaic()) {
        getOrCreateTagBuilder(BlockTags.SLABS).add(woodSet.getMosaicSlab());
        getOrCreateTagBuilder(BlockTags.STAIRS).add(woodSet.getMosaicStairs());
        getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).add(woodSet.getMosaic(), woodSet.getMosaicSlab(), woodSet.getMosaicStairs());
      }
      if (woodSet.getWoodPreset() == WoodSet.WoodPreset.WILLOW) {
        getOrCreateTagBuilder(BlockTags.CLIMBABLE).add(
            woodSet.getVinesPlant(),
            woodSet.getVines()
        );
      }
      getOrCreateTagBuilder(BlockTags.OVERWORLD_NATURAL_LOGS).add(new Block[]{woodSet.getLog()});
      getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN).addTag(woodSet.getBlockLogsTag());
      getOrCreateTagBuilder(BlockTags.WOODEN_TRAPDOORS).add(new Block[]{woodSet.getTrapDoor()});
      getOrCreateTagBuilder(BlockTags.STANDING_SIGNS).add(new Block[]{woodSet.getSign()});
      getOrCreateTagBuilder(BlockTags.WALL_SIGNS).add(new Block[]{woodSet.getWallSign()});
      getOrCreateTagBuilder(BlockTags.WOODEN_PRESSURE_PLATES).add(new Block[]{woodSet.getPressurePlate()});
      getOrCreateTagBuilder(BlockTags.FENCE_GATES).add(new Block[]{woodSet.getFenceGate()});
      getOrCreateTagBuilder(BlockTags.CEILING_HANGING_SIGNS).add(woodSet.getHangingSign());
      getOrCreateTagBuilder(BlockTags.WALL_HANGING_SIGNS).add(woodSet.getHangingWallSign());

    }
  }

  private void addStoneTags(HashMap<String, StoneSet> stones) {
    for (StoneSet stoneSet : stones.values()) {

      getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(stoneSet.getRegisteredBlocksList().toArray(new Block[]{}));
      if (stoneSet.hasTiles()) {
        getOrCreateTagBuilder(BlockTags.SLABS).add(stoneSet.getTilesSlab());
        getOrCreateTagBuilder(BlockTags.STAIRS).add(stoneSet.getTilesStairs());
        getOrCreateTagBuilder(BlockTags.WALLS).add(stoneSet.getTilesWall());
      }
      if (stoneSet.hasCobbled()) {
        getOrCreateTagBuilder(BlockTags.SLABS).add(stoneSet.getCobbledSlab());
        getOrCreateTagBuilder(BlockTags.STAIRS).add(stoneSet.getCobbledStairs());
        getOrCreateTagBuilder(BlockTags.WALLS).add(stoneSet.getCobbledWall());
        if (stoneSet.hasMossy()) {
          getOrCreateTagBuilder(BlockTags.SLABS).add(stoneSet.getMossyCobbledSlab());
          getOrCreateTagBuilder(BlockTags.STAIRS).add(stoneSet.getMossyCobbledStairs());
          getOrCreateTagBuilder(BlockTags.WALLS).add(stoneSet.getMossyCobbledWall());
        }
      }
      if (stoneSet.hasMossy()) {
        getOrCreateTagBuilder(BlockTags.SLABS).add(stoneSet.getMossyBricksSlab());
        getOrCreateTagBuilder(BlockTags.STAIRS).add(stoneSet.getMossyBricksStairs());
        getOrCreateTagBuilder(BlockTags.WALLS).add(stoneSet.getMossyBricksWall());
      }
      getOrCreateTagBuilder(BlockTags.SLABS).add(stoneSet.getPolishedSlab());
      getOrCreateTagBuilder(BlockTags.STAIRS).add(stoneSet.getPolishedStairs());
      getOrCreateTagBuilder(BlockTags.WALLS).add(stoneSet.getPolishedWall());
      getOrCreateTagBuilder(BlockTags.SLABS).add(stoneSet.getBricksSlab());
      getOrCreateTagBuilder(BlockTags.STAIRS).add(stoneSet.getBricksStairs());
      getOrCreateTagBuilder(BlockTags.WALLS).add(stoneSet.getBricksWall());
      getOrCreateTagBuilder(BlockTags.SLABS).add(stoneSet.getBaseSlab());
      getOrCreateTagBuilder(BlockTags.STAIRS).add(stoneSet.getBaseStairs());

    }
  }

  private void addTreeTags(HashMap<String, Block[]> saplings, HashMap<String, Block> leaves) {
    for (String i : leaves.keySet()) {
      Block leavesType = leaves.get(i);
      getOrCreateTagBuilder(BlockTags.HOE_MINEABLE).add(leavesType);
      getOrCreateTagBuilder(BlockTags.LEAVES).add(leavesType);
      if (!Objects.equals(i, "wisteria") && !Objects.equals(i, "coconut") && !i.startsWith("part") && !i.startsWith("frosty") && !Objects.equals(i, "yellow_aspen")) {
        Block[] saplingType = saplings.get(i);
        getOrCreateTagBuilder(BlockTags.SAPLINGS).add(new Block[]{saplingType[0]});
        getOrCreateTagBuilder(BlockTags.FLOWER_POTS).add(new Block[]{saplingType[1]});
      }
    }
  }

  @Override
  protected void configure(RegistryWrapper.WrapperLookup arg) {
    addWoodTags(NSRegistryHelper.WoodHashMap);
    addStoneTags(NSRegistryHelper.StoneHashMap);
    addTreeTags(NSRegistryHelper.SaplingHashMap, NSRegistryHelper.LeavesHashMap);
    for (FlowerSet flowerSet : NSRegistryHelper.FlowerHashMap.values()) {
      if (flowerSet.isTall()) {
        getOrCreateTagBuilder(BlockTags.TALL_FLOWERS).add(flowerSet.getFlowerBlock());
      } else {
        getOrCreateTagBuilder(BlockTags.FLOWER_POTS).add(flowerSet.getPottedFlowerBlock());
        getOrCreateTagBuilder(BlockTags.SMALL_FLOWERS).add(flowerSet.getFlowerBlock());
      }
      getOrCreateTagBuilder(BlockTags.SWORD_EFFICIENT).add(flowerSet.getFlowerBlock());
      getOrCreateTagBuilder(BlockTags.REPLACEABLE_BY_TREES).add(flowerSet.getFlowerBlock());
    }
    getOrCreateTagBuilder(BlockTags.REPLACEABLE).add(AZOLLA);
    getOrCreateTagBuilder(BlockTags.TALL_FLOWERS).add(CATTAIL);
    getOrCreateTagBuilder(BlockTags.FLOWERS).add(LOTUS_FLOWER,
        NSWoods.WISTERIA.getBlueVines(),
        NSWoods.WISTERIA.getBlueVinesPlant(),
        NSWoods.WISTERIA.getWhiteVines(),
        NSWoods.WISTERIA.getWhiteVinesPlant(),
        NSWoods.WISTERIA.getPinkVines(),
        NSWoods.WISTERIA.getPinkVinesPlant(),
        NSWoods.WISTERIA.getPurpleVines(),
        NSWoods.WISTERIA.getPurpleVinesPlant(),
        NSWoods.WISTERIA.getBlueLeaves(),
        NSWoods.WISTERIA.getWhiteLeaves(),
        NSWoods.WISTERIA.getPinkLeaves(),
        NSWoods.WISTERIA.getPurpleLeaves()
    );
    getOrCreateTagBuilder(NSTags.Blocks.ALLUAUDIA_BUNDLES).add(STRIPPED_ALLUAUDIA_BUNDLE, ALLUAUDIA_BUNDLE);
    getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN).addTag(NSTags.Blocks.ALLUAUDIA_BUNDLES);
    getOrCreateTagBuilder(BlockTags.WOODEN_DOORS).add(PAPER_DOOR, FRAMED_PAPER_DOOR, BLOOMING_PAPER_DOOR);
    getOrCreateTagBuilder(BlockTags.WOODEN_TRAPDOORS).add(PAPER_TRAPDOOR, FRAMED_PAPER_TRAPDOOR, BLOOMING_PAPER_TRAPDOOR);
    getOrCreateTagBuilder(BlockTags.CLIMBABLE).add(
        NSWoods.WISTERIA.getBlueVines(),
        NSWoods.WISTERIA.getBlueVinesPlant(),
        NSWoods.WISTERIA.getWhiteVines(),
        NSWoods.WISTERIA.getWhiteVinesPlant(),
        NSWoods.WISTERIA.getPinkVines(),
        NSWoods.WISTERIA.getPinkVinesPlant(),
        NSWoods.WISTERIA.getPurpleVines(),
        NSWoods.WISTERIA.getPurpleVinesPlant(),
        NSWoods.WILLOW.getVinesPlant(),
        NSWoods.WILLOW.getVines()
    );
    getOrCreateTagBuilder(BlockTags.BEE_GROWABLES).add(
        NSWoods.WISTERIA.getBlueVines(),
        NSWoods.WISTERIA.getBlueVinesPlant(),
        NSWoods.WISTERIA.getWhiteVines(),
        NSWoods.WISTERIA.getWhiteVinesPlant(),
        NSWoods.WISTERIA.getPinkVines(),
        NSWoods.WISTERIA.getPinkVinesPlant(),
        NSWoods.WISTERIA.getPurpleVines(),
        NSWoods.WISTERIA.getPurpleVinesPlant(),
        LOTUS_FLOWER
    );
    getOrCreateTagBuilder(BlockTags.DEAD_BUSH_MAY_PLACE_ON).forceAddTag(Blocks.KAOLIN);
    getOrCreateTagBuilder(BlockTags.DIRT).add(SANDY_SOIL, CHERT.getBase(), RED_MOSS_BLOCK);
    getOrCreateTagBuilder(BlockTags.OVERWORLD_CARVER_REPLACEABLES).add(PINK_SANDSTONE, RED_MOSS_BLOCK).forceAddTag(Blocks.KAOLIN).forceAddTag(Blocks.KAOLIN);
    getOrCreateTagBuilder(BlockTags.CROPS).add(DESERT_TURNIP_STEM);
    getOrCreateTagBuilder(BlockTags.MAINTAINS_FARMLAND).add(DESERT_TURNIP_STEM);
    getOrCreateTagBuilder(BlockTags.HOE_MINEABLE).add(
        SCORCHED_GRASS,
        TALL_SCORCHED_GRASS,
        BEACH_GRASS,
        TALL_BEACH_GRASS,
        SEDGE_GRASS,
        TALL_SEDGE_GRASS,
        LARGE_FLAXEN_FERN,
        FLAXEN_FERN,
        LARGE_LUSH_FERN,
        LUSH_FERN,
        TALL_MELIC_GRASS,
        MELIC_GRASS,
        FRIGID_GRASS,
        TALL_FRIGID_GRASS,
        OAT_GRASS,
        TALL_OAT_GRASS,
        RED_BEARBERRIES,
        GREEN_BEARBERRIES,
        PURPLE_BEARBERRIES,
        RED_BITTER_SPROUTS,
        PURPLE_BITTER_SPROUTS,
        GREEN_BITTER_SPROUTS,
        NSWoods.COCONUT_THATCH,
        NSWoods.COCONUT_THATCH_STAIRS,
        NSWoods.COCONUT_THATCH_CARPET,
        NSWoods.COCONUT_THATCH_SLAB,
        NSWoods.EVERGREEN_THATCH,
        NSWoods.EVERGREEN_THATCH_STAIRS,
        NSWoods.EVERGREEN_THATCH_CARPET,
        NSWoods.EVERGREEN_THATCH_SLAB,
        NSWoods.XERIC_THATCH,
        NSWoods.XERIC_THATCH_STAIRS,
        NSWoods.XERIC_THATCH_CARPET,
        NSWoods.XERIC_THATCH_SLAB,
        RED_MOSS_BLOCK,
        RED_MOSS_CARPET, ORNATE_SUCCULENT, AUREATE_SUCCULENT, SAGE_SUCCULENT, FOAMY_SUCCULENT, IMPERIAL_SUCCULENT, REGAL_SUCCULENT
    );
    getOrCreateTagBuilder(BlockTags.SWORD_EFFICIENT).add(
        SCORCHED_GRASS,
        TALL_SCORCHED_GRASS,
        BEACH_GRASS,
        TALL_BEACH_GRASS,
        SEDGE_GRASS,
        TALL_SEDGE_GRASS,
        LARGE_FLAXEN_FERN,
        FLAXEN_FERN,
        SHIITAKE_MUSHROOM,
        GRAY_POLYPORE,
        FRIGID_GRASS,
        TALL_FRIGID_GRASS,
        OAT_GRASS,
        TALL_OAT_GRASS,
        LARGE_LUSH_FERN,
        LUSH_FERN,
        TALL_MELIC_GRASS,
        MELIC_GRASS,
        RED_BEARBERRIES,
        GREEN_BEARBERRIES,
        PURPLE_BEARBERRIES,
        RED_BITTER_SPROUTS,
        PURPLE_BITTER_SPROUTS,
        GREEN_BITTER_SPROUTS
    );
    getOrCreateTagBuilder(BlockTags.REPLACEABLE_BY_TREES).add(
        SCORCHED_GRASS,
        TALL_SCORCHED_GRASS,
        BEACH_GRASS,
        TALL_BEACH_GRASS,
        SEDGE_GRASS,
        TALL_SEDGE_GRASS,
        LARGE_FLAXEN_FERN,
        FLAXEN_FERN,
        FRIGID_GRASS,
        TALL_FRIGID_GRASS,
        OAT_GRASS,
        TALL_OAT_GRASS,
        LARGE_LUSH_FERN,
        LUSH_FERN,
        TALL_MELIC_GRASS,
        MELIC_GRASS, AUREATE_SUCCULENT, SAGE_SUCCULENT, FOAMY_SUCCULENT, IMPERIAL_SUCCULENT, REGAL_SUCCULENT
    );
    getOrCreateTagBuilder(BlockTags.SAND).add(PINK_SAND, SANDY_SOIL);
    getOrCreateTagBuilder(BlockTags.SMELTS_TO_GLASS).add(PINK_SAND);
    getOrCreateTagBuilder(BlockTags.SHOVEL_MINEABLE).add(PINK_SAND, SANDY_SOIL);
    getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
        .forceAddTag(NSTags.Blocks.KAOLIN)
        .forceAddTag(NSTags.Blocks.KAOLIN_STAIRS)
        .forceAddTag(NSTags.Blocks.KAOLIN_SLABS)
        .forceAddTag(NSTags.Blocks.KAOLIN_BRICKS)
        .forceAddTag(NSTags.Blocks.KAOLIN_BRICK_STAIRS)
        .forceAddTag(NSTags.Blocks.KAOLIN_BRICK_SLABS)
        .forceAddTag(NSTags.Blocks.CHALK)
        .forceAddTag(NSTags.Blocks.CHALK_STAIRS)
        .forceAddTag(NSTags.Blocks.CHALK_SLABS)
        .add(CHERT_COAL_ORE, CHERT_COPPER_ORE, CHERT_DIAMOND_ORE, CHERT_GOLD_ORE, CHERT_EMERALD_ORE, CHERT_IRON_ORE, CHERT_LAPIS_ORE, CHERT_REDSTONE_ORE, PINK_SANDSTONE,
            SMOOTH_PINK_SANDSTONE, CUT_PINK_SANDSTONE, PINK_SANDSTONE_STAIRS, SMOOTH_PINK_SANDSTONE_STAIRS, PINK_SANDSTONE_SLAB, SMOOTH_PINK_SANDSTONE_SLAB,
            CUT_PINK_SANDSTONE_SLAB, PINK_SANDSTONE_WALL, CHISELED_PINK_SANDSTONE);
    getOrCreateTagBuilder(BlockTags.STAIRS).add(PINK_SANDSTONE_STAIRS, SMOOTH_PINK_SANDSTONE_STAIRS, NSWoods.EVERGREEN_THATCH_STAIRS, NSWoods.COCONUT_THATCH_STAIRS);
    getOrCreateTagBuilder(BlockTags.SLABS).add(PINK_SANDSTONE_SLAB, SMOOTH_PINK_SANDSTONE_SLAB, CUT_PINK_SANDSTONE_SLAB, NSWoods.EVERGREEN_THATCH_SLAB,
        NSWoods.COCONUT_THATCH_SLAB);
    getOrCreateTagBuilder(BlockTags.WALLS).add(PINK_SANDSTONE_WALL);
    getOrCreateTagBuilder(BlockTags.CAULDRONS).add(CHEESE_CAULDRON, MILK_CAULDRON);
    getOrCreateTagBuilder(BlockTags.FLOWER_POTS).add(
        POTTED_MELIC_GRASS,
        POTTED_FLAXEN_FERN,
        POTTED_FRIGID_GRASS,
        POTTED_SHIITAKE_MUSHROOM,
        POTTED_BEACH_GRASS,
        POTTED_SEDGE_GRASS,
        POTTED_SCORCHED_GRASS,
        POTTED_OAT_GRASS,
        POTTED_LUSH_FERN,
        POTTED_ORNATE_SUCCULENT,
        POTTED_DROWSY_SUCCULENT,
        POTTED_AUREATE_SUCCULENT,
        POTTED_SAGE_SUCCULENT,
        POTTED_FOAMY_SUCCULENT,
        POTTED_IMPERIAL_SUCCULENT);
    getOrCreateTagBuilder(BlockTags.ENDERMAN_HOLDABLE).add(SHIITAKE_MUSHROOM);
    getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).add(SHIITAKE_MUSHROOM, SHIITAKE_MUSHROOM_BLOCK, GRAY_POLYPORE, GRAY_POLYPORE_BLOCK, DESERT_TURNIP_BLOCK, DESERT_TURNIP_ROOT_BLOCK,
        DESERT_TURNIP_STEM, PAPER_BLOCK, PAPER_PANEL, PAPER_DOOR, PAPER_SIGN, PAPER_WALL_SIGN, PAPER_HANGING_SIGN, PAPER_WALL_HANGING_SIGN, FRAMED_PAPER_BLOCK, FRAMED_PAPER_PANEL,
        FRAMED_PAPER_DOOR, FRAMED_PAPER_TRAPDOOR, BLOOMING_PAPER_BLOCK, BLOOMING_PAPER_DOOR, BLOOMING_PAPER_TRAPDOOR, BLOOMING_PAPER_PANEL);
    getOrCreateTagBuilder(BlockTags.CEILING_HANGING_SIGNS).add(PAPER_HANGING_SIGN);
    getOrCreateTagBuilder(BlockTags.WALL_HANGING_SIGNS).add(PAPER_WALL_HANGING_SIGN);
    getOrCreateTagBuilder(BlockTags.STANDING_SIGNS).add(new Block[]{PAPER_SIGN});
    getOrCreateTagBuilder(BlockTags.WALL_SIGNS).add(new Block[]{PAPER_WALL_SIGN});
    getOrCreateTagBuilder(BlockTags.COAL_ORES).add(CHERT_COAL_ORE);
    getOrCreateTagBuilder(BlockTags.COPPER_ORES).add(CHERT_COPPER_ORE);
    getOrCreateTagBuilder(BlockTags.DIAMOND_ORES).add(CHERT_DIAMOND_ORE);
    getOrCreateTagBuilder(BlockTags.GOLD_ORES).add(CHERT_GOLD_ORE);
    getOrCreateTagBuilder(BlockTags.EMERALD_ORES).add(CHERT_EMERALD_ORE);
    getOrCreateTagBuilder(BlockTags.IRON_ORES).add(CHERT_IRON_ORE);
    getOrCreateTagBuilder(BlockTags.LAPIS_ORES).add(CHERT_LAPIS_ORE);
    getOrCreateTagBuilder(BlockTags.REDSTONE_ORES).add(CHERT_REDSTONE_ORE);
    getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL).add(CHERT_EMERALD_ORE, CHERT_DIAMOND_ORE, CHERT_GOLD_ORE, CHERT_REDSTONE_ORE);
    getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).add(CHERT_COPPER_ORE, CHERT_IRON_ORE, CHERT_LAPIS_ORE);
    getOrCreateTagBuilder(BlockTags.STONE_ORE_REPLACEABLES).add(TRAVERTINE.getBase());
    getOrCreateTagBuilder(BlockTags.BASE_STONE_OVERWORLD).add(TRAVERTINE.getBase());
    getOrCreateTagBuilder(BlockTags.BASE_STONE_OVERWORLD).add(CHERT.getBase());
    getOrCreateTagBuilder(BlockTags.STAIRS).forceAddTag(NSTags.Blocks.CHALK_STAIRS).forceAddTag(NSTags.Blocks.KAOLIN_STAIRS).forceAddTag(NSTags.Blocks.KAOLIN_BRICK_STAIRS)
        .add(PINK_SANDSTONE_STAIRS, SMOOTH_PINK_SANDSTONE_STAIRS);
    getOrCreateTagBuilder(BlockTags.SLABS).forceAddTag(NSTags.Blocks.CHALK_SLABS).forceAddTag(NSTags.Blocks.KAOLIN_SLABS).forceAddTag(NSTags.Blocks.KAOLIN_SLABS)
        .add(PINK_SANDSTONE_SLAB, SMOOTH_PINK_SANDSTONE_SLAB, CUT_PINK_SANDSTONE_SLAB);
    getOrCreateTagBuilder(BlockTags.WALLS).add(PINK_SANDSTONE_WALL);
  }
}
