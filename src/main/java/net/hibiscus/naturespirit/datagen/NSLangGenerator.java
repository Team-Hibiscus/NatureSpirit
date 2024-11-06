package net.hibiscus.naturespirit.datagen;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

import static net.hibiscus.naturespirit.NatureSpirit.MOD_ID;
import static net.hibiscus.naturespirit.registration.NSBiomes.BiomesHashMap;

import net.hibiscus.naturespirit.registration.NSColoredBlocks;
import net.hibiscus.naturespirit.registration.NSItemGroups;
import net.hibiscus.naturespirit.registration.NSMiscBlocks;

import static net.hibiscus.naturespirit.registration.NSMiscBlocks.*;

import net.hibiscus.naturespirit.registration.NSRegistryHelper;
import net.hibiscus.naturespirit.registration.NSTags;
import net.hibiscus.naturespirit.registration.NSWoods;
import net.hibiscus.naturespirit.registration.sets.FlowerSet;
import net.hibiscus.naturespirit.registration.sets.StoneSet;
import net.hibiscus.naturespirit.registration.sets.WoodSet;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.biome.Biome;

class NSLangGenerator extends FabricLanguageProvider {

  protected NSLangGenerator(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
    super(dataOutput, registryLookup);
  }

  public static String capitalizeString(String string) {
    char[] chars = string.toLowerCase().toCharArray();
    boolean found = false;
    for (int i = 0; i < chars.length; i++) {
      if (!found && Character.isLetter(chars[i])) {
        chars[i] = Character.toUpperCase(chars[i]);
        found = true;
      } else if (Character.isWhitespace(chars[i]) || chars[i] == '.' || chars[i] == '\'') {
        found = false;
      }
    }
    return String.valueOf(chars);
  }

  private void generateBlockTranslations(Block block, TranslationBuilder translationBuilder) {
    String temp = capitalizeString(Registries.BLOCK.getId(block).getPath().replace("_", " "));
    translationBuilder.add(block, temp);
  }

  private void generateItemTranslations(Item item, TranslationBuilder translationBuilder) {
    String temp = capitalizeString(Registries.ITEM.getId(item).getPath().replace("_", " "));
    translationBuilder.add(item, temp);
  }

  private void generateItemTagTranslations(TagKey<Item> itemTag, TranslationBuilder translationBuilder) {
    String temp = capitalizeString(itemTag.id().getPath().replace("_", " "));
    translationBuilder.add(itemTag, temp);
  }

  private void generateWoodTranslations(HashMap<String, WoodSet> woods, TranslationBuilder translationBuilder) {
    for (WoodSet woodSet : woods.values()) {
      for (Block block : woodSet.getRegisteredBlocksList()) {
        generateBlockTranslations(block, translationBuilder);
      }
      generateBlockTranslations(woodSet.getSign(), translationBuilder);
      generateBlockTranslations(woodSet.getHangingSign(), translationBuilder);
      translationBuilder.add(woodSet.getBoatItem(), capitalizeString(woodSet.getName().replace("_", " ")) + " Boat");
      translationBuilder.add(woodSet.getChestBoatItem(), capitalizeString(woodSet.getName().replace("_", " ")) + " Boat with Chest");
      generateArchExTranslations(woodSet.getName(), translationBuilder);
    }
  }

  private void generateStoneTranslations(HashMap<String, StoneSet> stones, TranslationBuilder translationBuilder) {
    for (StoneSet stoneSet : stones.values()) {
      for (Block block : stoneSet.getRegisteredBlocksList()) {
        generateBlockTranslations(block, translationBuilder);
      }
    }
  }

  private void generateFlowerTranslations(HashMap<String, FlowerSet> flowers, TranslationBuilder translationBuilder) {
    for (FlowerSet flowerSet : flowers.values()) {
      for (Block block : flowerSet.getRegisteredBlocksList()) {
        generateBlockTranslations(block, translationBuilder);
      }
    }
  }

