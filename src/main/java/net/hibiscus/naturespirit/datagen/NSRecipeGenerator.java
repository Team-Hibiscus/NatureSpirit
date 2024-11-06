package net.hibiscus.naturespirit.datagen;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;

import static net.hibiscus.naturespirit.NatureSpirit.MOD_ID;

import net.hibiscus.naturespirit.registration.NSColoredBlocks;
import net.hibiscus.naturespirit.registration.NSMiscBlocks;

import static net.hibiscus.naturespirit.registration.NSMiscBlocks.*;

import net.hibiscus.naturespirit.registration.NSRegistryHelper;
import net.hibiscus.naturespirit.registration.NSTags;
import net.hibiscus.naturespirit.registration.NSWoods;
import net.hibiscus.naturespirit.registration.sets.FlowerSet;
import net.hibiscus.naturespirit.registration.sets.StoneSet;
import net.hibiscus.naturespirit.registration.sets.WoodSet;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.block.Blocks;

import static net.minecraft.data.family.BlockFamilies.register;

import net.minecraft.data.family.BlockFamily;
import net.minecraft.data.server.recipe.CookingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.data.server.recipe.StonecuttingRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.util.Identifier;

public class NSRecipeGenerator extends FabricRecipeProvider {

  public NSRecipeGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
    super(output, registryLookup);
  }

  public static final BlockFamily PINK_SANDSTONE_FAMILY = register(NSMiscBlocks.PINK_SANDSTONE).wall(PINK_SANDSTONE_WALL).stairs(PINK_SANDSTONE_STAIRS).slab(PINK_SANDSTONE_SLAB)
      .chiseled(CHISELED_PINK_SANDSTONE).cut(CUT_PINK_SANDSTONE).noGenerateModels().noGenerateRecipes().build();
  public static final BlockFamily CUT_PINK_SANDSTONE_FAMILY = register(NSMiscBlocks.CUT_PINK_SANDSTONE).slab(CUT_PINK_SANDSTONE_SLAB).noGenerateModels().build();
  public static final BlockFamily SMOOTH_PINK_SANDSTONE_FAMILY = register(NSMiscBlocks.SMOOTH_PINK_SANDSTONE).slab(SMOOTH_PINK_SANDSTONE_SLAB).stairs(SMOOTH_PINK_SANDSTONE_STAIRS)
      .noGenerateModels().build();


  public static void offerShapelessRecipe(RecipeExporter exporter, ItemConvertible output, ItemConvertible input, String group, int outputCount) {
    ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, output, outputCount).input(input).group(group)
        .criterion(RecipeProvider.hasItem(input), RecipeProvider.conditionsFromItem(input))
        .offerTo(exporter, Identifier.of(MOD_ID, RecipeProvider.convertBetween(output, input)));
  }

  public static void offerStonecuttingRecipe(RecipeExporter exporter, RecipeCategory category, ItemConvertible output, ItemConvertible input) {
    offerStonecuttingRecipe(exporter, category, output, input, 1);
  }

  public static void offerStonecuttingRecipe(RecipeExporter exporter, RecipeCategory category, ItemConvertible output, ItemConvertible input, int count) {
    StonecuttingRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(input), category, output, count)
        .criterion(RecipeProvider.hasItem(input), RecipeProvider.conditionsFromItem(input))
        .offerTo(exporter, Identifier.of(MOD_ID, RecipeProvider.convertBetween(output, input) + "_stonecutting"));
  }

  private void generateWoodRecipes(HashMap<String, WoodSet> woods, RecipeExporter consumer) {
    for (WoodSet woodSet : woods.values()) {
      offerPlanksRecipe(consumer, woodSet.getPlanks(), woodSet.getItemLogsTag(), 4);
      if (woodSet.hasBark()) {
        offerBarkBlockRecipe(consumer, woodSet.getWood(), woodSet.getLog());
        offerBarkBlockRecipe(consumer, woodSet.getStrippedWood(), woodSet.getStrippedLog());
      }
      if (woodSet.getWoodPreset() == WoodSet.WoodPreset.JOSHUA) {
        offer2x2CompactingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, woodSet.getBundle(), woodSet.getLog());
        offer2x2CompactingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, woodSet.getStrippedBundle(), woodSet.getStrippedLog());
        offerShapelessRecipe(consumer, woodSet.getPlanks(), woodSet.getBundle(), "planks", 4);
        offerShapelessRecipe(consumer, woodSet.getPlanks(), woodSet.getStrippedBundle(), "planks", 4);
      }
      if (woodSet.hasMosaic()) {
        offerMosaicRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, woodSet.getMosaic(), woodSet.getSlab());
        createStairsRecipe(woodSet.getMosaicStairs(), Ingredient.ofItems(woodSet.getMosaic()));
        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, woodSet.getMosaicSlab(), Ingredient.ofItems(woodSet.getMosaic()));
      }
      offerHangingSignRecipe(consumer, woodSet.getHangingSign(), woodSet.getStrippedLog());
      offerBoatRecipe(consumer, woodSet.getBoatItem(), woodSet.getPlanks());
      offerChestBoatRecipe(consumer, woodSet.getChestBoatItem(), woodSet.getBoatItem());
      BlockFamily family = register(woodSet.getPlanks())
          .button(woodSet.getButton())
          .fence(woodSet.getFence())
          .fenceGate(woodSet.getFenceGate())
          .pressurePlate(woodSet.getPressurePlate())
          .sign(woodSet.getSign(), woodSet.getWallSign())
          .slab(woodSet.getSlab())
          .stairs(woodSet.getStairs())
          .door(woodSet.getDoor())
          .trapdoor(woodSet.getTrapDoor())
          .group("wooden")
          .unlockCriterionName("has_planks")
          .build();
      generateFamily(consumer, family, FeatureSet.of(FeatureFlags.VANILLA));
    }
  }

  private void generateFlowerRecipes(HashMap<String, FlowerSet> flowers, RecipeExporter consumer) {
    for (FlowerSet flowerSet : flowers.values()) {
      if (flowerSet.getDyeColor() != null) {
        offerShapelessRecipe(consumer, flowerSet.getDyeColor(), flowerSet.getFlowerBlock(), flowerSet.getDyeColor().toString(), flowerSet.getDyeNumber());
      }
    }
  }

  private void generateStoneRecipes(HashMap<String, StoneSet> stoones, RecipeExporter exporter) {
    for (StoneSet stoneSet : stoones.values()) {
      generateFamily(exporter, stoneSet.getBaseFamily(), FeatureSet.of(FeatureFlags.VANILLA));
      generateFamily(exporter, stoneSet.getBrickFamily(), FeatureSet.of(FeatureFlags.VANILLA));
      generateFamily(exporter, stoneSet.getPolishedFamily(), FeatureSet.of(FeatureFlags.VANILLA));
      ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, stoneSet.getBricks(), 4)
          .input('S', stoneSet.getPolished()).pattern("SS")
          .pattern("SS")
          .criterion("has_polished_" + stoneSet.getName(), conditionsFromItem(stoneSet.getPolished()))
          .offerTo(exporter);

      if (stoneSet.hasTiles()) {
        generateFamily(exporter, stoneSet.getTileFamily(), FeatureSet.of(FeatureFlags.VANILLA));
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, stoneSet.getTiles(), 4)
            .input('S', stoneSet.getBricks())
            .pattern("SS").pattern("SS")
            .criterion("has_" + stoneSet.getName() + "_bricks", conditionsFromItem(stoneSet.getBricks()))
            .offerTo(exporter);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getTiles(), stoneSet.getPolished());
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getTilesSlab(), stoneSet.getPolished(), 2);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getTilesStairs(), stoneSet.getPolished());
        offerStonecuttingRecipe(exporter, RecipeCategory.DECORATIONS, stoneSet.getTilesWall(), stoneSet.getPolished());
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getTiles(), stoneSet.getBricks());
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getTilesSlab(), stoneSet.getBricks(), 2);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getTilesStairs(), stoneSet.getBricks());
        offerStonecuttingRecipe(exporter, RecipeCategory.DECORATIONS, stoneSet.getTilesWall(), stoneSet.getBricks());
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getTilesSlab(), stoneSet.getTiles(), 2);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getTilesStairs(), stoneSet.getTiles());
        offerStonecuttingRecipe(exporter, RecipeCategory.DECORATIONS, stoneSet.getTilesWall(), stoneSet.getTiles());
      }
      if (stoneSet.hasCobbled()) {
        generateFamily(exporter, stoneSet.getCobbledFamily(), FeatureSet.of(FeatureFlags.VANILLA));
        if (stoneSet.hasMossy()) {
          generateFamily(exporter, stoneSet.getMossyCobbledFamily(), FeatureSet.of(FeatureFlags.VANILLA));
          ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, stoneSet.getMossyCobbled())
              .input(stoneSet.getCobbled()).input(Blocks.MOSS_BLOCK)
              .group("mossy_cobblestone")
              .criterion("has_moss_block", conditionsFromItem(Blocks.MOSS_BLOCK))
              .offerTo(exporter, Identifier.of(MOD_ID, convertBetween(stoneSet.getMossyCobbled(), Blocks.MOSS_BLOCK)));
          ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, stoneSet.getMossyCobbled())
              .input(stoneSet.getCobbled())
              .input(Blocks.VINE)
              .group("mossy_cobblestone")
              .criterion("has_vine", conditionsFromItem(Blocks.VINE))
              .offerTo(exporter, Identifier.of(MOD_ID, convertBetween(stoneSet.getMossyCobbled(), Blocks.VINE)));
          offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getMossyCobbledSlab(), stoneSet.getMossyCobbled(), 2);
          offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getMossyCobbledStairs(), stoneSet.getMossyCobbled());
          offerStonecuttingRecipe(exporter, RecipeCategory.DECORATIONS, stoneSet.getMossyCobbledWall(), stoneSet.getMossyCobbled());
        }
        CookingRecipeJsonBuilder.createSmelting(Ingredient.ofItems(stoneSet.getCobbled()), RecipeCategory.BUILDING_BLOCKS, stoneSet.getBase(), 0.1F, 200)
            .criterion("has_cobbled_" + stoneSet.getName(), conditionsFromItem(stoneSet.getCobbled()))
            .offerTo(exporter);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getCobbledSlab(), stoneSet.getCobbled(), 2);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getCobbledStairs(), stoneSet.getCobbled());
        offerStonecuttingRecipe(exporter, RecipeCategory.DECORATIONS, stoneSet.getCobbledWall(), stoneSet.getCobbled());
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getChiseled(), stoneSet.getCobbled());
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getPolished(), stoneSet.getCobbled());
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getPolishedSlab(), stoneSet.getCobbled(), 2);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getPolishedStairs(), stoneSet.getCobbled());
        offerStonecuttingRecipe(exporter, RecipeCategory.DECORATIONS, stoneSet.getPolishedWall(), stoneSet.getCobbled());
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getBricks(), stoneSet.getCobbled());
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getBricksSlab(), stoneSet.getCobbled(), 2);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getBricksStairs(), stoneSet.getCobbled());
        offerStonecuttingRecipe(exporter, RecipeCategory.DECORATIONS, stoneSet.getBricksWall(), stoneSet.getCobbled());
        if (stoneSet.hasTiles()) {
          offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getTiles(), stoneSet.getCobbled());
          offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getTilesSlab(), stoneSet.getCobbled(), 2);
          offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getTilesStairs(), stoneSet.getCobbled());
          offerStonecuttingRecipe(exporter, RecipeCategory.DECORATIONS, stoneSet.getTilesWall(), stoneSet.getCobbled());
        }
      } else {
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getChiseled(), stoneSet.getBase());
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getPolished(), stoneSet.getBase());
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getPolishedSlab(), stoneSet.getBase(), 2);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getPolishedStairs(), stoneSet.getBase());
        offerStonecuttingRecipe(exporter, RecipeCategory.DECORATIONS, stoneSet.getPolishedWall(), stoneSet.getBase());
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getBricks(), stoneSet.getBase());
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getBricksSlab(), stoneSet.getBase(), 2);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getBricksStairs(), stoneSet.getBase());
        offerStonecuttingRecipe(exporter, RecipeCategory.DECORATIONS, stoneSet.getBricksWall(), stoneSet.getBase());
        if (stoneSet.hasTiles()) {
          offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getTiles(), stoneSet.getBase());
          offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getTilesSlab(), stoneSet.getBase(), 2);
          offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getTilesStairs(), stoneSet.getBase());
          offerStonecuttingRecipe(exporter, RecipeCategory.DECORATIONS, stoneSet.getTilesWall(), stoneSet.getBase());
        }
      }
      if (stoneSet.hasMossy()) {
        generateFamily(exporter, stoneSet.getMossyBrickFamily(), FeatureSet.of(FeatureFlags.VANILLA));
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, stoneSet.getMossyBricks())
            .input(stoneSet.getBricks())
            .input(Blocks.VINE)
            .group("mossy_stone_bricks")
            .criterion("has_vine", conditionsFromItem(Blocks.VINE))
            .offerTo(exporter, Identifier.of(MOD_ID, convertBetween(stoneSet.getMossyBricks(), Blocks.VINE)));
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, stoneSet.getMossyBricks())
            .input(stoneSet.getBricks())
            .input(Blocks.MOSS_BLOCK)
            .group("mossy_stone_bricks")
            .criterion("has_moss_block", conditionsFromItem(Blocks.MOSS_BLOCK))
            .offerTo(exporter, Identifier.of(MOD_ID, convertBetween(stoneSet.getMossyBricks(), Blocks.MOSS_BLOCK)));
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getMossyBricksSlab(), stoneSet.getMossyBricks(), 2);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getMossyBricksStairs(), stoneSet.getMossyBricks());
        offerStonecuttingRecipe(exporter, RecipeCategory.DECORATIONS, stoneSet.getMossyBricksWall(), stoneSet.getMossyBricks());
      }

      offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getPolishedSlab(), stoneSet.getPolished(), 2);
      offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getPolishedStairs(), stoneSet.getPolished());
      offerStonecuttingRecipe(exporter, RecipeCategory.DECORATIONS, stoneSet.getPolishedWall(), stoneSet.getPolished());
      offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getBricks(), stoneSet.getPolished());
      offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getBricksSlab(), stoneSet.getPolished(), 2);
      offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getBricksStairs(), stoneSet.getPolished());
      offerStonecuttingRecipe(exporter, RecipeCategory.DECORATIONS, stoneSet.getBricksWall(), stoneSet.getPolished());
      offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getBricksSlab(), stoneSet.getBricks(), 2);
      offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, stoneSet.getBricksStairs(), stoneSet.getBricks());
      offerStonecuttingRecipe(exporter, RecipeCategory.DECORATIONS, stoneSet.getBricksWall(), stoneSet.getBricks());
    }
  }

  public static void offer2x2CompactingTagRecipe(RecipeExporter exporter, RecipeCategory category, ItemConvertible output, TagKey<Item> input) {
    ShapedRecipeJsonBuilder.create(category, output, 1).input('#', input).pattern("##").pattern("##").criterion("has_evergreen_leaves", conditionsFromTag(input)).offerTo(exporter);
  }

  @Override
  protected Identifier getRecipeIdentifier(Identifier identifier) {
    return Identifier.of(MOD_ID, identifier.getPath());
  }

  @Override
  public void generate(RecipeExporter exporter) {
    offer2x2CompactingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ALLUAUDIA_BUNDLE, ALLUAUDIA);
    offer2x2CompactingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, STRIPPED_ALLUAUDIA_BUNDLE, STRIPPED_ALLUAUDIA);
    createChiseledBlockRecipe(RecipeCategory.BUILDING_BLOCKS, CHISELED_PINK_SANDSTONE, Ingredient.ofItems(PINK_SANDSTONE_SLAB))
        .criterion("has_pink_sandstone", conditionsFromItem(NSMiscBlocks.PINK_SANDSTONE))
        .criterion("has_chiseled_pink_sandstone", conditionsFromItem(CHISELED_PINK_SANDSTONE))
        .criterion("has_cut_pink_sandstone", conditionsFromItem(NSMiscBlocks.CUT_PINK_SANDSTONE))
        .offerTo(exporter);
    ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, NSMiscBlocks.PINK_SANDSTONE)
        .input('#', PINK_SAND).pattern("##")
        .pattern("##").criterion("has_sand", conditionsFromItem(NSMiscBlocks.PINK_SAND))
        .offerTo(exporter);
    createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, NSMiscBlocks.PINK_SANDSTONE_SLAB, Ingredient.ofItems(
        NSMiscBlocks.PINK_SANDSTONE,
        NSMiscBlocks.CHISELED_PINK_SANDSTONE
    ))
        .criterion("has_pink_sandstone", conditionsFromItem(NSMiscBlocks.PINK_SANDSTONE)).criterion("has_chiseled_pink_sandstone", conditionsFromItem(
            NSMiscBlocks.CHISELED_PINK_SANDSTONE))
        .offerTo(exporter);
    createStairsRecipe(NSMiscBlocks.PINK_SANDSTONE_STAIRS, Ingredient.ofItems(
        NSMiscBlocks.PINK_SANDSTONE,
        NSMiscBlocks.CHISELED_PINK_SANDSTONE,
        NSMiscBlocks.CUT_PINK_SANDSTONE
    ))
        .criterion("has_pink_sandstone", conditionsFromItem(NSMiscBlocks.PINK_SANDSTONE))
        .criterion("has_chiseled_pink_sandstone", conditionsFromItem(NSMiscBlocks.CHISELED_PINK_SANDSTONE))
        .criterion("has_cut_pink_sandstone", conditionsFromItem(NSMiscBlocks.CUT_PINK_SANDSTONE))
        .offerTo(exporter);
    offerCutCopperRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, NSMiscBlocks.CUT_PINK_SANDSTONE, NSMiscBlocks.PINK_SANDSTONE);
    offerWallRecipe(exporter, RecipeCategory.DECORATIONS, NSMiscBlocks.PINK_SANDSTONE_WALL, NSMiscBlocks.PINK_SANDSTONE);
    CookingRecipeJsonBuilder.createSmelting(Ingredient.ofItems(NSMiscBlocks.PINK_SANDSTONE), RecipeCategory.BUILDING_BLOCKS, NSMiscBlocks.SMOOTH_PINK_SANDSTONE.asItem(), 0.1F, 200)
        .criterion("has_pink_sandstone", conditionsFromItem(NSMiscBlocks.PINK_SANDSTONE))
        .offerTo(exporter);
    offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, NSMiscBlocks.CUT_PINK_SANDSTONE, NSMiscBlocks.PINK_SANDSTONE);
    offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, NSMiscBlocks.PINK_SANDSTONE_SLAB, NSMiscBlocks.PINK_SANDSTONE, 2);
    offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, NSMiscBlocks.CUT_PINK_SANDSTONE_SLAB, NSMiscBlocks.PINK_SANDSTONE, 2);
    offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, NSMiscBlocks.CUT_PINK_SANDSTONE_SLAB, NSMiscBlocks.CUT_PINK_SANDSTONE, 2);
    offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, NSMiscBlocks.PINK_SANDSTONE_STAIRS, NSMiscBlocks.PINK_SANDSTONE);
    offerStonecuttingRecipe(exporter, RecipeCategory.DECORATIONS, NSMiscBlocks.PINK_SANDSTONE_WALL, NSMiscBlocks.PINK_SANDSTONE);
    offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, NSMiscBlocks.CHISELED_PINK_SANDSTONE, NSMiscBlocks.PINK_SANDSTONE);
    offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, NSMiscBlocks.SMOOTH_PINK_SANDSTONE_SLAB, NSMiscBlocks.SMOOTH_PINK_SANDSTONE, 2);
    offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, NSMiscBlocks.SMOOTH_PINK_SANDSTONE_STAIRS, NSMiscBlocks.SMOOTH_PINK_SANDSTONE);

    offerCarpetRecipe(exporter, NSWoods.COCONUT_THATCH_CARPET, NSWoods.COCONUT_THATCH);
    offerSlabRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, NSWoods.COCONUT_THATCH_SLAB, NSWoods.COCONUT_THATCH);
    createStairsRecipe(NSWoods.COCONUT_THATCH_STAIRS, Ingredient.ofItems(NSWoods.COCONUT_THATCH)).criterion(hasItem(NSWoods.COCONUT_THATCH),
        conditionsFromItem(NSWoods.COCONUT_THATCH)).offerTo(exporter);
    offer2x2CompactingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, NSWoods.COCONUT_THATCH, NSWoods.COCONUT.getLeaves());

    offerCarpetRecipe(exporter, NSWoods.EVERGREEN_THATCH_CARPET, NSWoods.EVERGREEN_THATCH);
    offerSlabRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, NSWoods.EVERGREEN_THATCH_SLAB, NSWoods.EVERGREEN_THATCH);
    createStairsRecipe(NSWoods.EVERGREEN_THATCH_STAIRS, Ingredient.ofItems(NSWoods.EVERGREEN_THATCH)).criterion(hasItem(NSWoods.EVERGREEN_THATCH),
        conditionsFromItem(NSWoods.EVERGREEN_THATCH)).offerTo(exporter);
    offer2x2CompactingTagRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, NSWoods.EVERGREEN_THATCH, NSTags.Items.EVERGREEN_LEAVES);

    offerCarpetRecipe(exporter, NSWoods.XERIC_THATCH_CARPET, NSWoods.XERIC_THATCH);
    offerSlabRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, NSWoods.XERIC_THATCH_SLAB, NSWoods.XERIC_THATCH);
    createStairsRecipe(NSWoods.XERIC_THATCH_STAIRS, Ingredient.ofItems(NSWoods.XERIC_THATCH)).criterion(hasItem(NSWoods.XERIC_THATCH), conditionsFromItem(NSWoods.XERIC_THATCH))
        .offerTo(exporter);
    offer2x2CompactingTagRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, NSWoods.XERIC_THATCH, NSTags.Items.XERIC_LEAVES);

    ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, NSColoredBlocks.KAOLIN_BRICKS, 4).input(Character.valueOf('S'), NSColoredBlocks.KAOLIN).pattern("SS")
        .pattern("SS").criterion("has_kaolin", conditionsFromItem(NSColoredBlocks.KAOLIN)).offerTo(exporter);
    ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, NSColoredBlocks.WHITE_KAOLIN_BRICKS, 4).input(Character.valueOf('S'), NSColoredBlocks.WHITE_KAOLIN).pattern("SS")
        .pattern("SS").criterion("has_white_kaolin", conditionsFromItem(NSColoredBlocks.WHITE_KAOLIN)).offerTo(exporter);
    ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, NSColoredBlocks.LIGHT_GRAY_KAOLIN_BRICKS, 4).input(Character.valueOf('S'), NSColoredBlocks.LIGHT_GRAY_KAOLIN)
        .pattern("SS").pattern("SS").criterion("has_light_gray_kaolin", conditionsFromItem(NSColoredBlocks.LIGHT_GRAY_KAOLIN)).offerTo(exporter);
    ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, NSColoredBlocks.GRAY_KAOLIN_BRICKS, 4).input(Character.valueOf('S'), NSColoredBlocks.GRAY_KAOLIN).pattern("SS")
        .pattern("SS").criterion("has_gray_kaolin", conditionsFromItem(NSColoredBlocks.GRAY_KAOLIN)).offerTo(exporter);
    ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, NSColoredBlocks.BLACK_KAOLIN_BRICKS, 4).input(Character.valueOf('S'), NSColoredBlocks.BLACK_KAOLIN).pattern("SS")
        .pattern("SS").criterion("has_black_kaolin", conditionsFromItem(NSColoredBlocks.BLACK_KAOLIN)).offerTo(exporter);
    ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, NSColoredBlocks.BROWN_KAOLIN_BRICKS, 4).input(Character.valueOf('S'), NSColoredBlocks.BROWN_KAOLIN).pattern("SS")
        .pattern("SS").criterion("has_brown_kaolin", conditionsFromItem(NSColoredBlocks.BROWN_KAOLIN)).offerTo(exporter);
    ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, NSColoredBlocks.RED_KAOLIN_BRICKS, 4).input(Character.valueOf('S'), NSColoredBlocks.RED_KAOLIN).pattern("SS")
        .pattern("SS").criterion("has_red_kaolin", conditionsFromItem(NSColoredBlocks.RED_KAOLIN)).offerTo(exporter);
    ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, NSColoredBlocks.ORANGE_KAOLIN_BRICKS, 4).input(Character.valueOf('S'), NSColoredBlocks.ORANGE_KAOLIN)
        .pattern("SS").pattern("SS").criterion("has_orange_kaolin", conditionsFromItem(NSColoredBlocks.ORANGE_KAOLIN)).offerTo(exporter);
    ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, NSColoredBlocks.YELLOW_KAOLIN_BRICKS, 4).input(Character.valueOf('S'), NSColoredBlocks.YELLOW_KAOLIN)
        .pattern("SS").pattern("SS").criterion("has_yellow_kaolin", conditionsFromItem(NSColoredBlocks.YELLOW_KAOLIN)).offerTo(exporter);
    ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, NSColoredBlocks.LIME_KAOLIN_BRICKS, 4).input(Character.valueOf('S'), NSColoredBlocks.LIME_KAOLIN).pattern("SS")
        .pattern("SS").criterion("has_lime_kaolin", conditionsFromItem(NSColoredBlocks.LIME_KAOLIN)).offerTo(exporter);
    ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, NSColoredBlocks.GREEN_KAOLIN_BRICKS, 4).input(Character.valueOf('S'), NSColoredBlocks.GREEN_KAOLIN).pattern("SS")
        .pattern("SS").criterion("has_green_kaolin", conditionsFromItem(NSColoredBlocks.GREEN_KAOLIN)).offerTo(exporter);
    ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, NSColoredBlocks.CYAN_KAOLIN_BRICKS, 4).input(Character.valueOf('S'), NSColoredBlocks.CYAN_KAOLIN).pattern("SS")
        .pattern("SS").criterion("has_cyan_kaolin", conditionsFromItem(NSColoredBlocks.CYAN_KAOLIN)).offerTo(exporter);
    ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, NSColoredBlocks.LIGHT_BLUE_KAOLIN_BRICKS, 4).input(Character.valueOf('S'), NSColoredBlocks.LIGHT_BLUE_KAOLIN)
        .pattern("SS").pattern("SS").criterion("has_light_blue_kaolin", conditionsFromItem(NSColoredBlocks.LIGHT_BLUE_KAOLIN)).offerTo(exporter);
    ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, NSColoredBlocks.BLUE_KAOLIN_BRICKS, 4).input(Character.valueOf('S'), NSColoredBlocks.BLUE_KAOLIN).pattern("SS")
        .pattern("SS").criterion("has_blue_kaolin", conditionsFromItem(NSColoredBlocks.BLUE_KAOLIN)).offerTo(exporter);
    ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, NSColoredBlocks.PURPLE_KAOLIN_BRICKS, 4).input(Character.valueOf('S'), NSColoredBlocks.PURPLE_KAOLIN)
        .pattern("SS").pattern("SS").criterion("has_purple_kaolin", conditionsFromItem(NSColoredBlocks.PURPLE_KAOLIN)).offerTo(exporter);
    ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, NSColoredBlocks.MAGENTA_KAOLIN_BRICKS, 4).input(Character.valueOf('S'), NSColoredBlocks.MAGENTA_KAOLIN)
        .pattern("SS").pattern("SS").criterion("has_magenta_kaolin", conditionsFromItem(NSColoredBlocks.MAGENTA_KAOLIN)).offerTo(exporter);
    ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, NSColoredBlocks.PINK_KAOLIN_BRICKS, 4).input(Character.valueOf('S'), NSColoredBlocks.PINK_KAOLIN).pattern("SS")
        .pattern("SS").criterion("has_pink_kaolin", conditionsFromItem(NSColoredBlocks.PINK_KAOLIN)).offerTo(exporter);

    offerCarpetRecipe(exporter, RED_MOSS_CARPET, RED_MOSS_BLOCK);

    generateFlowerRecipes(NSRegistryHelper.FlowerHashMap, exporter);
    generateWoodRecipes(NSRegistryHelper.WoodHashMap, exporter);
    generateStoneRecipes(NSRegistryHelper.StoneHashMap, exporter);
    offerShapelessRecipe(exporter, Items.BROWN_DYE, NSMiscBlocks.CATTAIL, "brown_dye", 2);
    offerShapelessRecipe(exporter, Items.PINK_DYE, LOTUS_FLOWER, "pink_dye", 1);
    offerShapelessRecipe(exporter, Items.WHITE_DYE, HELVOLA_FLOWER_ITEM, "white_dye", 1);
    offerShapelessRecipe(exporter, Items.RED_DYE, ORNATE_SUCCULENT, "red_dye", 1);
    offerShapelessRecipe(exporter, Items.LIME_DYE, DROWSY_SUCCULENT, "lime_dye", 1);
    offerShapelessRecipe(exporter, Items.YELLOW_DYE, AUREATE_SUCCULENT, "yellow_dye", 1);
    offerShapelessRecipe(exporter, Items.GREEN_DYE, SAGE_SUCCULENT, "green_dye", 1);
    offerShapelessRecipe(exporter, Items.LIGHT_BLUE_DYE, FOAMY_SUCCULENT, "light_blue_dye", 1);
    offerShapelessRecipe(exporter, Items.PURPLE_DYE, IMPERIAL_SUCCULENT, "purple_dye", 1);
    offerShapelessRecipe(exporter, Items.PINK_DYE, REGAL_SUCCULENT, "pink_dye", 1);
    offerCompactingRecipe(exporter, RecipeCategory.FOOD, NSMiscBlocks.DESERT_TURNIP_BLOCK, NSMiscBlocks.DESERT_TURNIP, "desert_turnip");
    offer2x2CompactingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, NSColoredBlocks.WHITE_CHALK, NSMiscBlocks.CHALK_POWDER);

    offerShapelessRecipe(exporter, NSWoods.COCONUT_HALF, NSWoods.COCONUT_BLOCK, "coconut_half", 2);
    offerShapelessRecipe(exporter, Items.BOWL, NSWoods.COCONUT_SHELL, "bowl", 1);
    CookingRecipeJsonBuilder.createSmelting(Ingredient.fromTag(NSTags.Items.COCONUT_ITEMS), RecipeCategory.MISC, Items.CHARCOAL, 0.15F, 125)
        .criterion("has_coconut", conditionsFromTag(NSTags.Items.COCONUT_ITEMS)).offerTo(exporter, Identifier.of(MOD_ID, "charcoal_from_coconuts"));

    generateFamily(exporter, CUT_PINK_SANDSTONE_FAMILY, FeatureSet.of(FeatureFlags.VANILLA));
    generateFamily(exporter, SMOOTH_PINK_SANDSTONE_FAMILY, FeatureSet.of(FeatureFlags.VANILLA));

  }
}