  private void generateBiomeTranslations(TranslationBuilder translationBuilder) {
    for (String name : BiomesHashMap.keySet()) {
      RegistryKey<Biome> biome = BiomesHashMap.get(name);
      translationBuilder.add(biome.toString().replace("ResourceKey[minecraft:worldgen/biome / natures_spirit:", "biome.natures_spirit.").replace("]", ""),
          capitalizeString(name.replace("_", " ")));
    }
  }

  private void generateArchExTranslations(String group, TranslationBuilder translationBuilder) {
    translationBuilder.add("architecture_extensions.grouped_block." + MOD_ID + "." + group, capitalizeString(group.replace("_", " ")));
  }

  private void generateColoredTranslations(String group, TranslationBuilder translationBuilder) {
    translationBuilder.add("block." + MOD_ID + "." + group, capitalizeString(group.replace("_", " ")));
  }

  @Override
  public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
    generateBiomeTranslations(translationBuilder);
    generateWoodTranslations(NSRegistryHelper.WoodHashMap, translationBuilder);
    generateStoneTranslations(NSRegistryHelper.StoneHashMap, translationBuilder);
    generateFlowerTranslations(NSRegistryHelper.FlowerHashMap, translationBuilder);
    translationBuilder.add(NSItemGroups.NS_ITEM_GROUP, "Nature's Spirit");
    translationBuilder.add("stat.minecraft.eat_pizza_slice", "Pizza Slices Eaten");

    translationBuilder.add("advancements.adventure.kaolin_kill.description", "Kill a Zombie with a Kaolin block");
    translationBuilder.add("advancements.adventure.kaolin_kill.title", "You're Kaolin This!");

    translationBuilder.add("advancements.adventure.shoot_coconut.description", "Hit a Coconut with a projectile");
    translationBuilder.add("advancements.adventure.shoot_coconut.title", "Tropical Blast!");

    translationBuilder.add("advancements.adventure.collect_all_flowers.description", "Collect all flowers.");
    translationBuilder.add("advancements.adventure.collect_all_flowers.title", "Flower Power");

    translationBuilder.add("advancements.adventure.sandy_forage.description", "Follow the Roots of a Turnip to a Turnip block");
    translationBuilder.add("advancements.adventure.sandy_forage.title", "Sandy Forage");

    generateItemTranslations(CHALK_POWDER, translationBuilder);
    generateItemTranslations(NSMiscBlocks.GREEN_OLIVES, translationBuilder);
    generateItemTranslations(NSMiscBlocks.BLACK_OLIVES, translationBuilder);
    generateItemTranslations(NSMiscBlocks.DESERT_TURNIP, translationBuilder);
    generateItemTranslations(CALCITE_SHARD, translationBuilder);
    generateBlockTranslations(SMALL_CALCITE_BUD,  translationBuilder);
    generateBlockTranslations(LARGE_CALCITE_BUD,  translationBuilder);
    generateBlockTranslations(CALCITE_CLUSTER, translationBuilder);
    generateBlockTranslations(NSWoods.OLIVE_BRANCH, translationBuilder);
    generateBlockTranslations(TALL_SCORCHED_GRASS, translationBuilder);
    generateBlockTranslations(PURPLE_BEARBERRIES, translationBuilder);
    generateBlockTranslations(RED_BEARBERRIES, translationBuilder);
    generateBlockTranslations(GREEN_BEARBERRIES, translationBuilder);
    generateBlockTranslations(RED_BITTER_SPROUTS, translationBuilder);
    generateBlockTranslations(PURPLE_BITTER_SPROUTS, translationBuilder);
    generateBlockTranslations(GREEN_BITTER_SPROUTS, translationBuilder);
    generateBlockTranslations(SCORCHED_GRASS, translationBuilder);
    generateBlockTranslations(TALL_BEACH_GRASS, translationBuilder);
    generateBlockTranslations(BEACH_GRASS, translationBuilder);
    generateBlockTranslations(TALL_SEDGE_GRASS, translationBuilder);
    generateBlockTranslations(SEDGE_GRASS, translationBuilder);
    generateBlockTranslations(TALL_FRIGID_GRASS, translationBuilder);
    generateBlockTranslations(FRIGID_GRASS, translationBuilder);
    generateBlockTranslations(LARGE_FLAXEN_FERN, translationBuilder);
    generateBlockTranslations(FLAXEN_FERN, translationBuilder);
    generateBlockTranslations(TALL_OAT_GRASS, translationBuilder);
    generateBlockTranslations(OAT_GRASS, translationBuilder);
    generateBlockTranslations(LARGE_LUSH_FERN, translationBuilder);
    generateBlockTranslations(LUSH_FERN, translationBuilder);
    generateBlockTranslations(TALL_MELIC_GRASS, translationBuilder);
    generateBlockTranslations(MELIC_GRASS, translationBuilder);
    generateBlockTranslations(SHIITAKE_MUSHROOM, translationBuilder);
    generateBlockTranslations(SHIITAKE_MUSHROOM_BLOCK, translationBuilder);
    generateBlockTranslations(GRAY_POLYPORE, translationBuilder);
    generateBlockTranslations(GRAY_POLYPORE_BLOCK, translationBuilder);
    generateBlockTranslations(PAPER_BLOCK, translationBuilder);
    generateBlockTranslations(FRAMED_PAPER_BLOCK, translationBuilder);
    generateBlockTranslations(BLOOMING_PAPER_BLOCK, translationBuilder);
    generateBlockTranslations(PAPER_DOOR, translationBuilder);
    generateBlockTranslations(FRAMED_PAPER_DOOR, translationBuilder);
    generateBlockTranslations(BLOOMING_PAPER_DOOR, translationBuilder);
    generateBlockTranslations(PAPER_TRAPDOOR, translationBuilder);
    generateBlockTranslations(BLOOMING_PAPER_TRAPDOOR, translationBuilder);
    generateBlockTranslations(FRAMED_PAPER_TRAPDOOR, translationBuilder);
    generateBlockTranslations(PAPER_SIGN, translationBuilder);
    generateBlockTranslations(PAPER_HANGING_SIGN, translationBuilder);
    generateBlockTranslations(PAPER_PANEL, translationBuilder);
    generateBlockTranslations(FRAMED_PAPER_PANEL, translationBuilder);
    generateBlockTranslations(BLOOMING_PAPER_PANEL, translationBuilder);
    generateBlockTranslations(NSColoredBlocks.PAPER_LANTERN, translationBuilder);
    generateBlockTranslations(NSMiscBlocks.CATTAIL, translationBuilder);
    generateBlockTranslations(NSMiscBlocks.DESERT_TURNIP_ROOT_BLOCK, translationBuilder);
    generateBlockTranslations(NSMiscBlocks.DESERT_TURNIP_BLOCK, translationBuilder);
    translationBuilder.add(HELVOLA_PAD_ITEM, "Helvola Pad");
    generateItemTranslations(HELVOLA_FLOWER_ITEM, translationBuilder);
    generateBlockTranslations(AZOLLA, translationBuilder);
    generateBlockTranslations(LOTUS_FLOWER, translationBuilder);
    generateBlockTranslations(LOTUS_STEM, translationBuilder);
    generateBlockTranslations(RED_MOSS_BLOCK, translationBuilder);
    generateBlockTranslations(RED_MOSS_CARPET, translationBuilder);
    generateBlockTranslations(ALLUAUDIA, translationBuilder);
    generateBlockTranslations(ALLUAUDIA_BUNDLE, translationBuilder);
    generateBlockTranslations(STRIPPED_ALLUAUDIA, translationBuilder);
    generateBlockTranslations(STRIPPED_ALLUAUDIA_BUNDLE, translationBuilder);

    generateBlockTranslations(CHERT_COAL_ORE, translationBuilder);
    generateBlockTranslations(CHERT_COPPER_ORE, translationBuilder);
    generateBlockTranslations(CHERT_DIAMOND_ORE, translationBuilder);
    generateBlockTranslations(CHERT_GOLD_ORE, translationBuilder);
    generateBlockTranslations(CHERT_EMERALD_ORE, translationBuilder);
    generateBlockTranslations(CHERT_IRON_ORE, translationBuilder);
    generateBlockTranslations(CHERT_LAPIS_ORE, translationBuilder);
    generateBlockTranslations(CHERT_REDSTONE_ORE, translationBuilder);

    generateBlockTranslations(NSColoredBlocks.KAOLIN, translationBuilder);
    generateBlockTranslations(NSColoredBlocks.KAOLIN_BRICKS, translationBuilder);
    generateBlockTranslations(NSColoredBlocks.KAOLIN_SLAB, translationBuilder);
    generateBlockTranslations(NSColoredBlocks.KAOLIN_BRICK_SLAB, translationBuilder);
    generateBlockTranslations(NSColoredBlocks.KAOLIN_STAIRS, translationBuilder);
    generateBlockTranslations(NSColoredBlocks.KAOLIN_BRICK_STAIRS, translationBuilder);

    generateBlockTranslations(NSWoods.COCONUT_BLOCK, translationBuilder);
    generateBlockTranslations(NSWoods.COCONUT_SPROUT, translationBuilder);
    generateItemTranslations(NSWoods.COCONUT_SHELL, translationBuilder);
    generateItemTranslations(NSWoods.COCONUT_HALF, translationBuilder);

    generateBlockTranslations(NSWoods.COCONUT_THATCH, translationBuilder);
    generateBlockTranslations(NSWoods.COCONUT_THATCH_SLAB, translationBuilder);
    generateBlockTranslations(NSWoods.COCONUT_THATCH_STAIRS, translationBuilder);
    generateBlockTranslations(NSWoods.COCONUT_THATCH_CARPET, translationBuilder);

    generateBlockTranslations(NSWoods.EVERGREEN_THATCH, translationBuilder);
    generateBlockTranslations(NSWoods.EVERGREEN_THATCH_SLAB, translationBuilder);
    generateBlockTranslations(NSWoods.EVERGREEN_THATCH_STAIRS, translationBuilder);
    generateBlockTranslations(NSWoods.EVERGREEN_THATCH_CARPET, translationBuilder);

    generateBlockTranslations(NSWoods.XERIC_THATCH, translationBuilder);
    generateBlockTranslations(NSWoods.XERIC_THATCH_SLAB, translationBuilder);
    generateBlockTranslations(NSWoods.XERIC_THATCH_STAIRS, translationBuilder);
    generateBlockTranslations(NSWoods.XERIC_THATCH_CARPET, translationBuilder);

    generateBlockTranslations(ORNATE_SUCCULENT, translationBuilder);
    generateBlockTranslations(DROWSY_SUCCULENT, translationBuilder);
    generateBlockTranslations(AUREATE_SUCCULENT, translationBuilder);
    generateBlockTranslations(SAGE_SUCCULENT, translationBuilder);
    generateBlockTranslations(FOAMY_SUCCULENT, translationBuilder);
    generateBlockTranslations(IMPERIAL_SUCCULENT, translationBuilder);
    generateBlockTranslations(REGAL_SUCCULENT, translationBuilder);

    generateBlockTranslations(PINK_SAND, translationBuilder);
    generateBlockTranslations(PINK_SANDSTONE, translationBuilder);
    generateBlockTranslations(PINK_SANDSTONE_SLAB, translationBuilder);
    generateBlockTranslations(PINK_SANDSTONE_STAIRS, translationBuilder);
    generateBlockTranslations(PINK_SANDSTONE_WALL, translationBuilder);
    generateBlockTranslations(SMOOTH_PINK_SANDSTONE, translationBuilder);
    generateBlockTranslations(SMOOTH_PINK_SANDSTONE_STAIRS, translationBuilder);
    generateBlockTranslations(SMOOTH_PINK_SANDSTONE_SLAB, translationBuilder);
    generateBlockTranslations(CHISELED_PINK_SANDSTONE, translationBuilder);
    generateBlockTranslations(CUT_PINK_SANDSTONE, translationBuilder);
    generateBlockTranslations(CUT_PINK_SANDSTONE_SLAB, translationBuilder);

    generateBlockTranslations(NSMiscBlocks.SANDY_SOIL, translationBuilder);
    generateBlockTranslations(CHEESE_BLOCK, translationBuilder);
    generateBlockTranslations(CHEESE_CAULDRON, translationBuilder);
    generateBlockTranslations(MILK_CAULDRON, translationBuilder);
    translationBuilder.add(CHEESE_BUCKET, "Cheese Bucket");
    translationBuilder.add(CHEESE_ARROW, "Cheese Arrow");
    translationBuilder.add("block.natures_spirit.pizza.cooked_chicken", "With Cooked Chicken");
    translationBuilder.add("block.natures_spirit.pizza.green_olives", "With Green Olives");
    translationBuilder.add("block.natures_spirit.pizza.black_olives", "With Black Olives");
    translationBuilder.add("block.natures_spirit.pizza.brown_mushroom", "With Mushrooms");
    translationBuilder.add("block.natures_spirit.pizza.beetroot", "With Beetroots");
    translationBuilder.add("block.natures_spirit.pizza.carrot", "With Carrots");
    translationBuilder.add("block.natures_spirit.pizza.cooked_cod", "With Cooked Cod");
    translationBuilder.add("block.natures_spirit.pizza.cooked_porkchop", "With Cooked Porkchop");
    translationBuilder.add("block.natures_spirit.pizza.cooked_rabbit", "With Cooked Rabbit");
    translationBuilder.add(NSMiscBlocks.HALF_PIZZA, "Half of a Pizza");
    translationBuilder.add(NSMiscBlocks.THREE_QUARTERS_PIZZA, "Three Quarters of a Pizza");
    translationBuilder.add(NSMiscBlocks.QUARTER_PIZZA, "Quarter of a Pizza");
    translationBuilder.add(NSMiscBlocks.WHOLE_PIZZA, "Pizza");
    translationBuilder.add("pack.natures_spirit.bushy_leaves_compatibility", "Bushy Leaves Compat");
    translationBuilder.add("pack.natures_spirit.plank_consistency", "Plank Consistency");
    translationBuilder.add("pack.natures_spirit.emissive_ores_compatibility", "Emissive Ores Compat");
    translationBuilder.add("pack.natures_spirit.modified_swamp", "Modified Swamp");
    translationBuilder.add("pack.natures_spirit.modified_desert", "Modified Desert");
    translationBuilder.add("pack.natures_spirit.modified_birch_forest", "Modified Birch Forest");
    translationBuilder.add("pack.natures_spirit.modified_badlands", "Modified Badlands");
    translationBuilder.add("pack.natures_spirit.modified_savannas", "Modified Savannas");
    translationBuilder.add("pack.natures_spirit.modified_dark_forest", "Modified Dark Forests");
    translationBuilder.add("pack.natures_spirit.modified_mountain_biomes", "Modified Mountain Biomes");
    translationBuilder.add("pack.natures_spirit.modified_vanilla_trees", "Modified Vanilla Trees");
    translationBuilder.add("pack.natures_spirit.modified_windswept_hills", "Modified Windswept Hills");
    translationBuilder.add("pack.natures_spirit.modified_jungle", "Modified Jungles");
    translationBuilder.add("painting.natures_spirit.redwood.title", "Woody Spires");
    translationBuilder.add("painting.natures_spirit.redwood.author", "Aeramisu");
    translationBuilder.add("painting.natures_spirit.lavender.title", "Happy Days");
    translationBuilder.add("painting.natures_spirit.lavender.author", "Aeramisu");
    translationBuilder.add("painting.natures_spirit.aspen.title", "Aureate");
    translationBuilder.add("painting.natures_spirit.aspen.author", "Aeramisu");
    translationBuilder.add("painting.natures_spirit.sandy_trees.title", "Lost");
    translationBuilder.add("painting.natures_spirit.sandy_trees.author", "Aeramisu");
    generateItemTagTranslations(NSTags.Items.PIZZA_TOPPINGS, translationBuilder);
    generateItemTagTranslations(NSTags.Items.DISABLED_PIZZA_TOPPINGS, translationBuilder);
    generateItemTagTranslations(NSTags.Items.CHEESE_MAKER, translationBuilder);
    generateItemTagTranslations(NSTags.Items.EVERGREEN_LEAVES, translationBuilder);
    generateItemTagTranslations(NSTags.Items.XERIC_LEAVES, translationBuilder);
    generateItemTagTranslations(NSTags.Items.COCONUT_ITEMS, translationBuilder);
    generateItemTagTranslations(NSTags.Items.SUCCULENTS, translationBuilder);
    generateItemTagTranslations(NSTags.Items.STRIPPED_LOGS, translationBuilder);
    generateItemTagTranslations(NSTags.Items.ALLUAUDIA_BUNDLES, translationBuilder);
    generateItemTagTranslations(NSTags.Items.KAOLIN, translationBuilder);
    generateItemTagTranslations(NSTags.Items.KAOLIN_STAIRS, translationBuilder);
    generateItemTagTranslations(NSTags.Items.KAOLIN_SLABS, translationBuilder);
    generateItemTagTranslations(NSTags.Items.KAOLIN_BRICKS, translationBuilder);
    generateItemTagTranslations(NSTags.Items.KAOLIN_BRICK_STAIRS, translationBuilder);
    generateItemTagTranslations(NSTags.Items.KAOLIN_BRICK_SLABS, translationBuilder);
    generateItemTagTranslations(NSTags.Items.CHALK, translationBuilder);
    generateItemTagTranslations(NSTags.Items.CHALK_STAIRS, translationBuilder);
    generateItemTagTranslations(NSTags.Items.CHALK_SLABS, translationBuilder);
    generateItemTagTranslations(NSTags.Items.COCONUT_HALVES, translationBuilder);
    generateItemTagTranslations(NSTags.Items.OLIVES, translationBuilder);

    generateArchExTranslations("kaolin", translationBuilder);
    generateArchExTranslations("kaolin_bricks", translationBuilder);

    for (var color : List.of(NSDataGen.DYE_COLORS)) {

      generateArchExTranslations(color + "_kaolin", translationBuilder);
      generateArchExTranslations(color + "_kaolin_bricks", translationBuilder);
      generateArchExTranslations(color + "_chalk", translationBuilder);

      generateColoredTranslations(color + "_paper_lantern", translationBuilder);
      generateColoredTranslations(color + "_kaolin", translationBuilder);
      generateColoredTranslations(color + "_kaolin_bricks", translationBuilder);
      generateColoredTranslations(color + "_kaolin_slab", translationBuilder);
      generateColoredTranslations(color + "_kaolin_brick_slab", translationBuilder);
      generateColoredTranslations(color + "_kaolin_stairs", translationBuilder);
      generateColoredTranslations(color + "_kaolin_brick_stairs", translationBuilder);
      generateColoredTranslations(color + "_chalk", translationBuilder);
      generateColoredTranslations(color + "_chalk_slab", translationBuilder);
      generateColoredTranslations(color + "_chalk_stairs", translationBuilder);
    }
    generateArchExTranslations("pink_sandstone", translationBuilder);
    generateArchExTranslations("smooth_pink_sandstone", translationBuilder);
    generateArchExTranslations("travertine", translationBuilder);
    generateArchExTranslations("travertine_bricks", translationBuilder);
    generateArchExTranslations("travertine_tiles", translationBuilder);
    generateArchExTranslations("cobbled_travertine", translationBuilder);
    generateArchExTranslations("mossy_cobbled_travertine", translationBuilder);
    generateArchExTranslations("mossy_travertine_bricks", translationBuilder);
  }
}
